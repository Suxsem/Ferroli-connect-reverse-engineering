package com.igexin.push.extension.distribution.gbd.p086i;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import kotlin.UByte;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.c */
public class C1535c {

    /* renamed from: a */
    static final /* synthetic */ boolean f2936a = (!C1535c.class.desiredAssertionStatus());

    private C1535c() {
    }

    /* renamed from: a */
    public static String m2985a(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (i < bArr.length) {
            int i4 = i;
            int i5 = 0;
            int i6 = 0;
            while (i5 < 3 && i4 < bArr.length) {
                i6 |= (bArr[i4] & UByte.MAX_VALUE) << (16 - (i5 << 3));
                i5++;
                i4++;
            }
            if (i2 > 0 && i3 == i2) {
                sb.append("\r\n");
                i3 = 0;
            }
            char charAt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((i6 << 8) >>> 26);
            char charAt2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((i6 << 14) >>> 26);
            char c = '=';
            char charAt3 = i5 < 2 ? '=' : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((i6 << 20) >>> 26);
            if (i5 >= 3) {
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((i6 << 26) >>> 26);
            }
            sb.append(charAt);
            sb.append(charAt2);
            sb.append(charAt3);
            sb.append(c);
            i3 += 4;
            i = i4;
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static byte[] m2986a(byte[] bArr, int i) {
        return m2987a(bArr, 0, bArr.length, i);
    }

    /* renamed from: a */
    public static byte[] m2987a(byte[] bArr, int i, int i2, int i3) {
        C1537e eVar = new C1537e(i3, new byte[((i2 * 3) / 4)]);
        if (!eVar.mo15170a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (eVar.f2938b == eVar.f2937a.length) {
            return eVar.f2937a;
        } else {
            byte[] bArr2 = new byte[eVar.f2938b];
            System.arraycopy(eVar.f2937a, 0, bArr2, 0, eVar.f2938b);
            return bArr2;
        }
    }

    /* renamed from: a */
    public static byte[] m2988a(char[] cArr, int i) {
        int i2;
        if (cArr == null || cArr.length == 0) {
            return null;
        }
        char[] cArr2 = new char[4];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(cArr.length >> 1);
        while (i < cArr.length) {
            int i3 = 0;
            while (i < cArr.length) {
                int i4 = i + 1;
                char c = cArr[i];
                if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c) != -1 || c == '=') {
                    cArr2[i3] = c;
                    i3++;
                } else if (!(c == 13 || c == 10)) {
                    throw new IOException("bad BASE 64 In->");
                }
                if (i3 >= 4) {
                    int i5 = 0;
                    boolean z = false;
                    while (i5 < 4) {
                        if (cArr2[i5] == '=' || !z) {
                            if (!z && cArr2[i5] == '=') {
                                z = true;
                            }
                            i5++;
                        } else {
                            throw new IOException("bad BASE 64 In->");
                        }
                    }
                    if (cArr2[3] != '=') {
                        i2 = 3;
                    } else if (i4 >= cArr.length) {
                        i2 = cArr2[2] == '=' ? 1 : 2;
                    } else {
                        throw new IOException("bad BASE 64 In->");
                    }
                    int i6 = 0;
                    for (int i7 = 0; i7 < 4; i7++) {
                        if (cArr2[i7] != '=') {
                            i6 |= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(cArr2[i7]) << ((3 - i7) * 6);
                        }
                    }
                    for (int i8 = 0; i8 < i2; i8++) {
                        byteArrayOutputStream.write((i6 >>> ((2 - i8) * 8)) & 255);
                    }
                    i = i4;
                } else {
                    i = i4;
                }
            }
            if (i3 <= 0) {
                return byteArrayOutputStream.toByteArray();
            }
            throw new IOException("bad BASE 64 In->");
        }
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }

    /* renamed from: b */
    public static String m2989b(byte[] bArr, int i) {
        try {
            return new String(m2991c(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: b */
    public static byte[] m2990b(byte[] bArr, int i, int i2, int i3) {
        C1538f fVar = new C1538f(i3, (byte[]) null);
        int i4 = (i2 / 3) * 4;
        int i5 = 2;
        if (!fVar.f2948d) {
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
        if (fVar.f2949e && i2 > 0) {
            int i7 = ((i2 - 1) / 57) + 1;
            if (!fVar.f2950f) {
                i5 = 1;
            }
            i4 += i7 * i5;
        }
        fVar.f2937a = new byte[i4];
        fVar.mo15171a(bArr, i, i2, true);
        if (f2936a || fVar.f2938b == i4) {
            return fVar.f2937a;
        }
        throw new AssertionError();
    }

    /* renamed from: c */
    public static byte[] m2991c(byte[] bArr, int i) {
        return m2990b(bArr, 0, bArr.length, i);
    }
}
