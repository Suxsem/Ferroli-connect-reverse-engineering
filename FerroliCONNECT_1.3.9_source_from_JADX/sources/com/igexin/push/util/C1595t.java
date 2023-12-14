package com.igexin.push.util;

import android.text.TextUtils;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.eclipse.jetty.http.HttpHeaders;

/* renamed from: com.igexin.push.util.t */
public class C1595t {

    /* renamed from: a */
    public static final String f3041a = "com.igexin.push.util.t";

    /* renamed from: a */
    private static String m3273a(InputStream inputStream, String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str));
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[256];
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read <= 0) {
                    break;
                }
                stringWriter.write(cArr, 0, read);
            }
            return stringWriter.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /* renamed from: a */
    private static String m3274a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(";");
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String trim = split[i].trim();
                if (trim.startsWith("charset")) {
                    String[] split2 = trim.split("=", 2);
                    if (split2.length == 2 && !TextUtils.isEmpty(split2[1])) {
                        return split2[1].trim();
                    }
                } else {
                    i++;
                }
            }
        }
        return "utf-8";
    }

    /* renamed from: a */
    private static HttpURLConnection m3275a(URL url, String str, String str2) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(str);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
        httpURLConnection.setReadTimeout(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
        httpURLConnection.setRequestProperty(HttpHeaders.USER_AGENT, "GETUI");
        httpURLConnection.setRequestProperty("Content-Type", str2);
        httpURLConnection.setRequestProperty("HOST", url.getHost() + ":" + url.getPort());
        return httpURLConnection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0029 A[SYNTHETIC, Splitter:B:17:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0030 A[SYNTHETIC, Splitter:B:25:0x0030] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] m3276a(java.io.InputStream r5) {
        /*
            r0 = 0
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x002d, all -> 0x0025 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x002d, all -> 0x0025 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
            r2 = 1024(0x400, float:1.435E-42)
            r5.<init>(r2)     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
        L_0x000f:
            int r3 = r1.read(r2)     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
            r4 = -1
            if (r3 == r4) goto L_0x001b
            r4 = 0
            r5.write(r2, r4, r3)     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
            goto L_0x000f
        L_0x001b:
            byte[] r5 = r5.toByteArray()     // Catch:{ Exception -> 0x002e, all -> 0x0023 }
            r1.close()     // Catch:{ IOException -> 0x0022 }
        L_0x0022:
            return r5
        L_0x0023:
            r5 = move-exception
            goto L_0x0027
        L_0x0025:
            r5 = move-exception
            r1 = r0
        L_0x0027:
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ IOException -> 0x002c }
        L_0x002c:
            throw r5
        L_0x002d:
            r1 = r0
        L_0x002e:
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0033:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1595t.m3276a(java.io.InputStream):byte[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.net.HttpURLConnection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003a  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m3277a(java.lang.String r2, java.lang.String r3, byte[] r4, int r5, int r6) {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            r1.<init>(r2)     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            java.lang.String r2 = "POST"
            java.net.HttpURLConnection r2 = m3275a(r1, r2, r3)     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            r2.setConnectTimeout(r5)     // Catch:{ IOException -> 0x002a }
            r2.setReadTimeout(r6)     // Catch:{ IOException -> 0x002a }
            java.io.OutputStream r0 = r2.getOutputStream()     // Catch:{ Exception -> 0x0028 }
            r0.write(r4)     // Catch:{ Exception -> 0x0028 }
            byte[] r3 = m3279a((java.net.HttpURLConnection) r2)     // Catch:{ Exception -> 0x0028 }
            if (r0 == 0) goto L_0x0022
            r0.close()
        L_0x0022:
            if (r2 == 0) goto L_0x0027
            r2.disconnect()
        L_0x0027:
            return r3
        L_0x0028:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0032 }
        L_0x002a:
            r3 = move-exception
            goto L_0x0031
        L_0x002c:
            r3 = move-exception
            r2 = r0
            goto L_0x0033
        L_0x002f:
            r3 = move-exception
            r2 = r0
        L_0x0031:
            throw r3     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r3 = move-exception
        L_0x0033:
            if (r0 == 0) goto L_0x0038
            r0.close()
        L_0x0038:
            if (r2 == 0) goto L_0x003d
            r2.disconnect()
        L_0x003d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1595t.m3277a(java.lang.String, java.lang.String, byte[], int, int):byte[]");
    }

    /* renamed from: a */
    public static byte[] m3278a(String str, byte[] bArr, int i, int i2) {
        return m3277a(str, "application/octet-stream", bArr, i, i2);
    }

    /* renamed from: a */
    private static byte[] m3279a(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getErrorStream() == null ? m3276a(httpURLConnection.getInputStream()) : m3280b(httpURLConnection).getBytes();
    }

    /* renamed from: b */
    private static String m3280b(HttpURLConnection httpURLConnection) {
        String a = m3273a(httpURLConnection.getErrorStream(), m3274a(httpURLConnection.getContentType()));
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        throw new IOException(httpURLConnection.getResponseCode() + ":" + httpURLConnection.getResponseMessage());
    }
}
