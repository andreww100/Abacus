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

@WebServlet(name = "WebApplicationServlet",
        urlPatterns = "/myController",
        loadOnStartup = 0, asyncSupported = true)
public class WebApplicationServlet extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(WebApplicationServlet.class);

    private int count;

    private StartupJob startUpJob;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("STARTING WebApplicationServlet!");
        super.init(config);
        getServletContext().log("init() called");
        count = 0;

        // Run the common StartUp job
        Injector injector = (Injector) config.getServletContext().getAttribute(Injector.class.getName());
        log.info("STARTING call start on " + startUpJob);
        startUpJob = injector.getInstance(StartupJob.class);
        startUpJob.start(injector);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().log("service() called");
        count++;
        response.getWriter().write("Incrementing the count: count = " + count);
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }

}