package javax.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class ServletRequestWrapper implements ServletRequest {
    private ServletRequest request;

    public ServletRequestWrapper(ServletRequest servletRequest) {
        if (servletRequest != null) {
            this.request = servletRequest;
            return;
        }
        throw new IllegalArgumentException("Request cannot be null");
    }

    public ServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(ServletRequest servletRequest) {
        if (servletRequest != null) {
            this.request = servletRequest;
            return;
        }
        throw new IllegalArgumentException("Request cannot be null");
    }

    public Object getAttribute(String str) {
        return this.request.getAttribute(str);
    }

    public Enumeration<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }

    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    public void setCharacterEncoding(String str) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(str);
    }

    public int getContentLength() {
        return this.request.getContentLength();
    }

    public String getContentType() {
        return this.request.getContentType();
    }

    public ServletInputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }

    public String getParameter(String str) {
        return this.request.getParameter(str);
    }

    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }

    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }

    public String[] getParameterValues(String str) {
        return this.request.getParameterValues(str);
    }

    public String getProtocol() {
        return this.request.getProtocol();
    }

    public String getScheme() {
        return this.request.getScheme();
    }

    public String getServerName() {
        return this.request.getServerName();
    }

    public int getServerPort() {
        return this.request.getServerPort();
    }

    public BufferedReader getReader() throws IOException {
        return this.request.getReader();
    }

    public String getRemoteAddr() {
        return this.request.getRemoteAddr();
    }

    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }

    public void setAttribute(String str, Object obj) {
        this.request.setAttribute(str, obj);
    }

    public void removeAttribute(String str) {
        this.request.removeAttribute(str);
    }

    public Locale getLocale() {
        return this.request.getLocale();
    }

    public Enumeration<Locale> getLocales() {
        return this.request.getLocales();
    }

    public boolean isSecure() {
        return this.request.isSecure();
    }

    public RequestDispatcher getRequestDispatcher(String str) {
        return this.request.getRequestDispatcher(str);
    }

    public String getRealPath(String str) {
        return this.request.getRealPath(str);
    }

    public int getRemotePort() {
        return this.request.getRemotePort();
    }

    public String getLocalName() {
        return this.request.getLocalName();
    }

    public String getLocalAddr() {
        return this.request.getLocalAddr();
    }

    public int getLocalPort() {
        return this.request.getLocalPort();
    }

    public ServletContext getServletContext() {
        return this.request.getServletContext();
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return this.request.startAsync();
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return this.request.startAsync(servletRequest, servletResponse);
    }

    public boolean isAsyncStarted() {
        return this.request.isAsyncStarted();
    }

    public boolean isAsyncSupported() {
        return this.request.isAsyncSupported();
    }

    public AsyncContext getAsyncContext() {
        return this.request.getAsyncContext();
    }

    public boolean isWrapperFor(ServletRequest servletRequest) {
        ServletRequest servletRequest2 = this.request;
        if (servletRequest2 == servletRequest) {
            return true;
        }
        if (servletRequest2 instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) servletRequest2).isWrapperFor(servletRequest);
        }
        return false;
    }

    public boolean isWrapperFor(Class cls) {
        if (!ServletRequest.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Given class " + cls.getName() + " not a subinterface of " + ServletRequest.class.getName());
        } else if (cls.isAssignableFrom(this.request.getClass())) {
            return true;
        } else {
            ServletRequest servletRequest = this.request;
            if (servletRequest instanceof ServletRequestWrapper) {
                return ((ServletRequestWrapper) servletRequest).isWrapperFor(cls);
            }
            return false;
        }
    }

    public DispatcherType getDispatcherType() {
        return this.request.getDispatcherType();
    }
}
