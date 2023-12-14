package org.eclipse.jetty.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.Container;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ShutdownThread;
import org.eclipse.jetty.util.thread.ThreadPool;

public class Server extends HandlerWrapper implements Attributes {
    private static final Logger LOG = Log.getLogger((Class<?>) Server.class);
    private static final String __version;
    private final AttributesMap _attributes = new AttributesMap();
    private Connector[] _connectors;
    private final Container _container = new Container();
    private boolean _dumpAfterStart = false;
    private boolean _dumpBeforeStop = false;
    private int _graceful = 0;
    private boolean _sendDateHeader = false;
    private boolean _sendServerVersion = true;
    private SessionIdManager _sessionIdManager;
    private boolean _stopAtShutdown;
    private ThreadPool _threadPool;
    private boolean _uncheckedPrintWriter = false;

    public interface Graceful extends Handler {
        void setShutdown(boolean z);
    }

    @Deprecated
    public int getMaxCookieVersion() {
        return 1;
    }

    @Deprecated
    public void setMaxCookieVersion(int i) {
    }

    static {
        if (Server.class.getPackage() == null || !"Eclipse.org - Jetty".equals(Server.class.getPackage().getImplementationVendor()) || Server.class.getPackage().getImplementationVersion() == null) {
            __version = System.getProperty("jetty.version", "8.y.z-SNAPSHOT");
        } else {
            __version = Server.class.getPackage().getImplementationVersion();
        }
    }

    public Server() {
        setServer(this);
    }

    public Server(int i) {
        setServer(this);
        SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
        selectChannelConnector.setPort(i);
        setConnectors(new Connector[]{selectChannelConnector});
    }

    public Server(InetSocketAddress inetSocketAddress) {
        setServer(this);
        SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
        selectChannelConnector.setHost(inetSocketAddress.getHostName());
        selectChannelConnector.setPort(inetSocketAddress.getPort());
        setConnectors(new Connector[]{selectChannelConnector});
    }

    public static String getVersion() {
        return __version;
    }

    public Container getContainer() {
        return this._container;
    }

    public boolean getStopAtShutdown() {
        return this._stopAtShutdown;
    }

    public void setStopAtShutdown(boolean z) {
        if (!z) {
            ShutdownThread.deregister(this);
        } else if (!this._stopAtShutdown && isStarted()) {
            ShutdownThread.register(this);
        }
        this._stopAtShutdown = z;
    }

    public Connector[] getConnectors() {
        return this._connectors;
    }

    public void addConnector(Connector connector) {
        setConnectors((Connector[]) LazyList.addToArray(getConnectors(), connector, Connector.class));
    }

    public void removeConnector(Connector connector) {
        setConnectors((Connector[]) LazyList.removeFromArray(getConnectors(), connector));
    }

    public void setConnectors(Connector[] connectorArr) {
        if (connectorArr != null) {
            for (Connector server : connectorArr) {
                server.setServer(this);
            }
        }
        this._container.update((Object) this, (Object[]) this._connectors, (Object[]) connectorArr, "connector");
        this._connectors = connectorArr;
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        ThreadPool threadPool2 = this._threadPool;
        if (threadPool2 != null) {
            removeBean(threadPool2);
        }
        this._container.update((Object) this, (Object) this._threadPool, (Object) threadPool, "threadpool", false);
        this._threadPool = threadPool;
        ThreadPool threadPool3 = this._threadPool;
        if (threadPool3 != null) {
            addBean(threadPool3);
        }
    }

    public boolean isDumpAfterStart() {
        return this._dumpAfterStart;
    }

    public void setDumpAfterStart(boolean z) {
        this._dumpAfterStart = z;
    }

    public boolean isDumpBeforeStop() {
        return this._dumpBeforeStop;
    }

    public void setDumpBeforeStop(boolean z) {
        this._dumpBeforeStop = z;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        int i = 0;
        if (getStopAtShutdown()) {
            ShutdownThread.register(this);
        }
        ShutdownMonitor.getInstance().start();
        Logger logger = LOG;
        logger.info("jetty-" + __version, new Object[0]);
        HttpGenerator.setServerVersion(__version);
        MultiException multiException = new MultiException();
        if (this._threadPool == null) {
            setThreadPool(new QueuedThreadPool());
        }
        try {
            super.doStart();
        } catch (Throwable th) {
            multiException.add(th);
        }
        if (this._connectors != null && multiException.size() == 0) {
            while (true) {
                Connector[] connectorArr = this._connectors;
                if (i >= connectorArr.length) {
                    break;
                }
                try {
                    connectorArr[i].start();
                } catch (Throwable th2) {
                    multiException.add(th2);
                }
                i++;
            }
        }
        if (isDumpAfterStart()) {
            dumpStdErr();
        }
        multiException.ifExceptionThrow();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStop() throws java.lang.Exception {
        /*
            r9 = this;
            boolean r0 = r9.isDumpBeforeStop()
            if (r0 == 0) goto L_0x0009
            r9.dumpStdErr()
        L_0x0009:
            org.eclipse.jetty.util.MultiException r0 = new org.eclipse.jetty.util.MultiException
            r0.<init>()
            int r1 = r9._graceful
            if (r1 <= 0) goto L_0x005d
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors
            java.lang.String r2 = "Graceful shutdown {}"
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x003a
            int r1 = r1.length
        L_0x001b:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x003a
            org.eclipse.jetty.util.log.Logger r1 = LOG
            java.lang.Object[] r6 = new java.lang.Object[r4]
            org.eclipse.jetty.server.Connector[] r7 = r9._connectors
            r7 = r7[r5]
            r6[r3] = r7
            r1.info((java.lang.String) r2, (java.lang.Object[]) r6)
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors     // Catch:{ Throwable -> 0x0034 }
            r1 = r1[r5]     // Catch:{ Throwable -> 0x0034 }
            r1.close()     // Catch:{ Throwable -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r1 = move-exception
            r0.add(r1)
        L_0x0038:
            r1 = r5
            goto L_0x001b
        L_0x003a:
            java.lang.Class<org.eclipse.jetty.server.Server$Graceful> r1 = org.eclipse.jetty.server.Server.Graceful.class
            org.eclipse.jetty.server.Handler[] r1 = r9.getChildHandlersByClass(r1)
            r5 = 0
        L_0x0041:
            int r6 = r1.length
            if (r5 >= r6) goto L_0x0057
            r6 = r1[r5]
            org.eclipse.jetty.server.Server$Graceful r6 = (org.eclipse.jetty.server.Server.Graceful) r6
            org.eclipse.jetty.util.log.Logger r7 = LOG
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r6
            r7.info((java.lang.String) r2, (java.lang.Object[]) r8)
            r6.setShutdown(r4)
            int r5 = r5 + 1
            goto L_0x0041
        L_0x0057:
            int r1 = r9._graceful
            long r1 = (long) r1
            java.lang.Thread.sleep(r1)
        L_0x005d:
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors
            if (r1 == 0) goto L_0x0074
            int r1 = r1.length
        L_0x0062:
            int r2 = r1 + -1
            if (r1 <= 0) goto L_0x0074
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors     // Catch:{ Throwable -> 0x006e }
            r1 = r1[r2]     // Catch:{ Throwable -> 0x006e }
            r1.stop()     // Catch:{ Throwable -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r1 = move-exception
            r0.add(r1)
        L_0x0072:
            r1 = r2
            goto L_0x0062
        L_0x0074:
            super.doStop()     // Catch:{ Throwable -> 0x0078 }
            goto L_0x007c
        L_0x0078:
            r1 = move-exception
            r0.add(r1)
        L_0x007c:
            r0.ifExceptionThrow()
            boolean r0 = r9.getStopAtShutdown()
            if (r0 == 0) goto L_0x0088
            org.eclipse.jetty.util.thread.ShutdownThread.deregister(r9)
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.Server.doStop():void");
    }

    public void handle(AbstractHttpConnection abstractHttpConnection) throws IOException, ServletException {
        String pathInfo = abstractHttpConnection.getRequest().getPathInfo();
        Request request = abstractHttpConnection.getRequest();
        Response response = abstractHttpConnection.getResponse();
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("REQUEST " + pathInfo + " on " + abstractHttpConnection, new Object[0]);
            handle(pathInfo, request, request, response);
            Logger logger2 = LOG;
            logger2.debug("RESPONSE " + pathInfo + "  " + abstractHttpConnection.getResponse().getStatus() + " handled=" + request.isHandled(), new Object[0]);
            return;
        }
        handle(pathInfo, request, request, response);
    }

    public void handleAsync(AbstractHttpConnection abstractHttpConnection) throws IOException, ServletException {
        AsyncContinuation asyncContinuation = abstractHttpConnection.getRequest().getAsyncContinuation();
        AsyncContinuation.AsyncEventState asyncEventState = asyncContinuation.getAsyncEventState();
        Request request = abstractHttpConnection.getRequest();
        String path = asyncEventState.getPath();
        if (path != null) {
            HttpURI httpURI = new HttpURI(URIUtil.addPaths(asyncEventState.getServletContext().getContextPath(), path));
            request.setUri(httpURI);
            request.setRequestURI((String) null);
            request.setPathInfo(request.getRequestURI());
            if (httpURI.getQuery() != null) {
                request.mergeQueryString(httpURI.getQuery());
            }
        }
        String pathInfo = request.getPathInfo();
        HttpServletRequest httpServletRequest = (HttpServletRequest) asyncContinuation.getRequest();
        HttpServletResponse httpServletResponse = (HttpServletResponse) asyncContinuation.getResponse();
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("REQUEST " + pathInfo + " on " + abstractHttpConnection, new Object[0]);
            handle(pathInfo, request, httpServletRequest, httpServletResponse);
            Logger logger2 = LOG;
            logger2.debug("RESPONSE " + pathInfo + "  " + abstractHttpConnection.getResponse().getStatus(), new Object[0]);
            return;
        }
        handle(pathInfo, request, httpServletRequest, httpServletResponse);
    }

    public void join() throws InterruptedException {
        getThreadPool().join();
    }

    public SessionIdManager getSessionIdManager() {
        return this._sessionIdManager;
    }

    public void setSessionIdManager(SessionIdManager sessionIdManager) {
        SessionIdManager sessionIdManager2 = this._sessionIdManager;
        if (sessionIdManager2 != null) {
            removeBean(sessionIdManager2);
        }
        this._container.update((Object) this, (Object) this._sessionIdManager, (Object) sessionIdManager, "sessionIdManager", false);
        this._sessionIdManager = sessionIdManager;
        SessionIdManager sessionIdManager3 = this._sessionIdManager;
        if (sessionIdManager3 != null) {
            addBean(sessionIdManager3);
        }
    }

    public void setSendServerVersion(boolean z) {
        this._sendServerVersion = z;
    }

    public boolean getSendServerVersion() {
        return this._sendServerVersion;
    }

    public void setSendDateHeader(boolean z) {
        this._sendDateHeader = z;
    }

    public boolean getSendDateHeader() {
        return this._sendDateHeader;
    }

    @Deprecated
    public void addLifeCycle(LifeCycle lifeCycle) {
        addBean(lifeCycle);
    }

    public boolean addBean(Object obj) {
        if (!super.addBean(obj)) {
            return false;
        }
        this._container.addBean(obj);
        return true;
    }

    @Deprecated
    public void removeLifeCycle(LifeCycle lifeCycle) {
        removeBean(lifeCycle);
    }

    public boolean removeBean(Object obj) {
        if (!super.removeBean(obj)) {
            return false;
        }
        this._container.removeBean(obj);
        return true;
    }

    public void clearAttributes() {
        this._attributes.clearAttributes();
    }

    public Object getAttribute(String str) {
        return this._attributes.getAttribute(str);
    }

    public Enumeration getAttributeNames() {
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public void removeAttribute(String str) {
        this._attributes.removeAttribute(str);
    }

    public void setAttribute(String str, Object obj) {
        this._attributes.setAttribute(str, obj);
    }

    public int getGracefulShutdown() {
        return this._graceful;
    }

    public void setGracefulShutdown(int i) {
        this._graceful = i;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, TypeUtil.asList(getHandlers()), getBeans(), TypeUtil.asList(this._connectors));
    }

    public boolean isUncheckedPrintWriter() {
        return this._uncheckedPrintWriter;
    }

    public void setUncheckedPrintWriter(boolean z) {
        this._uncheckedPrintWriter = z;
    }

    public static void main(String... strArr) throws Exception {
        System.err.println(getVersion());
    }
}
