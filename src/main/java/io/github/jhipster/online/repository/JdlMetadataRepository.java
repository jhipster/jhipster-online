package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.JdlMetadata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the JdlMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JdlMetadataRepository extends JpaRepository<JdlMetadata, Long> {

    @Query("select jdl_metadata from JdlMetadata jdl_metadata where jdl_metadata.user.login = ?#{principal.username}")
    List<JdlMetadata> findByUserIsCurrentUser();

}
