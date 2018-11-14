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

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.repository.*;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class StatisticsServiceIntTest {

    private String generatorId = "cf51ff78-187a-4554-9b09-8f6f95f1a7a5";

    private String dummyYo = "{\n" +
        "        \"generator-jhipster\": {\n" +
        "        \"useYarn\": false,\n" +
        "            \"experimental\": false,\n" +
        "            \"skipI18nQuestion\": true,\n" +
        "            \"logo\": false,\n" +
        "            \"clientPackageManager\": \"npm\",\n" +
        "            \"cacheProvider\": \"ehcache\",\n" +
        "            \"enableHibernateCache\": true,\n" +
        "            \"websocket\": false,\n" +
        "            \"databaseType\": \"sql\",\n" +
        "            \"devDatabaseType\": \"h2Disk\",\n" +
        "            \"prodDatabaseType\": \"mysql\",\n" +
        "            \"searchEngine\": false,\n" +
        "            \"messageBroker\": false,\n" +
        "            \"serviceDiscoveryType\": false,\n" +
        "            \"buildTool\": \"maven\",\n" +
        "            \"enableSwaggerCodegen\": false,\n" +
        "            \"authenticationType\": \"jwt\",\n" +
        "            \"serverPort\": \"8080\",\n" +
        "            \"clientFramework\": \"angularX\",\n" +
        "            \"useSass\": false,\n" +
        "            \"testFrameworks\": [],\n" +
        "        \"enableTranslation\": true,\n" +
        "            \"nativeLanguage\": \"en\",\n" +
        "            \"languages\": [\n" +
        "        \"en\"\n" +
        "      ],\n" +
        "        \"applicationType\": \"monolith\"\n" +
        "    },\n" +
        "        \"generator-id\": \"" + generatorId + "\",\n" +
        "        \"generator-version\": \"5.1.0\",\n" +
        "        \"git-provider\": \"local\",\n" +
        "        \"node-version\": \"v8.11.1\",\n" +
        "        \"os\": \"linux:4.15.0-29-generic\",\n" +
        "        \"arch\": \"x64\",\n" +
        "        \"cpu\": \"Intel(R) Core(TM) i7-7700K CPU @ 4.20GHz\",\n" +
        "        \"cores\": 8,\n" +
        "        \"memory\": 16776642560,\n" +
        "        \"user-language\": \"en_GB\",\n" +
        "        \"isARegeneration\": true\n" +
        "    }";


    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private SubGenEventRepository subGenEventRepository;

    @Autowired
    private EntityStatsRepository entityStatsRepository;

    @Autowired
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private UserRepository userRepository;

    private List<YoRC> yos;

    private List<SubGenEvent> subs;

    private List<EntityStats> entities;

    private User user;

    @Before
    public void init() {
        DataGenerationUtil.clearYoRcTable(yoRCRepository);
        DataGenerationUtil.clearSubGenVentTable(subGenEventRepository);
        DataGenerationUtil.clearEntityStatsTable(entityStatsRepository);

        yos = DataGenerationUtil.addYosToDatabase(100, Instant.now(), Instant.now().plus(365, ChronoUnit.DAYS), yoRCRepository);
        subs = DataGenerationUtil.addSubGenEventsToDatabase(100, Instant.now(), Instant.now().plus(365, ChronoUnit.DAYS), subGenEventRepository);
        entities = DataGenerationUtil.addEntityStatsToDatabase(100, Instant.now(), Instant.now().plus(365, ChronoUnit.DAYS), entityStatsRepository);

        user = userRepository.findAll().get((int) (Math.random() * userRepository.count()));
    }

    @Test
    public void assertThatAddingAYoWorks() {
        try {
            statisticsService.addEntry(dummyYo, "127.0.0.1");
        } catch (IOException e) {
            // Test is a failure with this exception.
            assertThat(false).isTrue();
        }

        assertThat(yoRCRepository.count()).isEqualTo(yos.size() + 1);
    }

    @Test
    public void assertThatAddingASubGenEventWorks() {
        statisticsService.addSubGenEvent(new SubGenEvent(), generatorId);
        assertThat(subGenEventRepository.count()).isEqualTo(subs.size() + 1);
    }

    @Test
    public void assertThatAddingAAnEntityStatWorks() {
        statisticsService.addEntityStats(new EntityStats(), generatorId);
        assertThat(entityStatsRepository.count()).isEqualTo(entities.size() + 1);
    }

    @Test
    public void assertThatStatisticsCanBeDeleted() {
        IntStream.range(0, 10).forEach(i -> {
            statisticsService.addSubGenEvent(new SubGenEvent(), generatorId);
            statisticsService.addEntityStats(new EntityStats(), generatorId);
            try {
                statisticsService.addEntry(dummyYo, "127.0.0.1");
            } catch (IOException e) {
                // Test is a failure with this exception.
                assertThat(false).isTrue();
            }
        });

        GeneratorIdentity generatorIdentity = generatorIdentityRepository.save(
            generatorIdentityRepository.findFirstByGuidEquals(generatorId).orElse(null).owner(user)
        );

        statisticsService.deleteStatistics(user);

        assertThat(
            yoRCRepository.findAll().stream()
                .noneMatch(yo -> yo.getOwner() != null &&
                    yo.getOwner().equals(generatorIdentity))
        ).isTrue();
        assertThat(
            subGenEventRepository.findAll().stream()
                .noneMatch(sub -> sub.getOwner() != null &&
                    sub.getOwner().equals(generatorIdentity))
        ).isTrue();
        assertThat(
            entityStatsRepository.findAll().stream()
                .noneMatch(entity -> entity.getOwner() != null &&
                    entity.getOwner().equals(generatorIdentity))
        ).isTrue();
    }
}
