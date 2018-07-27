package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
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
            Optional<GeneratorIdentity> maybeResult = generatorIdentityRepository.findFirstByGuidEquals(guid);
            if (!maybeResult.isPresent()) {
                return generatorIdentityRepository.save(new GeneratorIdentity().guid(guid));
            } else {
                return maybeResult.get();
            }
    }

    public boolean bindCurrentUserToGenerator(User user, String guid) {
        Optional<GeneratorIdentity> maybeGeneratorIdentity = generatorIdentityRepository.findFirstByGuidEquals(guid);

        if(maybeGeneratorIdentity.isPresent() && maybeGeneratorIdentity.get().getOwner() != null && maybeGeneratorIdentity.get().getOwner().getOwner() != null) {
            return false;
        }

        OwnerIdentity ownerIdentity = ownerIdentityService.findOrCreateUser(user);
        save(findOrCreateOneByGuid(guid).owner(ownerIdentity));

        return true;
    }

    public boolean unbindCurrentUserFromGenerator(User user, String guid) {
        Optional<GeneratorIdentity> maybeGeneratorIdentity = generatorIdentityRepository.findFirstByGuidEquals(guid);

        if (!maybeGeneratorIdentity.isPresent() ) {
            return false;
        }

        OwnerIdentity owner  = maybeGeneratorIdentity.get().getOwner();
        if (owner == null || !owner.getOwner().getId().equals(user.getId())) {
            return false;
        }

        maybeGeneratorIdentity.get().setOwner(null);
        generatorIdentityRepository.save(maybeGeneratorIdentity.get());

        return true;
    }
}
