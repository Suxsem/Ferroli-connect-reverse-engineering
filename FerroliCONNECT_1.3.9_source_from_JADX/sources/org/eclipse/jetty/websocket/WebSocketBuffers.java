package org.eclipse.jetty.websocket;

import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.BuffersFactory;

public class WebSocketBuffers {
    private final int _bufferSize;
    private final Buffers _buffers;

    public WebSocketBuffers(int i) {
        this._bufferSize = i;
        this._buffers = BuffersFactory.newBuffers(Buffers.Type.DIRECT, i, Buffers.Type.INDIRECT, i, Buffers.Type.INDIRECT, -1);
    }

    public Buffer getBuffer() {
        return this._buffers.getBuffer();
    }

    public Buffer getDirectBuffer() {
        return this._buffers.getHeader();
    }

    public void returnBuffer(Buffer buffer) {
        this._buffers.returnBuffer(buffer);
    }

    public int getBufferSize() {
        return this._bufferSize;
    }
}
