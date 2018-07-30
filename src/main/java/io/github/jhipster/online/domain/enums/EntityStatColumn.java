package io.github.jhipster.online.domain.enums;

import io.github.jhipster.online.domain.interfaces.DatabaseColumn;

public enum EntityStatColumn implements DatabaseColumn {

    FIELDS("fields"),
    RELATIONSHIPS("relationships"),
    PAGINATION("pagination"),
    DTO("dto"),
    SERVICE("service"),
    FLUENT_METHODS("fluentMethods"),
    DATE("date");

    private String databaseValue;

    EntityStatColumn(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    @Override
    public String getDatabaseValue() {
        return databaseValue;
    }
}
