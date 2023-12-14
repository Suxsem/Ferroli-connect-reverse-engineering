package com.igexin.push.core.p051d;

import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.C1287j;
import com.igexin.push.core.p050c.C1304ai;
import com.igexin.push.p088g.p089a.C1563b;
import com.igexin.sdk.PushConsts;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.d.h */
public class C1339h extends C1563b {

    /* renamed from: a */
    public static final String f2089a = "com.igexin.push.core.d.h";

    /* renamed from: g */
    private String f2090g;

    /* renamed from: h */
    private ArrayList<C1287j> f2091h;

    public C1339h(byte[] bArr, String str, ArrayList<C1287j> arrayList) {
        super(SDKUrlConfig.getBiUploadServiceUrl());
        m2029a(bArr, str, arrayList);
    }

    /* renamed from: a */
    private void m2029a(byte[] bArr, String str, ArrayList<C1287j> arrayList) {
        this.f2090g = str;
        this.f2091h = arrayList;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "upload_BI");
            jSONObject.put("BIType", str);
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
            C1304ai.m1896a().mo14648a(this.f2090g, this.f2091h);
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
