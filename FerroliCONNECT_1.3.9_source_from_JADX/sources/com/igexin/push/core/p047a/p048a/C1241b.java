package com.igexin.push.core.p047a.p048a;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1279b;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.b */
public class C1241b implements C1240a {
    /* renamed from: a */
    private boolean m1649a(String str) {
        try {
            return C1343f.f2169f.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("type") || !jSONObject.has("actionid")) {
                return null;
            }
            C1279b bVar = new C1279b();
            bVar.setType("checkapp");
            bVar.setActionId(jSONObject.getString("actionid"));
            if (!jSONObject.has("appstartupid")) {
                return null;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("appstartupid");
            if (!jSONObject2.has(DispatchConstants.ANDROID)) {
                return null;
            }
            bVar.mo14549a(jSONObject2.getString(DispatchConstants.ANDROID));
            if (!jSONObject.has("do_installed")) {
                if (!jSONObject.has("do_uninstalled")) {
                    return null;
                }
            }
            if (jSONObject.has("do_installed")) {
                bVar.mo14551b(jSONObject.getString("do_installed"));
            }
            if (jSONObject.has("do_uninstalled")) {
                bVar.mo14553c(jSONObject.getString("do_uninstalled"));
            }
            return bVar;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        String str;
        C1257f fVar;
        C1279b bVar = (C1279b) baseAction;
        String taskId = pushTaskBean.getTaskId();
        String messageId = pushTaskBean.getMessageId();
        if (m1649a(bVar.mo14548a())) {
            if (bVar.mo14550b() == null || bVar.mo14550b().equals("")) {
                return true;
            }
            fVar = C1257f.m1711a();
            str = bVar.mo14550b();
        } else if (bVar.mo14552c() == null || bVar.mo14552c().equals("")) {
            return true;
        } else {
            fVar = C1257f.m1711a();
            str = bVar.mo14552c();
        }
        fVar.mo14482a(taskId, messageId, str);
        return true;
    }
}
