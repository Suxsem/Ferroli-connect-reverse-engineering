package com.igexin.push.core.p047a.p048a;

import android.content.Intent;
import android.net.Uri;
import anetwork.channel.util.RequestConstant;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1290m;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.i */
public class C1248i implements C1240a {

    /* renamed from: a */
    private static final String f1871a = C1233j.f1818a;

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0063, code lost:
        if (r13.contains("=") != false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002b, code lost:
        if (r13.contains("=") != false) goto L_0x002d;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m1676a(com.igexin.push.core.bean.C1290m r12, java.lang.String r13) {
        /*
            r11 = this;
            java.lang.String r0 = r12.mo14617a()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            int r13 = r0.indexOf(r13)
            r1 = -1
            if (r13 != r1) goto L_0x000f
            return
        L_0x000f:
            r2 = 0
            java.lang.String r3 = "&"
            int r4 = r0.indexOf(r3)
            java.lang.String r5 = ""
            r6 = 0
            java.lang.String r7 = "="
            if (r4 != r1) goto L_0x0038
            int r1 = r13 + -1
            java.lang.String r5 = r0.substring(r6, r1)
            java.lang.String r13 = r0.substring(r13)
            boolean r0 = r13.contains(r7)
            if (r0 == 0) goto L_0x009e
        L_0x002d:
            int r0 = r13.indexOf(r7)
            int r0 = r0 + 1
            java.lang.String r2 = r13.substring(r0)
            goto L_0x009e
        L_0x0038:
            int r8 = r13 + -1
            char r9 = r0.charAt(r8)
            r10 = 63
            if (r9 != r10) goto L_0x0066
            java.lang.String r1 = r0.substring(r6, r13)
            int r3 = r4 + 1
            java.lang.String r3 = r0.substring(r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r1)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            java.lang.String r13 = r0.substring(r13, r4)
            boolean r0 = r13.contains(r7)
            if (r0 == 0) goto L_0x009e
            goto L_0x002d
        L_0x0066:
            char r4 = r0.charAt(r8)
            r9 = 38
            if (r4 != r9) goto L_0x009e
            java.lang.String r2 = r0.substring(r6, r8)
            java.lang.String r13 = r0.substring(r13)
            int r0 = r13.indexOf(r3)
            if (r0 == r1) goto L_0x0084
            java.lang.String r5 = r13.substring(r0)
            java.lang.String r13 = r13.substring(r6, r0)
        L_0x0084:
            int r0 = r13.indexOf(r7)
            int r0 = r0 + 1
            java.lang.String r13 = r13.substring(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            r2 = r13
        L_0x009e:
            r12.mo14618a((java.lang.String) r5)
            r12.mo14621b((java.lang.String) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1248i.m1676a(com.igexin.push.core.bean.m, java.lang.String):void");
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("url") || !jSONObject.has("do") || !jSONObject.has("actionid")) {
                return null;
            }
            String string = jSONObject.getString("url");
            if (string.equals("")) {
                return null;
            }
            C1290m mVar = new C1290m();
            mVar.setType("startweb");
            mVar.setActionId(jSONObject.getString("actionid"));
            mVar.setDoActionId(jSONObject.getString("do"));
            mVar.mo14618a(string);
            if (jSONObject.has("is_withcid")) {
                if (jSONObject.getString("is_withcid").equals(RequestConstant.TRUE)) {
                    mVar.mo14619a(true);
                }
            }
            if (jSONObject.has("is_withnettype") && jSONObject.getString("is_withnettype").equals(RequestConstant.TRUE)) {
                mVar.mo14622b(true);
            }
            return mVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1290m mVar = (C1290m) baseAction;
        m1676a(mVar, "targetpkgname");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        intent.setPackage(mVar.mo14620b());
        intent.setData(Uri.parse(mVar.mo14623c()));
        try {
            C1343f.f2169f.startActivity(intent);
        } catch (Exception unused) {
        }
        if (baseAction.getDoActionId().equals("")) {
            return true;
        }
        C1257f.m1711a().mo14482a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), baseAction.getDoActionId());
        return true;
    }
}
