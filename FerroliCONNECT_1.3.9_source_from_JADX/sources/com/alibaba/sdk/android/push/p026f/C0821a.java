package com.alibaba.sdk.android.push.p026f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.File;

/* renamed from: com.alibaba.sdk.android.push.f.a */
public class C0821a {
    /* renamed from: a */
    public static Bitmap m818a(Context context, String str) {
        File file;
        File file2 = new File(context.getCacheDir(), "aliyun_push_images");
        String a = m819a(str);
        if (new File(file2, a).exists()) {
            file = new File(file2, a);
        } else {
            try {
                m822a(str, file2, a);
                file = new File(file2, a);
            } catch (Exception unused) {
                return null;
            }
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /* renamed from: a */
    public static String m819a(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        return substring.contains("?") ? substring.substring(0, substring.indexOf("?")) : substring;
    }

    /* renamed from: a */
    public static StringBuilder m820a(BufferedReader bufferedReader) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return sb;
            }
            sb.append(readLine);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.io.FileOutputStream} */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x001e */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0037 A[SYNTHETIC, Splitter:B:31:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x003c A[SYNTHETIC, Splitter:B:35:0x003c] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void m821a(java.io.InputStream r3, java.io.File r4) {
        /*
            r0 = 0
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            r3 = 10240(0x2800, float:1.4349E-41)
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x0028, all -> 0x0026 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0028, all -> 0x0026 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0028, all -> 0x0026 }
        L_0x000f:
            int r4 = r1.read(r3)     // Catch:{ Throwable -> 0x0024, all -> 0x0022 }
            r0 = -1
            if (r4 == r0) goto L_0x001b
            r0 = 0
            r2.write(r3, r0, r4)     // Catch:{ Throwable -> 0x0024, all -> 0x0022 }
            goto L_0x000f
        L_0x001b:
            r1.close()     // Catch:{ Throwable -> 0x001e }
        L_0x001e:
            r2.close()     // Catch:{ Throwable -> 0x0021 }
        L_0x0021:
            return
        L_0x0022:
            r3 = move-exception
            goto L_0x0034
        L_0x0024:
            r3 = move-exception
            goto L_0x002a
        L_0x0026:
            r3 = move-exception
            goto L_0x0035
        L_0x0028:
            r3 = move-exception
            r2 = r0
        L_0x002a:
            r0 = r1
            goto L_0x0031
        L_0x002c:
            r3 = move-exception
            r1 = r0
            goto L_0x0035
        L_0x002f:
            r3 = move-exception
            r2 = r0
        L_0x0031:
            throw r3     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r3 = move-exception
            r1 = r0
        L_0x0034:
            r0 = r2
        L_0x0035:
            if (r1 == 0) goto L_0x003a
            r1.close()     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            if (r0 == 0) goto L_0x003f
            r0.close()     // Catch:{ Throwable -> 0x003f }
        L_0x003f:
            goto L_0x0041
        L_0x0040:
            throw r3
        L_0x0041:
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p026f.C0821a.m821a(java.io.InputStream, java.io.File):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.net.HttpURLConnection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: java.io.File} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: java.io.File} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: java.io.File} */
    /* JADX WARNING: type inference failed for: r8v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.io.File] */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r8v15 */
    /* JADX WARNING: type inference failed for: r8v17 */
    /* JADX WARNING: type inference failed for: r8v18 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* JADX WARNING: type inference failed for: r8v20 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00d2 A[SYNTHETIC, Splitter:B:61:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d7 A[SYNTHETIC, Splitter:B:65:0x00d7] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00e4 A[Catch:{ Throwable -> 0x00e7 }] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m822a(java.lang.String r8, java.io.File r9, java.lang.String r10) {
        /*
            java.lang.System.currentTimeMillis()
            r0 = 1
            r1 = 0
            r2 = 0
            java.net.URL r3 = new java.net.URL     // Catch:{ Throwable -> 0x00be, all -> 0x00b9 }
            r3.<init>(r8)     // Catch:{ Throwable -> 0x00be, all -> 0x00b9 }
            java.net.URLConnection r8 = r3.openConnection()     // Catch:{ Throwable -> 0x00be, all -> 0x00b9 }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ Throwable -> 0x00be, all -> 0x00b9 }
            r8.setDoInput(r0)     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            r8.setDoOutput(r1)     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            java.lang.String r3 = "GET"
            r8.setRequestMethod(r3)     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            java.lang.String r3 = "charset"
            java.lang.String r4 = "utf-8"
            r8.setRequestProperty(r3, r4)     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            int r3 = r8.getResponseCode()     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 != r4) goto L_0x0067
            boolean r3 = r9.exists()     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            if (r3 != 0) goto L_0x0034
            r9.mkdirs()     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
        L_0x0034:
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            r3.<init>(r9, r10)     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            java.io.InputStream r9 = r8.getInputStream()     // Catch:{ Throwable -> 0x0061, all -> 0x005d }
            m821a((java.io.InputStream) r9, (java.io.File) r3)     // Catch:{ Throwable -> 0x0055, all -> 0x004e }
            java.lang.System.currentTimeMillis()
            if (r8 == 0) goto L_0x0048
            r8.disconnect()
        L_0x0048:
            if (r9 == 0) goto L_0x004d
            r9.close()     // Catch:{ IOException -> 0x004d }
        L_0x004d:
            return
        L_0x004e:
            r10 = move-exception
            r7 = r2
            r2 = r9
            r9 = r10
            r10 = r7
            goto L_0x00c8
        L_0x0055:
            r10 = move-exception
            r7 = r2
            r2 = r8
            r8 = r9
            r9 = r10
            r10 = r7
            goto L_0x00c2
        L_0x005d:
            r9 = move-exception
            r10 = r2
            goto L_0x00c8
        L_0x0061:
            r9 = move-exception
            r10 = r2
            r2 = r8
            r8 = r10
            goto L_0x00c2
        L_0x0067:
            java.io.InputStream r9 = r8.getErrorStream()     // Catch:{ Throwable -> 0x00b3, all -> 0x00b0 }
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00a9, all -> 0x00a3 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00a9, all -> 0x00a3 }
            java.lang.String r4 = "UTF-8"
            r3.<init>(r9, r4)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a3 }
            r10.<init>(r3)     // Catch:{ Throwable -> 0x00a9, all -> 0x00a3 }
            java.lang.StringBuilder r3 = m820a((java.io.BufferedReader) r10)     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            java.lang.Exception r4 = new java.lang.Exception     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            r5.<init>()     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            int r6 = r8.getResponseCode()     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            r5.append(r6)     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            r5.append(r3)     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
            throw r4     // Catch:{ Throwable -> 0x009d, all -> 0x0098 }
        L_0x0098:
            r0 = move-exception
            r3 = r2
            r2 = r9
            r9 = r0
            goto L_0x00c8
        L_0x009d:
            r1 = move-exception
            r3 = r2
            r2 = r8
            r8 = r9
            r9 = r1
            goto L_0x00c2
        L_0x00a3:
            r10 = move-exception
            r3 = r2
            r2 = r9
            r9 = r10
            r10 = r3
            goto L_0x00c8
        L_0x00a9:
            r10 = move-exception
            r3 = r2
            r2 = r8
            r8 = r9
            r9 = r10
            r10 = r3
            goto L_0x00c2
        L_0x00b0:
            r9 = move-exception
            r10 = r2
            goto L_0x00bc
        L_0x00b3:
            r9 = move-exception
            r10 = r2
            r3 = r10
            r2 = r8
            r8 = r3
            goto L_0x00c2
        L_0x00b9:
            r9 = move-exception
            r8 = r2
            r10 = r8
        L_0x00bc:
            r3 = r10
            goto L_0x00c8
        L_0x00be:
            r9 = move-exception
            r8 = r2
            r10 = r8
            r3 = r10
        L_0x00c2:
            throw r9     // Catch:{ all -> 0x00c3 }
        L_0x00c3:
            r9 = move-exception
            r1 = 1
            r7 = r2
            r2 = r8
            r8 = r7
        L_0x00c8:
            java.lang.System.currentTimeMillis()
            if (r8 == 0) goto L_0x00d0
            r8.disconnect()
        L_0x00d0:
            if (r2 == 0) goto L_0x00d5
            r2.close()     // Catch:{ IOException -> 0x00d5 }
        L_0x00d5:
            if (r10 == 0) goto L_0x00da
            r10.close()     // Catch:{ IOException -> 0x00da }
        L_0x00da:
            if (r1 == 0) goto L_0x00e7
            if (r3 == 0) goto L_0x00e7
            boolean r8 = r3.exists()     // Catch:{ Throwable -> 0x00e7 }
            if (r8 == 0) goto L_0x00e7
            r3.delete()     // Catch:{ Throwable -> 0x00e7 }
        L_0x00e7:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p026f.C0821a.m822a(java.lang.String, java.io.File, java.lang.String):void");
    }
}
