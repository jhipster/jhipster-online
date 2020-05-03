package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.GeneratorIdentity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GeneratorIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneratorIdentityRepository extends JpaRepository<GeneratorIdentity, Long> {

    @Query("select generator_identity from GeneratorIdentity generator_identity where generator_identity.owner.login = ?#{principal.username}")
    List<GeneratorIdentity> findByOwnerIsCurrentUser();

}
