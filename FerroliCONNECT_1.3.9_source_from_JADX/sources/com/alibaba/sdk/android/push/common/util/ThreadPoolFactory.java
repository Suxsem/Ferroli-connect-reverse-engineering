package com.alibaba.sdk.android.push.common.util;

import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolFactory {
    private static final String TAG = "MPS:ThreadPoolFactory";
    /* access modifiers changed from: private */
    public static final AtomicInteger integer = new AtomicInteger();
    private static AmsLogger logger = AmsLogger.getLogger(TAG);
    private static volatile ScheduledThreadPoolExecutor scheduleThreadPoolExecutor;

    /* renamed from: com.alibaba.sdk.android.push.common.util.ThreadPoolFactory$a */
    static class C0807a implements ThreadFactory {

        /* renamed from: a */
        private String f1117a;

        public C0807a(String str) {
            this.f1117a = str;
        }

        public Thread newThread(Runnable runnable) {
            int andIncrement = ThreadPoolFactory.integer.getAndIncrement();
            Thread thread = new Thread(runnable, this.f1117a + andIncrement);
            thread.setPriority(5);
            return thread;
        }
    }

    public static void execute(Runnable runnable) {
        try {
            getScheduledExecutor().execute(runnable);
        } catch (Throwable th) {
            logger.mo9542e("ThreadPoolExecutorFactory execute", th);
        }
    }

    public static ScheduledThreadPoolExecutor getScheduledExecutor() {
        if (scheduleThreadPoolExecutor == null) {
            synchronized (ThreadPoolFactory.class) {
                if (scheduleThreadPoolExecutor == null) {
                    scheduleThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new C0807a(TAG));
                    scheduleThreadPoolExecutor.setKeepAliveTime(60, TimeUnit.SECONDS);
                    scheduleThreadPoolExecutor.allowCoreThreadTimeOut(true);
                }
            }
        }
        return scheduleThreadPoolExecutor;
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            return getScheduledExecutor().schedule(runnable, j, timeUnit);
        } catch (Throwable th) {
            logger.mo9542e("ThreadPoolExecutorFactory schedule", th);
            return null;
        }
    }
}
