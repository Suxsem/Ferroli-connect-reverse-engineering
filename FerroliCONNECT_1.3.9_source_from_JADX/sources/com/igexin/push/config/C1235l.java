package com.igexin.push.config;

import android.content.Context;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.util.C1581f;

/* renamed from: com.igexin.push.config.l */
public class C1235l {

    /* renamed from: a */
    private static String f1866a = "FileConfig";

    /* JADX WARNING: Can't wrap try/catch for region: R(5:43|44|45|46|79) */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        if (r0 != null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0025, code lost:
        if (r0 != null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00a1 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031 A[SYNTHETIC, Splitter:B:12:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b5 A[SYNTHETIC, Splitter:B:54:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00bc A[SYNTHETIC, Splitter:B:58:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00c4 A[SYNTHETIC, Splitter:B:67:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m1625a() {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.igexin.push.core.C1343f.f2168e
            r0.append(r1)
            java.lang.String r1 = ".properties"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.AssetManager r1 = r1.getAssets()
            r2 = 0
            java.io.InputStream r0 = r1.open(r0)     // Catch:{ Exception -> 0x0035, all -> 0x002d }
            m1627a((java.io.InputStream) r0)     // Catch:{ Exception -> 0x0036, all -> 0x002b }
            if (r0 == 0) goto L_0x0039
        L_0x0027:
            r0.close()     // Catch:{ Exception -> 0x0039 }
            goto L_0x0039
        L_0x002b:
            r1 = move-exception
            goto L_0x002f
        L_0x002d:
            r1 = move-exception
            r0 = r2
        L_0x002f:
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            throw r1
        L_0x0035:
            r0 = r2
        L_0x0036:
            if (r0 == 0) goto L_0x0039
            goto L_0x0027
        L_0x0039:
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f
            boolean r1 = com.igexin.push.util.C1593r.m3267a(r1)
            if (r1 != 0) goto L_0x0042
            return
        L_0x0042:
            java.io.File r1 = new java.io.File
            java.lang.String r3 = com.igexin.push.core.C1343f.f2127S
            r1.<init>(r3)
            boolean r1 = r1.exists()
            if (r1 != 0) goto L_0x0050
            return
        L_0x0050:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00c0, all -> 0x00ae }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2127S     // Catch:{ Exception -> 0x00c0, all -> 0x00ae }
            r1.<init>(r3)     // Catch:{ Exception -> 0x00c0, all -> 0x00ae }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00c1, all -> 0x00a9 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00c1, all -> 0x00a9 }
            java.lang.String r4 = "UTF-8"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x00c1, all -> 0x00a9 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x00c1, all -> 0x00a9 }
        L_0x0063:
            java.lang.String r2 = r0.readLine()     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            if (r2 == 0) goto L_0x009e
            java.lang.String r3 = "#"
            boolean r3 = r2.startsWith(r3)     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            if (r3 == 0) goto L_0x0072
            goto L_0x0063
        L_0x0072:
            java.lang.String r3 = "="
            java.lang.String[] r2 = r2.split(r3)     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            int r3 = r2.length     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            r4 = 2
            if (r3 >= r4) goto L_0x007d
            goto L_0x0063
        L_0x007d:
            r3 = 0
            r3 = r2[r3]     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            r4 = 1
            r2 = r2[r4]     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            java.lang.String r2 = r2.trim()     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            java.lang.String r4 = "sdk.debug"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            if (r3 == 0) goto L_0x0063
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            boolean r2 = r2.booleanValue()     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            com.igexin.p032b.p033a.p039c.C1179b.f1610a = r2     // Catch:{ Exception -> 0x00a7, all -> 0x00a5 }
            goto L_0x0063
        L_0x009e:
            r0.close()     // Catch:{ IOException -> 0x00a1 }
        L_0x00a1:
            r1.close()     // Catch:{ Exception -> 0x00cc }
            goto L_0x00cc
        L_0x00a5:
            r2 = move-exception
            goto L_0x00b3
        L_0x00a7:
            goto L_0x00c2
        L_0x00a9:
            r0 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x00b3
        L_0x00ae:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r2
            r2 = r5
        L_0x00b3:
            if (r0 == 0) goto L_0x00ba
            r0.close()     // Catch:{ IOException -> 0x00b9 }
            goto L_0x00ba
        L_0x00b9:
        L_0x00ba:
            if (r1 == 0) goto L_0x00bf
            r1.close()     // Catch:{ Exception -> 0x00bf }
        L_0x00bf:
            throw r2
        L_0x00c0:
            r1 = r0
        L_0x00c1:
            r0 = r2
        L_0x00c2:
            if (r0 == 0) goto L_0x00c9
            r0.close()     // Catch:{ IOException -> 0x00c8 }
            goto L_0x00c9
        L_0x00c8:
        L_0x00c9:
            if (r1 == 0) goto L_0x00cc
            goto L_0x00a1
        L_0x00cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.config.C1235l.m1625a():void");
    }

    /* renamed from: a */
    public static void m1626a(Context context) {
        try {
            byte[] a = C1581f.m3234a(context.getFilesDir().getPath() + "/" + "conf_n.pid");
            if (a != null) {
                C1234k.f1859t = Boolean.valueOf(new String(a)).booleanValue();
            }
        } catch (Throwable th) {
            C1179b.m1354a(f1866a + "|load need confgi error = " + th.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01a1 A[SYNTHETIC, Splitter:B:75:0x01a1] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01ad A[SYNTHETIC, Splitter:B:83:0x01ad] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m1627a(java.io.InputStream r7) {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x01aa, all -> 0x019d }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x01aa, all -> 0x019d }
            java.lang.String r3 = "UTF-8"
            r2.<init>(r7, r3)     // Catch:{ Exception -> 0x01aa, all -> 0x019d }
            r1.<init>(r2)     // Catch:{ Exception -> 0x01aa, all -> 0x019d }
        L_0x000d:
            java.lang.String r7 = r1.readLine()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r7 == 0) goto L_0x0195
            java.lang.String r0 = "#"
            boolean r0 = r7.startsWith(r0)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r0 == 0) goto L_0x001c
            goto L_0x000d
        L_0x001c:
            java.lang.String r0 = "="
            java.lang.String[] r7 = r7.split(r0)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            int r0 = r7.length     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            r2 = 2
            if (r0 >= r2) goto L_0x0027
            goto L_0x000d
        L_0x0027:
            r0 = 0
            r0 = r7[r0]     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            java.lang.String r0 = r0.trim()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            r2 = 1
            r7 = r7[r2]     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            java.lang.String r7 = r7.trim()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            java.lang.String r2 = "sdk.cm_address"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            java.lang.String r3 = ","
            if (r2 == 0) goto L_0x0047
            java.lang.String[] r7 = r7.split(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.SDKUrlConfig.setXfrAddressIps(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0047:
            java.lang.String r2 = "sdk.config_address"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0056
            java.lang.String[] r7 = r7.split(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.SDKUrlConfig.CONFIG_ADDRESS_IPS = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0056:
            java.lang.String r2 = "sdk.bi_address"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0065
            java.lang.String[] r7 = r7.split(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.SDKUrlConfig.BI_ADDRESS_IPS = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0065:
            java.lang.String r2 = "sdk.cm_address_backup"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0074
            java.lang.String[] r7 = r7.split(r3)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.SDKUrlConfig.XFR_ADDRESS_BAK = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0074:
            java.lang.String r2 = "sdk.debug"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0087
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.p032b.p033a.p039c.C1179b.f1610a = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0087:
            java.lang.String r2 = "sdk.domainbackup.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x009b
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1845f = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x009b:
            java.lang.String r2 = "sdk.uploadapplist.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x00af
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1846g = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x00af:
            java.lang.String r2 = "sdk.feature.sendmessage.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x00c3
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1847h = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x00c3:
            java.lang.String r2 = "sdk.feature.settag.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x00d7
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1848i = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x00d7:
            java.lang.String r2 = "sdk.feature.setsilenttime.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x00eb
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1849j = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x00eb:
            java.lang.String r2 = "sdk.feature.setheartbeatinterval.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x00ff
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1850k = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x00ff:
            java.lang.String r2 = "sdk.feature.setsockettimeout.enable"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0113
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            boolean r7 = r7.booleanValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1851l = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0113:
            java.lang.String r2 = "sdk.stay.backup.time"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            r3 = 1000(0x3e8, double:4.94E-321)
            if (r2 == 0) goto L_0x012b
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r7.longValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r5 * r3
            com.igexin.push.config.C1234k.f1862w = r5     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x012b:
            java.lang.String r2 = "sdk.enter.backup.detect.failed.cnt"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x013f
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            int r7 = r7.intValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1863x = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x013f:
            java.lang.String r2 = "sdk.login.failed.cnt"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0153
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            int r7 = r7.intValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            com.igexin.push.config.C1234k.f1864y = r7     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0153:
            java.lang.String r2 = "sdk.detect.ip.expired.time"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x0169
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r7.longValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r5 * r3
            com.igexin.push.config.C1234k.f1865z = r5     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0169:
            java.lang.String r2 = "sdk.detect.interval.time"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r2 == 0) goto L_0x017f
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r7.longValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r5 * r3
            com.igexin.push.config.C1234k.f1819A = r5     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x017f:
            java.lang.String r2 = "sdk.reset.reconnect.delay"
            boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            if (r0 == 0) goto L_0x000d
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r7.longValue()     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            long r5 = r5 * r3
            com.igexin.push.config.C1234k.f1861v = r5     // Catch:{ Exception -> 0x019b, all -> 0x0199 }
            goto L_0x000d
        L_0x0195:
            r1.close()     // Catch:{ Exception -> 0x01b1 }
            goto L_0x01b5
        L_0x0199:
            r7 = move-exception
            goto L_0x019f
        L_0x019b:
            goto L_0x01ab
        L_0x019d:
            r7 = move-exception
            r1 = r0
        L_0x019f:
            if (r1 == 0) goto L_0x01a9
            r1.close()     // Catch:{ Exception -> 0x01a5 }
            goto L_0x01a9
        L_0x01a5:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01a9:
            throw r7
        L_0x01aa:
            r1 = r0
        L_0x01ab:
            if (r1 == 0) goto L_0x01b5
            r1.close()     // Catch:{ Exception -> 0x01b1 }
            goto L_0x01b5
        L_0x01b1:
            r7 = move-exception
            r7.printStackTrace()
        L_0x01b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.config.C1235l.m1627a(java.io.InputStream):void");
    }
}
