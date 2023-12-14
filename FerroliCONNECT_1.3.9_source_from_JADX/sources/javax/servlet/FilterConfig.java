package javax.servlet;

import java.util.Enumeration;

public interface FilterConfig {
    String getFilterName();

    String getInitParameter(String str);

    Enumeration<String> getInitParameterNames();

    ServletContext getServletContext();
}
