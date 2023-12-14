package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.math.BigInteger;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;

public class WebSocketGeneratorD00 implements WebSocketGenerator {
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private final EndPoint _endp;

    public WebSocketGeneratorD00(WebSocketBuffers webSocketBuffers, EndPoint endPoint) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
    }

    public synchronized void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        long maxIdleTime = (long) this._endp.getMaxIdleTime();
        if (this._buffer == null) {
            this._buffer = this._buffers.getDirectBuffer();
        }
        if (this._buffer.space() == 0) {
            expelBuffer(maxIdleTime);
        }
        bufferPut(b2, maxIdleTime);
        if (isLengthFrame(b2)) {
            for (int bitLength = ((new BigInteger(String.valueOf(i2)).bitLength() / 7) + 1) - 1; bitLength > 0; bitLength--) {
                bufferPut((byte) (((i2 >> (bitLength * 7)) & 127) | 128), maxIdleTime);
            }
            bufferPut((byte) (i2 & 127), maxIdleTime);
        }
        int i3 = i2;
        while (i3 > 0) {
            int space = i3 < this._buffer.space() ? i3 : this._buffer.space();
            this._buffer.put(bArr, (i2 - i3) + i, space);
            i3 -= space;
            if (this._buffer.space() > 0) {
                if (!isLengthFrame(b2)) {
                    this._buffer.put((byte) -1);
                }
                flushBuffer();
            } else {
                expelBuffer(maxIdleTime);
                if (i3 == 0) {
                    if (!isLengthFrame(b2)) {
                        this._buffer.put((byte) -1);
                    }
                    flushBuffer();
                }
            }
        }
    }

    private synchronized boolean isLengthFrame(byte b) {
        return (b & Byte.MIN_VALUE) == Byte.MIN_VALUE;
    }

    private synchronized void bufferPut(byte b, long j) throws IOException {
        if (this._buffer == null) {
            this._buffer = this._buffers.getDirectBuffer();
        }
        this._buffer.put(b);
        if (this._buffer.space() == 0) {
            expelBuffer(j);
        }
    }

    public synchronized int flush(int i) throws IOException {
        return expelBuffer((long) i);
    }

    public synchronized int flush() throws IOException {
        int flushBuffer;
        flushBuffer = flushBuffer();
        if (this._buffer != null && this._buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
        return flushBuffer;
    }

    private synchronized int flushBuffer() throws IOException {
        if (!this._endp.isOpen()) {
            throw new EofException();
        } else if (this._buffer == null || !this._buffer.hasContent()) {
            return 0;
        } else {
            return this._endp.flush(this._buffer);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized int expelBuffer(long r3) throws java.io.IOException {
        /*
            r2 = this;
            monitor-enter(r2)
            org.eclipse.jetty.io.Buffer r0 = r2._buffer     // Catch:{ all -> 0x003e }
            if (r0 != 0) goto L_0x0008
            r3 = 0
            monitor-exit(r2)
            return r3
        L_0x0008:
            int r0 = r2.flushBuffer()     // Catch:{ all -> 0x003e }
            org.eclipse.jetty.io.Buffer r1 = r2._buffer     // Catch:{ all -> 0x003e }
            r1.compact()     // Catch:{ all -> 0x003e }
            org.eclipse.jetty.io.EndPoint r1 = r2._endp     // Catch:{ all -> 0x003e }
            boolean r1 = r1.isBlocking()     // Catch:{ all -> 0x003e }
            if (r1 != 0) goto L_0x003c
        L_0x0019:
            org.eclipse.jetty.io.Buffer r1 = r2._buffer     // Catch:{ all -> 0x003e }
            int r1 = r1.space()     // Catch:{ all -> 0x003e }
            if (r1 != 0) goto L_0x003c
            org.eclipse.jetty.io.EndPoint r1 = r2._endp     // Catch:{ all -> 0x003e }
            boolean r1 = r1.blockWritable(r3)     // Catch:{ all -> 0x003e }
            if (r1 == 0) goto L_0x0034
            int r1 = r2.flushBuffer()     // Catch:{ all -> 0x003e }
            int r0 = r0 + r1
            org.eclipse.jetty.io.Buffer r1 = r2._buffer     // Catch:{ all -> 0x003e }
            r1.compact()     // Catch:{ all -> 0x003e }
            goto L_0x0019
        L_0x0034:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x003e }
            java.lang.String r4 = "Write timeout"
            r3.<init>(r4)     // Catch:{ all -> 0x003e }
            throw r3     // Catch:{ all -> 0x003e }
        L_0x003c:
            monitor-exit(r2)
            return r0
        L_0x003e:
            r3 = move-exception
            monitor-exit(r2)
            goto L_0x0042
        L_0x0041:
            throw r3
        L_0x0042:
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketGeneratorD00.expelBuffer(long):int");
    }

    public synchronized boolean isBufferEmpty() {
        return this._buffer == null || this._buffer.length() == 0;
    }
}
