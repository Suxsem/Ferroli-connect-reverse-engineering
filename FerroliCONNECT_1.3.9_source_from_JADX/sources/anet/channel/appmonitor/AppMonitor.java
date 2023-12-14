package anet.channel.appmonitor;

import anet.channel.statist.AlarmObject;
import anet.channel.statist.CountObject;
import anet.channel.statist.StatObject;

/* compiled from: Taobao */
public class AppMonitor {
    /* access modifiers changed from: private */
    public static volatile IAppMonitor apmMonitor = null;
    private static volatile IAppMonitor appMonitor = new C0478a((IAppMonitor) null);

    public static IAppMonitor getInstance() {
        return appMonitor;
    }

    public static void setInstance(IAppMonitor iAppMonitor) {
        appMonitor = new C0478a(iAppMonitor);
    }

    public static void setApmMonitor(IAppMonitor iAppMonitor) {
        apmMonitor = iAppMonitor;
    }

    /* renamed from: anet.channel.appmonitor.AppMonitor$a */
    /* compiled from: Taobao */
    static class C0478a implements IAppMonitor {

        /* renamed from: a */
        IAppMonitor f153a = null;

        @Deprecated
        public void register() {
        }

        @Deprecated
        public void register(Class<?> cls) {
        }

        C0478a(IAppMonitor iAppMonitor) {
            this.f153a = iAppMonitor;
        }

        public void commitStat(StatObject statObject) {
            if (AppMonitor.apmMonitor != null) {
                AppMonitor.apmMonitor.commitStat(statObject);
            }
            IAppMonitor iAppMonitor = this.f153a;
            if (iAppMonitor != null) {
                iAppMonitor.commitStat(statObject);
            }
        }

        public void commitAlarm(AlarmObject alarmObject) {
            IAppMonitor iAppMonitor = this.f153a;
            if (iAppMonitor != null) {
                iAppMonitor.commitAlarm(alarmObject);
            }
        }

        public void commitCount(CountObject countObject) {
            IAppMonitor iAppMonitor = this.f153a;
            if (iAppMonitor != null) {
                iAppMonitor.commitCount(countObject);
            }
        }
    }
}
