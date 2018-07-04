package io.github.jhipster.online.service.dto;

import java.time.LocalDate;
import java.util.Map;

public class TemporalDistributionDTO {

    private LocalDate date;

    private Map<String, Long> values;

    public TemporalDistributionDTO(LocalDate date, Map<String, Long> values) {
        this.date = date;
        this.values = values;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Long> getValues() {
        return values;
    }

    public void setValues(Map<String, Long> values) {
        this.values = values;
    }
}
