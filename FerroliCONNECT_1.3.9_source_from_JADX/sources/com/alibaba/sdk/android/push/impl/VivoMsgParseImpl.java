package com.alibaba.sdk.android.push.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VivoMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    public static final String TAG = "MPS:VivoMsgParseImpl";
    private Context context;

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_VIVO;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str = null;
        if (intent == null) {
            ALog.m3727e(TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            String stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_VIVO_PAYLOAD);
            ALog.m3728i(TAG, "parseMsgFromIntent msg:" + stringExtra, new Object[0]);
            str = fixVivoMsg(stringExtra, intent);
            ALog.m3728i(TAG, "after fixup msg:" + str, new Object[0]);
            return str;
        } catch (Throwable th) {
            ALog.m3726e(TAG, "parseMsgFromIntent ", th, new Object[0]);
            return str;
        }
    }

    public void setContext(Context context2) {
        this.context = context2.getApplicationContext();
    }

    private String fixVivoMsg(String str, Intent intent) {
        ALog.m3728i(TAG, "fixVivoMsg intent:" + printBundle(intent.getExtras(), 1), new Object[0]);
        if (str == null) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (!jSONObject.has("p") && this.context != null) {
                        jSONObject.put("p", this.context.getPackageName());
                    }
                    if (!jSONObject.has("ext")) {
                        jSONObject.put("ext", intent.getStringExtra("ext"));
                    }
                    if (!jSONObject.has("b")) {
                        jSONObject.put("b", intent.getStringExtra("b"));
                    }
                    if (!jSONObject.has("f")) {
                        jSONObject.put("f", intent.getLongExtra("f", 0));
                    }
                    if (!jSONObject.has("i")) {
                        jSONObject.put("i", intent.getStringExtra("i"));
                    }
                    jSONArray.put(i, jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jSONArray.toString();
        } catch (JSONException unused) {
            return str;
        }
    }

    private static final String printBundle(Bundle bundle, int i) {
        StringBuilder sb = new StringBuilder();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                for (int i2 = 0; i2 < i; i2++) {
                    sb.append(9);
                }
                if (obj instanceof String) {
                    sb.append("String\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(obj);
                    sb.append(10);
                } else if (obj instanceof Integer) {
                    sb.append("int\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(obj);
                    sb.append(10);
                } else if (obj instanceof Long) {
                    sb.append("long\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(obj);
                    sb.append(10);
                } else if (obj instanceof Boolean) {
                    sb.append("boolean\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(obj);
                    sb.append(10);
                } else if (obj instanceof Bundle) {
                    sb.append("Bundle\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(10);
                    sb.append(printBundle((Bundle) obj, i + 1));
                } else {
                    sb.append("unknown\t");
                    sb.append(str);
                    sb.append(9);
                    sb.append(obj);
                    sb.append(10);
                }
            }
        }
        return sb.toString();
    }
}
