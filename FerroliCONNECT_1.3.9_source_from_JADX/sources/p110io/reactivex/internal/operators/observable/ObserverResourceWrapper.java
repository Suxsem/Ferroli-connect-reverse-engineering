package p110io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObserverResourceWrapper */
public final class ObserverResourceWrapper<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -8612022020200669122L;
    final Observer<? super T> actual;
    final AtomicReference<Disposable> subscription = new AtomicReference<>();

    public ObserverResourceWrapper(Observer<? super T> observer) {
        this.actual = observer;
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this.subscription, disposable)) {
            this.actual.onSubscribe(this);
        }
    }

    public void onNext(T t) {
        this.actual.onNext(t);
    }

    public void onError(Throwable th) {
        dispose();
        this.actual.onError(th);
    }

    public void onComplete() {
        dispose();
        this.actual.onComplete();
    }

    public void dispose() {
        DisposableHelper.dispose(this.subscription);
        DisposableHelper.dispose(this);
    }

    public boolean isDisposed() {
        return this.subscription.get() == DisposableHelper.DISPOSED;
    }

    public void setResource(Disposable disposable) {
        DisposableHelper.set(this, disposable);
    }
}
