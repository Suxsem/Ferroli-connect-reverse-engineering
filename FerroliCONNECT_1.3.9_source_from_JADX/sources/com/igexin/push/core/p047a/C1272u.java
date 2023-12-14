package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1238a;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.u */
public class C1272u extends C1253b {
    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        try {
            if (!jSONObject.has(PushConsts.CMD_ACTION) || !jSONObject.getString(PushConsts.CMD_ACTION).equals("sendmessage_feedback")) {
                return true;
            }
            String string = jSONObject.getString("appid");
            String string2 = jSONObject.getString("taskid");
            String string3 = jSONObject.getString("actionid");
            String string4 = jSONObject.getString("result");
            long j = jSONObject.getLong("timestamp");
            C1179b.m1354a("SendMessageFeedbackAction|appid:" + string + "|taskid:" + string2 + "|actionid:" + string3);
            C1238a.m1630a().mo14457a(string, string2, string3, string4, j);
            return true;
        } catch (Exception unused) {
            return true;
        }
    }
}
