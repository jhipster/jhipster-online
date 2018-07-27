package io.github.jhipster.online.service.yorc;

import io.github.jhipster.online.service.enums.TemporalValueType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class AbsoluteLocalDateTimeUnitTest {

    @Test
    public void assertThatYearsAreCorrectlySet() {
        assertThat(TemporalValueType.absoluteMomentToLocalDateTime(10L, TemporalValueType.YEAR).getYear()).isEqualTo(10);
    }

    @Test
    public void assertThatMonthsAreCorrectlySet() {
        long numberOfMonth = 45;
        long numberOfYear = numberOfMonth / 12 + 1970;
        long monthOfTheYear = numberOfMonth % 12 + 1;

        LocalDateTime toTest = TemporalValueType.absoluteMomentToLocalDateTime(numberOfMonth, TemporalValueType.MONTH);
        assertThat(toTest.getYear()).isEqualTo(numberOfYear);
        assertThat(toTest.getMonth().getValue()).isEqualTo(monthOfTheYear);
    }

    @Test
    public void assertThatWeeksAreCorrectlySet() {
        assertThat((TemporalValueType.absoluteMomentToLocalDateTime(2013L, TemporalValueType.WEEK).toEpochSecond(ZoneOffset.UTC)/ (24*3600)) / 7).isEqualTo(2013);
    }

    @Test
    public void assertThatDaysAreCorrectlySet() {
        assertThat(TemporalValueType.absoluteMomentToLocalDateTime(200L, TemporalValueType.DAY).toEpochSecond(ZoneOffset.UTC)).isEqualTo(200*3600*24);
    }

    @Test
    public void assertThatHoursAreCorrectlySet() {
        assertThat(TemporalValueType.absoluteMomentToLocalDateTime(200L, TemporalValueType.HOUR).toEpochSecond(ZoneOffset.UTC)).isEqualTo(200*3600);
    }
}
