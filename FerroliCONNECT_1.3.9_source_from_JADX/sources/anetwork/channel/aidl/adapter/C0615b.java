package anetwork.channel.aidl.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.fulltrace.C0521a;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.util.ALog;
import anetwork.channel.Network;
import anetwork.channel.NetworkListener;
import anetwork.channel.Request;
import anetwork.channel.Response;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.degrade.DegradableNetworkDelegate;
import anetwork.channel.http.HttpNetworkDelegate;
import anetwork.channel.util.RequestConstant;
import java.util.concurrent.Future;

/* renamed from: anetwork.channel.aidl.adapter.b */
/* compiled from: Taobao */
public class C0615b implements Network {
    protected static final int DEGRADE = 1;
    protected static final int HTTP = 0;
    protected static String TAG = "anet.NetworkProxy";
    private Context mContext;
    private volatile RemoteNetwork mDelegate = null;
    private int mType = 0;

    public C0615b(Context context, int i) {
        this.mContext = context;
        this.mType = i;
    }

    public Connection getConnection(Request request, Object obj) {
        ALog.m328i(TAG, "networkProxy getConnection", request.getSeqNo(), new Object[0]);
        recordRequestStat(request);
        initDelegateInstance(true);
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        if (parcelableRequest.url == null) {
            return new ConnectionDelegate(-102);
        }
        try {
            return this.mDelegate.getConnection(parcelableRequest);
        } catch (Throwable th) {
            reportRemoteError(th, "[getConnection]call getConnection method failed.");
            return new ConnectionDelegate(-103);
        }
    }

    public Response syncSend(Request request, Object obj) {
        ALog.m328i(TAG, "networkProxy syncSend", request.getSeqNo(), new Object[0]);
        recordRequestStat(request);
        initDelegateInstance(true);
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        if (parcelableRequest.url == null) {
            return new NetworkResponse(-102);
        }
        try {
            return this.mDelegate.syncSend(parcelableRequest);
        } catch (Throwable th) {
            reportRemoteError(th, "[syncSend]call syncSend method failed.");
            return new NetworkResponse(-103);
        }
    }

    private void initDelegateInstance(boolean z) {
        if (this.mDelegate == null) {
            if (NetworkConfigCenter.isRemoteNetworkServiceEnable()) {
                boolean isTargetProcess = GlobalAppRuntimeInfo.isTargetProcess();
                if (!NetworkConfigCenter.isBindServiceOptimize() || !isTargetProcess) {
                    C0617d.m375a(this.mContext, z);
                    tryGetRemoteNetworkInstance(this.mType);
                    if (this.mDelegate != null) {
                        return;
                    }
                } else {
                    C0617d.m375a(this.mContext, false);
                    if (!C0617d.f632c || this.mDelegate != null) {
                        tryGetRemoteNetworkInstance(this.mType);
                        if (this.mDelegate != null) {
                            return;
                        }
                    } else {
                        this.mDelegate = this.mType == 1 ? new DegradableNetworkDelegate(this.mContext) : new HttpNetworkDelegate(this.mContext);
                        ALog.m328i(TAG, "[initDelegateInstance] getNetworkInstance when binding service", (String) null, new Object[0]);
                        return;
                    }
                }
                if (NetworkConfigCenter.isAllowSpdyWhenBindServiceFailed() && isTargetProcess && C0617d.f631b) {
                    synchronized (this) {
                        if (this.mDelegate == null) {
                            this.mDelegate = this.mType == 1 ? new DegradableNetworkDelegate(this.mContext) : new HttpNetworkDelegate(this.mContext);
                            ALog.m327e(TAG, "[initDelegateInstance] getNetworkInstance when bindService failed.", (String) null, new Object[0]);
                            return;
                        }
                    }
                }
            }
            synchronized (this) {
                if (this.mDelegate == null) {
                    if (ALog.isPrintLog(2)) {
                        ALog.m328i(TAG, "[getLocalNetworkInstance]", (String) null, new Object[0]);
                    }
                    this.mDelegate = new HttpNetworkDelegate(this.mContext);
                }
            }
        }
    }

    public Future<Response> asyncSend(Request request, Object obj, Handler handler, NetworkListener networkListener) {
        boolean z = false;
        ALog.m328i(TAG, "networkProxy asyncSend", request.getSeqNo(), new Object[0]);
        recordRequestStat(request);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            z = true;
        }
        initDelegateInstance(z);
        ParcelableRequest parcelableRequest = new ParcelableRequest(request);
        ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper = null;
        if (!(networkListener == null && handler == null)) {
            parcelableNetworkListenerWrapper = new ParcelableNetworkListenerWrapper(networkListener, handler, obj);
        }
        if (parcelableRequest.url == null) {
            if (parcelableNetworkListenerWrapper != null) {
                try {
                    parcelableNetworkListenerWrapper.onFinished(new DefaultFinishEvent(-102));
                } catch (RemoteException unused) {
                }
            }
            return new C0614a((Response) new NetworkResponse(-102));
        }
        try {
            return new C0614a(this.mDelegate.asyncSend(parcelableRequest, parcelableNetworkListenerWrapper));
        } catch (Throwable th) {
            if (parcelableNetworkListenerWrapper != null) {
                try {
                    parcelableNetworkListenerWrapper.onFinished(new DefaultFinishEvent(-102));
                } catch (RemoteException unused2) {
                }
            }
            reportRemoteError(th, "[asyncSend]call asyncSend exception");
            return new C0614a((Response) new NetworkResponse(-103));
        }
    }

    private synchronized void tryGetRemoteNetworkInstance(int i) {
        if (this.mDelegate == null) {
            if (ALog.isPrintLog(2)) {
                String str = TAG;
                ALog.m328i(str, "[tryGetRemoteNetworkInstance] type=" + i, (String) null, new Object[0]);
            }
            IRemoteNetworkGetter a = C0617d.m373a();
            if (a != null) {
                try {
                    this.mDelegate = a.get(i);
                } catch (Throwable th) {
                    reportRemoteError(th, "[tryGetRemoteNetworkInstance]get RemoteNetwork Delegate failed.");
                }
            }
        } else {
            return;
        }
        return;
    }

    private void reportRemoteError(Throwable th, String str) {
        ALog.m326e(TAG, (String) null, str, th, new Object[0]);
        ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-103, (String) null, "rt");
        exceptionStatistic.exceptionStack = th.toString();
        AppMonitor.getInstance().commitStat(exceptionStatistic);
    }

    private void recordRequestStat(Request request) {
        if (request != null) {
            request.setExtProperty(RequestConstant.KEY_REQ_START, String.valueOf(System.currentTimeMillis()));
            String extProperty = request.getExtProperty(RequestConstant.KEY_TRACE_ID);
            if (TextUtils.isEmpty(extProperty)) {
                extProperty = C0521a.m128a().createRequest();
            }
            request.setExtProperty(RequestConstant.KEY_TRACE_ID, extProperty);
            request.setExtProperty(RequestConstant.KEY_REQ_PROCESS, GlobalAppRuntimeInfo.getCurrentProcess());
        }
    }
}
