package abacus.domain.posting;

import abacus.domain.money.Money;
import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A Debit or Credit of Money against an Account
 */
public class Posting {

    private long id;

    private long accountId;

    private LocalDate bizDate;

    private LocalTime bizTime;

    private Money value;

    private String description;


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

    public LocalDate getBizDate() {
        return bizDate;
    }

    public void setBizDate(LocalDate bizDate) {
        this.bizDate = bizDate;
    }

    public LocalTime getBizTime() {
        return bizTime;
    }

    public void setBizTime(LocalTime bizTime) {
        this.bizTime = bizTime;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("accountId", accountId)
                .add("bizDate", bizDate)
                .add("bizTime", bizTime)
                .add("value", value)
                .add("description", description)
                .toString();
    }
}
