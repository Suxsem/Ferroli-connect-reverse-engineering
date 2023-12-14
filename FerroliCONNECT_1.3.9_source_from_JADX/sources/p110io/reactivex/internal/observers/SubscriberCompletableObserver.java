package p110io.reactivex.internal.observers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.CompletableObserver;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.observers.SubscriberCompletableObserver */
public final class SubscriberCompletableObserver<T> implements CompletableObserver, Subscription {

    /* renamed from: d */
    Disposable f3658d;
    final Subscriber<? super T> subscriber;

    public void request(long j) {
    }

    public SubscriberCompletableObserver(Subscriber<? super T> subscriber2) {
        this.subscriber = subscriber2;
    }

    public void onComplete() {
        this.subscriber.onComplete();
    }

    public void onError(Throwable th) {
        this.subscriber.onError(th);
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.f3658d, disposable)) {
            this.f3658d = disposable;
            this.subscriber.onSubscribe(this);
        }
    }

    public void cancel() {
        this.f3658d.dispose();
    }
}
