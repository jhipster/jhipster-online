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

import static io.github.jhipster.online.service.GeneratorService.*;
import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        final String cwd = buildCwdPath();

        final String expected = cwd + ".zip";
        final String expectedYoRcPath = cwd + FILE_SEPARATOR + ".yo-rc.json";

        final String result = generatorService.generateZippedApplication(applicationId, applicationConfiguration);

        assertThat(result).isEqualTo(expected);
        verify(logsService).addLog(applicationId, "Creating `.yo-rc.json` file");
        verify(jHipsterService).generateApplication(applicationId, new File(cwd));
        assertThat(new File(expectedYoRcPath)).isFile().hasContent(applicationConfiguration);
    }

    @Test
    void generateGitApplication() throws GitAPIException, IOException, URISyntaxException {
        User user = mock(User.class);
        final String cwd = buildCwdPath();
        File workingDir = new File(cwd);

        generatorService.generateGitApplication(user, applicationId, applicationConfiguration, "gh-org", "repo", GitProvider.GITHUB);

        verify(logsService).addLog(applicationId, "Creating `.yo-rc.json` file");
        verify(jHipsterService).generateApplication(applicationId, workingDir);
        assertThat(new File(cwd + FILE_SEPARATOR + ".yo-rc.json")).isFile().hasContent(applicationConfiguration);
        verify(logsService).addLog(applicationId, "Pushing the application to the Git remote repository");
        verify(gitService).pushNewApplicationToGit(user, workingDir, "gh-org", "repo", GitProvider.GITHUB);
        verify(logsService).addLog(applicationId, "Application successfully pushed!");
        verify(gitService).cleanUpDirectory(workingDir);
    }

    private String buildCwdPath() {
        final String fromConfig = applicationProperties.getTmpFolder();
        String workingCopy = StringUtils.isBlank(fromConfig) ? OS_TEMP_DIR : fromConfig;
        // handling the trailing file separator in case we are in windows
        if (workingCopy.endsWith(FILE_SEPARATOR)) {
            int length = workingCopy.length();
            workingCopy = workingCopy.substring(0, length - 1);
        }
        return String.join(FILE_SEPARATOR, asList(workingCopy, JHIPSTER, APPLICATIONS, applicationId));
    }
}
