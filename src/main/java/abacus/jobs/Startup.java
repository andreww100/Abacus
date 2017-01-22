package abacus.jobs;

import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Startup {

    private Logger log = LoggerFactory.getLogger(Startup.class);

    public void start(Injector injector) {
        log.info("BEGIN StartUp Job");

        // Do Work!
        StaticSetup setup = injector.getInstance(StaticSetup.class);
        setup.perform();
        setup.check();

        Postie postie = injector.getInstance(Postie.class);
        postie.perform();
        postie.check();

        log.info("END StartUp Job");
    }
}
