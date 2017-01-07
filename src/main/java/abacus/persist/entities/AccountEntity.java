package abacus.persist.entities;

import abacus.domain.money.CurrencyCode;
import abacus.domain.posting.Posting;
import com.google.common.base.MoreObjects;
import com.google.common.hash.HashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Account")
public class AccountEntity implements Serializable
{
    @Transient
    private Logger log = LoggerFactory.getLogger(AccountEntity.class);

    @Id
    @TableGenerator(name="IDGenAccount", allocationSize=1,initialValue =1000)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="IDGenAccount")
    //@Access(AccessType.PROPERTY)
    private long id;

    private String name;

    @Column(length = 3)
    private CurrencyCode baseCur;

    @Access(AccessType.PROPERTY)
    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;

        this.id = HashCode.fromLong(id).asLong();

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("baseCur", baseCur)
                .toString();
    }

}
