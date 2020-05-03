package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JdlMetadata.
 */
@Entity
@Table(name = "jdl_metadata")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JdlMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private Long key;

    @Column(name = "name")
    private String name;

    @Column(name = "is_public")
    private Boolean isPublic;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("jdlMetadata")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKey() {
        return key;
    }

    public JdlMetadata key(Long key) {
        this.key = key;
        return this;
    }

    public void setKey(Long key) {
        this.key = key;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JdlMetadata)) {
            return false;
        }
        return id != null && id.equals(((JdlMetadata) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JdlMetadata{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", name='" + getName() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            "}";
    }
}
