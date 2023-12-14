package com.taobao.agoo.p105a;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorCode;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.C2122a;
import com.taobao.agoo.C2129b;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.IListAliasCallback;
import com.taobao.agoo.IListAliasCallbackInner;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.p105a.p106a.C2124a;
import com.taobao.agoo.p105a.p106a.C2125b;
import com.taobao.agoo.p105a.p106a.C2126c;
import com.taobao.agoo.p105a.p106a.C2127d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.Config;
import org.json.JSONObject;

/* renamed from: com.taobao.agoo.a.b */
/* compiled from: Taobao */
public class C2128b extends AccsAbstractDataListener {

    /* renamed from: b */
    public static C2123a f3603b;

    /* renamed from: a */
    public Map<String, ICallback> f3604a = new HashMap();

    public void onBind(String str, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onData(String str, String str2, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onSendData(String str, String str2, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onUnbind(String str, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public C2128b(Context context) {
        if (f3603b == null) {
            f3603b = new C2123a(context.getApplicationContext());
        }
    }

    public void onResponse(String str, String str2, int i, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        String string;
        try {
            if ("AgooDeviceCmd".equals(str)) {
                ICallback iCallback = this.f3604a.get(str2);
                if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                    String str4 = new String(bArr, "utf-8");
                    ALog.m3728i("RequestListener", "RequestListener onResponse", Constants.KEY_DATA_ID, str2, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, iCallback, "json", str4);
                    JSONObject jSONObject = new JSONObject(str4);
                    String string2 = JsonUtility.getString(jSONObject, C2125b.JSON_ERRORCODE, (String) null);
                    String string3 = JsonUtility.getString(jSONObject, C2125b.JSON_CMD, (String) null);
                    if (!C2125b.JSON_SUCCESS.equals(string2)) {
                        if (iCallback != null) {
                            ErrorCode build = C2122a.m3819a(string2, string3 + "报错").build();
                            iCallback.onFailure(build.getCode(), build.getMsg());
                        }
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f3604a.remove(str2);
                            return;
                        }
                        return;
                    } else if (C2126c.JSON_CMD_REGISTER.equals(string3)) {
                        String string4 = JsonUtility.getString(jSONObject, "deviceId", (String) null);
                        if (!TextUtils.isEmpty(string4)) {
                            Config.m3893a(GlobalClientInfo.getContext(), string4);
                            f3603b.mo18618a(GlobalClientInfo.getContext().getPackageName());
                            if (iCallback instanceof IRegister) {
                                UtilityImpl.saveUtdid(Config.PREFERENCES, GlobalClientInfo.getContext());
                                ((IRegister) iCallback).onSuccess(string4);
                            }
                        } else if (iCallback != null) {
                            ErrorCode build2 = C2122a.m3819a(C2125b.JSON_SUCCESS, string3 + "成功，但是未返回deviceId").detail(str4).build();
                            iCallback.onFailure(build2.getCode(), build2.getMsg());
                        }
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f3604a.remove(str2);
                            return;
                        }
                        return;
                    } else {
                        if (!(!C2124a.JSON_CMD_ADDALIAS.equals(string3) || (string = JsonUtility.getString(jSONObject, "pushAliasToken", (String) null)) == null || iCallback.extra == null)) {
                            C2129b.m3841a(GlobalClientInfo.getContext(), iCallback.extra, string);
                        }
                        if (C2124a.JSON_CMD_REMOVEALIAS.equals(string3) && iCallback.extra != null) {
                            C2129b.m3841a(GlobalClientInfo.getContext(), iCallback.extra, (String) null);
                        }
                        if (!C2124a.JSON_CMD_ADDALIAS.equals(string3) && !C2124a.JSON_CMD_REMOVEALIAS.equals(string3) && !C2124a.JSON_CMD_REMOVEALLALIAS.equals(string3) && !C2124a.JSON_CMD_REMOVEALLALIASANDADDALIAS.equals(string3) && !C2124a.JSON_CMD_RESETAlIASANDBINDCURRENT.equals(string3)) {
                            if (!C2124a.JSON_CMD_RESETALIASDEVICEONE2ONE.equals(string3)) {
                                if (C2124a.JSON_CMD_LISTALIAS.equals(string3)) {
                                    m3837a(jSONObject, (IListAliasCallback) iCallback);
                                    if ("AgooDeviceCmd".equals(str)) {
                                        this.f3604a.remove(str2);
                                        return;
                                    }
                                    return;
                                } else if ((C2127d.JSON_CMD_ENABLEPUSH.equals(string3) || C2127d.JSON_CMD_DISABLEPUSH.equals(string3)) && iCallback != null) {
                                    iCallback.onSuccess();
                                }
                            }
                        }
                        if (iCallback != null) {
                            iCallback.onSuccess();
                        }
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f3604a.remove(str2);
                            return;
                        }
                        return;
                    }
                } else if (iCallback != null) {
                    ErrorCode build3 = C2122a.m3818a(i, str3).build();
                    iCallback.onFailure(build3.getCode(), build3.getMsg());
                }
            }
            if (!"AgooDeviceCmd".equals(str)) {
                return;
            }
        } catch (Throwable th) {
            if ("AgooDeviceCmd".equals(str)) {
                this.f3604a.remove(str2);
            }
            throw th;
        }
        this.f3604a.remove(str2);
    }

    /* renamed from: a */
    private void m3837a(JSONObject jSONObject, IListAliasCallback iListAliasCallback) {
        Map map = JsonUtility.getMap(jSONObject, C2124a.JSON_ALIAS_TOKEN_MAP);
        if (map == null) {
            map = new HashMap();
        }
        if (iListAliasCallback == null) {
            return;
        }
        if (iListAliasCallback instanceof IListAliasCallbackInner) {
            ((IListAliasCallbackInner) iListAliasCallback).onSuccess((Map<String, String>) map);
        } else {
            iListAliasCallback.onSuccess(new ArrayList(map.keySet()));
        }
    }
}
