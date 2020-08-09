package io.github.jhipster.online.service.mapper;

import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubGenEventMapper extends EntityMapper<SubGenEventDTO, SubGenEvent> {

    default SubGenEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubGenEvent subGenEvent = new SubGenEvent();
        subGenEvent.setId(id);
        return subGenEvent;
    }
}
