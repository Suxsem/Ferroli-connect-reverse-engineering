package p110io.reactivex;

import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.ObservableSource */
public interface ObservableSource<T> {
    void subscribe(@NonNull Observer<? super T> observer);
}
