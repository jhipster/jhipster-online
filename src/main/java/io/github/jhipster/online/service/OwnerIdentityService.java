package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.OwnerIdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing OwnerIdentity.
 */
@Service
@Transactional
public class OwnerIdentityService {

    private final Logger log = LoggerFactory.getLogger(OwnerIdentityService.class);

    private final OwnerIdentityRepository ownerIdentityRepository;

    public OwnerIdentityService(OwnerIdentityRepository ownerIdentityRepository) {
        this.ownerIdentityRepository = ownerIdentityRepository;
    }

    /**
     * Save a ownerIdentity.
     *
     * @param ownerIdentity the entity to save
     * @return the persisted entity
     */
    public OwnerIdentity save(OwnerIdentity ownerIdentity) {
        log.debug("Request to save OwnerIdentity : {}", ownerIdentity);
        return ownerIdentityRepository.save(ownerIdentity);
    }

    /**
     * Get all the ownerIdentities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OwnerIdentity> findAll() {
        log.debug("Request to get all OwnerIdentities");
        return ownerIdentityRepository.findAll();
    }


    /**
     * Get one ownerIdentity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OwnerIdentity> findOne(Long id) {
        log.debug("Request to get OwnerIdentity : {}", id);
        return ownerIdentityRepository.findById(id);
    }

    /**
     * Delete the ownerIdentity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OwnerIdentity : {}", id);
        ownerIdentityRepository.deleteById(id);
    }

    /**
     * Find an OwnerIdentity. Create one if does not exist.
     *
     * @param user user
     */
    public OwnerIdentity findOrCreateUser(User user) {
        return ownerIdentityRepository.findByOwner(user).orElseGet(() -> ownerIdentityRepository.save(new OwnerIdentity().owner(user)));
    }
}
