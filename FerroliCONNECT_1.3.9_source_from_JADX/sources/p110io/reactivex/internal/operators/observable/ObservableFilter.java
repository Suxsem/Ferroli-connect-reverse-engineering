package p110io.reactivex.internal.operators.observable;

import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.functions.Predicate;
import p110io.reactivex.internal.observers.BasicFuseableObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFilter */
public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate2) {
        super(observableSource);
        this.predicate = predicate2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FilterObserver(observer, this.predicate));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFilter$FilterObserver */
    static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        FilterObserver(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.filter = predicate;
        }

        public void onNext(T t) {
            if (this.sourceMode == 0) {
                try {
                    if (this.filter.test(t)) {
                        this.actual.onNext(t);
                    }
                } catch (Throwable th) {
                    fail(th);
                }
            } else {
                this.actual.onNext(null);
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        @p110io.reactivex.annotations.Nullable
        public T poll() throws java.lang.Exception {
            /*
                r2 = this;
            L_0x0000:
                io.reactivex.internal.fuseable.QueueDisposable r0 = r2.f3641qs
                java.lang.Object r0 = r0.poll()
                if (r0 == 0) goto L_0x0010
                io.reactivex.functions.Predicate<? super T> r1 = r2.filter
                boolean r1 = r1.test(r0)
                if (r1 == 0) goto L_0x0000
            L_0x0010:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p110io.reactivex.internal.operators.observable.ObservableFilter.FilterObserver.poll():java.lang.Object");
        }
    }
}
