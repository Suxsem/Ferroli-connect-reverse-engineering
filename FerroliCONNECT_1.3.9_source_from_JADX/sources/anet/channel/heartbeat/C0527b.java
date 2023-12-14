package anet.channel.heartbeat;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.Session;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import java.util.concurrent.TimeUnit;

/* renamed from: anet.channel.heartbeat.b */
/* compiled from: Taobao */
class C0527b implements IHeartbeat, Runnable {

    /* renamed from: a */
    private Session f272a;

    /* renamed from: b */
    private volatile long f273b = 0;

    /* renamed from: c */
    private volatile boolean f274c = false;

    /* renamed from: d */
    private long f275d = 0;

    C0527b() {
    }

    public void start(Session session) {
        if (session != null) {
            this.f272a = session;
            this.f275d = (long) session.getConnStrategy().getHeartbeat();
            if (this.f275d <= 0) {
                this.f275d = 45000;
            }
            ALog.m328i("awcn.DefaultHeartbeatImpl", "heartbeat start", session.f104p, "session", session, "interval", Long.valueOf(this.f275d));
            m132a(this.f275d);
            return;
        }
        throw new NullPointerException("session is null");
    }

    public void stop() {
        Session session = this.f272a;
        if (session != null) {
            ALog.m328i("awcn.DefaultHeartbeatImpl", "heartbeat stop", session.f104p, "session", this.f272a);
            this.f274c = true;
        }
    }

    public void reSchedule() {
        this.f273b = System.currentTimeMillis() + this.f275d;
    }

    public void run() {
        if (!this.f274c) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.f273b - 1000) {
                m132a(this.f273b - currentTimeMillis);
            } else if (!GlobalAppRuntimeInfo.isAppBackground()) {
                if (ALog.isPrintLog(1)) {
                    ALog.m325d("awcn.DefaultHeartbeatImpl", "heartbeat", this.f272a.f104p, "session", this.f272a);
                }
                this.f272a.ping(true);
                m132a(this.f275d);
            } else {
                ALog.m327e("awcn.DefaultHeartbeatImpl", "close session in background", this.f272a.f104p, "session", this.f272a);
                this.f272a.close(false);
            }
        }
    }

    /* renamed from: a */
    private void m132a(long j) {
        try {
            this.f273b = System.currentTimeMillis() + j;
            ThreadPoolExecutorFactory.submitScheduledTask(this, j + 50, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            ALog.m326e("awcn.DefaultHeartbeatImpl", "Submit heartbeat task failed.", this.f272a.f104p, e, new Object[0]);
        }
    }
}
