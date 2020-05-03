package io.github.jhipster.online.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.service.JdlMetadataService;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.online.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JdlMetadata.
 */
@RestController
@RequestMapping("/api")
public class JdlMetadataResource {

    private final Logger log = LoggerFactory.getLogger(JdlMetadataResource.class);

    private static final String ENTITY_NAME = "jdlMetadata";

    private final JdlMetadataService jdlMetadataService;

    public JdlMetadataResource(JdlMetadataService jdlMetadataService) {
        this.jdlMetadataService = jdlMetadataService;
    }

    /**
     * POST  /jdl-metadata : Create a new jdlMetadata.
     *
     * @param jdlMetadata the jdlMetadata to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jdlMetadata, or with status 400 (Bad Request) if the jdlMetadata has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jdl-metadata")
    @Timed
    public ResponseEntity<JdlMetadata> createJdlMetadata(@Valid @RequestBody JdlMetadata jdlMetadata) throws URISyntaxException {
        log.debug("REST request to save JdlMetadata : {}", jdlMetadata);
        if (jdlMetadata.getId() != null) {
            throw new BadRequestAlertException("A new jdlMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JdlMetadata result = jdlMetadataService.save(jdlMetadata);
        return ResponseEntity.created(new URI("/api/jdl-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jdl-metadata : Updates an existing jdlMetadata.
     *
     * @param jdlMetadata the jdlMetadata to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jdlMetadata,
     * or with status 400 (Bad Request) if the jdlMetadata is not valid,
     * or with status 500 (Internal Server Error) if the jdlMetadata couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jdl-metadata")
    @Timed
    public ResponseEntity<JdlMetadata> updateJdlMetadata(@Valid @RequestBody JdlMetadata jdlMetadata) throws URISyntaxException {
        log.debug("REST request to update JdlMetadata : {}", jdlMetadata);
        if (jdlMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JdlMetadata result = jdlMetadataService.save(jdlMetadata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jdlMetadata.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jdl-metadata : get all the jdlMetadata.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jdlMetadata in body
     */
    @GetMapping("/jdl-metadata")
    @Timed
    public List<JdlMetadata> getAllJdlMetadata() {
        log.debug("REST request to get all JdlMetadata");
        return jdlMetadataService.findAll();
    }

    /**
     * GET  /jdl-metadata/:id : get the "id" jdlMetadata.
     *
     * @param id the id of the jdlMetadata to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jdlMetadata, or with status 404 (Not Found)
     */
    @GetMapping("/jdl-metadata/{id}")
    @Timed
    public ResponseEntity<JdlMetadata> getJdlMetadata(@PathVariable Long id) {
        log.debug("REST request to get JdlMetadata : {}", id);
        Optional<JdlMetadata> jdlMetadata = jdlMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jdlMetadata);
    }

    /**
     * DELETE  /jdl-metadata/:id : delete the "id" jdlMetadata.
     *
     * @param id the id of the jdlMetadata to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jdl-metadata/{id}")
    @Timed
    public ResponseEntity<Void> deleteJdlMetadata(@PathVariable Long id) {
        log.debug("REST request to delete JdlMetadata : {}", id);
        jdlMetadataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
