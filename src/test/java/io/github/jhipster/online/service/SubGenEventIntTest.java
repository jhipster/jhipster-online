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

package io.github.jhipster.online.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class SubGenEventIntTest {

    @Autowired
    private SubGenEventRepository subGenEventRepository;

    @Autowired
    private SubGenEventService subGenEventService;

    private List<SubGenEvent> sgeList;
    
    private Instant epochInstant;

    private Instant twoYearsAgoInstant;

    @Before
    public void init() {
        LocalDateTime epoch = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(2);

        epochInstant = Instant.ofEpochSecond(epoch.getSecond());
        twoYearsAgoInstant = Instant.ofEpochSecond(epoch.until(fiveYearsAgo, ChronoUnit.SECONDS));

        DataGenerationUtil.clearSubGenVentTable(subGenEventRepository);
        sgeList = DataGenerationUtil.addSubGenEventsToDatabase(10_000, twoYearsAgoInstant, Instant.now(), subGenEventRepository);
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count())
            .isEqualTo(subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()) && sge.getYear() == yearWeAreLookingFor)
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.YEAR)
                .stream()
                .filter(item ->
                    item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()) && sge.getWeek() == weekWeAreLookingFor)
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.WEEK)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()) && sge.getDay() == dayWeAreLookingFor)
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.DAY)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()) && sge.getHour() == hourWeAreLookingFor)
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.HOUR)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatDeploymentToolsMonthlyTotalDistributionIsCorrect() {
        assertThat(
            subGenEventService.getDeploymentToolDistribution(twoYearsAgoInstant, TemporalValueType.MONTH).stream()
            .mapToLong(distribution ->
                distribution.getValues().entrySet().stream()
                    .mapToLong(Map.Entry::getValue)
                    .sum())
            .sum()
        ).isEqualTo(
            sgeList.stream()
                .filter(sub ->
                    Arrays.stream(SubGenEventType.getDeploymentTools())
                        .anyMatch(type ->
                            type.getDatabaseValue().equalsIgnoreCase(sub.getType())
                        )
                ).count()
        );
    }

    @Test
    public void assertThatDeploymentToolsWeeklyTotalDistributionIsCorrect() {
        assertThat(
            subGenEventService.getDeploymentToolDistribution(twoYearsAgoInstant, TemporalValueType.WEEK).stream()
                .mapToLong(distribution ->
                    distribution.getValues().entrySet().stream()
                        .mapToLong(Map.Entry::getValue)
                        .sum())
                .sum()
        ).isEqualTo(
            sgeList.stream()
                .filter(sub ->
                    Arrays.stream(SubGenEventType.getDeploymentTools())
                        .anyMatch(type ->
                            type.getDatabaseValue().equalsIgnoreCase(sub.getType())
                        )
                ).count()
        );
    }

    @Test
    public void assertThatDeploymentToolsDailyTotalDistributionIsCorrect() {
        assertThat(
            subGenEventService.getDeploymentToolDistribution(twoYearsAgoInstant, TemporalValueType.DAY).stream()
                .mapToLong(distribution ->
                    distribution.getValues().entrySet().stream()
                        .mapToLong(Map.Entry::getValue)
                        .sum())
                .sum()
        ).isEqualTo(
            sgeList.stream()
                .filter(sub ->
                    Arrays.stream(SubGenEventType.getDeploymentTools())
                        .anyMatch(type ->
                            type.getDatabaseValue().equalsIgnoreCase(sub.getType())
                        )
                ).count()
        );
    }

    @Test
    public void assertThatDeploymentToolsHourlyTotalDistributionIsCorrect() {
        assertThat(
            subGenEventService.getDeploymentToolDistribution(twoYearsAgoInstant, TemporalValueType.HOUR).stream()
                .mapToLong(distribution ->
                    distribution.getValues().entrySet().stream()
                        .mapToLong(Map.Entry::getValue)
                        .sum())
                .sum()
        ).isEqualTo(
            sgeList.stream()
                .filter(sub ->
                    Arrays.stream(SubGenEventType.getDeploymentTools())
                        .anyMatch(type ->
                            type.getDatabaseValue().equalsIgnoreCase(sub.getType())
                        )
                ).count()
        );
    }
}
