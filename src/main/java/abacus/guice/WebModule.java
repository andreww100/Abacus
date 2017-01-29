package abacus.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WebModule extends ServletModule {

    private Logger log = LoggerFactory.getLogger(WebModule.class);

    private final String PU_NAME = "AbacusPUJTA";

    static private Boolean STARTED = false;

    protected void configureServlets() {

        synchronized (STARTED) {
            if (STARTED.booleanValue() == false) {
                log.info("Starting configureServlets");

                super.configureServlets();
                install(new JpaPersistModule(PU_NAME));
                filter("/*").through(PersistFilter.class);

                log.info("Ended configureServlets");
                STARTED = true;
            } else {
                log.error("Repeated call to start configureServlets ignored");
            }
        }
    }
}
