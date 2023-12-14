package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;

/* renamed from: com.igexin.push.e.c.n */
public class C1385n extends C1376e {

    /* renamed from: a */
    public boolean f2320a;

    /* renamed from: b */
    public boolean f2321b;

    /* renamed from: c */
    public String f2322c;

    /* renamed from: d */
    public String f2323d;

    /* renamed from: e */
    public long f2324e;

    public C1385n() {
        this.f2278i = 37;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        boolean z = false;
        byte b = bArr[0];
        int i = 1;
        this.f2320a = (b & 64) != 0;
        if ((b & 128) != 0) {
            z = true;
        }
        this.f2321b = z;
        if (this.f2321b) {
            this.f2322c = mo14838a(b);
            int c = C1177f.m1339c(bArr, 1);
            i = 1 + c + 2;
            try {
                this.f2323d = new String(bArr, 3, c, this.f2322c);
            } catch (Exception unused) {
            }
        }
        if (bArr.length > i) {
            this.f2324e = C1177f.m1343e(bArr, i);
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        int i;
        int i2;
        byte b = this.f2320a ? (byte) 64 : 0;
        byte[] bArr = null;
        if (this.f2321b) {
            byte b2 = (byte) (b | 128);
            i2 = 3;
            try {
                bArr = this.f2323d.getBytes(this.f2322c);
                i = bArr.length;
                i2 = 3 + i;
            } catch (Exception unused) {
                i = 0;
            }
            b = (byte) (b2 | mo14837a(this.f2322c));
        } else {
            i2 = 1;
            i = 0;
        }
        byte[] bArr2 = new byte[(i2 + 8)];
        int c = C1177f.m1338c(b, bArr2, 0);
        if (this.f2321b) {
            c = C1177f.m1334b(i, bArr2, c);
            if (bArr != null) {
                c += C1177f.m1327a(bArr, 0, bArr2, c, i);
            }
        }
        C1177f.m1325a(this.f2324e, bArr2, c);
        return bArr2;
    }
}
