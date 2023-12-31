package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p110io.reactivex.Flowable;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.operators.flowable.FlowableConcatMapEager;
import p110io.reactivex.internal.util.ErrorMode;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableConcatMapEagerPublisher */
public final class FlowableConcatMapEagerPublisher<T, R> extends Flowable<R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;
    final Publisher<T> source;

    public FlowableConcatMapEagerPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode2) {
        this.source = publisher;
        this.mapper = function;
        this.maxConcurrency = i;
        this.prefetch = i2;
        this.errorMode = errorMode2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe(new FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber(subscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }
}
