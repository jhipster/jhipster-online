/**
 * Copyright 2017-2021 the original author or authors from the JHipster project.
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
import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GithubService implements GitProviderService {

    private final Logger log = LoggerFactory.getLogger(GithubService.class);

    private final GeneratorService generatorService;

    private final ApplicationProperties applicationProperties;

    private final LogsService logsService;

    private final GitCompanyRepository gitCompanyRepository;

    private final UserRepository userRepository;

    public GithubService(
        GeneratorService generatorService,
        LogsService logsService,
        ApplicationProperties applicationProperties,
        GitCompanyRepository gitCompanyRepository,
        UserRepository userRepository
    ) {
        this.generatorService = generatorService;
        this.applicationProperties = applicationProperties;
        this.logsService = logsService;
        this.gitCompanyRepository = gitCompanyRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            if (this.applicationProperties.getGithub().getHost().equals("https://github.com")) {
                log.warn("JHipster Online is configured to work on the public GitHub instance at https://github.com");
            } else {
                log.warn(
                    "JHipster Online is configured to work on a private GitHub instance at {}",
                    this.applicationProperties.getGithub().getHost()
                );
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return (
            this.applicationProperties.getGithub().getClientId() != null && this.applicationProperties.getGithub().getClientSecret() != null
        );
    }

    @Override
    public String getHost() {
        return applicationProperties.getGithub().getHost();
    }

    @Override
    public String getClientId() {
        return applicationProperties.getGithub().getClientId();
    }

    /**
     * Sync User data from GitHub.
     */
    @Transactional
    @Override
    public void syncUserFromGitProvider() {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null));
        if (user.isPresent()) {
            this.getSyncedUserFromGitProvider(user.get());
        } else {
            log.info("No user was found to sync with GitHub");
        }
    }

    /**
     * Sync User data from GitHub.
     */
    @Transactional
    @Override
    public User getSyncedUserFromGitProvider(User user) {
        log.info("Syncing user `{}` with GitHub...", user.getLogin());
        try {
            GitHub gitHub = this.getConnection(user);
            GHMyself ghMyself = gitHub.getMyself();
            String githubLogin = ghMyself.getLogin();
            user.setGithubUser(ghMyself.getLogin());
            user.setGithubEmail(ghMyself.getEmail());
            user.setGithubCompany(ghMyself.getCompany());
            user.setGithubLocation(ghMyself.getLocation());
            Set<GitCompany> currentGithubCompanies = user
                .getGitCompanies()
                .stream()
                .filter(c -> c.getGitProvider().equals(GitProvider.GITHUB.getValue()))
                .collect(Collectors.toSet());

            Set<GitCompany> updatedGithubCompanies = new HashSet<>();

            syncUserProjectsFromCompanies(user, gitHub, currentGithubCompanies, updatedGithubCompanies);

            user.setGitCompanies(updatedGithubCompanies);
            List<String> organizationsProjects = updatedGithubCompanies
                .stream()
                .filter(o -> o.getGitProvider().equals("github") && !o.getName().equals(githubLogin))
                .flatMap(o -> o.getGitProjects().stream())
                .collect(Collectors.toList());

            syncUserProjects(user, gitHub, ghMyself, githubLogin, currentGithubCompanies, organizationsProjects);
        } catch (Exception e) {
            log.error("Could not sync user from Git provider. User: `{}`", user.getLogin(), e);
        }
        return user;
    }

    /**
     * Create a GitHub repository and add the JHipster Bot as collaborator.
     */
    @Async
    @Override
    public void createGitProviderRepository(
        User user,
        String applicationId,
        String applicationConfiguration,
        String organization,
        String repositoryName
    ) {
        try {
            log.info("Beginning to create repository {} / {}", organization, repositoryName);
            this.logsService.addLog(applicationId, "Creating GitHub repository");
            GitHub gitHub = this.getConnection(user);
            GHCreateRepositoryBuilder builder;
            if (user.getGithubUser().equals(organization)) {
                log.debug("Repository {} belongs to user {}", repositoryName, organization);
                builder = gitHub.createRepository(repositoryName);
            } else {
                log.debug("Repository {} belongs to organization {}", repositoryName, organization);
                builder = gitHub.getOrganization(organization).createRepository(repositoryName);
            }
            log.info("Creating repository {} / {}", organization, repositoryName);
            builder.create();
            this.logsService.addLog(applicationId, "GitHub repository created!");

            this.generatorService.generateGitApplication(
                    user,
                    applicationId,
                    applicationConfiguration,
                    organization,
                    repositoryName,
                    GitProvider.GITHUB
                );

            this.logsService.addLog(applicationId, "Generation finished");
        } catch (Exception e) {
            this.logsService.addLog(applicationId, "Error during generation: " + e.getMessage());
            this.logsService.addLog(applicationId, "Generation failed");
        }
    }

    public int createPullRequest(User user, String organization, String repositoryName, String title, String branchName, String body)
        throws IOException {
        log.info("Creating Pull Request on repository {} / {}", organization, repositoryName);

        GitHub gitHub = this.getConnection(user);
        GHRepository repository = gitHub.getRepository(organization + "/" + repositoryName);
        int number = repository.createPullRequest(title, branchName, repository.getDefaultBranch(), body).getNumber();

        log.info("Pull Request created!");
        return number;
    }

    @Override
    public boolean isConfigured() {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null));

        return user.isPresent() && user.get().getGithubOAuthToken() != null;
    }

    /**
     * Connect to GitHub as the current logged in user.
     */
    private GitHub getConnection(User user) throws IOException {
        log.debug("Authenticating as User `{}`", user.getGithubUser());
        if (user.getGithubOAuthToken() == null) {
            throw new ConnectException("No GitHub token configured.");
        }
        return GitHub.connectUsingOAuth(user.getGithubOAuthToken());
    }

    /**
     * Delete all the github organizations.
     *
     */
    @Transactional
    public void deleteAllOrganizationsUser(User user) {
        log.debug("Request to delete all github organizations for user {}", user.getLogin());
        gitCompanyRepository.deleteAllByUserLoginAndGitProvider(user.getLogin(), GitProvider.GITHUB.getValue());
    }

    /**
     *  Sync the projects from the user's companies
     */
    private void syncUserProjectsFromCompanies(
        User user,
        GitHub gitHub,
        Set<GitCompany> currentGithubCompanies,
        Set<GitCompany> updatedGithubCompanies
    )
        throws IOException {
        Map<String, GHOrganization> githubOrganizations = gitHub.getMyOrganizations();
        for (Map.Entry<String, GHOrganization> entry : githubOrganizations.entrySet()) {
            String githubOrganizationName = entry.getKey();
            log.debug("Syncing company `{}`", githubOrganizationName);
            GitCompany company;
            // Create company if it does not already exist
            Optional<GitCompany> currentGitHubOrganization = currentGithubCompanies
                .stream()
                .filter(g -> g.getName().equals(githubOrganizationName))
                .findFirst();

            if (currentGitHubOrganization.isEmpty()) {
                log.debug("Saving new company `{}`", githubOrganizationName);
                company = new GitCompany();
                company.setName(githubOrganizationName);
                company.setUser(user);
                company.setGitProvider(GitProvider.GITHUB.getValue());
                company = gitCompanyRepository.save(company);
            } else {
                company = currentGitHubOrganization.get();
            }
            log.debug("Adding company `{}` to user", company.getName());
            updatedGithubCompanies.add(company);
            company.setGitProjects(new ArrayList<>(githubOrganizations.get(githubOrganizationName).getRepositories().keySet()));
        }
    }

    /**
     * Sync the user's projects
     */
    private void syncUserProjects(
        User user,
        GitHub gitHub,
        GHMyself ghMyself,
        String githubLogin,
        Set<GitCompany> currentGithubCompanies,
        List<String> organizationsProjects
    )
        throws Exception {
        log.debug("Syncing user's projects");
        GitCompany myOrganization;
        if (currentGithubCompanies.stream().noneMatch(g -> g.getName().equals(ghMyself.getLogin()))) {
            myOrganization = new GitCompany();
            myOrganization.setName(githubLogin);
            myOrganization.setUser(user);
            myOrganization.setGitProvider(GitProvider.GITHUB.getValue());
            gitCompanyRepository.save(myOrganization);
        } else {
            myOrganization =
                currentGithubCompanies
                    .stream()
                    .filter(g -> g.getName().equals(ghMyself.getLogin()))
                    .findFirst()
                    .orElseThrow(Exception::new);
        }
        try {
            List<String> ownedProjects = gitHub
                .getMyself()
                .getAllRepositories()
                .keySet()
                .stream()
                .filter(ghRepository -> organizationsProjects.stream().noneMatch(p -> p.equals(ghRepository)))
                .collect(Collectors.toList());
            myOrganization.setGitProjects(ownedProjects);
        } catch (IOException e) {
            log.error("Could not sync GitHub repositories for user `{}`: {}", user.getLogin(), e.getMessage());
        }
    }
}
