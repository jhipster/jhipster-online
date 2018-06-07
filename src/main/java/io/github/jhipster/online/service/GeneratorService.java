/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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

import java.io.*;
import java.net.URISyntaxException;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.Language;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.enums.GitProvider;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.zeroturnaround.zip.ZipUtil;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.User;

@Service
public class GeneratorService {

    private final Logger log = LoggerFactory.getLogger(GeneratorService.class);

    private final ApplicationProperties applicationProperties;

    private final GitService gitService;

    private final JHipsterService jHipsterService;

    private final LogsService logsService;

    private final YoRCService yoRCService;

    private final UserService userService;

    private final OwnerIdentityService ownerIdentityService;

    private final LanguageService languageService;

    public GeneratorService(ApplicationProperties applicationProperties, GitService gitService, JHipsterService
        jHipsterService, LogsService
                                logsService, YoRCService yoRCService, UserService userService, OwnerIdentityService ownerIdentityService, LanguageService languageService) {
        this.applicationProperties = applicationProperties;
        this.gitService = gitService;
        this.jHipsterService = jHipsterService;
        this.logsService = logsService;
        this.yoRCService = yoRCService;
        this.userService = userService;
        this.ownerIdentityService = ownerIdentityService;
        this.languageService = languageService;
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

    public void generateGitApplication(User user, String applicationId,
                                       String applicationConfiguration, String githubOrganization, String applicationName,
                                       GitProvider gitProvider) throws IOException, GitAPIException, URISyntaxException {
        File workingDir = generateApplication(applicationId, applicationConfiguration);
        this.logsService.addLog(applicationId, "Pushing the application to the Git remote repository");
        this.gitService.pushNewApplicationToGit(user, workingDir, githubOrganization, applicationName, gitProvider);
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

    private void generateYoRc(String applicationId, File workingDir, String applicationConfiguration)
        throws IOException {

        this.logsService.addLog(applicationId, "Creating `.yo-rc.json` file");
        try {
            PrintWriter writer = new PrintWriter(workingDir + "/.yo-rc.json", "UTF-8");
            writer.print(applicationConfiguration);
            writer.close();
            saveYoRc(applicationConfiguration);
        } catch (IOException ioe) {
            log.error("Error creating file .yo-rc.json", ioe);
            throw ioe;
        }
    }

    private void saveYoRc(String applicationConfiguration) {
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Application configuration:\n{}", applicationConfiguration);
        try {
            JsonNode jsonNodeRoot = mapper.readTree(applicationConfiguration);
            JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
            YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
            yorc.setOwner(ownerIdentityService.findOrCreateUser(userService.getUser()));
            yoRCService.save(yorc);
            yorc.getSelectedLanguages().forEach(languageService::save);
            log.debug("Parsed json:\n{}", yorc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipResult(File workingDir) {
        ZipUtil.pack(workingDir, new File(workingDir + ".zip"));
    }
}
