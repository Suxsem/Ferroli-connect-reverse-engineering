package com.igexin.push.extension.distribution.basic.p064f;

import android.os.Process;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p068j.C1435c;
import com.igexin.push.extension.distribution.basic.p068j.C1445m;
import com.szacs.ferroliconnect.util.APConfig.UDPSocket;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.igexin.push.extension.distribution.basic.f.a */
public class C1422a extends C1190e {

    /* renamed from: a */
    public C1427f f2458a;

    /* renamed from: b */
    public HttpURLConnection f2459b;

    /* renamed from: c */
    public boolean f2460c;

    /* renamed from: d */
    private boolean f2461d;

    public C1422a(C1427f fVar) {
        super(0);
        this.f2458a = fVar;
        C1179b.m1354a("AsyncHttpTask|httpPlugin = " + fVar);
    }

    /* renamed from: a */
    private C1423b m2451a(String str) {
        try {
            this.f2459b = m2459b(str);
            byte[] a = m2456a(this.f2459b);
            if (a != null) {
                C1423b b = m2458b(this.f2459b, a);
                m2462i();
                return b;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            m2462i();
            throw th;
        }
        m2462i();
        return new C1423b(this, false, (byte[]) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c7, code lost:
        if (r6 == null) goto L_0x00cc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d9 A[SYNTHETIC, Splitter:B:42:0x00d9] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.igexin.push.extension.distribution.basic.p064f.C1423b m2452a(java.lang.String r5, byte[] r6) {
        /*
            r4 = this;
            java.lang.String r0 = "AsyncHttpTask|call httpPost start ###"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            r0 = 0
            r1 = 0
            boolean r2 = r4.m2461b((byte[]) r6)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            if (r2 == 0) goto L_0x0016
            com.igexin.push.extension.distribution.basic.f.b r5 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.<init>(r4, r0, r1)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r4.m2462i()
            return r5
        L_0x0016:
            java.net.HttpURLConnection r5 = r4.m2460b((java.lang.String) r5, (byte[]) r6)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r4.f2459b = r5     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.<init>()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r2 = "AsyncHttpTask|httpPost() src body len = "
            r5.append(r2)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            int r2 = r6.length     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.append(r2)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.net.HttpURLConnection r5 = r4.f2459b     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            byte[] r5 = r4.m2457a((byte[]) r6, (java.net.HttpURLConnection) r5)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            if (r5 != 0) goto L_0x0048
            java.lang.String r5 = "AsyncHttpTask|httpPost() getEncHttpData body = null"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            com.igexin.push.extension.distribution.basic.f.b r5 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6 = 1
            r5.<init>(r4, r6, r1)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r4.m2462i()
            return r5
        L_0x0048:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6.<init>()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r2 = "AsyncHttpTask|httpPost() getEncHttpData len = "
            r6.append(r2)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            int r2 = r5.length     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6.append(r2)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r6)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.net.HttpURLConnection r6 = r4.f2459b     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6.connect()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.DataOutputStream r6 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.net.HttpURLConnection r2 = r4.f2459b     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.OutputStream r2 = r2.getOutputStream()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6.<init>(r2)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            int r2 = r5.length     // Catch:{ Exception -> 0x00a9 }
            r6.write(r5, r0, r2)     // Catch:{ Exception -> 0x00a9 }
            r6.flush()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r5 = "AsyncHttpTask|httpPost() write and flush"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Exception -> 0x00a9 }
            java.net.HttpURLConnection r5 = r4.f2459b     // Catch:{ Exception -> 0x00a9 }
            byte[] r5 = r4.m2456a((java.net.HttpURLConnection) r5)     // Catch:{ Exception -> 0x00a9 }
            if (r5 == 0) goto L_0x00a3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a9 }
            r2.<init>()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "AsyncHttpTask|httpPost() server resp len ="
            r2.append(r3)     // Catch:{ Exception -> 0x00a9 }
            int r3 = r5.length     // Catch:{ Exception -> 0x00a9 }
            r2.append(r3)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a9 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r2)     // Catch:{ Exception -> 0x00a9 }
            java.net.HttpURLConnection r2 = r4.f2459b     // Catch:{ Exception -> 0x00a9 }
            com.igexin.push.extension.distribution.basic.f.b r5 = r4.m2458b((java.net.HttpURLConnection) r2, (byte[]) r5)     // Catch:{ Exception -> 0x00a9 }
            r6.close()     // Catch:{ Exception -> 0x009f }
        L_0x009f:
            r4.m2462i()
            return r5
        L_0x00a3:
            java.lang.String r5 = "AsyncHttpTask|httpPost() server resp is null"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00c9
        L_0x00a9:
            r5 = move-exception
            goto L_0x00af
        L_0x00ab:
            r5 = move-exception
            goto L_0x00d7
        L_0x00ad:
            r5 = move-exception
            r6 = r1
        L_0x00af:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d5 }
            r2.<init>()     // Catch:{ all -> 0x00d5 }
            java.lang.String r3 = "AsyncHttpTask httpPost|error|"
            r2.append(r3)     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x00d5 }
            r2.append(r5)     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x00d5 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ all -> 0x00d5 }
            if (r6 == 0) goto L_0x00cc
        L_0x00c9:
            r6.close()     // Catch:{ Exception -> 0x00cc }
        L_0x00cc:
            r4.m2462i()
            com.igexin.push.extension.distribution.basic.f.b r5 = new com.igexin.push.extension.distribution.basic.f.b
            r5.<init>(r4, r0, r1)
            return r5
        L_0x00d5:
            r5 = move-exception
            r1 = r6
        L_0x00d7:
            if (r1 == 0) goto L_0x00dc
            r1.close()     // Catch:{ Exception -> 0x00dc }
        L_0x00dc:
            r4.m2462i()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p064f.C1422a.m2452a(java.lang.String, byte[]):com.igexin.push.extension.distribution.basic.f.b");
    }

    /* renamed from: a */
    private Method m2453a(String str, Class<?>... clsArr) {
        try {
            return Class.forName("com.igexin.push.util.EncryptUtils").getMethod(str, clsArr);
        } catch (Exception unused) {
            C1179b.m1354a(this.f1613l + "invokeMethod error");
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: byte[]} */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=java.lang.Object, for r9v0, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2454a(java.net.HttpURLConnection r8, java.lang.Object r9) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            r0 = 0
            byte[] r1 = new byte[r0]
            if (r9 == 0) goto L_0x0009
            goto L_0x000a
        L_0x0009:
            r9 = r1
        L_0x000a:
            r1 = 1
            java.lang.String r2 = java.lang.String.valueOf(r1)
            java.lang.String r3 = "GT_C_T"
            r8.addRequestProperty(r3, r2)
            java.lang.String r2 = new java.lang.String
            java.lang.Class[] r3 = new java.lang.Class[r0]
            java.lang.String r4 = "getRSAKeyId"
            java.lang.reflect.Method r3 = r7.m2453a((java.lang.String) r4, (java.lang.Class<?>[]) r3)
            java.lang.Object[] r4 = new java.lang.Object[r0]
            r5 = 0
            java.lang.Object r3 = r3.invoke(r5, r4)
            byte[] r3 = (byte[]) r3
            byte[] r3 = (byte[]) r3
            r2.<init>(r3)
            java.lang.String r3 = "GT_C_K"
            r8.addRequestProperty(r3, r2)
            java.lang.Class[] r2 = new java.lang.Class[r0]
            java.lang.String r3 = "getHttpGTCV"
            java.lang.reflect.Method r2 = r7.m2453a((java.lang.String) r3, (java.lang.Class<?>[]) r2)
            java.lang.Object[] r3 = new java.lang.Object[r0]
            java.lang.Object r2 = r2.invoke(r5, r3)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r3 = "GT_C_V"
            r8.addRequestProperty(r3, r2)
            long r2 = java.lang.System.currentTimeMillis()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r4[r0] = r6
            java.lang.Class<byte[]> r6 = byte[].class
            r4[r1] = r6
            java.lang.String r6 = "getHttpSignature"
            java.lang.reflect.Method r4 = r7.m2453a((java.lang.String) r6, (java.lang.Class<?>[]) r4)
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r0] = r2
            r3[r1] = r9
            java.lang.Object r9 = r4.invoke(r5, r3)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r0 = "GT_T"
            r8.addRequestProperty(r0, r2)
            java.lang.String r0 = "GT_C_S"
            r8.addRequestProperty(r0, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p064f.C1422a.m2454a(java.net.HttpURLConnection, byte[]):void");
    }

    /* renamed from: a */
    private void m2455a(byte[] bArr) {
        this.f2458a.mo14975a(bArr);
        C1174c.m1310b().mo14319a((Object) this.f2458a);
        C1174c.m1310b().mo14268c();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:(2:21|22)|23|24|25) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:8|(2:9|(1:11)(1:51))|12|(2:14|15)|16|17|18) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x002b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0034 */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x004e A[SYNTHETIC, Splitter:B:43:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0055 A[SYNTHETIC, Splitter:B:47:0x0055] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m2456a(java.net.HttpURLConnection r5) {
        /*
            r4 = this;
            r0 = 0
            java.io.InputStream r1 = r5.getInputStream()     // Catch:{ Exception -> 0x0047, all -> 0x0043 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            r2.<init>()     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            int r5 = r5.getResponseCode()     // Catch:{ Exception -> 0x003a, all -> 0x0038 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r5 != r3) goto L_0x002f
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x003a, all -> 0x0038 }
        L_0x0016:
            int r0 = r1.read(r5)     // Catch:{ Exception -> 0x003a, all -> 0x0038 }
            r3 = -1
            if (r0 == r3) goto L_0x0022
            r3 = 0
            r2.write(r5, r3, r0)     // Catch:{ Exception -> 0x003a, all -> 0x0038 }
            goto L_0x0016
        L_0x0022:
            byte[] r5 = r2.toByteArray()     // Catch:{ Exception -> 0x003a, all -> 0x0038 }
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch:{ Exception -> 0x002b }
        L_0x002b:
            r2.close()     // Catch:{ Exception -> 0x002e }
        L_0x002e:
            return r5
        L_0x002f:
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            r2.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            return r0
        L_0x0038:
            r5 = move-exception
            goto L_0x004c
        L_0x003a:
            r5 = move-exception
            goto L_0x0041
        L_0x003c:
            r5 = move-exception
            r2 = r0
            goto L_0x004c
        L_0x003f:
            r5 = move-exception
            r2 = r0
        L_0x0041:
            r0 = r1
            goto L_0x0049
        L_0x0043:
            r5 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x004c
        L_0x0047:
            r5 = move-exception
            r2 = r0
        L_0x0049:
            throw r5     // Catch:{ all -> 0x004a }
        L_0x004a:
            r5 = move-exception
            r1 = r0
        L_0x004c:
            if (r1 == 0) goto L_0x0053
            r1.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0053
        L_0x0052:
        L_0x0053:
            if (r2 == 0) goto L_0x0058
            r2.close()     // Catch:{ Exception -> 0x0058 }
        L_0x0058:
            goto L_0x005a
        L_0x0059:
            throw r5
        L_0x005a:
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p064f.C1422a.m2456a(java.net.HttpURLConnection):byte[]");
    }

    /* renamed from: a */
    private byte[] m2457a(byte[] bArr, HttpURLConnection httpURLConnection) {
        try {
            C1179b.m1354a("AsyncHttpTask|getEncHttpData|isUseAES = |" + this.f2460c);
            if (!this.f2460c) {
                return C1445m.m2535a(bArr);
            }
            String requestProperty = httpURLConnection.getRequestProperty("GT_C_S");
            if (requestProperty != null) {
                return (byte[]) m2453a("aesEncHttp", (Class<?>[]) new Class[]{byte[].class, byte[].class}).invoke((Object) null, new Object[]{bArr, m2453a("md5", (Class<?>[]) new Class[]{byte[].class}).invoke((Object) null, new Object[]{requestProperty.getBytes()})});
            }
            return null;
        } catch (Throwable th) {
            C1179b.m1354a("AsyncHttpTask|getEncHttpData|error|" + th.getMessage());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.igexin.push.extension.distribution.basic.p064f.C1423b m2458b(java.net.HttpURLConnection r10, byte[] r11) {
        /*
            r9 = this;
            java.lang.String r0 = "AsyncHttpTask|authAndDecResp start ~~~"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            r0 = 0
            r1 = 1
            boolean r2 = r9.f2460c     // Catch:{ Throwable -> 0x0121 }
            r3 = 0
            if (r2 == 0) goto L_0x0103
            java.lang.String r2 = "GT_ERR"
            java.lang.String r2 = r10.getHeaderField(r2)     // Catch:{ Throwable -> 0x0121 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0121 }
            r4.<init>()     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r5 = r9.f1613l     // Catch:{ Throwable -> 0x0121 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r5 = "|GT_ERR = "
            r4.append(r5)     // Catch:{ Throwable -> 0x0121 }
            r4.append(r2)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0121 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)     // Catch:{ Throwable -> 0x0121 }
            if (r2 == 0) goto L_0x00fd
            java.lang.String r4 = "0"
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0121 }
            if (r2 != 0) goto L_0x0037
            goto L_0x00fd
        L_0x0037:
            java.lang.String r2 = "GT_T"
            java.lang.String r2 = r10.getHeaderField(r2)     // Catch:{ Throwable -> 0x0121 }
            if (r2 != 0) goto L_0x005b
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0121 }
            r10.<init>()     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = r9.f1613l     // Catch:{ Throwable -> 0x0121 }
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = "|GT_T = null"
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0121 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r10)     // Catch:{ Throwable -> 0x0121 }
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r1, r0)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x005b:
            java.lang.String r4 = "GT_C_S"
            java.lang.String r10 = r10.getHeaderField(r4)     // Catch:{ Throwable -> 0x0121 }
            if (r10 != 0) goto L_0x007f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0121 }
            r10.<init>()     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = r9.f1613l     // Catch:{ Throwable -> 0x0121 }
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = "|GT_C_S = null"
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0121 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r10)     // Catch:{ Throwable -> 0x0121 }
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r1, r0)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x007f:
            java.lang.String r4 = "aesDecHttp"
            r5 = 2
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch:{ Throwable -> 0x0121 }
            java.lang.Class<byte[]> r7 = byte[].class
            r6[r3] = r7     // Catch:{ Throwable -> 0x0121 }
            java.lang.Class<byte[]> r7 = byte[].class
            r6[r1] = r7     // Catch:{ Throwable -> 0x0121 }
            java.lang.reflect.Method r4 = r9.m2453a((java.lang.String) r4, (java.lang.Class<?>[]) r6)     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0121 }
            r6[r3] = r11     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = "md5"
            java.lang.Class[] r7 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x0121 }
            java.lang.Class<byte[]> r8 = byte[].class
            r7[r3] = r8     // Catch:{ Throwable -> 0x0121 }
            java.lang.reflect.Method r11 = r9.m2453a((java.lang.String) r11, (java.lang.Class<?>[]) r7)     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0121 }
            byte[] r8 = r2.getBytes()     // Catch:{ Throwable -> 0x0121 }
            r7[r3] = r8     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object r11 = r11.invoke(r0, r7)     // Catch:{ Throwable -> 0x0121 }
            r6[r1] = r11     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object r11 = r4.invoke(r0, r6)     // Catch:{ Throwable -> 0x0121 }
            byte[] r11 = (byte[]) r11     // Catch:{ Throwable -> 0x0121 }
            byte[] r11 = (byte[]) r11     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r4 = "getHttpSignature"
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch:{ Throwable -> 0x0121 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r3] = r7     // Catch:{ Throwable -> 0x0121 }
            java.lang.Class<byte[]> r7 = byte[].class
            r6[r1] = r7     // Catch:{ Throwable -> 0x0121 }
            java.lang.reflect.Method r4 = r9.m2453a((java.lang.String) r4, (java.lang.Class<?>[]) r6)     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0121 }
            r5[r3] = r2     // Catch:{ Throwable -> 0x0121 }
            r5[r1] = r11     // Catch:{ Throwable -> 0x0121 }
            java.lang.Object r2 = r4.invoke(r0, r5)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0121 }
            if (r2 == 0) goto L_0x00e1
            boolean r10 = r2.equals(r10)     // Catch:{ Throwable -> 0x0121 }
            if (r10 != 0) goto L_0x00db
            goto L_0x00e1
        L_0x00db:
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r3, r11)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x00e1:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0121 }
            r10.<init>()     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = r9.f1613l     // Catch:{ Throwable -> 0x0121 }
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r11 = "|signature = null or error"
            r10.append(r11)     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0121 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r10)     // Catch:{ Throwable -> 0x0121 }
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r1, r0)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x00fd:
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r1, r0)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x0103:
            com.igexin.push.extension.distribution.basic.f.f r10 = r9.f2458a     // Catch:{ Throwable -> 0x0121 }
            boolean r10 = r10.mo14981f()     // Catch:{ Throwable -> 0x0121 }
            if (r10 == 0) goto L_0x010f
            byte[] r11 = android.util.Base64.decode(r11, r3)     // Catch:{ Throwable -> 0x0121 }
        L_0x010f:
            com.igexin.push.extension.distribution.basic.f.f r10 = r9.f2458a     // Catch:{ Throwable -> 0x0121 }
            boolean r10 = r10.mo14980e()     // Catch:{ Throwable -> 0x0121 }
            if (r10 == 0) goto L_0x011b
            byte[] r11 = com.igexin.push.extension.distribution.basic.p068j.C1445m.m2536b(r11)     // Catch:{ Throwable -> 0x0121 }
        L_0x011b:
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b     // Catch:{ Throwable -> 0x0121 }
            r10.<init>(r9, r3, r11)     // Catch:{ Throwable -> 0x0121 }
            return r10
        L_0x0121:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r2 = "AsyncHttpTask|authAndDecResp|error|"
            r11.append(r2)
            java.lang.String r10 = r10.getMessage()
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r10)
            com.igexin.push.extension.distribution.basic.f.b r10 = new com.igexin.push.extension.distribution.basic.f.b
            r10.<init>(r9, r1, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p064f.C1422a.m2458b(java.net.HttpURLConnection, byte[]):com.igexin.push.extension.distribution.basic.f.b");
    }

    /* renamed from: b */
    private HttpURLConnection m2459b(String str) {
        this.f2459b = (HttpURLConnection) new URL(str).openConnection();
        this.f2459b.setConnectTimeout(UDPSocket.CLIENT_PORT);
        this.f2459b.setReadTimeout(UDPSocket.CLIENT_PORT);
        this.f2459b.setRequestMethod("GET");
        this.f2459b.setDoInput(true);
        if (this.f2460c) {
            m2454a(this.f2459b, (byte[]) null);
        }
        return this.f2459b;
    }

    /* renamed from: b */
    private HttpURLConnection m2460b(String str, byte[] bArr) {
        this.f2459b = (HttpURLConnection) new URL(str).openConnection();
        this.f2459b.setDoInput(true);
        this.f2459b.setDoOutput(true);
        this.f2459b.setRequestMethod("POST");
        this.f2459b.setUseCaches(false);
        this.f2459b.setInstanceFollowRedirects(true);
        this.f2459b.setRequestProperty("Content-Type", "application/octet-stream");
        this.f2459b.setConnectTimeout(UDPSocket.CLIENT_PORT);
        this.f2459b.setReadTimeout(UDPSocket.CLIENT_PORT);
        if (this.f2460c) {
            m2454a(this.f2459b, bArr);
        }
        return this.f2459b;
    }

    /* renamed from: b */
    private boolean m2461b(byte[] bArr) {
        if (bArr == null || bArr.length / 1024 <= C1416f.f2436n) {
            return false;
        }
        C1179b.m1354a(this.f1613l + "|http body size exceed " + C1416f.f2436n);
        return true;
    }

    /* renamed from: i */
    private void m2462i() {
        C1179b.m1354a("AsyncHttpTask call closeHttpURLConnection");
        HttpURLConnection httpURLConnection = this.f2459b;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
                this.f2459b = null;
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: j */
    private boolean m2463j() {
        return this.f2458a.f2474e && C1435c.m2503a();
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483639;
    }

    /* renamed from: b_ */
    public final void mo14232b_() {
        String c;
        super.mo14232b_();
        if (this.f2461d) {
            mo14305p();
            return;
        }
        this.f2461d = true;
        Process.setThreadPriority(10);
        if (this.f2458a != null && (c = this.f2458a.mo14978c()) != null) {
            this.f2460c = m2463j();
            if (!this.f2460c || ((Boolean) m2453a("isLoadSuccess", (Class<?>[]) new Class[0]).invoke((Object) null, new Object[0])).booleanValue()) {
                if (this.f2460c && this.f2458a.mo14979d() != null && this.f2458a.mo14979d().length > 0) {
                    this.f2458a.mo14977b(C1177f.m1332a(this.f2458a.mo14979d()));
                }
                try {
                    C1423b a = this.f2458a.mo14979d() == null ? m2451a(c) : m2452a(c, this.f2458a.mo14979d());
                    if (a.f2462a) {
                        Exception exc = new Exception("http server resp decode header error");
                        this.f2458a.mo14974a(exc);
                        throw exc;
                    } else if (a.f2463b != null) {
                        m2455a(a.f2463b);
                    } else {
                        Exception exc2 = new Exception("Http response exception");
                        this.f2458a.mo14974a(exc2);
                        throw exc2;
                    }
                } catch (Exception e) {
                    C1179b.m1354a("AsyncHttpTask|run() post or get error = " + e.getMessage());
                    this.f2458a.mo14974a(e);
                    throw e;
                } catch (Exception e2) {
                    C1179b.m1354a("AsyncHttpTask|run() error = " + e2.getMessage());
                    throw e2;
                }
            } else {
                C1179b.m1354a(this.f1613l + "|so load failed! AsyncHttpTask return!");
            }
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        this.f1648n = true;
        C1179b.m1354a("AsyncHttpTask initTask()|isBloker = " + this.f1648n + "|isCycle = " + this.f1649o);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }

    /* renamed from: f */
    public void mo14233f() {
        C1179b.m1354a("AsyncHttpTask|dispose()|closeHttpURLConnection");
        super.mo14233f();
        m2462i();
    }
}
