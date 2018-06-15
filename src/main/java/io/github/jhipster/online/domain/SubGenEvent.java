package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SubGenEvent.
 */
@Entity
@Table(name = "sub_gen_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubGenEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "event")
    private String event;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToOne
    @JsonIgnoreProperties("")
    private OwnerIdentity owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public SubGenEvent source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public SubGenEvent type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public SubGenEvent event(String event) {
        this.event = event;
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getDate() {
        return date;
    }

    public SubGenEvent date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public OwnerIdentity getOwner() {
        return owner;
    }

    public SubGenEvent owner(OwnerIdentity ownerIdentity) {
        this.owner = ownerIdentity;
        return this;
    }

    public void setOwner(OwnerIdentity ownerIdentity) {
        this.owner = ownerIdentity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubGenEvent subGenEvent = (SubGenEvent) o;
        if (subGenEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subGenEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubGenEvent{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", type='" + getType() + "'" +
            ", event='" + getEvent() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
