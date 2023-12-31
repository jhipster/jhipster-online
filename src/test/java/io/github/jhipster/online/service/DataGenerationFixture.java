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

package io.github.jhipster.online.service;

import static java.time.ZonedDateTime.parse;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DataGenerationFixture {

    public static void fillDatabaseWithYoRCs(YoRCRepository yoRCRepository) {
        // 2017 (before tests starting date)
        yoRCRepository.saveAndFlush(yorc(parse("2017-09-02T00:00:00.000+01:00[Europe/Paris]").toInstant(), "angularX"));
        yoRCRepository.saveAndFlush(yorc(parse("2017-09-02T00:00:00.000+01:00[Europe/Paris]").toInstant(), "angularX"));

        // 2018
        yoRCRepository.saveAndFlush(yorc(parse("2018-02-02T00:00:00.000+01:00[Europe/Paris]").toInstant(), "angularX"));
        yoRCRepository.saveAndFlush(yorc(parse("2018-03-01T18:22:17.000+01:00[Europe/Paris]").toInstant(), "react"));
        yoRCRepository.saveAndFlush(yorc(parse("2018-04-01T18:22:17.000+01:00[Europe/Paris]").toInstant(), "angularX"));
        yoRCRepository.saveAndFlush(yorc(parse("2018-05-01T18:22:17.000+01:00[Europe/Paris]").toInstant(), "angularX"));
        yoRCRepository.saveAndFlush(yorc(parse("2018-06-01T18:22:17.000+01:00[Europe/Paris]").toInstant(), "angularX"));

        // sept 2018 same week
        yoRCRepository.saveAndFlush(yorc(parse("2018-09-13T18:22:17.000+01:00[Europe/Paris]").toInstant(), "angularX"));
        yoRCRepository.saveAndFlush(yorc(parse("2018-09-14T18:22:17.000+01:00[Europe/Paris]").toInstant(), "vuejs"));

        // sept 2018 other week
        yoRCRepository.saveAndFlush(yorc(parse("2018-09-27T18:22:17.000+01:00[Europe/Paris]").toInstant(), "vuejs"));

        // 2019
        yoRCRepository.saveAndFlush(yorc(parse("2019-01-01T14:22:17.000+01:00[Europe/Paris]").toInstant(), "react"));

        // jan 2019 same hour
        yoRCRepository.saveAndFlush(yorc(parse("2019-01-02T17:12:01.000+01:00[Europe/Paris]").toInstant(), "react"));
        yoRCRepository.saveAndFlush(yorc(parse("2019-01-02T17:34:01.000+01:00[Europe/Paris]").toInstant(), "vuejs"));

        yoRCRepository.saveAndFlush(yorc(parse("2019-01-01T06:25:53.000+01:00[Europe/Paris]").toInstant(), "vuejs"));
    }

    public static YoRC yorc(Instant createdDate, String clientFramework) {
        YoRC yorc = new YoRC()
            .serverPort("8080")
            .authenticationType("jwt")
            .cacheProvider("ehcache")
            .enableHibernateCache(true)
            .websocket(true)
            .databaseType("sql")
            .devDatabaseType("h2")
            .prodDatabaseType("psql")
            .searchEngine(true)
            .messageBroker(true)
            .serviceDiscoveryType(true)
            .buildTool("maven")
            .enableSwaggerCodegen(true)
            .clientFramework(clientFramework)
            .withAdminUi(true)
            .useSass(true)
            .clientPackageManager("npm")
            .applicationType("monolith")
            .enableTranslation(true)
            .nativeLanguage("fr")
            .hasProtractor(true)
            .hasGatling(false)
            .hasCucumber(false)
            .arch("x86")
            .gitProvider("github")
            .nodeVersion("9.0.0")
            .os("linux")
            .cpu("i5 2500k")
            .cores("8")
            .memory("16gb")
            .userLanguage("fr")
            .jhipsterVersion("5.3.1");

        return computeYoRCDate(createdDate, yorc);
    }

    private static YoRC computeYoRCDate(Instant createdDate, YoRC yorc) {
        ZonedDateTime epoch = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("Europe/Paris"));
        ZonedDateTime createdDateZoned = ZonedDateTime.ofInstant(createdDate, ZoneId.of("Europe/Paris"));

        int year = createdDateZoned.getYear();
        int month = (int) ChronoUnit.MONTHS.between(epoch, createdDateZoned);
        int week = (int) ChronoUnit.WEEKS.between(epoch, createdDateZoned);
        int day = (int) ChronoUnit.DAYS.between(epoch, createdDateZoned);
        int hour = (int) ChronoUnit.HOURS.between(epoch, createdDateZoned);

        return yorc.creationDate(createdDate).year(year).month(month).week(week).day(day).hour(hour);
    }
}
