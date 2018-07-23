package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the YoRC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YoRCRepository extends JpaRepository<YoRC, Long> {

}
