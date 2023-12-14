package com.igexin.push.extension.distribution.basic.p058a.p059a;

import java.net.ServerSocket;

/* renamed from: com.igexin.push.extension.distribution.basic.a.a.c */
public class C1399c {

    /* renamed from: a */
    private static final String f2350a = ("EXT-" + C1399c.class.getName());

    /* renamed from: d */
    private static C1399c f2351d;

    /* renamed from: b */
    private Long f2352b;

    /* renamed from: c */
    private ServerSocket f2353c;

    private C1399c() {
    }

    /* renamed from: a */
    public static C1399c m2281a() {
        if (f2351d == null) {
            f2351d = new C1399c();
        }
        return f2351d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        com.igexin.p032b.p033a.p039c.C1179b.m1354a(f2350a + "|port 51688 has occupy by others");
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0021 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14852a(boolean r8) {
        /*
            r7 = this;
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0174 }
            boolean r0 = com.igexin.push.extension.distribution.basic.p062d.C1419c.m2437a(r0)     // Catch:{ Throwable -> 0x0174 }
            if (r0 == 0) goto L_0x0167
            if (r8 == 0) goto L_0x0167
            boolean r0 = com.igexin.push.core.C1343f.f2172i     // Catch:{ Throwable -> 0x0174 }
            if (r0 == 0) goto L_0x0167
            boolean r0 = com.igexin.push.core.C1343f.f2173j     // Catch:{ Throwable -> 0x0174 }
            if (r0 == 0) goto L_0x0167
            java.net.ServerSocket r0 = r7.f2353c     // Catch:{ Exception -> 0x0021 }
            if (r0 != 0) goto L_0x0037
            java.net.ServerSocket r0 = new java.net.ServerSocket     // Catch:{ Exception -> 0x0021 }
            r1 = 51688(0xc9e8, float:7.243E-41)
            r0.<init>(r1)     // Catch:{ Exception -> 0x0021 }
            r7.f2353c = r0     // Catch:{ Exception -> 0x0021 }
            goto L_0x0037
        L_0x0021:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r0.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = f2350a     // Catch:{ Throwable -> 0x0174 }
            r0.append(r1)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "|port 51688 has occupy by others"
            r0.append(r1)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0174 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Throwable -> 0x0174 }
        L_0x0037:
            java.net.ServerSocket r0 = r7.f2353c     // Catch:{ Throwable -> 0x0174 }
            if (r0 == 0) goto L_0x0167
            long r0 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2433k     // Catch:{ Throwable -> 0x0174 }
            r2 = 180000(0x2bf20, double:8.8932E-319)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0046
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2433k = r2     // Catch:{ Throwable -> 0x0174 }
        L_0x0046:
            long r0 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2432j     // Catch:{ Throwable -> 0x0174 }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x004e
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2432j = r2     // Catch:{ Throwable -> 0x0174 }
        L_0x004e:
            java.lang.Long r0 = r7.f2352b     // Catch:{ Throwable -> 0x0174 }
            if (r0 != 0) goto L_0x0080
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0174 }
            long r2 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2434l     // Catch:{ Throwable -> 0x0174 }
            long r0 = r0 - r2
            long r2 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2433k     // Catch:{ Throwable -> 0x0174 }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0076
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r8.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = f2350a     // Catch:{ Throwable -> 0x0174 }
            r8.append(r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = "|lastReportInterval < reportCidRestartThreshold not report"
            r8.append(r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0174 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r8)     // Catch:{ Throwable -> 0x0174 }
            return
        L_0x0076:
            long r2 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2432j     // Catch:{ Throwable -> 0x0174 }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x007e
            r0 = 2
            goto L_0x0092
        L_0x007e:
            r0 = 0
            goto L_0x0092
        L_0x0080:
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0174 }
            java.lang.Long r2 = r7.f2352b     // Catch:{ Throwable -> 0x0174 }
            long r2 = r2.longValue()     // Catch:{ Throwable -> 0x0174 }
            long r0 = r0 - r2
            long r2 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2432j     // Catch:{ Throwable -> 0x0174 }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x0150
            r0 = 1
        L_0x0092:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r1.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x0174 }
            r1.append(r2)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r2 = "/libs"
            r1.append(r2)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0174 }
            java.util.List r1 = com.igexin.push.extension.distribution.basic.p068j.C1440h.m2523a((java.lang.String) r1)     // Catch:{ Throwable -> 0x0174 }
            if (r1 != 0) goto L_0x00b3
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ Throwable -> 0x0174 }
            r1.<init>()     // Catch:{ Throwable -> 0x0174 }
            goto L_0x00bf
        L_0x00b3:
            int r2 = r1.size()     // Catch:{ Throwable -> 0x0174 }
            if (r2 <= 0) goto L_0x014f
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Throwable -> 0x0174 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0174 }
            r1 = r2
        L_0x00bf:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0174 }
            r2.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r3 = "appinfo"
            r2.put(r3, r1)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "deviceid"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r3.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = "ANDROID-"
            r3.append(r4)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2187x     // Catch:{ Throwable -> 0x0174 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0174 }
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "type"
            r2.put(r1, r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = "pkg"
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Throwable -> 0x0174 }
            r2.put(r0, r1)     // Catch:{ Throwable -> 0x0174 }
            r2.toString()     // Catch:{ Throwable -> 0x0174 }
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ Throwable -> 0x0174 }
            r0.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "action"
            java.lang.String r3 = "sendMessage"
            r0.putString(r1, r3)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "taskid"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r3.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = "6T5@S_"
            r3.append(r4)     // Catch:{ Throwable -> 0x0174 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r4.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r5 = com.igexin.push.core.C1343f.f2182s     // Catch:{ Throwable -> 0x0174 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0174 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0174 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r4)     // Catch:{ Throwable -> 0x0174 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0174 }
            r0.putString(r1, r3)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r1 = "extraData"
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0174 }
            byte[] r2 = r2.getBytes()     // Catch:{ Throwable -> 0x0174 }
            r0.putByteArray(r1, r2)     // Catch:{ Throwable -> 0x0174 }
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Throwable -> 0x0174 }
            r1.mo14474a((android.os.Bundle) r0)     // Catch:{ Throwable -> 0x0174 }
            com.igexin.push.extension.distribution.basic.d.b r0 = com.igexin.push.extension.distribution.basic.p062d.C1418b.m2428a()     // Catch:{ Throwable -> 0x0174 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0174 }
            r0.mo14956b(r1)     // Catch:{ Throwable -> 0x0174 }
            goto L_0x0167
        L_0x014f:
            return
        L_0x0150:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r8.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = f2350a     // Catch:{ Throwable -> 0x0174 }
            r8.append(r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = "|offline time < reportCidOfflineThreshold not report"
            r8.append(r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0174 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r8)     // Catch:{ Throwable -> 0x0174 }
            return
        L_0x0167:
            if (r8 != 0) goto L_0x0192
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0174 }
            java.lang.Long r8 = java.lang.Long.valueOf(r0)     // Catch:{ Throwable -> 0x0174 }
            r7.f2352b = r8     // Catch:{ Throwable -> 0x0174 }
            goto L_0x0192
        L_0x0174:
            r8 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = f2350a
            r0.append(r1)
            java.lang.String r1 = "|do report exception:"
            r0.append(r1)
            java.lang.String r8 = r8.toString()
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r8)
        L_0x0192:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.p059a.C1399c.mo14852a(boolean):void");
    }
}
