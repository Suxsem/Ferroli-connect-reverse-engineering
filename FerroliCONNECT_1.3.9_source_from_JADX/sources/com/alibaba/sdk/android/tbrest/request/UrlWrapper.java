package com.alibaba.sdk.android.tbrest.request;

import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.tbrest.SendService;

public class UrlWrapper {
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    public static int mErrorCode;
    private static C0882a mRestSslSocketFactory;

    static {
        System.setProperty("http.keepAlive", RequestConstant.TRUE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.io.DataOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.io.DataOutputStream} */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01dc A[SYNTHETIC, Splitter:B:108:0x01dc] */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A[ExcHandler: IOException | MalformedURLException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:5:0x0023] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0103 A[Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0122 A[SYNTHETIC, Splitter:B:51:0x0122] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x014e A[Catch:{ IOException -> 0x0159, all -> 0x0156 }, LOOP:0: B:61:0x0147->B:63:0x014e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0152 A[EDGE_INSN: B:64:0x0152->B:65:? ?: BREAK  , SYNTHETIC, Splitter:B:64:0x0152] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0168 A[SYNTHETIC, Splitter:B:75:0x0168] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x018f A[SYNTHETIC, Splitter:B:83:0x018f] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01af A[SYNTHETIC, Splitter:B:93:0x01af] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:99:0x01bd=Splitter:B:99:0x01bd, B:90:0x019f=Splitter:B:90:0x019f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.sdk.android.tbrest.request.BizResponse sendRequest(com.alibaba.sdk.android.tbrest.SendService r7, java.lang.String r8, java.lang.String r9, byte[] r10) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sendRequest use adashx, bytes length : "
            r0.append(r1)
            if (r10 != 0) goto L_0x000f
            java.lang.String r1 = "0"
            goto L_0x0014
        L_0x000f:
            int r1 = r10.length
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
        L_0x0014:
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r0)
            com.alibaba.sdk.android.tbrest.request.BizResponse r0 = new com.alibaba.sdk.android.tbrest.request.BizResponse
            r0.<init>()
            java.net.URL r1 = new java.net.URL     // Catch:{ IOException | MalformedURLException -> 0x01e9 }
            r1.<init>(r9)     // Catch:{ IOException | MalformedURLException -> 0x01e9 }
            java.net.URLConnection r9 = r1.openConnection()     // Catch:{ IOException | MalformedURLException -> 0x01e9 }
            java.net.HttpURLConnection r9 = (java.net.HttpURLConnection) r9     // Catch:{ IOException | MalformedURLException -> 0x01e9 }
            boolean r2 = r9 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ IOException | MalformedURLException -> 0x01e9 }
            if (r2 == 0) goto L_0x0053
            com.alibaba.sdk.android.tbrest.request.a r2 = mRestSslSocketFactory     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            if (r2 != 0) goto L_0x004b
            java.lang.String r2 = r1.getHost()     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            if (r2 != 0) goto L_0x004b
            com.alibaba.sdk.android.tbrest.request.a r2 = new com.alibaba.sdk.android.tbrest.request.a     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            java.lang.String r1 = r1.getHost()     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            r2.<init>(r1)     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            mRestSslSocketFactory = r2     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
        L_0x004b:
            r1 = r9
            javax.net.ssl.HttpsURLConnection r1 = (javax.net.ssl.HttpsURLConnection) r1     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            com.alibaba.sdk.android.tbrest.request.a r2 = mRestSslSocketFactory     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
            r1.setSSLSocketFactory(r2)     // Catch:{ IllegalStateException -> 0x0053, IOException | MalformedURLException -> 0x01e9 }
        L_0x0053:
            if (r9 == 0) goto L_0x01e9
            r1 = 1
            r9.setDoOutput(r1)
            r9.setDoInput(r1)
            java.lang.String r2 = "POST"
            r9.setRequestMethod(r2)     // Catch:{  }
            r2 = 0
            r9.setUseCaches(r2)
            r3 = 10000(0x2710, float:1.4013E-41)
            r9.setConnectTimeout(r3)
            r3 = 60000(0xea60, float:8.4078E-41)
            r9.setReadTimeout(r3)
            r9.setInstanceFollowRedirects(r1)
            java.lang.String r3 = "Content-Type"
            java.lang.String r4 = "application/x-www-form-urlencoded"
            r9.setRequestProperty(r3, r4)
            java.lang.String r3 = "Charset"
            java.lang.String r4 = "UTF-8"
            r9.setRequestProperty(r3, r4)
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 != 0) goto L_0x008c
            java.lang.String r3 = "x-k"
            r9.setRequestProperty(r3, r8)
        L_0x008c:
            java.lang.String r7 = r7.appSecret     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r3 = "x-t"
            java.lang.String r4 = "x-s"
            java.lang.String r5 = "signValue:"
            if (r7 == 0) goto L_0x00c4
            int r6 = r7.length()     // Catch:{ Throwable -> 0x00ee }
            if (r6 <= 0) goto L_0x00c4
            com.alibaba.sdk.android.tbrest.a.a r6 = new com.alibaba.sdk.android.tbrest.a.a     // Catch:{ Throwable -> 0x00ee }
            r6.<init>(r8, r7, r1)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = com.alibaba.sdk.android.tbrest.utils.MD5Utils.getMd5Hex(r10)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = r6.mo10128b(r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ee }
            r8.<init>()     // Catch:{ Throwable -> 0x00ee }
            r8.append(r5)     // Catch:{ Throwable -> 0x00ee }
            r8.append(r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x00ee }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r8)     // Catch:{ Throwable -> 0x00ee }
            r9.setRequestProperty(r4, r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = "2"
            r9.setRequestProperty(r3, r7)     // Catch:{ Throwable -> 0x00ee }
            goto L_0x00f6
        L_0x00c4:
            java.lang.String r7 = ""
            com.alibaba.sdk.android.tbrest.a.a r1 = new com.alibaba.sdk.android.tbrest.a.a     // Catch:{ Throwable -> 0x00ee }
            r1.<init>(r8, r7, r2)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = com.alibaba.sdk.android.tbrest.utils.MD5Utils.getMd5Hex(r10)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = r1.mo10128b(r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ee }
            r8.<init>()     // Catch:{ Throwable -> 0x00ee }
            r8.append(r5)     // Catch:{ Throwable -> 0x00ee }
            r8.append(r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x00ee }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r8)     // Catch:{ Throwable -> 0x00ee }
            r9.setRequestProperty(r4, r7)     // Catch:{ Throwable -> 0x00ee }
            java.lang.String r7 = "3"
            r9.setRequestProperty(r3, r7)     // Catch:{ Throwable -> 0x00ee }
            goto L_0x00f6
        L_0x00ee:
            r7 = move-exception
            java.lang.String r7 = r7.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r7)
        L_0x00f6:
            long r7 = java.lang.System.currentTimeMillis()
            r1 = 0
            r9.connect()     // Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }
            if (r10 == 0) goto L_0x011f
            int r3 = r10.length     // Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }
            if (r3 <= 0) goto L_0x011f
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }
            java.io.OutputStream r4 = r9.getOutputStream()     // Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }
            r3.<init>(r4)     // Catch:{ SSLHandshakeException -> 0x01bc, Exception -> 0x019e }
            r3.write(r10)     // Catch:{ SSLHandshakeException -> 0x011b, Exception -> 0x0117, all -> 0x0113 }
            r3.flush()     // Catch:{ SSLHandshakeException -> 0x011b, Exception -> 0x0117, all -> 0x0113 }
            goto L_0x0120
        L_0x0113:
            r7 = move-exception
            r1 = r3
            goto L_0x01da
        L_0x0117:
            r9 = move-exception
            r1 = r3
            goto L_0x019f
        L_0x011b:
            r9 = move-exception
            r1 = r3
            goto L_0x01bd
        L_0x011f:
            r3 = r1
        L_0x0120:
            if (r3 == 0) goto L_0x012e
            r3.close()     // Catch:{ IOException -> 0x0126 }
            goto L_0x012e
        L_0x0126:
            r10 = move-exception
            java.lang.String r10 = r10.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r10)
        L_0x012e:
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r7
            r0.f1336rt = r3
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream
            r7.<init>()
            java.io.DataInputStream r8 = new java.io.DataInputStream     // Catch:{ IOException -> 0x015e }
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ IOException -> 0x015e }
            r8.<init>(r9)     // Catch:{ IOException -> 0x015e }
            r9 = 2048(0x800, float:2.87E-42)
            byte[] r10 = new byte[r9]     // Catch:{ IOException -> 0x0159, all -> 0x0156 }
        L_0x0147:
            int r1 = r8.read(r10, r2, r9)     // Catch:{ IOException -> 0x0159, all -> 0x0156 }
            r3 = -1
            if (r1 == r3) goto L_0x0152
            r7.write(r10, r2, r1)     // Catch:{ IOException -> 0x0159, all -> 0x0156 }
            goto L_0x0147
        L_0x0152:
            r8.close()     // Catch:{ Exception -> 0x016c }
            goto L_0x0174
        L_0x0156:
            r7 = move-exception
            r1 = r8
            goto L_0x018d
        L_0x0159:
            r9 = move-exception
            r1 = r8
            goto L_0x015f
        L_0x015c:
            r7 = move-exception
            goto L_0x018d
        L_0x015e:
            r9 = move-exception
        L_0x015f:
            java.lang.String r8 = r9.toString()     // Catch:{ all -> 0x015c }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r8)     // Catch:{ all -> 0x015c }
            if (r1 == 0) goto L_0x0174
            r1.close()     // Catch:{ Exception -> 0x016c }
            goto L_0x0174
        L_0x016c:
            r8 = move-exception
            java.lang.String r8 = r8.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r8)
        L_0x0174:
            int r8 = r7.size()
            if (r8 <= 0) goto L_0x01e9
            byte[] r7 = r7.toByteArray()
            int r7 = com.alibaba.sdk.android.tbrest.request.BizRequest.parseResult(r7)
            mErrorCode = r7
            int r7 = mErrorCode
            r0.errCode = r7
            java.lang.String r7 = com.alibaba.sdk.android.tbrest.request.BizRequest.mResponseAdditionalData
            r0.data = r7
            goto L_0x01e9
        L_0x018d:
            if (r1 == 0) goto L_0x019b
            r1.close()     // Catch:{ Exception -> 0x0193 }
            goto L_0x019b
        L_0x0193:
            r8 = move-exception
            java.lang.String r8 = r8.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r8)
        L_0x019b:
            throw r7
        L_0x019c:
            r7 = move-exception
            goto L_0x01da
        L_0x019e:
            r9 = move-exception
        L_0x019f:
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x019c }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r9)     // Catch:{ all -> 0x019c }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x019c }
            long r9 = r9 - r7
            r0.f1336rt = r9     // Catch:{ all -> 0x019c }
            if (r1 == 0) goto L_0x01bb
            r1.close()     // Catch:{ IOException -> 0x01b3 }
            goto L_0x01bb
        L_0x01b3:
            r7 = move-exception
            java.lang.String r7 = r7.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r7)
        L_0x01bb:
            return r0
        L_0x01bc:
            r9 = move-exception
        L_0x01bd:
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x019c }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r9)     // Catch:{ all -> 0x019c }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x019c }
            long r9 = r9 - r7
            r0.f1336rt = r9     // Catch:{ all -> 0x019c }
            if (r1 == 0) goto L_0x01d9
            r1.close()     // Catch:{ IOException -> 0x01d1 }
            goto L_0x01d9
        L_0x01d1:
            r7 = move-exception
            java.lang.String r7 = r7.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r7)
        L_0x01d9:
            return r0
        L_0x01da:
            if (r1 == 0) goto L_0x01e8
            r1.close()     // Catch:{ IOException -> 0x01e0 }
            goto L_0x01e8
        L_0x01e0:
            r8 = move-exception
            java.lang.String r8 = r8.toString()
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1030e(r8)
        L_0x01e8:
            throw r7
        L_0x01e9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.request.UrlWrapper.sendRequest(com.alibaba.sdk.android.tbrest.SendService, java.lang.String, java.lang.String, byte[]):com.alibaba.sdk.android.tbrest.request.BizResponse");
    }

    public static BizResponse sendRequest(SendService sendService, String str, byte[] bArr) {
        String str2;
        String str3 = sendService.appKey;
        if (sendService.openHttp.booleanValue()) {
            str2 = "http://" + str + "/upload";
        } else {
            str2 = "https://" + str + "/upload";
        }
        return sendRequest(sendService, str3, str2, bArr);
    }

    public static BizResponse sendRequestByUrl(SendService sendService, String str, byte[] bArr) {
        return sendRequest(sendService, sendService.appKey, str, bArr);
    }
}
