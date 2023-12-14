package com.igexin.push.core.p047a.p048a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.e */
public class C1244e implements C1240a {
    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            BaseAction baseAction = new BaseAction();
            baseAction.setType("null");
            baseAction.setActionId(jSONObject.getString("actionid"));
            return baseAction;
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
        String a = C1257f.m1711a().mo14472a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId());
        C1179b.m1354a("EndAction execute, remove pushMessage from pushMessageMap, key = " + a);
        try {
            C1343f.f2142ad.remove(a);
            return true;
        } catch (Exception e) {
            C1179b.m1354a("EndAction|" + e.toString());
            return true;
        }
    }
}
