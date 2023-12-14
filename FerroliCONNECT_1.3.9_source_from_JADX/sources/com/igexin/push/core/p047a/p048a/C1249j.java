package com.igexin.push.core.p047a.p048a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1288k;
import com.igexin.push.core.bean.PushTaskBean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.j */
public class C1249j implements C1240a {

    /* renamed from: a */
    private static final String f1872a = "com.igexin.push.core.a.a.j";

    /* renamed from: a */
    private static void m1680a(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(packageInfo.packageName);
            ResolveInfo next = context.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
            if (next != null) {
                String str2 = next.activityInfo.packageName;
                String str3 = next.activityInfo.name;
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.LAUNCHER");
                intent2.setFlags(270532608);
                intent2.setComponent(new ComponentName(str2, str3));
                context.startActivity(intent2);
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            C1288k kVar = new C1288k();
            kVar.setType("startapp");
            kVar.setActionId(jSONObject.getString("actionid"));
            kVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("appstartupid")) {
                kVar.mo14606a(jSONObject.getJSONObject("appstartupid").getString(DispatchConstants.ANDROID));
            }
            if (jSONObject.has("is_autostart")) {
                kVar.mo14612d(jSONObject.getString("is_autostart"));
            }
            if (jSONObject.has("appid")) {
                kVar.mo14608b(jSONObject.getString("appid"));
            }
            if (jSONObject.has("noinstall_action")) {
                kVar.mo14610c(jSONObject.getString("noinstall_action"));
            }
            return kVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x004a A[SYNTHETIC, Splitter:B:12:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0086 A[Catch:{ Exception -> 0x00e3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b9 A[Catch:{ Exception -> 0x00e3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00cc A[Catch:{ Exception -> 0x00e3 }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r10, com.igexin.push.core.bean.BaseAction r11) {
        /*
            r9 = this;
            r0 = 1
            if (r10 == 0) goto L_0x00e3
            if (r11 == 0) goto L_0x00e3
            r1 = r11
            com.igexin.push.core.bean.k r1 = (com.igexin.push.core.bean.C1288k) r1
            java.lang.String r2 = r1.mo14607b()
            java.lang.String r3 = ""
            boolean r3 = r2.equals(r3)
            r4 = 0
            if (r3 == 0) goto L_0x001a
            java.lang.String r2 = com.igexin.push.core.C1343f.f2135a
        L_0x0017:
            r3 = r2
            r2 = 1
            goto L_0x0029
        L_0x001a:
            java.lang.String r3 = com.igexin.push.core.C1343f.f2135a
            java.lang.String r5 = r1.mo14607b()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0027
            goto L_0x0017
        L_0x0027:
            r3 = r2
            r2 = 0
        L_0x0029:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "doStartApp|"
            r5.append(r6)
            r5.append(r2)
            java.lang.String r6 = "|"
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)
            java.lang.String r5 = "true"
            r6 = 0
            if (r2 == 0) goto L_0x0086
            com.igexin.push.core.a r2 = com.igexin.push.core.C1238a.m1630a()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r4 = r10.getTaskId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r7 = r10.getMessageId()     // Catch:{ Exception -> 0x00e3 }
            r2.mo14456a((java.lang.String) r4, (java.lang.String) r7, (java.lang.String) r3, (java.lang.String) r6)     // Catch:{ Exception -> 0x00e3 }
            com.igexin.push.core.bean.k r11 = (com.igexin.push.core.bean.C1288k) r11     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r11 = r11.mo14611d()     // Catch:{ Exception -> 0x00e3 }
            boolean r11 = r11.equals(r5)     // Catch:{ Exception -> 0x00e3 }
            if (r11 == 0) goto L_0x006c
            android.content.Context r11 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r2 = com.igexin.push.core.C1343f.f2168e     // Catch:{ Exception -> 0x00e3 }
            m1680a((android.content.Context) r11, (java.lang.String) r2)     // Catch:{ Exception -> 0x00e3 }
        L_0x006c:
            java.lang.String r11 = r1.getDoActionId()     // Catch:{ Exception -> 0x00e3 }
            if (r11 == 0) goto L_0x00e3
            com.igexin.push.core.a.f r11 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r2 = r10.getTaskId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r10 = r10.getMessageId()     // Catch:{ Exception -> 0x00e3 }
        L_0x007e:
            java.lang.String r1 = r1.getDoActionId()     // Catch:{ Exception -> 0x00e3 }
        L_0x0082:
            r11.mo14482a((java.lang.String) r2, (java.lang.String) r10, (java.lang.String) r1)     // Catch:{ Exception -> 0x00e3 }
            goto L_0x00e3
        L_0x0086:
            com.igexin.push.core.a r2 = com.igexin.push.core.C1238a.m1630a()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r7 = r10.getTaskId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r8 = r10.getMessageId()     // Catch:{ Exception -> 0x00e3 }
            r2.mo14456a((java.lang.String) r7, (java.lang.String) r8, (java.lang.String) r3, (java.lang.String) r6)     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r2 = r1.mo14611d()     // Catch:{ Exception -> 0x00e3 }
            boolean r2 = r2.equals(r5)     // Catch:{ Exception -> 0x00e3 }
            if (r2 == 0) goto L_0x00b6
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r3 = r1.mo14605a()     // Catch:{ Exception -> 0x00e3 }
            boolean r2 = com.igexin.push.util.C1576a.m3202a((android.content.Context) r2, (java.lang.String) r3)     // Catch:{ Exception -> 0x00e3 }
            if (r2 == 0) goto L_0x00b7
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x00e3 }
            com.igexin.push.core.bean.k r11 = (com.igexin.push.core.bean.C1288k) r11     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r11 = r11.mo14605a()     // Catch:{ Exception -> 0x00e3 }
            m1680a((android.content.Context) r2, (java.lang.String) r11)     // Catch:{ Exception -> 0x00e3 }
        L_0x00b6:
            r4 = 1
        L_0x00b7:
            if (r4 == 0) goto L_0x00cc
            java.lang.String r11 = r1.getDoActionId()     // Catch:{ Exception -> 0x00e3 }
            if (r11 == 0) goto L_0x00e3
            com.igexin.push.core.a.f r11 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r2 = r10.getTaskId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r10 = r10.getMessageId()     // Catch:{ Exception -> 0x00e3 }
            goto L_0x007e
        L_0x00cc:
            java.lang.String r11 = r1.mo14609c()     // Catch:{ Exception -> 0x00e3 }
            if (r11 == 0) goto L_0x00e3
            com.igexin.push.core.a.f r11 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r2 = r10.getTaskId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r10 = r10.getMessageId()     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r1 = r1.mo14609c()     // Catch:{ Exception -> 0x00e3 }
            goto L_0x0082
        L_0x00e3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1249j.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
