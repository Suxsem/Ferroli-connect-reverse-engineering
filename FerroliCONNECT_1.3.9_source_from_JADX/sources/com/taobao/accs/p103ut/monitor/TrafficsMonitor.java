package com.taobao.accs.p103ut.monitor;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.p101a.C2006a;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.taobao.accs.ut.monitor.TrafficsMonitor */
/* compiled from: Taobao */
public class TrafficsMonitor {

    /* renamed from: a */
    private Map<String, List<C2082a>> f3530a = new HashMap();

    /* renamed from: b */
    private Map<String, String> f3531b = new HashMap<String, String>() {
        {
            put("im", "512");
            put("motu", "513");
            put("acds", "514");
            put(GlobalClientInfo.AGOO_SERVICE_ID, "515");
            put(AgooConstants.AGOO_SERVICE_AGOOACK, "515");
            put("agooTokenReport", "515");
            put("accsSelf", "1000");
        }
    };

    /* renamed from: c */
    private int f3532c = 0;

    /* renamed from: d */
    private Context f3533d;

    /* renamed from: e */
    private String f3534e = "";

    @Monitor(module = "NetworkSDK", monitorPoint = "TrafficStats")
    /* renamed from: com.taobao.accs.ut.monitor.TrafficsMonitor$StatTrafficMonitor */
    /* compiled from: Taobao */
    public static class StatTrafficMonitor extends BaseMonitor {
        @Dimension
        public String bizId;
        @Dimension
        public String date;
        @Dimension
        public String host;
        @Dimension
        public boolean isBackground;
        @Dimension
        public String serviceId;
        @Measure
        public long size;
    }

    public TrafficsMonitor(Context context) {
        this.f3533d = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008f, code lost:
        if (r10.f3532c < 10) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0091, code lost:
        m3721b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo18576a(com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a r11) {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x0098
            java.lang.String r0 = r11.f3540e
            if (r0 == 0) goto L_0x0098
            long r0 = r11.f3541f
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0098
            java.lang.String r0 = r11.f3538c
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0019
            java.lang.String r0 = "accsSelf"
            goto L_0x001b
        L_0x0019:
            java.lang.String r0 = r11.f3538c
        L_0x001b:
            r11.f3538c = r0
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$a>> r0 = r10.f3530a
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = r10.f3531b     // Catch:{ all -> 0x0095 }
            java.lang.String r2 = r11.f3538c     // Catch:{ all -> 0x0095 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0095 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0095 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0095 }
            if (r2 == 0) goto L_0x0032
            monitor-exit(r0)     // Catch:{ all -> 0x0095 }
            return
        L_0x0032:
            r11.f3537b = r1     // Catch:{ all -> 0x0095 }
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ all -> 0x0095 }
            com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ all -> 0x0095 }
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$a>> r2 = r10.f3530a     // Catch:{ all -> 0x0095 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0095 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x0095 }
            r3 = 1
            if (r2 == 0) goto L_0x0078
            java.util.Iterator r4 = r2.iterator()     // Catch:{ all -> 0x0095 }
        L_0x0048:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x0095 }
            if (r5 == 0) goto L_0x0071
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x0095 }
            com.taobao.accs.ut.monitor.TrafficsMonitor$a r5 = (com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a) r5     // Catch:{ all -> 0x0095 }
            boolean r6 = r5.f3539d     // Catch:{ all -> 0x0095 }
            boolean r7 = r11.f3539d     // Catch:{ all -> 0x0095 }
            if (r6 != r7) goto L_0x0048
            java.lang.String r6 = r5.f3540e     // Catch:{ all -> 0x0095 }
            if (r6 == 0) goto L_0x0048
            java.lang.String r6 = r5.f3540e     // Catch:{ all -> 0x0095 }
            java.lang.String r7 = r11.f3540e     // Catch:{ all -> 0x0095 }
            boolean r6 = r6.equals(r7)     // Catch:{ all -> 0x0095 }
            if (r6 == 0) goto L_0x0048
            long r6 = r5.f3541f     // Catch:{ all -> 0x0095 }
            long r8 = r11.f3541f     // Catch:{ all -> 0x0095 }
            long r6 = r6 + r8
            r5.f3541f = r6     // Catch:{ all -> 0x0095 }
            r4 = 0
            goto L_0x0072
        L_0x0071:
            r4 = 1
        L_0x0072:
            if (r4 == 0) goto L_0x0080
            r2.add(r11)     // Catch:{ all -> 0x0095 }
            goto L_0x0080
        L_0x0078:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0095 }
            r2.<init>()     // Catch:{ all -> 0x0095 }
            r2.add(r11)     // Catch:{ all -> 0x0095 }
        L_0x0080:
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$a>> r11 = r10.f3530a     // Catch:{ all -> 0x0095 }
            r11.put(r1, r2)     // Catch:{ all -> 0x0095 }
            int r11 = r10.f3532c     // Catch:{ all -> 0x0095 }
            int r11 = r11 + r3
            r10.f3532c = r11     // Catch:{ all -> 0x0095 }
            monitor-exit(r0)     // Catch:{ all -> 0x0095 }
            int r11 = r10.f3532c
            r0 = 10
            if (r11 < r0) goto L_0x0098
            r10.m3721b()
            goto L_0x0098
        L_0x0095:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0095 }
            throw r11
        L_0x0098:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.p103ut.monitor.TrafficsMonitor.mo18576a(com.taobao.accs.ut.monitor.TrafficsMonitor$a):void");
    }

    /* renamed from: b */
    private void m3721b() {
        boolean z;
        String str;
        synchronized (this.f3530a) {
            String a = UtilityImpl.m3736a(System.currentTimeMillis());
            if (TextUtils.isEmpty(this.f3534e) || this.f3534e.equals(a)) {
                str = a;
                z = false;
            } else {
                str = this.f3534e;
                z = true;
            }
            for (String str2 : this.f3530a.keySet()) {
                for (C2082a aVar : this.f3530a.get(str2)) {
                    if (aVar != null) {
                        C2006a.m3427a(this.f3533d).mo18319a(aVar.f3540e, aVar.f3538c, this.f3531b.get(aVar.f3538c), aVar.f3539d, aVar.f3541f, str);
                    }
                }
            }
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d("TrafficsMonitor", "savetoDay:" + str + " saveTraffics" + this.f3530a.toString(), new Object[0]);
            }
            if (z) {
                this.f3530a.clear();
                m3722c();
            } else if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d("TrafficsMonitor", "no need commit lastsaveDay:" + this.f3534e + " currday:" + a, new Object[0]);
            }
            this.f3534e = a;
            this.f3532c = 0;
        }
    }

    /* renamed from: a */
    public void mo18575a() {
        try {
            synchronized (this.f3530a) {
                this.f3530a.clear();
            }
            List<C2082a> a = C2006a.m3427a(this.f3533d).mo18317a(true);
            if (a != null) {
                for (C2082a a2 : a) {
                    mo18576a(a2);
                }
            }
        } catch (Exception e) {
            ALog.m3731w("TrafficsMonitor", e.toString(), new Object[0]);
        }
    }

    /* renamed from: c */
    private void m3722c() {
        List<C2082a> a = C2006a.m3427a(this.f3533d).mo18317a(false);
        if (a != null) {
            try {
                for (C2082a next : a) {
                    if (next != null) {
                        StatTrafficMonitor statTrafficMonitor = new StatTrafficMonitor();
                        statTrafficMonitor.bizId = next.f3537b;
                        statTrafficMonitor.date = next.f3536a;
                        statTrafficMonitor.host = next.f3540e;
                        statTrafficMonitor.isBackground = next.f3539d;
                        statTrafficMonitor.size = next.f3541f;
                        AppMonitor.getInstance().commitStat(statTrafficMonitor);
                    }
                }
                C2006a.m3427a(this.f3533d).mo18318a();
            } catch (Throwable th) {
                ALog.m3727e("", th.toString(), new Object[0]);
                th.printStackTrace();
            }
        }
    }

    /* renamed from: com.taobao.accs.ut.monitor.TrafficsMonitor$a */
    /* compiled from: Taobao */
    public static class C2082a {

        /* renamed from: a */
        String f3536a;

        /* renamed from: b */
        String f3537b;

        /* renamed from: c */
        String f3538c;

        /* renamed from: d */
        boolean f3539d;

        /* renamed from: e */
        String f3540e;

        /* renamed from: f */
        long f3541f;

        public C2082a(String str, boolean z, String str2, long j) {
            this.f3538c = str;
            this.f3539d = z;
            this.f3540e = str2;
            this.f3541f = j;
        }

        public C2082a(String str, String str2, String str3, boolean z, String str4, long j) {
            this.f3536a = str;
            this.f3537b = str2;
            this.f3538c = str3;
            this.f3539d = z;
            this.f3540e = str4;
            this.f3541f = j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("date:" + this.f3536a);
            sb.append(" ");
            sb.append("bizId:" + this.f3537b);
            sb.append(" ");
            sb.append("serviceId:" + this.f3538c);
            sb.append(" ");
            sb.append("host:" + this.f3540e);
            sb.append(" ");
            sb.append("isBackground:" + this.f3539d);
            sb.append(" ");
            sb.append("size:" + this.f3541f);
            return sb.toString();
        }
    }
}
