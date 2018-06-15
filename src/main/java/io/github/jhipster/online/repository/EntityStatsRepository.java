package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.EntityStats;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the EntityStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityStatsRepository extends JpaRepository<EntityStats, Long> {

}
