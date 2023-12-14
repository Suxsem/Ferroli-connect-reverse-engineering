package com.igexin.push.core.p051d;

import android.content.ContentValues;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p088g.p089a.C1563b;
import com.igexin.push.util.C1581f;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.d.g */
public class C1338g extends C1563b {

    /* renamed from: a */
    public boolean f2086a;

    /* renamed from: g */
    private boolean f2087g = false;

    /* renamed from: h */
    private int f2088h;

    public C1338g(String str, byte[] bArr, int i, boolean z) {
        super(str);
        this.f2087g = z;
        this.f2088h = i;
        m2026a(bArr, i);
    }

    /* renamed from: a */
    private void m2026a(byte[] bArr, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "upload_BI");
            jSONObject.put("BIType", String.valueOf(i));
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("BIData", new String(C1177f.m1344f(bArr, 0), "UTF-8"));
            mo15202b(jSONObject.toString().getBytes());
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public void mo14696a(byte[] bArr) {
        JSONObject jSONObject = new JSONObject(new String(bArr));
        if (jSONObject.has("result") && "ok".equals(jSONObject.getString("result"))) {
            this.f2086a = true;
            if (this.f2088h == 10) {
                C1581f.m3243g();
            }
            if (this.f2087g) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("type", "0");
                C1340e.m2032a().mo14712i().mo14356a("bi", contentValues, new String[]{"type"}, new String[]{"2"});
                C1312h.m1937a().mo14678c(System.currentTimeMillis());
            }
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
