package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;

/* renamed from: com.igexin.push.e.c.m */
public class C1384m extends C1376e {

    /* renamed from: a */
    public boolean f2315a;

    /* renamed from: b */
    public boolean f2316b;

    /* renamed from: c */
    public String f2317c;

    /* renamed from: d */
    public String f2318d;

    /* renamed from: e */
    public long f2319e;

    public C1384m() {
        this.f2278i = 5;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        boolean z = false;
        byte b = bArr[0];
        int i = 1;
        this.f2315a = (b & 64) != 0;
        if ((b & 128) != 0) {
            z = true;
        }
        this.f2316b = z;
        if (this.f2316b) {
            this.f2317c = mo14838a(b);
            int c = C1177f.m1339c(bArr, 1);
            i = 1 + c + 2;
            try {
                this.f2318d = new String(bArr, 3, c, this.f2317c);
            } catch (Exception unused) {
            }
        }
        if (bArr.length > i) {
            this.f2319e = C1177f.m1343e(bArr, i);
            C1179b.m1354a("LoginResult|session = " + this.f2319e);
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        int i;
        int i2;
        byte b = this.f2315a ? (byte) 64 : 0;
        byte[] bArr = null;
        if (this.f2316b) {
            byte b2 = (byte) (b | 128);
            i2 = 3;
            try {
                bArr = this.f2318d.getBytes(this.f2317c);
                i = bArr.length;
                i2 = 3 + i;
            } catch (Exception unused) {
                i = 0;
            }
            b = (byte) (b2 | mo14837a(this.f2317c));
        } else {
            i2 = 1;
            i = 0;
        }
        byte[] bArr2 = new byte[(i2 + 8)];
        int c = C1177f.m1338c(b, bArr2, 0);
        if (this.f2316b) {
            c = C1177f.m1334b(i, bArr2, c);
            if (bArr != null) {
                c += C1177f.m1327a(bArr, 0, bArr2, c, i);
            }
        }
        C1177f.m1325a(this.f2319e, bArr2, c);
        return bArr2;
    }
}
