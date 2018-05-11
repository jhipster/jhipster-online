package io.github.jhipster.online.config;

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
}
