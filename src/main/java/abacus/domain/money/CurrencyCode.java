package abacus.domain.money;

import javax.persistence.Embeddable;

/**
 * ISO 4217 Currency Code
 * <p>
 * NOTE: Class is deliberately immutable (i.e., ISO code value cannot be modified)
 */
public class CurrencyCode {

    public static final int REQUIRED_CHARS = 3;

    final private String value;

    public CurrencyCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyCode that = (CurrencyCode) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
