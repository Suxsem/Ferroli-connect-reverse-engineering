package com.igexin.push.core.p047a.p048a;

import android.content.Intent;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1289l;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.util.C1576a;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.h */
public class C1247h implements C1240a {

    /* renamed from: a */
    private static final String f1870a = C1233j.f1818a;

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("type") || !jSONObject.has("uri") || !jSONObject.has("do_failed")) {
                return null;
            }
            String optString = jSONObject.optString("uri");
            if (TextUtils.isEmpty(optString)) {
                return null;
            }
            C1289l lVar = new C1289l();
            lVar.setType("startmyactivity");
            lVar.setActionId(jSONObject.getString("actionid"));
            lVar.setDoActionId(jSONObject.getString("do"));
            lVar.mo14614a(optString);
            lVar.mo14616b(jSONObject.optString("do_failed"));
            return lVar;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1289l lVar = (C1289l) baseAction;
        try {
            Intent parseUri = Intent.parseUri(lVar.mo14613a(), 0);
            parseUri.setPackage(C1343f.f2169f.getPackageName());
            parseUri.addFlags(ClientDefaults.MAX_MSG_SIZE);
            if (C1576a.m3217c(parseUri, C1343f.f2169f)) {
                return C1294c.success;
            }
            C1179b.m1354a(f1870a + "|execute failed, activity not exist");
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), lVar.mo14615b());
            return C1294c.stop;
        } catch (Throwable th) {
            C1179b.m1354a(f1870a + "|execute exception = " + th.toString());
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), lVar.mo14615b());
            return C1294c.stop;
        }
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1257f a;
        String id;
        String messageId;
        String b;
        C1289l lVar = (C1289l) baseAction;
        try {
            Intent parseUri = Intent.parseUri(lVar.mo14613a(), 0);
            parseUri.setPackage(C1343f.f2169f.getPackageName());
            parseUri.addFlags(ClientDefaults.MAX_MSG_SIZE);
            if (C1576a.m3217c(parseUri, C1343f.f2169f)) {
                C1343f.f2169f.startActivity(parseUri);
                a = C1257f.m1711a();
                id = pushTaskBean.getTaskId();
                messageId = pushTaskBean.getMessageId();
                b = lVar.getDoActionId();
            } else {
                C1179b.m1354a(f1870a + "|execute failed, activity not exist");
                a = C1257f.m1711a();
                id = pushTaskBean.getId();
                messageId = pushTaskBean.getMessageId();
                b = lVar.mo14615b();
            }
            a.mo14482a(id, messageId, b);
            return true;
        } catch (Throwable th) {
            C1179b.m1354a(f1870a + "|execute exception = " + th.getMessage());
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), lVar.mo14615b());
            return true;
        }
    }
}
