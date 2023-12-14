package com.igexin.assist.action;

import android.content.Context;
import android.text.TextUtils;
import com.igexin.assist.MessageBean;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.assist.util.C1149a;
import java.util.UUID;
import org.json.JSONObject;

/* renamed from: com.igexin.assist.action.d */
class C1145d {

    /* renamed from: a */
    private byte[] f1528a;

    /* renamed from: b */
    private String f1529b;

    /* renamed from: c */
    private String f1530c;

    /* renamed from: d */
    private String f1531d;

    /* renamed from: e */
    private String f1532e;

    /* renamed from: f */
    private String f1533f;

    /* renamed from: g */
    private String f1534g;

    C1145d() {
    }

    /* renamed from: a */
    public void mo14194a(MessageBean messageBean) {
        try {
            Context context = messageBean.getContext();
            String stringMessage = messageBean.getStringMessage();
            if (TextUtils.isEmpty(stringMessage)) {
                return;
            }
            if (context != null) {
                this.f1531d = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(AssistPushConsts.GETUI_APPID);
                if (!TextUtils.isEmpty(this.f1531d)) {
                    this.f1532e = context.getPackageName();
                    String messageSource = TextUtils.isEmpty(messageBean.getMessageSource()) ? "" : messageBean.getMessageSource();
                    this.f1530c = messageSource + UUID.randomUUID().toString();
                    String a = C1149a.m1227a(stringMessage, this.f1531d);
                    if (!TextUtils.isEmpty(a)) {
                        JSONObject jSONObject = new JSONObject(a);
                        if (jSONObject.has(AssistPushConsts.MSG_KEY_TASKID)) {
                            this.f1529b = jSONObject.getString(AssistPushConsts.MSG_KEY_TASKID);
                        }
                        if (jSONObject.has(AssistPushConsts.MSG_KEY_ACTION)) {
                            this.f1533f = jSONObject.getString(AssistPushConsts.MSG_KEY_ACTION);
                        }
                        if (jSONObject.has(AssistPushConsts.MSG_KEY_CONTENT) && !TextUtils.isEmpty(jSONObject.getString(AssistPushConsts.MSG_KEY_CONTENT))) {
                            this.f1528a = jSONObject.getString(AssistPushConsts.MSG_KEY_CONTENT).getBytes();
                        }
                        if (jSONObject.has(AssistPushConsts.MSG_KEY_ACTION_CHAINS)) {
                            this.f1534g = jSONObject.getString(AssistPushConsts.MSG_KEY_ACTION_CHAINS);
                            if (!TextUtils.isEmpty(this.f1534g)) {
                                JSONObject jSONObject2 = new JSONObject(this.f1534g);
                                jSONObject2.put("extra_actionid", "40550");
                                this.f1534g = jSONObject2.toString();
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public boolean mo14195a() {
        return !(this.f1528a == null && this.f1534g == null) && !TextUtils.isEmpty(this.f1529b) && !TextUtils.isEmpty(this.f1532e) && !TextUtils.isEmpty(this.f1531d) && !TextUtils.isEmpty(this.f1533f) && !TextUtils.isEmpty(this.f1530c);
    }

    /* renamed from: b */
    public byte[] mo14196b() {
        return this.f1528a;
    }

    /* renamed from: c */
    public String mo14197c() {
        return this.f1529b;
    }

    /* renamed from: d */
    public String mo14198d() {
        return this.f1530c;
    }

    /* renamed from: e */
    public String mo14199e() {
        return this.f1531d;
    }

    /* renamed from: f */
    public String mo14200f() {
        return this.f1533f;
    }

    /* renamed from: g */
    public String mo14201g() {
        return this.f1532e;
    }

    /* renamed from: h */
    public String mo14202h() {
        return this.f1534g;
    }
}
