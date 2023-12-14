package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.Response;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class ParcelableFutureResponse extends ParcelableFuture.Stub {
    private static final String TAG = "anet.ParcelableFutureResponse";
    Future<Response> future;
    NetworkResponse response;

    public ParcelableFutureResponse(Future<Response> future2) {
        this.future = future2;
    }

    public boolean cancel(boolean z) throws RemoteException {
        Future<Response> future2 = this.future;
        if (future2 == null) {
            return true;
        }
        return future2.cancel(z);
    }

    public boolean isCancelled() throws RemoteException {
        Future<Response> future2 = this.future;
        if (future2 == null) {
            return true;
        }
        return future2.isCancelled();
    }

    public boolean isDone() throws RemoteException {
        Future<Response> future2 = this.future;
        if (future2 == null) {
            return true;
        }
        return future2.isDone();
    }

    public NetworkResponse get(long j) throws RemoteException {
        Future<Response> future2 = this.future;
        if (future2 == null) {
            NetworkResponse networkResponse = this.response;
            if (networkResponse != null) {
                return networkResponse;
            }
            return new NetworkResponse(ErrorConstant.ERROR_REQUEST_FAIL);
        }
        try {
            return (NetworkResponse) future2.get(j, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if ("NO SUPPORT".equalsIgnoreCase(e.getMessage())) {
                ALog.m326e(TAG, "[get]有listener将不支持future.get()方法，如有需要请listener传入null", (String) null, e, new Object[0]);
            }
            return new NetworkResponse(ErrorConstant.ERROR_REQUEST_FAIL);
        }
    }
}
