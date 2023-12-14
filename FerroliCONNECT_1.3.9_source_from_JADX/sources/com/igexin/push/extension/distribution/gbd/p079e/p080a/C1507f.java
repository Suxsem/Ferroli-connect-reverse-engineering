package com.igexin.push.extension.distribution.gbd.p079e.p080a;

import android.content.ContentValues;
import android.text.TextUtils;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import java.net.ServerSocket;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a.f */
public class C1507f {

    /* renamed from: a */
    private static C1507f f2899a;

    /* renamed from: a */
    public static synchronized C1507f m2840a() {
        C1507f fVar;
        synchronized (C1507f.class) {
            if (f2899a == null) {
                f2899a = new C1507f();
            }
            fVar = f2899a;
        }
        return fVar;
    }

    /* renamed from: a */
    private void m2841a(int i, String str) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", Integer.valueOf(i));
            contentValues.put("value", str);
            C1490c.f2748b.mo15099a("runtime", (String) null, contentValues);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    private void m2842a(int i, byte[] bArr) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", Integer.valueOf(i));
            contentValues.put("value", bArr);
            C1490c.f2748b.mo15099a("runtime", (String) null, contentValues);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: f */
    private ServerSocket m2843f() {
        try {
            return new ServerSocket(48432);
        } catch (Throwable unused) {
            C1540h.m2995a("GBD_RDM", "open port error ！");
            return null;
        }
    }

    /* renamed from: a */
    public void mo15115a(long j) {
        C1490c.f2754h = j;
        m2841a(102, String.valueOf(j));
        C1540h.m2995a("GBD_RDM", "saveTimeOffset = " + j);
    }

    /* renamed from: a */
    public void mo15116a(String str) {
        m2842a(165, C1196a.m1438b(str.getBytes()));
    }

    /* renamed from: a */
    public void mo15117a(List<Long> list) {
        try {
            C1490c.f2755i.clear();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                long longValue = list.get(i).longValue();
                C1490c.f2755i.add(Long.valueOf(longValue));
                sb.append(longValue);
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            m2842a(103, C1196a.m1438b(sb.toString().getBytes()));
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01e2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01e3, code lost:
        r2 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01e6, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01e7, code lost:
        r5 = r6;
        r9 = r8;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01e2 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:5:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0225  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0247  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo15118b() {
        /*
            r16 = this;
            java.lang.String r1 = "GBD_RDM"
            r2 = 0
            r4 = 0
            r5 = 0
            java.lang.String r0 = "select key, value from runtime order by key"
            com.igexin.push.extension.distribution.gbd.e.a r6 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ Exception -> 0x01f5 }
            android.database.Cursor r6 = r6.mo15096a((java.lang.String) r0, (java.lang.String[]) r5)     // Catch:{ Exception -> 0x01f5 }
            if (r6 == 0) goto L_0x01ea
            r7 = r5
            r8 = 0
        L_0x0012:
            boolean r0 = r6.moveToNext()     // Catch:{ Exception -> 0x01e6, all -> 0x01e2 }
            if (r0 == 0) goto L_0x01ea
            int r9 = r6.getInt(r4)     // Catch:{ Exception -> 0x01e6, all -> 0x01e2 }
            r0 = 103(0x67, float:1.44E-43)
            r10 = 178(0xb2, float:2.5E-43)
            r11 = 157(0x9d, float:2.2E-43)
            r12 = 176(0xb0, float:2.47E-43)
            r13 = 151(0x97, float:2.12E-43)
            r14 = 131(0x83, float:1.84E-43)
            r15 = 130(0x82, float:1.82E-43)
            r4 = 1
            if (r9 == r0) goto L_0x0057
            if (r9 == r15) goto L_0x0057
            if (r9 == r14) goto L_0x0057
            r0 = 139(0x8b, float:1.95E-43)
            if (r9 == r0) goto L_0x0057
            if (r9 == r13) goto L_0x0057
            if (r9 == r11) goto L_0x0057
            r0 = 163(0xa3, float:2.28E-43)
            if (r9 == r0) goto L_0x0057
            r0 = 165(0xa5, float:2.31E-43)
            if (r9 == r0) goto L_0x0057
            r0 = 168(0xa8, float:2.35E-43)
            if (r9 == r0) goto L_0x0057
            r0 = 170(0xaa, float:2.38E-43)
            if (r9 == r0) goto L_0x0057
            if (r9 == r12) goto L_0x0057
            if (r9 == r10) goto L_0x0057
            r0 = 180(0xb4, float:2.52E-43)
            if (r9 != r0) goto L_0x0052
            goto L_0x0057
        L_0x0052:
            java.lang.String r0 = r6.getString(r4)     // Catch:{ Throwable -> 0x01db }
            goto L_0x006d
        L_0x0057:
            byte[] r4 = r6.getBlob(r4)     // Catch:{ Throwable -> 0x01db }
            if (r4 == 0) goto L_0x0067
            byte[] r0 = com.igexin.p032b.p042b.C1196a.m1439c(r4)     // Catch:{ Throwable -> 0x0063 }
            r7 = r0
            goto L_0x0068
        L_0x0063:
            r0 = move-exception
            r7 = r4
            goto L_0x01dc
        L_0x0067:
            r7 = r4
        L_0x0068:
            if (r7 != 0) goto L_0x006c
            goto L_0x01df
        L_0x006c:
            r0 = r5
        L_0x006d:
            r4 = 125(0x7d, float:1.75E-43)
            if (r9 == r4) goto L_0x01c7
            r4 = 126(0x7e, float:1.77E-43)
            if (r9 == r4) goto L_0x01c0
            if (r9 == r15) goto L_0x01b8
            if (r9 == r14) goto L_0x01b0
            r4 = 145(0x91, float:2.03E-43)
            if (r9 == r4) goto L_0x01a9
            if (r9 == r13) goto L_0x01a1
            r4 = 154(0x9a, float:2.16E-43)
            if (r9 == r4) goto L_0x019a
            if (r9 == r12) goto L_0x0192
            r4 = 181(0xb5, float:2.54E-43)
            if (r9 == r4) goto L_0x018b
            if (r9 == r11) goto L_0x0183
            r4 = 158(0x9e, float:2.21E-43)
            if (r9 == r4) goto L_0x017c
            if (r9 == r10) goto L_0x0174
            r4 = 179(0xb3, float:2.51E-43)
            if (r9 == r4) goto L_0x0157
            switch(r9) {
                case 102: goto L_0x0135;
                case 103: goto L_0x00fb;
                case 104: goto L_0x00f3;
                default: goto L_0x0098;
            }
        L_0x0098:
            switch(r9) {
                case 138: goto L_0x00eb;
                case 139: goto L_0x00e2;
                case 140: goto L_0x00da;
                case 141: goto L_0x00d2;
                case 142: goto L_0x00ca;
                default: goto L_0x009b;
            }
        L_0x009b:
            switch(r9) {
                case 163: goto L_0x00c1;
                case 164: goto L_0x00b9;
                case 165: goto L_0x00b0;
                case 166: goto L_0x00a8;
                case 167: goto L_0x00a0;
                default: goto L_0x009e;
            }
        L_0x009e:
            goto L_0x01d9
        L_0x00a0:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2730G = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00a8:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2729F = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00b0:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2728E = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00b9:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2727D = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00c1:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2733J = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00ca:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2772z = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00d2:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2771y = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00da:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2769w = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00e2:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2770x = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00eb:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2768v = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00f3:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2756j = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x00fb:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r4.<init>()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r8 = "read recentWifi = "
            r4.append(r8)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r4.append(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r1, r4)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r4 = ","
            java.lang.String[] r0 = r0.split(r4)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.util.List<java.lang.Long> r4 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2755i     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r4.clear()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r4 = 0
        L_0x0120:
            int r8 = r0.length     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            if (r4 >= r8) goto L_0x01d9
            java.util.List<java.lang.Long> r8 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2755i     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r10 = r0[r4]     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            long r10 = java.lang.Long.parseLong(r10)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r8.add(r10)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            int r4 = r4 + 1
            goto L_0x0120
        L_0x0135:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2754h = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r4 = "read timeOffset = "
            r0.append(r4)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            long r10 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2754h     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.append(r10)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r1, r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x0153:
            r0 = move-exception
            r5 = r6
            goto L_0x01f7
        L_0x0157:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2767u = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r4 = "read MFLastTime = "
            r0.append(r4)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            long r10 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2767u     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.append(r10)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r1, r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x0174:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2766t = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x017c:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2725B = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x0183:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2726C = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x018b:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2745V = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x0192:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2737N = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x019a:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2765s = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01a1:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2764r = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01a9:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2724A = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01b0:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2763q = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01b8:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2762p = r0     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01c0:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2757k = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            goto L_0x01d9
        L_0x01c7:
            long r10 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2761o = r10     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            long r10 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2761o     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
            int r0 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r0 <= 0) goto L_0x01d9
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2761o = r2     // Catch:{ Exception -> 0x0153, all -> 0x01e2 }
        L_0x01d9:
            r8 = r9
            goto L_0x01df
        L_0x01db:
            r0 = move-exception
        L_0x01dc:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ Exception -> 0x01e6, all -> 0x01e2 }
        L_0x01df:
            r4 = 0
            goto L_0x0012
        L_0x01e2:
            r0 = move-exception
            r2 = r16
            goto L_0x0245
        L_0x01e6:
            r0 = move-exception
            r5 = r6
            r9 = r8
            goto L_0x01f7
        L_0x01ea:
            if (r6 == 0) goto L_0x021f
            r6.close()
            goto L_0x021f
        L_0x01f0:
            r0 = move-exception
            r2 = r16
            r6 = r5
            goto L_0x0245
        L_0x01f5:
            r0 = move-exception
            r9 = 0
        L_0x01f7:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x01f0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01f0 }
            r4.<init>()     // Catch:{ all -> 0x01f0 }
            java.lang.String r6 = "read DB exception = "
            r4.append(r6)     // Catch:{ all -> 0x01f0 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x01f0 }
            r4.append(r0)     // Catch:{ all -> 0x01f0 }
            java.lang.String r0 = " "
            r4.append(r0)     // Catch:{ all -> 0x01f0 }
            r4.append(r9)     // Catch:{ all -> 0x01f0 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x01f0 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r1, r0)     // Catch:{ all -> 0x01f0 }
            if (r5 == 0) goto L_0x021f
            r5.close()
        L_0x021f:
            long r0 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2754h
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0242
            double r0 = java.lang.Math.random()
            r2 = 4627448617123184640(0x4038000000000000, double:24.0)
            double r0 = r0 * r2
            r2 = 4660134898793709568(0x40ac200000000000, double:3600.0)
            double r0 = r0 * r2
            r2 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r0 = r0 * r2
            long r0 = (long) r0
            r2 = r16
            r2.mo15115a((long) r0)
            goto L_0x0244
        L_0x0242:
            r2 = r16
        L_0x0244:
            return
        L_0x0245:
            if (r6 == 0) goto L_0x024a
            r6.close()
        L_0x024a:
            goto L_0x024c
        L_0x024b:
            throw r0
        L_0x024c:
            goto L_0x024b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f.mo15118b():void");
    }

    /* renamed from: b */
    public void mo15119b(long j) {
        C1490c.f2769w = j;
        m2841a(140, String.valueOf(C1490c.f2769w));
    }

    /* renamed from: b */
    public void mo15120b(String str) {
        if (!TextUtils.isEmpty(str)) {
            C1490c.f2764r = str;
            m2842a(151, C1196a.m1438b(str.getBytes()));
        }
    }

    /* renamed from: c */
    public void mo15121c() {
        try {
            m2842a(130, C1196a.m1438b(C1490c.f2762p.getBytes()));
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: c */
    public void mo15122c(long j) {
        C1490c.f2771y = j;
        m2841a(141, String.valueOf(C1490c.f2771y));
    }

    /* renamed from: c */
    public void mo15123c(String str) {
        if (!TextUtils.isEmpty(str)) {
            C1490c.f2733J = str;
            m2842a(163, C1196a.m1438b(str.getBytes()));
        }
    }

    /* renamed from: d */
    public void mo15124d() {
        try {
            m2842a(131, C1196a.m1438b(C1490c.f2763q.getBytes()));
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: d */
    public void mo15125d(long j) {
        C1490c.f2772z = j;
        m2841a(142, String.valueOf(C1490c.f2772z));
    }

    /* renamed from: e */
    public void mo15126e(long j) {
        C1490c.f2724A = j;
        m2841a(145, String.valueOf(C1490c.f2724A));
    }

    /* renamed from: e */
    public boolean mo15127e() {
        if (C1490c.f2751e == null || C1490c.f2751e.isClosed()) {
            C1490c.f2751e = m2843f();
            if (C1490c.f2751e == null) {
                return false;
            }
        }
        C1540h.m2995a("GBD_RDM", "open port success !");
        return true;
    }

    /* renamed from: f */
    public void mo15128f(long j) {
        C1490c.f2727D = j;
        m2841a(164, String.valueOf(j));
    }

    /* renamed from: g */
    public void mo15129g(long j) {
        m2841a(166, String.valueOf(j));
    }

    /* renamed from: h */
    public void mo15130h(long j) {
        m2841a(167, String.valueOf(j));
    }

    /* renamed from: i */
    public void mo15131i(long j) {
        C1490c.f2756j = j;
        m2841a(104, String.valueOf(j));
    }

    /* renamed from: j */
    public void mo15132j(long j) {
        C1490c.f2757k = j;
        m2841a(126, String.valueOf(j));
    }

    /* renamed from: k */
    public void mo15133k(long j) {
        C1490c.f2761o = j;
        m2841a(125, Long.toString(j));
    }

    /* renamed from: l */
    public void mo15134l(long j) {
        C1490c.f2765s = j;
        m2841a(154, String.valueOf(C1490c.f2765s));
    }
}
