/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
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

import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.EntityStatsService;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.online.domain.EntityStats}.
 */
@RestController
@RequestMapping("/api")
public class EntityStatsResource {

    private final Logger log = LoggerFactory.getLogger(EntityStatsResource.class);

    private static final String ENTITY_NAME = "entityStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityStatsService entityStatsService;

    public EntityStatsResource(EntityStatsService entityStatsService) {
        this.entityStatsService = entityStatsService;
    }

    /**
     * {@code POST  /entity-stats} : Create a new entityStats.
     *
     * @param entityStats the entityStats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityStats, or with status {@code 400 (Bad Request)} if the entityStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-stats")
    public ResponseEntity<EntityStats> createEntityStats(@RequestBody EntityStats entityStats) throws URISyntaxException {
        log.debug("REST request to save EntityStats : {}", entityStats);
        if (entityStats.getId() != null) {
            throw new BadRequestAlertException("A new entityStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityStats result = entityStatsService.save(entityStats);
        return ResponseEntity.created(new URI("/api/entity-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-stats} : Updates an existing entityStats.
     *
     * @param entityStats the entityStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityStats,
     * or with status {@code 400 (Bad Request)} if the entityStats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityStats couldn't be updated.
     */
    @PutMapping("/entity-stats")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<EntityStats> updateEntityStats(@RequestBody EntityStats entityStats) {
        log.debug("REST request to update EntityStats : {}", entityStats);
        if (entityStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityStats result = entityStatsService.save(entityStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entityStats.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-stats} : get all the entityStats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityStats in body.
     */
    @GetMapping("/entity-stats")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<EntityStats> getAllEntityStats() {
        log.debug("REST request to get all EntityStats");
        return entityStatsService.findAll();
    }

    /**
     * {@code GET  /entity-stats/:id} : get the "id" entityStats.
     *
     * @param id the id of the entityStats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityStats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-stats/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<EntityStats> getEntityStats(@PathVariable Long id) {
        log.debug("REST request to get EntityStats : {}", id);
        Optional<EntityStats> entityStats = entityStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityStats);
    }

    /**
     * {@code DELETE  /entity-stats/:id} : delete the "id" entityStats.
     *
     * @param id the id of the entityStats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-stats/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteEntityStats(@PathVariable Long id) {
        log.debug("REST request to delete EntityStats : {}", id);
        entityStatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
