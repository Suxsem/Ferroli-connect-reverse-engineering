package com.alibaba.sdk.android.crashdefend.p013b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.alibaba.sdk.android.crashdefend.b.a */
public class C0696a {

    /* renamed from: a */
    private final ThreadFactory f874a = new ThreadFactory() {
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "safe_thread");
            thread.setDaemon(false);
            return thread;
        }
    };

    /* renamed from: b */
    private ExecutorService f875b;

    /* renamed from: a */
    public synchronized ExecutorService mo9593a() {
        if (this.f875b == null) {
            this.f875b = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1, TimeUnit.SECONDS, new SynchronousQueue(), this.f874a);
        }
        return this.f875b;
    }
}
