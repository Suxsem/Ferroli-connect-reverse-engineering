package org.eclipse.jetty.websocket;

import org.eclipse.jetty.p119io.Buffer;

public interface WebSocketParser {

    public interface FrameHandler {
        void close(int i, String str);

        void onFrame(byte b, byte b2, Buffer buffer);
    }

    void fill(Buffer buffer);

    Buffer getBuffer();

    boolean isBufferEmpty();

    int parseNext();
}
