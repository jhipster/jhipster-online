package io.github.jhipster.online.web.rest.util;

import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.service.enums.TemporalValueType;
import org.joda.time.DateTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class StatisticsResourceUtil {

    private final static String YEARLY = "yearly";
    private final static String MONTHLY = "monthly";
    private final static String WEEKLY = "weekly";
    private final static String DAILY = "daily";
    private final static String HOURLY = "hourly";

    private final static int MONTHLY_FREQUENCY = 365 * 2;

    public static Instant getFrequencyInstant(String frequency) {
        switch (frequency.toLowerCase()) {
            case YEARLY:
                return Instant.ofEpochMilli(0);
            case MONTHLY:
                return Instant.now().minus(Duration.ofDays(MONTHLY_FREQUENCY));
            case WEEKLY:
                DateTime nowMinusSixMonths = DateTime.now().minusMonths(6);
                return Instant.ofEpochMilli(nowMinusSixMonths.toInstant().getMillis());
            case DAILY:
                return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minusMonths(1).toInstant(ZoneOffset.UTC);
            case HOURLY:
                DateTime nowMinusTwentyFourHour = DateTime.now().minusHours(24);
                return Instant.ofEpochMilli(nowMinusTwentyFourHour.toInstant().getMillis());
            default:
                return null;
        }
    }

    public static TemporalValueType getTemporalValueTypeFromfrequency(String frequency) {
        switch (frequency.toLowerCase()) {
            case YEARLY:
                return TemporalValueType.YEAR;
            case MONTHLY:
                return TemporalValueType.MONTH;
            case WEEKLY:
                return TemporalValueType.WEEK;
            case DAILY:
                return TemporalValueType.DAY;
            case HOURLY:
                return TemporalValueType.HOUR;
            default:
                return null;
        }
    }

    public static YoRCColumn getColumnFromField(String field) {
        return Arrays
            .stream(YoRCColumn.values())
            .filter(c -> c.getValue().equals(field))
            .findFirst()
            .orElse(null);
    }
}
