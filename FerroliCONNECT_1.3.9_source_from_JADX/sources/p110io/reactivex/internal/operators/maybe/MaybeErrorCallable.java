package p110io.reactivex.internal.operators.maybe;

import java.util.concurrent.Callable;
import p110io.reactivex.Maybe;
import p110io.reactivex.MaybeObserver;
import p110io.reactivex.disposables.Disposables;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeErrorCallable */
public final class MaybeErrorCallable<T> extends Maybe<T> {
    final Callable<? extends Throwable> errorSupplier;

    public MaybeErrorCallable(Callable<? extends Throwable> callable) {
        this.errorSupplier = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(Disposables.disposed());
        try {
            th = (Throwable) ObjectHelper.requireNonNull(this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th) {
            th = th;
            Exceptions.throwIfFatal(th);
        }
        maybeObserver.onError(th);
    }
}
