package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.Response;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: anetwork.channel.aidl.adapter.a */
/* compiled from: Taobao */
public class C0614a implements Future<Response> {

    /* renamed from: a */
    private ParcelableFuture f625a;

    /* renamed from: b */
    private Response f626b;

    public /* synthetic */ Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return mo9273a(j);
    }

    public C0614a(ParcelableFuture parcelableFuture) {
        this.f625a = parcelableFuture;
    }

    public C0614a(Response response) {
        this.f626b = response;
    }

    public boolean cancel(boolean z) {
        ParcelableFuture parcelableFuture = this.f625a;
        if (parcelableFuture == null) {
            return false;
        }
        try {
            return parcelableFuture.cancel(z);
        } catch (RemoteException e) {
            ALog.m329w("anet.FutureResponse", "[cancel]", (String) null, e, new Object[0]);
            return false;
        }
    }

    public boolean isCancelled() {
        try {
            return this.f625a.isCancelled();
        } catch (RemoteException e) {
            ALog.m329w("anet.FutureResponse", "[isCancelled]", (String) null, e, new Object[0]);
            return false;
        }
    }

    public boolean isDone() {
        try {
            return this.f625a.isDone();
        } catch (RemoteException e) {
            ALog.m329w("anet.FutureResponse", "[isDone]", (String) null, e, new Object[0]);
            return true;
        }
    }

    /* renamed from: a */
    public Response get() throws InterruptedException, ExecutionException {
        Response response = this.f626b;
        if (response != null) {
            return response;
        }
        ParcelableFuture parcelableFuture = this.f625a;
        if (parcelableFuture != null) {
            try {
                return parcelableFuture.get(20000);
            } catch (RemoteException e) {
                ALog.m329w("anet.FutureResponse", "[get]", (String) null, e, new Object[0]);
            }
        }
        return null;
    }

    /* renamed from: a */
    public Response mo9273a(long j) throws InterruptedException, ExecutionException, TimeoutException {
        Response response = this.f626b;
        if (response != null) {
            return response;
        }
        ParcelableFuture parcelableFuture = this.f625a;
        if (parcelableFuture != null) {
            try {
                return parcelableFuture.get(j);
            } catch (RemoteException e) {
                ALog.m329w("anet.FutureResponse", "[get(long timeout, TimeUnit unit)]", (String) null, e, new Object[0]);
            }
        }
        return null;
    }
}
