/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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

package io.github.jhipster.online.web.rest.util;

import java.time.*;
import java.util.Arrays;

import org.joda.time.DateTime;

import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.service.enums.TemporalValueType;

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

    public static YoRCColumn getYoColumnFromField(String field) {
        return Arrays
            .stream(YoRCColumn.values())
            .filter(c -> c.getDatabaseValue().equals(field))
            .findFirst()
            .orElse(null);
    }

    public static EntityStatColumn getEntityColumnFromField(String field) {
        return Arrays
            .stream(EntityStatColumn.values())
            .filter(c -> c.getDatabaseValue().equals(field))
            .findFirst()
            .orElse(null);
    }
}
