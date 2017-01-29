package abacus.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Create and insert the Guice Injector into the ServletContext.
 */
public class WebGuiceServletConfig extends GuiceServletContextListener {

    private Logger log = LoggerFactory.getLogger(WebGuiceServletConfig.class);

    public WebGuiceServletConfig() {
        super();
        log.info("BEGIN constructor");

        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");

        log.info("END constructor");
    }

    /**
     * Place Guice Injector into the ServletContext.
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("WebGuiceServletConfig::contextInitialized");

        // No call to super as it also calls getInjector()
        ServletContext sc = servletContextEvent.getServletContext();
        sc.setAttribute(Injector.class.getName(), getInjector());
    }

    /**
     *  Remove the Injector
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("WebGuiceServletConfig::contextDestroyed");

        ServletContext sc = servletContextEvent.getServletContext();
        sc.removeAttribute(Injector.class.getName());
        super.contextDestroyed(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        Injector injector = null;
        try {
            if (injector == null) {
                injector = Guice.createInjector(new WebModule());
            }
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
            log.error("Guice.createInjector() failed",ex.getMessage(), ex);
        } finally {
            log.info("getInjector()" + injector);
        }
        return injector;
    }
}
