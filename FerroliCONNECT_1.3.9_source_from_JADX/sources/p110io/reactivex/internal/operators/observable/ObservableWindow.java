package p110io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import p110io.reactivex.Observable;
import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.subjects.UnicastSubject;

/* renamed from: io.reactivex.internal.operators.observable.ObservableWindow */
public final class ObservableWindow<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int capacityHint;
    final long count;
    final long skip;

    public ObservableWindow(ObservableSource<T> observableSource, long j, long j2, int i) {
        super(observableSource);
        this.count = j;
        this.skip = j2;
        this.capacityHint = i;
    }

    public void subscribeActual(Observer<? super Observable<T>> observer) {
        if (this.count == this.skip) {
            this.source.subscribe(new WindowExactObserver(observer, this.count, this.capacityHint));
            return;
        }
        this.source.subscribe(new WindowSkipObserver(observer, this.count, this.skip, this.capacityHint));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindow$WindowExactObserver */
    static final class WindowExactObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -7481782523886138128L;
        final Observer<? super Observable<T>> actual;
        volatile boolean cancelled;
        final int capacityHint;
        final long count;

        /* renamed from: s */
        Disposable f3933s;
        long size;
        UnicastSubject<T> window;

        WindowExactObserver(Observer<? super Observable<T>> observer, long j, int i) {
            this.actual = observer;
            this.count = j;
            this.capacityHint = i;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3933s, disposable)) {
                this.f3933s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            UnicastSubject<T> unicastSubject = this.window;
            if (unicastSubject == null && !this.cancelled) {
                unicastSubject = UnicastSubject.create(this.capacityHint, this);
                this.window = unicastSubject;
                this.actual.onNext(unicastSubject);
            }
            if (unicastSubject != null) {
                unicastSubject.onNext(t);
                long j = this.size + 1;
                this.size = j;
                if (j >= this.count) {
                    this.size = 0;
                    this.window = null;
                    unicastSubject.onComplete();
                    if (this.cancelled) {
                        this.f3933s.dispose();
                    }
                }
            }
        }

        public void onError(Throwable th) {
            UnicastSubject<T> unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onError(th);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            UnicastSubject<T> unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onComplete();
            }
            this.actual.onComplete();
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        public void run() {
            if (this.cancelled) {
                this.f3933s.dispose();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindow$WindowSkipObserver */
    static final class WindowSkipObserver<T> extends AtomicBoolean implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3366976432059579510L;
        final Observer<? super Observable<T>> actual;
        volatile boolean cancelled;
        final int capacityHint;
        final long count;
        long firstEmission;
        long index;

        /* renamed from: s */
        Disposable f3934s;
        final long skip;
        final ArrayDeque<UnicastSubject<T>> windows;
        final AtomicInteger wip = new AtomicInteger();

        WindowSkipObserver(Observer<? super Observable<T>> observer, long j, long j2, int i) {
            this.actual = observer;
            this.count = j;
            this.skip = j2;
            this.capacityHint = i;
            this.windows = new ArrayDeque<>();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3934s, disposable)) {
                this.f3934s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            long j = this.index;
            long j2 = this.skip;
            if (j % j2 == 0 && !this.cancelled) {
                this.wip.getAndIncrement();
                UnicastSubject create = UnicastSubject.create(this.capacityHint, this);
                arrayDeque.offer(create);
                this.actual.onNext(create);
            }
            long j3 = this.firstEmission + 1;
            Iterator<UnicastSubject<T>> it = arrayDeque.iterator();
            while (it.hasNext()) {
                it.next().onNext(t);
            }
            if (j3 >= this.count) {
                arrayDeque.poll().onComplete();
                if (!arrayDeque.isEmpty() || !this.cancelled) {
                    this.firstEmission = j3 - j2;
                } else {
                    this.f3934s.dispose();
                    return;
                }
            } else {
                this.firstEmission = j3;
            }
            this.index = j + 1;
        }

        public void onError(Throwable th) {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                arrayDeque.poll().onError(th);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            ArrayDeque<UnicastSubject<T>> arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                arrayDeque.poll().onComplete();
            }
            this.actual.onComplete();
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        public void run() {
            if (this.wip.decrementAndGet() == 0 && this.cancelled) {
                this.f3934s.dispose();
            }
        }
    }
}
