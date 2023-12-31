package p110io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.FlowableSubscriber;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.MissingBackpressureException;
import p110io.reactivex.flowables.ConnectableFlowable;
import p110io.reactivex.internal.fuseable.HasUpstreamPublisher;
import p110io.reactivex.internal.fuseable.QueueSubscription;
import p110io.reactivex.internal.fuseable.SimpleQueue;
import p110io.reactivex.internal.queue.SpscArrayQueue;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.internal.util.BackpressureHelper;
import p110io.reactivex.internal.util.NotificationLite;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish */
public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    static final long CANCELLED = Long.MIN_VALUE;
    final int bufferSize;
    final AtomicReference<PublishSubscriber<T>> current;
    final Publisher<T> onSubscribe;
    final Flowable<T> source;

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly(new FlowablePublish(new FlowablePublisher(atomicReference, i), flowable, atomicReference, i));
    }

    private FlowablePublish(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
        this.onSubscribe = publisher;
        this.source = flowable;
        this.current = atomicReference;
        this.bufferSize = i;
    }

    public Publisher<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.onSubscribe.subscribe(subscriber);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(p110io.reactivex.functions.Consumer<? super p110io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r0 = r4.current
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r0 = (p110io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0023
        L_0x0010:
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r1 = new io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.current
            int r3 = r4.bufferSize
            r1.<init>(r2, r3)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.current
            boolean r0 = r2.compareAndSet(r0, r1)
            if (r0 != 0) goto L_0x0022
            goto L_0x0000
        L_0x0022:
            r0 = r1
        L_0x0023:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0036
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0036
            goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            r5.accept(r0)     // Catch:{ Throwable -> 0x0042 }
            if (r2 == 0) goto L_0x0041
            io.reactivex.Flowable<T> r5 = r4.source
            r5.subscribe(r0)
        L_0x0041:
            return
        L_0x0042:
            r5 = move-exception
            p110io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = p110io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            goto L_0x004c
        L_0x004b:
            throw r5
        L_0x004c:
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.flowable.FlowablePublish.connect(io.reactivex.functions.Consumer):void");
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber */
    static final class PublishSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscriber[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber[] TERMINATED = new InnerSubscriber[0];
        private static final long serialVersionUID = -202316842419149694L;
        final int bufferSize;
        final AtomicReference<PublishSubscriber<T>> current;
        volatile SimpleQueue<T> queue;

        /* renamed from: s */
        final AtomicReference<Subscription> f3744s = new AtomicReference<>();
        final AtomicBoolean shouldConnect;
        int sourceMode;
        final AtomicReference<InnerSubscriber<T>[]> subscribers = new AtomicReference<>(EMPTY);
        volatile Object terminalEvent;

        PublishSubscriber(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.current = atomicReference;
            this.shouldConnect = new AtomicBoolean();
            this.bufferSize = i;
        }

        public void dispose() {
            InnerSubscriber<T>[] innerSubscriberArr = this.subscribers.get();
            InnerSubscriber<T>[] innerSubscriberArr2 = TERMINATED;
            if (innerSubscriberArr != innerSubscriberArr2 && ((InnerSubscriber[]) this.subscribers.getAndSet(innerSubscriberArr2)) != TERMINATED) {
                this.current.compareAndSet(this, (Object) null);
                SubscriptionHelper.cancel(this.f3744s);
            }
        }

        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.f3744s, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.terminalEvent = NotificationLite.complete();
                        dispatch();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request((long) this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                subscription.request((long) this.bufferSize);
            }
        }

        public void onNext(T t) {
            if (this.sourceMode != 0 || this.queue.offer(t)) {
                dispatch();
            } else {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            }
        }

        public void onError(Throwable th) {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.error(th);
                dispatch();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.complete();
                dispatch();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean add(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                if (innerSubscriberArr == TERMINATED) {
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[(length + 1)];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void remove(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (innerSubscriberArr[i2].equals(innerSubscriber)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            innerSubscriberArr2 = EMPTY;
                        } else {
                            InnerSubscriber[] innerSubscriberArr3 = new InnerSubscriber[(length - 1)];
                            System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                            System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                            innerSubscriberArr2 = innerSubscriberArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(Object obj, boolean z) {
            int i = 0;
            if (obj != null) {
                if (!NotificationLite.isComplete(obj)) {
                    Throwable error = NotificationLite.getError(obj);
                    this.current.compareAndSet(this, (Object) null);
                    InnerSubscriber[] innerSubscriberArr = (InnerSubscriber[]) this.subscribers.getAndSet(TERMINATED);
                    if (innerSubscriberArr.length != 0) {
                        int length = innerSubscriberArr.length;
                        while (i < length) {
                            innerSubscriberArr[i].child.onError(error);
                            i++;
                        }
                    } else {
                        RxJavaPlugins.onError(error);
                    }
                    return true;
                } else if (z) {
                    this.current.compareAndSet(this, (Object) null);
                    InnerSubscriber[] innerSubscriberArr2 = (InnerSubscriber[]) this.subscribers.getAndSet(TERMINATED);
                    int length2 = innerSubscriberArr2.length;
                    while (i < length2) {
                        innerSubscriberArr2[i].child.onComplete();
                        i++;
                    }
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x012a, code lost:
            if (r8 <= 0) goto L_0x013d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x012f, code lost:
            if (r1.sourceMode == 1) goto L_0x013e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x0131, code lost:
            r1.f3744s.get().request(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0142, code lost:
            if (r14 == 0) goto L_0x0148;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0144, code lost:
            if (r0 != false) goto L_0x0148;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x0157, code lost:
            continue;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void dispatch() {
            /*
                r25 = this;
                r1 = r25
                int r0 = r25.getAndIncrement()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber<T>[]> r2 = r1.subscribers
                java.lang.Object r0 = r2.get()
                io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[] r0 = (p110io.reactivex.internal.operators.flowable.FlowablePublish.InnerSubscriber[]) r0
                r3 = 1
                r4 = r0
                r5 = 1
            L_0x0014:
                java.lang.Object r0 = r1.terminalEvent
                io.reactivex.internal.fuseable.SimpleQueue<T> r6 = r1.queue
                if (r6 == 0) goto L_0x0023
                boolean r8 = r6.isEmpty()
                if (r8 == 0) goto L_0x0021
                goto L_0x0023
            L_0x0021:
                r8 = 0
                goto L_0x0024
            L_0x0023:
                r8 = 1
            L_0x0024:
                boolean r0 = r1.checkTerminated(r0, r8)
                if (r0 == 0) goto L_0x002b
                return
            L_0x002b:
                if (r8 != 0) goto L_0x0147
                int r0 = r4.length
                int r9 = r4.length
                r12 = 0
                r13 = 0
                r14 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            L_0x0036:
                r16 = -9223372036854775808
                if (r12 >= r9) goto L_0x0053
                r7 = r4[r12]
                long r18 = r7.get()
                int r20 = (r18 > r16 ? 1 : (r18 == r16 ? 0 : -1))
                if (r20 == 0) goto L_0x004e
                long r10 = r7.emitted
                long r10 = r18 - r10
                long r10 = java.lang.Math.min(r14, r10)
                r14 = r10
                goto L_0x0050
            L_0x004e:
                int r13 = r13 + 1
            L_0x0050:
                int r12 = r12 + 1
                goto L_0x0036
            L_0x0053:
                r9 = 1
                if (r0 != r13) goto L_0x0093
                java.lang.Object r0 = r1.terminalEvent
                java.lang.Object r7 = r6.poll()     // Catch:{ Throwable -> 0x005e }
                goto L_0x0075
            L_0x005e:
                r0 = move-exception
                r6 = r0
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r6)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3744s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.cancel()
                java.lang.Object r0 = p110io.reactivex.internal.util.NotificationLite.error(r6)
                r1.terminalEvent = r0
                r7 = 0
            L_0x0075:
                if (r7 != 0) goto L_0x0079
                r6 = 1
                goto L_0x007a
            L_0x0079:
                r6 = 0
            L_0x007a:
                boolean r0 = r1.checkTerminated(r0, r6)
                if (r0 == 0) goto L_0x0081
                return
            L_0x0081:
                int r0 = r1.sourceMode
                if (r0 == r3) goto L_0x0090
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3744s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.request(r9)
            L_0x0090:
                r6 = 1
                goto L_0x0146
            L_0x0093:
                r0 = r8
                r8 = 0
            L_0x0095:
                long r11 = (long) r8
                int r13 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
                if (r13 >= 0) goto L_0x012a
                java.lang.Object r0 = r1.terminalEvent
                java.lang.Object r13 = r6.poll()     // Catch:{ Throwable -> 0x00a1 }
                goto L_0x00b8
            L_0x00a1:
                r0 = move-exception
                r13 = r0
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r13)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3744s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.cancel()
                java.lang.Object r0 = p110io.reactivex.internal.util.NotificationLite.error(r13)
                r1.terminalEvent = r0
                r13 = 0
            L_0x00b8:
                if (r13 != 0) goto L_0x00bc
                r7 = 1
                goto L_0x00bd
            L_0x00bc:
                r7 = 0
            L_0x00bd:
                boolean r0 = r1.checkTerminated(r0, r7)
                if (r0 == 0) goto L_0x00c4
                return
            L_0x00c4:
                if (r7 == 0) goto L_0x00c9
                r0 = r7
                goto L_0x012a
            L_0x00c9:
                java.lang.Object r0 = p110io.reactivex.internal.util.NotificationLite.getValue(r13)
                int r11 = r4.length
                r12 = 0
                r13 = 0
            L_0x00d0:
                if (r12 >= r11) goto L_0x010b
                r3 = r4[r12]
                long r22 = r3.get()
                int r24 = (r22 > r16 ? 1 : (r22 == r16 ? 0 : -1))
                if (r24 == 0) goto L_0x00f9
                r20 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r24 = (r22 > r20 ? 1 : (r22 == r20 ? 0 : -1))
                if (r24 == 0) goto L_0x00ef
                r22 = r6
                r23 = r7
                long r6 = r3.emitted
                long r6 = r6 + r9
                r3.emitted = r6
                goto L_0x00f3
            L_0x00ef:
                r22 = r6
                r23 = r7
            L_0x00f3:
                org.reactivestreams.Subscriber<? super T> r3 = r3.child
                r3.onNext(r0)
                goto L_0x0103
            L_0x00f9:
                r22 = r6
                r23 = r7
                r20 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                r13 = 1
            L_0x0103:
                int r12 = r12 + 1
                r6 = r22
                r7 = r23
                r3 = 1
                goto L_0x00d0
            L_0x010b:
                r22 = r6
                r23 = r7
                r20 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r8 = r8 + 1
                java.lang.Object r0 = r2.get()
                io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[] r0 = (p110io.reactivex.internal.operators.flowable.FlowablePublish.InnerSubscriber[]) r0
                if (r13 != 0) goto L_0x0128
                if (r0 == r4) goto L_0x0121
                goto L_0x0128
            L_0x0121:
                r6 = r22
                r0 = r23
                r3 = 1
                goto L_0x0095
            L_0x0128:
                r4 = r0
                goto L_0x0157
            L_0x012a:
                if (r8 <= 0) goto L_0x013d
                int r3 = r1.sourceMode
                r6 = 1
                if (r3 == r6) goto L_0x013e
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r3 = r1.f3744s
                java.lang.Object r3 = r3.get()
                org.reactivestreams.Subscription r3 = (org.reactivestreams.Subscription) r3
                r3.request(r11)
                goto L_0x013e
            L_0x013d:
                r6 = 1
            L_0x013e:
                r7 = 0
                int r3 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
                if (r3 == 0) goto L_0x0148
                if (r0 != 0) goto L_0x0148
            L_0x0146:
                goto L_0x0157
            L_0x0147:
                r6 = 1
            L_0x0148:
                int r0 = -r5
                int r5 = r1.addAndGet(r0)
                if (r5 != 0) goto L_0x0150
                return
            L_0x0150:
                java.lang.Object r0 = r2.get()
                r4 = r0
                io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[] r4 = (p110io.reactivex.internal.operators.flowable.FlowablePublish.InnerSubscriber[]) r4
            L_0x0157:
                r3 = 1
                goto L_0x0014
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber.dispatch():void");
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber */
    static final class InnerSubscriber<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        long emitted;
        volatile PublishSubscriber<T> parent;

        InnerSubscriber(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                PublishSubscriber<T> publishSubscriber = this.parent;
                if (publishSubscriber != null) {
                    publishSubscriber.dispatch();
                }
            }
        }

        public void cancel() {
            PublishSubscriber<T> publishSubscriber;
            if (get() != Long.MIN_VALUE && getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE && (publishSubscriber = this.parent) != null) {
                publishSubscriber.remove(this);
                publishSubscriber.dispatch();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$FlowablePublisher */
    static final class FlowablePublisher<T> implements Publisher<T> {
        private final int bufferSize;
        private final AtomicReference<PublishSubscriber<T>> curr;

        FlowablePublisher(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.curr = atomicReference;
            this.bufferSize = i;
        }

        public void subscribe(Subscriber<? super T> subscriber) {
            PublishSubscriber<T> publishSubscriber;
            InnerSubscriber innerSubscriber = new InnerSubscriber(subscriber);
            subscriber.onSubscribe(innerSubscriber);
            while (true) {
                publishSubscriber = this.curr.get();
                if (publishSubscriber == null || publishSubscriber.isDisposed()) {
                    PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.curr, this.bufferSize);
                    if (!this.curr.compareAndSet(publishSubscriber, publishSubscriber2)) {
                        continue;
                    } else {
                        publishSubscriber = publishSubscriber2;
                    }
                }
                if (publishSubscriber.add(innerSubscriber)) {
                    break;
                }
            }
            if (innerSubscriber.get() == Long.MIN_VALUE) {
                publishSubscriber.remove(innerSubscriber);
            } else {
                innerSubscriber.parent = publishSubscriber;
            }
            publishSubscriber.dispatch();
        }
    }
}
