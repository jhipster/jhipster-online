package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.repository.JdlMetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing JdlMetadata.
 */
@Service
@Transactional
public class JdlMetadataService {

    private final Logger log = LoggerFactory.getLogger(JdlMetadataService.class);

    private final JdlMetadataRepository jdlMetadataRepository;

    public JdlMetadataService(JdlMetadataRepository jdlMetadataRepository) {
        this.jdlMetadataRepository = jdlMetadataRepository;
    }

    /**
     * Save a jdlMetadata.
     *
     * @param jdlMetadata the entity to save
     * @return the persisted entity
     */
    public JdlMetadata save(JdlMetadata jdlMetadata) {
        log.debug("Request to save JdlMetadata : {}", jdlMetadata);        return jdlMetadataRepository.save(jdlMetadata);
    }

    /**
     * Get all the jdlMetadata.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<JdlMetadata> findAll() {
        log.debug("Request to get all JdlMetadata");
        return jdlMetadataRepository.findAll();
    }


    /**
     * Get one jdlMetadata by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<JdlMetadata> findOne(Long id) {
        log.debug("Request to get JdlMetadata : {}", id);
        return jdlMetadataRepository.findById(id);
    }

    /**
     * Delete the jdlMetadata by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JdlMetadata : {}", id);
        jdlMetadataRepository.deleteById(id);
    }
}
