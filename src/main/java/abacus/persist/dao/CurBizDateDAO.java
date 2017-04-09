package abacus.persist.dao;

import abacus.persist.entities.CurBizDateEntity;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

/**
 * Get and Set the current business day
 *
 * Package scoped class, application should use CurBizDateSingletonBean to access cached cur, next and prev dates.
 */
class CurBizDateDAO {

    private Logger LOG = LoggerFactory.getLogger(CurBizDateDAO.class);

    @javax.inject.Inject
    private EntityManager em;

    public LocalDate getCurBizDate() {
        return getCurBizDateEntity().getBizDate();
    }

    @Transactional
    public void setCurBizDate(LocalDate bizDate)
            throws IllegalArgumentException {
        // Update the date, retain the Calendar and invalidate the next/prev date cache
        // Update the date
        CurBizDateEntity curBizDateEntity = getCurBizDateEntity();
        curBizDateEntity.setBizDate(bizDate);
        LOG.info("Setting current business date to {}", bizDate);
        em.persist(curBizDateEntity);
    }

    protected CurBizDateEntity getCurBizDateEntity() {
        CurBizDateEntity curBizDateEntity = null;

        // SELECT
        TypedQuery<CurBizDateEntity> query = em.createQuery(
                "SELECT d FROM CurBizDate d",
                CurBizDateEntity.class);
        try {
            curBizDateEntity = query.getSingleResult();
        } catch (NoResultException ex) {
            curBizDateEntity = new CurBizDateEntity();
        }
        return curBizDateEntity; // SELECT
    }
}
