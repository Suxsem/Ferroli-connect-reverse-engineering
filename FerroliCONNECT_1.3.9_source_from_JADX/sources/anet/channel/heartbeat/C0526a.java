package anet.channel.heartbeat;

import anet.channel.Session;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.util.concurrent.TimeUnit;

/* renamed from: anet.channel.heartbeat.a */
/* compiled from: Taobao */
public class C0526a implements IHeartbeat, Runnable {

    /* renamed from: a */
    Session f270a = null;

    /* renamed from: b */
    volatile boolean f271b = false;

    public void reSchedule() {
    }

    public void start(Session session) {
        if (session != null) {
            this.f270a = session;
            run();
            return;
        }
        throw new NullPointerException("session is null");
    }

    public void stop() {
        this.f271b = true;
    }

    public void run() {
        if (!this.f271b) {
            this.f270a.ping(true);
            ThreadPoolExecutorFactory.submitScheduledTask(this, 45000, TimeUnit.MILLISECONDS);
        }
    }
}
