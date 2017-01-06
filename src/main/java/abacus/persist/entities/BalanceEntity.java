package abacus.persist.entities;

import abacus.persist.embeddables.MoneyFields;
import org.eclipse.persistence.indirection.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Monatary total in an Account
 */
@Entity(name = "Balance")
public class BalanceEntity implements Serializable {
    @Transient
    private Logger log = LoggerFactory.getLogger(BalanceEntity.class);

    @Id
    private long id;

    @Transient
    private long accounntId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    @Embedded
    private MoneyFields value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MoneyFields getValue() {
        return value;
    }

    public void setValue(MoneyFields value) {
        this.value = value;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public long getAccounntId() {
        return getAccount().getId();
    }

    public void setAccounntId(long accounntId) {
        this.accounntId = accounntId;
    }
}
