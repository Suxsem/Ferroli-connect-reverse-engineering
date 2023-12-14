package com.igexin.push.core.p047a.p048a;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import com.contrarywind.timer.MessageHandler;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1293p;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushConsts;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.l */
public class C1251l implements C1240a {

    /* renamed from: b */
    private static final String f1874b = C1275b.f1913q;

    /* renamed from: c */
    private static final String f1875c = C1275b.f1915s;

    /* renamed from: d */
    private static final String f1876d = C1275b.f1914r;

    /* renamed from: a */
    private PackageManager f1877a;

    /* renamed from: a */
    private String m1689a(String str) {
        if (!C1593r.m3267a(C1343f.f2169f)) {
            return null;
        }
        try {
            List<PackageInfo> installedPackages = C1343f.f2169f.getPackageManager().getInstalledPackages(4);
            if (installedPackages != null) {
                for (PackageInfo next : installedPackages) {
                    if (str.equals(next.packageName)) {
                        ServiceInfo[] serviceInfoArr = next.services;
                        int length = serviceInfoArr.length;
                        int i = 0;
                        while (i < length) {
                            ServiceInfo serviceInfo = serviceInfoArr[i];
                            if (!f1874b.equals(serviceInfo.name) && !f1876d.equals(serviceInfo.name)) {
                                if (!f1875c.equals(serviceInfo.name)) {
                                    i++;
                                }
                            }
                            return serviceInfo.name;
                        }
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            C1179b.m1354a(e.toString());
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x01cf A[SYNTHETIC, Splitter:B:102:0x01cf] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01d7 A[SYNTHETIC, Splitter:B:107:0x01d7] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x01e2 A[SYNTHETIC, Splitter:B:112:0x01e2] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x01ec A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01c4 A[SYNTHETIC, Splitter:B:97:0x01c4] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> m1690a(int r17, java.lang.String r18) {
        /*
            r16 = this;
            r1 = r18
            java.lang.String r2 = "WakeupAction"
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            boolean r0 = com.igexin.push.util.C1593r.m3267a(r0)
            r3 = 0
            if (r0 != 0) goto L_0x000e
            return r3
        L_0x000e:
            java.io.File r4 = new java.io.File
            java.lang.String r0 = "/sdcard/libs/"
            r4.<init>(r0)
            boolean r0 = r4.exists()
            if (r0 != 0) goto L_0x001c
            return r3
        L_0x001c:
            java.lang.String[] r5 = r4.list()
            if (r5 != 0) goto L_0x0023
            return r3
        L_0x0023:
            r6 = 0
            r8 = r3
            r7 = 0
        L_0x0026:
            int r0 = r5.length
            r9 = 1
            if (r7 >= r0) goto L_0x01f0
            r0 = r5[r7]
            java.lang.String r10 = ".db"
            int r0 = r0.indexOf(r10)
            if (r0 <= 0) goto L_0x01ec
            r0 = r5[r7]
            java.lang.String r10 = "app.db"
            boolean r0 = r0.equals(r10)
            if (r0 != 0) goto L_0x01ec
            r0 = r5[r7]
            java.lang.String r10 = "imsi.db"
            boolean r0 = r0.equals(r10)
            if (r0 != 0) goto L_0x01ec
            r0 = r5[r7]
            java.lang.String r10 = "com.igexin.sdk.deviceId.db"
            boolean r0 = r0.equals(r10)
            if (r0 != 0) goto L_0x01ec
            r0 = r5[r7]     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r10 = r5[r7]     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            int r10 = r10.length()     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            int r10 = r10 + -3
            java.lang.String r0 = r0.substring(r6, r10)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r11.<init>()     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r11.append(r4)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.lang.String r12 = "/"
            r11.append(r12)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r12 = r5[r7]     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r11.append(r12)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r11 = 1024(0x400, float:1.435E-42)
            byte[] r11 = new byte[r11]     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            r12.<init>(r10)     // Catch:{ Exception -> 0x01a9, all -> 0x01a4 }
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x01a1, all -> 0x019d }
            r10.<init>()     // Catch:{ Exception -> 0x01a1, all -> 0x019d }
        L_0x0089:
            int r13 = r12.read(r11)     // Catch:{ Exception -> 0x019b }
            r14 = -1
            if (r13 == r14) goto L_0x0094
            r10.write(r11, r6, r13)     // Catch:{ Exception -> 0x019b }
            goto L_0x0089
        L_0x0094:
            byte[] r11 = r10.toByteArray()     // Catch:{ Exception -> 0x019b }
            java.lang.String r13 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Exception -> 0x019b }
            if (r13 != 0) goto L_0x009f
            java.lang.String r13 = "cantgetimei"
            goto L_0x00a1
        L_0x009f:
            java.lang.String r13 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Exception -> 0x019b }
        L_0x00a1:
            java.lang.String r13 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r13)     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = new java.lang.String     // Catch:{ Exception -> 0x019b }
            byte[] r11 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r11, r13)     // Catch:{ Exception -> 0x019b }
            r14.<init>(r11)     // Catch:{ Exception -> 0x019b }
            java.lang.String r11 = "\\|"
            java.lang.String[] r11 = r14.split(r11)     // Catch:{ Exception -> 0x019b }
            java.io.PrintStream r13 = java.lang.System.out     // Catch:{ Exception -> 0x019b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b }
            r14.<init>()     // Catch:{ Exception -> 0x019b }
            java.lang.String r15 = "length="
            r14.append(r15)     // Catch:{ Exception -> 0x019b }
            int r15 = r11.length     // Catch:{ Exception -> 0x019b }
            r14.append(r15)     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x019b }
            r13.println(r14)     // Catch:{ Exception -> 0x019b }
            r13 = r11[r6]     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = "v"
            boolean r13 = r13.startsWith(r14)     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = "null"
            if (r13 == 0) goto L_0x00f3
            r13 = r11[r6]     // Catch:{ Exception -> 0x019b }
            boolean r13 = r13.contains(r14)     // Catch:{ Exception -> 0x019b }
            if (r13 == 0) goto L_0x00e9
            r13 = r11[r6]     // Catch:{ Exception -> 0x019b }
            r15 = 7
            java.lang.String r13 = r13.substring(r15)     // Catch:{ Exception -> 0x019b }
            r11[r6] = r13     // Catch:{ Exception -> 0x019b }
            goto L_0x00f3
        L_0x00e9:
            r13 = r11[r6]     // Catch:{ Exception -> 0x019b }
            r15 = 20
            java.lang.String r13 = r13.substring(r15)     // Catch:{ Exception -> 0x019b }
            r11[r6] = r13     // Catch:{ Exception -> 0x019b }
        L_0x00f3:
            int r13 = r11.length     // Catch:{ Exception -> 0x019b }
            r15 = 2
            if (r13 <= r15) goto L_0x0117
            r13 = r11[r15]     // Catch:{ Exception -> 0x019b }
            if (r13 == 0) goto L_0x0118
            boolean r14 = r13.equals(r14)     // Catch:{ Exception -> 0x019b }
            if (r14 == 0) goto L_0x0102
            r13 = r3
        L_0x0102:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b }
            r14.<init>()     // Catch:{ Exception -> 0x019b }
            java.lang.String r15 = "WakeupAction get check form db file : "
            r14.append(r15)     // Catch:{ Exception -> 0x019b }
            r14.append(r13)     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x019b }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r14)     // Catch:{ Exception -> 0x019b }
            goto L_0x0118
        L_0x0117:
            r13 = r3
        L_0x0118:
            if (r13 != 0) goto L_0x0134
            r13 = r11[r6]     // Catch:{ Exception -> 0x019b }
            java.lang.String r13 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r13)     // Catch:{ Exception -> 0x019b }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b }
            r14.<init>()     // Catch:{ Exception -> 0x019b }
            java.lang.String r15 = "WakeupAction check cid form md5 session : "
            r14.append(r15)     // Catch:{ Exception -> 0x019b }
            r14.append(r13)     // Catch:{ Exception -> 0x019b }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x019b }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r14)     // Catch:{ Exception -> 0x019b }
        L_0x0134:
            if (r17 != 0) goto L_0x015a
            boolean r9 = r1.equals(r13)     // Catch:{ Exception -> 0x019b }
            if (r9 == 0) goto L_0x0186
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Exception -> 0x019b }
            r9.<init>()     // Catch:{ Exception -> 0x019b }
            r9.add(r0)     // Catch:{ Exception -> 0x0157 }
            r12.close()     // Catch:{ IOException -> 0x0148 }
            goto L_0x014d
        L_0x0148:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x014d:
            r10.close()     // Catch:{ IOException -> 0x0151 }
            goto L_0x0156
        L_0x0151:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x0156:
            return r9
        L_0x0157:
            r0 = move-exception
            r8 = r9
            goto L_0x01ac
        L_0x015a:
            int r13 = r11.length     // Catch:{ Exception -> 0x019b }
            if (r13 <= r9) goto L_0x0186
            r13 = r11[r9]     // Catch:{ Exception -> 0x019b }
            boolean r13 = r1.equals(r13)     // Catch:{ Exception -> 0x019b }
            if (r13 == 0) goto L_0x0170
            if (r8 != 0) goto L_0x016d
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ Exception -> 0x019b }
            r13.<init>()     // Catch:{ Exception -> 0x019b }
            r8 = r13
        L_0x016d:
            r8.add(r0)     // Catch:{ Exception -> 0x019b }
        L_0x0170:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019b }
            r0.<init>()     // Catch:{ Exception -> 0x019b }
            java.lang.String r13 = "check from appid="
            r0.append(r13)     // Catch:{ Exception -> 0x019b }
            r9 = r11[r9]     // Catch:{ Exception -> 0x019b }
            r0.append(r9)     // Catch:{ Exception -> 0x019b }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x019b }
            com.igexin.p032b.p033a.p039c.C1179b.m1355a(r2, r0)     // Catch:{ Exception -> 0x019b }
        L_0x0186:
            r12.close()     // Catch:{ IOException -> 0x018a }
            goto L_0x018f
        L_0x018a:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
        L_0x018f:
            r10.close()     // Catch:{ IOException -> 0x0194 }
            goto L_0x01ec
        L_0x0194:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
            goto L_0x01ec
        L_0x019b:
            r0 = move-exception
            goto L_0x01ac
        L_0x019d:
            r0 = move-exception
            r1 = r0
            r10 = r3
            goto L_0x01d5
        L_0x01a1:
            r0 = move-exception
            r10 = r3
            goto L_0x01ac
        L_0x01a4:
            r0 = move-exception
            r1 = r0
            r10 = r3
            r12 = r10
            goto L_0x01d5
        L_0x01a9:
            r0 = move-exception
            r10 = r3
            r12 = r10
        L_0x01ac:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d3 }
            r9.<init>()     // Catch:{ all -> 0x01d3 }
            r9.append(r2)     // Catch:{ all -> 0x01d3 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01d3 }
            r9.append(r0)     // Catch:{ all -> 0x01d3 }
            java.lang.String r0 = r9.toString()     // Catch:{ all -> 0x01d3 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x01d3 }
            if (r12 == 0) goto L_0x01cd
            r12.close()     // Catch:{ IOException -> 0x01c8 }
            goto L_0x01cd
        L_0x01c8:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
        L_0x01cd:
            if (r10 == 0) goto L_0x01ec
            r10.close()     // Catch:{ IOException -> 0x0194 }
            goto L_0x01ec
        L_0x01d3:
            r0 = move-exception
            r1 = r0
        L_0x01d5:
            if (r12 == 0) goto L_0x01e0
            r12.close()     // Catch:{ IOException -> 0x01db }
            goto L_0x01e0
        L_0x01db:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x01e0:
            if (r10 == 0) goto L_0x01eb
            r10.close()     // Catch:{ IOException -> 0x01e6 }
            goto L_0x01eb
        L_0x01e6:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x01eb:
            throw r1
        L_0x01ec:
            int r7 = r7 + 1
            goto L_0x0026
        L_0x01f0:
            if (r8 == 0) goto L_0x0212
            int r0 = r8.size()
            if (r0 != r9) goto L_0x0212
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "WakeupAction check finished, final pkg is  : "
            r0.append(r1)
            java.lang.Object r1 = r8.get(r6)
            java.lang.String r1 = (java.lang.String) r1
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
        L_0x0212:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1251l.m1690a(int, java.lang.String):java.util.List");
    }

    /* renamed from: a */
    private void m1692a(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder();
        sb.append(C1343f.f2169f.getPackageName());
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append(str4);
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append(str5);
        sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
        sb.append("-1");
        m1698b("30025", sb.toString(), str, str2, str3);
        C1179b.m1354a("feedback actionId=30025 result=" + sb.toString());
    }

    /* renamed from: a */
    private void m1693a(String str, boolean z, PushTaskBean pushTaskBean, BaseAction baseAction) {
        String messageId;
        String taskId;
        String a;
        StringBuffer stringBuffer;
        try {
            String a2 = m1689a(str);
            messageId = pushTaskBean.getMessageId();
            taskId = pushTaskBean.getTaskId();
            a = ((C1293p) baseAction).mo14636a();
            if (a2 != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("messageId", messageId);
                hashMap.put("taskId", taskId);
                hashMap.put(AgooConstants.MESSAGE_ID, a);
                hashMap.put("pkgName", str);
                stringBuffer = new StringBuffer();
                stringBuffer.append(C1343f.f2169f.getPackageName());
                stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                stringBuffer.append(m1697b(str));
                stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                stringBuffer.append(str);
                stringBuffer.append("/");
                if (a2.equals(f1874b)) {
                    stringBuffer.append(f1874b);
                    stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                    if (!m1696a(str, f1874b)) {
                        if (z) {
                            Intent intent = new Intent();
                            intent.setClassName(str, a2);
                            intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE);
                            intent.putExtra("op_app", C1343f.f2168e);
                            intent.putExtra("isSlave", true);
                            C1343f.f2169f.startService(intent);
                        } else if (!m1699b(str, a2)) {
                            m1694a(stringBuffer, messageId, taskId, a);
                            return;
                        }
                        hashMap.put("serviceName", f1874b);
                        m1695a((Map<String, String>) hashMap);
                        stringBuffer.append("1");
                        m1698b("30025", stringBuffer.toString(), messageId, taskId, a);
                        C1179b.m1354a("feedback actionId=30025 result=" + stringBuffer.toString());
                        return;
                    }
                } else if (a2.equals(f1876d)) {
                    stringBuffer.append(f1876d);
                    stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                    if (!m1696a(str, f1876d)) {
                        if (!m1699b(str, a2)) {
                            m1694a(stringBuffer, messageId, taskId, a);
                            return;
                        }
                        hashMap.put("serviceName", f1876d);
                        m1695a((Map<String, String>) hashMap);
                        stringBuffer.append("1");
                        m1698b("30025", stringBuffer.toString(), messageId, taskId, a);
                        C1179b.m1354a("feedback actionId=30025 result=" + stringBuffer.toString());
                        return;
                    }
                } else {
                    if (a2.equals(f1875c)) {
                        stringBuffer.append(f1875c);
                        stringBuffer.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                        if (!m1696a(str, f1875c)) {
                            if (!m1699b(str, a2)) {
                                m1694a(stringBuffer, messageId, taskId, a);
                                return;
                            }
                            hashMap.put("serviceName", f1875c);
                            m1695a((Map<String, String>) hashMap);
                            stringBuffer.append("1");
                        }
                    }
                    m1698b("30025", stringBuffer.toString(), messageId, taskId, a);
                    C1179b.m1354a("feedback actionId=30025 result=" + stringBuffer.toString());
                    return;
                }
                stringBuffer.append("0");
                m1698b("30025", stringBuffer.toString(), messageId, taskId, a);
                C1179b.m1354a("feedback actionId=30025 result=" + stringBuffer.toString());
                return;
            }
            m1692a(messageId, taskId, a, ((C1293p) baseAction).mo14643d() != null ? ((C1293p) baseAction).mo14643d() : "", ((C1293p) baseAction).mo14641c() != null ? ((C1293p) baseAction).mo14641c() : "");
        } catch (Exception e) {
            C1179b.m1354a(e.toString());
            m1694a(stringBuffer, messageId, taskId, a);
        } catch (Throwable th) {
            C1179b.m1354a("WakeupAction|" + th.toString());
        }
    }

    /* renamed from: a */
    private void m1694a(StringBuffer stringBuffer, String str, String str2, String str3) {
        stringBuffer.append("-1");
        m1698b("30025", stringBuffer.toString(), str, str2, str3);
        C1179b.m1354a("feedback actionId=30025 result=" + stringBuffer.toString());
    }

    /* renamed from: a */
    private void m1695a(Map<String, String> map) {
        C1340e.m2032a().mo14703a((C1575h) new C1252m(this, 180000, map));
    }

    /* renamed from: a */
    public static boolean m1696a(String str, String str2) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) C1343f.f2169f.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(str2) && runningServices.get(i).service.getPackageName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public String m1697b(String str) {
        try {
            this.f1877a = C1343f.f2169f.getPackageManager();
            Bundle bundle = this.f1877a.getApplicationInfo(str, 128).metaData;
            if (bundle == null) {
                return "";
            }
            for (String str2 : bundle.keySet()) {
                if (str2.equals(AssistPushConsts.GETUI_APPID)) {
                    return bundle.get(str2).toString();
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m1698b(String str, String str2, String str3, String str4, String str5) {
        PushTaskBean pushTaskBean = new PushTaskBean();
        pushTaskBean.setAppid(C1343f.f2135a);
        pushTaskBean.setMessageId(str3);
        pushTaskBean.setTaskId(str4);
        pushTaskBean.setId(str5);
        pushTaskBean.setAppKey(C1343f.f2165b);
        C1257f.m1711a().mo14477a(pushTaskBean, str, str2);
    }

    /* renamed from: b */
    private boolean m1699b(String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            C1343f.f2169f.startService(intent);
            return true;
        } catch (Exception e) {
            C1179b.m1354a("WakeupAction|" + e.toString());
            return false;
        }
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
            C1293p pVar = new C1293p();
            pVar.setType("wakeupsdk");
            pVar.setActionId(jSONObject.getString("actionid"));
            pVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("pkgname")) {
                pVar.mo14639b(jSONObject.getString("pkgname"));
            } else if (jSONObject.has("cid")) {
                pVar.mo14644d(jSONObject.getString("cid"));
            } else if (jSONObject.has("appid")) {
                pVar.mo14642c(jSONObject.getString("appid"));
            }
            if (jSONObject.has("is_forcestart")) {
                pVar.mo14638a(jSONObject.getBoolean("is_forcestart"));
            }
            if (jSONObject.has(AgooConstants.MESSAGE_ID)) {
                pVar.mo14637a(jSONObject.getString(AgooConstants.MESSAGE_ID));
            }
            return pVar;
        } catch (JSONException e) {
            C1179b.m1354a(e.toString());
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a0  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r11, com.igexin.push.core.bean.BaseAction r12) {
        /*
            r10 = this;
            r0 = 1
            if (r11 == 0) goto L_0x00b3
            if (r12 == 0) goto L_0x00b3
            r1 = r12
            com.igexin.push.core.bean.p r1 = (com.igexin.push.core.bean.C1293p) r1
            java.lang.String r2 = r1.mo14641c()
            r3 = 0
            if (r2 != 0) goto L_0x002e
            java.lang.String r4 = r1.mo14645e()
            if (r4 == 0) goto L_0x002e
            java.lang.String r4 = r1.mo14645e()
            java.util.List r4 = r10.m1690a((int) r3, (java.lang.String) r4)
            if (r4 == 0) goto L_0x002c
            int r5 = r4.size()
            if (r5 != r0) goto L_0x002c
            java.lang.Object r2 = r4.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x002e
        L_0x002c:
            r4 = 0
            goto L_0x002f
        L_0x002e:
            r4 = 1
        L_0x002f:
            if (r2 == 0) goto L_0x0039
            boolean r3 = r1.mo14640b()
            r10.m1693a((java.lang.String) r2, (boolean) r3, (com.igexin.push.core.bean.PushTaskBean) r11, (com.igexin.push.core.bean.BaseAction) r12)
            goto L_0x0067
        L_0x0039:
            java.lang.String r2 = r1.mo14643d()
            if (r2 == 0) goto L_0x0067
            java.lang.String r2 = r1.mo14643d()
            java.util.List r2 = r10.m1690a((int) r0, (java.lang.String) r2)
            if (r2 == 0) goto L_0x0068
            int r5 = r2.size()
            if (r5 <= 0) goto L_0x0068
            java.util.Iterator r2 = r2.iterator()
        L_0x0053:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0067
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            boolean r5 = r1.mo14640b()
            r10.m1693a((java.lang.String) r3, (boolean) r5, (com.igexin.push.core.bean.PushTaskBean) r11, (com.igexin.push.core.bean.BaseAction) r12)
            goto L_0x0053
        L_0x0067:
            r3 = r4
        L_0x0068:
            java.lang.String r2 = ""
            if (r3 != 0) goto L_0x0096
            java.lang.String r5 = r11.getMessageId()
            java.lang.String r6 = r11.getTaskId()
            java.lang.String r7 = r1.mo14636a()
            java.lang.String r3 = r1.mo14643d()
            if (r3 == 0) goto L_0x0084
            java.lang.String r3 = r1.mo14643d()
            r8 = r3
            goto L_0x0085
        L_0x0084:
            r8 = r2
        L_0x0085:
            java.lang.String r3 = r1.mo14641c()
            if (r3 == 0) goto L_0x0091
            java.lang.String r1 = r1.mo14641c()
            r9 = r1
            goto L_0x0092
        L_0x0091:
            r9 = r2
        L_0x0092:
            r4 = r10
            r4.m1692a(r5, r6, r7, r8, r9)
        L_0x0096:
            java.lang.String r1 = r12.getDoActionId()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x00b3
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()
            java.lang.String r2 = r11.getTaskId()
            java.lang.String r11 = r11.getMessageId()
            java.lang.String r12 = r12.getDoActionId()
            r1.mo14482a((java.lang.String) r2, (java.lang.String) r11, (java.lang.String) r12)
        L_0x00b3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1251l.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
