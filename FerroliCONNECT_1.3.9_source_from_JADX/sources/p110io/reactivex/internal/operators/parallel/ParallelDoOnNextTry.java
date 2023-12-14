package p110io.reactivex.internal.operators.parallel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.functions.BiFunction;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.internal.fuseable.ConditionalSubscriber;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.parallel.ParallelFailureHandling;
import p110io.reactivex.parallel.ParallelFlowable;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelDoOnNextTry */
public final class ParallelDoOnNextTry<T> extends ParallelFlowable<T> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Consumer<? super T> onNext;
    final ParallelFlowable<T> source;

    public ParallelDoOnNextTry(ParallelFlowable<T> parallelFlowable, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.source = parallelFlowable;
        this.onNext = consumer;
        this.errorHandler = biFunction;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelDoOnNextConditionalSubscriber(conditionalSubscriber, this.onNext, this.errorHandler);
                } else {
                    subscriberArr2[i] = new ParallelDoOnNextSubscriber(conditionalSubscriber, this.onNext, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelDoOnNextTry$ParallelDoOnNextSubscriber */
    static final class ParallelDoOnNextSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Consumer<? super T> onNext;

        /* renamed from: s */
        Subscription f3949s;

        ParallelDoOnNextSubscriber(Subscriber<? super T> subscriber, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.actual = subscriber;
            this.onNext = consumer;
            this.errorHandler = biFunction;
        }

        public void request(long j) {
            this.f3949s.request(j);
        }

        public void cancel() {
            this.f3949s.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3949s, subscription)) {
                this.f3949s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.f3949s.request(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x003a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 == 0) goto L_0x0006
                return r1
            L_0x0006:
                r2 = 0
            L_0x0008:
                r0 = 1
                io.reactivex.functions.Consumer<? super T> r4 = r9.onNext     // Catch:{ Throwable -> 0x0014 }
                r4.accept(r10)     // Catch:{ Throwable -> 0x0014 }
                org.reactivestreams.Subscriber<? super T> r1 = r9.actual
                r1.onNext(r10)
                return r0
            L_0x0014:
                r4 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch:{ Throwable -> 0x004b }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x004b }
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch:{ Throwable -> 0x004b }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x004b }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x004b }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelDoOnNextTry.C22651.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L_0x0008
                if (r6 == r5) goto L_0x004a
                r10 = 3
                if (r6 == r10) goto L_0x0044
                r9.cancel()
                r9.onError(r4)
                return r1
            L_0x0044:
                r9.cancel()
                r9.onComplete()
            L_0x004a:
                return r1
            L_0x004b:
                r10 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r10)
                r9.cancel()
                io.reactivex.exceptions.CompositeException r2 = new io.reactivex.exceptions.CompositeException
                java.lang.Throwable[] r3 = new java.lang.Throwable[r5]
                r3[r1] = r4
                r3[r0] = r10
                r2.<init>((java.lang.Throwable[]) r3)
                r9.onError(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelDoOnNextTry.ParallelDoOnNextSubscriber.tryOnNext(java.lang.Object):boolean");
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelDoOnNextTry$1 */
    static /* synthetic */ class C22651 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$parallel$ParallelFailureHandling = new int[ParallelFailureHandling.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                io.reactivex.parallel.ParallelFailureHandling[] r0 = p110io.reactivex.parallel.ParallelFailureHandling.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$reactivex$parallel$ParallelFailureHandling = r0
                int[] r0 = $SwitchMap$io$reactivex$parallel$ParallelFailureHandling     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.reactivex.parallel.ParallelFailureHandling r1 = p110io.reactivex.parallel.ParallelFailureHandling.RETRY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$reactivex$parallel$ParallelFailureHandling     // Catch:{ NoSuchFieldError -> 0x001f }
                io.reactivex.parallel.ParallelFailureHandling r1 = p110io.reactivex.parallel.ParallelFailureHandling.SKIP     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$reactivex$parallel$ParallelFailureHandling     // Catch:{ NoSuchFieldError -> 0x002a }
                io.reactivex.parallel.ParallelFailureHandling r1 = p110io.reactivex.parallel.ParallelFailureHandling.STOP     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelDoOnNextTry.C22651.<clinit>():void");
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelDoOnNextTry$ParallelDoOnNextConditionalSubscriber */
    static final class ParallelDoOnNextConditionalSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        final ConditionalSubscriber<? super T> actual;
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Consumer<? super T> onNext;

        /* renamed from: s */
        Subscription f3948s;

        ParallelDoOnNextConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.actual = conditionalSubscriber;
            this.onNext = consumer;
            this.errorHandler = biFunction;
        }

        public void request(long j) {
            this.f3948s.request(j);
        }

        public void cancel() {
            this.f3948s.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3948s, subscription)) {
                this.f3948s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f3948s.request(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 == 0) goto L_0x0006
                return r1
            L_0x0006:
                r2 = 0
            L_0x0008:
                io.reactivex.functions.Consumer<? super T> r0 = r9.onNext     // Catch:{ Throwable -> 0x0014 }
                r0.accept(r10)     // Catch:{ Throwable -> 0x0014 }
                io.reactivex.internal.fuseable.ConditionalSubscriber<? super T> r0 = r9.actual
                boolean r10 = r0.tryOnNext(r10)
                return r10
            L_0x0014:
                r0 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r4 = 1
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch:{ Throwable -> 0x004c }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x004c }
                java.lang.Object r6 = r6.apply(r7, r0)     // Catch:{ Throwable -> 0x004c }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x004c }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x004c }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelDoOnNextTry.C22651.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r4) goto L_0x0008
                if (r6 == r5) goto L_0x004b
                r10 = 3
                if (r6 == r10) goto L_0x0045
                r9.cancel()
                r9.onError(r0)
                return r1
            L_0x0045:
                r9.cancel()
                r9.onComplete()
            L_0x004b:
                return r1
            L_0x004c:
                r10 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r10)
                r9.cancel()
                io.reactivex.exceptions.CompositeException r2 = new io.reactivex.exceptions.CompositeException
                java.lang.Throwable[] r3 = new java.lang.Throwable[r5]
                r3[r1] = r0
                r3[r4] = r10
                r2.<init>((java.lang.Throwable[]) r3)
                r9.onError(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelDoOnNextTry.ParallelDoOnNextConditionalSubscriber.tryOnNext(java.lang.Object):boolean");
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }
    }
}
