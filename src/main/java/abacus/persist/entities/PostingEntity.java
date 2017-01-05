package abacus.persist.entities;

import abacus.persist.embeddables.MoneyFields;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Posting")
public class PostingEntity implements Serializable {
    @Id
    private long id;

    @Column
    private long accountId;

    /**
     * Foreign key relationship from Posting to Account
     * <p>
     * NOTE: This attribute only exists to register the foreign key relationship
     * and to enable lazy loading of the related Account.
     * 1) To avoid creating another database column, we re-use the "accountId" name.
     * 2) JPA demands that the duplicate attribute is non-insertable and non-updatable
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
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
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
        /* The account entity reference is not persisted, so copy the id */
        setAccountId(account.getId());
    }
}
