package com.taobao.accs.net;

import android.content.Context;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.taobao.accs.net.l */
/* compiled from: Taobao */
class C2060l implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2057j f3425a;

    C2060l(C2057j jVar) {
        this.f3425a = jVar;
    }

    public void run() {
        this.f3425a.f3414t.mo9711i("sendAccsHeartbeatMessage");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("dataType", "pingreq");
            jSONObject.put("timeInterval", this.f3425a.f3409o);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest((String) null, (String) null, jSONObject.toString().getBytes(), UUID.randomUUID().toString());
        accsRequest.setTarget("accs-iot");
        accsRequest.setTargetServiceName("sal");
        C2057j jVar = this.f3425a;
        Context context = jVar.f3376d;
        String packageName = this.f3425a.f3376d.getPackageName();
        this.f3425a.f3381i.getAppKey();
        this.f3425a.mo18472a(Message.m3452a(jVar, context, packageName, Constants.TARGET_SERVICE, accsRequest, true), true);
    }
}
