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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.service.util.StatisticsUtil;
import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

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
        log.info("Adding an entry for generator {}.", generatorGuid);

        DateTime now = DateTime.now();

        GeneratorIdentity generatorIdentity;
        try {
            generatorIdentity = generatorIdentityService.findOrCreateOneByGuid(generatorGuid);
        } catch (DataIntegrityViolationException e) {
            generatorIdentity = generatorIdentityService.handleDataDuplication(generatorGuid);
        }

        generatorIdentity.host(host);
        YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
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
            .owner(generatorIdentity)
            .creationDate(Instant.ofEpochMilli(now.getMillis()));
        yoRCService.save(yorc);
    }

    public void addSubGenEvent(SubGenEvent subGenEvent, String generatorId)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(subGenEvent, now);

        GeneratorIdentity generatorIdentity;
        try {
            generatorIdentity = generatorIdentityService.findOrCreateOneByGuid(generatorId);
        } catch (DataIntegrityViolationException e) {
            generatorIdentity = generatorIdentityService.handleDataDuplication(generatorId);
        }

        subGenEvent
            .date(Instant.ofEpochMilli(now.getMillis()))
            .owner(generatorIdentity);
        subGenEventService.save(subGenEvent);
    }

    public void addEntityStats(EntityStats entityStats, String generatorId)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(entityStats, now);

        GeneratorIdentity generatorIdentity;
        try {
            generatorIdentity = generatorIdentityService.findOrCreateOneByGuid(generatorId);
        } catch (DataIntegrityViolationException e) {
            generatorIdentity = generatorIdentityService.handleDataDuplication(generatorId);
        }

        entityStats
            .date(Instant.now())
            .owner(generatorIdentity);
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
}
