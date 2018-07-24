package io.github.jhipster.online.service.dto;

public class RawSQLField {

    private Integer moment;

    private String field;

    private Long count;

    public RawSQLField(Integer moment, String field, Long count) {
        this.moment = moment;
        this.field = field;
        this.count = count;
    }

    public Integer getMoment() {
        return moment;
    }

    public void setMoment(Integer moment) {
        this.moment = moment;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RawSQLField{" +
            "moment=" + moment +
            ", field='" + field + '\'' +
            ", count=" + count +
            '}';
    }
}
