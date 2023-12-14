package p110io.reactivex;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;
import p110io.reactivex.annotations.Nullable;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Cancellable;

/* renamed from: io.reactivex.ObservableEmitter */
public interface ObservableEmitter<T> extends Emitter<T> {
    boolean isDisposed();

    @NonNull
    ObservableEmitter<T> serialize();

    void setCancellable(@Nullable Cancellable cancellable);

    void setDisposable(@Nullable Disposable disposable);

    @Experimental
    boolean tryOnError(@NonNull Throwable th);
}
