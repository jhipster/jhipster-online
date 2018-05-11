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

import java.io.IOException;
import java.util.*;

import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import io.github.jhipster.online.domain.GithubOrganization;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.GithubOrganizationRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.security.SecurityUtils;

@Service
public class GithubService {

    private final Logger log = LoggerFactory.getLogger(GithubService.class);

    private final GeneratorService generatorService;

    private final LogsService logsService;

    private final GithubOrganizationRepository githubOrganizationRepository;

    private final UserRepository userRepository;

    public GithubService(GeneratorService generatorService,
        LogsService logsService, GithubOrganizationRepository githubOrganizationRepository,
        UserRepository userRepository) {
        this.generatorService = generatorService;
        this.logsService = logsService;
        this.githubOrganizationRepository = githubOrganizationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Sync User data from GitHub.
     */
    @Transactional
    public void syncUserFromGithub() throws Exception {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        if (user.isPresent()) {
            this.getSyncedUserFromGitHub(user.get());
        } else {
            log.info("No user `{} was found to sync with GitHub");
        }
    }

    /**
     * Sync User data from GitHub.
     */
    @Transactional
    public User getSyncedUserFromGitHub(User user) throws Exception {
        log.info("Syncing user `{}` with GitHub", user.getLogin());
        GitHub gitHub = this.getConnection(user);
        GHMyself ghMyself = gitHub.getMyself();
        user.setGithubUser(ghMyself.getLogin());
        user.setGithubEmail(ghMyself.getEmail());
        user.setGithubCompany(ghMyself.getCompany());
        user.setGithubLocation(ghMyself.getLocation());
        Set<GithubOrganization> organizations = user.getGithubOrganizations();
        organizations.forEach(organization -> {
            githubOrganizationRepository.delete(organization);
        });
        organizations.clear();
        // Sync the current user's projects
        GithubOrganization myOrganization = new GithubOrganization();
        myOrganization.setName(ghMyself.getLogin());
        myOrganization.setUser(user);
        githubOrganizationRepository.save(myOrganization);
        try {
            List<String> projects = new ArrayList<>();
            Map<String, GHRepository> projectMap = gitHub.getMyself().getAllRepositories();
            projects.addAll(projectMap.keySet());
            myOrganization.setGithubProjects(projects);
        } catch (IOException e) {
            log.error("Could not sync GitHub repositories for user `{}`: {}", user.getLogin(), e.getMessage());
        }
        organizations.add(myOrganization);
        // Sync the projects from the user's organizations
        gitHub.getMyOrganizations().keySet().forEach(organizationName -> {
            GithubOrganization organization = new GithubOrganization();
            organization.setName(organizationName);
            organization.setUser(user);
            githubOrganizationRepository.save(organization);
            organizations.add(organization);
            try {
                List<String> projects = new ArrayList<>();
                Map<String, GHRepository> projectMap = gitHub.getOrganization(organizationName).getRepositories();
                projects.addAll(projectMap.keySet());
                organization.setGithubProjects(projects);
            } catch (IOException e) {
                log.error("Could not sync GitHub repositories for user `{}`: {}", user.getLogin(), e.getMessage());
            }
        });
        user.setGithubOrganizations(organizations);
        return user;
    }

    /**
     * Create a GitHub repository and add the JHipster Bot as collaborator.
     */
    @Async
    public void createGitHubRepository(User user, String applicationId, String applicationConfiguration, String
        organization, String applicationName) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            log.info("Beginning to create repository {} / {}", organization, applicationName);
            this.logsService.addLog(applicationId, "Creating GitHub repository");
            GitHub gitHub = this.getConnection(user);
            GHCreateRepositoryBuilder builder = null;
            if (user.getGithubUser().equals(organization)) {
                log.debug("Repository {} belongs to user {}", applicationName, organization);
                builder = gitHub.createRepository(applicationName);
            } else {
                log.debug("Repository {} belongs to organization {}", applicationName, organization);
                builder = gitHub.getOrganization(organization).createRepository(applicationName);
            }
            log.info("Creating repository {} / {}", organization, applicationName);
            builder.create();
            this.logsService.addLog(applicationId, "GitHub repository created!");

            this.generatorService.generateGitApplication(user, applicationId, applicationConfiguration, organization,
                applicationName);

            this.logsService.addLog(applicationId, "Generation finished");
        } catch (Exception e) {
            this.logsService.addLog(applicationId, "Error during generation: " + e.getMessage());
            this.logsService.addLog(applicationId, "Generation failed");
        }
        watch.stop();
    }

    public int createPullRequest(User user, String organization, String applicationName,
        String title, String branchName, String body) throws Exception {

        log.info("Creating Pull Request on repository {} / {}", organization, applicationName);

        GitHub gitHub = this.getConnection(user);
        int number = gitHub
            .getRepository(organization + "/" + applicationName)
            .createPullRequest(title, branchName, "master", body)
            .getNumber();

        log.info("Pull Request created!");
        return number;
    }

    /**
     * Connect to GitHub as the current logged in user.
     */
    private GitHub getConnection(User user) throws Exception {
        log.debug("Authenticating as User `{}`", user.getGithubUser());
        if (user.getGithubOAuthToken() == null) {
            log.info("No GitHub token configured");
            throw new Exception("GitHub is not configured.");
        }
        return GitHub.connectUsingOAuth(user.getGithubOAuthToken());
    }
}
