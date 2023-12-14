package p110io.reactivex;

import p110io.reactivex.annotations.NonNull;

/* renamed from: io.reactivex.CompletableOnSubscribe */
public interface CompletableOnSubscribe {
    void subscribe(@NonNull CompletableEmitter completableEmitter) throws Exception;
}
