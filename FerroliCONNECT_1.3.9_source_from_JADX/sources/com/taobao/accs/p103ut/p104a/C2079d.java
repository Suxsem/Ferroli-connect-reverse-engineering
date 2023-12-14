package com.taobao.accs.p103ut.p104a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.accs.ut.a.d */
/* compiled from: Taobao */
public class C2079d {

    /* renamed from: a */
    public String f3509a;

    /* renamed from: b */
    public String f3510b;

    /* renamed from: c */
    public String f3511c;

    /* renamed from: d */
    public String f3512d;

    /* renamed from: e */
    public String f3513e;

    /* renamed from: f */
    public String f3514f;

    /* renamed from: g */
    public String f3515g;

    /* renamed from: h */
    public boolean f3516h = false;

    /* renamed from: i */
    public String f3517i;

    /* renamed from: j */
    private final String f3518j = "receiveMessage";

    /* renamed from: k */
    private boolean f3519k = false;

    /* renamed from: a */
    public void mo18540a() {
        String str;
        String str2;
        if (!this.f3519k) {
            this.f3519k = true;
            HashMap hashMap = new HashMap();
            try {
                str2 = this.f3509a;
                try {
                    str = String.valueOf(Constants.SDK_VERSION_CODE);
                } catch (Throwable th) {
                    th = th;
                    str = null;
                    ALog.m3725d("ReceiveMessage", UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
                try {
                    hashMap.put("device_id", this.f3509a);
                    hashMap.put("data_id", this.f3510b);
                    hashMap.put("receive_date", this.f3511c);
                    hashMap.put("to_bz_date", this.f3512d);
                    hashMap.put("service_id", this.f3513e);
                    hashMap.put("data_length", this.f3514f);
                    hashMap.put("msg_type", this.f3515g);
                    hashMap.put("repeat", this.f3516h ? "y" : "n");
                    hashMap.put("user_id", this.f3517i);
                    UTMini.getInstance().commitEvent(66001, "receiveMessage", (Object) str2, (Object) null, (Object) str, (Map<String, String>) hashMap);
                } catch (Throwable th2) {
                    th = th2;
                    ALog.m3725d("ReceiveMessage", UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th3) {
                th = th3;
                str2 = null;
                str = null;
                ALog.m3725d("ReceiveMessage", UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
