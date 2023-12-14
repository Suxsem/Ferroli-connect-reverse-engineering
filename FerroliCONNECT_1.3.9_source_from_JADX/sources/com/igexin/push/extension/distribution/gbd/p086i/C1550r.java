package com.igexin.push.extension.distribution.gbd.p086i;

import android.content.Context;
import android.net.Uri;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.r */
public class C1550r {

    /* renamed from: a */
    public static final Uri f2960a = Uri.parse("content://com.sina.weibo.sdkProvider/query/deviceId");

    /* renamed from: b */
    private static C1550r f2961b;

    /* renamed from: c */
    private Context f2962c = C1343f.f2169f;

    private C1550r() {
    }

    /* renamed from: a */
    public static C1550r m3044a() {
        if (f2961b == null) {
            f2961b = new C1550r();
        }
        return f2961b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        if (r5 != null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0049, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0057, code lost:
        if (r5 == null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005a, code lost:
        r0 = new java.lang.StringBuilder();
        r0.append("weibo divecid = ");
        r0.append(r4);
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo15182b() {
        /*
            r12 = this;
            java.lang.String r0 = "device_id"
            android.content.Context r1 = r12.f2962c
            java.lang.String r2 = "weibo divecid = "
            java.lang.String r3 = "GBD_weibo"
            java.lang.String r4 = "none"
            if (r1 != 0) goto L_0x0024
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            r0.append(r4)
            java.lang.String r1 = "context = null"
            r0.append(r1)
        L_0x001c:
            java.lang.String r0 = r0.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r3, r0)
            return r4
        L_0x0024:
            r5 = 0
            android.content.ContentResolver r6 = r1.getContentResolver()     // Catch:{ Throwable -> 0x004f }
            android.net.Uri r7 = f2960a     // Catch:{ Throwable -> 0x004f }
            java.lang.String[] r8 = new java.lang.String[]{r0}     // Catch:{ Throwable -> 0x004f }
            r9 = 0
            r10 = 0
            r11 = 0
            android.database.Cursor r5 = r6.query(r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x004f }
        L_0x0036:
            if (r5 == 0) goto L_0x0047
            boolean r1 = r5.moveToNext()     // Catch:{ Throwable -> 0x004f }
            if (r1 == 0) goto L_0x0047
            int r1 = r5.getColumnIndex(r0)     // Catch:{ Throwable -> 0x004f }
            java.lang.String r4 = r5.getString(r1)     // Catch:{ Throwable -> 0x004f }
            goto L_0x0036
        L_0x0047:
            if (r5 == 0) goto L_0x005a
        L_0x0049:
            r5.close()
            goto L_0x005a
        L_0x004d:
            r0 = move-exception
            goto L_0x0066
        L_0x004f:
            r0 = move-exception
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x004d }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r3, r0)     // Catch:{ all -> 0x004d }
            if (r5 == 0) goto L_0x005a
            goto L_0x0049
        L_0x005a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            r0.append(r4)
            goto L_0x001c
        L_0x0066:
            if (r5 == 0) goto L_0x006b
            r5.close()
        L_0x006b:
            goto L_0x006d
        L_0x006c:
            throw r0
        L_0x006d:
            goto L_0x006c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1550r.mo15182b():java.lang.String");
    }
}
