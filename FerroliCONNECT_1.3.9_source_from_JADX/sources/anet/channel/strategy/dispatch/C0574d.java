package anet.channel.strategy.dispatch;

import android.os.Build;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.C0604c;
import java.util.Map;
import java.util.Set;

/* renamed from: anet.channel.strategy.dispatch.d */
/* compiled from: Taobao */
class C0574d {
    public static final String TAG = "amdc.DispatchParamBuilder";

    C0574d() {
    }

    /* renamed from: a */
    public static Map m296a(Map<String, Object> map) {
        IAmdcSign sign = AmdcRuntimeInfo.getSign();
        if (sign == null || TextUtils.isEmpty(sign.getAppkey())) {
            ALog.m327e(TAG, "amdc sign is null or appkey is empty", (String) null, new Object[0]);
            return null;
        }
        NetworkStatusHelper.NetworkStatus status = NetworkStatusHelper.getStatus();
        if (!NetworkStatusHelper.isConnected()) {
            ALog.m327e(TAG, "no network, don't send amdc request", (String) null, new Object[0]);
            return null;
        }
        map.put("appkey", sign.getAppkey());
        map.put("v", DispatchConstants.VER_CODE);
        map.put("platform", DispatchConstants.ANDROID);
        map.put(DispatchConstants.PLATFORM_VERSION, Build.VERSION.RELEASE);
        if (!TextUtils.isEmpty(GlobalAppRuntimeInfo.getUserId())) {
            map.put("sid", GlobalAppRuntimeInfo.getUserId());
        }
        map.put(DispatchConstants.NET_TYPE, status.toString());
        map.put(DispatchConstants.CARRIER, NetworkStatusHelper.getCarrier());
        map.put(DispatchConstants.MNC, NetworkStatusHelper.getSimOp());
        if (AmdcRuntimeInfo.latitude != 0.0d) {
            map.put(DispatchConstants.LATITUDE, String.valueOf(AmdcRuntimeInfo.latitude));
        }
        if (AmdcRuntimeInfo.longitude != 0.0d) {
            map.put(DispatchConstants.LONGTITUDE, String.valueOf(AmdcRuntimeInfo.longitude));
        }
        map.putAll(AmdcRuntimeInfo.getParams());
        map.put("channel", AmdcRuntimeInfo.appChannel);
        map.put(DispatchConstants.APP_NAME, AmdcRuntimeInfo.appName);
        map.put("appVersion", AmdcRuntimeInfo.appVersion);
        map.put(DispatchConstants.STACK_TYPE, Integer.toString(m294a()));
        map.put(DispatchConstants.DOMAIN, m297b(map));
        map.put(DispatchConstants.SIGNTYPE, sign.useSecurityGuard() ? "sec" : "noSec");
        map.put("t", String.valueOf(System.currentTimeMillis()));
        String a = m295a(sign, map);
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        map.put("sign", a);
        return map;
    }

    /* renamed from: a */
    private static int m294a() {
        int c = C0604c.m351c();
        if (c == 1) {
            return 4;
        }
        if (c != 2) {
            return c != 3 ? 4 : 1;
        }
        return 2;
    }

    /* renamed from: b */
    private static String m297b(Map map) {
        StringBuilder sb = new StringBuilder();
        for (String append : (Set) map.remove(DispatchConstants.HOSTS)) {
            sb.append(append);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /* renamed from: a */
    static String m295a(IAmdcSign iAmdcSign, Map<String, String> map) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(C0594c.m322d(map.get("appkey")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.DOMAIN)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.APP_NAME)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("appVersion")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.BSSID)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("channel")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("deviceId")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.LATITUDE)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.LONGTITUDE)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.MACHINE)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.NET_TYPE)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.OTHER)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("platform")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.PLATFORM_VERSION)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.PRE_IP)));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("sid")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("t")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get("v")));
        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        sb.append(C0594c.m322d(map.get(DispatchConstants.SIGNTYPE)));
        try {
            return iAmdcSign.sign(sb.toString());
        } catch (Exception e) {
            ALog.m326e(TAG, "get sign failed", (String) null, e, new Object[0]);
            return null;
        }
    }
}
