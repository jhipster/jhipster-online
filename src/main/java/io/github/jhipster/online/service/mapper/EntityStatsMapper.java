/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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
package io.github.jhipster.online.service.mapper;

import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.service.dto.EntityStatsDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EntityStats} and its DTO {@link EntityStatsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntityStatsMapper extends EntityMapper<EntityStatsDTO, EntityStats> {
    default EntityStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntityStats entityStats = new EntityStats();
        entityStats.setId(id);
        return entityStats;
    }
}
