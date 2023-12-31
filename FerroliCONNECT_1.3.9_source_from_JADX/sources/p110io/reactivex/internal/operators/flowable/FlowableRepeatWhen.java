package p110io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.functions.ObjectHelper;
import p110io.reactivex.internal.subscriptions.EmptySubscription;
import p110io.reactivex.internal.subscriptions.SubscriptionArbiter;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.processors.FlowableProcessor;
import p110io.reactivex.processors.UnicastProcessor;
import p110io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableRepeatWhen */
public final class FlowableRepeatWhen<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Object>, ? extends Publisher<?>> handler;

    public FlowableRepeatWhen(Flowable<T> flowable, Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        super(flowable);
        this.handler = function;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        FlowableProcessor serialized = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.handler.apply(serialized), "handler returned a null Publisher");
            WhenReceiver whenReceiver = new WhenReceiver(this.source);
            RepeatWhenSubscriber repeatWhenSubscriber = new RepeatWhenSubscriber(serializedSubscriber, serialized, whenReceiver);
            whenReceiver.subscriber = repeatWhenSubscriber;
            subscriber.onSubscribe(repeatWhenSubscriber);
            publisher.subscribe(whenReceiver);
            whenReceiver.onNext(0);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRepeatWhen$WhenReceiver */
    static final class WhenReceiver<T, U> extends AtomicInteger implements FlowableSubscriber<Object>, Subscription {
        private static final long serialVersionUID = 2827772011130406689L;
        final AtomicLong requested = new AtomicLong();
        final Publisher<T> source;
        WhenSourceSubscriber<T, U> subscriber;
        final AtomicReference<Subscription> subscription = new AtomicReference<>();

        WhenReceiver(Publisher<T> publisher) {
            this.source = publisher;
        }

        public void onSubscribe(Subscription subscription2) {
            SubscriptionHelper.deferredSetOnce(this.subscription, this.requested, subscription2);
        }

        public void onNext(Object obj) {
            if (getAndIncrement() == 0) {
                while (!SubscriptionHelper.isCancelled(this.subscription.get())) {
                    this.source.subscribe(this.subscriber);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        public void onError(Throwable th) {
            this.subscriber.cancel();
            this.subscriber.actual.onError(th);
        }

        public void onComplete() {
            this.subscriber.cancel();
            this.subscriber.actual.onComplete();
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.subscription, this.requested, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.subscription);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRepeatWhen$WhenSourceSubscriber */
    static abstract class WhenSourceSubscriber<T, U> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -5604623027276966720L;
        protected final Subscriber<? super T> actual;
        protected final FlowableProcessor<U> processor;
        private long produced;
        protected final Subscription receiver;

        WhenSourceSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<U> flowableProcessor, Subscription subscription) {
            this.actual = subscriber;
            this.processor = flowableProcessor;
            this.receiver = subscription;
        }

        public final void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        public final void onNext(T t) {
            this.produced++;
            this.actual.onNext(t);
        }

        /* access modifiers changed from: protected */
        public final void again(U u) {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0;
                produced(j);
            }
            this.receiver.request(1);
            this.processor.onNext(u);
        }

        public final void cancel() {
            super.cancel();
            this.receiver.cancel();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRepeatWhen$RepeatWhenSubscriber */
    static final class RepeatWhenSubscriber<T> extends WhenSourceSubscriber<T, Object> {
        private static final long serialVersionUID = -2680129890138081029L;

        RepeatWhenSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<Object> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        public void onError(Throwable th) {
            this.receiver.cancel();
            this.actual.onError(th);
        }

        public void onComplete() {
            again(0);
        }
    }
}
