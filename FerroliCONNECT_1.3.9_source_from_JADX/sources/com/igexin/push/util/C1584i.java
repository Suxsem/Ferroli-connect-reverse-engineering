package com.igexin.push.util;

import java.io.UnsupportedEncodingException;

/* renamed from: com.igexin.push.util.i */
public class C1584i {

    /* renamed from: a */
    static final /* synthetic */ boolean f3018a = (!C1584i.class.desiredAssertionStatus());

    private C1584i() {
    }

    /* renamed from: a */
    public static byte[] m3246a(String str, int i) {
        return m3247a(str.getBytes(), i);
    }

    /* renamed from: a */
    public static byte[] m3247a(byte[] bArr, int i) {
        return m3248a(bArr, 0, bArr.length, i);
    }

    /* renamed from: a */
    public static byte[] m3248a(byte[] bArr, int i, int i2, int i3) {
        C1586k kVar = new C1586k(i3, new byte[((i2 * 3) / 4)]);
        if (!kVar.mo15217a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (kVar.f3020b == kVar.f3019a.length) {
            return kVar.f3019a;
        } else {
            byte[] bArr2 = new byte[kVar.f3020b];
            System.arraycopy(kVar.f3019a, 0, bArr2, 0, kVar.f3020b);
            return bArr2;
        }
    }

    /* renamed from: b */
    public static String m3249b(byte[] bArr, int i) {
        try {
            return new String(m3251c(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: b */
    public static byte[] m3250b(byte[] bArr, int i, int i2, int i3) {
        C1587l lVar = new C1587l(i3, (byte[]) null);
        int i4 = (i2 / 3) * 4;
        int i5 = 2;
        if (!lVar.f3030d) {
            int i6 = i2 % 3;
            if (i6 != 0) {
                if (i6 == 1) {
                    i4 += 2;
                } else if (i6 == 2) {
                    i4 += 3;
                }
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (lVar.f3031e && i2 > 0) {
            int i7 = ((i2 - 1) / 57) + 1;
            if (!lVar.f3032f) {
                i5 = 1;
            }
            i4 += i7 * i5;
        }
        lVar.f3019a = new byte[i4];
        lVar.mo15218a(bArr, i, i2, true);
        if (f3018a || lVar.f3020b == i4) {
            return lVar.f3019a;
        }
        throw new AssertionError();
    }

    /* renamed from: c */
    public static byte[] m3251c(byte[] bArr, int i) {
        return m3250b(bArr, 0, bArr.length, i);
    }
}
