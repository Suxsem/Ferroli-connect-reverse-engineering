package anet.channel.strategy.utils;

import anet.channel.util.ALog;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: anet.channel.strategy.utils.a */
/* compiled from: Taobao */
public class C0592a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static AtomicInteger f552a = new AtomicInteger(0);

    /* renamed from: b */
    private static ScheduledThreadPoolExecutor f553b = null;

    /* renamed from: a */
    static ScheduledThreadPoolExecutor m313a() {
        if (f553b == null) {
            synchronized (C0592a.class) {
                if (f553b == null) {
                    f553b = new ScheduledThreadPoolExecutor(2, new C0593b());
                    f553b.setKeepAliveTime(60, TimeUnit.SECONDS);
                    f553b.allowCoreThreadTimeOut(true);
                }
            }
        }
        return f553b;
    }

    /* renamed from: a */
    public static void m314a(Runnable runnable) {
        try {
            m313a().submit(runnable);
        } catch (Exception e) {
            ALog.m326e(C0570a.TAG, "submit task failed", (String) null, e, new Object[0]);
        }
    }

    /* renamed from: a */
    public static void m315a(Runnable runnable, long j) {
        try {
            m313a().schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            ALog.m326e(C0570a.TAG, "schedule task failed", (String) null, e, new Object[0]);
        }
    }
}
