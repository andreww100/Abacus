package abacus.persist.dao;

import abacus.persist.embeddables.CalendarEvent;
import abacus.persist.entities.CalendarEntity;
import abacus.persist.entities.CurBizDateEntity;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class BizDateDAO {

    private Logger LOG = LoggerFactory.getLogger(BizDateDAO.class);

    @javax.inject.Inject
    private EntityManager em;

    private CalendarEntity calendarEntity = null;
    private CurBizDateEntity curBizDateEntity = null;

    private LocalDate nextBizDate = null;
    private LocalDate prevBizDate = null;

    // Public Date Getters

    public LocalDate getCurBizDate() {
        return this.getCurBizDateEntity().getBizDate();
    }

    @Transactional
    public void setCurBizDate(LocalDate bizDate)
            throws IllegalArgumentException {
        // Check the date is a valid business date
        CalendarEntity relatedCalendarEntity = getCalendarEntity(bizDate.getYear());
        if (!relatedCalendarEntity.isBizDate(bizDate)) {
            throw new IllegalArgumentException("not a biz date");
        }

        // Update the date, retain the Calendar and invalidate the next/prev date cache
        try {
            // Update the date
            CurBizDateEntity newBizDate = this.getCurBizDateEntity();
            if (newBizDate == null)
            {
                newBizDate = new CurBizDateEntity();
            }
            newBizDate.setBizDate(bizDate);
            LOG.info("Setting {}", newBizDate);
            em.persist(newBizDate);
            // Swap
            this.curBizDateEntity = newBizDate;
            this.calendarEntity = relatedCalendarEntity;
        } finally {
            this.nextBizDate = null;
            this.prevBizDate = null;
        }
    }

    public LocalDate getNextBizDate() {
        if (this.nextBizDate == null) {
            this.nextBizDate = getCalendarEntity().nextBizDate(getCurBizDate());
        }
        return this.nextBizDate;
    }

    // Public Date & Calendar Setters

    public LocalDate getPrevBizDate() {
        if (this.prevBizDate == null) {
            this.prevBizDate = getCalendarEntity().prevBizDate(getCurBizDate());
        }
        return this.prevBizDate;
    }

    @Transactional
    public CalendarEntity createCalendar(int year, List<CalendarEvent> holidays) {
        CalendarEntity newCalendarEntity = new CalendarEntity();
        newCalendarEntity.setYear(year);
        holidays.stream().forEach(newCalendarEntity::addHoliday);
        em.persist(newCalendarEntity);
        return newCalendarEntity;
    }

    public void rollBizDate() {
        LOG.info("Rolling from {} to {}", getCurBizDate(), getNextBizDate());
        setCurBizDate(getNextBizDate());
    }

    // HELPERS


    protected CurBizDateEntity getCurBizDateEntity() {
        if (this.curBizDateEntity == null) {
            // SELECT
            TypedQuery<CurBizDateEntity> query = em.createQuery(
                    "SELECT d FROM CurBizDate d",
                    CurBizDateEntity.class);
            try {
                this.curBizDateEntity = query.getSingleResult();
            }
            catch(NoResultException ex)
            {

            }
        }
        return this.curBizDateEntity; // SELECT
    }

    public CalendarEntity getCalendarEntity(int year) {
        // SELECT
        TypedQuery<CalendarEntity> query = em.createQuery(
                "SELECT c FROM Calendar c WHERE c.year = :year",
                CalendarEntity.class);
        query.setParameter("year", 2017);
        CalendarEntity requested = query.getSingleResult();
        LOG.info("Found {}", requested);
        return requested;
    }

    public CalendarEntity getCalendarEntity() {
        if (this.calendarEntity == null) {
            // SELECT
            this.calendarEntity = getCalendarEntity(getCurBizDate().getYear());
        }
        return this.calendarEntity;
    }

}