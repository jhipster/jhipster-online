package io.github.jhipster.online.service.util;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.service.DataGenerationFixture;
import io.github.jhipster.online.web.rest.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    private static final ZonedDateTime now = ZonedDateTime.of(2018, 8, 1, 15, 26, 0, 0, ZoneId.of("Europe/Paris"));

    @Test
    @Transactional
    public void getAbsoluteDateFromCreationDate() {
        YoRC yorc = DataGenerationFixture.yorc(now.toInstant(), "react");

        DateUtil.setAbsoluteDate(yorc, yorc.getCreationDate());

        assertThat(yorc)
            .extracting(YoRC::getYear, YoRC::getMonth, YoRC::getWeek, YoRC::getDay, YoRC::getHour)
            .containsExactly(2018, 583, 2534, 17744, 425869);
    }

    @Test
    public void getStartingInstantForYearlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "yearly");

        assertThat(startingDate).isEqualTo(parse("1970-01-01T00:00:00Z"));
    }

    @Test
    public void getStartingInstantForMonthlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "monthly");

        assertThat(startingDate).isEqualTo(parse("2016-07-31T22:00:00Z"));
    }

    @Test
    public void getStartingInstantForWeeklyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "weekly");

        assertThat(startingDate).isEqualTo(parse("2018-01-31T23:00:00Z"));
    }

    @Test
    public void getStartingInstantForDailyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "daily");

        assertThat(startingDate).isEqualTo(parse("2018-06-30T22:00:00Z"));
    }

    @Test
    public void getStartingInstantForHourlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "hourly");

        assertThat(startingDate).isEqualTo(parse("2018-07-30T22:00:00Z"));
    }
}
