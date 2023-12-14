package org.eclipse.jetty.websocket;

import java.io.IOException;

public interface WebSocketGenerator {
    void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException;

    int flush() throws IOException;

    boolean isBufferEmpty();
}
