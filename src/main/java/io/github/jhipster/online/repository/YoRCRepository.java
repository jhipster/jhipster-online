package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the YoRC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YoRCRepository extends JpaRepository<YoRC, Long> {

    @Query("select yo_rc from YoRC yo_rc where yo_rc.owner.login = ?#{principal.username}")
    List<YoRC> findByOwnerIsCurrentUser();

    @Query(value = "select distinct yo_rc from YoRC yo_rc left join fetch yo_rc.languages",
        countQuery = "select count(distinct yo_rc) from YoRC yo_rc")
    Page<YoRC> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct yo_rc from YoRC yo_rc left join fetch yo_rc.languages")
    List<YoRC> findAllWithEagerRelationships();

    @Query("select yo_rc from YoRC yo_rc left join fetch yo_rc.languages where yo_rc.id =:id")
    Optional<YoRC> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select count(yo_rc.id) from YoRC yo_rc group by yo_rc.owner")
    List<Long> findYoRCCountForEachUser();

}
