package org.eclipse.jetty.continuation;

import com.igexin.sdk.GTIntentService;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import org.eclipse.jetty.continuation.ContinuationFilter;

class FauxContinuation implements ContinuationFilter.FilteredContinuation {
    private static final int __COMPLETE = 7;
    private static final int __COMPLETING = 4;
    private static final int __HANDLING = 1;
    private static final int __RESUMING = 3;
    private static final int __SUSPENDED = 5;
    private static final int __SUSPENDING = 2;
    private static final int __UNSUSPENDING = 6;
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private boolean _initial = true;
    private ArrayList<ContinuationListener> _listeners;
    private final ServletRequest _request;
    private ServletResponse _response;
    private boolean _responseWrapped = false;
    private boolean _resumed = false;
    private int _state = 1;
    private boolean _timeout = false;
    private long _timeoutMs = GTIntentService.WAIT_TIME;

    FauxContinuation(ServletRequest servletRequest) {
        this._request = servletRequest;
    }

    public void onComplete() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onComplete(this);
            }
        }
    }

    public void onTimeout() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onTimeout(this);
            }
        }
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

    public boolean isResumed() {
        boolean z;
        synchronized (this) {
            z = this._resumed;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0016, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isSuspended() {
        /*
            r4 = this;
            monitor-enter(r4)
            int r0 = r4._state     // Catch:{ all -> 0x0019 }
            r1 = 0
            r2 = 1
            if (r0 == r2) goto L_0x0017
            r3 = 2
            if (r0 == r3) goto L_0x0015
            r3 = 3
            if (r0 == r3) goto L_0x0015
            r3 = 4
            if (r0 == r3) goto L_0x0015
            r3 = 5
            if (r0 == r3) goto L_0x0015
            monitor-exit(r4)     // Catch:{ all -> 0x0019 }
            return r1
        L_0x0015:
            monitor-exit(r4)     // Catch:{ all -> 0x0019 }
            return r2
        L_0x0017:
            monitor-exit(r4)     // Catch:{ all -> 0x0019 }
            return r1
        L_0x0019:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0019 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.FauxContinuation.isSuspended():boolean");
    }

    public boolean isExpired() {
        boolean z;
        synchronized (this) {
            z = this._timeout;
        }
        return z;
    }

    public void setTimeout(long j) {
        this._timeoutMs = j;
    }

    public void suspend(ServletResponse servletResponse) {
        this._response = servletResponse;
        this._responseWrapped = servletResponse instanceof ServletResponseWrapper;
        suspend();
    }

    public void suspend() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._timeout = false;
                    this._resumed = false;
                    this._state = 2;
                    return;
                case 2:
                case 3:
                    return;
                case 4:
                case 5:
                case 6:
                    throw new IllegalStateException(getStatusString());
                default:
                    throw new IllegalStateException("" + this._state);
            }
        }
    }

    public void resume() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._resumed = true;
                    return;
                case 2:
                    this._resumed = true;
                    this._state = 3;
                    return;
                case 3:
                case 4:
                    return;
                case 5:
                    fauxResume();
                    this._resumed = true;
                    this._state = 6;
                    return;
                case 6:
                    this._resumed = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void complete() {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2._state     // Catch:{ all -> 0x002a }
            r1 = 4
            switch(r0) {
                case 1: goto L_0x0018;
                case 2: goto L_0x0014;
                case 3: goto L_0x0016;
                case 4: goto L_0x0012;
                case 5: goto L_0x000c;
                case 6: goto L_0x000a;
                default: goto L_0x0007;
            }     // Catch:{ all -> 0x002a }
        L_0x0007:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x002a }
            goto L_0x0022
        L_0x000a:
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            return
        L_0x000c:
            r2._state = r1     // Catch:{ all -> 0x002a }
            r2.fauxResume()     // Catch:{ all -> 0x002a }
            goto L_0x0016
        L_0x0012:
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            return
        L_0x0014:
            r2._state = r1     // Catch:{ all -> 0x002a }
        L_0x0016:
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            return
        L_0x0018:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x002a }
            java.lang.String r1 = r2.getStatusString()     // Catch:{ all -> 0x002a }
            r0.<init>(r1)     // Catch:{ all -> 0x002a }
            throw r0     // Catch:{ all -> 0x002a }
        L_0x0022:
            java.lang.String r1 = r2.getStatusString()     // Catch:{ all -> 0x002a }
            r0.<init>(r1)     // Catch:{ all -> 0x002a }
            throw r0     // Catch:{ all -> 0x002a }
        L_0x002a:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.FauxContinuation.complete():void");
    }

    public boolean enter(ServletResponse servletResponse) {
        this._response = servletResponse;
        return true;
    }

    public ServletResponse getServletResponse() {
        return this._response;
    }

    /* access modifiers changed from: package-private */
    public void handling() {
        synchronized (this) {
            this._responseWrapped = false;
            switch (this._state) {
                case 1:
                    throw new IllegalStateException(getStatusString());
                case 2:
                case 3:
                    throw new IllegalStateException(getStatusString());
                case 4:
                    return;
                case 5:
                    fauxResume();
                    break;
                case 6:
                    break;
                default:
                    throw new IllegalStateException("" + this._state);
            }
            this._state = 1;
        }
    }

    public boolean exit() {
        synchronized (this) {
            int i = this._state;
            if (i == 1) {
                this._state = 7;
                onComplete();
                return true;
            } else if (i == 2) {
                this._initial = false;
                this._state = 5;
                fauxSuspend();
                if (this._state != 5) {
                    if (this._state != 4) {
                        this._initial = false;
                        this._state = 1;
                        return false;
                    }
                }
                onComplete();
                return true;
            } else if (i == 3) {
                this._initial = false;
                this._state = 1;
                return false;
            } else if (i == 4) {
                this._initial = false;
                this._state = 7;
                onComplete();
                return true;
            } else {
                throw new IllegalStateException(getStatusString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void expire() {
        synchronized (this) {
            this._timeout = true;
        }
        onTimeout();
        synchronized (this) {
            switch (this._state) {
                case 1:
                    return;
                case 2:
                    this._timeout = true;
                    this._state = 3;
                    fauxResume();
                    return;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    this._timeout = true;
                    this._state = 6;
                    return;
                case 6:
                    this._timeout = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    private void fauxSuspend() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this._timeoutMs;
        long j2 = currentTimeMillis + j;
        while (this._timeoutMs > 0 && j > 0) {
            try {
                wait(j);
                j = j2 - System.currentTimeMillis();
            } catch (InterruptedException unused) {
            }
        }
        if (this._timeoutMs > 0 && j <= 0) {
            expire();
        }
    }

    private void fauxResume() {
        this._timeoutMs = 0;
        notifyAll();
    }

    public String toString() {
        return getStatusString();
    }

    /* access modifiers changed from: package-private */
    public String getStatusString() {
        String str;
        String sb;
        synchronized (this) {
            StringBuilder sb2 = new StringBuilder();
            if (this._state == 1) {
                str = "HANDLING";
            } else if (this._state == 2) {
                str = "SUSPENDING";
            } else if (this._state == 5) {
                str = "SUSPENDED";
            } else if (this._state == 3) {
                str = "RESUMING";
            } else if (this._state == 6) {
                str = "UNSUSPENDING";
            } else if (this._state == 4) {
                str = "COMPLETING";
            } else {
                str = "???" + this._state;
            }
            sb2.append(str);
            sb2.append(this._initial ? ",initial" : "");
            sb2.append(this._resumed ? ",resumed" : "");
            sb2.append(this._timeout ? ",timeout" : "");
            sb = sb2.toString();
        }
        return sb;
    }

    public void addContinuationListener(ContinuationListener continuationListener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList<>();
        }
        this._listeners.add(continuationListener);
    }

    public Object getAttribute(String str) {
        return this._request.getAttribute(str);
    }

    public void removeAttribute(String str) {
        this._request.removeAttribute(str);
    }

    public void setAttribute(String str, Object obj) {
        this._request.setAttribute(str, obj);
    }

    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        } else if (ContinuationFilter.__debug) {
            throw new ContinuationThrowable();
        } else {
            throw __exception;
        }
    }
}
