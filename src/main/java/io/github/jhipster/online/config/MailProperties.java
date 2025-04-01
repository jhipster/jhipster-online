package io.github.jhipster.online.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Mail.
 */
@Configuration
@ConfigurationProperties(prefix = "application.mail")
public class MailProperties {

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
