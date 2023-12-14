package anet.channel.util;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.NetTypeStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.android.netutil.UdpConnectType;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* renamed from: anet.channel.util.c */
/* compiled from: Taobao */
public class C0604c {
    public static final int IPV4_ONLY = 1;
    public static final int IPV6_ONLY = 2;
    public static final int IP_DUAL_STACK = 3;
    public static final int IP_STACK_UNKNOWN = 0;

    /* renamed from: a */
    static final byte[][] f579a = {new byte[]{-64, 0, 0, -86}, new byte[]{-64, 0, 0, -85}};

    /* renamed from: b */
    static volatile String f580b;

    /* renamed from: c */
    static C0607f f581c;

    /* renamed from: d */
    static ConcurrentHashMap<String, C0607f> f582d = new ConcurrentHashMap<>();

    /* renamed from: e */
    static ConcurrentHashMap<String, Integer> f583e = new ConcurrentHashMap<>();

    /* renamed from: a */
    public static boolean m347a() {
        return false;
    }

    static {
        f580b = null;
        f581c = null;
        try {
            f581c = new C0607f((Inet6Address) InetAddress.getAllByName("64:ff9b::")[0], 96);
            f580b = m349b(NetworkStatusHelper.getStatus());
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static String m349b(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String wifiBSSID = NetworkStatusHelper.getWifiBSSID();
            if (TextUtils.isEmpty(wifiBSSID)) {
                wifiBSSID = "";
            }
            return "WIFI$" + wifiBSSID;
        } else if (!networkStatus.isMobile()) {
            return "UnknownNetwork";
        } else {
            return networkStatus.getType() + "$" + NetworkStatusHelper.getApn();
        }
    }

    /* renamed from: b */
    public static boolean m350b() {
        Integer num = f583e.get(f580b);
        if (num == null || num.intValue() != 1) {
            return false;
        }
        return true;
    }

    /* renamed from: c */
    public static int m351c() {
        Integer num = f583e.get(f580b);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    /* renamed from: d */
    public static C0607f m352d() {
        C0607f fVar = f582d.get(f580b);
        return fVar == null ? f581c : fVar;
    }

    /* renamed from: a */
    public static String m346a(Inet4Address inet4Address) throws Exception {
        if (inet4Address != null) {
            C0607f d = m352d();
            if (d != null) {
                byte[] address = inet4Address.getAddress();
                byte[] address2 = d.f588b.getAddress();
                int i = d.f587a / 8;
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    int i4 = i2 + i;
                    if (i4 <= 15 && i3 < 4) {
                        if (i4 != 8) {
                            address2[i4] = (byte) (address[i3] | address2[i4]);
                            i3++;
                        }
                        i2++;
                    }
                }
                return InetAddress.getByAddress(address2).getHostAddress();
            }
            throw new Exception("cannot get nat64 prefix");
        }
        throw new InvalidParameterException("address in null");
    }

    /* renamed from: a */
    public static String m345a(String str) throws Exception {
        return m346a((Inet4Address) Inet4Address.getByName(str));
    }

    /* renamed from: h */
    private static int m356h() throws SocketException {
        String str;
        int i;
        TreeMap treeMap = new TreeMap();
        Iterator<T> it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
        while (true) {
            str = null;
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            NetworkInterface networkInterface = (NetworkInterface) it.next();
            if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                String displayName = networkInterface.getDisplayName();
                ALog.m328i("awcn.Inet64Util", "find NetworkInterface:" + displayName, (String) null, new Object[0]);
                int i2 = 0;
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    InetAddress address2 = address.getAddress();
                    if (address2 instanceof Inet6Address) {
                        Inet6Address inet6Address = (Inet6Address) address2;
                        if (!m348a((InetAddress) inet6Address)) {
                            ALog.m327e("awcn.Inet64Util", "Found IPv6 address:" + inet6Address.toString(), (String) null, new Object[0]);
                            i2 |= 2;
                        }
                    } else if (address2 instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address2;
                        if (!m348a((InetAddress) inet4Address) && !inet4Address.getHostAddress().startsWith("192.168.43.")) {
                            ALog.m327e("awcn.Inet64Util", "Found IPv4 address:" + inet4Address.toString(), (String) null, new Object[0]);
                            i2 |= 1;
                        }
                    }
                }
                if (i2 != 0) {
                    treeMap.put(displayName.toLowerCase(), Integer.valueOf(i2));
                }
            }
        }
        if (treeMap.isEmpty()) {
            return 0;
        }
        if (treeMap.size() == 1) {
            return ((Integer) treeMap.firstEntry().getValue()).intValue();
        }
        if (NetworkStatusHelper.getStatus().isWifi()) {
            str = "wlan";
        } else if (NetworkStatusHelper.getStatus().isMobile()) {
            str = "rmnet";
        }
        if (str != null) {
            Iterator it2 = treeMap.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                if (((String) entry.getKey()).startsWith(str)) {
                    i = ((Integer) entry.getValue()).intValue();
                    break;
                }
            }
        }
        return (i != 2 || !treeMap.containsKey("v4-wlan0")) ? i : i | ((Integer) treeMap.remove("v4-wlan0")).intValue();
    }

    /* renamed from: i */
    private static int m357i() {
        SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        int i = UdpConnectType.testUdpConnectIpv4() ? 1 : 0;
        return UdpConnectType.testUdpConnectIpv6() ? i | 2 : i;
    }

    /* renamed from: e */
    public static void m353e() {
        if (!AwcnConfig.isIpv6Enable()) {
            ALog.m327e("awcn.Inet64Util", "[startIpStackDetect]ipv6Enable=false", (String) null, new Object[0]);
            return;
        }
        f580b = m349b(NetworkStatusHelper.getStatus());
        if (f583e.putIfAbsent(f580b, 0) == null) {
            int j = m358j();
            f583e.put(f580b, Integer.valueOf(j));
            NetTypeStat netTypeStat = new NetTypeStat();
            netTypeStat.ipStackType = j;
            String str = f580b;
            if (j == 2 || j == 3) {
                ThreadPoolExecutorFactory.submitScheduledTask(new C0605d(str, netTypeStat), 1500, TimeUnit.MILLISECONDS);
            } else if (GlobalAppRuntimeInfo.isTargetProcess()) {
                AppMonitor.getInstance().commitStat(netTypeStat);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public static int m358j() {
        int i;
        String str;
        try {
            if (AwcnConfig.isIpStackDetectByUdpConnect()) {
                str = "udp_connect";
                try {
                    i = m357i();
                } catch (Throwable th) {
                    th = th;
                    ALog.m326e("awcn.Inet64Util", "[detectIpStack]error.", (String) null, th, new Object[0]);
                    i = 0;
                    ALog.m327e("awcn.Inet64Util", "startIpStackDetect", (String) null, "ip stack", Integer.valueOf(i), "detectType", str);
                    return i;
                }
                ALog.m327e("awcn.Inet64Util", "startIpStackDetect", (String) null, "ip stack", Integer.valueOf(i), "detectType", str);
                return i;
            }
            str = "interfaces";
            i = m356h();
            ALog.m327e("awcn.Inet64Util", "startIpStackDetect", (String) null, "ip stack", Integer.valueOf(i), "detectType", str);
            return i;
        } catch (Throwable th2) {
            th = th2;
            str = null;
            ALog.m326e("awcn.Inet64Util", "[detectIpStack]error.", (String) null, th, new Object[0]);
            i = 0;
            ALog.m327e("awcn.Inet64Util", "startIpStackDetect", (String) null, "ip stack", Integer.valueOf(i), "detectType", str);
            return i;
        }
    }

    /* renamed from: a */
    private static boolean m348a(InetAddress inetAddress) {
        return inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress();
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public static C0607f m359k() throws UnknownHostException {
        InetAddress inetAddress;
        boolean z;
        try {
            inetAddress = InetAddress.getByName("ipv4only.arpa");
        } catch (Exception unused) {
            inetAddress = null;
        }
        if (inetAddress instanceof Inet6Address) {
            ALog.m328i("awcn.Inet64Util", "Resolved AAAA: " + inetAddress.toString(), (String) null, new Object[0]);
            byte[] address = inetAddress.getAddress();
            if (address.length != 16) {
                return null;
            }
            int i = 12;
            while (true) {
                z = true;
                if (i < 0) {
                    z = false;
                    break;
                }
                byte b = address[i];
                byte[][] bArr = f579a;
                if ((b & bArr[0][0]) != 0 && address[i + 1] == 0 && address[i + 2] == 0) {
                    int i2 = i + 3;
                    if (address[i2] == bArr[0][3] || address[i2] == bArr[1][3]) {
                        break;
                    }
                }
                i--;
            }
            if (z) {
                address[i + 3] = 0;
                address[i + 2] = 0;
                address[i + 1] = 0;
                address[i] = 0;
                return new C0607f(Inet6Address.getByAddress("ipv4only.arpa", address, 0), i * 8);
            }
        } else if (inetAddress instanceof Inet4Address) {
            ALog.m328i("awcn.Inet64Util", "Resolved A: " + inetAddress.toString(), (String) null, new Object[0]);
        }
        return null;
    }
}
