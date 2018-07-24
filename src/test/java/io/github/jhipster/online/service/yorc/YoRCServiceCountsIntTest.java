package io.github.jhipster.online.service.yorc;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.YoRCServiceUtil;
import org.joda.time.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class YoRCServiceCountsIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    private List<YoRC> yos;

    private final Calendar calendar = Calendar.getInstance();

    private Calendar fiveYearsAgo;

    private Calendar fiveYearsAgoPlusOneYear;

    private Instant epochInstant;

    private Instant fiveYearsAgoInstant;

    @Before
    public void init() {
        fiveYearsAgo = Calendar.getInstance();
        fiveYearsAgo.add(Calendar.YEAR, -5);

        fiveYearsAgoPlusOneYear = (Calendar) fiveYearsAgo.clone();
        fiveYearsAgoPlusOneYear.add(Calendar.YEAR, 1);

        Calendar epoch = Calendar.getInstance();
        epoch.set(1970, 1, 1);

        epochInstant = new Instant(epoch.toInstant().toEpochMilli());
        fiveYearsAgoInstant = new Instant(fiveYearsAgo.toInstant().toEpochMilli());

        YoRCServiceUtil.clearDatabase(yoRCRepository);
        yos = YoRCServiceUtil.addFakeData(300, fiveYearsAgo, calendar, yoRCRepository);
    }

    @Test
    public void assertThatCountDoesNotOmitAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.countAll());
    }

    @Test
    public void assertThatYearlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatMonthlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.MONTH)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatWeeklyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.WEEK)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatDailyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.DAY)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatHourlyCountIsNotOmittingAnyData() {
        assertThat(yos.size())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.HOUR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAYearCountIsCorrect() {
        int yearWeAreLookingFor = fiveYearsAgoPlusOneYear.get(Calendar.YEAR);
        assertThat(yos.stream().filter(yo -> yo.getYear().equals(yearWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.YEAR)
                .get(yearWeAreLookingFor - fiveYearsAgo.get(Calendar.YEAR)).getCount());
    }

    @Test
    public void assertThatAMonthCountIsCorrect() {
        long monthWeAreLookingFor = 561;
        long numberOfYear = monthWeAreLookingFor / 12;
        long monthOfTheYear = monthWeAreLookingFor % 12 + 1;

        assertThat(
            yos.stream()
                .filter(yo -> yo.getMonth() == monthWeAreLookingFor)
                .count()
        ).isEqualTo(
            yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.MONTH).stream().filter(item ->
                item.getDate().getYear() == numberOfYear + 1970 && item.getDate().getMonth().getValue() == monthOfTheYear).findFirst().orElse(null).getCount()
        );
    }

    @Test
    public void assertThatAWeekCountIsCorrect() {
        long weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, fiveYearsAgoInstant).getWeeks()+1;

        assertThat(yos.stream().filter(yo -> yo.getWeek().equals(weekWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.WEEK).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK))).count());
    }

    @Test
    public void assertThatADayCountIsCorrect() {
        long dayWeAreLookingFor = Days.daysBetween(epochInstant, fiveYearsAgoInstant).getDays()+1;

        assertThat(yos.stream().filter(yo -> yo.getDay().equals(dayWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.DAY).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY))).count());
    }

    @Test
    public void assertThatAHourCountIsCorrect() {
        long hourWeAreLookingFor = Hours.hoursBetween(epochInstant, fiveYearsAgoInstant).getHours()+1;

        assertThat(yos.stream().filter(yo -> yo.getHour().equals(hourWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.HOUR).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR))).count());
    }
}
