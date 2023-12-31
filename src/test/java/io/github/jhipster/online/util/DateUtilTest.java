/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.service.DataGenerationFixture;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

class DateUtilTest {

    private static final ZonedDateTime now = ZonedDateTime.of(2018, 8, 1, 15, 26, 0, 0, ZoneId.of("Europe/Paris"));

    @Test
    @Transactional
    void getAbsoluteDateFromCreationDate() {
        YoRC yorc = DataGenerationFixture.yorc(now.toInstant(), "react");

        DateUtil.setAbsoluteDate(yorc, yorc.getCreationDate());

        assertThat(yorc)
            .extracting(YoRC::getYear, YoRC::getMonth, YoRC::getWeek, YoRC::getDay, YoRC::getHour)
            .containsExactly(2018, 583, 2534, 17744, 425869);
    }

    @Test
    void getStartingInstantForYearlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "yearly");

        assertThat(startingDate).isEqualTo(parse("1970-01-01T00:00:00Z"));
    }

    @Test
    void getStartingInstantForMonthlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "monthly");

        assertThat(startingDate).isEqualTo(parse("2016-07-31T22:00:00Z"));
    }

    @Test
    void getStartingInstantForWeeklyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "weekly");

        assertThat(startingDate).isEqualTo(parse("2018-01-31T23:00:00Z"));
    }

    @Test
    void getStartingInstantForDailyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "daily");

        assertThat(startingDate).isEqualTo(parse("2018-06-30T22:00:00Z"));
    }

    @Test
    void getStartingInstantForHourlyFrequency() {
        Instant startingDate = DateUtil.getFrequencyInstant(now, "hourly");

        assertThat(startingDate).isEqualTo(parse("2018-07-30T22:00:00Z"));
    }
}
