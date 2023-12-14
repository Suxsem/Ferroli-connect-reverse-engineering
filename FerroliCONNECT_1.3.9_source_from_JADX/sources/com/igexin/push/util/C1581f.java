package com.igexin.push.util;

import android.content.Context;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.igexin.sdk.p091a.C1599b;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.igexin.push.util.f */
public class C1581f {

    /* renamed from: a */
    private static final Object f3013a = new Object();

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c0 A[SYNTHETIC, Splitter:B:25:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c6 A[SYNTHETIC, Splitter:B:29:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m3230a() {
        /*
            java.lang.String r0 = "|"
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f
            boolean r1 = com.igexin.push.util.C1593r.m3267a(r1)
            if (r1 != 0) goto L_0x000b
            return
        L_0x000b:
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2128T     // Catch:{ Exception -> 0x00a5 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00a5 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00a5 }
            if (r3 != 0) goto L_0x003d
            boolean r3 = r2.createNewFile()     // Catch:{ Exception -> 0x00a5 }
            if (r3 != 0) goto L_0x003d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a5 }
            r0.<init>()     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r3 = "FileUtils | create file : "
            r0.append(r3)     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a5 }
            r0.append(r2)     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r2 = " failed !!!"
            r0.append(r2)     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a5 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x00a5 }
            return
        L_0x003d:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2128T     // Catch:{ Exception -> 0x00a5 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00a5 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r1.<init>()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r3 = "v01"
            r1.append(r3)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2186w     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r1.append(r3)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.<init>()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            long r4 = com.igexin.push.core.C1343f.f2181r     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r1 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r1 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r1 = com.igexin.push.core.C1343f.f2182s     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            com.igexin.push.core.s r0 = com.igexin.push.core.C1356s.m2138a()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r0 = r0.mo14789e(r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r3.append(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            byte[] r0 = r0.getBytes()     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            java.lang.String r1 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            byte[] r0 = com.igexin.p032b.p033a.p034a.C1150a.m1235d(r0, r1)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r2.write(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009d }
            r2.close()     // Catch:{ IOException -> 0x00c3 }
            goto L_0x00c3
        L_0x009d:
            r0 = move-exception
            goto L_0x00c4
        L_0x009f:
            r0 = move-exception
            r1 = r2
            goto L_0x00a6
        L_0x00a2:
            r0 = move-exception
            r2 = r1
            goto L_0x00c4
        L_0x00a5:
            r0 = move-exception
        L_0x00a6:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            r2.<init>()     // Catch:{ all -> 0x00a2 }
            java.lang.String r3 = "FileUtils | "
            r2.append(r3)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00a2 }
            r2.append(r0)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x00a2 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x00a2 }
            if (r1 == 0) goto L_0x00c3
            r1.close()     // Catch:{ IOException -> 0x00c3 }
        L_0x00c3:
            return
        L_0x00c4:
            if (r2 == 0) goto L_0x00c9
            r2.close()     // Catch:{ IOException -> 0x00c9 }
        L_0x00c9:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3230a():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001a A[SYNTHETIC, Splitter:B:13:0x001a] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0021 A[SYNTHETIC, Splitter:B:19:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m3232a(byte[] r3, java.lang.String r4, boolean r5) {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x001e, all -> 0x0017 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x001e, all -> 0x0017 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x001e, all -> 0x0017 }
            r1.<init>(r2, r5)     // Catch:{ Exception -> 0x001e, all -> 0x0017 }
            r1.write(r3)     // Catch:{ Exception -> 0x0015, all -> 0x0012 }
            r1.close()     // Catch:{ Exception -> 0x0024 }
            goto L_0x0024
        L_0x0012:
            r3 = move-exception
            r0 = r1
            goto L_0x0018
        L_0x0015:
            r0 = r1
            goto L_0x001f
        L_0x0017:
            r3 = move-exception
        L_0x0018:
            if (r0 == 0) goto L_0x001d
            r0.close()     // Catch:{ Exception -> 0x001d }
        L_0x001d:
            throw r3
        L_0x001e:
        L_0x001f:
            if (r0 == 0) goto L_0x0024
            r0.close()     // Catch:{ Exception -> 0x0024 }
        L_0x0024:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3232a(byte[], java.lang.String, boolean):void");
    }

    /* renamed from: a */
    public static boolean m3233a(Context context) {
        return !new C1599b(context).mo15292b();
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
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m3234a(java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 != 0) goto L_0x0026
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "FileUtils|get data from file = "
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
            java.lang.String r4 = "FileUtils|"
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
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3234a(java.lang.String):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
        if (r0.equals("null") != false) goto L_0x003b;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3235b() {
        /*
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            boolean r0 = com.igexin.push.util.C1593r.m3267a(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2128T     // Catch:{ Exception -> 0x003b }
            byte[] r0 = m3234a((java.lang.String) r0)     // Catch:{ Exception -> 0x003b }
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = "FileUtils | read file cid id = null"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x003b }
            return r1
        L_0x0018:
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x003b }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Exception -> 0x003b }
            byte[] r0 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r0, r3)     // Catch:{ Exception -> 0x003b }
            r2.<init>(r0)     // Catch:{ Exception -> 0x003b }
            java.lang.String r0 = "\\|"
            java.lang.String[] r0 = r2.split(r0)     // Catch:{ Exception -> 0x003b }
            int r2 = r0.length     // Catch:{ Exception -> 0x003b }
            r3 = 2
            if (r2 <= r3) goto L_0x003b
            r0 = r0[r3]     // Catch:{ Exception -> 0x003b }
            if (r0 == 0) goto L_0x003a
            java.lang.String r2 = "null"
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x003a }
            if (r2 == 0) goto L_0x003a
            goto L_0x003b
        L_0x003a:
            r1 = r0
        L_0x003b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "FileUtils|get cid from file cid = "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3235b():java.lang.String");
    }

    /* renamed from: b */
    private static String m3236b(String str) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()) + "|" + C1343f.f2182s + "|" + C1343f.f2135a + "|" + "1" + "|" + str;
    }

    /* renamed from: b */
    public static void m3237b(Context context) {
        String str;
        File file = new File(context.getFilesDir().getPath() + "/" + "init_c.pid");
        if (file.exists()) {
            file.delete();
        }
        if (!C1234k.f1860u) {
            str = "FileUtils|isReportInitialize = false";
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - C1343f.f2162ax < 1000) {
                str = "FileUtils|not allowed to save initialization twice within 1s";
            } else {
                C1343f.f2162ax = currentTimeMillis;
                if (C1343f.f2170g.get()) {
                    C1174c.m1310b().mo14317a(new C1582g(context, currentTimeMillis), false, true);
                    return;
                } else {
                    new Thread(new C1583h(context, currentTimeMillis)).start();
                    return;
                }
            }
        }
        C1179b.m1354a(str);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m3238b(Context context, String str) {
        if (context != null && str != null) {
            String str2 = context.getFilesDir().getPath() + "/" + "init_c1.pid";
            synchronized (f3013a) {
                if (str.length() == 0) {
                    m3232a(str.getBytes(), str2, false);
                } else {
                    m3232a((str + "|").getBytes(), str2, true);
                }
            }
        }
    }

    /* renamed from: c */
    public static String m3239c() {
        String str;
        if (!C1593r.m3267a(C1343f.f2169f)) {
            return null;
        }
        try {
            C1179b.m1354a("FileUtils|get device id from file : " + C1343f.f2129U);
            byte[] a = m3234a(C1343f.f2129U);
            if (a == null) {
                C1179b.m1354a("FileUtils|read file device id = null");
                return null;
            }
            str = new String(a, "utf-8");
            try {
                C1179b.m1354a("FileUtils|read file device id = " + str);
            } catch (Exception e) {
                e = e;
            }
            return str;
        } catch (Exception e2) {
            e = e2;
            str = null;
            C1179b.m1354a("FileUtils|get device id from file : " + e.toString());
            return str;
        }
    }

    /* renamed from: d */
    public static long m3240d() {
        long j = 0;
        if (!C1593r.m3267a(C1343f.f2169f)) {
            return 0;
        }
        try {
            byte[] a = m3234a(C1343f.f2128T);
            if (a == null) {
                C1179b.m1354a("FileUtils|read session from file, not exist");
                return 0;
            }
            String str = new String(C1150a.m1234c(a, C1343f.f2110B));
            String substring = str.contains("null") ? str.substring(7) : str.substring(20);
            int indexOf = substring.indexOf("|");
            if (indexOf >= 0) {
                substring = substring.substring(0, indexOf);
            }
            long parseLong = Long.parseLong(substring);
            if (parseLong != 0) {
                j = parseLong;
            }
            C1179b.m1354a("FileUtils|session : " + j);
            return j;
        } catch (Exception e) {
            C1179b.m1354a("FileUtils|" + e.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0086, code lost:
        if (r0 != null) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        if (r0 != null) goto L_0x0088;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b0 A[SYNTHETIC, Splitter:B:38:0x00b0] */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m3241e() {
        /*
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            boolean r0 = com.igexin.push.util.C1593r.m3267a(r0)
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x
            if (r0 != 0) goto L_0x000e
            return
        L_0x000e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "FileUtils|save device id to file : "
            r0.append(r1)
            java.lang.String r1 = com.igexin.push.core.C1343f.f2129U
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            r0 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r1.<init>()
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            boolean r2 = r1.tryLock()     // Catch:{ Exception -> 0x0091 }
            if (r2 == 0) goto L_0x0086
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0091 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2129U     // Catch:{ Exception -> 0x0091 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0091 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x0091 }
            if (r3 != 0) goto L_0x0068
            boolean r3 = r2.createNewFile()     // Catch:{ Exception -> 0x0091 }
            if (r3 != 0) goto L_0x0068
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0091 }
            r3.<init>()     // Catch:{ Exception -> 0x0091 }
            java.lang.String r4 = "FileUtils|create file : "
            r3.append(r4)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0091 }
            r3.append(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = " failed !!!"
            r3.append(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x0091 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r2)     // Catch:{ Exception -> 0x0091 }
            r1.unlock()
            return
        L_0x0068:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0091 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2129U     // Catch:{ Exception -> 0x0091 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r0 = com.igexin.push.core.C1343f.f2187x     // Catch:{ Exception -> 0x0081, all -> 0x007c }
            java.lang.String r3 = "utf-8"
            byte[] r0 = r0.getBytes(r3)     // Catch:{ Exception -> 0x0081, all -> 0x007c }
            r2.write(r0)     // Catch:{ Exception -> 0x0081, all -> 0x007c }
            r0 = r2
            goto L_0x0086
        L_0x007c:
            r0 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x00ae
        L_0x0081:
            r0 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0092
        L_0x0086:
            if (r0 == 0) goto L_0x008b
        L_0x0088:
            r0.close()     // Catch:{ IOException -> 0x008b }
        L_0x008b:
            r1.unlock()
            goto L_0x00ad
        L_0x008f:
            r2 = move-exception
            goto L_0x00ae
        L_0x0091:
            r2 = move-exception
        L_0x0092:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x008f }
            r3.<init>()     // Catch:{ all -> 0x008f }
            java.lang.String r4 = "FileUtils|"
            r3.append(r4)     // Catch:{ all -> 0x008f }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x008f }
            r3.append(r2)     // Catch:{ all -> 0x008f }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x008f }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r2)     // Catch:{ all -> 0x008f }
            if (r0 == 0) goto L_0x008b
            goto L_0x0088
        L_0x00ad:
            return
        L_0x00ae:
            if (r0 == 0) goto L_0x00b3
            r0.close()     // Catch:{ IOException -> 0x00b3 }
        L_0x00b3:
            r1.unlock()
            goto L_0x00b8
        L_0x00b7:
            throw r2
        L_0x00b8:
            goto L_0x00b7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3241e():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0078 A[SYNTHETIC, Splitter:B:26:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* renamed from: f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m3242f() {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f
            java.io.File r1 = r1.getFilesDir()
            java.lang.String r1 = r1.getPath()
            r0.append(r1)
            java.lang.String r1 = "/init.pid"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0059 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0059 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0059 }
            if (r2 == 0) goto L_0x0051
            java.lang.String r2 = com.igexin.push.core.C1343f.f2182s     // Catch:{ Exception -> 0x0059 }
            byte[] r2 = r2.getBytes()     // Catch:{ Exception -> 0x0059 }
            int r3 = r2.length     // Catch:{ Exception -> 0x0059 }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0059 }
            r4 = 0
        L_0x0031:
            int r5 = r2.length     // Catch:{ Exception -> 0x0059 }
            if (r4 >= r5) goto L_0x0041
            byte r5 = r2[r4]     // Catch:{ Exception -> 0x0059 }
            byte[] r6 = com.igexin.push.core.C1343f.f2134Z     // Catch:{ Exception -> 0x0059 }
            byte r6 = r6[r4]     // Catch:{ Exception -> 0x0059 }
            r5 = r5 ^ r6
            byte r5 = (byte) r5     // Catch:{ Exception -> 0x0059 }
            r3[r4] = r5     // Catch:{ Exception -> 0x0059 }
            int r4 = r4 + 1
            goto L_0x0031
        L_0x0041:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0059 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0059 }
            r2.write(r3)     // Catch:{ Exception -> 0x004e, all -> 0x004b }
            r1 = r2
            goto L_0x0051
        L_0x004b:
            r0 = move-exception
            r1 = r2
            goto L_0x0076
        L_0x004e:
            r0 = move-exception
            r1 = r2
            goto L_0x005a
        L_0x0051:
            if (r1 == 0) goto L_0x0075
        L_0x0053:
            r1.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x0075
        L_0x0057:
            r0 = move-exception
            goto L_0x0076
        L_0x0059:
            r0 = move-exception
        L_0x005a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            r2.<init>()     // Catch:{ all -> 0x0057 }
            java.lang.String r3 = "FileUtils|"
            r2.append(r3)     // Catch:{ all -> 0x0057 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0057 }
            r2.append(r0)     // Catch:{ all -> 0x0057 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x0057 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0075
            goto L_0x0053
        L_0x0075:
            return
        L_0x0076:
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch:{ Exception -> 0x007b }
        L_0x007b:
            goto L_0x007d
        L_0x007c:
            throw r0
        L_0x007d:
            goto L_0x007c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1581f.m3242f():void");
    }

    /* renamed from: g */
    public static void m3243g() {
        m3238b(C1343f.f2169f, "");
    }

    /* renamed from: h */
    public static String m3244h() {
        byte[] a;
        String str = C1343f.f2169f.getFilesDir().getPath() + "/" + "init_c1.pid";
        try {
            synchronized (f3013a) {
                a = m3234a(str);
            }
            if (a == null) {
                return null;
            }
            String str2 = new String(a);
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            if (str2.endsWith("|")) {
                str2 = str2.substring(0, str2.length() - 1);
            }
            String[] split = str2.split("\\|");
            if (split.length <= 300 || System.currentTimeMillis() - Long.parseLong(split[0]) <= 604800000) {
                StringBuilder sb = new StringBuilder();
                for (String parseLong : split) {
                    sb.append(m3236b(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(Long.parseLong(parseLong)))));
                    sb.append("\n");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                return sb.toString();
            }
            m3243g();
            return null;
        } catch (Throwable th) {
            C1179b.m1354a("FileUtils|upload init data error = " + th.toString());
            m3243g();
            return null;
        }
    }
}
