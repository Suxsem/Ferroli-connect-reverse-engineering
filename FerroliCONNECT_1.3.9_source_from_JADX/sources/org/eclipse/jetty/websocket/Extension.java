package org.eclipse.jetty.websocket;

import java.util.Map;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketParser;

public interface Extension extends WebSocketParser.FrameHandler, WebSocketGenerator {
    void bind(WebSocket.FrameConnection frameConnection, WebSocketParser.FrameHandler frameHandler, WebSocketGenerator webSocketGenerator);

    String getName();

    String getParameterizedName();

    boolean init(Map<String, String> map);
}
