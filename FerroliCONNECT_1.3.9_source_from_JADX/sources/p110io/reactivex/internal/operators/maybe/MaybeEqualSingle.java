package p110io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.MaybeObserver;
import p110io.reactivex.MaybeSource;
import p110io.reactivex.Single;
import p110io.reactivex.SingleObserver;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.BiPredicate;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeEqualSingle */
public final class MaybeEqualSingle<T> extends Single<Boolean> {
    final BiPredicate<? super T, ? super T> isEqual;
    final MaybeSource<? extends T> source1;
    final MaybeSource<? extends T> source2;

    public MaybeEqualSingle(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2, BiPredicate<? super T, ? super T> biPredicate) {
        this.source1 = maybeSource;
        this.source2 = maybeSource2;
        this.isEqual = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(singleObserver, this.isEqual);
        singleObserver.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe(this.source1, this.source2);
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeEqualSingle$EqualCoordinator */
    static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        final SingleObserver<? super Boolean> actual;
        final BiPredicate<? super T, ? super T> isEqual;
        final EqualObserver<T> observer1 = new EqualObserver<>(this);
        final EqualObserver<T> observer2 = new EqualObserver<>(this);

        EqualCoordinator(SingleObserver<? super Boolean> singleObserver, BiPredicate<? super T, ? super T> biPredicate) {
            super(2);
            this.actual = singleObserver;
            this.isEqual = biPredicate;
        }

        /* access modifiers changed from: package-private */
        public void subscribe(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2) {
            maybeSource.subscribe(this.observer1);
            maybeSource2.subscribe(this.observer2);
        }

        public void dispose() {
            this.observer1.dispose();
            this.observer2.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.observer1.get());
        }

        /* access modifiers changed from: package-private */
        public void done() {
            if (decrementAndGet() == 0) {
                Object obj = this.observer1.value;
                Object obj2 = this.observer2.value;
                if (obj == null || obj2 == null) {
                    this.actual.onSuccess(Boolean.valueOf(obj == null && obj2 == null));
                    return;
                }
                try {
                    this.actual.onSuccess(Boolean.valueOf(this.isEqual.test(obj, obj2)));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.actual.onError(th);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void error(EqualObserver<T> equalObserver, Throwable th) {
            if (getAndSet(0) > 0) {
                EqualObserver<T> equalObserver2 = this.observer1;
                if (equalObserver == equalObserver2) {
                    this.observer2.dispose();
                } else {
                    equalObserver2.dispose();
                }
                this.actual.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeEqualSingle$EqualObserver */
    static final class EqualObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = -3031974433025990931L;
        final EqualCoordinator<T> parent;
        Object value;

        EqualObserver(EqualCoordinator<T> equalCoordinator) {
            this.parent = equalCoordinator;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.value = t;
            this.parent.done();
        }

        public void onError(Throwable th) {
            this.parent.error(this, th);
        }

        public void onComplete() {
            this.parent.done();
        }
    }
}
