package com.igexin.push.p088g.p089a;

import android.os.Process;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1234k;
import java.net.HttpURLConnection;

/* renamed from: com.igexin.push.g.a.a */
public class C1562a extends C1190e {

    /* renamed from: a */
    public static final String f2978a = "com.igexin.push.g.a.a";

    /* renamed from: b */
    public C1563b f2979b;

    /* renamed from: c */
    private HttpURLConnection f2980c;

    public C1562a(C1563b bVar) {
        super(0);
        this.f2979b = bVar;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:(2:22|23)|50|51|52|54) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:8|(2:9|(1:11)(1:55))|12|(2:14|15)|16|17|18|20) */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x008c, code lost:
        if (r1 == null) goto L_0x0091;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0055 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:50:0x008e */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0075 A[SYNTHETIC, Splitter:B:34:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007c A[SYNTHETIC, Splitter:B:38:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0087 A[SYNTHETIC, Splitter:B:46:0x0087] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m3126a(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            r1.<init>(r7)     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.URLConnection r7 = r1.openConnection()     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            r6.f2980c = r7     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = r6.f2980c     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            r1 = 20000(0x4e20, float:2.8026E-41)
            r7.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = r6.f2980c     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            r7.setReadTimeout(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = r6.f2980c     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.lang.String r1 = "GET"
            r7.setRequestMethod(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = r6.f2980c     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            r1 = 1
            r7.setDoInput(r1)     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.net.HttpURLConnection r7 = r6.f2980c     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.io.InputStream r7 = r7.getInputStream()     // Catch:{ Exception -> 0x0083, all -> 0x0071 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x006f, all -> 0x0069 }
            r1.<init>()     // Catch:{ Exception -> 0x006f, all -> 0x0069 }
            java.net.HttpURLConnection r2 = r6.f2980c     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
            int r2 = r2.getResponseCode()     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x005c
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
        L_0x0040:
            int r3 = r7.read(r2)     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
            r4 = -1
            if (r3 == r4) goto L_0x004c
            r4 = 0
            r1.write(r2, r4, r3)     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
            goto L_0x0040
        L_0x004c:
            byte[] r0 = r1.toByteArray()     // Catch:{ Exception -> 0x0067, all -> 0x0062 }
            if (r7 == 0) goto L_0x0055
            r7.close()     // Catch:{ Exception -> 0x0055 }
        L_0x0055:
            r1.close()     // Catch:{ Exception -> 0x0058 }
        L_0x0058:
            r6.m3128i()
            return r0
        L_0x005c:
            if (r7 == 0) goto L_0x008e
            r7.close()     // Catch:{ Exception -> 0x008e }
            goto L_0x008e
        L_0x0062:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0073
        L_0x0067:
            goto L_0x0085
        L_0x0069:
            r1 = move-exception
            r5 = r0
            r0 = r7
            r7 = r1
            r1 = r5
            goto L_0x0073
        L_0x006f:
            r1 = r0
            goto L_0x0085
        L_0x0071:
            r7 = move-exception
            r1 = r0
        L_0x0073:
            if (r0 == 0) goto L_0x007a
            r0.close()     // Catch:{ Exception -> 0x0079 }
            goto L_0x007a
        L_0x0079:
        L_0x007a:
            if (r1 == 0) goto L_0x007f
            r1.close()     // Catch:{ Exception -> 0x007f }
        L_0x007f:
            r6.m3128i()
            throw r7
        L_0x0083:
            r7 = r0
            r1 = r7
        L_0x0085:
            if (r7 == 0) goto L_0x008c
            r7.close()     // Catch:{ Exception -> 0x008b }
            goto L_0x008c
        L_0x008b:
        L_0x008c:
            if (r1 == 0) goto L_0x0091
        L_0x008e:
            r1.close()     // Catch:{ Exception -> 0x0091 }
        L_0x0091:
            r6.m3128i()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p088g.p089a.C1562a.m3126a(java.lang.String):byte[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: Can't wrap try/catch for region: R(14:7|8|9|10|11|(2:12|(1:14)(1:73))|15|16|17|(2:20|21)|22|23|24|26) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0087 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ae A[SYNTHETIC, Splitter:B:43:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b5 A[SYNTHETIC, Splitter:B:47:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00bc A[SYNTHETIC, Splitter:B:51:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00c8 A[SYNTHETIC, Splitter:B:60:0x00c8] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00cf A[SYNTHETIC, Splitter:B:64:0x00cf] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00d6 A[SYNTHETIC, Splitter:B:68:0x00d6] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m3127a(java.lang.String r8, byte[] r9) {
        /*
            r7 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r1.<init>(r8)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.URLConnection r8 = r1.openConnection()     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r7.f2980c = r8     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r1 = 1
            r8.setDoInput(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r8.setDoOutput(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.lang.String r2 = "POST"
            r8.setRequestMethod(r2)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r2 = 0
            r8.setUseCaches(r2)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r8.setInstanceFollowRedirects(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.lang.String r1 = "Content-Type"
            java.lang.String r3 = "application/octet-stream"
            r8.setRequestProperty(r1, r3)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r1 = 20000(0x4e20, float:2.8026E-41)
            r8.setConnectTimeout(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r8.setReadTimeout(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r8 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r8.connect()     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.io.DataOutputStream r8 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.net.HttpURLConnection r1 = r7.f2980c     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            java.io.OutputStream r1 = r1.getOutputStream()     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            r8.<init>(r1)     // Catch:{ Exception -> 0x00c3, all -> 0x00a9 }
            int r1 = r9.length     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            r8.write(r9, r2, r1)     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            r8.flush()     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            java.net.HttpURLConnection r9 = r7.f2980c     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            int r9 = r9.getResponseCode()     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r9 != r1) goto L_0x009d
            java.net.HttpURLConnection r9 = r7.f2980c     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ Exception -> 0x00a7, all -> 0x00a1 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x009b, all -> 0x0095 }
            r1.<init>()     // Catch:{ Exception -> 0x009b, all -> 0x0095 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0093, all -> 0x008e }
        L_0x0070:
            int r4 = r9.read(r3)     // Catch:{ Exception -> 0x0093, all -> 0x008e }
            r5 = -1
            if (r4 == r5) goto L_0x007b
            r1.write(r3, r2, r4)     // Catch:{ Exception -> 0x0093, all -> 0x008e }
            goto L_0x0070
        L_0x007b:
            byte[] r0 = r1.toByteArray()     // Catch:{ Exception -> 0x0093, all -> 0x008e }
            r8.close()     // Catch:{ Exception -> 0x0082 }
        L_0x0082:
            if (r9 == 0) goto L_0x0087
            r9.close()     // Catch:{ Exception -> 0x0087 }
        L_0x0087:
            r1.close()     // Catch:{ Exception -> 0x008a }
        L_0x008a:
            r7.m3128i()
            return r0
        L_0x008e:
            r0 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L_0x00ac
        L_0x0093:
            goto L_0x00c6
        L_0x0095:
            r1 = move-exception
            r6 = r0
            r0 = r8
            r8 = r1
            r1 = r6
            goto L_0x00ac
        L_0x009b:
            r1 = r0
            goto L_0x00c6
        L_0x009d:
            r8.close()     // Catch:{ Exception -> 0x00d9 }
            goto L_0x00d9
        L_0x00a1:
            r9 = move-exception
            r1 = r0
            r0 = r8
            r8 = r9
            r9 = r1
            goto L_0x00ac
        L_0x00a7:
            r9 = r0
            goto L_0x00c5
        L_0x00a9:
            r8 = move-exception
            r9 = r0
            r1 = r9
        L_0x00ac:
            if (r0 == 0) goto L_0x00b3
            r0.close()     // Catch:{ Exception -> 0x00b2 }
            goto L_0x00b3
        L_0x00b2:
        L_0x00b3:
            if (r9 == 0) goto L_0x00ba
            r9.close()     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00ba
        L_0x00b9:
        L_0x00ba:
            if (r1 == 0) goto L_0x00bf
            r1.close()     // Catch:{ Exception -> 0x00bf }
        L_0x00bf:
            r7.m3128i()
            throw r8
        L_0x00c3:
            r8 = r0
            r9 = r8
        L_0x00c5:
            r1 = r9
        L_0x00c6:
            if (r8 == 0) goto L_0x00cd
            r8.close()     // Catch:{ Exception -> 0x00cc }
            goto L_0x00cd
        L_0x00cc:
        L_0x00cd:
            if (r9 == 0) goto L_0x00d4
            r9.close()     // Catch:{ Exception -> 0x00d3 }
            goto L_0x00d4
        L_0x00d3:
        L_0x00d4:
            if (r1 == 0) goto L_0x00d9
            r1.close()     // Catch:{ Exception -> 0x00d9 }
        L_0x00d9:
            r7.m3128i()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p088g.p089a.C1562a.m3127a(java.lang.String, byte[]):byte[]");
    }

    /* renamed from: i */
    private void m3128i() {
        HttpURLConnection httpURLConnection = this.f2980c;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
                this.f2980c = null;
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483639;
    }

    /* renamed from: b_ */
    public final void mo14232b_() {
        super.mo14232b_();
        Process.setThreadPriority(10);
        C1563b bVar = this.f2979b;
        if (bVar == null || bVar.f2981b == null || (this.f2979b.f2982c != null && this.f2979b.f2982c.length > C1234k.f1827I * 1024)) {
            mo14305p();
            C1179b.m1354a(f2978a + "|run return ###");
            return;
        }
        try {
            byte[] a = this.f2979b.f2982c == null ? m3126a(this.f2979b.f2981b) : m3127a(this.f2979b.f2981b, this.f2979b.f2982c);
            if (a != null) {
                try {
                    this.f2979b.mo14696a(a);
                    C1174c.m1310b().mo14319a((Object) this.f2979b);
                    C1174c.m1310b().mo14268c();
                } catch (Exception e) {
                    this.f2979b.mo14695a(e);
                    throw e;
                }
            } else {
                Exception exc = new Exception("Http response ＝＝ null");
                this.f2979b.mo14695a(exc);
                throw exc;
            }
        } catch (Exception e2) {
            this.f2979b.mo14695a(e2);
            throw e2;
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        this.f1648n = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }

    /* renamed from: f */
    public void mo14233f() {
        super.mo14233f();
        m3128i();
    }
}
