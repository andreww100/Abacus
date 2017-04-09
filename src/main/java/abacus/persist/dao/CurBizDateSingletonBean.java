package abacus.persist.dao;

import abacus.persist.entities.CalendarEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;

/**
 * Singleton bean containing a cache of the current, next and previous business day.
 * <p>
 * TODO: Listen for Calendar changes, and check current, next and previous dates
 */
@Singleton
@Lock(LockType.READ)
public class CurBizDateSingletonBean {

    private static final Logger LOG = LoggerFactory.getLogger(CurBizDateSingletonBean.class);

    @Inject
    private CurBizDateDAO curBizDateDAO;

    @Inject
    private CalendarDAO calendarDAO;

    private LocalDate curBizDate = null;
    private LocalDate nextBizDate = null;
    private LocalDate prevBizDate = null;

    public LocalDate getCurBizDate() {
        return this.curBizDate;
    }

    @Lock(LockType.WRITE)
    public void setCurBizDate(LocalDate bizDate)
            throws IllegalArgumentException {
        // Check the date is a valid business date
        CalendarEntity calendarEntity = calendarDAO.getCalendarEntity(bizDate.getYear());
        if (!calendarEntity.isBizDate(bizDate)) {
            throw new IllegalArgumentException(String.format("The date %s is not a business date.", bizDate));
        }

        // Set the business date in the database
        this.curBizDateDAO.setCurBizDate(bizDate);

        // Cache the current business date and adjacent next and previous dates
        this.curBizDate = bizDate;
        this.nextBizDate = calendarDAO.getNextBizDate(bizDate);
        this.prevBizDate = calendarDAO.getPrevBizDate(bizDate);
    }

    public LocalDate getNextBizDate() {
        return this.nextBizDate;
    }

    public LocalDate getPrevBizDate() {
        return this.prevBizDate;
    }

    @Lock(LockType.WRITE)
    public void rollBizDate() {
        LOG.info("Rolling from {} to {}", getCurBizDate(), getNextBizDate());
        setCurBizDate(getNextBizDate());
    }
}