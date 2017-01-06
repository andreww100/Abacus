package abacus.domain.posting;

import abacus.domain.money.Money;
import com.google.common.base.MoreObjects;

/**
 * Monetary total in an Account
 */
public class Balance {

    private long id;

    private long accountId;

    private Money value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("accountId", accountId)
                .add("value", value)
                .toString();
    }
}
