package abacus.persist.entities;

import abacus.persist.embeddables.CalendarEvent;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Calendar")
public class CalendarEntity {

    @Transient
    private Logger log = LoggerFactory.getLogger(CalendarEntity.class);

    @Id
    private int year;

    @ElementCollection
    @Temporal(TemporalType.DATE)
    private Set<CalendarEvent> holidayDates = new HashSet<>();

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addHoliday(CalendarEvent event) {
        holidayDates.add(event);
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return (dow == DayOfWeek.SATURDAY) || (dow == DayOfWeek.SUNDAY);
    }

    public boolean isHoliday(LocalDate date) {
        assert(date != null);
        CalendarEvent event = this.holidayDates.stream().filter(e -> date.equals(e.getDate())).findFirst().orElse(null);
        if (event != null)
        {
            log.info("found {}", event);
        }
        return event != null;
    }

    public boolean isBizDate(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    public LocalDate nextBizDate(LocalDate date) {
        LocalDate proposal = date;

        do {
            proposal = proposal.plus(Period.ofDays(1));
        }
        while (!isBizDate(proposal));

        return proposal;
    }

    public LocalDate prevBizDate(LocalDate date) {
        LocalDate proposal = date;

        do {
            proposal = proposal.minus(Period.ofDays(1));
        }
        while (!isBizDate(proposal));

        return proposal;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("year", year)
                .add("holidayDates", holidayDates)
                .toString();
    }
}
