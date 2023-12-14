package org.eclipse.jetty.websocket;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.p119io.EndPoint;

public class WebSocketServletConnectionD06 extends WebSocketConnectionD06 implements WebSocketServletConnection {
    private final WebSocketFactory factory;

    public WebSocketServletConnectionD06(WebSocketFactory webSocketFactory, WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str) throws IOException {
        super(webSocket, endPoint, webSocketBuffers, j, i, str);
        this.factory = webSocketFactory;
    }

    public void handshake(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String str) throws IOException {
        String header = httpServletRequest.getHeader("Sec-WebSocket-Key");
        httpServletResponse.setHeader("Upgrade", "WebSocket");
        httpServletResponse.addHeader(HttpHeaders.CONNECTION, "Upgrade");
        httpServletResponse.addHeader("Sec-WebSocket-Accept", hashKey(header));
        if (str != null) {
            httpServletResponse.addHeader("Sec-WebSocket-Protocol", str);
        }
        httpServletResponse.sendError(101);
        onFrameHandshake();
        onWebSocketOpen();
    }

    public void onClose() {
        super.onClose();
        this.factory.removeConnection(this);
    }
}
