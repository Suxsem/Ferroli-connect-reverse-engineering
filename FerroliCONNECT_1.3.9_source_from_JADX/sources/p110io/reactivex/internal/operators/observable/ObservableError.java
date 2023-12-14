package p110io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p110io.reactivex.Observable;
import p110io.reactivex.Observer;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.internal.disposables.EmptyDisposable;
import p110io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableError */
public final class ObservableError<T> extends Observable<T> {
    final Callable<? extends Throwable> errorSupplier;

    public ObservableError(Callable<? extends Throwable> callable) {
        this.errorSupplier = callable;
    }

    public void subscribeActual(Observer<? super T> observer) {
        try {
            th = (Throwable) ObjectHelper.requireNonNull(this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th) {
            th = th;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, (Observer<?>) observer);
    }
}
