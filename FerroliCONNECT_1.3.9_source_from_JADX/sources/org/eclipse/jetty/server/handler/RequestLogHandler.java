package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class RequestLogHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger((Class<?>) RequestLogHandler.class);
    /* access modifiers changed from: private */
    public RequestLog _requestLog;

    public void handle(String str, final Request request, HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws IOException, ServletException {
        RequestLog requestLog;
        boolean equals;
        boolean isAsync;
        AsyncContinuation asyncContinuation = request.getAsyncContinuation();
        if (!asyncContinuation.isInitial()) {
            request.setDispatchTime(System.currentTimeMillis());
        }
        try {
            super.handle(str, request, httpServletRequest, httpServletResponse);
            if (requestLog != null && equals) {
                if (!isAsync) {
                    this._requestLog.log(request, (Response) httpServletResponse);
                }
            }
        } finally {
            if (this._requestLog != null && request.getDispatcherType().equals(DispatcherType.REQUEST)) {
                if (!asyncContinuation.isAsync()) {
                    this._requestLog.log(request, (Response) httpServletResponse);
                } else if (asyncContinuation.isInitial()) {
                    asyncContinuation.addContinuationListener(new ContinuationListener() {
                        public void onTimeout(Continuation continuation) {
                        }

                        public void onComplete(Continuation continuation) {
                            RequestLogHandler.this._requestLog.log(request, (Response) httpServletResponse);
                        }
                    });
                }
            }
        }
    }

    public void setRequestLog(RequestLog requestLog) {
        try {
            if (this._requestLog != null) {
                this._requestLog.stop();
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) this._requestLog, (Object) requestLog, "logimpl", true);
        }
        this._requestLog = requestLog;
        try {
            if (isStarted() && this._requestLog != null) {
                this._requestLog.start();
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public void setServer(Server server) {
        if (this._requestLog != null) {
            if (!(getServer() == null || getServer() == server)) {
                getServer().getContainer().update((Object) this, (Object) this._requestLog, (Object) null, "logimpl", true);
            }
            super.setServer(server);
            if (server != null && server != getServer()) {
                server.getContainer().update((Object) this, (Object) null, (Object) this._requestLog, "logimpl", true);
                return;
            }
            return;
        }
        super.setServer(server);
    }

    public RequestLog getRequestLog() {
        return this._requestLog;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        if (this._requestLog == null) {
            LOG.warn("!RequestLog", new Object[0]);
            this._requestLog = new NullRequestLog();
        }
        super.doStart();
        this._requestLog.start();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        this._requestLog.stop();
        if (this._requestLog instanceof NullRequestLog) {
            this._requestLog = null;
        }
    }

    private static class NullRequestLog extends AbstractLifeCycle implements RequestLog {
        public void log(Request request, Response response) {
        }

        private NullRequestLog() {
        }
    }
}
