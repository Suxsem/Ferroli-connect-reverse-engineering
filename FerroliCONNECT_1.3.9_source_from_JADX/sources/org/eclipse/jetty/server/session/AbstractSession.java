package org.eclipse.jetty.server.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.log.Logger;

public abstract class AbstractSession implements AbstractSessionManager.SessionIf {
    static final Logger LOG = SessionHandler.LOG;
    private long _accessed;
    private final Map<String, Object> _attributes = new HashMap();
    private final String _clusterId;
    private long _cookieSet;
    private final long _created;
    private boolean _doInvalidate;
    private boolean _idChanged;
    private boolean _invalid;
    private long _lastAccessed;
    private final AbstractSessionManager _manager;
    private long _maxIdleMs;
    private boolean _newSession;
    private final String _nodeId;
    private int _requests;

    public AbstractSession getSession() {
        return this;
    }

    protected AbstractSession(AbstractSessionManager abstractSessionManager, HttpServletRequest httpServletRequest) {
        this._manager = abstractSessionManager;
        this._newSession = true;
        this._created = System.currentTimeMillis();
        this._clusterId = this._manager._sessionIdManager.newSessionId(httpServletRequest, this._created);
        this._nodeId = this._manager._sessionIdManager.getNodeId(this._clusterId, httpServletRequest);
        long j = this._created;
        this._accessed = j;
        this._lastAccessed = j;
        this._requests = 1;
        this._maxIdleMs = this._manager._dftMaxIdleSecs > 0 ? ((long) this._manager._dftMaxIdleSecs) * 1000 : -1;
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("new session & id " + this._nodeId + " " + this._clusterId, new Object[0]);
        }
    }

    protected AbstractSession(AbstractSessionManager abstractSessionManager, long j, long j2, String str) {
        this._manager = abstractSessionManager;
        this._created = j;
        this._clusterId = str;
        this._nodeId = this._manager._sessionIdManager.getNodeId(this._clusterId, (HttpServletRequest) null);
        this._accessed = j2;
        this._lastAccessed = j2;
        this._requests = 1;
        this._maxIdleMs = this._manager._dftMaxIdleSecs > 0 ? ((long) this._manager._dftMaxIdleSecs) * 1000 : -1;
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("new session " + this._nodeId + " " + this._clusterId, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void checkValid() throws IllegalStateException {
        if (this._invalid) {
            throw new IllegalStateException();
        }
    }

    public long getAccessed() {
        long j;
        synchronized (this) {
            j = this._accessed;
        }
        return j;
    }

    public Object getAttribute(String str) {
        Object obj;
        synchronized (this) {
            checkValid();
            obj = this._attributes.get(str);
        }
        return obj;
    }

    public int getAttributes() {
        int size;
        synchronized (this) {
            checkValid();
            size = this._attributes.size();
        }
        return size;
    }

    public Enumeration<String> getAttributeNames() {
        Enumeration<String> enumeration;
        synchronized (this) {
            checkValid();
            enumeration = Collections.enumeration(this._attributes == null ? Collections.EMPTY_LIST : new ArrayList(this._attributes.keySet()));
        }
        return enumeration;
    }

    public Set<String> getNames() {
        HashSet hashSet;
        synchronized (this) {
            hashSet = new HashSet(this._attributes.keySet());
        }
        return hashSet;
    }

    public long getCookieSetTime() {
        return this._cookieSet;
    }

    public long getCreationTime() throws IllegalStateException {
        return this._created;
    }

    public String getId() throws IllegalStateException {
        return this._manager._nodeIdInSessionId ? this._nodeId : this._clusterId;
    }

    public String getNodeId() {
        return this._nodeId;
    }

    public String getClusterId() {
        return this._clusterId;
    }

    public long getLastAccessedTime() throws IllegalStateException {
        checkValid();
        return this._lastAccessed;
    }

    public void setLastAccessedTime(long j) {
        this._lastAccessed = j;
    }

    public int getMaxInactiveInterval() {
        checkValid();
        return (int) (this._maxIdleMs / 1000);
    }

    public ServletContext getServletContext() {
        return this._manager._context;
    }

    @Deprecated
    public HttpSessionContext getSessionContext() throws IllegalStateException {
        checkValid();
        return AbstractSessionManager.__nullSessionContext;
    }

    @Deprecated
    public Object getValue(String str) throws IllegalStateException {
        return getAttribute(str);
    }

    @Deprecated
    public String[] getValueNames() throws IllegalStateException {
        synchronized (this) {
            checkValid();
            if (this._attributes == null) {
                String[] strArr = new String[0];
                return strArr;
            }
            String[] strArr2 = (String[]) this._attributes.keySet().toArray(new String[this._attributes.size()]);
            return strArr2;
        }
    }

    /* access modifiers changed from: protected */
    public Map<String, Object> getAttributeMap() {
        return this._attributes;
    }

    /* access modifiers changed from: protected */
    public void addAttributes(Map<String, Object> map) {
        this._attributes.putAll(map);
    }

    /* access modifiers changed from: protected */
    public boolean access(long j) {
        synchronized (this) {
            if (this._invalid) {
                return false;
            }
            this._newSession = false;
            this._lastAccessed = this._accessed;
            this._accessed = j;
            if (this._maxIdleMs <= 0 || this._lastAccessed <= 0 || this._lastAccessed + this._maxIdleMs >= j) {
                this._requests++;
                return true;
            }
            invalidate();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void complete() {
        synchronized (this) {
            this._requests--;
            if (this._doInvalidate && this._requests <= 0) {
                doInvalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void timeout() throws IllegalStateException {
        boolean z = true;
        this._manager.removeSession(this, true);
        synchronized (this) {
            if (!this._invalid) {
                if (this._requests > 0) {
                    this._doInvalidate = true;
                }
            }
            z = false;
        }
        if (z) {
            doInvalidate();
        }
    }

    public void invalidate() throws IllegalStateException {
        this._manager.removeSession(this, true);
        doInvalidate();
    }

    /* access modifiers changed from: protected */
    public void doInvalidate() throws IllegalStateException {
        try {
            LOG.debug("invalidate {}", this._clusterId);
            if (isValid()) {
                clearAttributes();
            }
            synchronized (this) {
                this._invalid = true;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this._invalid = true;
                throw th;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003f, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0041, code lost:
        r0.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clearAttributes() {
        /*
            r5 = this;
        L_0x0000:
            java.util.Map<java.lang.String, java.lang.Object> r0 = r5._attributes
            if (r0 == 0) goto L_0x003d
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x003d
            monitor-enter(r5)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x003a }
            java.util.Map<java.lang.String, java.lang.Object> r1 = r5._attributes     // Catch:{ all -> 0x003a }
            java.util.Set r1 = r1.keySet()     // Catch:{ all -> 0x003a }
            r0.<init>(r1)     // Catch:{ all -> 0x003a }
            monitor-exit(r5)     // Catch:{ all -> 0x003a }
            java.util.Iterator r0 = r0.iterator()
        L_0x001b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0000
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            monitor-enter(r5)
            r2 = 0
            java.lang.Object r3 = r5.doPutOrRemove(r1, r2)     // Catch:{ all -> 0x0037 }
            monitor-exit(r5)     // Catch:{ all -> 0x0037 }
            r5.unbindValue(r1, r3)
            org.eclipse.jetty.server.session.AbstractSessionManager r4 = r5._manager
            r4.doSessionAttributeListeners(r5, r1, r3, r2)
            goto L_0x001b
        L_0x0037:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0037 }
            throw r0
        L_0x003a:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x003a }
            throw r0
        L_0x003d:
            java.util.Map<java.lang.String, java.lang.Object> r0 = r5._attributes
            if (r0 == 0) goto L_0x0044
            r0.clear()
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.AbstractSession.clearAttributes():void");
    }

    public boolean isIdChanged() {
        return this._idChanged;
    }

    public boolean isNew() throws IllegalStateException {
        checkValid();
        return this._newSession;
    }

    @Deprecated
    public void putValue(String str, Object obj) throws IllegalStateException {
        setAttribute(str, obj);
    }

    public void removeAttribute(String str) {
        setAttribute(str, (Object) null);
    }

    @Deprecated
    public void removeValue(String str) throws IllegalStateException {
        removeAttribute(str);
    }

    /* access modifiers changed from: protected */
    public Object doPutOrRemove(String str, Object obj) {
        return obj == null ? this._attributes.remove(str) : this._attributes.put(str, obj);
    }

    /* access modifiers changed from: protected */
    public Object doGet(String str) {
        return this._attributes.get(str);
    }

    public void setAttribute(String str, Object obj) {
        Object doPutOrRemove;
        synchronized (this) {
            checkValid();
            doPutOrRemove = doPutOrRemove(str, obj);
        }
        if (obj == null || !obj.equals(doPutOrRemove)) {
            if (doPutOrRemove != null) {
                unbindValue(str, doPutOrRemove);
            }
            if (obj != null) {
                bindValue(str, obj);
            }
            this._manager.doSessionAttributeListeners(this, str, doPutOrRemove, obj);
        }
    }

    public void setIdChanged(boolean z) {
        this._idChanged = z;
    }

    public void setMaxInactiveInterval(int i) {
        this._maxIdleMs = ((long) i) * 1000;
    }

    public String toString() {
        return getClass().getName() + ":" + getId() + "@" + hashCode();
    }

    public void bindValue(String str, Object obj) {
        if (obj != null && (obj instanceof HttpSessionBindingListener)) {
            ((HttpSessionBindingListener) obj).valueBound(new HttpSessionBindingEvent(this, str));
        }
    }

    public boolean isValid() {
        return !this._invalid;
    }

    /* access modifiers changed from: protected */
    public void cookieSet() {
        synchronized (this) {
            this._cookieSet = this._accessed;
        }
    }

    public int getRequests() {
        int i;
        synchronized (this) {
            i = this._requests;
        }
        return i;
    }

    public void setRequests(int i) {
        synchronized (this) {
            this._requests = i;
        }
    }

    public void unbindValue(String str, Object obj) {
        if (obj != null && (obj instanceof HttpSessionBindingListener)) {
            ((HttpSessionBindingListener) obj).valueUnbound(new HttpSessionBindingEvent(this, str));
        }
    }

    public void willPassivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object next : this._attributes.values()) {
                if (next instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) next).sessionWillPassivate(httpSessionEvent);
                }
            }
        }
    }

    public void didActivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object next : this._attributes.values()) {
                if (next instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) next).sessionDidActivate(httpSessionEvent);
                }
            }
        }
    }
}
