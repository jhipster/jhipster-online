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
        jHipsterService = new JHipsterService(logsService, applicationProperties, taskExecutor);
        jHipsterServiceSpy = spy(jHipsterService);
    }

    @Test
    void shouldRunProcess(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";

        jHipsterService.installNpmDependencies(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Installing the JHipster version used by the project");
        verify(taskExecutor).execute(any(Runnable.class));
        verify(logsService).addLog(eq(generationId), startsWith("up to date in"));
        verify(logsService).addLog(generationId, "found 0 vulnerabilities");
    }

    @Test
    void shouldInstallNpmDependencies(@TempDir Path tempDir) throws IOException {
        String generationId = "generation-id";
        willDoNothing()
            .given(jHipsterServiceSpy)
            .runProcess(generationId, tempDir.toFile(), "npm", "install", "--ignore" + "-scripts", "--package-lock-only");

        jHipsterServiceSpy.installNpmDependencies(generationId, tempDir.toFile());

        verify(logsService).addLog(generationId, "Installing the JHipster version used by the project");
        verify(jHipsterServiceSpy)
            .runProcess(generationId, tempDir.toFile(), "npm", "install", "--ignore-scripts", "--package" + "-lock" + "-only");
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
                "--prettier-java"
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
                "--prettier-java"
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
