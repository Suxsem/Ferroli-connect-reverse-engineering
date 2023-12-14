package p110io.reactivex.parallel;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.functions.BiFunction;

@Experimental
/* renamed from: io.reactivex.parallel.ParallelFailureHandling */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    public ParallelFailureHandling apply(Long l, Throwable th) {
        return this;
    }
}
