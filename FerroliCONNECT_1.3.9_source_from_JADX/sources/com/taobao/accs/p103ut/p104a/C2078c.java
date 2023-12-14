package com.taobao.accs.p103ut.p104a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.accs.ut.a.c */
/* compiled from: Taobao */
public class C2078c {

    /* renamed from: a */
    public int f3498a;

    /* renamed from: b */
    public int f3499b;

    /* renamed from: c */
    public boolean f3500c = false;

    /* renamed from: d */
    public int f3501d = 0;

    /* renamed from: e */
    public int f3502e = 0;

    /* renamed from: f */
    public String f3503f;

    /* renamed from: g */
    public String f3504g;

    /* renamed from: h */
    public long f3505h;

    /* renamed from: i */
    public boolean f3506i;

    /* renamed from: j */
    public boolean f3507j;

    /* renamed from: k */
    private long f3508k = 0;

    /* renamed from: a */
    public void mo18539a() {
        String str;
        String str2;
        String valueOf;
        String str3;
        long currentTimeMillis = System.currentTimeMillis();
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d("MonitorStatistic", "commitUT interval:" + (currentTimeMillis - this.f3508k) + " interval1:" + (currentTimeMillis - this.f3505h), new Object[0]);
        }
        if (currentTimeMillis - this.f3508k > 1200000 && currentTimeMillis - this.f3505h > 60000) {
            HashMap hashMap = new HashMap();
            String str4 = null;
            try {
                str = String.valueOf(this.f3501d);
                try {
                    valueOf = String.valueOf(this.f3502e);
                    try {
                        str2 = String.valueOf(Constants.SDK_VERSION_CODE);
                    } catch (Throwable th) {
                        th = th;
                        str3 = valueOf;
                        str2 = null;
                        str4 = str3;
                        ALog.m3725d("MonitorStatistic", UTMini.getCommitInfo(66001, str, str4, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    str2 = null;
                    ALog.m3725d("MonitorStatistic", UTMini.getCommitInfo(66001, str, str4, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
                try {
                    hashMap.put("connStatus", String.valueOf(this.f3498a));
                    hashMap.put("connType", String.valueOf(this.f3499b));
                    hashMap.put("tcpConnected", String.valueOf(this.f3500c));
                    hashMap.put("proxy", String.valueOf(this.f3503f));
                    hashMap.put("startServiceTime", String.valueOf(this.f3505h));
                    hashMap.put("commitTime", String.valueOf(currentTimeMillis));
                    hashMap.put("networkAvailable", String.valueOf(this.f3506i));
                    hashMap.put("threadIsalive", String.valueOf(this.f3507j));
                    hashMap.put("url", this.f3504g);
                    if (ALog.isPrintLog(ALog.Level.D)) {
                        try {
                            ALog.m3725d("MonitorStatistic", UTMini.getCommitInfo(66001, str, valueOf, str2, (Map<String, String>) hashMap), new Object[0]);
                        } catch (Throwable th3) {
                            th = th3;
                            str4 = valueOf;
                        }
                    }
                    String str5 = str2;
                    str3 = valueOf;
                    try {
                        UTMini.getInstance().commitEvent(66001, "MONITOR", (Object) str, (Object) valueOf, (Object) str2, (Map<String, String>) hashMap);
                        this.f3508k = currentTimeMillis;
                    } catch (Throwable th4) {
                        th = th4;
                        str2 = str5;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str6 = str2;
                    str3 = valueOf;
                    str4 = str3;
                    ALog.m3725d("MonitorStatistic", UTMini.getCommitInfo(66001, str, str4, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th6) {
                th = th6;
                str2 = null;
                str = null;
                ALog.m3725d("MonitorStatistic", UTMini.getCommitInfo(66001, str, str4, str2, (Map<String, String>) hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
