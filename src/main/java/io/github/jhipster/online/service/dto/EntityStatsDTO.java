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
        return "EntityStatsDTO{" +
            "id=" + id +
            ", year=" + year +
            ", month=" + month +
            ", week=" + week +
            ", day=" + day +
            ", hour=" + hour +
            ", fields=" + fields +
            ", relationships=" + relationships +
            ", pagination='" + pagination + '\'' +
            ", dto='" + dto + '\'' +
            ", service='" + service + '\'' +
            ", fluentMethods=" + fluentMethods +
            ", date=" + date +
            '}';
    }
}

