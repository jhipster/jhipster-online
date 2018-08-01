package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.SubGenEvent_;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.dto.RawSQLField;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.QueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SubGenEvent.
 */
@Service
@Transactional
public class SubGenEventService {

    private final Logger log = LoggerFactory.getLogger(SubGenEventService.class);

    private final SubGenEventRepository subGenEventRepository;

    private final EntityManager entityManager;

    public SubGenEventService(SubGenEventRepository subGenEventRepository, EntityManager entityManager) {
        this.subGenEventRepository = subGenEventRepository;
        this.entityManager = entityManager;
    }

    /**
     * Save a subGenEvent.
     *
     * @param subGenEvent the entity to save
     * @return the persisted entity
     */
    public SubGenEvent save(SubGenEvent subGenEvent) {
        log.debug("Request to save SubGenEvent : {}", subGenEvent);
        return subGenEventRepository.save(subGenEvent);
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
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQLField> query = builder.createQuery(RawSQLField.class);
        Root<SubGenEvent> root = query.from(SubGenEvent.class);
        ParameterExpression<Instant> dateParameter = builder.parameter(Instant.class, QueryUtil.DATE);
        ParameterExpression<String> typeParameter = builder.parameter(String.class, QueryUtil.TYPE);

        query.select(builder.construct(RawSQLField.class, root.get(dbTemporalFunction.getFieldName()), root.get(SubGenEvent_.type), builder.count(root)))
            .where(builder.greaterThan(root.get(SubGenEvent_.date).as(Instant.class), dateParameter), builder.equal(root.get(SubGenEvent_.type), typeParameter))
            .groupBy(root.get(SubGenEvent_.type), root.get(dbTemporalFunction.getFieldName()));

        return entityManager
            .createQuery(query)
            .setParameter(QueryUtil.DATE, after)
            .setParameter(QueryUtil.TYPE, field.getDatabaseValue())
            .getResultList()
            .stream()
            .map(entry ->
                new TemporalCountDTO(TemporalValueType.absoluteMomentToLocalDateTime(entry.getMoment().longValue(), dbTemporalFunction), entry.getCount())
            ).collect(Collectors.toList());
    }
}
