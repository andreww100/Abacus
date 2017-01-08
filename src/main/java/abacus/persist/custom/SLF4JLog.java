package abacus.persist.custom;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bridge EclipseLink logging to SLF4J
 */
public class SLF4JLog extends AbstractSessionLog implements SessionLog, SessionCustomizer {

    private Logger log = LoggerFactory.getLogger(SLF4JLog.class);

    public void customize(Session aSession) throws Exception {

        // create a custom logger
        SessionLog aCustomLogger = new SLF4JLog();
        aCustomLogger.setLevel(1); // Logging level finest
        aSession.setSessionLog(aCustomLogger);
    }

    /* @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(SessionLogEntry sessionLogEntry) {

        switch (sessionLogEntry.getLevel()) {
            case OFF:
                break;

            case SEVERE:
                log.error(helper(sessionLogEntry));
                break;

            case WARNING:
                log.warn(helper(sessionLogEntry));
                break;

            case INFO:
            case CONFIG:
            case FINE:
            case FINER:
            case FINEST:
            case ALL:
                if (isInteresting(sessionLogEntry.getMessage())) {
                    log.info(helper(sessionLogEntry));
                }
        }
    }


    /**
     * public AbstractSession getSession() {
     * public Accessor getConnection() {
     * <p>
     * public Object[] getParameters() {
     * public String getNameSpace() {
     * <p>
     * public boolean shouldTranslate() {
     * public int getLevel() {
     **/
    protected String helper(SessionLogEntry sessionLogEntry) {
        StringBuilder builder = new StringBuilder();

        // Level
        builder.append("[" + sessionLogEntry.getLevel() + "]");

        // Message
        if (sessionLogEntry.hasMessage()) {
            String message = sessionLogEntry.getMessage();
            builder.append(message);

            if ("execute_query".equalsIgnoreCase(message)) {
                for (Object param : sessionLogEntry.getParameters()) {
                    builder.append(" > " + param);
                }
            }
        }

        if (sessionLogEntry.hasException()) {
            builder.append(sessionLogEntry.getException());
        }

        if (sessionLogEntry.getSourceClassName() != null) {
            builder.append("source:=" + sessionLogEntry.getSourceClassName() + "#" + sessionLogEntry.getSourceMethodName());
        }
        return builder.toString();
    }

    protected boolean isInteresting(String message) {
        boolean interesting = true;

        switch (message) {
            case "acquire_connection":
            case "release_connection":
            case "register_new_for_persist":
                interesting = false;
                break;
        }

        return interesting;
    }
}