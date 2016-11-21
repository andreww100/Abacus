package abacus.persist.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * ISO 3166-1 alpha-2 Country Codes
 *
 * @author Andrew Ward
 */
@Embeddable
public class CurrencyCodeField implements Serializable {

    public CurrencyCodeField()
    {}

    public CurrencyCodeField(String value) {
        setValue(value);
    }

    @Column(name="value", length = 3)
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
