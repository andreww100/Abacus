package abacus.guice;

import com.google.inject.Injector;

import javax.servlet.jsp.PageContext;

/**
 * Provide access to the Injector placed into the context by WebGuiceServletConfig
 *
 * <code><% Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName()); %></code>
 */
public class JspInjector {

    public static Injector getInjector(PageContext pageContext)
    {
        return (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
    }
}
