package io.github.jhipster.online.service.enums;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;

public enum CiCdTool {
    TRAVIS,
    JENKINS,
    GITLAB,
    AZURE;

    public static Optional<CiCdTool> getByName(String name) {
        return Stream
            .of(values())
            .filter(ciCdTool -> ciCdTool.name().equalsIgnoreCase(name))
            .findFirst();
    }



    public String command() {
        return name().toLowerCase();
    }

    public String branchName(String complement) {
        return format("{0}-{1}-{2}" ,"jhipster", name().toLowerCase(), complement);
    }

    public String capitalize() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
