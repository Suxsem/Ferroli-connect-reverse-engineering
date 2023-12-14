package org.eclipse.jetty.p119io;

import java.io.IOException;

/* renamed from: org.eclipse.jetty.io.ByteArrayEndPoint */
public class ByteArrayEndPoint implements ConnectedEndPoint {
    protected boolean _closed;
    protected Connection _connection;
    protected boolean _growOutput;
    protected ByteArrayBuffer _in;
    protected byte[] _inBytes;
    protected int _maxIdleTime;
    protected boolean _nonBlocking;
    protected ByteArrayBuffer _out;

    public boolean blockReadable(long j) {
        return true;
    }

    public boolean blockWritable(long j) {
        return true;
    }

    public void flush() throws IOException {
    }

    public String getLocalAddr() {
        return null;
    }

    public String getLocalHost() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public ByteArrayEndPoint() {
    }

    public Connection getConnection() {
        return this._connection;
    }

    public void setConnection(Connection connection) {
        this._connection = connection;
    }

    public boolean isNonBlocking() {
        return this._nonBlocking;
    }

    public void setNonBlocking(boolean z) {
        this._nonBlocking = z;
    }

    public ByteArrayEndPoint(byte[] bArr, int i) {
        this._inBytes = bArr;
        this._in = new ByteArrayBuffer(bArr);
        this._out = new ByteArrayBuffer(i);
    }

    public ByteArrayBuffer getIn() {
        return this._in;
    }

    public void setIn(ByteArrayBuffer byteArrayBuffer) {
        this._in = byteArrayBuffer;
    }

    public ByteArrayBuffer getOut() {
        return this._out;
    }

    public void setOut(ByteArrayBuffer byteArrayBuffer) {
        this._out = byteArrayBuffer;
    }

    public boolean isOpen() {
        return !this._closed;
    }

    public boolean isInputShutdown() {
        return this._closed;
    }

    public boolean isOutputShutdown() {
        return this._closed;
    }

    public boolean isBlocking() {
        return !this._nonBlocking;
    }

    public void shutdownOutput() throws IOException {
        close();
    }

    public void shutdownInput() throws IOException {
        close();
    }

    public void close() throws IOException {
        this._closed = true;
    }

    public int fill(Buffer buffer) throws IOException {
        if (!this._closed) {
            ByteArrayBuffer byteArrayBuffer = this._in;
            if (byteArrayBuffer == null || byteArrayBuffer.length() <= 0) {
                ByteArrayBuffer byteArrayBuffer2 = this._in;
                if (byteArrayBuffer2 != null && byteArrayBuffer2.length() == 0 && this._nonBlocking) {
                    return 0;
                }
                close();
                return -1;
            }
            int put = buffer.put((Buffer) this._in);
            this._in.skip(put);
            return put;
        }
        throw new IOException("CLOSED");
    }

    public int flush(Buffer buffer) throws IOException {
        if (!this._closed) {
            if (this._growOutput && buffer.length() > this._out.space()) {
                this._out.compact();
                if (buffer.length() > this._out.space()) {
                    ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(this._out.putIndex() + buffer.length());
                    ByteArrayBuffer byteArrayBuffer2 = this._out;
                    byteArrayBuffer.put(byteArrayBuffer2.peek(0, byteArrayBuffer2.putIndex()));
                    if (this._out.getIndex() > 0) {
                        byteArrayBuffer.mark();
                        byteArrayBuffer.setGetIndex(this._out.getIndex());
                    }
                    this._out = byteArrayBuffer;
                }
            }
            int put = this._out.put(buffer);
            if (!buffer.isImmutable()) {
                buffer.skip(put);
            }
            return put;
        }
        throw new IOException("CLOSED");
    }

    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        if (!this._closed) {
            int i = 0;
            if (buffer != null && buffer.length() > 0) {
                i = flush(buffer);
            }
            if (buffer != null && buffer.length() != 0) {
                return i;
            }
            if (buffer2 != null && buffer2.length() > 0) {
                i += flush(buffer2);
            }
            return ((buffer2 == null || buffer2.length() == 0) && buffer3 != null && buffer3.length() > 0) ? i + flush(buffer3) : i;
        }
        throw new IOException("CLOSED");
    }

    public void reset() {
        this._closed = false;
        this._in.clear();
        this._out.clear();
        byte[] bArr = this._inBytes;
        if (bArr != null) {
            this._in.setPutIndex(bArr.length);
        }
    }

    public Object getTransport() {
        return this._inBytes;
    }

    public boolean isGrowOutput() {
        return this._growOutput;
    }

    public void setGrowOutput(boolean z) {
        this._growOutput = z;
    }

    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public void setMaxIdleTime(int i) throws IOException {
        this._maxIdleTime = i;
    }
}
