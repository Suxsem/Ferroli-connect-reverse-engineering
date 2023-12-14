package org.eclipse.jetty.continuation;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import org.eclipse.jetty.continuation.ContinuationFilter;
import org.mortbay.log.Log;
import org.mortbay.log.Logger;
import org.mortbay.util.ajax.Continuation;

public class Jetty6Continuation implements ContinuationFilter.FilteredContinuation {
    private static final Logger LOG = Log.getLogger(Jetty6Continuation.class.getName());
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private volatile boolean _completed = false;
    private volatile boolean _expired = false;
    private boolean _initial = true;
    private final Continuation _j6Continuation;
    private List<ContinuationListener> _listeners;
    private final ServletRequest _request;
    private ServletResponse _response;
    private boolean _responseWrapped = false;
    private volatile boolean _resumed = false;
    private Throwable _retry;
    private int _timeout;

    public Jetty6Continuation(ServletRequest servletRequest, Continuation continuation) {
        if (ContinuationFilter._initialized) {
            this._request = servletRequest;
            this._j6Continuation = continuation;
            return;
        }
        LOG.warn("!ContinuationFilter installed", (Object) null, (Object) null);
        throw new IllegalStateException("!ContinuationFilter installed");
    }

    public void addContinuationListener(ContinuationListener continuationListener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList();
        }
        this._listeners.add(continuationListener);
    }

    public void complete() {
        synchronized (this) {
            if (!this._resumed) {
                this._completed = true;
                if (this._j6Continuation.isPending()) {
                    this._j6Continuation.resume();
                }
            } else {
                throw new IllegalStateException();
            }
        }
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

    public ServletResponse getServletResponse() {
        return this._response;
    }

    public boolean isExpired() {
        return this._expired;
    }

    public boolean isInitial() {
        return this._initial;
    }

    public boolean isResumed() {
        return this._resumed;
    }

    public boolean isSuspended() {
        return this._retry != null;
    }

    public void resume() {
        synchronized (this) {
            if (!this._completed) {
                this._resumed = true;
                if (this._j6Continuation.isPending()) {
                    this._j6Continuation.resume();
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void setTimeout(long j) {
        this._timeout = j > 2147483647L ? Integer.MAX_VALUE : (int) j;
    }

    public void suspend(ServletResponse servletResponse) {
        try {
            this._response = servletResponse;
            this._responseWrapped = this._response instanceof ServletResponseWrapper;
            this._resumed = false;
            this._expired = false;
            this._completed = false;
            this._j6Continuation.suspend((long) this._timeout);
        } catch (Throwable th) {
            this._retry = th;
        }
    }

    public void suspend() {
        try {
            this._response = null;
            this._responseWrapped = false;
            this._resumed = false;
            this._expired = false;
            this._completed = false;
            this._j6Continuation.suspend((long) this._timeout);
        } catch (Throwable th) {
            this._retry = th;
        }
    }

    public boolean isResponseWrapped() {
        return this._responseWrapped;
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

    public boolean enter(ServletResponse servletResponse) {
        List<ContinuationListener> list;
        this._response = servletResponse;
        this._expired = !this._j6Continuation.isResumed();
        if (this._initial) {
            return true;
        }
        this._j6Continuation.reset();
        if (this._expired && (list = this._listeners) != null) {
            for (ContinuationListener onTimeout : list) {
                onTimeout.onTimeout(this);
            }
        }
        return !this._completed;
    }

    public boolean exit() {
        this._initial = false;
        Throwable th = this._retry;
        this._retry = null;
        if (th instanceof Error) {
            throw ((Error) th);
        } else if (!(th instanceof RuntimeException)) {
            List<ContinuationListener> list = this._listeners;
            if (list == null) {
                return true;
            }
            for (ContinuationListener onComplete : list) {
                onComplete.onComplete(this);
            }
            return true;
        } else {
            throw ((RuntimeException) th);
        }
    }
}
