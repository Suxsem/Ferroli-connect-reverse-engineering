package p110io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p110io.reactivex.Flowable;
import p110io.reactivex.exceptions.CompositeException;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.functions.ObjectHelper;
import p110io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableOnErrorReturn */
public final class FlowableOnErrorReturn<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends T> valueSupplier;

    public FlowableOnErrorReturn(Flowable<T> flowable, Function<? super Throwable, ? extends T> function) {
        super(flowable);
        this.valueSupplier = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new OnErrorReturnSubscriber(subscriber, this.valueSupplier));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableOnErrorReturn$OnErrorReturnSubscriber */
    static final class OnErrorReturnSubscriber<T> extends SinglePostCompleteSubscriber<T, T> {
        private static final long serialVersionUID = -3740826063558713822L;
        final Function<? super Throwable, ? extends T> valueSupplier;

        OnErrorReturnSubscriber(Subscriber<? super T> subscriber, Function<? super Throwable, ? extends T> function) {
            super(subscriber);
            this.valueSupplier = function;
        }

        public void onNext(T t) {
            this.produced++;
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            try {
                complete(ObjectHelper.requireNonNull(this.valueSupplier.apply(th), "The valueSupplier returned a null value"));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.actual.onError(new CompositeException(th, th2));
            }
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }
}
