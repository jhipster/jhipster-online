/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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
import io.github.jhipster.online.domain.GitCompany;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.repository.GitCompanyRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.interfaces.GitProviderService;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitlabService implements GitProviderService {

    private final Logger log = LoggerFactory.getLogger(GitlabService.class);

    private final GeneratorService generatorService;

    private final ApplicationProperties applicationProperties;

    private final LogsService logsService;

    private final UserRepository userRepository;

    private final GitCompanyRepository gitCompanyRepository;

    public GitlabService(GeneratorService generatorService,
                         ApplicationProperties applicationProperties,
                         LogsService logsService, UserRepository userRepository,
                         GitCompanyRepository gitCompanyRepository) {
        this.generatorService = generatorService;
        this.applicationProperties = applicationProperties;
        this.logsService = logsService;
        this.userRepository = userRepository;
        this.gitCompanyRepository = gitCompanyRepository;
    }

    @Transactional
    @Override
    public void syncUserFromGitProvider() throws Exception {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        if (user.isPresent()) {
            this.getSyncedUserFromGitProvider(user.get());
        } else {
            log.info("No user `{} was found to sync with GitHub");
        }
    }

    @Transactional
    @Override
    public User getSyncedUserFromGitProvider(User user) throws Exception {
        GitlabAPI gitlab = getConnection(user);
        GitlabUser myself = gitlab.getUser();
        user.setGitlabUser(myself.getUsername());
        user.setGitlabEmail(myself.getEmail());
        // user.setGitCompany(myself.getUsername());
        // user.setGitLocation("");
        Set<GitCompany> groups = user.getGitCompanies();

        // Sync the current user's projects
        if (groups.stream().noneMatch(g -> g.getName().equals(myself.getUsername()))) {
            GitCompany myGroup = new GitCompany();
            myGroup.setName(myself.getUsername());
            myGroup.setUser(user);
            myGroup.setGitProvider(GitProvider.GITLAB.getValue());
            myGroup.setGitProjects(new  ArrayList<>());
            gitCompanyRepository.save(myGroup);
            try {
                List<GitlabProject> projectList = gitlab.getProjects();
                List<String> projects = projectList.stream().map(GitlabProject::getName).collect(Collectors.toList());
                myGroup.setGitProjects(projects);
            } catch (IOException e) {
                log.error("Could not sync GitHub repositories for user `{}`: {}", user.getLogin(), e.getMessage());
            }
            groups.add(myGroup);
        }

        // Sync the projects from the user's groups
        gitlab.getGroups().forEach(group -> {
            GitCompany company = new GitCompany();
            company.setName(group.getName());
            company.setUser(user);
            company.setGitProvider(GitProvider.GITLAB.getValue());
            if (groups.stream().noneMatch(g -> g.getName().equals(company.getName()))) {
                gitCompanyRepository.save(company);
                groups.add(company);
            }

            try {
                List<GitlabProject> projectList = gitlab.getGroup(group.getName()).getSharedProjects();
                List<String> projects = projectList.stream().map(GitlabProject::getName).collect(Collectors.toList());
                company.setGitProjects(projects);
            } catch (IOException e) {
                log.error("Could not sync GitHub repositories for user `{}`: {}", user.getLogin(), e.getMessage());
            }
        });

        user.setGitCompanies(groups);
        return user;
    }

    /**
     * Create a GitLab repository and add the JHipster Bot as collaborator.
     */
    @Async
    @Override
    public void createGitProviderRepository(User user, String applicationId, String applicationConfiguration, String group, String applicationName) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            log.info("Beginning to create repository {} / {}", group, applicationName);
            this.logsService.addLog(applicationId, "Creating GitLab repository");
            GitlabAPI gitlab = getConnection(user);
            if (user.getGitlabUser().equals(group)) {
                log.debug("Repository {} belongs to user {}", applicationName, group);
                log.info("Creating repository {} / {}", group, applicationName);
                gitlab.createProject(applicationName);
                this.logsService.addLog(applicationId, "GitLab repository created!");
            } else {
                log.debug("Repository {} belongs to organization {}", applicationName, group);
                log.info("Creating repository {} / {}", group, applicationName);
                gitlab.createProjectForGroup(applicationName, gitlab.getGroup(group));
                this.logsService.addLog(applicationId, "GitLab repository created!");
            }

            this.generatorService.generateGitApplication(user, applicationId, applicationConfiguration, group,
                applicationName, GitProvider.GITLAB);

            this.logsService.addLog(applicationId, "Generation finished");
        } catch (Exception e) {
            this.logsService.addLog(applicationId, "Error during generation: " + e.getMessage());
            this.logsService.addLog(applicationId, "Generation failed");
        }
        watch.stop();
    }

    @Override
    public int createPullRequest(User user, String group, String applicationName,
                                 String title, String branchName, String body) throws Exception {
        log.info("Creating Merge Request on repository {} / {}", group, applicationName);
        GitlabAPI gitlab = getConnection(user);
        int number = gitlab.getProject(group, applicationName).getId();
        gitlab.createMergeRequest(number, branchName, "master", null, title);
        log.info("Merge Request created!");
        return number;
    }

    @Override
    public boolean isAvailable() {
        return applicationProperties.getGitlab().getClientId() != null &&
            applicationProperties.getGitlab().getClientSecret() != null &&
            applicationProperties.getGitlab().getHost() != null &&
            applicationProperties.getGitlab().getRedirectUri() != null;
    }

    public String getHost() {
        return applicationProperties.getGitlab().getHost();
    }

    public String getRedirectUri() {
        return applicationProperties.getGitlab().getRedirectUri();
    }

    /**
     * Connect to GitLab as the current logged in user.
     */
    private GitlabAPI getConnection(User user) throws Exception {
        log.debug("Authenticating as User `{}`", user.getGitlabUser());
        if (user.getGitlabOAuthToken() == null) {
            log.info("No GitLab token configured");
            throw new Exception("GitLab is not configured.");
        }
        return GitlabAPI.connect(applicationProperties.getGitlab().getHost(), user.getGitlabOAuthToken(), TokenType.ACCESS_TOKEN);
    }

    /**
     *  Delete all the gitlab groups.
     *
     */
    @Transactional
    public void deleteAllOrganizationsForCurrentUser(String userLogin) {
        log.debug("Request to delete all gitlab groups for current user");
        gitCompanyRepository.deleteAllByUserLoginAndGitProvider(userLogin, GitProvider.GITLAB.getValue());
    }
}
