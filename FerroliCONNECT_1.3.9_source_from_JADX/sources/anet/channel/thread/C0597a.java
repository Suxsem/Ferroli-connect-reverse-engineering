package anet.channel.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: anet.channel.thread.a */
/* compiled from: Taobao */
class C0597a extends ThreadPoolExecutor {
    public C0597a(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(i, i2, j, timeUnit, blockingQueue, threadFactory);
    }

    /* access modifiers changed from: protected */
    public <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new C0598a(runnable, t);
    }

    /* access modifiers changed from: protected */
    public <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new C0598a(callable);
    }

    /* renamed from: anet.channel.thread.a$a */
    /* compiled from: Taobao */
    class C0598a<V> extends FutureTask<V> implements Comparable<C0598a<V>> {

        /* renamed from: b */
        private Object f568b;

        public C0598a(Callable<V> callable) {
            super(callable);
            this.f568b = callable;
        }

        public C0598a(Runnable runnable, V v) {
            super(runnable, v);
            this.f568b = runnable;
        }

        /* renamed from: a */
        public int compareTo(C0598a<V> aVar) {
            if (this == aVar) {
                return 0;
            }
            if (aVar == null) {
                return -1;
            }
            Object obj = this.f568b;
            if (!(obj == null || aVar.f568b == null || !obj.getClass().equals(aVar.f568b.getClass()))) {
                Object obj2 = this.f568b;
                if (obj2 instanceof Comparable) {
                    return ((Comparable) obj2).compareTo(aVar.f568b);
                }
            }
            return 0;
        }
    }
}
