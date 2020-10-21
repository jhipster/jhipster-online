package io.github.jhipster.online.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ApplicationProperties.class)
@TestPropertySource("classpath:config/application.yml")
class GeneratorServiceTest {

    private final String applicationId = "app-id";
    private final String applicationConfiguration = "app-config";

    @Autowired
    private ApplicationProperties applicationProperties;

    @Mock
    private GitService gitService;

    @Mock
    private JHipsterService jHipsterService;

    @Mock
    private LogsService logsService;

    private GeneratorService generatorService;

    @BeforeEach
    void shouldConstructGeneratorService() {
        generatorService = new GeneratorService(applicationProperties, gitService, jHipsterService, logsService);
    }

    @Test
    void generateZippedApplication() throws IOException {
        String result = generatorService.generateZippedApplication(applicationId, applicationConfiguration);

        assertThat(result).isEqualTo("/tmp/jhipster/applications/app-id.zip");
        verify(logsService).addLog(applicationId, "Creating `.yo-rc.json` file");
        verify(jHipsterService).generateApplication(applicationId, new File("/tmp/jhipster/applications/app-id"));
        assertThat(new File("/tmp/jhipster/applications/app-id/.yo-rc.json")).isFile().hasContent(applicationConfiguration);
    }

    @Test
    void generateGitApplication() throws GitAPIException, IOException, URISyntaxException {
        User user = mock(User.class);
        File workingDir = new File("/tmp/jhipster/applications/app-id");

        generatorService.generateGitApplication(user, applicationId, applicationConfiguration, "gh-org", "repo", GitProvider.GITHUB);

        verify(logsService).addLog(applicationId, "Creating `.yo-rc.json` file");
        verify(jHipsterService).generateApplication(applicationId, new File("/tmp/jhipster/applications/app-id"));
        assertThat(new File("/tmp/jhipster/applications/app-id/.yo-rc.json")).isFile().hasContent(applicationConfiguration);
        verify(logsService).addLog(applicationId, "Pushing the application to the Git remote repository");
        verify(gitService).pushNewApplicationToGit(user, workingDir, "gh-org", "repo", GitProvider.GITHUB);
        verify(logsService).addLog(applicationId, "Application successfully pushed!");
        verify(gitService).cleanUpDirectory(workingDir);
    }
}
