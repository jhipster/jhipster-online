package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;
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

    long countAllByCreationDate(Instant creationDate);

    List<YoRC> findAllByOrderByCreationDateAsc();
}
