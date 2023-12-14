package com.igexin.push.extension.distribution.basic.p058a;

import android.content.Intent;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p060b.C1408c;
import com.igexin.push.extension.distribution.basic.p068j.C1435c;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.a.d */
public class C1402d implements C1240a {
    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("type") || !jSONObject.has("uri") || !jSONObject.has("do_failed")) {
                return null;
            }
            String string = jSONObject.getString("uri");
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            C1408c cVar = new C1408c();
            cVar.setType("startmyactivity");
            cVar.setActionId(jSONObject.getString("actionid"));
            cVar.setDoActionId(jSONObject.getString("do"));
            cVar.mo14922a(string);
            cVar.mo14924b(jSONObject.getString("do_failed"));
            return cVar;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1408c cVar = (C1408c) baseAction;
        try {
            Intent parseUri = Intent.parseUri(cVar.mo14921a(), 0);
            parseUri.setPackage(C1343f.f2169f.getPackageName());
            parseUri.addFlags(ClientDefaults.MAX_MSG_SIZE);
            if (C1435c.m2505a(parseUri, C1343f.f2169f)) {
                return C1294c.success;
            }
            C1179b.m1354a("EXT-StartMyActivity|execute failed, activity not exist");
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), cVar.mo14923b());
            return C1294c.stop;
        } catch (Throwable th) {
            C1179b.m1354a("EXT-StartMyActivity|execute exception = " + th.toString());
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), cVar.mo14923b());
            return C1294c.stop;
        }
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1257f a;
        String id;
        String messageId;
        String b;
        C1408c cVar = (C1408c) baseAction;
        try {
            Intent parseUri = Intent.parseUri(cVar.mo14921a(), 0);
            parseUri.setPackage(C1343f.f2169f.getPackageName());
            parseUri.addFlags(ClientDefaults.MAX_MSG_SIZE);
            if (C1435c.m2505a(parseUri, C1343f.f2169f)) {
                C1343f.f2169f.startActivity(parseUri);
                a = C1257f.m1711a();
                id = pushTaskBean.getTaskId();
                messageId = pushTaskBean.getMessageId();
                b = cVar.getDoActionId();
            } else {
                C1179b.m1354a("EXT-StartMyActivity|execute failed, activity not exist");
                a = C1257f.m1711a();
                id = pushTaskBean.getId();
                messageId = pushTaskBean.getMessageId();
                b = cVar.mo14923b();
            }
            a.mo14482a(id, messageId, b);
            return true;
        } catch (Throwable th) {
            C1179b.m1354a("EXT-StartMyActivity|execute exception = " + th.getMessage());
            C1257f.m1711a().mo14482a(pushTaskBean.getId(), pushTaskBean.getMessageId(), cVar.mo14923b());
            return true;
        }
    }
}
