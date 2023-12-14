package org.eclipse.jetty.server;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ServletResponseHttpWrapper extends ServletResponseWrapper implements HttpServletResponse {
    public void addCookie(Cookie cookie) {
    }

    public void addDateHeader(String str, long j) {
    }

    public void addHeader(String str, String str2) {
    }

    public void addIntHeader(String str, int i) {
    }

    public boolean containsHeader(String str) {
        return false;
    }

    public String encodeRedirectURL(String str) {
        return null;
    }

    public String encodeRedirectUrl(String str) {
        return null;
    }

    public String encodeURL(String str) {
        return null;
    }

    public String encodeUrl(String str) {
        return null;
    }

    public String getHeader(String str) {
        return null;
    }

    public Collection<String> getHeaderNames() {
        return null;
    }

    public Collection<String> getHeaders(String str) {
        return null;
    }

    public int getStatus() {
        return 0;
    }

    public void sendError(int i) throws IOException {
    }

    public void sendError(int i, String str) throws IOException {
    }

    public void sendRedirect(String str) throws IOException {
    }

    public void setDateHeader(String str, long j) {
    }

    public void setHeader(String str, String str2) {
    }

    public void setIntHeader(String str, int i) {
    }

    public void setStatus(int i) {
    }

    public void setStatus(int i, String str) {
    }

    public ServletResponseHttpWrapper(ServletResponse servletResponse) {
        super(servletResponse);
    }
}
