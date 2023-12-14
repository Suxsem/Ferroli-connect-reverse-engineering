package com.igexin.push.extension.distribution.basic.p061c;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.basic.p062d.C1419c;
import com.igexin.push.extension.distribution.basic.p063e.C1420a;
import java.io.File;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.extension.distribution.basic.c.c */
public class C1413c {

    /* renamed from: a */
    private static final String f2414a = ("EXT-" + C1413c.class.getName());

    /* renamed from: d */
    private static C1413c f2415d;

    /* renamed from: b */
    private C1420a f2416b;

    /* renamed from: c */
    private Context f2417c;

    /* renamed from: e */
    private String f2418e = "/sdcard/libs//com.getui.sdk.deviceId.db";

    private C1413c(Context context) {
        this.f2417c = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.igexin.sdk.action.notification.burying.point");
        this.f2417c.registerReceiver(C1415e.m2422a(), intentFilter);
    }

    /* renamed from: a */
    public static C1413c m2411a() {
        if (f2415d == null) {
            f2415d = new C1413c(C1416f.f2423a);
        }
        return f2415d;
    }

    /* renamed from: a */
    private void m2412a(File file) {
        for (File file2 : file.listFiles()) {
            while (file2.exists()) {
                if (file2.isFile()) {
                    file2.delete();
                } else if (!file2.delete()) {
                    m2412a(file2);
                }
            }
        }
        file.delete();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0099, code lost:
        if (r1 != null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c2, code lost:
        if (r1 != null) goto L_0x009b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c8 A[SYNTHETIC, Splitter:B:32:0x00c8] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2413b(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = f2414a
            r0.append(r1)
            java.lang.String r1 = "|save deviceId = "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r1 = " to "
            r0.append(r1)
            java.lang.String r1 = r4.f2418e
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r0.<init>()
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r1 = 0
            boolean r2 = r0.tryLock()     // Catch:{ Exception -> 0x00a4 }
            if (r2 == 0) goto L_0x0099
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r3 = r4.f2418e     // Catch:{ Exception -> 0x00a4 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00a4 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00a4 }
            if (r3 != 0) goto L_0x006c
            boolean r3 = r2.createNewFile()     // Catch:{ Exception -> 0x00a4 }
            if (r3 != 0) goto L_0x006c
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a4 }
            r5.<init>()     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r3 = f2414a     // Catch:{ Exception -> 0x00a4 }
            r5.append(r3)     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r3 = "|create file "
            r5.append(r3)     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00a4 }
            r5.append(r2)     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r2 = " failed"
            r5.append(r2)     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00a4 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Exception -> 0x00a4 }
            r0.unlock()
            return
        L_0x006c:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a4 }
            java.lang.String r3 = r4.f2418e     // Catch:{ Exception -> 0x00a4 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00a4 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            r1.<init>()     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            java.lang.String r3 = "V1|"
            r1.append(r3)     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            r1.append(r5)     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            java.lang.String r1 = "utf-8"
            byte[] r5 = r5.getBytes(r1)     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            byte[] r5 = com.igexin.p032b.p042b.C1196a.m1438b(r5)     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            r2.write(r5)     // Catch:{ Exception -> 0x0096, all -> 0x0093 }
            r1 = r2
            goto L_0x0099
        L_0x0093:
            r5 = move-exception
            r1 = r2
            goto L_0x00c6
        L_0x0096:
            r5 = move-exception
            r1 = r2
            goto L_0x00a5
        L_0x0099:
            if (r1 == 0) goto L_0x009e
        L_0x009b:
            r1.close()     // Catch:{ Exception -> 0x009e }
        L_0x009e:
            r0.unlock()
            goto L_0x00c5
        L_0x00a2:
            r5 = move-exception
            goto L_0x00c6
        L_0x00a4:
            r5 = move-exception
        L_0x00a5:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            r2.<init>()     // Catch:{ all -> 0x00a2 }
            java.lang.String r3 = f2414a     // Catch:{ all -> 0x00a2 }
            r2.append(r3)     // Catch:{ all -> 0x00a2 }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ all -> 0x00a2 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00a2 }
            r2.append(r5)     // Catch:{ all -> 0x00a2 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x00a2 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ all -> 0x00a2 }
            if (r1 == 0) goto L_0x009e
            goto L_0x009b
        L_0x00c5:
            return
        L_0x00c6:
            if (r1 == 0) goto L_0x00cb
            r1.close()     // Catch:{ Exception -> 0x00cb }
        L_0x00cb:
            r0.unlock()
            goto L_0x00d0
        L_0x00cf:
            throw r5
        L_0x00d0:
            goto L_0x00cf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p061c.C1413c.m2413b(java.lang.String):void");
    }

    /* renamed from: c */
    private void m2414c(String str) {
        try {
            if (C1416f.f2441s.mo14970a()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(AgooConstants.MESSAGE_ID, 2);
                contentValues.put("value", str);
                C1416f.f2441s.mo14968a("runtime", (String) null, contentValues);
                C1416f.f2441s.close();
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0082, code lost:
        if (r3 == null) goto L_0x0085;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x005c */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A[SYNTHETIC, Splitter:B:33:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0075 A[SYNTHETIC, Splitter:B:37:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007d A[SYNTHETIC, Splitter:B:44:0x007d] */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m2415g() {
        /*
            r7 = this;
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            boolean r0 = com.igexin.push.extension.distribution.basic.p062d.C1419c.m2437a(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.io.File r0 = new java.io.File
            java.lang.String r2 = r7.f2418e
            r0.<init>(r2)
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x0085
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0079, all -> 0x0069 }
            java.lang.String r3 = r7.f2418e     // Catch:{ Exception -> 0x0079, all -> 0x0069 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0079, all -> 0x0069 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0067, all -> 0x0064 }
            r3.<init>()     // Catch:{ Exception -> 0x0067, all -> 0x0064 }
        L_0x0027:
            int r4 = r2.read(r0)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r5 = -1
            r6 = 0
            if (r4 == r5) goto L_0x0033
            r3.write(r0, r6, r4)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            goto L_0x0027
        L_0x0033:
            byte[] r0 = r3.toByteArray()     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            byte[] r0 = com.igexin.p032b.p042b.C1196a.m1439c(r0)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            java.lang.String r5 = "utf-8"
            r4.<init>(r0, r5)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            java.lang.String r0 = "\\|"
            java.lang.String[] r0 = r4.split(r0)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            int r4 = r0.length     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r5 = 1
            if (r4 <= r5) goto L_0x0059
            java.lang.String r4 = "V1"
            r6 = r0[r6]     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            if (r4 == 0) goto L_0x0059
            r0 = r0[r5]     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r1 = r0
        L_0x0059:
            r2.close()     // Catch:{ Exception -> 0x005c }
        L_0x005c:
            r3.close()     // Catch:{ Exception -> 0x0085 }
            goto L_0x0085
        L_0x0060:
            r0 = move-exception
            goto L_0x006c
        L_0x0062:
            goto L_0x007b
        L_0x0064:
            r0 = move-exception
            r3 = r1
            goto L_0x006c
        L_0x0067:
            r3 = r1
            goto L_0x007b
        L_0x0069:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x006c:
            if (r2 == 0) goto L_0x0073
            r2.close()     // Catch:{ Exception -> 0x0072 }
            goto L_0x0073
        L_0x0072:
        L_0x0073:
            if (r3 == 0) goto L_0x0078
            r3.close()     // Catch:{ Exception -> 0x0078 }
        L_0x0078:
            throw r0
        L_0x0079:
            r2 = r1
            r3 = r2
        L_0x007b:
            if (r2 == 0) goto L_0x0082
            r2.close()     // Catch:{ Exception -> 0x0081 }
            goto L_0x0082
        L_0x0081:
        L_0x0082:
            if (r3 == 0) goto L_0x0085
            goto L_0x005c
        L_0x0085:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p061c.C1413c.m2415g():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0084, code lost:
        if (r2 != null) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0086, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x008a, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008b, code lost:
        if (r2 != null) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008d, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0090, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0092, code lost:
        if (r2 != null) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0095, code lost:
        return r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x008a A[ExcHandler: all (r12v1 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r2 
      PHI: (r2v4 android.database.Cursor) = (r2v1 android.database.Cursor), (r2v6 android.database.Cursor), (r2v6 android.database.Cursor) binds: [B:1:0x000b, B:5:0x0022, B:12:0x0043] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x0022] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0080 A[EDGE_INSN: B:29:0x0080->B:16:0x0080 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028 A[Catch:{ Exception -> 0x0082, all -> 0x008a }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo14941a(java.lang.String r12) {
        /*
            r11 = this;
            java.lang.String r0 = "imageurl"
            java.lang.String r1 = ""
            com.igexin.push.extension.distribution.basic.e.a r2 = r11.mo14942b()
            r11.f2416b = r2
            r2 = 0
            com.igexin.push.extension.distribution.basic.e.a r3 = r11.f2416b     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r4 = "image"
            java.lang.String[] r5 = new java.lang.String[]{r0}     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r9 = 1
            java.lang.String[] r6 = new java.lang.String[r9]     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r10 = 0
            r6[r10] = r12     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r7 = 0
            r8 = 0
            android.database.Cursor r2 = r3.mo14960a(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            if (r2 == 0) goto L_0x0084
        L_0x0021:
            r3 = r1
        L_0x0022:
            boolean r4 = r2.moveToNext()     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            if (r4 == 0) goto L_0x0080
            java.lang.String r4 = "imagesrc"
            int r4 = r2.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            java.lang.String r3 = r2.getString(r4)     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            if (r5 == 0) goto L_0x0043
            boolean r4 = r4.canRead()     // Catch:{ Exception -> 0x0082, all -> 0x008a }
            if (r4 != 0) goto L_0x0022
        L_0x0043:
            com.igexin.push.extension.distribution.basic.e.a r3 = r11.f2416b     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r4 = "image"
            java.lang.String[] r5 = new java.lang.String[]{r0}     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String[] r6 = new java.lang.String[r9]     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r6[r10] = r12     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r3.mo14963a((java.lang.String) r4, (java.lang.String[]) r5, (java.lang.String[]) r6)     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            android.content.Context r3 = r11.f2417c     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r4 = "android.permission.WRITE_EXTERNAL_STORAGE"
            android.content.Context r5 = r11.f2417c     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            int r3 = r3.checkPermission(r4, r5)     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            if (r3 == 0) goto L_0x0021
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r3.<init>()     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            android.content.Context r4 = r11.f2417c     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.io.File r4 = r4.getCacheDir()     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            r3.append(r4)     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r4 = "/ImgCache/"
            r3.append(r4)     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            com.igexin.push.extension.distribution.basic.p061c.C1416f.f2440r = r3     // Catch:{ Exception -> 0x0091, all -> 0x008a }
            goto L_0x0021
        L_0x0080:
            r1 = r3
            goto L_0x0084
        L_0x0082:
            r1 = r3
            goto L_0x0092
        L_0x0084:
            if (r2 == 0) goto L_0x0095
        L_0x0086:
            r2.close()
            goto L_0x0095
        L_0x008a:
            r12 = move-exception
            if (r2 == 0) goto L_0x0090
            r2.close()
        L_0x0090:
            throw r12
        L_0x0091:
        L_0x0092:
            if (r2 == 0) goto L_0x0095
            goto L_0x0086
        L_0x0095:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p061c.C1413c.mo14941a(java.lang.String):java.lang.String");
    }

    /* renamed from: b */
    public C1420a mo14942b() {
        if (this.f2416b == null) {
            this.f2416b = new C1420a(this.f2417c);
        }
        return this.f2416b;
    }

    /* renamed from: c */
    public void mo14943c() {
        long currentTimeMillis = System.currentTimeMillis() - 604800000;
        Cursor cursor = null;
        try {
            cursor = mo14942b().mo14959a("image", new String[]{"taskid"}, "createtime <= " + String.valueOf(currentTimeMillis));
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String string = cursor.getString(cursor.getColumnIndexOrThrow("taskid"));
                    mo14942b().mo14963a("image", new String[]{"taskid"}, new String[]{string});
                    File file = new File(C1416f.f2440r + string);
                    if (file.exists()) {
                        m2412a(file);
                    }
                }
            }
            if (cursor == null) {
                return;
            }
        } catch (Exception unused) {
            if (cursor == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        cursor.close();
    }

    /* renamed from: d */
    public void mo14944d() {
        File file = new File(C1275b.f1898b);
        if (file.exists()) {
            for (File file2 : file.listFiles(new C1414d(this))) {
                if (file2.exists()) {
                    m2412a(file2);
                }
            }
        }
    }

    /* renamed from: e */
    public void mo14945e() {
        if (C1419c.m2437a(C1343f.f2169f)) {
            String g = m2415g();
            if (g != null) {
                if (!g.equals(C1343f.f2187x)) {
                    C1343f.f2187x = g;
                    m2414c(g);
                }
            } else if (C1343f.f2187x != null) {
                m2413b(C1343f.f2187x);
            }
        }
    }

    /* renamed from: f */
    public void mo14946f() {
        this.f2417c.unregisterReceiver(C1415e.m2422a());
    }
}
