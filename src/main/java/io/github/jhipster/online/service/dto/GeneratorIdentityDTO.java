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
        return "GeneratorIdentityDTO{" +
            "id=" + id +
            ", host='" + host + '\'' +
            ", guid='" + guid + '\'' +
            ", owner=" + owner +
            '}';
    }
}
