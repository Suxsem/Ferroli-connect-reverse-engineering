package p110io.reactivex;

import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;

@Experimental
/* renamed from: io.reactivex.CompletableConverter */
public interface CompletableConverter<R> {
    @NonNull
    R apply(@NonNull Completable completable);
}
