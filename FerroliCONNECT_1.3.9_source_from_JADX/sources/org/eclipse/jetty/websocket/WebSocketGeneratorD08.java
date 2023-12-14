package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.ByteCompanionObject;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;

public class WebSocketGeneratorD08 implements WebSocketGenerator {
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private boolean _closed;
    private final EndPoint _endp;
    private final Lock _lock;

    /* renamed from: _m */
    private int f4120_m;
    private final byte[] _mask;
    private final MaskGen _maskGen;
    private boolean _opsent;

    public WebSocketGeneratorD08(WebSocketBuffers webSocketBuffers, EndPoint endPoint) {
        this(webSocketBuffers, endPoint, (MaskGen) null);
    }

    public WebSocketGeneratorD08(WebSocketBuffers webSocketBuffers, EndPoint endPoint, MaskGen maskGen) {
        this._lock = new ReentrantLock();
        this._mask = new byte[4];
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._maskGen = maskGen;
    }

    public Buffer getBuffer() {
        this._lock.lock();
        try {
            return this._buffer;
        } finally {
            this._lock.unlock();
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v2 */
    public void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        int i3;
        byte b3;
        byte[] bArr2 = bArr;
        this._lock.lock();
        try {
            if (!this._closed) {
                ? r3 = 1;
                byte b4 = b2;
                if (b4 == 8) {
                    this._closed = true;
                }
                byte b5 = 0;
                boolean z = this._maskGen != null;
                if (this._buffer == null) {
                    this._buffer = z ? this._buffers.getBuffer() : this._buffers.getDirectBuffer();
                }
                boolean isLastFrame = WebSocketConnectionD08.isLastFrame(b);
                int i4 = 10;
                int i5 = z ? 14 : 10;
                int i6 = i;
                int i7 = i2;
                while (true) {
                    if (this._opsent) {
                        b4 = 0;
                    }
                    byte b6 = (byte) (((b & 15) << 4) + (b4 & 15));
                    this._opsent = r3;
                    if (i7 + i5 > this._buffer.capacity()) {
                        b6 = (byte) (b6 & ByteCompanionObject.MAX_VALUE);
                        i3 = this._buffer.capacity() - i5;
                    } else {
                        if (isLastFrame) {
                            b6 = (byte) (b6 | 128);
                        }
                        i3 = i7;
                    }
                    if (this._buffer.space() <= i5) {
                        flushBuffer();
                        if (this._buffer.space() <= i5) {
                            flush();
                        }
                    }
                    if (i3 > 65535) {
                        Buffer buffer = this._buffer;
                        byte[] bArr3 = new byte[i4];
                        bArr3[b5] = b6;
                        bArr3[r3] = z ? -1 : ByteCompanionObject.MAX_VALUE;
                        bArr3[2] = b5;
                        bArr3[3] = b5;
                        bArr3[4] = b5;
                        bArr3[5] = b5;
                        bArr3[6] = (byte) ((i3 >> 24) & 255);
                        bArr3[7] = (byte) ((i3 >> 16) & 255);
                        bArr3[8] = (byte) ((i3 >> 8) & 255);
                        bArr3[9] = (byte) (i3 & 255);
                        buffer.put(bArr3);
                    } else {
                        byte b7 = 126;
                        if (i3 >= 126) {
                            Buffer buffer2 = this._buffer;
                            byte[] bArr4 = new byte[4];
                            bArr4[b5] = b6;
                            if (z) {
                                b7 = -2;
                            }
                            bArr4[r3] = b7;
                            bArr4[2] = (byte) (i3 >> 8);
                            bArr4[3] = (byte) (i3 & 255);
                            buffer2.put(bArr4);
                        } else {
                            Buffer buffer3 = this._buffer;
                            byte[] bArr5 = new byte[2];
                            bArr5[b5] = b6;
                            bArr5[r3] = (byte) (z ? i3 | 128 : i3);
                            buffer3.put(bArr5);
                        }
                    }
                    if (z) {
                        this._maskGen.genMask(this._mask);
                        this.f4120_m = b5;
                        this._buffer.put(this._mask);
                    }
                    int i8 = i3;
                    while (i8 > 0) {
                        this._buffer.compact();
                        int space = i8 < this._buffer.space() ? i8 : this._buffer.space();
                        if (z) {
                            int i9 = 0;
                            while (i9 < space) {
                                Buffer buffer4 = this._buffer;
                                byte b8 = bArr2[(i3 - i8) + i6 + i9];
                                byte[] bArr6 = this._mask;
                                int i10 = this.f4120_m;
                                this.f4120_m = i10 + 1;
                                buffer4.put((byte) (bArr6[i10 % 4] ^ b8));
                                i9++;
                                b6 = b6;
                            }
                            b3 = b6;
                        } else {
                            b3 = b6;
                            this._buffer.put(bArr2, (i3 - i8) + i6, space);
                        }
                        i8 -= space;
                        if (this._buffer.space() > 0) {
                            flushBuffer();
                        } else {
                            flush();
                            if (i8 == 0) {
                                flushBuffer();
                            }
                        }
                        b6 = b3;
                    }
                    byte b9 = b6;
                    i6 += i3;
                    i7 -= i3;
                    if (i7 <= 0) {
                        break;
                    }
                    b4 = b9;
                    r3 = 1;
                    b5 = 0;
                    i4 = 10;
                }
                this._opsent = !isLastFrame;
                if (this._buffer != null && this._buffer.length() == 0) {
                    this._buffers.returnBuffer(this._buffer);
                    this._buffer = null;
                }
                return;
            }
            throw new EofException("Closed");
        } finally {
            this._lock.unlock();
        }
    }

    public int flushBuffer() throws IOException {
        int i = 0;
        if (!this._lock.tryLock()) {
            return 0;
        }
        try {
            if (this._endp.isOpen()) {
                if (this._buffer != null) {
                    if (this._buffer.hasContent()) {
                        i = this._endp.flush(this._buffer);
                    }
                    if (this._closed && this._buffer.length() == 0) {
                        this._endp.shutdownOutput();
                    }
                }
                return i;
            }
            throw new EofException();
        } finally {
            this._lock.unlock();
        }
    }

    public int flush() throws IOException {
        if (!this._lock.tryLock()) {
            return 0;
        }
        try {
            if (this._buffer == null) {
                return 0;
            }
            int flushBuffer = flushBuffer();
            if (!this._endp.isBlocking()) {
                long currentTimeMillis = System.currentTimeMillis();
                long maxIdleTime = ((long) this._endp.getMaxIdleTime()) + currentTimeMillis;
                while (this._buffer.length() > 0) {
                    if (!this._endp.blockWritable(maxIdleTime - currentTimeMillis)) {
                        currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis >= maxIdleTime) {
                            throw new IOException("Write timeout");
                        }
                    } else {
                        flushBuffer += flushBuffer();
                    }
                }
            }
            this._buffer.compact();
            this._lock.unlock();
            return flushBuffer;
        } finally {
            this._lock.unlock();
        }
    }

    public boolean isBufferEmpty() {
        this._lock.lock();
        try {
            return this._buffer == null || this._buffer.length() == 0;
        } finally {
            this._lock.unlock();
        }
    }

    public void returnBuffer() {
        this._lock.lock();
        try {
            if (this._buffer != null && this._buffer.length() == 0) {
                this._buffers.returnBuffer(this._buffer);
                this._buffer = null;
            }
        } finally {
            this._lock.unlock();
        }
    }

    public String toString() {
        Buffer buffer = this._buffer;
        Object[] objArr = new Object[4];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = Integer.valueOf(hashCode());
        objArr[2] = Boolean.valueOf(this._closed);
        objArr[3] = Integer.valueOf(buffer == null ? -1 : buffer.length());
        return String.format("%s@%x closed=%b buffer=%d", objArr);
    }
}
