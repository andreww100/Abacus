package abacus.persist.entities;

import abacus.domain.money.CurrencyCode;
import abacus.domain.money.Money;
import abacus.domain.posting.Balance;
import abacus.persist.embeddables.MoneyFields;
import com.google.common.base.MoreObjects;
import org.eclipse.persistence.indirection.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Daily monetary total in an Account.
 * NOTE: An account can have balances in multiple currencies
 */
@Entity(name = "Balance")
@IdClass(BalancePK.class)
public class BalanceEntity implements Serializable {

    @Transient
    private static final Logger LOG = LoggerFactory.getLogger(BalanceEntity.class);

    /**
     * Account the BalanceResource is for
     */
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    /**
     * Business Date the BalanceResource was recorded on
     */
    @Id
    @Temporal(TemporalType.DATE)
    private LocalDate bizDate;

    /**
     * Currency of the balance.
     * (Accounts can have balances in multiple currencies)
     */
    @Id
    @Column(length = 3)
    private CurrencyCode currency;

    @Column
    private BigDecimal amount;

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public LocalDate getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDate bizDate) {
        this.bizDate = bizDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Helpers

    public long getAccountId() {
        return getAccount().getId();
    }

    public Money getValue() {
        return new Money(this.amount, this.currency);
    }

    public void setValue(Money value) {
        setAmount(value.getAmount());
        setCurrency(value.getCurrency());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", getAccountId())
                .add("currency", currency)
                .add("bizDate", bizDate)
                .add("amount", amount)
                .toString();
    }
}
