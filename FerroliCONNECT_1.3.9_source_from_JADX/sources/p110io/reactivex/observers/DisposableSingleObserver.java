package p110io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.SingleObserver;
import p110io.reactivex.annotations.NonNull;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.DisposableSingleObserver */
public abstract class DisposableSingleObserver<T> implements SingleObserver<T>, Disposable {

    /* renamed from: s */
    final AtomicReference<Disposable> f4024s = new AtomicReference<>();

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void onSubscribe(@NonNull Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.f4024s, disposable, getClass())) {
            onStart();
        }
    }

    public final boolean isDisposed() {
        return this.f4024s.get() == DisposableHelper.DISPOSED;
    }

    public final void dispose() {
        DisposableHelper.dispose(this.f4024s);
    }
}
