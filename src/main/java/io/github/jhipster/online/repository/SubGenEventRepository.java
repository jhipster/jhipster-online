package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.SubGenEvent;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the SubGenEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubGenEventRepository extends JpaRepository<SubGenEvent, Long> {

}
