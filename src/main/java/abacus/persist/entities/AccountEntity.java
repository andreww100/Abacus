package abacus.persist.entities;

import abacus.persist.embeddables.CurrencyCodeField;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Account")
public class AccountEntity implements Serializable
{
    @Id
    private long id;

    private String name;

    @Column
    @AttributeOverrides({
            @AttributeOverride(name="value",
                    column=@Column(name="base_cur", length=3))
    })
    private CurrencyCodeField baseCur;

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

    public CurrencyCodeField getBaseCurrency() {
        return baseCur;
    }

    public void setBaseCurrency(CurrencyCodeField cur) {
        this.baseCur = cur;
    }
}
