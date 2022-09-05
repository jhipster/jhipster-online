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
import io.github.jhipster.online.domain.GitCompany;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.repository.GitCompanyRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.interfaces.GitProviderService;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
public class GitlabService implements GitProviderService {

    private final Logger log = LoggerFactory.getLogger(GitlabService.class);

    private final GeneratorService generatorService;

    private final ApplicationProperties applicationProperties;

    private final LogsService logsService;

    private final UserRepository userRepository;

    private final GitCompanyRepository gitCompanyRepository;

    public GitlabService(
        GeneratorService generatorService,
        ApplicationProperties applicationProperties,
        LogsService logsService,
        UserRepository userRepository,
        GitCompanyRepository gitCompanyRepository
    ) {
        this.generatorService = generatorService;
        this.applicationProperties = applicationProperties;
        this.logsService = logsService;
        this.userRepository = userRepository;
        this.gitCompanyRepository = gitCompanyRepository;
    }

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            if (this.applicationProperties.getGitlab().getHost().equals("https://gitlab.com")) {
                log.warn("JHipster Online is configured to work on the public GitLab instance at https://gitlab.com");
            } else {
                log.warn(
                    "JHipster Online is configured to work on a private GitLab instance at {}",
                    this.applicationProperties.getGitlab().getHost()
                );
            }
        }
    }

    @Override
    public String getHost() {
        return applicationProperties.getGitlab().getHost();
    }

    @Override
    public String getClientId() {
        return applicationProperties.getGitlab().getClientId();
    }

    public String getRedirectUri() {
        return applicationProperties.getGitlab().getRedirectUri();
    }

    @Override
    public boolean isEnabled() {
        return (
            this.applicationProperties.getGitlab().getClientId() != null &&
            this.applicationProperties.getGitlab().getClientSecret() != null &&
            this.applicationProperties.getGitlab().getHost() != null &&
            this.applicationProperties.getGitlab().getRedirectUri() != null
        );
    }

    @Transactional
    @Override
    public void syncUserFromGitProvider() throws IOException {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userRepository.findOneByLogin(currentUserLogin.orElse(null));
        if (user.isPresent()) {
            this.getSyncedUserFromGitProvider(user.get());
        } else {
            log.info("No user {} was found to sync with GitLab", currentUserLogin);
        }
    }

    @Transactional
    @Override
    public User getSyncedUserFromGitProvider(User user) throws IOException {
        log.info("Syncing user `{}` with GitLab...", user.getLogin());
        StopWatch watch = new StopWatch();
        watch.start();
        GitlabAPI gitlab = getConnection(user);
        GitlabUser myself = gitlab.getUser();
        user.setGitlabUser(myself.getUsername());
        user.setGitlabEmail(myself.getEmail());
        Set<GitCompany> currentGitlabCompanies = user
            .getGitCompanies()
            .stream()
            .filter(c -> c.getGitProvider().equals(GitProvider.GITLAB.getValue()))
            .collect(Collectors.toSet());

        GitCompany gitCompany;

        log.debug("Syncing user's projects");
        if (currentGitlabCompanies.stream().noneMatch(g -> g.getName().equals(myself.getUsername()))) {
            gitCompany = new GitCompany();
            gitCompany.setName(myself.getUsername());
            gitCompany.setUser(user);
            gitCompany.setGitProvider(GitProvider.GITLAB.getValue());
            gitCompany.setGitProjects(new ArrayList<>());
            gitCompanyRepository.save(gitCompany);
        } else {
            gitCompany =
                currentGitlabCompanies
                    .stream()
                    .filter(g -> g.getName().equals(myself.getUsername()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Could not find any GitCompany for user."));
        }

        try {
            List<GitlabProject> projectList = gitlab
                .getMembershipProjects()
                .stream()
                .filter(p -> p.getOwner() != null && p.getOwner().getId().equals(myself.getId()))
                .collect(Collectors.toList());

            List<String> projects = projectList.stream().map(GitlabProject::getName).collect(Collectors.toList());
            gitCompany.setGitProjects(projects);
        } catch (IOException e) {
            log.error("Could not sync GitLab repositories for user `{}`: {}", user.getLogin(), e.getMessage());
        }
        currentGitlabCompanies.add(gitCompany);

        // Sync the projects from the user's groups
        Set<GitCompany> updatedGitlabCompanies = new HashSet<>();
        gitlab
            .getGroups()
            .forEach(
                group -> {
                    log.debug("Syncing organization `{}`", group.getName());
                    GitCompany company;
                    Optional<GitCompany> currentGitlabCompany = currentGitlabCompanies
                        .stream()
                        .filter(g -> g.getName().equals(group.getName()))
                        .findFirst();

                    if (!currentGitlabCompany.isPresent()) {
                        log.debug("Saving new company `{}`", group.getName());
                        company = new GitCompany();
                        company.setName(group.getName());
                        company.setUser(user);
                        company.setGitProvider(GitProvider.GITLAB.getValue());
                        gitCompanyRepository.save(company);
                    } else {
                        company = currentGitlabCompany.get();
                    }
                    log.debug("Adding company `{}` to user", company.getName());
                    updatedGitlabCompanies.add(company);
                    try {
                        List<GitlabProject> projectList = gitlab.getGroupProjects(group);
                        List<String> projects = projectList.stream().map(GitlabProject::getName).collect(Collectors.toList());
                        company.setGitProjects(projects);
                    } catch (IOException e) {
                        log.error("Could not sync GitLab repositories for user `{}`: {}", user.getLogin(), e.getMessage());
                    }
                }
            );

        user.setGitCompanies(updatedGitlabCompanies);
        watch.stop();
        log.info("Finished syncing user `{}` with GitLab in {} ms", user.getLogin(), watch.getTotalTimeMillis());
        return user;
    }

    /**
     * Create a GitLab repository and add the JHipster Bot as collaborator.
     */
    @Async
    @Override
    public void createGitProviderRepository(
        User user,
        String applicationId,
        String applicationConfiguration,
        String group,
        String repositoryName
    ) {
        try {
            log.info("Beginning to create repository {} / {}", group, repositoryName);
            this.logsService.addLog(applicationId, "Creating GitLab repository");
            GitlabAPI gitlab = getConnection(user);
            if (user.getGitlabUser().equals(group)) {
                log.debug("Repository {} belongs to user {}", repositoryName, group);
                log.info("Creating repository {} / {}", group, repositoryName);
                gitlab.createProject(repositoryName);
            } else {
                log.debug("Repository {} belongs to organization {}", repositoryName, group);
                log.info("Creating repository {} / {}", group, repositoryName);
                gitlab.createProjectForGroup(repositoryName, gitlab.getGroup(group));
            }
            this.logsService.addLog(applicationId, "GitLab repository created!");

            this.generatorService.generateGitApplication(
                    user,
                    applicationId,
                    applicationConfiguration,
                    group,
                    repositoryName,
                    GitProvider.GITLAB
                );

            this.logsService.addLog(applicationId, "Generation finished");
        } catch (Exception e) {
            this.logsService.addLog(applicationId, "Error during generation: " + e.getMessage());
            this.logsService.addLog(applicationId, "Generation failed");
        }
    }

    @Override
    public int createPullRequest(User user, String group, String repositoryName, String title, String branchName, String body)
        throws IOException {
        log.info("Creating Merge Request on repository {} / {}", group, repositoryName);
        GitlabAPI gitlab = getConnection(user);
        GitlabProject gitlabProject = gitlab.getProject(group, repositoryName);
        int projectId = gitlabProject.getId();
        GitlabMergeRequest mergeRequest = gitlab.createMergeRequest(projectId, branchName, gitlabProject.getDefaultBranch(), null, title);
        log.info("Merge Request created!");
        return mergeRequest.getIid();
    }

    @Override
    public boolean isConfigured() {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null));

        return user.isPresent() && user.get().getGitlabOAuthToken() != null;
    }

    /**
     * Connect to GitLab as the current logged in user.
     */
    private GitlabAPI getConnection(User user) throws IOException {
        log.debug("Authenticating user `{}` on GitLab", user.getLogin());
        if (user.getGitlabOAuthToken() == null) {
            throw new ConnectException("No GitLab token configured");
        }
        GitlabAPI gitlab = GitlabAPI.connect(
            applicationProperties.getGitlab().getHost(),
            user.getGitlabOAuthToken(),
            TokenType.ACCESS_TOKEN
        );

        log.debug("User `{}` authenticated as `{}` on GitLab", user.getLogin(), gitlab.getUser().getUsername());
        return gitlab;
    }

    /**
     * Delete all the Gitlab groups.
     */
    @Transactional
    public void deleteAllOrganizationsUser(User user) {
        log.debug("Request to delete all gitlab groups for user {}", user.getLogin());
        gitCompanyRepository.deleteAllByUserLoginAndGitProvider(user.getLogin(), GitProvider.GITLAB.getValue());
    }
}
