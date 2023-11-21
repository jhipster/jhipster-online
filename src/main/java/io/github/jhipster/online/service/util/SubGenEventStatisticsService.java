/**
 * Copyright 2017-2023 the original author or authors from the JHipster project.
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

import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.SubGenEvent_;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.service.dto.RawSQLField;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Service;

@Service
public class SubGenEventStatisticsService {

    private final EntityManager entityManager;

    public SubGenEventStatisticsService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TemporalCountDTO> getFieldCount(Instant after, SubGenEventType field, TemporalValueType dbTemporalFunction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RawSQLField> query = builder.createQuery(RawSQLField.class);
        Root<SubGenEvent> root = query.from(SubGenEvent.class);
        ParameterExpression<Instant> dateParameter = builder.parameter(Instant.class, QueryUtil.DATE);
        ParameterExpression<String> typeParameter = builder.parameter(String.class, QueryUtil.TYPE);

        query
            .select(
                builder.construct(
                    RawSQLField.class,
                    root.get(dbTemporalFunction.getFieldName()),
                    root.get(SubGenEvent_.type),
                    builder.count(root)
                )
            )
            .where(
                builder.greaterThan(root.get(SubGenEvent_.date).as(Instant.class), dateParameter),
                builder.equal(root.get(SubGenEvent_.type), typeParameter)
            )
            .groupBy(root.get(SubGenEvent_.type), root.get(dbTemporalFunction.getFieldName()));

        return entityManager
            .createQuery(query)
            .setParameter(QueryUtil.DATE, after)
            .setParameter(QueryUtil.TYPE, field.getDatabaseValue())
            .getResultList()
            .stream()
            .map(entry ->
                new TemporalCountDTO(
                    TemporalValueType.absoluteMomentToInstant(entry.getMoment().longValue(), dbTemporalFunction),
                    entry.getCount()
                )
            )
            .collect(Collectors.toList());
    }

    public List<TemporalDistributionDTO> getDeploymentToolDistribution(Instant after, TemporalValueType dbTemporalFunction) {
        Map<SubGenEventType, List<TemporalCountDTO>> entries = Arrays
            .stream(SubGenEventType.getDeploymentTools())
            .map(deploymentTool -> new AbstractMap.SimpleEntry<>(deploymentTool, getFieldCount(after, deploymentTool, dbTemporalFunction)))
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        List<TemporalDistributionDTO> result = new ArrayList<>();
        for (Map.Entry<SubGenEventType, List<TemporalCountDTO>> entry : entries.entrySet()) {
            for (TemporalCountDTO count : entry.getValue()) {
                Optional<TemporalDistributionDTO> maybeDistribution = result
                    .stream()
                    .filter(td -> td.getDate().equals(count.getDate()))
                    .findFirst();
                TemporalDistributionDTO distributionDTO;
                if (maybeDistribution.isPresent()) {
                    distributionDTO = maybeDistribution.get();
                } else {
                    distributionDTO = new TemporalDistributionDTO(count.getDate());
                    result.add(distributionDTO);
                }

                distributionDTO.getValues().put(entry.getKey().getDatabaseValue(), count.getCount());
            }
        }

        return result;
    }
}