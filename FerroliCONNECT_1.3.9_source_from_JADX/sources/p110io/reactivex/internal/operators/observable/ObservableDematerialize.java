package p110io.reactivex.internal.operators.observable;

import p110io.reactivex.Notification;
import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDematerialize */
public final class ObservableDematerialize<T> extends AbstractObservableWithUpstream<Notification<T>, T> {
    public ObservableDematerialize(ObservableSource<Notification<T>> observableSource) {
        super(observableSource);
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DematerializeObserver(observer));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDematerialize$DematerializeObserver */
    static final class DematerializeObserver<T> implements Observer<Notification<T>>, Disposable {
        final Observer<? super T> actual;
        boolean done;

        /* renamed from: s */
        Disposable f3863s;

        DematerializeObserver(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3863s, disposable)) {
                this.f3863s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3863s.dispose();
        }

        public boolean isDisposed() {
            return this.f3863s.isDisposed();
        }

        public void onNext(Notification<T> notification) {
            if (this.done) {
                if (notification.isOnError()) {
                    RxJavaPlugins.onError(notification.getError());
                }
            } else if (notification.isOnError()) {
                this.f3863s.dispose();
                onError(notification.getError());
            } else if (notification.isOnComplete()) {
                this.f3863s.dispose();
                onComplete();
            } else {
                this.actual.onNext(notification.getValue());
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
