package org.eclipse.jetty.util.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.ThreadPool;

public class QueuedThreadPool extends AbstractLifeCycle implements ThreadPool.SizedThreadPool, Executor, Dumpable {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) QueuedThreadPool.class);
    private boolean _daemon;
    private boolean _detailedDump;
    /* access modifiers changed from: private */
    public BlockingQueue<Runnable> _jobs;
    private final Object _joinLock;
    /* access modifiers changed from: private */
    public final AtomicLong _lastShrink;
    /* access modifiers changed from: private */
    public int _maxIdleTimeMs;
    private int _maxQueued;
    private int _maxStopTime;
    private int _maxThreads;
    /* access modifiers changed from: private */
    public int _minThreads;
    private String _name;
    private int _priority;
    private Runnable _runnable;
    /* access modifiers changed from: private */
    public final ConcurrentLinkedQueue<Thread> _threads;
    /* access modifiers changed from: private */
    public final AtomicInteger _threadsIdle;
    /* access modifiers changed from: private */
    public final AtomicInteger _threadsStarted;

    public QueuedThreadPool() {
        this._threadsStarted = new AtomicInteger();
        this._threadsIdle = new AtomicInteger();
        this._lastShrink = new AtomicLong();
        this._threads = new ConcurrentLinkedQueue<>();
        this._joinLock = new Object();
        this._maxIdleTimeMs = 60000;
        this._maxThreads = 254;
        this._minThreads = 8;
        this._maxQueued = -1;
        this._priority = 5;
        this._daemon = false;
        this._maxStopTime = 100;
        this._detailedDump = false;
        this._runnable = new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ed, code lost:
                if (r1 == false) goto L_0x0111;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:59:0x010f, code lost:
                if (r1 == false) goto L_0x0111;
             */
            /* JADX WARNING: Removed duplicated region for block: B:55:0x0104 A[Catch:{ InterruptedException -> 0x0105, Exception -> 0x00f8, all -> 0x00f4, all -> 0x0128 }] */
            /* JADX WARNING: Removed duplicated region for block: B:65:0x012b  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r12 = this;
                    r0 = 0
                    org.eclipse.jetty.util.thread.QueuedThreadPool r1 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x0105, Exception -> 0x00f8, all -> 0x00f4 }
                    java.util.concurrent.BlockingQueue r1 = r1._jobs     // Catch:{ InterruptedException -> 0x0105, Exception -> 0x00f8, all -> 0x00f4 }
                    java.lang.Object r1 = r1.poll()     // Catch:{ InterruptedException -> 0x0105, Exception -> 0x00f8, all -> 0x00f4 }
                    java.lang.Runnable r1 = (java.lang.Runnable) r1     // Catch:{ InterruptedException -> 0x0105, Exception -> 0x00f8, all -> 0x00f4 }
                    r2 = r1
                    r1 = 0
                L_0x000f:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    boolean r3 = r3.isRunning()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    if (r3 == 0) goto L_0x00ed
                L_0x0017:
                    if (r2 == 0) goto L_0x0033
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    boolean r3 = r3.isRunning()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    if (r3 == 0) goto L_0x0033
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    r3.runJob(r2)     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.util.concurrent.BlockingQueue r2 = r2._jobs     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.lang.Object r2 = r2.poll()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    goto L_0x0017
                L_0x0033:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.atomic.AtomicInteger r3 = r3._threadsIdle     // Catch:{ all -> 0x00e2 }
                    r3.incrementAndGet()     // Catch:{ all -> 0x00e2 }
                L_0x003c:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    boolean r3 = r3.isRunning()     // Catch:{ all -> 0x00e2 }
                    if (r3 == 0) goto L_0x00d7
                    if (r2 != 0) goto L_0x00d7
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    int r2 = r2._maxIdleTimeMs     // Catch:{ all -> 0x00e2 }
                    if (r2 > 0) goto L_0x005b
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.BlockingQueue r2 = r2._jobs     // Catch:{ all -> 0x00e2 }
                    java.lang.Object r2 = r2.take()     // Catch:{ all -> 0x00e2 }
                    java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ all -> 0x00e2 }
                    goto L_0x003c
                L_0x005b:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.atomic.AtomicInteger r2 = r2._threadsStarted     // Catch:{ all -> 0x00e2 }
                    int r2 = r2.get()     // Catch:{ all -> 0x00e2 }
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    int r3 = r3._minThreads     // Catch:{ all -> 0x00e2 }
                    if (r2 <= r3) goto L_0x00cf
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.atomic.AtomicLong r3 = r3._lastShrink     // Catch:{ all -> 0x00e2 }
                    long r3 = r3.get()     // Catch:{ all -> 0x00e2 }
                    long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e2 }
                    r7 = 0
                    int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
                    if (r9 == 0) goto L_0x008e
                    long r7 = r5 - r3
                    org.eclipse.jetty.util.thread.QueuedThreadPool r9 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    int r9 = r9._maxIdleTimeMs     // Catch:{ all -> 0x00e2 }
                    long r9 = (long) r9     // Catch:{ all -> 0x00e2 }
                    int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                    if (r11 <= 0) goto L_0x00cf
                L_0x008e:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r7 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.atomic.AtomicLong r7 = r7._lastShrink     // Catch:{ all -> 0x00e2 }
                    boolean r3 = r7.compareAndSet(r3, r5)     // Catch:{ all -> 0x00e2 }
                    if (r3 == 0) goto L_0x00aa
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.util.concurrent.atomic.AtomicInteger r3 = r3._threadsStarted     // Catch:{ all -> 0x00e2 }
                    int r4 = r2 + -1
                    boolean r1 = r3.compareAndSet(r2, r4)     // Catch:{ all -> 0x00e2 }
                    if (r1 == 0) goto L_0x00aa
                    r1 = 1
                    goto L_0x00ab
                L_0x00aa:
                    r1 = 0
                L_0x00ab:
                    if (r1 == 0) goto L_0x00cf
                    org.eclipse.jetty.util.thread.QueuedThreadPool r0 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.util.concurrent.atomic.AtomicInteger r0 = r0._threadsIdle     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    r0.decrementAndGet()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    if (r1 != 0) goto L_0x00c1
                    org.eclipse.jetty.util.thread.QueuedThreadPool r0 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.atomic.AtomicInteger r0 = r0._threadsStarted
                    r0.decrementAndGet()
                L_0x00c1:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r0 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.ConcurrentLinkedQueue r0 = r0._threads
                    java.lang.Thread r1 = java.lang.Thread.currentThread()
                    r0.remove(r1)
                    return
                L_0x00cf:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ all -> 0x00e2 }
                    java.lang.Runnable r2 = r2.idleJobPoll()     // Catch:{ all -> 0x00e2 }
                    goto L_0x003c
                L_0x00d7:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r3 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.util.concurrent.atomic.AtomicInteger r3 = r3._threadsIdle     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    r3.decrementAndGet()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    goto L_0x000f
                L_0x00e2:
                    r0 = move-exception
                    org.eclipse.jetty.util.thread.QueuedThreadPool r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.this     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    java.util.concurrent.atomic.AtomicInteger r2 = r2._threadsIdle     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    r2.decrementAndGet()     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                    throw r0     // Catch:{ InterruptedException -> 0x00f2, Exception -> 0x00f0 }
                L_0x00ed:
                    if (r1 != 0) goto L_0x011a
                    goto L_0x0111
                L_0x00f0:
                    r0 = move-exception
                    goto L_0x00fb
                L_0x00f2:
                    r0 = move-exception
                    goto L_0x0108
                L_0x00f4:
                    r1 = move-exception
                    r0 = r1
                    r1 = 0
                    goto L_0x0129
                L_0x00f8:
                    r1 = move-exception
                    r0 = r1
                    r1 = 0
                L_0x00fb:
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.LOG     // Catch:{ all -> 0x0128 }
                    r2.warn(r0)     // Catch:{ all -> 0x0128 }
                    if (r1 != 0) goto L_0x011a
                    goto L_0x0111
                L_0x0105:
                    r1 = move-exception
                    r0 = r1
                    r1 = 0
                L_0x0108:
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.thread.QueuedThreadPool.LOG     // Catch:{ all -> 0x0128 }
                    r2.ignore(r0)     // Catch:{ all -> 0x0128 }
                    if (r1 != 0) goto L_0x011a
                L_0x0111:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r0 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.atomic.AtomicInteger r0 = r0._threadsStarted
                    r0.decrementAndGet()
                L_0x011a:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r0 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.ConcurrentLinkedQueue r0 = r0._threads
                    java.lang.Thread r1 = java.lang.Thread.currentThread()
                    r0.remove(r1)
                    return
                L_0x0128:
                    r0 = move-exception
                L_0x0129:
                    if (r1 != 0) goto L_0x0134
                    org.eclipse.jetty.util.thread.QueuedThreadPool r1 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.atomic.AtomicInteger r1 = r1._threadsStarted
                    r1.decrementAndGet()
                L_0x0134:
                    org.eclipse.jetty.util.thread.QueuedThreadPool r1 = org.eclipse.jetty.util.thread.QueuedThreadPool.this
                    java.util.concurrent.ConcurrentLinkedQueue r1 = r1._threads
                    java.lang.Thread r2 = java.lang.Thread.currentThread()
                    r1.remove(r2)
                    goto L_0x0143
                L_0x0142:
                    throw r0
                L_0x0143:
                    goto L_0x0142
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.thread.QueuedThreadPool.C24593.run():void");
            }
        };
        this._name = "qtp" + super.hashCode();
    }

    public QueuedThreadPool(int i) {
        this();
        setMaxThreads(i);
    }

    public QueuedThreadPool(BlockingQueue<Runnable> blockingQueue) {
        this();
        this._jobs = blockingQueue;
        this._jobs.clear();
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        BlockingQueue<Runnable> blockingQueue;
        super.doStart();
        this._threadsStarted.set(0);
        if (this._jobs == null) {
            int i = this._maxQueued;
            if (i > 0) {
                blockingQueue = new ArrayBlockingQueue<>(i);
            } else {
                int i2 = this._minThreads;
                blockingQueue = new BlockingArrayQueue<>(i2, i2);
            }
            this._jobs = blockingQueue;
        }
        int i3 = this._threadsStarted.get();
        while (isRunning() && i3 < this._minThreads) {
            startThread(i3);
            i3 = this._threadsStarted.get();
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        long currentTimeMillis = System.currentTimeMillis();
        while (this._threadsStarted.get() > 0 && System.currentTimeMillis() - currentTimeMillis < ((long) (this._maxStopTime / 2))) {
            Thread.sleep(1);
        }
        this._jobs.clear();
        C24571 r2 = new Runnable() {
            public void run() {
            }
        };
        int i = this._threadsIdle.get();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            this._jobs.offer(r2);
            i = i2;
        }
        Thread.yield();
        if (this._threadsStarted.get() > 0) {
            Iterator<Thread> it = this._threads.iterator();
            while (it.hasNext()) {
                it.next().interrupt();
            }
        }
        while (this._threadsStarted.get() > 0 && System.currentTimeMillis() - currentTimeMillis < ((long) this._maxStopTime)) {
            Thread.sleep(1);
        }
        Thread.yield();
        int size = this._threads.size();
        if (size > 0) {
            LOG.warn(size + " threads could not be stopped", new Object[0]);
            if (size == 1 || LOG.isDebugEnabled()) {
                Iterator<Thread> it2 = this._threads.iterator();
                while (it2.hasNext()) {
                    Thread next = it2.next();
                    LOG.info("Couldn't stop " + next, new Object[0]);
                    for (StackTraceElement stackTraceElement : next.getStackTrace()) {
                        LOG.info(" at " + stackTraceElement, new Object[0]);
                    }
                }
            }
        }
        synchronized (this._joinLock) {
            this._joinLock.notifyAll();
        }
    }

    public void setDaemon(boolean z) {
        this._daemon = z;
    }

    public void setMaxIdleTimeMs(int i) {
        this._maxIdleTimeMs = i;
    }

    public void setMaxStopTimeMs(int i) {
        this._maxStopTime = i;
    }

    public void setMaxThreads(int i) {
        this._maxThreads = i;
        int i2 = this._minThreads;
        int i3 = this._maxThreads;
        if (i2 > i3) {
            this._minThreads = i3;
        }
    }

    public void setMinThreads(int i) {
        this._minThreads = i;
        int i2 = this._minThreads;
        if (i2 > this._maxThreads) {
            this._maxThreads = i2;
        }
        int i3 = this._threadsStarted.get();
        while (isStarted() && i3 < this._minThreads) {
            startThread(i3);
            i3 = this._threadsStarted.get();
        }
    }

    public void setName(String str) {
        if (!isRunning()) {
            this._name = str;
            return;
        }
        throw new IllegalStateException("started");
    }

    public void setThreadsPriority(int i) {
        this._priority = i;
    }

    public int getMaxQueued() {
        return this._maxQueued;
    }

    public void setMaxQueued(int i) {
        if (!isRunning()) {
            this._maxQueued = i;
            return;
        }
        throw new IllegalStateException("started");
    }

    public int getMaxIdleTimeMs() {
        return this._maxIdleTimeMs;
    }

    public int getMaxStopTimeMs() {
        return this._maxStopTime;
    }

    public int getMaxThreads() {
        return this._maxThreads;
    }

    public int getMinThreads() {
        return this._minThreads;
    }

    public String getName() {
        return this._name;
    }

    public int getThreadsPriority() {
        return this._priority;
    }

    public boolean isDaemon() {
        return this._daemon;
    }

    public boolean isDetailedDump() {
        return this._detailedDump;
    }

    public void setDetailedDump(boolean z) {
        this._detailedDump = z;
    }

    public boolean dispatch(Runnable runnable) {
        int i;
        if (isRunning()) {
            int size = this._jobs.size();
            int idleThreads = getIdleThreads();
            if (this._jobs.offer(runnable)) {
                if ((idleThreads == 0 || size > idleThreads) && (i = this._threadsStarted.get()) < this._maxThreads) {
                    startThread(i);
                }
                return true;
            }
        }
        LOG.debug("Dispatched {} to stopped {}", runnable, this);
        return false;
    }

    public void execute(Runnable runnable) {
        if (!dispatch(runnable)) {
            throw new RejectedExecutionException();
        }
    }

    public void join() throws InterruptedException {
        synchronized (this._joinLock) {
            while (isRunning()) {
                this._joinLock.wait();
            }
        }
        while (isStopping()) {
            Thread.sleep(1);
        }
    }

    public int getThreads() {
        return this._threadsStarted.get();
    }

    public int getIdleThreads() {
        return this._threadsIdle.get();
    }

    public boolean isLowOnThreads() {
        return this._threadsStarted.get() == this._maxThreads && this._jobs.size() >= this._threadsIdle.get();
    }

    private boolean startThread(int i) {
        if (!this._threadsStarted.compareAndSet(i, i + 1)) {
            return false;
        }
        try {
            Thread newThread = newThread(this._runnable);
            newThread.setDaemon(this._daemon);
            newThread.setPriority(this._priority);
            newThread.setName(this._name + "-" + newThread.getId());
            this._threads.add(newThread);
            newThread.start();
            return true;
        } catch (Throwable th) {
            this._threadsStarted.decrementAndGet();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }

    public String dump() {
        return AggregateLifeCycle.dump((Dumpable) this);
    }

    public void dump(Appendable appendable, String str) throws IOException {
        ArrayList arrayList = new ArrayList(getMaxThreads());
        Iterator<Thread> it = this._threads.iterator();
        while (true) {
            final boolean z = true;
            if (it.hasNext()) {
                final Thread next = it.next();
                final StackTraceElement[] stackTrace = next.getStackTrace();
                if (stackTrace != null) {
                    int length = stackTrace.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if ("idleJobPoll".equals(stackTrace[i].getMethodName())) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                z = false;
                if (this._detailedDump) {
                    arrayList.add(new Dumpable() {
                        public String dump() {
                            return null;
                        }

                        public void dump(Appendable appendable, String str) throws IOException {
                            appendable.append(String.valueOf(next.getId())).append(' ').append(next.getName()).append(' ').append(next.getState().toString()).append(z ? " IDLE" : "").append(10);
                            if (!z) {
                                AggregateLifeCycle.dump(appendable, str, Arrays.asList(stackTrace));
                            }
                        }
                    });
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(next.getId());
                    sb.append(" ");
                    sb.append(next.getName());
                    sb.append(" ");
                    sb.append(next.getState());
                    sb.append(" @ ");
                    sb.append(stackTrace.length > 0 ? stackTrace[0] : "???");
                    sb.append(z ? " IDLE" : "");
                    arrayList.add(sb.toString());
                }
            } else {
                AggregateLifeCycle.dumpObject(appendable, this);
                AggregateLifeCycle.dump(appendable, str, arrayList);
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._name);
        sb.append("{");
        sb.append(getMinThreads());
        sb.append("<=");
        sb.append(getIdleThreads());
        sb.append("<=");
        sb.append(getThreads());
        sb.append("/");
        sb.append(getMaxThreads());
        sb.append(",");
        BlockingQueue<Runnable> blockingQueue = this._jobs;
        sb.append(blockingQueue == null ? -1 : blockingQueue.size());
        sb.append("}");
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public Runnable idleJobPoll() throws InterruptedException {
        return this._jobs.poll((long) this._maxIdleTimeMs, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    public void runJob(Runnable runnable) {
        runnable.run();
    }

    /* access modifiers changed from: protected */
    public BlockingQueue<Runnable> getQueue() {
        return this._jobs;
    }

    @Deprecated
    public boolean stopThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                next.stop();
                return true;
            }
        }
        return false;
    }

    public boolean interruptThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                next.interrupt();
                return true;
            }
        }
        return false;
    }

    public String dumpThread(long j) {
        Iterator<Thread> it = this._threads.iterator();
        while (it.hasNext()) {
            Thread next = it.next();
            if (next.getId() == j) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.getId());
                sb.append(" ");
                sb.append(next.getName());
                sb.append(" ");
                sb.append(next.getState());
                sb.append(":\n");
                for (StackTraceElement stackTraceElement : next.getStackTrace()) {
                    sb.append("  at ");
                    sb.append(stackTraceElement.toString());
                    sb.append(10);
                }
                return sb.toString();
            }
        }
        return null;
    }
}
