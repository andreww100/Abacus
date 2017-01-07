package abacus.persist.entities;

import abacus.jobs.Postie;
import abacus.persist.embeddables.MoneyFields;
import org.eclipse.persistence.indirection.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Method;

@Entity(name = "Posting")
public class PostingEntity implements Serializable {
    @Transient
    private Logger log = LoggerFactory.getLogger(PostingEntity.class);

    @Id
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    @Column(length = 255)
    private String description;

    @Embedded
    private MoneyFields value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MoneyFields getValue() {
        return value;
    }

    public void setValue(MoneyFields value) {
        this.value = value;
    }

    public long getAccountId() {
        return getAccount().getId();
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public void state(String var1) throws Exception
    {
        Class tClass = getClass();

        // public void _persistence_checkFetched(String var1) {

        Method method = tClass.getMethod("_persistence_isAttributeFetched", new Class[] { String.class });
        log.info("calling with 'val2'");
        Object ret = method.invoke(this, new Object[] { var1 });

        log.info(ret.toString());

        method = tClass.getMethod("_persistence_get_account_vh", new Class[]{});
        log.info("calling with:");
        ret = method.invoke(this, new Object[]{});
        ValueHolder vh = (ValueHolder) ret;
        log.info(vh.toString());
    }

}
