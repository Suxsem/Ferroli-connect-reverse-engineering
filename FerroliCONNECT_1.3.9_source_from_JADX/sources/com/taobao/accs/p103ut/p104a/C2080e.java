package com.taobao.accs.p103ut.p104a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.accs.ut.a.e */
/* compiled from: Taobao */
public class C2080e {

    /* renamed from: a */
    public String f3520a;

    /* renamed from: b */
    public String f3521b;

    /* renamed from: c */
    public String f3522c;

    /* renamed from: d */
    public String f3523d;

    /* renamed from: e */
    public String f3524e;

    /* renamed from: f */
    public String f3525f;

    /* renamed from: g */
    private final String f3526g = "sendAck";

    /* renamed from: h */
    private boolean f3527h = false;

    /* renamed from: a */
    public void mo18541a() {
        String str;
        String str2;
        if (!this.f3527h) {
            this.f3527h = true;
            HashMap hashMap = new HashMap();
            try {
                str2 = this.f3520a;
                try {
                    str = String.valueOf(Constants.SDK_VERSION_CODE);
                    try {
                        hashMap.put("device_id", this.f3520a);
                        hashMap.put("session_id", this.f3521b);
                        hashMap.put("data_id", this.f3522c);
                        hashMap.put("ack_date", this.f3523d);
                        hashMap.put("service_id", this.f3524e);
                        hashMap.put("fail_reasons", this.f3525f);
                        UTMini.getInstance().commitEvent(66001, "sendAck", (Object) str2, (Object) null, (Object) str, (Map<String, String>) hashMap);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    str = null;
                    ALog.m3725d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th3) {
                th = th3;
                str2 = null;
                str = null;
                ALog.m3725d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
