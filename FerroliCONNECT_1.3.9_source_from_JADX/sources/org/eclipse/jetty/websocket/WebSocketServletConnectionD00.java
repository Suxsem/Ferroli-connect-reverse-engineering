package org.eclipse.jetty.websocket;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.QuotedStringTokenizer;

public class WebSocketServletConnectionD00 extends WebSocketConnectionD00 implements WebSocketServletConnection {
    private final WebSocketFactory factory;

    public WebSocketServletConnectionD00(WebSocketFactory webSocketFactory, WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str) throws IOException {
        super(webSocket, endPoint, webSocketBuffers, j, i, str);
        this.factory = webSocketFactory;
    }

    public void handshake(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String str) throws IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            requestURI = requestURI + "?" + queryString;
        }
        String httpURI = new HttpURI(requestURI).toString();
        String header = httpServletRequest.getHeader("Host");
        String header2 = httpServletRequest.getHeader("Sec-WebSocket-Origin");
        if (header2 == null) {
            header2 = httpServletRequest.getHeader("Origin");
        }
        if (header2 != null) {
            header2 = QuotedStringTokenizer.quoteIfNeeded(header2, "\r\n");
        }
        String header3 = httpServletRequest.getHeader("Sec-WebSocket-Key1");
        String str2 = "wss://";
        if (header3 != null) {
            setHixieKeys(header3, httpServletRequest.getHeader("Sec-WebSocket-Key2"));
            httpServletResponse.setHeader("Upgrade", "WebSocket");
            httpServletResponse.addHeader(HttpHeaders.CONNECTION, "Upgrade");
            if (header2 != null) {
                httpServletResponse.addHeader("Sec-WebSocket-Origin", header2);
            }
            StringBuilder sb = new StringBuilder();
            if (!httpServletRequest.isSecure()) {
                str2 = "ws://";
            }
            sb.append(str2);
            sb.append(header);
            sb.append(httpURI);
            httpServletResponse.addHeader("Sec-WebSocket-Location", sb.toString());
            if (str != null) {
                httpServletResponse.addHeader("Sec-WebSocket-Protocol", str);
            }
            httpServletResponse.sendError(101, "WebSocket Protocol Handshake");
            return;
        }
        httpServletResponse.setHeader("Upgrade", "WebSocket");
        httpServletResponse.addHeader(HttpHeaders.CONNECTION, "Upgrade");
        httpServletResponse.addHeader("WebSocket-Origin", header2);
        StringBuilder sb2 = new StringBuilder();
        if (!httpServletRequest.isSecure()) {
            str2 = "ws://";
        }
        sb2.append(str2);
        sb2.append(header);
        sb2.append(httpURI);
        httpServletResponse.addHeader("WebSocket-Location", sb2.toString());
        if (str != null) {
            httpServletResponse.addHeader("WebSocket-Protocol", str);
        }
        httpServletResponse.sendError(101, "Web Socket Protocol Handshake");
        httpServletResponse.flushBuffer();
        onFrameHandshake();
        onWebsocketOpen();
    }

    public void onClose() {
        super.onClose();
        this.factory.removeConnection(this);
    }
}
