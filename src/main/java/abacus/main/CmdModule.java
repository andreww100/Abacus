package abacus.main;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CmdModule extends AbstractModule
{
    private Logger log = LoggerFactory.getLogger(CmdModule.class);

    private final String PU_NAME = "AbacusPUNonJTA";

    @Override
    protected void configure()
    {
        log.info("configure");
        JpaPersistModule jpaModule = new JpaPersistModule(PU_NAME);
        install(jpaModule);
    }
}