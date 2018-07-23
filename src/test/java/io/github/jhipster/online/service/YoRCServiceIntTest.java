package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.repository.YoRCRepository;
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
public class YoRCServiceIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private OwnerIdentityService ownerIdentityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private YoRCService yoRCService;

    private List<YoRC> yos;

    private final Calendar calendar = Calendar.getInstance();

    private Calendar fiveYearsAgo;

    private Calendar fiveYearsAgoPlusOneYear;

    private Calendar epoch;

    private Instant epochInstant;


    private Instant fiveYearsAgoInstant;


    @Before
    public void init() {
        fiveYearsAgo = Calendar.getInstance();
        fiveYearsAgo.add(Calendar.YEAR, -5);

        fiveYearsAgoPlusOneYear = (Calendar) fiveYearsAgo.clone();
        fiveYearsAgoPlusOneYear.add(Calendar.YEAR, 1);

        epoch = Calendar.getInstance();
        epoch.set(1970, 01, 01);

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

    // FIXME: months are for now in our implementation really unreliable due the way they're handle with regular libs.
//    @Test
//    public void assertThatAMonthCountIsCorrect() {
//        int monthWeAreLookingFor = Months.monthsBetween(epochInstant, fiveYearsAgoInstant).getMonths() + (1970 * 12);
//
//        assertThat(yos.stream().filter(yo -> yo.getMonth().equals(monthWeAreLookingFor)).count())
//            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.MONTH).get(monthWeAreLookingFor - (1970 * 12)));
//    }

    @Test
    public void assertThatAWeekCountIsCorrect() {
        int weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, fiveYearsAgoInstant).getWeeks()+1;

        assertThat(yos.stream().filter(yo -> yo.getWeek().equals(weekWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.WEEK).stream()
                .filter(e -> e.getDate().equals(yoRCService.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK))).count());
    }

    @Test
    public void assertThatADayCountIsCorrect() {
        int dayWeAreLookingFor = Days.daysBetween(epochInstant, fiveYearsAgoInstant).getDays()+1;

        assertThat(yos.stream().filter(yo -> yo.getDay().equals(dayWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.DAY).stream()
                .filter(e -> e.getDate().equals(yoRCService.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY))).count());
    }

    @Test
    public void assertThatAHourCountIsCorrect() {
        int hourWeAreLookingFor = Hours.hoursBetween(epochInstant, fiveYearsAgoInstant).getHours()+1;

        assertThat(yos.stream().filter(yo -> yo.getHour().equals(hourWeAreLookingFor)).count())
            .isEqualTo(yoRCService.getCount(fiveYearsAgo.toInstant(), TemporalValueType.HOUR).stream()
                .filter(e -> e.getDate().equals(yoRCService.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR))).count());
    }
}
