package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.igexin.push.e.c.o */
public class C1386o extends C1376e {

    /* renamed from: m */
    private static final String f2325m = "com.igexin.push.e.c.o";

    /* renamed from: a */
    public int f2326a;

    /* renamed from: b */
    public int f2327b;

    /* renamed from: c */
    public long f2328c;

    /* renamed from: d */
    public String f2329d;

    /* renamed from: e */
    public Object f2330e;

    /* renamed from: f */
    public Object f2331f;

    /* renamed from: g */
    public String f2332g;

    /* renamed from: h */
    public String f2333h = "UTF-8";

    public C1386o() {
        this.f2278i = 26;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        int i;
        int i2;
        this.f2326a = C1177f.m1339c(bArr, 0);
        this.f2327b = bArr[2] & 192;
        this.f2333h = mo14838a(bArr[2]);
        this.f2328c = C1177f.m1343e(bArr, 3);
        byte b = bArr[11] & UByte.MAX_VALUE;
        try {
            this.f2329d = new String(bArr, 12, b, this.f2333h);
        } catch (Exception unused) {
            this.f2329d = "";
        }
        int i3 = 12 + b;
        int i4 = 0;
        while (true) {
            i = i4 | (bArr[i3] & ByteCompanionObject.MAX_VALUE);
            if ((bArr[i3] & 128) == 0) {
                break;
            }
            i4 = i << 7;
            i3++;
        }
        int i5 = i3 + 1;
        if (i > 0) {
            if (this.f2327b == 192) {
                this.f2330e = new byte[i];
                System.arraycopy(bArr, i5, this.f2330e, 0, i);
            } else {
                try {
                    this.f2330e = new String(bArr, i5, i, this.f2333h);
                } catch (Exception unused2) {
                }
            }
        }
        int i6 = i5 + i;
        int i7 = 0;
        while (true) {
            i2 = i7 | (bArr[i6] & ByteCompanionObject.MAX_VALUE);
            if ((bArr[i6] & 128) == 0) {
                break;
            }
            i7 = i2 << 7;
            i6++;
        }
        int i8 = i6 + 1;
        if (i2 > 0) {
            this.f2331f = new byte[i2];
            System.arraycopy(bArr, i8, this.f2331f, 0, i2);
        }
        int i9 = i8 + i2;
        if (bArr.length > i9) {
            try {
                this.f2332g = new String(bArr, i9 + 1, bArr[i9] & UByte.MAX_VALUE, this.f2333h);
            } catch (Exception unused3) {
            }
        }
    }

    /* renamed from: a */
    public final boolean mo14842a() {
        return this.f2327b == 128;
    }

    /* renamed from: c */
    public final void mo14843c() {
        this.f2327b = 128;
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte[] bArr = null;
        try {
            byte[] bytes = this.f2329d.getBytes(this.f2333h);
            byte[] bytes2 = this.f2332g.getBytes(this.f2333h);
            byte[] bytes3 = !"".equals(this.f2330e) ? this.f2327b == 192 ? (byte[]) this.f2330e : ((String) this.f2330e).getBytes(this.f2333h) : null;
            byte[] bArr2 = this.f2331f != null ? (byte[]) this.f2331f : null;
            int length = bytes3 == null ? 0 : bytes3.length;
            int length2 = bArr2 == null ? 0 : bArr2.length;
            byte[] a = C1177f.m1331a(length);
            byte[] a2 = C1177f.m1331a(length2);
            bArr = new byte[(bytes.length + 13 + a.length + length + a2.length + length2 + bytes2.length)];
            int b = C1177f.m1334b(this.f2326a, bArr, 0);
            int c = b + C1177f.m1338c(this.f2327b | mo14837a(this.f2333h), bArr, b);
            int a3 = c + C1177f.m1325a(this.f2328c, bArr, c);
            int c2 = a3 + C1177f.m1338c(bytes.length, bArr, a3);
            int a4 = c2 + C1177f.m1327a(bytes, 0, bArr, c2, bytes.length);
            int a5 = a4 + C1177f.m1327a(a, 0, bArr, a4, a.length);
            if (length > 0) {
                a5 += C1177f.m1327a(bytes3, 0, bArr, a5, length);
            }
            int a6 = a5 + C1177f.m1327a(a2, 0, bArr, a5, a2.length);
            if (length2 > 0) {
                a6 += C1177f.m1327a(bArr2, 0, bArr, a6, length2);
            }
            C1177f.m1327a(bytes2, 0, bArr, a6 + C1177f.m1338c(bytes2.length, bArr, a6), bytes2.length);
        } catch (Exception unused) {
        }
        return bArr;
    }
}
