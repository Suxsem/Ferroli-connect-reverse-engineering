package com.igexin.push.extension.distribution.basic.p062d;

import android.content.ContentValues;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p063e.C1420a;

/* renamed from: com.igexin.push.extension.distribution.basic.d.b */
public class C1418b {

    /* renamed from: a */
    public static C1418b f2444a;

    /* renamed from: b */
    private static final String f2445b = ("EXT-" + C1418b.class.getName());

    /* renamed from: c */
    private static int f2446c = 0;

    /* renamed from: d */
    private static String f2447d = C1416f.f2435m;

    /* renamed from: e */
    private static boolean f2448e = C1416f.f2431i;

    /* renamed from: f */
    private static long f2449f = C1416f.f2432j;

    /* renamed from: g */
    private static long f2450g = C1416f.f2433k;

    /* renamed from: h */
    private static int f2451h = C1416f.f2436n;

    /* renamed from: i */
    private static String f2452i = C1416f.f2437o;

    /* renamed from: j */
    private static String f2453j = C1416f.f2438p;

    /* renamed from: k */
    private static C1420a f2454k = null;

    /* renamed from: a */
    public static C1418b m2428a() {
        if (f2444a == null) {
            f2444a = new C1418b();
            f2454k = new C1420a(C1416f.f2423a);
        }
        return f2444a;
    }

    /* renamed from: a */
    public void mo14951a(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", Integer.valueOf(i));
        contentValues.put("value", str);
        f2454k.mo14962a("extconfig", (String) null, contentValues);
    }

    /* renamed from: a */
    public void mo14952a(int i, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", Integer.valueOf(i));
        contentValues.put("value", bArr);
        f2454k.mo14962a("extconfig", (String) null, contentValues);
    }

    /* renamed from: a */
    public void mo14953a(long j) {
        C1416f.f2429g = j;
        mo14951a(3, String.valueOf(j));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(23:9|(1:11)|12|(1:14)|15|(2:17|(1:21))|22|(3:24|25|26)|27|28|(3:30|31|32)|33|34|(1:36)|37|(3:39|40|41)|42|43|(1:45)|46|(1:48)|49|55) */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0132, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0133, code lost:
        com.igexin.p032b.p033a.p039c.C1179b.m1354a(f2445b + "|parseSdkConfig error|" + r14.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x00d7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00ee */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x010e */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00dd A[Catch:{ Exception -> 0x0132 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00f4 A[Catch:{ Exception -> 0x0132 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0100 A[Catch:{ Exception -> 0x0132 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0114 A[Catch:{ Exception -> 0x0132 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0128 A[Catch:{ Exception -> 0x0132 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14954a(byte[] r14) {
        /*
            r13 = this;
            java.lang.String r0 = "sdk.ext.guardgactivity.pmblacklist"
            java.lang.String r1 = "sdk.ext.guardgactivity.blacklist"
            java.lang.String r2 = "sdk.ext.httpdata.maxsize"
            java.lang.String r3 = "sdk.ext.foreground.phonelist"
            java.lang.String r4 = "sdk.ext.reportcid.restart.interval"
            java.lang.String r5 = "sdk.ext.reportcid.offline.interval"
            java.lang.String r6 = "sdk.ext.guardactivity.first"
            java.lang.String r7 = "sdk.ext.deviceid.sync.interval"
            java.lang.String r8 = "sdk.ext.startservice.limit"
            java.lang.String r9 = "config"
            java.lang.String r10 = "result"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0132 }
            r11.<init>()     // Catch:{ Exception -> 0x0132 }
            java.lang.String r12 = f2445b     // Catch:{ Exception -> 0x0132 }
            r11.append(r12)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r12 = "|parseSdkConfig resp data len = "
            r11.append(r12)     // Catch:{ Exception -> 0x0132 }
            int r12 = r14.length     // Catch:{ Exception -> 0x0132 }
            r11.append(r12)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0132 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ Exception -> 0x0132 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0132 }
            r13.mo14953a((long) r11)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r11 = new java.lang.String     // Catch:{ Exception -> 0x0132 }
            r11.<init>(r14)     // Catch:{ Exception -> 0x0132 }
            org.json.JSONObject r14 = new org.json.JSONObject     // Catch:{ Exception -> 0x0132 }
            r14.<init>(r11)     // Catch:{ Exception -> 0x0132 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0132 }
            r11.<init>()     // Catch:{ Exception -> 0x0132 }
            java.lang.String r12 = f2445b     // Catch:{ Exception -> 0x0132 }
            r11.append(r12)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r12 = "|parseSdkConfig : "
            r11.append(r12)     // Catch:{ Exception -> 0x0132 }
            r11.append(r14)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0132 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ Exception -> 0x0132 }
            boolean r11 = r14.has(r10)     // Catch:{ Exception -> 0x0132 }
            if (r11 == 0) goto L_0x0150
            java.lang.String r10 = r14.getString(r10)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r11 = "ok"
            boolean r10 = r11.equals(r10)     // Catch:{ Exception -> 0x0132 }
            if (r10 != 0) goto L_0x006d
            return
        L_0x006d:
            int r10 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2427e     // Catch:{ Exception -> 0x0132 }
            f2446c = r10     // Catch:{ Exception -> 0x0132 }
            boolean r10 = r14.has(r9)     // Catch:{ Exception -> 0x0132 }
            if (r10 == 0) goto L_0x0150
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Exception -> 0x0132 }
            java.lang.String r14 = r14.getString(r9)     // Catch:{ Exception -> 0x0132 }
            r10.<init>(r14)     // Catch:{ Exception -> 0x0132 }
            boolean r14 = r10.has(r8)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x008c
            java.lang.String r14 = r10.getString(r8)     // Catch:{ Exception -> 0x0132 }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2430h = r14     // Catch:{ Exception -> 0x0132 }
        L_0x008c:
            boolean r14 = r10.has(r7)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x009c
            java.lang.String r14 = r10.getString(r7)     // Catch:{ Exception -> 0x0132 }
            int r14 = java.lang.Integer.parseInt(r14)     // Catch:{ Exception -> 0x0132 }
            f2446c = r14     // Catch:{ Exception -> 0x0132 }
        L_0x009c:
            boolean r14 = r10.has(r6)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x00c0
            java.lang.String r14 = r10.getString(r6)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r6 = "true"
            boolean r6 = r14.equals(r6)     // Catch:{ Exception -> 0x0132 }
            if (r6 != 0) goto L_0x00b6
            java.lang.String r6 = "false"
            boolean r6 = r14.equals(r6)     // Catch:{ Exception -> 0x0132 }
            if (r6 == 0) goto L_0x00c0
        L_0x00b6:
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r14)     // Catch:{ Exception -> 0x0132 }
            boolean r14 = r14.booleanValue()     // Catch:{ Exception -> 0x0132 }
            f2448e = r14     // Catch:{ Exception -> 0x0132 }
        L_0x00c0:
            boolean r14 = r10.has(r5)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x00d7
            java.lang.String r14 = r10.getString(r5)     // Catch:{ Exception -> 0x0132 }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x00d7 }
            int r14 = r14.intValue()     // Catch:{ Exception -> 0x00d7 }
            int r14 = r14 * 1000
            long r5 = (long) r14     // Catch:{ Exception -> 0x00d7 }
            f2449f = r5     // Catch:{ Exception -> 0x00d7 }
        L_0x00d7:
            boolean r14 = r10.has(r4)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x00ee
            java.lang.String r14 = r10.getString(r4)     // Catch:{ Exception -> 0x0132 }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x00ee }
            int r14 = r14.intValue()     // Catch:{ Exception -> 0x00ee }
            int r14 = r14 * 1000
            long r4 = (long) r14     // Catch:{ Exception -> 0x00ee }
            f2450g = r4     // Catch:{ Exception -> 0x00ee }
        L_0x00ee:
            boolean r14 = r10.has(r3)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x00fa
            java.lang.String r14 = r10.getString(r3)     // Catch:{ Exception -> 0x0132 }
            f2447d = r14     // Catch:{ Exception -> 0x0132 }
        L_0x00fa:
            boolean r14 = r10.has(r2)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x010e
            java.lang.String r14 = r10.getString(r2)     // Catch:{ Exception -> 0x0132 }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x010e }
            int r14 = r14.intValue()     // Catch:{ Exception -> 0x010e }
            f2451h = r14     // Catch:{ Exception -> 0x010e }
        L_0x010e:
            boolean r14 = r10.has(r1)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x0122
            java.lang.String r14 = r10.getString(r1)     // Catch:{ Exception -> 0x0132 }
            java.lang.String r1 = " "
            java.lang.String r2 = ""
            java.lang.String r14 = r14.replace(r1, r2)     // Catch:{ Exception -> 0x0132 }
            f2452i = r14     // Catch:{ Exception -> 0x0132 }
        L_0x0122:
            boolean r14 = r10.has(r0)     // Catch:{ Exception -> 0x0132 }
            if (r14 == 0) goto L_0x012e
            java.lang.String r14 = r10.getString(r0)     // Catch:{ Exception -> 0x0132 }
            f2453j = r14     // Catch:{ Exception -> 0x0132 }
        L_0x012e:
            r13.mo14957c()     // Catch:{ Exception -> 0x0132 }
            goto L_0x0150
        L_0x0132:
            r14 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = f2445b
            r0.append(r1)
            java.lang.String r1 = "|parseSdkConfig error|"
            r0.append(r1)
            java.lang.String r14 = r14.getMessage()
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r14)
        L_0x0150:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p062d.C1418b.mo14954a(byte[]):void");
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14955b() {
        /*
            r4 = this;
            r0 = 0
            com.igexin.push.extension.distribution.basic.e.a r1 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2428f     // Catch:{ Exception -> 0x00b4, all -> 0x00ad }
            java.lang.String r2 = "select key, value from extconfig order by key"
            android.database.Cursor r0 = r1.mo14958a((java.lang.String) r2, (java.lang.String[]) r0)     // Catch:{ Exception -> 0x00b4, all -> 0x00ad }
            if (r0 == 0) goto L_0x00aa
        L_0x000b:
            boolean r1 = r0.moveToNext()     // Catch:{ Exception -> 0x00b4, all -> 0x00ad }
            if (r1 == 0) goto L_0x00aa
            java.lang.String r1 = "key"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ Exception -> 0x00b4, all -> 0x00ad }
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x00b4, all -> 0x00ad }
            r2 = 11
            java.lang.String r3 = "value"
            if (r1 == r2) goto L_0x002f
            r2 = 12
            if (r1 != r2) goto L_0x0026
            goto L_0x002f
        L_0x0026:
            int r2 = r0.getColumnIndex(r3)     // Catch:{ Throwable -> 0x000b }
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Throwable -> 0x000b }
            goto L_0x0046
        L_0x002f:
            int r2 = r0.getColumnIndex(r3)     // Catch:{ Throwable -> 0x000b }
            byte[] r2 = r0.getBlob(r2)     // Catch:{ Throwable -> 0x000b }
            if (r2 == 0) goto L_0x003d
            byte[] r2 = com.igexin.p032b.p042b.C1196a.m1439c(r2)     // Catch:{ Throwable -> 0x000b }
        L_0x003d:
            if (r2 != 0) goto L_0x0040
            goto L_0x000b
        L_0x0040:
            java.lang.String r3 = new java.lang.String     // Catch:{ Throwable -> 0x000b }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x000b }
            r2 = r3
        L_0x0046:
            switch(r1) {
                case 1: goto L_0x000b;
                case 2: goto L_0x009e;
                case 3: goto L_0x0096;
                case 4: goto L_0x0092;
                case 5: goto L_0x008a;
                case 6: goto L_0x007f;
                case 7: goto L_0x0074;
                case 8: goto L_0x0069;
                case 9: goto L_0x0066;
                case 10: goto L_0x005b;
                case 11: goto L_0x0058;
                case 12: goto L_0x0055;
                case 13: goto L_0x004a;
                default: goto L_0x0049;
            }     // Catch:{ Throwable -> 0x000b }
        L_0x0049:
            goto L_0x000b
        L_0x004a:
            java.lang.String r1 = "null"
            boolean r1 = r2.equals(r1)     // Catch:{ Throwable -> 0x000b }
            if (r1 != 0) goto L_0x000b
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2430h = r2     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0055:
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2438p = r2     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0058:
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2437o = r2     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x005b:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x000b }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2436n = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0066:
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2435m = r2     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0069:
            java.lang.Long r1 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x000b }
            long r1 = r1.longValue()     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2433k = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0074:
            java.lang.Long r1 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x000b }
            long r1 = r1.longValue()     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2432j = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x007f:
            java.lang.Long r1 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x000b }
            long r1 = r1.longValue()     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2434l = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x008a:
            boolean r1 = java.lang.Boolean.parseBoolean(r2)     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2431i = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0092:
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2426d = r2     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x0096:
            long r1 = java.lang.Long.parseLong(r2)     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2429g = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x009e:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x000b }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x000b }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2427e = r1     // Catch:{ Throwable -> 0x000b }
            goto L_0x000b
        L_0x00aa:
            if (r0 == 0) goto L_0x00ba
            goto L_0x00b7
        L_0x00ad:
            r1 = move-exception
            if (r0 == 0) goto L_0x00b3
            r0.close()
        L_0x00b3:
            throw r1
        L_0x00b4:
            if (r0 == 0) goto L_0x00ba
        L_0x00b7:
            r0.close()
        L_0x00ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p062d.C1418b.mo14955b():void");
    }

    /* renamed from: b */
    public void mo14956b(long j) {
        C1416f.f2434l = j;
        mo14951a(6, String.valueOf(j));
    }

    /* renamed from: c */
    public void mo14957c() {
        int i = C1416f.f2427e;
        int i2 = f2446c;
        if (i != i2) {
            C1416f.f2427e = i2;
            mo14951a(2, String.valueOf(f2446c));
        }
        boolean z = C1416f.f2431i;
        boolean z2 = f2448e;
        if (z != z2) {
            C1416f.f2431i = z2;
            mo14951a(5, String.valueOf(f2448e));
        }
        long j = C1416f.f2432j;
        long j2 = f2449f;
        if (j != j2) {
            C1416f.f2432j = j2;
            mo14951a(7, String.valueOf(f2449f));
        }
        long j3 = C1416f.f2433k;
        long j4 = f2450g;
        if (j3 != j4) {
            C1416f.f2433k = j4;
            mo14951a(8, String.valueOf(f2450g));
        }
        if (!C1416f.f2435m.equals(f2447d)) {
            C1416f.f2435m = f2447d;
            mo14951a(9, f2447d);
        }
        int i3 = C1416f.f2436n;
        int i4 = f2451h;
        if (i3 != i4) {
            C1416f.f2436n = i4;
            mo14951a(10, String.valueOf(f2451h));
        }
        if (!C1416f.f2437o.equals(f2452i)) {
            C1416f.f2437o = f2452i;
            mo14952a(11, C1196a.m1438b(f2452i.getBytes()));
        }
        if (!C1416f.f2438p.equals(f2453j)) {
            C1416f.f2438p = f2453j;
            mo14952a(12, C1196a.m1438b(f2453j.getBytes()));
        }
        mo14951a(13, C1416f.f2430h);
    }
}
