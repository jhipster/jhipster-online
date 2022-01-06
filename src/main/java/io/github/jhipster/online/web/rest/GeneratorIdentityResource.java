/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.service.GeneratorIdentityService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.service.dto.GeneratorIdentityDTO;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing GeneratorIdentity.
 */
@RestController
@RequestMapping("/api")
public class GeneratorIdentityResource {

    private final Logger log = LoggerFactory.getLogger(GeneratorIdentityResource.class);

    private static final String ENTITY_NAME = "generatorIdentity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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
    public ResponseEntity<GeneratorIdentityDTO> createGeneratorIdentity(@RequestBody GeneratorIdentityDTO generatorIdentity)
        throws URISyntaxException {
        log.debug("REST request to save GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() != null) {
            throw new BadRequestAlertException("A new generatorIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneratorIdentityDTO result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity
            .created(new URI("/api/generator-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generator-identities : Updates an existing generatorIdentity.
     *
     * @param generatorIdentity the generatorIdentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generatorIdentity,
     * or with status 400 (Bad Request) if the generatorIdentity is not valid,
     * or with status 500 (Internal Server Error) if the generatorIdentity couldn't be updated
     */
    @PutMapping("/generator-identities")
    public ResponseEntity<GeneratorIdentityDTO> updateGeneratorIdentity(@RequestBody GeneratorIdentityDTO generatorIdentity) {
        log.debug("REST request to update GeneratorIdentity : {}", generatorIdentity);
        if (generatorIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeneratorIdentityDTO result = generatorIdentityService.save(generatorIdentity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generatorIdentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generator-identities : get all the generatorIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generatorIdentities in body
     */
    @GetMapping("/generator-identities")
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
    public ResponseEntity<Void> deleteGeneratorIdentity(@PathVariable Long id) {
        log.debug("REST request to delete GeneratorIdentity : {}", id);
        generatorIdentityService.delete(id);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /generator-identities/owned : get all owned generatorIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generatorIdentities in body
     */
    @GetMapping("/generator-identities/owned")
    public List<GeneratorIdentity> getAllOwnedGeneratorIdentities() {
        log.debug("REST request to get all owned GeneratorIdentities");
        return generatorIdentityService.findAllOwned(userService.getUser());
    }
}
