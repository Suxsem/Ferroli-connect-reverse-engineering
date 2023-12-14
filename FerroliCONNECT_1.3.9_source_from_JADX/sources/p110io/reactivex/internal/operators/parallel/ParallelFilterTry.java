package p110io.reactivex.internal.operators.parallel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.functions.BiFunction;
import p110io.reactivex.functions.Predicate;
import p110io.reactivex.internal.fuseable.ConditionalSubscriber;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;
import p110io.reactivex.parallel.ParallelFailureHandling;
import p110io.reactivex.parallel.ParallelFlowable;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry */
public final class ParallelFilterTry<T> extends ParallelFlowable<T> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Predicate<? super T> predicate;
    final ParallelFlowable<T> source;

    public ParallelFilterTry(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.source = parallelFlowable;
        this.predicate = predicate2;
        this.errorHandler = biFunction;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelFilterConditionalSubscriber(conditionalSubscriber, this.predicate, this.errorHandler);
                } else {
                    subscriberArr2[i] = new ParallelFilterSubscriber(conditionalSubscriber, this.predicate, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$BaseFilterSubscriber */
    static abstract class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Predicate<? super T> predicate;

        /* renamed from: s */
        Subscription f3951s;

        BaseFilterSubscriber(Predicate<? super T> predicate2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.predicate = predicate2;
            this.errorHandler = biFunction;
        }

        public final void request(long j) {
            this.f3951s.request(j);
        }

        public final void cancel() {
            this.f3951s.cancel();
        }

        public final void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f3951s.request(1);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$ParallelFilterSubscriber */
    static final class ParallelFilterSubscriber<T> extends BaseFilterSubscriber<T> {
        final Subscriber<? super T> actual;

        ParallelFilterSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3951s, subscription)) {
                this.f3951s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x003d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 != 0) goto L_0x0063
                r2 = 0
            L_0x0007:
                r0 = 1
                io.reactivex.functions.Predicate r4 = r9.predicate     // Catch:{ Throwable -> 0x0017 }
                boolean r2 = r4.test(r10)     // Catch:{ Throwable -> 0x0017 }
                if (r2 == 0) goto L_0x0016
                org.reactivestreams.Subscriber<? super T> r1 = r9.actual
                r1.onNext(r10)
                return r0
            L_0x0016:
                return r1
            L_0x0017:
                r4 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction r6 = r9.errorHandler     // Catch:{ Throwable -> 0x004e }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x004e }
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch:{ Throwable -> 0x004e }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x004e }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x004e }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelFilterTry.C22661.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L_0x0007
                if (r6 == r5) goto L_0x004d
                r10 = 3
                if (r6 == r10) goto L_0x0047
                r9.cancel()
                r9.onError(r4)
                return r1
            L_0x0047:
                r9.cancel()
                r9.onComplete()
            L_0x004d:
                return r1
            L_0x004e:
                r10 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r10)
                r9.cancel()
                io.reactivex.exceptions.CompositeException r2 = new io.reactivex.exceptions.CompositeException
                java.lang.Throwable[] r3 = new java.lang.Throwable[r5]
                r3[r1] = r4
                r3[r0] = r10
                r2.<init>((java.lang.Throwable[]) r3)
                r9.onError(r2)
            L_0x0063:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelFilterTry.ParallelFilterSubscriber.tryOnNext(java.lang.Object):boolean");
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

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$1 */
    static /* synthetic */ class C22661 {
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
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelFilterTry.C22661.<clinit>():void");
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$ParallelFilterConditionalSubscriber */
    static final class ParallelFilterConditionalSubscriber<T> extends BaseFilterSubscriber<T> {
        final ConditionalSubscriber<? super T> actual;

        ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.actual = conditionalSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3951s, subscription)) {
                this.f3951s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 != 0) goto L_0x0067
                r2 = 0
            L_0x0007:
                r0 = 1
                io.reactivex.functions.Predicate r4 = r9.predicate     // Catch:{ Throwable -> 0x001b }
                boolean r2 = r4.test(r10)     // Catch:{ Throwable -> 0x001b }
                if (r2 == 0) goto L_0x0019
                io.reactivex.internal.fuseable.ConditionalSubscriber<? super T> r2 = r9.actual
                boolean r10 = r2.tryOnNext(r10)
                if (r10 == 0) goto L_0x0019
                goto L_0x001a
            L_0x0019:
                r0 = 0
            L_0x001a:
                return r0
            L_0x001b:
                r4 = move-exception
                p110io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction r6 = r9.errorHandler     // Catch:{ Throwable -> 0x0052 }
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x0052 }
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch:{ Throwable -> 0x0052 }
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = p110io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch:{ Throwable -> 0x0052 }
                io.reactivex.parallel.ParallelFailureHandling r6 = (p110io.reactivex.parallel.ParallelFailureHandling) r6     // Catch:{ Throwable -> 0x0052 }
                int[] r7 = p110io.reactivex.internal.operators.parallel.ParallelFilterTry.C22661.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L_0x0007
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
            L_0x0067:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.parallel.ParallelFilterTry.ParallelFilterConditionalSubscriber.tryOnNext(java.lang.Object):boolean");
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
