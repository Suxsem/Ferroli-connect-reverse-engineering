package com.igexin.p030a;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.igexin.a.a */
public class C1117a implements C1134d {
    /* renamed from: a */
    private long m1157a(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    /* renamed from: a */
    private void m1158a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00de, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00df, code lost:
        r6 = null;
        r12 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00e8, code lost:
        r6 = null;
        r12 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00ee, code lost:
        r6 = null;
        r12 = null;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:13:0x002b, B:44:0x009d] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0106 A[SYNTHETIC, Splitter:B:90:0x0106] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14151a(android.content.Context r19, java.lang.String[] r20, java.lang.String r21, java.io.File r22, com.igexin.p030a.C1138h r23) {
        /*
            r18 = this;
            r1 = r18
            r0 = r20
            r2 = r21
            r3 = r22
            r4 = r23
            r5 = 0
            android.content.pm.ApplicationInfo r6 = r19.getApplicationInfo()     // Catch:{ all -> 0x0102 }
            r7 = 0
            r8 = 0
        L_0x0011:
            int r9 = r8 + 1
            r10 = 5
            r11 = 1
            if (r8 >= r10) goto L_0x0026
            java.util.zip.ZipFile r8 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x0024 }
            java.io.File r12 = new java.io.File     // Catch:{ IOException -> 0x0024 }
            java.lang.String r13 = r6.sourceDir     // Catch:{ IOException -> 0x0024 }
            r12.<init>(r13)     // Catch:{ IOException -> 0x0024 }
            r8.<init>(r12, r11)     // Catch:{ IOException -> 0x0024 }
            goto L_0x0027
        L_0x0024:
            r8 = r9
            goto L_0x0011
        L_0x0026:
            r8 = r5
        L_0x0027:
            if (r8 != 0) goto L_0x0037
            java.lang.String r0 = "FATAL! Couldn't find application APK!"
            r4.mo14178a((java.lang.String) r0)     // Catch:{ all -> 0x0034 }
            if (r8 == 0) goto L_0x0033
            r8.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0033:
            return
        L_0x0034:
            r0 = move-exception
            goto L_0x0104
        L_0x0037:
            r6 = 0
        L_0x0038:
            int r9 = r6 + 1
            if (r6 >= r10) goto L_0x00f7
            int r6 = r0.length     // Catch:{ all -> 0x0034 }
            r13 = r5
            r14 = r13
            r12 = 0
        L_0x0040:
            if (r12 >= r6) goto L_0x006c
            r13 = r0[r12]     // Catch:{ all -> 0x0034 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0034 }
            r14.<init>()     // Catch:{ all -> 0x0034 }
            java.lang.String r15 = "lib"
            r14.append(r15)     // Catch:{ all -> 0x0034 }
            char r15 = java.io.File.separatorChar     // Catch:{ all -> 0x0034 }
            r14.append(r15)     // Catch:{ all -> 0x0034 }
            r14.append(r13)     // Catch:{ all -> 0x0034 }
            char r13 = java.io.File.separatorChar     // Catch:{ all -> 0x0034 }
            r14.append(r13)     // Catch:{ all -> 0x0034 }
            r14.append(r2)     // Catch:{ all -> 0x0034 }
            java.lang.String r13 = r14.toString()     // Catch:{ all -> 0x0034 }
            java.util.zip.ZipEntry r14 = r8.getEntry(r13)     // Catch:{ all -> 0x0034 }
            if (r14 == 0) goto L_0x0069
            goto L_0x006c
        L_0x0069:
            int r12 = r12 + 1
            goto L_0x0040
        L_0x006c:
            if (r13 == 0) goto L_0x0077
            java.lang.String r6 = "Looking for %s in APK..."
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ all -> 0x0034 }
            r12[r7] = r13     // Catch:{ all -> 0x0034 }
            r4.mo14179a((java.lang.String) r6, (java.lang.Object[]) r12)     // Catch:{ all -> 0x0034 }
        L_0x0077:
            if (r14 != 0) goto L_0x0087
            if (r13 == 0) goto L_0x0081
            com.igexin.a.b r0 = new com.igexin.a.b     // Catch:{ all -> 0x0034 }
            r0.<init>(r13)     // Catch:{ all -> 0x0034 }
            throw r0     // Catch:{ all -> 0x0034 }
        L_0x0081:
            com.igexin.a.b r0 = new com.igexin.a.b     // Catch:{ all -> 0x0034 }
            r0.<init>(r2)     // Catch:{ all -> 0x0034 }
            throw r0     // Catch:{ all -> 0x0034 }
        L_0x0087:
            java.lang.String r6 = "Found %s! Extracting..."
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ all -> 0x0034 }
            r12[r7] = r13     // Catch:{ all -> 0x0034 }
            r4.mo14179a((java.lang.String) r6, (java.lang.Object[]) r12)     // Catch:{ all -> 0x0034 }
            boolean r6 = r22.exists()     // Catch:{ IOException -> 0x00f4 }
            if (r6 != 0) goto L_0x009d
            boolean r6 = r22.createNewFile()     // Catch:{ IOException -> 0x00f4 }
            if (r6 != 0) goto L_0x009d
            goto L_0x00f4
        L_0x009d:
            java.io.InputStream r6 = r8.getInputStream(r14)     // Catch:{ FileNotFoundException -> 0x00ee, IOException -> 0x00e8, all -> 0x00de }
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00dc, IOException -> 0x00da, all -> 0x00d7 }
            r12.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00dc, IOException -> 0x00da, all -> 0x00d7 }
            long r13 = r1.m1157a(r6, r12)     // Catch:{ FileNotFoundException -> 0x00f0, IOException -> 0x00ea, all -> 0x00d5 }
            java.io.FileDescriptor r15 = r12.getFD()     // Catch:{ FileNotFoundException -> 0x00f0, IOException -> 0x00ea, all -> 0x00d5 }
            r15.sync()     // Catch:{ FileNotFoundException -> 0x00f0, IOException -> 0x00ea, all -> 0x00d5 }
            long r15 = r22.length()     // Catch:{ FileNotFoundException -> 0x00f0, IOException -> 0x00ea, all -> 0x00d5 }
            int r17 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r17 == 0) goto L_0x00c0
            r1.m1158a(r6)     // Catch:{ all -> 0x0034 }
        L_0x00bc:
            r1.m1158a(r12)     // Catch:{ all -> 0x0034 }
            goto L_0x00f4
        L_0x00c0:
            r1.m1158a(r6)     // Catch:{ all -> 0x0034 }
            r1.m1158a(r12)     // Catch:{ all -> 0x0034 }
            r3.setReadable(r11, r7)     // Catch:{ all -> 0x0034 }
            r3.setExecutable(r11, r7)     // Catch:{ all -> 0x0034 }
            r3.setWritable(r11)     // Catch:{ all -> 0x0034 }
            if (r8 == 0) goto L_0x00d4
            r8.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x00d4:
            return
        L_0x00d5:
            r0 = move-exception
            goto L_0x00e1
        L_0x00d7:
            r0 = move-exception
            r12 = r5
            goto L_0x00e1
        L_0x00da:
            r12 = r5
            goto L_0x00ea
        L_0x00dc:
            r12 = r5
            goto L_0x00f0
        L_0x00de:
            r0 = move-exception
            r6 = r5
            r12 = r6
        L_0x00e1:
            r1.m1158a(r6)     // Catch:{ all -> 0x0034 }
            r1.m1158a(r12)     // Catch:{ all -> 0x0034 }
            throw r0     // Catch:{ all -> 0x0034 }
        L_0x00e8:
            r6 = r5
            r12 = r6
        L_0x00ea:
            r1.m1158a(r6)     // Catch:{ all -> 0x0034 }
            goto L_0x00bc
        L_0x00ee:
            r6 = r5
            r12 = r6
        L_0x00f0:
            r1.m1158a(r6)     // Catch:{ all -> 0x0034 }
            goto L_0x00bc
        L_0x00f4:
            r6 = r9
            goto L_0x0038
        L_0x00f7:
            java.lang.String r0 = "FATAL! Couldn't extract the library from the APK!"
            r4.mo14178a((java.lang.String) r0)     // Catch:{ all -> 0x0034 }
            if (r8 == 0) goto L_0x0101
            r8.close()     // Catch:{ IOException -> 0x0101 }
        L_0x0101:
            return
        L_0x0102:
            r0 = move-exception
            r8 = r5
        L_0x0104:
            if (r8 == 0) goto L_0x0109
            r8.close()     // Catch:{ IOException -> 0x0109 }
        L_0x0109:
            goto L_0x010b
        L_0x010a:
            throw r0
        L_0x010b:
            goto L_0x010a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p030a.C1117a.mo14151a(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.igexin.a.h):void");
    }
}
