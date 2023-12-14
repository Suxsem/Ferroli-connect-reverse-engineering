package p110io.reactivex;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;

@Experimental
/* renamed from: io.reactivex.SingleConverter */
public interface SingleConverter<T, R> {
    @NonNull
    R apply(@NonNull Single<T> single);
}
