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

package io.github.jhipster.online.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.config.ApplicationProperties;
import io.github.jhipster.online.domain.GitCompany;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.GithubService;
import io.github.jhipster.online.service.GitlabService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.service.dto.GitConfigurationDTO;
import io.github.jhipster.online.util.SanitizeInputs;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class GitResource {

    private final Logger log = LoggerFactory.getLogger(GitResource.class);

    private static final String GITHUB = "github";

    private static final String GITLAB = "gitlab";

    private static final String UNKNOWN_GIT_PROVIDER = "Unknown git provider: ";

    private final ApplicationProperties applicationProperties;

    private final UserService userService;

    private final GithubService githubService;

    private final GitlabService gitlabService;

    public GitResource(
        ApplicationProperties applicationProperties,
        UserService userService,
        GithubService githubService,
        GitlabService gitlabService
    ) {
        this.applicationProperties = applicationProperties;
        this.userService = userService;
        this.githubService = githubService;
        this.gitlabService = gitlabService;
    }

    /**
     * Handles the callback code returned by the OAuth2 authentication.
     */
    @GetMapping("/{gitProvider}/callback")
    public RedirectView callback(@PathVariable String gitProvider, String code) {
        gitProvider = SanitizeInputs.sanitizeInput(gitProvider);
        code = SanitizeInputs.sanitizeInput(code);
        if (!SanitizeInputs.isAlphaNumeric(code)) {
            log.error("Invalid code: {}", code);
            return null;
        }
        switch (gitProvider.toLowerCase()) {
            case GITHUB:
                log.debug("GitHub callback received: {}", code);
                return new RedirectView("/github/callback/" + code);
            case GITLAB:
                log.debug("GitHub callback received: {}", code);
                return new RedirectView("/gitlab/callback/" + code);
            default:
                log.error("Unknown git provider: {}", gitProvider);
                return null;
        }
    }

    /**
     * Saves the callback code returned by the OAuth2 authentication.
     */
    @PostMapping("/{gitProvider}/save-token")
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody ResponseEntity<String> saveToken(@PathVariable String gitProvider, @RequestBody String code)
        throws InterruptedException {
        try {
            String url;
            GitProvider gitProviderEnum;
            GitAccessTokenRequest request = new GitAccessTokenRequest();
            switch (gitProvider.toLowerCase()) {
                case GITHUB:
                    url = applicationProperties.getGithub().getHost() + "/login/oauth/access_token";
                    gitProviderEnum = GitProvider.GITHUB;
                    request.setClientId(applicationProperties.getGithub().getClientId());
                    request.setClientSecret(applicationProperties.getGithub().getClientSecret());
                    request.setCode(code);
                    break;
                case GITLAB:
                    url = applicationProperties.getGitlab().getHost() + "/oauth/token";
                    gitProviderEnum = GitProvider.GITLAB;
                    request.setClientId(applicationProperties.getGitlab().getClientId());
                    request.setClientSecret(applicationProperties.getGitlab().getClientSecret());
                    request.setGrantType("authorization_code");
                    request.setRedirectUri(applicationProperties.getGitlab().getRedirectUri());
                    request.setCode(code);
                    break;
                default:
                    return new ResponseEntity<>(UNKNOWN_GIT_PROVIDER + gitProvider, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(request);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            GitAccessTokenResponse accessTokenResponse = objectMapper.readValue(jsonResponse, GitAccessTokenResponse.class);
            this.userService.saveToken(accessTokenResponse.getAccessToken(), gitProviderEnum);
        } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("OAuth2 token could not saved: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static class GitAccessTokenRequest {

        private String clientId;

        private String clientSecret;

        private String code;

        private String grantType;

        private String redirectUri;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }

        @Override
        public String toString() {
            return (
                "GitAccessTokenRequest{" +
                "client_id='" +
                clientId +
                '\'' +
                ", client_secret='" +
                clientSecret +
                '\'' +
                ", code='" +
                code +
                '\'' +
                ", grantType='" +
                grantType +
                '\'' +
                ", redirectUri='" +
                redirectUri +
                '\'' +
                '}'
            );
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GitAccessTokenResponse {

        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    /**
     * Refresh Github data for the current user.
     */
    @PostMapping("/{gitProvider}/refresh")
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody ResponseEntity<String> refreshGitProvider(@PathVariable String gitProvider) {
        log.info("Refreshing git provider");
        try {
            switch (gitProvider.toLowerCase()) {
                case GITHUB:
                    log.info("Refreshing GitHub.");
                    this.githubService.syncUserFromGitProvider();
                    break;
                case GITLAB:
                    log.info("Refreshing GitLab.");
                    this.gitlabService.syncUserFromGitProvider();
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNKNOWN_GIT_PROVIDER + gitProvider);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            switch (gitProvider.toLowerCase()) {
                case GITHUB:
                    log.error("Could not refresh GitHub data for User `{}`: {}", SecurityUtils.getCurrentUserLogin(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GitHub data could not be " + "refreshed");
                case GITLAB:
                    log.error("Could not refresh GitLab data for User `{}`: {}", SecurityUtils.getCurrentUserLogin(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GitLab data could not be " + "refreshed");
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNKNOWN_GIT_PROVIDER + gitProvider);
            }
        }
    }

    /**
     * Get the current user's GitHub companies.
     */
    @GetMapping("/{gitProvider}/companies")
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody ResponseEntity<Collection<GitCompany>> getUserCompanies(@PathVariable String gitProvider) {
        Optional<GitProvider> maybeGitProvider = GitProvider.getGitProviderByValue(gitProvider);
        if (maybeGitProvider.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<GitCompany> organizations = this.userService.getOrganizations(maybeGitProvider.get());
        if (organizations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organizations, HttpStatus.OK);
        }
    }

    /**
     * Get the projects belonging to an organization.
     */
    @GetMapping("/{gitProvider}/companies/{companyName}/projects")
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody ResponseEntity getOrganizationProjects(@PathVariable String gitProvider, @PathVariable String companyName) {
        Optional<GitProvider> maybeGitProvider = GitProvider.getGitProviderByValue(gitProvider);
        return maybeGitProvider
            .<ResponseEntity>map(
                gitProvider1 -> new ResponseEntity<>(this.userService.getProjects(companyName, gitProvider1), HttpStatus.OK)
            )
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/git/config")
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody ResponseEntity<GitConfigurationDTO> getGitlabConfig() {
        GitConfigurationDTO result = new GitConfigurationDTO(
            githubService.getHost(),
            githubService.getClientId(),
            githubService.isEnabled(),
            gitlabService.getHost(),
            gitlabService.getRedirectUri(),
            gitlabService.getClientId(),
            gitlabService.isEnabled(),
            githubService.isConfigured(),
            gitlabService.isConfigured()
        );

        this.log.debug("Git configuration : {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
