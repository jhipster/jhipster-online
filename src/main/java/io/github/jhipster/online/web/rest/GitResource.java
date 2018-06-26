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

package io.github.jhipster.online.web.rest;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.GitCompany;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.*;

@RestController
@RequestMapping("/api")
public class GitResource {

    private final Logger log = LoggerFactory.getLogger(GitResource.class);

    private final ApplicationProperties applicationProperties;

    private final UserService userService;

    private final GithubService githubService;

    private final GitlabService gitlabService;

    public GitResource(ApplicationProperties applicationProperties,
        UserService userService,
        GithubService githubService,
        GitlabService gitlabService) {
        this.applicationProperties = applicationProperties;
        this.userService = userService;
        this.githubService = githubService;
        this.gitlabService = gitlabService;
    }

    /**
     * The client ID used for OAuth2 authentication.
     */
    @GetMapping("/{gitProvider}/client-id")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getClientId(@PathVariable String gitProvider) {
        switch (gitProvider.toLowerCase()) {
            case "github":
                return new ResponseEntity<>(this.applicationProperties.getGithub().getClientId(), HttpStatus.OK);
            case "gitlab":
                return new ResponseEntity<>(this.applicationProperties.getGitlab().getClientId(), HttpStatus.OK);
            default:
                log.error("Unknown git provider : {}", gitProvider);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the callback code returned by the OAuth2 authentication.
     */
    @GetMapping("/callback/{gitProvider}")
    @Timed
    public RedirectView callback(@PathVariable String gitProvider, String code) {
        switch (gitProvider.toLowerCase()) {
            case "github":
                log.debug("GitHub callback received: {}", code);
                return new RedirectView("/#/callback/github/" + code);
            case "gitlab":
                log.debug("GitHub callback received: {}", code);
                return new RedirectView("/#/callback/gitlab/" + code);
            default:
                log.error("Unknown git provider : {}", gitProvider);
                return null;
        }
    }

    /**
     * Saves the callback code returned by the OAuth2 authentication.
     */
    @PostMapping("/{gitProvider}/save-token")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity saveToken(@PathVariable String gitProvider, @RequestBody String code) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url;
            GitProvider gitProviderEnum;
            GitAccessTokenRequest request = new GitAccessTokenRequest();
            switch (gitProvider.toLowerCase()) {
                case "github":
                    url = "https://github.com/login/oauth/access_token";
                    gitProviderEnum = GitProvider.GITHUB;
                    request.setClient_id(applicationProperties.getGithub().getClientId());
                    request.setClient_secret(applicationProperties.getGithub().getClientSecret());
                    request.setCode(code);
                    break;
                case "gitlab":
                    url = applicationProperties.getGitlab().getHost() + "oauth/token";
                    gitProviderEnum = GitProvider.GITLAB;
                    request.setClient_id(applicationProperties.getGitlab().getClientId());
                    request.setClient_secret(applicationProperties.getGitlab().getClientSecret());
                    request.setGrant_type("authorization_code");
                    request.setRedirect_uri(applicationProperties.getGitlab().getRedirectUri());
                    request.setCode(code);
                    break;
                default:
                    return new ResponseEntity<>("Unknown git provider: " + gitProvider, HttpStatus
                        .INTERNAL_SERVER_ERROR);
            }

            ResponseEntity<GitAccessTokenResponse> response =
                restTemplate.postForEntity(url, request, GitAccessTokenResponse.class);
            this.userService.saveToken(response.getBody().getAccess_token(), gitProviderEnum);
        } catch (Exception e) {
            log.error("OAuth2 token could not saved: {}", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static class GitAccessTokenRequest {

        private String client_id;

        private String client_secret;

        private String code;

        private String grantType;

        private String redirectUri;

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getClient_secret() {
            return client_secret;
        }

        public void setClient_secret(String client_secret) {
            this.client_secret = client_secret;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getGrant_type() {
            return grantType;
        }

        public void setGrant_type(String grantType) {
            this.grantType = grantType;
        }

        public String getRedirect_uri() {
            return redirectUri;
        }

        public void setRedirect_uri(String redirectUri) {
            this.redirectUri = redirectUri;
        }

        @Override
        public String toString() {
            return "GitAccessTokenRequest{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", code='" + code + '\'' +
                ", grantType='" + grantType + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                '}';
        }
    }

    public static class GitAccessTokenResponse {

        private String access_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }

    /**
     * Refresh Github data for the current user.
     */
    @PostMapping("/{gitProvider}/refresh")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity refreshGithub(@PathVariable String gitProvider) {
        log.info("Refreshing git provider");
        try {
            switch (gitProvider.toLowerCase()) {
                case "github":
                    this.githubService.syncUserFromGitProvider();
                    break;
                case "gitlab":
                    log.info("Refreshing Gitlab.");
                    this.gitlabService.syncUserFromGitProvider();
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown git provider: " +
                        gitProvider);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Could not refresh Github data for User `{}`: {}", SecurityUtils.getCurrentUserLogin(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Github data could not be refreshed");
        }
    }

    /**
     * Get the current user's GitHub companies.
     */
    @GetMapping("/{gitProvider}/companies")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getUserCompanies(@PathVariable String gitProvider) {
        Optional<GitProvider> maybeGitProvider = GitProvider.getGitProviderByValue(gitProvider);
        if (!maybeGitProvider.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<GitCompany> organizations = this.userService.getOrganizations(maybeGitProvider.get());
        if (organizations.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organizations, HttpStatus.OK);
        }
    }

    /**
     * Get the projects belonging to an organization.
     */
    @GetMapping("/{gitProvider}/companies/{companyName}/projects")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getOrganizationProjects(@PathVariable String gitProvider, @PathVariable String companyName) {
        Optional<GitProvider> maybeGitProvider = GitProvider.getGitProviderByValue(gitProvider);
        if (!maybeGitProvider.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.userService.getProjects(companyName, maybeGitProvider.get()), HttpStatus.OK);
    }

    @GetMapping("/git/providers")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getAvailableProviders() {
        Set<String> result = new HashSet<>();
        if (githubService.isEnabled())
            result.add(GitProvider.GITHUB.getValue());
        if (gitlabService.isEnabled())
            result.add(GitProvider.GITLAB.getValue());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/gitlab/config")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getGitlabConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("host", gitlabService.getHost());
        config.put("redirectUri", gitlabService.getRedirectUri());
        return new ResponseEntity<>(config, HttpStatus.OK);
    }
}
