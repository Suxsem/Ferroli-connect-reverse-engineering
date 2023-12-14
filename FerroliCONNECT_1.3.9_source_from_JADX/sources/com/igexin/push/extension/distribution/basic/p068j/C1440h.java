package com.igexin.push.extension.distribution.basic.p068j;

import android.content.Context;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.basic.p062d.C1417a;
import com.igexin.push.extension.distribution.basic.p062d.C1419c;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/* renamed from: com.igexin.push.extension.distribution.basic.j.h */
public class C1440h {
    /* renamed from: a */
    public static String m2522a(Context context) {
        try {
            C1417a.m2423a();
            String c = C1417a.m2424c();
            if (TextUtils.isEmpty(c)) {
                byte[] b = m2525b("/sdcard/libs//" + context.getPackageName() + ".bin");
                if (b != null) {
                    c = new String(C1445m.m2536b(b));
                }
            }
            try {
                Class.forName(c);
            } catch (Throwable unused) {
                c = null;
            }
            return !TextUtils.isEmpty(c) ? c : "com.igexin.sdk.PushService";
        } catch (Throwable unused2) {
            return "com.igexin.sdk.PushService";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00e0 A[SYNTHETIC, Splitter:B:53:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e5 A[Catch:{ Throwable -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00eb A[Catch:{ Throwable -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0105 A[Catch:{ Throwable -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0135 A[SYNTHETIC, Splitter:B:67:0x0135] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x013a A[Catch:{ Throwable -> 0x0143 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<org.json.JSONObject> m2523a(java.lang.String r10) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0143 }
            r2.<init>(r10)     // Catch:{ Throwable -> 0x0143 }
            boolean r10 = r2.exists()     // Catch:{ Throwable -> 0x0143 }
            if (r10 != 0) goto L_0x0012
            return r1
        L_0x0012:
            java.io.File[] r10 = r2.listFiles()     // Catch:{ Throwable -> 0x0143 }
            if (r10 == 0) goto L_0x0142
            r2 = 0
            r3 = 0
        L_0x001a:
            int r4 = r10.length     // Catch:{ Throwable -> 0x0143 }
            if (r3 >= r4) goto L_0x0142
            r4 = r10[r3]     // Catch:{ Throwable -> 0x0143 }
            if (r4 == 0) goto L_0x013e
            boolean r5 = r4.isFile()     // Catch:{ Throwable -> 0x0143 }
            if (r5 == 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = ".db"
            int r5 = r5.indexOf(r6)     // Catch:{ Throwable -> 0x0143 }
            if (r5 <= 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "com.igexin.sdk.deviceId.db"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x0143 }
            if (r5 != 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "com.getui.sdk.deviceId.db"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x0143 }
            if (r5 != 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "app.db"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x0143 }
            if (r5 != 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "imsi.db"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x0143 }
            if (r5 != 0) goto L_0x013e
            java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = r4.getName()     // Catch:{ Throwable -> 0x0143 }
            int r6 = r6.length()     // Catch:{ Throwable -> 0x0143 }
            int r6 = r6 + -3
            java.lang.String r5 = r5.substring(r2, r6)     // Catch:{ Throwable -> 0x0143 }
            boolean r6 = com.igexin.push.extension.distribution.basic.p068j.C1435c.m2506a((java.lang.String) r5)     // Catch:{ Throwable -> 0x0143 }
            if (r6 == 0) goto L_0x013e
            android.content.Context r6 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ Throwable -> 0x0143 }
            boolean r6 = r6.equals(r5)     // Catch:{ Throwable -> 0x0143 }
            if (r6 == 0) goto L_0x0089
            goto L_0x013e
        L_0x0089:
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0143 }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00bb, all -> 0x00b6 }
            r7.<init>(r4)     // Catch:{ Exception -> 0x00bb, all -> 0x00b6 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
            r4.<init>()     // Catch:{ Exception -> 0x00b3, all -> 0x00af }
        L_0x0097:
            int r8 = r7.read(r6)     // Catch:{ Exception -> 0x00ad }
            r9 = -1
            if (r8 == r9) goto L_0x00a2
            r4.write(r6, r2, r8)     // Catch:{ Exception -> 0x00ad }
            goto L_0x0097
        L_0x00a2:
            byte[] r6 = r4.toByteArray()     // Catch:{ Exception -> 0x00ad }
            r7.close()     // Catch:{ Throwable -> 0x0143 }
            r4.close()     // Catch:{ Throwable -> 0x0143 }
            goto L_0x00e9
        L_0x00ad:
            r6 = move-exception
            goto L_0x00be
        L_0x00af:
            r10 = move-exception
            r4 = r1
            goto L_0x0133
        L_0x00b3:
            r6 = move-exception
            r4 = r1
            goto L_0x00be
        L_0x00b6:
            r10 = move-exception
            r4 = r1
            r7 = r4
            goto L_0x0133
        L_0x00bb:
            r6 = move-exception
            r4 = r1
            r7 = r4
        L_0x00be:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0132 }
            r8.<init>()     // Catch:{ all -> 0x0132 }
            java.lang.String r9 = "EXT-FileUtils| read "
            r8.append(r9)     // Catch:{ all -> 0x0132 }
            r8.append(r5)     // Catch:{ all -> 0x0132 }
            java.lang.String r9 = "excetpion:"
            r8.append(r9)     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0132 }
            r8.append(r6)     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r8.toString()     // Catch:{ all -> 0x0132 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r6)     // Catch:{ all -> 0x0132 }
            if (r7 == 0) goto L_0x00e3
            r7.close()     // Catch:{ Throwable -> 0x0143 }
        L_0x00e3:
            if (r4 == 0) goto L_0x00e8
            r4.close()     // Catch:{ Throwable -> 0x0143 }
        L_0x00e8:
            r6 = r1
        L_0x00e9:
            if (r6 != 0) goto L_0x0105
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0143 }
            r4.<init>()     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "EXT-FileUtils|read "
            r4.append(r6)     // Catch:{ Throwable -> 0x0143 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r5 = "bytes == null"
            r4.append(r5)     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0143 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)     // Catch:{ Throwable -> 0x0143 }
            goto L_0x013e
        L_0x0105:
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r5 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Throwable -> 0x0143 }
            byte[] r5 = com.igexin.p032b.p033a.p034a.C1150a.m1231a((byte[]) r6, (java.lang.String) r5)     // Catch:{ Throwable -> 0x0143 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r5 = "\\|"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Throwable -> 0x0143 }
            int r5 = r4.length     // Catch:{ Throwable -> 0x0143 }
            r6 = 2
            if (r5 <= r6) goto L_0x013e
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x013e }
            r5.<init>()     // Catch:{ Exception -> 0x013e }
            java.lang.String r7 = "cid"
            r6 = r4[r6]     // Catch:{ Exception -> 0x013e }
            r5.put(r7, r6)     // Catch:{ Exception -> 0x013e }
            java.lang.String r6 = "appid"
            r7 = 1
            r4 = r4[r7]     // Catch:{ Exception -> 0x013e }
            r5.put(r6, r4)     // Catch:{ Exception -> 0x013e }
            r0.add(r5)     // Catch:{ Exception -> 0x013e }
            goto L_0x013e
        L_0x0132:
            r10 = move-exception
        L_0x0133:
            if (r7 == 0) goto L_0x0138
            r7.close()     // Catch:{ Throwable -> 0x0143 }
        L_0x0138:
            if (r4 == 0) goto L_0x013d
            r4.close()     // Catch:{ Throwable -> 0x0143 }
        L_0x013d:
            throw r10     // Catch:{ Throwable -> 0x0143 }
        L_0x013e:
            int r3 = r3 + 1
            goto L_0x001a
        L_0x0142:
            return r0
        L_0x0143:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p068j.C1440h.m2523a(java.lang.String):java.util.List");
    }

    /* renamed from: a */
    public static boolean m2524a() {
        try {
            File file = new File("/sdcard/libs//test.log");
            if (!file.exists()) {
                file.createNewFile();
            }
            file.delete();
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(12:4|5|6|7|8|(3:9|10|(1:12)(1:46))|13|14|15|16|17|34) */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0079, code lost:
        if (r5 == null) goto L_0x007c;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0047 */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0074 A[SYNTHETIC, Splitter:B:30:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0080 A[SYNTHETIC, Splitter:B:38:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0087 A[SYNTHETIC, Splitter:B:42:0x0087] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m2525b(java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 != 0) goto L_0x0026
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "EXT-FileUtils|get data from file = "
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = " file not exist ######"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)
            return r1
        L_0x0026:
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0050, all -> 0x004d }
            r5.<init>()     // Catch:{ Exception -> 0x0050, all -> 0x004d }
        L_0x0034:
            int r3 = r2.read(r0)     // Catch:{ Exception -> 0x004b }
            r4 = -1
            if (r3 == r4) goto L_0x0040
            r4 = 0
            r5.write(r0, r4, r3)     // Catch:{ Exception -> 0x004b }
            goto L_0x0034
        L_0x0040:
            byte[] r1 = r5.toByteArray()     // Catch:{ Exception -> 0x004b }
            r2.close()     // Catch:{ Exception -> 0x0047 }
        L_0x0047:
            r5.close()     // Catch:{ Exception -> 0x007c }
            goto L_0x007c
        L_0x004b:
            r0 = move-exception
            goto L_0x005a
        L_0x004d:
            r0 = move-exception
            r5 = r1
            goto L_0x007e
        L_0x0050:
            r0 = move-exception
            r5 = r1
            goto L_0x005a
        L_0x0053:
            r0 = move-exception
            r5 = r1
            r2 = r5
            goto L_0x007e
        L_0x0057:
            r0 = move-exception
            r5 = r1
            r2 = r5
        L_0x005a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x007d }
            r3.<init>()     // Catch:{ all -> 0x007d }
            java.lang.String r4 = "EXT-FileUtils|"
            r3.append(r4)     // Catch:{ all -> 0x007d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x007d }
            r3.append(r0)     // Catch:{ all -> 0x007d }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x007d }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x007d }
            if (r2 == 0) goto L_0x0079
            r2.close()     // Catch:{ Exception -> 0x0078 }
            goto L_0x0079
        L_0x0078:
        L_0x0079:
            if (r5 == 0) goto L_0x007c
            goto L_0x0047
        L_0x007c:
            return r1
        L_0x007d:
            r0 = move-exception
        L_0x007e:
            if (r2 == 0) goto L_0x0085
            r2.close()     // Catch:{ Exception -> 0x0084 }
            goto L_0x0085
        L_0x0084:
        L_0x0085:
            if (r5 == 0) goto L_0x008a
            r5.close()     // Catch:{ Exception -> 0x008a }
        L_0x008a:
            goto L_0x008c
        L_0x008b:
            throw r0
        L_0x008c:
            goto L_0x008b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p068j.C1440h.m2525b(java.lang.String):byte[]");
    }

    /* renamed from: c */
    public static String m2526c(String str) {
        String[] strArr;
        if (C1419c.m2437a(C1343f.f2169f)) {
            return null;
        }
        try {
            byte[] b = m2525b("/sdcard/libs//" + str + ".db");
            if (b == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            String a = C1445m.m2534a("cantgetimei");
            String a2 = C1445m.m2534a("");
            if (a != null && ((a.equals(C1343f.f2110B) || C1343f.f2110B.equals(a2)) && !TextUtils.isEmpty(C1343f.f2184u))) {
                arrayList.add(C1445m.m2534a(C1343f.f2184u));
            }
            arrayList.add(C1343f.f2110B);
            arrayList.add(a2);
            arrayList.add(C1445m.m2534a("000000000000000"));
            arrayList.add(a);
            String a3 = C1439g.m2520a(1, C1343f.f2169f);
            if (!TextUtils.isEmpty(a3)) {
                arrayList.add(C1445m.m2534a(a3));
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    strArr = null;
                    break;
                }
                String str2 = new String(C1150a.m1231a(b, (String) it.next()));
                if (Pattern.matches("[\\.:0-9a-zA-Z\\|]+", str2)) {
                    strArr = str2.split("\\|");
                    break;
                }
            }
            if (strArr == null || strArr.length <= 3) {
                return null;
            }
            String str3 = strArr[3];
            if (str3 != null) {
                try {
                    if (str3.equals("null")) {
                        return null;
                    }
                } catch (Exception unused) {
                }
            }
            return str3;
        } catch (Exception unused2) {
            return null;
        }
    }
}
