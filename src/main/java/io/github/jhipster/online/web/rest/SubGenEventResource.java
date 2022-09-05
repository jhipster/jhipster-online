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

import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.SubGenEventService;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing SubGenEvent.
 */
@RestController
@RequestMapping("/api")
public class SubGenEventResource {

    private final Logger log = LoggerFactory.getLogger(SubGenEventResource.class);

    private static final String ENTITY_NAME = "subGenEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubGenEventService subGenEventService;

    public SubGenEventResource(SubGenEventService subGenEventService) {
        this.subGenEventService = subGenEventService;
    }

    /**
     * POST  /sub-gen-events : Create a new subGenEvent.
     *
     * @param subGenEvent the subGenEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subGenEvent, or with status 400 (Bad Request) if the subGenEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-gen-events")
    public ResponseEntity<SubGenEventDTO> createSubGenEvent(@RequestBody SubGenEventDTO subGenEvent) throws URISyntaxException {
        log.debug("REST request to save SubGenEvent : {}", subGenEvent);
        if (subGenEvent.getId() != null) {
            throw new BadRequestAlertException("A new subGenEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubGenEventDTO result = subGenEventService.save(subGenEvent);
        return ResponseEntity
            .created(new URI("/api/sub-gen-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-gen-events : get all the subGenEvents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subGenEvents in body
     */
    @GetMapping("/sub-gen-events")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<SubGenEvent> getAllSubGenEvents() {
        log.debug("REST request to get all SubGenEvents");
        return subGenEventService.findAll();
    }

    /**
     * PUT  /sub-gen-events : Updates an existing subGenEvent.
     *
     * @param subGenEvent the subGenEvent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subGenEvent,
     * or with status 400 (Bad Request) if the subGenEvent is not valid,
     * or with status 500 (Internal Server Error) if the subGenEvent couldn't be updated
     */
    @PutMapping("/sub-gen-events")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<SubGenEventDTO> updateSubGenEvent(@RequestBody SubGenEventDTO subGenEvent) {
        log.debug("REST request to update SubGenEvent : {}", subGenEvent);
        if (subGenEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubGenEventDTO result = subGenEventService.save(subGenEvent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subGenEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-gen-events/:id : get the "id" subGenEvent.
     *
     * @param id the id of the subGenEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subGenEvent, or with status 404 (Not Found)
     */
    @GetMapping("/sub-gen-events/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<SubGenEvent> getSubGenEvent(@PathVariable Long id) {
        log.debug("REST request to get SubGenEvent : {}", id);
        Optional<SubGenEvent> subGenEvent = subGenEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subGenEvent);
    }

    /**
     * DELETE  /sub-gen-events/:id : delete the "id" subGenEvent.
     *
     * @param id the id of the subGenEvent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-gen-events/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteSubGenEvent(@PathVariable Long id) {
        log.debug("REST request to delete SubGenEvent : {}", id);
        subGenEventService.delete(id);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
