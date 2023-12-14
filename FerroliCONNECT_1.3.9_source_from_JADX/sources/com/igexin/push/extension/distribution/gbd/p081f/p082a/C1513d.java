package com.igexin.push.extension.distribution.gbd.p081f.p082a;

import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p076b.C1482b;
import com.igexin.push.extension.distribution.gbd.p076b.C1486f;
import com.igexin.push.extension.distribution.gbd.p081f.C1514b;
import com.igexin.push.extension.distribution.gbd.p081f.C1516d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.sdk.PushConsts;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.a.d */
public class C1513d extends C1516d {

    /* renamed from: n */
    int f2905n;

    /* renamed from: o */
    List<C1486f> f2906o;

    public C1513d(byte[] bArr, int i, List<C1486f> list) {
        super(C1514b.m2891a());
        mo15145a(true);
        this.f2906o = list;
        this.f2905n = i;
        m2887a(bArr, i);
    }

    /* renamed from: a */
    private void m2887a(byte[] bArr, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "upload_BI");
            jSONObject.put("BIType", String.valueOf(i));
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("BIData", new String(C1177f.m1344f(bArr, 0), "UTF-8"));
            mo15146a(jSONObject.toString().getBytes());
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
        C1540h.m2997b("GBD_UBLP", "gbdreportReq|" + i);
    }

    /* renamed from: a */
    public void mo15137a(int i) {
        try {
            C1482b bVar = new C1482b();
            bVar.mo15041a(false);
            bVar.mo15040a(this.f2906o);
            this.f2910d.mo15092a(bVar);
            C1540h.m2997b("GBD_UBLP", "type = " + this.f2905n + " requestFailed doReport failed...");
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    public void mo15138a(Throwable th) {
        try {
            if (this.f2910d != null) {
                C1482b bVar = new C1482b();
                bVar.mo15041a(false);
                bVar.mo15040a(this.f2906o);
                this.f2910d.mo15092a(bVar);
            }
            C1540h.m2997b("GBD_UBLP", "type = " + this.f2905n + " exceptionHandler doReport error..." + th.toString());
        } catch (Throwable th2) {
            C1540h.m2996a(th2);
        }
    }

    /* renamed from: a */
    public void mo15139a(Map<String, List<String>> map, byte[] bArr) {
        String str;
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            if (jSONObject.has("result")) {
                String string = jSONObject.getString("result");
                if (this.f2906o != null) {
                    if (this.f2910d != null) {
                        C1482b bVar = new C1482b();
                        bVar.mo15040a(this.f2906o);
                        if (string.equals("ok")) {
                            bVar.mo15041a(true);
                            this.f2910d.mo15092a(bVar);
                            str = "gbdreportRsp|" + this.f2905n;
                            C1540h.m2997b("GBD_UBLP", str);
                        }
                        bVar.mo15041a(false);
                        this.f2910d.mo15092a(bVar);
                        return;
                    }
                }
                str = "send list = null type = " + this.f2905n;
                C1540h.m2997b("GBD_UBLP", str);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }
}
