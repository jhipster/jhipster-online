package io.github.jhipster.online.service.enums;

public enum TemporalFunction {
    YEAR("year"), YEAR_MONTH("year_month"), DATE("date");

    private String function;

    private TemporalFunction(String function) {
        this.function = function;
    }

    public String getTemporalFunction() {
        return function;
    }
}
