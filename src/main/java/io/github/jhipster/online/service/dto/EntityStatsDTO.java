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

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link io.github.jhipster.online.domain.EntityStats} entity.
 */
public class EntityStatsDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer month;

    private Integer week;

    private Integer day;

    private Integer hour;

    private Integer fields;

    private Integer relationships;

    private String pagination;

    private String dto;

    private String service;

    private Boolean fluentMethods;

    private Instant date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getFields() {
        return fields;
    }

    public void setFields(Integer fields) {
        this.fields = fields;
    }

    public Integer getRelationships() {
        return relationships;
    }

    public void setRelationships(Integer relationships) {
        this.relationships = relationships;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public String getDto() {
        return dto;
    }

    public void setDto(String dto) {
        this.dto = dto;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Boolean getFluentMethods() {
        return fluentMethods;
    }

    public void setFluentMethods(Boolean fluentMethods) {
        this.fluentMethods = fluentMethods;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return (
            "EntityStatsDTO{" +
            "id=" +
            id +
            ", year=" +
            year +
            ", month=" +
            month +
            ", week=" +
            week +
            ", day=" +
            day +
            ", hour=" +
            hour +
            ", fields=" +
            fields +
            ", relationships=" +
            relationships +
            ", pagination='" +
            pagination +
            '\'' +
            ", dto='" +
            dto +
            '\'' +
            ", service='" +
            service +
            '\'' +
            ", fluentMethods=" +
            fluentMethods +
            ", date=" +
            date +
            '}'
        );
    }
}
