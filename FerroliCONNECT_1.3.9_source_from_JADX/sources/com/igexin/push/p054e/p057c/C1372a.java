package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.igexin.push.e.c.a */
public class C1372a extends C1376e {

    /* renamed from: a */
    public int f2249a;

    /* renamed from: b */
    public int f2250b;

    /* renamed from: c */
    public Object f2251c;

    /* renamed from: d */
    public String f2252d;

    /* renamed from: e */
    public String f2253e = "UTF-8";

    /* renamed from: f */
    private int f2254f = 0;

    /* renamed from: g */
    private int f2255g = 0;

    public C1372a() {
        this.f2278i = 28;
    }

    /* renamed from: a */
    public int mo14824a() {
        return this.f2254f;
    }

    /* renamed from: a */
    public void mo14825a(int i) {
        this.f2254f = i;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        int i;
        this.f2249a = C1177f.m1339c(bArr, 0);
        this.f2250b = bArr[2] & 192;
        this.f2253e = mo14838a(bArr[2]);
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
            if (this.f2250b == 192) {
                this.f2251c = new byte[i];
                System.arraycopy(bArr, i4, this.f2251c, 0, i);
            } else {
                try {
                    this.f2251c = new String(bArr, i4, i, this.f2253e);
                } catch (Exception unused) {
                }
            }
        }
        int i5 = i4 + i;
        byte b = bArr[i5] & UByte.MAX_VALUE;
        int i6 = i5 + 1;
        if (bArr.length > i6) {
            try {
                this.f2252d = new String(bArr, i6, b, this.f2253e);
            } catch (Exception unused2) {
            }
        }
    }

    /* renamed from: b */
    public void mo14827b(int i) {
        this.f2255g = i;
    }

    /* renamed from: c */
    public int mo14828c() {
        return this.f2255g;
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte[] bArr = null;
        try {
            byte[] bytes = this.f2252d.getBytes(this.f2253e);
            byte[] bytes2 = !"".equals(this.f2251c) ? this.f2250b == 192 ? (byte[]) this.f2251c : ((String) this.f2251c).getBytes(this.f2253e) : null;
            int length = bytes2 == null ? 0 : bytes2.length;
            byte[] a = C1177f.m1331a(length);
            bArr = new byte[(a.length + 4 + length + bytes.length)];
            int b = C1177f.m1334b(this.f2249a, bArr, 0);
            int c = b + C1177f.m1338c(this.f2250b | mo14837a(this.f2253e), bArr, b);
            int a2 = c + C1177f.m1327a(a, 0, bArr, c, a.length);
            if (length > 0) {
                a2 += C1177f.m1327a(bytes2, 0, bArr, a2, length);
            }
            C1177f.m1327a(bytes, 0, bArr, a2 + C1177f.m1338c(bytes.length, bArr, a2), bytes.length);
        } catch (Exception unused) {
        }
        return bArr;
    }
}
