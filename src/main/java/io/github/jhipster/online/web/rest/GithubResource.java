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

import java.util.Collection;

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
import io.github.jhipster.online.domain.GithubOrganization;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.GithubService;
import io.github.jhipster.online.service.UserService;

@RestController
@RequestMapping("/api")
public class GithubResource {

    private final Logger log = LoggerFactory.getLogger(GithubResource.class);

    private final ApplicationProperties applicationProperties;

    private final UserService userService;

    private final GithubService githubService;

    public GithubResource(ApplicationProperties applicationProperties, UserService userService, GithubService
        githubService) {
        this.applicationProperties = applicationProperties;
        this.userService = userService;
        this.githubService = githubService;
    }

    /**
     * The client ID used for OAuth2 authentication.
     */
    @GetMapping("/github/client-id")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public String getClientId() {
        return this.applicationProperties.getGithub().getClientId();
    }

    /**
     * Handles the callback code returned by the OAuth2 authentication.
     */
    @GetMapping("/github/callback")
    @Timed
    public RedirectView callback(String code) {
        log.debug("GitHub callback received: {}", code);
        return new RedirectView("/#/github/callback/" + code);
    }

    /**
     * Saves the callback code returned by the OAuth2 authentication.
     */
    @PostMapping("/github/save-token")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity saveToken(@RequestBody String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://github.com/login/oauth/access_token";
            GitHubAccessTokenRequest request = new GitHubAccessTokenRequest();
            request.setClient_id(applicationProperties.getGithub().getClientId());
            request.setClient_secret(applicationProperties.getGithub().getClientSecret());
            request.setCode(code);
            ResponseEntity<GitHubAccessTokenResponse> response =
                restTemplate.postForEntity(url, request, GitHubAccessTokenResponse.class);

            this.userService.saveToken(response.getBody().getAccess_token());
        } catch (Exception e) {
            log.error("OAuth2 token could not saved: {}", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static class GitHubAccessTokenRequest {

        private String client_id;

        private String client_secret;

        private String code;

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
    }

    public static class GitHubAccessTokenResponse {

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
    @PostMapping("/github/refresh")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity refreshGithub() {
        try {
            this.githubService.syncUserFromGithub();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Could not refresh Github data for User `{}`: {}", SecurityUtils.getCurrentUserLogin(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Github data could not be refreshed");
        }
    }

    /**
     * Get the current user's GitHub organizations.
     */
    @GetMapping("/github/organizations")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getUserOrganizations() {
        Collection<GithubOrganization> organizations = this.userService.getOrganizations();
        if (organizations.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organizations, HttpStatus.OK);
        }
    }

    /**
     * Get the projects belonging to an organization.
     */
    @GetMapping("/github/organizations/{organizationName}/projects")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity getOrganizationProjects(@PathVariable String organizationName) {
        return new ResponseEntity<>(this.userService.getProjects(organizationName), HttpStatus.OK);
    }
}
