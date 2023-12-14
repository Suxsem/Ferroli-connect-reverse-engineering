package com.p092ta.utdid2.p097a.p098a;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;

/* renamed from: com.ta.utdid2.a.a.a */
public class C1983a {

    /* renamed from: d */
    static final /* synthetic */ boolean f3170d = (!C1983a.class.desiredAssertionStatus());

    /* renamed from: com.ta.utdid2.a.a.a$a */
    static abstract class C1984a {

        /* renamed from: a */
        public byte[] f3171a;

        /* renamed from: b */
        public int f3172b;

        C1984a() {
        }
    }

    public static String encodeToString(byte[] bArr, int i) {
        try {
            return new String(encode(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    @SuppressLint({"Assert"})
    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        C1985b bVar = new C1985b(i3, (byte[]) null);
        int i4 = (i2 / 3) * 4;
        int i5 = 2;
        if (!bVar.f3178e) {
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
        if (bVar.f3180f && i2 > 0) {
            int i7 = ((i2 - 1) / 57) + 1;
            if (!bVar.f3181g) {
                i5 = 1;
            }
            i4 += i7 * i5;
        }
        bVar.f3171a = new byte[i4];
        bVar.mo18138a(bArr, i, i2, true);
        if (f3170d || bVar.f3172b == i4) {
            return bVar.f3171a;
        }
        throw new AssertionError();
    }

    /* renamed from: com.ta.utdid2.a.a.a$b */
    static class C1985b extends C1984a {

        /* renamed from: b */
        private static final byte[] f3173b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

        /* renamed from: c */
        private static final byte[] f3174c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

        /* renamed from: d */
        static final /* synthetic */ boolean f3175d = (!C1983a.class.desiredAssertionStatus());

        /* renamed from: c */
        int f3176c;
        private int count;

        /* renamed from: d */
        private final byte[] f3177d;

        /* renamed from: e */
        public final boolean f3178e;

        /* renamed from: e */
        private final byte[] f3179e;

        /* renamed from: f */
        public final boolean f3180f;

        /* renamed from: g */
        public final boolean f3181g;

        public C1985b(int i, byte[] bArr) {
            this.f3171a = bArr;
            boolean z = true;
            this.f3178e = (i & 1) == 0;
            this.f3180f = (i & 2) == 0;
            this.f3181g = (i & 4) == 0 ? false : z;
            this.f3179e = (i & 8) == 0 ? f3173b : f3174c;
            this.f3177d = new byte[2];
            this.f3176c = 0;
            this.count = this.f3180f ? 19 : -1;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0097  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x00eb  */
        /* JADX WARNING: Removed duplicated region for block: B:90:0x01e0  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x00e9 A[SYNTHETIC] */
        /* renamed from: a */
        public boolean mo18138a(byte[] r18, int r19, int r20, boolean r21) {
            /*
                r17 = this;
                r0 = r17
                byte[] r1 = r0.f3179e
                byte[] r2 = r0.f3171a
                int r3 = r0.count
                int r4 = r20 + r19
                int r5 = r0.f3176c
                r6 = -1
                r7 = 0
                r8 = 2
                r9 = 1
                if (r5 == 0) goto L_0x0053
                if (r5 == r9) goto L_0x0034
                if (r5 == r8) goto L_0x0017
                goto L_0x0053
            L_0x0017:
                int r5 = r19 + 1
                if (r5 > r4) goto L_0x0053
                byte[] r10 = r0.f3177d
                byte r11 = r10[r7]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                byte r10 = r10[r9]
                r10 = r10 & 255(0xff, float:3.57E-43)
                int r10 = r10 << 8
                r10 = r10 | r11
                byte r11 = r18[r19]
                r11 = r11 & 255(0xff, float:3.57E-43)
                r10 = r10 | r11
                r0.f3176c = r7
                r11 = r5
                r5 = r10
                goto L_0x0056
            L_0x0034:
                int r5 = r19 + 2
                if (r5 > r4) goto L_0x0053
                byte[] r5 = r0.f3177d
                byte r5 = r5[r7]
                r5 = r5 & 255(0xff, float:3.57E-43)
                int r5 = r5 << 16
                int r10 = r19 + 1
                byte r11 = r18[r19]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 8
                r5 = r5 | r11
                int r11 = r10 + 1
                byte r10 = r18[r10]
                r10 = r10 & 255(0xff, float:3.57E-43)
                r5 = r5 | r10
                r0.f3176c = r7
                goto L_0x0056
            L_0x0053:
                r11 = r19
                r5 = -1
            L_0x0056:
                r12 = 4
                r13 = 13
                r14 = 10
                if (r5 == r6) goto L_0x0092
                int r6 = r5 >> 18
                r6 = r6 & 63
                byte r6 = r1[r6]
                r2[r7] = r6
                int r6 = r5 >> 12
                r6 = r6 & 63
                byte r6 = r1[r6]
                r2[r9] = r6
                int r6 = r5 >> 6
                r6 = r6 & 63
                byte r6 = r1[r6]
                r2[r8] = r6
                r5 = r5 & 63
                byte r5 = r1[r5]
                r6 = 3
                r2[r6] = r5
                int r3 = r3 + -1
                if (r3 != 0) goto L_0x0090
                boolean r3 = r0.f3181g
                if (r3 == 0) goto L_0x0088
                r3 = 5
                r2[r12] = r13
                goto L_0x0089
            L_0x0088:
                r3 = 4
            L_0x0089:
                int r5 = r3 + 1
                r2[r3] = r14
            L_0x008d:
                r3 = 19
                goto L_0x0093
            L_0x0090:
                r5 = 4
                goto L_0x0093
            L_0x0092:
                r5 = 0
            L_0x0093:
                int r6 = r11 + 3
                if (r6 > r4) goto L_0x00e9
                byte r15 = r18[r11]
                r15 = r15 & 255(0xff, float:3.57E-43)
                int r15 = r15 << 16
                int r16 = r11 + 1
                byte r10 = r18[r16]
                r10 = r10 & 255(0xff, float:3.57E-43)
                int r10 = r10 << 8
                r10 = r10 | r15
                int r11 = r11 + 2
                byte r11 = r18[r11]
                r11 = r11 & 255(0xff, float:3.57E-43)
                r10 = r10 | r11
                int r11 = r10 >> 18
                r11 = r11 & 63
                byte r11 = r1[r11]
                r2[r5] = r11
                int r11 = r5 + 1
                int r15 = r10 >> 12
                r15 = r15 & 63
                byte r15 = r1[r15]
                r2[r11] = r15
                int r11 = r5 + 2
                int r15 = r10 >> 6
                r15 = r15 & 63
                byte r15 = r1[r15]
                r2[r11] = r15
                int r11 = r5 + 3
                r10 = r10 & 63
                byte r10 = r1[r10]
                r2[r11] = r10
                int r5 = r5 + 4
                int r3 = r3 + -1
                if (r3 != 0) goto L_0x00e7
                boolean r3 = r0.f3181g
                if (r3 == 0) goto L_0x00e0
                int r3 = r5 + 1
                r2[r5] = r13
                goto L_0x00e1
            L_0x00e0:
                r3 = r5
            L_0x00e1:
                int r5 = r3 + 1
                r2[r3] = r14
                r11 = r6
                goto L_0x008d
            L_0x00e7:
                r11 = r6
                goto L_0x0093
            L_0x00e9:
                if (r21 == 0) goto L_0x01e0
                int r6 = r0.f3176c
                int r10 = r11 - r6
                int r15 = r4 + -1
                if (r10 != r15) goto L_0x013d
                if (r6 <= 0) goto L_0x00fb
                byte[] r6 = r0.f3177d
                byte r6 = r6[r7]
                r7 = 1
                goto L_0x0101
            L_0x00fb:
                int r6 = r11 + 1
                byte r8 = r18[r11]
                r11 = r6
                r6 = r8
            L_0x0101:
                r6 = r6 & 255(0xff, float:3.57E-43)
                int r6 = r6 << r12
                int r8 = r0.f3176c
                int r8 = r8 - r7
                r0.f3176c = r8
                int r7 = r5 + 1
                int r8 = r6 >> 6
                r8 = r8 & 63
                byte r8 = r1[r8]
                r2[r5] = r8
                int r5 = r7 + 1
                r6 = r6 & 63
                byte r1 = r1[r6]
                r2[r7] = r1
                boolean r1 = r0.f3178e
                if (r1 == 0) goto L_0x0129
                int r1 = r5 + 1
                r6 = 61
                r2[r5] = r6
                int r5 = r1 + 1
                r2[r1] = r6
            L_0x0129:
                boolean r1 = r0.f3180f
                if (r1 == 0) goto L_0x01c4
                boolean r1 = r0.f3181g
                if (r1 == 0) goto L_0x0136
                int r1 = r5 + 1
                r2[r5] = r13
                goto L_0x0137
            L_0x0136:
                r1 = r5
            L_0x0137:
                int r5 = r1 + 1
                r2[r1] = r14
                goto L_0x01c4
            L_0x013d:
                int r10 = r11 - r6
                int r12 = r4 + -2
                if (r10 != r12) goto L_0x01ac
                if (r6 <= r9) goto L_0x014b
                byte[] r6 = r0.f3177d
                byte r6 = r6[r7]
                r7 = 1
                goto L_0x0151
            L_0x014b:
                int r6 = r11 + 1
                byte r10 = r18[r11]
                r11 = r6
                r6 = r10
            L_0x0151:
                r6 = r6 & 255(0xff, float:3.57E-43)
                int r6 = r6 << r14
                int r10 = r0.f3176c
                if (r10 <= 0) goto L_0x0161
                byte[] r10 = r0.f3177d
                int r12 = r7 + 1
                byte r7 = r10[r7]
                r10 = r11
                r11 = r7
                goto L_0x0166
            L_0x0161:
                int r10 = r11 + 1
                byte r11 = r18[r11]
                r12 = r7
            L_0x0166:
                r7 = r11 & 255(0xff, float:3.57E-43)
                int r7 = r7 << r8
                r6 = r6 | r7
                int r7 = r0.f3176c
                int r7 = r7 - r12
                r0.f3176c = r7
                int r7 = r5 + 1
                int r8 = r6 >> 12
                r8 = r8 & 63
                byte r8 = r1[r8]
                r2[r5] = r8
                int r5 = r7 + 1
                int r8 = r6 >> 6
                r8 = r8 & 63
                byte r8 = r1[r8]
                r2[r7] = r8
                int r7 = r5 + 1
                r6 = r6 & 63
                byte r1 = r1[r6]
                r2[r5] = r1
                boolean r1 = r0.f3178e
                if (r1 == 0) goto L_0x0196
                int r1 = r7 + 1
                r5 = 61
                r2[r7] = r5
                goto L_0x0197
            L_0x0196:
                r1 = r7
            L_0x0197:
                boolean r5 = r0.f3180f
                if (r5 == 0) goto L_0x01a9
                boolean r5 = r0.f3181g
                if (r5 == 0) goto L_0x01a4
                int r5 = r1 + 1
                r2[r1] = r13
                r1 = r5
            L_0x01a4:
                int r5 = r1 + 1
                r2[r1] = r14
                r1 = r5
            L_0x01a9:
                r5 = r1
                r11 = r10
                goto L_0x01c4
            L_0x01ac:
                boolean r1 = r0.f3180f
                if (r1 == 0) goto L_0x01c4
                if (r5 <= 0) goto L_0x01c4
                r1 = 19
                if (r3 == r1) goto L_0x01c4
                boolean r1 = r0.f3181g
                if (r1 == 0) goto L_0x01bf
                int r1 = r5 + 1
                r2[r5] = r13
                goto L_0x01c0
            L_0x01bf:
                r1 = r5
            L_0x01c0:
                int r5 = r1 + 1
                r2[r1] = r14
            L_0x01c4:
                boolean r1 = f3175d
                if (r1 != 0) goto L_0x01d3
                int r1 = r0.f3176c
                if (r1 != 0) goto L_0x01cd
                goto L_0x01d3
            L_0x01cd:
                java.lang.AssertionError r1 = new java.lang.AssertionError
                r1.<init>()
                throw r1
            L_0x01d3:
                boolean r1 = f3175d
                if (r1 != 0) goto L_0x020b
                if (r11 != r4) goto L_0x01da
                goto L_0x020b
            L_0x01da:
                java.lang.AssertionError r1 = new java.lang.AssertionError
                r1.<init>()
                throw r1
            L_0x01e0:
                int r1 = r4 + -1
                if (r11 != r1) goto L_0x01f1
                byte[] r1 = r0.f3177d
                int r2 = r0.f3176c
                int r4 = r2 + 1
                r0.f3176c = r4
                byte r4 = r18[r11]
                r1[r2] = r4
                goto L_0x020b
            L_0x01f1:
                int r4 = r4 - r8
                if (r11 != r4) goto L_0x020b
                byte[] r1 = r0.f3177d
                int r2 = r0.f3176c
                int r4 = r2 + 1
                r0.f3176c = r4
                byte r4 = r18[r11]
                r1[r2] = r4
                int r2 = r0.f3176c
                int r4 = r2 + 1
                r0.f3176c = r4
                int r11 = r11 + r9
                byte r4 = r18[r11]
                r1[r2] = r4
            L_0x020b:
                r0.f3172b = r5
                r0.count = r3
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.p092ta.utdid2.p097a.p098a.C1983a.C1985b.mo18138a(byte[], int, int, boolean):boolean");
        }
    }

    private C1983a() {
    }
}
