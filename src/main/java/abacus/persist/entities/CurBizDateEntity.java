package abacus.persist.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Entity(name = "CurBizDate")
public class CurBizDateEntity {

    @Id
    @Temporal(TemporalType.DATE)
    private LocalDate bizDate;

    public LocalDate getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDate bizDate) {
        this.bizDate = bizDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bizDate", bizDate)
                .toString();
    }
}
