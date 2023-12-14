package com.alibaba.sdk.android.tbrest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.alibaba.sdk.android.tbrest.a */
/* compiled from: SendAsyncExecutor */
public class C0879a {

    /* renamed from: a */
    public static ScheduledExecutorService f1328a = null;

    /* renamed from: a */
    public static final AtomicInteger f1329a = new AtomicInteger();

    /* renamed from: g */
    public static int f1330g = 1;

    /* renamed from: a */
    public Integer f1331a = 2;

    /* renamed from: com.alibaba.sdk.android.tbrest.a$a */
    /* compiled from: SendAsyncExecutor */
    static class C0880a implements ThreadFactory {
        private final int priority;

        public C0880a(int i) {
            this.priority = i;
        }

        public Thread newThread(Runnable runnable) {
            int andIncrement = C0879a.f1329a.getAndIncrement();
            Thread thread = new Thread(runnable, "RestSend:" + andIncrement);
            thread.setPriority(this.priority);
            return thread;
        }
    }

    /* renamed from: a */
    public synchronized void mo10126a(Runnable runnable) {
        try {
            if (f1328a == null) {
                f1328a = Executors.newScheduledThreadPool(this.f1331a.intValue(), new C0880a(f1330g));
            }
            f1328a.submit(runnable);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return;
    }
}
