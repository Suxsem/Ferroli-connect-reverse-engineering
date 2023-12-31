package p110io.reactivex.internal.schedulers;

import java.util.concurrent.ThreadFactory;
import p110io.reactivex.Scheduler;
import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.internal.schedulers.NewThreadScheduler */
public final class NewThreadScheduler extends Scheduler {
    private static final String KEY_NEWTHREAD_PRIORITY = "rx2.newthread-priority";
    private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(THREAD_NAME_PREFIX, Math.max(1, Math.min(10, Integer.getInteger(KEY_NEWTHREAD_PRIORITY, 5).intValue())));
    private static final String THREAD_NAME_PREFIX = "RxNewThreadScheduler";
    final ThreadFactory threadFactory;

    public NewThreadScheduler() {
        this(THREAD_FACTORY);
    }

    public NewThreadScheduler(ThreadFactory threadFactory2) {
        this.threadFactory = threadFactory2;
    }

    @NonNull
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.threadFactory);
    }
}
