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

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.service.util.StatisticsUtil;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private final YoRCService yoRCService;


    private final GeneratorIdentityService generatorIdentityService;

    private final SubGenEventService subGenEventService;

    private final EntityStatsService entityStatsService;

    public StatisticsService(YoRCService yoRCService,
                             GeneratorIdentityService generatorIdentityService,
                             SubGenEventService subGenEventService,
                             EntityStatsService entityStatsService) {
        this.yoRCService = yoRCService;
        this.generatorIdentityService = generatorIdentityService;
        this.subGenEventService = subGenEventService;
        this.entityStatsService = entityStatsService;
    }

    @Transactional
    @Async("statisticsExecutor")
    public void addEntry(String entry, String host) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeRoot = mapper.readTree(entry);
        JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
        String generatorGuid = jsonNodeRoot.get("generator-id").asText();
        String generatorVersion = jsonNodeRoot.get("generator-version").asText();
        String gitProvider = jsonNodeRoot.get("git-provider").asText();
        String nodeVersion = jsonNodeRoot.get("node-version").asText();
        String os = jsonNodeRoot.get("os").asText();
        String arch = jsonNodeRoot.get("arch").asText();
        String cpu = jsonNodeRoot.get("cpu").asText();
        String cores = jsonNodeRoot.get("cores").asText();
        String memory = jsonNodeRoot.get("memory").asText();
        String userLanguage = jsonNodeRoot.get("user-language").asText();

        YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(yorc, now);

        yorc.jhipsterVersion(generatorVersion)
            .gitProvider(gitProvider)
            .nodeVersion(nodeVersion)
            .os(os)
            .arch(arch)
            .cpu(cpu)
            .cores(cores)
            .memory(memory)
            .userLanguage(userLanguage)
            .creationDate(Instant.ofEpochMilli(now.getMillis()));

        log.info("Adding an entry for generator {}.", generatorGuid);
        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);

        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        if (generatorIdentity.isPresent()) {
            generatorIdentity.get().host(host);
            yorc.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }

        yoRCService.save(yorc);
    }

    @Transactional
    @Async("statisticsExecutor")
    public void addSubGenEvent(SubGenEvent subGenEvent, String generatorGuid)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(subGenEvent, now);

        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        subGenEvent.date(Instant.ofEpochMilli(now.getMillis()));
        if (generatorIdentity.isPresent()) {
            subGenEvent.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }
        subGenEventService.save(subGenEvent);
    }

    @Transactional
    @Async("statisticsExecutor")
    public void addEntityStats(EntityStats entityStats, String generatorGuid)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(entityStats, now);

        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        entityStats.date(Instant.now());
        if (generatorIdentity.isPresent()) {
            entityStats.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }
        entityStatsService.save(entityStats);
    }

    @Transactional
    public void deleteStatistics(User owner) {
        List<GeneratorIdentity> generators = generatorIdentityService.findAllOwned(owner);

        log.debug("Statistics data deletion requested for : {} ({} generator(s)) ", owner.getLogin(), generators.size());

        log.debug("Deleting yos");
        generators.forEach(yoRCService::deleteByOwnerIdentity);
        log.debug("Deleting sub generator events");
        generators.forEach(subGenEventService::deleteByOwner);
        log.debug("Deleting entity statistics");
        generators.forEach(entityStatsService::deleteByOwner);

    }

    public void tryToCreateGeneratorIdentityAndIgnoreErrors(String generatorGuid) {
        try {
            generatorIdentityService.tryToCreateGeneratorIdentity(generatorGuid);
        } catch (RuntimeException re) {
            log.debug("GeneratorIdentity {} could not be created, ignoring", generatorGuid);
        }
    }
}
