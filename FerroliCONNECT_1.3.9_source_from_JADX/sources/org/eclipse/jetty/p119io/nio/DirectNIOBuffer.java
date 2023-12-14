package org.eclipse.jetty.p119io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.eclipse.jetty.p119io.AbstractBuffer;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* renamed from: org.eclipse.jetty.io.nio.DirectNIOBuffer */
public class DirectNIOBuffer extends AbstractBuffer implements NIOBuffer {
    private static final Logger LOG = Log.getLogger((Class<?>) DirectNIOBuffer.class);
    protected final ByteBuffer _buf;
    private ReadableByteChannel _in;
    private InputStream _inStream;
    private WritableByteChannel _out;
    private OutputStream _outStream;

    public byte[] array() {
        return null;
    }

    public boolean isDirect() {
        return true;
    }

    public DirectNIOBuffer(int i) {
        super(2, false);
        this._buf = ByteBuffer.allocateDirect(i);
        this._buf.position(0);
        ByteBuffer byteBuffer = this._buf;
        byteBuffer.limit(byteBuffer.capacity());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DirectNIOBuffer(ByteBuffer byteBuffer, boolean z) {
        super(z ? 0 : 2, false);
        if (byteBuffer.isDirect()) {
            this._buf = byteBuffer;
            setGetIndex(byteBuffer.position());
            setPutIndex(byteBuffer.limit());
            return;
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041 A[SYNTHETIC, Splitter:B:16:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DirectNIOBuffer(java.io.File r10) throws java.io.IOException {
        /*
            r9 = this;
            r0 = 0
            r1 = 1
            r9.<init>(r1, r0)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x003d }
            r2.<init>(r10)     // Catch:{ all -> 0x003d }
            java.nio.channels.FileChannel r1 = r2.getChannel()     // Catch:{ all -> 0x003b }
            java.nio.channels.FileChannel$MapMode r4 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x003b }
            r5 = 0
            long r7 = r10.length()     // Catch:{ all -> 0x003b }
            r3 = r1
            java.nio.MappedByteBuffer r3 = r3.map(r4, r5, r7)     // Catch:{ all -> 0x003b }
            r9._buf = r3     // Catch:{ all -> 0x003b }
            r9.setGetIndex(r0)     // Catch:{ all -> 0x003b }
            long r3 = r10.length()     // Catch:{ all -> 0x003b }
            int r10 = (int) r3     // Catch:{ all -> 0x003b }
            r9.setPutIndex(r10)     // Catch:{ all -> 0x003b }
            r9._access = r0     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0037
        L_0x0031:
            r10 = move-exception
            org.eclipse.jetty.util.log.Logger r0 = LOG
            r0.ignore(r10)
        L_0x0037:
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)
            return
        L_0x003b:
            r10 = move-exception
            goto L_0x003f
        L_0x003d:
            r10 = move-exception
            r2 = r1
        L_0x003f:
            if (r1 == 0) goto L_0x004b
            r1.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x004b
        L_0x0045:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG
            r1.ignore(r0)
        L_0x004b:
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.DirectNIOBuffer.<init>(java.io.File):void");
    }

    public int capacity() {
        return this._buf.capacity();
    }

    public byte peek(int i) {
        return this._buf.get(i);
    }

    /* JADX INFO: finally extract failed */
    public int peek(int i, byte[] bArr, int i2, int i3) {
        if ((i + i3 > capacity() && (i3 = capacity() - i) == 0) || i3 < 0) {
            return -1;
        }
        try {
            this._buf.position(i);
            this._buf.get(bArr, i2, i3);
            this._buf.position(0);
            return i3;
        } catch (Throwable th) {
            this._buf.position(0);
            throw th;
        }
    }

    public void poke(int i, byte b) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        } else if (i < 0) {
            throw new IllegalArgumentException("index<0: " + i + "<0");
        } else if (i <= capacity()) {
            this._buf.put(i, b);
        } else {
            throw new IllegalArgumentException("index>capacity(): " + i + ">" + capacity());
        }
    }

    public int poke(int i, Buffer buffer) {
        if (!isReadOnly()) {
            byte[] array = buffer.array();
            if (array != null) {
                return poke(i, array, buffer.getIndex(), buffer.length());
            }
            Buffer buffer2 = buffer.buffer();
            if (!(buffer2 instanceof DirectNIOBuffer)) {
                return super.poke(i, buffer);
            }
            ByteBuffer byteBuffer = ((DirectNIOBuffer) buffer2)._buf;
            ByteBuffer byteBuffer2 = this._buf;
            if (byteBuffer == byteBuffer2) {
                byteBuffer = byteBuffer2.duplicate();
            }
            try {
                this._buf.position(i);
                int remaining = this._buf.remaining();
                int length = buffer.length();
                if (length <= remaining) {
                    remaining = length;
                }
                byteBuffer.position(buffer.getIndex());
                byteBuffer.limit(buffer.getIndex() + remaining);
                this._buf.put(byteBuffer);
                return remaining;
            } finally {
                this._buf.position(0);
                byteBuffer.limit(byteBuffer.capacity());
                byteBuffer.position(0);
            }
        } else {
            throw new IllegalStateException("READONLY");
        }
    }

    /* JADX INFO: finally extract failed */
    public int poke(int i, byte[] bArr, int i2, int i3) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        } else if (i < 0) {
            throw new IllegalArgumentException("index<0: " + i + "<0");
        } else if (i + i3 <= capacity() || (i3 = capacity() - i) >= 0) {
            try {
                this._buf.position(i);
                int remaining = this._buf.remaining();
                if (i3 <= remaining) {
                    remaining = i3;
                }
                if (remaining > 0) {
                    this._buf.put(bArr, i2, remaining);
                }
                this._buf.position(0);
                return remaining;
            } catch (Throwable th) {
                this._buf.position(0);
                throw th;
            }
        } else {
            throw new IllegalArgumentException("index>capacity(): " + i + ">" + capacity());
        }
    }

    public ByteBuffer getByteBuffer() {
        return this._buf;
    }

    public int readFrom(InputStream inputStream, int i) throws IOException {
        ReadableByteChannel readableByteChannel = this._in;
        if (readableByteChannel == null || !readableByteChannel.isOpen() || inputStream != this._inStream) {
            this._in = Channels.newChannel(inputStream);
            this._inStream = inputStream;
        }
        if (i < 0 || i > space()) {
            i = space();
        }
        int i2 = i;
        int putIndex = putIndex();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i3 >= i) {
                break;
            }
            try {
                this._buf.position(putIndex);
                this._buf.limit(putIndex + i2);
                i5 = this._in.read(this._buf);
                if (i5 >= 0) {
                    if (i5 > 0) {
                        putIndex += i5;
                        i3 += i5;
                        i2 -= i5;
                        setPutIndex(putIndex);
                        i4 = 0;
                    } else {
                        int i6 = i4 + 1;
                        if (i4 > 1) {
                            break;
                        }
                        i4 = i6;
                    }
                    if (inputStream.available() <= 0) {
                        break;
                    }
                } else {
                    this._in = null;
                    this._inStream = inputStream;
                    break;
                }
            } catch (IOException e) {
                this._in = null;
                this._inStream = inputStream;
                throw e;
            } catch (Throwable th) {
                ReadableByteChannel readableByteChannel2 = this._in;
                if (readableByteChannel2 != null && !readableByteChannel2.isOpen()) {
                    this._in = null;
                    this._inStream = inputStream;
                }
                this._buf.position(0);
                ByteBuffer byteBuffer = this._buf;
                byteBuffer.limit(byteBuffer.capacity());
                throw th;
            }
        }
        if (i5 >= 0 || i3 != 0) {
            ReadableByteChannel readableByteChannel3 = this._in;
            if (readableByteChannel3 != null && !readableByteChannel3.isOpen()) {
                this._in = null;
                this._inStream = inputStream;
            }
            this._buf.position(0);
            ByteBuffer byteBuffer2 = this._buf;
            byteBuffer2.limit(byteBuffer2.capacity());
            return i3;
        }
        ReadableByteChannel readableByteChannel4 = this._in;
        if (readableByteChannel4 != null && !readableByteChannel4.isOpen()) {
            this._in = null;
            this._inStream = inputStream;
        }
        this._buf.position(0);
        ByteBuffer byteBuffer3 = this._buf;
        byteBuffer3.limit(byteBuffer3.capacity());
        return -1;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        int write;
        WritableByteChannel writableByteChannel = this._out;
        if (writableByteChannel == null || !writableByteChannel.isOpen() || outputStream != this._outStream) {
            this._out = Channels.newChannel(outputStream);
            this._outStream = outputStream;
        }
        synchronized (this._buf) {
            loop0:
            while (true) {
                int i = 0;
                while (true) {
                    try {
                        if (hasContent() && this._out.isOpen()) {
                            this._buf.position(getIndex());
                            this._buf.limit(putIndex());
                            write = this._out.write(this._buf);
                            if (write < 0) {
                                break loop0;
                            } else if (write > 0) {
                                break;
                            } else {
                                int i2 = i + 1;
                                if (i <= 1) {
                                    i = i2;
                                }
                            }
                        }
                    } catch (IOException e) {
                        this._out = null;
                        this._outStream = null;
                        throw e;
                    } catch (Throwable th) {
                        if (this._out != null && !this._out.isOpen()) {
                            this._out = null;
                            this._outStream = null;
                        }
                        this._buf.position(0);
                        this._buf.limit(this._buf.capacity());
                        throw th;
                    }
                }
                skip(write);
            }
            if (this._out != null && !this._out.isOpen()) {
                this._out = null;
                this._outStream = null;
            }
            this._buf.position(0);
            this._buf.limit(this._buf.capacity());
        }
    }
}
