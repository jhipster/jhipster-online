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
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class YoRCServiceChoicesIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    private static final String CLIENT_FRAMEWORK = "react";

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
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getClientFramework().equals(CLIENT_FRAMEWORK))
                .count()
        ).isEqualTo(yoRCService.getFieldCount(twoYearsAgoInstant, YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.YEAR).stream()
            .mapToLong(item ->
                item.getValues()
                    .entrySet()
                    .stream()
                    .filter(obj ->
                        obj.getKey().equals(CLIENT_FRAMEWORK))
                    .mapToLong(Map.Entry::getValue)
                    .sum()
            ).sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();

        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getYear() == yearWeAreLookingFor && yo.getClientFramework().equals(CLIENT_FRAMEWORK))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(twoYearsAgoInstant, YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.YEAR).stream()
                .filter(item ->
                    item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals(CLIENT_FRAMEWORK))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
            ).sum()
        );
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getWeek() == weekWeAreLookingFor && yo.getClientFramework().equals(CLIENT_FRAMEWORK))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(twoYearsAgoInstant, YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.WEEK).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals(CLIENT_FRAMEWORK))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getDay() == dayWeAreLookingFor && yo.getClientFramework().equals(CLIENT_FRAMEWORK))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(twoYearsAgoInstant, YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.DAY).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals(CLIENT_FRAMEWORK))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getHour() == hourWeAreLookingFor && yo.getClientFramework().equals(CLIENT_FRAMEWORK))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(twoYearsAgoInstant, YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.HOUR).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals(CLIENT_FRAMEWORK))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }
}
