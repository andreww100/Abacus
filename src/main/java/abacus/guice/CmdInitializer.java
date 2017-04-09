package abacus.guice;

import com.google.inject.persist.PersistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

class CmdInitializer {

    private Logger log = LoggerFactory.getLogger(CmdInitializer.class);

    /**
     * Called once the CmdModule has loaded.
     * Start the JPA singletons
     *
     * @param service start JPA
     */
    @Inject protected CmdInitializer(PersistService service) {
        service.start();

        // At this point JPA is started and ready.
        log.info("Started PersistService - " + service.toString());
    }
}