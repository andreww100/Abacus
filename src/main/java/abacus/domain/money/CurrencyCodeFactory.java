package abacus.domain.money;

/**
 * Avoid creating common currencies repeatedly.
 * <p>
 * NOTE: Immutability of CurrencyCode enables safe instance re-use
 */
public class CurrencyCodeFactory {

    public final static CurrencyCode USD = new CurrencyCode("USD");
    public final static CurrencyCode GBP = new CurrencyCode("GBP");
    public final static CurrencyCode CHF = new CurrencyCode("CHF");

    /**
     * Return the CurrencyCode associated with the ISO 4217 code.
     *
     * @param value the ISO 4217 code
     * @return the CurrencyCode
     */
    public final static CurrencyCode getCurrencyCode(String value) {
        CurrencyCode code = null;

        // Check code vs pre-cached common codes
        switch (value) {
            case "USD":
                code = USD;
                break;
            case "GBP":
                code = GBP;
                break;
            case "CHF":
                code = CHF;
                break;
        }

        // Create a new CurrencyCode if not a common code
        return (code != null) ? code : new CurrencyCode(value);
    }
}
