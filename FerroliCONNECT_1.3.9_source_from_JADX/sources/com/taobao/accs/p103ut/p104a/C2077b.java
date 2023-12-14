package com.taobao.accs.p103ut.p104a;

import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.accs.ut.a.b */
/* compiled from: Taobao */
public class C2077b {

    /* renamed from: a */
    public String f3491a;

    /* renamed from: b */
    public String f3492b;

    /* renamed from: c */
    public boolean f3493c;

    /* renamed from: d */
    public String f3494d;

    /* renamed from: e */
    public String f3495e;

    /* renamed from: f */
    private final String f3496f = "BindUser";

    /* renamed from: g */
    private boolean f3497g = false;

    /* renamed from: a */
    public void mo18538a(String str) {
        this.f3494d = str;
    }

    /* renamed from: a */
    public void mo18537a(ErrorCode errorCode) {
        if (errorCode.getCodeInt() != AccsErrorCode.SUCCESS.getCodeInt()) {
            mo18538a(errorCode.getMsg());
        }
    }

    /* renamed from: a */
    public void mo18536a() {
        m3714b("BindUser");
    }

    /* renamed from: b */
    private void m3714b(String str) {
        String str2;
        String str3;
        if (!this.f3497g) {
            this.f3497g = true;
            HashMap hashMap = new HashMap();
            try {
                str3 = this.f3491a;
                try {
                    str2 = String.valueOf(Constants.SDK_VERSION_CODE);
                    try {
                        hashMap.put("device_id", this.f3491a);
                        hashMap.put("bind_date", this.f3492b);
                        hashMap.put("ret", this.f3493c ? "y" : "n");
                        hashMap.put("fail_reasons", this.f3494d);
                        hashMap.put("user_id", this.f3495e);
                        if (ALog.isPrintLog(ALog.Level.D)) {
                            ALog.m3725d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(66001, str, (Object) str3, (Object) null, (Object) str2, (Map<String, String>) hashMap);
                    } catch (Throwable th) {
                        th = th;
                        ALog.m3725d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    str2 = null;
                    ALog.m3725d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th3) {
                th = th3;
                str3 = null;
                str2 = null;
                ALog.m3725d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
