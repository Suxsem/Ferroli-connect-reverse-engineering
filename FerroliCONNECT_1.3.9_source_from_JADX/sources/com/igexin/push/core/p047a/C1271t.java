package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.t */
public class C1271t extends C1253b {

    /* renamed from: a */
    private static final String f1894a = C1233j.f1818a;

    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        try {
            if (!jSONObject.has(PushConsts.CMD_ACTION) || !jSONObject.getString(PushConsts.CMD_ACTION).equals("response_deviceid")) {
                return true;
            }
            String string = jSONObject.getString("deviceid");
            C1179b.m1354a(f1894a + " get devid resp, devid : " + string + ", save 2db and file");
            C1312h.m1937a().mo14679c(string);
            if (C1343f.f2154ap != null) {
                C1179b.m1354a(f1894a + " deviceid arrived cancel addPhoneInfoTimerTask...");
                C1343f.f2154ap.mo14309t();
                C1343f.f2154ap = null;
            }
            if (C1343f.f2187x != null) {
                C1340e.m2032a().mo14711h().mo14492f();
            }
            C1179b.m1354a("deviceidRsp|" + C1343f.f2187x);
            return true;
        } catch (Exception unused) {
            return true;
        }
    }
}
