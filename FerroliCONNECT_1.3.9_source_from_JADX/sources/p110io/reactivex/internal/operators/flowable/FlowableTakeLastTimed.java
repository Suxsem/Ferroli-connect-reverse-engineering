package p110io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.Scheduler;
import p110io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastTimed */
public final class FlowableTakeLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final long count;
    final boolean delayError;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public FlowableTakeLastTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler2, int i, boolean z) {
        super(flowable);
        this.count = j;
        this.time = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.bufferSize = i;
        this.delayError = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new TakeLastTimedSubscriber(subscriber, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastTimed$TakeLastTimedSubscriber */
    static final class TakeLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        final long count;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f3778s;
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;

        TakeLastTimedSubscriber(Subscriber<? super T> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler scheduler2, int i, boolean z) {
            this.actual = subscriber;
            this.count = j;
            this.time = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler2;
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.delayError = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3778s, subscription)) {
                this.f3778s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(LongCompanionObject.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            long now = this.scheduler.now(this.unit);
            spscLinkedArrayQueue.offer(Long.valueOf(now), t);
            trim(now, spscLinkedArrayQueue);
        }

        public void onError(Throwable th) {
            if (this.delayError) {
                trim(this.scheduler.now(this.unit), this.queue);
            }
            this.error = th;
            this.done = true;
            drain();
        }

        public void onComplete() {
            trim(this.scheduler.now(this.unit), this.queue);
            this.done = true;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void trim(long j, SpscLinkedArrayQueue<Object> spscLinkedArrayQueue) {
            long j2 = this.time;
            long j3 = this.count;
            boolean z = j3 == LongCompanionObject.MAX_VALUE;
            while (!spscLinkedArrayQueue.isEmpty()) {
                if (((Long) spscLinkedArrayQueue.peek()).longValue() < j - j2 || (!z && ((long) (spscLinkedArrayQueue.size() >> 1)) > j3)) {
                    spscLinkedArrayQueue.poll();
                    spscLinkedArrayQueue.poll();
                } else {
                    return;
                }
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f3778s.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.actual;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
                boolean z = this.delayError;
                int i = 1;
                do {
                    if (this.done) {
                        if (!checkTerminated(spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                            long j = this.requested.get();
                            long j2 = 0;
                            while (true) {
                                if (!checkTerminated(spscLinkedArrayQueue.peek() == null, subscriber, z)) {
                                    if (j != j2) {
                                        spscLinkedArrayQueue.poll();
                                        subscriber.onNext(spscLinkedArrayQueue.poll());
                                        j2++;
                                    } else if (j2 != 0) {
                                        BackpressureHelper.produced(this.requested, j2);
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean z, Subscriber<? super T> subscriber, boolean z2) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            } else if (!z2) {
                Throwable th = this.error;
                if (th != null) {
                    this.queue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (!z) {
                    return false;
                } else {
                    subscriber.onComplete();
                    return true;
                }
            } else if (!z) {
                return false;
            } else {
                Throwable th2 = this.error;
                if (th2 != null) {
                    subscriber.onError(th2);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
    }
}
