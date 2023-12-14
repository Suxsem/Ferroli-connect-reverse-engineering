package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Constraint;

public class ServletHandler extends ScopedHandler {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) ServletHandler.class);
    public static final String __DEFAULT_SERVLET = "default";
    protected final ConcurrentMap<String, FilterChain>[] _chainCache = new ConcurrentMap[31];
    protected final Queue<String>[] _chainLRU = new Queue[31];
    private ServletContextHandler _contextHandler;
    private boolean _filterChainsCached = true;
    private FilterMapping[] _filterMappings;
    private final Map<String, FilterHolder> _filterNameMap = new HashMap();
    private MultiMap<String> _filterNameMappings;
    private List<FilterMapping> _filterPathMappings;
    private FilterHolder[] _filters = new FilterHolder[0];
    private IdentityService _identityService;
    private int _matchAfterIndex = -1;
    private int _matchBeforeIndex = -1;
    private int _maxFilterChainsCacheSize = 512;
    private ContextHandler.Context _servletContext;
    private ServletMapping[] _servletMappings;
    private final Map<String, ServletHolder> _servletNameMap = new HashMap();
    private PathMap _servletPathMap;
    private ServletHolder[] _servlets = new ServletHolder[0];
    private boolean _startWithUnavailable = false;

    public Object getContextLog() {
        return null;
    }

    public void setServer(Server server) {
        Server server2 = getServer();
        if (!(server2 == null || server2 == server)) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) null, "filter", true);
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) null, "filterMapping", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) null, "servlet", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) null, "servletMapping", true);
        }
        super.setServer(server);
        if (server != null && server2 != server) {
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filters, "filter", true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filterMappings, "filterMapping", true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servlets, "servlet", true);
            server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servletMappings, "servletMapping", true);
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void doStart() throws Exception {
        SecurityHandler securityHandler;
        this._servletContext = ContextHandler.getCurrentContext();
        this._contextHandler = (ServletContextHandler) (this._servletContext == null ? null : this._servletContext.getContextHandler());
        if (!(this._contextHandler == null || (securityHandler = (SecurityHandler) this._contextHandler.getChildHandlerByClass(SecurityHandler.class)) == null)) {
            this._identityService = securityHandler.getIdentityService();
        }
        updateNameMappings();
        updateMappings();
        if (this._filterChainsCached) {
            this._chainCache[1] = new ConcurrentHashMap();
            this._chainCache[2] = new ConcurrentHashMap();
            this._chainCache[4] = new ConcurrentHashMap();
            this._chainCache[8] = new ConcurrentHashMap();
            this._chainCache[16] = new ConcurrentHashMap();
            this._chainLRU[1] = new ConcurrentLinkedQueue();
            this._chainLRU[2] = new ConcurrentLinkedQueue();
            this._chainLRU[4] = new ConcurrentLinkedQueue();
            this._chainLRU[8] = new ConcurrentLinkedQueue();
            this._chainLRU[16] = new ConcurrentLinkedQueue();
        }
        super.doStart();
        if (this._contextHandler == null || !(this._contextHandler instanceof ServletContextHandler)) {
            initialize();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00af A[Catch:{ Exception -> 0x0022 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void doStop() throws java.lang.Exception {
        /*
            r6 = this;
            monitor-enter(r6)
            super.doStop()     // Catch:{ all -> 0x012f }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x012f }
            r0.<init>()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterMapping[] r1 = r6._filterMappings     // Catch:{ all -> 0x012f }
            java.util.List r1 = org.eclipse.jetty.util.LazyList.array2List(r1)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterHolder[] r2 = r6._filters     // Catch:{ all -> 0x012f }
            if (r2 == 0) goto L_0x0072
            org.eclipse.jetty.servlet.FilterHolder[] r2 = r6._filters     // Catch:{ all -> 0x012f }
            int r2 = r2.length     // Catch:{ all -> 0x012f }
        L_0x0016:
            int r3 = r2 + -1
            if (r2 <= 0) goto L_0x0072
            org.eclipse.jetty.servlet.FilterHolder[] r2 = r6._filters     // Catch:{ Exception -> 0x0022 }
            r2 = r2[r3]     // Catch:{ Exception -> 0x0022 }
            r2.stop()     // Catch:{ Exception -> 0x0022 }
            goto L_0x002a
        L_0x0022:
            r2 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x012f }
            java.lang.String r5 = "EXCEPTION "
            r4.warn((java.lang.String) r5, (java.lang.Throwable) r2)     // Catch:{ all -> 0x012f }
        L_0x002a:
            org.eclipse.jetty.servlet.FilterHolder[] r2 = r6._filters     // Catch:{ all -> 0x012f }
            r2 = r2[r3]     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.Holder$Source r2 = r2.getSource()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.Holder$Source r4 = org.eclipse.jetty.servlet.Holder.Source.EMBEDDED     // Catch:{ all -> 0x012f }
            if (r2 == r4) goto L_0x0069
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.FilterHolder> r2 = r6._filterNameMap     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterHolder[] r4 = r6._filters     // Catch:{ all -> 0x012f }
            r4 = r4[r3]     // Catch:{ all -> 0x012f }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x012f }
            r2.remove(r4)     // Catch:{ all -> 0x012f }
            java.util.ListIterator r2 = r1.listIterator()     // Catch:{ all -> 0x012f }
        L_0x0047:
            boolean r4 = r2.hasNext()     // Catch:{ all -> 0x012f }
            if (r4 == 0) goto L_0x0070
            java.lang.Object r4 = r2.next()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterMapping r4 = (org.eclipse.jetty.servlet.FilterMapping) r4     // Catch:{ all -> 0x012f }
            java.lang.String r4 = r4.getFilterName()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterHolder[] r5 = r6._filters     // Catch:{ all -> 0x012f }
            r5 = r5[r3]     // Catch:{ all -> 0x012f }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x012f }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x012f }
            if (r4 == 0) goto L_0x0047
            r2.remove()     // Catch:{ all -> 0x012f }
            goto L_0x0047
        L_0x0069:
            org.eclipse.jetty.servlet.FilterHolder[] r2 = r6._filters     // Catch:{ all -> 0x012f }
            r2 = r2[r3]     // Catch:{ all -> 0x012f }
            r0.add(r2)     // Catch:{ all -> 0x012f }
        L_0x0070:
            r2 = r3
            goto L_0x0016
        L_0x0072:
            java.lang.Class<org.eclipse.jetty.servlet.FilterHolder> r2 = org.eclipse.jetty.servlet.FilterHolder.class
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.toArray(r0, r2)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterHolder[] r0 = (org.eclipse.jetty.servlet.FilterHolder[]) r0     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterHolder[] r0 = (org.eclipse.jetty.servlet.FilterHolder[]) r0     // Catch:{ all -> 0x012f }
            r6._filters = r0     // Catch:{ all -> 0x012f }
            java.lang.Class<org.eclipse.jetty.servlet.FilterMapping> r0 = org.eclipse.jetty.servlet.FilterMapping.class
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.toArray(r1, r0)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterMapping[] r0 = (org.eclipse.jetty.servlet.FilterMapping[]) r0     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterMapping[] r0 = (org.eclipse.jetty.servlet.FilterMapping[]) r0     // Catch:{ all -> 0x012f }
            r6._filterMappings = r0     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.FilterMapping[] r0 = r6._filterMappings     // Catch:{ all -> 0x012f }
            r1 = -1
            if (r0 == 0) goto L_0x009b
            org.eclipse.jetty.servlet.FilterMapping[] r0 = r6._filterMappings     // Catch:{ all -> 0x012f }
            int r0 = r0.length     // Catch:{ all -> 0x012f }
            if (r0 != 0) goto L_0x0095
            goto L_0x009b
        L_0x0095:
            org.eclipse.jetty.servlet.FilterMapping[] r0 = r6._filterMappings     // Catch:{ all -> 0x012f }
            int r0 = r0.length     // Catch:{ all -> 0x012f }
            int r0 = r0 + -1
            goto L_0x009c
        L_0x009b:
            r0 = -1
        L_0x009c:
            r6._matchAfterIndex = r0     // Catch:{ all -> 0x012f }
            r6._matchBeforeIndex = r1     // Catch:{ all -> 0x012f }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x012f }
            r0.<init>()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletMapping[] r1 = r6._servletMappings     // Catch:{ all -> 0x012f }
            java.util.List r1 = org.eclipse.jetty.util.LazyList.array2List(r1)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletHolder[] r2 = r6._servlets     // Catch:{ all -> 0x012f }
            if (r2 == 0) goto L_0x010e
            org.eclipse.jetty.servlet.ServletHolder[] r2 = r6._servlets     // Catch:{ all -> 0x012f }
            int r2 = r2.length     // Catch:{ all -> 0x012f }
        L_0x00b2:
            int r3 = r2 + -1
            if (r2 <= 0) goto L_0x010e
            org.eclipse.jetty.servlet.ServletHolder[] r2 = r6._servlets     // Catch:{ Exception -> 0x00be }
            r2 = r2[r3]     // Catch:{ Exception -> 0x00be }
            r2.stop()     // Catch:{ Exception -> 0x00be }
            goto L_0x00c6
        L_0x00be:
            r2 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x012f }
            java.lang.String r5 = "EXCEPTION "
            r4.warn((java.lang.String) r5, (java.lang.Throwable) r2)     // Catch:{ all -> 0x012f }
        L_0x00c6:
            org.eclipse.jetty.servlet.ServletHolder[] r2 = r6._servlets     // Catch:{ all -> 0x012f }
            r2 = r2[r3]     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.Holder$Source r2 = r2.getSource()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.Holder$Source r4 = org.eclipse.jetty.servlet.Holder.Source.EMBEDDED     // Catch:{ all -> 0x012f }
            if (r2 == r4) goto L_0x0105
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.ServletHolder> r2 = r6._servletNameMap     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletHolder[] r4 = r6._servlets     // Catch:{ all -> 0x012f }
            r4 = r4[r3]     // Catch:{ all -> 0x012f }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x012f }
            r2.remove(r4)     // Catch:{ all -> 0x012f }
            java.util.ListIterator r2 = r1.listIterator()     // Catch:{ all -> 0x012f }
        L_0x00e3:
            boolean r4 = r2.hasNext()     // Catch:{ all -> 0x012f }
            if (r4 == 0) goto L_0x010c
            java.lang.Object r4 = r2.next()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletMapping r4 = (org.eclipse.jetty.servlet.ServletMapping) r4     // Catch:{ all -> 0x012f }
            java.lang.String r4 = r4.getServletName()     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletHolder[] r5 = r6._servlets     // Catch:{ all -> 0x012f }
            r5 = r5[r3]     // Catch:{ all -> 0x012f }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x012f }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x012f }
            if (r4 == 0) goto L_0x00e3
            r2.remove()     // Catch:{ all -> 0x012f }
            goto L_0x00e3
        L_0x0105:
            org.eclipse.jetty.servlet.ServletHolder[] r2 = r6._servlets     // Catch:{ all -> 0x012f }
            r2 = r2[r3]     // Catch:{ all -> 0x012f }
            r0.add(r2)     // Catch:{ all -> 0x012f }
        L_0x010c:
            r2 = r3
            goto L_0x00b2
        L_0x010e:
            java.lang.Class<org.eclipse.jetty.servlet.ServletHolder> r2 = org.eclipse.jetty.servlet.ServletHolder.class
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.toArray(r0, r2)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletHolder[] r0 = (org.eclipse.jetty.servlet.ServletHolder[]) r0     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletHolder[] r0 = (org.eclipse.jetty.servlet.ServletHolder[]) r0     // Catch:{ all -> 0x012f }
            r6._servlets = r0     // Catch:{ all -> 0x012f }
            java.lang.Class<org.eclipse.jetty.servlet.ServletMapping> r0 = org.eclipse.jetty.servlet.ServletMapping.class
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.toArray(r1, r0)     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletMapping[] r0 = (org.eclipse.jetty.servlet.ServletMapping[]) r0     // Catch:{ all -> 0x012f }
            org.eclipse.jetty.servlet.ServletMapping[] r0 = (org.eclipse.jetty.servlet.ServletMapping[]) r0     // Catch:{ all -> 0x012f }
            r6._servletMappings = r0     // Catch:{ all -> 0x012f }
            r0 = 0
            r6._filterPathMappings = r0     // Catch:{ all -> 0x012f }
            r6._filterNameMappings = r0     // Catch:{ all -> 0x012f }
            r6._servletPathMap = r0     // Catch:{ all -> 0x012f }
            monitor-exit(r6)
            return
        L_0x012f:
            r0 = move-exception
            monitor-exit(r6)
            goto L_0x0133
        L_0x0132:
            throw r0
        L_0x0133:
            goto L_0x0132
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHandler.doStop():void");
    }

    /* access modifiers changed from: protected */
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public FilterMapping[] getFilterMappings() {
        return this._filterMappings;
    }

    public FilterHolder[] getFilters() {
        return this._filters;
    }

    public PathMap.Entry getHolderEntry(String str) {
        PathMap pathMap = this._servletPathMap;
        if (pathMap == null) {
            return null;
        }
        return pathMap.getMatch(str);
    }

    public ServletContext getServletContext() {
        return this._servletContext;
    }

    public ServletMapping[] getServletMappings() {
        return this._servletMappings;
    }

    public ServletMapping getServletMapping(String str) {
        ServletMapping[] servletMappingArr = this._servletMappings;
        if (servletMappingArr == null) {
            return null;
        }
        ServletMapping servletMapping = null;
        for (ServletMapping servletMapping2 : servletMappingArr) {
            String[] pathSpecs = servletMapping2.getPathSpecs();
            if (pathSpecs != null) {
                ServletMapping servletMapping3 = servletMapping;
                for (String equals : pathSpecs) {
                    if (str.equals(equals)) {
                        servletMapping3 = servletMapping2;
                    }
                }
                servletMapping = servletMapping3;
            }
        }
        return servletMapping;
    }

    public ServletHolder[] getServlets() {
        return this._servlets;
    }

    public ServletHolder getServlet(String str) {
        return this._servletNameMap.get(str);
    }

    public void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ServletHolder servletHolder;
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        DispatcherType dispatcherType = request.getDispatcherType();
        ServletHolder servletHolder2 = null;
        if (str.startsWith("/")) {
            PathMap.Entry holderEntry = getHolderEntry(str);
            if (holderEntry != null) {
                servletHolder = (ServletHolder) holderEntry.getValue();
                String str2 = (String) holderEntry.getKey();
                String mapped = holderEntry.getMapped() != null ? holderEntry.getMapped() : PathMap.pathMatch(str2, str);
                String pathInfo2 = PathMap.pathInfo(str2, str);
                if (DispatcherType.INCLUDE.equals(dispatcherType)) {
                    request.setAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH, mapped);
                    request.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO, pathInfo2);
                } else {
                    request.setServletPath(mapped);
                    request.setPathInfo(pathInfo2);
                }
            } else {
                servletHolder = servletHolder2;
            }
        } else {
            servletHolder = this._servletNameMap.get(str);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("servlet {}|{}|{} -> {}", request.getContextPath(), request.getServletPath(), request.getPathInfo(), servletHolder);
        }
        try {
            servletHolder2 = request.getUserIdentityScope();
            request.setUserIdentityScope(servletHolder);
            if (never()) {
                nextScope(str, request, httpServletRequest, httpServletResponse);
            } else if (this._nextScope != null) {
                this._nextScope.doScope(str, request, httpServletRequest, httpServletResponse);
            } else if (this._outerScope != null) {
                this._outerScope.doHandle(str, request, httpServletRequest, httpServletResponse);
            } else {
                doHandle(str, request, httpServletRequest, httpServletResponse);
            }
        } finally {
            if (servletHolder2 != null) {
                request.setUserIdentityScope(servletHolder2);
            }
            if (!DispatcherType.INCLUDE.equals(dispatcherType)) {
                request.setServletPath(servletPath);
                request.setPathInfo(pathInfo);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:144:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00e6, code lost:
        ((org.eclipse.jetty.server.AsyncContinuation) r14.getAsyncContext()).errorComplete();
     */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01d8 A[Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082, all -> 0x007f, all -> 0x01dc }] */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:141:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0047 A[SYNTHETIC, Splitter:B:17:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0055 A[Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082, all -> 0x007f, all -> 0x01dc }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x013c A[Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082, all -> 0x007f, all -> 0x01dc }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:44:0x0095=Splitter:B:44:0x0095, B:76:0x0118=Splitter:B:76:0x0118} */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:44:0x0095=Splitter:B:44:0x0095, B:86:0x0138=Splitter:B:86:0x0138} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doHandle(java.lang.String r12, org.eclipse.jetty.server.Request r13, javax.servlet.http.HttpServletRequest r14, javax.servlet.http.HttpServletResponse r15) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r11 = this;
            javax.servlet.DispatcherType r0 = r13.getDispatcherType()
            org.eclipse.jetty.server.UserIdentity$Scope r1 = r13.getUserIdentityScope()
            org.eclipse.jetty.servlet.ServletHolder r1 = (org.eclipse.jetty.servlet.ServletHolder) r1
            java.lang.String r2 = "/"
            boolean r2 = r12.startsWith(r2)
            r3 = 0
            if (r2 == 0) goto L_0x0021
            if (r1 == 0) goto L_0x002f
            org.eclipse.jetty.servlet.FilterMapping[] r2 = r11._filterMappings
            if (r2 == 0) goto L_0x002f
            int r2 = r2.length
            if (r2 <= 0) goto L_0x002f
            javax.servlet.FilterChain r2 = r11.getFilterChain(r13, r12, r1)
            goto L_0x0030
        L_0x0021:
            if (r1 == 0) goto L_0x002f
            org.eclipse.jetty.servlet.FilterMapping[] r2 = r11._filterMappings
            if (r2 == 0) goto L_0x002f
            int r2 = r2.length
            if (r2 <= 0) goto L_0x002f
            javax.servlet.FilterChain r2 = r11.getFilterChain(r13, r3, r1)
            goto L_0x0030
        L_0x002f:
            r2 = r3
        L_0x0030:
            org.eclipse.jetty.util.log.Logger r4 = LOG
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r7 = 0
            r6[r7] = r2
            java.lang.String r8 = "chain={}"
            r4.debug((java.lang.String) r8, (java.lang.Object[]) r6)
            java.lang.String r4 = "Response already committed for handling "
            r6 = 500(0x1f4, float:7.0E-43)
            java.lang.String r8 = "javax.servlet.error.exception"
            java.lang.String r9 = "javax.servlet.error.exception_type"
            if (r1 != 0) goto L_0x0055
            org.eclipse.jetty.server.Handler r2 = r11.getHandler()     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            if (r2 != 0) goto L_0x0051
            r11.notFound(r14, r15)     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            goto L_0x0078
        L_0x0051:
            r11.nextHandle(r12, r13, r14, r15)     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            goto L_0x0078
        L_0x0055:
            boolean r12 = r14 instanceof org.eclipse.jetty.server.ServletRequestHttpWrapper     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            if (r12 == 0) goto L_0x0061
            r12 = r14
            org.eclipse.jetty.server.ServletRequestHttpWrapper r12 = (org.eclipse.jetty.server.ServletRequestHttpWrapper) r12     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            javax.servlet.ServletRequest r12 = r12.getRequest()     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            goto L_0x0062
        L_0x0061:
            r12 = r14
        L_0x0062:
            boolean r10 = r15 instanceof org.eclipse.jetty.server.ServletResponseHttpWrapper     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            if (r10 == 0) goto L_0x006e
            r10 = r15
            org.eclipse.jetty.server.ServletResponseHttpWrapper r10 = (org.eclipse.jetty.server.ServletResponseHttpWrapper) r10     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            javax.servlet.ServletResponse r10 = r10.getResponse()     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            goto L_0x006f
        L_0x006e:
            r10 = r15
        L_0x006f:
            if (r2 == 0) goto L_0x0075
            r2.doFilter(r12, r10)     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
            goto L_0x0078
        L_0x0075:
            r1.handle(r13, r12, r10)     // Catch:{ EofException -> 0x01e4, RuntimeIOException -> 0x01e2, ContinuationThrowable -> 0x01e0, Exception -> 0x00f1, Error -> 0x0082 }
        L_0x0078:
            if (r1 == 0) goto L_0x01cf
            r13.setHandled(r5)
            goto L_0x01cf
        L_0x007f:
            r12 = move-exception
            goto L_0x01e6
        L_0x0082:
            r12 = move-exception
            javax.servlet.DispatcherType r2 = javax.servlet.DispatcherType.REQUEST     // Catch:{ all -> 0x007f }
            boolean r2 = r2.equals(r0)     // Catch:{ all -> 0x007f }
            if (r2 != 0) goto L_0x0095
            javax.servlet.DispatcherType r2 = javax.servlet.DispatcherType.ASYNC     // Catch:{ all -> 0x007f }
            boolean r0 = r2.equals(r0)     // Catch:{ all -> 0x007f }
            if (r0 == 0) goto L_0x0094
            goto L_0x0095
        L_0x0094:
            throw r12     // Catch:{ all -> 0x007f }
        L_0x0095:
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01dc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dc }
            r2.<init>()     // Catch:{ all -> 0x01dc }
            java.lang.String r3 = "Error for "
            r2.append(r3)     // Catch:{ all -> 0x01dc }
            java.lang.String r3 = r14.getRequestURI()     // Catch:{ all -> 0x01dc }
            r2.append(r3)     // Catch:{ all -> 0x01dc }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01dc }
            r0.warn((java.lang.String) r2, (java.lang.Throwable) r12)     // Catch:{ all -> 0x01dc }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01dc }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x01dc }
            if (r0 == 0) goto L_0x00c2
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01dc }
            java.lang.String r2 = r14.toString()     // Catch:{ all -> 0x01dc }
            java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch:{ all -> 0x01dc }
            r0.debug((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x01dc }
        L_0x00c2:
            java.lang.Class r0 = r12.getClass()     // Catch:{ all -> 0x01dc }
            r14.setAttribute(r9, r0)     // Catch:{ all -> 0x01dc }
            r14.setAttribute(r8, r12)     // Catch:{ all -> 0x01dc }
            boolean r0 = r15.isCommitted()     // Catch:{ all -> 0x01dc }
            if (r0 != 0) goto L_0x00d6
            r15.sendError(r6)     // Catch:{ all -> 0x01dc }
            goto L_0x00db
        L_0x00d6:
            org.eclipse.jetty.util.log.Logger r15 = LOG     // Catch:{ all -> 0x01dc }
            r15.debug((java.lang.String) r4, (java.lang.Throwable) r12)     // Catch:{ all -> 0x01dc }
        L_0x00db:
            if (r1 == 0) goto L_0x00e0
            r13.setHandled(r5)
        L_0x00e0:
            boolean r12 = r14.isAsyncStarted()
            if (r12 == 0) goto L_0x01cf
        L_0x00e6:
            javax.servlet.AsyncContext r12 = r14.getAsyncContext()
            org.eclipse.jetty.server.AsyncContinuation r12 = (org.eclipse.jetty.server.AsyncContinuation) r12
            r12.errorComplete()
            goto L_0x01cf
        L_0x00f1:
            r12 = move-exception
            javax.servlet.DispatcherType r2 = javax.servlet.DispatcherType.REQUEST     // Catch:{ all -> 0x007f }
            boolean r2 = r2.equals(r0)     // Catch:{ all -> 0x007f }
            if (r2 != 0) goto L_0x0118
            javax.servlet.DispatcherType r2 = javax.servlet.DispatcherType.ASYNC     // Catch:{ all -> 0x007f }
            boolean r0 = r2.equals(r0)     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x0118
            boolean r0 = r12 instanceof java.io.IOException     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x0115
            boolean r0 = r12 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x0112
            boolean r0 = r12 instanceof javax.servlet.ServletException     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x010f
            goto L_0x0118
        L_0x010f:
            javax.servlet.ServletException r12 = (javax.servlet.ServletException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x0112:
            java.lang.RuntimeException r12 = (java.lang.RuntimeException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x0115:
            java.io.IOException r12 = (java.io.IOException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x0118:
            boolean r0 = r12 instanceof javax.servlet.UnavailableException     // Catch:{ all -> 0x01dc }
            if (r0 == 0) goto L_0x0122
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01dc }
            r0.debug(r12)     // Catch:{ all -> 0x01dc }
            goto L_0x0136
        L_0x0122:
            boolean r0 = r12 instanceof javax.servlet.ServletException     // Catch:{ all -> 0x01dc }
            if (r0 == 0) goto L_0x0136
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01dc }
            r0.warn(r12)     // Catch:{ all -> 0x01dc }
            r0 = r12
            javax.servlet.ServletException r0 = (javax.servlet.ServletException) r0     // Catch:{ all -> 0x01dc }
            java.lang.Throwable r0 = r0.getRootCause()     // Catch:{ all -> 0x01dc }
            if (r0 == 0) goto L_0x0136
            r3 = r0
            goto L_0x0137
        L_0x0136:
            r3 = r12
        L_0x0137:
            boolean r12 = r3 instanceof org.eclipse.jetty.http.HttpException     // Catch:{ all -> 0x007f }
            if (r12 != 0) goto L_0x01d8
            boolean r12 = r3 instanceof org.eclipse.jetty.p119io.RuntimeIOException     // Catch:{ all -> 0x007f }
            if (r12 != 0) goto L_0x01d4
            boolean r12 = r3 instanceof org.eclipse.jetty.p119io.EofException     // Catch:{ all -> 0x007f }
            if (r12 != 0) goto L_0x01d0
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            boolean r12 = r12.isDebugEnabled()     // Catch:{ all -> 0x007f }
            if (r12 == 0) goto L_0x0161
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            java.lang.String r0 = r14.getRequestURI()     // Catch:{ all -> 0x007f }
            r12.warn((java.lang.String) r0, (java.lang.Throwable) r3)     // Catch:{ all -> 0x007f }
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            java.lang.String r0 = r14.toString()     // Catch:{ all -> 0x007f }
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x007f }
            r12.debug((java.lang.String) r0, (java.lang.Object[]) r2)     // Catch:{ all -> 0x007f }
            goto L_0x017d
        L_0x0161:
            boolean r12 = r3 instanceof java.io.IOException     // Catch:{ all -> 0x007f }
            if (r12 != 0) goto L_0x0174
            boolean r12 = r3 instanceof javax.servlet.UnavailableException     // Catch:{ all -> 0x007f }
            if (r12 == 0) goto L_0x016a
            goto L_0x0174
        L_0x016a:
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            java.lang.String r0 = r14.getRequestURI()     // Catch:{ all -> 0x007f }
            r12.warn((java.lang.String) r0, (java.lang.Throwable) r3)     // Catch:{ all -> 0x007f }
            goto L_0x017d
        L_0x0174:
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            java.lang.String r0 = r14.getRequestURI()     // Catch:{ all -> 0x007f }
            r12.debug((java.lang.String) r0, (java.lang.Throwable) r3)     // Catch:{ all -> 0x007f }
        L_0x017d:
            java.lang.Class r12 = r3.getClass()     // Catch:{ all -> 0x007f }
            r14.setAttribute(r9, r12)     // Catch:{ all -> 0x007f }
            r14.setAttribute(r8, r3)     // Catch:{ all -> 0x007f }
            boolean r12 = r15.isCommitted()     // Catch:{ all -> 0x007f }
            if (r12 != 0) goto L_0x01aa
            boolean r12 = r3 instanceof javax.servlet.UnavailableException     // Catch:{ all -> 0x007f }
            if (r12 == 0) goto L_0x01a6
            r12 = r3
            javax.servlet.UnavailableException r12 = (javax.servlet.UnavailableException) r12     // Catch:{ all -> 0x007f }
            boolean r12 = r12.isPermanent()     // Catch:{ all -> 0x007f }
            if (r12 == 0) goto L_0x01a0
            r12 = 404(0x194, float:5.66E-43)
            r15.sendError(r12)     // Catch:{ all -> 0x007f }
            goto L_0x01c0
        L_0x01a0:
            r12 = 503(0x1f7, float:7.05E-43)
            r15.sendError(r12)     // Catch:{ all -> 0x007f }
            goto L_0x01c0
        L_0x01a6:
            r15.sendError(r6)     // Catch:{ all -> 0x007f }
            goto L_0x01c0
        L_0x01aa:
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ all -> 0x007f }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x007f }
            r15.<init>()     // Catch:{ all -> 0x007f }
            r15.append(r4)     // Catch:{ all -> 0x007f }
            r15.append(r3)     // Catch:{ all -> 0x007f }
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x007f }
            java.lang.Object[] r0 = new java.lang.Object[r7]     // Catch:{ all -> 0x007f }
            r12.debug((java.lang.String) r15, (java.lang.Object[]) r0)     // Catch:{ all -> 0x007f }
        L_0x01c0:
            if (r1 == 0) goto L_0x01c5
            r13.setHandled(r5)
        L_0x01c5:
            if (r3 == 0) goto L_0x01cf
            boolean r12 = r14.isAsyncStarted()
            if (r12 == 0) goto L_0x01cf
            goto L_0x00e6
        L_0x01cf:
            return
        L_0x01d0:
            r12 = r3
            org.eclipse.jetty.io.EofException r12 = (org.eclipse.jetty.p119io.EofException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01d4:
            r12 = r3
            org.eclipse.jetty.io.RuntimeIOException r12 = (org.eclipse.jetty.p119io.RuntimeIOException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01d8:
            r12 = r3
            org.eclipse.jetty.http.HttpException r12 = (org.eclipse.jetty.http.HttpException) r12     // Catch:{ all -> 0x007f }
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01dc:
            r15 = move-exception
            r3 = r12
            r12 = r15
            goto L_0x01e6
        L_0x01e0:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01e2:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01e4:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x007f }
        L_0x01e6:
            if (r1 == 0) goto L_0x01eb
            r13.setHandled(r5)
        L_0x01eb:
            if (r3 == 0) goto L_0x01fc
            boolean r13 = r14.isAsyncStarted()
            if (r13 == 0) goto L_0x01fc
            javax.servlet.AsyncContext r13 = r14.getAsyncContext()
            org.eclipse.jetty.server.AsyncContinuation r13 = (org.eclipse.jetty.server.AsyncContinuation) r13
            r13.errorComplete()
        L_0x01fc:
            goto L_0x01fe
        L_0x01fd:
            throw r12
        L_0x01fe:
            goto L_0x01fd
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHandler.doHandle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    /* access modifiers changed from: protected */
    public FilterChain getFilterChain(Request request, String str, ServletHolder servletHolder) {
        Object obj;
        MultiMap<String> multiMap;
        ConcurrentMap<String, FilterChain>[] concurrentMapArr;
        FilterChain filterChain;
        String name = str == null ? servletHolder.getName() : str;
        int dispatch = FilterMapping.dispatch(request.getDispatcherType());
        if (this._filterChainsCached && (concurrentMapArr = this._chainCache) != null && (filterChain = (FilterChain) concurrentMapArr[dispatch].get(name)) != null) {
            return filterChain;
        }
        CachedChain cachedChain = null;
        if (str == null || this._filterPathMappings == null) {
            obj = null;
        } else {
            obj = null;
            for (int i = 0; i < this._filterPathMappings.size(); i++) {
                FilterMapping filterMapping = this._filterPathMappings.get(i);
                if (filterMapping.appliesTo(str, dispatch)) {
                    obj = LazyList.add(obj, filterMapping.getFilterHolder());
                }
            }
        }
        if (servletHolder != null && (multiMap = this._filterNameMappings) != null && multiMap.size() > 0 && this._filterNameMappings.size() > 0) {
            Object obj2 = this._filterNameMappings.get(servletHolder.getName());
            for (int i2 = 0; i2 < LazyList.size(obj2); i2++) {
                FilterMapping filterMapping2 = (FilterMapping) LazyList.get(obj2, i2);
                if (filterMapping2.appliesTo(dispatch)) {
                    obj = LazyList.add(obj, filterMapping2.getFilterHolder());
                }
            }
            Object obj3 = this._filterNameMappings.get(Constraint.ANY_ROLE);
            for (int i3 = 0; i3 < LazyList.size(obj3); i3++) {
                FilterMapping filterMapping3 = (FilterMapping) LazyList.get(obj3, i3);
                if (filterMapping3.appliesTo(dispatch)) {
                    obj = LazyList.add(obj, filterMapping3.getFilterHolder());
                }
            }
        }
        if (obj == null) {
            return null;
        }
        if (this._filterChainsCached) {
            if (LazyList.size(obj) > 0) {
                cachedChain = new CachedChain(obj, servletHolder);
            }
            ConcurrentMap<String, FilterChain> concurrentMap = this._chainCache[dispatch];
            Queue<String> queue = this._chainLRU[dispatch];
            while (true) {
                if (this._maxFilterChainsCacheSize <= 0 || concurrentMap.size() < this._maxFilterChainsCacheSize) {
                    break;
                }
                String poll = queue.poll();
                if (poll == null) {
                    concurrentMap.clear();
                    break;
                }
                concurrentMap.remove(poll);
            }
            concurrentMap.put(name, cachedChain);
            queue.add(name);
            return cachedChain;
        } else if (LazyList.size(obj) > 0) {
            return new Chain(request, obj, servletHolder);
        } else {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void invalidateChainsCache() {
        Queue<String>[] queueArr = this._chainLRU;
        if (queueArr[1] != null) {
            queueArr[1].clear();
            this._chainLRU[2].clear();
            this._chainLRU[4].clear();
            this._chainLRU[8].clear();
            this._chainLRU[16].clear();
            this._chainCache[1].clear();
            this._chainCache[2].clear();
            this._chainCache[4].clear();
            this._chainCache[8].clear();
            this._chainCache[16].clear();
        }
    }

    public boolean isAvailable() {
        if (!isStarted()) {
            return false;
        }
        ServletHolder[] servlets = getServlets();
        for (ServletHolder servletHolder : servlets) {
            if (servletHolder != null && !servletHolder.isAvailable()) {
                return false;
            }
        }
        return true;
    }

    public void setStartWithUnavailable(boolean z) {
        this._startWithUnavailable = z;
    }

    public boolean isStartWithUnavailable() {
        return this._startWithUnavailable;
    }

    public void initialize() throws Exception {
        MultiException multiException = new MultiException();
        if (this._filters != null) {
            int i = 0;
            while (true) {
                FilterHolder[] filterHolderArr = this._filters;
                if (i >= filterHolderArr.length) {
                    break;
                }
                filterHolderArr[i].start();
                i++;
            }
        }
        ServletHolder[] servletHolderArr = this._servlets;
        if (servletHolderArr != null) {
            ServletHolder[] servletHolderArr2 = (ServletHolder[]) servletHolderArr.clone();
            Arrays.sort(servletHolderArr2);
            for (int i2 = 0; i2 < servletHolderArr2.length; i2++) {
                try {
                    if (servletHolderArr2[i2].getClassName() == null && servletHolderArr2[i2].getForcedPath() != null) {
                        ServletHolder servletHolder = (ServletHolder) this._servletPathMap.match(servletHolderArr2[i2].getForcedPath());
                        if (servletHolder != null) {
                            if (servletHolder.getClassName() != null) {
                                servletHolderArr2[i2].setClassName(servletHolder.getClassName());
                            }
                        }
                        multiException.add(new IllegalStateException("No forced path servlet for " + servletHolderArr2[i2].getForcedPath()));
                    }
                    servletHolderArr2[i2].start();
                } catch (Throwable th) {
                    LOG.debug(Log.EXCEPTION, th);
                    multiException.add(th);
                }
            }
            multiException.ifExceptionThrow();
        }
    }

    public boolean isFilterChainsCached() {
        return this._filterChainsCached;
    }

    public ServletHolder newServletHolder(Holder.Source source) {
        return new ServletHolder(source);
    }

    public ServletHolder addServletWithMapping(String str, String str2) {
        ServletHolder newServletHolder = newServletHolder(Holder.Source.EMBEDDED);
        newServletHolder.setClassName(str);
        addServletWithMapping(newServletHolder, str2);
        return newServletHolder;
    }

    public ServletHolder addServletWithMapping(Class<? extends Servlet> cls, String str) {
        ServletHolder newServletHolder = newServletHolder(Holder.Source.EMBEDDED);
        newServletHolder.setHeldClass(cls);
        addServletWithMapping(newServletHolder, str);
        return newServletHolder;
    }

    public void addServletWithMapping(ServletHolder servletHolder, String str) {
        ServletHolder[] servlets = getServlets();
        if (servlets != null) {
            servlets = (ServletHolder[]) servlets.clone();
        }
        try {
            setServlets((ServletHolder[]) LazyList.addToArray(servlets, servletHolder, ServletHolder.class));
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(servletHolder.getName());
            servletMapping.setPathSpec(str);
            setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
        } catch (Exception e) {
            setServlets(servlets);
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException(e);
        }
    }

    public void addServlet(ServletHolder servletHolder) {
        setServlets((ServletHolder[]) LazyList.addToArray(getServlets(), servletHolder, ServletHolder.class));
    }

    public void addServletMapping(ServletMapping servletMapping) {
        setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic dynamic, ServletSecurityElement servletSecurityElement) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            return servletContextHandler.setServletSecurity(dynamic, servletSecurityElement);
        }
        return Collections.emptySet();
    }

    public FilterHolder newFilterHolder(Holder.Source source) {
        return new FilterHolder(source);
    }

    public FilterHolder getFilter(String str) {
        return this._filterNameMap.get(str);
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setHeldClass(cls);
        addFilterWithMapping(newFilterHolder, str, enumSet);
        return newFilterHolder;
    }

    public FilterHolder addFilterWithMapping(String str, String str2, EnumSet<DispatcherType> enumSet) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setClassName(str);
        addFilterWithMapping(newFilterHolder, str2, enumSet);
        return newFilterHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatcherTypes(enumSet);
            addFilterMapping(filterMapping);
        } catch (RuntimeException e) {
            setFilters(filters);
            throw e;
        } catch (Error e2) {
            setFilters(filters);
            throw e2;
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, int i) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setHeldClass(cls);
        addFilterWithMapping(newFilterHolder, str, i);
        return newFilterHolder;
    }

    public FilterHolder addFilterWithMapping(String str, String str2, int i) {
        FilterHolder newFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        newFilterHolder.setClassName(str);
        addFilterWithMapping(newFilterHolder, str2, i);
        return newFilterHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, int i) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatches(i);
            addFilterMapping(filterMapping);
        } catch (RuntimeException e) {
            setFilters(filters);
            throw e;
        } catch (Error e2) {
            setFilters(filters);
            throw e2;
        }
    }

    public FilterHolder addFilter(String str, String str2, EnumSet<DispatcherType> enumSet) {
        return addFilterWithMapping(str, str2, enumSet);
    }

    public void addFilter(FilterHolder filterHolder, FilterMapping filterMapping) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
        if (filterMapping != null) {
            addFilterMapping(filterMapping);
        }
    }

    public void addFilter(FilterHolder filterHolder) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
    }

    public void addFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            Holder.Source source = filterMapping.getFilterHolder() == null ? null : filterMapping.getFilterHolder().getSource();
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, false));
                if (source != null && source == Holder.Source.JAVAX_API) {
                    this._matchAfterIndex = 0;
                }
            } else if (source == null || Holder.Source.JAVAX_API != source) {
                int i = this._matchAfterIndex;
                if (i < 0) {
                    setFilterMappings(insertFilterMapping(filterMapping, filterMappings.length - 1, false));
                    return;
                }
                FilterMapping[] insertFilterMapping = insertFilterMapping(filterMapping, i, true);
                this._matchAfterIndex++;
                setFilterMappings(insertFilterMapping);
            } else {
                setFilterMappings(insertFilterMapping(filterMapping, filterMappings.length - 1, false));
                if (this._matchAfterIndex < 0) {
                    this._matchAfterIndex = getFilterMappings().length - 1;
                }
            }
        }
    }

    public void prependFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            Holder.Source source = filterMapping.getFilterHolder().getSource();
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, false));
                if (source != null && Holder.Source.JAVAX_API == source) {
                    this._matchBeforeIndex = 0;
                    return;
                }
                return;
            }
            if (source == null || Holder.Source.JAVAX_API != source) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, true));
            } else {
                int i = this._matchBeforeIndex;
                if (i < 0) {
                    this._matchBeforeIndex = 0;
                    setFilterMappings(insertFilterMapping(filterMapping, 0, true));
                } else {
                    FilterMapping[] insertFilterMapping = insertFilterMapping(filterMapping, i, false);
                    this._matchBeforeIndex++;
                    setFilterMappings(insertFilterMapping);
                }
            }
            int i2 = this._matchAfterIndex;
            if (i2 >= 0) {
                this._matchAfterIndex = i2 + 1;
            }
        }
    }

    /* access modifiers changed from: protected */
    public FilterMapping[] insertFilterMapping(FilterMapping filterMapping, int i, boolean z) {
        if (i >= 0) {
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                return new FilterMapping[]{filterMapping};
            }
            FilterMapping[] filterMappingArr = new FilterMapping[(filterMappings.length + 1)];
            if (z) {
                System.arraycopy(filterMappings, 0, filterMappingArr, 0, i);
                filterMappingArr[i] = filterMapping;
                System.arraycopy(filterMappings, i, filterMappingArr, i + 1, filterMappings.length - i);
            } else {
                int i2 = i + 1;
                System.arraycopy(filterMappings, 0, filterMappingArr, 0, i2);
                filterMappingArr[i2] = filterMapping;
                if (filterMappings.length > i2) {
                    System.arraycopy(filterMappings, i2, filterMappingArr, i + 2, filterMappings.length - i2);
                }
            }
            return filterMappingArr;
        }
        throw new IllegalArgumentException("FilterMapping insertion pos < 0");
    }

    /* access modifiers changed from: protected */
    public synchronized void updateNameMappings() {
        this._filterNameMap.clear();
        if (this._filters != null) {
            for (int i = 0; i < this._filters.length; i++) {
                this._filterNameMap.put(this._filters[i].getName(), this._filters[i]);
                this._filters[i].setServletHandler(this);
            }
        }
        this._servletNameMap.clear();
        if (this._servlets != null) {
            for (int i2 = 0; i2 < this._servlets.length; i2++) {
                this._servletNameMap.put(this._servlets[i2].getName(), this._servlets[i2]);
                this._servlets[i2].setServletHandler(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0110 A[Catch:{ Exception -> 0x01cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x012e A[Catch:{ Exception -> 0x01cb }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateMappings() {
        /*
            r8 = this;
            monitor-enter(r8)
            org.eclipse.jetty.servlet.FilterMapping[] r0 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r1 = 0
            r2 = 0
            if (r0 != 0) goto L_0x000d
            r8._filterPathMappings = r1     // Catch:{ all -> 0x01d2 }
            r8._filterNameMappings = r1     // Catch:{ all -> 0x01d2 }
            goto L_0x0097
        L_0x000d:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x01d2 }
            r0.<init>()     // Catch:{ all -> 0x01d2 }
            r8._filterPathMappings = r0     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.MultiMap r0 = new org.eclipse.jetty.util.MultiMap     // Catch:{ all -> 0x01d2 }
            r0.<init>()     // Catch:{ all -> 0x01d2 }
            r8._filterNameMappings = r0     // Catch:{ all -> 0x01d2 }
            r0 = 0
        L_0x001c:
            org.eclipse.jetty.servlet.FilterMapping[] r3 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            int r3 = r3.length     // Catch:{ all -> 0x01d2 }
            if (r0 >= r3) goto L_0x0097
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.FilterHolder> r3 = r8._filterNameMap     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterMapping[] r4 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r0]     // Catch:{ all -> 0x01d2 }
            java.lang.String r4 = r4.getFilterName()     // Catch:{ all -> 0x01d2 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterHolder r3 = (org.eclipse.jetty.servlet.FilterHolder) r3     // Catch:{ all -> 0x01d2 }
            if (r3 == 0) goto L_0x0078
            org.eclipse.jetty.servlet.FilterMapping[] r4 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r0]     // Catch:{ all -> 0x01d2 }
            r4.setFilterHolder(r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterMapping[] r3 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r3 = r3[r0]     // Catch:{ all -> 0x01d2 }
            java.lang.String[] r3 = r3.getPathSpecs()     // Catch:{ all -> 0x01d2 }
            if (r3 == 0) goto L_0x004d
            java.util.List<org.eclipse.jetty.servlet.FilterMapping> r3 = r8._filterPathMappings     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterMapping[] r4 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r0]     // Catch:{ all -> 0x01d2 }
            r3.add(r4)     // Catch:{ all -> 0x01d2 }
        L_0x004d:
            org.eclipse.jetty.servlet.FilterMapping[] r3 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r3 = r3[r0]     // Catch:{ all -> 0x01d2 }
            java.lang.String[] r3 = r3.getServletNames()     // Catch:{ all -> 0x01d2 }
            if (r3 == 0) goto L_0x0075
            org.eclipse.jetty.servlet.FilterMapping[] r3 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r3 = r3[r0]     // Catch:{ all -> 0x01d2 }
            java.lang.String[] r3 = r3.getServletNames()     // Catch:{ all -> 0x01d2 }
            r4 = 0
        L_0x0060:
            int r5 = r3.length     // Catch:{ all -> 0x01d2 }
            if (r4 >= r5) goto L_0x0075
            r5 = r3[r4]     // Catch:{ all -> 0x01d2 }
            if (r5 == 0) goto L_0x0072
            org.eclipse.jetty.util.MultiMap<java.lang.String> r5 = r8._filterNameMappings     // Catch:{ all -> 0x01d2 }
            r6 = r3[r4]     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterMapping[] r7 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r7 = r7[r0]     // Catch:{ all -> 0x01d2 }
            r5.add(r6, r7)     // Catch:{ all -> 0x01d2 }
        L_0x0072:
            int r4 = r4 + 1
            goto L_0x0060
        L_0x0075:
            int r0 = r0 + 1
            goto L_0x001c
        L_0x0078:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r2.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "No filter named "
            r2.append(r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.FilterMapping[] r3 = r8._filterMappings     // Catch:{ all -> 0x01d2 }
            r0 = r3[r0]     // Catch:{ all -> 0x01d2 }
            java.lang.String r0 = r0.getFilterName()     // Catch:{ all -> 0x01d2 }
            r2.append(r0)     // Catch:{ all -> 0x01d2 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x01d2 }
            r1.<init>(r0)     // Catch:{ all -> 0x01d2 }
            throw r1     // Catch:{ all -> 0x01d2 }
        L_0x0097:
            org.eclipse.jetty.servlet.ServletMapping[] r0 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            if (r0 == 0) goto L_0x010a
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.ServletHolder> r0 = r8._servletNameMap     // Catch:{ all -> 0x01d2 }
            if (r0 != 0) goto L_0x00a0
            goto L_0x010a
        L_0x00a0:
            org.eclipse.jetty.http.PathMap r0 = new org.eclipse.jetty.http.PathMap     // Catch:{ all -> 0x01d2 }
            r0.<init>()     // Catch:{ all -> 0x01d2 }
            r1 = 0
        L_0x00a6:
            org.eclipse.jetty.servlet.ServletMapping[] r3 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            int r3 = r3.length     // Catch:{ all -> 0x01d2 }
            if (r1 >= r3) goto L_0x0107
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.ServletHolder> r3 = r8._servletNameMap     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.ServletMapping[] r4 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r1]     // Catch:{ all -> 0x01d2 }
            java.lang.String r4 = r4.getServletName()     // Catch:{ all -> 0x01d2 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.ServletHolder r3 = (org.eclipse.jetty.servlet.ServletHolder) r3     // Catch:{ all -> 0x01d2 }
            if (r3 == 0) goto L_0x00e8
            boolean r4 = r3.isEnabled()     // Catch:{ all -> 0x01d2 }
            if (r4 == 0) goto L_0x00e5
            org.eclipse.jetty.servlet.ServletMapping[] r4 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r1]     // Catch:{ all -> 0x01d2 }
            java.lang.String[] r4 = r4.getPathSpecs()     // Catch:{ all -> 0x01d2 }
            if (r4 == 0) goto L_0x00e5
            org.eclipse.jetty.servlet.ServletMapping[] r4 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            r4 = r4[r1]     // Catch:{ all -> 0x01d2 }
            java.lang.String[] r4 = r4.getPathSpecs()     // Catch:{ all -> 0x01d2 }
            r5 = 0
        L_0x00d6:
            int r6 = r4.length     // Catch:{ all -> 0x01d2 }
            if (r5 >= r6) goto L_0x00e5
            r6 = r4[r5]     // Catch:{ all -> 0x01d2 }
            if (r6 == 0) goto L_0x00e2
            r6 = r4[r5]     // Catch:{ all -> 0x01d2 }
            r0.put(r6, r3)     // Catch:{ all -> 0x01d2 }
        L_0x00e2:
            int r5 = r5 + 1
            goto L_0x00d6
        L_0x00e5:
            int r1 = r1 + 1
            goto L_0x00a6
        L_0x00e8:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r2.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "No such servlet: "
            r2.append(r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.servlet.ServletMapping[] r3 = r8._servletMappings     // Catch:{ all -> 0x01d2 }
            r1 = r3[r1]     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.getServletName()     // Catch:{ all -> 0x01d2 }
            r2.append(r1)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x01d2 }
            r0.<init>(r1)     // Catch:{ all -> 0x01d2 }
            throw r0     // Catch:{ all -> 0x01d2 }
        L_0x0107:
            r8._servletPathMap = r0     // Catch:{ all -> 0x01d2 }
            goto L_0x010c
        L_0x010a:
            r8._servletPathMap = r1     // Catch:{ all -> 0x01d2 }
        L_0x010c:
            java.util.concurrent.ConcurrentMap<java.lang.String, javax.servlet.FilterChain>[] r0 = r8._chainCache     // Catch:{ all -> 0x01d2 }
            if (r0 == 0) goto L_0x0126
            java.util.concurrent.ConcurrentMap<java.lang.String, javax.servlet.FilterChain>[] r0 = r8._chainCache     // Catch:{ all -> 0x01d2 }
            int r0 = r0.length     // Catch:{ all -> 0x01d2 }
        L_0x0113:
            int r1 = r0 + -1
            if (r0 <= 0) goto L_0x0126
            java.util.concurrent.ConcurrentMap<java.lang.String, javax.servlet.FilterChain>[] r0 = r8._chainCache     // Catch:{ all -> 0x01d2 }
            r0 = r0[r1]     // Catch:{ all -> 0x01d2 }
            if (r0 == 0) goto L_0x0124
            java.util.concurrent.ConcurrentMap<java.lang.String, javax.servlet.FilterChain>[] r0 = r8._chainCache     // Catch:{ all -> 0x01d2 }
            r0 = r0[r1]     // Catch:{ all -> 0x01d2 }
            r0.clear()     // Catch:{ all -> 0x01d2 }
        L_0x0124:
            r0 = r1
            goto L_0x0113
        L_0x0126:
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x01d2 }
            if (r0 == 0) goto L_0x01b0
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r1.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "filterNameMap="
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.FilterHolder> r3 = r8._filterNameMap     // Catch:{ all -> 0x01d2 }
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01d2 }
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x01d2 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r1.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "pathFilters="
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.util.List<org.eclipse.jetty.servlet.FilterMapping> r3 = r8._filterPathMappings     // Catch:{ all -> 0x01d2 }
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01d2 }
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x01d2 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r1.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "servletFilterMap="
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.MultiMap<java.lang.String> r3 = r8._filterNameMappings     // Catch:{ all -> 0x01d2 }
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01d2 }
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x01d2 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r1.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "servletPathMap="
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.http.PathMap r3 = r8._servletPathMap     // Catch:{ all -> 0x01d2 }
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01d2 }
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x01d2 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ all -> 0x01d2 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x01d2 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d2 }
            r1.<init>()     // Catch:{ all -> 0x01d2 }
            java.lang.String r3 = "servletNameMap="
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.util.Map<java.lang.String, org.eclipse.jetty.servlet.ServletHolder> r3 = r8._servletNameMap     // Catch:{ all -> 0x01d2 }
            r1.append(r3)     // Catch:{ all -> 0x01d2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01d2 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x01d2 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r2)     // Catch:{ all -> 0x01d2 }
        L_0x01b0:
            org.eclipse.jetty.servlet.ServletContextHandler r0 = r8._contextHandler     // Catch:{ Exception -> 0x01cb }
            if (r0 == 0) goto L_0x01bc
            org.eclipse.jetty.servlet.ServletContextHandler r0 = r8._contextHandler     // Catch:{ Exception -> 0x01cb }
            boolean r0 = r0.isStarted()     // Catch:{ Exception -> 0x01cb }
            if (r0 != 0) goto L_0x01c6
        L_0x01bc:
            org.eclipse.jetty.servlet.ServletContextHandler r0 = r8._contextHandler     // Catch:{ Exception -> 0x01cb }
            if (r0 != 0) goto L_0x01c9
            boolean r0 = r8.isStarted()     // Catch:{ Exception -> 0x01cb }
            if (r0 == 0) goto L_0x01c9
        L_0x01c6:
            r8.initialize()     // Catch:{ Exception -> 0x01cb }
        L_0x01c9:
            monitor-exit(r8)
            return
        L_0x01cb:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ all -> 0x01d2 }
            r1.<init>(r0)     // Catch:{ all -> 0x01d2 }
            throw r1     // Catch:{ all -> 0x01d2 }
        L_0x01d2:
            r0 = move-exception
            monitor-exit(r8)
            goto L_0x01d6
        L_0x01d5:
            throw r0
        L_0x01d6:
            goto L_0x01d5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHandler.updateMappings():void");
    }

    /* access modifiers changed from: protected */
    public void notFound(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Not Found " + httpServletRequest.getRequestURI(), new Object[0]);
        }
    }

    public void setFilterChainsCached(boolean z) {
        this._filterChainsCached = z;
    }

    public void setFilterMappings(FilterMapping[] filterMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) filterMappingArr, "filterMapping", true);
        }
        this._filterMappings = filterMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public synchronized void setFilters(FilterHolder[] filterHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) filterHolderArr, "filter", true);
        }
        this._filters = filterHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    public void setServletMappings(ServletMapping[] servletMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) servletMappingArr, "servletMapping", true);
        }
        this._servletMappings = servletMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public synchronized void setServlets(ServletHolder[] servletHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) servletHolderArr, "servlet", true);
        }
        this._servlets = servletHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    private class CachedChain implements FilterChain {
        FilterHolder _filterHolder;
        CachedChain _next;
        ServletHolder _servletHolder;

        CachedChain(Object obj, ServletHolder servletHolder) {
            if (LazyList.size(obj) > 0) {
                this._filterHolder = (FilterHolder) LazyList.get(obj, 0);
                this._next = new CachedChain(LazyList.remove(obj, 0), servletHolder);
                return;
            }
            this._servletHolder = servletHolder;
        }

        /* JADX INFO: finally extract failed */
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            Request request = servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
            if (this._filterHolder != null) {
                if (ServletHandler.LOG.isDebugEnabled()) {
                    Logger access$000 = ServletHandler.LOG;
                    access$000.debug("call filter " + this._filterHolder, new Object[0]);
                }
                Filter filter = this._filterHolder.getFilter();
                if (this._filterHolder.isAsyncSupported()) {
                    filter.doFilter(servletRequest, servletResponse, this._next);
                } else if (request.isAsyncSupported()) {
                    try {
                        request.setAsyncSupported(false);
                        filter.doFilter(servletRequest, servletResponse, this._next);
                        request.setAsyncSupported(true);
                    } catch (Throwable th) {
                        request.setAsyncSupported(true);
                        throw th;
                    }
                } else {
                    filter.doFilter(servletRequest, servletResponse, this._next);
                }
            } else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder != null) {
                    if (ServletHandler.LOG.isDebugEnabled()) {
                        Logger access$0002 = ServletHandler.LOG;
                        access$0002.debug("call servlet " + this._servletHolder, new Object[0]);
                    }
                    this._servletHolder.handle(request, servletRequest, servletResponse);
                } else if (ServletHandler.this.getHandler() == null) {
                    ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                } else {
                    ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), request, httpServletRequest, (HttpServletResponse) servletResponse);
                }
            }
        }

        public String toString() {
            if (this._filterHolder != null) {
                return this._filterHolder + "->" + this._next.toString();
            }
            ServletHolder servletHolder = this._servletHolder;
            return servletHolder != null ? servletHolder.toString() : "null";
        }
    }

    private class Chain implements FilterChain {
        final Request _baseRequest;
        final Object _chain;
        int _filter = 0;
        final ServletHolder _servletHolder;

        Chain(Request request, Object obj, ServletHolder servletHolder) {
            this._baseRequest = request;
            this._chain = obj;
            this._servletHolder = servletHolder;
        }

        /* JADX INFO: finally extract failed */
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            if (ServletHandler.LOG.isDebugEnabled()) {
                Logger access$000 = ServletHandler.LOG;
                access$000.debug("doFilter " + this._filter, new Object[0]);
            }
            if (this._filter < LazyList.size(this._chain)) {
                Object obj = this._chain;
                int i = this._filter;
                this._filter = i + 1;
                FilterHolder filterHolder = (FilterHolder) LazyList.get(obj, i);
                if (ServletHandler.LOG.isDebugEnabled()) {
                    Logger access$0002 = ServletHandler.LOG;
                    access$0002.debug("call filter " + filterHolder, new Object[0]);
                }
                Filter filter = filterHolder.getFilter();
                if (filterHolder.isAsyncSupported() || !this._baseRequest.isAsyncSupported()) {
                    filter.doFilter(servletRequest, servletResponse, this);
                    return;
                }
                try {
                    this._baseRequest.setAsyncSupported(false);
                    filter.doFilter(servletRequest, servletResponse, this);
                    this._baseRequest.setAsyncSupported(true);
                } catch (Throwable th) {
                    this._baseRequest.setAsyncSupported(true);
                    throw th;
                }
            } else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder != null) {
                    if (ServletHandler.LOG.isDebugEnabled()) {
                        Logger access$0003 = ServletHandler.LOG;
                        access$0003.debug("call servlet " + this._servletHolder, new Object[0]);
                    }
                    this._servletHolder.handle(this._baseRequest, servletRequest, servletResponse);
                } else if (ServletHandler.this.getHandler() == null) {
                    ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                } else {
                    ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest(), httpServletRequest, (HttpServletResponse) servletResponse);
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < LazyList.size(this._chain); i++) {
                sb.append(LazyList.get(this._chain, i).toString());
                sb.append("->");
            }
            sb.append(this._servletHolder);
            return sb.toString();
        }
    }

    public int getMaxFilterChainsCacheSize() {
        return this._maxFilterChainsCacheSize;
    }

    public void setMaxFilterChainsCacheSize(int i) {
        this._maxFilterChainsCacheSize = i;
    }

    /* access modifiers changed from: package-private */
    public void destroyServlet(Servlet servlet) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyServlet(servlet);
        }
    }

    /* access modifiers changed from: package-private */
    public void destroyFilter(Filter filter) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyFilter(filter);
        }
    }

    public void dump(Appendable appendable, String str) throws IOException {
        super.dumpThis(appendable);
        dump(appendable, str, TypeUtil.asList(getHandlers()), getBeans(), TypeUtil.asList(getFilterMappings()), TypeUtil.asList(getFilters()), TypeUtil.asList(getServletMappings()), TypeUtil.asList(getServlets()));
    }
}
