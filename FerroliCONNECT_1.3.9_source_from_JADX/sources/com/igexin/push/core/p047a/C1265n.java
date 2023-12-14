package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1307c;
import com.igexin.push.p088g.C1567b;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.n */
class C1265n extends C1567b {

    /* renamed from: a */
    final /* synthetic */ C1262k f1890a;

    C1265n(C1262k kVar) {
        this.f1890a = kVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14352a() {
        try {
            C1307c cVar = new C1307c(C1343f.f2169f);
            JSONObject c = cVar.mo14656c();
            if (c != null) {
                Iterator<String> keys = c.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    JSONObject jSONObject = c.getJSONObject(next);
                    C1179b.m1354a("LoginResultAction|send unFeedback taskid = " + next);
                    jSONObject.put("appid", C1343f.f2135a);
                    jSONObject.put("appkey", C1343f.f2165b);
                    C1257f.m1711a().mo14480a(jSONObject, jSONObject.getString("multaid"));
                    keys.remove();
                }
                cVar.mo14658d();
            }
        } catch (Throwable th) {
            C1179b.m1354a("LoginResultAction|feedbackMultiBrandPushMessage exception :" + th.toString());
        }
    }
}
