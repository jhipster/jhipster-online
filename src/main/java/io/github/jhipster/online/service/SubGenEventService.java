/**
 * Copyright 2017-2023 the original author or authors from the JHipster project.
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

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.mapper.SubGenEventMapper;
import io.github.jhipster.online.service.util.SubGenEventStatisticsService;
import java.time.Instant;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing SubGenEvent.
 */
@Service
@Transactional
public class SubGenEventService {

    private final Logger log = LoggerFactory.getLogger(SubGenEventService.class);

    private final SubGenEventRepository subGenEventRepository;

    private final SubGenEventMapper subGenEventMapper;

    private final SubGenEventStatisticsService subGenEventStatisticsService;

    public SubGenEventService(
        SubGenEventRepository subGenEventRepository,
        SubGenEventMapper subGenEventMapper,
        SubGenEventStatisticsService subGenEventStatisticsService
    ) {
        this.subGenEventRepository = subGenEventRepository;
        this.subGenEventMapper = subGenEventMapper;
        this.subGenEventStatisticsService = subGenEventStatisticsService;
    }

    /**
     * Save a subGenEventDTO.
     *
     * @param subGenEventDTO the entity to save
     * @return the persisted entity
     */
    public SubGenEventDTO save(SubGenEventDTO subGenEventDTO) {
        log.debug("Request to save SubGenEvent : {}", subGenEventDTO);
        SubGenEvent subGenEvent = subGenEventMapper.toEntity(subGenEventDTO);
        subGenEvent = subGenEventRepository.save(subGenEvent);
        return subGenEventMapper.toDto(subGenEvent);
    }

    /**
     * Get all the subGenEvents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SubGenEvent> findAll() {
        log.debug("Request to get all SubGenEvents");
        return subGenEventRepository.findAll();
    }

    /**
     * Get one subGenEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SubGenEvent> findOne(Long id) {
        log.debug("Request to get SubGenEvent : {}", id);
        return subGenEventRepository.findById(id);
    }

    /**
     * Delete the subGenEvent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubGenEvent : {}", id);
        subGenEventRepository.deleteById(id);
    }

    public void deleteByOwner(GeneratorIdentity owner) {
        log.debug("Request to delete SubGenEvent by owner : {}", owner);
        subGenEventRepository.deleteAllByOwner(owner);
    }

    public List<TemporalCountDTO> getFieldCount(Instant after, SubGenEventType field, TemporalValueType dbTemporalFunction) {
        return subGenEventStatisticsService.getFieldCount(after, field, dbTemporalFunction);
    }

    public List<TemporalDistributionDTO> getDeploymentToolDistribution(Instant after, TemporalValueType dbTemporalFunction) {
        return subGenEventStatisticsService.getDeploymentToolDistribution(after, dbTemporalFunction);
    }
}