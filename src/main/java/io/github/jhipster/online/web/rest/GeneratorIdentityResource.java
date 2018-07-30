package io.github.jhipster.online.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.service.GeneratorIdentityService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.online.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GeneratorIdentity.
 */
@RestController
@RequestMapping("/api")
public class GeneratorIdentityResource {

    private final Logger log = LoggerFactory.getLogger(GeneratorIdentityResource.class);

    private static final String ENTITY_NAME = "generatorIdentity";

    private final GeneratorIdentityService generatorIdentityService;

    private final UserService userService;

    public GeneratorIdentityResource(GeneratorIdentityService generatorIdentityService, UserService userService) {
        this.generatorIdentityService = generatorIdentityService;
        this.userService = userService;
    }

    /**
     * POST  /generator-identities : Create a new generatorIdentity.
     *
     * @param generatorIdentity the generatorIdentity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new generatorIdentity, or with status 400 (Bad Request) if the generatorIdentity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generator-identities")
    @Timed
    public ResponseEntity<GeneratorIdentity> createGeneratorIdentity(@RequestBody GeneratorIdentity generatorIdentity) throws URISyntaxException {
        log.debug("REST request to save GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() != null) {
            throw new BadRequestAlertException("A new generatorIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneratorIdentity result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity.created(new URI("/api/generator-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generator-identities : Updates an existing generatorIdentity.
     *
     * @param generatorIdentity the generatorIdentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generatorIdentity,
     * or with status 400 (Bad Request) if the generatorIdentity is not valid,
     * or with status 500 (Internal Server Error) if the generatorIdentity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generator-identities")
    @Timed
    public ResponseEntity<GeneratorIdentity> updateGeneratorIdentity(@RequestBody GeneratorIdentity generatorIdentity) throws URISyntaxException {
        log.debug("REST request to update GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeneratorIdentity result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, generatorIdentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generator-identities : get all the generatorIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generatorIdentities in body
     */
    @GetMapping("/generator-identities")
    @Timed
    public List<GeneratorIdentity> getAllGeneratorIdentities() {
        log.debug("REST request to get all GeneratorIdentities");
        return generatorIdentityService.findAll();
    }

    /**
     * GET  /generator-identities/:id : get the "id" generatorIdentity.
     *
     * @param id the id of the generatorIdentity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the generatorIdentity, or with status 404 (Not Found)
     */
    @GetMapping("/generator-identities/{id}")
    @Timed
    public ResponseEntity<GeneratorIdentity> getGeneratorIdentity(@PathVariable Long id) {
        log.debug("REST request to get GeneratorIdentity : {}", id);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generatorIdentity);
    }

    /**
     * DELETE  /generator-identities/:id : delete the "id" generatorIdentity.
     *
     * @param id the id of the generatorIdentity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generator-identities/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeneratorIdentity(@PathVariable Long id) {
        log.debug("REST request to delete GeneratorIdentity : {}", id);
        generatorIdentityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /generator-identities/owned : get all owned generatorIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generatorIdentities in body
     */
    @GetMapping("/generator-identities/owned")
    @Timed
    public List<GeneratorIdentity> getAllOwnedGeneratorIdentities() {
        log.debug("REST request to get all owned GeneratorIdentities");
        return generatorIdentityService.findAllOwned(userService.getUser());
    }

}
