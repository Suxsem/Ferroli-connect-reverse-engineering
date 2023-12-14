package com.taobao.agoo.p105a.p106a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;

/* renamed from: com.taobao.agoo.a.a.a */
/* compiled from: Taobao */
public class C2124a extends C2125b {
    public static final String INVALID_TOKEN = "deprecated_alias_token_should_be_ignored";
    public static final String JSON_ALIAS_TOKEN_MAP = "aliasTokenMap";
    public static final String JSON_CMD_ADDALIAS = "setAlias";
    public static final String JSON_CMD_LISTALIAS = "getAliasTokenMap";
    public static final String JSON_CMD_REMOVEALIAS = "removeAlias";
    public static final String JSON_CMD_REMOVEALLALIAS = "unbindAllAliasByDeviceId";
    public static final String JSON_CMD_REMOVEALLALIASANDADDALIAS = "resetDeviceAndBindCurrentAlias";
    public static final String JSON_CMD_RESETALIASDEVICEONE2ONE = "resetDeviceAndAliasToSingleBind";
    public static final String JSON_CMD_RESETAlIASANDBINDCURRENT = "resetAliasAndBindCurrentDevice";

    /* renamed from: a */
    public String f3580a;

    /* renamed from: b */
    public String f3581b;

    /* renamed from: c */
    public String f3582c;

    /* renamed from: d */
    public String f3583d;

    /* renamed from: a */
    public byte[] mo18620a() {
        try {
            String jSONObject = new JsonUtility.JsonObjectBuilder().put(C2125b.JSON_CMD, this.f3584e).put(Constants.KEY_APP_KEY, this.f3580a).put("deviceId", this.f3581b).put("alias", this.f3582c).put("pushAliasToken", this.f3583d).build().toString();
            ALog.m3728i("AliasDO", "buildData", Constants.KEY_DATA, jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.m3726e("AliasDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m3826a(String str, String str2, String str3) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3582c = str3;
        aVar.f3584e = JSON_CMD_RESETAlIASANDBINDCURRENT;
        return aVar.mo18620a();
    }

    /* renamed from: b */
    public static byte[] m3829b(String str, String str2, String str3) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3582c = str3;
        aVar.f3584e = JSON_CMD_ADDALIAS;
        return aVar.mo18620a();
    }

    /* renamed from: a */
    public static byte[] m3827a(String str, String str2, String str3, String str4) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3582c = str3;
        aVar.f3583d = str4;
        aVar.f3584e = JSON_CMD_REMOVEALIAS;
        return aVar.mo18620a();
    }

    /* renamed from: a */
    public static byte[] m3825a(String str, String str2) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3584e = JSON_CMD_REMOVEALLALIAS;
        return aVar.mo18620a();
    }

    /* renamed from: b */
    public static byte[] m3828b(String str, String str2) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3584e = JSON_CMD_LISTALIAS;
        return aVar.mo18620a();
    }

    /* renamed from: c */
    public static byte[] m3830c(String str, String str2, String str3) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3582c = str3;
        aVar.f3584e = JSON_CMD_REMOVEALLALIASANDADDALIAS;
        return aVar.mo18620a();
    }

    /* renamed from: d */
    public static byte[] m3831d(String str, String str2, String str3) {
        C2124a aVar = new C2124a();
        aVar.f3580a = str;
        aVar.f3581b = str2;
        aVar.f3582c = str3;
        aVar.f3584e = JSON_CMD_RESETALIASDEVICEONE2ONE;
        return aVar.mo18620a();
    }
}
