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
package io.github.jhipster.online.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhonline.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Github github = new Github();

    private final Gitlab gitlab = new Gitlab();

    private final Mail mail = new Mail();

    private String tmpFolder = "/tmp";

    public String getTmpFolder() {
        return tmpFolder;
    }

    public void setTmpFolder(String tmpFolder) {
        this.tmpFolder = tmpFolder;
    }

    public Github getGithub() {
        return github;
    }

    public Gitlab getGitlab() {
        return gitlab;
    }

    public Mail getMail() {
        return mail;
    }

    public static class Github {
        private String clientId;
        private String clientSecret;
        private String jhipsterBotOauthToken = "";

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

        public String getJhipsterBotOauthToken() {
            return jhipsterBotOauthToken;
        }

        public void setJhipsterBotOauthToken(String jhipsterBotOauthToken) {
            this.jhipsterBotOauthToken = jhipsterBotOauthToken;
        }
    }

    public static class Gitlab {
        private String clientId;
        private String clientSecret;
        private String host;
        private String redirectUri;
        private String jhipsterBotOauthToken = "";

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

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }

        public String getJhipsterBotOauthToken() {
            return jhipsterBotOauthToken;
        }

        public void setJhipsterBotOauthToken(String jhipsterBotOauthToken) {
            this.jhipsterBotOauthToken = jhipsterBotOauthToken;
        }
    }

    public static class Mail {

        public boolean enable;

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean isEnable() {
            return enable;
        }
    }

}
