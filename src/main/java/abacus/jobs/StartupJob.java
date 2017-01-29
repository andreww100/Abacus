package abacus.jobs;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class StartupJob {

    private Logger log = LoggerFactory.getLogger(StartupJob.class);

    public void start(Injector injector) {
        log.info("BEGIN StartUp Job");

        // Do Work!
        AccountJob setup = injector.getInstance(AccountJob.class);
        setup.perform();
        setup.check();

        PostingJob postie = injector.getInstance(PostingJob.class);
        postie.perform();
        postie.check();

        log.info("END StartUp Job");
    }
}
