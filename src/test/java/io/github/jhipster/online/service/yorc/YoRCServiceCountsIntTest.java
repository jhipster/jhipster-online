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

package io.github.jhipster.online.service.yorc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class YoRCServiceCountsIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    private List<YoRC> yos;

    private Instant epochInstant;

    private Instant twoYearsAgoInstant;

    @Before
    public void init() {
        LocalDateTime epoch = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(2);

        epochInstant = Instant.ofEpochSecond(epoch.getSecond());
        twoYearsAgoInstant = Instant.ofEpochSecond(epoch.until(fiveYearsAgo, ChronoUnit.SECONDS));

        DataGenerationUtil.clearYoRcTable(yoRCRepository);
        yos = DataGenerationUtil.addYosToDatabase(1_000, twoYearsAgoInstant, Instant.now(), yoRCRepository);
    }

    @Test
    public void assertThatCountDoesNotOmitAnyData() {
        assertThat(yos.size()).isEqualTo(yoRCService.countAll());
    }

    @Test
    public void assertThatYearlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatMonthlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.MONTH)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatWeeklyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.WEEK)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatDailyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.DAY)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatHourlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.HOUR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAYearCountIsCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();
        assertThat(
            yos.stream().filter(yo -> yo.getYear() == yearWeAreLookingFor).count())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.YEAR)
                .stream()
                .filter(item -> item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAMonthCountIsCorrect() {
        long monthWeAreLookingFor = 561;
        long numberOfYear = monthWeAreLookingFor / 12;
        long monthOfTheYear = monthWeAreLookingFor % 12 + 1;

        assertThat(yos.stream().filter(yo -> yo.getMonth() == monthWeAreLookingFor).count())
            .isEqualTo(
                yoRCService
                .getCount(twoYearsAgoInstant, TemporalValueType.MONTH)
                .stream()
                .filter(item -> item.getDate().getYear() == numberOfYear + 1970 && item.getDate().getMonth().getValue() == monthOfTheYear)
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAWeekCountIsCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(yos.stream().filter(yo -> yo.getWeek() == weekWeAreLookingFor).count())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.WEEK).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatADayCountIsCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(yos.stream()
            .filter(yo -> yo.getDay() == dayWeAreLookingFor).count())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.DAY).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAHourCountIsCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(yos.stream().filter(yo -> yo.getHour() == hourWeAreLookingFor).count())
            .isEqualTo(
                yoRCService.getCount(twoYearsAgoInstant, TemporalValueType.HOUR)
                .stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }
}
