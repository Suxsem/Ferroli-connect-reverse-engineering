package com.igexin.push.p088g;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p040d.C1190e;
import java.util.ArrayList;
import kotlin.jvm.internal.LongCompanionObject;

/* renamed from: com.igexin.push.g.a */
public class C1561a extends C1190e {

    /* renamed from: d */
    private static C1561a f2974d;

    /* renamed from: a */
    private ArrayList<String> f2975a = new ArrayList<>();

    /* renamed from: b */
    private ArrayList<String> f2976b = new ArrayList<>();

    /* renamed from: c */
    private long f2977c = System.currentTimeMillis();

    private C1561a() {
        super(-2147483637);
    }

    /* renamed from: i */
    public static C1561a m3115i() {
        if (f2974d == null) {
            synchronized (C1561a.class) {
                if (f2974d == null) {
                    f2974d = new C1561a();
                    C1174c.m1310b().mo14317a(f2974d, true, true);
                }
            }
        }
        return f2974d;
    }

    /* renamed from: k */
    public static void m3116k() {
        C1561a aVar = f2974d;
        if (aVar != null) {
            aVar.mo15201j();
        }
    }

    /* renamed from: v */
    private long m3117v() {
        long currentTimeMillis = System.currentTimeMillis() - this.f2977c;
        if ((currentTimeMillis < 60000 || this.f2975a.size() <= 0) && this.f2975a.size() < 10) {
            return this.f2975a.size() <= 0 ? LongCompanionObject.MAX_VALUE : 60000 - currentTimeMillis;
        }
        return 0;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:30|31) */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r0 = new java.io.FileOutputStream(r4, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00fb, code lost:
        if (r3 != null) goto L_0x00fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0109, code lost:
        if (r3 == null) goto L_0x010c;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x00b3 */
    /* renamed from: w */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3118w() {
        /*
            r6 = this;
            java.lang.String r0 = com.igexin.push.core.C1343f.f2110B
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            monitor-enter(r6)
            java.util.ArrayList<java.lang.String> r0 = r6.f2976b     // Catch:{ all -> 0x0118 }
            java.util.ArrayList<java.lang.String> r1 = r6.f2975a     // Catch:{ all -> 0x0118 }
            r0.addAll(r1)     // Catch:{ all -> 0x0118 }
            java.util.ArrayList<java.lang.String> r0 = r6.f2975a     // Catch:{ all -> 0x0118 }
            r0.clear()     // Catch:{ all -> 0x0118 }
            monitor-exit(r6)     // Catch:{ all -> 0x0118 }
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r2 = "yyyy-MM-dd"
            r0.<init>(r2, r1)
            java.util.Date r1 = new java.util.Date
            r1.<init>()
            java.lang.String r0 = r0.format(r1)
            java.lang.String r1 = com.igexin.push.core.C1343f.f2168e
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x0030
            return
        L_0x0030:
            java.lang.String r2 = android.os.Environment.getExternalStorageState()
            java.lang.String r3 = "mounted"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x010c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r4 = "gtpush"
            java.io.File r3 = r3.getExternalFilesDir(r4)
            r2.append(r3)
            java.lang.String r3 = "/log/"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.io.File r3 = new java.io.File
            r3.<init>(r2)
            boolean r4 = r3.exists()
            if (r4 != 0) goto L_0x0067
            boolean r3 = r3.mkdir()
            if (r3 != 0) goto L_0x0067
            return
        L_0x0067:
            r3 = 0
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r5.<init>()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r5.append(r2)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r5.append(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r1 = "."
            r5.append(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r5.append(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r0 = ".log"
            r5.append(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            boolean r0 = r4.exists()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            if (r0 != 0) goto L_0x0099
            boolean r0 = r4.createNewFile()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            if (r0 != 0) goto L_0x0096
            return
        L_0x0096:
            com.igexin.p032b.p033a.p039c.C1178a.m1348a()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
        L_0x0099:
            byte[] r0 = com.igexin.p032b.p033a.p039c.C1178a.m1352b()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            if (r0 != 0) goto L_0x00a5
            com.igexin.p032b.p033a.p039c.C1178a.m1348a()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r4.delete()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
        L_0x00a5:
            javax.crypto.spec.SecretKeySpec r0 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Throwable -> 0x00b3 }
            byte[] r1 = com.igexin.push.core.C1343f.f2137aB     // Catch:{ Throwable -> 0x00b3 }
            java.lang.String r2 = "AES"
            r0.<init>(r1, r2)     // Catch:{ Throwable -> 0x00b3 }
            javax.crypto.CipherOutputStream r0 = com.igexin.p032b.p033a.p039c.C1178a.m1347a(r4, r0)     // Catch:{ Throwable -> 0x00b3 }
            goto L_0x00b9
        L_0x00b3:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r1 = 1
            r0.<init>(r4, r1)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
        L_0x00b9:
            r3 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r1 = ""
            r0.<init>(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.util.ArrayList<java.lang.String> r1 = r6.f2976b     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
        L_0x00c7:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            if (r2 == 0) goto L_0x00e8
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r4.<init>()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r4.append(r2)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r2 = "\r\n"
            r4.append(r2)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r0.append(r2)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            goto L_0x00c7
        L_0x00e8:
            int r1 = r0.length()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            if (r1 <= 0) goto L_0x00fb
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            java.lang.String r1 = "UTF-8"
            byte[] r0 = r0.getBytes(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
            r3.write(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0101 }
        L_0x00fb:
            if (r3 == 0) goto L_0x010c
        L_0x00fd:
            r3.close()     // Catch:{ IOException -> 0x010c }
            goto L_0x010c
        L_0x0101:
            r0 = move-exception
            if (r3 == 0) goto L_0x0107
            r3.close()     // Catch:{ IOException -> 0x0107 }
        L_0x0107:
            throw r0
        L_0x0108:
            if (r3 == 0) goto L_0x010c
            goto L_0x00fd
        L_0x010c:
            long r0 = java.lang.System.currentTimeMillis()
            r6.f2977c = r0
            java.util.ArrayList<java.lang.String> r0 = r6.f2976b
            r0.clear()
            return
        L_0x0118:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0118 }
            goto L_0x011c
        L_0x011b:
            throw r0
        L_0x011c:
            goto L_0x011b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p088g.C1561a.m3118w():void");
    }

    /* renamed from: a */
    public synchronized void mo15200a(String str) {
        this.f2975a.add(str);
        try {
            if (this.f2975a.size() <= 1 || this.f2975a.size() >= 10) {
                notify();
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return -2147483637;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        while (true) {
            try {
                synchronized (this) {
                    while (true) {
                        long v = m3117v();
                        if (v == 0) {
                            break;
                        }
                        wait(v);
                    }
                    while (true) {
                    }
                }
                m3118w();
            } catch (Throwable th) {
                m3118w();
                throw th;
            }
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        super.mo14221d();
        this.f1648n = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: g_ */
    public void mo14302g_() {
        super.mo14302g_();
        f2974d = null;
    }

    /* renamed from: j */
    public void mo15201j() {
        if (mo14308s() != null) {
            mo14308s().interrupt();
        }
    }
}
