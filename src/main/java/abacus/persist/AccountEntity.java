package abacus.persist;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Account")
public class AccountEntity
{
    private String id;
    private String name;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
