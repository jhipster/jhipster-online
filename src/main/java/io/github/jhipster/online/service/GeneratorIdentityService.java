package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing GeneratorIdentity.
 */
@Service
@Transactional
public class GeneratorIdentityService {

    private final Logger log = LoggerFactory.getLogger(GeneratorIdentityService.class);

    private final GeneratorIdentityRepository generatorIdentityRepository;

    private final OwnerIdentityService ownerIdentityService;

    public GeneratorIdentityService(GeneratorIdentityRepository generatorIdentityRepository, OwnerIdentityService ownerIdentityService) {
        this.generatorIdentityRepository = generatorIdentityRepository;
        this.ownerIdentityService = ownerIdentityService;
    }

    /**
     * Save a generatorIdentity.
     *
     * @param generatorIdentity the entity to save
     * @return the persisted entity
     */
    public GeneratorIdentity save(GeneratorIdentity generatorIdentity) {
        log.debug("Request to save GeneratorIdentity : {}", generatorIdentity);
        return generatorIdentityRepository.save(generatorIdentity);
    }

    /**
     * Get all the generatorIdentities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GeneratorIdentity> findAll() {
        log.debug("Request to get all GeneratorIdentities");
        return generatorIdentityRepository.findAll();
    }

    /**
     * Get all the generatorIdentities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GeneratorIdentity> findAllOwned(User user) {
        log.debug("Request to get all GeneratorIdentities");
        Optional<OwnerIdentity> ownerIdentity = ownerIdentityService.findByUser(user);

        if (ownerIdentity.isPresent()) {
            return generatorIdentityRepository.findAllByOwner(ownerIdentity.get());
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Get one generatorIdentity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GeneratorIdentity> findOne(Long id) {
        log.debug("Request to get GeneratorIdentity : {}", id);
        return generatorIdentityRepository.findById(id);
    }

    /**
     * Delete the generatorIdentity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneratorIdentity : {}", id);
        generatorIdentityRepository.deleteById(id);
    }

    /**
     * Find a GeneratorIdentity. Create one if can't be found.
     *
     * @param guid Generator you're looking for.
     */
    @Transactional
    public GeneratorIdentity findOrCreateOneByGuid(String guid) {
        GeneratorIdentity result;

        result = generatorIdentityRepository.findFirstByGuidEquals(guid).orElseGet(() -> save(new GeneratorIdentity().guid(guid)));

        getOrCreateOwnerIdentity(result);

        return result;
    }

    @Transactional
    public GeneratorIdentity handleDataDuplication(String guid) {
        return generatorIdentityRepository.findFirstByGuidEquals(guid).orElse(null);
    }

    private OwnerIdentity getOrCreateOwnerIdentity(GeneratorIdentity generatorIdentity) {
        OwnerIdentity result;
        generatorIdentity = generatorIdentityRepository.findById(generatorIdentity.getId()).get();
        if (generatorIdentity.getOwner() == null) {
            result = new OwnerIdentity();
            generatorIdentity.owner(ownerIdentityService.save(result));

        } else {
            result = generatorIdentity.getOwner();
        }

        return result;
    }


    @Transactional
    public boolean bindCurrentUserToGenerator(User user, String guid) {
        GeneratorIdentity generatorIdentity = findOrCreateOneByGuid(guid);

        // Check if the generator has already an owner
        if(generatorIdentity.getOwner().getOwner() != null) {
            return false;
        }

        generatorIdentity.getOwner().owner(user);

        return true;
    }

    @Transactional
    public boolean unbindCurrentUserFromGenerator(User user, String guid) {
        Optional<GeneratorIdentity> maybeGeneratorIdentity = generatorIdentityRepository.findFirstByGuidEquals(guid);

        if (!maybeGeneratorIdentity.isPresent() ) {
            return false;
        }

        OwnerIdentity owner  = maybeGeneratorIdentity.get().getOwner();
        if (owner == null || !owner.getOwner().getId().equals(user.getId())) {
            return false;
        }

        maybeGeneratorIdentity.get().getOwner().setOwner(null);
        return true;
    }
}
