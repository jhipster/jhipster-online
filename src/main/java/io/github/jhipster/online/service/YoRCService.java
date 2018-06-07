package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing YoRC.
 */
@Service
@Transactional
public class YoRCService {

    private final Logger log = LoggerFactory.getLogger(YoRCService.class);

    private final YoRCRepository yoRCRepository;

    public YoRCService(YoRCRepository yoRCRepository) {
        this.yoRCRepository = yoRCRepository;
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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<YoRC> findAll(Pageable pageable) {
        log.debug("Request to get all YoRCS");
        return yoRCRepository.findAll(pageable);
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
}
