package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1233j;
import com.igexin.push.p054e.p057c.C1386o;
import com.igexin.sdk.PushConsts;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.h */
public class C1259h extends C1239a {

    /* renamed from: a */
    private static final String f1884a = C1233j.f1818a;

    /* renamed from: b */
    private static Map<String, C1253b> f1885b;

    public C1259h() {
        f1885b = new HashMap();
        f1885b.put("redirect_server", new C1269r());
        f1885b.put("response_deviceid", new C1271t());
        f1885b.put("pushmessage", new C1267p());
        f1885b.put("received", new C1268q());
        f1885b.put("sendmessage_feedback", new C1272u());
        f1885b.put("block_client", new C1255d());
        f1885b.put("settag_result", new C1273v());
        f1885b.put("response_bind", new C1254c());
        f1885b.put("response_unbind", new C1274w());
    }

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        C1253b bVar;
        if (!(obj instanceof C1386o)) {
            return false;
        }
        C1386o oVar = (C1386o) obj;
        if (!oVar.mo14842a() || oVar.f2330e == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject((String) oVar.f2330e);
            if (jSONObject.has(PushConsts.CMD_ACTION) && !jSONObject.getString(PushConsts.CMD_ACTION).equals("received") && !jSONObject.getString(PushConsts.CMD_ACTION).equals("redirect_server") && jSONObject.has(AgooConstants.MESSAGE_ID)) {
                C1257f.m1711a().mo14478a(jSONObject.getString(AgooConstants.MESSAGE_ID));
            }
            if (!jSONObject.has(PushConsts.CMD_ACTION) || (bVar = f1885b.get(jSONObject.getString(PushConsts.CMD_ACTION))) == null) {
                return false;
            }
            return bVar.mo14470a(obj, jSONObject);
        } catch (Exception unused) {
            return false;
        }
    }
}
