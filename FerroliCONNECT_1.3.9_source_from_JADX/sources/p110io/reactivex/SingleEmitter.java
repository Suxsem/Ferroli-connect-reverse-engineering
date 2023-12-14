package p110io.reactivex;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;
import p110io.reactivex.annotations.Nullable;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Cancellable;

/* renamed from: io.reactivex.SingleEmitter */
public interface SingleEmitter<T> {
    boolean isDisposed();

    void onError(@NonNull Throwable th);

    void onSuccess(@NonNull T t);

    void setCancellable(@Nullable Cancellable cancellable);

    void setDisposable(@Nullable Disposable disposable);

    @Experimental
    boolean tryOnError(@NonNull Throwable th);
}
