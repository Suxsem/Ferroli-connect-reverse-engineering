package org.eclipse.jetty.util.thread;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class Timeout {
    private static final Logger LOG = Log.getLogger((Class<?>) Timeout.class);
    private long _duration;
    private Task _head;
    /* access modifiers changed from: private */
    public Object _lock;
    /* access modifiers changed from: private */
    public volatile long _now;

    public Timeout() {
        this._now = System.currentTimeMillis();
        this._head = new Task();
        this._lock = new Object();
        this._head._timeout = this;
    }

    public Timeout(Object obj) {
        this._now = System.currentTimeMillis();
        this._head = new Task();
        this._lock = obj;
        this._head._timeout = this;
    }

    public long getDuration() {
        return this._duration;
    }

    public void setDuration(long j) {
        this._duration = j;
    }

    public long setNow() {
        long currentTimeMillis = System.currentTimeMillis();
        this._now = currentTimeMillis;
        return currentTimeMillis;
    }

    public long getNow() {
        return this._now;
    }

    public void setNow(long j) {
        this._now = j;
    }

    public Task expired() {
        synchronized (this._lock) {
            long j = this._now - this._duration;
            if (this._head._next == this._head) {
                return null;
            }
            Task task = this._head._next;
            if (task._timestamp > j) {
                return null;
            }
            task.unlink();
            task._expired = true;
            return task;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r3.expired();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void tick() {
        /*
            r7 = this;
            long r0 = r7._now
            long r2 = r7._duration
            long r0 = r0 - r2
        L_0x0005:
            java.lang.Object r2 = r7._lock     // Catch:{ Throwable -> 0x002a }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x002a }
            org.eclipse.jetty.util.thread.Timeout$Task r3 = r7._head     // Catch:{ all -> 0x0027 }
            org.eclipse.jetty.util.thread.Timeout$Task r3 = r3._next     // Catch:{ all -> 0x0027 }
            org.eclipse.jetty.util.thread.Timeout$Task r4 = r7._head     // Catch:{ all -> 0x0027 }
            if (r3 == r4) goto L_0x0025
            long r4 = r3._timestamp     // Catch:{ all -> 0x0027 }
            int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r6 <= 0) goto L_0x0017
            goto L_0x0025
        L_0x0017:
            r3.unlink()     // Catch:{ all -> 0x0027 }
            r4 = 1
            r3._expired = r4     // Catch:{ all -> 0x0027 }
            r3.expire()     // Catch:{ all -> 0x0027 }
            monitor-exit(r2)     // Catch:{ all -> 0x0027 }
            r3.expired()     // Catch:{ Throwable -> 0x002a }
            goto L_0x0005
        L_0x0025:
            monitor-exit(r2)     // Catch:{ all -> 0x0027 }
            return
        L_0x0027:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0027 }
            throw r3     // Catch:{ Throwable -> 0x002a }
        L_0x002a:
            r2 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = LOG
            java.lang.String r4 = "EXCEPTION "
            r3.warn((java.lang.String) r4, (java.lang.Throwable) r2)
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.thread.Timeout.tick():void");
    }

    public void tick(long j) {
        this._now = j;
        tick();
    }

    public void schedule(Task task) {
        schedule(task, 0);
    }

    public void schedule(Task task, long j) {
        synchronized (this._lock) {
            if (task._timestamp != 0) {
                task.unlink();
                task._timestamp = 0;
            }
            task._timeout = this;
            task._expired = false;
            task._delay = j;
            task._timestamp = this._now + j;
            Task task2 = this._head._prev;
            while (true) {
                if (task2 == this._head) {
                    break;
                } else if (task2._timestamp <= task._timestamp) {
                    break;
                } else {
                    task2 = task2._prev;
                }
            }
            task2.link(task);
        }
    }

    public void cancelAll() {
        synchronized (this._lock) {
            Task task = this._head;
            Task task2 = this._head;
            Task task3 = this._head;
            task2._prev = task3;
            task._next = task3;
        }
    }

    public boolean isEmpty() {
        boolean z;
        synchronized (this._lock) {
            z = this._head._next == this._head;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getTimeToNext() {
        /*
            r6 = this;
            java.lang.Object r0 = r6._lock
            monitor-enter(r0)
            org.eclipse.jetty.util.thread.Timeout$Task r1 = r6._head     // Catch:{ all -> 0x0024 }
            org.eclipse.jetty.util.thread.Timeout$Task r1 = r1._next     // Catch:{ all -> 0x0024 }
            org.eclipse.jetty.util.thread.Timeout$Task r2 = r6._head     // Catch:{ all -> 0x0024 }
            if (r1 != r2) goto L_0x000f
            r1 = -1
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return r1
        L_0x000f:
            long r1 = r6._duration     // Catch:{ all -> 0x0024 }
            org.eclipse.jetty.util.thread.Timeout$Task r3 = r6._head     // Catch:{ all -> 0x0024 }
            org.eclipse.jetty.util.thread.Timeout$Task r3 = r3._next     // Catch:{ all -> 0x0024 }
            long r3 = r3._timestamp     // Catch:{ all -> 0x0024 }
            long r1 = r1 + r3
            long r3 = r6._now     // Catch:{ all -> 0x0024 }
            long r1 = r1 - r3
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x0022
            r1 = r3
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return r1
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.thread.Timeout.getTimeToNext():long");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        for (Task task = this._head._next; task != this._head; task = task._next) {
            stringBuffer.append("-->");
            stringBuffer.append(task);
        }
        return stringBuffer.toString();
    }

    public static class Task {
        long _delay;
        boolean _expired = false;
        Task _next = this;
        Task _prev = this;
        Timeout _timeout;
        long _timestamp = 0;

        /* access modifiers changed from: protected */
        public void expire() {
        }

        public void expired() {
        }

        protected Task() {
        }

        public long getTimestamp() {
            return this._timestamp;
        }

        public long getAge() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                long access$200 = timeout._now;
                if (access$200 != 0) {
                    long j = this._timestamp;
                    if (j != 0) {
                        return access$200 - j;
                    }
                }
            }
            return 0;
        }

        /* access modifiers changed from: private */
        public void unlink() {
            Task task = this._next;
            task._prev = this._prev;
            this._prev._next = task;
            this._prev = this;
            this._next = this;
            this._expired = false;
        }

        /* access modifiers changed from: private */
        public void link(Task task) {
            Task task2 = this._next;
            task2._prev = task;
            this._next = task;
            this._next._next = task2;
            this._next._prev = this;
        }

        public void schedule(Timeout timeout) {
            timeout.schedule(this);
        }

        public void schedule(Timeout timeout, long j) {
            timeout.schedule(this, j);
        }

        public void reschedule() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                timeout.schedule(this, this._delay);
            }
        }

        public void cancel() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                synchronized (timeout._lock) {
                    unlink();
                    this._timestamp = 0;
                }
            }
        }

        public boolean isExpired() {
            return this._expired;
        }

        public boolean isScheduled() {
            return this._next != this;
        }
    }
}
