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

package io.github.jhipster.online.service.yorc;

import static java.time.ZonedDateTime.parse;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.DataGenerationFixture;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class YoRCServiceChoicesIntTest {

    @Autowired
    private YoRCService yoRCService;

    @Autowired
    private YoRCRepository yoRCRepository;

    @Transactional
    @Test
    void assertThatYearlyProportionsAreCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalDistributionDTO> result = yoRCService.getFieldCount(
            parse("2018-01-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            YoRCColumn.CLIENT_FRAMEWORK,
            TemporalValueType.YEAR
        );

        assertThat(result)
            .hasSize(2)
            .extracting(TemporalDistributionDTO::getDate)
            .containsExactly(Instant.parse("2018-01-01T00:00:00Z"), Instant.parse("2019-01-01T00:00:00Z"));

        Map<String, Long> fst = result.get(0).getValues(); // 2018
        Map<String, Long> snd = result.get(1).getValues(); // 2019

        assertThat(fst).containsEntry("react", 1L).containsEntry("angularX", 5L).containsEntry("vuejs", 2L);
        assertThat(snd).containsEntry("react", 2L).containsEntry("vuejs", 2L);
    }

    @Transactional
    @Test
    void assertThatMonthlyProportionsAreCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalDistributionDTO> result = yoRCService.getFieldCount(
            parse("2018-01-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            YoRCColumn.CLIENT_FRAMEWORK,
            TemporalValueType.MONTH
        );

        assertThat(result)
            .hasSize(7)
            .extracting(TemporalDistributionDTO::getDate)
            .containsExactly(
                Instant.parse("2018-02-01T00:00:00Z"),
                Instant.parse("2018-03-01T00:00:00Z"),
                Instant.parse("2018-04-01T00:00:00Z"),
                Instant.parse("2018-05-01T00:00:00Z"),
                Instant.parse("2018-06-01T00:00:00Z"),
                Instant.parse("2018-09-01T00:00:00Z"),
                Instant.parse("2019-01-01T00:00:00Z")
            );

        Map<String, Long> feb = result.get(0).getValues(); // feb 2018
        Map<String, Long> mar = result.get(1).getValues(); // mar 2018
        Map<String, Long> apr = result.get(2).getValues(); // apr 2018
        Map<String, Long> may = result.get(3).getValues(); // may 2018
        Map<String, Long> jun = result.get(4).getValues(); // jun 2018
        Map<String, Long> sep = result.get(5).getValues(); // sep 2018
        Map<String, Long> jan = result.get(6).getValues(); // jan 2019

        assertThat(feb).containsEntry("angularX", 1L);

        assertThat(mar).containsEntry("react", 1L);

        assertThat(apr).containsEntry("angularX", 1L);
        assertThat(may).containsEntry("angularX", 1L);
        assertThat(jun).containsEntry("angularX", 1L);

        assertThat(sep).containsEntry("angularX", 1L).containsEntry("vuejs", 2L);
        assertThat(jan).containsEntry("react", 2L).containsEntry("vuejs", 2L);
    }

    @Transactional
    @Test
    void assertThatWeeklyProportionsAreCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalDistributionDTO> result = yoRCService.getFieldCount(
            parse("2018-06-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            YoRCColumn.CLIENT_FRAMEWORK,
            TemporalValueType.WEEK
        );

        assertThat(result)
            .hasSize(3)
            .extracting(TemporalDistributionDTO::getDate)
            .containsExactly(
                Instant.parse("2018-12-27T00:00:00Z"),
                Instant.parse("2018-09-13T00:00:00Z"),
                Instant.parse("2018-09-27T00:00:00Z")
            );

        Map<String, Long> fst = result.get(0).getValues(); // thu 27/12/2018
        Map<String, Long> snd = result.get(1).getValues(); // thu 13/09/2018
        Map<String, Long> thr = result.get(2).getValues(); // thu 27/09/2018

        assertThat(fst).containsEntry("react", 2L).containsEntry("vuejs", 2L);

        assertThat(snd).containsEntry("angularX", 1L).containsEntry("vuejs", 1L);

        assertThat(thr).containsEntry("vuejs", 1L);
    }

    @Transactional
    @Test
    void assertThatDailyProportionsAreCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalDistributionDTO> result = yoRCService.getFieldCount(
            parse("2018-06-01T18:22:17.000+01:00[Europe/Paris]").toInstant(),
            YoRCColumn.CLIENT_FRAMEWORK,
            TemporalValueType.DAY
        );

        assertThat(result)
            .hasSize(5)
            .extracting(TemporalDistributionDTO::getDate)
            .containsExactly(
                Instant.parse("2018-09-27T00:00:00Z"),
                Instant.parse("2019-01-01T00:00:00Z"),
                Instant.parse("2019-01-02T00:00:00Z"),
                Instant.parse("2018-09-13T00:00:00Z"),
                Instant.parse("2018-09-14T00:00:00Z")
            );

        Map<String, Long> fst = result.get(0).getValues(); // 27 sept 2018
        Map<String, Long> snd = result.get(1).getValues(); // 01 jan 2019
        Map<String, Long> thr = result.get(2).getValues(); // 02 jan 2019
        Map<String, Long> fou = result.get(3).getValues(); // 13 sept 2018
        Map<String, Long> fiv = result.get(4).getValues(); // 14 sept 2018

        assertThat(fst).containsEntry("vuejs", 1L);

        assertThat(snd).containsEntry("react", 1L).containsEntry("vuejs", 1L);

        assertThat(thr).containsEntry("react", 1L).containsEntry("vuejs", 1L);

        assertThat(fou).containsEntry("angularX", 1L);

        assertThat(fiv).containsEntry("vuejs", 1L);
    }

    @Transactional
    @Test
    void assertThatHourlyProportionsAreCorrect() {
        DataGenerationFixture.fillDatabaseWithYoRCs(yoRCRepository);

        List<TemporalDistributionDTO> result = yoRCService.getFieldCount(
            parse("2019-01-01T01:01:01.000+01:00[Europe/Paris]").toInstant(),
            YoRCColumn.CLIENT_FRAMEWORK,
            TemporalValueType.HOUR
        );

        assertThat(result)
            .hasSize(3)
            .extracting(TemporalDistributionDTO::getDate)
            .containsExactly(
                Instant.parse("2019-01-01T13:00:00Z"),
                Instant.parse("2019-01-02T16:00:00Z"),
                Instant.parse("2019-01-01T05:00:00Z")
            );

        Map<String, Long> fst = result.get(0).getValues(); // /01/01/2018 13h
        Map<String, Long> snd = result.get(1).getValues(); // 02/01/2018 16h
        Map<String, Long> thr = result.get(2).getValues(); // 01/01/2018 5h

        assertThat(fst).containsEntry("react", 1L);

        assertThat(snd).containsEntry("react", 1L).containsEntry("vuejs", 1L);

        assertThat(thr).containsEntry("vuejs", 1L);
    }
}
