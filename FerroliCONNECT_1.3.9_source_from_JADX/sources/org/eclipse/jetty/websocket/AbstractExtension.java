package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketParser;

public class AbstractExtension implements Extension {
    private static final int[] __mask = {-1, 4, 2, 1};
    private WebSocket.FrameConnection _connection;
    private WebSocketParser.FrameHandler _inbound;
    private final String _name;
    private WebSocketGenerator _outbound;
    private final Map<String, String> _parameters = new HashMap();

    public AbstractExtension(String str) {
        this._name = str;
    }

    public WebSocket.FrameConnection getConnection() {
        return this._connection;
    }

    public boolean init(Map<String, String> map) {
        this._parameters.putAll(map);
        return true;
    }

    public String getInitParameter(String str) {
        return this._parameters.get(str);
    }

    public String getInitParameter(String str, String str2) {
        if (!this._parameters.containsKey(str)) {
            return str2;
        }
        return this._parameters.get(str);
    }

    public int getInitParameter(String str, int i) {
        String str2 = this._parameters.get(str);
        if (str2 == null) {
            return i;
        }
        return Integer.valueOf(str2).intValue();
    }

    public void bind(WebSocket.FrameConnection frameConnection, WebSocketParser.FrameHandler frameHandler, WebSocketGenerator webSocketGenerator) {
        this._connection = frameConnection;
        this._inbound = frameHandler;
        this._outbound = webSocketGenerator;
    }

    public String getName() {
        return this._name;
    }

    public String getParameterizedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._name);
        for (String next : this._parameters.keySet()) {
            sb.append(';');
            sb.append(next);
            sb.append('=');
            sb.append(QuotedStringTokenizer.quoteIfNeeded(this._parameters.get(next), ";="));
        }
        return sb.toString();
    }

    public void onFrame(byte b, byte b2, Buffer buffer) {
        this._inbound.onFrame(b, b2, buffer);
    }

    public void close(int i, String str) {
        this._inbound.close(i, str);
    }

    public int flush() throws IOException {
        return this._outbound.flush();
    }

    public boolean isBufferEmpty() {
        return this._outbound.isBufferEmpty();
    }

    public void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        this._outbound.addFrame(b, b2, bArr, i, i2);
    }

    public byte setFlag(byte b, int i) {
        if (i >= 1 && i <= 3) {
            return (byte) (b | __mask[i]);
        }
        throw new IllegalArgumentException("rsv" + i);
    }

    public byte clearFlag(byte b, int i) {
        if (i >= 1 && i <= 3) {
            return (byte) (b & (__mask[i] ^ -1));
        }
        throw new IllegalArgumentException("rsv" + i);
    }

    public boolean isFlag(byte b, int i) {
        if (i >= 1 && i <= 3) {
            return (b & __mask[i]) != 0;
        }
        throw new IllegalArgumentException("rsv" + i);
    }

    public String toString() {
        return getParameterizedName();
    }
}
