package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.RunAsToken;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class ServletHolder extends Holder<Servlet> implements UserIdentity.Scope, Comparable {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) ServletHolder.class);
    public static final Map<String, String> NO_MAPPED_ROLES = Collections.emptyMap();
    /* access modifiers changed from: private */
    public transient Config _config;
    private transient boolean _enabled;
    private String _forcedPath;
    private IdentityService _identityService;
    private boolean _initOnStartup;
    private int _initOrder;
    private ServletRegistration.Dynamic _registration;
    private Map<String, String> _roleMap;
    /* access modifiers changed from: private */
    public String _runAsRole;
    private RunAsToken _runAsToken;
    private transient Servlet _servlet;
    private transient long _unavailable;
    private transient UnavailableException _unavailableEx;

    public ServletHolder() {
        this(Holder.Source.EMBEDDED);
    }

    public ServletHolder(Holder.Source source) {
        super(source);
        this._initOnStartup = false;
        this._enabled = true;
    }

    public ServletHolder(Servlet servlet) {
        this(Holder.Source.EMBEDDED);
        setServlet(servlet);
    }

    public ServletHolder(String str, Class<? extends Servlet> cls) {
        this(Holder.Source.EMBEDDED);
        setName(str);
        setHeldClass(cls);
    }

    public ServletHolder(String str, Servlet servlet) {
        this(Holder.Source.EMBEDDED);
        setName(str);
        setServlet(servlet);
    }

    public ServletHolder(Class<? extends Servlet> cls) {
        this(Holder.Source.EMBEDDED);
        setHeldClass(cls);
    }

    public UnavailableException getUnavailableException() {
        return this._unavailableEx;
    }

    public synchronized void setServlet(Servlet servlet) {
        if (servlet != null) {
            if (!(servlet instanceof SingleThreadModel)) {
                this._extInstance = true;
                this._servlet = servlet;
                setHeldClass(servlet.getClass());
                if (getName() == null) {
                    setName(servlet.getClass().getName() + "-" + super.hashCode());
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public int getInitOrder() {
        return this._initOrder;
    }

    public void setInitOrder(int i) {
        this._initOnStartup = true;
        this._initOrder = i;
    }

    public boolean isSetInitOrder() {
        return this._initOnStartup;
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof ServletHolder)) {
            return 1;
        }
        ServletHolder servletHolder = (ServletHolder) obj;
        int i = 0;
        if (servletHolder == this) {
            return 0;
        }
        int i2 = servletHolder._initOrder;
        int i3 = this._initOrder;
        if (i2 < i3) {
            return 1;
        }
        if (i2 > i3) {
            return -1;
        }
        if (!(this._className == null || servletHolder._className == null)) {
            i = this._className.compareTo(servletHolder._className);
        }
        return i == 0 ? this._name.compareTo(servletHolder._name) : i;
    }

    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }

    public int hashCode() {
        return this._name == null ? System.identityHashCode(this) : this._name.hashCode();
    }

    public synchronized void setUserRoleLink(String str, String str2) {
        if (this._roleMap == null) {
            this._roleMap = new HashMap();
        }
        this._roleMap.put(str, str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0005, code lost:
        r0 = r0.get(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getUserRoleLink(java.lang.String r2) {
        /*
            r1 = this;
            java.util.Map<java.lang.String, java.lang.String> r0 = r1._roleMap
            if (r0 != 0) goto L_0x0005
            return r2
        L_0x0005:
            java.lang.Object r0 = r0.get(r2)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x000e
            goto L_0x000f
        L_0x000e:
            r2 = r0
        L_0x000f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.getUserRoleLink(java.lang.String):java.lang.String");
    }

    public Map<String, String> getRoleMap() {
        Map<String, String> map = this._roleMap;
        return map == null ? NO_MAPPED_ROLES : map;
    }

    public String getForcedPath() {
        return this._forcedPath;
    }

    public void setForcedPath(String str) {
        this._forcedPath = str;
    }

    public boolean isEnabled() {
        return this._enabled;
    }

    public void setEnabled(boolean z) {
        this._enabled = z;
    }

    public void doStart() throws Exception {
        String str;
        this._unavailable = 0;
        if (this._enabled) {
            try {
                super.doStart();
                try {
                    checkServletType();
                    this._identityService = this._servletHandler.getIdentityService();
                    IdentityService identityService = this._identityService;
                    if (!(identityService == null || (str = this._runAsRole) == null)) {
                        this._runAsToken = identityService.newRunAsToken(str);
                    }
                    this._config = new Config();
                    if (this._class != null && SingleThreadModel.class.isAssignableFrom(this._class)) {
                        this._servlet = new SingleThreadedWrapper();
                    }
                    if (this._extInstance || this._initOnStartup) {
                        try {
                            initServlet();
                        } catch (Exception e) {
                            if (this._servletHandler.isStartWithUnavailable()) {
                                LOG.ignore(e);
                                return;
                            }
                            throw e;
                        }
                    }
                } catch (UnavailableException e2) {
                    makeUnavailable(e2);
                    if (this._servletHandler.isStartWithUnavailable()) {
                        LOG.ignore(e2);
                        return;
                    }
                    throw e2;
                }
            } catch (UnavailableException e3) {
                makeUnavailable(e3);
                if (this._servletHandler.isStartWithUnavailable()) {
                    LOG.ignore(e3);
                    return;
                }
                throw e3;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x004f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStop() throws java.lang.Exception {
        /*
            r5 = this;
            javax.servlet.Servlet r0 = r5._servlet
            r1 = 0
            if (r0 == 0) goto L_0x004b
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            if (r0 == 0) goto L_0x0018
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            org.eclipse.jetty.server.UserIdentity r2 = r2.getSystemUserIdentity()     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            org.eclipse.jetty.security.RunAsToken r3 = r5._runAsToken     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            java.lang.Object r0 = r0.setRunAs(r2, r3)     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            goto L_0x0019
        L_0x0018:
            r0 = r1
        L_0x0019:
            javax.servlet.Servlet r2 = r5._servlet     // Catch:{ Exception -> 0x002b, all -> 0x0026 }
            r5.destroyInstance(r2)     // Catch:{ Exception -> 0x002b, all -> 0x0026 }
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L_0x004b
            r2.unsetRunAs(r0)
            goto L_0x004b
        L_0x0026:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0043
        L_0x002b:
            r2 = move-exception
            r4 = r2
            r2 = r0
            r0 = r4
            goto L_0x0034
        L_0x0030:
            r0 = move-exception
            goto L_0x0043
        L_0x0032:
            r0 = move-exception
            r2 = r1
        L_0x0034:
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ all -> 0x0041 }
            r3.warn(r0)     // Catch:{ all -> 0x0041 }
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService
            if (r0 == 0) goto L_0x004b
            r0.unsetRunAs(r2)
            goto L_0x004b
        L_0x0041:
            r0 = move-exception
            r1 = r2
        L_0x0043:
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L_0x004a
            r2.unsetRunAs(r1)
        L_0x004a:
            throw r0
        L_0x004b:
            boolean r0 = r5._extInstance
            if (r0 != 0) goto L_0x0051
            r5._servlet = r1
        L_0x0051:
            r5._config = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.doStop():void");
    }

    public void destroyInstance(Object obj) throws Exception {
        if (obj != null) {
            Servlet servlet = (Servlet) obj;
            getServletHandler().destroyServlet(servlet);
            servlet.destroy();
        }
    }

    public synchronized Servlet getServlet() throws ServletException {
        if (this._unavailable != 0) {
            if (this._unavailable < 0 || (this._unavailable > 0 && System.currentTimeMillis() < this._unavailable)) {
                throw this._unavailableEx;
            }
            this._unavailable = 0;
            this._unavailableEx = null;
        }
        if (this._servlet == null) {
            initServlet();
        }
        return this._servlet;
    }

    public Servlet getServletInstance() {
        return this._servlet;
    }

    public void checkServletType() throws UnavailableException {
        if (this._class == null || !Servlet.class.isAssignableFrom(this._class)) {
            throw new UnavailableException("Servlet " + this._class + " is not a javax.servlet.Servlet");
        }
    }

    public boolean isAvailable() {
        if (isStarted() && this._unavailable == 0) {
            return true;
        }
        try {
            getServlet();
        } catch (Exception e) {
            LOG.ignore(e);
        }
        if (!isStarted() || this._unavailable != 0) {
            return false;
        }
        return true;
    }

    private void makeUnavailable(UnavailableException unavailableException) {
        if (this._unavailableEx != unavailableException || this._unavailable == 0) {
            this._servletHandler.getServletContext().log("unavailable", (Throwable) unavailableException);
            this._unavailableEx = unavailableException;
            this._unavailable = -1;
            if (unavailableException.isPermanent()) {
                this._unavailable = -1;
            } else if (this._unavailableEx.getUnavailableSeconds() > 0) {
                this._unavailable = System.currentTimeMillis() + ((long) (this._unavailableEx.getUnavailableSeconds() * 1000));
            } else {
                this._unavailable = System.currentTimeMillis() + 5000;
            }
        }
    }

    private void makeUnavailable(final Throwable th) {
        if (th instanceof UnavailableException) {
            makeUnavailable((UnavailableException) th);
            return;
        }
        ServletContext servletContext = this._servletHandler.getServletContext();
        if (servletContext == null) {
            LOG.info("unavailable", th);
        } else {
            servletContext.log("unavailable", th);
        }
        this._unavailableEx = new UnavailableException(String.valueOf(th), -1) {
            {
                initCause(th);
            }
        };
        this._unavailable = -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0076 A[Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058, all -> 0x008e }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0078 A[Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058, all -> 0x008e }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initServlet() throws javax.servlet.ServletException {
        /*
            r5 = this;
            r0 = 0
            javax.servlet.Servlet r1 = r5._servlet     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            if (r1 != 0) goto L_0x000b
            javax.servlet.Servlet r1 = r5.newInstance()     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            r5._servlet = r1     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
        L_0x000b:
            org.eclipse.jetty.servlet.ServletHolder$Config r1 = r5._config     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            if (r1 != 0) goto L_0x0016
            org.eclipse.jetty.servlet.ServletHolder$Config r1 = new org.eclipse.jetty.servlet.ServletHolder$Config     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            r1.<init>()     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            r5._config = r1     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
        L_0x0016:
            org.eclipse.jetty.security.IdentityService r1 = r5._identityService     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            if (r1 == 0) goto L_0x0029
            org.eclipse.jetty.security.IdentityService r1 = r5._identityService     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            org.eclipse.jetty.server.UserIdentity r2 = r2.getSystemUserIdentity()     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            org.eclipse.jetty.security.RunAsToken r3 = r5._runAsToken     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            java.lang.Object r1 = r1.setRunAs(r2, r3)     // Catch:{ UnavailableException -> 0x0084, ServletException -> 0x006e, Exception -> 0x005b, all -> 0x0058 }
            goto L_0x002a
        L_0x0029:
            r1 = r0
        L_0x002a:
            boolean r2 = r5.isJspServlet()     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
            if (r2 == 0) goto L_0x0033
            r5.initJspServlet()     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
        L_0x0033:
            r5.initMultiPart()     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
            javax.servlet.Servlet r2 = r5._servlet     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
            org.eclipse.jetty.servlet.ServletHolder$Config r3 = r5._config     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
            r2.init(r3)     // Catch:{ UnavailableException -> 0x0053, ServletException -> 0x004e, Exception -> 0x0049, all -> 0x0045 }
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService
            if (r0 == 0) goto L_0x0044
            r0.unsetRunAs(r1)
        L_0x0044:
            return
        L_0x0045:
            r0 = move-exception
            r2 = r1
            r1 = r0
            goto L_0x008f
        L_0x0049:
            r2 = move-exception
            r4 = r2
            r2 = r1
            r1 = r4
            goto L_0x005d
        L_0x004e:
            r2 = move-exception
            r4 = r2
            r2 = r1
            r1 = r4
            goto L_0x0070
        L_0x0053:
            r2 = move-exception
            r4 = r2
            r2 = r1
            r1 = r4
            goto L_0x0086
        L_0x0058:
            r1 = move-exception
            r2 = r0
            goto L_0x008f
        L_0x005b:
            r1 = move-exception
            r2 = r0
        L_0x005d:
            r5.makeUnavailable((java.lang.Throwable) r1)     // Catch:{ all -> 0x008e }
            r5._servlet = r0     // Catch:{ all -> 0x008e }
            r5._config = r0     // Catch:{ all -> 0x008e }
            javax.servlet.ServletException r0 = new javax.servlet.ServletException     // Catch:{ all -> 0x008e }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x008e }
            r0.<init>(r3, r1)     // Catch:{ all -> 0x008e }
            throw r0     // Catch:{ all -> 0x008e }
        L_0x006e:
            r1 = move-exception
            r2 = r0
        L_0x0070:
            java.lang.Throwable r3 = r1.getCause()     // Catch:{ all -> 0x008e }
            if (r3 != 0) goto L_0x0078
            r3 = r1
            goto L_0x007c
        L_0x0078:
            java.lang.Throwable r3 = r1.getCause()     // Catch:{ all -> 0x008e }
        L_0x007c:
            r5.makeUnavailable((java.lang.Throwable) r3)     // Catch:{ all -> 0x008e }
            r5._servlet = r0     // Catch:{ all -> 0x008e }
            r5._config = r0     // Catch:{ all -> 0x008e }
            throw r1     // Catch:{ all -> 0x008e }
        L_0x0084:
            r1 = move-exception
            r2 = r0
        L_0x0086:
            r5.makeUnavailable((javax.servlet.UnavailableException) r1)     // Catch:{ all -> 0x008e }
            r5._servlet = r0     // Catch:{ all -> 0x008e }
            r5._config = r0     // Catch:{ all -> 0x008e }
            throw r1     // Catch:{ all -> 0x008e }
        L_0x008e:
            r1 = move-exception
        L_0x008f:
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService
            if (r0 == 0) goto L_0x0096
            r0.unsetRunAs(r2)
        L_0x0096:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.initServlet():void");
    }

    /* access modifiers changed from: protected */
    public void initJspServlet() throws Exception {
        ContextHandler contextHandler = ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler();
        contextHandler.setAttribute("org.apache.catalina.jsp_classpath", contextHandler.getClassPath());
        setInitParameter("com.sun.appserv.jsp.classpath", Loader.getClassPath(contextHandler.getClassLoader().getParent()));
        if ("?".equals(getInitParameter("classpath"))) {
            String classPath = contextHandler.getClassPath();
            Logger logger = LOG;
            logger.debug("classpath=" + classPath, new Object[0]);
            if (classPath != null) {
                setInitParameter("classpath", classPath);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initMultiPart() throws Exception {
        if (((Registration) getRegistration()).getMultipartConfig() != null) {
            ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler().addEventListener(new Request.MultiPartCleanerListener());
        }
    }

    public String getContextPath() {
        return this._config.getServletContext().getContextPath();
    }

    public Map<String, String> getRoleRefMap() {
        return this._roleMap;
    }

    public String getRunAsRole() {
        return this._runAsRole;
    }

    public void setRunAsRole(String str) {
        this._runAsRole = str;
    }

    public void handle(Request request, ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, UnavailableException, IOException {
        if (this._class != null) {
            Servlet servlet = this._servlet;
            synchronized (this) {
                if (isStarted()) {
                    if (this._unavailable != 0 || !this._initOnStartup) {
                        servlet = getServlet();
                    }
                    if (servlet == null) {
                        throw new UnavailableException("Could not instantiate " + this._class);
                    }
                } else {
                    throw new UnavailableException("Servlet not initialized", -1);
                }
            }
            Object obj = null;
            boolean isAsyncSupported = request.isAsyncSupported();
            try {
                if (this._forcedPath != null) {
                    servletRequest.setAttribute(Dispatcher.__JSP_FILE, this._forcedPath);
                }
                if (this._identityService != null) {
                    obj = this._identityService.setRunAs(request.getResolvedUserIdentity(), this._runAsToken);
                }
                if (!isAsyncSupported()) {
                    request.setAsyncSupported(false);
                }
                MultipartConfigElement multipartConfig = ((Registration) getRegistration()).getMultipartConfig();
                if (multipartConfig != null) {
                    servletRequest.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, multipartConfig);
                }
                servlet.service(servletRequest, servletResponse);
                request.setAsyncSupported(isAsyncSupported);
                IdentityService identityService = this._identityService;
                if (identityService != null) {
                    identityService.unsetRunAs(obj);
                }
            } catch (UnavailableException e) {
                makeUnavailable(e);
                throw this._unavailableEx;
            } catch (Throwable th) {
                request.setAsyncSupported(isAsyncSupported);
                IdentityService identityService2 = this._identityService;
                if (identityService2 != null) {
                    identityService2.unsetRunAs((Object) null);
                }
                servletRequest.setAttribute(RequestDispatcher.ERROR_SERVLET_NAME, getName());
                throw th;
            }
        } else {
            throw new UnavailableException("Servlet Not Initialized");
        }
    }

    private boolean isJspServlet() {
        Servlet servlet = this._servlet;
        boolean z = false;
        if (servlet == null) {
            return false;
        }
        for (Class cls = servlet.getClass(); cls != null && !z; cls = cls.getSuperclass()) {
            z = isJspServlet(cls.getName());
        }
        return z;
    }

    private boolean isJspServlet(String str) {
        if (str == null) {
            return false;
        }
        return "org.apache.jasper.servlet.JspServlet".equals(str);
    }

    protected class Config extends Holder<Servlet>.HolderConfig implements ServletConfig {
        protected Config() {
            super();
        }

        public String getServletName() {
            return ServletHolder.this.getName();
        }
    }

    public class Registration extends Holder<Servlet>.HolderRegistration implements ServletRegistration.Dynamic {
        protected MultipartConfigElement _multipartConfig;

        public Registration() {
            super();
        }

        public /* bridge */ /* synthetic */ String getClassName() {
            return super.getClassName();
        }

        public /* bridge */ /* synthetic */ String getInitParameter(String str) {
            return super.getInitParameter(str);
        }

        public /* bridge */ /* synthetic */ Map getInitParameters() {
            return super.getInitParameters();
        }

        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public /* bridge */ /* synthetic */ void setAsyncSupported(boolean z) {
            super.setAsyncSupported(z);
        }

        public /* bridge */ /* synthetic */ void setDescription(String str) {
            super.setDescription(str);
        }

        public /* bridge */ /* synthetic */ boolean setInitParameter(String str, String str2) {
            return super.setInitParameter(str, str2);
        }

        public /* bridge */ /* synthetic */ Set setInitParameters(Map map) {
            return super.setInitParameters(map);
        }

        public Set<String> addMapping(String... strArr) {
            ServletHolder.this.illegalStateIfContextStarted();
            HashSet hashSet = null;
            for (String str : strArr) {
                ServletMapping servletMapping = ServletHolder.this._servletHandler.getServletMapping(str);
                if (servletMapping != null && !servletMapping.isDefault()) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(str);
                }
            }
            if (hashSet != null) {
                return hashSet;
            }
            ServletMapping servletMapping2 = new ServletMapping();
            servletMapping2.setServletName(ServletHolder.this.getName());
            servletMapping2.setPathSpecs(strArr);
            ServletHolder.this._servletHandler.addServletMapping(servletMapping2);
            return Collections.emptySet();
        }

        public Collection<String> getMappings() {
            String[] pathSpecs;
            ServletMapping[] servletMappings = ServletHolder.this._servletHandler.getServletMappings();
            ArrayList arrayList = new ArrayList();
            if (servletMappings != null) {
                for (ServletMapping servletMapping : servletMappings) {
                    if (servletMapping.getServletName().equals(getName()) && (pathSpecs = servletMapping.getPathSpecs()) != null && pathSpecs.length > 0) {
                        arrayList.addAll(Arrays.asList(pathSpecs));
                    }
                }
            }
            return arrayList;
        }

        public String getRunAsRole() {
            return ServletHolder.this._runAsRole;
        }

        public void setLoadOnStartup(int i) {
            ServletHolder.this.illegalStateIfContextStarted();
            ServletHolder.this.setInitOrder(i);
        }

        public int getInitOrder() {
            return ServletHolder.this.getInitOrder();
        }

        public void setMultipartConfig(MultipartConfigElement multipartConfigElement) {
            this._multipartConfig = multipartConfigElement;
        }

        public MultipartConfigElement getMultipartConfig() {
            return this._multipartConfig;
        }

        public void setRunAsRole(String str) {
            String unused = ServletHolder.this._runAsRole = str;
        }

        public Set<String> setServletSecurity(ServletSecurityElement servletSecurityElement) {
            return ServletHolder.this._servletHandler.setServletSecurity(this, servletSecurityElement);
        }
    }

    public ServletRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    private class SingleThreadedWrapper implements Servlet {
        Stack<Servlet> _stack;

        public String getServletInfo() {
            return null;
        }

        private SingleThreadedWrapper() {
            this._stack = new Stack<>();
        }

        public void destroy() {
            synchronized (this) {
                while (this._stack.size() > 0) {
                    try {
                        this._stack.pop().destroy();
                    } catch (Exception e) {
                        ServletHolder.LOG.warn(e);
                    }
                }
            }
        }

        public ServletConfig getServletConfig() {
            return ServletHolder.this._config;
        }

        public void init(ServletConfig servletConfig) throws ServletException {
            synchronized (this) {
                if (this._stack.size() == 0) {
                    try {
                        Servlet newInstance = ServletHolder.this.newInstance();
                        newInstance.init(servletConfig);
                        this._stack.push(newInstance);
                    } catch (ServletException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new ServletException((Throwable) e2);
                    }
                }
            }
        }

        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            Servlet servlet;
            synchronized (this) {
                if (this._stack.size() > 0) {
                    servlet = this._stack.pop();
                } else {
                    try {
                        servlet = ServletHolder.this.newInstance();
                        servlet.init(ServletHolder.this._config);
                    } catch (ServletException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new ServletException((Throwable) e2);
                    }
                }
            }
            try {
                servlet.service(servletRequest, servletResponse);
                synchronized (this) {
                    this._stack.push(servlet);
                }
            } catch (Throwable th) {
                synchronized (this) {
                    this._stack.push(servlet);
                    throw th;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Servlet newInstance() throws ServletException, IllegalAccessException, InstantiationException {
        try {
            ServletContext servletContext = getServletHandler().getServletContext();
            if (servletContext == null) {
                return (Servlet) getHeldClass().newInstance();
            }
            return ((ServletContextHandler.Context) servletContext).createServlet(getHeldClass());
        } catch (ServletException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof InstantiationException) {
                throw ((InstantiationException) rootCause);
            } else if (rootCause instanceof IllegalAccessException) {
                throw ((IllegalAccessException) rootCause);
            } else {
                throw e;
            }
        }
    }
}
