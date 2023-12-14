package com.igexin.push.util;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.util.d */
final class C1579d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f3011a;

    /* renamed from: b */
    final /* synthetic */ C1580e f3012b;

    C1579d(Context context, C1580e eVar) {
        this.f3011a = context;
        this.f3012b = eVar;
    }

    public void run() {
        boolean z = false;
        try {
            if (C1578c.m3227c(this.f3011a)) {
                C1581f.m3232a(String.valueOf(System.currentTimeMillis()).getBytes(), this.f3011a.getFilesDir().getPath() + "/" + "init_er.pid", false);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(PushConsts.CMD_ACTION, "upload_BI");
                jSONObject.put("BIType", "25");
                jSONObject.put("cid", "0");
                jSONObject.put("BIData", new String(C1177f.m1344f(C1578c.m3228d(this.f3011a).getBytes(), 0), "UTF-8"));
                byte[] a = C1595t.m3278a(SDKUrlConfig.getBiUploadServiceUrl(), C1196a.m1438b(jSONObject.toString().getBytes()), RestConstants.G_MAX_CONNECTION_TIME_OUT, RestConstants.G_MAX_CONNECTION_TIME_OUT);
                if (a != null) {
                    new String(a);
                }
                z = true;
            }
        } catch (Throwable th) {
            C1179b.m1354a("ErrorReport|report 25 ex = " + th.toString());
        }
        C1580e eVar = this.f3012b;
        if (eVar != null) {
            eVar.mo14804a(z);
        }
    }
}
