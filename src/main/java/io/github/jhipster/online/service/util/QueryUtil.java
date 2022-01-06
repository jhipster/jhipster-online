/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.online.service.util;

import io.github.jhipster.online.service.dto.RawSQL;
import io.github.jhipster.online.service.dto.RawSQLField;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public final class QueryUtil {

    public static final String DATE = "date";

    public static final String TYPE = "type";

    private QueryUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<TemporalDistributionDTO> createDistributionQueryAndCollectData(
        Instant after,
        TemporalValueType dbTemporalFunction,
        CriteriaQuery<RawSQLField> query,
        EntityManager entityManager
    ) {
        return entityManager
            .createQuery(query)
            .setParameter(DATE, after)
            .getResultList()
            .stream()
            .collect(Collectors.groupingBy(RawSQLField::getMoment))
            .entrySet()
            .stream()
            .map(
                entry -> {
                    Instant date = TemporalValueType.absoluteMomentToInstant(entry.getKey().longValue(), dbTemporalFunction);
                    Map<String, Long> values = new TreeMap<>();
                    entry.getValue().forEach(e -> values.put(e.getField(), e.getCount()));
                    return new TemporalDistributionDTO(date, values);
                }
            )
            .collect(Collectors.toList());
    }

    public static List<TemporalCountDTO> createCountQueryAndCollectData(
        Instant after,
        TemporalValueType dbTemporalFunction,
        CriteriaQuery<RawSQL> query,
        EntityManager entityManager
    ) {
        return entityManager
            .createQuery(query)
            .setParameter(DATE, after)
            .getResultList()
            .stream()
            .map(
                item ->
                    new TemporalCountDTO(
                        TemporalValueType.absoluteMomentToInstant(item.getMoment().longValue(), dbTemporalFunction),
                        item.getCount()
                    )
            )
            .sorted(TemporalCountDTO::compareTo)
            .collect(Collectors.toList());
    }
}
