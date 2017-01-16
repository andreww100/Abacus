package abacus.persist.entities;

import abacus.jobs.Postie;
import abacus.persist.embeddables.MoneyFields;
import com.google.common.base.MoreObjects;
import org.eclipse.persistence.indirection.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Represents a Credit or Debit Posting to an Account
 */
@Entity(name = "Posting")
public class PostingEntity implements Serializable {
    @Transient
    private Logger log = LoggerFactory.getLogger(PostingEntity.class);

    @Id
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    /**
     * Business Date & Time the Posting was recorded on
     * TODO: Consider splitting date from time
     */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime bizDate;

    @Embedded
    private MoneyFields value;

    @Column(length = 255)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public LocalDateTime getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDateTime bizDate) {
        this.bizDate = bizDate;
    }

    public MoneyFields getValue() {
        return value;
    }

    public void setValue(MoneyFields value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Helper

    public long getAccountId() {
        return getAccount().getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("accountId", getAccountId())
                .add("bizDate", bizDate)
                .add("value", value)
                .add("description", description)
                .toString();
    }
}
