package com.igexin.p032b.p033a.p035b;

import anet.channel.util.HttpConstant;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.UByte;

/* renamed from: com.igexin.b.a.b.f */
public final class C1177f {
    /* renamed from: a */
    public static int m1324a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
        return 4;
    }

    /* renamed from: a */
    public static int m1325a(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) ((j >> 56) & 255));
        bArr[i + 1] = (byte) ((int) ((j >> 48) & 255));
        bArr[i + 2] = (byte) ((int) ((j >> 40) & 255));
        bArr[i + 3] = (byte) ((int) ((j >> 32) & 255));
        bArr[i + 4] = (byte) ((int) ((j >> 24) & 255));
        bArr[i + 5] = (byte) ((int) ((j >> 16) & 255));
        bArr[i + 6] = (byte) ((int) ((j >> 8) & 255));
        bArr[i + 7] = (byte) ((int) (j & 255));
        return 8;
    }

    /* renamed from: a */
    public static int m1326a(byte[] bArr, int i) {
        return bArr[i] & UByte.MAX_VALUE;
    }

    /* renamed from: a */
    public static int m1327a(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        System.arraycopy(bArr, i, bArr2, i2, i3);
        return i3;
    }

    /* renamed from: a */
    public static String m1328a(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        if (!strArr[0].equals("")) {
            sb.append(strArr[0]);
            sb.append(HttpConstant.SCHEME_SPLIT);
        }
        if (!strArr[1].equals("")) {
            sb.append(strArr[1]);
        }
        if (!strArr[2].equals("")) {
            sb.append(':');
            sb.append(strArr[2]);
        }
        if (!strArr[3].equals("")) {
            sb.append(strArr[3]);
            if (!strArr[3].equals("/")) {
                sb.append('/');
            }
        }
        if (!strArr[4].equals("")) {
            sb.append(strArr[4]);
        }
        if (!strArr[5].equals("")) {
            sb.append('?');
            sb.append(strArr[5]);
        }
        return sb.toString();
    }

    /* renamed from: a */
    private static void m1329a(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    public static void m1330a(InputStream inputStream, OutputStream outputStream, int i) {
        C1151a aVar = new C1151a(outputStream, i);
        m1329a(inputStream, (OutputStream) aVar);
        aVar.mo14218a();
    }

    /* renamed from: a */
    public static byte[] m1331a(int i) {
        int i2;
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        do {
            i2 = 24;
            i4 |= (i3 & 127) << 24;
            i3 >>>= 7;
            i5++;
            if (i3 > 0) {
                i4 = (i4 >>> 8) | Integer.MIN_VALUE;
                continue;
            }
        } while (i3 > 0);
        byte[] bArr = new byte[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            bArr[i6] = (byte) (i4 >>> i2);
            i2 -= 8;
        }
        return bArr;
    }

    /* renamed from: a */
    public static byte[] m1332a(byte[] bArr) {
        return m1340c(bArr);
    }

    /* renamed from: a */
    public static String[] m1333a(String str) {
        StringBuilder sb = new StringBuilder(str.toLowerCase());
        String[] strArr = new String[6];
        for (int i = 0; i < 6; i++) {
            strArr[i] = "";
        }
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            strArr[0] = str.substring(0, indexOf);
            sb.delete(0, indexOf + 1);
        } else if (indexOf == 0) {
            throw new IllegalArgumentException("url format error - protocol");
        }
        if (sb.length() >= 2 && sb.charAt(0) == '/' && sb.charAt(1) == '/') {
            sb.delete(0, 2);
            int indexOf2 = sb.toString().indexOf(47);
            if (indexOf2 < 0) {
                indexOf2 = sb.length();
            }
            if (indexOf2 != 0) {
                int lastIndexOf = sb.toString().lastIndexOf(58);
                if (lastIndexOf < 0) {
                    lastIndexOf = indexOf2;
                } else if (lastIndexOf <= indexOf2) {
                    strArr[2] = sb.toString().substring(lastIndexOf + 1, indexOf2);
                } else {
                    throw new IllegalArgumentException("url format error - port");
                }
                strArr[1] = sb.toString().substring(0, lastIndexOf);
                sb.delete(0, indexOf2);
            }
        }
        if (sb.length() > 0) {
            String sb2 = sb.toString();
            int lastIndexOf2 = sb2.lastIndexOf(47);
            if (lastIndexOf2 > 0) {
                strArr[3] = sb2.substring(0, lastIndexOf2);
            } else if (lastIndexOf2 == 0) {
                if (sb2.indexOf(63) <= 0) {
                    strArr[3] = sb2;
                    return strArr;
                }
                throw new IllegalArgumentException("url format error - path");
            }
            if (lastIndexOf2 < sb2.length() - 1) {
                String substring = sb2.substring(lastIndexOf2 + 1, sb2.length());
                int indexOf3 = substring.indexOf(63);
                if (indexOf3 >= 0) {
                    strArr[4] = substring.substring(0, indexOf3);
                    strArr[5] = substring.substring(indexOf3 + 1);
                } else {
                    strArr[4] = substring;
                }
            }
        } else {
            strArr[3] = "/";
        }
        return strArr;
    }

    /* renamed from: b */
    public static int m1334b(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 1] = (byte) (i & 255);
        return 2;
    }

    /* renamed from: b */
    public static short m1335b(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8));
    }

    /* renamed from: b */
    public static byte[] m1336b(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    /* renamed from: b */
    public static byte[] m1337b(byte[] bArr) {
        return m1342d(bArr);
    }

    /* renamed from: c */
    public static int m1338c(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        return 1;
    }

    /* renamed from: c */
    public static int m1339c(byte[] bArr, int i) {
        return (bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|4|5|6|7|8|23|(1:(0))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:9|(2:14|15)|16|17|18) */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002c, code lost:
        if (r2 != null) goto L_0x0015;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0018 */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0024 A[SYNTHETIC, Splitter:B:14:0x0024] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m1340c(byte[] r3) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x002b, all -> 0x0020 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x002b, all -> 0x0020 }
            r2.write(r3)     // Catch:{ Throwable -> 0x001e, all -> 0x001c }
            r2.finish()     // Catch:{ Throwable -> 0x001e, all -> 0x001c }
            byte[] r1 = r0.toByteArray()     // Catch:{ Throwable -> 0x001e, all -> 0x001c }
        L_0x0015:
            r2.close()     // Catch:{ Exception -> 0x0018 }
        L_0x0018:
            r0.close()     // Catch:{ Exception -> 0x002f }
            goto L_0x002f
        L_0x001c:
            r3 = move-exception
            goto L_0x0022
        L_0x001e:
            goto L_0x002c
        L_0x0020:
            r3 = move-exception
            r2 = r1
        L_0x0022:
            if (r2 == 0) goto L_0x0027
            r2.close()     // Catch:{ Exception -> 0x0027 }
        L_0x0027:
            r0.close()     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            throw r3
        L_0x002b:
            r2 = r1
        L_0x002c:
            if (r2 == 0) goto L_0x0018
            goto L_0x0015
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.C1177f.m1340c(byte[]):byte[]");
    }

    /* renamed from: d */
    public static int m1341d(byte[] bArr, int i) {
        return (bArr[i + 3] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i + 1] & UByte.MAX_VALUE) << 16) | ((bArr[i + 2] & UByte.MAX_VALUE) << 8);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(15:0|1|2|3|4|(3:5|6|(1:8)(1:44))|9|10|11|12|13|14|15|42|(1:(0))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:16|(2:25|26)|(2:29|30)|31|32|33) */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0051, code lost:
        if (r1 == null) goto L_0x0025;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0022 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0025 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0044 */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003a A[SYNTHETIC, Splitter:B:25:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A[SYNTHETIC, Splitter:B:29:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004c A[SYNTHETIC, Splitter:B:38:0x004c] */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m1342d(byte[] r6) {
        /*
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r6)
            r6 = 0
            java.util.zip.GZIPInputStream r1 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x0048, all -> 0x0034 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x0048, all -> 0x0034 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0032, all -> 0x002d }
            r2.<init>()     // Catch:{ Throwable -> 0x0032, all -> 0x002d }
        L_0x0010:
            int r3 = r1.read()     // Catch:{ Throwable -> 0x002b, all -> 0x0029 }
            r4 = -1
            if (r3 == r4) goto L_0x001b
            r2.write(r3)     // Catch:{ Throwable -> 0x002b, all -> 0x0029 }
            goto L_0x0010
        L_0x001b:
            byte[] r6 = r2.toByteArray()     // Catch:{ Throwable -> 0x002b, all -> 0x0029 }
            r2.close()     // Catch:{ Exception -> 0x0022 }
        L_0x0022:
            r1.close()     // Catch:{ Exception -> 0x0025 }
        L_0x0025:
            r0.close()     // Catch:{ Exception -> 0x0054 }
            goto L_0x0054
        L_0x0029:
            r6 = move-exception
            goto L_0x0038
        L_0x002b:
            goto L_0x004a
        L_0x002d:
            r2 = move-exception
            r5 = r2
            r2 = r6
            r6 = r5
            goto L_0x0038
        L_0x0032:
            r2 = r6
            goto L_0x004a
        L_0x0034:
            r1 = move-exception
            r2 = r6
            r6 = r1
            r1 = r2
        L_0x0038:
            if (r2 == 0) goto L_0x003f
            r2.close()     // Catch:{ Exception -> 0x003e }
            goto L_0x003f
        L_0x003e:
        L_0x003f:
            if (r1 == 0) goto L_0x0044
            r1.close()     // Catch:{ Exception -> 0x0044 }
        L_0x0044:
            r0.close()     // Catch:{ Exception -> 0x0047 }
        L_0x0047:
            throw r6
        L_0x0048:
            r1 = r6
            r2 = r1
        L_0x004a:
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ Exception -> 0x0050 }
            goto L_0x0051
        L_0x0050:
        L_0x0051:
            if (r1 == 0) goto L_0x0025
            goto L_0x0022
        L_0x0054:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.C1177f.m1342d(byte[]):byte[]");
    }

    /* renamed from: e */
    public static long m1343e(byte[] bArr, int i) {
        return (((long) bArr[i + 7]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i + 1]) & 255) << 48) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 6]) & 255) << 8);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0026 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0010 */
    /* renamed from: f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m1344f(byte[] r3, int r4) {
        /*
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r3)
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
            m1330a((java.io.InputStream) r0, (java.io.OutputStream) r3, (int) r4)     // Catch:{ Throwable -> 0x001a }
            r0.close()     // Catch:{ Throwable -> 0x0010 }
        L_0x0010:
            r3.close()     // Catch:{ Throwable -> 0x0013 }
        L_0x0013:
            byte[] r3 = r3.toByteArray()
            return r3
        L_0x0018:
            r4 = move-exception
            goto L_0x0023
        L_0x001a:
            r4 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ all -> 0x0018 }
            java.lang.String r2 = "Unexpected I/O error"
            r1.<init>(r2, r4)     // Catch:{ all -> 0x0018 }
            throw r1     // Catch:{ all -> 0x0018 }
        L_0x0023:
            r0.close()     // Catch:{ Throwable -> 0x0026 }
        L_0x0026:
            r3.close()     // Catch:{ Throwable -> 0x0029 }
        L_0x0029:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.C1177f.m1344f(byte[], int):byte[]");
    }
}
