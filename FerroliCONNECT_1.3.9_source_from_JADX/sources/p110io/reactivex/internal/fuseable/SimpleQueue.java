package p110io.reactivex.internal.fuseable;

import p110io.reactivex.annotations.NonNull;
import p110io.reactivex.annotations.Nullable;

/* renamed from: io.reactivex.internal.fuseable.SimpleQueue */
public interface SimpleQueue<T> {
    void clear();

    boolean isEmpty();

    boolean offer(@NonNull T t);

    boolean offer(@NonNull T t, @NonNull T t2);

    @Nullable
    T poll() throws Exception;
}
