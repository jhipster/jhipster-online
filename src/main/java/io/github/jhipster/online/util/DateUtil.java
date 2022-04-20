/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.online.util;

import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.domain.interfaces.CompleteDate;
import io.github.jhipster.online.service.enums.TemporalValueType;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class DateUtil {

    private static final String YEARLY = "yearly";
    private static final String MONTHLY = "monthly";
    private static final String WEEKLY = "weekly";
    private static final String DAILY = "daily";
    private static final String HOURLY = "hourly";

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void setAbsoluteDate(CompleteDate datableObject, Instant now) {
        ZonedDateTime epoch = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("Europe/Paris"));
        ZonedDateTime createdDateZoned = ZonedDateTime.ofInstant(now, ZoneId.of("Europe/Paris"));

        int year = createdDateZoned.getYear();
        int month = (int) ChronoUnit.MONTHS.between(epoch, createdDateZoned);
        int week = (int) ChronoUnit.WEEKS.between(epoch, createdDateZoned);
        int day = (int) ChronoUnit.DAYS.between(epoch, createdDateZoned);
        int hour = (int) ChronoUnit.HOURS.between(epoch, createdDateZoned);

        datableObject.year(year).month(month).week(week).day(day).hour(hour);
    }

    public static Instant getFrequencyInstant(ZonedDateTime now, String frequency) {
        switch (frequency.toLowerCase()) {
            case YEARLY:
                return Instant.ofEpochMilli(0);
            case MONTHLY:
                return now.minusYears(2).truncatedTo(ChronoUnit.DAYS).toInstant();
            case WEEKLY:
                return now.minusMonths(6).truncatedTo(ChronoUnit.DAYS).toInstant();
            case DAILY:
                return now.minusMonths(1).truncatedTo(ChronoUnit.DAYS).toInstant();
            case HOURLY:
                return now.minusDays(1).truncatedTo(ChronoUnit.DAYS).toInstant();
            default:
                return null;
        }
    }

    public static TemporalValueType getTemporalValueTypeFromFrequency(String frequency) {
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

    public static YoRCColumn getYoColumnFromField(String field) {
        return Arrays.stream(YoRCColumn.values()).filter(c -> c.getDatabaseValue().equals(field)).findFirst().orElse(null);
    }

    public static EntityStatColumn getEntityColumnFromField(String field) {
        return Arrays.stream(EntityStatColumn.values()).filter(c -> c.getDatabaseValue().equals(field)).findFirst().orElse(null);
    }
}
