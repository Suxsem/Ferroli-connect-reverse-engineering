package com.igexin.push.extension.distribution.basic.p058a;

import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p060b.C1407b;
import com.taobao.accs.messenger.MessengerService;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.a.c */
public class C1401c implements C1240a {

    /* renamed from: a */
    private static final String f2360a = ("EXT-" + C1401c.class.getName());

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (jSONObject.has("do") && jSONObject.has("actionid") && jSONObject.has("type") && jSONObject.has(MessengerService.INTENT) && jSONObject.has("do_failed") && jSONObject.has("t")) {
                String string = jSONObject.getString(MessengerService.INTENT);
                String string2 = jSONObject.getString("t");
                String string3 = jSONObject.getString("do");
                String string4 = jSONObject.getString("do_failed");
                if ((!string2.equals("0") && !string2.equals("1")) || string4.equals("") || string3.equals("0")) {
                    return null;
                }
                C1407b bVar = new C1407b();
                bVar.setType("startintent");
                bVar.setActionId(jSONObject.getString("actionid"));
                bVar.setDoActionId(jSONObject.getString("do"));
                bVar.mo14918b(string);
                bVar.mo14916a(string2);
                bVar.mo14920c(jSONObject.getString("do_failed"));
                return bVar;
            }
        } catch (Exception unused) {
        }
        return null;
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:17|18|20) */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        com.igexin.p032b.p033a.p039c.C1179b.m1354a(f2360a + "|executeAction err intent=" + r7.mo14917b() + " t=" + r7.mo14915a());
        com.igexin.push.core.p047a.C1257f.m1711a().mo14482a(r6.getTaskId(), r6.getMessageId(), r7.mo14919c());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return true;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x009b */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r6, com.igexin.push.core.bean.BaseAction r7) {
        /*
            r5 = this;
            java.lang.String r0 = " t="
            com.igexin.push.extension.distribution.basic.b.b r7 = (com.igexin.push.extension.distribution.basic.p060b.C1407b) r7
            java.lang.String r1 = r7.mo14917b()     // Catch:{ Exception -> 0x00d5 }
            r2 = 0
            android.content.Intent r1 = android.content.Intent.parseUri(r1, r2)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r3 = r7.mo14915a()     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = "0"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x009b }
            if (r3 == 0) goto L_0x0038
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            r1.setFlags(r2)     // Catch:{ Exception -> 0x009b }
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x009b }
            r2.startActivity(r1)     // Catch:{ Exception -> 0x009b }
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = r6.getTaskId()     // Catch:{ Exception -> 0x009b }
            java.lang.String r3 = r6.getMessageId()     // Catch:{ Exception -> 0x009b }
        L_0x002f:
            java.lang.String r4 = r7.getDoActionId()     // Catch:{ Exception -> 0x009b }
        L_0x0033:
            r1.mo14482a((java.lang.String) r2, (java.lang.String) r3, (java.lang.String) r4)     // Catch:{ Exception -> 0x009b }
            goto L_0x00d5
        L_0x0038:
            java.lang.String r3 = r7.mo14915a()     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = "1"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x009b }
            if (r3 == 0) goto L_0x00d5
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x009b }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Exception -> 0x009b }
            java.util.List r2 = r3.queryIntentServices(r1, r2)     // Catch:{ Exception -> 0x009b }
            if (r2 == 0) goto L_0x0068
            int r2 = r2.size()     // Catch:{ Exception -> 0x009b }
            if (r2 <= 0) goto L_0x0068
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x009b }
            r2.startService(r1)     // Catch:{ Exception -> 0x009b }
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = r6.getTaskId()     // Catch:{ Exception -> 0x009b }
            java.lang.String r3 = r6.getMessageId()     // Catch:{ Exception -> 0x009b }
            goto L_0x002f
        L_0x0068:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009b }
            r1.<init>()     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = "StartIntentAction err intent="
            r1.append(r2)     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = r7.mo14917b()     // Catch:{ Exception -> 0x009b }
            r1.append(r2)     // Catch:{ Exception -> 0x009b }
            r1.append(r0)     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = r7.mo14915a()     // Catch:{ Exception -> 0x009b }
            r1.append(r2)     // Catch:{ Exception -> 0x009b }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x009b }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r1)     // Catch:{ Exception -> 0x009b }
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x009b }
            java.lang.String r2 = r6.getTaskId()     // Catch:{ Exception -> 0x009b }
            java.lang.String r3 = r6.getMessageId()     // Catch:{ Exception -> 0x009b }
            java.lang.String r4 = r7.mo14919c()     // Catch:{ Exception -> 0x009b }
            goto L_0x0033
        L_0x009b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d5 }
            r1.<init>()     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r2 = f2360a     // Catch:{ Exception -> 0x00d5 }
            r1.append(r2)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r2 = "|executeAction err intent="
            r1.append(r2)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r2 = r7.mo14917b()     // Catch:{ Exception -> 0x00d5 }
            r1.append(r2)     // Catch:{ Exception -> 0x00d5 }
            r1.append(r0)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r0 = r7.mo14915a()     // Catch:{ Exception -> 0x00d5 }
            r1.append(r0)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x00d5 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x00d5 }
            com.igexin.push.core.a.f r0 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r1 = r6.getTaskId()     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r6 = r6.getMessageId()     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r7 = r7.mo14919c()     // Catch:{ Exception -> 0x00d5 }
            r0.mo14482a((java.lang.String) r1, (java.lang.String) r6, (java.lang.String) r7)     // Catch:{ Exception -> 0x00d5 }
        L_0x00d5:
            r6 = 1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1401c.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
