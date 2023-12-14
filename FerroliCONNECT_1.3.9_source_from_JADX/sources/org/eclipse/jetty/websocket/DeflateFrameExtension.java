package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import kotlin.jvm.internal.ByteCompanionObject;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class DeflateFrameExtension extends AbstractExtension {
    private static final Logger LOG = Log.getLogger((Class<?>) DeflateFrameExtension.class);
    private Deflater _deflater;
    private Inflater _inflater;
    private int _minLength = 8;

    public DeflateFrameExtension() {
        super("x-deflate-frame");
    }

    public boolean init(Map<String, String> map) {
        if (!map.containsKey("minLength")) {
            map.put("minLength", Integer.toString(this._minLength));
        }
        if (!super.init(map)) {
            return false;
        }
        this._minLength = getInitParameter("minLength", this._minLength);
        this._deflater = new Deflater();
        this._inflater = new Inflater();
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onFrame(byte r6, byte r7, org.eclipse.jetty.p119io.Buffer r8) {
        /*
            r5 = this;
            org.eclipse.jetty.websocket.WebSocket$FrameConnection r0 = r5.getConnection()
            boolean r0 = r0.isControl(r7)
            if (r0 != 0) goto L_0x009f
            r0 = 1
            boolean r1 = r5.isFlag(r6, r0)
            if (r1 != 0) goto L_0x0013
            goto L_0x009f
        L_0x0013:
            byte[] r1 = r8.array()
            if (r1 != 0) goto L_0x001d
            org.eclipse.jetty.io.Buffer r8 = r8.asMutableBuffer()
        L_0x001d:
            byte r1 = r8.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 126(0x7e, float:1.77E-43)
            if (r1 < r2) goto L_0x0040
            r2 = 127(0x7f, float:1.78E-43)
            if (r1 != r2) goto L_0x002e
            r1 = 8
            goto L_0x002f
        L_0x002e:
            r1 = 2
        L_0x002f:
            r2 = 0
        L_0x0030:
            int r3 = r1 + -1
            if (r1 <= 0) goto L_0x003f
            int r2 = r2 * 256
            byte r1 = r8.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r2 = r2 + r1
            r1 = r3
            goto L_0x0030
        L_0x003f:
            r1 = r2
        L_0x0040:
            java.util.zip.Inflater r2 = r5._inflater
            byte[] r3 = r8.array()
            int r4 = r8.getIndex()
            int r8 = r8.length()
            r2.setInput(r3, r4, r8)
            org.eclipse.jetty.io.ByteArrayBuffer r8 = new org.eclipse.jetty.io.ByteArrayBuffer
            r8.<init>((int) r1)
        L_0x0056:
            java.util.zip.Inflater r1 = r5._inflater     // Catch:{ DataFormatException -> 0x008b }
            int r1 = r1.getRemaining()     // Catch:{ DataFormatException -> 0x008b }
            if (r1 <= 0) goto L_0x0083
            java.util.zip.Inflater r1 = r5._inflater     // Catch:{ DataFormatException -> 0x008b }
            byte[] r2 = r8.array()     // Catch:{ DataFormatException -> 0x008b }
            int r3 = r8.putIndex()     // Catch:{ DataFormatException -> 0x008b }
            int r4 = r8.space()     // Catch:{ DataFormatException -> 0x008b }
            int r1 = r1.inflate(r2, r3, r4)     // Catch:{ DataFormatException -> 0x008b }
            if (r1 == 0) goto L_0x007b
            int r2 = r8.putIndex()     // Catch:{ DataFormatException -> 0x008b }
            int r2 = r2 + r1
            r8.setPutIndex(r2)     // Catch:{ DataFormatException -> 0x008b }
            goto L_0x0056
        L_0x007b:
            java.util.zip.DataFormatException r6 = new java.util.zip.DataFormatException     // Catch:{ DataFormatException -> 0x008b }
            java.lang.String r7 = "insufficient data"
            r6.<init>(r7)     // Catch:{ DataFormatException -> 0x008b }
            throw r6     // Catch:{ DataFormatException -> 0x008b }
        L_0x0083:
            byte r6 = r5.clearFlag(r6, r0)     // Catch:{ DataFormatException -> 0x008b }
            super.onFrame(r6, r7, r8)     // Catch:{ DataFormatException -> 0x008b }
            goto L_0x009e
        L_0x008b:
            r6 = move-exception
            org.eclipse.jetty.util.log.Logger r7 = LOG
            r7.warn(r6)
            org.eclipse.jetty.websocket.WebSocket$FrameConnection r7 = r5.getConnection()
            r8 = 1007(0x3ef, float:1.411E-42)
            java.lang.String r6 = r6.toString()
            r7.close(r8, r6)
        L_0x009e:
            return
        L_0x009f:
            super.onFrame(r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.DeflateFrameExtension.onFrame(byte, byte, org.eclipse.jetty.io.Buffer):void");
    }

    public void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        int i3;
        byte b3 = b;
        int i4 = i2;
        byte b4 = b2;
        if (getConnection().isControl(b2) || i4 < this._minLength) {
            byte[] bArr2 = bArr;
            int i5 = i;
            super.addFrame(clearFlag(b, 1), b2, bArr, i, i2);
            return;
        }
        this._deflater.reset();
        byte[] bArr3 = bArr;
        this._deflater.setInput(bArr, i, i4);
        this._deflater.finish();
        byte[] bArr4 = new byte[i4];
        if (i4 > 65535) {
            bArr4[0] = ByteCompanionObject.MAX_VALUE;
            bArr4[1] = 0;
            bArr4[2] = 0;
            bArr4[3] = 0;
            bArr4[4] = 0;
            bArr4[5] = (byte) ((i4 >> 24) & 255);
            bArr4[6] = (byte) ((i4 >> 16) & 255);
            bArr4[7] = (byte) ((i4 >> 8) & 255);
            i3 = 9;
            bArr4[8] = (byte) (i4 & 255);
        } else if (i4 >= 126) {
            bArr4[0] = 126;
            bArr4[1] = (byte) (i4 >> 8);
            bArr4[2] = (byte) (i4 & 255);
            i3 = 3;
        } else {
            bArr4[0] = (byte) (i4 & 127);
            i3 = 1;
        }
        int deflate = this._deflater.deflate(bArr4, i3, i4 - i3);
        if (this._deflater.finished()) {
            super.addFrame(setFlag(b, 1), b2, bArr4, 0, deflate + i3);
            return;
        }
        super.addFrame(clearFlag(b, 1), b2, bArr, i, i2);
    }
}
