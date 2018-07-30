package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.domain.GeneratorIdentity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntityStats entity.
 */
@Repository
public interface EntityStatsRepository extends JpaRepository<EntityStats, Long> {
    void deleteAllByOwner(GeneratorIdentity ownerIdentity);
}
