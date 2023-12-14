package org.eclipse.jetty.websocket;

public class ZeroMaskGen implements MaskGen {
    public void genMask(byte[] bArr) {
        bArr[3] = 0;
        bArr[2] = 0;
        bArr[1] = 0;
        bArr[0] = 0;
    }
}
