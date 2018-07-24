package io.github.jhipster.online.service.util;

import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import org.joda.time.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SubGenEventUtil {

    public static void clearDatabase(SubGenEventRepository subGenEventRepository) {
        subGenEventRepository.deleteAll();
    }

    public static List<SubGenEvent> addFakeData(int nbData, Calendar start, Calendar end, SubGenEventRepository subGenEventRepository) {
        List<SubGenEvent> list = new ArrayList<>();
        String source = "generator";
        String type = "";
        String event = "";
        Instant date;

        for (int i = 0; i < nbData; i++) {
            SubGenEvent sge = new SubGenEvent();
            Duration between = Duration.between(start.toInstant(), end.toInstant());
            date = end.toInstant().minus(Duration.ofSeconds((int) (Math.random() * between.getSeconds())));

            DateTime ldtNow = new DateTime(date.toEpochMilli());
            DateTime epoch = new DateTime(1970, 1, 1, 0, 0);

            int year = ldtNow.getYear();
            int month = Months.monthsBetween(epoch.toInstant(), ldtNow.toInstant()).getMonths();
            int week = Weeks.weeksBetween(epoch.toInstant(), ldtNow.toInstant()).getWeeks();
            int day = Days.daysBetween(epoch.toInstant(), ldtNow.toInstant()).getDays();
            int hour = Hours.hoursBetween(epoch.toInstant(), ldtNow.toInstant()).getHours();

            SubGenEventType[] arr = SubGenEventType.values();
            type = arr[(int)(Math.random() * arr.length)].getDatabaseValue();

            sge
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .hour(hour)
                .type(type);

            subGenEventRepository.save(sge);
        }

        return list;
    }
}
