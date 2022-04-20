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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.online.domain.interfaces.CompleteDate;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EntityStats.
 */
@Entity
@Table(name = "entity_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntityStats implements Serializable, CompleteDate {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "week")
    private Integer week;

    @Column(name = "day")
    private Integer day;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "fields")
    private Integer fields;

    @Column(name = "relationships")
    private Integer relationships;

    @Column(name = "pagination")
    private String pagination;

    @Column(name = "dto")
    private String dto;

    @Column(name = "service")
    private String service;

    @Column(name = "fluent_methods")
    private Boolean fluentMethods;

    @Column(name = "jhi_date")
    private Instant date;

    @ManyToOne
    @JsonIgnoreProperties("")
    private GeneratorIdentity owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public EntityStats year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public EntityStats month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public EntityStats week(Integer week) {
        this.week = week;
        return this;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public EntityStats day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public EntityStats hour(Integer hour) {
        this.hour = hour;
        return this;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
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

    public String getDto() {
        return dto;
    }

    public EntityStats dto(String dto) {
        this.dto = dto;
        return this;
    }

    public void setDto(String dto) {
        this.dto = dto;
    }

    public String getService() {
        return service;
    }

    public EntityStats service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
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

    public Instant getDate() {
        return date;
    }

    public EntityStats date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public GeneratorIdentity getOwner() {
        return owner;
    }

    public EntityStats owner(GeneratorIdentity generatorIdentity) {
        this.owner = generatorIdentity;
        return this;
    }

    public void setOwner(GeneratorIdentity generatorIdentity) {
        this.owner = generatorIdentity;
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
        return (
            "EntityStats{" +
            "id=" +
            getId() +
            ", year=" +
            getYear() +
            ", month=" +
            getMonth() +
            ", week=" +
            getWeek() +
            ", day=" +
            getDay() +
            ", hour=" +
            getHour() +
            ", fields=" +
            getFields() +
            ", relationships=" +
            getRelationships() +
            ", pagination='" +
            getPagination() +
            "'" +
            ", dto='" +
            getDto() +
            "'" +
            ", service='" +
            getService() +
            "'" +
            ", fluentMethods='" +
            isFluentMethods() +
            "'" +
            ", date='" +
            getDate() +
            "'" +
            "}"
        );
    }
}
