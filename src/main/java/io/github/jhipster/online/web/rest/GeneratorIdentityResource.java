package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.service.GeneratorIdentityService;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.online.domain.GeneratorIdentity}.
 */
@RestController
@RequestMapping("/api")
public class GeneratorIdentityResource {

    private final Logger log = LoggerFactory.getLogger(GeneratorIdentityResource.class);

    private static final String ENTITY_NAME = "generatorIdentity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeneratorIdentityService generatorIdentityService;

    public GeneratorIdentityResource(GeneratorIdentityService generatorIdentityService) {
        this.generatorIdentityService = generatorIdentityService;
    }

    /**
     * {@code POST  /generator-identities} : Create a new generatorIdentity.
     *
     * @param generatorIdentity the generatorIdentity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generatorIdentity, or with status {@code 400 (Bad Request)} if the generatorIdentity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generator-identities")
    public ResponseEntity<GeneratorIdentity> createGeneratorIdentity(@RequestBody GeneratorIdentity generatorIdentity) throws URISyntaxException {
        log.debug("REST request to save GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() != null) {
            throw new BadRequestAlertException("A new generatorIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneratorIdentity result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity.created(new URI("/api/generator-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /generator-identities} : Updates an existing generatorIdentity.
     *
     * @param generatorIdentity the generatorIdentity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generatorIdentity,
     * or with status {@code 400 (Bad Request)} if the generatorIdentity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generatorIdentity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/generator-identities")
    public ResponseEntity<GeneratorIdentity> updateGeneratorIdentity(@RequestBody GeneratorIdentity generatorIdentity) throws URISyntaxException {
        log.debug("REST request to update GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeneratorIdentity result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generatorIdentity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /generator-identities} : get all the generatorIdentities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generatorIdentities in body.
     */
    @GetMapping("/generator-identities")
    public List<GeneratorIdentity> getAllGeneratorIdentities() {
        log.debug("REST request to get all GeneratorIdentities");
        return generatorIdentityService.findAll();
    }

    /**
     * {@code GET  /generator-identities/:id} : get the "id" generatorIdentity.
     *
     * @param id the id of the generatorIdentity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generatorIdentity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generator-identities/{id}")
    public ResponseEntity<GeneratorIdentity> getGeneratorIdentity(@PathVariable Long id) {
        log.debug("REST request to get GeneratorIdentity : {}", id);
        Optional<GeneratorIdentity> generatorIdentity = generatorIdentityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generatorIdentity);
    }

    /**
     * {@code DELETE  /generator-identities/:id} : delete the "id" generatorIdentity.
     *
     * @param id the id of the generatorIdentity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/generator-identities/{id}")
    public ResponseEntity<Void> deleteGeneratorIdentity(@PathVariable Long id) {
        log.debug("REST request to delete GeneratorIdentity : {}", id);
        generatorIdentityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
