package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.repository.EntityStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing EntityStats.
 */
@Service
@Transactional
public class EntityStatsService {

    private final Logger log = LoggerFactory.getLogger(EntityStatsService.class);

    private final EntityStatsRepository entityStatsRepository;

    public EntityStatsService(EntityStatsRepository entityStatsRepository) {
        this.entityStatsRepository = entityStatsRepository;
    }

    /**
     * Save a entityStats.
     *
     * @param entityStats the entity to save
     * @return the persisted entity
     */
    public EntityStats save(EntityStats entityStats) {
        log.debug("Request to save EntityStats : {}", entityStats);        return entityStatsRepository.save(entityStats);
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
}
