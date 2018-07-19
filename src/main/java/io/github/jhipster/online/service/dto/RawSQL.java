package io.github.jhipster.online.service.dto;

import java.util.Objects;

public class RawSQL {

    private Integer date;

    private Long count;

    public RawSQL(Integer date, Long count) {
        this.date = date;
        this.count = count;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
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
        RawSQL rawSQL = (RawSQL) o;
        return Objects.equals(date, rawSQL.date) &&
            Objects.equals(count, rawSQL.count);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, count);
    }
}
