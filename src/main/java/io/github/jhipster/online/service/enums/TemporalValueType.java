package io.github.jhipster.online.service.enums;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public static LocalDateTime absoluteMomentToLocalDateTime(Long value, TemporalValueType valueType) {
        switch (valueType) {
            case YEAR:
                return LocalDateTime.of(value.intValue(), 1, 1, 1, 1);
            case MONTH:
                return LocalDateTime.of(1970 + (value.intValue() / 12), (value.intValue() % 12) + 1, 1, 1, 1);
            case WEEK:
            case DAY:
                return LocalDateTime
                    .ofEpochSecond(0, 0, ZoneOffset.UTC)
                    .plus(Duration.of((value * valueType.getDayMultiplier()), ChronoUnit.DAYS));
            case HOUR:
                return LocalDateTime
                    .ofEpochSecond(0, 0, ZoneOffset.UTC)
                    .plus(Duration.of(value * valueType.getDayMultiplier(), valueType.getUnit()));
            default:
                return null;
        }
    }
}
