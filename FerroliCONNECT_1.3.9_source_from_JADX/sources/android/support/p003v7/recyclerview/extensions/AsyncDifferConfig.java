package android.support.p003v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p003v7.util.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* renamed from: android.support.v7.recyclerview.extensions.AsyncDifferConfig */
public final class AsyncDifferConfig<T> {
    @NonNull
    private final Executor mBackgroundThreadExecutor;
    @NonNull
    private final DiffUtil.ItemCallback<T> mDiffCallback;
    @NonNull
    private final Executor mMainThreadExecutor;

    private AsyncDifferConfig(@NonNull Executor executor, @NonNull Executor executor2, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mMainThreadExecutor = executor;
        this.mBackgroundThreadExecutor = executor2;
        this.mDiffCallback = itemCallback;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @NonNull
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.mDiffCallback;
    }

    /* renamed from: android.support.v7.recyclerview.extensions.AsyncDifferConfig$Builder */
    public static final class Builder<T> {
        private static Executor sDiffExecutor = null;
        private static final Object sExecutorLock = new Object();
        private static final Executor sMainThreadExecutor = new MainThreadExecutor();
        private Executor mBackgroundThreadExecutor;
        private final DiffUtil.ItemCallback<T> mDiffCallback;
        private Executor mMainThreadExecutor;

        public Builder(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
            this.mDiffCallback = itemCallback;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @NonNull
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        /* renamed from: android.support.v7.recyclerview.extensions.AsyncDifferConfig$Builder$MainThreadExecutor */
        private static class MainThreadExecutor implements Executor {
            final Handler mHandler;

            private MainThreadExecutor() {
                this.mHandler = new Handler(Looper.getMainLooper());
            }

            public void execute(@NonNull Runnable runnable) {
                this.mHandler.post(runnable);
            }
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.mMainThreadExecutor == null) {
                this.mMainThreadExecutor = sMainThreadExecutor;
            }
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            return new AsyncDifferConfig<>(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }
}
