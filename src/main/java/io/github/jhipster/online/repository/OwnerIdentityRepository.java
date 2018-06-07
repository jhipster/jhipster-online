package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the OwnerIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerIdentityRepository extends JpaRepository<OwnerIdentity, Long> {

    Optional<OwnerIdentity> findByOwner(User user);
}
