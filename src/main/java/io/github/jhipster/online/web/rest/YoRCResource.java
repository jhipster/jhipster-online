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

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.dto.YoRCDTO;
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
 * REST controller for managing YoRC.
 */
@RestController
@RequestMapping("/api")
public class YoRCResource {

    private final Logger log = LoggerFactory.getLogger(YoRCResource.class);

    private static final String ENTITY_NAME = "yoRC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final YoRCService yoRCService;

    public YoRCResource(YoRCService yoRCService) {
        this.yoRCService = yoRCService;
    }

    /**
     * POST  /yo-rcs : Create a new yoRC.
     *
     * @param yoRC the yoRC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yoRC, or with status 400 (Bad Request) if the yoRC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/yo-rcs")
    public ResponseEntity<YoRCDTO> createYoRC(@RequestBody YoRCDTO yoRC) throws URISyntaxException {
        log.debug("REST request to save YoRC : {}", yoRC);
        if (yoRC.getId() != null) {
            throw new BadRequestAlertException("A new yoRC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YoRCDTO result = yoRCService.save(yoRC);
        return ResponseEntity
            .created(new URI("/api/yo-rcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yo-rcs : Updates an existing yoRC.
     *
     * @param yoRC the yoRC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yoRC,
     * or with status 400 (Bad Request) if the yoRC is not valid,
     * or with status 500 (Internal Server Error) if the yoRC couldn't be updated
     */
    @PutMapping("/yo-rcs")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<YoRCDTO> updateYoRC(@RequestBody YoRCDTO yoRC) {
        log.debug("REST request to update YoRC : {}", yoRC);
        if (yoRC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        YoRCDTO result = yoRCService.save(yoRC);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yoRC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yo-rcs : get all the yoRCS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of yoRCS in body
     */
    @GetMapping("/yo-rcs")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<YoRC> getAllYoRCS() {
        log.debug("REST request to get all YoRCS");
        return yoRCService.findAll();
    }

    /**
     * GET  /yo-rcs/:id : get the "id" yoRC.
     *
     * @param id the id of the yoRC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yoRC, or with status 404 (Not Found)
     */
    @GetMapping("/yo-rcs/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<YoRC> getYoRC(@PathVariable Long id) {
        log.debug("REST request to get YoRC : {}", id);
        Optional<YoRC> yoRC = yoRCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yoRC);
    }

    /**
     * DELETE  /yo-rcs/:id : delete the "id" yoRC.
     *
     * @param id the id of the yoRC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/yo-rcs/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteYoRC(@PathVariable Long id) {
        log.debug("REST request to delete YoRC : {}", id);
        yoRCService.delete(id);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
