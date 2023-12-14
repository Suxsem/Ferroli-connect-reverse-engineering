package org.eclipse.jetty.websocket;

import java.io.IOException;
import kotlin.jvm.internal.ByteCompanionObject;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;

public class WebSocketGeneratorD06 implements WebSocketGenerator {
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private final EndPoint _endp;

    /* renamed from: _m */
    private int f4119_m;
    private final byte[] _mask = new byte[4];
    private final MaskGen _maskGen;
    private boolean _opsent;

    public WebSocketGeneratorD06(WebSocketBuffers webSocketBuffers, EndPoint endPoint) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._maskGen = null;
    }

    public WebSocketGeneratorD06(WebSocketBuffers webSocketBuffers, EndPoint endPoint, MaskGen maskGen) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._maskGen = maskGen;
    }

    public synchronized void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        boolean z;
        int i3;
        byte[] bArr2 = bArr;
        synchronized (this) {
            long maxIdleTime = (long) this._endp.getMaxIdleTime();
            if (this._buffer == null) {
                this._buffer = this._maskGen != null ? this._buffers.getBuffer() : this._buffers.getDirectBuffer();
            }
            boolean isLastFrame = WebSocketConnectionD06.isLastFrame(b);
            int i4 = 4;
            byte b3 = (byte) ((((b & 15) << 4) + 15) & b2);
            int i5 = 10;
            int i6 = this._maskGen != null ? 14 : 10;
            int i7 = i;
            int i8 = i2;
            while (true) {
                if (this._opsent) {
                    b3 = 0;
                }
                z = true;
                this._opsent = true;
                if (i8 + i6 > this._buffer.capacity()) {
                    b3 = (byte) (b3 & ByteCompanionObject.MAX_VALUE);
                    i3 = this._buffer.capacity() - i6;
                } else {
                    if (isLastFrame) {
                        b3 = (byte) (b3 | Byte.MIN_VALUE);
                    }
                    i3 = i8;
                }
                if (this._buffer.space() <= i6) {
                    expelBuffer(maxIdleTime);
                }
                if (this._maskGen != null) {
                    this._maskGen.genMask(this._mask);
                    this.f4119_m = 0;
                    this._buffer.put(this._mask);
                }
                if (i3 > 65535) {
                    byte[] bArr3 = new byte[i5];
                    bArr3[0] = b3;
                    bArr3[1] = ByteCompanionObject.MAX_VALUE;
                    bArr3[2] = 0;
                    bArr3[3] = 0;
                    bArr3[i4] = 0;
                    bArr3[5] = 0;
                    bArr3[6] = (byte) ((i3 >> 24) & 255);
                    bArr3[7] = (byte) ((i3 >> 16) & 255);
                    bArr3[8] = (byte) ((i3 >> 8) & 255);
                    bArr3[9] = (byte) (i3 & 255);
                    bufferPut(bArr3);
                } else if (i3 >= 126) {
                    byte[] bArr4 = new byte[i4];
                    bArr4[0] = b3;
                    bArr4[1] = 126;
                    bArr4[2] = (byte) (i3 >> 8);
                    bArr4[3] = (byte) (i3 & 255);
                    bufferPut(bArr4);
                } else {
                    bufferPut(b3);
                    bufferPut((byte) i3);
                }
                int i9 = i3;
                while (i9 > 0) {
                    this._buffer.compact();
                    int space = i9 < this._buffer.space() ? i9 : this._buffer.space();
                    if (this._maskGen != null) {
                        for (int i10 = 0; i10 < space; i10++) {
                            bufferPut(bArr2[i7 + (i3 - i9) + i10]);
                        }
                    } else {
                        this._buffer.put(bArr2, (i3 - i9) + i7, space);
                    }
                    i9 -= space;
                    if (this._buffer.space() > 0) {
                        flushBuffer();
                    } else {
                        expelBuffer(maxIdleTime);
                        if (i9 == 0) {
                            flushBuffer();
                        }
                    }
                }
                i7 += i3;
                i8 -= i3;
                if (i8 <= 0) {
                    break;
                }
                i4 = 4;
                i5 = 10;
            }
            if (isLastFrame) {
                z = false;
            }
            this._opsent = z;
        }
    }

    private synchronized void bufferPut(byte[] bArr) throws IOException {
        if (this._maskGen != null) {
            for (int i = 0; i < bArr.length; i++) {
                byte b = bArr[i];
                byte[] bArr2 = this._mask;
                int i2 = this.f4119_m;
                this.f4119_m = i2 + 1;
                bArr[i] = (byte) (b ^ bArr2[i2 % 4]);
            }
        }
        this._buffer.put(bArr);
    }

    private synchronized void bufferPut(byte b) throws IOException {
        Buffer buffer = this._buffer;
        byte[] bArr = this._mask;
        int i = this.f4119_m;
        this.f4119_m = i + 1;
        buffer.put((byte) (b ^ bArr[i % 4]));
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
        } else if (this._buffer == null) {
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
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketGeneratorD06.expelBuffer(long):int");
    }

    public synchronized boolean isBufferEmpty() {
        return this._buffer == null || this._buffer.length() == 0;
    }
}
