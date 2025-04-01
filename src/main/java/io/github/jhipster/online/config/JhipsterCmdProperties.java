package io.github.jhipster.online.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JHipster command execution.
 */
@Configuration
@ConfigurationProperties(prefix = "application.jhipster-cmd")
public class JhipsterCmdProperties {

    private static String cmd = "jhipster";
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
