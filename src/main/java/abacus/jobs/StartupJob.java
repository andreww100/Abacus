package abacus.jobs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class StartupJob {

    private Logger log = LoggerFactory.getLogger(StartupJob.class);

    @Inject
    private CalendarJob calendarJob;

    @Inject
    private AccountJob accountJob;

    @Inject
    private PostingJob postingJob;

    public void start() {
        log.info("BEGIN StartUp Job");

        // Set up calendars and biz date
        calendarJob.perform();

        // Set up accounts
        accountJob.perform();
        accountJob.check();

        // Insert sample postings
        postingJob.perform();
        postingJob.check();

        log.info("END StartUp Job");
    }
}
