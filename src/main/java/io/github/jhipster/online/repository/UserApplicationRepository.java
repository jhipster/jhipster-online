package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.UserApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

}
