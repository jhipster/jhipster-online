package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.domain.enums.SubGenEventType;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    
    private Instant epochInstant;

    private Instant twoYearsAgoInstant;

    @Before
    public void init() {
        LocalDateTime epoch = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(2);

        epochInstant = Instant.ofEpochSecond(epoch.getSecond());
        twoYearsAgoInstant = Instant.ofEpochSecond(epoch.until(fiveYearsAgo, ChronoUnit.SECONDS));

        DataGenerationUtil.clearSubGenVentTable(subGenEventRepository);
        sgeList = DataGenerationUtil.addSubGenEventsToDatabase(1_000, twoYearsAgoInstant, Instant.now(), subGenEventRepository);
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count())
            .isEqualTo(subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = LocalDateTime.now().minusYears(1).getYear();

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.YEAR)
                .stream()
                .filter(item ->
                    item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) / 7 + 30;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.WEEK)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = ChronoUnit.DAYS.between(epochInstant, twoYearsAgoInstant) + 100;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.DAY)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = ChronoUnit.HOURS.between(epochInstant, twoYearsAgoInstant) + 500;

        assertThat(
            sgeList.stream()
                .filter(sge ->
                    sge.getType().equals(SubGenEventType.HEROKU.getDatabaseValue()))
                .count()
        ).isEqualTo(
            subGenEventService.getFieldCount(twoYearsAgoInstant, SubGenEventType.HEROKU, TemporalValueType.HOUR)
                .stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(TemporalCountDTO::getCount)
                .sum()
        );
    }
}
