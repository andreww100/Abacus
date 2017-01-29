package abacus.guice;

import abacus.jobs.StartupJob;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "WebInitializerServlet",
        urlPatterns = "/myController",
        loadOnStartup = 0, asyncSupported = true)
public class WebInitializerServlet extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(WebInitializerServlet.class);

    /**
     * NOTE: there is no need to start the Persistence Service, as the Guice
     * PersistFilter already does this.
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("BEGIN");

        super.init(config);
        getServletContext().log("init() called");

        // Run the common StartUp job
        Injector injector = (Injector) config.getServletContext().getAttribute(Injector.class.getName());
        StartupJob startUpJob = injector.getInstance(StartupJob.class);
        startUpJob.start();

        log.info("END");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().log("service() called");
        response.getWriter().write("Hello, World!");
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }

}