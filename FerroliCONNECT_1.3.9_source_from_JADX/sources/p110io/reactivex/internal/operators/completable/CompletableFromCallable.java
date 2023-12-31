package p110io.reactivex.internal.operators.completable;

import java.util.concurrent.Callable;
import p110io.reactivex.Completable;
import p110io.reactivex.CompletableObserver;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.disposables.Disposables;
import p110io.reactivex.exceptions.Exceptions;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromCallable */
public final class CompletableFromCallable extends Completable {
    final Callable<?> callable;

    public CompletableFromCallable(Callable<?> callable2) {
        this.callable = callable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        Disposable empty = Disposables.empty();
        completableObserver.onSubscribe(empty);
        try {
            this.callable.call();
            if (!empty.isDisposed()) {
                completableObserver.onComplete();
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (!empty.isDisposed()) {
                completableObserver.onError(th);
            }
        }
    }
}
