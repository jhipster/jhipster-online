package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EntityStats.
 */
@Entity
@Table(name = "entity_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntityStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fields")
    private Integer fields;

    @Column(name = "relationships")
    private Integer relationships;

    @Column(name = "pagination")
    private String pagination;

    @Column(name = "dto")
    private Boolean dto;

    @Column(name = "service")
    private Boolean service;

    @Column(name = "fluent_methods")
    private Boolean fluentMethods;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

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

    public Integer getFields() {
        return fields;
    }

    public EntityStats fields(Integer fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(Integer fields) {
        this.fields = fields;
    }

    public Integer getRelationships() {
        return relationships;
    }

    public EntityStats relationships(Integer relationships) {
        this.relationships = relationships;
        return this;
    }

    public void setRelationships(Integer relationships) {
        this.relationships = relationships;
    }

    public String getPagination() {
        return pagination;
    }

    public EntityStats pagination(String pagination) {
        this.pagination = pagination;
        return this;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public Boolean isDto() {
        return dto;
    }

    public EntityStats dto(Boolean dto) {
        this.dto = dto;
        return this;
    }

    public void setDto(Boolean dto) {
        this.dto = dto;
    }

    public Boolean isService() {
        return service;
    }

    public EntityStats service(Boolean service) {
        this.service = service;
        return this;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    public Boolean isFluentMethods() {
        return fluentMethods;
    }

    public EntityStats fluentMethods(Boolean fluentMethods) {
        this.fluentMethods = fluentMethods;
        return this;
    }

    public void setFluentMethods(Boolean fluentMethods) {
        this.fluentMethods = fluentMethods;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public EntityStats date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public OwnerIdentity getOwner() {
        return owner;
    }

    public EntityStats owner(OwnerIdentity ownerIdentity) {
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
        EntityStats entityStats = (EntityStats) o;
        if (entityStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entityStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntityStats{" +
            "id=" + getId() +
            ", fields=" + getFields() +
            ", relationships=" + getRelationships() +
            ", pagination='" + getPagination() + "'" +
            ", dto='" + isDto() + "'" +
            ", service='" + isService() + "'" +
            ", fluentMethods='" + isFluentMethods() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
