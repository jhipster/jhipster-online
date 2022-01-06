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
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A JDL.
 */
@Entity
@Table(name = "jdl")
public class Jdl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    @Size(max = 200_000)
    private String content;

    @OneToOne(optional = false)
    @NotNull
    @JsonIgnore
    private JdlMetadata jdlMetadata;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JdlMetadata getJdlMetadata() {
        return jdlMetadata;
    }

    public Jdl jdlMetadata(JdlMetadata jdlMetadata) {
        this.jdlMetadata = jdlMetadata;
        return this;
    }

    public void setJdlMetadata(JdlMetadata jdlMetadata) {
        this.jdlMetadata = jdlMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jdl jdlMetadataObject = (Jdl) o;
        if (jdlMetadataObject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jdlMetadataObject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JdlMetadata{" + "id=" + getId() + "}";
    }
}
