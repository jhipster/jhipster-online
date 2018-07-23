package io.github.jhipster.online.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class TemporalCountDTO implements Comparable<TemporalCountDTO> {

    private LocalDateTime date;

    private Long count;

    public TemporalCountDTO(LocalDateTime date, Long count) {
        this.date = date;
        this.count = count;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemporalCountDTO that = (TemporalCountDTO) o;
        return Objects.equals(date, that.date) &&
            Objects.equals(count, that.count);
    }


    @Override
    public int hashCode() {
        return Objects.hash(date, count);
    }

    @Override
    public String toString() {
        return "TemporalCountDTO{" +
            "date=" + date +
            ", count=" + count +
            '}';
    }

    @Override
    public int compareTo(TemporalCountDTO other) {
        return date.compareTo(other.date);
    }
}
