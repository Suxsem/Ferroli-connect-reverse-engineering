package com.taobao.accs.p103ut.p104a;

import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.accs.ut.a.a */
/* compiled from: Taobao */
public class C2076a {

    /* renamed from: a */
    public String f3485a;

    /* renamed from: b */
    public String f3486b;

    /* renamed from: c */
    public boolean f3487c;

    /* renamed from: d */
    public String f3488d;

    /* renamed from: e */
    private final String f3489e = "BindApp";

    /* renamed from: f */
    private boolean f3490f = false;

    /* renamed from: a */
    public void mo18535a(String str) {
        this.f3488d = str;
    }

    /* renamed from: a */
    public void mo18534a(ErrorCode errorCode) {
        if (errorCode.getCodeInt() != AccsErrorCode.SUCCESS.getCodeInt()) {
            mo18535a(errorCode.getMsg());
        }
    }

    /* renamed from: a */
    public void mo18533a() {
        m3710b("BindApp");
    }

    /* renamed from: b */
    private void m3710b(String str) {
        String str2;
        String str3;
        if (!this.f3490f) {
            this.f3490f = true;
            HashMap hashMap = new HashMap();
            try {
                str3 = this.f3485a;
                try {
                    str2 = String.valueOf(Constants.SDK_VERSION_CODE);
                } catch (Throwable th) {
                    th = th;
                    str2 = null;
                    ALog.m3725d("BindAppStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
                try {
                    hashMap.put("device_id", this.f3485a);
                    hashMap.put("bind_date", this.f3486b);
                    hashMap.put("ret", this.f3487c ? "y" : "n");
                    hashMap.put("fail_reasons", this.f3488d);
                    hashMap.put("push_token", "");
                    UTMini.getInstance().commitEvent(66001, str, (Object) str3, (Object) null, (Object) str2, (Map<String, String>) hashMap);
                } catch (Throwable th2) {
                    th = th2;
                    ALog.m3725d("BindAppStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th3) {
                th = th3;
                str3 = null;
                str2 = null;
                ALog.m3725d("BindAppStatistic", UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
