package org.eclipse.jetty.websocket;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.websocket.WebSocketFactory;

public abstract class WebSocketHandler extends HandlerWrapper implements WebSocketFactory.Acceptor {
    private final WebSocketFactory _webSocketFactory = new WebSocketFactory(this, 32768);

    public boolean checkOrigin(HttpServletRequest httpServletRequest, String str) {
        return true;
    }

    public WebSocketFactory getWebSocketFactory() {
        return this._webSocketFactory;
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._webSocketFactory.acceptWebSocket(httpServletRequest, httpServletResponse) || httpServletResponse.isCommitted()) {
            request.setHandled(true);
        } else {
            super.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }
}
