package abacus.persist.sequences;

import abacus.jobs.StaticSetup;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAbstractSessionLog extends AbstractSessionLog implements SessionLog, SessionCustomizer {

    private Logger log = LoggerFactory.getLogger(CustomAbstractSessionLog.class);

    public void customize(Session aSession) throws Exception {

        // create a custom logger
        SessionLog aCustomLogger = new CustomAbstractSessionLog();
        aCustomLogger.setLevel(1); // Logging level finest
        aSession.setSessionLog(aCustomLogger);
    }

    /* @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(SessionLogEntry sessionLogEntry) {
        log.info("CUSTOM: " + sessionLogEntry.getMessage()); // untranslated/undecoded message_id
    }
}