package abacus.rest;

import javax.ws.rs.ApplicationPath;

// WE MUST USE javax.inject.Inject NOT com.google.inject.Inject;
// This is because the Jersey Servlet is using HK2 not Guice
import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.hk2.api.ServiceLocator;

import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import abacus.guice.WebGuiceServletConfig;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationPath("resources")
public class AbacusJAXRSApp extends ResourceConfig {

    private Logger log = LoggerFactory.getLogger(AbacusJAXRSApp.class);

    @Inject
    public AbacusJAXRSApp(ServiceLocator serviceLocator) {
        super(AccountResource.class, BalanceResource.class);

        enableGuiceBridge(serviceLocator);
    }

    /**
     * Jersey uses the HK2 dependency injection library - not Guice.
     * In order to use DAOs etc., it is therefore necessary to bridge from HK2 to Guice
     *
     * @param serviceLocator
     * @See https://hk2.java.net/guice-bridge/
     */
    private void enableGuiceBridge(ServiceLocator serviceLocator) {
        log.info("Enable the HK2 to Guice Bridge");

        // Create the Bridge using the HK2 Service Locator
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);

        // Bridge to the Injector created by the WebGuiceServletConfig
        Injector injector = WebGuiceServletConfig.injector;

        if (injector != null) {
            guiceBridge.bridgeGuiceInjector(injector);
            log.info("Registered: " + injector);
        } else {
            log.error("WebGuiceServletConfig has NULL injector!");
        }
    }
}
