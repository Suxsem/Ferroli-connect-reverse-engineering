package com.igexin.push.extension.distribution.gbd.p081f.p082a;

import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1508g;
import com.igexin.push.extension.distribution.gbd.p081f.C1514b;
import com.igexin.push.extension.distribution.gbd.p081f.C1516d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.sdk.PushConsts;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.a.c */
public class C1512c extends C1516d {
    public C1512c() {
        super(C1514b.m2893b());
        mo15145a(true);
        mo15141n();
    }

    /* renamed from: a */
    public void mo15139a(Map<String, List<String>> map, byte[] bArr) {
        if (bArr != null) {
            try {
                C1508g.m2864a().mo15135a(bArr);
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }

    /* renamed from: n */
    public void mo15141n() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "fetchus");
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("appid", C1343f.f2135a);
            jSONObject.put("pkgs", C1541i.m3000a(C1490c.f2747a, 0));
            mo15146a(jSONObject.toString().getBytes());
            C1540h.m2997b("GBD_GSSHP", "init jsonObject = " + jSONObject);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }
}
