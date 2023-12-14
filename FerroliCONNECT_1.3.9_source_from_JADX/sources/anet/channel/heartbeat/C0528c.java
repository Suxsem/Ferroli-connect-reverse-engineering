package anet.channel.heartbeat;

import anet.channel.Session;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.util.concurrent.TimeUnit;

/* renamed from: anet.channel.heartbeat.c */
/* compiled from: Taobao */
public class C0528c implements IHeartbeat, Runnable {

    /* renamed from: a */
    private Session f276a = null;

    /* renamed from: b */
    private volatile boolean f277b = false;

    /* renamed from: c */
    private volatile long f278c = System.currentTimeMillis();

    public void start(Session session) {
        if (session != null) {
            this.f276a = session;
            this.f278c = System.currentTimeMillis() + 45000;
            ThreadPoolExecutorFactory.submitScheduledTask(this, 45000, TimeUnit.MILLISECONDS);
            return;
        }
        throw new NullPointerException("session is null");
    }

    public void stop() {
        this.f277b = true;
    }

    public void reSchedule() {
        this.f278c = System.currentTimeMillis() + 45000;
    }

    public void run() {
        if (!this.f277b) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.f278c - 1000) {
                ThreadPoolExecutorFactory.submitScheduledTask(this, this.f278c - currentTimeMillis, TimeUnit.MILLISECONDS);
            } else {
                this.f276a.close(false);
            }
        }
    }
}
