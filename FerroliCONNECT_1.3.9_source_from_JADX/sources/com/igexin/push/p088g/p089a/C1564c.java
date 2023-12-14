package com.igexin.push.p088g.p089a;

import android.os.Process;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1234k;
import com.igexin.push.util.EncryptUtils;
import com.szacs.ferroliconnect.util.APConfig.UDPSocket;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.igexin.push.g.a.c */
public class C1564c extends C1190e {

    /* renamed from: a */
    public C1563b f2986a;

    /* renamed from: b */
    private HttpURLConnection f2987b;

    public C1564c(C1563b bVar) {
        super(0);
        this.f2986a = bVar;
    }

    /* renamed from: a */
    private C1565d m3141a(String str) {
        try {
            this.f2987b = m3148b(str);
            byte[] a = m3145a(this.f2987b);
            if (a != null) {
                C1565d b = m3147b(this.f2987b, a);
                m3150i();
                return b;
            }
        } catch (Throwable th) {
            m3150i();
            throw th;
        }
        m3150i();
        return new C1565d(this, false, (byte[]) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0056, code lost:
        if (r5 != null) goto L_0x0058;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004e A[SYNTHETIC, Splitter:B:23:0x004e] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.igexin.push.p088g.p089a.C1565d m3142a(java.lang.String r4, byte[] r5) {
        /*
            r3 = this;
            r0 = 0
            r1 = 0
            java.net.HttpURLConnection r4 = r3.m3149b((java.lang.String) r4, (byte[]) r5)     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            r3.f2987b = r4     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            java.net.HttpURLConnection r4 = r3.f2987b     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            byte[] r4 = r3.m3146a((byte[]) r5, (java.net.HttpURLConnection) r4)     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            if (r4 != 0) goto L_0x001a
            com.igexin.push.g.a.d r4 = new com.igexin.push.g.a.d     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            r5 = 1
            r4.<init>(r3, r5, r1)     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            r3.m3150i()
            return r4
        L_0x001a:
            java.net.HttpURLConnection r5 = r3.f2987b     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            r5.connect()     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            java.io.DataOutputStream r5 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            java.net.HttpURLConnection r2 = r3.f2987b     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            java.io.OutputStream r2 = r2.getOutputStream()     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x0055, all -> 0x004b }
            int r2 = r4.length     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r5.write(r4, r0, r2)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r5.flush()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.net.HttpURLConnection r4 = r3.f2987b     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            byte[] r4 = r3.m3145a((java.net.HttpURLConnection) r4)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r4 == 0) goto L_0x0058
            java.net.HttpURLConnection r2 = r3.f2987b     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            com.igexin.push.g.a.d r4 = r3.m3147b((java.net.HttpURLConnection) r2, (byte[]) r4)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r5.close()     // Catch:{ Exception -> 0x0042 }
        L_0x0042:
            r3.m3150i()
            return r4
        L_0x0046:
            r4 = move-exception
            r1 = r5
            goto L_0x004c
        L_0x0049:
            goto L_0x0056
        L_0x004b:
            r4 = move-exception
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ Exception -> 0x0051 }
        L_0x0051:
            r3.m3150i()
            throw r4
        L_0x0055:
            r5 = r1
        L_0x0056:
            if (r5 == 0) goto L_0x005b
        L_0x0058:
            r5.close()     // Catch:{ Exception -> 0x005b }
        L_0x005b:
            r3.m3150i()
            com.igexin.push.g.a.d r4 = new com.igexin.push.g.a.d
            r4.<init>(r3, r0, r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p088g.p089a.C1564c.m3142a(java.lang.String, byte[]):com.igexin.push.g.a.d");
    }

    /* renamed from: a */
    private void m3143a(HttpURLConnection httpURLConnection, byte[] bArr) {
        if (httpURLConnection != null) {
            byte[] bArr2 = new byte[0];
            if (bArr == null) {
                bArr = bArr2;
            }
            httpURLConnection.addRequestProperty("GT_C_T", String.valueOf(1));
            httpURLConnection.addRequestProperty("GT_C_K", new String(EncryptUtils.getRSAKeyId()));
            httpURLConnection.addRequestProperty("GT_C_V", EncryptUtils.getHttpGTCV());
            String valueOf = String.valueOf(System.currentTimeMillis());
            String httpSignature = EncryptUtils.getHttpSignature(valueOf, bArr);
            httpURLConnection.addRequestProperty("GT_T", valueOf);
            httpURLConnection.addRequestProperty("GT_C_S", httpSignature);
        }
    }

    /* renamed from: a */
    private void m3144a(byte[] bArr) {
        this.f2986a.mo14696a(bArr);
        C1174c.m1310b().mo14319a((Object) this.f2986a);
        C1174c.m1310b().mo14268c();
    }

    /* renamed from: a */
    private byte[] m3145a(HttpURLConnection httpURLConnection) {
        InputStream inputStream = null;
        try {
            InputStream inputStream2 = httpURLConnection.getInputStream();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (httpURLConnection.getResponseCode() == 200) {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream2.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (Exception unused) {
                        }
                    }
                    return byteArray;
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Exception unused2) {
                    }
                }
                return null;
            } catch (Exception e) {
                e = e;
                inputStream = inputStream2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            throw e;
        }
    }

    /* renamed from: a */
    private byte[] m3146a(byte[] bArr, HttpURLConnection httpURLConnection) {
        String requestProperty;
        try {
            if (!httpURLConnection.getRequestProperties().containsKey("GT_C_S") || (requestProperty = httpURLConnection.getRequestProperty("GT_C_S")) == null) {
                return null;
            }
            return EncryptUtils.aesEncHttp(bArr, EncryptUtils.md5(requestProperty.getBytes()));
        } catch (Throwable th) {
            C1179b.m1354a("_HttpTask|" + th.toString());
            return null;
        }
    }

    /* renamed from: b */
    private C1565d m3147b(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            String headerField = httpURLConnection.getHeaderField("GT_ERR");
            C1179b.m1354a("_HttpTask|GT_ERR = " + headerField);
            if (headerField != null) {
                if (headerField.equals("0")) {
                    String headerField2 = httpURLConnection.getHeaderField("GT_T");
                    if (headerField2 == null) {
                        C1179b.m1354a("_HttpTask|GT_T = null");
                        return new C1565d(this, true, (byte[]) null);
                    }
                    String headerField3 = httpURLConnection.getHeaderField("GT_C_S");
                    if (headerField3 == null) {
                        C1179b.m1354a("_HttpTask|GT_C_S = null");
                        return new C1565d(this, true, (byte[]) null);
                    }
                    byte[] aesDecHttp = EncryptUtils.aesDecHttp(bArr, EncryptUtils.md5(headerField2.getBytes()));
                    String httpSignature = EncryptUtils.getHttpSignature(headerField2, aesDecHttp);
                    if (httpSignature != null) {
                        if (httpSignature.equals(headerField3)) {
                            return new C1565d(this, false, aesDecHttp);
                        }
                    }
                    C1179b.m1354a("_HttpTask|signature = null or error");
                    return new C1565d(this, true, (byte[]) null);
                }
            }
            return new C1565d(this, true, (byte[]) null);
        } catch (Throwable th) {
            C1179b.m1354a("_HttpTask|" + th.toString());
            return new C1565d(this, true, (byte[]) null);
        }
    }

    /* renamed from: b */
    private HttpURLConnection m3148b(String str) {
        this.f2987b = (HttpURLConnection) new URL(str).openConnection();
        this.f2987b.setConnectTimeout(UDPSocket.CLIENT_PORT);
        this.f2987b.setReadTimeout(UDPSocket.CLIENT_PORT);
        this.f2987b.setRequestMethod("GET");
        this.f2987b.setDoInput(true);
        m3143a(this.f2987b, (byte[]) null);
        return this.f2987b;
    }

    /* renamed from: b */
    private HttpURLConnection m3149b(String str, byte[] bArr) {
        this.f2987b = (HttpURLConnection) new URL(str).openConnection();
        this.f2987b.setDoInput(true);
        this.f2987b.setDoOutput(true);
        this.f2987b.setRequestMethod("POST");
        this.f2987b.setUseCaches(false);
        this.f2987b.setInstanceFollowRedirects(true);
        this.f2987b.setRequestProperty("Content-Type", "application/octet-stream");
        this.f2987b.setConnectTimeout(UDPSocket.CLIENT_PORT);
        this.f2987b.setReadTimeout(UDPSocket.CLIENT_PORT);
        m3143a(this.f2987b, bArr);
        return this.f2987b;
    }

    /* renamed from: i */
    private void m3150i() {
        HttpURLConnection httpURLConnection = this.f2987b;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
                this.f2987b = null;
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483638;
    }

    /* renamed from: b_ */
    public final void mo14232b_() {
        super.mo14232b_();
        Process.setThreadPriority(10);
        C1563b bVar = this.f2986a;
        if (bVar == null || bVar.f2981b == null || (this.f2986a.f2982c != null && this.f2986a.f2982c.length > C1234k.f1827I * 1024)) {
            mo14305p();
            C1179b.m1354a("_HttpTask|run return ###");
            return;
        }
        if (this.f2986a.f2982c != null && this.f2986a.f2982c.length > 0) {
            C1563b bVar2 = this.f2986a;
            bVar2.f2982c = C1177f.m1340c(bVar2.f2982c);
        }
        int i = 0;
        while (i < 3) {
            C1565d a = this.f2986a.f2982c == null ? m3141a(this.f2986a.f2981b) : m3142a(this.f2986a.f2981b, this.f2986a.f2982c);
            if (a.f2988a) {
                throw new Exception("http server resp decode header error");
            } else if (a.f2989b != null) {
                m3144a(a.f2989b);
                return;
            } else if (i != 2) {
                i++;
            } else {
                this.f2986a.mo14695a(new Exception("try up to limit"));
                throw new Exception("http request exception, try times = " + (i + 1));
            }
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
        m3150i();
    }
}
