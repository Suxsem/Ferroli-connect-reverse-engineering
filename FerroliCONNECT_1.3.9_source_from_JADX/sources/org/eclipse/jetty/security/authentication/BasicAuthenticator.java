package org.eclipse.jetty.security.authentication;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.server.Authentication;

public class BasicAuthenticator extends LoginAuthenticator {
    public String getAuthMethod() {
        return "BASIC";
    }

    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
        r7 = org.eclipse.jetty.util.B64Code.decode(r0.substring(r7 + 1), org.eclipse.jetty.util.StringUtil.__ISO_8859_1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.server.Authentication validateRequest(javax.servlet.ServletRequest r5, javax.servlet.ServletResponse r6, boolean r7) throws org.eclipse.jetty.security.ServerAuthException {
        /*
            r4 = this;
            javax.servlet.http.HttpServletRequest r5 = (javax.servlet.http.HttpServletRequest) r5
            javax.servlet.http.HttpServletResponse r6 = (javax.servlet.http.HttpServletResponse) r6
            java.lang.String r0 = "Authorization"
            java.lang.String r0 = r5.getHeader(r0)
            if (r7 != 0) goto L_0x0015
            org.eclipse.jetty.security.authentication.DeferredAuthentication r5 = new org.eclipse.jetty.security.authentication.DeferredAuthentication     // Catch:{ IOException -> 0x0012 }
            r5.<init>(r4)     // Catch:{ IOException -> 0x0012 }
            return r5
        L_0x0012:
            r5 = move-exception
            goto L_0x008c
        L_0x0015:
            if (r0 == 0) goto L_0x005a
            r7 = 32
            int r7 = r0.indexOf(r7)     // Catch:{ IOException -> 0x0012 }
            if (r7 <= 0) goto L_0x005a
            r1 = 0
            java.lang.String r2 = r0.substring(r1, r7)     // Catch:{ IOException -> 0x0012 }
            java.lang.String r3 = "basic"
            boolean r2 = r3.equalsIgnoreCase(r2)     // Catch:{ IOException -> 0x0012 }
            if (r2 == 0) goto L_0x005a
            int r7 = r7 + 1
            java.lang.String r7 = r0.substring(r7)     // Catch:{ IOException -> 0x0012 }
            java.lang.String r0 = "ISO-8859-1"
            java.lang.String r7 = org.eclipse.jetty.util.B64Code.decode((java.lang.String) r7, (java.lang.String) r0)     // Catch:{ IOException -> 0x0012 }
            r0 = 58
            int r0 = r7.indexOf(r0)     // Catch:{ IOException -> 0x0012 }
            if (r0 <= 0) goto L_0x005a
            java.lang.String r1 = r7.substring(r1, r0)     // Catch:{ IOException -> 0x0012 }
            int r0 = r0 + 1
            java.lang.String r7 = r7.substring(r0)     // Catch:{ IOException -> 0x0012 }
            org.eclipse.jetty.server.UserIdentity r5 = r4.login(r1, r7, r5)     // Catch:{ IOException -> 0x0012 }
            if (r5 == 0) goto L_0x005a
            org.eclipse.jetty.security.UserAuthentication r6 = new org.eclipse.jetty.security.UserAuthentication     // Catch:{ IOException -> 0x0012 }
            java.lang.String r7 = r4.getAuthMethod()     // Catch:{ IOException -> 0x0012 }
            r6.<init>(r7, r5)     // Catch:{ IOException -> 0x0012 }
            return r6
        L_0x005a:
            boolean r5 = org.eclipse.jetty.security.authentication.DeferredAuthentication.isDeferred(r6)     // Catch:{ IOException -> 0x0012 }
            if (r5 == 0) goto L_0x0063
            org.eclipse.jetty.server.Authentication r5 = org.eclipse.jetty.server.Authentication.UNAUTHENTICATED     // Catch:{ IOException -> 0x0012 }
            return r5
        L_0x0063:
            java.lang.String r5 = "WWW-Authenticate"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0012 }
            r7.<init>()     // Catch:{ IOException -> 0x0012 }
            java.lang.String r0 = "basic realm=\""
            r7.append(r0)     // Catch:{ IOException -> 0x0012 }
            org.eclipse.jetty.security.LoginService r0 = r4._loginService     // Catch:{ IOException -> 0x0012 }
            java.lang.String r0 = r0.getName()     // Catch:{ IOException -> 0x0012 }
            r7.append(r0)     // Catch:{ IOException -> 0x0012 }
            r0 = 34
            r7.append(r0)     // Catch:{ IOException -> 0x0012 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x0012 }
            r6.setHeader(r5, r7)     // Catch:{ IOException -> 0x0012 }
            r5 = 401(0x191, float:5.62E-43)
            r6.sendError(r5)     // Catch:{ IOException -> 0x0012 }
            org.eclipse.jetty.server.Authentication r5 = org.eclipse.jetty.server.Authentication.SEND_CONTINUE     // Catch:{ IOException -> 0x0012 }
            return r5
        L_0x008c:
            org.eclipse.jetty.security.ServerAuthException r6 = new org.eclipse.jetty.security.ServerAuthException
            r6.<init>((java.lang.Throwable) r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.authentication.BasicAuthenticator.validateRequest(javax.servlet.ServletRequest, javax.servlet.ServletResponse, boolean):org.eclipse.jetty.server.Authentication");
    }
}
