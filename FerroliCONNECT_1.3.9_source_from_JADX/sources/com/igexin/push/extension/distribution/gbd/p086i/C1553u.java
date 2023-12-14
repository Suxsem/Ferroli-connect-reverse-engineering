package com.igexin.push.extension.distribution.gbd.p086i;

import android.app.ActivityManager;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.contrarywind.timer.MessageHandler;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.u */
public class C1553u {
    /* renamed from: a */
    private static int m3053a(String str, String str2) {
        int length = str.split(str2).length - 1;
        if (length > 0) {
            return length;
        }
        return 0;
    }

    /* renamed from: a */
    private static String m3054a(int i) {
        String str;
        try {
            str = m3062b(String.format("/proc/%d/cmdline", new Object[]{Integer.valueOf(i)})).trim();
        } catch (Exception e) {
            C1540h.m2996a(e);
            str = null;
        }
        try {
            if (TextUtils.isEmpty(str)) {
                return m3062b(String.format("/proc/%d/stat", new Object[]{Integer.valueOf(i)})).split("\\s+")[1].replace("(", "").replace(")", "");
            }
        } catch (Exception e2) {
            C1540h.m2996a(e2);
        }
        return str;
    }

    /* renamed from: a */
    private static String m3055a(String str) {
        try {
            if (!Pattern.compile("^([a-zA-Z]+[.][a-zA-Z]+)[.]*.*").matcher(str).find()) {
                return null;
            }
            return str.contains(":") ? str.split(":")[0] : str;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    private static String m3056a(String str, boolean z) {
        String str2;
        try {
            Matcher matcher = Pattern.compile("^([a-zA-Z]+[.][a-zA-Z]+)[.]*.*").matcher(str);
            if (C1490c.f2744U == null || C1490c.f2744U.isEmpty()) {
                C1490c.f2744U = new ArrayList(Arrays.asList(C1488a.f2656aM.split(",")));
            }
            if (str.contains(":")) {
                String[] split = str.split(":");
                String str3 = split[0];
                str2 = split[1];
                str = str3;
            } else {
                str2 = null;
            }
            boolean contains = C1490c.f2744U.contains(str);
            if (!matcher.find() || ((str.startsWith("com.android") && !contains) || str.startsWith("android.process") || str.startsWith("org."))) {
                return null;
            }
            PackageInfo packageInfo = C1490c.f2747a.getPackageManager().getPackageInfo(str, 0);
            if ((1 & packageInfo.applicationInfo.flags) == 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
                if (!z || str2 == null) {
                    return str;
                }
                return str + ":" + str2;
            }
            return null;
        } catch (Exception e) {
            C1540h.m2996a(e);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0138, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0139, code lost:
        com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0);
     */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0134 A[SYNTHETIC, Splitter:B:92:0x0134] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0140 A[SYNTHETIC, Splitter:B:98:0x0140] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.String[]> m3057a(java.lang.String r16, boolean r17, java.lang.String r18) {
        /*
            java.lang.String r0 = ","
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 0
            java.lang.String r4 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2626J     // Catch:{ Throwable -> 0x012e }
            java.lang.String r5 = "\\|"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Throwable -> 0x012e }
            int r5 = r4.length     // Catch:{ Throwable -> 0x012e }
            r6 = 4
            if (r5 == r6) goto L_0x0019
            return r3
        L_0x0019:
            r5 = 0
            r6 = r4[r5]     // Catch:{ Throwable -> 0x012e }
            java.lang.String[] r6 = r6.split(r0)     // Catch:{ Throwable -> 0x012e }
            r7 = 1
            r8 = r4[r7]     // Catch:{ Throwable -> 0x012e }
            java.lang.String[] r8 = r8.split(r0)     // Catch:{ Throwable -> 0x012e }
            r9 = 2
            r10 = r4[r9]     // Catch:{ Throwable -> 0x012e }
            java.lang.String[] r10 = r10.split(r0)     // Catch:{ Throwable -> 0x012e }
            r11 = 3
            r4 = r4[r11]     // Catch:{ Throwable -> 0x012e }
            java.lang.String[] r0 = r4.split(r0)     // Catch:{ Throwable -> 0x012e }
            r4 = r16
            java.lang.Process r1 = r1.exec(r4)     // Catch:{ Throwable -> 0x012e }
            java.io.InputStream r1 = r1.getInputStream()     // Catch:{ Throwable -> 0x012e }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x012e }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x012e }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x012e }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x012e }
        L_0x0049:
            java.lang.String r3 = r1.readLine()     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r3 == 0) goto L_0x0121
            java.lang.String r4 = "USER"
            boolean r4 = r3.contains(r4)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 == 0) goto L_0x0058
            goto L_0x0049
        L_0x0058:
            java.lang.String r4 = "zygote"
            java.lang.String r11 = "none"
            if (r17 == 0) goto L_0x0065
            boolean r12 = r3.contains(r4)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r12 != 0) goto L_0x0084
            goto L_0x0049
        L_0x0065:
            int r12 = r8.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r12 <= r7) goto L_0x0084
            r12 = r8[r5]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r12 = r12.equals(r11)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r12 != 0) goto L_0x0084
            int r12 = r8.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r13 = 0
            r14 = 1
        L_0x0073:
            if (r13 >= r12) goto L_0x0081
            r15 = r8[r13]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r15 = r3.contains(r15)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r15 == 0) goto L_0x007e
            r14 = 0
        L_0x007e:
            int r13 = r13 + 1
            goto L_0x0073
        L_0x0081:
            if (r14 != 0) goto L_0x0084
            goto L_0x0049
        L_0x0084:
            java.lang.String r12 = " +"
            java.lang.String[] r3 = r3.split(r12)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            int r12 = r3.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            int r12 = r12 - r7
            if (r17 == 0) goto L_0x009e
            r11 = r3[r12]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r4 = r11.equals(r4)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 == 0) goto L_0x009b
            r2.add(r3)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            goto L_0x0121
        L_0x009b:
            r11 = r18
            goto L_0x0049
        L_0x009e:
            r4 = r3[r12]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            java.lang.String r13 = "\\."
            int r4 = m3053a((java.lang.String) r4, (java.lang.String) r13)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 <= r9) goto L_0x00a9
            goto L_0x0049
        L_0x00a9:
            int r4 = r10.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 < r7) goto L_0x00cc
            r4 = r10[r5]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r4 = r4.equals(r11)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 != 0) goto L_0x00cc
            int r4 = r10.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r13 = 0
        L_0x00b6:
            if (r13 >= r4) goto L_0x00c7
            r14 = r10[r13]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r15 = r3[r12]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r14 = r15.contains(r14)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r14 != 0) goto L_0x00c4
            r4 = 0
            goto L_0x00c8
        L_0x00c4:
            int r13 = r13 + 1
            goto L_0x00b6
        L_0x00c7:
            r4 = 1
        L_0x00c8:
            if (r4 != 0) goto L_0x00cc
            goto L_0x0049
        L_0x00cc:
            int r4 = r6.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 < r7) goto L_0x00ef
            r4 = r6[r5]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r4 = r4.equals(r11)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 != 0) goto L_0x00ef
            int r4 = r6.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r13 = 0
        L_0x00d9:
            if (r13 >= r4) goto L_0x00ea
            r14 = r6[r13]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r15 = r3[r12]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r14 = r15.startsWith(r14)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r14 == 0) goto L_0x00e7
            r4 = 0
            goto L_0x00eb
        L_0x00e7:
            int r13 = r13 + 1
            goto L_0x00d9
        L_0x00ea:
            r4 = 1
        L_0x00eb:
            if (r4 != 0) goto L_0x00ef
            goto L_0x0049
        L_0x00ef:
            int r4 = r0.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 < r7) goto L_0x0112
            r4 = r0[r5]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r4 = r4.equals(r11)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 != 0) goto L_0x0112
            int r4 = r0.length     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r11 = 0
        L_0x00fc:
            if (r11 >= r4) goto L_0x010d
            r13 = r0[r11]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r14 = r3[r12]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            boolean r13 = r14.endsWith(r13)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r13 == 0) goto L_0x010a
            r4 = 0
            goto L_0x010e
        L_0x010a:
            int r11 = r11 + 1
            goto L_0x00fc
        L_0x010d:
            r4 = 1
        L_0x010e:
            if (r4 != 0) goto L_0x0112
            goto L_0x0049
        L_0x0112:
            r4 = r3[r9]     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            r11 = r18
            boolean r4 = r4.equals(r11)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            if (r4 == 0) goto L_0x0049
            r2.add(r3)     // Catch:{ Throwable -> 0x0128, all -> 0x0125 }
            goto L_0x0049
        L_0x0121:
            r1.close()     // Catch:{ Exception -> 0x0138 }
            goto L_0x013d
        L_0x0125:
            r0 = move-exception
            r3 = r1
            goto L_0x012c
        L_0x0128:
            r0 = move-exception
            r3 = r1
            goto L_0x012f
        L_0x012b:
            r0 = move-exception
        L_0x012c:
            r1 = r0
            goto L_0x013e
        L_0x012e:
            r0 = move-exception
        L_0x012f:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x012b }
            if (r3 == 0) goto L_0x013d
            r3.close()     // Catch:{ Exception -> 0x0138 }
            goto L_0x013d
        L_0x0138:
            r0 = move-exception
            r1 = r0
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x013d:
            return r2
        L_0x013e:
            if (r3 == 0) goto L_0x0149
            r3.close()     // Catch:{ Exception -> 0x0144 }
            goto L_0x0149
        L_0x0144:
            r0 = move-exception
            r2 = r0
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r2)
        L_0x0149:
            goto L_0x014b
        L_0x014a:
            throw r1
        L_0x014b:
            goto L_0x014a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1553u.m3057a(java.lang.String, boolean, java.lang.String):java.util.ArrayList");
    }

    /* renamed from: a */
    private static List<String> m3058a() {
        ArrayList<String[]> a;
        try {
            ArrayList<String[]> a2 = m3057a("ps -P", true, "");
            if (a2 != null) {
                if (!a2.isEmpty()) {
                    if (!a2.isEmpty() && (a = m3057a("ps -P", false, a2.get(0)[1])) != null) {
                        if (!a.isEmpty()) {
                            return m3059a(a, (List<ActivityManager.RecentTaskInfo>) null);
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: a */
    private static List<String> m3059a(ArrayList<String[]> arrayList, List<ActivityManager.RecentTaskInfo> list) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList != null) {
            Iterator<String[]> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(0, it.next()[9]);
            }
        } else {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
                arrayList2.add(recentTaskInfo.baseIntent.getComponent().getPackageName());
            }
        }
        return arrayList2;
    }

    /* renamed from: a */
    public static List<String> m3060a(boolean z, boolean z2) {
        List<String> b = m3063b(z, z2);
        if ((b == null || b.isEmpty()) && !z2) {
            b = m3058a();
        }
        if (b != null) {
            C1540h.m2997b("GBD_RAU", Build.VERSION.SDK_INT + ", running = " + b.toString());
        }
        return b;
    }

    /* renamed from: b */
    private static long m3061b(int i) {
        try {
            return (C1541i.m3034i() - SystemClock.elapsedRealtime()) + (Long.parseLong(m3062b(String.format("/proc/%d/stat", new Object[]{Integer.valueOf(i)})).split("\\s+")[21]) * 10);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0044 A[SYNTHETIC, Splitter:B:27:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0050 A[SYNTHETIC, Splitter:B:34:0x0050] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m3062b(java.lang.String r5) {
        /*
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
            r1.<init>()     // Catch:{ all -> 0x003f }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x003f }
            r2.<init>(r5)     // Catch:{ all -> 0x003f }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ all -> 0x003c }
            r5.<init>(r2)     // Catch:{ all -> 0x003c }
            java.lang.String r3 = r5.readLine()     // Catch:{ all -> 0x003a }
            java.lang.String r4 = ""
        L_0x0016:
            if (r3 == 0) goto L_0x0025
            r1.append(r4)     // Catch:{ all -> 0x003a }
            r1.append(r3)     // Catch:{ all -> 0x003a }
            java.lang.String r4 = "\n"
            java.lang.String r3 = r5.readLine()     // Catch:{ all -> 0x003a }
            goto L_0x0016
        L_0x0025:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x003a }
            r2.close()     // Catch:{ Exception -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r2 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r2)     // Catch:{ Throwable -> 0x0048 }
        L_0x0031:
            r5.close()     // Catch:{ Exception -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)     // Catch:{ Throwable -> 0x0048 }
        L_0x0039:
            return r1
        L_0x003a:
            r1 = move-exception
            goto L_0x0042
        L_0x003c:
            r1 = move-exception
            r5 = r0
            goto L_0x0042
        L_0x003f:
            r1 = move-exception
            r5 = r0
            r2 = r5
        L_0x0042:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004a }
            goto L_0x004e
        L_0x0048:
            r5 = move-exception
            goto L_0x0059
        L_0x004a:
            r2 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r2)     // Catch:{ Throwable -> 0x0048 }
        L_0x004e:
            if (r5 == 0) goto L_0x0058
            r5.close()     // Catch:{ Exception -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)     // Catch:{ Throwable -> 0x0048 }
        L_0x0058:
            throw r1     // Catch:{ Throwable -> 0x0048 }
        L_0x0059:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1553u.m3062b(java.lang.String):java.lang.String");
    }

    /* renamed from: b */
    private static List<String> m3063b(boolean z, boolean z2) {
        Map<String, Long> map;
        Long valueOf;
        ArrayList arrayList = new ArrayList();
        if (C1490c.f2759m == null) {
            C1490c.f2759m = new HashMap();
        }
        if (z) {
            C1490c.f2759m.clear();
        }
        for (File name : new File("/proc").listFiles(new C1554v(z2 ? 0 : MessageHandler.WHAT_SMOOTH_SCROLL))) {
            try {
                int parseInt = Integer.parseInt(name.getName());
                try {
                    String a = m3054a(parseInt);
                    if (!TextUtils.isEmpty(a)) {
                        a = z2 ? m3055a(a) : m3056a(a, z);
                    }
                    if (!TextUtils.isEmpty(a) && !arrayList.contains(a)) {
                        arrayList.add(a);
                        if (!z2) {
                            if (a.contains(":")) {
                                a = a.split(":")[0];
                            }
                            long b = m3061b(parseInt);
                            if (!C1490c.f2759m.containsKey(a)) {
                                map = C1490c.f2759m;
                                valueOf = Long.valueOf(b);
                            } else if (b < C1490c.f2759m.get(a).longValue()) {
                                map = C1490c.f2759m;
                                valueOf = Long.valueOf(b);
                            }
                            map.put(a, valueOf);
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                }
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }
}
