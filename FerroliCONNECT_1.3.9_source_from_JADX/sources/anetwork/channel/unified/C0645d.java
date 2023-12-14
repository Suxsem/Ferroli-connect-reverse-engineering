package anetwork.channel.unified;

import anetwork.channel.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: anetwork.channel.unified.d */
/* compiled from: Taobao */
class C0645d implements Future<Response> {

    /* renamed from: a */
    private C0653k f752a;

    /* renamed from: b */
    private boolean f753b;

    public /* synthetic */ Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return mo9358b();
    }

    public C0645d(C0653k kVar) {
        this.f752a = kVar;
    }

    public boolean cancel(boolean z) {
        if (!this.f753b) {
            this.f752a.mo9372b();
            this.f753b = true;
        }
        return true;
    }

    public boolean isCancelled() {
        return this.f753b;
    }

    public boolean isDone() {
        throw new RuntimeException("NOT SUPPORT!");
    }

    /* renamed from: a */
    public Response get() throws InterruptedException, ExecutionException {
        throw new RuntimeException("NOT SUPPORT!");
    }

    /* renamed from: b */
    public Response mo9358b() throws InterruptedException, ExecutionException, TimeoutException {
        throw new RuntimeException("NOT SUPPORT!");
    }
}
