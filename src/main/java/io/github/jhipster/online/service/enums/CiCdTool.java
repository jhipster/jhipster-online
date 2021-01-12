package io.github.jhipster.online.service.enums;

import static java.text.MessageFormat.format;

import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public enum CiCdTool {
    TRAVIS,
    JENKINS,
    GITLAB,
    AZURE,
    GITHUB;

    public static Optional<CiCdTool> getByName(String name) {
        return Stream.of(values()).filter(ciCdTool -> ciCdTool.name().equalsIgnoreCase(name)).findFirst();
    }

    public String command() {
        return name().toLowerCase();
    }

    public String branchName(String complement) {
        return format("{0}-{1}-{2}", "jhipster", name().toLowerCase(), complement);
    }

    public String getCiCdToolName() {
        if (name().equals("GITLAB")) {
            return "GitLab";
        } else if (name().equals("GITHUB")) {
            return "GitHub";
        } else {
            return StringUtils.capitalize(name().toLowerCase());
        }
    }
}
