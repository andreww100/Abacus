package abacus.domain.posting;

import abacus.domain.money.CurrencyCode;
import abacus.domain.money.Money;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Monetary total in an Account
 */
public class Balance {

    private long accountId;

    private LocalDate bizDate;

    private Money value;

    public long getAccountId() {
        return accountId;
    }

    public LocalDate getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDate bizDate) {
        this.bizDate = bizDate;
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

    public Balance()
    {
    }

    public Balance(long accountId, LocalDate bizDate, Money value) {
        this.accountId = accountId;
        this.bizDate = bizDate;
        this.value = value;
    }

    public Balance(long accountId, LocalDate bizDate, CurrencyCode currency, BigDecimal amount) {
        this.accountId = accountId;
        this.bizDate = bizDate;
        this.value = new Money(amount, currency);
    }

    public static List<Balance> toBalances(long accountId, LocalDate bizDate, List<Money> values) {
        List<Balance> output = new ArrayList<>();

        // Build a new list of Balances
        values.forEach(value -> output.add(new Balance(accountId, bizDate, value)));

        return output;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("bizDate", bizDate)
                .add("value", value)
                .toString();
    }
}
