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

import io.github.jhipster.online.domain.User;
import java.io.Serializable;

/**
 * A DTO for the {@link io.github.jhipster.online.domain.GeneratorIdentity} entity.
 */
public class GeneratorIdentityDTO implements Serializable {

    private Long id;

    private String host;

    private String guid;

    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public GeneratorIdentityDTO guid(String guid) {
        this.guid = guid;
        return this;
    }

    @Override
    public String toString() {
        return "GeneratorIdentityDTO{" + "id=" + id + ", host='" + host + '\'' + ", guid='" + guid + '\'' + ", owner=" + owner + '}';
    }
}
