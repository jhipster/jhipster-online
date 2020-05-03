package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the YoRC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YoRCRepository extends JpaRepository<YoRC, Long> {
}
