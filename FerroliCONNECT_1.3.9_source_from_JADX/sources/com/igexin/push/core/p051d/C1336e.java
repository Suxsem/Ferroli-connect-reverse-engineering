package com.igexin.push.core.p051d;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1224a;
import com.igexin.push.config.C1236m;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.p047a.C1269r;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p088g.p089a.C1563b;
import com.igexin.push.util.C1584i;
import org.json.JSONArray;

/* renamed from: com.igexin.push.core.d.e */
public class C1336e extends C1563b {

    /* renamed from: a */
    public static JSONArray f2084a;

    public C1336e(String str, JSONArray jSONArray) {
        super(str);
        mo14699a(jSONArray);
    }

    /* renamed from: a */
    public void mo14695a(Exception exc) {
        C1312h.m1937a().mo14683d(System.currentTimeMillis());
        C1179b.m1354a("-> get idc config " + exc.toString());
    }

    /* renamed from: a */
    public void mo14699a(JSONArray jSONArray) {
        f2084a = jSONArray;
    }

    /* renamed from: a */
    public void mo14696a(byte[] bArr) {
        if (bArr != null) {
            try {
                String str = new String(C1196a.m1439c(C1584i.m3247a(bArr, 0)));
                C1179b.m1354a("->get idc config server resp data : " + str);
                C1224a.m1601a().mo14446b(str);
                C1236m.m1628a(str, true);
                C1312h.m1937a().mo14683d(0);
                C1224a.m1601a().mo14443a(f2084a.toString());
                SDKUrlConfig.setIdcConfigUrl(C1269r.m1770a(f2084a));
            } catch (Exception e) {
                C1312h.m1937a().mo14683d(System.currentTimeMillis());
                throw e;
            }
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
