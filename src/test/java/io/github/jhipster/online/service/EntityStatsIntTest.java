package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.repository.EntityStatsRepository;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.service.util.DataGenerationUtil;
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
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class EntityStatsIntTest {

    @Autowired
    private EntityStatsRepository entityStatsRepository;

    @Autowired
    private EntityStatsService entityStatsService;

    private List<EntityStats> list;

    private Calendar calendar = Calendar.getInstance();

    private Calendar threeYearsAgo;

    private Calendar threeYearsAgoPlusOneYear;

    private Instant epochInstant;

    private Instant threeYearsAgoInstant;

    @Before
    public void init() {
        threeYearsAgo = Calendar.getInstance();
        threeYearsAgo.add(Calendar.YEAR, -3);

        threeYearsAgoPlusOneYear = (Calendar) threeYearsAgo.clone();
        threeYearsAgoPlusOneYear.add(Calendar.YEAR, 1);

        Calendar epoch = Calendar.getInstance();
        epoch.set(1970, 1, 1);

        epochInstant = new Instant(epoch.toInstant().toEpochMilli());
        threeYearsAgoInstant = new Instant(threeYearsAgo.toInstant().toEpochMilli());
        DataGenerationUtil.clearEntityStatsTable(entityStatsRepository);
        list = DataGenerationUtil.addEntityStatsToDatabase(3000, threeYearsAgo, calendar, entityStatsRepository);
    }

    @Test
    public void assertThatYearlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.YEAR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatMonthlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.MONTH)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatWeeklyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.WEEK)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatDailyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.DAY)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatHourlyCountIsNotOmittingAnyData() {
        assertThat(list.size())
            .isEqualTo(entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.HOUR)
                .stream()
                .mapToLong(TemporalCountDTO::getCount)
                .sum());
    }

    @Test
    public void assertThatAYearCountIsCorrect() {
        int yearWeAreLookingFor = threeYearsAgoPlusOneYear.get(Calendar.YEAR);
        assertThat(
            list.stream().filter(yo ->
                yo.getYear().equals(yearWeAreLookingFor))
                .count()
        ).isEqualTo(
            entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.YEAR).get(yearWeAreLookingFor - threeYearsAgo.get(Calendar.YEAR))
                .getCount()
        );
    }

    @Test
    public void assertThatAMonthCountIsCorrect() {
        long monthWeAreLookingFor = 561;
        long numberOfYear = monthWeAreLookingFor / 12;
        long monthOfTheYear = monthWeAreLookingFor % 12 + 1;

        assertThat(
            list.stream()
                .filter(yo -> yo.getMonth() == monthWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.MONTH).stream()
                .filter(item ->
                    item.getDate().getYear() == numberOfYear + 1970 && item.getDate().getMonth().getValue() == monthOfTheYear)
                .findFirst()
                .orElse(null)
                .getCount()
        );
    }

    @Test
    public void assertThatAWeekCountIsCorrect() {
        long weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, threeYearsAgoInstant).getWeeks()+52;

        assertThat(
            list.stream()
                .filter(yo -> yo.getWeek() == weekWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.WEEK).stream()
                .filter(e ->
                    e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(e -> e.getCount())
                .sum()
        );
    }

    @Test
    public void assertThatADayCountIsCorrect() {
        long dayWeAreLookingFor = Days.daysBetween(epochInstant, threeYearsAgoInstant).getDays()+1;

        assertThat(
            list.stream().filter(yo -> yo.getDay() == dayWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.DAY).stream()
                .filter(e -> e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .count()
        );
    }

    @Test
    public void assertThatAHourCountIsCorrect() {
        long hourWeAreLookingFor = Hours.hoursBetween(epochInstant, threeYearsAgoInstant).getHours()+1;

        assertThat(
            list.stream()
                .filter(yo ->
                    yo.getHour() == hourWeAreLookingFor)
                .count()
        ).isEqualTo(
            entityStatsService.getCount(threeYearsAgo.toInstant(), TemporalValueType.HOUR).stream()
                .filter(e ->
                    e.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .count()
        );
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals("infinite-scroll"))
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(threeYearsAgo.toInstant(), EntityStatColumn.PAGINATION, TemporalValueType.YEAR).stream()
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals("infinite-scroll")).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = threeYearsAgoPlusOneYear.get(Calendar.YEAR);
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals("infinite-scroll") && entity.getYear() == yearWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(threeYearsAgo.toInstant(), EntityStatColumn.PAGINATION, TemporalValueType.YEAR).stream()
            .filter(item ->
                item.getDate().getYear() == yearWeAreLookingFor)
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals("infinite-scroll")).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, threeYearsAgoInstant).getWeeks() + 1;
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals("infinite-scroll") && entity.getWeek() == weekWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(threeYearsAgo.toInstant(), EntityStatColumn.PAGINATION, TemporalValueType.WEEK).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals("infinite-scroll")).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = Days.daysBetween(epochInstant, threeYearsAgoInstant).getDays() + 1;
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals("infinite-scroll") && entity.getDay() == dayWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(threeYearsAgo.toInstant(), EntityStatColumn.PAGINATION, TemporalValueType.DAY).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals("infinite-scroll")).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = Hours.hoursBetween(epochInstant, threeYearsAgoInstant).getHours() + 1;
        assertThat(
            list.stream()
                .filter(entity ->
                    entity.getPagination().equals("infinite-scroll") && entity.getHour() == hourWeAreLookingFor)
                .count()
        ).isEqualTo(entityStatsService.getFieldCount(threeYearsAgo.toInstant(), EntityStatColumn.PAGINATION, TemporalValueType.HOUR).stream()
            .filter(item ->
                item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
            .mapToLong(item ->
                item.getValues().entrySet().stream()
                    .filter(entry ->
                        entry.getKey().equals("infinite-scroll")).mapToLong(Map.Entry::getValue)
                    .sum())
            .sum());
    }
}
