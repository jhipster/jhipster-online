package io.github.jhipster.online.service.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class TemporalDistributionDTO {

    private LocalDateTime date;

    private Map<String, Long> values;

    public TemporalDistributionDTO(LocalDateTime date, Map<String, Long> values) {
        this.date = date;
        this.values = values;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Map<String, Long> getValues() {
        return values;
    }

    public void setValues(Map<String, Long> values) {
        this.values = values;
    }
}
