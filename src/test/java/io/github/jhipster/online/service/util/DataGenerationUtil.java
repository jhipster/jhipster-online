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

package io.github.jhipster.online.service.util;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.*;

import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.*;

public class DataGenerationUtil {

    public static void clearSubGenVentTable(SubGenEventRepository subGenEventRepository) {
        subGenEventRepository.deleteAll();
    }

    public static void clearYoRcTable(YoRCRepository yoRCRepository) {
        yoRCRepository.deleteAll();
    }

    public static void clearEntityStatsTable(EntityStatsRepository entityStatsRepository) {
        entityStatsRepository.deleteAll();
    }

    public static List<SubGenEvent> addSubGenEventsToDatabase(int nbData, Instant start, Instant end, SubGenEventRepository subGenEventRepository) {
        List<SubGenEvent> list = new ArrayList<>();
        String source = "generator";
        String type;
        String event = "";
        Instant date;

        for (int i = 0; i < nbData; i++) {
            SubGenEvent sge = new SubGenEvent();
            Duration between = Duration.between(start, end);
            date = end.minus(Duration.ofSeconds((int) (Math.random() * between.getSeconds())));

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
                .date(date)
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .hour(hour)
                .type(type)
                .source(source)
                .event(event);

            list.add(subGenEventRepository.save(sge));
        }

        return list;
    }

    public static List<YoRC> addYosToDatabase(int nbData, Instant start, Instant end, YoRCRepository yoRCRepository) {
        List<YoRC> ret = new ArrayList<>();

        String jhipsterVersion = "5.0.2";
        String gitProvider = "";
        String nodeVersion = "";
        String os = "";
        String arch = "";
        String cpu = "";
        String cores = "";
        String memory = "16";
        String userLanguage = "";

        String applicationType = "";
        String authenticationType = "";
        String serverPort = "8080";
        String cacheProvider = "";
        boolean enableHibernateCache;
        boolean webSocket;
        String databaseType = "";
        String devDatabaseType = "";
        String prodDatabaseType = "";
        boolean searchEngine;
        boolean messageBroker;
        boolean serviceDiscoveryType;
        String buildTool = "";
        boolean enableSwaggerCodegen;
        String clientFramework = "";
        boolean useSass;
        String clientPackageManager = "";
        boolean enableTranslation;
        String nativeLanguage = "";
        boolean hasProtractor;
        boolean hasGatling;
        boolean hasCucumber;

        for (int i = 0; i < nbData; i++) {
            YoRC yorc = new YoRC();

            Duration between = Duration.between(start, end);
            Instant createdDate = end.minus(Duration.ofSeconds((int) (Math.random() * between.getSeconds())));

            DateTime ldtNow = new DateTime(createdDate.toEpochMilli());
            DateTime epoch = new DateTime(1970, 1, 1, 0, 0);

            int year = ldtNow.getYear();
            int month = Months.monthsBetween(epoch.toInstant(), ldtNow.toInstant()).getMonths();
            int week = Weeks.weeksBetween(epoch.toInstant(), ldtNow.toInstant()).getWeeks();
            int day = Days.daysBetween(epoch.toInstant(), ldtNow.toInstant()).getDays();
            int hour = Hours.hoursBetween(epoch.toInstant(), ldtNow.toInstant()).getHours();

            int randomUserLang = (int) (Math.random() * 3);
            if (randomUserLang == 0) {
                userLanguage = "en-gb";
            } else if (randomUserLang == 1) {
                userLanguage = "fr-fr";
            } else if (randomUserLang == 2) {
                userLanguage = "pt-pt";
            }

            int randomVersionJhip = (int) (Math.random() * 3);
            if (randomVersionJhip == 0) {
                jhipsterVersion = "beta-5.0.2";
            } else if (randomVersionJhip == 1) {
                jhipsterVersion = "beta-5.0.1";
            } else if (randomVersionJhip == 2) {
                jhipsterVersion = "4.14.4";
            }

            int randomArch = (int) (Math.random() * 2);
            arch = randomArch == 0 ? "x64" : "x86";

            int randomApplicationType = (int) (Math.random() * 3);
            if (randomApplicationType == 0) {
                applicationType = "monolith";
            } else if (randomApplicationType == 1) {
                applicationType = "microservice";
            } else if (randomApplicationType == 2) {
                applicationType = "gateway";
            } else if (randomApplicationType == 3) {
                applicationType = "uaa";
            }

            int randomAuthenticationType = (int) (Math.random() * 4);
            if (randomAuthenticationType == 0) {
                authenticationType = "jwt";
            } else if (randomAuthenticationType == 1) {
                authenticationType = "oauth2";
            } else if (randomAuthenticationType == 2) {
                authenticationType = "session";
            } else if (randomAuthenticationType == 3) {
                authenticationType = "uaa";
            }

            int randomCacheProvider = (int) (Math.random() * 4);
            if (randomCacheProvider == 0) {
                cacheProvider = "ehcache";
            } else if (randomCacheProvider == 1) {
                cacheProvider = "hazelcast";
            } else if (randomCacheProvider == 2) {
                cacheProvider = "infinispan";
            } else if (randomCacheProvider == 3) {
                cacheProvider = "no";
            }

            int randomEnableHibernateCache = (int) (Math.random() * 2);
            enableHibernateCache = randomEnableHibernateCache == 0;

            int randomWebSocket = (int) (Math.random() * 2);
            webSocket = randomWebSocket == 0;

            int randomDatabaseType = (int) (Math.random() * 5);
            if (randomDatabaseType == 0) {
                databaseType = "no";
            } else if (randomDatabaseType == 1) {
                databaseType = "sql";
            } else if (randomDatabaseType == 2) {
                databaseType = "mongodb";
            } else if (randomDatabaseType == 3) {
                databaseType = "cassandra";
            } else if (randomDatabaseType == 4) {
                databaseType = "couchbase";
            }

            int randomDevDatabaseType = (int) (Math.random() * 6);
            if (randomDevDatabaseType == 0) {
                devDatabaseType = "h2Disk";
            } else if (randomDevDatabaseType == 1) {
                devDatabaseType = "h2Memory";
            } else if (randomDevDatabaseType == 2) {
                devDatabaseType = "mysql";
            } else if (randomDevDatabaseType == 3) {
                devDatabaseType = "mariadb";
            } else if (randomDevDatabaseType == 4) {
                devDatabaseType = "postgresql";
            } else if (randomDevDatabaseType == 5) {
                devDatabaseType = "oracle";
            }

            int randomProdDatabaseType = (int) (Math.random() * 4);
            if (randomProdDatabaseType == 0) {
                prodDatabaseType = "mysql";
            } else if (randomProdDatabaseType == 1) {
                prodDatabaseType = "mariadb";
            } else if (randomProdDatabaseType == 2) {
                prodDatabaseType = "postgresql";
            } else if (randomProdDatabaseType == 3) {
                prodDatabaseType = "oracle";
            }

            int randomSearchEngine = (int) (Math.random() * 2);
            searchEngine = randomSearchEngine == 0;

            int randomMessageBroker = (int) (Math.random() * 2);
            messageBroker = randomMessageBroker == 0;

            int randomServiceDiscoveryType = (int) (Math.random() * 2);
            serviceDiscoveryType = randomServiceDiscoveryType == 0;

            int randomBuildTool = (int) (Math.random() * 2);
            buildTool = randomBuildTool == 0 ? "maven" : "gradle";

            int randomEnableSwaggerCodegen = (int) (Math.random() * 2);
            enableSwaggerCodegen = randomEnableSwaggerCodegen == 0;

            int randomFront = (int) (Math.random() * 2);
            clientFramework = randomFront == 0 ? "react" : "angularX";

            int randomClientPackageManager = (int) (Math.random() * 2);
            clientPackageManager = randomClientPackageManager == 0 ? "yarn" : "npm";

            int randomUseSass = (int) (Math.random() * 2);
            useSass = randomUseSass == 0;

            int randomEnableTranslation = (int) (Math.random() * 2);
            enableTranslation = randomEnableTranslation == 0;

            int randomHasProtractor = (int) (Math.random() * 2);
            hasProtractor = randomHasProtractor == 0;

            int randomHasCum = (int) (Math.random() * 2);
            hasCucumber = randomHasCum == 0;

            int randomHasGatling = (int) (Math.random() * 2);
            hasGatling = randomHasGatling == 0;

            String[] languages = {"en", "pt-pt", "pt-br", "fr", "ar", "or", "ir", "ur", "erre", "lol", "okk", "min", "max", "upmc", "p7", "21milton", "pg"};
            int randomLanguage = (int) (Math.random() * languages.length);
            nativeLanguage = languages[randomLanguage];

            yorc.serverPort(serverPort)
                .authenticationType(authenticationType)
                .cacheProvider(cacheProvider)
                .enableHibernateCache(enableHibernateCache)
                .websocket(webSocket)
                .databaseType(databaseType)
                .devDatabaseType(devDatabaseType)
                .prodDatabaseType(prodDatabaseType)
                .searchEngine(searchEngine)
                .messageBroker(messageBroker)
                .serviceDiscoveryType(serviceDiscoveryType)
                .buildTool(buildTool)
                .enableSwaggerCodegen(enableSwaggerCodegen)
                .clientFramework(clientFramework)
                .useSass(useSass)
                .clientPackageManager(clientPackageManager)
                .applicationType(applicationType)
                .enableTranslation(enableTranslation)
                .nativeLanguage(nativeLanguage)
                .hasProtractor(hasProtractor)
                .hasGatling(hasGatling)
                .hasCucumber(hasCucumber)
                .creationDate(createdDate)
                .arch(arch)
                .gitProvider(gitProvider)
                .nodeVersion(nodeVersion)
                .os(os)
                .arch(arch)
                .cpu(cpu)
                .cores(cores)
                .memory(memory)
                .userLanguage(userLanguage)
                .jhipsterVersion(jhipsterVersion)
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .hour(hour);

            ret.add(yoRCRepository.save(yorc));
        }
        return ret;
    }

    public static List<EntityStats> addEntityStatsToDatabase(int nbData, Instant start, Instant end, EntityStatsRepository entityStatsRepository) {
        String[] dtoChoices = { "no", "mapstruct" };
        String[] paginationChoices = { "no", "infinite-scroll", "links" };
        Random rng = new Random();
        return IntStream.range(0, nbData).mapToObj(i -> {
            Duration between = Duration.between(start, end);
            Instant date = end.minus(Duration.ofSeconds((int) (Math.random() * between.getSeconds())));

            DateTime ldtNow = new DateTime(date.toEpochMilli());
            DateTime epoch = new DateTime(1970, 1, 1, 0, 0);

            int year = ldtNow.getYear();
            int month = Months.monthsBetween(epoch.toInstant(), ldtNow.toInstant()).getMonths();
            int week = Weeks.weeksBetween(epoch.toInstant(), ldtNow.toInstant()).getWeeks();
            int day = Days.daysBetween(epoch.toInstant(), ldtNow.toInstant()).getDays();
            int hour = Hours.hoursBetween(epoch.toInstant(), ldtNow.toInstant()).getHours();


            return entityStatsRepository.save(
                new EntityStats()
                    .date(date)
                    .dto(dtoChoices[rng.nextInt(dtoChoices.length)])
                    .fields(rng.nextInt(10))
                    .fluentMethods(rng.nextBoolean())
                    .relationships(rng.nextInt(10))
                    .pagination(paginationChoices[rng.nextInt(paginationChoices.length)])
                    .year(year)
                    .month(month)
                    .week(week)
                    .day(day)
                    .hour(hour)
            );
        }).collect(Collectors.toList());
    }

    public static List<GeneratorIdentity> addGeneratorIdentitiesToDatabase(int howMany, GeneratorIdentityRepository generatorIdentityRepository) {
        return IntStream.range(0, howMany)
            .mapToObj(i ->
                generatorIdentityRepository.save(
                    new GeneratorIdentity()
                        .guid(UUID.randomUUID().toString()))
            ).collect(Collectors.toList());
    }

}
