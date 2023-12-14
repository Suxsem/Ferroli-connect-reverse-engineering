package org.eclipse.jetty.websocket;

public class FixedMaskGen implements MaskGen {
    private final byte[] _mask;

    public FixedMaskGen() {
        this(new byte[]{-1, -1, -1, -1});
    }

    public FixedMaskGen(byte[] bArr) {
        this._mask = new byte[4];
        System.arraycopy(bArr, 0, this._mask, 0, 4);
    }

    public void genMask(byte[] bArr) {
        System.arraycopy(this._mask, 0, bArr, 0, 4);
    }
}
