package com.igexin.push.util;

/* renamed from: com.igexin.push.util.k */
class C1586k extends C1585j {

    /* renamed from: c */
    private static final int[] f3021c = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    /* renamed from: d */
    private static final int[] f3022d = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    /* renamed from: e */
    private int f3023e;

    /* renamed from: f */
    private int f3024f;

    /* renamed from: g */
    private final int[] f3025g;

    public C1586k(int i, byte[] bArr) {
        this.f3019a = bArr;
        this.f3025g = (i & 8) == 0 ? f3021c : f3022d;
        this.f3023e = 0;
        this.f3024f = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00bd, code lost:
        r0.f3023e = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c0, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ef, code lost:
        if (r20 != false) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f1, code lost:
        r0.f3023e = r5;
        r0.f3024f = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f5, code lost:
        r0.f3020b = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f7, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00f8, code lost:
        if (r5 == 0) goto L_0x011a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00fa, code lost:
        if (r5 == 1) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00fc, code lost:
        if (r5 == 2) goto L_0x0112;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00fe, code lost:
        if (r5 == 3) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0100, code lost:
        if (r5 == 4) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0103, code lost:
        r1 = r9 + 1;
        r6[r9] = (byte) (r8 >> 10);
        r9 = r1 + 1;
        r6[r1] = (byte) (r8 >> 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0112, code lost:
        r6[r9] = (byte) (r8 >> 4);
        r9 = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x011a, code lost:
        r0.f3023e = r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ef A[EDGE_INSN: B:70:0x00ef->B:54:0x00ef ?: BREAK  , SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo15217a(byte[] r17, int r18, int r19, boolean r20) {
        /*
            r16 = this;
            r0 = r16
            int r1 = r0.f3023e
            r2 = 0
            r3 = 6
            if (r1 != r3) goto L_0x0009
            return r2
        L_0x0009:
            int r4 = r19 + r18
            int r5 = r0.f3024f
            byte[] r6 = r0.f3019a
            int[] r7 = r0.f3025g
            r8 = r5
            r9 = 0
            r5 = r1
            r1 = r18
        L_0x0016:
            r10 = 3
            r11 = 4
            r12 = 2
            r13 = 1
            if (r1 >= r4) goto L_0x00ef
            if (r5 != 0) goto L_0x0063
        L_0x001e:
            int r14 = r1 + 4
            if (r14 > r4) goto L_0x005f
            byte r8 = r17[r1]
            r8 = r8 & 255(0xff, float:3.57E-43)
            r8 = r7[r8]
            int r8 = r8 << 18
            int r15 = r1 + 1
            byte r15 = r17[r15]
            r15 = r15 & 255(0xff, float:3.57E-43)
            r15 = r7[r15]
            int r15 = r15 << 12
            r8 = r8 | r15
            int r15 = r1 + 2
            byte r15 = r17[r15]
            r15 = r15 & 255(0xff, float:3.57E-43)
            r15 = r7[r15]
            int r15 = r15 << r3
            r8 = r8 | r15
            int r15 = r1 + 3
            byte r15 = r17[r15]
            r15 = r15 & 255(0xff, float:3.57E-43)
            r15 = r7[r15]
            r8 = r8 | r15
            if (r8 < 0) goto L_0x005f
            int r1 = r9 + 2
            byte r15 = (byte) r8
            r6[r1] = r15
            int r1 = r9 + 1
            int r15 = r8 >> 8
            byte r15 = (byte) r15
            r6[r1] = r15
            int r1 = r8 >> 16
            byte r1 = (byte) r1
            r6[r9] = r1
            int r9 = r9 + 3
            r1 = r14
            goto L_0x001e
        L_0x005f:
            if (r1 < r4) goto L_0x0063
            goto L_0x00ef
        L_0x0063:
            int r14 = r1 + 1
            byte r1 = r17[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r1 = r7[r1]
            r15 = 5
            r2 = -1
            if (r5 == 0) goto L_0x00df
            if (r5 == r13) goto L_0x00d3
            r13 = -2
            if (r5 == r12) goto L_0x00c1
            if (r5 == r10) goto L_0x008e
            if (r5 == r11) goto L_0x0082
            if (r5 == r15) goto L_0x007c
            goto L_0x00eb
        L_0x007c:
            if (r1 == r2) goto L_0x00eb
        L_0x007e:
            r0.f3023e = r3
            r10 = 0
            return r10
        L_0x0082:
            r10 = 0
            if (r1 != r13) goto L_0x0089
            int r5 = r5 + 1
            goto L_0x00eb
        L_0x0089:
            if (r1 == r2) goto L_0x00eb
            r0.f3023e = r3
            return r10
        L_0x008e:
            if (r1 < 0) goto L_0x00a9
            int r2 = r8 << 6
            r1 = r1 | r2
            int r2 = r9 + 2
            byte r5 = (byte) r1
            r6[r2] = r5
            int r2 = r9 + 1
            int r5 = r1 >> 8
            byte r5 = (byte) r5
            r6[r2] = r5
            int r2 = r1 >> 16
            byte r2 = (byte) r2
            r6[r9] = r2
            int r9 = r9 + 3
            r8 = r1
            r5 = 0
            goto L_0x00eb
        L_0x00a9:
            if (r1 != r13) goto L_0x00bb
            int r1 = r9 + 1
            int r2 = r8 >> 2
            byte r2 = (byte) r2
            r6[r1] = r2
            int r1 = r8 >> 10
            byte r1 = (byte) r1
            r6[r9] = r1
            int r9 = r9 + 2
            r5 = 5
            goto L_0x00eb
        L_0x00bb:
            if (r1 == r2) goto L_0x00eb
        L_0x00bd:
            r0.f3023e = r3
            r1 = 0
            return r1
        L_0x00c1:
            if (r1 < 0) goto L_0x00c4
            goto L_0x00d6
        L_0x00c4:
            if (r1 != r13) goto L_0x00d0
            int r1 = r9 + 1
            int r2 = r8 >> 4
            byte r2 = (byte) r2
            r6[r9] = r2
            r9 = r1
            r5 = 4
            goto L_0x00eb
        L_0x00d0:
            if (r1 == r2) goto L_0x00eb
            goto L_0x007e
        L_0x00d3:
            r10 = 0
            if (r1 < 0) goto L_0x00da
        L_0x00d6:
            int r2 = r8 << 6
            r1 = r1 | r2
            goto L_0x00e2
        L_0x00da:
            if (r1 == r2) goto L_0x00eb
            r0.f3023e = r3
            return r10
        L_0x00df:
            r10 = 0
            if (r1 < 0) goto L_0x00e6
        L_0x00e2:
            int r5 = r5 + 1
            r8 = r1
            goto L_0x00eb
        L_0x00e6:
            if (r1 == r2) goto L_0x00eb
            r0.f3023e = r3
            return r10
        L_0x00eb:
            r1 = r14
            r2 = 0
            goto L_0x0016
        L_0x00ef:
            if (r20 != 0) goto L_0x00f8
            r0.f3023e = r5
            r0.f3024f = r8
        L_0x00f5:
            r0.f3020b = r9
            return r13
        L_0x00f8:
            if (r5 == 0) goto L_0x011a
            if (r5 == r13) goto L_0x00bd
            if (r5 == r12) goto L_0x0112
            if (r5 == r10) goto L_0x0103
            if (r5 == r11) goto L_0x00bd
            goto L_0x011a
        L_0x0103:
            int r1 = r9 + 1
            int r2 = r8 >> 10
            byte r2 = (byte) r2
            r6[r9] = r2
            int r9 = r1 + 1
            int r2 = r8 >> 2
            byte r2 = (byte) r2
            r6[r1] = r2
            goto L_0x011a
        L_0x0112:
            int r1 = r9 + 1
            int r2 = r8 >> 4
            byte r2 = (byte) r2
            r6[r9] = r2
            r9 = r1
        L_0x011a:
            r0.f3023e = r5
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1586k.mo15217a(byte[], int, int, boolean):boolean");
    }
}
