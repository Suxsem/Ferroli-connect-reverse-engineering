package p110io.reactivex.internal.operators.completable;

import p110io.reactivex.Completable;
import p110io.reactivex.CompletableObserver;
import p110io.reactivex.SingleObserver;
import p110io.reactivex.SingleSource;
import p110io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    public CompletableFromSingle(SingleSource<T> singleSource) {
        this.single = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.single.subscribe(new CompletableFromSingleObserver(completableObserver));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle$CompletableFromSingleObserver */
    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {

        /* renamed from: co */
        final CompletableObserver f3672co;

        CompletableFromSingleObserver(CompletableObserver completableObserver) {
            this.f3672co = completableObserver;
        }

        public void onError(Throwable th) {
            this.f3672co.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.f3672co.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.f3672co.onComplete();
        }
    }
}
