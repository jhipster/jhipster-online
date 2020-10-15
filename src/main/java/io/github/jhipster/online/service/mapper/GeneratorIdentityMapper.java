package io.github.jhipster.online.service.mapper;

import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.service.dto.GeneratorIdentityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneratorIdentityMapper extends EntityMapper<GeneratorIdentityDTO, GeneratorIdentity> {
    default GeneratorIdentity fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeneratorIdentity generatorIdentity = new GeneratorIdentity();
        generatorIdentity.setId(id);
        return generatorIdentity;
    }
}
