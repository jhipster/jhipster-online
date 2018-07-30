package io.github.jhipster.online.service.util;

import io.github.jhipster.online.domain.interfaces.CompleteDate;
import org.joda.time.*;

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
