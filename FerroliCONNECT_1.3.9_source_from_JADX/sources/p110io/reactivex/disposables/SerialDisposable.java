package p110io.reactivex.disposables;

import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.annotations.Nullable;
import p110io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.disposables.SerialDisposable */
public final class SerialDisposable implements Disposable {
    final AtomicReference<Disposable> resource;

    public SerialDisposable() {
        this.resource = new AtomicReference<>();
    }

    public SerialDisposable(@Nullable Disposable disposable) {
        this.resource = new AtomicReference<>(disposable);
    }

    public boolean set(@Nullable Disposable disposable) {
        return DisposableHelper.set(this.resource, disposable);
    }

    public boolean replace(@Nullable Disposable disposable) {
        return DisposableHelper.replace(this.resource, disposable);
    }

    @Nullable
    public Disposable get() {
        Disposable disposable = this.resource.get();
        return disposable == DisposableHelper.DISPOSED ? Disposables.disposed() : disposable;
    }

    public void dispose() {
        DisposableHelper.dispose(this.resource);
    }

    public boolean isDisposed() {
        return DisposableHelper.isDisposed(this.resource.get());
    }
}
