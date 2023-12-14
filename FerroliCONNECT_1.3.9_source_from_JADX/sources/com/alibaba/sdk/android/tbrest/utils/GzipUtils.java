package com.alibaba.sdk.android.tbrest.utils;

public class GzipUtils {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.util.zip.GZIPOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: byte[]} */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0040 A[SYNTHETIC, Splitter:B:29:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004a A[SYNTHETIC, Splitter:B:34:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0052 A[SYNTHETIC, Splitter:B:40:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x005c A[SYNTHETIC, Splitter:B:45:0x005c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzip(byte[] r4) {
        /*
            if (r4 == 0) goto L_0x0065
            int r0 = r4.length
            if (r0 != 0) goto L_0x0007
            goto L_0x0065
        L_0x0007:
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            r1.<init>()     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            int r3 = r4.length     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0032, all -> 0x0030 }
            r2.write(r4)     // Catch:{ Exception -> 0x002e }
            r2.finish()     // Catch:{ Exception -> 0x002e }
            byte[] r0 = r1.toByteArray()     // Catch:{ Exception -> 0x002e }
            r2.close()     // Catch:{ IOException -> 0x0021 }
            goto L_0x0025
        L_0x0021:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0025:
            r1.close()     // Catch:{ IOException -> 0x0029 }
            goto L_0x004d
        L_0x0029:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x004d
        L_0x002e:
            r4 = move-exception
            goto L_0x003b
        L_0x0030:
            r4 = move-exception
            goto L_0x0050
        L_0x0032:
            r4 = move-exception
            r2 = r0
            goto L_0x003b
        L_0x0035:
            r4 = move-exception
            r1 = r0
            goto L_0x0050
        L_0x0038:
            r4 = move-exception
            r1 = r0
            r2 = r1
        L_0x003b:
            r4.printStackTrace()     // Catch:{ all -> 0x004e }
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0048:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ IOException -> 0x0029 }
        L_0x004d:
            return r0
        L_0x004e:
            r4 = move-exception
            r0 = r2
        L_0x0050:
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r0 = move-exception
            r0.printStackTrace()
        L_0x005a:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0064:
            throw r4
        L_0x0065:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.GzipUtils.gzip(byte[]):byte[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0060 A[SYNTHETIC, Splitter:B:42:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x006a A[SYNTHETIC, Splitter:B:47:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0074 A[SYNTHETIC, Splitter:B:52:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x007b A[SYNTHETIC, Splitter:B:57:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0085 A[SYNTHETIC, Splitter:B:62:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x008f A[SYNTHETIC, Splitter:B:67:0x008f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] unGzip(byte[] r7) {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0057, all -> 0x0051 }
            r1.<init>(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0051 }
            java.util.zip.GZIPInputStream r7 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r7.<init>(r1)     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
            r3.<init>()     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
        L_0x0014:
            int r4 = r2.length     // Catch:{ Exception -> 0x0041 }
            r5 = 0
            int r4 = r7.read(r2, r5, r4)     // Catch:{ Exception -> 0x0041 }
            r6 = -1
            if (r4 == r6) goto L_0x0021
            r3.write(r2, r5, r4)     // Catch:{ Exception -> 0x0041 }
            goto L_0x0014
        L_0x0021:
            r3.flush()     // Catch:{ Exception -> 0x0041 }
            byte[] r0 = r3.toByteArray()     // Catch:{ Exception -> 0x0041 }
            r3.close()     // Catch:{ Exception -> 0x002c }
            goto L_0x0030
        L_0x002c:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0030:
            r7.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0038:
            r1.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0077
        L_0x003c:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x0077
        L_0x0041:
            r2 = move-exception
            goto L_0x005b
        L_0x0043:
            r2 = move-exception
            r3 = r0
            r0 = r2
            goto L_0x0079
        L_0x0047:
            r2 = move-exception
            r3 = r0
            goto L_0x005b
        L_0x004a:
            r7 = move-exception
            r3 = r0
            goto L_0x0054
        L_0x004d:
            r2 = move-exception
            r7 = r0
            r3 = r7
            goto L_0x005b
        L_0x0051:
            r7 = move-exception
            r1 = r0
            r3 = r1
        L_0x0054:
            r0 = r7
            r7 = r3
            goto L_0x0079
        L_0x0057:
            r2 = move-exception
            r7 = r0
            r1 = r7
            r3 = r1
        L_0x005b:
            r2.printStackTrace()     // Catch:{ all -> 0x0078 }
            if (r3 == 0) goto L_0x0068
            r3.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x0068
        L_0x0064:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0068:
            if (r7 == 0) goto L_0x0072
            r7.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0072:
            if (r1 == 0) goto L_0x0077
            r1.close()     // Catch:{ IOException -> 0x003c }
        L_0x0077:
            return r0
        L_0x0078:
            r0 = move-exception
        L_0x0079:
            if (r3 == 0) goto L_0x0083
            r3.close()     // Catch:{ Exception -> 0x007f }
            goto L_0x0083
        L_0x007f:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0083:
            if (r7 == 0) goto L_0x008d
            r7.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x008d
        L_0x0089:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008d:
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ IOException -> 0x0093 }
            goto L_0x0097
        L_0x0093:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0097:
            goto L_0x0099
        L_0x0098:
            throw r0
        L_0x0099:
            goto L_0x0098
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.GzipUtils.unGzip(byte[]):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0029 A[SYNTHETIC, Splitter:B:17:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003a A[SYNTHETIC, Splitter:B:26:0x003a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzipAndRc4Bytes(java.lang.String r3) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0023 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0023 }
            java.lang.String r1 = "UTF-8"
            byte[] r3 = r3.getBytes(r1)     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.write(r3)     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.flush()     // Catch:{ IOException -> 0x001d, all -> 0x001b }
            r2.close()     // Catch:{ Exception -> 0x002c }
            goto L_0x002c
        L_0x001b:
            r3 = move-exception
            goto L_0x0038
        L_0x001d:
            r3 = move-exception
            r1 = r2
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            r2 = r1
            goto L_0x0038
        L_0x0023:
            r3 = move-exception
        L_0x0024:
            r3.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ Exception -> 0x002c }
        L_0x002c:
            byte[] r3 = r0.toByteArray()
            byte[] r3 = com.alibaba.sdk.android.tbrest.utils.RC4.rc4(r3)
            r0.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            return r3
        L_0x0038:
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ Exception -> 0x003d }
        L_0x003d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.GzipUtils.gzipAndRc4Bytes(java.lang.String):byte[]");
    }
}
