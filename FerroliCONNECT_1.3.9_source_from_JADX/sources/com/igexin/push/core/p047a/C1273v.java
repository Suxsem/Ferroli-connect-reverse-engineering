package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1238a;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.v */
public class C1273v extends C1253b {

    /* renamed from: a */
    private static final String f1895a = (C1233j.f1818a + "_SetTagResultAction");

    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        C1179b.m1354a(f1895a + "|set tag result resp data = " + jSONObject);
        if (jSONObject == null) {
            return true;
        }
        try {
            if (!jSONObject.has(PushConsts.CMD_ACTION) || !jSONObject.getString(PushConsts.CMD_ACTION).equals("settag_result")) {
                return true;
            }
            C1238a.m1630a().mo14455a(jSONObject.getString("sn"), jSONObject.getString("error_code"));
            return true;
        } catch (Exception e) {
            C1179b.m1354a(f1895a + "|" + e.toString());
            return true;
        }
    }
}
