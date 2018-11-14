/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import io.github.jhipster.online.service.dto.*;
import io.github.jhipster.online.service.enums.TemporalValueType;

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
                Map<String, Long> values = new TreeMap<>();
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
