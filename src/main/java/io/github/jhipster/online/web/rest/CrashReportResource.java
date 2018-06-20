package io.github.jhipster.online.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.online.domain.CrashReport;
import io.github.jhipster.online.repository.CrashReportRepository;
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
 * REST controller for managing CrashReport.
 */
@RestController
@RequestMapping("/api")
public class CrashReportResource {

    private final Logger log = LoggerFactory.getLogger(CrashReportResource.class);

    private static final String ENTITY_NAME = "crashReport";

    private final CrashReportRepository crashReportRepository;

    public CrashReportResource(CrashReportRepository crashReportRepository) {
        this.crashReportRepository = crashReportRepository;
    }

    /**
     * POST  /crash-reports : Create a new crashReport.
     *
     * @param crashReport the crashReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crashReport, or with status 400 (Bad Request) if the crashReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crash-reports")
    @Timed
    public ResponseEntity<CrashReport> createCrashReport(@RequestBody CrashReport crashReport) throws URISyntaxException {
        log.debug("REST request to save CrashReport : {}", crashReport);
        if (crashReport.getId() != null) {
            throw new BadRequestAlertException("A new crashReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrashReport result = crashReportRepository.save(crashReport);
        return ResponseEntity.created(new URI("/api/crash-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crash-reports : Updates an existing crashReport.
     *
     * @param crashReport the crashReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crashReport,
     * or with status 400 (Bad Request) if the crashReport is not valid,
     * or with status 500 (Internal Server Error) if the crashReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crash-reports")
    @Timed
    public ResponseEntity<CrashReport> updateCrashReport(@RequestBody CrashReport crashReport) throws URISyntaxException {
        log.debug("REST request to update CrashReport : {}", crashReport);
        if (crashReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CrashReport result = crashReportRepository.save(crashReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crashReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crash-reports : get all the crashReports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of crashReports in body
     */
    @GetMapping("/crash-reports")
    @Timed
    public List<CrashReport> getAllCrashReports() {
        log.debug("REST request to get all CrashReports");
        return crashReportRepository.findAll();
    }

    /**
     * GET  /crash-reports/:id : get the "id" crashReport.
     *
     * @param id the id of the crashReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crashReport, or with status 404 (Not Found)
     */
    @GetMapping("/crash-reports/{id}")
    @Timed
    public ResponseEntity<CrashReport> getCrashReport(@PathVariable Long id) {
        log.debug("REST request to get CrashReport : {}", id);
        Optional<CrashReport> crashReport = crashReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(crashReport);
    }

    /**
     * DELETE  /crash-reports/:id : delete the "id" crashReport.
     *
     * @param id the id of the crashReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crash-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrashReport(@PathVariable Long id) {
        log.debug("REST request to delete CrashReport : {}", id);

        crashReportRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
