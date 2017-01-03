package abacus.persist.entities;

import abacus.persist.embeddables.MoneyFields;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Posting")
public class PostingEntity implements Serializable
{
    @Id
    private long id;

    @Column(length=255)
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
}
