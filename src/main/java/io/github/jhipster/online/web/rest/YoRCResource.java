package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.service.YoRCService;
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
 * REST controller for managing {@link io.github.jhipster.online.domain.YoRC}.
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
     * {@code POST  /yo-rcs} : Create a new yoRC.
     *
     * @param yoRC the yoRC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new yoRC, or with status {@code 400 (Bad Request)} if the yoRC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/yo-rcs")
    public ResponseEntity<YoRC> createYoRC(@RequestBody YoRC yoRC) throws URISyntaxException {
        log.debug("REST request to save YoRC : {}", yoRC);
        if (yoRC.getId() != null) {
            throw new BadRequestAlertException("A new yoRC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        YoRC result = yoRCService.save(yoRC);
        return ResponseEntity.created(new URI("/api/yo-rcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /yo-rcs} : Updates an existing yoRC.
     *
     * @param yoRC the yoRC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated yoRC,
     * or with status {@code 400 (Bad Request)} if the yoRC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the yoRC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/yo-rcs")
    public ResponseEntity<YoRC> updateYoRC(@RequestBody YoRC yoRC) throws URISyntaxException {
        log.debug("REST request to update YoRC : {}", yoRC);
        if (yoRC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        YoRC result = yoRCService.save(yoRC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, yoRC.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /yo-rcs} : get all the yoRCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of yoRCS in body.
     */
    @GetMapping("/yo-rcs")
    public List<YoRC> getAllYoRCS() {
        log.debug("REST request to get all YoRCS");
        return yoRCService.findAll();
    }

    /**
     * {@code GET  /yo-rcs/:id} : get the "id" yoRC.
     *
     * @param id the id of the yoRC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the yoRC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/yo-rcs/{id}")
    public ResponseEntity<YoRC> getYoRC(@PathVariable Long id) {
        log.debug("REST request to get YoRC : {}", id);
        Optional<YoRC> yoRC = yoRCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(yoRC);
    }

    /**
     * {@code DELETE  /yo-rcs/:id} : delete the "id" yoRC.
     *
     * @param id the id of the yoRC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/yo-rcs/{id}")
    public ResponseEntity<Void> deleteYoRC(@PathVariable Long id) {
        log.debug("REST request to delete YoRC : {}", id);
        yoRCService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
