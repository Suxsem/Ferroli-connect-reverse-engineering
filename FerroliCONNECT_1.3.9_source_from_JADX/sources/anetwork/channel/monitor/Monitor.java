package anetwork.channel.monitor;

import android.content.Context;
import anet.channel.monitor.C0532a;
import anet.channel.monitor.C0533b;
import anet.channel.monitor.C0538f;
import anet.channel.monitor.INetworkQualityChangeListener;
import anet.channel.util.ALog;
import anetwork.channel.monitor.speed.NetworkSpeed;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
public class Monitor {
    private static final String TAG = "anet.Monitor";
    static AtomicBoolean isInit = new AtomicBoolean(false);

    public static synchronized void init() {
        synchronized (Monitor.class) {
            if (isInit.compareAndSet(false, true)) {
                C0533b.m138a().mo8845d();
            }
        }
    }

    @Deprecated
    public static synchronized void init(Context context) {
        synchronized (Monitor.class) {
            init();
        }
    }

    public static void start() {
        try {
            C0533b.m138a().mo8845d();
        } catch (Throwable th) {
            ALog.m326e(TAG, "start failed", (String) null, th, new Object[0]);
        }
    }

    public static void stop() {
        try {
            C0533b.m138a().mo8846e();
        } catch (Throwable th) {
            ALog.m326e(TAG, "stop failed", (String) null, th, new Object[0]);
        }
    }

    @Deprecated
    public static NetworkSpeed getNetworkSpeed() {
        return NetworkSpeed.valueOfCode(getNetSpeed().getCode());
    }

    public static anet.channel.monitor.NetworkSpeed getNetSpeed() {
        anet.channel.monitor.NetworkSpeed networkSpeed = anet.channel.monitor.NetworkSpeed.Fast;
        try {
            return anet.channel.monitor.NetworkSpeed.valueOfCode(C0533b.m138a().mo8843b());
        } catch (Throwable th) {
            ALog.m326e(TAG, "getNetworkSpeed failed", (String) null, th, new Object[0]);
            return networkSpeed;
        }
    }

    public static void addListener(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        addListener(iNetworkQualityChangeListener, (C0538f) null);
    }

    public static void addListener(INetworkQualityChangeListener iNetworkQualityChangeListener, C0538f fVar) {
        C0532a.m133a().mo8841a(iNetworkQualityChangeListener, fVar);
    }

    public static void removeListener(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        C0532a.m133a().mo8840a(iNetworkQualityChangeListener);
    }

    public static double getNetSpeedValue() {
        return C0533b.m138a().mo8844c();
    }
}
