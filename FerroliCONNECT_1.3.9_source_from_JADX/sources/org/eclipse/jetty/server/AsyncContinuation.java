package org.eclipse.jetty.server;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

public class AsyncContinuation implements AsyncContext, Continuation {
    private static final long DEFAULT_TIMEOUT = 30000;
    private static final Logger LOG = Log.getLogger((Class<?>) AsyncContinuation.class);
    private static final int __ASYNCSTARTED = 2;
    private static final int __ASYNCWAIT = 4;
    private static final int __COMPLETED = 9;
    private static final int __COMPLETING = 7;
    private static final int __DISPATCHED = 1;
    private static final int __IDLE = 0;
    private static final int __REDISPATCH = 5;
    private static final int __REDISPATCHED = 6;
    private static final int __REDISPATCHING = 3;
    private static final int __UNCOMPLETED = 8;
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private List<AsyncListener> _asyncListeners;
    protected AbstractHttpConnection _connection;
    private volatile boolean _continuation;
    private List<ContinuationListener> _continuationListeners;
    private AsyncEventState _event;
    private volatile long _expireAt;
    private boolean _expired;
    private boolean _initial = true;
    private List<AsyncListener> _lastAsyncListeners;
    private volatile boolean _responseWrapped;
    private boolean _resumed;
    private int _state = 0;
    private long _timeoutMs = 30000;

    protected AsyncContinuation() {
    }

    /* access modifiers changed from: protected */
    public void setConnection(AbstractHttpConnection abstractHttpConnection) {
        synchronized (this) {
            this._connection = abstractHttpConnection;
        }
    }

    public void addListener(AsyncListener asyncListener) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    public void addListener(AsyncListener asyncListener, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    public void addContinuationListener(ContinuationListener continuationListener) {
        synchronized (this) {
            if (this._continuationListeners == null) {
                this._continuationListeners = new ArrayList();
            }
            this._continuationListeners.add(continuationListener);
        }
    }

    public void setTimeout(long j) {
        synchronized (this) {
            this._timeoutMs = j;
        }
    }

    public long getTimeout() {
        long j;
        synchronized (this) {
            j = this._timeoutMs;
        }
        return j;
    }

    public AsyncEventState getAsyncEventState() {
        AsyncEventState asyncEventState;
        synchronized (this) {
            asyncEventState = this._event;
        }
        return asyncEventState;
    }

    public boolean isResponseWrapped() {
        return this._responseWrapped;
    }

    public boolean isInitial() {
        boolean z;
        synchronized (this) {
            z = this._initial;
        }
        return z;
    }

    public boolean isContinuation() {
        return this._continuation;
    }

    public boolean isSuspended() {
        synchronized (this) {
            int i = this._state;
            if (i == 2 || i == 3 || i == 4 || i == 7) {
                return true;
            }
            return false;
        }
    }

    public boolean isSuspending() {
        synchronized (this) {
            int i = this._state;
            if (i == 2 || i == 4) {
                return true;
            }
            return false;
        }
    }

    public boolean isDispatchable() {
        synchronized (this) {
            int i = this._state;
            if (i == 3 || i == 5 || i == 6 || i == 7) {
                return true;
            }
            return false;
        }
    }

    public String toString() {
        String str;
        synchronized (this) {
            str = super.toString() + "@" + getStatusString();
        }
        return str;
    }

    public String getStatusString() {
        String str;
        String sb;
        synchronized (this) {
            StringBuilder sb2 = new StringBuilder();
            if (this._state == 0) {
                str = "IDLE";
            } else if (this._state == 1) {
                str = "DISPATCHED";
            } else if (this._state == 2) {
                str = "ASYNCSTARTED";
            } else if (this._state == 4) {
                str = "ASYNCWAIT";
            } else if (this._state == 3) {
                str = "REDISPATCHING";
            } else if (this._state == 5) {
                str = "REDISPATCH";
            } else if (this._state == 6) {
                str = "REDISPATCHED";
            } else if (this._state == 7) {
                str = "COMPLETING";
            } else if (this._state == 8) {
                str = "UNCOMPLETED";
            } else if (this._state == 9) {
                str = "COMPLETE";
            } else {
                str = "UNKNOWN?" + this._state;
            }
            sb2.append(str);
            sb2.append(this._initial ? ",initial" : "");
            sb2.append(this._resumed ? ",resumed" : "");
            sb2.append(this._expired ? ",expired" : "");
            sb = sb2.toString();
        }
        return sb;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0048, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handling() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            r4._continuation = r0     // Catch:{ all -> 0x0049 }
            int r1 = r4._state     // Catch:{ all -> 0x0049 }
            r2 = 1
            if (r1 == 0) goto L_0x0029
            r3 = 7
            if (r1 == r3) goto L_0x0023
            r3 = 4
            if (r1 == r3) goto L_0x0021
            r0 = 5
            if (r1 != r0) goto L_0x0017
            r0 = 6
            r4._state = r0     // Catch:{ all -> 0x0049 }
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            return r2
        L_0x0017:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0049 }
            java.lang.String r1 = r4.getStatusString()     // Catch:{ all -> 0x0049 }
            r0.<init>(r1)     // Catch:{ all -> 0x0049 }
            throw r0     // Catch:{ all -> 0x0049 }
        L_0x0021:
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            return r0
        L_0x0023:
            r1 = 8
            r4._state = r1     // Catch:{ all -> 0x0049 }
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            return r0
        L_0x0029:
            r4._initial = r2     // Catch:{ all -> 0x0049 }
            r4._state = r2     // Catch:{ all -> 0x0049 }
            java.util.List<javax.servlet.AsyncListener> r0 = r4._lastAsyncListeners     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0036
            java.util.List<javax.servlet.AsyncListener> r0 = r4._lastAsyncListeners     // Catch:{ all -> 0x0049 }
            r0.clear()     // Catch:{ all -> 0x0049 }
        L_0x0036:
            java.util.List<javax.servlet.AsyncListener> r0 = r4._asyncListeners     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0040
            java.util.List<javax.servlet.AsyncListener> r0 = r4._asyncListeners     // Catch:{ all -> 0x0049 }
            r0.clear()     // Catch:{ all -> 0x0049 }
            goto L_0x0047
        L_0x0040:
            java.util.List<javax.servlet.AsyncListener> r0 = r4._lastAsyncListeners     // Catch:{ all -> 0x0049 }
            r4._asyncListeners = r0     // Catch:{ all -> 0x0049 }
            r0 = 0
            r4._lastAsyncListeners = r0     // Catch:{ all -> 0x0049 }
        L_0x0047:
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            return r2
        L_0x0049:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0049 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.handling():boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doSuspend(javax.servlet.ServletContext r3, javax.servlet.ServletRequest r4, javax.servlet.ServletResponse r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2._state     // Catch:{ all -> 0x0080 }
            r1 = 1
            if (r0 == r1) goto L_0x0014
            r1 = 6
            if (r0 != r1) goto L_0x000a
            goto L_0x0014
        L_0x000a:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0080 }
            java.lang.String r4 = r2.getStatusString()     // Catch:{ all -> 0x0080 }
            r3.<init>(r4)     // Catch:{ all -> 0x0080 }
            throw r3     // Catch:{ all -> 0x0080 }
        L_0x0014:
            r0 = 0
            r2._resumed = r0     // Catch:{ all -> 0x0080 }
            r2._expired = r0     // Catch:{ all -> 0x0080 }
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch:{ all -> 0x0080 }
            if (r0 == 0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch:{ all -> 0x0080 }
            javax.servlet.ServletRequest r0 = r0.getSuppliedRequest()     // Catch:{ all -> 0x0080 }
            if (r4 != r0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch:{ all -> 0x0080 }
            javax.servlet.ServletResponse r0 = r0.getSuppliedResponse()     // Catch:{ all -> 0x0080 }
            if (r5 != r0) goto L_0x0042
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = r2._event     // Catch:{ all -> 0x0080 }
            javax.servlet.ServletContext r0 = r0.getServletContext()     // Catch:{ all -> 0x0080 }
            if (r3 == r0) goto L_0x0036
            goto L_0x0042
        L_0x0036:
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r3 = r2._event     // Catch:{ all -> 0x0080 }
            r4 = 0
            javax.servlet.ServletContext unused = r3._dispatchContext = r4     // Catch:{ all -> 0x0080 }
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r3 = r2._event     // Catch:{ all -> 0x0080 }
            java.lang.String unused = r3._pathInContext = r4     // Catch:{ all -> 0x0080 }
            goto L_0x0049
        L_0x0042:
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r0 = new org.eclipse.jetty.server.AsyncContinuation$AsyncEventState     // Catch:{ all -> 0x0080 }
            r0.<init>(r3, r4, r5)     // Catch:{ all -> 0x0080 }
            r2._event = r0     // Catch:{ all -> 0x0080 }
        L_0x0049:
            r3 = 2
            r2._state = r3     // Catch:{ all -> 0x0080 }
            java.util.List<javax.servlet.AsyncListener> r3 = r2._lastAsyncListeners     // Catch:{ all -> 0x0080 }
            java.util.List<javax.servlet.AsyncListener> r4 = r2._asyncListeners     // Catch:{ all -> 0x0080 }
            r2._lastAsyncListeners = r4     // Catch:{ all -> 0x0080 }
            r2._asyncListeners = r3     // Catch:{ all -> 0x0080 }
            java.util.List<javax.servlet.AsyncListener> r3 = r2._asyncListeners     // Catch:{ all -> 0x0080 }
            if (r3 == 0) goto L_0x005d
            java.util.List<javax.servlet.AsyncListener> r3 = r2._asyncListeners     // Catch:{ all -> 0x0080 }
            r3.clear()     // Catch:{ all -> 0x0080 }
        L_0x005d:
            monitor-exit(r2)     // Catch:{ all -> 0x0080 }
            java.util.List<javax.servlet.AsyncListener> r3 = r2._lastAsyncListeners
            if (r3 == 0) goto L_0x007f
            java.util.Iterator r3 = r3.iterator()
        L_0x0066:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x007f
            java.lang.Object r4 = r3.next()
            javax.servlet.AsyncListener r4 = (javax.servlet.AsyncListener) r4
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r5 = r2._event     // Catch:{ Exception -> 0x0078 }
            r4.onStartAsync(r5)     // Catch:{ Exception -> 0x0078 }
            goto L_0x0066
        L_0x0078:
            r4 = move-exception
            org.eclipse.jetty.util.log.Logger r5 = LOG
            r5.warn(r4)
            goto L_0x0066
        L_0x007f:
            return
        L_0x0080:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0080 }
            goto L_0x0084
        L_0x0083:
            throw r3
        L_0x0084:
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.doSuspend(javax.servlet.ServletContext, javax.servlet.ServletRequest, javax.servlet.ServletResponse):void");
    }

    /* access modifiers changed from: protected */
    public boolean unhandle() {
        synchronized (this) {
            int i = this._state;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        this._initial = false;
                        this._state = 4;
                        scheduleTimeout();
                        if (this._state == 4) {
                            return true;
                        }
                        if (this._state == 7) {
                            this._state = 8;
                            return true;
                        }
                        this._initial = false;
                        this._state = 6;
                        return false;
                    } else if (i == 3) {
                        this._initial = false;
                        this._state = 6;
                        return false;
                    } else if (i != 6) {
                        if (i == 7) {
                            this._initial = false;
                            this._state = 8;
                            return true;
                        }
                        throw new IllegalStateException(getStatusString());
                    }
                }
                this._state = 8;
                return true;
            }
            throw new IllegalStateException(getStatusString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        cancelTimeout();
        scheduleDispatch();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatch() {
        /*
            r4 = this;
            monitor-enter(r4)
            int r0 = r4._state     // Catch:{ all -> 0x0035 }
            r1 = 2
            r2 = 1
            if (r0 == r1) goto L_0x002e
            r1 = 4
            r3 = 5
            if (r0 == r1) goto L_0x0019
            if (r0 != r3) goto L_0x000f
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            return
        L_0x000f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0035 }
            java.lang.String r1 = r4.getStatusString()     // Catch:{ all -> 0x0035 }
            r0.<init>(r1)     // Catch:{ all -> 0x0035 }
            throw r0     // Catch:{ all -> 0x0035 }
        L_0x0019:
            boolean r0 = r4._expired     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x001f
            r0 = 1
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            r4._state = r3     // Catch:{ all -> 0x0035 }
            r4._resumed = r2     // Catch:{ all -> 0x0035 }
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x002d
            r4.cancelTimeout()
            r4.scheduleDispatch()
        L_0x002d:
            return
        L_0x002e:
            r0 = 3
            r4._state = r0     // Catch:{ all -> 0x0035 }
            r4._resumed = r2     // Catch:{ all -> 0x0035 }
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            return
        L_0x0035:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.dispatch():void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        r3 = r3.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        if (r3.hasNext() == false) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r3.next().onTimeout(r6._event);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        LOG.debug(r3);
        r6._connection.getRequest().setAttribute(javax.servlet.RequestDispatcher.ERROR_EXCEPTION, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        if (r3 == null) goto L_0x003c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void expired() {
        /*
            r6 = this;
            monitor-enter(r6)
            int r0 = r6._state     // Catch:{ all -> 0x0073 }
            r1 = 4
            r2 = 2
            if (r0 == r2) goto L_0x000b
            if (r0 == r1) goto L_0x000b
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            return
        L_0x000b:
            java.util.List<org.eclipse.jetty.continuation.ContinuationListener> r0 = r6._continuationListeners     // Catch:{ all -> 0x0073 }
            java.util.List<javax.servlet.AsyncListener> r3 = r6._asyncListeners     // Catch:{ all -> 0x0073 }
            r4 = 1
            r6._expired = r4     // Catch:{ all -> 0x0073 }
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            if (r3 == 0) goto L_0x003c
            java.util.Iterator r3 = r3.iterator()
        L_0x0019:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003c
            java.lang.Object r4 = r3.next()
            javax.servlet.AsyncListener r4 = (javax.servlet.AsyncListener) r4
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r5 = r6._event     // Catch:{ Exception -> 0x002b }
            r4.onTimeout(r5)     // Catch:{ Exception -> 0x002b }
            goto L_0x0019
        L_0x002b:
            r3 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG
            r4.debug(r3)
            org.eclipse.jetty.server.AbstractHttpConnection r4 = r6._connection
            org.eclipse.jetty.server.Request r4 = r4.getRequest()
            java.lang.String r5 = "javax.servlet.error.exception"
            r4.setAttribute(r5, r3)
        L_0x003c:
            if (r0 == 0) goto L_0x0059
            java.util.Iterator r0 = r0.iterator()
        L_0x0042:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0059
            java.lang.Object r3 = r0.next()
            org.eclipse.jetty.continuation.ContinuationListener r3 = (org.eclipse.jetty.continuation.ContinuationListener) r3
            r3.onTimeout(r6)     // Catch:{ Exception -> 0x0052 }
            goto L_0x0042
        L_0x0052:
            r3 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG
            r4.warn(r3)
            goto L_0x0042
        L_0x0059:
            monitor-enter(r6)
            int r0 = r6._state     // Catch:{ all -> 0x0070 }
            if (r0 == r2) goto L_0x0068
            if (r0 == r1) goto L_0x0068
            boolean r0 = r6._continuation     // Catch:{ all -> 0x0070 }
            if (r0 != 0) goto L_0x006b
            r0 = 0
            r6._expired = r0     // Catch:{ all -> 0x0070 }
            goto L_0x006b
        L_0x0068:
            r6.dispatch()     // Catch:{ all -> 0x0070 }
        L_0x006b:
            monitor-exit(r6)     // Catch:{ all -> 0x0070 }
            r6.scheduleDispatch()
            return
        L_0x0070:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0070 }
            throw r0
        L_0x0073:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            goto L_0x0077
        L_0x0076:
            throw r0
        L_0x0077:
            goto L_0x0076
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.expired():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        cancelTimeout();
        scheduleDispatch();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void complete() {
        /*
            r4 = this;
            monitor-enter(r4)
            int r0 = r4._state     // Catch:{ all -> 0x0037 }
            r1 = 1
            if (r0 == r1) goto L_0x002d
            r2 = 2
            r3 = 7
            if (r0 == r2) goto L_0x0029
            r2 = 4
            if (r0 == r2) goto L_0x001a
            r1 = 6
            if (r0 == r1) goto L_0x002d
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0037 }
            java.lang.String r1 = r4.getStatusString()     // Catch:{ all -> 0x0037 }
            r0.<init>(r1)     // Catch:{ all -> 0x0037 }
            throw r0     // Catch:{ all -> 0x0037 }
        L_0x001a:
            r4._state = r3     // Catch:{ all -> 0x0037 }
            boolean r0 = r4._expired     // Catch:{ all -> 0x0037 }
            r0 = r0 ^ r1
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            if (r0 == 0) goto L_0x0028
            r4.cancelTimeout()
            r4.scheduleDispatch()
        L_0x0028:
            return
        L_0x0029:
            r4._state = r3     // Catch:{ all -> 0x0037 }
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            return
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0037 }
            java.lang.String r1 = r4.getStatusString()     // Catch:{ all -> 0x0037 }
            r0.<init>(r1)     // Catch:{ all -> 0x0037 }
            throw r0     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncContinuation.complete():void");
    }

    public void errorComplete() {
        synchronized (this) {
            int i = this._state;
            if (i == 2 || i == 3) {
                this._state = 7;
                this._resumed = false;
            } else if (i != 7) {
                throw new IllegalStateException(getStatusString());
            }
        }
    }

    public <T extends AsyncListener> T createListener(Class<T> cls) throws ServletException {
        try {
            return (AsyncListener) cls.newInstance();
        } catch (Exception e) {
            throw new ServletException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void doComplete(Throwable th) {
        List<ContinuationListener> list;
        List<AsyncListener> list2;
        synchronized (this) {
            if (this._state == 8) {
                this._state = 9;
                list = this._continuationListeners;
                list2 = this._asyncListeners;
            } else {
                throw new IllegalStateException(getStatusString());
            }
        }
        if (list2 != null) {
            for (AsyncListener next : list2) {
                if (th != null) {
                    try {
                        this._event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_EXCEPTION, th);
                        this._event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_MESSAGE, th.getMessage());
                        next.onError(this._event);
                    } catch (Exception e) {
                        LOG.warn(e);
                    }
                } else {
                    next.onComplete(this._event);
                }
            }
        }
        if (list != null) {
            for (ContinuationListener onComplete : list) {
                try {
                    onComplete.onComplete(this);
                } catch (Exception e2) {
                    LOG.warn(e2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void recycle() {
        synchronized (this) {
            int i = this._state;
            if (i == 1 || i == 6) {
                throw new IllegalStateException(getStatusString());
            }
            this._state = 0;
            this._initial = true;
            this._resumed = false;
            this._expired = false;
            this._responseWrapped = false;
            cancelTimeout();
            this._timeoutMs = 30000;
            this._continuationListeners = null;
        }
    }

    public void cancel() {
        synchronized (this) {
            cancelTimeout();
            this._continuationListeners = null;
        }
    }

    /* access modifiers changed from: protected */
    public void scheduleDispatch() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (!endPoint.isBlocking()) {
            ((AsyncEndPoint) endPoint).asyncDispatch();
        }
    }

    /* access modifiers changed from: protected */
    public void scheduleTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (this._timeoutMs <= 0) {
            return;
        }
        if (endPoint.isBlocking()) {
            synchronized (this) {
                this._expireAt = System.currentTimeMillis() + this._timeoutMs;
                long j = this._timeoutMs;
                while (this._expireAt > 0 && j > 0 && this._connection.getServer().isRunning()) {
                    try {
                        wait(j);
                    } catch (InterruptedException e) {
                        LOG.ignore(e);
                    }
                    j = this._expireAt - System.currentTimeMillis();
                }
                if (this._expireAt > 0 && j <= 0 && this._connection.getServer().isRunning()) {
                    expired();
                }
            }
            return;
        }
        ((AsyncEndPoint) endPoint).scheduleTimeout(this._event._timeout, this._timeoutMs);
    }

    /* access modifiers changed from: protected */
    public void cancelTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (endPoint.isBlocking()) {
            synchronized (this) {
                this._expireAt = 0;
                notifyAll();
            }
            return;
        }
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            ((AsyncEndPoint) endPoint).cancelTimeout(asyncEventState._timeout);
        }
    }

    public boolean isCompleting() {
        boolean z;
        synchronized (this) {
            z = this._state == 7;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public boolean isUncompleted() {
        boolean z;
        synchronized (this) {
            z = this._state == 8;
        }
        return z;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this) {
            z = this._state == 9;
        }
        return z;
    }

    public boolean isAsyncStarted() {
        synchronized (this) {
            int i = this._state;
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                return true;
            }
            return false;
        }
    }

    public boolean isAsync() {
        synchronized (this) {
            int i = this._state;
            if (i == 0 || i == 1 || i == 8 || i == 9) {
                return false;
            }
            return true;
        }
    }

    public void dispatch(ServletContext servletContext, String str) {
        ServletContext unused = this._event._dispatchContext = servletContext;
        this._event.setPath(str);
        dispatch();
    }

    public void dispatch(String str) {
        this._event.setPath(str);
        dispatch();
    }

    public Request getBaseRequest() {
        return this._connection.getRequest();
    }

    public ServletRequest getRequest() {
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            return asyncEventState.getSuppliedRequest();
        }
        return this._connection.getRequest();
    }

    public ServletResponse getResponse() {
        AsyncEventState asyncEventState;
        if (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) {
            return this._connection.getResponse();
        }
        return this._event.getSuppliedResponse();
    }

    public void start(final Runnable runnable) {
        final AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            this._connection.getServer().getThreadPool().dispatch(new Runnable() {
                public void run() {
                    ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler().handle(runnable);
                }
            });
        }
    }

    public boolean hasOriginalRequestAndResponse() {
        boolean z;
        synchronized (this) {
            z = this._event != null && this._event.getSuppliedRequest() == this._connection._request && this._event.getSuppliedResponse() == this._connection._response;
        }
        return z;
    }

    public ContextHandler getContextHandler() {
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            return ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler();
        }
        return null;
    }

    public boolean isResumed() {
        boolean z;
        synchronized (this) {
            z = this._resumed;
        }
        return z;
    }

    public boolean isExpired() {
        boolean z;
        synchronized (this) {
            z = this._expired;
        }
        return z;
    }

    public void resume() {
        dispatch();
    }

    /* access modifiers changed from: protected */
    public void startAsync(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            this._responseWrapped = !(servletResponse instanceof Response);
            doSuspend(servletContext, servletRequest, servletResponse);
            if (servletRequest instanceof HttpServletRequest) {
                String unused = this._event._pathInContext = URIUtil.addPaths(((HttpServletRequest) servletRequest).getServletPath(), ((HttpServletRequest) servletRequest).getPathInfo());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void startAsync() {
        this._responseWrapped = false;
        this._continuation = false;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), this._connection.getResponse());
    }

    public void suspend(ServletResponse servletResponse) {
        boolean z = true;
        this._continuation = true;
        if (servletResponse instanceof Response) {
            z = false;
        }
        this._responseWrapped = z;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), servletResponse);
    }

    public void suspend() {
        this._responseWrapped = false;
        this._continuation = true;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), this._connection.getResponse());
    }

    public ServletResponse getServletResponse() {
        AsyncEventState asyncEventState;
        if (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) {
            return this._connection.getResponse();
        }
        return this._event.getSuppliedResponse();
    }

    public Object getAttribute(String str) {
        return this._connection.getRequest().getAttribute(str);
    }

    public void removeAttribute(String str) {
        this._connection.getRequest().removeAttribute(str);
    }

    public void setAttribute(String str, Object obj) {
        this._connection.getRequest().setAttribute(str, obj);
    }

    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        } else if (LOG.isDebugEnabled()) {
            throw new ContinuationThrowable();
        } else {
            throw __exception;
        }
    }

    public class AsyncTimeout extends Timeout.Task implements Runnable {
        public AsyncTimeout() {
        }

        public void expired() {
            AsyncContinuation.this.expired();
        }

        public void run() {
            AsyncContinuation.this.expired();
        }
    }

    public class AsyncEventState extends AsyncEvent {
        /* access modifiers changed from: private */
        public ServletContext _dispatchContext;
        /* access modifiers changed from: private */
        public String _pathInContext;
        private final ServletContext _suspendedContext;
        /* access modifiers changed from: private */
        public Timeout.Task _timeout = new AsyncTimeout();

        public AsyncEventState(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
            super(AsyncContinuation.this, servletRequest, servletResponse);
            this._suspendedContext = servletContext;
            Request request = AsyncContinuation.this._connection.getRequest();
            if (request.getAttribute(AsyncContext.ASYNC_REQUEST_URI) == null) {
                String str = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
                if (str != null) {
                    request.setAttribute(AsyncContext.ASYNC_REQUEST_URI, str);
                    request.setAttribute(AsyncContext.ASYNC_CONTEXT_PATH, request.getAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH));
                    request.setAttribute(AsyncContext.ASYNC_SERVLET_PATH, request.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH));
                    request.setAttribute(AsyncContext.ASYNC_PATH_INFO, request.getAttribute(RequestDispatcher.FORWARD_PATH_INFO));
                    request.setAttribute(AsyncContext.ASYNC_QUERY_STRING, request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING));
                    return;
                }
                request.setAttribute(AsyncContext.ASYNC_REQUEST_URI, request.getRequestURI());
                request.setAttribute(AsyncContext.ASYNC_CONTEXT_PATH, request.getContextPath());
                request.setAttribute(AsyncContext.ASYNC_SERVLET_PATH, request.getServletPath());
                request.setAttribute(AsyncContext.ASYNC_PATH_INFO, request.getPathInfo());
                request.setAttribute(AsyncContext.ASYNC_QUERY_STRING, request.getQueryString());
            }
        }

        public ServletContext getSuspendedContext() {
            return this._suspendedContext;
        }

        public ServletContext getDispatchContext() {
            return this._dispatchContext;
        }

        public ServletContext getServletContext() {
            ServletContext servletContext = this._dispatchContext;
            return servletContext == null ? this._suspendedContext : servletContext;
        }

        public void setPath(String str) {
            this._pathInContext = str;
        }

        public String getPath() {
            return this._pathInContext;
        }
    }
}
