package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.SubGenEventUtil;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.joda.time.Weeks;
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
public class SubGenEventIntTest {

    @Autowired
    private SubGenEventRepository subGenEventRepository;

    @Autowired
    private SubGenEventService subGenEventService;

    private List<SubGenEvent> sgeList;

    private final Calendar calendar = Calendar.getInstance();

    private Calendar threeYearsAgo;

    private Calendar threeYearsAgoPlusOneYear;

    private Instant epochInstant;

    private Instant fiveYearsAgoInstant;

    @Before
    public void init() {
        threeYearsAgo = Calendar.getInstance();
        threeYearsAgo.add(Calendar.YEAR, -3);

        threeYearsAgoPlusOneYear = (Calendar) threeYearsAgo.clone();
        threeYearsAgoPlusOneYear.add(Calendar.YEAR, 1);

        Calendar epoch = Calendar.getInstance();
        epoch.set(1970, 1, 1);

        epochInstant = new Instant(epoch.toInstant().toEpochMilli());
        fiveYearsAgoInstant = new Instant(threeYearsAgo.toInstant().toEpochMilli());

        SubGenEventUtil.clearDatabase(subGenEventRepository);
        sgeList = SubGenEventUtil.addFakeData(300, threeYearsAgo, calendar, subGenEventRepository);
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
            ).isEqualTo(subGenEventService.getFieldCount(threeYearsAgo.toInstant(), SubGenEventType.HEROKU, TemporalValueType.YEAR).stream()
            .mapToLong(TemporalCountDTO::getCount)
            .sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = threeYearsAgoPlusOneYear.get(Calendar.YEAR);
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(threeYearsAgo.toInstant(), SubGenEventType.HEROKU, TemporalValueType.YEAR).stream()
                .filter(item ->
                    item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, fiveYearsAgoInstant).getWeeks()+1;
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(threeYearsAgo.toInstant(), SubGenEventType.HEROKU, TemporalValueType.WEEK).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = Days.daysBetween(epochInstant, fiveYearsAgoInstant).getDays()+1;
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(threeYearsAgo.toInstant(), SubGenEventType.HEROKU, TemporalValueType.DAY).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = Hours.hoursBetween(epochInstant, fiveYearsAgoInstant).getHours()+1;
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(threeYearsAgo.toInstant(), SubGenEventType.HEROKU, TemporalValueType.HOUR).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }
}
