/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.zeroturnaround.zip.ZipUtil;

@Service
public class GeneratorService {

    private final Logger log = LoggerFactory.getLogger(GeneratorService.class);

    private final ApplicationProperties applicationProperties;

    private final GitService gitService;

    private final JHipsterService jHipsterService;

    private final LogsService logsService;

    public GeneratorService(
        ApplicationProperties applicationProperties,
        GitService gitService,
        JHipsterService jHipsterService,
        LogsService logsService
    ) {
        this.applicationProperties = applicationProperties;
        this.gitService = gitService;
        this.jHipsterService = jHipsterService;
        this.logsService = logsService;
    }

    public String generateZippedApplication(String applicationId, String applicationConfiguration) throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();
        File workingDir = generateApplication(applicationId, applicationConfiguration);
        this.zipResult(workingDir);
        watch.stop();
        log.info("Zipped application generated in {} ms", watch.getTotalTimeMillis());
        return workingDir + ".zip";
    }

    public void generateGitApplication(
        User user,
        String applicationId,
        String applicationConfiguration,
        String githubOrganization,
        String repositoryName,
        GitProvider gitProvider
    )
        throws IOException, GitAPIException, URISyntaxException {
        File workingDir = generateApplication(applicationId, applicationConfiguration);
        this.logsService.addLog(applicationId, "Pushing the application to the Git remote repository");
        this.gitService.pushNewApplicationToGit(user, workingDir, githubOrganization, repositoryName, gitProvider);
        this.logsService.addLog(applicationId, "Application successfully pushed!");
        this.gitService.cleanUpDirectory(workingDir);
    }

    private File generateApplication(String applicationId, String applicationConfiguration) throws IOException {
        File workingDir = new File(applicationProperties.getTmpFolder() + "/jhipster/applications/" + applicationId);
        FileUtils.forceMkdir(workingDir);
        this.generateYoRc(applicationId, workingDir, applicationConfiguration);
        log.info(".yo-rc.json created");
        this.jHipsterService.generateApplication(applicationId, workingDir);
        log.info("Application generated");
        return workingDir;
    }

    private void generateYoRc(String applicationId, File workingDir, String applicationConfiguration) throws IOException {
        this.logsService.addLog(applicationId, "Creating `.yo-rc.json` file");
        try (PrintWriter writer = new PrintWriter(workingDir + "/.yo-rc.json", StandardCharsets.UTF_8)) {
            writer.print(applicationConfiguration);
        } catch (IOException ioe) {
            log.error("Error creating file .yo-rc.json", ioe);
            throw ioe;
        }
    }

    private void zipResult(File workingDir) {
        ZipUtil.pack(workingDir, new File(workingDir + ".zip"));
    }
}
