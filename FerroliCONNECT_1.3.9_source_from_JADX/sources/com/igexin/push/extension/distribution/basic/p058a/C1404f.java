package com.igexin.push.extension.distribution.basic.p058a;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.text.TextUtils;
import com.contrarywind.timer.MessageHandler;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p060b.C1410e;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p062d.C1417a;
import com.igexin.push.extension.distribution.basic.p062d.C1419c;
import com.igexin.push.extension.distribution.basic.p068j.C1435c;
import com.igexin.push.extension.distribution.basic.p068j.C1440h;
import com.igexin.push.extension.distribution.basic.p068j.C1443k;
import com.igexin.push.extension.distribution.basic.p068j.C1445m;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.sdk.GActivity;
import com.igexin.sdk.PushConsts;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.a.f */
public class C1404f implements C1240a {

    /* renamed from: a */
    private static final String f2362a = ("EXT-" + C1404f.class.getName());

    /* renamed from: b */
    private String f2363b;

    /* renamed from: c */
    private String f2364c;

    /* renamed from: d */
    private PackageManager f2365d = null;

    /* renamed from: a */
    private String m2296a(String str) {
        List<PackageInfo> installedPackages;
        try {
            byte[] b = C1440h.m2525b("/sdcard/libs//" + str + ".bin");
            String str2 = b != null ? new String(C1445m.m2536b(b)) : C1417a.m2423a().mo14949a(str);
            if (str2 != null) {
                C1179b.m1354a(f2362a + "|guardService name = " + str2);
                return str2;
            }
            if (C1419c.m2437a(C1343f.f2169f) && (installedPackages = C1343f.f2169f.getPackageManager().getInstalledPackages(4)) != null) {
                for (PackageInfo next : installedPackages) {
                    if (str.equals(next.packageName)) {
                        ServiceInfo[] serviceInfoArr = next.services;
                        int length = serviceInfoArr.length;
                        int i = 0;
                        while (i < length) {
                            ServiceInfo serviceInfo = serviceInfoArr[i];
                            if (!"com.igexin.sdk.PushService".equals(serviceInfo.name) && !"com.igexin.sdk.coordinator.SdkMsgService".equals(serviceInfo.name)) {
                                if (!"com.igexin.sdk.coordinator.GexinMsgService".equals(serviceInfo.name)) {
                                    i++;
                                }
                            }
                            return serviceInfo.name;
                        }
                        continue;
                    }
                }
            }
            return null;
        } catch (Throwable th) {
            C1179b.m1354a(f2362a + "|" + th.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x0135 A[SYNTHETIC, Splitter:B:73:0x0135] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0138 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0155 A[SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> m2297a(int r13, java.lang.String r14, java.lang.String r15) {
        /*
            r12 = this;
            java.lang.String r0 = "|"
            java.io.File r1 = new java.io.File
            r1.<init>(r15)
            boolean r15 = r1.exists()
            r2 = 0
            if (r15 != 0) goto L_0x000f
            return r2
        L_0x000f:
            java.lang.String[] r15 = r1.list()
            if (r15 != 0) goto L_0x0016
            return r2
        L_0x0016:
            r3 = 0
            r5 = r2
            r4 = 0
        L_0x0019:
            int r6 = r15.length
            if (r4 >= r6) goto L_0x0159
            r6 = r15[r4]
            java.lang.String r7 = ".db"
            int r6 = r6.indexOf(r7)
            if (r6 <= 0) goto L_0x0155
            r6 = r15[r4]
            java.lang.String r7 = "app.db"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0155
            r6 = r15[r4]
            java.lang.String r7 = "imsi.db"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0155
            r6 = r15[r4]
            java.lang.String r7 = "com.igexin.sdk.deviceId.db"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0155
            r6 = r15[r4]
            r7 = r15[r4]
            int r7 = r7.length()
            int r7 = r7 + -3
            java.lang.String r6 = r6.substring(r3, r7)
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0139 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0139 }
            r8.<init>()     // Catch:{ Exception -> 0x0139 }
            r8.append(r1)     // Catch:{ Exception -> 0x0139 }
            java.lang.String r9 = "/"
            r8.append(r9)     // Catch:{ Exception -> 0x0139 }
            r9 = r15[r4]     // Catch:{ Exception -> 0x0139 }
            r8.append(r9)     // Catch:{ Exception -> 0x0139 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0139 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x0139 }
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x0139 }
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0112, all -> 0x010f }
            r9.<init>(r7)     // Catch:{ Exception -> 0x0112, all -> 0x010f }
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x010d }
            r7.<init>()     // Catch:{ Exception -> 0x010d }
        L_0x007b:
            int r10 = r9.read(r8)     // Catch:{ Exception -> 0x010d }
            r11 = -1
            if (r10 == r11) goto L_0x0086
            r7.write(r8, r3, r10)     // Catch:{ Exception -> 0x010d }
            goto L_0x007b
        L_0x0086:
            byte[] r7 = r7.toByteArray()     // Catch:{ Exception -> 0x010d }
            java.lang.String r8 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Exception -> 0x010d }
            if (r8 != 0) goto L_0x0091
            java.lang.String r8 = "cantgetimei"
            goto L_0x0093
        L_0x0091:
            java.lang.String r8 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Exception -> 0x010d }
        L_0x0093:
            java.lang.String r8 = com.igexin.push.extension.distribution.basic.p068j.C1445m.m2534a((java.lang.String) r8)     // Catch:{ Exception -> 0x010d }
            java.lang.String r10 = new java.lang.String     // Catch:{ Exception -> 0x010d }
            byte[] r7 = com.igexin.p032b.p033a.p034a.C1150a.m1231a((byte[]) r7, (java.lang.String) r8)     // Catch:{ Exception -> 0x010d }
            r10.<init>(r7)     // Catch:{ Exception -> 0x010d }
            java.lang.String r7 = "\\|"
            java.lang.String[] r7 = r10.split(r7)     // Catch:{ Exception -> 0x010d }
            r8 = r7[r3]     // Catch:{ Exception -> 0x010d }
            java.lang.String r10 = "v"
            boolean r8 = r8.startsWith(r10)     // Catch:{ Exception -> 0x010d }
            if (r8 == 0) goto L_0x00ce
            r8 = r7[r3]     // Catch:{ Exception -> 0x010d }
            java.lang.String r10 = "null"
            boolean r8 = r8.contains(r10)     // Catch:{ Exception -> 0x010d }
            if (r8 == 0) goto L_0x00c4
            r8 = r7[r3]     // Catch:{ Exception -> 0x010d }
            r10 = 7
            java.lang.String r8 = r8.substring(r10)     // Catch:{ Exception -> 0x010d }
            r7[r3] = r8     // Catch:{ Exception -> 0x010d }
            goto L_0x00ce
        L_0x00c4:
            r8 = r7[r3]     // Catch:{ Exception -> 0x010d }
            r10 = 20
            java.lang.String r8 = r8.substring(r10)     // Catch:{ Exception -> 0x010d }
            r7[r3] = r8     // Catch:{ Exception -> 0x010d }
        L_0x00ce:
            r8 = r7[r3]     // Catch:{ Exception -> 0x010d }
            java.lang.String r8 = com.igexin.push.extension.distribution.basic.p068j.C1445m.m2534a((java.lang.String) r8)     // Catch:{ Exception -> 0x010d }
            if (r13 != 0) goto L_0x00f2
            boolean r7 = r14.equals(r8)     // Catch:{ Exception -> 0x010d }
            if (r7 == 0) goto L_0x0109
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ Exception -> 0x010d }
            r7.<init>()     // Catch:{ Exception -> 0x010d }
            r7.add(r6)     // Catch:{ Exception -> 0x00ef, all -> 0x00ec }
            r9.close()     // Catch:{ Exception -> 0x00e8 }
            return r7
        L_0x00e8:
            r5 = move-exception
            r6 = r5
            r5 = r7
            goto L_0x013a
        L_0x00ec:
            r6 = move-exception
            r5 = r7
            goto L_0x0133
        L_0x00ef:
            r6 = move-exception
            r5 = r7
            goto L_0x0114
        L_0x00f2:
            int r8 = r7.length     // Catch:{ Exception -> 0x010d }
            r10 = 1
            if (r8 <= r10) goto L_0x0109
            r7 = r7[r10]     // Catch:{ Exception -> 0x010d }
            boolean r7 = r14.equals(r7)     // Catch:{ Exception -> 0x010d }
            if (r7 == 0) goto L_0x0109
            if (r5 != 0) goto L_0x0106
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ Exception -> 0x010d }
            r7.<init>()     // Catch:{ Exception -> 0x010d }
            r5 = r7
        L_0x0106:
            r5.add(r6)     // Catch:{ Exception -> 0x010d }
        L_0x0109:
            r9.close()     // Catch:{ Exception -> 0x0139 }
            goto L_0x0155
        L_0x010d:
            r6 = move-exception
            goto L_0x0114
        L_0x010f:
            r6 = move-exception
            r9 = r2
            goto L_0x0133
        L_0x0112:
            r6 = move-exception
            r9 = r2
        L_0x0114:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0132 }
            r7.<init>()     // Catch:{ all -> 0x0132 }
            java.lang.String r8 = f2362a     // Catch:{ all -> 0x0132 }
            r7.append(r8)     // Catch:{ all -> 0x0132 }
            r7.append(r0)     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0132 }
            r7.append(r6)     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r7.toString()     // Catch:{ all -> 0x0132 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r6)     // Catch:{ all -> 0x0132 }
            if (r9 == 0) goto L_0x0155
            goto L_0x0109
        L_0x0132:
            r6 = move-exception
        L_0x0133:
            if (r9 == 0) goto L_0x0138
            r9.close()     // Catch:{ Exception -> 0x0139 }
        L_0x0138:
            throw r6     // Catch:{ Exception -> 0x0139 }
        L_0x0139:
            r6 = move-exception
        L_0x013a:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = f2362a
            r7.append(r8)
            r7.append(r0)
            java.lang.String r6 = r6.toString()
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r6)
        L_0x0155:
            int r4 = r4 + 1
            goto L_0x0019
        L_0x0159:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1404f.m2297a(int, java.lang.String, java.lang.String):java.util.List");
    }

    /* renamed from: a */
    private void m2299a(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(C1343f.f2169f.getPackageName());
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append(str4);
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append(str5);
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append("-1");
        m2307b(this.f2363b, sb.toString(), str, str2, str3);
        C1179b.m1354a(f2362a + "|feedback actionId=" + this.f2363b + " result=" + sb.toString());
    }

    /* renamed from: a */
    private void m2300a(String str, boolean z, PushTaskBean pushTaskBean, BaseAction baseAction) {
        String str2 = str;
        boolean z2 = z;
        try {
            String a = m2296a(str);
            C1179b.m1354a(f2362a + "|startSByPkgName Name = " + a);
            String messageId = pushTaskBean.getMessageId();
            String taskId = pushTaskBean.getTaskId();
            String a2 = ((C1410e) baseAction).mo14929a();
            if (a != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("messageId", messageId);
                hashMap.put("taskId", taskId);
                hashMap.put(AgooConstants.MESSAGE_ID, a2);
                hashMap.put("pkgName", str2);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(C1343f.f2169f.getPackageName());
                stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                stringBuffer.append(m2311c(str));
                stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                stringBuffer.append(str2);
                stringBuffer.append("/");
                String str3 = "com.igexin.sdk.coordinator.GexinMsgService";
                if (a.equals("com.igexin.sdk.PushService")) {
                    stringBuffer.append("com.igexin.sdk.PushService");
                    stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                    if (!m2309b(str2, "com.igexin.sdk.PushService")) {
                        if (!m2306a(str2, a, z2)) {
                            m2301a(stringBuffer, messageId, taskId, a2);
                            return;
                        }
                        hashMap.put("serviceName", "com.igexin.sdk.PushService");
                        m2302a((Map<String, String>) hashMap);
                        stringBuffer.append("1");
                        m2307b(this.f2363b, stringBuffer.toString(), messageId, taskId, a2);
                        C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
                        return;
                    }
                } else if (a.equals("com.igexin.sdk.coordinator.SdkMsgService")) {
                    stringBuffer.append("com.igexin.sdk.coordinator.SdkMsgService");
                    stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                    if (!m2309b(str2, "com.igexin.sdk.coordinator.SdkMsgService")) {
                        if (!m2306a(str2, a, false)) {
                            m2301a(stringBuffer, messageId, taskId, a2);
                            return;
                        }
                        hashMap.put("serviceName", "com.igexin.sdk.coordinator.SdkMsgService");
                        m2302a((Map<String, String>) hashMap);
                        stringBuffer.append("1");
                        m2307b(this.f2363b, stringBuffer.toString(), messageId, taskId, a2);
                        C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
                        return;
                    }
                } else {
                    String str4 = str3;
                    if (a.equals(str4)) {
                        stringBuffer.append(str4);
                        stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                        if (!m2309b(str2, str4)) {
                            if (!m2306a(str2, a, false)) {
                                m2301a(stringBuffer, messageId, taskId, a2);
                                return;
                            }
                            hashMap.put("serviceName", str4);
                            m2302a((Map<String, String>) hashMap);
                            stringBuffer.append("1");
                            m2307b(this.f2363b, stringBuffer.toString(), messageId, taskId, a2);
                            C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
                            return;
                        }
                    } else {
                        stringBuffer.append(a);
                        stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                        if (!m2309b(str2, a)) {
                            if (!m2306a(str2, a, z2)) {
                                m2301a(stringBuffer, messageId, taskId, a2);
                                return;
                            }
                            hashMap.put("serviceName", a);
                            m2302a((Map<String, String>) hashMap);
                            stringBuffer.append("1");
                            m2307b(this.f2363b, stringBuffer.toString(), messageId, taskId, a2);
                            C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
                            return;
                        }
                    }
                }
                stringBuffer.append("0");
                m2307b(this.f2363b, stringBuffer.toString(), messageId, taskId, a2);
                C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
                return;
            }
            m2299a(messageId, taskId, a2, ((C1410e) baseAction).mo14936d() != null ? ((C1410e) baseAction).mo14936d() : "", ((C1410e) baseAction).mo14934c() != null ? ((C1410e) baseAction).mo14934c() : "");
        } catch (Throwable th) {
            C1179b.m1354a(f2362a + "|" + th.toString());
        }
    }

    /* renamed from: a */
    private void m2301a(StringBuffer stringBuffer, String str, String str2, String str3) {
        stringBuffer.append("-1");
        m2307b(this.f2363b, stringBuffer.toString(), str, str2, str3);
        C1179b.m1354a("feedback actionId=" + this.f2363b + " result=" + stringBuffer.toString());
    }

    /* renamed from: a */
    private void m2302a(Map<String, String> map) {
        C1340e.m2032a().mo14703a((C1575h) new C1405g(this, 40000, map, this.f2364c));
    }

    /* renamed from: a */
    private boolean m2303a() {
        C1179b.m1354a(f2362a + "|Check is activity guard available, Build.Version = " + Build.VERSION.SDK_INT);
        if (!C1435c.m2510c()) {
            C1179b.m1354a(f2362a + "| Check black list app = false, gEnable = true.");
            return true;
        }
        if (Build.VERSION.SDK_INT < 21) {
            try {
                List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) C1343f.f2169f.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningTasks(1);
                if (runningTasks == null || runningTasks.isEmpty()) {
                    return true;
                }
                ComponentName componentName = runningTasks.get(0).topActivity;
                if (componentName != null) {
                    boolean d = C1435c.m2513d(componentName.getPackageName());
                    C1179b.m1354a(f2362a + "|SDK < 21, top app = " + componentName.getPackageName() + ", isInBlackList = " + d);
                    return !d;
                }
            } catch (Exception e) {
                C1179b.m1354a(f2362a + "|" + e.toString());
            }
        } else {
            try {
                List<String> a = C1443k.m2531a();
                if (a != null && !a.isEmpty()) {
                    if (a.size() != 1 || !a.get(0).equals(C1343f.f2169f.getPackageName())) {
                        boolean a2 = C1435c.m2507a(a);
                        C1179b.m1354a(f2362a + "| SDK >= 21, isInBlackList = " + a2);
                        return !a2;
                    }
                }
                C1179b.m1354a(f2362a + "|SDK >= 21, recentList = null, guard = false");
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: a */
    private boolean m2304a(Intent intent) {
        if (intent == null) {
            return false;
        }
        try {
            List<ResolveInfo> queryIntentServices = C1343f.f2169f.getPackageManager().queryIntentServices(intent, 0);
            return queryIntentServices != null && queryIntentServices.size() > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001f A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m2306a(java.lang.String r4, java.lang.String r5, boolean r6) {
        /*
            r3 = this;
            boolean r0 = r3.m2308b(r4)
            r1 = 0
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r3.m2314d(r4)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0016
            boolean r0 = r3.m2310b(r4, r0, r6)
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            if (r0 != 0) goto L_0x001f
            boolean r4 = r3.m2313c(r4, r5, r6)
            if (r4 == 0) goto L_0x0020
        L_0x001f:
            r1 = 1
        L_0x0020:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1404f.m2306a(java.lang.String, java.lang.String, boolean):boolean");
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2307b(String str, String str2, String str3, String str4, String str5) {
        PushTaskBean pushTaskBean = new PushTaskBean();
        pushTaskBean.setAppid(C1343f.f2135a);
        pushTaskBean.setMessageId(str3);
        pushTaskBean.setTaskId(str4);
        pushTaskBean.setId(str5);
        pushTaskBean.setAppKey(C1343f.f2165b);
        C1257f.m1711a().mo14477a(pushTaskBean, str, str2);
    }

    /* renamed from: b */
    private boolean m2308b(String str) {
        if (!C1416f.f2431i || C1435c.m2511c(str)) {
            return false;
        }
        C1257f.m1711a().mo14494h();
        if (C1343f.f2180q == 0) {
            return true;
        }
        return m2303a();
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static boolean m2309b(String str, String str2) {
        boolean z = false;
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) C1343f.f2169f.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
            if (runningServices.isEmpty()) {
                return false;
            }
            int i = 0;
            while (true) {
                if (i < runningServices.size()) {
                    if (runningServices.get(i).service.getClassName().equals(str2) && runningServices.get(i).service.getPackageName().equals(str)) {
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            C1179b.m1354a(f2362a + "|isServiceRunning pkgName = " + str + ", serviceName = " + str2 + "isRunning = " + z);
            return z;
        } catch (Throwable th) {
            C1179b.m1354a(f2362a + "|" + th.toString());
        }
    }

    /* renamed from: b */
    private boolean m2310b(String str, String str2, boolean z) {
        try {
            this.f2363b = "30022";
            this.f2364c = "30023";
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            if (z) {
                intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE);
                intent.putExtra("op_app", C1343f.f2168e);
                intent.putExtra("isSlave", true);
            }
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            C1343f.f2169f.startActivity(intent);
            C1179b.m1354a(f2362a + "|pkg = " + str + ", guardActivity success");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0022 A[Catch:{ Exception -> 0x0041 }] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String m2311c(java.lang.String r4) {
        /*
            r3 = this;
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x0041 }
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch:{ Exception -> 0x0041 }
            r3.f2365d = r0     // Catch:{ Exception -> 0x0041 }
            android.content.pm.PackageManager r0 = r3.f2365d     // Catch:{ Exception -> 0x0041 }
            r1 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r4 = r0.getApplicationInfo(r4, r1)     // Catch:{ Exception -> 0x0041 }
            android.os.Bundle r4 = r4.metaData     // Catch:{ Exception -> 0x0041 }
            if (r4 == 0) goto L_0x0041
            java.util.Set r0 = r4.keySet()     // Catch:{ Exception -> 0x0041 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x0041 }
        L_0x001c:
            boolean r1 = r0.hasNext()     // Catch:{ Exception -> 0x0041 }
            if (r1 == 0) goto L_0x0041
            java.lang.Object r1 = r0.next()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0041 }
            java.lang.String r2 = "PUSH_APPID"
            boolean r2 = r1.equals(r2)     // Catch:{ Exception -> 0x0041 }
            if (r2 != 0) goto L_0x0038
            java.lang.String r2 = "appid"
            boolean r2 = r1.equals(r2)     // Catch:{ Exception -> 0x0041 }
            if (r2 == 0) goto L_0x001c
        L_0x0038:
            java.lang.Object r4 = r4.get(r1)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0041 }
            return r4
        L_0x0041:
            java.lang.String r4 = ""
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1404f.m2311c(java.lang.String):java.lang.String");
    }

    /* renamed from: c */
    private boolean m2312c(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    Intent intent = new Intent();
                    intent.setClassName(str, str2);
                    if (C1343f.f2169f.getPackageManager().resolveActivity(intent, 0) != null) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: c */
    private boolean m2313c(String str, String str2, boolean z) {
        try {
            this.f2363b = "30020";
            this.f2364c = "30021";
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            if (!m2304a(intent)) {
                return false;
            }
            if (z) {
                intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE);
                intent.putExtra("op_app", C1343f.f2168e);
                intent.putExtra("isSlave", true);
            }
            C1343f.f2169f.startService(intent);
            C1179b.m1354a(f2362a + "|pkg = " + str + ", guardService success");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: d */
    private String m2314d(String str) {
        try {
            String c = C1440h.m2526c(str);
            if (TextUtils.isEmpty(c)) {
                c = GActivity.TAG;
            }
            if (m2312c(str, c)) {
                C1179b.m1354a(f2362a + "|guard" + "dynamic p-a " + str + "  " + c);
                return c;
            }
        } catch (Throwable unused) {
        }
        C1179b.m1354a(f2362a + "|guard" + "dynamic p-a " + str + "  check = false");
        return null;
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!C1234k.f1853n || !jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("type")) {
                return null;
            }
            if (!jSONObject.has("pkgname")) {
                if (!jSONObject.has("appid") && !jSONObject.has("cid")) {
                    return null;
                }
            }
            C1410e eVar = new C1410e();
            eVar.setType("wakeupsdk");
            eVar.setActionId(jSONObject.getString("actionid"));
            eVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("pkgname")) {
                eVar.mo14932b(jSONObject.getString("pkgname"));
            } else if (jSONObject.has("cid")) {
                eVar.mo14937d(jSONObject.getString("cid"));
            } else if (jSONObject.has("appid")) {
                eVar.mo14935c(jSONObject.getString("appid"));
            }
            if (jSONObject.has("is_forcestart")) {
                eVar.mo14931a(jSONObject.getBoolean("is_forcestart"));
            }
            if (jSONObject.has(AgooConstants.MESSAGE_ID)) {
                eVar.mo14930a(jSONObject.getString(AgooConstants.MESSAGE_ID));
            }
            return eVar;
        } catch (Exception e) {
            C1179b.m1354a(f2362a + "|" + e.toString());
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d6  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r13, com.igexin.push.core.bean.BaseAction r14) {
        /*
            r12 = this;
            r0 = 1
            if (r13 == 0) goto L_0x00e9
            if (r14 == 0) goto L_0x00e9
            java.lang.String r1 = "30020"
            r12.f2363b = r1
            java.lang.String r1 = "30021"
            r12.f2364c = r1
            r1 = r14
            com.igexin.push.extension.distribution.basic.b.e r1 = (com.igexin.push.extension.distribution.basic.p060b.C1410e) r1
            java.lang.String r2 = r1.mo14934c()
            java.lang.String r3 = "/sdcard/libs//yl"
            java.lang.String r4 = "/sdcard/libs/"
            r5 = 0
            if (r2 != 0) goto L_0x004f
            java.lang.String r6 = r1.mo14938e()
            if (r6 == 0) goto L_0x004f
            java.lang.String r6 = r1.mo14938e()
            java.util.List r6 = r12.m2297a((int) r5, (java.lang.String) r6, (java.lang.String) r4)
            java.lang.String r7 = r1.mo14938e()
            java.util.List r7 = r12.m2297a((int) r5, (java.lang.String) r7, (java.lang.String) r3)
            if (r6 != 0) goto L_0x0037
            if (r7 == 0) goto L_0x0037
            r6 = r7
            goto L_0x003e
        L_0x0037:
            if (r6 == 0) goto L_0x003e
            if (r7 == 0) goto L_0x003e
            r6.addAll(r7)
        L_0x003e:
            if (r6 == 0) goto L_0x004d
            int r7 = r6.size()
            if (r7 != r0) goto L_0x004d
            java.lang.Object r2 = r6.get(r5)
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x004f
        L_0x004d:
            r6 = 0
            goto L_0x0050
        L_0x004f:
            r6 = 1
        L_0x0050:
            if (r2 == 0) goto L_0x005a
            boolean r3 = r1.mo14933b()
            r12.m2300a((java.lang.String) r2, (boolean) r3, (com.igexin.push.core.bean.PushTaskBean) r13, (com.igexin.push.core.bean.BaseAction) r14)
            goto L_0x009d
        L_0x005a:
            java.lang.String r2 = r1.mo14936d()
            if (r2 == 0) goto L_0x009d
            java.lang.String r2 = r1.mo14936d()
            java.util.List r2 = r12.m2297a((int) r0, (java.lang.String) r2, (java.lang.String) r4)
            java.lang.String r4 = r1.mo14936d()
            java.util.List r3 = r12.m2297a((int) r0, (java.lang.String) r4, (java.lang.String) r3)
            if (r2 != 0) goto L_0x0076
            if (r3 == 0) goto L_0x0076
            r2 = r3
            goto L_0x007d
        L_0x0076:
            if (r2 == 0) goto L_0x007d
            if (r3 == 0) goto L_0x007d
            r2.addAll(r3)
        L_0x007d:
            if (r2 == 0) goto L_0x009e
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x009e
            java.util.Iterator r2 = r2.iterator()
        L_0x0089:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x009d
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = r1.mo14933b()
            r12.m2300a((java.lang.String) r3, (boolean) r4, (com.igexin.push.core.bean.PushTaskBean) r13, (com.igexin.push.core.bean.BaseAction) r14)
            goto L_0x0089
        L_0x009d:
            r5 = r6
        L_0x009e:
            java.lang.String r2 = ""
            if (r5 != 0) goto L_0x00cc
            java.lang.String r7 = r13.getMessageId()
            java.lang.String r8 = r13.getTaskId()
            java.lang.String r9 = r1.mo14929a()
            java.lang.String r3 = r1.mo14936d()
            if (r3 == 0) goto L_0x00ba
            java.lang.String r3 = r1.mo14936d()
            r10 = r3
            goto L_0x00bb
        L_0x00ba:
            r10 = r2
        L_0x00bb:
            java.lang.String r3 = r1.mo14934c()
            if (r3 == 0) goto L_0x00c7
            java.lang.String r1 = r1.mo14934c()
            r11 = r1
            goto L_0x00c8
        L_0x00c7:
            r11 = r2
        L_0x00c8:
            r6 = r12
            r6.m2299a(r7, r8, r9, r10, r11)
        L_0x00cc:
            java.lang.String r1 = r14.getDoActionId()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x00e9
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()
            java.lang.String r2 = r13.getTaskId()
            java.lang.String r13 = r13.getMessageId()
            java.lang.String r14 = r14.getDoActionId()
            r1.mo14482a((java.lang.String) r2, (java.lang.String) r13, (java.lang.String) r14)
        L_0x00e9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1404f.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
