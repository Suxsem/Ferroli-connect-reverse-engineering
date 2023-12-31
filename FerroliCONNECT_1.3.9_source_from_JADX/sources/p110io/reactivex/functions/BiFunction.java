package p110io.reactivex.functions;

import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.functions.BiFunction */
public interface BiFunction<T1, T2, R> {
    @NonNull
    R apply(@NonNull T1 t1, @NonNull T2 t2) throws Exception;
}
