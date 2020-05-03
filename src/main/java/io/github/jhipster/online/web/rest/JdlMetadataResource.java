package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.service.JdlMetadataService;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.online.domain.JdlMetadata}.
 */
@RestController
@RequestMapping("/api")
public class JdlMetadataResource {

    private final Logger log = LoggerFactory.getLogger(JdlMetadataResource.class);

    private static final String ENTITY_NAME = "jdlMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JdlMetadataService jdlMetadataService;

    public JdlMetadataResource(JdlMetadataService jdlMetadataService) {
        this.jdlMetadataService = jdlMetadataService;
    }

    /**
     * {@code POST  /jdl-metadata} : Create a new jdlMetadata.
     *
     * @param jdlMetadata the jdlMetadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jdlMetadata, or with status {@code 400 (Bad Request)} if the jdlMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jdl-metadata")
    public ResponseEntity<JdlMetadata> createJdlMetadata(@Valid @RequestBody JdlMetadata jdlMetadata) throws URISyntaxException {
        log.debug("REST request to save JdlMetadata : {}", jdlMetadata);
        if (jdlMetadata.getId() != null) {
            throw new BadRequestAlertException("A new jdlMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JdlMetadata result = jdlMetadataService.save(jdlMetadata);
        return ResponseEntity.created(new URI("/api/jdl-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jdl-metadata} : Updates an existing jdlMetadata.
     *
     * @param jdlMetadata the jdlMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jdlMetadata,
     * or with status {@code 400 (Bad Request)} if the jdlMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jdlMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jdl-metadata")
    public ResponseEntity<JdlMetadata> updateJdlMetadata(@Valid @RequestBody JdlMetadata jdlMetadata) throws URISyntaxException {
        log.debug("REST request to update JdlMetadata : {}", jdlMetadata);
        if (jdlMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JdlMetadata result = jdlMetadataService.save(jdlMetadata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jdlMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jdl-metadata} : get all the jdlMetadata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jdlMetadata in body.
     */
    @GetMapping("/jdl-metadata")
    public List<JdlMetadata> getAllJdlMetadata() {
        log.debug("REST request to get all JdlMetadata");
        return jdlMetadataService.findAll();
    }

    /**
     * {@code GET  /jdl-metadata/:id} : get the "id" jdlMetadata.
     *
     * @param id the id of the jdlMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jdlMetadata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jdl-metadata/{id}")
    public ResponseEntity<JdlMetadata> getJdlMetadata(@PathVariable Long id) {
        log.debug("REST request to get JdlMetadata : {}", id);
        Optional<JdlMetadata> jdlMetadata = jdlMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jdlMetadata);
    }

    /**
     * {@code DELETE  /jdl-metadata/:id} : delete the "id" jdlMetadata.
     *
     * @param id the id of the jdlMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jdl-metadata/{id}")
    public ResponseEntity<Void> deleteJdlMetadata(@PathVariable Long id) {
        log.debug("REST request to delete JdlMetadata : {}", id);
        jdlMetadataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
