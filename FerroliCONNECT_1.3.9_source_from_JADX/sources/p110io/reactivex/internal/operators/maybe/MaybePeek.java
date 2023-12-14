package p110io.reactivex.internal.operators.maybe;

import p110io.reactivex.MaybeObserver;
import p110io.reactivex.MaybeSource;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.CompositeException;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Action;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.internal.disposables.EmptyDisposable;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybePeek */
public final class MaybePeek<T> extends AbstractMaybeWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onCompleteCall;
    final Action onDisposeCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super Disposable> onSubscribeCall;
    final Consumer<? super T> onSuccessCall;

    public MaybePeek(MaybeSource<T> maybeSource, Consumer<? super Disposable> consumer, Consumer<? super T> consumer2, Consumer<? super Throwable> consumer3, Action action, Action action2, Action action3) {
        super(maybeSource);
        this.onSubscribeCall = consumer;
        this.onSuccessCall = consumer2;
        this.onErrorCall = consumer3;
        this.onCompleteCall = action;
        this.onAfterTerminate = action2;
        this.onDisposeCall = action3;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new MaybePeekObserver(maybeObserver, this));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybePeek$MaybePeekObserver */
    static final class MaybePeekObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3829d;
        final MaybePeek<T> parent;

        MaybePeekObserver(MaybeObserver<? super T> maybeObserver, MaybePeek<T> maybePeek) {
            this.actual = maybeObserver;
            this.parent = maybePeek;
        }

        public void dispose() {
            try {
                this.parent.onDisposeCall.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.f3829d.dispose();
            this.f3829d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3829d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3829d, disposable)) {
                try {
                    this.parent.onSubscribeCall.accept(disposable);
                    this.f3829d = disposable;
                    this.actual.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    this.f3829d = DisposableHelper.DISPOSED;
                    EmptyDisposable.error(th, (MaybeObserver<?>) this.actual);
                }
            }
        }

        public void onSuccess(T t) {
            if (this.f3829d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onSuccessCall.accept(t);
                    this.f3829d = DisposableHelper.DISPOSED;
                    this.actual.onSuccess(t);
                    onAfterTerminate();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onErrorInner(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.f3829d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
            } else {
                onErrorInner(th);
            }
        }

        /* access modifiers changed from: package-private */
        public void onErrorInner(Throwable th) {
            try {
                this.parent.onErrorCall.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.f3829d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
            onAfterTerminate();
        }

        public void onComplete() {
            if (this.f3829d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onCompleteCall.run();
                    this.f3829d = DisposableHelper.DISPOSED;
                    this.actual.onComplete();
                    onAfterTerminate();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onErrorInner(th);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onAfterTerminate() {
            try {
                this.parent.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
