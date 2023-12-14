package org.eclipse.jetty.continuation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;

public class Servlet3Continuation implements Continuation {
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private AsyncContext _context;
    /* access modifiers changed from: private */
    public volatile boolean _expired = false;
    /* access modifiers changed from: private */
    public volatile boolean _initial = true;
    private List<AsyncListener> _listeners = new ArrayList();
    private final ServletRequest _request;
    private ServletResponse _response;
    private volatile boolean _responseWrapped = false;
    private volatile boolean _resumed = false;
    private long _timeoutMs = -1;

    public Servlet3Continuation(ServletRequest servletRequest) {
        this._request = servletRequest;
        this._listeners.add(new AsyncListener() {
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
            }

            public void onError(AsyncEvent asyncEvent) throws IOException {
            }

            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                asyncEvent.getAsyncContext().addListener(this);
            }

            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                boolean unused = Servlet3Continuation.this._initial = false;
                asyncEvent.getAsyncContext().dispatch();
            }
        });
    }

    public void addContinuationListener(final ContinuationListener continuationListener) {
        C23742 r0 = new AsyncListener() {
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                continuationListener.onComplete(Servlet3Continuation.this);
            }

            public void onError(AsyncEvent asyncEvent) throws IOException {
                continuationListener.onComplete(Servlet3Continuation.this);
            }

            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                asyncEvent.getAsyncContext().addListener(this);
            }

            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                boolean unused = Servlet3Continuation.this._expired = true;
                continuationListener.onTimeout(Servlet3Continuation.this);
            }
        };
        AsyncContext asyncContext = this._context;
        if (asyncContext != null) {
            asyncContext.addListener(r0);
        } else {
            this._listeners.add(r0);
        }
    }

    public void complete() {
        AsyncContext asyncContext = this._context;
        if (asyncContext != null) {
            asyncContext.complete();
            return;
        }
        throw new IllegalStateException();
    }

    public ServletResponse getServletResponse() {
        return this._response;
    }

    public boolean isExpired() {
        return this._expired;
    }

    public boolean isInitial() {
        return this._initial && this._request.getDispatcherType() != DispatcherType.ASYNC;
    }

    public boolean isResumed() {
        return this._resumed;
    }

    public boolean isSuspended() {
        return this._request.isAsyncStarted();
    }

    public void keepWrappers() {
        this._responseWrapped = true;
    }

    public void resume() {
        AsyncContext asyncContext = this._context;
        if (asyncContext != null) {
            this._resumed = true;
            asyncContext.dispatch();
            return;
        }
        throw new IllegalStateException();
    }

    public void setTimeout(long j) {
        this._timeoutMs = j;
        AsyncContext asyncContext = this._context;
        if (asyncContext != null) {
            asyncContext.setTimeout(j);
        }
    }

    public void suspend(ServletResponse servletResponse) {
        this._response = servletResponse;
        this._responseWrapped = servletResponse instanceof ServletResponseWrapper;
        this._resumed = false;
        this._expired = false;
        this._context = this._request.startAsync();
        this._context.setTimeout(this._timeoutMs);
        for (AsyncListener addListener : this._listeners) {
            this._context.addListener(addListener);
        }
        this._listeners.clear();
    }

    public void suspend() {
        this._resumed = false;
        this._expired = false;
        this._context = this._request.startAsync();
        this._context.setTimeout(this._timeoutMs);
        for (AsyncListener addListener : this._listeners) {
            this._context.addListener(addListener);
        }
        this._listeners.clear();
    }

    public boolean isResponseWrapped() {
        return this._responseWrapped;
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
