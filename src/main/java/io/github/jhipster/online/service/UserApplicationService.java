package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.UserApplication;
import io.github.jhipster.online.repository.UserApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserApplication}.
 */
@Service
@Transactional
public class UserApplicationService {

    private final Logger log = LoggerFactory.getLogger(UserApplicationService.class);

    private final UserApplicationRepository userApplicationRepository;

    public UserApplicationService(UserApplicationRepository userApplicationRepository) {
        this.userApplicationRepository = userApplicationRepository;
    }

    /**
     * Save a userApplication.
     *
     * @param userApplication the entity to save.
     * @return the persisted entity.
     */
    public UserApplication save(UserApplication userApplication) {
        log.debug("Request to save UserApplication : {}", userApplication);
        return userApplicationRepository.save(userApplication);
    }

    /**
     * Get all the userApplications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserApplication> findAll() {
        log.debug("Request to get all UserApplications");
        return userApplicationRepository.findAll();
    }

    /**
     * Get one userApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserApplication> findOne(Long id) {
        log.debug("Request to get UserApplication : {}", id);
        return userApplicationRepository.findById(id);
    }

    /**
     * Delete the userApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserApplication : {}", id);
        userApplicationRepository.deleteById(id);
    }
}
