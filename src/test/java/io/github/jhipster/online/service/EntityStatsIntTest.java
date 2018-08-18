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
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.repository.EntityStatsRepository;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class EntityStatsIntTest {

    @Autowired
    private EntityStatsRepository entityStatsRepository;

    @Autowired
    private EntityStatsService entityStatsService;

    private static final String INFINITE_SCROLL = "infinite-scroll";

    private List<EntityStats> list;

    private Instant epochInstant;

    private Instant twoYearsAgoInstant;

    @Before
    public void init() {
        LocalDateTime epoch = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(2);

        epochInstant = java.time.Instant.ofEpochSecond(epoch.getSecond());
        twoYearsAgoInstant = java.time.Instant.ofEpochSecond(epoch.until(fiveYearsAgo, ChronoUnit.SECONDS));

        DataGenerationUtil.clearEntityStatsTable(entityStatsRepository);
        list = DataGenerationUtil.addEntityStatsToDatabase(1_000, twoYearsAgoInstant, Instant.now(), entityStatsRepository);
    }

    @Test
    public void assertThatYearlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(twoYearsAgoInstant, TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatMonthlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(twoYearsAgoInstant, TemporalValueType.MONTH)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatWeeklyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(twoYearsAgoInstant, TemporalValueType.WEEK)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatDailyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(twoYearsAgoInstant, TemporalValueType.DAY)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatHourlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(twoYearsAgoInstant, TemporalValueType.HOUR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAYearCountIsCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();

        assertThat(
            list.stream().filter(yo -> yo.getYear() == yearWeAreLookingFor).count()
        ).isEqualTo(
            entityStatsService
                .getCount(twoYearsAgoInstant, TemporalValueType.YEAR)
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

        assertThat(
            list.stream()
                .filter(yo -> yo.getMonth() == monthWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService
                .getCount(twoYearsAgoInstant, TemporalValueType.MONTH)
                .stream()
                .filter(item ->
                    item.getDate().getYear() == numberOfYear + 1970 && item.getDate().getMonth().getValue() == monthOfTheYear)
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatAWeekCountIsCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(
            list.stream()
                .filter(yo -> yo.getWeek() == weekWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService
                .getCount(twoYearsAgoInstant, TemporalValueType.WEEK)
                .stream()
                .filter(e ->
                    e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatADayCountIsCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(
            list.stream().filter(yo -> yo.getDay() == dayWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService
                .getCount(twoYearsAgoInstant, TemporalValueType.DAY)
                .stream()
                .filter(e ->
                    e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAHourCountIsCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(
            list.stream()
                .filter(yo ->
                    yo.getHour() == hourWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService
                .getCount(twoYearsAgoInstant, TemporalValueType.HOUR)
                .stream()
                .filter(e ->
                    e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals(INFINITE_SCROLL))
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(twoYearsAgoInstant, EntityStatColumn.PAGINATION, TemporalValueType.YEAR).stream()
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals(INFINITE_SCROLL)).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();

        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals(INFINITE_SCROLL) && entity.getYear() == yearWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(twoYearsAgoInstant, EntityStatColumn.PAGINATION, TemporalValueType.YEAR).stream()
            .filter(item ->
                item.getDate().getYear() == yearWeAreLookingFor)
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals(INFINITE_SCROLL)).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals(INFINITE_SCROLL) && entity.getWeek() == weekWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(twoYearsAgoInstant, EntityStatColumn.PAGINATION, TemporalValueType.WEEK).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals(INFINITE_SCROLL)).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals(INFINITE_SCROLL) && entity.getDay() == dayWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(twoYearsAgoInstant, EntityStatColumn.PAGINATION, TemporalValueType.DAY).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals(INFINITE_SCROLL)).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals(INFINITE_SCROLL) && entity.getHour() == hourWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(twoYearsAgoInstant, EntityStatColumn.PAGINATION, TemporalValueType.HOUR).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals(INFINITE_SCROLL)).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }
}
