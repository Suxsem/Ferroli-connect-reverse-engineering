package org.eclipse.jetty.security;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class SecurityHandler extends HandlerWrapper implements Authenticator.AuthConfiguration {
    private static final Logger LOG = Log.getLogger((Class<?>) SecurityHandler.class);
    public static Principal __NOBODY = new Principal() {
        public String getName() {
            return "Nobody";
        }

        public String toString() {
            return getName();
        }
    };
    public static Principal __NO_USER = new Principal() {
        public String getName() {
            return null;
        }

        public String toString() {
            return "No User";
        }
    };
    private String _authMethod;
    private Authenticator _authenticator;
    private Authenticator.Factory _authenticatorFactory = new DefaultAuthenticatorFactory();
    private boolean _checkWelcomeFiles = false;
    private IdentityService _identityService;
    private final Map<String, String> _initParameters = new HashMap();
    private LoginService _loginService;
    private boolean _loginServiceShared;
    private String _realmName;
    private boolean _renewSession = true;

    /* access modifiers changed from: protected */
    public abstract boolean checkUserDataPermissions(String str, Request request, Response response, Object obj) throws IOException;

    /* access modifiers changed from: protected */
    public abstract boolean checkWebResourcePermissions(String str, Request request, Response response, Object obj, UserIdentity userIdentity) throws IOException;

    /* access modifiers changed from: protected */
    public abstract boolean isAuthMandatory(Request request, Response response, Object obj);

    /* access modifiers changed from: protected */
    public abstract Object prepareConstraintInfo(String str, Request request);

    protected SecurityHandler() {
    }

    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        if (!isStarted()) {
            this._identityService = identityService;
            return;
        }
        throw new IllegalStateException("Started");
    }

    public LoginService getLoginService() {
        return this._loginService;
    }

    public void setLoginService(LoginService loginService) {
        if (!isStarted()) {
            this._loginService = loginService;
            this._loginServiceShared = false;
            return;
        }
        throw new IllegalStateException("Started");
    }

    public Authenticator getAuthenticator() {
        return this._authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        if (!isStarted()) {
            this._authenticator = authenticator;
            return;
        }
        throw new IllegalStateException("Started");
    }

    public Authenticator.Factory getAuthenticatorFactory() {
        return this._authenticatorFactory;
    }

    public void setAuthenticatorFactory(Authenticator.Factory factory) {
        if (!isRunning()) {
            this._authenticatorFactory = factory;
            return;
        }
        throw new IllegalStateException("running");
    }

    public String getRealmName() {
        return this._realmName;
    }

    public void setRealmName(String str) {
        if (!isRunning()) {
            this._realmName = str;
            return;
        }
        throw new IllegalStateException("running");
    }

    public String getAuthMethod() {
        return this._authMethod;
    }

    public void setAuthMethod(String str) {
        if (!isRunning()) {
            this._authMethod = str;
            return;
        }
        throw new IllegalStateException("running");
    }

    public boolean isCheckWelcomeFiles() {
        return this._checkWelcomeFiles;
    }

    public void setCheckWelcomeFiles(boolean z) {
        if (!isRunning()) {
            this._checkWelcomeFiles = z;
            return;
        }
        throw new IllegalStateException("running");
    }

    public String getInitParameter(String str) {
        return this._initParameters.get(str);
    }

    public Set<String> getInitParameterNames() {
        return this._initParameters.keySet();
    }

    public String setInitParameter(String str, String str2) {
        if (!isRunning()) {
            return this._initParameters.put(str, str2);
        }
        throw new IllegalStateException("running");
    }

    /* access modifiers changed from: protected */
    public LoginService findLoginService() {
        List<LoginService> beans = getServer().getBeans(LoginService.class);
        String realmName = getRealmName();
        if (realmName != null) {
            for (LoginService next : beans) {
                if (next.getName() != null && next.getName().equals(realmName)) {
                    return next;
                }
            }
            return null;
        } else if (beans.size() == 1) {
            return beans.get(0);
        } else {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public IdentityService findIdentityService() {
        return (IdentityService) getServer().getBean(IdentityService.class);
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        Authenticator.Factory factory;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext != null) {
            Enumeration initParameterNames = currentContext.getInitParameterNames();
            while (initParameterNames != null && initParameterNames.hasMoreElements()) {
                String str = (String) initParameterNames.nextElement();
                if (str.startsWith("org.eclipse.jetty.security.") && getInitParameter(str) == null) {
                    setInitParameter(str, currentContext.getInitParameter(str));
                }
            }
            currentContext.getContextHandler().addEventListener(new HttpSessionListener() {
                public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
                }

                public void sessionCreated(HttpSessionEvent httpSessionEvent) {
                    Request request;
                    AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
                    if (currentConnection != null && (request = currentConnection.getRequest()) != null && request.isSecure()) {
                        httpSessionEvent.getSession().setAttribute(AbstractSessionManager.SESSION_KNOWN_ONLY_TO_AUTHENTICATED, Boolean.TRUE);
                    }
                }
            });
        }
        if (this._loginService == null) {
            this._loginService = findLoginService();
            if (this._loginService != null) {
                this._loginServiceShared = true;
            }
        }
        if (this._identityService == null) {
            LoginService loginService = this._loginService;
            if (loginService != null) {
                this._identityService = loginService.getIdentityService();
            }
            if (this._identityService == null) {
                this._identityService = findIdentityService();
            }
            if (this._identityService == null && this._realmName != null) {
                this._identityService = new DefaultIdentityService();
            }
        }
        LoginService loginService2 = this._loginService;
        if (loginService2 != null) {
            if (loginService2.getIdentityService() == null) {
                this._loginService.setIdentityService(this._identityService);
            } else if (this._loginService.getIdentityService() != this._identityService) {
                throw new IllegalStateException("LoginService has different IdentityService to " + this);
            }
        }
        if (!this._loginServiceShared) {
            LoginService loginService3 = this._loginService;
            if (loginService3 instanceof LifeCycle) {
                ((LifeCycle) loginService3).start();
            }
        }
        if (!(this._authenticator != null || (factory = this._authenticatorFactory) == null || this._identityService == null)) {
            this._authenticator = factory.getAuthenticator(getServer(), ContextHandler.getCurrentContext(), this, this._identityService, this._loginService);
            Authenticator authenticator = this._authenticator;
            if (authenticator != null) {
                this._authMethod = authenticator.getAuthMethod();
            }
        }
        Authenticator authenticator2 = this._authenticator;
        if (authenticator2 != null) {
            authenticator2.setConfiguration(this);
            Authenticator authenticator3 = this._authenticator;
            if (authenticator3 instanceof LifeCycle) {
                ((LifeCycle) authenticator3).start();
            }
        } else if (this._realmName != null) {
            Logger logger = LOG;
            logger.warn("No ServerAuthentication for " + this, new Object[0]);
            throw new IllegalStateException("No ServerAuthentication");
        }
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        if (!this._loginServiceShared) {
            LoginService loginService = this._loginService;
            if (loginService instanceof LifeCycle) {
                ((LifeCycle) loginService).stop();
            }
        }
    }

    /* renamed from: org.eclipse.jetty.security.SecurityHandler$4 */
    static /* synthetic */ class C23994 {
        static final /* synthetic */ int[] $SwitchMap$javax$servlet$DispatcherType = new int[DispatcherType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                javax.servlet.DispatcherType[] r0 = javax.servlet.DispatcherType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$javax$servlet$DispatcherType = r0
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x0014 }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.REQUEST     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x001f }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.ASYNC     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x002a }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.FORWARD     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.SecurityHandler.C23994.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkSecurity(Request request) {
        int i = C23994.$SwitchMap$javax$servlet$DispatcherType[request.getDispatcherType().ordinal()];
        if (i == 1 || i == 2) {
            return true;
        }
        if (i != 3 || !this._checkWelcomeFiles || request.getAttribute("org.eclipse.jetty.server.welcome") == null) {
            return false;
        }
        request.removeAttribute("org.eclipse.jetty.server.welcome");
        return true;
    }

    public boolean isSessionRenewedOnAuthentication() {
        return this._renewSession;
    }

    public void setSessionRenewedOnAuthentication(boolean z) {
        this._renewSession = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:61:0x00f3=Splitter:B:61:0x00f3, B:88:0x0139=Splitter:B:88:0x0139} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(java.lang.String r20, org.eclipse.jetty.server.Request r21, javax.servlet.http.HttpServletRequest r22, javax.servlet.http.HttpServletResponse r23) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r19 = this;
            r7 = r19
            r0 = r20
            r8 = r21
            r1 = r22
            r2 = r23
            org.eclipse.jetty.server.Response r4 = r21.getResponse()
            org.eclipse.jetty.server.Handler r9 = r19.getHandler()
            if (r9 != 0) goto L_0x0015
            return
        L_0x0015:
            org.eclipse.jetty.security.Authenticator r10 = r7._authenticator
            boolean r3 = r7.checkSecurity(r8)
            if (r3 == 0) goto L_0x016e
            java.lang.Object r5 = r19.prepareConstraintInfo(r20, r21)
            boolean r3 = r7.checkUserDataPermissions(r0, r8, r4, r5)
            r11 = 403(0x193, float:5.65E-43)
            r12 = 1
            if (r3 != 0) goto L_0x0037
            boolean r0 = r21.isHandled()
            if (r0 != 0) goto L_0x0036
            r2.sendError(r11)
            r8.setHandled(r12)
        L_0x0036:
            return
        L_0x0037:
            boolean r13 = r7.isAuthMandatory(r8, r4, r5)
            if (r13 == 0) goto L_0x0065
            if (r10 != 0) goto L_0x0065
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "No authenticator for: "
            r1.append(r3)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r0.warn((java.lang.String) r1, (java.lang.Object[]) r3)
            boolean r0 = r21.isHandled()
            if (r0 != 0) goto L_0x0064
            r2.sendError(r11)
            r8.setHandled(r12)
        L_0x0064:
            return
        L_0x0065:
            r3 = 0
            org.eclipse.jetty.server.Authentication r6 = r21.getAuthentication()     // Catch:{ ServerAuthException -> 0x0153 }
            if (r6 == 0) goto L_0x0070
            org.eclipse.jetty.server.Authentication r14 = org.eclipse.jetty.server.Authentication.NOT_CHECKED     // Catch:{ ServerAuthException -> 0x0153 }
            if (r6 != r14) goto L_0x0079
        L_0x0070:
            if (r10 != 0) goto L_0x0075
            org.eclipse.jetty.server.Authentication r6 = org.eclipse.jetty.server.Authentication.UNAUTHENTICATED     // Catch:{ ServerAuthException -> 0x0153 }
            goto L_0x0079
        L_0x0075:
            org.eclipse.jetty.server.Authentication r6 = r10.validateRequest(r1, r2, r13)     // Catch:{ ServerAuthException -> 0x0153 }
        L_0x0079:
            boolean r14 = r6 instanceof org.eclipse.jetty.server.Authentication.Wrapped     // Catch:{ ServerAuthException -> 0x0153 }
            if (r14 == 0) goto L_0x008b
            r1 = r6
            org.eclipse.jetty.server.Authentication$Wrapped r1 = (org.eclipse.jetty.server.Authentication.Wrapped) r1     // Catch:{ ServerAuthException -> 0x0153 }
            javax.servlet.http.HttpServletRequest r1 = r1.getHttpServletRequest()     // Catch:{ ServerAuthException -> 0x0153 }
            r14 = r6
            org.eclipse.jetty.server.Authentication$Wrapped r14 = (org.eclipse.jetty.server.Authentication.Wrapped) r14     // Catch:{ ServerAuthException -> 0x0153 }
            javax.servlet.http.HttpServletResponse r2 = r14.getHttpServletResponse()     // Catch:{ ServerAuthException -> 0x0153 }
        L_0x008b:
            r14 = r1
            r15 = r2
            boolean r1 = r6 instanceof org.eclipse.jetty.server.Authentication.ResponseSent     // Catch:{ ServerAuthException -> 0x014f }
            if (r1 == 0) goto L_0x0097
            r8.setHandled(r12)     // Catch:{ ServerAuthException -> 0x014f }
            r1 = r3
            goto L_0x0141
        L_0x0097:
            boolean r1 = r6 instanceof org.eclipse.jetty.server.Authentication.User     // Catch:{ ServerAuthException -> 0x014f }
            if (r1 == 0) goto L_0x00fe
            r2 = r6
            org.eclipse.jetty.server.Authentication$User r2 = (org.eclipse.jetty.server.Authentication.User) r2     // Catch:{ ServerAuthException -> 0x014f }
            r8.setAuthentication(r6)     // Catch:{ ServerAuthException -> 0x014f }
            org.eclipse.jetty.security.IdentityService r1 = r7._identityService     // Catch:{ ServerAuthException -> 0x014f }
            if (r1 == 0) goto L_0x00b1
            org.eclipse.jetty.security.IdentityService r1 = r7._identityService     // Catch:{ ServerAuthException -> 0x014f }
            org.eclipse.jetty.server.UserIdentity r6 = r2.getUserIdentity()     // Catch:{ ServerAuthException -> 0x014f }
            java.lang.Object r1 = r1.associate(r6)     // Catch:{ ServerAuthException -> 0x014f }
            r6 = r1
            goto L_0x00b2
        L_0x00b1:
            r6 = r3
        L_0x00b2:
            if (r13 == 0) goto L_0x00f0
            org.eclipse.jetty.server.UserIdentity r16 = r2.getUserIdentity()     // Catch:{ ServerAuthException -> 0x00ed, all -> 0x00ea }
            r1 = r19
            r3 = r2
            r2 = r20
            r17 = r3
            r3 = r21
            r18 = r6
            r6 = r16
            boolean r1 = r1.checkWebResourcePermissions(r2, r3, r4, r5, r6)     // Catch:{ ServerAuthException -> 0x00e5, all -> 0x00e0 }
            if (r1 != 0) goto L_0x00dd
            java.lang.String r0 = "!role"
            r15.sendError(r11, r0)     // Catch:{ ServerAuthException -> 0x00e5, all -> 0x00e0 }
            r8.setHandled(r12)     // Catch:{ ServerAuthException -> 0x00e5, all -> 0x00e0 }
            org.eclipse.jetty.security.IdentityService r0 = r7._identityService
            if (r0 == 0) goto L_0x00dc
            r1 = r18
            r0.disassociate(r1)
        L_0x00dc:
            return
        L_0x00dd:
            r1 = r18
            goto L_0x00f3
        L_0x00e0:
            r0 = move-exception
            r1 = r18
            goto L_0x014a
        L_0x00e5:
            r0 = move-exception
            r1 = r18
            goto L_0x014d
        L_0x00ea:
            r0 = move-exception
            r1 = r6
            goto L_0x014a
        L_0x00ed:
            r0 = move-exception
            r1 = r6
            goto L_0x014d
        L_0x00f0:
            r17 = r2
            r1 = r6
        L_0x00f3:
            r9.handle(r0, r8, r14, r15)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            if (r10 == 0) goto L_0x0141
            r6 = r17
            r10.secureResponse(r14, r15, r13, r6)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            goto L_0x0141
        L_0x00fe:
            boolean r1 = r6 instanceof org.eclipse.jetty.server.Authentication.Deferred     // Catch:{ ServerAuthException -> 0x014f }
            if (r1 == 0) goto L_0x012a
            r1 = r6
            org.eclipse.jetty.security.authentication.DeferredAuthentication r1 = (org.eclipse.jetty.security.authentication.DeferredAuthentication) r1     // Catch:{ ServerAuthException -> 0x014f }
            r8.setAuthentication(r6)     // Catch:{ ServerAuthException -> 0x014f }
            r9.handle(r0, r8, r14, r15)     // Catch:{ all -> 0x0123 }
            java.lang.Object r1 = r1.getPreviousAssociation()     // Catch:{ ServerAuthException -> 0x014f }
            if (r10 == 0) goto L_0x0141
            org.eclipse.jetty.server.Authentication r0 = r21.getAuthentication()     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            boolean r2 = r0 instanceof org.eclipse.jetty.server.Authentication.User     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            if (r2 == 0) goto L_0x011f
            org.eclipse.jetty.server.Authentication$User r0 = (org.eclipse.jetty.server.Authentication.User) r0     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            r10.secureResponse(r14, r15, r13, r0)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            goto L_0x0141
        L_0x011f:
            r10.secureResponse(r14, r15, r13, r3)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            goto L_0x0141
        L_0x0123:
            r0 = move-exception
            r2 = r0
            java.lang.Object r3 = r1.getPreviousAssociation()     // Catch:{ ServerAuthException -> 0x014f }
            throw r2     // Catch:{ ServerAuthException -> 0x014f }
        L_0x012a:
            r8.setAuthentication(r6)     // Catch:{ ServerAuthException -> 0x014f }
            org.eclipse.jetty.security.IdentityService r1 = r7._identityService     // Catch:{ ServerAuthException -> 0x014f }
            if (r1 == 0) goto L_0x0138
            org.eclipse.jetty.security.IdentityService r1 = r7._identityService     // Catch:{ ServerAuthException -> 0x014f }
            java.lang.Object r1 = r1.associate(r3)     // Catch:{ ServerAuthException -> 0x014f }
            goto L_0x0139
        L_0x0138:
            r1 = r3
        L_0x0139:
            r9.handle(r0, r8, r14, r15)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
            if (r10 == 0) goto L_0x0141
            r10.secureResponse(r14, r15, r13, r3)     // Catch:{ ServerAuthException -> 0x014c, all -> 0x0149 }
        L_0x0141:
            org.eclipse.jetty.security.IdentityService r0 = r7._identityService
            if (r0 == 0) goto L_0x0171
            r0.disassociate(r1)
            goto L_0x0171
        L_0x0149:
            r0 = move-exception
        L_0x014a:
            r3 = r1
            goto L_0x0166
        L_0x014c:
            r0 = move-exception
        L_0x014d:
            r3 = r1
            goto L_0x0155
        L_0x014f:
            r0 = move-exception
            goto L_0x0155
        L_0x0151:
            r0 = move-exception
            goto L_0x0166
        L_0x0153:
            r0 = move-exception
            r15 = r2
        L_0x0155:
            r1 = 500(0x1f4, float:7.0E-43)
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0151 }
            r15.sendError(r1, r0)     // Catch:{ all -> 0x0151 }
            org.eclipse.jetty.security.IdentityService r0 = r7._identityService
            if (r0 == 0) goto L_0x0171
            r0.disassociate(r3)
            goto L_0x0171
        L_0x0166:
            org.eclipse.jetty.security.IdentityService r1 = r7._identityService
            if (r1 == 0) goto L_0x016d
            r1.disassociate(r3)
        L_0x016d:
            throw r0
        L_0x016e:
            r9.handle(r0, r8, r1, r2)
        L_0x0171:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.SecurityHandler.handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    public static SecurityHandler getCurrentSecurityHandler() {
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext == null) {
            return null;
        }
        return (SecurityHandler) currentContext.getContextHandler().getChildHandlerByClass(SecurityHandler.class);
    }

    public void logout(Authentication.User user) {
        LOG.debug("logout {}", user);
        LoginService loginService = getLoginService();
        if (loginService != null) {
            loginService.logout(user.getUserIdentity());
        }
        IdentityService identityService = getIdentityService();
        if (identityService != null) {
            identityService.disassociate((Object) null);
        }
    }

    public class NotChecked implements Principal {
        public String getName() {
            return null;
        }

        public String toString() {
            return "NOT CHECKED";
        }

        public NotChecked() {
        }

        public SecurityHandler getSecurityHandler() {
            return SecurityHandler.this;
        }
    }
}
