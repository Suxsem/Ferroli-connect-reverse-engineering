package android.support.p000v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.util.Pools */
public final class Pools {

    /* renamed from: android.support.v4.util.Pools$Pool */
    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(@NonNull T t);
    }

    private Pools() {
    }

    /* renamed from: android.support.v4.util.Pools$SimplePool */
    public static class SimplePool<T> implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int i) {
            if (i > 0) {
                this.mPool = new Object[i];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        public T acquire() {
            int i = this.mPoolSize;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            T[] tArr = this.mPool;
            T t = tArr[i2];
            tArr[i2] = null;
            this.mPoolSize = i - 1;
            return t;
        }

        public boolean release(@NonNull T t) {
            if (!isInPool(t)) {
                int i = this.mPoolSize;
                Object[] objArr = this.mPool;
                if (i >= objArr.length) {
                    return false;
                }
                objArr[i] = t;
                this.mPoolSize = i + 1;
                return true;
            }
            throw new IllegalStateException("Already in the pool!");
        }

        private boolean isInPool(@NonNull T t) {
            for (int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == t) {
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: android.support.v4.util.Pools$SynchronizedPool */
    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object mLock = new Object();

        public SynchronizedPool(int i) {
            super(i);
        }

        public T acquire() {
            T acquire;
            synchronized (this.mLock) {
                acquire = super.acquire();
            }
            return acquire;
        }

        public boolean release(@NonNull T t) {
            boolean release;
            synchronized (this.mLock) {
                release = super.release(t);
            }
            return release;
        }
    }
}
