package com.p092ta.utdid2.device;

import com.p092ta.p093a.p095b.C1971e;
import com.p092ta.p093a.p096c.C1982f;
import com.taobao.accs.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.ta.utdid2.device.b */
class C1994b {

    /* renamed from: d */
    int f3189d = -1;

    /* renamed from: a */
    static boolean m3392a(int i) {
        return i >= 0 && i != 10012;
    }

    C1994b() {
    }

    /* renamed from: a */
    static C1994b m3391a(String str) {
        JSONObject jSONObject;
        C1994b bVar = new C1994b();
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has("code")) {
                bVar.f3189d = jSONObject2.getInt("code");
            }
            if (jSONObject2.has(Constants.KEY_DATA) && (jSONObject = jSONObject2.getJSONObject(Constants.KEY_DATA)) != null && jSONObject.has("utdid")) {
                String string = jSONObject.getString("utdid");
                if (C1996d.m3403c(string)) {
                    C1971e.m3340a(string);
                }
            }
            C1982f.m3366a("BizResponse", "content", str);
        } catch (JSONException e) {
            C1982f.m3366a("", e.toString());
        }
        return bVar;
    }
}
