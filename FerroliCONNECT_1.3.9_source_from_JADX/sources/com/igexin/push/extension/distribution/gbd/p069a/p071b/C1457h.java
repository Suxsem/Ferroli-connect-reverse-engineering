package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anetwork.channel.util.RequestConstant;
import com.contrarywind.timer.MessageHandler;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1550r;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.h */
public class C1457h {

    /* renamed from: d */
    private static C1457h f2516d;

    /* renamed from: a */
    private Context f2517a;

    /* renamed from: b */
    private long f2518b = 0;

    /* renamed from: c */
    private SimpleDateFormat f2519c;

    private C1457h(Context context) {
        this.f2517a = context;
        this.f2519c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    /* renamed from: a */
    public static C1457h m2610a() {
        if (f2516d == null) {
            f2516d = new C1457h(C1490c.f2747a);
        }
        return f2516d;
    }

    /* renamed from: a */
    private boolean m2611a(String str, Intent intent) {
        if (str.equals("0")) {
            if (!C1541i.m3007a(intent, this.f2517a)) {
                return false;
            }
            C1540h.m2995a("GBD_GOSA", "intent start service");
            this.f2517a.startService(intent);
            return true;
        } else if (str.equals("1")) {
            if (!C1541i.m3013b(intent, this.f2517a)) {
                return false;
            }
            C1540h.m2995a("GBD_GOSA", "intent start broadcast");
            this.f2517a.sendBroadcast(intent);
            return true;
        } else if (!str.equals("2") || !C1541i.m3018c(intent, this.f2517a)) {
            return false;
        } else {
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            C1540h.m2995a("GBD_GOSA", "intent start activity");
            this.f2517a.startActivity(intent);
            return true;
        }
    }

    /* renamed from: d */
    private boolean m2612d() {
        C1540h.m2997b("GBD_GOSA", "look enable = " + C1488a.f2716v);
        C1540h.m2997b("GBD_GOSA", "look service = " + C1488a.f2718x);
        if (C1488a.f2716v && System.currentTimeMillis() - this.f2518b > 360000) {
            return C1507f.m2840a().mo15127e();
        }
        C1540h.m2997b("GBD_GOSA", "gurad other service time not meet");
        return false;
    }

    /* renamed from: e */
    private void m2613e() {
        C1458i.m2619a(SchedulerSupport.NONE, 0, this.f2519c.format(new Date()), 3);
        this.f2518b = System.currentTimeMillis();
        m2614f();
        m2615g();
    }

    /* renamed from: f */
    private void m2614f() {
        String[] strArr;
        String str;
        if (!TextUtils.isEmpty(C1488a.f2718x)) {
            try {
                String[] split = C1488a.f2718x.split(",");
                if (split.length > 0) {
                    String format = this.f2519c.format(new Date());
                    String packageName = this.f2517a.getPackageName();
                    List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.f2517a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
                    char c = 0;
                    int i = 0;
                    while (i < split.length) {
                        String[] split2 = split[i].split("/");
                        int i2 = 2;
                        if (split2.length > 2 && !packageName.equals(split2[c])) {
                            try {
                                String str2 = split2[c];
                                String str3 = split2[1];
                                if (!TextUtils.isEmpty(str2)) {
                                    if (!TextUtils.isEmpty(str3)) {
                                        C1540h.m2997b("GBD_GOSA", "other service check pkgname srvname pass");
                                        Intent intent = new Intent();
                                        intent.setClassName(str2, str3);
                                        if (C1541i.m3007a(intent, this.f2517a)) {
                                            C1540h.m2997b("GBD_GOSA", "other service exist..");
                                            ArrayList arrayList = new ArrayList();
                                            HashMap hashMap = new HashMap();
                                            while (i2 < split2.length) {
                                                try {
                                                    strArr = split;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    strArr = split;
                                                    c = 0;
                                                    C1540h.m2996a(th);
                                                    C1540h.m2997b("GBD_GOSA", "Looker error:" + th.toString());
                                                    i++;
                                                    split = strArr;
                                                }
                                                try {
                                                    hashMap.put(split2[i2], Boolean.valueOf(C1541i.m3008a(split2[i2], str2, runningServices)));
                                                    arrayList.add(split2[i2]);
                                                    i2++;
                                                    split = strArr;
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    c = 0;
                                                    C1540h.m2996a(th);
                                                    C1540h.m2997b("GBD_GOSA", "Looker error:" + th.toString());
                                                    i++;
                                                    split = strArr;
                                                }
                                            }
                                            strArr = split;
                                            str = str2 + "," + str3;
                                            C1458i.m2618a(str);
                                            if (!hashMap.toString().contains(RequestConstant.TRUE)) {
                                                C1540h.m2997b("GBD_GOSA", "guard other service start..");
                                                HashMap hashMap2 = new HashMap();
                                                hashMap2.put("pkgName", str2);
                                                hashMap2.put("srvName", str3);
                                                hashMap2.put("datetime", format);
                                                hashMap2.put("checkList", arrayList);
                                                C1458i.m2619a(str, 0, format, 0);
                                                intent.putExtra("intent_come_from", "getui");
                                                if (str2.contains("com.sina.weibo")) {
                                                    C1540h.m2995a("GBD_GOSA", "start weibo service, add deviceid");
                                                    intent.putExtra("clientId", C1343f.f2187x + "|" + C1550r.m3044a().mo15182b());
                                                }
                                                this.f2517a.startService(intent);
                                                C1540h.m2997b("GBD_GOSA", "look---startService pkgName = " + str2 + " srvName = " + str3);
                                                C1458i.m2620a(hashMap2, 0, 0);
                                                c = 0;
                                            } else {
                                                C1540h.m2997b("GBD_GOSA", "other service already run..");
                                                c = 0;
                                                try {
                                                    C1458i.m2619a(str, 0, format, -1);
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                }
                                            }
                                            i++;
                                            split = strArr;
                                        }
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                strArr = split;
                                C1540h.m2996a(th);
                                C1540h.m2997b("GBD_GOSA", "Looker error:" + th.toString());
                                i++;
                                split = strArr;
                            }
                        }
                        strArr = split;
                        i++;
                        split = strArr;
                    }
                }
            } catch (Throwable th5) {
                C1540h.m2997b("GBD_GOSA", th5.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008a, code lost:
        if (r9.equals("1") == false) goto L_0x0064;
     */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2615g() {
        /*
            r20 = this;
            r1 = r20
            java.lang.String r2 = ","
            java.lang.String r3 = "GBD_GOSA"
            java.lang.String r0 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2719y
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x000f
            return
        L_0x000f:
            java.lang.String r0 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2719y     // Catch:{ Throwable -> 0x01db }
            java.lang.String[] r4 = r0.split(r2)     // Catch:{ Throwable -> 0x01db }
            android.content.Context r0 = r1.f2517a     // Catch:{ Throwable -> 0x01db }
            java.lang.String r5 = "activity"
            java.lang.Object r0 = r0.getSystemService(r5)     // Catch:{ Throwable -> 0x01db }
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0     // Catch:{ Throwable -> 0x01db }
            r5 = 2000(0x7d0, float:2.803E-42)
            java.util.List r5 = r0.getRunningServices(r5)     // Catch:{ Throwable -> 0x01db }
            int r6 = r4.length     // Catch:{ Throwable -> 0x01db }
            r7 = 0
            r8 = 0
        L_0x0028:
            if (r8 >= r6) goto L_0x01df
            r0 = r4[r8]     // Catch:{ Throwable -> 0x01db }
            java.lang.String r9 = "\\|"
            java.lang.String[] r0 = r0.split(r9)     // Catch:{ Throwable -> 0x01c3 }
            int r9 = r0.length     // Catch:{ Throwable -> 0x01c3 }
            r10 = 4
            if (r9 < r10) goto L_0x0064
            r9 = r0[r7]     // Catch:{ Throwable -> 0x01c3 }
            r10 = 1
            r10 = r0[r10]     // Catch:{ Throwable -> 0x01c3 }
            android.content.Intent r10 = android.content.Intent.parseUri(r10, r7)     // Catch:{ Throwable -> 0x01c3 }
            r11 = 2
            r12 = r0[r11]     // Catch:{ Throwable -> 0x01c3 }
            r13 = 3
            r0 = r0[r13]     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r13 = "/"
            java.lang.String[] r0 = r0.split(r13)     // Catch:{ Throwable -> 0x01c3 }
            java.text.SimpleDateFormat r13 = r1.f2519c     // Catch:{ Throwable -> 0x01c3 }
            java.util.Date r14 = new java.util.Date     // Catch:{ Throwable -> 0x01c3 }
            r14.<init>()     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r13 = r13.format(r14)     // Catch:{ Throwable -> 0x01c3 }
            boolean r14 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Throwable -> 0x01c3 }
            if (r14 == 0) goto L_0x006f
            r14 = r0[r7]     // Catch:{ Throwable -> 0x01c3 }
            boolean r14 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x01c3 }
            if (r14 == 0) goto L_0x006f
        L_0x0064:
            r16 = r2
            r17 = r4
            r18 = r6
            r19 = r8
        L_0x006c:
            r4 = 0
            goto L_0x01d0
        L_0x006f:
            java.lang.String r14 = "other service check pkgname srvname pass"
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r14)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r14 = "0"
            boolean r14 = r9.equals(r14)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r15 = "2"
            if (r14 != 0) goto L_0x008c
            boolean r14 = r9.equals(r15)     // Catch:{ Throwable -> 0x01c3 }
            if (r14 != 0) goto L_0x008c
            java.lang.String r14 = "1"
            boolean r14 = r9.equals(r14)     // Catch:{ Throwable -> 0x01c3 }
            if (r14 == 0) goto L_0x0064
        L_0x008c:
            boolean r14 = r9.equals(r15)     // Catch:{ Throwable -> 0x01c3 }
            if (r14 == 0) goto L_0x00a2
            com.igexin.push.extension.distribution.gbd.a.b.d r14 = com.igexin.push.extension.distribution.gbd.p069a.p071b.C1453d.m2573a()     // Catch:{ Throwable -> 0x01c3 }
            boolean r14 = r14.mo15006e()     // Catch:{ Throwable -> 0x01c3 }
            if (r14 != 0) goto L_0x00a2
            java.lang.String r0 = "third guard intent activity not safe."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r0)     // Catch:{ Throwable -> 0x01c3 }
            goto L_0x0064
        L_0x00a2:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c3 }
            r14.<init>()     // Catch:{ Throwable -> 0x01c3 }
            r14.append(r12)     // Catch:{ Throwable -> 0x01c3 }
            r14.append(r2)     // Catch:{ Throwable -> 0x01c3 }
            r15 = r0[r7]     // Catch:{ Throwable -> 0x01c3 }
            r14.append(r15)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x01c3 }
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2618a(r14)     // Catch:{ Throwable -> 0x01c3 }
            android.content.Intent r15 = new android.content.Intent     // Catch:{ Throwable -> 0x01c3 }
            r15.<init>()     // Catch:{ Throwable -> 0x01c3 }
            r11 = r0[r7]     // Catch:{ Throwable -> 0x01c3 }
            r15.setClassName(r12, r11)     // Catch:{ Throwable -> 0x01c3 }
            android.content.Context r11 = r1.f2517a     // Catch:{ Throwable -> 0x01c3 }
            boolean r11 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3007a((android.content.Intent) r15, (android.content.Context) r11)     // Catch:{ Throwable -> 0x01c3 }
            java.lang.String r15 = " srvName = "
            java.lang.String r7 = "pkgName = "
            if (r11 == 0) goto L_0x018a
            java.lang.String r11 = "guard other service start.."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r11)     // Catch:{ Throwable -> 0x01c3 }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ Throwable -> 0x01c3 }
            r11.<init>()     // Catch:{ Throwable -> 0x01c3 }
            r16 = r2
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Throwable -> 0x0188 }
            r2.<init>()     // Catch:{ Throwable -> 0x0188 }
            r17 = r4
            r18 = r6
            r4 = 0
        L_0x00e5:
            int r6 = r0.length     // Catch:{ Throwable -> 0x0186 }
            if (r4 >= r6) goto L_0x0103
            r6 = r0[r4]     // Catch:{ Throwable -> 0x0186 }
            r19 = r8
            r8 = r0[r4]     // Catch:{ Throwable -> 0x01c1 }
            boolean r8 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3008a(r8, r12, r5)     // Catch:{ Throwable -> 0x01c1 }
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ Throwable -> 0x01c1 }
            r2.put(r6, r8)     // Catch:{ Throwable -> 0x01c1 }
            r6 = r0[r4]     // Catch:{ Throwable -> 0x01c1 }
            r11.add(r6)     // Catch:{ Throwable -> 0x01c1 }
            int r4 = r4 + 1
            r8 = r19
            goto L_0x00e5
        L_0x0103:
            r19 = r8
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01c1 }
            java.lang.String r4 = "true"
            boolean r2 = r2.contains(r4)     // Catch:{ Throwable -> 0x01c1 }
            if (r2 != 0) goto L_0x017f
            boolean r2 = r1.m2611a(r9, r10)     // Catch:{ Throwable -> 0x0174 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r4.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r6 = "intent guard-- type = "
            r4.append(r6)     // Catch:{ Throwable -> 0x0174 }
            r4.append(r9)     // Catch:{ Throwable -> 0x0174 }
            r4.append(r7)     // Catch:{ Throwable -> 0x0174 }
            r4.append(r12)     // Catch:{ Throwable -> 0x0174 }
            r4.append(r15)     // Catch:{ Throwable -> 0x0174 }
            r6 = 0
            r7 = r0[r6]     // Catch:{ Throwable -> 0x0174 }
            r4.append(r7)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0174 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r4)     // Catch:{ Throwable -> 0x0174 }
            if (r2 == 0) goto L_0x006c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0174 }
            r4.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r6 = "guard intentExist = "
            r4.append(r6)     // Catch:{ Throwable -> 0x0174 }
            r4.append(r2)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r2 = r4.toString()     // Catch:{ Throwable -> 0x0174 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r2)     // Catch:{ Throwable -> 0x0174 }
            r2 = 0
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2619a(r14, r2, r13, r2)     // Catch:{ Throwable -> 0x0174 }
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Throwable -> 0x0174 }
            r2.<init>()     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = "pkgName"
            r2.put(r4, r12)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r4 = "srvName"
            r6 = 0
            r0 = r0[r6]     // Catch:{ Throwable -> 0x0174 }
            r2.put(r4, r0)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = "datetime"
            r2.put(r0, r13)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r0 = "checkList"
            r2.put(r0, r11)     // Catch:{ Throwable -> 0x0174 }
            r4 = 0
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2620a(r2, r4, r4)     // Catch:{ Throwable -> 0x0174 }
            goto L_0x006c
        L_0x0174:
            r0 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ Throwable -> 0x01c1 }
            r2 = 2
            r4 = 0
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2619a(r14, r4, r13, r2)     // Catch:{ Throwable -> 0x01bf }
            goto L_0x006c
        L_0x017f:
            r4 = 0
            r0 = -1
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2619a(r14, r4, r13, r0)     // Catch:{ Throwable -> 0x01bf }
            goto L_0x006c
        L_0x0186:
            r0 = move-exception
            goto L_0x01ca
        L_0x0188:
            r0 = move-exception
            goto L_0x01c6
        L_0x018a:
            r16 = r2
            r17 = r4
            r18 = r6
            r19 = r8
            boolean r2 = r1.m2611a(r9, r10)     // Catch:{ Throwable -> 0x01c1 }
            if (r2 == 0) goto L_0x006c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01c1 }
            r2.<init>()     // Catch:{ Throwable -> 0x01c1 }
            java.lang.String r4 = "guard service not Exist type = "
            r2.append(r4)     // Catch:{ Throwable -> 0x01c1 }
            r2.append(r9)     // Catch:{ Throwable -> 0x01c1 }
            r2.append(r7)     // Catch:{ Throwable -> 0x01c1 }
            r2.append(r12)     // Catch:{ Throwable -> 0x01c1 }
            r2.append(r15)     // Catch:{ Throwable -> 0x01c1 }
            r4 = 0
            r0 = r0[r4]     // Catch:{ Throwable -> 0x01bf }
            r2.append(r0)     // Catch:{ Throwable -> 0x01bf }
            java.lang.String r0 = r2.toString()     // Catch:{ Throwable -> 0x01bf }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r0)     // Catch:{ Throwable -> 0x01bf }
            com.igexin.push.extension.distribution.gbd.p069a.p071b.C1458i.m2619a(r14, r4, r13, r4)     // Catch:{ Throwable -> 0x01bf }
            goto L_0x01d0
        L_0x01bf:
            r0 = move-exception
            goto L_0x01cd
        L_0x01c1:
            r0 = move-exception
            goto L_0x01cc
        L_0x01c3:
            r0 = move-exception
            r16 = r2
        L_0x01c6:
            r17 = r4
            r18 = r6
        L_0x01ca:
            r19 = r8
        L_0x01cc:
            r4 = 0
        L_0x01cd:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ Throwable -> 0x01db }
        L_0x01d0:
            int r8 = r19 + 1
            r2 = r16
            r4 = r17
            r6 = r18
            r7 = 0
            goto L_0x0028
        L_0x01db:
            r0 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)
        L_0x01df:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p071b.C1457h.m2615g():void");
    }

    /* renamed from: b */
    public void mo15007b() {
        try {
            String packageName = this.f2517a.getPackageName();
            String[] split = C1488a.f2718x.split(",");
            if (split.length > 0) {
                for (String split2 : split) {
                    String[] split3 = split2.split("/");
                    if (split3.length > 2 && !packageName.equals(split3[0])) {
                        C1458i.m2618a(split3[0] + "," + split3[1]);
                    }
                }
            }
            String[] split4 = C1488a.f2719y.split(",");
            if (split4.length > 0) {
                for (String split5 : split4) {
                    String[] split6 = split5.split("\\|");
                    if (split6.length >= 4) {
                        try {
                            String str = split6[2];
                            String[] split7 = split6[3].split("/");
                            C1458i.m2618a(str + "," + split7[0]);
                        } catch (Throwable th) {
                            C1540h.m2996a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            C1540h.m2996a(th2);
        }
    }

    /* renamed from: c */
    public void mo15008c() {
        try {
            boolean d = m2612d();
            C1540h.m2997b("GBD_GOSA", "isGuardService = " + d);
            if (d) {
                m2613e();
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }
}
