package p110io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.Scheduler;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableUnsubscribeOn */
public final class FlowableUnsubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler scheduler;

    public FlowableUnsubscribeOn(Flowable<T> flowable, Scheduler scheduler2) {
        super(flowable);
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new UnsubscribeSubscriber(subscriber, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableUnsubscribeOn$UnsubscribeSubscriber */
    static final class UnsubscribeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 1015244841293359600L;
        final Subscriber<? super T> actual;

        /* renamed from: s */
        Subscription f3786s;
        final Scheduler scheduler;

        UnsubscribeSubscriber(Subscriber<? super T> subscriber, Scheduler scheduler2) {
            this.actual = subscriber;
            this.scheduler = scheduler2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3786s, subscription)) {
                this.f3786s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!get()) {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            if (get()) {
                RxJavaPlugins.onError(th);
            } else {
                this.actual.onError(th);
            }
        }

        public void onComplete() {
            if (!get()) {
                this.actual.onComplete();
            }
        }

        public void request(long j) {
            this.f3786s.request(j);
        }

        public void cancel() {
            if (compareAndSet(false, true)) {
                this.scheduler.scheduleDirect(new Cancellation());
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableUnsubscribeOn$UnsubscribeSubscriber$Cancellation */
        final class Cancellation implements Runnable {
            Cancellation() {
            }

            public void run() {
                UnsubscribeSubscriber.this.f3786s.cancel();
            }
        }
    }
}
