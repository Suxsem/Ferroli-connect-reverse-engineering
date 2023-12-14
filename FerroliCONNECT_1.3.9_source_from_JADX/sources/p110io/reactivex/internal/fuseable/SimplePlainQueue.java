package p110io.reactivex.internal.fuseable;

import p110io.reactivex.annotations.Nullable;

/* renamed from: io.reactivex.internal.fuseable.SimplePlainQueue */
public interface SimplePlainQueue<T> extends SimpleQueue<T> {
    @Nullable
    T poll();
}
