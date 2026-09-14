/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jhipster.online.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.service.enums.CiCdTool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ApplicationProperties.class)
@TestPropertySource("classpath:config/application.yml")
class JHipsterServiceTest {

    @Mock
    private LogsService logsService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Mock
    private Executor taskExecutor;

    private JHipsterService jHipsterService;

    private JHipsterService jHipsterServiceSpy;

    @TempDir
    private Path generatorDir;

    private Path jhipsterScript;

    @BeforeEach
    void shouldConstructJHipsterService() throws IOException {
        applicationProperties.getJhipsterCmd().setCmd(createFakeJHipsterInstallation().toString());

        jHipsterService = new JHipsterService(logsService, applicationProperties, taskExecutor);
        jHipsterServiceSpy = spy(jHipsterService);
    }

    /**
     * Mimics an installation of generator-jhipster: a package with a nested {@code dist/package.json}, a
     * {@code node_modules} directory, and a {@code jhipster} command printing the banner and the install
     * path ({@code dist}), as {@code jhipster --install-path} does. Each invocation is recorded in
     * {@code invocations.log}.
     */
    private Path createFakeJHipsterInstallation() throws IOException {
        Path packageRoot = generatorDir.resolve("generator-jhipster");
        Files.createDirectories(packageRoot.resolve("node_modules"));
        Files.createDirectories(packageRoot.resolve("dist/cli"));
        Files.writeString(packageRoot.resolve("package.json"), "{}");
        Files.writeString(packageRoot.resolve("dist/package.json"), "{}");
        jhipsterScript = Files.writeString(packageRoot.resolve("dist/cli/jhipster.cjs"), "");
        return createFakeJHipsterCommand("echo 'Welcome to JHipster v9.3.0'; echo; echo '" + packageRoot.resolve("dist") + "'");
    }

    private Path createFakeJHipsterCommand(String body) throws IOException {
        Path command = generatorDir.resolve("bin/jhipster");
        Files.createDirectories(command.getParent());
        Files.writeString(command, "#!/bin/sh\necho invoked >> '" + generatorDir.resolve("invocations.log") + "'\n" + body + "\n");
        command.toFile().setExecutable(true);
        return command;
    }

    private long fakeJHipsterInvocations() throws IOException {
        Path log = generatorDir.resolve("invocations.log");
        return Files.exists(log) ? Files.lines(log).count() : 0;
    }

    private String[] expectedJHipsterCommand(Path workingDir, String... args) throws IOException {
        Path generationDir = workingDir.toRealPath();
        List<String> command = new ArrayList<>(
            List.of(
                applicationProperties.getJhipsterCmd().getNodeCmd(),
                "--permission",
                "--allow-fs-read=" + generatorDir.resolve("generator-jhipster").toRealPath(),
                "--allow-fs-read=" + Path.of(System.getProperty("user.home"), ".yo-rc-global.json"),
                "--allow-fs-read=" + generationDir,
                "--allow-fs-write=" + generationDir,
                "--allow-child-process",
                "--allow-worker",
                "--allow-addons",
                jhipsterScript.toRealPath().toString()
            )
        );
        command.addAll(List.of(args));
        return command.toArray(new String[0]);
    }

    @Test
    void shouldRunProcess(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";

        jHipsterService.runProcess(generationId, tempDir.toFile(), "echo", "hello from the process");

        verify(taskExecutor).execute(any(Runnable.class));
        verify(logsService).addLog(generationId, "hello from the process");
    }

    @Test
    void shouldGenerateApplication(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--skip-git",
                    "--disable-blueprints",
                    "--force"
                )
            );

        jHipsterServiceSpy.generateApplication(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Running JHipster");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--skip-git",
                    "--disable-blueprints",
                    "--force"
                )
            );
    }

    @Test
    void shouldRunImportJdl(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        String jdlFileName = "test.jdl";
        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "import-jdl",
                    jdlFileName + ".jh",
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--skip-git",
                    "--disable-blueprints",
                    "--force"
                )
            );

        jHipsterServiceSpy.runImportJdl(generationId, tempDir.toFile(), jdlFileName);

        verify(logsService).addLog(generationId, "Running `jhipster import-jdl`");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "import-jdl",
                    jdlFileName + ".jh",
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--skip-git",
                    "--disable-blueprints",
                    "--force"
                )
            );
    }

    @Test
    void shouldAddCiCd(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        CiCdTool ciCdTool = CiCdTool.JENKINS;
        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "ci-cd",
                    "--autoconfigure-" + ciCdTool.command(),
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--disable-blueprints",
                    "--force"
                )
            );

        jHipsterServiceSpy.addCiCd(generationId, tempDir.toFile(), ciCdTool);

        verify(logsService).addLog(generationId, "Running `jhipster ci-cd`");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                expectedJHipsterCommand(
                    tempDir,
                    "ci-cd",
                    "--autoconfigure-" + ciCdTool.command(),
                    "--skip-checks",
                    "--skip-install",
                    "--skip-cache",
                    "--disable-blueprints",
                    "--force"
                )
            );
    }

    @Test
    void shouldNotAdCiCdIfToolIsMissing(@TempDir Path tempDir) {
        String generationId = "generation-id";

        Throwable thrown = catchThrowable(() -> jHipsterServiceSpy.addCiCd(generationId, tempDir.toFile(), null));

        verify(logsService).addLog(generationId, "Continuous Integration system not supported, aborting");

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid Continuous Integration system");
    }

    @Test
    void shouldResolveInstallPathOnceAndCacheIt(@TempDir Path tempDir) throws IOException {
        assertThat(fakeJHipsterInvocations()).isZero();

        List<String> first = jHipsterService.jhipsterCommand(tempDir.toFile());
        List<String> second = jHipsterService.jhipsterCommand(tempDir.toFile());

        assertThat(first).containsExactly(expectedJHipsterCommand(tempDir));
        assertThat(second).isEqualTo(first);
        assertThat(fakeJHipsterInvocations()).isEqualTo(1);
    }

    @Test
    void shouldRejectMissingJHipsterCommand(@TempDir Path tempDir) {
        applicationProperties.getJhipsterCmd().setCmd("jhipster-command-that-does-not-exist");
        JHipsterService service = new JHipsterService(logsService, applicationProperties, taskExecutor);

        Throwable thrown = catchThrowable(() -> service.jhipsterCommand(tempDir.toFile()));

        assertThat(thrown).isInstanceOf(IOException.class);
    }

    @Test
    void shouldRejectFailingInstallPathCommand(@TempDir Path tempDir) throws IOException {
        applicationProperties.getJhipsterCmd().setCmd(createFakeJHipsterCommand("echo 'boom'; exit 3").toString());
        JHipsterService service = new JHipsterService(logsService, applicationProperties, taskExecutor);

        Throwable thrown = catchThrowable(() -> service.jhipsterCommand(tempDir.toFile()));

        assertThat(thrown).isInstanceOf(IOException.class).hasMessageContaining("failed with exit code 3");
    }

    @Test
    void shouldRejectInstallPathWithoutJHipsterScript(@TempDir Path tempDir) throws IOException {
        applicationProperties.getJhipsterCmd().setCmd(createFakeJHipsterCommand("echo '" + tempDir + "'").toString());
        JHipsterService service = new JHipsterService(logsService, applicationProperties, taskExecutor);

        Throwable thrown = catchThrowable(() -> service.jhipsterCommand(tempDir.toFile()));

        assertThat(thrown).isInstanceOf(IOException.class).hasMessageContaining("did not return a generator-jhipster install path");
    }
}
