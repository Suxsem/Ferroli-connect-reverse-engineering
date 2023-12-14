package p110io.reactivex.internal.operators.completable;

import p110io.reactivex.Completable;
import p110io.reactivex.CompletableObserver;
import p110io.reactivex.CompletableSource;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.exceptions.CompositeException;
import p110io.reactivex.exceptions.Exceptions;
import p110io.reactivex.functions.Function;
import p110io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext */
public final class CompletableResumeNext extends Completable {
    final Function<? super Throwable, ? extends CompletableSource> errorMapper;
    final CompletableSource source;

    public CompletableResumeNext(CompletableSource completableSource, Function<? super Throwable, ? extends CompletableSource> function) {
        this.source = completableSource;
        this.errorMapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        completableObserver.onSubscribe(sequentialDisposable);
        this.source.subscribe(new ResumeNext(completableObserver, sequentialDisposable));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext */
    final class ResumeNext implements CompletableObserver {

        /* renamed from: s */
        final CompletableObserver f3677s;

        /* renamed from: sd */
        final SequentialDisposable f3678sd;

        ResumeNext(CompletableObserver completableObserver, SequentialDisposable sequentialDisposable) {
            this.f3677s = completableObserver;
            this.f3678sd = sequentialDisposable;
        }

        public void onComplete() {
            this.f3677s.onComplete();
        }

        public void onError(Throwable th) {
            try {
                CompletableSource completableSource = (CompletableSource) CompletableResumeNext.this.errorMapper.apply(th);
                if (completableSource == null) {
                    NullPointerException nullPointerException = new NullPointerException("The CompletableConsumable returned is null");
                    nullPointerException.initCause(th);
                    this.f3677s.onError(nullPointerException);
                    return;
                }
                completableSource.subscribe(new OnErrorObserver());
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.f3677s.onError(new CompositeException(th2, th));
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.f3678sd.update(disposable);
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext$OnErrorObserver */
        final class OnErrorObserver implements CompletableObserver {
            OnErrorObserver() {
            }

            public void onComplete() {
                ResumeNext.this.f3677s.onComplete();
            }

            public void onError(Throwable th) {
                ResumeNext.this.f3677s.onError(th);
            }

            public void onSubscribe(Disposable disposable) {
                ResumeNext.this.f3678sd.update(disposable);
            }
        }
    }
}
