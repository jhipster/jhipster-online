package io.github.jhipster.online.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.service.OwnerIdentityService;
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
 * REST controller for managing OwnerIdentity.
 */
@RestController
@RequestMapping("/api")
public class OwnerIdentityResource {

    private final Logger log = LoggerFactory.getLogger(OwnerIdentityResource.class);

    private static final String ENTITY_NAME = "ownerIdentity";

    private final OwnerIdentityService ownerIdentityService;

    public OwnerIdentityResource(OwnerIdentityService ownerIdentityService) {
        this.ownerIdentityService = ownerIdentityService;
    }

    /**
     * POST  /owner-identities : Create a new ownerIdentity.
     *
     * @param ownerIdentity the ownerIdentity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ownerIdentity, or with status 400 (Bad Request) if the ownerIdentity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-identities")
    @Timed
    public ResponseEntity<OwnerIdentity> createOwnerIdentity(@RequestBody OwnerIdentity ownerIdentity) throws URISyntaxException {
        log.debug("REST request to save OwnerIdentity : {}", ownerIdentity);
        if (ownerIdentity.getId() != null) {
            throw new BadRequestAlertException("A new ownerIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        OwnerIdentity result = ownerIdentityService.save(ownerIdentity);
        return ResponseEntity.created(new URI("/api/owner-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-identities : Updates an existing ownerIdentity.
     *
     * @param ownerIdentity the ownerIdentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ownerIdentity,
     * or with status 400 (Bad Request) if the ownerIdentity is not valid,
     * or with status 500 (Internal Server Error) if the ownerIdentity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-identities")
    @Timed
    public ResponseEntity<OwnerIdentity> updateOwnerIdentity(@RequestBody OwnerIdentity ownerIdentity) throws URISyntaxException {
        log.debug("REST request to update OwnerIdentity : {}", ownerIdentity);
        if (ownerIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        OwnerIdentity result = ownerIdentityService.save(ownerIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ownerIdentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-identities : get all the ownerIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ownerIdentities in body
     */
    @GetMapping("/owner-identities")
    @Timed
    public List<OwnerIdentity> getAllOwnerIdentities() {
        log.debug("REST request to get all OwnerIdentities");
        return ownerIdentityService.findAll();
    }

    /**
     * GET  /owner-identities/:id : get the "id" ownerIdentity.
     *
     * @param id the id of the ownerIdentity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ownerIdentity, or with status 404 (Not Found)
     */
    @GetMapping("/owner-identities/{id}")
    @Timed
    public ResponseEntity<OwnerIdentity> getOwnerIdentity(@PathVariable Long id) {
        log.debug("REST request to get OwnerIdentity : {}", id);
        Optional<OwnerIdentity> ownerIdentity = ownerIdentityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ownerIdentity);
    }

    /**
     * DELETE  /owner-identities/:id : delete the "id" ownerIdentity.
     *
     * @param id the id of the ownerIdentity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-identities/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwnerIdentity(@PathVariable Long id) {
        log.debug("REST request to delete OwnerIdentity : {}", id);
        ownerIdentityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
