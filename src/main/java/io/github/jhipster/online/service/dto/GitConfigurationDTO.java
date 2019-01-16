/**
 * Copyright 2017-2019 the original author or authors from the JHipster Online project.
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

public class GitConfigurationDTO {

    private String githubHost;

    private String githubClientId;

    private boolean githubAvailable;

    private String gitlabHost;

    private String gitlabRedirectUri;

    private String gitlabClientId;

    private boolean gitlabAvailable;

    private String bitbucketHost;

    private String bitbucketRedirectUri;

    private String bitbucketClientId;

    private boolean bitbucketAvailable;

    private boolean githubConfigured;

    private boolean gitlabConfigured;

    private boolean bitbucketConfigured;

    public GitConfigurationDTO(String githubHost,
                               String githubClientId,
                               boolean githubAvailable,
                               String gitlabHost,
                               String gitlabRedirectUri,
                               String gitlabClientId,
                               boolean gitlabAvailable,
                               String bitbucketHost,
                               String bitbucketRedirectUri,
                               String bitbucketClientId,
                               boolean bitbucketAvailable,
                               boolean githubConfigured,
                               boolean gitlabConfigured,
                               boolean bitbucketConfigured) {
        this.githubHost = githubHost;
        this.githubClientId = githubClientId;
        this.githubAvailable = githubAvailable;
        this.gitlabHost = gitlabHost;
        this.gitlabRedirectUri = gitlabRedirectUri;
        this.gitlabClientId = gitlabClientId;
        this.gitlabAvailable = gitlabAvailable;
        this.bitbucketHost = bitbucketHost;
        this.bitbucketRedirectUri = bitbucketRedirectUri;
        this.bitbucketClientId = bitbucketClientId;
        this.bitbucketAvailable = bitbucketAvailable;
        this.githubConfigured = githubConfigured;
        this.gitlabConfigured = gitlabConfigured;
        this.bitbucketConfigured = bitbucketConfigured;
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

    public String getBitbucketHost() {
        return bitbucketHost;
    }

    public void setBitbucketHost(String bitbucketHost) {
        this.bitbucketHost = bitbucketHost;
    }

    public String getBitbucketRedirectUri() {
        return bitbucketRedirectUri;
    }

    public void setBitbucketRedirectUri(String bitbucketRedirectUri) {
        this.bitbucketRedirectUri = bitbucketRedirectUri;
    }

    public String getBitbucketClientId() {
        return bitbucketClientId;
    }

    public void setBitbucketClientId(String bitbucketClientId) {
        this.bitbucketClientId = bitbucketClientId;
    }

    public boolean isBitbucketAvailable() {
        return bitbucketAvailable;
    }

    public void setBitbucketAvailable(boolean bitbucketAvailable) {
        this.bitbucketAvailable = bitbucketAvailable;
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

    public boolean isBitbucketConfigured() {
        return bitbucketConfigured;
    }

    public void setBitbucketConfigured(boolean bitbucketConfigured) {
        this.bitbucketConfigured = bitbucketConfigured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitConfigurationDTO that = (GitConfigurationDTO) o;

        if (githubAvailable != that.githubAvailable) return false;
        if (gitlabAvailable != that.gitlabAvailable) return false;
        if (bitbucketAvailable != that.bitbucketAvailable) return false;
        if (githubConfigured != that.githubConfigured) return false;
        if (gitlabConfigured != that.gitlabConfigured) return false;
        if (bitbucketConfigured != that.bitbucketConfigured) return false;
        if (githubHost != null ? !githubHost.equals(that.githubHost) : that.githubHost != null) return false;
        if (githubClientId != null ? !githubClientId.equals(that.githubClientId) : that.githubClientId != null)
            return false;
        if (gitlabHost != null ? !gitlabHost.equals(that.gitlabHost) : that.gitlabHost != null) return false;
        if (gitlabRedirectUri != null ? !gitlabRedirectUri.equals(that.gitlabRedirectUri) : that.gitlabRedirectUri != null)
            return false;
        if (gitlabClientId != null ? !gitlabClientId.equals(that.gitlabClientId) : that.gitlabClientId != null)
            return false;
        if (bitbucketHost != null ? !bitbucketHost.equals(that.bitbucketHost) : that.bitbucketHost != null)
            return false;
        if (bitbucketRedirectUri != null ? !bitbucketRedirectUri.equals(that.bitbucketRedirectUri) : that.bitbucketRedirectUri != null)
            return false;
        return bitbucketClientId != null ? bitbucketClientId.equals(that.bitbucketClientId) : that.bitbucketClientId == null;
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
        result = 31 * result + (bitbucketHost != null ? bitbucketHost.hashCode() : 0);
        result = 31 * result + (bitbucketRedirectUri != null ? bitbucketRedirectUri.hashCode() : 0);
        result = 31 * result + (bitbucketClientId != null ? bitbucketClientId.hashCode() : 0);
        result = 31 * result + (bitbucketAvailable ? 1 : 0);
        result = 31 * result + (githubConfigured ? 1 : 0);
        result = 31 * result + (gitlabConfigured ? 1 : 0);
        result = 31 * result + (bitbucketConfigured ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitConfigurationDTO{" +
            "githubHost='" + githubHost + '\'' +
            ", githubClientId='" + githubClientId + '\'' +
            ", githubAvailable=" + githubAvailable +
            ", gitlabHost='" + gitlabHost + '\'' +
            ", gitlabRedirectUri='" + gitlabRedirectUri + '\'' +
            ", gitlabClientId='" + gitlabClientId + '\'' +
            ", gitlabAvailable=" + gitlabAvailable +
            ", bitbucketHost='" + bitbucketHost + '\'' +
            ", bitbucketRedirectUri='" + bitbucketRedirectUri + '\'' +
            ", bitbucketClientId='" + bitbucketClientId + '\'' +
            ", bitbucketAvailable=" + bitbucketAvailable +
            ", githubConfigured=" + githubConfigured +
            ", gitlabConfigured=" + gitlabConfigured +
            ", bitbucketConfigured=" + bitbucketConfigured +
            '}';
    }
}
