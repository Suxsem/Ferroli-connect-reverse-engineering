package com.igexin.push.extension.distribution.basic.p068j;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: com.igexin.push.extension.distribution.basic.j.k */
public class C1443k {
    /* renamed from: a */
    private static String m2529a(int i) {
        String str;
        try {
            str = m2532b(String.format("/proc/%d/cmdline", new Object[]{Integer.valueOf(i)})).trim();
        } catch (Exception unused) {
            str = null;
        }
        try {
            if (TextUtils.isEmpty(str)) {
                return m2532b(String.format("/proc/%d/stat", new Object[]{Integer.valueOf(i)})).split("\\s+")[1].replace("(", "").replace(")", "");
            }
        } catch (Exception unused2) {
        }
        return str;
    }

    /* renamed from: a */
    private static String m2530a(String str) {
        if (Pattern.compile("^([a-zA-Z]+[.][a-zA-Z]+)[.]*.*").matcher(str).find() && !str.startsWith("com.android") && !str.startsWith("android.process") && !str.startsWith("org.")) {
            if (str.contains(":")) {
                str = str.split(":")[0];
            }
            try {
                int i = C1343f.f2169f.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags;
                if ((i & 1) == 0 || (i & 128) != 0) {
                    return str;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return null;
    }

    /* renamed from: a */
    public static List<String> m2531a() {
        List<String> b = m2533b();
        if (b != null) {
            C1179b.m1354a("EXT-RecentAppUtil|" + Build.VERSION.SDK_INT + ",running = " + b.toString());
        }
        return b;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:11|12|13|14|15|16) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:18|19|20|21|22|23|24) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0033 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x003e */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0053 A[SYNTHETIC, Splitter:B:35:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x005a A[SYNTHETIC, Splitter:B:39:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0062 A[SYNTHETIC, Splitter:B:46:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0069 A[SYNTHETIC, Splitter:B:50:0x0069] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m2532b(java.lang.String r7) {
        /*
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e, all -> 0x004d }
            r1.<init>()     // Catch:{ Exception -> 0x005e, all -> 0x004d }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x005e, all -> 0x004d }
            r2.<init>(r7)     // Catch:{ Exception -> 0x005e, all -> 0x004d }
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ Exception -> 0x004b, all -> 0x0046 }
            r7.<init>(r2)     // Catch:{ Exception -> 0x004b, all -> 0x0046 }
        L_0x0010:
            java.lang.String r3 = r7.readLine()     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            if (r3 == 0) goto L_0x001f
            r1.append(r3)     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            java.lang.String r3 = "\n"
            r1.append(r3)     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            goto L_0x0010
        L_0x001f:
            int r3 = r1.length()     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            r4 = 2
            if (r3 <= r4) goto L_0x0037
            r3 = 0
            int r5 = r1.length()     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            int r5 = r5 - r4
            java.lang.String r0 = r1.substring(r3, r5)     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            r2.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0033:
            r7.close()     // Catch:{ Exception -> 0x0036 }
        L_0x0036:
            return r0
        L_0x0037:
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x0044, all -> 0x0042 }
            r2.close()     // Catch:{ Exception -> 0x003e }
        L_0x003e:
            r7.close()     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            return r0
        L_0x0042:
            r0 = move-exception
            goto L_0x0051
        L_0x0044:
            goto L_0x0060
        L_0x0046:
            r7 = move-exception
            r6 = r0
            r0 = r7
            r7 = r6
            goto L_0x0051
        L_0x004b:
            r7 = r0
            goto L_0x0060
        L_0x004d:
            r7 = move-exception
            r2 = r0
            r0 = r7
            r7 = r2
        L_0x0051:
            if (r2 == 0) goto L_0x0058
            r2.close()     // Catch:{ Exception -> 0x0057 }
            goto L_0x0058
        L_0x0057:
        L_0x0058:
            if (r7 == 0) goto L_0x005d
            r7.close()     // Catch:{ Exception -> 0x005d }
        L_0x005d:
            throw r0
        L_0x005e:
            r7 = r0
            r2 = r7
        L_0x0060:
            if (r2 == 0) goto L_0x0067
            r2.close()     // Catch:{ Exception -> 0x0066 }
            goto L_0x0067
        L_0x0066:
        L_0x0067:
            if (r7 == 0) goto L_0x006c
            r7.close()     // Catch:{ Exception -> 0x006c }
        L_0x006c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p068j.C1443k.m2532b(java.lang.String):java.lang.String");
    }

    /* renamed from: b */
    private static List<String> m2533b() {
        ArrayList arrayList = new ArrayList();
        for (File name : new File("/proc").listFiles(new C1444l())) {
            try {
                String a = m2529a(Integer.parseInt(name.getName()));
                if (!TextUtils.isEmpty(a)) {
                    a = m2530a(a);
                }
                if (!TextUtils.isEmpty(a) && !arrayList.contains(a)) {
                    arrayList.add(a);
                }
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }
}
