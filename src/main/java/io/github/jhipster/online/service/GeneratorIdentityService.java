package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private final UserService userService;

    public GeneratorIdentityService(GeneratorIdentityRepository generatorIdentityRepository, OwnerIdentityService ownerIdentityService, UserService userService) {
        this.generatorIdentityRepository = generatorIdentityRepository;
        this.ownerIdentityService = ownerIdentityService;
        this.userService = userService;
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
    public GeneratorIdentity findOrCreateOneByGuid(String guid) {
        GeneratorIdentity result = generatorIdentityRepository.findFirstByGuidIs(guid)
            .orElseGet(() -> generatorIdentityRepository.save(new GeneratorIdentity().guid(guid)));
        OwnerIdentity owner = result.getOwner();
        if (owner == null) {
            owner = new OwnerIdentity();
            ownerIdentityService.save(owner);
            result.setOwner(owner);
            save(result);
        }
        return result;
    }

    public boolean bindCurrentUserToGenerator(String guid) {
        Optional<GeneratorIdentity> maybeGeneratorIdentity = generatorIdentityRepository.findFirstByGuidIs(guid);
        if(maybeGeneratorIdentity.isPresent() && maybeGeneratorIdentity.get().getOwner() != null) {
            return false;
        }

        OwnerIdentity ownerIdentity = ownerIdentityService.findOrCreateUser(userService.getUser());
        save(findOrCreateOneByGuid(guid).owner(ownerIdentity));

        return true;
    }
}
