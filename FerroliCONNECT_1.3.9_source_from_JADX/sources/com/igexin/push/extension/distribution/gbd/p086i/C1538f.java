package com.igexin.push.extension.distribution.gbd.p086i;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.f */
class C1538f extends C1536d {

    /* renamed from: g */
    static final /* synthetic */ boolean f2944g = (!C1535c.class.desiredAssertionStatus());

    /* renamed from: h */
    private static final byte[] f2945h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    /* renamed from: i */
    private static final byte[] f2946i = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    /* renamed from: c */
    int f2947c;

    /* renamed from: d */
    public final boolean f2948d;

    /* renamed from: e */
    public final boolean f2949e;

    /* renamed from: f */
    public final boolean f2950f;

    /* renamed from: j */
    private final byte[] f2951j;

    /* renamed from: k */
    private int f2952k;

    /* renamed from: l */
    private final byte[] f2953l;

    public C1538f(int i, byte[] bArr) {
        this.f2937a = bArr;
        boolean z = true;
        this.f2948d = (i & 1) == 0;
        this.f2949e = (i & 2) == 0;
        this.f2950f = (i & 4) == 0 ? false : z;
        this.f2953l = (i & 8) == 0 ? f2945h : f2946i;
        this.f2951j = new byte[2];
        this.f2947c = 0;
        this.f2952k = this.f2949e ? 19 : -1;
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
    public boolean mo15171a(byte[] r18, int r19, int r20, boolean r21) {
        /*
            r17 = this;
            r0 = r17
            byte[] r1 = r0.f2953l
            byte[] r2 = r0.f2937a
            int r3 = r0.f2952k
            int r4 = r20 + r19
            int r5 = r0.f2947c
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
            byte[] r10 = r0.f2951j
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
            r0.f2947c = r7
            r11 = r5
            r5 = r10
            goto L_0x0056
        L_0x0034:
            int r5 = r19 + 2
            if (r5 > r4) goto L_0x0053
            byte[] r5 = r0.f2951j
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
            r0.f2947c = r7
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
            boolean r3 = r0.f2950f
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
            boolean r3 = r0.f2950f
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
            int r6 = r0.f2947c
            int r10 = r11 - r6
            int r15 = r4 + -1
            if (r10 != r15) goto L_0x013d
            if (r6 <= 0) goto L_0x00fb
            byte[] r6 = r0.f2951j
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
            int r8 = r0.f2947c
            int r8 = r8 - r7
            r0.f2947c = r8
            int r7 = r5 + 1
            int r8 = r6 >> 6
            r8 = r8 & 63
            byte r8 = r1[r8]
            r2[r5] = r8
            int r5 = r7 + 1
            r6 = r6 & 63
            byte r1 = r1[r6]
            r2[r7] = r1
            boolean r1 = r0.f2948d
            if (r1 == 0) goto L_0x0129
            int r1 = r5 + 1
            r6 = 61
            r2[r5] = r6
            int r5 = r1 + 1
            r2[r1] = r6
        L_0x0129:
            boolean r1 = r0.f2949e
            if (r1 == 0) goto L_0x01c4
            boolean r1 = r0.f2950f
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
            byte[] r6 = r0.f2951j
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
            int r10 = r0.f2947c
            if (r10 <= 0) goto L_0x0161
            byte[] r10 = r0.f2951j
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
            int r7 = r0.f2947c
            int r7 = r7 - r12
            r0.f2947c = r7
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
            boolean r1 = r0.f2948d
            if (r1 == 0) goto L_0x0196
            int r1 = r7 + 1
            r5 = 61
            r2[r7] = r5
            goto L_0x0197
        L_0x0196:
            r1 = r7
        L_0x0197:
            boolean r5 = r0.f2949e
            if (r5 == 0) goto L_0x01a9
            boolean r5 = r0.f2950f
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
            boolean r1 = r0.f2949e
            if (r1 == 0) goto L_0x01c4
            if (r5 <= 0) goto L_0x01c4
            r1 = 19
            if (r3 == r1) goto L_0x01c4
            boolean r1 = r0.f2950f
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
            boolean r1 = f2944g
            if (r1 != 0) goto L_0x01d3
            int r1 = r0.f2947c
            if (r1 != 0) goto L_0x01cd
            goto L_0x01d3
        L_0x01cd:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            throw r1
        L_0x01d3:
            boolean r1 = f2944g
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
            byte[] r1 = r0.f2951j
            int r2 = r0.f2947c
            int r4 = r2 + 1
            r0.f2947c = r4
            byte r4 = r18[r11]
            r1[r2] = r4
            goto L_0x020b
        L_0x01f1:
            int r4 = r4 - r8
            if (r11 != r4) goto L_0x020b
            byte[] r1 = r0.f2951j
            int r2 = r0.f2947c
            int r4 = r2 + 1
            r0.f2947c = r4
            byte r4 = r18[r11]
            r1[r2] = r4
            int r2 = r0.f2947c
            int r4 = r2 + 1
            r0.f2947c = r4
            int r11 = r11 + r9
            byte r4 = r18[r11]
            r1[r2] = r4
        L_0x020b:
            r0.f2938b = r5
            r0.f2952k = r3
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1538f.mo15171a(byte[], int, int, boolean):boolean");
    }
}
