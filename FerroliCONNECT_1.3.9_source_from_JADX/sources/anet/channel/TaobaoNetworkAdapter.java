package anet.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import anet.channel.Config;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.appmonitor.C0479a;
import anet.channel.entity.C0519c;
import anet.channel.entity.ConnType;
import anet.channel.entity.ENV;
import anet.channel.flow.NetworkAnalysis;
import anet.channel.fulltrace.C0521a;
import anet.channel.fulltrace.IFullTraceAnalysis;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.p004a.C0476a;
import anet.channel.p004a.C0477b;
import anet.channel.p006c.C0488a;
import anet.channel.p007d.C0491a;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import anet.channel.util.Utils;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.util.RequestConstant;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;

/* compiled from: Taobao */
public class TaobaoNetworkAdapter implements Serializable {
    public static AtomicBoolean isInited = new AtomicBoolean();

    public static void init(Context context, HashMap<String, Object> hashMap) {
        boolean z;
        if (isInited.compareAndSet(false, true)) {
            if (hashMap != null && AgooConstants.TAOBAO_PACKAGE.equals(hashMap.get("process"))) {
                AwcnConfig.setAccsSessionCreateForbiddenInBg(true);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put("liveng-bfrtc.alibabausercontent.com");
                jSONArray.put("livecb-bfrtc.alibabausercontent.com");
                jSONArray.put("liveca-bfrtc.alibabausercontent.com");
                AwcnConfig.setHttpDnsNotifyWhiteList(jSONArray.toString());
            }
            if (hashMap != null && "com.taobao.taobao:channel".equals(hashMap.get("process")) && NetworkConfigCenter.isChannelLocalInstanceEnable()) {
                ALog.m327e("awcn.TaobaoNetworkAdapter", "channelLocalInstanceEnable", (String) null, new Object[0]);
                NetworkConfigCenter.setRemoteNetworkServiceEnable(false);
            }
            ALog.setLog(new C0491a());
            NetworkConfigCenter.setRemoteConfig(new C0488a());
            AppMonitor.setInstance(new C0479a());
            NetworkAnalysis.setInstance(new C0477b());
            C0521a.m129a((IFullTraceAnalysis) new C0476a());
            ThreadPoolExecutorFactory.submitPriorityTask(new C0530j(), ThreadPoolExecutorFactory.Priority.NORMAL);
            if (hashMap != null) {
                try {
                    if (AgooConstants.TAOBAO_PACKAGE.equals(hashMap.get("process")) && ((Boolean) hashMap.get("isDebuggable")).booleanValue()) {
                        Utils.invokeStaticMethodThrowException("com.taobao.android.request.analysis.RequestRecorder", "init", new Class[]{Context.class}, context);
                    }
                } catch (Exception e) {
                    ALog.m326e("awcn.TaobaoNetworkAdapter", "RequestRecorder error.", (String) null, e, new Object[0]);
                }
            }
            if (hashMap != null) {
                try {
                    if (!hashMap.containsKey("isNextLaunch") || PreferenceManager.getDefaultSharedPreferences(context).getBoolean(AwcnConfig.NEXT_LAUNCH_FORBID, false)) {
                        z = false;
                    } else {
                        GlobalAppRuntimeInfo.addBucketInfo("isNextLaunch", RequestConstant.TRUE);
                        z = true;
                    }
                    AwcnConfig.setTbNextLaunch(z);
                } catch (Exception unused) {
                }
            }
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean z2 = defaultSharedPreferences.getBoolean(AwcnConfig.HTTP3_ENABLE, true);
            AwcnConfig.setHttp3OrangeEnable(z2);
            if (z2 && hashMap != null && AgooConstants.TAOBAO_PACKAGE.equals(hashMap.get("process"))) {
                AwcnConfig.setHttp3Enable(true);
                ALog.m327e("awcn.TaobaoNetworkAdapter", "http3 enabled.", (String) null, new Object[0]);
            }
            if (hashMap != null) {
                try {
                    boolean containsKey = hashMap.containsKey("ngLaunch");
                    if (AgooConstants.TAOBAO_PACKAGE.equals((String) hashMap.get("process"))) {
                        if (defaultSharedPreferences.getBoolean(NetworkConfigCenter.SERVICE_OPTIMIZE, true)) {
                            NetworkConfigCenter.setBindServiceOptimize(true);
                            ALog.m327e("awcn.TaobaoNetworkAdapter", "bindservice optimize enabled.", (String) null, new Object[0]);
                        }
                        String str = (String) hashMap.get("onlineAppKey");
                        m54a("guide-acs.m.taobao.com", str, ConnProtocol.valueOf(ConnType.HTTP2, ConnType.RTT_0, ConnType.PK_ACS), true, containsKey);
                        ConnProtocol valueOf = ConnProtocol.valueOf(ConnType.HTTP2, ConnType.RTT_0, ConnType.PK_CDN);
                        m54a("gw.alicdn.com", str, valueOf, false, containsKey);
                        m54a("dorangesource.alicdn.com", str, valueOf, false, containsKey);
                        m54a("ossgw.alicdn.com", str, valueOf, false, containsKey);
                    }
                } catch (Exception unused2) {
                }
            }
        }
    }

    /* renamed from: a */
    private static void m54a(String str, String str2, ConnProtocol connProtocol, boolean z, boolean z2) {
        StrategyTemplate.getInstance().registerConnProtocol(str, connProtocol);
        if (!z) {
            return;
        }
        if (!z2) {
            SessionCenter.getInstance(new Config.Builder().setAppkey(str2).setEnv(ENV.ONLINE).build()).registerSessionInfo(SessionInfo.create(str, z, false, (IAuth) null, (IHeartbeat) null, (DataFrameCb) null));
            return;
        }
        SessionCenter.getInstance(new Config.Builder().setAppkey(str2).setEnv(ENV.ONLINE).build()).get(HttpUrl.parse(StringUtils.concatString("https", HttpConstant.SCHEME_SPLIT, str)), C0519c.f249a, 0);
    }
}
