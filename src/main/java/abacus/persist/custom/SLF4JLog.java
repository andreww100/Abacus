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

    /**
     * @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(SessionLogEntry sessionLogEntry) {

        // Only log events we are interested in
        if (isInteresting(sessionLogEntry.getMessage())) {
            String message = format(sessionLogEntry);
            // Map EclipseLink levels to SLF4J levels
            switch (sessionLogEntry.getLevel()) {
                case SEVERE:
                    log.error(message);
                    break;

                case WARNING:
                    log.warn(message);
                    break;

                case INFO:
                case CONFIG:
                case ALL:
                    log.info(message);
                    break;

                case FINE:
                case FINER:
                case FINEST:
                    log.debug(message);
                    break;

                case OFF:
                    break;
            }
        }
    }

    /**
     **/
    protected String format(SessionLogEntry sessionLogEntry) {
        StringBuilder builder = new StringBuilder();

        // Level
        builder.append("[" + sessionLogEntry.getLevel() + "]");

        // Unpack the message and its parameters
        if (sessionLogEntry.hasMessage()) {
            String message = sessionLogEntry.getMessage();
            builder.append(message);

            if (sessionLogEntry.getParameters() != null) {
                for (Object param : sessionLogEntry.getParameters()) {
                    if (param != null) {
                        builder.append(" > " + param.toString());
                    }
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

    /**
     * Filter out messages that we are not interested in
     *
     * @param message
     * @return true if we wish to log
     */
    protected boolean isInteresting(String message) {
        boolean interesting = true;

        switch (message) {
            case "acquire_connection":
            case "release_connection":
            case "register_new_for_persist":
            case "dbPlatformHelper_regExprDbPlatform":
            case "acquire_unit_of_work_with_argument":
            case "release_unit_of_work":
            case "client_acquired":
            case "client_released":
            case "initialize_identitymaps":
            case "register_existing":
                interesting = false;
                break;
        }

        return interesting;
    }
}