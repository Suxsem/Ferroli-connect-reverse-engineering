package org.eclipse.jetty.servlet;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;

public class JspPropertyGroupServlet extends GenericServlet {
    public static final String NAME = "__org.eclipse.jetty.servlet.JspPropertyGroupServlet__";
    private static final long serialVersionUID = 3681783214726776945L;
    private final ContextHandler _contextHandler;
    private ServletHolder _dftServlet;
    private ServletHolder _jspServlet;
    private final ServletHandler _servletHandler;
    private boolean _starJspMapped;

    public JspPropertyGroupServlet(ContextHandler contextHandler, ServletHandler servletHandler) {
        this._contextHandler = contextHandler;
        this._servletHandler = servletHandler;
    }

    public void init() throws ServletException {
        String str;
        ServletMapping servletMapping = this._servletHandler.getServletMapping("*.jsp");
        if (servletMapping != null) {
            this._starJspMapped = true;
            ServletMapping servletMapping2 = servletMapping;
            for (ServletMapping servletMapping3 : this._servletHandler.getServletMappings()) {
                String[] pathSpecs = servletMapping3.getPathSpecs();
                if (pathSpecs != null) {
                    ServletMapping servletMapping4 = servletMapping2;
                    for (String equals : pathSpecs) {
                        if ("*.jsp".equals(equals) && !NAME.equals(servletMapping3.getServletName())) {
                            servletMapping4 = servletMapping3;
                        }
                    }
                    servletMapping2 = servletMapping4;
                }
            }
            str = servletMapping2.getServletName();
        } else {
            str = "jsp";
        }
        this._jspServlet = this._servletHandler.getServlet(str);
        ServletMapping servletMapping5 = this._servletHandler.getServletMapping("/");
        this._dftServlet = this._servletHandler.getServlet(servletMapping5 != null ? servletMapping5.getServletName() : "default");
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String str;
        String str2;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if (httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
                str2 = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
                str = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
                if (str2 == null) {
                    str2 = httpServletRequest.getServletPath();
                    str = httpServletRequest.getPathInfo();
                }
            } else {
                str2 = httpServletRequest.getServletPath();
                str = httpServletRequest.getPathInfo();
            }
            String addPaths = URIUtil.addPaths(str2, str);
            if (addPaths.endsWith("/")) {
                this._dftServlet.getServlet().service(servletRequest, servletResponse);
            } else if (!this._starJspMapped || !addPaths.toLowerCase().endsWith(".jsp")) {
                Resource resource = this._contextHandler.getResource(addPaths);
                if (resource == null || !resource.isDirectory()) {
                    this._jspServlet.getServlet().service(servletRequest, servletResponse);
                } else {
                    this._dftServlet.getServlet().service(servletRequest, servletResponse);
                }
            } else {
                this._jspServlet.getServlet().service(servletRequest, servletResponse);
            }
        } else {
            throw new ServletException("Request not HttpServletRequest");
        }
    }
}
