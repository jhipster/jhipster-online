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

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.zeroturnaround.zip.ZipUtil;

@Service
public class GeneratorService {

    public static final String JHIPSTER = "jhipster";

    public static final String APPLICATIONS = "applications";

    public static final String OS_TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private final Logger log = LoggerFactory.getLogger(GeneratorService.class);

    private final ApplicationProperties applicationProperties;

    private final GitService gitService;

    private final JHipsterService jHipsterService;

    private final LogsService logsService;

    @Value("${openshift.devspace.url-devfile}")
    private String devSpaces;

    @Value("${openshift.tekton.url-pipeline}")
    private String pipelineJhipster;

    @Value("${openshift.tekton.url-pipeline-run}")
    private String pipelineJhipsterRun;

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
        final String fromConfig = applicationProperties.getTmpFolder();
        final String tempDir = StringUtils.isBlank(fromConfig) ? OS_TEMP_DIR : fromConfig;
        final File workingDir = new File(String.join(FILE_SEPARATOR, tempDir, JHIPSTER, APPLICATIONS, applicationId));
        FileUtils.forceMkdir(workingDir);
        this.generateYoRc(applicationId, workingDir, applicationConfiguration);
        log.info(".yo-rc.json created");
        this.generateDevSpaces(applicationId, workingDir);
        log.info("devfile.yaml created");
        this.generateTektonPipeline(applicationId, workingDir);
        log.info("pipeline.yaml and pipeline-run.yaml created");
        // this.jHipsterService.yqPatchPipelineRun(applicationId, workingDir, applicationConfiguration);
        log.info("yq script created");
        this.generateYqScript(applicationId, workingDir, applicationConfiguration);
        this.jHipsterService.generateApplication(applicationId, workingDir);
        log.info("Application generated");
        return workingDir;
    }

    private void generateYoRc(String applicationId, File workingDir, String applicationConfiguration) throws IOException {
        this.logsService.addLog(applicationId, "Creating `.yo-rc.json` file");
        // removed the catch/log/throw since the exception is handled in calling code.
        PrintWriter writer = new PrintWriter(workingDir + "/.yo-rc.json", StandardCharsets.UTF_8);
        writer.print(applicationConfiguration);
        writer.close();
    }

    private void generateDevSpaces(String applicationId, File workingDir) throws IOException {
        this.logsService.addLog(applicationId, "Creating `devfile.yaml` file");
        PrintWriter writer = new PrintWriter(workingDir + "/devfile.yaml", StandardCharsets.UTF_8);
        writer.print(IOUtils.toString(new URL(devSpaces).openStream(), StandardCharsets.UTF_8));
        writer.close();
    }

    private void generateTektonPipeline(String applicationId, File workingDir) throws IOException {
        this.logsService.addLog(applicationId, "Creating `pipeline.yaml` file");
        PrintWriter writer = new PrintWriter(workingDir + "/pipeline.yaml", StandardCharsets.UTF_8);
        writer.print(IOUtils.toString(new URL(pipelineJhipster).openStream(), StandardCharsets.UTF_8));
        writer.flush();
        writer.close();

        writer = new PrintWriter(workingDir + "/pipeline-run.yaml", StandardCharsets.UTF_8);
        writer.print(IOUtils.toString(new URL(pipelineJhipsterRun).openStream(), StandardCharsets.UTF_8));
        writer.flush();
        writer.close();
    }

    //TODO re-name pipeline name value from jhipster-pipeline.yaml

    private void generateYqScript(String applicationId, File workingDir, String applicationConfiguration) throws IOException {
        this.logsService.addLog(applicationId, "Creating `yq-script` file");
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(applicationConfiguration);
        String gitCompany = JsonPath.read(document, "$.git-company");
        String repositoryName = JsonPath.read(document, "$.repository-name");
        //String gitHost = JsonPath.read(document, "$.git-provider");
        String gitRepo =
            "'(.spec.params[] | select(.name == \"GIT_REPO\").value) |=\"https://github.com/" + gitCompany + "/" + repositoryName + "\"'";
        String appJarVersion =
            "'(.spec.params[] | select(.name == \"APP_JAR_VERSION\").value) |=\"" + repositoryName + "-0.0.1-SNAPSHOT.jar\"" + "'";
        //String pipelineName = "'.metadata.name=\"" + repositoryName + "\"'";
        // removed the catch/log/throw since the exception is handled in calling code.
        PrintWriter writer = new PrintWriter(workingDir + "/yq-script", StandardCharsets.UTF_8);
        writer.println("#!/bin/sh");
        //writer.println("yq -Yi " + pipelineName + " /pipeline-run.yaml");
        writer.println("yq -Yi " + gitRepo + " pipeline-run.yaml");
        writer.println("yq -Yi " + appJarVersion + " pipeline-run.yaml");
        writer.flush();
        writer.close();
    }

    private void zipResult(File workingDir) {
        ZipUtil.pack(workingDir, new File(workingDir + ".zip"));
    }
}
