package p110io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.MaybeObserver;
import p110io.reactivex.MaybeSource;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty */
public final class MaybeSwitchIfEmpty<T> extends AbstractMaybeWithUpstream<T, T> {
    final MaybeSource<? extends T> other;

    public MaybeSwitchIfEmpty(MaybeSource<T> maybeSource, MaybeSource<? extends T> maybeSource2) {
        super(maybeSource);
        this.other = maybeSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new SwitchIfEmptyMaybeObserver(maybeObserver, this.other));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty$SwitchIfEmptyMaybeObserver */
    static final class SwitchIfEmptyMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -2223459372976438024L;
        final MaybeObserver<? super T> actual;
        final MaybeSource<? extends T> other;

        SwitchIfEmptyMaybeObserver(MaybeObserver<? super T> maybeObserver, MaybeSource<? extends T> maybeSource) {
            this.actual = maybeObserver;
            this.other = maybeSource;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            Disposable disposable = (Disposable) get();
            if (disposable != DisposableHelper.DISPOSED && compareAndSet(disposable, (Object) null)) {
                this.other.subscribe(new OtherMaybeObserver(this.actual, this));
            }
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty$SwitchIfEmptyMaybeObserver$OtherMaybeObserver */
        static final class OtherMaybeObserver<T> implements MaybeObserver<T> {
            final MaybeObserver<? super T> actual;
            final AtomicReference<Disposable> parent;

            OtherMaybeObserver(MaybeObserver<? super T> maybeObserver, AtomicReference<Disposable> atomicReference) {
                this.actual = maybeObserver;
                this.parent = atomicReference;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this.parent, disposable);
            }

            public void onSuccess(T t) {
                this.actual.onSuccess(t);
            }

            public void onError(Throwable th) {
                this.actual.onError(th);
            }

            public void onComplete() {
                this.actual.onComplete();
            }
        }
    }
}
