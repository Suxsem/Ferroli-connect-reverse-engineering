package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p110io.reactivex.Flowable;
import p110io.reactivex.Observable;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> upstream;

    public FlowableFromObservable(Observable<T> observable) {
        this.upstream = observable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.upstream.subscribe(new SubscriberObserver(subscriber));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable$SubscriberObserver */
    static class SubscriberObserver<T> implements Observer<T>, Subscription {

        /* renamed from: d */
        private Disposable f3728d;

        /* renamed from: s */
        private final Subscriber<? super T> f3729s;

        public void request(long j) {
        }

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.f3729s = subscriber;
        }

        public void onComplete() {
            this.f3729s.onComplete();
        }

        public void onError(Throwable th) {
            this.f3729s.onError(th);
        }

        public void onNext(T t) {
            this.f3729s.onNext(t);
        }

        public void onSubscribe(Disposable disposable) {
            this.f3728d = disposable;
            this.f3729s.onSubscribe(this);
        }

        public void cancel() {
            this.f3728d.dispose();
        }
    }
}
