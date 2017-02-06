package abacus.util;

/**
 * Helper functions
 */
public class StringUtil {

    public static String safeToString(Object o)
    {
        final String NULL = "null";
        return (o!=null) ? o.toString() : NULL;
    }
}
