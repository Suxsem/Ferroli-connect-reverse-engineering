package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p110io.reactivex.Flowable;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.operators.flowable.FlowableMap;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableMapPublisher */
public final class FlowableMapPublisher<T, U> extends Flowable<U> {
    final Function<? super T, ? extends U> mapper;
    final Publisher<T> source;

    public FlowableMapPublisher(Publisher<T> publisher, Function<? super T, ? extends U> function) {
        this.source = publisher;
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe(new FlowableMap.MapSubscriber(subscriber, this.mapper));
    }
}
