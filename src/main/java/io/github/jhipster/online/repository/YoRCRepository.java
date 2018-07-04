package io.github.jhipster.online.repository;

import io.github.jhipster.online.domain.YoRC;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the YoRC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YoRCRepository extends JpaRepository<YoRC, Long> {

    @Query("select year(yorc.creationDate), count(yorc) " +
        "from YoRC yorc " +
        "group by year(yorc.creationDate) " +
        "order by year(yorc.creationDate) asc")
    List<Object[]> countAllByYear();

    @Query("select extract(year_month from yorc.creationDate), count(yorc) " +
        "from YoRC yorc " +
        "where yorc.creationDate >= :date " +
        "group by extract(year_month " +
        "from yorc.creationDate) " +
        "order by extract(year_month " +
        "from yorc.creationDate) asc")
    List<Object[]> countAllByMonth(@Param("date") Instant date);

    @Query("select date(yorc.creationDate), count(yorc) " +
        "from YoRC yorc " +
        "where yorc.creationDate >= :date " +
        "group by date(yorc.creationDate) " +
        "order by date(yorc.creationDate) asc")
    List<Object[]> countAllByDay(@Param("date") Instant date);

    @Query("select extract(year_month from yorc.creationDate), yorc.clientFramework, count(yorc)" +
        "from YoRC yorc " +
        "where yorc.creationDate >= :date " +
        "group by extract(year_month from yorc.creationDate), yorc.clientFramework " +
        "order by extract(year_month from yorc.creationDate), yorc.clientFramework asc")
    List<Object[]> countAllByClientFrameworkByMonth(@Param("date") Instant date);
}
