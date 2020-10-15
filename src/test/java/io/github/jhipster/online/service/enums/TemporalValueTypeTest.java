package io.github.jhipster.online.service.enums;

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class TemporalValueTypeTest {

    @Test
    void assertThatYearToInstantIsCorrect() {
        Instant result = TemporalValueType.absoluteMomentToInstant(2018L, TemporalValueType.YEAR);

        assertThat(result).isEqualTo(parse("2018-01-01T00:00:00.000Z"));
    }

    @Test
    void assertThatAbsoluteMonthToInstantIsCorrect() {
        Instant result = TemporalValueType.absoluteMomentToInstant(583L, TemporalValueType.MONTH);

        assertThat(result).isEqualTo(parse("2018-08-01T00:00:00.000Z"));
    }

    @Test
    void assertThatAbsoluteWeekToInstantIsCorrect() {
        Instant result = TemporalValueType.absoluteMomentToInstant(2535L, TemporalValueType.WEEK);

        assertThat(result).isEqualTo(parse("2018-08-02T00:00:00.000Z"));
    }

    @Test
    void assertThatAbsoluteDayToInstantIsCorrect() {
        Instant result = TemporalValueType.absoluteMomentToInstant(17745L, TemporalValueType.DAY);

        assertThat(result).isEqualTo(parse("2018-08-02T00:00:00.000Z"));
    }

    @Test
    void assertThatAbsoluteHourToInstantIsCorrect() {
        Instant result = TemporalValueType.absoluteMomentToInstant(425885L, TemporalValueType.HOUR);

        assertThat(result).isEqualTo(parse("2018-08-02T05:00:00.000Z"));
    }
}
