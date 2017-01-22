package abacus.guice;

import abacus.jobs.Startup;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CmdApplication
{
    private Logger log = LoggerFactory.getLogger(CmdApplication.class);

    public static void main(String[] args)
    {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");

        Injector injector = Guice.createInjector(new CmdModule());

        // Start the CmdInitializer
        injector.getInstance(CmdInitializer.class);

        // Run the common StartUp job
        Startup startUpJob = injector.getInstance(Startup.class);
        startUpJob.start(injector);
    }


}