package javax.servlet.http;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletResponseWrapper;

public class HttpServletResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {
    public HttpServletResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    public void addCookie(Cookie cookie) {
        _getHttpServletResponse().addCookie(cookie);
    }

    public boolean containsHeader(String str) {
        return _getHttpServletResponse().containsHeader(str);
    }

    public String encodeURL(String str) {
        return _getHttpServletResponse().encodeURL(str);
    }

    public String encodeRedirectURL(String str) {
        return _getHttpServletResponse().encodeRedirectURL(str);
    }

    public String encodeUrl(String str) {
        return _getHttpServletResponse().encodeUrl(str);
    }

    public String encodeRedirectUrl(String str) {
        return _getHttpServletResponse().encodeRedirectUrl(str);
    }

    public void sendError(int i, String str) throws IOException {
        _getHttpServletResponse().sendError(i, str);
    }

    public void sendError(int i) throws IOException {
        _getHttpServletResponse().sendError(i);
    }

    public void sendRedirect(String str) throws IOException {
        _getHttpServletResponse().sendRedirect(str);
    }

    public void setDateHeader(String str, long j) {
        _getHttpServletResponse().setDateHeader(str, j);
    }

    public void addDateHeader(String str, long j) {
        _getHttpServletResponse().addDateHeader(str, j);
    }

    public void setHeader(String str, String str2) {
        _getHttpServletResponse().setHeader(str, str2);
    }

    public void addHeader(String str, String str2) {
        _getHttpServletResponse().addHeader(str, str2);
    }

    public void setIntHeader(String str, int i) {
        _getHttpServletResponse().setIntHeader(str, i);
    }

    public void addIntHeader(String str, int i) {
        _getHttpServletResponse().addIntHeader(str, i);
    }

    public void setStatus(int i) {
        _getHttpServletResponse().setStatus(i);
    }

    public void setStatus(int i, String str) {
        _getHttpServletResponse().setStatus(i, str);
    }

    public int getStatus() {
        return _getHttpServletResponse().getStatus();
    }

    public String getHeader(String str) {
        return _getHttpServletResponse().getHeader(str);
    }

    public Collection<String> getHeaders(String str) {
        return _getHttpServletResponse().getHeaders(str);
    }

    public Collection<String> getHeaderNames() {
        return _getHttpServletResponse().getHeaderNames();
    }
}
