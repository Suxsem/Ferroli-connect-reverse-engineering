package com.igexin.push.core.p050c;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.C1561a;
import com.igexin.push.p088g.p090b.C1574g;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.C1589n;
import com.igexin.push.util.EncryptUtils;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.core.c.h */
public class C1312h implements C1295a {

    /* renamed from: a */
    private static final String f2047a = "com.igexin.push.core.c.h";

    /* renamed from: b */
    private static C1312h f2048b;

    /* renamed from: c */
    private Map<String, String> f2049c = new TreeMap();

    /* renamed from: d */
    private boolean f2050d;

    private C1312h() {
    }

    /* renamed from: a */
    public static C1312h m1937a() {
        if (f2048b == null) {
            f2048b = new C1312h();
        }
        return f2048b;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1938a(SQLiteDatabase sQLiteDatabase, int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgooConstants.MESSAGE_ID, Integer.valueOf(i));
        contentValues.put("value", str);
        sQLiteDatabase.replace("runtime", (String) null, contentValues);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1939a(SQLiteDatabase sQLiteDatabase, int i, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgooConstants.MESSAGE_ID, Integer.valueOf(i));
        contentValues.put("value", bArr);
        sQLiteDatabase.replace("runtime", (String) null, contentValues);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0039, code lost:
        if (r4 != null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        if (r4 != null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003f  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] m1943a(android.database.sqlite.SQLiteDatabase r4, int r5) {
        /*
            r3 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            r1.<init>()     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            java.lang.String r2 = "select value from runtime where id="
            r1.append(r2)     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            r1.append(r5)     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            android.database.Cursor r4 = r4.rawQuery(r5, r0)     // Catch:{ Exception -> 0x0043, all -> 0x003c }
            if (r4 == 0) goto L_0x0039
            boolean r5 = r4.moveToFirst()     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            if (r5 == 0) goto L_0x0039
            java.lang.String r5 = "value"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            byte[] r5 = r4.getBlob(r5)     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            java.lang.String r1 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            byte[] r5 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r5, r1)     // Catch:{ Exception -> 0x0037, all -> 0x0034 }
            if (r4 == 0) goto L_0x0033
            r4.close()
        L_0x0033:
            return r5
        L_0x0034:
            r5 = move-exception
            r0 = r4
            goto L_0x003d
        L_0x0037:
            goto L_0x0044
        L_0x0039:
            if (r4 == 0) goto L_0x0049
            goto L_0x0046
        L_0x003c:
            r5 = move-exception
        L_0x003d:
            if (r0 == 0) goto L_0x0042
            r0.close()
        L_0x0042:
            throw r5
        L_0x0043:
            r4 = r0
        L_0x0044:
            if (r4 == 0) goto L_0x0049
        L_0x0046:
            r4.close()
        L_0x0049:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1312h.m1943a(android.database.sqlite.SQLiteDatabase, int):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        if (r4 != null) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003e, code lost:
        if (r4 != null) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0040, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m1945b(android.database.sqlite.SQLiteDatabase r4, int r5) {
        /*
            r3 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            r1.<init>()     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            java.lang.String r2 = "select value from runtime where id="
            r1.append(r2)     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            r1.append(r5)     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            android.database.Cursor r4 = r4.rawQuery(r5, r0)     // Catch:{ Exception -> 0x003d, all -> 0x0036 }
            if (r4 == 0) goto L_0x0033
            boolean r5 = r4.moveToFirst()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            if (r5 == 0) goto L_0x0033
            java.lang.String r5 = "value"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            if (r4 == 0) goto L_0x002d
            r4.close()
        L_0x002d:
            return r5
        L_0x002e:
            r5 = move-exception
            r0 = r4
            goto L_0x0037
        L_0x0031:
            goto L_0x003e
        L_0x0033:
            if (r4 == 0) goto L_0x0043
            goto L_0x0040
        L_0x0036:
            r5 = move-exception
        L_0x0037:
            if (r0 == 0) goto L_0x003c
            r0.close()
        L_0x003c:
            throw r5
        L_0x003d:
            r4 = r0
        L_0x003e:
            if (r4 == 0) goto L_0x0043
        L_0x0040:
            r4.close()
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1312h.m1945b(android.database.sqlite.SQLiteDatabase, int):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        if (r0 == 0) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
        if (com.igexin.push.core.C1343f.f2110B != null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        if (com.igexin.push.core.C1343f.f2184u != null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        r4 = "cantgetimei";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        r4 = com.igexin.push.core.C1343f.f2184u;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        com.igexin.push.core.C1343f.f2110B = com.igexin.p032b.p042b.C1196a.m1435a(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002e, code lost:
        if (r0 != 0) goto L_0x003b;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m1946e(android.database.sqlite.SQLiteDatabase r4) {
        /*
            r3 = this;
            r0 = 0
            java.lang.String r1 = "select value from runtime where id=25"
            android.database.Cursor r0 = r4.rawQuery(r1, r0)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            if (r0 == 0) goto L_0x002e
            boolean r4 = r0.moveToFirst()     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            if (r4 == 0) goto L_0x002e
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            java.lang.String r1 = "value"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            byte[] r1 = r0.getBlob(r1)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            java.lang.String r2 = r2.getPackageName()     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            java.lang.String r2 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r2)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            byte[] r1 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r1, r2)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
            com.igexin.push.core.C1343f.f2110B = r4     // Catch:{ Exception -> 0x0038, all -> 0x0031 }
        L_0x002e:
            if (r0 == 0) goto L_0x003e
            goto L_0x003b
        L_0x0031:
            r4 = move-exception
            if (r0 == 0) goto L_0x0037
            r0.close()
        L_0x0037:
            throw r4
        L_0x0038:
            if (r0 == 0) goto L_0x003e
        L_0x003b:
            r0.close()
        L_0x003e:
            java.lang.String r4 = com.igexin.push.core.C1343f.f2110B
            if (r4 != 0) goto L_0x0051
            java.lang.String r4 = com.igexin.push.core.C1343f.f2184u
            if (r4 != 0) goto L_0x0049
            java.lang.String r4 = "cantgetimei"
            goto L_0x004b
        L_0x0049:
            java.lang.String r4 = com.igexin.push.core.C1343f.f2184u
        L_0x004b:
            java.lang.String r4 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r4)
            com.igexin.push.core.C1343f.f2110B = r4
        L_0x0051:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1312h.m1946e(android.database.sqlite.SQLiteDatabase):void");
    }

    /* renamed from: f */
    private void m1947f() {
        String str = C1343f.f2184u;
        if (TextUtils.isEmpty(str) || str.length() <= 8) {
            if (Build.VERSION.SDK_INT < 26) {
                str = "V" + C1589n.m3260b();
            }
            if (str == null || str.length() <= 8) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("V");
                    sb.append(C1196a.m1435a(m1954i() + C1343f.f2168e + UUID.randomUUID()));
                    str = sb.toString();
                } catch (Throwable th) {
                    C1179b.m1354a(f2047a + "|" + th.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("V");
                    sb2.append(m1954i());
                    str = sb2.toString();
                }
            }
        }
        C1343f.f2109A = "A-" + str + "-" + System.currentTimeMillis();
        if (C1343f.f2109A.length() >= 64) {
            try {
                C1343f.f2109A = C1343f.f2109A.substring(0, 62);
            } catch (Throwable th2) {
                C1179b.m1354a(f2047a + "|" + th2.toString());
            }
        }
    }

    /* renamed from: f */
    private void m1948f(SQLiteDatabase sQLiteDatabase) {
        String b = m1945b(sQLiteDatabase, 2);
        if (!TextUtils.isEmpty(b)) {
            if (b.equals("null")) {
                b = null;
            }
            C1343f.f2187x = b;
        }
    }

    /* renamed from: g */
    private void m1949g(SQLiteDatabase sQLiteDatabase) {
        String b = m1945b(sQLiteDatabase, 46);
        if (!TextUtils.isEmpty(b)) {
            if (b.equals("null")) {
                b = null;
            }
            C1343f.f2188y = b;
        }
    }

    /* renamed from: g */
    private boolean m1950g() {
        return C1174c.m1310b().mo14317a(new C1298ac(this), false, true);
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void m1951h() {
        C1581f.m3230a();
        String c = C1581f.m3239c();
        if (c == null || c.length() <= 5) {
            C1581f.m3241e();
        }
    }

    /* renamed from: h */
    private void m1952h(SQLiteDatabase sQLiteDatabase) {
        String b = m1945b(sQLiteDatabase, 48);
        if (!TextUtils.isEmpty(b)) {
            if (b.equals("null")) {
                b = null;
            }
            C1343f.f2189z = b;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public byte[] m1953h(String str) {
        return EncryptUtils.getBytesEncrypted(str.getBytes());
    }

    /* renamed from: i */
    private String m1954i() {
        Random random = new Random(Math.abs(new Random().nextLong()));
        String str = "";
        for (int i = 0; i < 15; i++) {
            str = str + random.nextInt(10);
        }
        return str;
    }

    /* renamed from: i */
    private void m1955i(SQLiteDatabase sQLiteDatabase) {
        String b = m1945b(sQLiteDatabase, 3);
        if (!TextUtils.isEmpty(b)) {
            if (b.equals("null")) {
                b = null;
            }
            C1343f.f2109A = b;
        }
    }

    /* renamed from: j */
    private void m1956j(SQLiteDatabase sQLiteDatabase) {
        byte[] a = m1943a(sQLiteDatabase, 1);
        if (a != null) {
            try {
                String str = new String(a);
                C1343f.f2181r = str.equals("null") ? 0 : Long.parseLong(str);
            } catch (Exception unused) {
            }
            C1179b.m1354a(f2047a + "|db version changed, save session = " + C1343f.f2181r);
        }
    }

    /* renamed from: k */
    private void m1957k(SQLiteDatabase sQLiteDatabase) {
        byte[] a = m1943a(sQLiteDatabase, 20);
        if (a != null) {
            String str = new String(a);
            if (str.equals("null")) {
                str = null;
            }
            C1343f.f2183t = str;
            C1343f.f2182s = str;
            C1179b.m1354a(f2047a + "|db version changed, save cid = " + str);
        }
    }

    /* renamed from: a */
    public void mo14442a(SQLiteDatabase sQLiteDatabase) {
    }

    /* renamed from: a */
    public boolean mo14665a(int i) {
        C1343f.f2126R = i;
        return C1174c.m1310b().mo14317a(new C1321q(this), false, true);
    }

    /* renamed from: a */
    public boolean mo14666a(long j) {
        if (j == C1343f.f2115G) {
            return false;
        }
        C1343f.f2115G = j;
        return C1174c.m1310b().mo14317a(new C1324t(this), false, true);
    }

    /* renamed from: a */
    public boolean mo14667a(String str) {
        return C1174c.m1310b().mo14317a(new C1297ab(this, str), false, true);
    }

    /* renamed from: a */
    public boolean mo14668a(String str, String str2, long j) {
        C1343f.f2181r = j;
        if (TextUtils.isEmpty(C1343f.f2187x)) {
            C1343f.f2187x = str2;
        }
        C1343f.f2182s = str;
        return m1950g();
    }

    /* renamed from: a */
    public boolean mo14669a(String str, boolean z) {
        C1174c b;
        C1190e sVar;
        if (str == null) {
            return false;
        }
        String str2 = null;
        if (z) {
            if (!str.equals(C1343f.f2158at)) {
                if (!str.equals("null")) {
                    str2 = str;
                }
                C1343f.f2158at = str2;
                b = C1174c.m1310b();
                sVar = new C1322r(this, str);
            }
            return false;
        }
        if (!str.equals(C1343f.f2159au)) {
            if (!str.equals("null")) {
                str2 = str;
            }
            C1343f.f2159au = str2;
            b = C1174c.m1310b();
            sVar = new C1323s(this, str);
        }
        return false;
        return b.mo14317a(sVar, false, true);
    }

    /* renamed from: a */
    public boolean mo14670a(boolean z) {
        if (C1343f.f2121M == z) {
            return false;
        }
        C1343f.f2121M = z;
        if (!z) {
            C1561a.m3116k();
        }
        return C1174c.m1310b().mo14317a(new C1318n(this), false, true);
    }

    /* renamed from: b */
    public void mo14671b() {
        C1174c.m1310b().mo14317a(new C1313i(this), false, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:184:0x02ec, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x02f0, code lost:
        if (r14 != null) goto L_0x02fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x02f7, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x02fc, code lost:
        if (r14 != null) goto L_0x02fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x02fe, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x0305, code lost:
        if (com.igexin.push.core.C1343f.f2181r != 0) goto L_0x0320;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x0307, code lost:
        r14 = com.igexin.push.util.C1581f.m3240d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x030d, code lost:
        if (r14 == 0) goto L_0x0320;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x030f, code lost:
        com.igexin.push.core.C1343f.f2181r = r14;
        m1939a(r2, 1, com.igexin.push.util.EncryptUtils.getBytesEncrypted(java.lang.String.valueOf(r14).getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0322, code lost:
        if (com.igexin.push.core.C1343f.f2182s != null) goto L_0x033b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x0324, code lost:
        r0 = com.igexin.push.util.C1581f.m3235b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x0328, code lost:
        if (r0 == null) goto L_0x033b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x032a, code lost:
        com.igexin.push.core.C1343f.f2183t = r0;
        com.igexin.push.core.C1343f.f2182s = r0;
        m1939a(r2, 20, com.igexin.push.util.EncryptUtils.getBytesEncrypted(com.igexin.push.core.C1343f.f2182s.getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x033d, code lost:
        if (com.igexin.push.core.C1343f.f2182s != null) goto L_0x0363;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0343, code lost:
        if (com.igexin.push.core.C1343f.f2181r == 0) goto L_0x0363;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x0345, code lost:
        com.igexin.push.core.C1343f.f2183t = com.igexin.p032b.p042b.C1196a.m1435a(java.lang.String.valueOf(com.igexin.push.core.C1343f.f2181r));
        com.igexin.push.core.C1343f.m2076a(com.igexin.push.core.C1343f.f2181r);
        m1939a(r2, 20, com.igexin.push.util.EncryptUtils.getBytesEncrypted(com.igexin.push.core.C1343f.f2182s.getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x036b, code lost:
        if ("cfcd208495d565ef66e7dff9f98764da".equals(com.igexin.push.core.C1343f.f2182s) != false) goto L_0x037b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x036f, code lost:
        if (com.igexin.push.core.C1343f.f2182s == null) goto L_0x0398;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x0379, code lost:
        if (com.igexin.push.core.C1343f.f2182s.matches("([a-f]|[0-9]){32}") != false) goto L_0x0398;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x037f, code lost:
        if (com.igexin.push.core.C1343f.f2181r == 0) goto L_0x0392;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x0381, code lost:
        m1937a().mo14673b(com.igexin.push.core.C1343f.f2181r);
        com.igexin.push.core.C1343f.f2183t = com.igexin.push.core.C1343f.f2182s;
        com.igexin.push.util.C1581f.m3242f();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x0392, code lost:
        com.igexin.push.core.C1343f.f2183t = null;
        com.igexin.push.core.C1343f.f2182s = "null";
        com.igexin.push.core.C1343f.f2181r = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x039e, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2153ao) != false) goto L_0x03a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x03a6, code lost:
        if ("null".equals(com.igexin.push.core.C1343f.f2153ao) == false) goto L_0x03bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x03a8, code lost:
        com.igexin.push.core.C1343f.f2153ao = com.igexin.p032b.p042b.C1196a.m1434a(32);
        m1939a(r2, 14, com.igexin.push.util.EncryptUtils.getBytesEncrypted(com.igexin.push.core.C1343f.f2153ao.getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x03bd, code lost:
        r0 = com.igexin.push.util.C1581f.m3239c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x03c4, code lost:
        if (com.igexin.push.core.C1343f.f2187x != null) goto L_0x03d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x03c6, code lost:
        if (r0 == null) goto L_0x03d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x03cc, code lost:
        if (r0.length() <= 5) goto L_0x03d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x03ce, code lost:
        com.igexin.push.core.C1343f.f2187x = r0;
        m1938a(r2, 2, com.igexin.push.core.C1343f.f2187x);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x03d7, code lost:
        if (com.igexin.push.core.C1343f.f2109A != null) goto L_0x03f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x03d9, code lost:
        m1947f();
        m1938a(r2, 3, com.igexin.push.core.C1343f.f2109A);
        com.igexin.p032b.p033a.p039c.C1179b.m1355a(f2047a, "new registerId : " + com.igexin.push.core.C1343f.f2109A);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x03fd, code lost:
        if (com.igexin.push.config.C1234k.f1837S == false) goto L_0x041f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x03ff, code lost:
        r0 = new com.igexin.push.core.p050c.C1307c(com.igexin.push.core.C1343f.f2169f).mo14659e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x040e, code lost:
        if (android.text.TextUtils.isEmpty(r0) != false) goto L_0x041f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x0416, code lost:
        if (r0.equals(com.igexin.push.core.C1343f.f2188y) != false) goto L_0x041f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x0418, code lost:
        com.igexin.push.core.C1343f.f2188y = r0;
        m1938a(r2, 46, com.igexin.push.core.C1343f.f2188y);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x0421, code lost:
        if (r1.f2050d == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x0423, code lost:
        r1.f2050d = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x042c, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2110B) != false) goto L_0x0447;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x042e, code lost:
        m1939a(r2, 25, com.igexin.p032b.p033a.p034a.C1150a.m1235d(com.igexin.push.core.C1343f.f2110B.getBytes(), com.igexin.p032b.p042b.C1196a.m1435a(com.igexin.push.core.C1343f.f2169f.getPackageName())));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x044b, code lost:
        if (com.igexin.push.core.C1343f.f2181r == 0) goto L_0x045e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x044d, code lost:
        m1939a(r2, 1, com.igexin.push.util.EncryptUtils.getBytesEncrypted(java.lang.String.valueOf(com.igexin.push.core.C1343f.f2181r).getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x0464, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2182s) != false) goto L_0x0473;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:0x0466, code lost:
        m1939a(r2, 20, com.igexin.push.util.EncryptUtils.getBytesEncrypted(com.igexin.push.core.C1343f.f2182s.getBytes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:0x0479, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2187x) != false) goto L_0x0488;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x0481, code lost:
        if (com.igexin.push.core.C1343f.f2187x.length() <= 5) goto L_0x0488;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0483, code lost:
        m1938a(r2, 2, com.igexin.push.core.C1343f.f2187x);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x048e, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2109A) != false) goto L_0x0495;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x0490, code lost:
        m1938a(r2, 3, com.igexin.push.core.C1343f.f2109A);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x049b, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2188y) != false) goto L_0x04a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x049d, code lost:
        m1938a(r2, 46, com.igexin.push.core.C1343f.f2188y);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x04a8, code lost:
        if (android.text.TextUtils.isEmpty(com.igexin.push.core.C1343f.f2189z) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x04aa, code lost:
        m1938a(r2, 48, com.igexin.push.core.C1343f.f2189z);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:?, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:179:0x02c8 */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x02ec A[ExcHandler: all (th java.lang.Throwable), Splitter:B:173:0x02b8] */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x02f7  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14445b(android.database.sqlite.SQLiteDatabase r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            java.lang.String r3 = "null"
            r18.m1946e((android.database.sqlite.SQLiteDatabase) r19)
            r4 = 25
            r5 = 14
            r6 = 2
            r7 = 3
            r8 = 20
            r9 = 0
            r10 = 1
            r11 = 0
            r13 = 0
            java.lang.String r0 = "select id, value from runtime order by id"
            android.database.Cursor r14 = r2.rawQuery(r0, r13)     // Catch:{ Exception -> 0x02fb, all -> 0x02f3 }
            if (r14 == 0) goto L_0x02f0
        L_0x001e:
            boolean r0 = r14.moveToNext()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x02f0
            int r0 = r14.getInt(r9)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == r10) goto L_0x0054
            if (r0 == r5) goto L_0x0054
            r15 = 19
            if (r0 == r15) goto L_0x0054
            if (r0 == r8) goto L_0x0054
            r15 = 23
            if (r0 == r15) goto L_0x0054
            if (r0 == r4) goto L_0x0054
            r15 = 22
            if (r0 == r15) goto L_0x0054
            r15 = 31
            if (r0 == r15) goto L_0x0054
            r15 = 30
            if (r0 == r15) goto L_0x0054
            r15 = 49
            if (r0 == r15) goto L_0x0054
            r15 = 50
            if (r0 != r15) goto L_0x004d
            goto L_0x0054
        L_0x004d:
            java.lang.String r15 = r14.getString(r10)     // Catch:{ Throwable -> 0x02cf }
            r4 = r15
            r15 = r13
            goto L_0x0062
        L_0x0054:
            byte[] r15 = r14.getBlob(r10)     // Catch:{ Throwable -> 0x02cf }
            if (r15 == 0) goto L_0x0061
            java.lang.String r4 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Throwable -> 0x02cf }
            byte[] r4 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r15, r4)     // Catch:{ Throwable -> 0x02cf }
            r15 = r4
        L_0x0061:
            r4 = r13
        L_0x0062:
            if (r15 != 0) goto L_0x0069
            if (r4 != 0) goto L_0x0069
            r4 = 25
            goto L_0x001e
        L_0x0069:
            if (r0 == r10) goto L_0x02b3
            if (r0 == r6) goto L_0x02a9
            if (r0 == r7) goto L_0x029f
            r9 = 4
            if (r0 == r9) goto L_0x028c
            r9 = 6
            if (r0 == r9) goto L_0x027c
            r9 = 8
            if (r0 == r9) goto L_0x026c
            r9 = 40
            if (r0 == r9) goto L_0x0254
            switch(r0) {
                case 11: goto L_0x0243;
                case 12: goto L_0x0232;
                case 13: goto L_0x0227;
                case 14: goto L_0x021e;
                case 15: goto L_0x0210;
                case 16: goto L_0x01ff;
                case 17: goto L_0x01f4;
                case 18: goto L_0x01e4;
                case 19: goto L_0x01d4;
                case 20: goto L_0x01c2;
                case 21: goto L_0x01b1;
                case 22: goto L_0x0189;
                case 23: goto L_0x0161;
                default: goto L_0x0080;
            }
        L_0x0080:
            switch(r0) {
                case 30: goto L_0x0139;
                case 31: goto L_0x0111;
                case 32: goto L_0x0100;
                default: goto L_0x0083;
            }
        L_0x0083:
            switch(r0) {
                case 46: goto L_0x00f5;
                case 47: goto L_0x00e5;
                case 48: goto L_0x00da;
                case 49: goto L_0x00b3;
                case 50: goto L_0x0088;
                default: goto L_0x0086;
            }
        L_0x0086:
            goto L_0x02ca
        L_0x0088:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x0094
            r0 = r13
        L_0x0094:
            com.igexin.push.core.C1343f.f2161aw = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last mobileRedirectCmList = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2161aw     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x00ae:
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x00b3:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x00bf
            r0 = r13
        L_0x00bf:
            com.igexin.push.core.C1343f.f2160av = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last wifiRedirectCmList = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2160av     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x00da:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x00e1
            r4 = r13
        L_0x00e1:
            com.igexin.push.core.C1343f.f2189z = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x00e5:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x00ed
            r9 = 0
            goto L_0x00f1
        L_0x00ed:
            int r9 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x00f1:
            com.igexin.push.core.C1343f.f2164az = r9     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x00f5:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x00fc
            r4 = r13
        L_0x00fc:
            com.igexin.push.core.C1343f.f2188y = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0100:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0109
            r16 = r11
            goto L_0x010d
        L_0x0109:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x010d:
            com.igexin.push.core.C1343f.f2119K = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0111:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x011d
            r0 = r13
        L_0x011d:
            com.igexin.push.core.C1343f.f2158at = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last domainMobileStatus = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2158at     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x0139:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x0145
            r0 = r13
        L_0x0145:
            com.igexin.push.core.C1343f.f2159au = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last domainWifiStatus = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2159au     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x0161:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x016d
            r0 = r13
        L_0x016d:
            com.igexin.push.core.C1343f.f2156ar = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last mobile result = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2156ar     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x0189:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x0195
            r0 = r13
        L_0x0195:
            com.igexin.push.core.C1343f.f2157as = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = "|read last wifi result = "
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r4 = com.igexin.push.core.C1343f.f2157as     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.append(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x01b1:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x01ba
            r16 = r11
            goto L_0x01be
        L_0x01ba:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x01be:
            com.igexin.push.core.C1343f.f2155aq = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x01c2:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x01ce
            r0 = r13
        L_0x01ce:
            com.igexin.push.core.C1343f.f2183t = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            com.igexin.push.core.C1343f.f2182s = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x01d4:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r4 == 0) goto L_0x01e0
            r0 = r13
        L_0x01e0:
            com.igexin.push.core.C1343f.f2186w = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x01e4:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x01ec
            r9 = 0
            goto L_0x01f0
        L_0x01ec:
            int r9 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x01f0:
            com.igexin.push.core.C1343f.f2126R = r9     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x01f4:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x01fb
            r4 = r13
        L_0x01fb:
            com.igexin.push.core.C1343f.f2124P = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x01ff:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0208
            r16 = r11
            goto L_0x020c
        L_0x0208:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x020c:
            com.igexin.push.core.C1343f.f2122N = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0210:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 != 0) goto L_0x02ca
            boolean r0 = java.lang.Boolean.parseBoolean(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            com.igexin.push.core.C1343f.f2121M = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x021e:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            com.igexin.push.core.C1343f.f2153ao = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0227:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x022e
            r4 = r13
        L_0x022e:
            com.igexin.push.core.C1343f.f2120L = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0232:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x023b
            r16 = r11
            goto L_0x023f
        L_0x023b:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x023f:
            com.igexin.push.core.C1343f.f2118J = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0243:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x024c
            r16 = r11
            goto L_0x0250
        L_0x024c:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x0250:
            com.igexin.push.core.C1343f.f2117I = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x0254:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 != 0) goto L_0x0262
            boolean r0 = java.lang.Boolean.parseBoolean(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0262
            r0 = 1
            goto L_0x0263
        L_0x0262:
            r0 = 0
        L_0x0263:
            com.igexin.push.e.b r4 = com.igexin.push.p054e.C1368b.m2191a()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r4.mo14817a((boolean) r0)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x026c:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0275
            r16 = r11
            goto L_0x0279
        L_0x0275:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x0279:
            com.igexin.push.core.C1343f.f2115G = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x027c:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0285
            r16 = r11
            goto L_0x0289
        L_0x0285:
            long r16 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x0289:
            com.igexin.push.core.C1343f.f2114F = r16     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x028c:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 != 0) goto L_0x029b
            boolean r0 = java.lang.Boolean.parseBoolean(r4)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x0299
            goto L_0x029b
        L_0x0299:
            r0 = 0
            goto L_0x029c
        L_0x029b:
            r0 = 1
        L_0x029c:
            com.igexin.push.core.C1343f.f2174k = r0     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x029f:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x02a6
            r4 = r13
        L_0x02a6:
            com.igexin.push.core.C1343f.f2109A = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x02a9:
            boolean r0 = r4.equals(r3)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            if (r0 == 0) goto L_0x02b0
            r4 = r13
        L_0x02b0:
            com.igexin.push.core.C1343f.f2187x = r4     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x02ca
        L_0x02b3:
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r0.<init>(r15)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            boolean r4 = r0.equals(r3)     // Catch:{ Exception -> 0x02c8, all -> 0x02ec }
            if (r4 == 0) goto L_0x02c1
            r16 = r11
            goto L_0x02c5
        L_0x02c1:
            long r16 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x02c8, all -> 0x02ec }
        L_0x02c5:
            com.igexin.push.core.C1343f.f2181r = r16     // Catch:{ Exception -> 0x02c8, all -> 0x02ec }
            goto L_0x02ca
        L_0x02c8:
            com.igexin.push.core.C1343f.f2181r = r11     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
        L_0x02ca:
            r4 = 25
            r9 = 0
            goto L_0x001e
        L_0x02cf:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r4.<init>()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r9 = f2047a     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r4.append(r9)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r9 = "|"
            r4.append(r9)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            r4.append(r0)     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            java.lang.String r0 = r4.toString()     // Catch:{ Exception -> 0x02ee, all -> 0x02ec }
            goto L_0x00ae
        L_0x02ec:
            r0 = move-exception
            goto L_0x02f5
        L_0x02ee:
            goto L_0x02fc
        L_0x02f0:
            if (r14 == 0) goto L_0x0301
            goto L_0x02fe
        L_0x02f3:
            r0 = move-exception
            r14 = r13
        L_0x02f5:
            if (r14 == 0) goto L_0x02fa
            r14.close()
        L_0x02fa:
            throw r0
        L_0x02fb:
            r14 = r13
        L_0x02fc:
            if (r14 == 0) goto L_0x0301
        L_0x02fe:
            r14.close()
        L_0x0301:
            long r14 = com.igexin.push.core.C1343f.f2181r
            int r0 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0320
            long r14 = com.igexin.push.util.C1581f.m3240d()
            int r0 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0320
            com.igexin.push.core.C1343f.f2181r = r14
            java.lang.String r0 = java.lang.String.valueOf(r14)
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r10, (byte[]) r0)
        L_0x0320:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            if (r0 != 0) goto L_0x033b
            java.lang.String r0 = com.igexin.push.util.C1581f.m3235b()
            if (r0 == 0) goto L_0x033b
            com.igexin.push.core.C1343f.f2183t = r0
            com.igexin.push.core.C1343f.f2182s = r0
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r8, (byte[]) r0)
        L_0x033b:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            if (r0 != 0) goto L_0x0363
            long r14 = com.igexin.push.core.C1343f.f2181r
            int r0 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0363
            long r14 = com.igexin.push.core.C1343f.f2181r
            java.lang.String r0 = java.lang.String.valueOf(r14)
            java.lang.String r0 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r0)
            com.igexin.push.core.C1343f.f2183t = r0
            long r14 = com.igexin.push.core.C1343f.f2181r
            com.igexin.push.core.C1343f.m2076a((long) r14)
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r8, (byte[]) r0)
        L_0x0363:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            java.lang.String r4 = "cfcd208495d565ef66e7dff9f98764da"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L_0x037b
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            if (r0 == 0) goto L_0x0398
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            java.lang.String r4 = "([a-f]|[0-9]){32}"
            boolean r0 = r0.matches(r4)
            if (r0 != 0) goto L_0x0398
        L_0x037b:
            long r14 = com.igexin.push.core.C1343f.f2181r
            int r0 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0392
            com.igexin.push.core.c.h r0 = m1937a()
            long r13 = com.igexin.push.core.C1343f.f2181r
            r0.mo14673b((long) r13)
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            com.igexin.push.core.C1343f.f2183t = r0
            com.igexin.push.util.C1581f.m3242f()
            goto L_0x0398
        L_0x0392:
            com.igexin.push.core.C1343f.f2183t = r13
            com.igexin.push.core.C1343f.f2182s = r3
            com.igexin.push.core.C1343f.f2181r = r11
        L_0x0398:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2153ao
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x03a8
            java.lang.String r0 = com.igexin.push.core.C1343f.f2153ao
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x03bd
        L_0x03a8:
            r0 = 32
            java.lang.String r0 = com.igexin.p032b.p042b.C1196a.m1434a((int) r0)
            com.igexin.push.core.C1343f.f2153ao = r0
            java.lang.String r0 = com.igexin.push.core.C1343f.f2153ao
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r5, (byte[]) r0)
        L_0x03bd:
            java.lang.String r0 = com.igexin.push.util.C1581f.m3239c()
            java.lang.String r3 = com.igexin.push.core.C1343f.f2187x
            r4 = 5
            if (r3 != 0) goto L_0x03d5
            if (r0 == 0) goto L_0x03d5
            int r3 = r0.length()
            if (r3 <= r4) goto L_0x03d5
            com.igexin.push.core.C1343f.f2187x = r0
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r6, (java.lang.String) r0)
        L_0x03d5:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2109A
            if (r0 != 0) goto L_0x03f9
            r18.m1947f()
            java.lang.String r0 = com.igexin.push.core.C1343f.f2109A
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r7, (java.lang.String) r0)
            java.lang.String r0 = f2047a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "new registerId : "
            r3.append(r5)
            java.lang.String r5 = com.igexin.push.core.C1343f.f2109A
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1355a(r0, r3)
        L_0x03f9:
            boolean r0 = com.igexin.push.config.C1234k.f1837S
            r3 = 46
            if (r0 == 0) goto L_0x041f
            com.igexin.push.core.c.c r0 = new com.igexin.push.core.c.c
            android.content.Context r5 = com.igexin.push.core.C1343f.f2169f
            r0.<init>(r5)
            java.lang.String r0 = r0.mo14659e()
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 != 0) goto L_0x041f
            java.lang.String r5 = com.igexin.push.core.C1343f.f2188y
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L_0x041f
            com.igexin.push.core.C1343f.f2188y = r0
            java.lang.String r0 = com.igexin.push.core.C1343f.f2188y
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r3, (java.lang.String) r0)
        L_0x041f:
            boolean r0 = r1.f2050d
            if (r0 == 0) goto L_0x04b1
            r5 = 0
            r1.f2050d = r5
            java.lang.String r0 = com.igexin.push.core.C1343f.f2110B
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0447
            java.lang.String r0 = com.igexin.push.core.C1343f.f2110B
            byte[] r0 = r0.getBytes()
            android.content.Context r5 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r5 = r5.getPackageName()
            java.lang.String r5 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r5)
            byte[] r0 = com.igexin.p032b.p033a.p034a.C1150a.m1235d(r0, r5)
            r5 = 25
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r5, (byte[]) r0)
        L_0x0447:
            long r13 = com.igexin.push.core.C1343f.f2181r
            int r0 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x045e
            long r11 = com.igexin.push.core.C1343f.f2181r
            java.lang.String r0 = java.lang.String.valueOf(r11)
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r10, (byte[]) r0)
        L_0x045e:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0473
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            byte[] r0 = r0.getBytes()
            byte[] r0 = com.igexin.push.util.EncryptUtils.getBytesEncrypted(r0)
            r1.m1939a((android.database.sqlite.SQLiteDatabase) r2, (int) r8, (byte[]) r0)
        L_0x0473:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0488
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x
            int r0 = r0.length()
            if (r0 <= r4) goto L_0x0488
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r6, (java.lang.String) r0)
        L_0x0488:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2109A
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0495
            java.lang.String r0 = com.igexin.push.core.C1343f.f2109A
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r7, (java.lang.String) r0)
        L_0x0495:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2188y
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x04a2
            java.lang.String r0 = com.igexin.push.core.C1343f.f2188y
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r3, (java.lang.String) r0)
        L_0x04a2:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2189z
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x04b1
            r0 = 48
            java.lang.String r3 = com.igexin.push.core.C1343f.f2189z
            r1.m1938a((android.database.sqlite.SQLiteDatabase) r2, (int) r0, (java.lang.String) r3)
        L_0x04b1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1312h.mo14445b(android.database.sqlite.SQLiteDatabase):void");
    }

    /* renamed from: b */
    public boolean mo14672b(int i) {
        if (C1343f.f2164az == i) {
            return false;
        }
        C1343f.f2164az = i;
        return C1174c.m1310b().mo14317a(new C1329y(this), false, true);
    }

    /* renamed from: b */
    public boolean mo14673b(long j) {
        C1343f.m2076a(j);
        return C1174c.m1310b().mo14317a(new C1300ae(this), false, true);
    }

    /* renamed from: b */
    public boolean mo14674b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        C1343f.f2186w = str;
        return C1174c.m1310b().mo14317a(new C1299ad(this), false, true);
    }

    /* renamed from: b */
    public boolean mo14675b(String str, boolean z) {
        C1174c b;
        C1190e vVar;
        if (str == null) {
            return false;
        }
        String str2 = null;
        if (z) {
            if (!str.equals(C1343f.f2156ar)) {
                if (!str.equals("null")) {
                    str2 = str;
                }
                C1343f.f2156ar = str2;
                b = C1174c.m1310b();
                vVar = new C1325u(this, str);
            }
            return false;
        }
        if (!str.equals(C1343f.f2157as)) {
            if (!str.equals("null")) {
                str2 = str;
            }
            C1343f.f2157as = str2;
            b = C1174c.m1310b();
            vVar = new C1326v(this, str);
        }
        return false;
        return b.mo14317a(vVar, false, true);
    }

    /* renamed from: b */
    public boolean mo14676b(boolean z) {
        return C1174c.m1310b().mo14317a(new C1330z(this, z), false, true);
    }

    /* renamed from: c */
    public void mo14448c(SQLiteDatabase sQLiteDatabase) {
        m1939a(sQLiteDatabase, 1, C1150a.m1235d(String.valueOf(C1343f.f2181r).getBytes(), C1343f.f2110B));
        m1938a(sQLiteDatabase, 4, String.valueOf(C1343f.f2174k));
        m1938a(sQLiteDatabase, 8, String.valueOf(C1343f.f2115G));
        m1938a(sQLiteDatabase, 32, String.valueOf(C1343f.f2119K));
        m1938a(sQLiteDatabase, 3, C1343f.f2109A);
        m1938a(sQLiteDatabase, 11, String.valueOf(C1343f.f2117I));
        m1938a(sQLiteDatabase, 12, String.valueOf(C1343f.f2118J));
        m1939a(sQLiteDatabase, 20, C1150a.m1235d(C1343f.f2182s.getBytes(), C1343f.f2110B));
        m1938a(sQLiteDatabase, 2, C1343f.f2187x);
        m1939a(sQLiteDatabase, 25, C1150a.m1235d(C1343f.f2110B.getBytes(), C1196a.m1435a(C1343f.f2169f.getPackageName())));
    }

    /* renamed from: c */
    public boolean mo14677c() {
        C1343f.f2181r = 0;
        C1343f.f2182s = "null";
        return m1950g();
    }

    /* renamed from: c */
    public boolean mo14678c(long j) {
        if (C1343f.f2118J == j) {
            return false;
        }
        C1343f.f2118J = j;
        C1174c.m1310b().mo14317a(new C1314j(this), false, true);
        return true;
    }

    /* renamed from: c */
    public boolean mo14679c(String str) {
        C1343f.f2187x = str;
        return C1174c.m1310b().mo14317a(new C1301af(this), false, true);
    }

    /* renamed from: c */
    public boolean mo14680c(String str, boolean z) {
        if (str == null) {
            return false;
        }
        String str2 = str.equals("null") ? null : str;
        if (z && !TextUtils.equals(C1343f.f2161aw, str)) {
            C1343f.f2161aw = str2;
        } else if (z || TextUtils.equals(C1343f.f2160av, str)) {
            return false;
        } else {
            C1343f.f2160av = str2;
        }
        C1179b.m1354a(f2047a + "|saveLastRedirectCmList isMobile = " + z + ", lastRedirectCmList = " + str);
        return C1174c.m1310b().mo14317a(new C1296aa(this, z, str), false, true);
    }

    /* renamed from: d */
    public Map<String, String> mo14681d() {
        return this.f2049c;
    }

    /* renamed from: d */
    public void mo14682d(SQLiteDatabase sQLiteDatabase) {
        this.f2050d = true;
        m1946e(sQLiteDatabase);
        m1956j(sQLiteDatabase);
        m1957k(sQLiteDatabase);
        m1955i(sQLiteDatabase);
        m1948f(sQLiteDatabase);
        m1949g(sQLiteDatabase);
        m1952h(sQLiteDatabase);
    }

    /* renamed from: d */
    public boolean mo14683d(long j) {
        C1343f.f2155aq = j;
        C1179b.m1354a(f2047a + "|save idc config failed time : " + j);
        return C1174c.m1310b().mo14317a(new C1315k(this, j), false, true);
    }

    /* renamed from: d */
    public boolean mo14684d(String str) {
        C1343f.f2188y = str;
        return C1174c.m1310b().mo14317a(new C1302ag(this), false, true);
    }

    /* renamed from: e */
    public void mo14685e() {
        C1179b.m1354a(f2047a + "| found a duplicate cid " + C1343f.f2182s);
        C1343f.f2109A = null;
        m1947f();
        m1937a().mo14667a(C1343f.f2109A);
        m1937a().mo14677c();
        C1343f.f2178o = 0;
        C1574g.m3187i().mo15212a(SystemClock.elapsedRealtime());
    }

    /* renamed from: e */
    public boolean mo14686e(long j) {
        if (C1343f.f2117I == j) {
            return false;
        }
        C1343f.f2117I = j;
        return C1174c.m1310b().mo14317a(new C1316l(this), false, true);
    }

    /* renamed from: e */
    public boolean mo14687e(String str) {
        C1343f.f2189z = str;
        return C1174c.m1310b().mo14317a(new C1303ah(this), false, true);
    }

    /* renamed from: f */
    public boolean mo14688f(long j) {
        if (C1343f.f2122N == j) {
            return false;
        }
        C1343f.f2122N = j;
        return C1174c.m1310b().mo14317a(new C1319o(this), false, true);
    }

    /* renamed from: f */
    public boolean mo14689f(String str) {
        if (str == null || str.equals(C1343f.f2120L)) {
            return false;
        }
        C1343f.f2120L = str;
        C1174c.m1310b().mo14317a(new C1317m(this), false, true);
        return true;
    }

    /* renamed from: g */
    public boolean mo14690g(long j) {
        if (C1343f.f2119K == j) {
            return false;
        }
        C1343f.f2119K = j;
        return C1174c.m1310b().mo14317a(new C1327w(this), false, true);
    }

    /* renamed from: g */
    public boolean mo14691g(String str) {
        if (str.equals(C1343f.f2124P)) {
            return false;
        }
        C1343f.f2124P = str;
        return C1174c.m1310b().mo14317a(new C1320p(this), false, true);
    }

    /* renamed from: h */
    public boolean mo14692h(long j) {
        if (C1343f.f2114F == j) {
            return false;
        }
        C1343f.f2114F = j;
        return C1174c.m1310b().mo14317a(new C1328x(this), false, true);
    }
}
