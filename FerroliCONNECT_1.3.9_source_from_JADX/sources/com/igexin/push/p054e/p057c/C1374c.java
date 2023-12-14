package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.push.p088g.p090b.C1569b;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.igexin.push.e.c.c */
public class C1374c extends C1376e {

    /* renamed from: a */
    public int f2263a;

    /* renamed from: b */
    public int f2264b;

    /* renamed from: c */
    public Object f2265c;

    /* renamed from: d */
    public String f2266d;

    /* renamed from: e */
    public String f2267e = "UTF-8";

    /* renamed from: f */
    public int f2268f = 1;

    /* renamed from: g */
    public C1569b f2269g;

    public C1374c() {
        this.f2278i = 27;
        this.f2279j = 20;
    }

    /* renamed from: a */
    public final void mo14831a() {
        this.f2264b = 64;
    }

    /* renamed from: a */
    public void mo14832a(int i) {
        this.f2268f = i;
    }

    /* renamed from: a */
    public void mo14833a(C1569b bVar) {
        this.f2269g = bVar;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        int i;
        this.f2263a = C1177f.m1339c(bArr, 0);
        this.f2264b = bArr[2] & 192;
        this.f2267e = mo14838a(bArr[2]);
        int i2 = 0;
        int i3 = 3;
        while (true) {
            i = i2 | (bArr[i3] & ByteCompanionObject.MAX_VALUE);
            if ((bArr[i3] & 128) == 0) {
                break;
            }
            i2 = i << 7;
            i3++;
        }
        int i4 = i3 + 1;
        if (i > 0) {
            if (this.f2264b == 192) {
                this.f2265c = new byte[i];
                System.arraycopy(bArr, i4, this.f2265c, 0, i);
            } else {
                try {
                    this.f2265c = new String(bArr, i4, i, this.f2267e);
                } catch (Exception unused) {
                }
            }
        }
        int i5 = i4 + i;
        byte b = bArr[i5] & UByte.MAX_VALUE;
        int i6 = i5 + 1;
        if (bArr.length > i6) {
            try {
                this.f2266d = new String(bArr, i6, b, this.f2267e);
            } catch (Exception unused2) {
            }
        }
    }

    /* renamed from: c */
    public int mo14834c() {
        return this.f2268f;
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte[] bArr = null;
        try {
            byte[] bytes = this.f2266d.getBytes(this.f2267e);
            byte[] bytes2 = !"".equals(this.f2265c) ? this.f2264b == 192 ? (byte[]) this.f2265c : ((String) this.f2265c).getBytes(this.f2267e) : null;
            int length = bytes2 == null ? 0 : bytes2.length;
            byte[] a = C1177f.m1331a(length);
            bArr = new byte[(a.length + 4 + length + bytes.length)];
            int b = C1177f.m1334b(this.f2263a, bArr, 0);
            int c = b + C1177f.m1338c(this.f2264b | mo14837a(this.f2267e), bArr, b);
            int a2 = c + C1177f.m1327a(a, 0, bArr, c, a.length);
            if (length > 0) {
                a2 += C1177f.m1327a(bytes2, 0, bArr, a2, length);
            }
            C1177f.m1327a(bytes, 0, bArr, a2 + C1177f.m1338c(bytes.length, bArr, a2), bytes.length);
        } catch (Exception unused) {
        }
        if (bArr != null && bArr.length >= 512) {
            this.f2279j = (byte) (this.f2279j | 128);
        }
        return bArr;
    }

    /* renamed from: e */
    public C1569b mo14835e() {
        return this.f2269g;
    }
}
