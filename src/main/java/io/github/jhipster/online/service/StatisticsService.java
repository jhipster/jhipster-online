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

package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.service.dto.EntityStatsDTO;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import io.github.jhipster.online.service.mapper.EntityStatsMapper;
import io.github.jhipster.online.service.mapper.SubGenEventMapper;
import io.github.jhipster.online.service.mapper.YoRCMapper;
import io.github.jhipster.online.util.DateUtil;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private final YoRCService yoRCService;

    private final GeneratorIdentityService generatorIdentityService;

    private final SubGenEventService subGenEventService;

    private final EntityStatsService entityStatsService;

    private final SubGenEventMapper subGenEventMapper;

    private final EntityStatsMapper entityStatsMapper;

    private YoRCMapper yoRCMapper;

    public StatisticsService(
        YoRCService yoRCService,
        GeneratorIdentityService generatorIdentityService,
        SubGenEventService subGenEventService,
        EntityStatsService entityStatsService,
        SubGenEventMapper subGenEventMapper,
        EntityStatsMapper entityStatsMapper,
        YoRCMapper yoRCMapper
    ) {
        this.yoRCService = yoRCService;
        this.generatorIdentityService = generatorIdentityService;
        this.subGenEventService = subGenEventService;
        this.entityStatsService = entityStatsService;
        this.subGenEventMapper = subGenEventMapper;
        this.entityStatsMapper = entityStatsMapper;
        this.yoRCMapper = yoRCMapper;
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

        Instant now = Instant.now();
        DateUtil.setAbsoluteDate(yorc, now);

        yorc
            .jhipsterVersion(generatorVersion)
            .gitProvider(gitProvider)
            .nodeVersion(nodeVersion)
            .os(os)
            .arch(arch)
            .cpu(cpu)
            .cores(cores)
            .memory(memory)
            .userLanguage(userLanguage)
            .creationDate(now);

        log.info("Adding an entry for generator {}.", generatorGuid);
        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);

        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        if (generatorIdentity.isPresent()) {
            generatorIdentity.get().host(host);
            yorc.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }

        yoRCService.save(yoRCMapper.toDto(yorc));
    }

    @Transactional
    @Async("statisticsExecutor")
    public void addSubGenEvent(SubGenEventDTO subGenEventDTO, String generatorGuid) {
        Instant now = Instant.now();
        SubGenEvent subGenEvent = subGenEventMapper.toEntity(subGenEventDTO);
        DateUtil.setAbsoluteDate(subGenEvent, now);

        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        subGenEvent.date(now);
        if (generatorIdentity.isPresent()) {
            subGenEvent.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }
        subGenEventService.save(subGenEventMapper.toDto(subGenEvent));
    }

    @Transactional
    @Async("statisticsExecutor")
    public void addEntityStats(EntityStatsDTO entityStatsDTO, String generatorGuid) {
        Instant now = Instant.now();
        EntityStats entityStats = entityStatsMapper.toEntity(entityStatsDTO);
        DateUtil.setAbsoluteDate(entityStats, now);

        this.tryToCreateGeneratorIdentityAndIgnoreErrors(generatorGuid);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOneByGuid(generatorGuid);

        entityStats.date(now);
        if (generatorIdentity.isPresent()) {
            entityStats.owner(generatorIdentity.get());
        } else {
            log.info("GeneratorIdentity {} was not correctly created", generatorGuid);
        }
        entityStatsService.save(entityStatsMapper.toDto(entityStats));
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
