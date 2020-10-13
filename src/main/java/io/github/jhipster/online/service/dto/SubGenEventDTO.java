package io.github.jhipster.online.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link io.github.jhipster.online.domain.SubGenEvent} entity.
 */
public class SubGenEventDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer month;

    private Integer week;

    private Integer day;

    private Integer hour;

    private String source;

    private String type;

    private String event;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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
            "SubGenEventDTO{" +
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
            ", source='" +
            source +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", event='" +
            event +
            '\'' +
            ", date=" +
            date +
            '}'
        );
    }
}
