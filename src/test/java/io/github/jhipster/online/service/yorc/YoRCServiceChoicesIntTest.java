package io.github.jhipster.online.service.yorc;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
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
public class YoRCServiceChoicesIntTest {

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    private List<YoRC> yos;

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

        DataGenerationUtil.clearYoRcTable(yoRCRepository);
        yos = DataGenerationUtil.addYosToDatabase(300, threeYearsAgo, calendar, yoRCRepository);
    }

    @Test
    public void assertThatTheFieldProportionEqualsToTheTotal() {
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getClientFramework().equals("react"))
                .count()
        ).isEqualTo(yoRCService.getFieldCount(threeYearsAgo.toInstant(), YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.YEAR).stream()
            .mapToLong(item ->
                item.getValues()
                    .entrySet()
                    .stream()
                    .filter(obj ->
                        obj.getKey().equals("react"))
                    .mapToLong(Map.Entry::getValue)
                    .sum()
            ).sum());
    }

    @Test
    public void assertThatYearlyProportionsAreCorrect() {
        int yearWeAreLookingFor = threeYearsAgoPlusOneYear.get(Calendar.YEAR);
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getYear().equals(yearWeAreLookingFor) && yo.getClientFramework().equals("react"))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(threeYearsAgo.toInstant(), YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.YEAR).stream()
                .filter(item ->
                    item.getDate().getYear() == yearWeAreLookingFor)
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals("react"))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
            ).sum()
        );
    }

    @Test
    public void assertThatWeeklyProportionsAreCorrect() {
        long weekWeAreLookingFor = Weeks.weeksBetween(epochInstant, fiveYearsAgoInstant).getWeeks()+1;
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getYear().equals(weekWeAreLookingFor) && yo.getClientFramework().equals("react"))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(threeYearsAgo.toInstant(), YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.WEEK).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(weekWeAreLookingFor, TemporalValueType.WEEK)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals("react"))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }

    @Test
    public void assertThatDailyProportionsAreCorrect() {
        long dayWeAreLookingFor = Days.daysBetween(epochInstant, fiveYearsAgoInstant).getDays()+1;
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getYear().equals(dayWeAreLookingFor) && yo.getClientFramework().equals("react"))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(threeYearsAgo.toInstant(), YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.DAY).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(dayWeAreLookingFor, TemporalValueType.DAY)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals("react"))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }

    @Test
    public void assertThatHourlyProportionsAreCorrect() {
        long hourWeAreLookingFor = Hours.hoursBetween(epochInstant, fiveYearsAgoInstant).getHours()+1;
        assertThat(
            yos.stream()
                .filter(yo ->
                    yo.getYear().equals(hourWeAreLookingFor) && yo.getClientFramework().equals("react"))
                .count()
        ).isEqualTo(
            yoRCService.getFieldCount(threeYearsAgo.toInstant(), YoRCColumn.CLIENT_FRAMEWORK, TemporalValueType.HOUR).stream()
                .filter(item ->
                    item.getDate().equals(TemporalValueType.absoluteMomentToLocalDateTime(hourWeAreLookingFor, TemporalValueType.HOUR)))
                .mapToLong(item ->
                    item.getValues()
                        .entrySet()
                        .stream()
                        .filter(obj ->
                            obj.getKey().equals("react"))
                        .mapToLong(Map.Entry::getValue)
                        .sum()
                ).sum()
        );
    }
}
