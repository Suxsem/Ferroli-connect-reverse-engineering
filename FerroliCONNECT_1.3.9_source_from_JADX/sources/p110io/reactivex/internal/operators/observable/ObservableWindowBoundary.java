package p110io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p110io.reactivex.Observable;
import p110io.reactivex.ObservableSource;
import p110io.reactivex.Observer;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.disposables.DisposableHelper;
import p110io.reactivex.internal.queue.MpscLinkedQueue;
import p110io.reactivex.internal.util.AtomicThrowable;
import p110io.reactivex.observers.DisposableObserver;
import p110io.reactivex.plugins.RxJavaPlugins;
import p110io.reactivex.subjects.UnicastSubject;

/* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundary */
public final class ObservableWindowBoundary<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int capacityHint;
    final ObservableSource<B> other;

    public ObservableWindowBoundary(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, int i) {
        super(observableSource);
        this.other = observableSource2;
        this.capacityHint = i;
    }

    public void subscribeActual(Observer<? super Observable<T>> observer) {
        WindowBoundaryMainObserver windowBoundaryMainObserver = new WindowBoundaryMainObserver(observer, this.capacityHint);
        observer.onSubscribe(windowBoundaryMainObserver);
        this.other.subscribe(windowBoundaryMainObserver.boundaryObserver);
        this.source.subscribe(windowBoundaryMainObserver);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundary$WindowBoundaryMainObserver */
    static final class WindowBoundaryMainObserver<T, B> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        static final Object NEXT_WINDOW = new Object();
        private static final long serialVersionUID = 2233020065421370272L;
        final WindowBoundaryInnerObserver<T, B> boundaryObserver = new WindowBoundaryInnerObserver<>(this);
        final int capacityHint;
        volatile boolean done;
        final Observer<? super Observable<T>> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final MpscLinkedQueue<Object> queue = new MpscLinkedQueue<>();
        final AtomicBoolean stopWindows = new AtomicBoolean();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();
        UnicastSubject<T> window;
        final AtomicInteger windows = new AtomicInteger(1);

        WindowBoundaryMainObserver(Observer<? super Observable<T>> observer, int i) {
            this.downstream = observer;
            this.capacityHint = i;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.upstream, disposable)) {
                innerNext();
            }
        }

        public void onNext(T t) {
            this.queue.offer(t);
            drain();
        }

        public void onError(Throwable th) {
            this.boundaryObserver.dispose();
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.boundaryObserver.dispose();
            this.done = true;
            drain();
        }

        public void dispose() {
            if (this.stopWindows.compareAndSet(false, true)) {
                this.boundaryObserver.dispose();
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.upstream);
                }
            }
        }

        public boolean isDisposed() {
            return this.stopWindows.get();
        }

        public void run() {
            if (this.windows.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.upstream);
            }
        }

        /* access modifiers changed from: package-private */
        public void innerNext() {
            this.queue.offer(NEXT_WINDOW);
            drain();
        }

        /* access modifiers changed from: package-private */
        public void innerError(Throwable th) {
            DisposableHelper.dispose(this.upstream);
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: package-private */
        public void innerComplete() {
            DisposableHelper.dispose(this.upstream);
            this.done = true;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Observer<? super Observable<T>> observer = this.downstream;
                MpscLinkedQueue<Object> mpscLinkedQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                int i = 1;
                while (this.windows.get() != 0) {
                    UnicastSubject<T> unicastSubject = this.window;
                    boolean z = this.done;
                    if (!z || atomicThrowable.get() == null) {
                        Object poll = mpscLinkedQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            Throwable terminate = atomicThrowable.terminate();
                            if (terminate == null) {
                                if (unicastSubject != null) {
                                    this.window = null;
                                    unicastSubject.onComplete();
                                }
                                observer.onComplete();
                                return;
                            }
                            if (unicastSubject != null) {
                                this.window = null;
                                unicastSubject.onError(terminate);
                            }
                            observer.onError(terminate);
                            return;
                        } else if (z2) {
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else if (poll != NEXT_WINDOW) {
                            unicastSubject.onNext(poll);
                        } else {
                            if (unicastSubject != null) {
                                this.window = null;
                                unicastSubject.onComplete();
                            }
                            if (!this.stopWindows.get()) {
                                UnicastSubject<T> create = UnicastSubject.create(this.capacityHint, this);
                                this.window = create;
                                this.windows.getAndIncrement();
                                observer.onNext(create);
                            }
                        }
                    } else {
                        mpscLinkedQueue.clear();
                        Throwable terminate2 = atomicThrowable.terminate();
                        if (unicastSubject != null) {
                            this.window = null;
                            unicastSubject.onError(terminate2);
                        }
                        observer.onError(terminate2);
                        return;
                    }
                }
                mpscLinkedQueue.clear();
                this.window = null;
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowBoundary$WindowBoundaryInnerObserver */
    static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
        boolean done;
        final WindowBoundaryMainObserver<T, B> parent;

        WindowBoundaryInnerObserver(WindowBoundaryMainObserver<T, B> windowBoundaryMainObserver) {
            this.parent = windowBoundaryMainObserver;
        }

        public void onNext(B b) {
            if (!this.done) {
                this.parent.innerNext();
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.innerError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.innerComplete();
            }
        }
    }
}
