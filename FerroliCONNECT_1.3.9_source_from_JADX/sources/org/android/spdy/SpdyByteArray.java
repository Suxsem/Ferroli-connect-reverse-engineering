package org.android.spdy;

import java.util.Arrays;

public class SpdyByteArray implements Comparable<SpdyByteArray> {
    private byte[] byteArray;
    int dataLength;
    int length;

    SpdyByteArray() {
        this.byteArray = null;
        this.length = 0;
        this.dataLength = 0;
    }

    SpdyByteArray(int i) {
        this.byteArray = new byte[i];
        this.length = i;
        this.dataLength = 0;
    }

    /* access modifiers changed from: package-private */
    public void setByteArrayDataLength(int i) {
        this.dataLength = i;
    }

    public byte[] getByteArray() {
        return this.byteArray;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public void recycle() {
        Arrays.fill(this.byteArray, (byte) 0);
        this.dataLength = 0;
        SpdyBytePool.getInstance().recycle(this);
    }

    public int compareTo(SpdyByteArray spdyByteArray) {
        int i = this.length;
        int i2 = spdyByteArray.length;
        if (i != i2) {
            return i - i2;
        }
        if (this.byteArray == null) {
            return -1;
        }
        if (spdyByteArray.byteArray == null) {
            return 1;
        }
        return hashCode() - spdyByteArray.hashCode();
    }
}
