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
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GitService {

    private final Logger log = LoggerFactory.getLogger(GitService.class);

    private final ApplicationProperties applicationProperties;

    public GitService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void pushNewApplicationToGit(User user, File workingDir, String organization, String applicationName, GitProvider gitProvider)
        throws GitAPIException, URISyntaxException, IOException {
        log.info("Create Git repository for {}", workingDir);
        Git git = Git.init().setDirectory(workingDir).call();

        addAllFilesToRepository(git, workingDir);
        commit(git, workingDir, "Initial application generation by JHipster");

        log.debug("Adding remote repository {} / {}", organization, applicationName);
        URIish urIish = null;
        if (gitProvider.equals(GitProvider.GITHUB)) {
            urIish = new URIish(applicationProperties.getGithub().getHost() + "/" + organization + "/" + applicationName + ".git");
        } else if (gitProvider.equals(GitProvider.GITLAB)) {
            urIish =
                new URIish(applicationProperties.getGitlab().getHost() + "/" + organization + "/" + applicationName + ".git")
                .setPass(user.getGitlabOAuthToken());
        }
        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(urIish);
        remoteAddCommand.call();

        String currentBranch = git.getRepository().getFullBranch();
        if (currentBranch.equals("refs/heads/master")) {
            git.branchRename().setNewName("main").call();
        }
        this.push(git, workingDir, user, organization, applicationName, gitProvider);

        log.debug("Repository successfully pushed!");
    }

    public void addAllFilesToRepository(Git git, File workingDir) throws GitAPIException {
        log.debug("Adding all files to repository {}", workingDir);
        git.add().addFilepattern(".").call();
    }

    public void commit(Git git, File workingDir, String message) throws GitAPIException {
        log.debug("Commiting all files to repository {}", workingDir);
        git.commit().setCommitter("JHipster Bot", "jhipster-bot@jhipster.tech").setMessage(message).call();
    }

    public void push(Git git, File workingDir, User user, String organization, String applicationName, GitProvider gitProvider)
        throws GitAPIException {
        log.info("Pushing {} to {} / {} for user {}", workingDir, organization, applicationName, user);
        git.push().setCredentialsProvider(getCredentialProvider(user, gitProvider)).call();
    }

    public Git cloneRepository(User user, File workingDir, String organization, String applicationName, GitProvider gitProvider)
        throws GitAPIException {
        log.debug("Cloning repository {} / {}", organization, applicationName);

        Git git = null;
        if (gitProvider.equals(GitProvider.GITLAB)) {
            git =
                Git
                    .cloneRepository()
                    .setURI(applicationProperties.getGitlab().getHost() + "/" + organization + "/" + applicationName + "" + ".git")
                    .setDirectory(workingDir)
                    .setCredentialsProvider(getCredentialProvider(user, gitProvider))
                    .setCloneAllBranches(false)
                    .call();
        } else if (gitProvider.equals(GitProvider.GITHUB)) {
            git =
                Git
                    .cloneRepository()
                    .setURI(applicationProperties.getGithub().getHost() + "/" + organization + "/" + applicationName + ".git")
                    .setDirectory(workingDir)
                    .setCredentialsProvider(getCredentialProvider(user, gitProvider))
                    .setCloneAllBranches(false)
                    .call();
        }

        log.debug("Repository successfully cloned");
        return git;
    }

    public void createBranch(Git git, String branchName) throws GitAPIException {
        git.checkout().setName(branchName).setCreateBranch(true).call();

        log.debug("Branch successfully created");
    }

    /**
     * If a generation failed, it could have left a non-empty directory.
     */
    @Scheduled(fixedDelay = 60_000L)
    public void cleanUpOldApplications() {
        File workingDir = new File(applicationProperties.getTmpFolder() + "/jhipster/applications/");
        if (workingDir.exists()) {
            File[] files = workingDir.listFiles();
            Long tenMinutesAgo = System.currentTimeMillis() - (10 * 60_000);
            for (File file : files) {
                if (file.lastModified() < tenMinutesAgo) {
                    log.info("Removing directory '{}'", file.getAbsolutePath());
                    FileUtils.deleteQuietly(file);
                }
            }
        }
    }

    public void cleanUpDirectory(File workingDir) throws IOException {
        log.debug("Cleaning up directory {}", workingDir);
        FileUtils.deleteDirectory(workingDir);
    }

    private CredentialsProvider getCredentialProvider(User user, GitProvider gitProvider) {
        if (gitProvider.equals(GitProvider.GITHUB)) {
            return new UsernamePasswordCredentialsProvider(user.getGithubUser(), user.getGithubOAuthToken());
        } else if (gitProvider.equals(GitProvider.GITLAB)) {
            return new UsernamePasswordCredentialsProvider("oauth2", user.getGitlabOAuthToken());
        }
        return null;
    }
}
