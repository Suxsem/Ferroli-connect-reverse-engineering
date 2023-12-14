package org.eclipse.jetty.server.session;

import java.io.IOException;
import java.util.EnumSet;
import java.util.EventListener;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class SessionHandler extends ScopedHandler {
    public static final EnumSet<SessionTrackingMode> DEFAULT_TRACKING = EnumSet.of(SessionTrackingMode.COOKIE, SessionTrackingMode.URL);
    static final Logger LOG = Log.getLogger("org.eclipse.jetty.server.session");
    private SessionManager _sessionManager;

    public SessionHandler() {
        this(new HashSessionManager());
    }

    public SessionHandler(SessionManager sessionManager) {
        setSessionManager(sessionManager);
    }

    public SessionManager getSessionManager() {
        return this._sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        if (!isStarted()) {
            SessionManager sessionManager2 = this._sessionManager;
            if (getServer() != null) {
                getServer().getContainer().update((Object) this, (Object) sessionManager2, (Object) sessionManager, "sessionManager", true);
            }
            if (sessionManager != null) {
                sessionManager.setSessionHandler(this);
            }
            this._sessionManager = sessionManager;
            if (sessionManager2 != null) {
                sessionManager2.setSessionHandler((SessionHandler) null);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    public void setServer(Server server) {
        Server server2 = getServer();
        if (!(server2 == null || server2 == server)) {
            server2.getContainer().update((Object) this, (Object) this._sessionManager, (Object) null, "sessionManager", true);
        }
        super.setServer(server);
        if (server != null && server != server2) {
            server.getContainer().update((Object) this, (Object) null, (Object) this._sessionManager, "sessionManager", true);
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._sessionManager.start();
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._sessionManager.stop();
        super.doStop();
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doScope(java.lang.String r9, org.eclipse.jetty.server.Request r10, javax.servlet.http.HttpServletRequest r11, javax.servlet.http.HttpServletResponse r12) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            org.eclipse.jetty.server.SessionManager r2 = r10.getSessionManager()     // Catch:{ all -> 0x00c4 }
            javax.servlet.http.HttpSession r3 = r10.getSession(r1)     // Catch:{ all -> 0x00c1 }
            org.eclipse.jetty.server.SessionManager r4 = r8._sessionManager     // Catch:{ all -> 0x00bf }
            if (r2 == r4) goto L_0x0019
            org.eclipse.jetty.server.SessionManager r4 = r8._sessionManager     // Catch:{ all -> 0x00bf }
            r10.setSessionManager(r4)     // Catch:{ all -> 0x00bf }
            r10.setSession(r0)     // Catch:{ all -> 0x00bf }
            r8.checkRequestedSessionId(r10, r11)     // Catch:{ all -> 0x00bf }
        L_0x0019:
            org.eclipse.jetty.server.SessionManager r4 = r8._sessionManager     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x004a
            javax.servlet.http.HttpSession r4 = r10.getSession(r1)     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x003e
            if (r4 == r3) goto L_0x004b
            org.eclipse.jetty.server.SessionManager r0 = r8._sessionManager     // Catch:{ all -> 0x003a }
            boolean r5 = r11.isSecure()     // Catch:{ all -> 0x003a }
            org.eclipse.jetty.http.HttpCookie r0 = r0.access(r4, r5)     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x0038
            org.eclipse.jetty.server.Response r5 = r10.getResponse()     // Catch:{ all -> 0x003a }
            r5.addCookie((org.eclipse.jetty.http.HttpCookie) r0)     // Catch:{ all -> 0x003a }
        L_0x0038:
            r0 = r4
            goto L_0x004b
        L_0x003a:
            r9 = move-exception
            r0 = r4
            goto L_0x00c7
        L_0x003e:
            org.eclipse.jetty.server.SessionManager r4 = r8._sessionManager     // Catch:{ all -> 0x00bf }
            javax.servlet.http.HttpSession r4 = r10.recoverNewSession(r4)     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x004b
            r10.setSession(r4)     // Catch:{ all -> 0x00bf }
            goto L_0x004b
        L_0x004a:
            r4 = r0
        L_0x004b:
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ all -> 0x00bf }
            boolean r5 = r5.isDebugEnabled()     // Catch:{ all -> 0x00bf }
            if (r5 == 0) goto L_0x0085
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r6.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.String r7 = "sessionManager="
            r6.append(r7)     // Catch:{ all -> 0x00bf }
            org.eclipse.jetty.server.SessionManager r7 = r8._sessionManager     // Catch:{ all -> 0x00bf }
            r6.append(r7)     // Catch:{ all -> 0x00bf }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00bf }
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ all -> 0x00bf }
            r5.debug((java.lang.String) r6, (java.lang.Object[]) r7)     // Catch:{ all -> 0x00bf }
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r6.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.String r7 = "session="
            r6.append(r7)     // Catch:{ all -> 0x00bf }
            r6.append(r4)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r6.toString()     // Catch:{ all -> 0x00bf }
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x00bf }
            r5.debug((java.lang.String) r4, (java.lang.Object[]) r6)     // Catch:{ all -> 0x00bf }
        L_0x0085:
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r8._nextScope     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x008f
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r8._nextScope     // Catch:{ all -> 0x00bf }
            r4.doScope(r9, r10, r11, r12)     // Catch:{ all -> 0x00bf }
            goto L_0x009c
        L_0x008f:
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r8._outerScope     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x0099
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r8._outerScope     // Catch:{ all -> 0x00bf }
            r4.doHandle(r9, r10, r11, r12)     // Catch:{ all -> 0x00bf }
            goto L_0x009c
        L_0x0099:
            r8.doHandle(r9, r10, r11, r12)     // Catch:{ all -> 0x00bf }
        L_0x009c:
            if (r0 == 0) goto L_0x00a3
            org.eclipse.jetty.server.SessionManager r9 = r8._sessionManager
            r9.complete(r0)
        L_0x00a3:
            javax.servlet.http.HttpSession r9 = r10.getSession(r1)
            if (r9 == 0) goto L_0x00b2
            if (r3 != 0) goto L_0x00b2
            if (r9 == r0) goto L_0x00b2
            org.eclipse.jetty.server.SessionManager r11 = r8._sessionManager
            r11.complete(r9)
        L_0x00b2:
            if (r2 == 0) goto L_0x00be
            org.eclipse.jetty.server.SessionManager r9 = r8._sessionManager
            if (r2 == r9) goto L_0x00be
            r10.setSessionManager(r2)
            r10.setSession(r3)
        L_0x00be:
            return
        L_0x00bf:
            r9 = move-exception
            goto L_0x00c7
        L_0x00c1:
            r9 = move-exception
            r3 = r0
            goto L_0x00c7
        L_0x00c4:
            r9 = move-exception
            r2 = r0
            r3 = r2
        L_0x00c7:
            if (r0 == 0) goto L_0x00ce
            org.eclipse.jetty.server.SessionManager r11 = r8._sessionManager
            r11.complete(r0)
        L_0x00ce:
            javax.servlet.http.HttpSession r11 = r10.getSession(r1)
            if (r11 == 0) goto L_0x00dd
            if (r3 != 0) goto L_0x00dd
            if (r11 == r0) goto L_0x00dd
            org.eclipse.jetty.server.SessionManager r12 = r8._sessionManager
            r12.complete(r11)
        L_0x00dd:
            if (r2 == 0) goto L_0x00e9
            org.eclipse.jetty.server.SessionManager r11 = r8._sessionManager
            if (r2 == r11) goto L_0x00e9
            r10.setSessionManager(r2)
            r10.setSession(r3)
        L_0x00e9:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.SessionHandler.doScope(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (never()) {
            nextHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._nextScope != null && this._nextScope == this._handler) {
            this._nextScope.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._handler != null) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    /* access modifiers changed from: protected */
    public void checkRequestedSessionId(Request request, HttpServletRequest httpServletRequest) {
        boolean z;
        int indexOf;
        Cookie[] cookies;
        String requestedSessionId = httpServletRequest.getRequestedSessionId();
        SessionManager sessionManager = getSessionManager();
        if (requestedSessionId != null && sessionManager != null) {
            HttpSession httpSession = sessionManager.getHttpSession(requestedSessionId);
            if (httpSession != null && sessionManager.isValid(httpSession)) {
                request.setSession(httpSession);
            }
        } else if (DispatcherType.REQUEST.equals(request.getDispatcherType())) {
            HttpSession httpSession2 = null;
            boolean z2 = true;
            if (this._sessionManager.isUsingCookies() && (cookies = httpServletRequest.getCookies()) != null && cookies.length > 0) {
                String name = sessionManager.getSessionCookieConfig().getName();
                String str = requestedSessionId;
                HttpSession httpSession3 = null;
                int i = 0;
                boolean z3 = false;
                while (true) {
                    if (i >= cookies.length) {
                        z = z3;
                        requestedSessionId = str;
                        httpSession2 = httpSession3;
                        break;
                    }
                    if (name.equalsIgnoreCase(cookies[i].getName())) {
                        String value = cookies[i].getValue();
                        LOG.debug("Got Session ID {} from cookie", value);
                        if (value != null) {
                            HttpSession httpSession4 = sessionManager.getHttpSession(value);
                            if (httpSession4 != null && sessionManager.isValid(httpSession4)) {
                                requestedSessionId = value;
                                httpSession2 = httpSession4;
                                z = true;
                                break;
                            }
                            httpSession3 = httpSession4;
                        } else {
                            LOG.warn("null session id from cookie", new Object[0]);
                        }
                        str = value;
                        z3 = true;
                    }
                    i++;
                }
            } else {
                z = false;
            }
            if (requestedSessionId == null || httpSession2 == null) {
                String requestURI = httpServletRequest.getRequestURI();
                String sessionIdPathParameterNamePrefix = sessionManager.getSessionIdPathParameterNamePrefix();
                if (sessionIdPathParameterNamePrefix != null && (indexOf = requestURI.indexOf(sessionIdPathParameterNamePrefix)) >= 0) {
                    int length = indexOf + sessionIdPathParameterNamePrefix.length();
                    int i2 = length;
                    while (i2 < requestURI.length() && (r2 = requestURI.charAt(i2)) != ';' && r2 != '#' && r2 != '?' && r2 != '/') {
                        i2++;
                    }
                    requestedSessionId = requestURI.substring(length, i2);
                    httpSession2 = sessionManager.getHttpSession(requestedSessionId);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Got Session ID {} from URL", requestedSessionId);
                    }
                    z = false;
                }
            }
            request.setRequestedSessionId(requestedSessionId);
            if (requestedSessionId == null || !z) {
                z2 = false;
            }
            request.setRequestedSessionIdFromCookie(z2);
            if (httpSession2 != null && sessionManager.isValid(httpSession2)) {
                request.setSession(httpSession2);
            }
        }
    }

    public void addEventListener(EventListener eventListener) {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.addEventListener(eventListener);
        }
    }

    public void clearEventListeners() {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.clearEventListeners();
        }
    }
}
