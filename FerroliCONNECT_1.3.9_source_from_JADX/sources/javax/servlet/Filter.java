package javax.servlet;

import java.io.IOException;

public interface Filter {
    void destroy();

    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException;

    void init(FilterConfig filterConfig) throws ServletException;
}
