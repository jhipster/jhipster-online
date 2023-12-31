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
package io.github.jhipster.online.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link io.github.jhipster.online.domain.JdlMetadata} entity.
 */
public class JdlMetadataDTO implements Serializable {

    private String id;

    private String name;

    private Instant createdDate = Instant.now();

    private Instant updatedDate = Instant.now();

    private Boolean isPublic = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return (
            "JdlMetadataDTO{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", createdDate=" +
            createdDate +
            ", updatedDate=" +
            updatedDate +
            ", isPublic=" +
            isPublic +
            '}'
        );
    }
}
