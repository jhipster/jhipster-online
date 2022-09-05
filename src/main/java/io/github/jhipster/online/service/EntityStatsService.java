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

import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.domain.EntityStats_;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.repository.EntityStatsRepository;
import io.github.jhipster.online.service.dto.*;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.mapper.EntityStatsMapper;
import io.github.jhipster.online.service.util.QueryUtil;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing EntityStats.
 */
@Service
@Transactional
public class EntityStatsService {

    private final Logger log = LoggerFactory.getLogger(EntityStatsService.class);

    private final EntityStatsRepository entityStatsRepository;

    private final EntityManager entityManager;

    private final EntityStatsMapper entityStatsMapper;

    public EntityStatsService(
        EntityStatsRepository entityStatsRepository,
        EntityManager entityManager,
        EntityStatsMapper entityStatsMapper
    ) {
        this.entityStatsRepository = entityStatsRepository;
        this.entityManager = entityManager;
        this.entityStatsMapper = entityStatsMapper;
    }

    /**
     * Save a entityStatsDTO.
     *
     * @param entityStatsDTO the entity to save
     * @return the persisted entity
     */
    public EntityStatsDTO save(EntityStatsDTO entityStatsDTO) {
        log.debug("Request to save EntityStats : {}", entityStatsDTO);
        EntityStats entityStats = entityStatsMapper.toEntity(entityStatsDTO);
        entityStats = entityStatsRepository.save(entityStats);
        return entityStatsMapper.toDto(entityStats);
    }

    /**
     * Get all the entityStats.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EntityStats> findAll() {
        log.debug("Request to get all EntityStats");
        return entityStatsRepository.findAll();
    }

    /**
     * Get one entityStats by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EntityStats> findOne(Long id) {
        log.debug("Request to get EntityStats : {}", id);
        return entityStatsRepository.findById(id);
    }

    /**
     * Delete the entityStats by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EntityStats : {}", id);
        entityStatsRepository.deleteById(id);
    }

    public void deleteByOwner(GeneratorIdentity owner) {
        log.debug("Request to delete EntityStats by owner: {}", owner);
        entityStatsRepository.deleteAllByOwner(owner);
    }

    public List<TemporalCountDTO> getCount(Instant after, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQL> query = builder.createQuery(RawSQL.class);
        Root<EntityStats> root = query.from(EntityStats.class);
        ParameterExpression<Instant> parameter = builder.parameter(Instant.class, QueryUtil.DATE);

        query
            .select(builder.construct(RawSQL.class, root.get(dbTemporalFunction.getFieldName()), builder.count(root)))
            .where(builder.greaterThan(root.get(EntityStats_.date).as(Instant.class), parameter))
            .groupBy(root.get(dbTemporalFunction.getFieldName()));

        return QueryUtil.createCountQueryAndCollectData(after, dbTemporalFunction, query, entityManager);
    }

    public List<TemporalDistributionDTO> getFieldCount(Instant after, EntityStatColumn field, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQLField> query = builder.createQuery(RawSQLField.class);
        Root<EntityStats> root = query.from(EntityStats.class);
        ParameterExpression<Instant> parameter = builder.parameter(Instant.class, QueryUtil.DATE);

        query
            .select(
                builder.construct(
                    RawSQLField.class,
                    root.get(dbTemporalFunction.getFieldName()),
                    root.get(field.getDatabaseValue()).as(String.class),
                    builder.count(root)
                )
            )
            .where(builder.greaterThan(root.get(EntityStats_.date).as(Instant.class), parameter))
            .groupBy(root.get(field.getDatabaseValue()), root.get(dbTemporalFunction.getFieldName()));

        return QueryUtil.createDistributionQueryAndCollectData(after, dbTemporalFunction, query, entityManager);
    }
}
