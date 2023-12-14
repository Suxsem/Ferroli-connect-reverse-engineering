package com.igexin.push.core.p047a;

import com.igexin.push.config.C1224a;
import com.igexin.push.config.C1234k;
import com.igexin.push.p043a.p044a.C1199c;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.d */
public class C1255d extends C1253b {
    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        try {
            if (!jSONObject.has(PushConsts.CMD_ACTION) || !jSONObject.getString(PushConsts.CMD_ACTION).equals("block_client") || !jSONObject.has("duration")) {
                return true;
            }
            long j = jSONObject.getLong("duration") * 1000;
            long currentTimeMillis = System.currentTimeMillis();
            if (j == 0) {
                return true;
            }
            C1234k.f1842c = currentTimeMillis + j;
            C1224a.m1601a().mo14450e();
            C1199c.m1446c().mo14351d();
            return true;
        } catch (Exception unused) {
            return true;
        }
    }
}
