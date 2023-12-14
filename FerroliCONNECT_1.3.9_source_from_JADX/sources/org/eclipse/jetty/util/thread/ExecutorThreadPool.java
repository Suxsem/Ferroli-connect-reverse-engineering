package org.eclipse.jetty.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class ExecutorThreadPool extends AbstractLifeCycle implements ThreadPool, LifeCycle {
    private static final Logger LOG = Log.getLogger((Class<?>) ExecutorThreadPool.class);
    private final ExecutorService _executor;

    public ExecutorThreadPool(ExecutorService executorService) {
        this._executor = executorService;
    }

    public ExecutorThreadPool() {
        this((ExecutorService) new ThreadPoolExecutor(256, 256, 60, TimeUnit.SECONDS, new LinkedBlockingQueue()));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ExecutorThreadPool(int i) {
        this((ExecutorService) i < 0 ? new ThreadPoolExecutor(256, 256, 60, TimeUnit.SECONDS, new LinkedBlockingQueue()) : i == 0 ? new ThreadPoolExecutor(32, 256, 60, TimeUnit.SECONDS, new SynchronousQueue()) : new ThreadPoolExecutor(32, 256, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(i)));
    }

    public ExecutorThreadPool(int i, int i2, long j) {
        this(i, i2, j, TimeUnit.MILLISECONDS);
    }

    public ExecutorThreadPool(int i, int i2, long j, TimeUnit timeUnit) {
        this(i, i2, j, timeUnit, new LinkedBlockingQueue());
    }

    public ExecutorThreadPool(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this((ExecutorService) new ThreadPoolExecutor(i, i2, j, timeUnit, blockingQueue));
    }

    public boolean dispatch(Runnable runnable) {
        try {
            this._executor.execute(runnable);
            return true;
        } catch (RejectedExecutionException e) {
            LOG.warn(e);
            return false;
        }
    }

    public int getIdleThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return -1;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        return threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount();
    }

    public int getThreads() {
        ExecutorService executorService = this._executor;
        if (executorService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executorService).getPoolSize();
        }
        return -1;
    }

    public boolean isLowOnThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return false;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        if (threadPoolExecutor.getPoolSize() != threadPoolExecutor.getMaximumPoolSize() || threadPoolExecutor.getQueue().size() < threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount()) {
            return false;
        }
        return true;
    }

    public void join() throws InterruptedException {
        this._executor.awaitTermination(LongCompanionObject.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        this._executor.shutdownNow();
    }
}
