package io.github.jhipster.online.service.mapper;

import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.service.dto.JdlMetadataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JdlMetadataMapper extends EntityMapper<JdlMetadataDTO, JdlMetadata> {
    default JdlMetadata fromId(String id) {
        if (id == null) {
            return null;
        }
        JdlMetadata jdlMetadata = new JdlMetadata();
        jdlMetadata.setId(id);
        return jdlMetadata;
    }
}
