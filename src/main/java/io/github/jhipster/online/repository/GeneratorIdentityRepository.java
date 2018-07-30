package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.User;
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
    Optional<GeneratorIdentity> findFirstByGuidEquals(String guid);

    List<GeneratorIdentity> findAllByOwner(User owner);

    @Query("select generator_identity from GeneratorIdentity generator_identity where generator_identity.owner.login = ?#{principal.username}")
    List<GeneratorIdentity> findByOwnerIsCurrentUser();
}
