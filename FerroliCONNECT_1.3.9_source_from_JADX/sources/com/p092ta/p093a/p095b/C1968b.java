package com.p092ta.p093a.p095b;

import anetwork.channel.util.RequestConstant;

/* renamed from: com.ta.a.b.b */
public class C1968b {

    /* renamed from: a */
    private static C1970d f3152a = null;

    /* renamed from: a */
    private static C1972f f3153a = null;

    static {
        System.setProperty("http.keepAlive", RequestConstant.TRUE);
    }

    /* JADX WARNING: type inference failed for: r6v14, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v16, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r10v3, types: [java.io.DataInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r10v5, types: [java.io.DataInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v35 */
    /* JADX WARNING: type inference failed for: r6v38 */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0239, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x023a, code lost:
        com.p092ta.p093a.p096c.C1982f.m3366a("", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01d6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01d7, code lost:
        r2 = r0;
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01da, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01db, code lost:
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0201, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0202, code lost:
        com.p092ta.p093a.p096c.C1982f.m3366a("", r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0210  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0225 A[SYNTHETIC, Splitter:B:108:0x0225] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0235 A[SYNTHETIC, Splitter:B:114:0x0235] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0267 A[SYNTHETIC, Splitter:B:131:0x0267] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01d6 A[ExcHandler: all (r0v27 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r10 
      PHI: (r10v4 ?) = (r10v3 ?), (r10v5 ?) binds: [B:90:0x01f1, B:70:0x01bc] A[DONT_GENERATE, DONT_INLINE], Splitter:B:70:0x01bc] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01f9 A[Catch:{ Exception -> 0x0218, all -> 0x01d6 }, LOOP:1: B:92:0x01f3->B:94:0x01f9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01fd A[EDGE_INSN: B:95:0x01fd->B:96:? ?: BREAK  , SYNTHETIC, Splitter:B:95:0x01fd] */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.p092ta.p093a.p095b.C1967a m3338a(java.lang.String r17, java.lang.String r18, boolean r19) {
        /*
            r0 = r18
            java.lang.String r1 = ""
            com.ta.a.b.a r2 = new com.ta.a.b.a
            r2.<init>()
            boolean r3 = android.text.TextUtils.isEmpty(r17)
            if (r3 == 0) goto L_0x0010
            return r2
        L_0x0010:
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r5 = r17
            r4.<init>(r5)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            java.lang.String r5 = r4.getHost()     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            if (r5 == 0) goto L_0x0023
            return r2
        L_0x0023:
            java.net.URLConnection r5 = r4.openConnection()     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            boolean r6 = r5 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            if (r6 == 0) goto L_0x005b
            com.ta.a.b.f r6 = f3153a     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            if (r6 != 0) goto L_0x003c
            com.ta.a.b.f r6 = new com.ta.a.b.f     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            java.lang.String r7 = r4.getHost()     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r6.<init>(r7)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            f3153a = r6     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
        L_0x003c:
            com.ta.a.b.d r6 = f3152a     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            if (r6 != 0) goto L_0x004b
            com.ta.a.b.d r6 = new com.ta.a.b.d     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            java.lang.String r4 = r4.getHost()     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r6.<init>(r4)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            f3152a = r6     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
        L_0x004b:
            r4 = r5
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            com.ta.a.b.f r6 = f3153a     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r4.setSSLSocketFactory(r6)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r4 = r5
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            com.ta.a.b.d r6 = f3152a     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
            r4.setHostnameVerifier(r6)     // Catch:{ MalformedURLException -> 0x028a, IOException -> 0x0283, Throwable -> 0x027c }
        L_0x005b:
            if (r5 == 0) goto L_0x027b
            r4 = 1
            r5.setDoInput(r4)
            if (r19 == 0) goto L_0x0073
            r5.setDoOutput(r4)
            java.lang.String r6 = "POST"
            r5.setRequestMethod(r6)     // Catch:{ ProtocolException -> 0x006c }
            goto L_0x0078
        L_0x006c:
            r0 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.p092ta.p093a.p096c.C1982f.m3367a(r1, r0, r3)
            return r2
        L_0x0073:
            java.lang.String r6 = "GET"
            r5.setRequestMethod(r6)     // Catch:{ ProtocolException -> 0x0275 }
        L_0x0078:
            r5.setUseCaches(r3)
            r6 = 10000(0x2710, float:1.4013E-41)
            r5.setConnectTimeout(r6)
            r5.setReadTimeout(r6)
            r5.setInstanceFollowRedirects(r4)
            java.lang.String r6 = "Content-Type"
            java.lang.String r7 = "application/x-www-form-urlencoded"
            r5.setRequestProperty(r6, r7)
            java.lang.String r6 = "UTF-8"
            java.lang.String r7 = "Charset"
            r5.setRequestProperty(r7, r6)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "x-audid-appkey"
            r5.setRequestProperty(r8, r1)
            com.ta.a.a r8 = com.p092ta.p093a.C1964a.mo18112a()
            android.content.Context r8 = r8.getContext()
            java.lang.String r8 = r8.getPackageName()
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 != 0) goto L_0x00bc
            java.lang.String r9 = "x-audid-appname"
            java.lang.String r6 = java.net.URLEncoder.encode(r8, r6)     // Catch:{ Exception -> 0x00bc }
            r5.setRequestProperty(r9, r6)     // Catch:{ Exception -> 0x00bc }
            r7.append(r8)     // Catch:{ Exception -> 0x00bc }
        L_0x00bc:
            java.lang.String r6 = "2.5.3-mini"
            java.lang.String r8 = "x-audid-sdk"
            r5.setRequestProperty(r8, r6)
            r7.append(r6)
            com.ta.a.a r6 = com.p092ta.p093a.C1964a.mo18112a()
            java.lang.String r6 = r6.mo18112a()
            java.lang.String r8 = "x-audid-timestamp"
            r5.setRequestProperty(r8, r6)
            java.lang.Object[] r9 = new java.lang.Object[r4]
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "timestamp:"
            r10.append(r11)
            r10.append(r6)
            java.lang.String r10 = r10.toString()
            r9[r3] = r10
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r9)
            r7.append(r6)
            r7.append(r0)
            java.lang.String r6 = r7.toString()
            java.lang.String r6 = com.p092ta.p093a.p096c.C1976b.m3356d(r6)
            byte[] r6 = r6.getBytes()
            r7 = 2
            java.lang.String r6 = com.p092ta.utdid2.p097a.p098a.C1983a.encodeToString(r6, r7)
            java.lang.String r7 = "signature"
            r5.setRequestProperty(r7, r6)
            long r9 = java.lang.System.currentTimeMillis()
            r6 = 0
            r5.connect()     // Catch:{ Throwable -> 0x0246 }
            if (r0 == 0) goto L_0x0130
            int r11 = r18.length()     // Catch:{ Throwable -> 0x0246 }
            if (r11 <= 0) goto L_0x0130
            java.io.DataOutputStream r11 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x0246 }
            java.io.OutputStream r12 = r5.getOutputStream()     // Catch:{ Throwable -> 0x0246 }
            r11.<init>(r12)     // Catch:{ Throwable -> 0x0246 }
            r11.writeBytes(r0)     // Catch:{ Throwable -> 0x012c, all -> 0x0127 }
            r11.flush()     // Catch:{ Throwable -> 0x012c, all -> 0x0127 }
            goto L_0x0131
        L_0x0127:
            r0 = move-exception
            r2 = r0
            r6 = r11
            goto L_0x0265
        L_0x012c:
            r0 = move-exception
            r6 = r11
            goto L_0x0247
        L_0x0130:
            r11 = r6
        L_0x0131:
            if (r11 == 0) goto L_0x0140
            r11.close()     // Catch:{ IOException -> 0x0137 }
            goto L_0x0140
        L_0x0137:
            r0 = move-exception
            r11 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r11
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x0140:
            int r0 = r5.getResponseCode()     // Catch:{ Exception -> 0x014d }
            r2.f3149a = r0     // Catch:{ Exception -> 0x014d }
            java.lang.String r0 = r5.getHeaderField(r7)     // Catch:{ Exception -> 0x014d }
            r2.f3150a = r0     // Catch:{ Exception -> 0x014d }
            goto L_0x0155
        L_0x014d:
            r0 = move-exception
            java.lang.Object[] r7 = new java.lang.Object[r4]
            r7[r3] = r0
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r7)
        L_0x0155:
            java.lang.String r0 = r5.getHeaderField(r8)     // Catch:{ Exception -> 0x01a4 }
            long r7 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x01a4 }
            r2.timestamp = r7     // Catch:{ Exception -> 0x01a4 }
            java.lang.Object[] r0 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x01a4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01a4 }
            r7.<init>()     // Catch:{ Exception -> 0x01a4 }
            java.lang.String r8 = "repsonse.timestamp:"
            r7.append(r8)     // Catch:{ Exception -> 0x01a4 }
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01a4 }
            r7.append(r11)     // Catch:{ Exception -> 0x01a4 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x01a4 }
            r0[r3] = r7     // Catch:{ Exception -> 0x01a4 }
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)     // Catch:{ Exception -> 0x01a4 }
            com.ta.a.a r0 = com.p092ta.p093a.C1964a.mo18112a()     // Catch:{ Exception -> 0x01a4 }
            long r7 = r0.mo18112a()     // Catch:{ Exception -> 0x01a4 }
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01a4 }
            r13 = 0
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 <= 0) goto L_0x01a4
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01a4 }
            r13 = 1800000(0x1b7740, double:8.89318E-318)
            long r15 = r7 + r13
            int r0 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r0 > 0) goto L_0x019b
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01a4 }
            long r7 = r7 - r13
            int r0 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x01a4
        L_0x019b:
            com.ta.a.a r0 = com.p092ta.p093a.C1964a.mo18112a()     // Catch:{ Exception -> 0x01a4 }
            long r7 = r2.timestamp     // Catch:{ Exception -> 0x01a4 }
            r0.mo18113a((long) r7)     // Catch:{ Exception -> 0x01a4 }
        L_0x01a4:
            long r7 = java.lang.System.currentTimeMillis()
            long r7 = r7 - r9
            r2.f3151b = r7
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream
            r7.<init>()
            r8 = -1
            r9 = 2048(0x800, float:2.87E-42)
            java.io.DataInputStream r10 = new java.io.DataInputStream     // Catch:{ IOException -> 0x01e0 }
            java.io.InputStream r0 = r5.getInputStream()     // Catch:{ IOException -> 0x01e0 }
            r10.<init>(r0)     // Catch:{ IOException -> 0x01e0 }
            byte[] r0 = new byte[r9]     // Catch:{ IOException -> 0x01da, all -> 0x01d6 }
        L_0x01be:
            int r6 = r10.read(r0, r3, r9)     // Catch:{ IOException -> 0x01da, all -> 0x01d6 }
            if (r6 == r8) goto L_0x01c8
            r7.write(r0, r3, r6)     // Catch:{ IOException -> 0x01da, all -> 0x01d6 }
            goto L_0x01be
        L_0x01c8:
            r10.close()     // Catch:{ Exception -> 0x01cc }
            goto L_0x020a
        L_0x01cc:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
            goto L_0x020a
        L_0x01d6:
            r0 = move-exception
            r2 = r0
            r6 = r10
            goto L_0x0233
        L_0x01da:
            r0 = move-exception
            r6 = r10
            goto L_0x01e1
        L_0x01dd:
            r0 = move-exception
            r2 = r0
            goto L_0x0233
        L_0x01e0:
            r0 = move-exception
        L_0x01e1:
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ all -> 0x01dd }
            r10[r3] = r0     // Catch:{ all -> 0x01dd }
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r10)     // Catch:{ all -> 0x01dd }
            java.io.DataInputStream r10 = new java.io.DataInputStream     // Catch:{ Exception -> 0x021b }
            java.io.InputStream r0 = r5.getErrorStream()     // Catch:{ Exception -> 0x021b }
            r10.<init>(r0)     // Catch:{ Exception -> 0x021b }
            byte[] r0 = new byte[r9]     // Catch:{ Exception -> 0x0218, all -> 0x01d6 }
        L_0x01f3:
            int r5 = r10.read(r0, r3, r9)     // Catch:{ Exception -> 0x0218, all -> 0x01d6 }
            if (r5 == r8) goto L_0x01fd
            r7.write(r0, r3, r5)     // Catch:{ Exception -> 0x0218, all -> 0x01d6 }
            goto L_0x01f3
        L_0x01fd:
            r10.close()     // Catch:{ Exception -> 0x0201 }
            goto L_0x020a
        L_0x0201:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x020a:
            int r0 = r7.size()
            if (r0 <= 0) goto L_0x027b
            byte[] r0 = r7.toByteArray()
            r2.data = r0
            goto L_0x027b
        L_0x0218:
            r0 = move-exception
            r6 = r10
            goto L_0x021c
        L_0x021b:
            r0 = move-exception
        L_0x021c:
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x01dd }
            r5[r3] = r0     // Catch:{ all -> 0x01dd }
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r5)     // Catch:{ all -> 0x01dd }
            if (r6 == 0) goto L_0x0232
            r6.close()     // Catch:{ Exception -> 0x0229 }
            goto L_0x0232
        L_0x0229:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x0232:
            return r2
        L_0x0233:
            if (r6 == 0) goto L_0x0242
            r6.close()     // Catch:{ Exception -> 0x0239 }
            goto L_0x0242
        L_0x0239:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x0242:
            throw r2
        L_0x0243:
            r0 = move-exception
            r2 = r0
            goto L_0x0265
        L_0x0246:
            r0 = move-exception
        L_0x0247:
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0243 }
            r5[r3] = r0     // Catch:{ all -> 0x0243 }
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r5)     // Catch:{ all -> 0x0243 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0243 }
            long r7 = r7 - r9
            r2.f3151b = r7     // Catch:{ all -> 0x0243 }
            if (r6 == 0) goto L_0x0264
            r6.close()     // Catch:{ IOException -> 0x025b }
            goto L_0x0264
        L_0x025b:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x0264:
            return r2
        L_0x0265:
            if (r6 == 0) goto L_0x0274
            r6.close()     // Catch:{ IOException -> 0x026b }
            goto L_0x0274
        L_0x026b:
            r0 = move-exception
            r5 = r0
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            com.p092ta.p093a.p096c.C1982f.m3366a((java.lang.String) r1, (java.lang.Object[]) r0)
        L_0x0274:
            throw r2
        L_0x0275:
            r0 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.p092ta.p093a.p096c.C1982f.m3367a(r1, r0, r3)
        L_0x027b:
            return r2
        L_0x027c:
            r0 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.p092ta.p093a.p096c.C1982f.m3367a(r1, r0, r3)
            return r2
        L_0x0283:
            r0 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.p092ta.p093a.p096c.C1982f.m3367a(r1, r0, r3)
            return r2
        L_0x028a:
            r0 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.p092ta.p093a.p096c.C1982f.m3367a(r1, r0, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p092ta.p093a.p095b.C1968b.m3338a(java.lang.String, java.lang.String, boolean):com.ta.a.b.a");
    }
}
