package com.alibaba.sdk.android.ams.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.UByte;

/* renamed from: com.alibaba.sdk.android.ams.common.util.b */
public class C0682b {

    /* renamed from: a */
    private static final byte[] f814a = {Byte.MIN_VALUE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /* renamed from: b */
    private static final C0682b f815b = new C0682b();

    /* renamed from: c */
    private static final char[] f816c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: d */
    private long[] f817d = new long[4];

    /* renamed from: e */
    private long[] f818e = new long[2];

    /* renamed from: f */
    private byte[] f819f = new byte[64];

    /* renamed from: g */
    private String f820g;

    /* renamed from: h */
    private byte[] f821h = new byte[16];

    private C0682b() {
        m527b();
    }

    /* renamed from: a */
    public static long m515a(byte b) {
        if (b < 0) {
            b &= UByte.MAX_VALUE;
        }
        return (long) b;
    }

    /* renamed from: a */
    private long m516a(long j, long j2, long j3) {
        return ((j ^ -1) & j3) | (j2 & j);
    }

    /* renamed from: a */
    private long m517a(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        long j8 = j6;
        int a = (int) (m516a(j2, j3, j4) + j5 + j7 + j);
        return ((long) ((a >>> ((int) (32 - j8))) | (a << ((int) j8)))) + j2;
    }

    /* renamed from: a */
    public static C0682b m518a() {
        return f815b;
    }

    /* renamed from: a */
    public static String m519a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(f816c[(bArr[i] & 240) >>> 4]);
            sb.append(f816c[bArr[i] & 15]);
        }
        return sb.toString();
    }

    /* renamed from: a */
    private void m520a(byte[] bArr, int i) {
        int i2;
        byte[] bArr2 = new byte[64];
        long[] jArr = this.f818e;
        int i3 = ((int) (jArr[0] >>> 3)) & 63;
        long j = (long) (i << 3);
        long j2 = jArr[0] + j;
        jArr[0] = j2;
        if (j2 < j) {
            jArr[1] = jArr[1] + 1;
        }
        long[] jArr2 = this.f818e;
        jArr2[1] = jArr2[1] + ((long) (i >>> 29));
        int i4 = 64 - i3;
        if (i >= i4) {
            m521a(this.f819f, bArr, i3, 0, i4);
            m528b(this.f819f);
            while (i4 + 63 < i) {
                m521a(bArr2, bArr, 0, i4, 64);
                m528b(bArr2);
                i4 += 64;
            }
            i2 = i4;
            i3 = 0;
        } else {
            i2 = 0;
        }
        m521a(this.f819f, bArr, i3, i2, i - i2);
    }

    /* renamed from: a */
    private void m521a(byte[] bArr, byte[] bArr2, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i + i4] = bArr2[i2 + i4];
        }
    }

    /* renamed from: a */
    private void m522a(byte[] bArr, long[] jArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3 += 4) {
            bArr[i3] = (byte) ((int) (jArr[i2] & 255));
            bArr[i3 + 1] = (byte) ((int) ((jArr[i2] >>> 8) & 255));
            bArr[i3 + 2] = (byte) ((int) ((jArr[i2] >>> 16) & 255));
            bArr[i3 + 3] = (byte) ((int) (255 & (jArr[i2] >>> 24)));
            i2++;
        }
    }

    /* renamed from: a */
    private void m523a(long[] jArr, byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3 += 4) {
            jArr[i2] = m515a(bArr[i3]) | (m515a(bArr[i3 + 1]) << 8) | (m515a(bArr[i3 + 2]) << 16) | (m515a(bArr[i3 + 3]) << 24);
            i2++;
        }
    }

    /* renamed from: b */
    private long m524b(long j, long j2, long j3) {
        return (j & j3) | (j2 & (j3 ^ -1));
    }

    /* renamed from: b */
    private long m525b(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        long j8 = j6;
        int b = (int) (m524b(j2, j3, j4) + j5 + j7 + j);
        return ((long) ((b >>> ((int) (32 - j8))) | (b << ((int) j8)))) + j2;
    }

    /* renamed from: b */
    public static String m526b(byte b) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        return new String(new char[]{cArr[(b >>> 4) & 15], cArr[b & 15]});
    }

    /* renamed from: b */
    private void m527b() {
        long[] jArr = this.f818e;
        jArr[0] = 0;
        jArr[1] = 0;
        long[] jArr2 = this.f817d;
        jArr2[0] = 1732584193;
        jArr2[1] = 4023233417L;
        jArr2[2] = 2562383102L;
        jArr2[3] = 271733878;
    }

    /* renamed from: b */
    private void m528b(byte[] bArr) {
        long[] jArr = this.f817d;
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long[] jArr2 = new long[16];
        m523a(jArr2, bArr, 64);
        long[] jArr3 = jArr2;
        long a = m517a(j, j2, j3, j4, jArr2[0], 7, 3614090360L);
        long a2 = m517a(j4, a, j2, j3, jArr3[1], 12, 3905402710L);
        long a3 = m517a(j3, a2, a, j2, jArr3[2], 17, 606105819);
        long a4 = m517a(j2, a3, a2, a, jArr3[3], 22, 3250441966L);
        long a5 = m517a(a, a4, a3, a2, jArr3[4], 7, 4118548399L);
        long a6 = m517a(a2, a5, a4, a3, jArr3[5], 12, 1200080426);
        long a7 = m517a(a3, a6, a5, a4, jArr3[6], 17, 2821735955L);
        long a8 = m517a(a4, a7, a6, a5, jArr3[7], 22, 4249261313L);
        long a9 = m517a(a5, a8, a7, a6, jArr3[8], 7, 1770035416);
        long a10 = m517a(a6, a9, a8, a7, jArr3[9], 12, 2336552879L);
        long a11 = m517a(a7, a10, a9, a8, jArr3[10], 17, 4294925233L);
        long a12 = m517a(a8, a11, a10, a9, jArr3[11], 22, 2304563134L);
        long a13 = m517a(a9, a12, a11, a10, jArr3[12], 7, 1804603682);
        long a14 = m517a(a10, a13, a12, a11, jArr3[13], 12, 4254626195L);
        long a15 = m517a(a11, a14, a13, a12, jArr3[14], 17, 2792965006L);
        long a16 = m517a(a12, a15, a14, a13, jArr3[15], 22, 1236535329);
        long b = m525b(a13, a16, a15, a14, jArr3[1], 5, 4129170786L);
        long b2 = m525b(a14, b, a16, a15, jArr3[6], 9, 3225465664L);
        long b3 = m525b(a15, b2, b, a16, jArr3[11], 14, 643717713);
        long b4 = m525b(a16, b3, b2, b, jArr3[0], 20, 3921069994L);
        long b5 = m525b(b, b4, b3, b2, jArr3[5], 5, 3593408605L);
        long b6 = m525b(b2, b5, b4, b3, jArr3[10], 9, 38016083);
        long b7 = m525b(b3, b6, b5, b4, jArr3[15], 14, 3634488961L);
        long b8 = m525b(b4, b7, b6, b5, jArr3[4], 20, 3889429448L);
        long b9 = m525b(b5, b8, b7, b6, jArr3[9], 5, 568446438);
        long b10 = m525b(b6, b9, b8, b7, jArr3[14], 9, 3275163606L);
        long b11 = m525b(b7, b10, b9, b8, jArr3[3], 14, 4107603335L);
        long b12 = m525b(b8, b11, b10, b9, jArr3[8], 20, 1163531501);
        long b13 = m525b(b9, b12, b11, b10, jArr3[13], 5, 2850285829L);
        long b14 = m525b(b10, b13, b12, b11, jArr3[2], 9, 4243563512L);
        long b15 = m525b(b11, b14, b13, b12, jArr3[7], 14, 1735328473);
        long b16 = m525b(b12, b15, b14, b13, jArr3[12], 20, 2368359562L);
        long c = m530c(b13, b16, b15, b14, jArr3[5], 4, 4294588738L);
        long c2 = m530c(b14, c, b16, b15, jArr3[8], 11, 2272392833L);
        long c3 = m530c(b15, c2, c, b16, jArr3[11], 16, 1839030562);
        long c4 = m530c(b16, c3, c2, c, jArr3[14], 23, 4259657740L);
        long c5 = m530c(c, c4, c3, c2, jArr3[1], 4, 2763975236L);
        long c6 = m530c(c2, c5, c4, c3, jArr3[4], 11, 1272893353);
        long c7 = m530c(c3, c6, c5, c4, jArr3[7], 16, 4139469664L);
        long c8 = m530c(c4, c7, c6, c5, jArr3[10], 23, 3200236656L);
        long c9 = m530c(c5, c8, c7, c6, jArr3[13], 4, 681279174);
        long c10 = m530c(c6, c9, c8, c7, jArr3[0], 11, 3936430074L);
        long c11 = m530c(c7, c10, c9, c8, jArr3[3], 16, 3572445317L);
        long c12 = m530c(c8, c11, c10, c9, jArr3[6], 23, 76029189);
        long c13 = m530c(c9, c12, c11, c10, jArr3[9], 4, 3654602809L);
        long c14 = m530c(c10, c13, c12, c11, jArr3[12], 11, 3873151461L);
        long c15 = m530c(c11, c14, c13, c12, jArr3[15], 16, 530742520);
        long c16 = m530c(c12, c15, c14, c13, jArr3[2], 23, 3299628645L);
        long d = m533d(c13, c16, c15, c14, jArr3[0], 6, 4096336452L);
        long d2 = m533d(c14, d, c16, c15, jArr3[7], 10, 1126891415);
        long d3 = m533d(c15, d2, d, c16, jArr3[14], 15, 2878612391L);
        long d4 = m533d(c16, d3, d2, d, jArr3[5], 21, 4237533241L);
        long d5 = m533d(d, d4, d3, d2, jArr3[12], 6, 1700485571);
        long d6 = m533d(d2, d5, d4, d3, jArr3[3], 10, 2399980690L);
        long d7 = m533d(d3, d6, d5, d4, jArr3[10], 15, 4293915773L);
        long d8 = m533d(d4, d7, d6, d5, jArr3[1], 21, 2240044497L);
        long d9 = m533d(d5, d8, d7, d6, jArr3[8], 6, 1873313359);
        long d10 = m533d(d6, d9, d8, d7, jArr3[15], 10, 4264355552L);
        long d11 = m533d(d7, d10, d9, d8, jArr3[6], 15, 2734768916L);
        long d12 = m533d(d8, d11, d10, d9, jArr3[13], 21, 1309151649);
        long d13 = m533d(d9, d12, d11, d10, jArr3[4], 6, 4149444226L);
        long d14 = m533d(d10, d13, d12, d11, jArr3[11], 10, 3174756917L);
        long d15 = m533d(d11, d14, d13, d12, jArr3[2], 15, 718787259);
        long d16 = m533d(d12, d15, d14, d13, jArr3[9], 21, 3951481745L);
        long[] jArr4 = this.f817d;
        jArr4[0] = jArr4[0] + d13;
        jArr4[1] = jArr4[1] + d16;
        jArr4[2] = jArr4[2] + d15;
        jArr4[3] = jArr4[3] + d14;
    }

    /* renamed from: c */
    private long m529c(long j, long j2, long j3) {
        return (j ^ j2) ^ j3;
    }

    /* renamed from: c */
    private long m530c(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        long j8 = j6;
        int c = (int) (m529c(j2, j3, j4) + j5 + j7 + j);
        return ((long) ((c >>> ((int) (32 - j8))) | (c << ((int) j8)))) + j2;
    }

    /* renamed from: c */
    private void m531c() {
        byte[] bArr = new byte[8];
        m522a(bArr, this.f818e, 8);
        int i = ((int) (this.f818e[0] >>> 3)) & 63;
        m520a(f814a, i < 56 ? 56 - i : 120 - i);
        m520a(bArr, 8);
        m522a(this.f821h, this.f817d, 16);
    }

    /* renamed from: d */
    private long m532d(long j, long j2, long j3) {
        return (j | (j3 ^ -1)) ^ j2;
    }

    /* renamed from: d */
    private long m533d(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        long j8 = j6;
        int d = (int) (m532d(j2, j3, j4) + j5 + j7 + j);
        return ((long) ((d >>> ((int) (32 - j8))) | (d << ((int) j8)))) + j2;
    }

    /* renamed from: a */
    public String mo9556a(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        messageDigest.update(str.getBytes(Charset.forName("UTF-8")));
        return m519a(messageDigest.digest());
    }

    /* renamed from: b */
    public String mo9557b(String str) {
        m527b();
        m520a(str.getBytes(Charset.forName("UTF-8")), str.length());
        m531c();
        this.f820g = "";
        for (int i = 0; i < 16; i++) {
            this.f820g += m526b(this.f821h[i]);
        }
        return this.f820g;
    }
}
