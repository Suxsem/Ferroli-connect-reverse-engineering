package com.igexin.push.core.p047a.p048a;

import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1281d;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p050c.C1312h;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.d */
public class C1243d implements C1240a {
    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("duration")) {
                return null;
            }
            C1281d dVar = new C1281d();
            dVar.setType(jSONObject.getString("type"));
            dVar.setActionId(jSONObject.getString("actionid"));
            dVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("duration")) {
                dVar.mo14562a(Long.valueOf(jSONObject.getString("duration")).longValue());
            }
            return dVar;
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
        long currentTimeMillis = System.currentTimeMillis() + (((C1281d) baseAction).mo14561a() * 1000);
        C1312h.m1937a().mo14670a(true);
        C1312h.m1937a().mo14688f(currentTimeMillis);
        return true;
    }
}
