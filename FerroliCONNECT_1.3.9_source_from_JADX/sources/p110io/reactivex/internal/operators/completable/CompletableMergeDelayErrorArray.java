package p110io.reactivex.internal.operators.completable;

import java.util.concurrent.atomic.AtomicInteger;
import p110io.reactivex.Completable;
import p110io.reactivex.CompletableObserver;
import p110io.reactivex.CompletableSource;
import p110io.reactivex.disposables.CompositeDisposable;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.internal.util.AtomicThrowable;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableMergeDelayErrorArray */
public final class CompletableMergeDelayErrorArray extends Completable {
    final CompletableSource[] sources;

    public CompletableMergeDelayErrorArray(CompletableSource[] completableSourceArr) {
        this.sources = completableSourceArr;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AtomicInteger atomicInteger = new AtomicInteger(this.sources.length + 1);
        AtomicThrowable atomicThrowable = new AtomicThrowable();
        completableObserver.onSubscribe(compositeDisposable);
        CompletableSource[] completableSourceArr = this.sources;
        int length = completableSourceArr.length;
        int i = 0;
        while (i < length) {
            CompletableSource completableSource = completableSourceArr[i];
            if (!compositeDisposable.isDisposed()) {
                if (completableSource == null) {
                    atomicThrowable.addThrowable(new NullPointerException("A completable source is null"));
                    atomicInteger.decrementAndGet();
                } else {
                    completableSource.subscribe(new MergeInnerCompletableObserver(completableObserver, compositeDisposable, atomicThrowable, atomicInteger));
                }
                i++;
            } else {
                return;
            }
        }
        if (atomicInteger.decrementAndGet() == 0) {
            Throwable terminate = atomicThrowable.terminate();
            if (terminate == null) {
                completableObserver.onComplete();
            } else {
                completableObserver.onError(terminate);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableMergeDelayErrorArray$MergeInnerCompletableObserver */
    static final class MergeInnerCompletableObserver implements CompletableObserver {
        final CompletableObserver actual;
        final AtomicThrowable error;
        final CompositeDisposable set;
        final AtomicInteger wip;

        MergeInnerCompletableObserver(CompletableObserver completableObserver, CompositeDisposable compositeDisposable, AtomicThrowable atomicThrowable, AtomicInteger atomicInteger) {
            this.actual = completableObserver;
            this.set = compositeDisposable;
            this.error = atomicThrowable;
            this.wip = atomicInteger;
        }

        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        public void onError(Throwable th) {
            if (this.error.addThrowable(th)) {
                tryTerminate();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            tryTerminate();
        }

        /* access modifiers changed from: package-private */
        public void tryTerminate() {
            if (this.wip.decrementAndGet() == 0) {
                Throwable terminate = this.error.terminate();
                if (terminate == null) {
                    this.actual.onComplete();
                } else {
                    this.actual.onError(terminate);
                }
            }
        }
    }
}
