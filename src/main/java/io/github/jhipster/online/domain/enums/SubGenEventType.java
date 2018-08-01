package io.github.jhipster.online.domain.enums;

import java.util.Calendar;

public enum SubGenEventType {
    HEROKU("heroku"),
    CLOUDFOUNDRY("cloudfoundry"),
    AWS("aws"),
    CI_CD("ci-cd"),
    CLIENT(""),
    DOCKER_COMPOSE("docker-compose"),
    EXPORT_JDL("export-jdl"),
    IMPORT_JDL("import-jdl"),
    KUBERNETES("kubernetes"),
    LANGUAGES("languages"),
    OPENSHIFT("openshift"),
    RANCHER_COMPOSE("rancher-compose"),
    SERVER("server"),
    SPRING_CONTROLLER("spring-controller"),
    UPGRADE("upgrade");

    private String databaseValue;

    SubGenEventType(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public static SubGenEventType[] getDeploymentTools() {
        return new SubGenEventType[]{
            HEROKU,
            CLOUDFOUNDRY,
            AWS,
            OPENSHIFT,
            KUBERNETES
        };
    }
}
