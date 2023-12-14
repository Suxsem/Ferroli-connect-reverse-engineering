package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class DeferredAuthentication implements Authentication.Deferred {
    private static final Logger LOG = Log.getLogger((Class<?>) DeferredAuthentication.class);
    static final HttpServletResponse __deferredResponse = new HttpServletResponse() {
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

        public void flushBuffer() throws IOException {
        }

        public int getBufferSize() {
            return 1024;
        }

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public String getHeader(String str) {
            return null;
        }

        public Locale getLocale() {
            return null;
        }

        public int getStatus() {
            return 0;
        }

        public boolean isCommitted() {
            return true;
        }

        public void reset() {
        }

        public void resetBuffer() {
        }

        public void sendError(int i) throws IOException {
        }

        public void sendError(int i, String str) throws IOException {
        }

        public void sendRedirect(String str) throws IOException {
        }

        public void setBufferSize(int i) {
        }

        public void setCharacterEncoding(String str) {
        }

        public void setContentLength(int i) {
        }

        public void setContentType(String str) {
        }

        public void setDateHeader(String str, long j) {
        }

        public void setHeader(String str, String str2) {
        }

        public void setIntHeader(String str, int i) {
        }

        public void setLocale(Locale locale) {
        }

        public void setStatus(int i) {
        }

        public void setStatus(int i, String str) {
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return DeferredAuthentication.__nullOut;
        }

        public PrintWriter getWriter() throws IOException {
            return C2439IO.getNullPrintWriter();
        }

        public Collection<String> getHeaderNames() {
            return Collections.emptyList();
        }

        public Collection<String> getHeaders(String str) {
            return Collections.emptyList();
        }
    };
    /* access modifiers changed from: private */
    public static ServletOutputStream __nullOut = new ServletOutputStream() {
        public void print(String str) throws IOException {
        }

        public void println(String str) throws IOException {
        }

        public void write(int i) throws IOException {
        }
    };
    protected final LoginAuthenticator _authenticator;
    private Object _previousAssociation;

    public DeferredAuthentication(LoginAuthenticator loginAuthenticator) {
        if (loginAuthenticator != null) {
            this._authenticator = loginAuthenticator;
            return;
        }
        throw new NullPointerException("No Authenticator");
    }

    public Authentication authenticate(ServletRequest servletRequest) {
        try {
            Authentication validateRequest = this._authenticator.validateRequest(servletRequest, __deferredResponse, true);
            if (validateRequest != null && (validateRequest instanceof Authentication.User) && !(validateRequest instanceof Authentication.ResponseSent)) {
                IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
                if (identityService != null) {
                    this._previousAssociation = identityService.associate(((Authentication.User) validateRequest).getUserIdentity());
                }
                return validateRequest;
            }
        } catch (ServerAuthException e) {
            LOG.debug(e);
        }
        return this;
    }

    public Authentication authenticate(ServletRequest servletRequest, ServletResponse servletResponse) {
        try {
            IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
            Authentication validateRequest = this._authenticator.validateRequest(servletRequest, servletResponse, true);
            if ((validateRequest instanceof Authentication.User) && identityService != null) {
                this._previousAssociation = identityService.associate(((Authentication.User) validateRequest).getUserIdentity());
            }
            return validateRequest;
        } catch (ServerAuthException e) {
            LOG.debug(e);
            return this;
        }
    }

    public Authentication login(String str, Object obj, ServletRequest servletRequest) {
        UserIdentity login = this._authenticator.login(str, obj, servletRequest);
        if (login == null) {
            return null;
        }
        IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
        UserAuthentication userAuthentication = new UserAuthentication("API", login);
        if (identityService != null) {
            this._previousAssociation = identityService.associate(login);
        }
        return userAuthentication;
    }

    public Object getPreviousAssociation() {
        return this._previousAssociation;
    }

    public static boolean isDeferred(HttpServletResponse httpServletResponse) {
        return httpServletResponse == __deferredResponse;
    }
}
