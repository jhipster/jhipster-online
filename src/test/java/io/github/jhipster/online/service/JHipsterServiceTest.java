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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.service.enums.CiCdTool;
import java.io.IOException;
import java.nio.file.Path;
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

    @BeforeEach
    void shouldConstructJHipsterService() {
        final String os = System.getProperty("os.name");
        final String command = os.contains("indows") ? "npm.cmd" : applicationProperties.getNpmCmd().getCmd();
        applicationProperties.getNpmCmd().setCmd(command);

        jHipsterService = new JHipsterService(logsService, applicationProperties, taskExecutor);
        jHipsterServiceSpy = spy(jHipsterService);
    }

    @Test
    void shouldRunProcess(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";

        jHipsterService.installNpmDependencies(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Installing the JHipster version used by the project");
        verify(taskExecutor).execute(any(Runnable.class));
        verify(logsService).addLog(eq(generationId), startsWith("up to date"));
        //verify(logsService).addLog(generationId, "found 0 vulnerabilities");
    }

    @Test
    void shouldInstallNpmDependencies(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        final String command = applicationProperties.getNpmCmd().getCmd();
        applicationProperties.getNpmCmd().setCmd(command);

        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(generationId, tempDir.toFile(), command, "install", "--ignore" + "-scripts", "--package-lock-only");

        jHipsterServiceSpy.installNpmDependencies(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Installing the JHipster version used by the project");
        verify(jHipsterServiceSpy)
            .runProcess(generationId, tempDir.toFile(), command, "install", "--ignore-scripts", "--package" + "-lock" + "-only");
    }

    @Test
    void shouldGenerateApplication(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                applicationProperties.getJhipsterCmd().getCmd(),
                "--force-insight",
                "--skip" + "-checks",
                "--skip-install",
                "--skip-cache",
                "--skip-git",
                "--force"
            );

        jHipsterServiceSpy.generateApplication(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Running JHipster");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                applicationProperties.getJhipsterCmd().getCmd(),
                "--force-insight",
                "--skip" + "-checks",
                "--skip-install",
                "--skip-cache",
                "--skip-git",
                "--force"
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
                applicationProperties.getJhipsterCmd().getCmd(),
                "import-jdl",
                jdlFileName + ".jh",
                "--force-insight",
                "--skip-checks",
                "--skip-install",
                "--force"
            );

        jHipsterServiceSpy.runImportJdl(generationId, tempDir.toFile(), jdlFileName);

        verify(logsService).addLog(generationId, "Running `jhipster import-jdl`");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                applicationProperties.getJhipsterCmd().getCmd(),
                "import-jdl",
                jdlFileName + ".jh",
                "--force-insight",
                "--skip-checks",
                "--skip-install",
                "--force"
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
                applicationProperties.getJhipsterCmd().getCmd(),
                "ci-cd",
                "--autoconfigure-" + ciCdTool.command(),
                "--force-insight",
                "--skip-checks",
                "--skip-install",
                "--force"
            );

        jHipsterServiceSpy.addCiCd(generationId, tempDir.toFile(), ciCdTool);

        verify(logsService).addLog(generationId, "Running `jhipster ci-cd`");
        verify(jHipsterServiceSpy)
            .runProcess(
                generationId,
                tempDir.toFile(),
                applicationProperties.getJhipsterCmd().getCmd(),
                "ci-cd",
                "--autoconfigure-" + ciCdTool.command(),
                "--force-insight",
                "--skip-checks",
                "--skip-install",
                "--force"
            );
    }

    @Test
    void shouldNotAdCiCdIfToolIsMissing(@TempDir Path tempDir) {
        String generationId = "generation-id";

        Throwable thrown = catchThrowable(() -> jHipsterServiceSpy.addCiCd(generationId, tempDir.toFile(), null));

        verify(logsService).addLog(generationId, "Continuous Integration system not supported, aborting");

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid Continuous Integration system");
    }
}
