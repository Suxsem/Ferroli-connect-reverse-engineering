package com.igexin.push.extension.distribution.basic.p064f;

import com.alibaba.sdk.android.tbrest.rest.RestUrlWrapper;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.basic.p062d.C1418b;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.f.e */
public class C1426e extends C1427f {
    public C1426e() {
        super(C1424c.m2469a());
        C1179b.m1354a("EXT-_ConfigHttp|post url = " + C1424c.m2469a());
        mo14976a();
    }

    /* renamed from: a */
    public void mo14976a() {
        this.f2473d = true;
        this.f2472c = true;
        this.f2474e = true;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "sdkconfig");
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("appid", C1343f.f2135a);
            jSONObject.put(RestUrlWrapper.FIELD_SDK_VERSION, "EXT-2.8.10.0");
            C1179b.m1354a("EXT-_ConfigHttp|post src data = " + jSONObject.toString());
            mo14977b(jSONObject.toString().getBytes());
        } catch (Exception e) {
            C1179b.m1354a("EXT-_ConfigHttp|" + e.getMessage());
        }
    }

    /* renamed from: a */
    public void mo14974a(Exception exc) {
        super.mo14974a(exc);
        C1418b.m2428a().mo14953a(System.currentTimeMillis());
    }

    /* renamed from: a */
    public void mo14975a(byte[] bArr) {
        C1418b.m2428a().mo14954a(bArr);
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
