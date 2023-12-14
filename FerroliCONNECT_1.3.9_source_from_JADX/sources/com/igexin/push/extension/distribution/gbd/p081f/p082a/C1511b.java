package com.igexin.push.extension.distribution.gbd.p081f.p082a;

import com.alibaba.sdk.android.tbrest.rest.RestUrlWrapper;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1502a;
import com.igexin.push.extension.distribution.gbd.p081f.C1514b;
import com.igexin.push.extension.distribution.gbd.p081f.C1516d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.sdk.PushConsts;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.a.b */
public class C1511b extends C1516d {
    public C1511b() {
        super(C1514b.m2893b());
        mo15145a(true);
        mo15140n();
    }

    /* renamed from: a */
    public void mo15137a(int i) {
    }

    /* renamed from: a */
    public void mo15138a(Throwable th) {
        C1540h.m2996a(th);
    }

    /* renamed from: a */
    public void mo15139a(Map<String, List<String>> map, byte[] bArr) {
        try {
            C1502a.m2812a().mo15106a(bArr);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: n */
    public void mo15140n() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "sdkconfig");
            jSONObject.put("tag", C1488a.f2632P);
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("appid", C1343f.f2135a);
            jSONObject.put(RestUrlWrapper.FIELD_SDK_VERSION, "GBD-1.9.24");
            mo15146a(jSONObject.toString().getBytes());
            C1540h.m2997b("GBD_ConfigHttp", "init jsonObject = " + jSONObject);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }
}
