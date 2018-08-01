package io.github.jhipster.online.service.util;

import io.github.jhipster.online.service.dto.RawSQL;
import io.github.jhipster.online.service.dto.RawSQLField;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class QueryUtil {

    public static final String DATE = "date";
    public static final String TYPE = "type";

    public static List<TemporalDistributionDTO> createDistributionQueryAndCollectData(Instant after, TemporalValueType dbTemporalFunction, CriteriaQuery<RawSQLField> query, EntityManager entityManager) {
        return entityManager
            .createQuery(query)
            .setParameter(DATE, after)
            .getResultList()
            .stream()
            .collect(Collectors.groupingBy(RawSQLField::getMoment))
            .entrySet()
            .stream()
            .map(entry -> {
                LocalDateTime localDate = TemporalValueType.absoluteMomentToLocalDateTime(entry.getKey().longValue(), dbTemporalFunction);
                Map<String, Long> values = new HashMap<>();
                entry.getValue().forEach(e -> values.put(e.getField(), e.getCount()));
                return new TemporalDistributionDTO(localDate, values);
            }).collect(Collectors.toList());
    }

    public static List<TemporalCountDTO> createCountQueryAndCollectData(Instant after, TemporalValueType dbTemporalFunction, CriteriaQuery<RawSQL> query, EntityManager entityManager) {
        return entityManager
            .createQuery(query)
            .setParameter(QueryUtil.DATE, after)
            .getResultList()
            .stream()
            .map(item ->
                new TemporalCountDTO(TemporalValueType.absoluteMomentToLocalDateTime(item.getMoment().longValue(), dbTemporalFunction), item.getCount()))
            .sorted(TemporalCountDTO::compareTo)
            .collect(Collectors.toList());
    }
}
