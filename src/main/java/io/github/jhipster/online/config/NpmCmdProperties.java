package io.github.jhipster.online.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for npm command execution.
 */
@Configuration
@ConfigurationProperties(prefix = "application.npm-cmd")
public class NpmCmdProperties {

    private String cmd = "npm";
    private Integer timeout = 120;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
