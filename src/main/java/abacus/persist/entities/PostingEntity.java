package abacus.persist.entities;

import abacus.persist.embeddables.MoneyFields;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Credit or Debit Posting to an Account
 */
@Entity(name = "Posting")
public class PostingEntity implements Serializable {
    @Transient
    private static final Logger LOG = LoggerFactory.getLogger(PostingEntity.class);

    @Id
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    @Column(insertable = false, updatable = false)
    private long accountId;

    /**
     * Business Date the Posting was recorded on
     */
    @Temporal(TemporalType.DATE)
    private LocalDate postingDate;

    /**
     * Business Time the Posting was recorded on
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

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

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDate bizDate) {
        this.postingDate = bizDate;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
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
                .add("account", account)
                .add("accountId", accountId)
                .add("postingDate", postingDate)
                .add("transactionDate", transactionDate)
                .add("value", value)
                .add("description", description)
                .toString();
    }
}
