package p110io.reactivex.internal.subscribers;

import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscriber;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.MissingBackpressureException;
import p110io.reactivex.internal.fuseable.SimplePlainQueue;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.internal.util.BackpressureHelper;
import p110io.reactivex.internal.util.QueueDrain;
import p110io.reactivex.internal.util.QueueDrainHelper;

/* renamed from: io.reactivex.internal.subscribers.QueueDrainSubscriber */
public abstract class QueueDrainSubscriber<T, U, V> extends QueueDrainSubscriberPad4 implements FlowableSubscriber<T>, QueueDrain<U, V> {
    protected final Subscriber<? super V> actual;
    /* access modifiers changed from: protected */
    public volatile boolean cancelled;
    protected volatile boolean done;
    protected Throwable error;
    /* access modifiers changed from: protected */
    public final SimplePlainQueue<U> queue;

    public boolean accept(Subscriber<? super V> subscriber, U u) {
        return false;
    }

    public QueueDrainSubscriber(Subscriber<? super V> subscriber, SimplePlainQueue<U> simplePlainQueue) {
        this.actual = subscriber;
        this.queue = simplePlainQueue;
    }

    public final boolean cancelled() {
        return this.cancelled;
    }

    public final boolean done() {
        return this.done;
    }

    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    public final boolean fastEnter() {
        return this.wip.get() == 0 && this.wip.compareAndSet(0, 1);
    }

    /* access modifiers changed from: protected */
    public final void fastPathEmitMax(U u, boolean z, Disposable disposable) {
        Subscriber<? super V> subscriber = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.requested.get();
            if (j != 0) {
                if (accept(subscriber, u) && j != LongCompanionObject.MAX_VALUE) {
                    produced(1);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                disposable.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            }
        } else {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    /* access modifiers changed from: protected */
    public final void fastPathOrderedEmitMax(U u, boolean z, Disposable disposable) {
        Subscriber<? super V> subscriber = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.requested.get();
            if (j == 0) {
                this.cancelled = true;
                disposable.dispose();
                subscriber.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
                return;
            } else if (simplePlainQueue.isEmpty()) {
                if (accept(subscriber, u) && j != LongCompanionObject.MAX_VALUE) {
                    produced(1);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                simplePlainQueue.offer(u);
            }
        } else {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    public final Throwable error() {
        return this.error;
    }

    public final int leave(int i) {
        return this.wip.addAndGet(i);
    }

    public final long requested() {
        return this.requested.get();
    }

    public final long produced(long j) {
        return this.requested.addAndGet(-j);
    }

    public final void requested(long j) {
        if (SubscriptionHelper.validate(j)) {
            BackpressureHelper.add(this.requested, j);
        }
    }
}
