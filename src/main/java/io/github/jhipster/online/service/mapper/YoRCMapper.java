package io.github.jhipster.online.service.mapper;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.service.dto.YoRCDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface YoRCMapper extends EntityMapper<YoRCDTO, YoRC> {
    default YoRC fromId(Long id) {
        if (id == null) {
            return null;
        }
        YoRC yoRC = new YoRC();
        yoRC.setId(id);
        return yoRC;
    }
}
