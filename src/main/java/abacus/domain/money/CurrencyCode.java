package abacus.domain.money;

/**
 * ISO 4217 Currency Codes
 *
 * @author Andrew Ward
 */
public class CurrencyCode  {

    public static final int REQUIRED_CHARS = 3;

    private String value;

    public CurrencyCode() {
    }

    public CurrencyCode(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
