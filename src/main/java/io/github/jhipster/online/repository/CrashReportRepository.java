package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.CrashReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CrashReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrashReportRepository extends JpaRepository<CrashReport, Long> {

}
