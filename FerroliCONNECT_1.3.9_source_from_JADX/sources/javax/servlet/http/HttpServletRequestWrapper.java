package javax.servlet.http;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;

public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {
    public HttpServletRequestWrapper(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    public String getAuthType() {
        return _getHttpServletRequest().getAuthType();
    }

    public Cookie[] getCookies() {
        return _getHttpServletRequest().getCookies();
    }

    public long getDateHeader(String str) {
        return _getHttpServletRequest().getDateHeader(str);
    }

    public String getHeader(String str) {
        return _getHttpServletRequest().getHeader(str);
    }

    public Enumeration<String> getHeaders(String str) {
        return _getHttpServletRequest().getHeaders(str);
    }

    public Enumeration<String> getHeaderNames() {
        return _getHttpServletRequest().getHeaderNames();
    }

    public int getIntHeader(String str) {
        return _getHttpServletRequest().getIntHeader(str);
    }

    public String getMethod() {
        return _getHttpServletRequest().getMethod();
    }

    public String getPathInfo() {
        return _getHttpServletRequest().getPathInfo();
    }

    public String getPathTranslated() {
        return _getHttpServletRequest().getPathTranslated();
    }

    public String getContextPath() {
        return _getHttpServletRequest().getContextPath();
    }

    public String getQueryString() {
        return _getHttpServletRequest().getQueryString();
    }

    public String getRemoteUser() {
        return _getHttpServletRequest().getRemoteUser();
    }

    public boolean isUserInRole(String str) {
        return _getHttpServletRequest().isUserInRole(str);
    }

    public Principal getUserPrincipal() {
        return _getHttpServletRequest().getUserPrincipal();
    }

    public String getRequestedSessionId() {
        return _getHttpServletRequest().getRequestedSessionId();
    }

    public String getRequestURI() {
        return _getHttpServletRequest().getRequestURI();
    }

    public StringBuffer getRequestURL() {
        return _getHttpServletRequest().getRequestURL();
    }

    public String getServletPath() {
        return _getHttpServletRequest().getServletPath();
    }

    public HttpSession getSession(boolean z) {
        return _getHttpServletRequest().getSession(z);
    }

    public HttpSession getSession() {
        return _getHttpServletRequest().getSession();
    }

    public boolean isRequestedSessionIdValid() {
        return _getHttpServletRequest().isRequestedSessionIdValid();
    }

    public boolean isRequestedSessionIdFromCookie() {
        return _getHttpServletRequest().isRequestedSessionIdFromCookie();
    }

    public boolean isRequestedSessionIdFromURL() {
        return _getHttpServletRequest().isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromUrl() {
        return _getHttpServletRequest().isRequestedSessionIdFromUrl();
    }

    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return _getHttpServletRequest().authenticate(httpServletResponse);
    }

    public void login(String str, String str2) throws ServletException {
        _getHttpServletRequest().login(str, str2);
    }

    public void logout() throws ServletException {
        _getHttpServletRequest().logout();
    }

    public Collection<Part> getParts() throws IOException, ServletException {
        return _getHttpServletRequest().getParts();
    }

    public Part getPart(String str) throws IOException, ServletException {
        return _getHttpServletRequest().getPart(str);
    }
}
