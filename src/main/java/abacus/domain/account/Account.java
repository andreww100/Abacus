package abacus.domain.account;

import abacus.domain.money.CurrencyCode;
import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * An Account has entries posted against it
 */
@XmlRootElement
public class Account {

    private long id;
    private String name;
    private CurrencyCode baseCur;

    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyCode getBaseCurrency() {
        return this.baseCur;
    }

    public void setBaseCurrency(CurrencyCode cur) {
        this.baseCur = cur;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("baseCur", baseCur)
                .toString();
    }
}
