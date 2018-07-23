package io.github.jhipster.online.service.enums;

import java.time.temporal.ChronoUnit;

public enum TemporalValueType {
    YEAR("year", ChronoUnit.DAYS, 365),
    MONTH("month",ChronoUnit.DAYS, 31),
    WEEK("week", ChronoUnit.DAYS, 7),
    DAY("day", ChronoUnit.DAYS, 1),
    HOUR("hour", ChronoUnit.HOURS, 1);

    private ChronoUnit unit;

    private String fieldName;

    private int dayMultiplier;

    TemporalValueType(String fieldName, ChronoUnit unit, Integer dayMultiplier) {
        this.fieldName = fieldName;
        this.unit = unit;
        this.dayMultiplier = dayMultiplier;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

    public int getDayMultiplier() {
        return dayMultiplier;
    }
}
