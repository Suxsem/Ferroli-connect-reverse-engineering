package com.taobao.agoo.p105a.p106a;

import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;

/* renamed from: com.taobao.agoo.a.a.d */
/* compiled from: Taobao */
public class C2127d extends C2125b {
    public static final String JSON_CMD_DISABLEPUSH = "disablePush";
    public static final String JSON_CMD_ENABLEPUSH = "enablePush";

    /* renamed from: a */
    public String f3600a;

    /* renamed from: b */
    public String f3601b;

    /* renamed from: c */
    public String f3602c;

    /* renamed from: a */
    public byte[] mo18622a() {
        try {
            JsonUtility.JsonObjectBuilder jsonObjectBuilder = new JsonUtility.JsonObjectBuilder();
            jsonObjectBuilder.put(C2125b.JSON_CMD, this.f3584e).put(Constants.KEY_APP_KEY, this.f3600a);
            if (TextUtils.isEmpty(this.f3601b)) {
                jsonObjectBuilder.put("utdid", this.f3602c);
            } else {
                jsonObjectBuilder.put("deviceId", this.f3601b);
            }
            String jSONObject = jsonObjectBuilder.build().toString();
            ALog.m3728i("SwitchDO", "buildData", Constants.KEY_DATA, jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.m3726e("SwitchDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m3835a(String str, String str2, String str3, boolean z) {
        C2127d dVar = new C2127d();
        dVar.f3600a = str;
        dVar.f3601b = str2;
        dVar.f3602c = str3;
        if (z) {
            dVar.f3584e = JSON_CMD_ENABLEPUSH;
        } else {
            dVar.f3584e = JSON_CMD_DISABLEPUSH;
        }
        return dVar.mo18622a();
    }
}
