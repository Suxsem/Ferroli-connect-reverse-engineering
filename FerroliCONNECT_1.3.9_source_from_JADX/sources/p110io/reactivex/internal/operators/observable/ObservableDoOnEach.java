package p110io.reactivex.internal.operators.observable;

import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.CompositeException;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Action;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDoOnEach */
public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;

    public ObservableDoOnEach(ObservableSource<T> observableSource, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        super(observableSource);
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onAfterTerminate = action2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DoOnEachObserver(observer, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDoOnEach$DoOnEachObserver */
    static final class DoOnEachObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        boolean done;
        final Action onAfterTerminate;
        final Action onComplete;
        final Consumer<? super Throwable> onError;
        final Consumer<? super T> onNext;

        /* renamed from: s */
        Disposable f3867s;

        DoOnEachObserver(Observer<? super T> observer, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            this.actual = observer;
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3867s, disposable)) {
                this.f3867s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3867s.dispose();
        }

        public boolean isDisposed() {
            return this.f3867s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.onNext.accept(t);
                    this.actual.onNext(t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3867s.dispose();
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
            try {
                this.onError.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.actual.onError(th);
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        public void onComplete() {
            if (!this.done) {
                try {
                    this.onComplete.run();
                    this.done = true;
                    this.actual.onComplete();
                    try {
                        this.onAfterTerminate.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }
    }
}
