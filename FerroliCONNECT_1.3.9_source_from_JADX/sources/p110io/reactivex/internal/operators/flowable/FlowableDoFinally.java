package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.Nullable;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Action;
import p110io.reactivex.internal.fuseable.ConditionalSubscriber;
import p110io.reactivex.internal.fuseable.QueueSubscription;
import p110io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

@Experimental
/* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally */
public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action onFinally;

    public FlowableDoFinally(Flowable<T> flowable, Action action) {
        super(flowable);
        this.onFinally = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe(new DoFinallyConditionalSubscriber((ConditionalSubscriber) subscriber, this.onFinally));
        } else {
            this.source.subscribe(new DoFinallySubscriber(subscriber, this.onFinally));
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally$DoFinallySubscriber */
    static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Subscriber<? super T> actual;
        final Action onFinally;

        /* renamed from: qs */
        QueueSubscription<T> f3715qs;

        /* renamed from: s */
        Subscription f3716s;
        boolean syncFused;

        DoFinallySubscriber(Subscriber<? super T> subscriber, Action action) {
            this.actual = subscriber;
            this.onFinally = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3716s, subscription)) {
                this.f3716s = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.f3715qs = (QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
            runFinally();
        }

        public void onComplete() {
            this.actual.onComplete();
            runFinally();
        }

        public void cancel() {
            this.f3716s.cancel();
            runFinally();
        }

        public void request(long j) {
            this.f3716s.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.f3715qs;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.syncFused = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.f3715qs.clear();
        }

        public boolean isEmpty() {
            return this.f3715qs.isEmpty();
        }

        @Nullable
        public T poll() throws Exception {
            T poll = this.f3715qs.poll();
            if (poll == null && this.syncFused) {
                runFinally();
            }
            return poll;
        }

        /* access modifiers changed from: package-private */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally$DoFinallyConditionalSubscriber */
    static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final ConditionalSubscriber<? super T> actual;
        final Action onFinally;

        /* renamed from: qs */
        QueueSubscription<T> f3713qs;

        /* renamed from: s */
        Subscription f3714s;
        boolean syncFused;

        DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Action action) {
            this.actual = conditionalSubscriber;
            this.onFinally = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3714s, subscription)) {
                this.f3714s = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.f3713qs = (QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
            runFinally();
        }

        public void onComplete() {
            this.actual.onComplete();
            runFinally();
        }

        public void cancel() {
            this.f3714s.cancel();
            runFinally();
        }

        public void request(long j) {
            this.f3714s.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.f3713qs;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.syncFused = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.f3713qs.clear();
        }

        public boolean isEmpty() {
            return this.f3713qs.isEmpty();
        }

        @Nullable
        public T poll() throws Exception {
            T poll = this.f3713qs.poll();
            if (poll == null && this.syncFused) {
                runFinally();
            }
            return poll;
        }

        /* access modifiers changed from: package-private */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
