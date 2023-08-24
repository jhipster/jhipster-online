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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.jhipster.online.config.CacheConfiguration;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.YoRC_;
import io.github.jhipster.online.domain.deserializer.YoRCDeserializer;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.dto.*;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.mapper.YoRCMapper;
import io.github.jhipster.online.service.util.QueryUtil;
import java.io.IOException;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing YoRC.
 */
@Service
@Transactional
@JsonDeserialize(using = YoRCDeserializer.class)
public class YoRCService {

    private final Logger log = LoggerFactory.getLogger(YoRCService.class);

    private final YoRCRepository yoRCRepository;

    private final EntityManager entityManager;

    private final YoRCMapper yoRCMapper;

    public YoRCService(YoRCRepository yoRCRepository, EntityManager entityManager, YoRCMapper yoRCMapper) {
        this.yoRCRepository = yoRCRepository;
        this.entityManager = entityManager;
        this.yoRCMapper = yoRCMapper;
    }

    /**
     * Save a yoRCDTO.
     *
     * @param yoRCDTO the entity to save
     * @return the persisted entity
     */
    public YoRCDTO save(YoRCDTO yoRCDTO) {
        log.debug("Request to save YoRC : {}", yoRCDTO);
        YoRC yoRC = yoRCMapper.toEntity(yoRCDTO);
        yoRC = yoRCRepository.save(yoRC);
        return yoRCMapper.toDto(yoRC);
    }

    /**
     * Get all the yoRCS.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<YoRC> findAll() {
        log.debug("Request to get all YoRCS");
        return yoRCRepository.findAll();
    }

    /**
     * Get one yoRC by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<YoRC> findOne(Long id) {
        log.debug("Request to get YoRC : {}", id);
        return yoRCRepository.findById(id);
    }

    /**
     * Delete the yoRC by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete YoRC : {}", id);
        yoRCRepository.deleteById(id);
    }

    public void deleteByOwnerIdentity(GeneratorIdentity ownerIdentity) {
        log.debug("Request to delete by OwnerIdentity : {}", ownerIdentity);
        yoRCRepository.deleteAllByOwner(ownerIdentity);
    }

    @Cacheable(cacheNames = CacheConfiguration.STATISTICS_YORC_COUNT)
    public long countAll() {
        return yoRCRepository.count();
    }

    public void save(String applicationConfiguration) {
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Application configuration:\n{}", applicationConfiguration);
        try {
            JsonNode jsonNodeRoot = mapper.readTree(applicationConfiguration);
            JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
            YoRCDTO yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRCDTO.class);
            /**
             * TODO: do something about apps that are generated by jhonline
             * -> Optout settings
             * -> What should be the guid for such case ?
             * yorc.setOwner( ### );
             */
            save(yorc);
            log.debug("Parsed json:\n{}", yorc);
        } catch (IOException e) {
            log.error("Generation failed", e);
        }
    }

    public List<TemporalCountDTO> getCount(Instant after, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQL> query = builder.createQuery(RawSQL.class);
        Root<YoRC> root = query.from(YoRC.class);
        ParameterExpression<Instant> parameter = builder.parameter(Instant.class, QueryUtil.DATE);

        query
            .select(builder.construct(RawSQL.class, root.get(dbTemporalFunction.getFieldName()), builder.count(root)))
            .where(builder.greaterThan(root.get(YoRC_.creationDate).as(Instant.class), parameter))
            .groupBy(root.get(dbTemporalFunction.getFieldName()));

        return QueryUtil.createCountQueryAndCollectData(after, dbTemporalFunction, query, entityManager);
    }

    public List<TemporalDistributionDTO> getFieldCount(Instant after, YoRCColumn field, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQLField> query = builder.createQuery(RawSQLField.class);
        Root<YoRC> root = query.from(YoRC.class);
        ParameterExpression<Instant> parameter = builder.parameter(Instant.class, QueryUtil.DATE);

        query
            .select(
                builder.construct(
                    RawSQLField.class,
                    root.get(dbTemporalFunction.getFieldName()),
                    root.get(field.getDatabaseValue()),
                    builder.count(root)
                )
            )
            .where(builder.greaterThan(root.get(YoRC_.creationDate).as(Instant.class), parameter))
            .groupBy(root.get(field.getDatabaseValue()), root.get(dbTemporalFunction.getFieldName()));

        return QueryUtil.createDistributionQueryAndCollectData(after, dbTemporalFunction, query, entityManager);
    }
}
