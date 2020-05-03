package io.github.jhipster.online.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.online.domain.UserApplication;
import io.github.jhipster.online.service.UserApplicationService;
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
 * REST controller for managing UserApplication.
 */
@RestController
@RequestMapping("/api")
public class UserApplicationResource {

    private final Logger log = LoggerFactory.getLogger(UserApplicationResource.class);

    private static final String ENTITY_NAME = "userApplication";

    private final UserApplicationService userApplicationService;

    public UserApplicationResource(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * POST  /user-applications : Create a new userApplication.
     *
     * @param userApplication the userApplication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userApplication, or with status 400 (Bad Request) if the userApplication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-applications")
    @Timed
    public ResponseEntity<UserApplication> createUserApplication(@RequestBody UserApplication userApplication) throws URISyntaxException {
        log.debug("REST request to save UserApplication : {}", userApplication);
        if (userApplication.getId() != null) {
            throw new BadRequestAlertException("A new userApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApplication result = userApplicationService.save(userApplication);
        return ResponseEntity.created(new URI("/api/user-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-applications : Updates an existing userApplication.
     *
     * @param userApplication the userApplication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userApplication,
     * or with status 400 (Bad Request) if the userApplication is not valid,
     * or with status 500 (Internal Server Error) if the userApplication couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-applications")
    @Timed
    public ResponseEntity<UserApplication> updateUserApplication(@RequestBody UserApplication userApplication) throws URISyntaxException {
        log.debug("REST request to update UserApplication : {}", userApplication);
        if (userApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApplication result = userApplicationService.save(userApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-applications : get all the userApplications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userApplications in body
     */
    @GetMapping("/user-applications")
    @Timed
    public List<UserApplication> getAllUserApplications() {
        log.debug("REST request to get all UserApplications");
        return userApplicationService.findAll();
    }

    /**
     * GET  /user-applications/:id : get the "id" userApplication.
     *
     * @param id the id of the userApplication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userApplication, or with status 404 (Not Found)
     */
    @GetMapping("/user-applications/{id}")
    @Timed
    public ResponseEntity<UserApplication> getUserApplication(@PathVariable Long id) {
        log.debug("REST request to get UserApplication : {}", id);
        Optional<UserApplication> userApplication = userApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApplication);
    }

    /**
     * DELETE  /user-applications/:id : delete the "id" userApplication.
     *
     * @param id the id of the userApplication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-applications/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserApplication(@PathVariable Long id) {
        log.debug("REST request to delete UserApplication : {}", id);
        userApplicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
