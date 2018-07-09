package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.CrashReport;
import io.github.jhipster.online.repository.CrashReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing CrashReport.
 */
@Service
@Transactional
public class CrashReportService {

    private final Logger log = LoggerFactory.getLogger(CrashReportService.class);

    private final CrashReportRepository crashReportRepository;

    public CrashReportService(CrashReportRepository crashReportRepository) {
        this.crashReportRepository = crashReportRepository;
    }

    /**
     * Save a crashReport.
     *
     * @param crashReport the entity to save
     * @return the persisted entity
     */
    public CrashReport save(CrashReport crashReport) {
        log.debug("Request to save CrashReport : {}", crashReport);        return crashReportRepository.save(crashReport);
    }

    /**
     * Get all the crashReports.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CrashReport> findAll() {
        log.debug("Request to get all CrashReports");
        return crashReportRepository.findAll();
    }


    /**
     * Get one crashReport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CrashReport> findOne(Long id) {
        log.debug("Request to get CrashReport : {}", id);
        return crashReportRepository.findById(id);
    }

    /**
     * Delete the crashReport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CrashReport : {}", id);
        crashReportRepository.deleteById(id);
    }
}
