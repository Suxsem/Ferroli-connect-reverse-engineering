package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.push.util.EncryptUtils;
import kotlin.UByte;

/* renamed from: com.igexin.push.e.c.i */
public class C1380i extends C1376e {

    /* renamed from: a */
    public String f2302a;

    /* renamed from: b */
    public byte[] f2303b;

    /* renamed from: c */
    public byte f2304c;

    /* renamed from: d */
    public String f2305d;

    public C1380i() {
        this.f2278i = 96;
        this.f2279j = 4;
        this.f2280k = (byte) (this.f2280k | 16);
    }

    /* renamed from: a */
    private String m2234a(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        try {
            this.f2304c = bArr[0];
            byte b = bArr[1] & UByte.MAX_VALUE;
            this.f2302a = m2234a(bArr, 2, b);
            int i = 2 + b;
            int i2 = i + 1;
            int i3 = bArr[i] & UByte.MAX_VALUE;
            this.f2303b = new byte[i3];
            System.arraycopy(bArr, i2, this.f2303b, 0, i3);
            int i4 = i2 + i3;
            this.f2305d = m2234a(bArr, i4 + 1, bArr[i4] & UByte.MAX_VALUE);
        } catch (Exception unused) {
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte[] bytes = this.f2302a.getBytes();
        byte[] iv = EncryptUtils.getIV(C1177f.m1336b((int) (System.currentTimeMillis() / 1000)));
        byte[] socketAESKey = EncryptUtils.getSocketAESKey();
        byte[] bArr = new byte[(bytes.length + 2 + 2 + socketAESKey.length + 1 + iv.length)];
        int c = C1177f.m1338c(0, bArr, 0);
        int c2 = c + C1177f.m1338c((byte) bytes.length, bArr, c);
        int a = c2 + C1177f.m1327a(bytes, 0, bArr, c2, bytes.length);
        int b = a + C1177f.m1334b((short) socketAESKey.length, bArr, a);
        int a2 = b + C1177f.m1327a(socketAESKey, 0, bArr, b, socketAESKey.length);
        C1177f.m1327a(iv, 0, bArr, a2 + C1177f.m1338c((byte) iv.length, bArr, a2), iv.length);
        return bArr;
    }
}
