package org.eclipse.jetty.websocket;

import java.util.List;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.nio.AsyncConnection;
import org.eclipse.jetty.websocket.WebSocket;

public interface WebSocketConnection extends AsyncConnection {
    void fillBuffersFrom(Buffer buffer);

    WebSocket.Connection getConnection();

    List<Extension> getExtensions();

    void shutdown();
}
