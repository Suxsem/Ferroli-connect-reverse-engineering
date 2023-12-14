package p110io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableTakeLast */
public final class ObservableTakeLast<T> extends AbstractObservableWithUpstream<T, T> {
    final int count;

    public ObservableTakeLast(ObservableSource<T> observableSource, int i) {
        super(observableSource);
        this.count = i;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeLastObserver(observer, this.count));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTakeLast$TakeLastObserver */
    static final class TakeLastObserver<T> extends ArrayDeque<T> implements Observer<T>, Disposable {
        private static final long serialVersionUID = 7240042530241604978L;
        final Observer<? super T> actual;
        volatile boolean cancelled;
        final int count;

        /* renamed from: s */
        Disposable f3921s;

        TakeLastObserver(Observer<? super T> observer, int i) {
            this.actual = observer;
            this.count = i;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3921s, disposable)) {
                this.f3921s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.count == size()) {
                poll();
            }
            offer(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            Observer<? super T> observer = this.actual;
            while (!this.cancelled) {
                Object poll = poll();
                if (poll != null) {
                    observer.onNext(poll);
                } else if (!this.cancelled) {
                    observer.onComplete();
                    return;
                } else {
                    return;
                }
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f3921s.dispose();
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }
    }
}
