package p110io.reactivex;

import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.CompletableSource */
public interface CompletableSource {
    void subscribe(@NonNull CompletableObserver completableObserver);
}
