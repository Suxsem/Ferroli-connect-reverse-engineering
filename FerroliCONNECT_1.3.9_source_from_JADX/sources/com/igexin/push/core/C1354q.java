package com.igexin.push.core;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushManager;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1224a;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.bean.C1286i;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p050c.C1308d;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p043a.p044a.C1199c;
import com.igexin.push.p054e.p057c.C1375d;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.C1584i;
import com.igexin.sdk.PushConsts;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.q */
public class C1354q {

    /* renamed from: a */
    private static C1354q f2210a;

    private C1354q() {
    }

    /* renamed from: a */
    public static C1354q m2102a() {
        if (f2210a == null) {
            f2210a = new C1354q();
        }
        return f2210a;
    }

    /* renamed from: a */
    public void mo14752a(int i, int i2, String str) {
        C1234k.f1840a = i;
        C1234k.f1841b = i2;
        C1224a.m1601a().mo14444b();
        C1199c.m1446c().mo14351d();
    }

    /* renamed from: a */
    public void mo14753a(int i, String str) {
        C1234k.f1843d = i;
        C1224a.m1601a().mo14447c();
        if (C1343f.f2175l) {
            C1179b.m1354a("setHeartbeatInterval heartbeatReq");
            if (System.currentTimeMillis() - C1343f.f2123O > 5000) {
                C1343f.f2123O = System.currentTimeMillis();
                C1257f.m1711a().mo14484b();
            }
        }
    }

    /* renamed from: a */
    public void mo14754a(Bundle bundle) {
        String string = bundle.getString(PushConsts.CMD_ACTION);
        C1179b.m1354a("PushController|action pushmanager action = " + string);
        if (!TextUtils.isEmpty(string)) {
            if (string.equals("setTag")) {
                if (C1234k.f1848i) {
                    mo14756a(bundle.getString("tags"), bundle.getString("sn"));
                }
            } else if (string.equals("setSilentTime")) {
                if (C1234k.f1849j) {
                    int i = bundle.getInt("beginHour", 0);
                    int i2 = bundle.getInt("duration", 0);
                    mo14752a(i, i2, C1343f.f2169f.getPackageName());
                    AssistPushManager.getInstance().setSilentTime(C1343f.f2169f, i, i2);
                }
            } else if (string.equals("sendMessage")) {
                C1179b.m1354a("PushController onPushManagerMessage recevie action : sendMessage");
                if (C1234k.f1847h) {
                    String string2 = bundle.getString("taskid");
                    byte[] byteArray = bundle.getByteArray("extraData");
                    C1179b.m1354a("PushController receive broadcast msg data , task id : " + string2 + " ######@##@@@#");
                    mo14759a(string2, byteArray);
                }
            } else if (string.equals("setHeartbeatInterval")) {
                if (C1234k.f1850k) {
                    mo14753a(bundle.getInt("interval", 0), C1343f.f2169f.getPackageName());
                }
            } else if (string.equals("setSocketTimeout")) {
                if (C1234k.f1851l) {
                    mo14760b(bundle.getInt("timeout", 0), C1343f.f2169f.getPackageName());
                }
            } else if (string.equals("sendFeedbackMessage")) {
                if (C1234k.f1854o && C1343f.f2148aj <= 200) {
                    String string3 = bundle.getString("taskid");
                    String string4 = bundle.getString("messageid");
                    String string5 = bundle.getString("actionid");
                    String str = string3 + ":" + string4 + ":" + string5;
                    if (C1343f.f2147ai.get(str) == null) {
                        long currentTimeMillis = System.currentTimeMillis();
                        PushTaskBean pushTaskBean = new PushTaskBean();
                        pushTaskBean.setTaskId(string3);
                        pushTaskBean.setMessageId(string4);
                        pushTaskBean.setAppid(C1343f.f2135a);
                        pushTaskBean.setAppKey(C1343f.f2165b);
                        C1257f.m1711a().mo14486b(pushTaskBean, string5);
                        C1343f.f2148aj++;
                        C1343f.f2147ai.put(str, Long.valueOf(currentTimeMillis));
                    }
                }
            } else if (string.equals("turnOffPush")) {
                C1340e.m2032a().mo14708e();
                AssistPushManager.getInstance().turnOffPush(C1343f.f2169f);
            } else if (string.equals("bindAlias")) {
                String string6 = bundle.getString("alias");
                String string7 = bundle.getString("sn");
                C1179b.m1354a("PushController|onPushManagerMessage bindAlias...");
                mo14761b(string6, string7);
            } else if (string.equals("unbindAlias")) {
                String string8 = bundle.getString("alias");
                String string9 = bundle.getString("sn");
                boolean z = bundle.getBoolean("isSeft");
                C1179b.m1354a("PushController|onPushManagerMessage unbindAlias...");
                mo14757a(string8, string9, z);
            } else if (string.equals("sendApplinkFeedback")) {
                mo14755a(bundle.getString("url"));
            }
        }
    }

    /* renamed from: a */
    public void mo14755a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                Uri parse = Uri.parse(str);
                String host = parse.getHost();
                String queryParameter = parse.getQueryParameter("p");
                if (parse != null && !TextUtils.isEmpty(host)) {
                    if (!TextUtils.isEmpty(queryParameter)) {
                        if (!C1234k.f1830L) {
                            C1179b.m1354a("PushController|isApplinkFeedback is false, not feedback");
                            return;
                        } else if (!C1576a.m3215b(host)) {
                            C1179b.m1354a("PushController|checkIsWhiteApplinkDomain is false, not feedback");
                            return;
                        } else {
                            C1179b.m1354a("PushController|isApplinkFeedback is true and checkIsWhiteApplinkDomain is true, to feedback");
                            PushTaskBean pushTaskBean = new PushTaskBean();
                            pushTaskBean.setTaskId("getuiapplinkup");
                            pushTaskBean.setMessageId(queryParameter);
                            pushTaskBean.setAppid(C1343f.f2135a);
                            pushTaskBean.setAppKey(C1343f.f2165b);
                            C1257f.m1711a().mo14486b(pushTaskBean, PushConsts.SEND_MESSAGE_ERROR);
                            return;
                        }
                    }
                }
                C1179b.m1354a("PushController|url " + str + " is invalid");
            } catch (Exception e) {
                C1179b.m1354a("PushController|" + e.toString());
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:3|4|5|6|7|8|9|(1:11)|12|14) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0044 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004e A[Catch:{ Exception -> 0x0082 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14756a(java.lang.String r17, java.lang.String r18) {
        /*
            r16 = this;
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0082 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0082 }
            r0.<init>()     // Catch:{ Exception -> 0x0082 }
            java.lang.String r1 = "action"
            java.lang.String r2 = "set_tag"
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = "id"
            java.lang.String r2 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x0044 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = "cid"
            java.lang.String r2 = com.igexin.push.core.C1343f.f2182s     // Catch:{ Exception -> 0x0044 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = "appid"
            java.lang.String r2 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x0044 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = "tags"
            java.lang.String r2 = "utf-8"
            r3 = r17
            java.lang.String r2 = java.net.URLEncoder.encode(r3, r2)     // Catch:{ Exception -> 0x0044 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
            java.lang.String r1 = "sn"
            r2 = r18
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0044 }
        L_0x0044:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0082 }
            com.igexin.push.core.c.d r8 = com.igexin.push.core.p050c.C1308d.m1924a()     // Catch:{ Exception -> 0x0082 }
            if (r8 == 0) goto L_0x005a
            com.igexin.push.core.bean.i r9 = new com.igexin.push.core.bean.i     // Catch:{ Exception -> 0x0082 }
            r5 = 2
            r1 = r9
            r2 = r6
            r4 = r0
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Exception -> 0x0082 }
            r8.mo14662a((com.igexin.push.core.bean.C1286i) r9)     // Catch:{ Exception -> 0x0082 }
        L_0x005a:
            com.igexin.push.e.c.d r14 = new com.igexin.push.e.c.d     // Catch:{ Exception -> 0x0082 }
            r14.<init>()     // Catch:{ Exception -> 0x0082 }
            r14.mo14836a()     // Catch:{ Exception -> 0x0082 }
            java.lang.String r1 = "17258000"
            r14.f2273d = r1     // Catch:{ Exception -> 0x0082 }
            r14.f2274e = r0     // Catch:{ Exception -> 0x0082 }
            com.igexin.b.a.b.c r10 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()     // Catch:{ Exception -> 0x0082 }
            java.lang.String r11 = com.igexin.push.config.SDKUrlConfig.getCmAddress()     // Catch:{ Exception -> 0x0082 }
            r12 = 3
            com.igexin.push.core.e r0 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Exception -> 0x0082 }
            com.igexin.b.a.b.b r13 = r0.mo14709f()     // Catch:{ Exception -> 0x0082 }
            r15 = 0
            r10.mo14260a(r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0082 }
            java.lang.String r0 = "settag"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x0082 }
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1354q.mo14756a(java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    public void mo14757a(String str, String str2, boolean z) {
        String str3;
        if (!z || !TextUtils.isEmpty(C1343f.f2182s)) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - C1343f.f2125Q > 5000) {
                String format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(currentTimeMillis));
                if (!format.equals(C1343f.f2124P)) {
                    C1312h.m1937a().mo14691g(format);
                    C1312h.m1937a().mo14665a(0);
                }
                if (C1343f.f2126R < 100) {
                    C1179b.m1354a("start unbindAlias ###");
                    C1343f.f2125Q = currentTimeMillis;
                    C1312h.m1937a().mo14665a(C1343f.f2126R + 1);
                    mo14758a(str, str2, true, z);
                    return;
                }
                str3 = "PushController|unbindAlias times exceed";
            } else {
                str3 = "PushController|unbindAlias frequently called";
            }
            C1179b.m1354a(str3);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:3|4|(1:6)(1:7)|8|(1:10)(1:11)|12|13|14|(1:16)|17|18|(1:20)|21|23) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0056 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0060 A[Catch:{ Exception -> 0x00a6 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14758a(java.lang.String r18, java.lang.String r19, boolean r20, boolean r21) {
        /*
            r17 = this;
            java.lang.String r0 = com.igexin.push.core.C1343f.f2182s
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00a6 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00a6 }
            r0.<init>()     // Catch:{ Exception -> 0x00a6 }
            if (r20 == 0) goto L_0x0017
            java.lang.String r1 = "unbind_alias"
            goto L_0x0019
        L_0x0017:
            java.lang.String r1 = "bind_alias"
        L_0x0019:
            r8 = r1
            if (r20 == 0) goto L_0x0021
            r1 = 8
            r5 = 8
            goto L_0x0023
        L_0x0021:
            r1 = 7
            r5 = 7
        L_0x0023:
            java.lang.String r1 = "action"
            r0.put(r1, r8)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = "id"
            java.lang.String r2 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x0056 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = "cid"
            java.lang.String r2 = com.igexin.push.core.C1343f.f2182s     // Catch:{ Exception -> 0x0056 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = "appid"
            java.lang.String r2 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x0056 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = "alias"
            r2 = r18
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = "sn"
            r2 = r19
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
            if (r20 == 0) goto L_0x0056
            java.lang.String r1 = "is_self"
            r2 = r21
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0056 }
        L_0x0056:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a6 }
            com.igexin.push.core.c.d r9 = com.igexin.push.core.p050c.C1308d.m1924a()     // Catch:{ Exception -> 0x00a6 }
            if (r9 == 0) goto L_0x006b
            com.igexin.push.core.bean.i r10 = new com.igexin.push.core.bean.i     // Catch:{ Exception -> 0x00a6 }
            r1 = r10
            r2 = r6
            r4 = r0
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Exception -> 0x00a6 }
            r9.mo14662a((com.igexin.push.core.bean.C1286i) r10)     // Catch:{ Exception -> 0x00a6 }
        L_0x006b:
            com.igexin.push.e.c.d r15 = new com.igexin.push.e.c.d     // Catch:{ Exception -> 0x00a6 }
            r15.<init>()     // Catch:{ Exception -> 0x00a6 }
            r15.mo14836a()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r1 = "17258000"
            r15.f2273d = r1     // Catch:{ Exception -> 0x00a6 }
            r15.f2274e = r0     // Catch:{ Exception -> 0x00a6 }
            com.igexin.b.a.b.c r11 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r12 = com.igexin.push.config.SDKUrlConfig.getCmAddress()     // Catch:{ Exception -> 0x00a6 }
            r13 = 3
            com.igexin.push.core.e r1 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Exception -> 0x00a6 }
            com.igexin.b.a.b.b r14 = r1.mo14709f()     // Catch:{ Exception -> 0x00a6 }
            r16 = 0
            r11.mo14260a(r12, r13, r14, r15, r16)     // Catch:{ Exception -> 0x00a6 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a6 }
            r1.<init>()     // Catch:{ Exception -> 0x00a6 }
            r1.append(r8)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r2 = " = "
            r1.append(r2)     // Catch:{ Exception -> 0x00a6 }
            r1.append(r0)     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x00a6 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x00a6 }
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1354q.mo14758a(java.lang.String, java.lang.String, boolean, boolean):void");
    }

    /* renamed from: a */
    public void mo14759a(String str, byte[] bArr) {
        if (C1343f.f2182s != null) {
            JSONObject jSONObject = new JSONObject();
            long currentTimeMillis = System.currentTimeMillis();
            try {
                jSONObject.put(PushConsts.CMD_ACTION, "sendmessage");
                jSONObject.put(AgooConstants.MESSAGE_ID, String.valueOf(currentTimeMillis));
                jSONObject.put("cid", C1343f.f2182s);
                jSONObject.put("appid", C1343f.f2135a);
                jSONObject.put("taskid", str);
                jSONObject.put("extraData", C1584i.m3249b(bArr, 0));
                String jSONObject2 = jSONObject.toString();
                C1308d.m1924a().mo14662a(new C1286i(currentTimeMillis, jSONObject2, (byte) 6, currentTimeMillis));
                C1375d dVar = new C1375d();
                dVar.mo14836a();
                dVar.f2270a = (int) currentTimeMillis;
                dVar.f2273d = C1343f.f2182s;
                dVar.f2274e = jSONObject2;
                dVar.f2275f = bArr;
                dVar.f2276g = C1343f.f2182s;
                C1560a g = C1340e.m2032a().mo14710g();
                g.mo15186a("C-" + C1343f.f2182s, dVar);
                if (str != null && str.startsWith("4T5@S_")) {
                    C1179b.m1354a("PushController sending lbs report message : " + jSONObject2);
                }
            } catch (Throwable th) {
                C1179b.m1354a("PushController|" + th.toString());
            }
        }
    }

    /* renamed from: b */
    public void mo14760b(int i, String str) {
        C1234k.f1844e = i;
        C1224a.m1601a().mo14449d();
    }

    /* renamed from: b */
    public void mo14761b(String str, String str2) {
        String str3;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - C1343f.f2125Q > 5000) {
            String format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(currentTimeMillis));
            if (!format.equals(C1343f.f2124P)) {
                C1312h.m1937a().mo14691g(format);
                C1312h.m1937a().mo14665a(0);
            }
            C1179b.m1354a("-> CoreRuntimeInfo.opAliasTimes:" + C1343f.f2126R);
            if (C1343f.f2126R < 100) {
                C1179b.m1354a("start bindAlias ###");
                C1343f.f2125Q = currentTimeMillis;
                C1312h.m1937a().mo14665a(C1343f.f2126R + 1);
                mo14758a(str, str2, false, true);
                return;
            }
            str3 = "PushController|bindAlias times exceed";
        } else {
            str3 = "PushController|bindAlias frequently called";
        }
        C1179b.m1354a(str3);
    }
}
