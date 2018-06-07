package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YoRC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YoRCRepository extends JpaRepository<YoRC, Long> {

    @Query("select count(yo_rc.id) from YoRC yo_rc group by yo_rc.owner")
    List<Long> findYoRCCountForEachUser();
}
