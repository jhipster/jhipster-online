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
import io.github.jhipster.online.service.enums.CiCdTool;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CiCdService {

    private final Logger log = LoggerFactory.getLogger(CiCdService.class);

    private final LogsService logsService;

    private final GitService gitService;

    private final GithubService githubService;

    private final GitlabService gitlabService;

    private final JHipsterService jHipsterService;

    private final ApplicationProperties applicationProperties;

    public CiCdService(
        LogsService logsService,
        GitService gitService,
        GithubService githubService,
        GitlabService gitlabService,
        JHipsterService jHipsterService,
        ApplicationProperties applicationProperties
    ) {
        this.logsService = logsService;
        this.gitService = gitService;
        this.githubService = githubService;
        this.gitlabService = gitlabService;
        this.jHipsterService = jHipsterService;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Apply a CI-CD configuration to an existing repository.
     */
    @Async
    public void configureCiCd(
        User user,
        String organizationName,
        String projectName,
        CiCdTool ciCdTool,
        String ciCdId,
        GitProvider gitProvider
    ) {
        try {
            log.info("Beginning to configure CI with {} to {} / {}", ciCdTool, organizationName, projectName);
            boolean isGitHub = gitProvider.equals(GitProvider.GITHUB);
            this.logsService.addLog(
                    ciCdId,
                    "Cloning " + (isGitHub ? "GitHub" : "GitLab") + " repository `" + organizationName + "/" + projectName + "`"
                );

            File workingDir = new File(applicationProperties.getTmpFolder() + "/jhipster/applications/" + ciCdId);
            FileUtils.forceMkdir(workingDir);
            Git git = this.gitService.cloneRepository(user, workingDir, organizationName, projectName, gitProvider);

            String branchName = ciCdTool.branchName(ciCdId);
            this.logsService.addLog(ciCdId, "Creating branch `" + branchName + "`");
            this.gitService.createBranch(git, branchName);

            this.logsService.addLog(ciCdId, "Generating Continuous Integration configuration");
            //this.jHipsterService.installNpmDependencies(ciCdId, workingDir);
            this.jHipsterService.addCiCd(ciCdId, workingDir, ciCdTool);

            this.gitService.addAllFilesToRepository(git, workingDir);
            this.gitService.commit(git, workingDir, "Configure " + ciCdTool.getCiCdToolName() + " Continuous Integration");

            this.logsService.addLog(
                    ciCdId,
                    "Pushing the application to the " + (isGitHub ? "GitHub" : "GitLab") + " " + "remote repository"
                );
            this.gitService.push(git, workingDir, user, organizationName, projectName, gitProvider);
            this.logsService.addLog(ciCdId, "Application successfully pushed!");
            this.logsService.addLog(ciCdId, "Creating " + (isGitHub ? "Pull" : "Merge") + " Request");

            String pullRequestTitle = "Configure Continuous Integration with " + ciCdTool.getCiCdToolName();
            String pullRequestBody = "Continuous Integration configured by JHipster";

            if (isGitHub) {
                int pullRequestNumber =
                    this.githubService.createPullRequest(
                            user,
                            organizationName,
                            projectName,
                            pullRequestTitle,
                            branchName,
                            pullRequestBody
                        );
                this.logsService.addLog(
                        ciCdId,
                        "Pull Request created at " +
                        applicationProperties.getGitlab().getHost() +
                        "/" +
                        organizationName +
                        "/" +
                        projectName +
                        "/pull/" +
                        pullRequestNumber
                    );
            } else if (gitProvider.equals(GitProvider.GITLAB)) {
                int pullRequestNumber =
                    this.gitlabService.createPullRequest(
                            user,
                            organizationName,
                            projectName,
                            pullRequestTitle,
                            branchName,
                            pullRequestBody
                        );
                this.logsService.addLog(
                        ciCdId,
                        "Pull Request created at " +
                        gitlabService.getHost() +
                        "/" +
                        organizationName +
                        "/" +
                        projectName +
                        "/merge_requests/" +
                        pullRequestNumber
                    );
            }

            this.gitService.cleanUpDirectory(workingDir);

            this.logsService.addLog(ciCdId, "Generation finished");
        } catch (Exception e) {
            this.logsService.addLog(ciCdId, "Error during generation: " + e.getMessage());
            log.error("Generation failed", e);
            this.logsService.addLog(ciCdId, "Generation failed");
        }
    }
}
