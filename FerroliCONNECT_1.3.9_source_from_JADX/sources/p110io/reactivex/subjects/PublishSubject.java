package p110io.reactivex.subjects;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.Observer;
import p110io.reactivex.annotations.CheckReturnValue;
import p110io.reactivex.annotations.Nullable;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.functions.ObjectHelper;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.subjects.PublishSubject */
public final class PublishSubject<T> extends Subject<T> {
    static final PublishDisposable[] EMPTY = new PublishDisposable[0];
    static final PublishDisposable[] TERMINATED = new PublishDisposable[0];
    Throwable error;
    final AtomicReference<PublishDisposable<T>[]> subscribers = new AtomicReference<>(EMPTY);

    @CheckReturnValue
    public static <T> PublishSubject<T> create() {
        return new PublishSubject<>();
    }

    PublishSubject() {
    }

    public void subscribeActual(Observer<? super T> observer) {
        PublishDisposable publishDisposable = new PublishDisposable(observer, this);
        observer.onSubscribe(publishDisposable);
        if (!add(publishDisposable)) {
            Throwable th = this.error;
            if (th != null) {
                observer.onError(th);
            } else {
                observer.onComplete();
            }
        } else if (publishDisposable.isDisposed()) {
            remove(publishDisposable);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean add(PublishDisposable<T> publishDisposable) {
        PublishDisposable[] publishDisposableArr;
        PublishDisposable[] publishDisposableArr2;
        do {
            publishDisposableArr = (PublishDisposable[]) this.subscribers.get();
            if (publishDisposableArr == TERMINATED) {
                return false;
            }
            int length = publishDisposableArr.length;
            publishDisposableArr2 = new PublishDisposable[(length + 1)];
            System.arraycopy(publishDisposableArr, 0, publishDisposableArr2, 0, length);
            publishDisposableArr2[length] = publishDisposable;
        } while (!this.subscribers.compareAndSet(publishDisposableArr, publishDisposableArr2));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void remove(PublishDisposable<T> publishDisposable) {
        PublishDisposable<T>[] publishDisposableArr;
        PublishDisposable[] publishDisposableArr2;
        do {
            publishDisposableArr = (PublishDisposable[]) this.subscribers.get();
            if (publishDisposableArr != TERMINATED && publishDisposableArr != EMPTY) {
                int length = publishDisposableArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (publishDisposableArr[i2] == publishDisposable) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        publishDisposableArr2 = EMPTY;
                    } else {
                        PublishDisposable[] publishDisposableArr3 = new PublishDisposable[(length - 1)];
                        System.arraycopy(publishDisposableArr, 0, publishDisposableArr3, 0, i);
                        System.arraycopy(publishDisposableArr, i + 1, publishDisposableArr3, i, (length - i) - 1);
                        publishDisposableArr2 = publishDisposableArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.subscribers.compareAndSet(publishDisposableArr, publishDisposableArr2));
    }

    public void onSubscribe(Disposable disposable) {
        if (this.subscribers.get() == TERMINATED) {
            disposable.dispose();
        }
    }

    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.subscribers.get() != TERMINATED) {
            for (PublishDisposable onNext : (PublishDisposable[]) this.subscribers.get()) {
                onNext.onNext(t);
            }
        }
    }

    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        PublishDisposable<T>[] publishDisposableArr = this.subscribers.get();
        PublishDisposable<T>[] publishDisposableArr2 = TERMINATED;
        if (publishDisposableArr == publishDisposableArr2) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.error = th;
        for (PublishDisposable onError : (PublishDisposable[]) this.subscribers.getAndSet(publishDisposableArr2)) {
            onError.onError(th);
        }
    }

    public void onComplete() {
        PublishDisposable<T>[] publishDisposableArr = this.subscribers.get();
        PublishDisposable<T>[] publishDisposableArr2 = TERMINATED;
        if (publishDisposableArr != publishDisposableArr2) {
            for (PublishDisposable onComplete : (PublishDisposable[]) this.subscribers.getAndSet(publishDisposableArr2)) {
                onComplete.onComplete();
            }
        }
    }

    public boolean hasObservers() {
        return ((PublishDisposable[]) this.subscribers.get()).length != 0;
    }

    @Nullable
    public Throwable getThrowable() {
        if (this.subscribers.get() == TERMINATED) {
            return this.error;
        }
        return null;
    }

    public boolean hasThrowable() {
        return this.subscribers.get() == TERMINATED && this.error != null;
    }

    public boolean hasComplete() {
        return this.subscribers.get() == TERMINATED && this.error == null;
    }

    /* renamed from: io.reactivex.subjects.PublishSubject$PublishDisposable */
    static final class PublishDisposable<T> extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 3562861878281475070L;
        final Observer<? super T> actual;
        final PublishSubject<T> parent;

        PublishDisposable(Observer<? super T> observer, PublishSubject<T> publishSubject) {
            this.actual = observer;
            this.parent = publishSubject;
        }

        public void onNext(T t) {
            if (!get()) {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            if (get()) {
                RxJavaPlugins.onError(th);
            } else {
                this.actual.onError(th);
            }
        }

        public void onComplete() {
            if (!get()) {
                this.actual.onComplete();
            }
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                this.parent.remove(this);
            }
        }

        public boolean isDisposed() {
            return get();
        }
    }
}
