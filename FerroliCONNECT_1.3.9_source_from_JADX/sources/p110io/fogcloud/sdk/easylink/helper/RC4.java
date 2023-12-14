package p110io.fogcloud.sdk.easylink.helper;

import kotlin.UByte;

/* renamed from: io.fogcloud.sdk.easylink.helper.RC4 */
public class RC4 {

    /* renamed from: S */
    private final byte[] f3624S = new byte[256];

    /* renamed from: T */
    private final byte[] f3625T = new byte[256];
    private final int keylen;

    public RC4(byte[] bArr) {
        if (bArr.length < 1 || bArr.length > 256) {
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        }
        this.keylen = bArr.length;
        for (int i = 0; i < 256; i++) {
            this.f3624S[i] = (byte) i;
            this.f3625T[i] = bArr[i % this.keylen];
        }
        byte b = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            byte[] bArr2 = this.f3624S;
            b = (b + bArr2[i2] + this.f3625T[i2]) & UByte.MAX_VALUE;
            byte b2 = bArr2[b];
            bArr2[b] = bArr2[i2];
            bArr2[i2] = b2;
        }
    }

    public byte[] encrypt(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        byte b = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i = (i + 1) & 255;
            byte[] bArr3 = this.f3624S;
            b = (b + bArr3[i]) & UByte.MAX_VALUE;
            byte b2 = bArr3[b];
            bArr3[b] = bArr3[i];
            bArr3[i] = b2;
            bArr2[i2] = (byte) (bArr3[(bArr3[i] + bArr3[b]) & 255] ^ bArr[i2]);
        }
        return bArr2;
    }

    public byte[] decrypt(byte[] bArr) {
        return encrypt(bArr);
    }
}
