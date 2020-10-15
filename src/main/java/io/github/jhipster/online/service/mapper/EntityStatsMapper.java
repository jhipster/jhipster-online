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
