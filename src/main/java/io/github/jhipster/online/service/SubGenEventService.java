package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.repository.SubGenEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing SubGenEvent.
 */
@Service
@Transactional
public class SubGenEventService {

    private final Logger log = LoggerFactory.getLogger(SubGenEventService.class);

    private final SubGenEventRepository subGenEventRepository;

    public SubGenEventService(SubGenEventRepository subGenEventRepository) {
        this.subGenEventRepository = subGenEventRepository;
    }

    /**
     * Save a subGenEvent.
     *
     * @param subGenEvent the entity to save
     * @return the persisted entity
     */
    public SubGenEvent save(SubGenEvent subGenEvent) {
        log.debug("Request to save SubGenEvent : {}", subGenEvent);        return subGenEventRepository.save(subGenEvent);
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
}
