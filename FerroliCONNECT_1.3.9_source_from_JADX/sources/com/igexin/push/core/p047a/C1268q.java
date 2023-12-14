package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.p050c.C1308d;
import com.igexin.sdk.PushConsts;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.q */
public class C1268q extends C1253b {
    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        try {
            if (jSONObject.has(PushConsts.CMD_ACTION) && jSONObject.getString(PushConsts.CMD_ACTION).equals("received")) {
                String string = jSONObject.getString(AgooConstants.MESSAGE_ID);
                C1179b.m1354a("ReceivedAction received, cmd id :" + string);
                try {
                    C1308d.m1924a().mo14661a(Long.parseLong(string), true, false);
                    C1257f.m1711a().mo14490d();
                } catch (NumberFormatException e) {
                    C1179b.m1354a("ReceivedAction|" + e.toString());
                }
            }
        } catch (Exception e2) {
            C1179b.m1354a("ReceivedAction|" + e2.toString());
        }
        return true;
    }
}
