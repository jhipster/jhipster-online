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
        return "JdlMetadataDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", createdDate=" + createdDate +
            ", updatedDate=" + updatedDate +
            ", isPublic=" + isPublic +
            '}';
    }
}
