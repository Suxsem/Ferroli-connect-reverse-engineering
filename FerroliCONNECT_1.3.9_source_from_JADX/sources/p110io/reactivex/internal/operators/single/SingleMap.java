package p110io.reactivex.internal.operators.single;

import p110io.reactivex.Single;
import p110io.reactivex.SingleObserver;
import p110io.reactivex.SingleSource;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleMap */
public final class SingleMap<T, R> extends Single<R> {
    final Function<? super T, ? extends R> mapper;
    final SingleSource<? extends T> source;

    public SingleMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends R> function) {
        this.source = singleSource;
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new MapSingleObserver(singleObserver, this.mapper));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleMap$MapSingleObserver */
    static final class MapSingleObserver<T, R> implements SingleObserver<T> {
        final Function<? super T, ? extends R> mapper;

        /* renamed from: t */
        final SingleObserver<? super R> f3982t;

        MapSingleObserver(SingleObserver<? super R> singleObserver, Function<? super T, ? extends R> function) {
            this.f3982t = singleObserver;
            this.mapper = function;
        }

        public void onSubscribe(Disposable disposable) {
            this.f3982t.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                this.f3982t.onSuccess(ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.f3982t.onError(th);
        }
    }
}
