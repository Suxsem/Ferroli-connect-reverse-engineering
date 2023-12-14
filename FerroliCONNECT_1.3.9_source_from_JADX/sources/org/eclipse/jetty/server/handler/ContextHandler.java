package org.eclipse.jetty.server.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public class ContextHandler extends ScopedHandler implements Attributes, Server.Graceful {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) ContextHandler.class);
    public static final String MANAGED_ATTRIBUTES = "org.eclipse.jetty.server.context.ManagedAttributes";
    private static final int __AVAILABLE = 1;
    private static final int __SHUTDOWN = 2;
    private static final int __STOPPED = 0;
    private static final int __UNAVAILABLE = 3;
    private static final ThreadLocal<Context> __context = new ThreadLocal<>();
    private final CopyOnWriteArrayList<AliasCheck> _aliasChecks;
    private boolean _aliasesAllowed;
    private boolean _allowNullPathInfo;
    /* access modifiers changed from: private */
    public final AttributesMap _attributes;
    private volatile int _availability;
    private boolean _available;
    private Resource _baseResource;
    /* access modifiers changed from: private */
    public ClassLoader _classLoader;
    private boolean _compactPath;
    private Set<String> _connectors;
    /* access modifiers changed from: private */
    public Object _contextAttributeListeners;
    /* access modifiers changed from: private */
    public final AttributesMap _contextAttributes;
    private Object _contextListeners;
    /* access modifiers changed from: private */
    public String _contextPath;
    private String _displayName;
    private Object _durableListeners;
    private ErrorHandler _errorHandler;
    private EventListener[] _eventListeners;
    private final Map<String, String> _initParams;
    private Map<String, String> _localeEncodingMap;
    /* access modifiers changed from: private */
    public Logger _logger;
    private Map<String, Object> _managedAttributes;
    private int _maxFormContentSize;
    private int _maxFormKeys;
    /* access modifiers changed from: private */
    public MimeTypes _mimeTypes;
    private String[] _protectedTargets;
    private Object _requestAttributeListeners;
    private Object _requestListeners;
    protected Context _scontext;
    private boolean _shutdown;
    private String[] _vhosts;
    private String[] _welcomeFiles;

    public interface AliasCheck {
        boolean check(String str, Resource resource);
    }

    public void restrictEventListener(EventListener eventListener) {
    }

    public static Context getCurrentContext() {
        return __context.get();
    }

    public ContextHandler() {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", -1).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", -1).intValue();
        this._compactPath = false;
        this._aliasesAllowed = false;
        this._aliasChecks = new CopyOnWriteArrayList<>();
        this._shutdown = false;
        this._available = true;
        this._scontext = new Context();
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
        addAliasCheck(new ApproveNonExistentDirectoryAliases());
    }

    protected ContextHandler(Context context) {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", -1).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", -1).intValue();
        this._compactPath = false;
        this._aliasesAllowed = false;
        this._aliasChecks = new CopyOnWriteArrayList<>();
        this._shutdown = false;
        this._available = true;
        this._scontext = context;
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
        addAliasCheck(new ApproveNonExistentDirectoryAliases());
    }

    public ContextHandler(String str) {
        this();
        setContextPath(str);
    }

    public ContextHandler(HandlerContainer handlerContainer, String str) {
        this();
        setContextPath(str);
        if (handlerContainer instanceof HandlerWrapper) {
            ((HandlerWrapper) handlerContainer).setHandler(this);
        } else if (handlerContainer instanceof HandlerCollection) {
            ((HandlerCollection) handlerContainer).addHandler(this);
        }
    }

    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, Collections.singletonList(new CLDump(getClassLoader())), TypeUtil.asList(getHandlers()), getBeans(), this._initParams.entrySet(), this._attributes.getAttributeEntrySet(), this._contextAttributes.getAttributeEntrySet());
    }

    public Context getServletContext() {
        return this._scontext;
    }

    public boolean getAllowNullPathInfo() {
        return this._allowNullPathInfo;
    }

    public void setAllowNullPathInfo(boolean z) {
        this._allowNullPathInfo = z;
    }

    public void setServer(Server server) {
        if (this._errorHandler != null) {
            Server server2 = getServer();
            if (!(server2 == null || server2 == server)) {
                server2.getContainer().update((Object) this, (Object) this._errorHandler, (Object) null, "error", true);
            }
            super.setServer(server);
            if (!(server == null || server == server2)) {
                server.getContainer().update((Object) this, (Object) null, (Object) this._errorHandler, "error", true);
            }
            this._errorHandler.setServer(server);
            return;
        }
        super.setServer(server);
    }

    public void setVirtualHosts(String[] strArr) {
        if (strArr == null) {
            this._vhosts = strArr;
            return;
        }
        this._vhosts = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this._vhosts[i] = normalizeHostname(strArr[i]);
        }
    }

    public void addVirtualHosts(String[] strArr) {
        ArrayList arrayList;
        if (strArr != null) {
            String[] strArr2 = this._vhosts;
            if (strArr2 != null) {
                arrayList = new ArrayList(Arrays.asList(strArr2));
            } else {
                arrayList = new ArrayList();
            }
            for (String normalizeHostname : strArr) {
                String normalizeHostname2 = normalizeHostname(normalizeHostname);
                if (!arrayList.contains(normalizeHostname2)) {
                    arrayList.add(normalizeHostname2);
                }
            }
            this._vhosts = (String[]) arrayList.toArray(new String[0]);
        }
    }

    public void removeVirtualHosts(String[] strArr) {
        String[] strArr2;
        if (strArr != null && (strArr2 = this._vhosts) != null && strArr2.length != 0) {
            ArrayList arrayList = new ArrayList(Arrays.asList(strArr2));
            for (String normalizeHostname : strArr) {
                String normalizeHostname2 = normalizeHostname(normalizeHostname);
                if (arrayList.contains(normalizeHostname2)) {
                    arrayList.remove(normalizeHostname2);
                }
            }
            if (arrayList.isEmpty()) {
                this._vhosts = null;
            } else {
                this._vhosts = (String[]) arrayList.toArray(new String[0]);
            }
        }
    }

    public String[] getVirtualHosts() {
        return this._vhosts;
    }

    public String[] getConnectorNames() {
        Set<String> set = this._connectors;
        if (set == null || set.size() == 0) {
            return null;
        }
        Set<String> set2 = this._connectors;
        return (String[]) set2.toArray(new String[set2.size()]);
    }

    public void setConnectorNames(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            this._connectors = null;
        } else {
            this._connectors = new HashSet(Arrays.asList(strArr));
        }
    }

    public Object getAttribute(String str) {
        return this._attributes.getAttribute(str);
    }

    public Enumeration getAttributeNames() {
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        return this._attributes;
    }

    public ClassLoader getClassLoader() {
        return this._classLoader;
    }

    public String getClassPath() {
        ClassLoader classLoader = this._classLoader;
        if (classLoader == null || !(classLoader instanceof URLClassLoader)) {
            return null;
        }
        URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uRLs.length; i++) {
            try {
                File file = newResource(uRLs[i]).getFile();
                if (file != null && file.exists()) {
                    if (sb.length() > 0) {
                        sb.append(File.pathSeparatorChar);
                    }
                    sb.append(file.getAbsolutePath());
                }
            } catch (IOException e) {
                LOG.debug(e);
            }
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String getContextPath() {
        return this._contextPath;
    }

    public String getInitParameter(String str) {
        return this._initParams.get(str);
    }

    public String setInitParameter(String str, String str2) {
        return this._initParams.put(str, str2);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(this._initParams.keySet());
    }

    public Map<String, String> getInitParams() {
        return this._initParams;
    }

    public String getDisplayName() {
        return this._displayName;
    }

    public EventListener[] getEventListeners() {
        return this._eventListeners;
    }

    public void setEventListeners(EventListener[] eventListenerArr) {
        this._contextListeners = null;
        this._contextAttributeListeners = null;
        this._requestListeners = null;
        this._requestAttributeListeners = null;
        this._eventListeners = eventListenerArr;
        int i = 0;
        while (eventListenerArr != null && i < eventListenerArr.length) {
            EventListener eventListener = this._eventListeners[i];
            if (eventListener instanceof ServletContextListener) {
                this._contextListeners = LazyList.add(this._contextListeners, eventListener);
            }
            if (eventListener instanceof ServletContextAttributeListener) {
                this._contextAttributeListeners = LazyList.add(this._contextAttributeListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestListener) {
                this._requestListeners = LazyList.add(this._requestListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestAttributeListener) {
                this._requestAttributeListeners = LazyList.add(this._requestAttributeListeners, eventListener);
            }
            i++;
        }
    }

    public void addEventListener(EventListener eventListener) {
        if (!isStarted() && !isStarting()) {
            this._durableListeners = LazyList.add(this._durableListeners, eventListener);
        }
        setEventListeners((EventListener[]) LazyList.addToArray(getEventListeners(), eventListener, EventListener.class));
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this) {
            z = !this._shutdown;
        }
        return z;
    }

    public void setShutdown(boolean z) {
        synchronized (this) {
            this._shutdown = z;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public boolean isAvailable() {
        boolean z;
        synchronized (this) {
            z = this._available;
        }
        return z;
    }

    public void setAvailable(boolean z) {
        synchronized (this) {
            this._available = z;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public Logger getLogger() {
        return this._logger;
    }

    public void setLogger(Logger logger) {
        this._logger = logger;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStart() throws java.lang.Exception {
        /*
            r6 = this;
            r0 = 0
            r6._availability = r0
            java.lang.String r0 = r6._contextPath
            if (r0 == 0) goto L_0x0089
            java.lang.String r0 = r6.getDisplayName()
            if (r0 != 0) goto L_0x0012
            java.lang.String r0 = r6.getContextPath()
            goto L_0x0016
        L_0x0012:
            java.lang.String r0 = r6.getDisplayName()
        L_0x0016:
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.util.log.Log.getLogger((java.lang.String) r0)
            r6._logger = r0
            r0 = 0
            java.lang.ClassLoader r1 = r6._classLoader     // Catch:{ all -> 0x0079 }
            if (r1 == 0) goto L_0x0032
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0079 }
            java.lang.ClassLoader r2 = r1.getContextClassLoader()     // Catch:{ all -> 0x002f }
            java.lang.ClassLoader r3 = r6._classLoader     // Catch:{ all -> 0x0077 }
            r1.setContextClassLoader(r3)     // Catch:{ all -> 0x0077 }
            goto L_0x0034
        L_0x002f:
            r3 = move-exception
            r2 = r0
            goto L_0x007c
        L_0x0032:
            r1 = r0
            r2 = r1
        L_0x0034:
            org.eclipse.jetty.http.MimeTypes r3 = r6._mimeTypes     // Catch:{ all -> 0x0077 }
            if (r3 != 0) goto L_0x003f
            org.eclipse.jetty.http.MimeTypes r3 = new org.eclipse.jetty.http.MimeTypes     // Catch:{ all -> 0x0077 }
            r3.<init>()     // Catch:{ all -> 0x0077 }
            r6._mimeTypes = r3     // Catch:{ all -> 0x0077 }
        L_0x003f:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = __context     // Catch:{ all -> 0x0077 }
            java.lang.Object r3 = r3.get()     // Catch:{ all -> 0x0077 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r3     // Catch:{ all -> 0x0077 }
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = __context     // Catch:{ all -> 0x0072 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r4 = r6._scontext     // Catch:{ all -> 0x0072 }
            r0.set(r4)     // Catch:{ all -> 0x0072 }
            r6.startContext()     // Catch:{ all -> 0x0072 }
            monitor-enter(r6)     // Catch:{ all -> 0x0072 }
            boolean r0 = r6._shutdown     // Catch:{ all -> 0x006f }
            if (r0 == 0) goto L_0x0058
            r0 = 2
            goto L_0x005f
        L_0x0058:
            boolean r0 = r6._available     // Catch:{ all -> 0x006f }
            if (r0 == 0) goto L_0x005e
            r0 = 1
            goto L_0x005f
        L_0x005e:
            r0 = 3
        L_0x005f:
            r6._availability = r0     // Catch:{ all -> 0x006f }
            monitor-exit(r6)     // Catch:{ all -> 0x006f }
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = __context
            r0.set(r3)
            java.lang.ClassLoader r0 = r6._classLoader
            if (r0 == 0) goto L_0x006e
            r1.setContextClassLoader(r2)
        L_0x006e:
            return
        L_0x006f:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x006f }
            throw r0     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r0 = move-exception
            r5 = r3
            r3 = r0
            r0 = r5
            goto L_0x007c
        L_0x0077:
            r3 = move-exception
            goto L_0x007c
        L_0x0079:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L_0x007c:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r4 = __context
            r4.set(r0)
            java.lang.ClassLoader r0 = r6._classLoader
            if (r0 == 0) goto L_0x0088
            r1.setContextClassLoader(r2)
        L_0x0088:
            throw r3
        L_0x0089:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Null contextPath"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStart():void");
    }

    /* access modifiers changed from: protected */
    public void startContext() throws Exception {
        String str = this._initParams.get(MANAGED_ATTRIBUTES);
        if (str != null) {
            this._managedAttributes = new HashMap();
            for (String put : str.split(",")) {
                this._managedAttributes.put(put, (Object) null);
            }
            Enumeration attributeNames = this._scontext.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String str2 = (String) attributeNames.nextElement();
                checkManagedAttribute(str2, this._scontext.getAttribute(str2));
            }
        }
        super.doStart();
        ErrorHandler errorHandler = this._errorHandler;
        if (errorHandler != null) {
            errorHandler.start();
        }
        if (this._contextListeners != null) {
            ServletContextEvent servletContextEvent = new ServletContextEvent(this._scontext);
            for (int i = 0; i < LazyList.size(this._contextListeners); i++) {
                callContextInitialized((ServletContextListener) LazyList.get(this._contextListeners, i), servletContextEvent);
            }
        }
    }

    public void callContextInitialized(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextInitialized(servletContextEvent);
    }

    public void callContextDestroyed(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextDestroyed(servletContextEvent);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStop() throws java.lang.Exception {
        /*
            r11 = this;
            java.lang.String r0 = "stopped {}"
            r1 = 0
            r11._availability = r1
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r2 = __context
            java.lang.Object r2 = r2.get()
            org.eclipse.jetty.server.handler.ContextHandler$Context r2 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r2
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = __context
            org.eclipse.jetty.server.handler.ContextHandler$Context r4 = r11._scontext
            r3.set(r4)
            r3 = 1
            r4 = 0
            java.lang.ClassLoader r5 = r11._classLoader     // Catch:{ all -> 0x00a2 }
            if (r5 == 0) goto L_0x002e
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00a2 }
            java.lang.ClassLoader r6 = r5.getContextClassLoader()     // Catch:{ all -> 0x0028 }
            java.lang.ClassLoader r7 = r11._classLoader     // Catch:{ all -> 0x00a0 }
            r5.setContextClassLoader(r7)     // Catch:{ all -> 0x00a0 }
            goto L_0x0030
        L_0x0028:
            r6 = move-exception
            r10 = r6
            r6 = r4
            r4 = r10
            goto L_0x00a6
        L_0x002e:
            r5 = r4
            r6 = r5
        L_0x0030:
            super.doStop()     // Catch:{ all -> 0x00a0 }
            java.lang.Object r7 = r11._contextListeners     // Catch:{ all -> 0x00a0 }
            if (r7 == 0) goto L_0x0055
            javax.servlet.ServletContextEvent r7 = new javax.servlet.ServletContextEvent     // Catch:{ all -> 0x00a0 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r8 = r11._scontext     // Catch:{ all -> 0x00a0 }
            r7.<init>(r8)     // Catch:{ all -> 0x00a0 }
            java.lang.Object r8 = r11._contextListeners     // Catch:{ all -> 0x00a0 }
            int r8 = org.eclipse.jetty.util.LazyList.size(r8)     // Catch:{ all -> 0x00a0 }
        L_0x0044:
            int r9 = r8 + -1
            if (r8 <= 0) goto L_0x0055
            java.lang.Object r8 = r11._contextListeners     // Catch:{ all -> 0x00a0 }
            java.lang.Object r8 = org.eclipse.jetty.util.LazyList.get(r8, r9)     // Catch:{ all -> 0x00a0 }
            javax.servlet.ServletContextListener r8 = (javax.servlet.ServletContextListener) r8     // Catch:{ all -> 0x00a0 }
            r8.contextDestroyed(r7)     // Catch:{ all -> 0x00a0 }
            r8 = r9
            goto L_0x0044
        L_0x0055:
            java.lang.Object r7 = r11._durableListeners     // Catch:{ all -> 0x00a0 }
            java.lang.Class<java.util.EventListener> r8 = java.util.EventListener.class
            java.lang.Object r7 = org.eclipse.jetty.util.LazyList.toArray(r7, r8)     // Catch:{ all -> 0x00a0 }
            java.util.EventListener[] r7 = (java.util.EventListener[]) r7     // Catch:{ all -> 0x00a0 }
            java.util.EventListener[] r7 = (java.util.EventListener[]) r7     // Catch:{ all -> 0x00a0 }
            r11.setEventListeners(r7)     // Catch:{ all -> 0x00a0 }
            r11._durableListeners = r4     // Catch:{ all -> 0x00a0 }
            org.eclipse.jetty.server.handler.ErrorHandler r7 = r11._errorHandler     // Catch:{ all -> 0x00a0 }
            if (r7 == 0) goto L_0x006f
            org.eclipse.jetty.server.handler.ErrorHandler r7 = r11._errorHandler     // Catch:{ all -> 0x00a0 }
            r7.stop()     // Catch:{ all -> 0x00a0 }
        L_0x006f:
            org.eclipse.jetty.server.handler.ContextHandler$Context r7 = r11._scontext     // Catch:{ all -> 0x00a0 }
            java.util.Enumeration r7 = r7.getAttributeNames()     // Catch:{ all -> 0x00a0 }
        L_0x0075:
            boolean r8 = r7.hasMoreElements()     // Catch:{ all -> 0x00a0 }
            if (r8 == 0) goto L_0x0085
            java.lang.Object r8 = r7.nextElement()     // Catch:{ all -> 0x00a0 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x00a0 }
            r11.checkManagedAttribute(r8, r4)     // Catch:{ all -> 0x00a0 }
            goto L_0x0075
        L_0x0085:
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r1] = r11
            r4.info((java.lang.String) r0, (java.lang.Object[]) r3)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = __context
            r0.set(r2)
            java.lang.ClassLoader r0 = r11._classLoader
            if (r0 == 0) goto L_0x009a
            r5.setContextClassLoader(r6)
        L_0x009a:
            org.eclipse.jetty.util.AttributesMap r0 = r11._contextAttributes
            r0.clearAttributes()
            return
        L_0x00a0:
            r4 = move-exception
            goto L_0x00a6
        L_0x00a2:
            r5 = move-exception
            r6 = r4
            r4 = r5
            r5 = r6
        L_0x00a6:
            org.eclipse.jetty.util.log.Logger r7 = LOG
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r1] = r11
            r7.info((java.lang.String) r0, (java.lang.Object[]) r3)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = __context
            r0.set(r2)
            java.lang.ClassLoader r0 = r11._classLoader
            if (r0 == 0) goto L_0x00bb
            r5.setContextClassLoader(r6)
        L_0x00bb:
            goto L_0x00bd
        L_0x00bc:
            throw r4
        L_0x00bd:
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStop():void");
    }

    public boolean checkContext(String str, Request request, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        String name;
        DispatcherType dispatcherType = request.getDispatcherType();
        int i = this._availability;
        if (!(i == 0 || i == 2)) {
            if (i == 3) {
                request.setHandled(true);
                httpServletResponse.sendError(503);
            } else if (DispatcherType.REQUEST.equals(dispatcherType) && request.isHandled()) {
                return false;
            } else {
                String[] strArr = this._vhosts;
                if (strArr != null && strArr.length > 0) {
                    String normalizeHostname = normalizeHostname(request.getServerName());
                    boolean z = false;
                    int i2 = 0;
                    while (!z) {
                        String[] strArr2 = this._vhosts;
                        if (i2 >= strArr2.length) {
                            break;
                        }
                        String str2 = strArr2[i2];
                        if (str2 != null) {
                            if (str2.startsWith("*.")) {
                                z = str2.regionMatches(true, 2, normalizeHostname, normalizeHostname.indexOf(".") + 1, str2.length() - 2);
                            } else {
                                z = str2.equalsIgnoreCase(normalizeHostname);
                            }
                        }
                        i2++;
                    }
                    if (!z) {
                        return false;
                    }
                }
                Set<String> set = this._connectors;
                if (set != null && set.size() > 0 && ((name = AbstractHttpConnection.getCurrentConnection().getConnector().getName()) == null || !this._connectors.contains(name))) {
                    return false;
                }
                if (this._contextPath.length() > 1) {
                    if (!str.startsWith(this._contextPath)) {
                        return false;
                    }
                    if (str.length() > this._contextPath.length() && str.charAt(this._contextPath.length()) != '/') {
                        return false;
                    }
                    if (!this._allowNullPathInfo && this._contextPath.length() == str.length()) {
                        request.setHandled(true);
                        if (request.getQueryString() != null) {
                            httpServletResponse.sendRedirect(URIUtil.addPaths(request.getRequestURI(), "/") + "?" + request.getQueryString());
                        } else {
                            httpServletResponse.sendRedirect(URIUtil.addPaths(request.getRequestURI(), "/"));
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ec A[Catch:{ all -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f2 A[Catch:{ all -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0106 A[Catch:{ all -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012e A[Catch:{ all -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0132 A[Catch:{ all -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:87:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doScope(java.lang.String r18, org.eclipse.jetty.server.Request r19, javax.servlet.http.HttpServletRequest r20, javax.servlet.http.HttpServletResponse r21) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r17 = this;
            r1 = r17
            r2 = r19
            r0 = r20
            r3 = r21
            org.eclipse.jetty.util.log.Logger r4 = LOG
            boolean r4 = r4.isDebugEnabled()
            r5 = 3
            r6 = 2
            r7 = 0
            r8 = 4
            r9 = 1
            if (r4 == 0) goto L_0x0032
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.Object[] r10 = new java.lang.Object[r8]
            java.lang.String r11 = r19.getContextPath()
            r10[r7] = r11
            java.lang.String r11 = r19.getServletPath()
            r10[r9] = r11
            java.lang.String r11 = r19.getPathInfo()
            r10[r6] = r11
            r10[r5] = r1
            java.lang.String r11 = "scope {}|{}|{} @ {}"
            r4.debug((java.lang.String) r11, (java.lang.Object[]) r10)
        L_0x0032:
            javax.servlet.DispatcherType r4 = r19.getDispatcherType()
            org.eclipse.jetty.server.handler.ContextHandler$Context r10 = r19.getContext()
            org.eclipse.jetty.server.handler.ContextHandler$Context r11 = r1._scontext
            java.lang.String r12 = "/"
            if (r10 == r11) goto L_0x00b8
            javax.servlet.DispatcherType r11 = javax.servlet.DispatcherType.REQUEST
            boolean r11 = r11.equals(r4)
            if (r11 != 0) goto L_0x0067
            javax.servlet.DispatcherType r11 = javax.servlet.DispatcherType.ASYNC
            boolean r11 = r11.equals(r4)
            if (r11 != 0) goto L_0x0067
            javax.servlet.DispatcherType r11 = javax.servlet.DispatcherType.ERROR
            boolean r11 = r11.equals(r4)
            if (r11 == 0) goto L_0x0063
            org.eclipse.jetty.server.AsyncContinuation r11 = r19.getAsyncContinuation()
            boolean r11 = r11.isExpired()
            if (r11 == 0) goto L_0x0063
            goto L_0x0067
        L_0x0063:
            r11 = r18
        L_0x0065:
            r14 = r11
            goto L_0x00a4
        L_0x0067:
            boolean r11 = r1._compactPath
            if (r11 == 0) goto L_0x0070
            java.lang.String r11 = org.eclipse.jetty.util.URIUtil.compactPath(r18)
            goto L_0x0072
        L_0x0070:
            r11 = r18
        L_0x0072:
            boolean r14 = r1.checkContext(r11, r2, r3)
            if (r14 != 0) goto L_0x0079
            return
        L_0x0079:
            int r14 = r11.length()
            java.lang.String r15 = r1._contextPath
            int r15 = r15.length()
            if (r14 <= r15) goto L_0x0098
            java.lang.String r14 = r1._contextPath
            int r14 = r14.length()
            if (r14 <= r9) goto L_0x0065
            java.lang.String r14 = r1._contextPath
            int r14 = r14.length()
            java.lang.String r11 = r11.substring(r14)
            goto L_0x0065
        L_0x0098:
            java.lang.String r11 = r1._contextPath
            int r11 = r11.length()
            if (r11 != r9) goto L_0x00a2
            r11 = r12
            goto L_0x0065
        L_0x00a2:
            r11 = r12
            r14 = 0
        L_0x00a4:
            java.lang.ClassLoader r15 = r1._classLoader
            if (r15 == 0) goto L_0x00bb
            java.lang.Thread r15 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r16 = r15.getContextClassLoader()
            java.lang.ClassLoader r5 = r1._classLoader
            r15.setContextClassLoader(r5)
            r5 = r16
            goto L_0x00bd
        L_0x00b8:
            r11 = r18
            r14 = r11
        L_0x00bb:
            r5 = 0
            r15 = 0
        L_0x00bd:
            java.lang.String r6 = r19.getContextPath()     // Catch:{ all -> 0x0170 }
            java.lang.String r7 = r19.getServletPath()     // Catch:{ all -> 0x016c }
            java.lang.String r8 = r19.getPathInfo()     // Catch:{ all -> 0x0168 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r13 = r1._scontext     // Catch:{ all -> 0x0166 }
            r2.setContext(r13)     // Catch:{ all -> 0x0166 }
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r13 = __context     // Catch:{ all -> 0x0166 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r9 = r1._scontext     // Catch:{ all -> 0x0166 }
            r13.set(r9)     // Catch:{ all -> 0x0166 }
            javax.servlet.DispatcherType r9 = javax.servlet.DispatcherType.INCLUDE     // Catch:{ all -> 0x0166 }
            boolean r4 = r9.equals(r4)     // Catch:{ all -> 0x0166 }
            if (r4 != 0) goto L_0x00fe
            boolean r4 = r11.startsWith(r12)     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x00fe
            java.lang.String r4 = r1._contextPath     // Catch:{ all -> 0x0166 }
            int r4 = r4.length()     // Catch:{ all -> 0x0166 }
            r9 = 1
            if (r4 != r9) goto L_0x00f2
            java.lang.String r4 = ""
            r2.setContextPath(r4)     // Catch:{ all -> 0x0166 }
            goto L_0x00f7
        L_0x00f2:
            java.lang.String r4 = r1._contextPath     // Catch:{ all -> 0x0166 }
            r2.setContextPath(r4)     // Catch:{ all -> 0x0166 }
        L_0x00f7:
            r4 = 0
            r2.setServletPath(r4)     // Catch:{ all -> 0x0166 }
            r2.setPathInfo(r14)     // Catch:{ all -> 0x0166 }
        L_0x00fe:
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x0166 }
            boolean r4 = r4.isDebugEnabled()     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x0128
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x0166 }
            java.lang.String r9 = "context={}|{}|{} @ {}"
            r12 = 4
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ all -> 0x0166 }
            java.lang.String r13 = r19.getContextPath()     // Catch:{ all -> 0x0166 }
            r14 = 0
            r12[r14] = r13     // Catch:{ all -> 0x0166 }
            java.lang.String r13 = r19.getServletPath()     // Catch:{ all -> 0x0166 }
            r14 = 1
            r12[r14] = r13     // Catch:{ all -> 0x0166 }
            java.lang.String r13 = r19.getPathInfo()     // Catch:{ all -> 0x0166 }
            r14 = 2
            r12[r14] = r13     // Catch:{ all -> 0x0166 }
            r13 = 3
            r12[r13] = r1     // Catch:{ all -> 0x0166 }
            r4.debug((java.lang.String) r9, (java.lang.Object[]) r12)     // Catch:{ all -> 0x0166 }
        L_0x0128:
            boolean r4 = r17.never()     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x0132
            r1.nextScope(r11, r2, r0, r3)     // Catch:{ all -> 0x0166 }
            goto L_0x0149
        L_0x0132:
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r1._nextScope     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x013c
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r1._nextScope     // Catch:{ all -> 0x0166 }
            r4.doScope(r11, r2, r0, r3)     // Catch:{ all -> 0x0166 }
            goto L_0x0149
        L_0x013c:
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r1._outerScope     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x0146
            org.eclipse.jetty.server.handler.ScopedHandler r4 = r1._outerScope     // Catch:{ all -> 0x0166 }
            r4.doHandle(r11, r2, r0, r3)     // Catch:{ all -> 0x0166 }
            goto L_0x0149
        L_0x0146:
            r1.doHandle(r11, r2, r0, r3)     // Catch:{ all -> 0x0166 }
        L_0x0149:
            org.eclipse.jetty.server.handler.ContextHandler$Context r0 = r1._scontext
            if (r10 == r0) goto L_0x0165
            java.lang.ClassLoader r0 = r1._classLoader
            if (r0 == 0) goto L_0x0154
            r15.setContextClassLoader(r5)
        L_0x0154:
            r2.setContext(r10)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = __context
            r0.set(r10)
            r2.setContextPath(r6)
            r2.setServletPath(r7)
            r2.setPathInfo(r8)
        L_0x0165:
            return
        L_0x0166:
            r0 = move-exception
            goto L_0x0175
        L_0x0168:
            r0 = move-exception
            r4 = 0
            r8 = r4
            goto L_0x0175
        L_0x016c:
            r0 = move-exception
            r4 = 0
            r7 = r4
            goto L_0x0174
        L_0x0170:
            r0 = move-exception
            r4 = 0
            r6 = r4
            r7 = r6
        L_0x0174:
            r8 = r7
        L_0x0175:
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = r1._scontext
            if (r10 == r3) goto L_0x0191
            java.lang.ClassLoader r3 = r1._classLoader
            if (r3 == 0) goto L_0x0180
            r15.setContextClassLoader(r5)
        L_0x0180:
            r2.setContext(r10)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = __context
            r3.set(r10)
            r2.setContextPath(r6)
            r2.setServletPath(r7)
            r2.setPathInfo(r8)
        L_0x0191:
            goto L_0x0193
        L_0x0192:
            throw r0
        L_0x0193:
            goto L_0x0192
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doScope(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void doHandle(java.lang.String r7, org.eclipse.jetty.server.Request r8, javax.servlet.http.HttpServletRequest r9, javax.servlet.http.HttpServletResponse r10) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r6 = this;
            javax.servlet.DispatcherType r0 = r8.getDispatcherType()
            boolean r1 = r8.takeNewContext()
            if (r1 == 0) goto L_0x0047
            java.lang.Object r2 = r6._requestAttributeListeners     // Catch:{ HttpException -> 0x00c0 }
            r3 = 0
            if (r2 == 0) goto L_0x0026
            java.lang.Object r2 = r6._requestAttributeListeners     // Catch:{ HttpException -> 0x00c0 }
            int r2 = org.eclipse.jetty.util.LazyList.size(r2)     // Catch:{ HttpException -> 0x00c0 }
            r4 = 0
        L_0x0016:
            if (r4 >= r2) goto L_0x0026
            java.lang.Object r5 = r6._requestAttributeListeners     // Catch:{ HttpException -> 0x00c0 }
            java.lang.Object r5 = org.eclipse.jetty.util.LazyList.get(r5, r4)     // Catch:{ HttpException -> 0x00c0 }
            java.util.EventListener r5 = (java.util.EventListener) r5     // Catch:{ HttpException -> 0x00c0 }
            r8.addEventListener(r5)     // Catch:{ HttpException -> 0x00c0 }
            int r4 = r4 + 1
            goto L_0x0016
        L_0x0026:
            java.lang.Object r2 = r6._requestListeners     // Catch:{ HttpException -> 0x00c0 }
            if (r2 == 0) goto L_0x0047
            java.lang.Object r2 = r6._requestListeners     // Catch:{ HttpException -> 0x00c0 }
            int r2 = org.eclipse.jetty.util.LazyList.size(r2)     // Catch:{ HttpException -> 0x00c0 }
            javax.servlet.ServletRequestEvent r4 = new javax.servlet.ServletRequestEvent     // Catch:{ HttpException -> 0x00c0 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r5 = r6._scontext     // Catch:{ HttpException -> 0x00c0 }
            r4.<init>(r5, r9)     // Catch:{ HttpException -> 0x00c0 }
        L_0x0037:
            if (r3 >= r2) goto L_0x0047
            java.lang.Object r5 = r6._requestListeners     // Catch:{ HttpException -> 0x00c0 }
            java.lang.Object r5 = org.eclipse.jetty.util.LazyList.get(r5, r3)     // Catch:{ HttpException -> 0x00c0 }
            javax.servlet.ServletRequestListener r5 = (javax.servlet.ServletRequestListener) r5     // Catch:{ HttpException -> 0x00c0 }
            r5.requestInitialized(r4)     // Catch:{ HttpException -> 0x00c0 }
            int r3 = r3 + 1
            goto L_0x0037
        L_0x0047:
            javax.servlet.DispatcherType r2 = javax.servlet.DispatcherType.REQUEST     // Catch:{ HttpException -> 0x00c0 }
            boolean r0 = r2.equals(r0)     // Catch:{ HttpException -> 0x00c0 }
            if (r0 == 0) goto L_0x005e
            boolean r0 = r6.isProtectedTarget(r7)     // Catch:{ HttpException -> 0x00c0 }
            if (r0 != 0) goto L_0x0056
            goto L_0x005e
        L_0x0056:
            org.eclipse.jetty.http.HttpException r7 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x00c0 }
            r0 = 404(0x194, float:5.66E-43)
            r7.<init>(r0)     // Catch:{ HttpException -> 0x00c0 }
            throw r7     // Catch:{ HttpException -> 0x00c0 }
        L_0x005e:
            boolean r0 = r6.never()     // Catch:{ HttpException -> 0x00c0 }
            if (r0 == 0) goto L_0x0068
            r6.nextHandle(r7, r8, r9, r10)     // Catch:{ HttpException -> 0x00c0 }
            goto L_0x0081
        L_0x0068:
            org.eclipse.jetty.server.handler.ScopedHandler r0 = r6._nextScope     // Catch:{ HttpException -> 0x00c0 }
            if (r0 == 0) goto L_0x0078
            org.eclipse.jetty.server.handler.ScopedHandler r0 = r6._nextScope     // Catch:{ HttpException -> 0x00c0 }
            org.eclipse.jetty.server.Handler r2 = r6._handler     // Catch:{ HttpException -> 0x00c0 }
            if (r0 != r2) goto L_0x0078
            org.eclipse.jetty.server.handler.ScopedHandler r0 = r6._nextScope     // Catch:{ HttpException -> 0x00c0 }
            r0.doHandle(r7, r8, r9, r10)     // Catch:{ HttpException -> 0x00c0 }
            goto L_0x0081
        L_0x0078:
            org.eclipse.jetty.server.Handler r0 = r6._handler     // Catch:{ HttpException -> 0x00c0 }
            if (r0 == 0) goto L_0x0081
            org.eclipse.jetty.server.Handler r0 = r6._handler     // Catch:{ HttpException -> 0x00c0 }
            r0.handle(r7, r8, r9, r10)     // Catch:{ HttpException -> 0x00c0 }
        L_0x0081:
            if (r1 == 0) goto L_0x0112
            java.lang.Object r7 = r6._requestListeners
            if (r7 == 0) goto L_0x00a5
            javax.servlet.ServletRequestEvent r7 = new javax.servlet.ServletRequestEvent
            org.eclipse.jetty.server.handler.ContextHandler$Context r10 = r6._scontext
            r7.<init>(r10, r9)
            java.lang.Object r9 = r6._requestListeners
            int r9 = org.eclipse.jetty.util.LazyList.size(r9)
        L_0x0094:
            int r10 = r9 + -1
            if (r9 <= 0) goto L_0x00a5
            java.lang.Object r9 = r6._requestListeners
            java.lang.Object r9 = org.eclipse.jetty.util.LazyList.get(r9, r10)
            javax.servlet.ServletRequestListener r9 = (javax.servlet.ServletRequestListener) r9
            r9.requestDestroyed(r7)
            r9 = r10
            goto L_0x0094
        L_0x00a5:
            java.lang.Object r7 = r6._requestAttributeListeners
            if (r7 == 0) goto L_0x0112
            int r7 = org.eclipse.jetty.util.LazyList.size(r7)
        L_0x00ad:
            int r9 = r7 + -1
            if (r7 <= 0) goto L_0x0112
            java.lang.Object r7 = r6._requestAttributeListeners
            java.lang.Object r7 = org.eclipse.jetty.util.LazyList.get(r7, r9)
            java.util.EventListener r7 = (java.util.EventListener) r7
            r8.removeEventListener(r7)
            r7 = r9
            goto L_0x00ad
        L_0x00be:
            r7 = move-exception
            goto L_0x0113
        L_0x00c0:
            r7 = move-exception
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00be }
            r0.debug(r7)     // Catch:{ all -> 0x00be }
            r0 = 1
            r8.setHandled(r0)     // Catch:{ all -> 0x00be }
            int r0 = r7.getStatus()     // Catch:{ all -> 0x00be }
            java.lang.String r7 = r7.getReason()     // Catch:{ all -> 0x00be }
            r10.sendError(r0, r7)     // Catch:{ all -> 0x00be }
            if (r1 == 0) goto L_0x0112
            java.lang.Object r7 = r6._requestListeners
            if (r7 == 0) goto L_0x00f9
            javax.servlet.ServletRequestEvent r7 = new javax.servlet.ServletRequestEvent
            org.eclipse.jetty.server.handler.ContextHandler$Context r10 = r6._scontext
            r7.<init>(r10, r9)
            java.lang.Object r9 = r6._requestListeners
            int r9 = org.eclipse.jetty.util.LazyList.size(r9)
        L_0x00e8:
            int r10 = r9 + -1
            if (r9 <= 0) goto L_0x00f9
            java.lang.Object r9 = r6._requestListeners
            java.lang.Object r9 = org.eclipse.jetty.util.LazyList.get(r9, r10)
            javax.servlet.ServletRequestListener r9 = (javax.servlet.ServletRequestListener) r9
            r9.requestDestroyed(r7)
            r9 = r10
            goto L_0x00e8
        L_0x00f9:
            java.lang.Object r7 = r6._requestAttributeListeners
            if (r7 == 0) goto L_0x0112
            int r7 = org.eclipse.jetty.util.LazyList.size(r7)
        L_0x0101:
            int r9 = r7 + -1
            if (r7 <= 0) goto L_0x0112
            java.lang.Object r7 = r6._requestAttributeListeners
            java.lang.Object r7 = org.eclipse.jetty.util.LazyList.get(r7, r9)
            java.util.EventListener r7 = (java.util.EventListener) r7
            r8.removeEventListener(r7)
            r7 = r9
            goto L_0x0101
        L_0x0112:
            return
        L_0x0113:
            if (r1 == 0) goto L_0x0150
            java.lang.Object r10 = r6._requestListeners
            if (r10 == 0) goto L_0x0137
            javax.servlet.ServletRequestEvent r10 = new javax.servlet.ServletRequestEvent
            org.eclipse.jetty.server.handler.ContextHandler$Context r0 = r6._scontext
            r10.<init>(r0, r9)
            java.lang.Object r9 = r6._requestListeners
            int r9 = org.eclipse.jetty.util.LazyList.size(r9)
        L_0x0126:
            int r0 = r9 + -1
            if (r9 <= 0) goto L_0x0137
            java.lang.Object r9 = r6._requestListeners
            java.lang.Object r9 = org.eclipse.jetty.util.LazyList.get(r9, r0)
            javax.servlet.ServletRequestListener r9 = (javax.servlet.ServletRequestListener) r9
            r9.requestDestroyed(r10)
            r9 = r0
            goto L_0x0126
        L_0x0137:
            java.lang.Object r9 = r6._requestAttributeListeners
            if (r9 == 0) goto L_0x0150
            int r9 = org.eclipse.jetty.util.LazyList.size(r9)
        L_0x013f:
            int r10 = r9 + -1
            if (r9 <= 0) goto L_0x0150
            java.lang.Object r9 = r6._requestAttributeListeners
            java.lang.Object r9 = org.eclipse.jetty.util.LazyList.get(r9, r10)
            java.util.EventListener r9 = (java.util.EventListener) r9
            r8.removeEventListener(r9)
            r9 = r10
            goto L_0x013f
        L_0x0150:
            goto L_0x0152
        L_0x0151:
            throw r7
        L_0x0152:
            goto L_0x0151
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doHandle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(java.lang.Runnable r5) {
        /*
            r4 = this;
            r0 = 0
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r1 = __context     // Catch:{ all -> 0x0036 }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x0036 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r1 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r1     // Catch:{ all -> 0x0036 }
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r2 = __context     // Catch:{ all -> 0x0033 }
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = r4._scontext     // Catch:{ all -> 0x0033 }
            r2.set(r3)     // Catch:{ all -> 0x0033 }
            java.lang.ClassLoader r2 = r4._classLoader     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0022
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0033 }
            java.lang.ClassLoader r0 = r2.getContextClassLoader()     // Catch:{ all -> 0x0031 }
            java.lang.ClassLoader r3 = r4._classLoader     // Catch:{ all -> 0x0031 }
            r2.setContextClassLoader(r3)     // Catch:{ all -> 0x0031 }
            goto L_0x0023
        L_0x0022:
            r2 = r0
        L_0x0023:
            r5.run()     // Catch:{ all -> 0x0031 }
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r5 = __context
            r5.set(r1)
            if (r0 == 0) goto L_0x0030
            r2.setContextClassLoader(r0)
        L_0x0030:
            return
        L_0x0031:
            r5 = move-exception
            goto L_0x0039
        L_0x0033:
            r5 = move-exception
            r2 = r0
            goto L_0x0039
        L_0x0036:
            r5 = move-exception
            r1 = r0
            r2 = r1
        L_0x0039:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = __context
            r3.set(r1)
            if (r0 == 0) goto L_0x0043
            r2.setContextClassLoader(r0)
        L_0x0043:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.handle(java.lang.Runnable):void");
    }

    public boolean isProtectedTarget(String str) {
        boolean z = false;
        if (str != null && this._protectedTargets != null) {
            while (str.startsWith("//")) {
                str = URIUtil.compactPath(str);
            }
            int i = 0;
            while (!z) {
                String[] strArr = this._protectedTargets;
                if (i >= strArr.length) {
                    break;
                }
                boolean startsWithIgnoreCase = StringUtil.startsWithIgnoreCase(str, strArr[i]);
                i++;
                z = startsWithIgnoreCase;
            }
        }
        return z;
    }

    public void setProtectedTargets(String[] strArr) {
        if (strArr == null) {
            this._protectedTargets = null;
            return;
        }
        this._protectedTargets = new String[strArr.length];
        System.arraycopy(strArr, 0, this._protectedTargets, 0, strArr.length);
    }

    public String[] getProtectedTargets() {
        String[] strArr = this._protectedTargets;
        if (strArr == null) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    public void removeAttribute(String str) {
        checkManagedAttribute(str, (Object) null);
        this._attributes.removeAttribute(str);
    }

    public void setAttribute(String str, Object obj) {
        checkManagedAttribute(str, obj);
        this._attributes.setAttribute(str, obj);
    }

    public void setAttributes(Attributes attributes) {
        this._attributes.clearAttributes();
        this._attributes.addAll(attributes);
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            checkManagedAttribute(nextElement, attributes.getAttribute(nextElement));
        }
    }

    public void clearAttributes() {
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            checkManagedAttribute(attributeNames.nextElement(), (Object) null);
        }
        this._attributes.clearAttributes();
    }

    public void checkManagedAttribute(String str, Object obj) {
        Map<String, Object> map = this._managedAttributes;
        if (map != null && map.containsKey(str)) {
            setManagedAttribute(str, obj);
        }
    }

    public void setManagedAttribute(String str, Object obj) {
        getServer().getContainer().update((Object) this, this._managedAttributes.put(str, obj), obj, str, true);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this._classLoader = classLoader;
    }

    public void setContextPath(String str) {
        if (str == null || str.length() <= 1 || !str.endsWith("/")) {
            this._contextPath = str;
            if (getServer() == null) {
                return;
            }
            if (getServer().isStarting() || getServer().isStarted()) {
                Handler[] childHandlersByClass = getServer().getChildHandlersByClass(ContextHandlerCollection.class);
                int i = 0;
                while (childHandlersByClass != null && i < childHandlersByClass.length) {
                    ((ContextHandlerCollection) childHandlersByClass[i]).mapContexts();
                    i++;
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("ends with /");
    }

    public void setDisplayName(String str) {
        this._displayName = str;
    }

    public Resource getBaseResource() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource;
    }

    public String getResourceBase() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource.toString();
    }

    public void setBaseResource(Resource resource) {
        this._baseResource = resource;
    }

    public void setResourceBase(String str) {
        try {
            setBaseResource(newResource(str));
        } catch (Exception e) {
            LOG.warn(e.toString(), new Object[0]);
            LOG.debug(e);
            throw new IllegalArgumentException(str);
        }
    }

    public boolean isAliases() {
        return this._aliasesAllowed;
    }

    public void setAliases(boolean z) {
        this._aliasesAllowed = z;
    }

    public MimeTypes getMimeTypes() {
        if (this._mimeTypes == null) {
            this._mimeTypes = new MimeTypes();
        }
        return this._mimeTypes;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public void setWelcomeFiles(String[] strArr) {
        this._welcomeFiles = strArr;
    }

    public String[] getWelcomeFiles() {
        return this._welcomeFiles;
    }

    public ErrorHandler getErrorHandler() {
        return this._errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler != null) {
            errorHandler.setServer(getServer());
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) this._errorHandler, (Object) errorHandler, "errorHandler", true);
        }
        this._errorHandler = errorHandler;
    }

    public int getMaxFormContentSize() {
        return this._maxFormContentSize;
    }

    public void setMaxFormContentSize(int i) {
        this._maxFormContentSize = i;
    }

    public int getMaxFormKeys() {
        return this._maxFormKeys;
    }

    public void setMaxFormKeys(int i) {
        this._maxFormKeys = i;
    }

    public boolean isCompactPath() {
        return this._compactPath;
    }

    public void setCompactPath(boolean z) {
        this._compactPath = z;
    }

    public String toString() {
        String name;
        String[] virtualHosts = getVirtualHosts();
        StringBuilder sb = new StringBuilder();
        Package packageR = getClass().getPackage();
        if (!(packageR == null || (name = packageR.getName()) == null || name.length() <= 0)) {
            for (String charAt : name.split("\\.")) {
                sb.append(charAt.charAt(0));
                sb.append('.');
            }
        }
        sb.append(getClass().getSimpleName());
        sb.append('{');
        sb.append(getContextPath());
        sb.append(',');
        sb.append(getBaseResource());
        if (virtualHosts != null && virtualHosts.length > 0) {
            sb.append(',');
            sb.append(virtualHosts[0]);
        }
        sb.append('}');
        return sb.toString();
    }

    public synchronized Class<?> loadClass(String str) throws ClassNotFoundException {
        if (str == null) {
            return null;
        }
        if (this._classLoader == null) {
            return Loader.loadClass(getClass(), str);
        }
        return this._classLoader.loadClass(str);
    }

    public void addLocaleEncoding(String str, String str2) {
        if (this._localeEncodingMap == null) {
            this._localeEncodingMap = new HashMap();
        }
        this._localeEncodingMap.put(str, str2);
    }

    public String getLocaleEncoding(String str) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public String getLocaleEncoding(Locale locale) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        String str = map.get(locale.toString());
        return str == null ? this._localeEncodingMap.get(locale.getLanguage()) : str;
    }

    public Resource getResource(String str) throws MalformedURLException {
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        } else if (this._baseResource == null) {
            return null;
        } else {
            try {
                String canonicalPath = URIUtil.canonicalPath(str);
                Resource addPath = this._baseResource.addPath(canonicalPath);
                if (checkAlias(canonicalPath, addPath)) {
                    return addPath;
                }
                return null;
            } catch (Exception e) {
                LOG.ignore(e);
                return null;
            }
        }
    }

    public boolean checkAlias(String str, Resource resource) {
        if (this._aliasesAllowed || resource.getAlias() == null) {
            return true;
        }
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Aliased resource: " + resource + "~=" + resource.getAlias(), new Object[0]);
        }
        Iterator<AliasCheck> it = this._aliasChecks.iterator();
        while (it.hasNext()) {
            AliasCheck next = it.next();
            if (next.check(str, resource)) {
                if (LOG.isDebugEnabled()) {
                    Logger logger2 = LOG;
                    logger2.debug("Aliased resource: " + resource + " approved by " + next, new Object[0]);
                }
                return true;
            }
        }
        return false;
    }

    public Resource newResource(URL url) throws IOException {
        return Resource.newResource(url);
    }

    public Resource newResource(String str) throws IOException {
        return Resource.newResource(str);
    }

    public Set<String> getResourcePaths(String str) {
        try {
            String canonicalPath = URIUtil.canonicalPath(str);
            Resource resource = getResource(canonicalPath);
            if (resource != null && resource.exists()) {
                if (!canonicalPath.endsWith("/")) {
                    canonicalPath = canonicalPath + "/";
                }
                String[] list = resource.list();
                if (list != null) {
                    HashSet hashSet = new HashSet();
                    for (int i = 0; i < list.length; i++) {
                        hashSet.add(canonicalPath + list[i]);
                    }
                    return hashSet;
                }
            }
        } catch (Exception e) {
            LOG.ignore(e);
        }
        return Collections.emptySet();
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }

    public void addAliasCheck(AliasCheck aliasCheck) {
        this._aliasChecks.add(aliasCheck);
    }

    public List<AliasCheck> getAliasChecks() {
        return this._aliasChecks;
    }

    public class Context implements ServletContext {
        private static final String __unimplmented = "Unimplemented - use org.eclipse.jetty.servlet.ServletContextHandler";
        protected boolean _enabled = true;
        protected int _majorVersion = 3;
        protected int _minorVersion = 0;

        public int getMajorVersion() {
            return 3;
        }

        public int getMinorVersion() {
            return 0;
        }

        public RequestDispatcher getNamedDispatcher(String str) {
            return null;
        }

        @Deprecated
        public Servlet getServlet(String str) throws ServletException {
            return null;
        }

        public void setJspConfigDescriptor(JspConfigDescriptor jspConfigDescriptor) {
        }

        protected Context() {
        }

        public ContextHandler getContextHandler() {
            return ContextHandler.this;
        }

        public ServletContext getContext(String str) {
            String str2;
            Context context = this;
            String str3 = str;
            ArrayList arrayList = new ArrayList();
            Handler[] childHandlersByClass = ContextHandler.this.getServer().getChildHandlersByClass(ContextHandler.class);
            int length = childHandlersByClass.length;
            int i = 0;
            String str4 = null;
            while (i < length) {
                Handler handler = childHandlersByClass[i];
                if (handler != null) {
                    ContextHandler contextHandler = (ContextHandler) handler;
                    String contextPath = contextHandler.getContextPath();
                    if (str3.equals(contextPath) || ((str3.startsWith(contextPath) && str3.charAt(contextPath.length()) == '/') || "/".equals(contextPath))) {
                        if (ContextHandler.this.getVirtualHosts() == null || ContextHandler.this.getVirtualHosts().length <= 0) {
                            if (str4 == null || contextPath.length() > str4.length()) {
                                arrayList.clear();
                                str4 = contextPath;
                            }
                            if (str4.equals(contextPath)) {
                                arrayList.add(contextHandler);
                            }
                        } else if (contextHandler.getVirtualHosts() != null && contextHandler.getVirtualHosts().length > 0) {
                            String[] virtualHosts = ContextHandler.this.getVirtualHosts();
                            int length2 = virtualHosts.length;
                            String str5 = str4;
                            int i2 = 0;
                            while (i2 < length2) {
                                String str6 = virtualHosts[i2];
                                String[] virtualHosts2 = contextHandler.getVirtualHosts();
                                int length3 = virtualHosts2.length;
                                String str7 = str5;
                                int i3 = 0;
                                while (i3 < length3) {
                                    if (str6.equals(virtualHosts2[i3])) {
                                        if (str7 == null || contextPath.length() > str7.length()) {
                                            arrayList.clear();
                                            str2 = contextPath;
                                        } else {
                                            str2 = str7;
                                        }
                                        if (str2.equals(contextPath)) {
                                            arrayList.add(contextHandler);
                                        }
                                        str7 = str2;
                                    }
                                    i3++;
                                }
                                i2++;
                                str5 = str7;
                            }
                            str4 = str5;
                        }
                    }
                }
                i++;
                context = this;
            }
            if (arrayList.size() > 0) {
                return ((ContextHandler) arrayList.get(0))._scontext;
            }
            String str8 = null;
            for (Handler handler2 : childHandlersByClass) {
                if (handler2 != null) {
                    ContextHandler contextHandler2 = (ContextHandler) handler2;
                    String contextPath2 = contextHandler2.getContextPath();
                    if (str3.equals(contextPath2) || ((str3.startsWith(contextPath2) && str3.charAt(contextPath2.length()) == '/') || "/".equals(contextPath2))) {
                        if (str8 == null || contextPath2.length() > str8.length()) {
                            arrayList.clear();
                            str8 = contextPath2;
                        }
                        if (str8.equals(contextPath2)) {
                            arrayList.add(contextHandler2);
                        }
                    }
                }
            }
            if (arrayList.size() > 0) {
                return ((ContextHandler) arrayList.get(0))._scontext;
            }
            return null;
        }

        public String getMimeType(String str) {
            Buffer mimeByExtension;
            if (ContextHandler.this._mimeTypes == null || (mimeByExtension = ContextHandler.this._mimeTypes.getMimeByExtension(str)) == null) {
                return null;
            }
            return mimeByExtension.toString();
        }

        public RequestDispatcher getRequestDispatcher(String str) {
            String str2;
            if (str == null || !str.startsWith("/")) {
                return null;
            }
            try {
                int indexOf = str.indexOf(63);
                if (indexOf > 0) {
                    str2 = str.substring(indexOf + 1);
                    str = str.substring(0, indexOf);
                } else {
                    str2 = null;
                }
                String canonicalPath = URIUtil.canonicalPath(URIUtil.decodePath(str));
                if (canonicalPath != null) {
                    return new Dispatcher(ContextHandler.this, URIUtil.addPaths(getContextPath(), str), canonicalPath, str2);
                }
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
            }
            return null;
        }

        public String getRealPath(String str) {
            File file;
            if (str == null) {
                return null;
            }
            if (str.length() == 0) {
                str = "/";
            } else if (str.charAt(0) != '/') {
                str = "/" + str;
            }
            try {
                Resource resource = ContextHandler.this.getResource(str);
                if (!(resource == null || (file = resource.getFile()) == null)) {
                    return file.getCanonicalPath();
                }
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
            }
            return null;
        }

        public URL getResource(String str) throws MalformedURLException {
            Resource resource = ContextHandler.this.getResource(str);
            if (resource == null || !resource.exists()) {
                return null;
            }
            return resource.getURL();
        }

        public InputStream getResourceAsStream(String str) {
            try {
                URL resource = getResource(str);
                if (resource == null) {
                    return null;
                }
                return Resource.newResource(resource).getInputStream();
            } catch (Exception e) {
                ContextHandler.LOG.ignore(e);
                return null;
            }
        }

        public Set getResourcePaths(String str) {
            return ContextHandler.this.getResourcePaths(str);
        }

        public String getServerInfo() {
            return "jetty/" + Server.getVersion();
        }

        @Deprecated
        public Enumeration getServletNames() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        @Deprecated
        public Enumeration getServlets() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        public void log(Exception exc, String str) {
            ContextHandler.this._logger.warn(str, (Throwable) exc);
        }

        public void log(String str) {
            ContextHandler.this._logger.info(str, new Object[0]);
        }

        public void log(String str, Throwable th) {
            ContextHandler.this._logger.warn(str, th);
        }

        public String getInitParameter(String str) {
            return ContextHandler.this.getInitParameter(str);
        }

        public Enumeration getInitParameterNames() {
            return ContextHandler.this.getInitParameterNames();
        }

        public synchronized Object getAttribute(String str) {
            Object attribute;
            attribute = ContextHandler.this.getAttribute(str);
            if (attribute == null && ContextHandler.this._contextAttributes != null) {
                attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            }
            return attribute;
        }

        public synchronized Enumeration getAttributeNames() {
            HashSet hashSet;
            hashSet = new HashSet();
            if (ContextHandler.this._contextAttributes != null) {
                Enumeration<String> attributeNames = ContextHandler.this._contextAttributes.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    hashSet.add(attributeNames.nextElement());
                }
            }
            Enumeration<String> attributeNames2 = ContextHandler.this._attributes.getAttributeNames();
            while (attributeNames2.hasMoreElements()) {
                hashSet.add(attributeNames2.nextElement());
            }
            return Collections.enumeration(hashSet);
        }

        public synchronized void setAttribute(String str, Object obj) {
            ContextHandler.this.checkManagedAttribute(str, obj);
            Object attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            if (obj == null) {
                ContextHandler.this._contextAttributes.removeAttribute(str);
            } else {
                ContextHandler.this._contextAttributes.setAttribute(str, obj);
            }
            if (ContextHandler.this._contextAttributeListeners != null) {
                ServletContextAttributeEvent servletContextAttributeEvent = new ServletContextAttributeEvent(ContextHandler.this._scontext, str, attribute == null ? obj : attribute);
                for (int i = 0; i < LazyList.size(ContextHandler.this._contextAttributeListeners); i++) {
                    ServletContextAttributeListener servletContextAttributeListener = (ServletContextAttributeListener) LazyList.get(ContextHandler.this._contextAttributeListeners, i);
                    if (attribute == null) {
                        servletContextAttributeListener.attributeAdded(servletContextAttributeEvent);
                    } else if (obj == null) {
                        servletContextAttributeListener.attributeRemoved(servletContextAttributeEvent);
                    } else {
                        servletContextAttributeListener.attributeReplaced(servletContextAttributeEvent);
                    }
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0060, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized void removeAttribute(java.lang.String r4) {
            /*
                r3 = this;
                monitor-enter(r3)
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                r1 = 0
                r0.checkManagedAttribute(r4, r1)     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.util.AttributesMap r0 = r0._contextAttributes     // Catch:{ all -> 0x0061 }
                if (r0 != 0) goto L_0x001a
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.util.AttributesMap r0 = r0._attributes     // Catch:{ all -> 0x0061 }
                r0.removeAttribute(r4)     // Catch:{ all -> 0x0061 }
                monitor-exit(r3)
                return
            L_0x001a:
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.util.AttributesMap r0 = r0._contextAttributes     // Catch:{ all -> 0x0061 }
                java.lang.Object r0 = r0.getAttribute(r4)     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.server.handler.ContextHandler r1 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.util.AttributesMap r1 = r1._contextAttributes     // Catch:{ all -> 0x0061 }
                r1.removeAttribute(r4)     // Catch:{ all -> 0x0061 }
                if (r0 == 0) goto L_0x005f
                org.eclipse.jetty.server.handler.ContextHandler r1 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                java.lang.Object r1 = r1._contextAttributeListeners     // Catch:{ all -> 0x0061 }
                if (r1 == 0) goto L_0x005f
                javax.servlet.ServletContextAttributeEvent r1 = new javax.servlet.ServletContextAttributeEvent     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.server.handler.ContextHandler r2 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                org.eclipse.jetty.server.handler.ContextHandler$Context r2 = r2._scontext     // Catch:{ all -> 0x0061 }
                r1.<init>(r2, r4, r0)     // Catch:{ all -> 0x0061 }
                r4 = 0
            L_0x0041:
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                java.lang.Object r0 = r0._contextAttributeListeners     // Catch:{ all -> 0x0061 }
                int r0 = org.eclipse.jetty.util.LazyList.size(r0)     // Catch:{ all -> 0x0061 }
                if (r4 >= r0) goto L_0x005f
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ all -> 0x0061 }
                java.lang.Object r0 = r0._contextAttributeListeners     // Catch:{ all -> 0x0061 }
                java.lang.Object r0 = org.eclipse.jetty.util.LazyList.get(r0, r4)     // Catch:{ all -> 0x0061 }
                javax.servlet.ServletContextAttributeListener r0 = (javax.servlet.ServletContextAttributeListener) r0     // Catch:{ all -> 0x0061 }
                r0.attributeRemoved(r1)     // Catch:{ all -> 0x0061 }
                int r4 = r4 + 1
                goto L_0x0041
            L_0x005f:
                monitor-exit(r3)
                return
            L_0x0061:
                r4 = move-exception
                monitor-exit(r3)
                goto L_0x0065
            L_0x0064:
                throw r4
            L_0x0065:
                goto L_0x0064
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.Context.removeAttribute(java.lang.String):void");
        }

        public String getServletContextName() {
            String displayName = ContextHandler.this.getDisplayName();
            return displayName == null ? ContextHandler.this.getContextPath() : displayName;
        }

        public String getContextPath() {
            if (ContextHandler.this._contextPath == null || !ContextHandler.this._contextPath.equals("/")) {
                return ContextHandler.this._contextPath;
            }
            return "";
        }

        public String toString() {
            return "ServletContext@" + ContextHandler.this.toString();
        }

        public boolean setInitParameter(String str, String str2) {
            if (ContextHandler.this.getInitParameter(str) != null) {
                return false;
            }
            ContextHandler.this.getInitParams().put(str, str2);
            return true;
        }

        public FilterRegistration.Dynamic addFilter(String str, Class<? extends Filter> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public FilterRegistration.Dynamic addFilter(String str, Filter filter) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public FilterRegistration.Dynamic addFilter(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String str, Class<? extends Servlet> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String str, Servlet servlet) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public <T extends Filter> T createFilter(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public <T extends Servlet> T createServlet(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public FilterRegistration getFilterRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration getServletRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public SessionCookieConfig getSessionCookieConfig() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
        }

        public void addListener(String str) {
            if (this._enabled) {
                try {
                    addListener((Class<? extends EventListener>) ContextHandler.this._classLoader == null ? Loader.loadClass(ContextHandler.class, str) : ContextHandler.this._classLoader.loadClass(str));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public <T extends EventListener> void addListener(T t) {
            if (this._enabled) {
                ContextHandler.this.addEventListener(t);
                ContextHandler.this.restrictEventListener(t);
                return;
            }
            throw new UnsupportedOperationException();
        }

        /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Class<? extends java.util.EventListener>, java.lang.Class] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void addListener(java.lang.Class<? extends java.util.EventListener> r2) {
            /*
                r1 = this;
                boolean r0 = r1._enabled
                if (r0 == 0) goto L_0x001a
                java.util.EventListener r2 = r1.createListener(r2)     // Catch:{ ServletException -> 0x0013 }
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ ServletException -> 0x0013 }
                r0.addEventListener(r2)     // Catch:{ ServletException -> 0x0013 }
                org.eclipse.jetty.server.handler.ContextHandler r0 = org.eclipse.jetty.server.handler.ContextHandler.this     // Catch:{ ServletException -> 0x0013 }
                r0.restrictEventListener(r2)     // Catch:{ ServletException -> 0x0013 }
                return
            L_0x0013:
                r2 = move-exception
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                r0.<init>(r2)
                throw r0
            L_0x001a:
                java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException
                r2.<init>()
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.Context.addListener(java.lang.Class):void");
        }

        public <T extends EventListener> T createListener(Class<T> cls) throws ServletException {
            try {
                return (EventListener) cls.newInstance();
            } catch (InstantiationException e) {
                throw new ServletException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new ServletException((Throwable) e2);
            }
        }

        public ClassLoader getClassLoader() {
            AccessController.checkPermission(new RuntimePermission("getClassLoader"));
            return ContextHandler.this._classLoader;
        }

        public int getEffectiveMajorVersion() {
            return this._majorVersion;
        }

        public int getEffectiveMinorVersion() {
            return this._minorVersion;
        }

        public void setEffectiveMajorVersion(int i) {
            this._majorVersion = i;
        }

        public void setEffectiveMinorVersion(int i) {
            this._minorVersion = i;
        }

        public JspConfigDescriptor getJspConfigDescriptor() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public void declareRoles(String... strArr) {
            if (!ContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
        }

        public void setEnabled(boolean z) {
            this._enabled = z;
        }

        public boolean isEnabled() {
            return this._enabled;
        }
    }

    private static class CLDump implements Dumpable {
        final ClassLoader _loader;

        CLDump(ClassLoader classLoader) {
            this._loader = classLoader;
        }

        public String dump() {
            return AggregateLifeCycle.dump((Dumpable) this);
        }

        public void dump(Appendable appendable, String str) throws IOException {
            Object parent;
            appendable.append(String.valueOf(this._loader)).append("\n");
            ClassLoader classLoader = this._loader;
            if (classLoader != null && (parent = classLoader.getParent()) != null) {
                if (!(parent instanceof Dumpable)) {
                    parent = new CLDump((ClassLoader) parent);
                }
                ClassLoader classLoader2 = this._loader;
                if (classLoader2 instanceof URLClassLoader) {
                    AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(((URLClassLoader) classLoader2).getURLs()), Collections.singleton(parent));
                    return;
                }
                AggregateLifeCycle.dump(appendable, str, Collections.singleton(parent));
            }
        }
    }

    @Deprecated
    public static class ApproveSameSuffixAliases implements AliasCheck {
        public ApproveSameSuffixAliases() {
            ContextHandler.LOG.warn("ApproveSameSuffixAlias is not safe for production", new Object[0]);
        }

        public boolean check(String str, Resource resource) {
            int lastIndexOf = str.lastIndexOf(46);
            if (lastIndexOf < 0) {
                return false;
            }
            return resource.toString().endsWith(str.substring(lastIndexOf));
        }
    }

    @Deprecated
    public static class ApprovePathPrefixAliases implements AliasCheck {
        public ApprovePathPrefixAliases() {
            ContextHandler.LOG.warn("ApprovePathPrefixAliases is not safe for production", new Object[0]);
        }

        public boolean check(String str, Resource resource) {
            int lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf < 0 || lastIndexOf == str.length() - 1) {
                return false;
            }
            return resource.toString().endsWith(str.substring(lastIndexOf));
        }
    }

    public static class ApproveNonExistentDirectoryAliases implements AliasCheck {
        public boolean check(String str, Resource resource) {
            if (resource.exists()) {
                return false;
            }
            String url = resource.getAlias().toString();
            String url2 = resource.getURL().toString();
            if (url.length() > url2.length()) {
                if (!url.startsWith(url2) || url.length() != url2.length() + 1 || !url.endsWith("/")) {
                    return false;
                }
                return true;
            } else if (!url2.startsWith(url) || url2.length() != url.length() + 1 || !url2.endsWith("/")) {
                return false;
            } else {
                return true;
            }
        }
    }
}
