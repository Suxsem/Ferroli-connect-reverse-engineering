package p110io.reactivex;

import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.SingleSource */
public interface SingleSource<T> {
    void subscribe(@NonNull SingleObserver<? super T> singleObserver);
}
