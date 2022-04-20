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

package io.github.jhipster.online.service.dto;

import java.util.Objects;

public class GitConfigurationDTO {

    private String githubHost;

    private String githubClientId;

    private boolean githubAvailable;

    private String gitlabHost;

    private String gitlabRedirectUri;

    private String gitlabClientId;

    private boolean gitlabAvailable;

    private boolean githubConfigured;

    private boolean gitlabConfigured;

    public GitConfigurationDTO(
        String githubHost,
        String githubClientId,
        boolean githubAvailable,
        String gitlabHost,
        String gitlabRedirectUri,
        String gitlabClientId,
        boolean gitlabAvailable,
        boolean githubConfigured,
        boolean gitlabConfigured
    ) {
        this.githubHost = githubHost;
        this.githubClientId = githubClientId;
        this.githubAvailable = githubAvailable;
        this.gitlabHost = gitlabHost;
        this.gitlabRedirectUri = gitlabRedirectUri;
        this.gitlabClientId = gitlabClientId;
        this.gitlabAvailable = gitlabAvailable;
        this.githubConfigured = githubConfigured;
        this.gitlabConfigured = gitlabConfigured;
    }

    public String getGithubHost() {
        return githubHost;
    }

    public void setGithubHost(String githubHost) {
        this.githubHost = githubHost;
    }

    public String getGithubClientId() {
        return githubClientId;
    }

    public void setGithubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
    }

    public String getGitlabHost() {
        return gitlabHost;
    }

    public void setGitlabHost(String gitlabHost) {
        this.gitlabHost = gitlabHost;
    }

    public String getGitlabRedirectUri() {
        return gitlabRedirectUri;
    }

    public void setGitlabRedirectUri(String gitlabRedirectUri) {
        this.gitlabRedirectUri = gitlabRedirectUri;
    }

    public String getGitlabClientId() {
        return gitlabClientId;
    }

    public void setGitlabClientId(String gitlabClientId) {
        this.gitlabClientId = gitlabClientId;
    }

    public boolean isGitlabConfigured() {
        return gitlabConfigured;
    }

    public void setGitlabConfigured(boolean gitlabConfigured) {
        this.gitlabConfigured = gitlabConfigured;
    }

    public boolean isGithubAvailable() {
        return githubAvailable;
    }

    public void setGithubAvailable(boolean githubAvailable) {
        this.githubAvailable = githubAvailable;
    }

    public boolean isGitlabAvailable() {
        return gitlabAvailable;
    }

    public void setGitlabAvailable(boolean gitlabAvailable) {
        this.gitlabAvailable = gitlabAvailable;
    }

    public boolean isGithubConfigured() {
        return githubConfigured;
    }

    public void setGithubConfigured(boolean githubConfigured) {
        this.githubConfigured = githubConfigured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitConfigurationDTO that = (GitConfigurationDTO) o;

        if (githubAvailable != that.githubAvailable) return false;
        if (gitlabAvailable != that.gitlabAvailable) return false;
        if (githubConfigured != that.githubConfigured) return false;
        if (gitlabConfigured != that.gitlabConfigured) return false;
        if (!Objects.equals(githubHost, that.githubHost)) return false;
        if (!Objects.equals(githubClientId, that.githubClientId)) return false;
        if (!Objects.equals(gitlabHost, that.gitlabHost)) return false;
        if (!Objects.equals(gitlabRedirectUri, that.gitlabRedirectUri)) return false;
        return Objects.equals(gitlabClientId, that.gitlabClientId);
    }

    @Override
    public int hashCode() {
        int result = githubHost != null ? githubHost.hashCode() : 0;
        result = 31 * result + (githubClientId != null ? githubClientId.hashCode() : 0);
        result = 31 * result + (githubAvailable ? 1 : 0);
        result = 31 * result + (gitlabHost != null ? gitlabHost.hashCode() : 0);
        result = 31 * result + (gitlabRedirectUri != null ? gitlabRedirectUri.hashCode() : 0);
        result = 31 * result + (gitlabClientId != null ? gitlabClientId.hashCode() : 0);
        result = 31 * result + (gitlabAvailable ? 1 : 0);
        result = 31 * result + (githubConfigured ? 1 : 0);
        result = 31 * result + (gitlabConfigured ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return (
            "GitConfigurationDTO{" +
            "githubHost='" +
            githubHost +
            '\'' +
            ", githubClientId='" +
            githubClientId +
            '\'' +
            ", githubAvailable=" +
            githubAvailable +
            ", gitlabHost='" +
            gitlabHost +
            '\'' +
            ", gitlabRedirectUri='" +
            gitlabRedirectUri +
            '\'' +
            ", gitlabClientId='" +
            gitlabClientId +
            '\'' +
            ", gitlabAvailable=" +
            gitlabAvailable +
            ", githubConfigured=" +
            githubConfigured +
            ", gitlabConfigured=" +
            gitlabConfigured +
            '}'
        );
    }
}
