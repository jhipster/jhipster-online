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

package io.github.jhipster.online.service.enums;

import java.time.*;
import java.time.temporal.ChronoUnit;

public enum TemporalValueType {
    YEAR("year", ChronoUnit.DAYS, 365),
    MONTH("month",ChronoUnit.DAYS, 31),
    WEEK("week", ChronoUnit.DAYS, 7),
    DAY("day", ChronoUnit.DAYS, 1),
    HOUR("hour", ChronoUnit.HOURS, 1);

    private final ChronoUnit unit;

    private final String fieldName;

    private final int dayMultiplier;

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
