package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import p110io.reactivex.Flowable;
import p110io.reactivex.internal.functions.ObjectHelper;
import p110io.reactivex.internal.fuseable.HasUpstreamPublisher;

/* renamed from: io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream */
abstract class AbstractFlowableWithUpstream<T, R> extends Flowable<R> implements HasUpstreamPublisher<T> {
    protected final Flowable<T> source;

    AbstractFlowableWithUpstream(Flowable<T> flowable) {
        this.source = (Flowable) ObjectHelper.requireNonNull(flowable, "source is null");
    }

    public final Publisher<T> source() {
        return this.source;
    }
}
