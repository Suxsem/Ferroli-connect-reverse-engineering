package com.p092ta.p093a.p096c;

import android.text.TextUtils;
import java.io.File;

/* renamed from: com.ta.a.c.a */
public class C1975a {
    /* renamed from: b */
    public static void m3349b(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                File file = new File(str);
                if (!file.exists()) {
                    C1982f.m3371b("FileUtils", "mkdirs path", str, "created", Boolean.valueOf(file.mkdirs()));
                    return;
                }
                C1982f.m3371b("FileUtils", "path exists", str);
            }
        } catch (Exception e) {
            C1982f.m3370b("FileUtils", e, new Object[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043 A[SYNTHETIC, Splitter:B:24:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0050 A[SYNTHETIC, Splitter:B:30:0x0050] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3351c(java.lang.String r7) {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "FileUtils"
            r2 = 0
            r3 = 0
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x003b }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x003b }
            r5.<init>(r7)     // Catch:{ Exception -> 0x003b }
            r4.<init>(r5)     // Catch:{ Exception -> 0x003b }
            r7 = 100
            char[] r7 = new char[r7]     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
        L_0x0019:
            int r5 = r4.read(r7)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r6 = -1
            if (r5 == r6) goto L_0x0024
            r3.append(r7, r2, r5)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            goto L_0x0019
        L_0x0024:
            java.lang.String r7 = r3.toString()     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r4.close()     // Catch:{ Exception -> 0x002c }
            goto L_0x0032
        L_0x002c:
            r0 = move-exception
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r1, r0, r2)
        L_0x0032:
            return r7
        L_0x0033:
            r7 = move-exception
            r3 = r4
            goto L_0x004e
        L_0x0036:
            r7 = move-exception
            r3 = r4
            goto L_0x003c
        L_0x0039:
            r7 = move-exception
            goto L_0x004e
        L_0x003b:
            r7 = move-exception
        L_0x003c:
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ all -> 0x0039 }
            com.p092ta.p093a.p096c.C1982f.m3370b(r1, r7, r4)     // Catch:{ all -> 0x0039 }
            if (r3 == 0) goto L_0x004d
            r3.close()     // Catch:{ Exception -> 0x0047 }
            goto L_0x004d
        L_0x0047:
            r7 = move-exception
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r1, r7, r2)
        L_0x004d:
            return r0
        L_0x004e:
            if (r3 == 0) goto L_0x005a
            r3.close()     // Catch:{ Exception -> 0x0054 }
            goto L_0x005a
        L_0x0054:
            r0 = move-exception
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r1, r0, r2)
        L_0x005a:
            goto L_0x005c
        L_0x005b:
            throw r7
        L_0x005c:
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p092ta.p093a.p096c.C1975a.m3351c(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0043 A[SYNTHETIC, Splitter:B:29:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004f A[SYNTHETIC, Splitter:B:34:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x005d A[SYNTHETIC, Splitter:B:41:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0069 A[SYNTHETIC, Splitter:B:46:0x0069] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean m3350b(java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.String r0 = "FileUtils"
            r1 = 0
            r2 = 0
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
            java.io.BufferedWriter r5 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0035 }
            r5.<init>(r3)     // Catch:{ Exception -> 0x0035 }
            r5.write(r6)     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            r5.flush()     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            r6 = 1
            r5.close()     // Catch:{ Exception -> 0x001e }
            goto L_0x0024
        L_0x001e:
            r5 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r1)
        L_0x0024:
            r3.close()     // Catch:{ Exception -> 0x0028 }
            goto L_0x002e
        L_0x0028:
            r5 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r1)
        L_0x002e:
            return r6
        L_0x002f:
            r6 = move-exception
            r1 = r5
            goto L_0x005b
        L_0x0032:
            r6 = move-exception
            r1 = r5
            goto L_0x003c
        L_0x0035:
            r6 = move-exception
            goto L_0x003c
        L_0x0037:
            r6 = move-exception
            r3 = r1
            goto L_0x005b
        L_0x003a:
            r6 = move-exception
            r3 = r1
        L_0x003c:
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x005a }
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r6, r5)     // Catch:{ all -> 0x005a }
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ Exception -> 0x0047 }
            goto L_0x004d
        L_0x0047:
            r5 = move-exception
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r6)
        L_0x004d:
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ Exception -> 0x0053 }
            goto L_0x0059
        L_0x0053:
            r5 = move-exception
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r6)
        L_0x0059:
            return r2
        L_0x005a:
            r6 = move-exception
        L_0x005b:
            if (r1 == 0) goto L_0x0067
            r1.close()     // Catch:{ Exception -> 0x0061 }
            goto L_0x0067
        L_0x0061:
            r5 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r1)
        L_0x0067:
            if (r3 == 0) goto L_0x0073
            r3.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x0073
        L_0x006d:
            r5 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.p092ta.p093a.p096c.C1982f.m3370b(r0, r5, r1)
        L_0x0073:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p092ta.p093a.p096c.C1975a.m3350b(java.lang.String, java.lang.String):boolean");
    }
}
