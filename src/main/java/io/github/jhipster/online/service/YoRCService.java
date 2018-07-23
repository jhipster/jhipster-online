package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.YoRC_;
import io.github.jhipster.online.domain.deserializer.YoRCDeserializer;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.dto.RawSQL;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing YoRC.
 */
@Service
@Transactional
@JsonDeserialize(using = YoRCDeserializer.class)
public class YoRCService {

    private final static Integer numberOfDaysOfEpoch = 1970 * 365;

    private final Logger log = LoggerFactory.getLogger(YoRCService.class);

    private final YoRCRepository yoRCRepository;

    private final UserRepository userRepository;

    private final OwnerIdentityService ownerIdentityService;

    private final LanguageService languageService;

    private final EntityManager entityManager;

    public YoRCService(YoRCRepository yoRCRepository, OwnerIdentityService ownerIdentityService, UserRepository userRepository, LanguageService languageService, EntityManager entityManager) {
        this.yoRCRepository = yoRCRepository;
        this.ownerIdentityService = ownerIdentityService;
        this.userRepository = userRepository;
        this.languageService = languageService;
        this.entityManager = entityManager;
    }

    /**
     * Save a yoRC.
     *
     * @param yoRC the entity to save
     * @return the persisted entity
     */
    public YoRC save(YoRC yoRC) {
        log.debug("Request to save YoRC : {}", yoRC);
        return yoRCRepository.save(yoRC);
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

    public long countAll() {
        return yoRCRepository.count();
    }


    public List<TemporalCountDTO> getCount(Instant after, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQL> query = builder.createQuery(RawSQL.class);
        Root<YoRC> root = query.from(YoRC.class);
        ParameterExpression<Instant> parameter = builder.parameter(Instant.class, "date");
        query.select(builder.construct(RawSQL.class, root.get(dbTemporalFunction.getFieldName()), builder.count(root)))
            .where(builder.greaterThan(root.get(YoRC_.creationDate).as(Instant.class), parameter))
            .groupBy(root.get(dbTemporalFunction.getFieldName()));

        List<TemporalCountDTO> result = entityManager
            .createQuery(query)
            .setParameter("date", after)
            .getResultList()
            .stream()
            .map(item ->
                new TemporalCountDTO(absoluteMomentToLocalDateTime(item.getMoment(), dbTemporalFunction), item.getCount()))
            .collect(Collectors.toList());
        result.sort(TemporalCountDTO::compareTo);
        return result;
    }

    public void save(String applicationConfiguration) {
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Application configuration:\n{}", applicationConfiguration);
        try {
            JsonNode jsonNodeRoot = mapper.readTree(applicationConfiguration);
            JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
            YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
            yorc.setOwner(ownerIdentityService.findOrCreateUser(
                userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null)).orElse(null)));
            save(yorc);
            yorc.getSelectedLanguages().forEach(languageService::save);
            log.debug("Parsed json:\n{}", yorc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalDateTime absoluteMomentToLocalDateTime(Integer value, TemporalValueType valueType) {
        if (valueType.getUnit().equals(ChronoUnit.DAYS)) {
            return LocalDateTime
                .ofEpochSecond(0, 0, ZoneOffset.UTC)
                .plus(Duration.of((value * valueType.getDayMultiplier()) - numberOfDaysOfEpoch, ChronoUnit.DAYS));
        } else {
            return LocalDateTime
                .ofEpochSecond(0, 0, ZoneOffset.UTC)
                .plus(Duration.of(value * valueType.getDayMultiplier(), valueType.getUnit()));
        }


    }
}
