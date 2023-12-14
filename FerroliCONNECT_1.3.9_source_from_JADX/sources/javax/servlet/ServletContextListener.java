package javax.servlet;

import java.util.EventListener;

public interface ServletContextListener extends EventListener {
    void contextDestroyed(ServletContextEvent servletContextEvent);

    void contextInitialized(ServletContextEvent servletContextEvent);
}
