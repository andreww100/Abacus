package abacus.persist.dao;

import abacus.persist.embeddables.CalendarEvent;
import abacus.persist.entities.CalendarEntity;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Create Calendars and navigate (next & previous) business days
 */
public class CalendarDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarDAO.class);

    @javax.inject.Inject
    private EntityManager em;

    @Transactional
    public CalendarEntity createCalendarEntity(int year, List<CalendarEvent> holidays) {
        CalendarEntity newCalendarEntity = new CalendarEntity();
        newCalendarEntity.setYear(year);
        holidays.forEach(newCalendarEntity::addHoliday);
        em.persist(newCalendarEntity);
        return newCalendarEntity;
    }

    public CalendarEntity getCalendarEntity(int year) {
        CalendarEntity requested = null;
        // SELECT
        TypedQuery<CalendarEntity> query = em.createQuery(
                "SELECT c FROM Calendar c WHERE c.year = :year",
                CalendarEntity.class);
        query.setParameter("year", year);
        try {
            requested = query.getSingleResult();
        } catch (NoResultException ex) {
            LOG.warn("Unable to locate a Calendar for year {}", year);
        }
        return requested;
    }

    /**
     * @return closest next business (non-holiday) day
     */
    public LocalDate getNextBizDate(LocalDate date) {
        return adjacentBizDate(date, true);
    }

    /**
     * @return closest previous business (non-holiday) day
     */
    public LocalDate getPrevBizDate(LocalDate date) {
        return adjacentBizDate(date, false);
    }

    /**
     * Find the closest business date (non-holiday) later (or before) the provided date.
     * This method will load all required Calendar(s).
     *
     * @param date  starting date
     * @param later true means find next valid business day, false means previous
     * @return closest business date
     */
    protected LocalDate adjacentBizDate(LocalDate date, boolean later) {
        final Period ONE_DAY = Period.ofDays(1);
        final int TRIES_LIMIT = 100;

        LocalDate proposal = date;
        CalendarEntity proposalCalendar = null;
        int tries = 0;

        do {
            tries++;
            proposal = later ? proposal.plus(ONE_DAY) : proposal.minus(ONE_DAY);
            if ((proposalCalendar == null) || (proposalCalendar.getYear() != proposal.getYear())) {
                proposalCalendar = getCalendarEntity(proposal.getYear());
            }
        }
        while (!proposalCalendar.isBizDate(proposal) && tries < TRIES_LIMIT);

        if (tries == TRIES_LIMIT) {
            LOG.error("Problem with Calendar logic/values, tries limit of {} hit", TRIES_LIMIT);
        }
        return proposal;
    }
}
