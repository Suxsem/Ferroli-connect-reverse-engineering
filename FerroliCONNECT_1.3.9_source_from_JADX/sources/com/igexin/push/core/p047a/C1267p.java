package com.igexin.push.core.p047a;

import com.igexin.assist.action.MessageManger;
import com.igexin.assist.sdk.C1146a;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1355r;
import com.igexin.push.core.p050c.C1307c;
import com.igexin.push.p054e.p057c.C1386o;
import com.igexin.sdk.PushConsts;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.p */
public class C1267p extends C1253b {
    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        C1257f a;
        String str;
        try {
            C1386o oVar = (C1386o) obj;
            if (jSONObject.has(PushConsts.CMD_ACTION) && jSONObject.getString(PushConsts.CMD_ACTION).equals("pushmessage")) {
                byte[] bArr = null;
                if (oVar.f2331f instanceof byte[]) {
                    bArr = (byte[]) oVar.f2331f;
                }
                String string = jSONObject.getString("taskid");
                if (C1343f.f2145ag.containsKey(string)) {
                    C1343f.f2145ag.get(string).cancel();
                    C1343f.f2145ag.remove(string);
                }
                C1179b.m1354a("getui receive message : " + jSONObject.toString());
                if (bArr == null || !C1146a.m1225c(C1343f.f2169f)) {
                    C1355r.m2114a().mo14768a(jSONObject, bArr, true);
                } else {
                    C1307c cVar = new C1307c(C1343f.f2169f);
                    if (!cVar.mo14653a(string)) {
                        cVar.mo14655b(string);
                        C1355r.m2114a().mo14768a(jSONObject, bArr, true);
                        a = C1257f.m1711a();
                        str = AgooConstants.ACK_REMOVE_PACKAGE;
                    } else {
                        a = C1257f.m1711a();
                        str = "1" + MessageManger.getInstance().getBrandCode(C1343f.f2169f);
                    }
                    a.mo14480a(jSONObject, str);
                }
            }
        } catch (Exception e) {
            C1179b.m1354a("PushmessageAction|" + e.toString());
        }
        return true;
    }
}
