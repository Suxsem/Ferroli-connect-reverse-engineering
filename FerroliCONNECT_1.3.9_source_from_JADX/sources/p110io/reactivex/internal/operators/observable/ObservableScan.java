package p110io.reactivex.internal.operators.observable;

import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.BiFunction;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.internal.functions.ObjectHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableScan */
public final class ObservableScan<T> extends AbstractObservableWithUpstream<T, T> {
    final BiFunction<T, T, T> accumulator;

    public ObservableScan(ObservableSource<T> observableSource, BiFunction<T, T, T> biFunction) {
        super(observableSource);
        this.accumulator = biFunction;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new ScanObserver(observer, this.accumulator));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableScan$ScanObserver */
    static final class ScanObserver<T> implements Observer<T>, Disposable {
        final BiFunction<T, T, T> accumulator;
        final Observer<? super T> actual;
        boolean done;

        /* renamed from: s */
        Disposable f3904s;
        T value;

        ScanObserver(Observer<? super T> observer, BiFunction<T, T, T> biFunction) {
            this.actual = observer;
            this.accumulator = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3904s, disposable)) {
                this.f3904s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3904s.dispose();
        }

        public boolean isDisposed() {
            return this.f3904s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                Observer<? super T> observer = this.actual;
                T t2 = this.value;
                if (t2 == null) {
                    this.value = t;
                    observer.onNext(t);
                    return;
                }
                try {
                    T requireNonNull = ObjectHelper.requireNonNull(this.accumulator.apply(t2, t), "The value returned by the accumulator is null");
                    this.value = requireNonNull;
                    observer.onNext(requireNonNull);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3904s.dispose();
                    onError(th);
                }
            }
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
