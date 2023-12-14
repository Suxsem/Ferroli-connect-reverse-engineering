package anet.channel.thread;

import anet.channel.util.ALog;
import com.p107tb.appyunsdk.Constant;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Taobao */
public class ThreadPoolExecutorFactory {

    /* renamed from: a */
    private static ScheduledThreadPoolExecutor f554a = new ScheduledThreadPoolExecutor(1, new C0596b("AWCN Scheduler"));

    /* renamed from: b */
    private static ThreadPoolExecutor f555b = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN Worker(H)"));

    /* renamed from: c */
    private static ThreadPoolExecutor f556c = new C0597a(16, 16, 60, TimeUnit.SECONDS, new PriorityBlockingQueue(), new C0596b("AWCN Worker(M)"));

    /* renamed from: d */
    private static ThreadPoolExecutor f557d = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN Worker(L)"));

    /* renamed from: e */
    private static ThreadPoolExecutor f558e = new ThreadPoolExecutor(32, 32, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN Worker(Backup)"));

    /* renamed from: f */
    private static ThreadPoolExecutor f559f = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN Detector"));

    /* renamed from: g */
    private static ThreadPoolExecutor f560g = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN HR"));

    /* renamed from: h */
    private static ThreadPoolExecutor f561h = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingDeque(), new C0596b("AWCN Cookie"));

    /* compiled from: Taobao */
    public static class Priority {
        public static int HIGH = 0;
        public static int LOW = 9;
        public static int NORMAL = 1;
    }

    static {
        f555b.allowCoreThreadTimeOut(true);
        f556c.allowCoreThreadTimeOut(true);
        f557d.allowCoreThreadTimeOut(true);
        f558e.allowCoreThreadTimeOut(true);
        f559f.allowCoreThreadTimeOut(true);
        f560g.allowCoreThreadTimeOut(true);
        f561h.allowCoreThreadTimeOut(true);
    }

    /* renamed from: anet.channel.thread.ThreadPoolExecutorFactory$b */
    /* compiled from: Taobao */
    private static class C0596b implements ThreadFactory {

        /* renamed from: a */
        AtomicInteger f565a = new AtomicInteger(0);

        /* renamed from: b */
        String f566b;

        C0596b(String str) {
            this.f566b = str;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, this.f566b + this.f565a.incrementAndGet());
            ALog.m328i("awcn.ThreadPoolExecutorFactory", "thread created!", (String) null, Constant.NAME, thread.getName());
            thread.setPriority(5);
            return thread;
        }
    }

    public static Future<?> submitScheduledTask(Runnable runnable) {
        return f554a.submit(runnable);
    }

    public static Future<?> submitScheduledTask(Runnable runnable, long j, TimeUnit timeUnit) {
        return f554a.schedule(runnable, j, timeUnit);
    }

    public static void removeScheduleTask(Runnable runnable) {
        f554a.remove(runnable);
    }

    public static Future<?> submitPriorityTask(Runnable runnable, int i) {
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.ThreadPoolExecutorFactory", "submit priority task", (String) null, "priority", Integer.valueOf(i));
        }
        if (i < Priority.HIGH || i > Priority.LOW) {
            i = Priority.LOW;
        }
        if (i == Priority.HIGH) {
            return f555b.submit(runnable);
        }
        if (i == Priority.LOW) {
            return f557d.submit(runnable);
        }
        return f556c.submit(new C0595a(runnable, i));
    }

    public static Future<?> submitBackupTask(Runnable runnable) {
        return f558e.submit(runnable);
    }

    public static Future<?> submitDetectTask(Runnable runnable) {
        return f559f.submit(runnable);
    }

    public static Future<?> submitHRTask(Runnable runnable) {
        return f560g.submit(runnable);
    }

    public static Future<?> submitCookieMonitor(Runnable runnable) {
        return f561h.submit(runnable);
    }

    /* renamed from: anet.channel.thread.ThreadPoolExecutorFactory$a */
    /* compiled from: Taobao */
    static class C0595a implements Comparable<C0595a>, Runnable {

        /* renamed from: a */
        Runnable f562a = null;

        /* renamed from: b */
        int f563b = 0;

        /* renamed from: c */
        long f564c = System.currentTimeMillis();

        public C0595a(Runnable runnable, int i) {
            this.f562a = runnable;
            this.f563b = i;
            this.f564c = System.currentTimeMillis();
        }

        /* renamed from: a */
        public int compareTo(C0595a aVar) {
            int i = this.f563b;
            int i2 = aVar.f563b;
            if (i != i2) {
                return i - i2;
            }
            return (int) (aVar.f564c - this.f564c);
        }

        public void run() {
            this.f562a.run();
        }
    }

    public static synchronized void setNormalExecutorPoolSize(int i) {
        synchronized (ThreadPoolExecutorFactory.class) {
            if (i < 6) {
                i = 6;
            }
            f556c.setCorePoolSize(i);
            f556c.setMaximumPoolSize(i);
        }
    }
}
