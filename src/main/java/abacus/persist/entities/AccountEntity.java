package abacus.persist.entities;

import abacus.domain.money.CurrencyCode;
import abacus.domain.posting.Posting;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Account")
public class AccountEntity implements Serializable
{
    @Id
    private long id;

    private String name;

    @Column(length = 3)
    private CurrencyCode baseCur;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyCode getBaseCurrency() {
        return baseCur;
    }

    public void setBaseCurrency(CurrencyCode cur) {
        this.baseCur = cur;
    }
}
