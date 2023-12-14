package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;

public class IdleTimeoutHandler extends HandlerWrapper {
    private boolean _applyToAsync = false;
    private int _idleTimeoutMs = 1000;

    public boolean isApplyToAsync() {
        return this._applyToAsync;
    }

    public void setApplyToAsync(boolean z) {
        this._applyToAsync = z;
    }

    public long getIdleTimeoutMs() {
        return (long) this._idleTimeoutMs;
    }

    public void setIdleTimeoutMs(int i) {
        this._idleTimeoutMs = i;
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        final EndPoint endPoint;
        final int i;
        boolean z;
        boolean isAsyncStarted;
        AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
        if (currentConnection == null) {
            endPoint = null;
        } else {
            endPoint = currentConnection.getEndPoint();
        }
        if (endPoint == null) {
            i = -1;
        } else {
            i = endPoint.getMaxIdleTime();
            endPoint.setMaxIdleTime(this._idleTimeoutMs);
        }
        try {
            super.handle(str, request, httpServletRequest, httpServletResponse);
            if (endPoint == null) {
                return;
            }
            if (!z || !isAsyncStarted) {
                endPoint.setMaxIdleTime(i);
            }
        } finally {
            if (endPoint != null) {
                if (!this._applyToAsync || !httpServletRequest.isAsyncStarted()) {
                    endPoint.setMaxIdleTime(i);
                } else {
                    httpServletRequest.getAsyncContext().addListener(new AsyncListener() {
                        public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                        }

                        public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                        }

                        public void onError(AsyncEvent asyncEvent) throws IOException {
                            endPoint.setMaxIdleTime(i);
                        }

                        public void onComplete(AsyncEvent asyncEvent) throws IOException {
                            endPoint.setMaxIdleTime(i);
                        }
                    });
                }
            }
        }
    }
}
