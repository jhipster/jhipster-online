package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GeneratorIdentity.
 */
@Entity
@Table(name = "generator_identity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GeneratorIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host")
    private String host;

    @Column(name = "guid")
    private String guid;

    @ManyToOne
    @JsonIgnoreProperties("generatorIdentities")
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public GeneratorIdentity host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGuid() {
        return guid;
    }

    public GeneratorIdentity guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public User getOwner() {
        return owner;
    }

    public GeneratorIdentity owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeneratorIdentity)) {
            return false;
        }
        return id != null && id.equals(((GeneratorIdentity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GeneratorIdentity{" +
            "id=" + getId() +
            ", host='" + getHost() + "'" +
            ", guid='" + getGuid() + "'" +
            "}";
    }
}
