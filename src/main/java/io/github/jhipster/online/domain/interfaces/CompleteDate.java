package io.github.jhipster.online.domain.interfaces;

public interface CompleteDate {

    void setYear(Integer year);

    void setMonth(Integer month);

    void setWeek(Integer week);

    void setDay(Integer day);

    void setHour(Integer hour);

    Integer getMonth();

    Integer getWeek();

    Integer getDay();

    Integer getHour();

    CompleteDate year(Integer year);

    CompleteDate month(Integer month);

    CompleteDate week(Integer week);

    CompleteDate day(Integer day);

    CompleteDate hour(Integer hour);

}
