package anet.channel.util;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.status.NetworkStatusHelper;

/* renamed from: anet.channel.util.e */
/* compiled from: Taobao */
class C0606e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0605d f586a;

    C0606e(C0605d dVar) {
        this.f586a = dVar;
    }

    public void run() {
        C0607f g;
        try {
            if (this.f586a.f584a.equals(C0604c.m349b(NetworkStatusHelper.getStatus()))) {
                ALog.m327e("awcn.Inet64Util", "startIpStackDetect double check", (String) null, new Object[0]);
                int f = C0604c.m358j();
                if (this.f586a.f585b.ipStackType != f) {
                    C0604c.f583e.put(this.f586a.f584a, Integer.valueOf(f));
                    this.f586a.f585b.lastIpStackType = this.f586a.f585b.ipStackType;
                    this.f586a.f585b.ipStackType = f;
                }
                if ((f == 2 || f == 3) && (g = C0604c.m359k()) != null) {
                    C0604c.f582d.put(this.f586a.f584a, g);
                    this.f586a.f585b.nat64Prefix = g.toString();
                }
                if (GlobalAppRuntimeInfo.isTargetProcess()) {
                    AppMonitor.getInstance().commitStat(this.f586a.f585b);
                }
            }
        } catch (Exception unused) {
        }
    }
}
