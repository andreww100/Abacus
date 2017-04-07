package abacus.jobs;

import abacus.persist.dao.BizDateDAO;
import abacus.persist.embeddables.CalendarEvent;
import abacus.persist.entities.CalendarEntity;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarJob {

    private Logger log = LoggerFactory.getLogger(CalendarJob.class);

    @javax.inject.Inject
    private BizDateDAO repo;

    //@Transactional
    public void perform() {
        // Create a Calendar for this year
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        CalendarEntity calendarEntity = repo.createCalendar(year, getHolidays(year));
        log.info(calendarEntity.toString());

        // Set the Current Business Date
        LocalDate curBizDate = calendarEntity.isBizDate(today) ? today : calendarEntity.nextBizDate(today);
        repo.setCurBizDate(curBizDate);
    }

    public List<CalendarEvent> getHolidays(int year) {
        List<CalendarEvent> holidays = new ArrayList<>();
        addHoliday(holidays, year, 12, 25, "Christmas Day");
        addHoliday(holidays, year, 12, 26, "Boxing Day");
        return holidays;
    }

    public void addHoliday(List<CalendarEvent> holidays, int year, int month, int day,
                           String description) {
        holidays.add(new CalendarEvent(LocalDate.of(year, month, day), description));
    }
}
