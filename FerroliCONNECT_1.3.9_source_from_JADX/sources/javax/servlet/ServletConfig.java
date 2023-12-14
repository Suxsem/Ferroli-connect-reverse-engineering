package javax.servlet;

import java.util.Enumeration;

public interface ServletConfig {
    String getInitParameter(String str);

    Enumeration<String> getInitParameterNames();

    ServletContext getServletContext();

    String getServletName();
}
