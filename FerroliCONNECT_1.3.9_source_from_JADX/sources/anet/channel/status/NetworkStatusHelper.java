package anet.channel.status;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.C0608g;
import anet.channel.util.StringUtils;
import com.taobao.accs.utl.BaseMonitor;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: Taobao */
public class NetworkStatusHelper {
    private static final String TAG = "awcn.NetworkStatusHelper";
    static CopyOnWriteArraySet<INetworkStatusChangeListener> listeners = new CopyOnWriteArraySet<>();

    /* compiled from: Taobao */
    public interface INetworkStatusChangeListener {
        void onNetworkStatusChanged(NetworkStatus networkStatus);
    }

    /* compiled from: Taobao */
    public enum NetworkStatus {
        NONE,
        NO,
        G2,
        G3,
        G4,
        WIFI,
        G5;

        public boolean isMobile() {
            return this == G2 || this == G3 || this == G4 || this == G5;
        }

        public boolean isWifi() {
            return this == WIFI;
        }

        public String getType() {
            if (this == G2) {
                return "2G";
            }
            if (this == G3) {
                return "3G";
            }
            if (this == G4) {
                return "4G";
            }
            if (this == G5) {
                return "5G";
            }
            return toString();
        }
    }

    public static synchronized void startListener(Context context) {
        synchronized (NetworkStatusHelper.class) {
            if (context != null) {
                C0560b.f417a = context;
                C0560b.m226a();
                C0560b.m230c();
            } else {
                throw new NullPointerException("context is null");
            }
        }
    }

    public void stopListener(Context context) {
        C0560b.m228b();
    }

    public static void addStatusChangeListener(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        listeners.add(iNetworkStatusChangeListener);
    }

    public static void removeStatusChangeListener(INetworkStatusChangeListener iNetworkStatusChangeListener) {
        listeners.remove(iNetworkStatusChangeListener);
    }

    static void notifyStatusChanged(NetworkStatus networkStatus) {
        ThreadPoolExecutorFactory.submitScheduledTask(new C0559a(networkStatus));
    }

    public static NetworkStatus getStatus() {
        return C0560b.f419c;
    }

    public static String getNetworkSubType() {
        return C0560b.f420d;
    }

    public static String getApn() {
        return C0560b.f421e;
    }

    public static String getCarrier() {
        return C0560b.f424h;
    }

    public static String getSimOp() {
        return C0560b.f425i;
    }

    public static boolean isRoaming() {
        return C0560b.f427k;
    }

    public static String getWifiBSSID() {
        return C0560b.f423g;
    }

    public static String getWifiSSID() {
        return C0560b.f422f;
    }

    public static String getDnsServerAddress() {
        if (!C0560b.f428l.isEmpty()) {
            return C0560b.f428l.get(0).getHostAddress();
        }
        return C0560b.m233f();
    }

    public static boolean isConnected() {
        if (Build.VERSION.SDK_INT >= 24) {
            if (C0560b.f418b) {
                return true;
            }
        } else if (C0560b.f419c != NetworkStatus.NO) {
            return true;
        }
        try {
            NetworkInfo e = C0560b.m232e();
            if (e == null || !e.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean isProxy() {
        NetworkStatus networkStatus = C0560b.f419c;
        String str = C0560b.f421e;
        if (networkStatus == NetworkStatus.WIFI && getWifiProxy() != null) {
            return true;
        }
        if (networkStatus.isMobile()) {
            return str.contains("wap") || C0608g.m360a() != null;
        }
        return false;
    }

    public static String getProxyType() {
        NetworkStatus networkStatus = C0560b.f419c;
        if (networkStatus == NetworkStatus.WIFI && getWifiProxy() != null) {
            return "proxy";
        }
        if (!networkStatus.isMobile() || !C0560b.f421e.contains("wap")) {
            return (!networkStatus.isMobile() || C0608g.m360a() == null) ? "" : BaseMonitor.ALARM_POINT_AUTH;
        }
        return "wap";
    }

    public static Pair<String, Integer> getWifiProxy() {
        if (C0560b.f419c != NetworkStatus.WIFI) {
            return null;
        }
        return C0560b.f426j;
    }

    public static void printNetworkDetail() {
        try {
            NetworkStatus status = getStatus();
            StringBuilder sb = new StringBuilder(128);
            sb.append("\nNetwork detail*******************************\n");
            sb.append("Status: ");
            sb.append(status.getType());
            sb.append(10);
            sb.append("Subtype: ");
            sb.append(getNetworkSubType());
            sb.append(10);
            if (status != NetworkStatus.NO) {
                if (status.isMobile()) {
                    sb.append("Apn: ");
                    sb.append(getApn());
                    sb.append(10);
                    sb.append("Carrier: ");
                    sb.append(getCarrier());
                    sb.append(10);
                } else {
                    sb.append("BSSID: ");
                    sb.append(getWifiBSSID());
                    sb.append(10);
                    sb.append("SSID: ");
                    sb.append(getWifiSSID());
                    sb.append(10);
                }
            }
            if (isProxy()) {
                sb.append("Proxy: ");
                sb.append(getProxyType());
                sb.append(10);
                Pair<String, Integer> wifiProxy = getWifiProxy();
                if (wifiProxy != null) {
                    sb.append("ProxyHost: ");
                    sb.append((String) wifiProxy.first);
                    sb.append(10);
                    sb.append("ProxyPort: ");
                    sb.append(wifiProxy.second);
                    sb.append(10);
                }
            }
            sb.append("*********************************************");
            ALog.m328i(TAG, sb.toString(), (String) null, new Object[0]);
        } catch (Exception unused) {
        }
    }

    public static String getUniqueId(NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String md5ToHex = StringUtils.md5ToHex(getWifiBSSID());
            if (TextUtils.isEmpty(md5ToHex)) {
                md5ToHex = "";
            }
            return "WIFI$" + md5ToHex;
        } else if (!networkStatus.isMobile()) {
            return "";
        } else {
            return networkStatus.getType() + "$" + getApn();
        }
    }

    public static int getRestrictBackgroundStatus() {
        return C0560b.m234g();
    }
}
