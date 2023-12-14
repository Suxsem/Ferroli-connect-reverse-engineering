package org.eclipse.jetty.p119io;

import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.nio.DirectNIOBuffer;
import org.eclipse.jetty.p119io.nio.IndirectNIOBuffer;

/* renamed from: org.eclipse.jetty.io.AbstractBuffers */
public abstract class AbstractBuffers implements Buffers {
    protected final int _bufferSize;
    protected final Buffers.Type _bufferType;
    protected final int _headerSize;
    protected final Buffers.Type _headerType;
    protected final Buffers.Type _otherType;

    public AbstractBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3) {
        this._headerType = type;
        this._headerSize = i;
        this._bufferType = type2;
        this._bufferSize = i2;
        this._otherType = type3;
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public int getHeaderSize() {
        return this._headerSize;
    }

    /* renamed from: org.eclipse.jetty.io.AbstractBuffers$1 */
    static /* synthetic */ class C23821 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$io$Buffers$Type = new int[Buffers.Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.eclipse.jetty.io.Buffers$Type[] r0 = org.eclipse.jetty.p119io.Buffers.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$io$Buffers$Type = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$io$Buffers$Type     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.io.Buffers$Type r1 = org.eclipse.jetty.p119io.Buffers.Type.BYTE_ARRAY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$io$Buffers$Type     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.io.Buffers$Type r1 = org.eclipse.jetty.p119io.Buffers.Type.DIRECT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$io$Buffers$Type     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.io.Buffers$Type r1 = org.eclipse.jetty.p119io.Buffers.Type.INDIRECT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.AbstractBuffers.C23821.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public final Buffer newHeader() {
        int i = C23821.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._headerType.ordinal()];
        if (i == 1) {
            return new ByteArrayBuffer(this._headerSize);
        }
        if (i == 2) {
            return new DirectNIOBuffer(this._headerSize);
        }
        if (i == 3) {
            return new IndirectNIOBuffer(this._headerSize);
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Buffer newBuffer() {
        int i = C23821.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._bufferType.ordinal()];
        if (i == 1) {
            return new ByteArrayBuffer(this._bufferSize);
        }
        if (i == 2) {
            return new DirectNIOBuffer(this._bufferSize);
        }
        if (i == 3) {
            return new IndirectNIOBuffer(this._bufferSize);
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Buffer newBuffer(int i) {
        int i2 = C23821.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._otherType.ordinal()];
        if (i2 == 1) {
            return new ByteArrayBuffer(i);
        }
        if (i2 == 2) {
            return new DirectNIOBuffer(i);
        }
        if (i2 == 3) {
            return new IndirectNIOBuffer(i);
        }
        throw new IllegalStateException();
    }

    public final boolean isHeader(Buffer buffer) {
        if (buffer.capacity() == this._headerSize) {
            int i = C23821.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._headerType.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return buffer instanceof DirectNIOBuffer;
                }
                if (i == 3) {
                    return buffer instanceof IndirectNIOBuffer;
                }
            } else if (!(buffer instanceof ByteArrayBuffer) || (buffer instanceof IndirectNIOBuffer)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public final boolean isBuffer(Buffer buffer) {
        if (buffer.capacity() == this._bufferSize) {
            int i = C23821.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._bufferType.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return buffer instanceof DirectNIOBuffer;
                }
                if (i == 3) {
                    return buffer instanceof IndirectNIOBuffer;
                }
            } else if (!(buffer instanceof ByteArrayBuffer) || (buffer instanceof IndirectNIOBuffer)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return String.format("%s [%d,%d]", new Object[]{getClass().getSimpleName(), Integer.valueOf(this._headerSize), Integer.valueOf(this._bufferSize)});
    }
}
