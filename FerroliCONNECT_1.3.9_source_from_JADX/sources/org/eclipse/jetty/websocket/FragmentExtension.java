package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.util.Map;

public class FragmentExtension extends AbstractExtension {
    private int _maxLength = -1;
    private int _minFragments = 1;

    public FragmentExtension() {
        super("fragment");
    }

    public boolean init(Map<String, String> map) {
        if (!super.init(map)) {
            return false;
        }
        this._maxLength = getInitParameter("maxLength", this._maxLength);
        this._minFragments = getInitParameter("minFragments", this._minFragments);
        return true;
    }

    public void addFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        if (getConnection().isControl(b2)) {
            super.addFrame(b, b2, bArr, i, i2);
            return;
        }
        int i3 = 1;
        byte b3 = b2;
        while (true) {
            int i4 = this._maxLength;
            if (i4 <= 0 || i2 <= i4) {
                byte b4 = b3;
            } else {
                int i5 = i3 + 1;
                super.addFrame((byte) ((getConnection().finMask() ^ -1) & b), b3, bArr, i, this._maxLength);
                int i6 = this._maxLength;
                i2 -= i6;
                i += i6;
                b3 = getConnection().continuationOpcode();
                i3 = i5;
            }
        }
        byte b42 = b3;
        while (i3 < this._minFragments) {
            int i7 = i2 / 2;
            i3++;
            super.addFrame((byte) (b & 7), b42, bArr, i, i7);
            i2 -= i7;
            i += i7;
            b42 = getConnection().continuationOpcode();
        }
        super.addFrame((byte) (b | getConnection().finMask()), b42, bArr, i, i2);
    }
}
