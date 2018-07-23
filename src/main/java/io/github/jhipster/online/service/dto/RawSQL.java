package io.github.jhipster.online.service.dto;

import java.util.Objects;

public class RawSQL {

    private Integer moment;

    private Long count;

    public RawSQL(Integer moment, Long count) {
        this.moment = moment;
        this.count = count;
    }

    public Integer getMoment() {
        return moment;
    }

    public void setMoment(Integer moment) {
        this.moment = moment;
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
        return Objects.equals(moment, rawSQL.moment) &&
            Objects.equals(count, rawSQL.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moment, count);
    }

    @Override
    public String toString() {
        return "RawSQL{" +
            "moment=" + moment +
            ", count=" + count +
            '}';
    }
}
