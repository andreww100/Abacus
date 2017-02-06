package abacus.persist.entities;

import abacus.domain.money.CurrencyCode;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * BalanceResource has a Primary Key of Account, Business Date and Currency
 */
public class BalancePK implements Serializable {

    /**
     * Account the BalanceResource is for.
     * NOTE: name of this field must exactly match that used in BalanceEntity
     */
    private long account;

    /**
     * Business Date the BalanceResource was recorded on
     */
    private LocalDate bizDate;

    /**
     * Currency of the balance.
     * NOTE: Accounts can have balances in multiple currencies
     */
    private CurrencyCode currency;

    public long getAccount() {
        return account;
    }

    public void setAccountId(long account) {
        this.account = account;
    }

    public LocalDate getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDate bizDate) {
        this.bizDate = bizDate;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalancePK balancePK = (BalancePK) o;

        if (account != balancePK.account) return false;
        if (bizDate != null ? !bizDate.equals(balancePK.bizDate) : balancePK.bizDate != null) return false;
        return currency != null ? currency.equals(balancePK.currency) : balancePK.currency == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (account ^ (account >>> 32));
        result = 31 * result + (bizDate != null ? bizDate.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("account", account)
                .add("bizDate", bizDate)
                .add("currency", currency)
                .toString();
    }
}
