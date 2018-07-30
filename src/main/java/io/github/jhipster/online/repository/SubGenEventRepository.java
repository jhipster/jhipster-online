package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.SubGenEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubGenEvent entity.
 */
@Repository
public interface SubGenEventRepository extends JpaRepository<SubGenEvent, Long> {
    void deleteAllByOwner(GeneratorIdentity owner);
}
