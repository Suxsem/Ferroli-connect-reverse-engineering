package p110io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import p110io.reactivex.Flowable;
import p110io.reactivex.SingleObserver;
import p110io.reactivex.SingleSource;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import p110io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableConcatWithSingle */
public final class FlowableConcatWithSingle<T> extends AbstractFlowableWithUpstream<T, T> {
    final SingleSource<? extends T> other;

    public FlowableConcatWithSingle(Flowable<T> flowable, SingleSource<? extends T> singleSource) {
        super(flowable);
        this.other = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new ConcatWithSubscriber(subscriber, this.other));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableConcatWithSingle$ConcatWithSubscriber */
    static final class ConcatWithSubscriber<T> extends SinglePostCompleteSubscriber<T, T> implements SingleObserver<T> {
        private static final long serialVersionUID = -7346385463600070225L;
        SingleSource<? extends T> other;
        final AtomicReference<Disposable> otherDisposable = new AtomicReference<>();

        ConcatWithSubscriber(Subscriber<? super T> subscriber, SingleSource<? extends T> singleSource) {
            super(subscriber);
            this.other = singleSource;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.otherDisposable, disposable);
        }

        public void onNext(T t) {
            this.produced++;
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onSuccess(T t) {
            complete(t);
        }

        public void onComplete() {
            this.f4015s = SubscriptionHelper.CANCELLED;
            SingleSource<? extends T> singleSource = this.other;
            this.other = null;
            singleSource.subscribe(this);
        }

        public void cancel() {
            super.cancel();
            DisposableHelper.dispose(this.otherDisposable);
        }
    }
}
