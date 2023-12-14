package p110io.reactivex;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;

@Experimental
/* renamed from: io.reactivex.ObservableConverter */
public interface ObservableConverter<T, R> {
    @NonNull
    R apply(@NonNull Observable<T> observable);
}
