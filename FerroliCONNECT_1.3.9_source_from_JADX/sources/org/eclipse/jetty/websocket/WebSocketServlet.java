package org.eclipse.jetty.websocket;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocketFactory;

public abstract class WebSocketServlet extends HttpServlet implements WebSocketFactory.Acceptor {
    private final Logger LOG = Log.getLogger(getClass());
    private WebSocketFactory _webSocketFactory;

    public boolean checkOrigin(HttpServletRequest httpServletRequest, String str) {
        return true;
    }

    public void init() throws ServletException {
        try {
            String initParameter = getInitParameter("bufferSize");
            this._webSocketFactory = new WebSocketFactory(this, initParameter == null ? 8192 : Integer.parseInt(initParameter));
            this._webSocketFactory.start();
            String initParameter2 = getInitParameter("maxIdleTime");
            if (initParameter2 != null) {
                this._webSocketFactory.setMaxIdleTime(Integer.parseInt(initParameter2));
            }
            String initParameter3 = getInitParameter("maxTextMessageSize");
            if (initParameter3 != null) {
                this._webSocketFactory.setMaxTextMessageSize(Integer.parseInt(initParameter3));
            }
            String initParameter4 = getInitParameter("maxBinaryMessageSize");
            if (initParameter4 != null) {
                this._webSocketFactory.setMaxBinaryMessageSize(Integer.parseInt(initParameter4));
            }
            String initParameter5 = getInitParameter("minVersion");
            if (initParameter5 != null) {
                this._webSocketFactory.setMinVersion(Integer.parseInt(initParameter5));
            }
        } catch (ServletException e) {
            throw e;
        } catch (Exception e2) {
            throw new ServletException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (!this._webSocketFactory.acceptWebSocket(httpServletRequest, httpServletResponse) && !httpServletResponse.isCommitted()) {
            super.service(httpServletRequest, httpServletResponse);
        }
    }

    public void destroy() {
        try {
            this._webSocketFactory.stop();
        } catch (Exception e) {
            this.LOG.ignore(e);
        }
    }
}
