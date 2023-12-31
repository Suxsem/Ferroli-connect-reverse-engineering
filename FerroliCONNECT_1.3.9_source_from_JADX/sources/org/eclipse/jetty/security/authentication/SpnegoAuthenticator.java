package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Constraint;

public class SpnegoAuthenticator extends LoginAuthenticator {
    private static final Logger LOG = Log.getLogger((Class<?>) SpnegoAuthenticator.class);
    private String _authMethod = Constraint.__SPNEGO_AUTH;

    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    public SpnegoAuthenticator() {
    }

    public SpnegoAuthenticator(String str) {
        this._authMethod = str;
    }

    public String getAuthMethod() {
        return this._authMethod;
    }

    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z) throws ServerAuthException {
        UserIdentity login;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (!z) {
            return new DeferredAuthentication(this);
        }
        if (header == null) {
            try {
                if (DeferredAuthentication.isDeferred(httpServletResponse)) {
                    return Authentication.UNAUTHENTICATED;
                }
                LOG.debug("SpengoAuthenticator: sending challenge", new Object[0]);
                httpServletResponse.setHeader(HttpHeaders.WWW_AUTHENTICATE, HttpHeaders.NEGOTIATE);
                httpServletResponse.sendError(401);
                return Authentication.SEND_CONTINUE;
            } catch (IOException e) {
                throw new ServerAuthException((Throwable) e);
            }
        } else if (header == null || !header.startsWith(HttpHeaders.NEGOTIATE) || (login = login((String) null, header.substring(10), servletRequest)) == null) {
            return Authentication.UNAUTHENTICATED;
        } else {
            return new UserAuthentication(getAuthMethod(), login);
        }
    }
}
