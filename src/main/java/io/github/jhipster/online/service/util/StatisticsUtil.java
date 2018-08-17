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

package io.github.jhipster.online.service.util;

import org.joda.time.*;

import io.github.jhipster.online.domain.interfaces.CompleteDate;

public class StatisticsUtil {

    public static CompleteDate setAbsoluteDate(CompleteDate datableObject, DateTime now) {
        DateTime epoch = new DateTime(1970, 1, 1, 0, 0);
        int year = now.getYear();
        int month = Months.monthsBetween(epoch.toInstant(), now.toInstant()).getMonths();
        int week = Weeks.weeksBetween(epoch.toInstant(), now.toInstant()).getWeeks();
        int day = Days.daysBetween(epoch.toInstant(), now.toInstant()).getDays();
        int hour = Hours.hoursBetween(epoch.toInstant(), now.toInstant()).getHours();

        return datableObject
            .year(year)
            .month(month)
            .week(week)
            .day(day)
            .hour(hour);
    }
}
