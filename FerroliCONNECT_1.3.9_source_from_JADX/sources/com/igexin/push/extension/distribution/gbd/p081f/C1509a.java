package com.igexin.push.extension.distribution.gbd.p081f;

import android.os.Process;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p086i.C1535c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1549q;
import com.igexin.push.extension.distribution.gbd.p086i.C1555w;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.a */
public class C1509a extends C1190e {

    /* renamed from: a */
    public C1516d f2902a;

    /* renamed from: b */
    private HttpURLConnection f2903b;

    /* renamed from: c */
    private boolean f2904c = false;

    public C1509a(C1516d dVar) {
        super(0);
        this.f2902a = dVar;
    }

    /* renamed from: a */
    private void m2867a(HttpURLConnection httpURLConnection, byte[] bArr) {
        if (httpURLConnection != null) {
            try {
                byte[] bArr2 = new byte[0];
                if (bArr == null) {
                    bArr = bArr2;
                }
                httpURLConnection.addRequestProperty("GT_C_T", String.valueOf(1));
                httpURLConnection.addRequestProperty("GT_C_K", new String(C1555w.m3068b()));
                httpURLConnection.addRequestProperty("GT_C_V", C1555w.m3071d());
                String valueOf = String.valueOf(System.currentTimeMillis());
                String a = C1555w.m3064a(valueOf, bArr);
                httpURLConnection.addRequestProperty("GT_T", valueOf);
                httpURLConnection.addRequestProperty("GT_C_S", a);
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x01b2 A[SYNTHETIC, Splitter:B:106:0x01b2] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01bc A[Catch:{ all -> 0x01b6, Throwable -> 0x01b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01c1 A[Catch:{ all -> 0x01b6, Throwable -> 0x01b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0182 A[Catch:{ all -> 0x01ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0192 A[SYNTHETIC, Splitter:B:88:0x0192] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x019c A[Catch:{ all -> 0x0196, Throwable -> 0x0198 }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01a1 A[Catch:{ all -> 0x0196, Throwable -> 0x0198 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m2868a(java.util.Map<java.lang.String, java.util.List<java.lang.String>> r9) {
        /*
            r8 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.lang.String r2 = r2.mo15157k()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r8.f2903b = r1     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            int r2 = r2.mo15151e()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            int r2 = r2.mo15152f()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setReadTimeout(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15154h()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setDoInput(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15153g()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setDoOutput(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.lang.String r2 = "POST"
            r1.setRequestMethod(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15150d()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setUseCaches(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15149c()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r1.setInstanceFollowRedirects(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r1 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.util.HashMap r1 = r1.mo15155i()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.util.Set r1 = r1.keySet()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
        L_0x006b:
            boolean r2 = r1.hasNext()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            if (r2 == 0) goto L_0x0089
            java.lang.Object r2 = r1.next()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r3 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r4 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.util.HashMap r4 = r4.mo15155i()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r3.setRequestProperty(r2, r4)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            goto L_0x006b
        L_0x0089:
            com.igexin.push.extension.distribution.gbd.f.d r1 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            byte[] r1 = r1.mo15158l()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15156j()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            if (r2 == 0) goto L_0x009c
            byte[] r1 = r8.m2869a((byte[]) r1)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            goto L_0x00a8
        L_0x009c:
            com.igexin.push.extension.distribution.gbd.f.d r2 = r8.f2902a     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            boolean r2 = r2.mo15147a()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            if (r2 == 0) goto L_0x00a8
            byte[] r1 = com.igexin.p032b.p042b.C1196a.m1438b(r1)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
        L_0x00a8:
            if (r1 != 0) goto L_0x00ae
            r8.m2872i()
            return r0
        L_0x00ae:
            java.net.HttpURLConnection r2 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r2.connect()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.net.HttpURLConnection r3 = r8.f2903b     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            java.io.OutputStream r3 = r3.getOutputStream()     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x017a, all -> 0x0176 }
            int r3 = r1.length     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            r4 = 0
            r2.write(r1, r4, r3)     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            r2.flush()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            int r1 = r1.getResponseCode()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x0141
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.util.Map r1 = r1.getHeaderFields()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            if (r1 == 0) goto L_0x00e1
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.util.Map r1 = r1.getHeaderFields()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            r9.putAll(r1)     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
        L_0x00e1:
            java.net.HttpURLConnection r9 = r8.f2903b     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x013b, all -> 0x0133 }
            r1.<init>()     // Catch:{ Throwable -> 0x013b, all -> 0x0133 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
        L_0x00f0:
            int r5 = r9.read(r3)     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            r6 = -1
            if (r5 == r6) goto L_0x00fb
            r1.write(r3, r4, r5)     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            goto L_0x00f0
        L_0x00fb:
            byte[] r3 = r1.toByteArray()     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            if (r3 == 0) goto L_0x0152
            java.net.HttpURLConnection r3 = r8.f2903b     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            byte[] r4 = r1.toByteArray()     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            byte[] r0 = r8.m2870b(r3, r4)     // Catch:{ Throwable -> 0x012c, all -> 0x0126 }
            r2.close()     // Catch:{ Throwable -> 0x011c }
            if (r9 == 0) goto L_0x0113
            r9.close()     // Catch:{ Throwable -> 0x011c }
        L_0x0113:
            r1.close()     // Catch:{ Throwable -> 0x011c }
        L_0x0116:
            r8.m2872i()
            goto L_0x0121
        L_0x011a:
            r9 = move-exception
            goto L_0x0122
        L_0x011c:
            r9 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r9)     // Catch:{ all -> 0x011a }
            goto L_0x0116
        L_0x0121:
            return r0
        L_0x0122:
            r8.m2872i()
            throw r9
        L_0x0126:
            r0 = move-exception
            r3 = r1
            r1 = r9
            r9 = r0
            goto L_0x01af
        L_0x012c:
            r3 = move-exception
            r7 = r1
            r1 = r9
            r9 = r3
            r3 = r7
            goto L_0x017e
        L_0x0133:
            r1 = move-exception
            r3 = r0
            r0 = r2
            r7 = r1
            r1 = r9
            r9 = r7
            goto L_0x01b0
        L_0x013b:
            r1 = move-exception
            r3 = r0
            r7 = r1
            r1 = r9
            r9 = r7
            goto L_0x017e
        L_0x0141:
            com.igexin.push.extension.distribution.gbd.f.d r9 = r8.f2902a     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            if (r9 == 0) goto L_0x0150
            com.igexin.push.extension.distribution.gbd.f.d r9 = r8.f2902a     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            java.net.HttpURLConnection r1 = r8.f2903b     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            int r1 = r1.getResponseCode()     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
            r9.mo15137a((int) r1)     // Catch:{ Throwable -> 0x0172, all -> 0x016e }
        L_0x0150:
            r9 = r0
            r1 = r9
        L_0x0152:
            r2.close()     // Catch:{ Throwable -> 0x0165 }
            if (r9 == 0) goto L_0x015a
            r9.close()     // Catch:{ Throwable -> 0x0165 }
        L_0x015a:
            if (r1 == 0) goto L_0x015f
            r1.close()     // Catch:{ Throwable -> 0x0165 }
        L_0x015f:
            r8.m2872i()
            goto L_0x01ad
        L_0x0163:
            r9 = move-exception
            goto L_0x016a
        L_0x0165:
            r9 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r9)     // Catch:{ all -> 0x0163 }
            goto L_0x015f
        L_0x016a:
            r8.m2872i()
            throw r9
        L_0x016e:
            r9 = move-exception
            r1 = r0
            r3 = r1
            goto L_0x01af
        L_0x0172:
            r9 = move-exception
            r1 = r0
            r3 = r1
            goto L_0x017e
        L_0x0176:
            r9 = move-exception
            r1 = r0
            r3 = r1
            goto L_0x01b0
        L_0x017a:
            r9 = move-exception
            r1 = r0
            r2 = r1
            r3 = r2
        L_0x017e:
            com.igexin.push.extension.distribution.gbd.f.d r4 = r8.f2902a     // Catch:{ all -> 0x01ae }
            if (r4 == 0) goto L_0x0187
            com.igexin.push.extension.distribution.gbd.f.d r4 = r8.f2902a     // Catch:{ all -> 0x01ae }
            r4.mo15138a((java.lang.Throwable) r9)     // Catch:{ all -> 0x01ae }
        L_0x0187:
            java.lang.String r4 = "GBDAsyncHttpTask"
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x01ae }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r4, r9)     // Catch:{ all -> 0x01ae }
            if (r2 == 0) goto L_0x019a
            r2.close()     // Catch:{ Throwable -> 0x0198 }
            goto L_0x019a
        L_0x0196:
            r9 = move-exception
            goto L_0x01a9
        L_0x0198:
            r9 = move-exception
            goto L_0x01a5
        L_0x019a:
            if (r1 == 0) goto L_0x019f
            r1.close()     // Catch:{ Throwable -> 0x0198 }
        L_0x019f:
            if (r3 == 0) goto L_0x015f
            r3.close()     // Catch:{ Throwable -> 0x0198 }
            goto L_0x015f
        L_0x01a5:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r9)     // Catch:{ all -> 0x0196 }
            goto L_0x015f
        L_0x01a9:
            r8.m2872i()
            throw r9
        L_0x01ad:
            return r0
        L_0x01ae:
            r9 = move-exception
        L_0x01af:
            r0 = r2
        L_0x01b0:
            if (r0 == 0) goto L_0x01ba
            r0.close()     // Catch:{ Throwable -> 0x01b8 }
            goto L_0x01ba
        L_0x01b6:
            r9 = move-exception
            goto L_0x01c9
        L_0x01b8:
            r0 = move-exception
            goto L_0x01c5
        L_0x01ba:
            if (r1 == 0) goto L_0x01bf
            r1.close()     // Catch:{ Throwable -> 0x01b8 }
        L_0x01bf:
            if (r3 == 0) goto L_0x01cd
            r3.close()     // Catch:{ Throwable -> 0x01b8 }
            goto L_0x01cd
        L_0x01c5:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x01b6 }
            goto L_0x01cd
        L_0x01c9:
            r8.m2872i()
            throw r9
        L_0x01cd:
            r8.m2872i()
            goto L_0x01d2
        L_0x01d1:
            throw r9
        L_0x01d2:
            goto L_0x01d1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p081f.C1509a.m2868a(java.util.Map):byte[]");
    }

    /* renamed from: a */
    private byte[] m2869a(byte[] bArr) {
        byte[] a = C1177f.m1332a(bArr);
        m2867a(this.f2903b, a);
        String requestProperty = this.f2903b.getRequestProperty("GT_C_S");
        return requestProperty != null ? C1555w.m3067a(a, C1549q.m3042a(requestProperty.getBytes())) : a;
    }

    /* renamed from: b */
    private byte[] m2870b(HttpURLConnection httpURLConnection, byte[] bArr) {
        String headerField;
        byte[] b;
        String a;
        try {
            if (!this.f2902a.mo15156j()) {
                return this.f2902a.mo15148b() ? C1196a.m1439c(C1535c.m2986a(bArr, 0)) : bArr;
            }
            String headerField2 = httpURLConnection.getHeaderField("GT_ERR");
            if (headerField2 != null) {
                if (headerField2.equals("0")) {
                    String headerField3 = httpURLConnection.getHeaderField("GT_T");
                    if (!(headerField3 == null || (headerField = httpURLConnection.getHeaderField("GT_C_S")) == null || (a = C1555w.m3064a(headerField3, b)) == null || !a.equals(headerField))) {
                        return (b = C1555w.m3069b(bArr, C1549q.m3042a(headerField3.getBytes())));
                    }
                }
            }
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x0125 A[Catch:{ all -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x012f A[SYNTHETIC, Splitter:B:71:0x012f] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0139 A[Catch:{ all -> 0x0133, Throwable -> 0x0135 }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x014a A[SYNTHETIC, Splitter:B:87:0x014a] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0154 A[Catch:{ all -> 0x014e, Throwable -> 0x0150 }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m2871b(java.util.Map<java.lang.String, java.util.List<java.lang.String>> r6) {
        /*
            r5 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.lang.String r2 = r2.mo15157k()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r5.f2903b = r1     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            int r2 = r2.mo15151e()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            int r2 = r2.mo15152f()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.setReadTimeout(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            boolean r2 = r2.mo15154h()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.setDoInput(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.lang.String r2 = "GET"
            r1.setRequestMethod(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            boolean r2 = r2.mo15150d()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.setUseCaches(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r2 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            boolean r2 = r2.mo15149c()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r1.setInstanceFollowRedirects(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r1 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.util.HashMap r1 = r1.mo15155i()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.util.Set r1 = r1.keySet()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
        L_0x0060:
            boolean r2 = r1.hasNext()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            if (r2 == 0) goto L_0x007e
            java.lang.Object r2 = r1.next()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.net.HttpURLConnection r3 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            com.igexin.push.extension.distribution.gbd.f.d r4 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.util.HashMap r4 = r4.mo15155i()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r3.setRequestProperty(r2, r4)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            goto L_0x0060
        L_0x007e:
            com.igexin.push.extension.distribution.gbd.f.d r1 = r5.f2902a     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            boolean r1 = r1.mo15156j()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            if (r1 == 0) goto L_0x008b
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            r5.m2867a(r1, r0)     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
        L_0x008b:
            java.net.HttpURLConnection r1 = r5.f2903b     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.io.InputStream r1 = r1.getInputStream()     // Catch:{ Throwable -> 0x011e, all -> 0x011b }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0118, all -> 0x0115 }
            r2.<init>()     // Catch:{ Throwable -> 0x0118, all -> 0x0115 }
            java.net.HttpURLConnection r3 = r5.f2903b     // Catch:{ Throwable -> 0x0113 }
            int r3 = r3.getResponseCode()     // Catch:{ Throwable -> 0x0113 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 != r4) goto L_0x00eb
            java.net.HttpURLConnection r3 = r5.f2903b     // Catch:{ Throwable -> 0x0113 }
            java.util.Map r3 = r3.getHeaderFields()     // Catch:{ Throwable -> 0x0113 }
            if (r3 == 0) goto L_0x00b1
            java.net.HttpURLConnection r3 = r5.f2903b     // Catch:{ Throwable -> 0x0113 }
            java.util.Map r3 = r3.getHeaderFields()     // Catch:{ Throwable -> 0x0113 }
            r6.putAll(r3)     // Catch:{ Throwable -> 0x0113 }
        L_0x00b1:
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0113 }
        L_0x00b5:
            int r3 = r1.read(r6)     // Catch:{ Throwable -> 0x0113 }
            r4 = -1
            if (r3 == r4) goto L_0x00c1
            r4 = 0
            r2.write(r6, r4, r3)     // Catch:{ Throwable -> 0x0113 }
            goto L_0x00b5
        L_0x00c1:
            byte[] r6 = r2.toByteArray()     // Catch:{ Throwable -> 0x0113 }
            if (r6 == 0) goto L_0x00fa
            java.net.HttpURLConnection r6 = r5.f2903b     // Catch:{ Throwable -> 0x0113 }
            byte[] r3 = r2.toByteArray()     // Catch:{ Throwable -> 0x0113 }
            byte[] r6 = r5.m2870b(r6, r3)     // Catch:{ Throwable -> 0x0113 }
            if (r1 == 0) goto L_0x00db
            r1.close()     // Catch:{ Throwable -> 0x00d9 }
            goto L_0x00db
        L_0x00d7:
            r6 = move-exception
            goto L_0x00e7
        L_0x00d9:
            r0 = move-exception
            goto L_0x00e2
        L_0x00db:
            r2.close()     // Catch:{ Throwable -> 0x00d9 }
        L_0x00de:
            r5.m2872i()
            goto L_0x00e6
        L_0x00e2:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x00de
        L_0x00e6:
            return r6
        L_0x00e7:
            r5.m2872i()
            throw r6
        L_0x00eb:
            com.igexin.push.extension.distribution.gbd.f.d r6 = r5.f2902a     // Catch:{ Throwable -> 0x0113 }
            if (r6 == 0) goto L_0x00fa
            com.igexin.push.extension.distribution.gbd.f.d r6 = r5.f2902a     // Catch:{ Throwable -> 0x0113 }
            java.net.HttpURLConnection r3 = r5.f2903b     // Catch:{ Throwable -> 0x0113 }
            int r3 = r3.getResponseCode()     // Catch:{ Throwable -> 0x0113 }
            r6.mo15137a((int) r3)     // Catch:{ Throwable -> 0x0113 }
        L_0x00fa:
            if (r1 == 0) goto L_0x0104
            r1.close()     // Catch:{ Throwable -> 0x0102 }
            goto L_0x0104
        L_0x0100:
            r6 = move-exception
            goto L_0x010f
        L_0x0102:
            r6 = move-exception
            goto L_0x010b
        L_0x0104:
            r2.close()     // Catch:{ Throwable -> 0x0102 }
        L_0x0107:
            r5.m2872i()
            goto L_0x0145
        L_0x010b:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r6)     // Catch:{ all -> 0x0100 }
            goto L_0x0107
        L_0x010f:
            r5.m2872i()
            throw r6
        L_0x0113:
            r6 = move-exception
            goto L_0x0121
        L_0x0115:
            r6 = move-exception
            r2 = r0
            goto L_0x0147
        L_0x0118:
            r6 = move-exception
            r2 = r0
            goto L_0x0121
        L_0x011b:
            r6 = move-exception
            r2 = r0
            goto L_0x0148
        L_0x011e:
            r6 = move-exception
            r1 = r0
            r2 = r1
        L_0x0121:
            com.igexin.push.extension.distribution.gbd.f.d r3 = r5.f2902a     // Catch:{ all -> 0x0146 }
            if (r3 == 0) goto L_0x012a
            com.igexin.push.extension.distribution.gbd.f.d r3 = r5.f2902a     // Catch:{ all -> 0x0146 }
            r3.mo15138a((java.lang.Throwable) r6)     // Catch:{ all -> 0x0146 }
        L_0x012a:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r6)     // Catch:{ all -> 0x0146 }
            if (r1 == 0) goto L_0x0137
            r1.close()     // Catch:{ Throwable -> 0x0135 }
            goto L_0x0137
        L_0x0133:
            r6 = move-exception
            goto L_0x0141
        L_0x0135:
            r6 = move-exception
            goto L_0x013d
        L_0x0137:
            if (r2 == 0) goto L_0x0107
            r2.close()     // Catch:{ Throwable -> 0x0135 }
            goto L_0x0107
        L_0x013d:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r6)     // Catch:{ all -> 0x0133 }
            goto L_0x0107
        L_0x0141:
            r5.m2872i()
            throw r6
        L_0x0145:
            return r0
        L_0x0146:
            r6 = move-exception
        L_0x0147:
            r0 = r1
        L_0x0148:
            if (r0 == 0) goto L_0x0152
            r0.close()     // Catch:{ Throwable -> 0x0150 }
            goto L_0x0152
        L_0x014e:
            r6 = move-exception
            goto L_0x015c
        L_0x0150:
            r0 = move-exception
            goto L_0x0158
        L_0x0152:
            if (r2 == 0) goto L_0x0160
            r2.close()     // Catch:{ Throwable -> 0x0150 }
            goto L_0x0160
        L_0x0158:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x014e }
            goto L_0x0160
        L_0x015c:
            r5.m2872i()
            throw r6
        L_0x0160:
            r5.m2872i()
            goto L_0x0165
        L_0x0164:
            throw r6
        L_0x0165:
            goto L_0x0164
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p081f.C1509a.m2871b(java.util.Map):byte[]");
    }

    /* renamed from: i */
    private void m2872i() {
        HttpURLConnection httpURLConnection = this.f2903b;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
                this.f2903b = null;
            } catch (Throwable th) {
                C1540h.m2996a(th);
                C1540h.m2997b("GBDAsyncHttpTask", th.toString());
            }
        }
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483639;
    }

    /* renamed from: b_ */
    public final void mo14232b_() {
        try {
            Process.setThreadPriority(10);
            if (this.f2904c) {
                mo14305p();
                return;
            }
            this.f2904c = true;
            if (this.f2902a != null) {
                if (this.f2902a.f2908b != null) {
                    if (this.f2902a.f2908b.length > C1488a.f2684ao * 1024) {
                        C1540h.m2997b("GBDAsyncHttpTask", "http data size (" + this.f2902a.f2908b.length + ") > max size (" + (C1488a.f2684ao * 1024) + ")");
                        this.f2902a.mo15138a((Throwable) new Exception("HttpPlugin length over max size."));
                        return;
                    }
                }
                this.f2902a.mo15159m();
                if (!TextUtils.isEmpty(this.f2902a.f2907a)) {
                    C1540h.m2995a("GBDAsyncHttpTask", "-----------" + this.f2902a.f2907a + "-----------");
                    HashMap hashMap = new HashMap();
                    byte[] b = this.f2902a.f2908b == null ? m2871b(hashMap) : m2868a((Map<String, List<String>>) hashMap);
                    if (b != null) {
                        this.f2902a.mo15139a((Map<String, List<String>>) hashMap, b);
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
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
        m2872i();
    }
}
