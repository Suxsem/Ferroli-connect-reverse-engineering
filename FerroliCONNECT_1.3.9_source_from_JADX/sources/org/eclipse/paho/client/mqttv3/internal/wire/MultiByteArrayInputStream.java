package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.InputStream;

public class MultiByteArrayInputStream extends InputStream {
    private byte[] bytesA;
    private byte[] bytesB;
    private int lengthA;
    private int lengthB;
    private int offsetA;
    private int offsetB;
    private int pos = 0;

    public MultiByteArrayInputStream(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        this.bytesA = bArr;
        this.bytesB = bArr2;
        this.offsetA = i;
        this.offsetB = i3;
        this.lengthA = i2;
        this.lengthB = i4;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: byte} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() throws java.io.IOException {
        /*
            r4 = this;
            int r0 = r4.pos
            int r1 = r4.lengthA
            if (r0 >= r1) goto L_0x000e
            byte[] r1 = r4.bytesA
            int r2 = r4.offsetA
            int r2 = r2 + r0
            byte r0 = r1[r2]
            goto L_0x001b
        L_0x000e:
            int r2 = r4.lengthB
            int r2 = r2 + r1
            if (r0 >= r2) goto L_0x0026
            byte[] r2 = r4.bytesB
            int r3 = r4.offsetB
            int r3 = r3 + r0
            int r3 = r3 - r1
            byte r0 = r2[r3]
        L_0x001b:
            if (r0 >= 0) goto L_0x001f
            int r0 = r0 + 256
        L_0x001f:
            int r1 = r4.pos
            int r1 = r1 + 1
            r4.pos = r1
            return r0
        L_0x0026:
            r0 = -1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.wire.MultiByteArrayInputStream.read():int");
    }
}
