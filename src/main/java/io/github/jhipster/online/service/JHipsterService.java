/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 * <p>
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jhipster.online.service;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.service.enums.CiCdTool;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JHipsterService {

    public final Logger log = LoggerFactory.getLogger(JHipsterService.class);

    private static final String SKIP_CHECKS = "--skip-checks";

    private static final String SKIP_INSTALL = "--skip-install";

    private static final String SKIP_CACHE = "--skip-cache";

    private static final String SKIP_GIT = "--skip-git";

    private static final String FORCE = "--force";

    // Blueprints are npm packages executed during generation. JHipster Online never generates with a
    // blueprint, so they are disabled to prevent a submitted configuration from running arbitrary code.
    private static final String DISABLE_BLUEPRINTS = "--disable-blueprints";

    /**
     * Options passed to every generator-jhipster command, unless a specific command does not accept one of them.
     */
    private static final List<String> DEFAULT_OPTIONS = List.of(SKIP_CHECKS, SKIP_INSTALL, SKIP_CACHE, SKIP_GIT, DISABLE_BLUEPRINTS, FORCE);

    /**
     * Node.js permission model flags (https://nodejs.org/api/permissions.html).
     *
     * <p>generator-jhipster is run with {@code node --permission}, so the process can only read the
     * generator installation and the generation directory, and can only write inside the generation
     * directory. A user-submitted configuration therefore cannot make the generator write anywhere else.
     * The remaining flags are required by generator-jhipster itself: it spawns {@code git} even with
     * {@code --skip-git}, uses worker threads, and loads a native addon through {@code unrs-resolver}.
     */
    private static final String NODE_PERMISSION = "--permission";
    private static final String NODE_ALLOW_FS_READ = "--allow-fs-read=";
    private static final String NODE_ALLOW_FS_WRITE = "--allow-fs-write=";
    private static final List<String> NODE_EXTRA_PERMISSIONS = List.of("--allow-child-process", "--allow-worker", "--allow-addons");

    private static final String INSTALL_PATH = "--install-path";

    private static final String JHIPSTER_CLI_SCRIPT = "cli/jhipster.cjs";

    /**
     * Name of the per-generation temporary directory, created inside the generation directory and handed to the
     * generator through the environment variables read by Node.js {@code os.tmpdir()}. generator-jhipster writes
     * there (for instance to generate the KeyStore with {@code keytool}), so it is the only temporary location the
     * confined process can use. It lives inside the generation directory, which is already readable and writable,
     * because the Node.js permission model (v24) denies a path once a sibling sharing its prefix is also allowed:
     * granting both {@code /tmp/wd} and {@code /tmp/wd-tmp} makes {@code /tmp/wd} inaccessible. It is deleted
     * before the generation directory is zipped or pushed.
     */
    private static final String TEMPORARY_DIR_NAME = ".jhipster-online-tmp";
    private static final List<String> TEMPORARY_DIR_VARIABLES = List.of("TMPDIR", "TMP", "TEMP");

    private final LogsService logsService;

    private final Executor taskExecutor;

    private final String jhipsterCommand;

    private final String nodeCommand;

    private volatile Path installPath;

    private final Integer timeout;

    public JHipsterService(LogsService logsService, ApplicationProperties applicationProperties, Executor taskExecutor) {
        this.logsService = logsService;
        this.taskExecutor = taskExecutor;

        jhipsterCommand = applicationProperties.getJhipsterCmd().getCmd();
        nodeCommand = applicationProperties.getJhipsterCmd().getNodeCmd();
        timeout = applicationProperties.getJhipsterCmd().getTimeout();

        log.info("JHipster service will be using \"{}\" to run generator-jhipster with \"{} --permission\".", jhipsterCommand, nodeCommand);
    }

    public void generateApplication(String generationId, File workingDir) throws IOException {
        this.logsService.addLog(generationId, "Running JHipster");
        this.runJHipster(generationId, workingDir, DEFAULT_OPTIONS);
    }

    public void runImportJdl(String generationId, File workingDir, String jdlFileName) throws IOException {
        this.logsService.addLog(generationId, "Running `jhipster import-jdl`");
        this.runJHipster(generationId, workingDir, DEFAULT_OPTIONS, "import-jdl", jdlFileName + ".jh");
    }

    public void addCiCd(String generationId, File workingDir, CiCdTool ciCdTool) throws IOException {
        if (ciCdTool == null) {
            this.logsService.addLog(generationId, "Continuous Integration system not supported, aborting");
            throw new IllegalArgumentException("Invalid Continuous Integration system");
        }
        this.logsService.addLog(generationId, "Running `jhipster ci-cd`");
        // The ci-cd command does not accept --skip-git.
        List<String> options = DEFAULT_OPTIONS.stream().filter(option -> !SKIP_GIT.equals(option)).collect(Collectors.toList());
        this.runJHipster(generationId, workingDir, options, "ci-cd", "--autoconfigure-" + ciCdTool.command());
    }

    /**
     * Runs a generator-jhipster command under {@code node --permission}: {@code jhipster <args> <options>}.
     */
    private void runJHipster(String generationId, File workingDir, List<String> options, String... args) throws IOException {
        Path temporaryDir = temporaryDirectory(workingDir);
        try {
            List<String> command = jhipsterCommand(workingDir);
            command.addAll(Arrays.asList(args));
            command.addAll(options);
            Map<String, String> environment = TEMPORARY_DIR_VARIABLES
                .stream()
                .collect(Collectors.toMap(variable -> variable, variable -> temporaryDir.toString()));
            this.runProcess(generationId, workingDir, environment, command.toArray(new String[0]));
        } finally {
            FileUtils.deleteDirectory(temporaryDir.toFile());
        }
    }

    /**
     * Creates, if needed, the temporary directory of a generation and returns its real path.
     */
    private static Path temporaryDirectory(File workingDir) throws IOException {
        Path temporaryDir = workingDir.toPath().toRealPath().resolve(TEMPORARY_DIR_NAME);
        FileUtils.forceMkdir(temporaryDir.toFile());
        return temporaryDir;
    }

    /**
     * Builds the {@code node --permission ... jhipster.cjs} command used to run generator-jhipster
     * confined to the generator installation and to {@code workingDir}.
     */
    List<String> jhipsterCommand(File workingDir) throws IOException {
        Path installPath = installPath();
        Path script = installPath.resolve(JHIPSTER_CLI_SCRIPT);
        Path generatorRoot = findPackageRoot(installPath);
        Path generationDir = workingDir.toPath().toRealPath();
        Path globalYoRc = Paths.get(System.getProperty("user.home"), ".yo-rc-global.json");

        List<String> command = new ArrayList<>();
        command.add(nodeCommand);
        command.add(NODE_PERMISSION);
        command.add(NODE_ALLOW_FS_READ + generatorRoot);
        command.add(NODE_ALLOW_FS_READ + globalYoRc);
        command.add(NODE_ALLOW_FS_READ + generationDir);
        command.add(NODE_ALLOW_FS_WRITE + generationDir);
        command.addAll(NODE_EXTRA_PERMISSIONS);
        command.add(script.toString());
        return command;
    }

    /**
     * Returns the generator-jhipster install path ({@code <package>/dist}), resolved once with
     * {@code jhipster --install-path} and cached for the lifetime of the service.
     */
    Path installPath() throws IOException {
        Path path = installPath;
        if (path == null) {
            synchronized (this) {
                path = installPath;
                if (path == null) {
                    path = resolveInstallPath();
                    installPath = path;
                }
            }
        }
        return path;
    }

    private Path resolveInstallPath() throws IOException {
        String output;
        int exitValue;
        try {
            Process process = new ProcessBuilder(jhipsterCommand, INSTALL_PATH).redirectError(ProcessBuilder.Redirect.DISCARD).start();
            output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!process.waitFor(timeout, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new IOException("Timeout while running \"" + jhipsterCommand + " " + INSTALL_PATH + "\"");
            }
            exitValue = process.exitValue();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while running \"" + jhipsterCommand + " " + INSTALL_PATH + "\"", e);
        }
        if (exitValue != 0) {
            throw new IOException("\"" + jhipsterCommand + " " + INSTALL_PATH + "\" failed with exit code " + exitValue + ": " + output);
        }
        // The path is printed on the last line, after the JHipster banner.
        String lastLine = output.lines().map(String::trim).filter(line -> !line.isEmpty()).reduce((first, second) -> second).orElse("");
        Path path = Paths.get(lastLine);
        if (!path.isAbsolute() || !Files.isRegularFile(path.resolve(JHIPSTER_CLI_SCRIPT))) {
            throw new IOException(
                "\"" + jhipsterCommand + " " + INSTALL_PATH + "\" did not return a generator-jhipster install path: " + output
            );
        }
        Path realPath = path.toRealPath();
        log.info("generator-jhipster install path resolved to \"{}\"", realPath);
        return realPath;
    }

    /**
     * Finds the root of the npm package containing {@code installPath}: the nearest directory, starting
     * from it, holding both a {@code package.json} and a {@code node_modules} directory, so the generator
     * dependencies are readable.
     */
    private static Path findPackageRoot(Path installPath) throws IOException {
        Path packageJsonOnly = null;
        for (Path dir = installPath; dir != null; dir = dir.getParent()) {
            if (Files.exists(dir.resolve("package.json"))) {
                if (Files.isDirectory(dir.resolve("node_modules"))) {
                    return dir;
                }
                if (packageJsonOnly == null) {
                    packageJsonOnly = dir;
                }
            }
        }
        if (packageJsonOnly != null) {
            return packageJsonOnly;
        }
        throw new IOException("Unable to find the generator-jhipster package containing \"" + installPath + "\"");
    }

    void runProcess(String generationId, File workingDir, String... command) throws IOException {
        this.runProcess(generationId, workingDir, Map.of(), command);
    }

    void runProcess(String generationId, File workingDir, Map<String, String> environment, String... command) throws IOException {
        log.info("Running command: \"{}\" in directory:  \"{}\"", command, workingDir);
        ProcessBuilder processBuilder = new ProcessBuilder()
            .directory(workingDir)
            .command(command)
            .redirectError(ProcessBuilder.Redirect.DISCARD);
        processBuilder.environment().putAll(environment);
        Process p = processBuilder.start();

        taskExecutor.execute(
            () -> {
                try {
                    p.waitFor(timeout, TimeUnit.SECONDS);
                    p.destroyForcibly();
                } catch (InterruptedException e) {
                    log.error("Unable to execute process successfully.", e);
                    Thread.currentThread().interrupt();
                }
            }
        );

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            log.debug(line);
            this.logsService.addLog(generationId, line);
        }
        input.close();
    }
}
