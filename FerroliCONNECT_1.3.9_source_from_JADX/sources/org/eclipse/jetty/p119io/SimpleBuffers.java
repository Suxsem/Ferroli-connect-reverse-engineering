package org.eclipse.jetty.p119io;

/* renamed from: org.eclipse.jetty.io.SimpleBuffers */
public class SimpleBuffers implements Buffers {
    final Buffer _buffer;
    boolean _bufferOut;
    final Buffer _header;
    boolean _headerOut;

    public SimpleBuffers(Buffer buffer, Buffer buffer2) {
        this._header = buffer;
        this._buffer = buffer2;
    }

    public Buffer getBuffer() {
        synchronized (this) {
            if (this._buffer != null && !this._bufferOut) {
                this._bufferOut = true;
                Buffer buffer = this._buffer;
                return buffer;
            } else if (this._buffer != null && this._header != null && this._header.capacity() == this._buffer.capacity() && !this._headerOut) {
                this._headerOut = true;
                Buffer buffer2 = this._header;
                return buffer2;
            } else if (this._buffer != null) {
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(this._buffer.capacity());
                return byteArrayBuffer;
            } else {
                ByteArrayBuffer byteArrayBuffer2 = new ByteArrayBuffer(4096);
                return byteArrayBuffer2;
            }
        }
    }

    public Buffer getHeader() {
        synchronized (this) {
            if (this._header != null && !this._headerOut) {
                this._headerOut = true;
                Buffer buffer = this._header;
                return buffer;
            } else if (this._buffer != null && this._header != null && this._header.capacity() == this._buffer.capacity() && !this._bufferOut) {
                this._bufferOut = true;
                Buffer buffer2 = this._buffer;
                return buffer2;
            } else if (this._header != null) {
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(this._header.capacity());
                return byteArrayBuffer;
            } else {
                ByteArrayBuffer byteArrayBuffer2 = new ByteArrayBuffer(4096);
                return byteArrayBuffer2;
            }
        }
    }

    public Buffer getBuffer(int i) {
        synchronized (this) {
            if (this._header != null && this._header.capacity() == i) {
                Buffer header = getHeader();
                return header;
            } else if (this._buffer == null || this._buffer.capacity() != i) {
                return null;
            } else {
                Buffer buffer = getBuffer();
                return buffer;
            }
        }
    }

    public void returnBuffer(Buffer buffer) {
        synchronized (this) {
            buffer.clear();
            if (buffer == this._header) {
                this._headerOut = false;
            }
            if (buffer == this._buffer) {
                this._bufferOut = false;
            }
        }
    }
}
