package org.eclipse.jetty.server.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JDBCSessionManager extends AbstractSessionManager {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) JDBCSessionManager.class);
    protected JDBCSessionIdManager _jdbcSessionIdMgr = null;
    protected long _saveIntervalSec = 60;
    private ConcurrentHashMap<String, AbstractSession> _sessions;

    public void cacheInvalidate(Session session) {
    }

    /* access modifiers changed from: protected */
    public void invalidateSessions() {
    }

    public class Session extends AbstractSession {
        private static final long serialVersionUID = 5208464051134226143L;
        private String _canonicalContext;
        private long _cookieSet;
        private boolean _dirty = false;
        /* access modifiers changed from: private */
        public long _expiryTime;
        /* access modifiers changed from: private */
        public String _lastNode;
        /* access modifiers changed from: private */
        public long _lastSaved;
        private String _rowId;
        private String _virtualHost;

        protected Session(HttpServletRequest httpServletRequest) {
            super(JDBCSessionManager.this, httpServletRequest);
            long j;
            int maxInactiveInterval = getMaxInactiveInterval();
            if (maxInactiveInterval <= 0) {
                j = 0;
            } else {
                j = System.currentTimeMillis() + (((long) maxInactiveInterval) * 1000);
            }
            this._expiryTime = j;
            this._virtualHost = JDBCSessionManager.getVirtualHost(JDBCSessionManager.this._context);
            this._canonicalContext = JDBCSessionManager.canonicalize(JDBCSessionManager.this._context.getContextPath());
            this._lastNode = JDBCSessionManager.this.getSessionIdManager().getWorkerName();
        }

        protected Session(String str, String str2, long j, long j2) {
            super(JDBCSessionManager.this, j, j2, str);
            this._rowId = str2;
        }

        /* access modifiers changed from: protected */
        public synchronized String getRowId() {
            return this._rowId;
        }

        /* access modifiers changed from: protected */
        public synchronized void setRowId(String str) {
            this._rowId = str;
        }

        public synchronized void setVirtualHost(String str) {
            this._virtualHost = str;
        }

        public synchronized String getVirtualHost() {
            return this._virtualHost;
        }

        public synchronized long getLastSaved() {
            return this._lastSaved;
        }

        public synchronized void setLastSaved(long j) {
            this._lastSaved = j;
        }

        public synchronized void setExpiryTime(long j) {
            this._expiryTime = j;
        }

        public synchronized long getExpiryTime() {
            return this._expiryTime;
        }

        public synchronized void setCanonicalContext(String str) {
            this._canonicalContext = str;
        }

        public synchronized String getCanonicalContext() {
            return this._canonicalContext;
        }

        public void setCookieSet(long j) {
            this._cookieSet = j;
        }

        public synchronized long getCookieSet() {
            return this._cookieSet;
        }

        public synchronized void setLastNode(String str) {
            this._lastNode = str;
        }

        public synchronized String getLastNode() {
            return this._lastNode;
        }

        public void setAttribute(String str, Object obj) {
            super.setAttribute(str, obj);
            this._dirty = true;
        }

        public void removeAttribute(String str) {
            super.removeAttribute(str);
            this._dirty = true;
        }

        /* access modifiers changed from: protected */
        public void cookieSet() {
            this._cookieSet = getAccessed();
        }

        /* access modifiers changed from: protected */
        public boolean access(long j) {
            synchronized (this) {
                if (!super.access(j)) {
                    return false;
                }
                int maxInactiveInterval = getMaxInactiveInterval();
                this._expiryTime = maxInactiveInterval <= 0 ? 0 : j + (((long) maxInactiveInterval) * 1000);
                return true;
            }
        }

        /* access modifiers changed from: protected */
        public void complete() {
            synchronized (this) {
                super.complete();
                try {
                    if (isValid()) {
                        if (this._dirty) {
                            willPassivate();
                            JDBCSessionManager.this.updateSession(this);
                            didActivate();
                        } else if (getAccessed() - this._lastSaved >= JDBCSessionManager.this.getSaveInterval() * 1000) {
                            JDBCSessionManager.this.updateSessionAccessTime(this);
                        }
                    }
                } catch (Exception e) {
                    try {
                        Logger logger = LOG;
                        logger.warn("Problem persisting changed session data id=" + getId(), (Throwable) e);
                    } catch (Throwable th) {
                        this._dirty = false;
                        throw th;
                    }
                }
                this._dirty = false;
            }
        }

        /* access modifiers changed from: protected */
        public void timeout() throws IllegalStateException {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Timing out session id=" + getClusterId(), new Object[0]);
            }
            super.timeout();
        }

        public String toString() {
            return "Session rowId=" + this._rowId + ",id=" + getId() + ",lastNode=" + this._lastNode + ",created=" + getCreationTime() + ",accessed=" + getAccessed() + ",lastAccessed=" + getLastAccessedTime() + ",cookieSet=" + this._cookieSet + ",lastSaved=" + this._lastSaved + ",expiry=" + this._expiryTime;
        }
    }

    protected class ClassLoadingObjectInputStream extends ObjectInputStream {
        public ClassLoadingObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        public ClassLoadingObjectInputStream() throws IOException {
        }

        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            try {
                return Class.forName(objectStreamClass.getName(), false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException unused) {
                return super.resolveClass(objectStreamClass);
            }
        }
    }

    public void setSaveInterval(long j) {
        this._saveIntervalSec = j;
    }

    public long getSaveInterval() {
        return this._saveIntervalSec;
    }

    public Session getSession(String str) {
        Session session;
        Session session2 = (Session) this._sessions.get(str);
        synchronized (this) {
            long currentTimeMillis = System.currentTimeMillis();
            if (LOG.isDebugEnabled()) {
                if (session2 == null) {
                    Logger logger = LOG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("getSession(");
                    sb.append(str);
                    sb.append("): not in session map,");
                    sb.append(" now=");
                    sb.append(currentTimeMillis);
                    sb.append(" lastSaved=");
                    sb.append(session2 == null ? 0 : session2._lastSaved);
                    sb.append(" interval=");
                    sb.append(this._saveIntervalSec * 1000);
                    logger.debug(sb.toString(), new Object[0]);
                } else {
                    Logger logger2 = LOG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("getSession(");
                    sb2.append(str);
                    sb2.append("): in session map, ");
                    sb2.append(" now=");
                    sb2.append(currentTimeMillis);
                    sb2.append(" lastSaved=");
                    sb2.append(session2 == null ? 0 : session2._lastSaved);
                    sb2.append(" interval=");
                    sb2.append(this._saveIntervalSec * 1000);
                    sb2.append(" lastNode=");
                    sb2.append(session2._lastNode);
                    sb2.append(" thisNode=");
                    sb2.append(getSessionIdManager().getWorkerName());
                    sb2.append(" difference=");
                    sb2.append(currentTimeMillis - session2._lastSaved);
                    logger2.debug(sb2.toString(), new Object[0]);
                }
            }
            if (session2 == null) {
                try {
                    LOG.debug("getSession(" + str + "): no session in session map. Reloading session data from db.", new Object[0]);
                    session = loadSession(str, canonicalize(this._context.getContextPath()), getVirtualHost(this._context));
                } catch (Exception e) {
                    LOG.warn("Unable to load session " + str, (Throwable) e);
                    return null;
                }
            } else if (currentTimeMillis - session2._lastSaved >= this._saveIntervalSec * 1000) {
                LOG.debug("getSession(" + str + "): stale session. Reloading session data from db.", new Object[0]);
                session = loadSession(str, canonicalize(this._context.getContextPath()), getVirtualHost(this._context));
            } else {
                LOG.debug("getSession(" + str + "): session in session map", new Object[0]);
                session = session2;
            }
            if (session != null) {
                if (session.getLastNode().equals(getSessionIdManager().getWorkerName())) {
                    if (session2 != null) {
                        LOG.debug("getSession({}): Session not stale {}", str, session2);
                    }
                }
                if (session._expiryTime > 0) {
                    if (session._expiryTime <= currentTimeMillis) {
                        LOG.debug("getSession ({}): Session has expired", str);
                        session2 = null;
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("getSession(" + str + "): lastNode=" + session.getLastNode() + " thisNode=" + getSessionIdManager().getWorkerName(), new Object[0]);
                }
                session.setLastNode(getSessionIdManager().getWorkerName());
                this._sessions.put(str, session);
                try {
                    updateSessionNode(session);
                    session.didActivate();
                } catch (Exception e2) {
                    LOG.warn("Unable to update freshly loaded session " + str, (Throwable) e2);
                    return null;
                }
            } else {
                LOG.debug("getSession({}): No session in database matching id={}", str, str);
            }
            session2 = session;
        }
        return session2;
    }

    public int getSessions() {
        int size;
        synchronized (this) {
            size = this._sessions.size();
        }
        return size;
    }

    public void doStart() throws Exception {
        if (this._sessionIdManager != null) {
            this._jdbcSessionIdMgr = (JDBCSessionIdManager) this._sessionIdManager;
            this._sessions = new ConcurrentHashMap<>();
            super.doStart();
            return;
        }
        throw new IllegalStateException("No session id manager defined");
    }

    public void doStop() throws Exception {
        this._sessions.clear();
        this._sessions = null;
        super.doStop();
    }

    /* access modifiers changed from: protected */
    public void invalidateSession(String str) {
        Session session;
        synchronized (this) {
            session = (Session) this._sessions.get(str);
        }
        if (session != null) {
            session.invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean removeSession(String str) {
        boolean z;
        synchronized (this) {
            Session session = (Session) this._sessions.remove(str);
            if (session != null) {
                try {
                    deleteSession(session);
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn("Problem deleting session id=" + str, (Throwable) e);
                }
            }
            z = session != null;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void addSession(AbstractSession abstractSession) {
        if (abstractSession != null) {
            synchronized (this) {
                this._sessions.put(abstractSession.getClusterId(), abstractSession);
            }
            try {
                synchronized (abstractSession) {
                    abstractSession.willPassivate();
                    storeSession((Session) abstractSession);
                    abstractSession.didActivate();
                }
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn("Unable to store new session id=" + abstractSession.getId(), (Throwable) e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new Session(httpServletRequest);
    }

    public void removeSession(AbstractSession abstractSession, boolean z) {
        boolean z2;
        synchronized (this) {
            if (getSession(abstractSession.getClusterId()) != null) {
                z2 = true;
                removeSession(abstractSession.getClusterId());
            } else {
                z2 = false;
            }
        }
        if (z2) {
            this._sessionIdManager.removeSession(abstractSession);
            if (z) {
                this._sessionIdManager.invalidateAll(abstractSession.getClusterId());
            }
            if (z && !this._sessionListeners.isEmpty()) {
                HttpSessionEvent httpSessionEvent = new HttpSessionEvent(abstractSession);
                for (HttpSessionListener sessionDestroyed : this._sessionListeners) {
                    sessionDestroyed.sessionDestroyed(httpSessionEvent);
                }
            }
            if (!z) {
                abstractSession.willPassivate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void expire(List<?> list) {
        if (!isStopping() && !isStopped()) {
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            ListIterator<?> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                try {
                    String str = (String) listIterator.next();
                    if (LOG.isDebugEnabled()) {
                        Logger logger = LOG;
                        logger.debug("Expiring session id " + str, new Object[0]);
                    }
                    Session session = (Session) this._sessions.get(str);
                    if (session != null) {
                        session.timeout();
                        listIterator.remove();
                    } else if (LOG.isDebugEnabled()) {
                        Logger logger2 = LOG;
                        logger2.debug("Unrecognized session id=" + str, new Object[0]);
                    }
                } catch (Throwable th) {
                    currentThread.setContextClassLoader(contextClassLoader);
                    throw th;
                }
            }
            currentThread.setContextClassLoader(contextClassLoader);
        }
    }

    /* access modifiers changed from: protected */
    public Session loadSession(String str, String str2, String str3) throws Exception {
        AtomicReference atomicReference = new AtomicReference();
        AtomicReference atomicReference2 = new AtomicReference();
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final AtomicReference atomicReference3 = atomicReference;
        final AtomicReference atomicReference4 = atomicReference2;
        C24341 r0 = new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:32:0x00fd A[SYNTHETIC, Splitter:B:32:0x00fd] */
            /* JADX WARNING: Removed duplicated region for block: B:37:0x010b A[SYNTHETIC, Splitter:B:37:0x010b] */
            /* JADX WARNING: Removed duplicated region for block: B:43:0x011b A[SYNTHETIC, Splitter:B:43:0x011b] */
            /* JADX WARNING: Removed duplicated region for block: B:48:0x0129 A[SYNTHETIC, Splitter:B:48:0x0129] */
            /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r14 = this;
                    r0 = 0
                    org.eclipse.jetty.server.session.JDBCSessionManager r1 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00f2, all -> 0x00ed }
                    java.sql.Connection r1 = r1.getConnection()     // Catch:{ Exception -> 0x00f2, all -> 0x00ed }
                    org.eclipse.jetty.server.session.JDBCSessionManager r2 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    org.eclipse.jetty.server.session.JDBCSessionIdManager r2 = r2._jdbcSessionIdMgr     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r2 = r2._dbAdaptor     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    java.lang.String r3 = r2     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    java.lang.String r4 = r3     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    java.lang.String r5 = r4     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    java.sql.PreparedStatement r2 = r2.getLoadStatement(r1, r3, r4, r5)     // Catch:{ Exception -> 0x00e8, all -> 0x00e3 }
                    java.sql.ResultSet r3 = r2.executeQuery()     // Catch:{ Exception -> 0x00e1 }
                    boolean r4 = r3.next()     // Catch:{ Exception -> 0x00e1 }
                    if (r4 == 0) goto L_0x00c8
                    org.eclipse.jetty.server.session.JDBCSessionManager$Session r0 = new org.eclipse.jetty.server.session.JDBCSessionManager$Session     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionManager r6 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r7 = r2     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionManager r4 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionIdManager r4 = r4._jdbcSessionIdMgr     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = r4._sessionTableRowId     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r8 = r3.getString(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "createTime"
                    long r9 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "accessTime"
                    long r11 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    r5 = r0
                    r5.<init>(r7, r8, r9, r11)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "cookieTime"
                    long r4 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setCookieSet(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "lastAccessTime"
                    long r4 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setLastAccessedTime(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "lastNode"
                    java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setLastNode(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "lastSavedTime"
                    long r4 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setLastSaved(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "expiryTime"
                    long r4 = r3.getLong(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setExpiryTime(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "contextPath"
                    java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setCanonicalContext(r4)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = "virtualHost"
                    java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00e1 }
                    r0.setVirtualHost(r4)     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionManager r4 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.SessionIdManager r4 = r4.getSessionIdManager()     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionIdManager r4 = (org.eclipse.jetty.server.session.JDBCSessionIdManager) r4     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionIdManager$DatabaseAdaptor r4 = r4._dbAdaptor     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r5 = "map"
                    java.io.InputStream r3 = r4.getBlobInputStream(r3, r5)     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionManager$ClassLoadingObjectInputStream r4 = new org.eclipse.jetty.server.session.JDBCSessionManager$ClassLoadingObjectInputStream     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.server.session.JDBCSessionManager r5 = org.eclipse.jetty.server.session.JDBCSessionManager.this     // Catch:{ Exception -> 0x00e1 }
                    r4.<init>(r3)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.Object r3 = r4.readObject()     // Catch:{ Exception -> 0x00e1 }
                    java.util.Map r3 = (java.util.Map) r3     // Catch:{ Exception -> 0x00e1 }
                    r0.addAttributes(r3)     // Catch:{ Exception -> 0x00e1 }
                    r4.close()     // Catch:{ Exception -> 0x00e1 }
                    org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG     // Catch:{ Exception -> 0x00e1 }
                    boolean r3 = r3.isDebugEnabled()     // Catch:{ Exception -> 0x00e1 }
                    if (r3 == 0) goto L_0x00c8
                    org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG     // Catch:{ Exception -> 0x00e1 }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e1 }
                    r4.<init>()     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r5 = "LOADED session "
                    r4.append(r5)     // Catch:{ Exception -> 0x00e1 }
                    r4.append(r0)     // Catch:{ Exception -> 0x00e1 }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00e1 }
                    r5 = 0
                    java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x00e1 }
                    r3.debug((java.lang.String) r4, (java.lang.Object[]) r5)     // Catch:{ Exception -> 0x00e1 }
                L_0x00c8:
                    java.util.concurrent.atomic.AtomicReference r3 = r5     // Catch:{ Exception -> 0x00e1 }
                    r3.set(r0)     // Catch:{ Exception -> 0x00e1 }
                    if (r2 == 0) goto L_0x00db
                    r2.close()     // Catch:{ Exception -> 0x00d3 }
                    goto L_0x00db
                L_0x00d3:
                    r0 = move-exception
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG
                    r2.warn(r0)
                L_0x00db:
                    if (r1 == 0) goto L_0x0117
                    r1.close()     // Catch:{ Exception -> 0x010f }
                    goto L_0x0117
                L_0x00e1:
                    r0 = move-exception
                    goto L_0x00f6
                L_0x00e3:
                    r2 = move-exception
                    r13 = r2
                    r2 = r0
                    r0 = r13
                    goto L_0x0119
                L_0x00e8:
                    r2 = move-exception
                    r13 = r2
                    r2 = r0
                    r0 = r13
                    goto L_0x00f6
                L_0x00ed:
                    r1 = move-exception
                    r2 = r0
                    r0 = r1
                    r1 = r2
                    goto L_0x0119
                L_0x00f2:
                    r1 = move-exception
                    r2 = r0
                    r0 = r1
                    r1 = r2
                L_0x00f6:
                    java.util.concurrent.atomic.AtomicReference r3 = r6     // Catch:{ all -> 0x0118 }
                    r3.set(r0)     // Catch:{ all -> 0x0118 }
                    if (r2 == 0) goto L_0x0109
                    r2.close()     // Catch:{ Exception -> 0x0101 }
                    goto L_0x0109
                L_0x0101:
                    r0 = move-exception
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG
                    r2.warn(r0)
                L_0x0109:
                    if (r1 == 0) goto L_0x0117
                    r1.close()     // Catch:{ Exception -> 0x010f }
                    goto L_0x0117
                L_0x010f:
                    r0 = move-exception
                    org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG
                    r1.warn(r0)
                L_0x0117:
                    return
                L_0x0118:
                    r0 = move-exception
                L_0x0119:
                    if (r2 == 0) goto L_0x0127
                    r2.close()     // Catch:{ Exception -> 0x011f }
                    goto L_0x0127
                L_0x011f:
                    r2 = move-exception
                    org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG
                    r3.warn(r2)
                L_0x0127:
                    if (r1 == 0) goto L_0x0135
                    r1.close()     // Catch:{ Exception -> 0x012d }
                    goto L_0x0135
                L_0x012d:
                    r1 = move-exception
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG
                    r2.warn(r1)
                L_0x0135:
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionManager.C24341.run():void");
            }
        };
        if (this._context == null) {
            r0.run();
        } else {
            this._context.getContextHandler().handle(r0);
        }
        if (atomicReference2.get() == null) {
            return (Session) atomicReference.get();
        }
        this._jdbcSessionIdMgr.removeSession(str);
        throw ((Exception) atomicReference2.get());
    }

    /* access modifiers changed from: protected */
    public void storeSession(Session session) throws Exception {
        if (session != null) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = null;
            try {
                String calculateRowId = calculateRowId(session);
                long currentTimeMillis = System.currentTimeMillis();
                connection.setAutoCommit(true);
                preparedStatement = connection.prepareStatement(this._jdbcSessionIdMgr._insertSession);
                preparedStatement.setString(1, calculateRowId);
                preparedStatement.setString(2, session.getId());
                preparedStatement.setString(3, session.getCanonicalContext());
                preparedStatement.setString(4, session.getVirtualHost());
                preparedStatement.setString(5, getSessionIdManager().getWorkerName());
                preparedStatement.setLong(6, session.getAccessed());
                preparedStatement.setLong(7, session.getLastAccessedTime());
                preparedStatement.setLong(8, session.getCreationTime());
                preparedStatement.setLong(9, session.getCookieSet());
                preparedStatement.setLong(10, currentTimeMillis);
                preparedStatement.setLong(11, session.getExpiryTime());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new ObjectOutputStream(byteArrayOutputStream).writeObject(session.getAttributeMap());
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                preparedStatement.setBinaryStream(12, new ByteArrayInputStream(byteArray), byteArray.length);
                preparedStatement.executeUpdate();
                session.setRowId(calculateRowId);
                session.setLastSaved(currentTimeMillis);
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Stored session " + session, new Object[0]);
                }
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (Exception e) {
                        LOG.warn(e);
                    }
                }
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateSession(Session session) throws Exception {
        if (session != null) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = null;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                connection.setAutoCommit(true);
                preparedStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSession);
                preparedStatement.setString(1, getSessionIdManager().getWorkerName());
                preparedStatement.setLong(2, session.getAccessed());
                preparedStatement.setLong(3, session.getLastAccessedTime());
                preparedStatement.setLong(4, currentTimeMillis);
                preparedStatement.setLong(5, session.getExpiryTime());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new ObjectOutputStream(byteArrayOutputStream).writeObject(session.getAttributeMap());
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                preparedStatement.setBinaryStream(6, new ByteArrayInputStream(byteArray), byteArray.length);
                preparedStatement.setString(7, session.getRowId());
                preparedStatement.executeUpdate();
                session.setLastSaved(currentTimeMillis);
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Updated session " + session, new Object[0]);
                }
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (Exception e) {
                        LOG.warn(e);
                    }
                }
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateSessionNode(Session session) throws Exception {
        String workerName = getSessionIdManager().getWorkerName();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionNode);
            preparedStatement.setString(1, workerName);
            preparedStatement.setString(2, session.getRowId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Updated last node for session id=" + session.getId() + ", lastNode = " + workerName, new Object[0]);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    LOG.warn(e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateSessionAccessTime(Session session) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionAccessTime);
            preparedStatement.setString(1, getSessionIdManager().getWorkerName());
            preparedStatement.setLong(2, session.getAccessed());
            preparedStatement.setLong(3, session.getLastAccessedTime());
            preparedStatement.setLong(4, currentTimeMillis);
            preparedStatement.setLong(5, session.getExpiryTime());
            preparedStatement.setString(6, session.getRowId());
            preparedStatement.executeUpdate();
            session.setLastSaved(currentTimeMillis);
            preparedStatement.close();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Updated access time session id=" + session.getId(), new Object[0]);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    LOG.warn(e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void deleteSession(Session session) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(this._jdbcSessionIdMgr._deleteSession);
            preparedStatement.setString(1, session.getRowId());
            preparedStatement.executeUpdate();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Deleted Session " + session, new Object[0]);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    LOG.warn(e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /* access modifiers changed from: private */
    public Connection getConnection() throws SQLException {
        return ((JDBCSessionIdManager) getSessionIdManager()).getConnection();
    }

    private String calculateRowId(Session session) {
        return (canonicalize(this._context.getContextPath()) + "_" + getVirtualHost(this._context)) + "_" + session.getId();
    }

    /* access modifiers changed from: private */
    public static String getVirtualHost(ContextHandler.Context context) {
        String[] virtualHosts;
        if (context == null || (virtualHosts = context.getContextHandler().getVirtualHosts()) == null || virtualHosts.length == 0 || virtualHosts[0] == null) {
            return StringUtil.ALL_INTERFACES;
        }
        return virtualHosts[0];
    }

    /* access modifiers changed from: private */
    public static String canonicalize(String str) {
        return str == null ? "" : str.replace('/', '_').replace('.', '_').replace('\\', '_');
    }
}
