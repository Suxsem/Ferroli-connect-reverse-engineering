package org.eclipse.jetty.util.component;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class AbstractLifeCycle implements LifeCycle {
    public static final String FAILED = "FAILED";
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractLifeCycle.class);
    public static final String RUNNING = "RUNNING";
    public static final String STARTED = "STARTED";
    public static final String STARTING = "STARTING";
    public static final String STOPPED = "STOPPED";
    public static final String STOPPING = "STOPPING";
    private final int __FAILED = -1;
    private final int __STARTED = 2;
    private final int __STARTING = 1;
    private final int __STOPPED = 0;
    private final int __STOPPING = 3;
    protected final CopyOnWriteArrayList<LifeCycle.Listener> _listeners = new CopyOnWriteArrayList<>();
    private final Object _lock = new Object();
    private volatile int _state = 0;

    public static abstract class AbstractLifeCycleListener implements LifeCycle.Listener {
        public void lifeCycleFailure(LifeCycle lifeCycle, Throwable th) {
        }

        public void lifeCycleStarted(LifeCycle lifeCycle) {
        }

        public void lifeCycleStarting(LifeCycle lifeCycle) {
        }

        public void lifeCycleStopped(LifeCycle lifeCycle) {
        }

        public void lifeCycleStopping(LifeCycle lifeCycle) {
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void start() throws java.lang.Exception {
        /*
            r3 = this;
            java.lang.Object r0 = r3._lock
            monitor-enter(r0)
            int r1 = r3._state     // Catch:{ Exception -> 0x0022, Error -> 0x001d }
            r2 = 2
            if (r1 == r2) goto L_0x0019
            int r1 = r3._state     // Catch:{ Exception -> 0x0022, Error -> 0x001d }
            r2 = 1
            if (r1 != r2) goto L_0x000e
            goto L_0x0019
        L_0x000e:
            r3.setStarting()     // Catch:{ Exception -> 0x0022, Error -> 0x001d }
            r3.doStart()     // Catch:{ Exception -> 0x0022, Error -> 0x001d }
            r3.setStarted()     // Catch:{ Exception -> 0x0022, Error -> 0x001d }
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return
        L_0x001b:
            r1 = move-exception
            goto L_0x0027
        L_0x001d:
            r1 = move-exception
            r3.setFailed(r1)     // Catch:{ all -> 0x001b }
            throw r1     // Catch:{ all -> 0x001b }
        L_0x0022:
            r1 = move-exception
            r3.setFailed(r1)     // Catch:{ all -> 0x001b }
            throw r1     // Catch:{ all -> 0x001b }
        L_0x0027:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.component.AbstractLifeCycle.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void stop() throws java.lang.Exception {
        /*
            r3 = this;
            java.lang.Object r0 = r3._lock
            monitor-enter(r0)
            int r1 = r3._state     // Catch:{ Exception -> 0x0021, Error -> 0x001c }
            r2 = 3
            if (r1 == r2) goto L_0x0018
            int r1 = r3._state     // Catch:{ Exception -> 0x0021, Error -> 0x001c }
            if (r1 != 0) goto L_0x000d
            goto L_0x0018
        L_0x000d:
            r3.setStopping()     // Catch:{ Exception -> 0x0021, Error -> 0x001c }
            r3.doStop()     // Catch:{ Exception -> 0x0021, Error -> 0x001c }
            r3.setStopped()     // Catch:{ Exception -> 0x0021, Error -> 0x001c }
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            return
        L_0x0018:
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            return
        L_0x001a:
            r1 = move-exception
            goto L_0x0026
        L_0x001c:
            r1 = move-exception
            r3.setFailed(r1)     // Catch:{ all -> 0x001a }
            throw r1     // Catch:{ all -> 0x001a }
        L_0x0021:
            r1 = move-exception
            r3.setFailed(r1)     // Catch:{ all -> 0x001a }
            throw r1     // Catch:{ all -> 0x001a }
        L_0x0026:
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.component.AbstractLifeCycle.stop():void");
    }

    public boolean isRunning() {
        int i = this._state;
        return i == 2 || i == 1;
    }

    public boolean isStarted() {
        return this._state == 2;
    }

    public boolean isStarting() {
        return this._state == 1;
    }

    public boolean isStopping() {
        return this._state == 3;
    }

    public boolean isStopped() {
        return this._state == 0;
    }

    public boolean isFailed() {
        return this._state == -1;
    }

    public void addLifeCycleListener(LifeCycle.Listener listener) {
        this._listeners.add(listener);
    }

    public void removeLifeCycleListener(LifeCycle.Listener listener) {
        this._listeners.remove(listener);
    }

    public String getState() {
        int i = this._state;
        if (i == -1) {
            return FAILED;
        }
        if (i == 0) {
            return STOPPED;
        }
        if (i == 1) {
            return STARTING;
        }
        if (i == 2) {
            return STARTED;
        }
        if (i != 3) {
            return null;
        }
        return STOPPING;
    }

    public static String getState(LifeCycle lifeCycle) {
        if (lifeCycle.isStarting()) {
            return STARTING;
        }
        if (lifeCycle.isStarted()) {
            return STARTED;
        }
        if (lifeCycle.isStopping()) {
            return STOPPING;
        }
        return lifeCycle.isStopped() ? STOPPED : FAILED;
    }

    private void setStarted() {
        this._state = 2;
        LOG.debug("STARTED {}", this);
        Iterator<LifeCycle.Listener> it = this._listeners.iterator();
        while (it.hasNext()) {
            it.next().lifeCycleStarted(this);
        }
    }

    private void setStarting() {
        LOG.debug("starting {}", this);
        this._state = 1;
        Iterator<LifeCycle.Listener> it = this._listeners.iterator();
        while (it.hasNext()) {
            it.next().lifeCycleStarting(this);
        }
    }

    private void setStopping() {
        LOG.debug("stopping {}", this);
        this._state = 3;
        Iterator<LifeCycle.Listener> it = this._listeners.iterator();
        while (it.hasNext()) {
            it.next().lifeCycleStopping(this);
        }
    }

    private void setStopped() {
        this._state = 0;
        LOG.debug("{} {}", STOPPED, this);
        Iterator<LifeCycle.Listener> it = this._listeners.iterator();
        while (it.hasNext()) {
            it.next().lifeCycleStopped(this);
        }
    }

    private void setFailed(Throwable th) {
        this._state = -1;
        Logger logger = LOG;
        logger.warn("FAILED " + this + ": " + th, th);
        Iterator<LifeCycle.Listener> it = this._listeners.iterator();
        while (it.hasNext()) {
            it.next().lifeCycleFailure(this, th);
        }
    }
}
