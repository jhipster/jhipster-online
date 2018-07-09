package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.OwnerIdentity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the GeneratorIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneratorIdentityRepository extends JpaRepository<GeneratorIdentity, Long> {

    Optional<GeneratorIdentity> findFirstByGuidIs(String guid);

    List<GeneratorIdentity> findAllByOwner(OwnerIdentity owner);
}
