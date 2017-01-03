package abacus.persist.embeddables;

import abacus.domain.money.CurrencyCode;
import abacus.persist.converters.CurrencyCodeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Amount and Currency columns
 */
@Embeddable
public class MoneyFields implements Serializable {

    /**
     * Precision is the number of digits in a number. Scale is the number of digits to the right
     * of the decimal point in a number. For example, the number 123.45 has a precision of
     * 5 and a scale of 2.
     */
    @Column(precision = 10, scale=2)
    private BigDecimal amount;

    @Column(length = 3)
    private CurrencyCode currency;

    public MoneyFields() {
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
}
