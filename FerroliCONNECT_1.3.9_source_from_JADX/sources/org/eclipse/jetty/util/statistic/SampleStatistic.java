package org.eclipse.jetty.util.statistic;

import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.Atomics;

public class SampleStatistic {
    protected final AtomicLong _count = new AtomicLong();
    protected final AtomicLong _max = new AtomicLong();
    protected final AtomicLong _total = new AtomicLong();
    protected final AtomicLong _totalVariance100 = new AtomicLong();

    public void reset() {
        this._max.set(0);
        this._total.set(0);
        this._count.set(0);
        this._totalVariance100.set(0);
    }

    public void set(long j) {
        long addAndGet = this._total.addAndGet(j);
        long incrementAndGet = this._count.incrementAndGet();
        if (incrementAndGet > 1) {
            long j2 = (10 * j) - ((addAndGet * 10) / incrementAndGet);
            this._totalVariance100.addAndGet(j2 * j2);
        }
        Atomics.updateMax(this._max, j);
    }

    public long getMax() {
        return this._max.get();
    }

    public long getTotal() {
        return this._total.get();
    }

    public long getCount() {
        return this._count.get();
    }

    public double getMean() {
        double d = (double) this._total.get();
        double d2 = (double) this._count.get();
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }

    public double getVariance() {
        long j = this._totalVariance100.get();
        long j2 = this._count.get();
        if (j2 <= 1) {
            return 0.0d;
        }
        double d = (double) j;
        Double.isNaN(d);
        double d2 = (double) (j2 - 1);
        Double.isNaN(d2);
        return (d / 100.0d) / d2;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }
}
