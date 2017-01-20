package abacus.domain.money;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents an amount of a currency
 */
public class Money {

    private BigDecimal amount;

    private CurrencyCode currency;

    public Money() {
    }

    public Money(BigDecimal amount, CurrencyCode currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public static List<Money> aggregate(List<Money> input) {
        List<Money> output = new ArrayList<>();

        // Merge insert all monetary amounts into a Map, keyed by CurrencyCode
        HashMap<CurrencyCode, BigDecimal> map = new HashMap<>();
        input.forEach(value -> map.merge(value.getCurrency(), value.getAmount(), BigDecimal::add));

        // Populate the output list with Money objects built from the map
        map.forEach((k, v) -> output.add(new Money(v, k)));

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amount != null ? !amount.equals(money.amount) : money.amount != null) return false;
        return currency != null ? currency.equals(money.currency) : money.currency == null;

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("amount", amount)
                .add("currency", currency)
                .toString();
    }
}

