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
package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JdlMetadata.
 */
@Entity
@Table(name = "jdl_metadata")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JdlMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();

    @Column(name = "updated_date", nullable = false)
    private Instant updatedDate = Instant.now();

    @Column(name = "is_public")
    private Boolean isPublic = false;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public JdlMetadata name(String name) {
        this.name = name;
        return this;
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

    public Boolean isIsPublic() {
        return isPublic;
    }

    public JdlMetadata isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public User getUser() {
        return user;
    }

    public JdlMetadata user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JdlMetadata jdlMetadata = (JdlMetadata) o;
        if (jdlMetadata.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jdlMetadata.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JdlMetadata{" + "id=" + getId() + ", name='" + getName() + "'" + ", isPublic='" + isIsPublic() + "'" + "}";
    }
}
