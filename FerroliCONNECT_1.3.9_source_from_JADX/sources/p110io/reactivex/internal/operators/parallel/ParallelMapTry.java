package p110io.reactivex.internal.operators.parallel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.functions.BiFunction;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.fuseable.ConditionalSubscriber;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.parallel.ParallelFailureHandling;
import p110io.reactivex.parallel.ParallelFlowable;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelMapTry */
public final class ParallelMapTry<T, R> extends ParallelFlowable<R> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Function<? super T, ? extends R> mapper;
    final ParallelFlowable<T> source;

    public ParallelMapTry(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.source = parallelFlowable;
        this.mapper = function;
        this.errorHandler = biFunction;
    }

    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelMapTryConditionalSubscriber(conditionalSubscriber, this.mapper, this.errorHandler);
                } else {
                    subscriberArr2[i] = new ParallelMapTrySubscriber(conditionalSubscriber, this.mapper, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelMapTry$ParallelMapTrySubscriber */
    static final class ParallelMapTrySubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        final Subscriber<? super R> actual;
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Function<? super T, ? extends R> mapper;

        /* renamed from: s */
        Subscription f3958s;

        ParallelMapTrySubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.actual = subscriber;
            this.mapper = function;
            this.errorHandler = biFunction;
        }

        public void request(long j) {
            this.f3958s.request(j);
        }

        public void cancel() {
            this.f3958s.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3958s, subscription)) {
                this.f3958s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f3958s.request(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
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
                io.reactivex.functions.Function<? super T, ? extends R> r4 = r9.mapper     // Catch:{ Throwable -> 0x001b }
                java.lang.Object r4 = r4.apply(r10)     // Catch:{ Throwable -> 0x001b }
                java.lang.String r5 = "The mapper returned a null value"
                java.lang.Object r10 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r4, r5)     // Catch:{ Throwable -> 0x001b }
                org.reactivestreams.Subscriber<? super R> r1 = r9.actual
                r1.onNext(r10)
                return r0
            L_0x001b:
                r4 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch:{ Throwable -> 0x0052 }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x0052 }
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch:{ Throwable -> 0x0052 }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x0052 }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x0052 }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelMapTry.C22671.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L_0x0008
                if (r6 == r5) goto L_0x0051
                r10 = 3
                if (r6 == r10) goto L_0x004b
                r9.cancel()
                r9.onError(r4)
                return r1
            L_0x004b:
                r9.cancel()
                r9.onComplete()
            L_0x0051:
                return r1
            L_0x0052:
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
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelMapTry.ParallelMapTrySubscriber.tryOnNext(java.lang.Object):boolean");
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

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelMapTry$1 */
    static /* synthetic */ class C22671 {
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
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelMapTry.C22671.<clinit>():void");
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelMapTry$ParallelMapTryConditionalSubscriber */
    static final class ParallelMapTryConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        final ConditionalSubscriber<? super R> actual;
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Function<? super T, ? extends R> mapper;

        /* renamed from: s */
        Subscription f3957s;

        ParallelMapTryConditionalSubscriber(ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.actual = conditionalSubscriber;
            this.mapper = function;
            this.errorHandler = biFunction;
        }

        public void request(long j) {
            this.f3957s.request(j);
        }

        public void cancel() {
            this.f3957s.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3957s, subscription)) {
                this.f3957s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f3957s.request(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0042  */
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
                io.reactivex.functions.Function<? super T, ? extends R> r0 = r9.mapper     // Catch:{ Throwable -> 0x001b }
                java.lang.Object r0 = r0.apply(r10)     // Catch:{ Throwable -> 0x001b }
                java.lang.String r4 = "The mapper returned a null value"
                java.lang.Object r10 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r0, r4)     // Catch:{ Throwable -> 0x001b }
                io.reactivex.internal.fuseable.ConditionalSubscriber<? super R> r0 = r9.actual
                boolean r10 = r0.tryOnNext(r10)
                return r10
            L_0x001b:
                r0 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r4 = 1
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch:{ Throwable -> 0x0053 }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x0053 }
                java.lang.Object r6 = r6.apply(r7, r0)     // Catch:{ Throwable -> 0x0053 }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x0053 }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x0053 }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelMapTry.C22671.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r4) goto L_0x0008
                if (r6 == r5) goto L_0x0052
                r10 = 3
                if (r6 == r10) goto L_0x004c
                r9.cancel()
                r9.onError(r0)
                return r1
            L_0x004c:
                r9.cancel()
                r9.onComplete()
            L_0x0052:
                return r1
            L_0x0053:
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
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelMapTry.ParallelMapTryConditionalSubscriber.tryOnNext(java.lang.Object):boolean");
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
