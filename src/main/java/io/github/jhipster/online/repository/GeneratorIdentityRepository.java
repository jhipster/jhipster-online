package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.GeneratorIdentity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the GeneratorIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneratorIdentityRepository extends JpaRepository<GeneratorIdentity, Long> {
    Optional<GeneratorIdentity> findFirstByGuidIs(String guid);
}
