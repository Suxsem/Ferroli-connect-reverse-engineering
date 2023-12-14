package com.igexin.push.core;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushManager;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1159d;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p033a.p040d.C1191f;
import com.igexin.p032b.p033a.p040d.p041a.C1181a;
import com.igexin.p032b.p033a.p040d.p041a.C1182b;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1224a;
import com.igexin.push.config.C1232i;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p050c.C1304ai;
import com.igexin.push.core.p050c.C1306b;
import com.igexin.push.core.p050c.C1308d;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.extension.C1395a;
import com.igexin.push.p043a.p044a.C1197a;
import com.igexin.push.p043a.p044a.C1198b;
import com.igexin.push.p043a.p044a.C1199c;
import com.igexin.push.p043a.p044a.C1200d;
import com.igexin.push.p045b.C1202a;
import com.igexin.push.p045b.C1203b;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p053d.C1362c;
import com.igexin.push.p054e.C1365a;
import com.igexin.push.p054e.p055a.C1367b;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.p088g.p090b.C1568a;
import com.igexin.push.p088g.p090b.C1570c;
import com.igexin.push.p088g.p090b.C1574g;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.p091a.C1599b;
import com.igexin.sdk.p091a.C1600c;
import com.igexin.sdk.p091a.C1601d;
import com.taobao.accs.utl.UtilityImpl;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.igexin.push.core.e */
public class C1340e implements C1182b {

    /* renamed from: k */
    private static C1340e f2092k;

    /* renamed from: a */
    private Context f2093a;

    /* renamed from: b */
    private C1345h f2094b = new C1345h();

    /* renamed from: c */
    private Handler f2095c;

    /* renamed from: d */
    private Handler f2096d;

    /* renamed from: e */
    private ConcurrentLinkedQueue<Message> f2097e = new ConcurrentLinkedQueue<>();

    /* renamed from: f */
    private C1257f f2098f;

    /* renamed from: g */
    private C1174c f2099g = C1174c.m1310b();

    /* renamed from: h */
    private C1173b f2100h;

    /* renamed from: i */
    private C1560a f2101i;

    /* renamed from: j */
    private C1203b f2102j;

    /* renamed from: l */
    private final AtomicBoolean f2103l = new AtomicBoolean(false);

    private C1340e() {
        this.f2099g.mo14264a((C1181a<String, Integer, C1173b, C1176e>) new C1365a(this.f2093a));
        this.f2099g.mo14314a((C1182b) this);
        this.f2101i = new C1560a();
    }

    /* renamed from: a */
    public static C1340e m2032a() {
        if (f2092k == null) {
            f2092k = new C1340e();
        }
        return f2092k;
    }

    /* renamed from: m */
    private void m2033m() {
        if (C1593r.m3267a(this.f2093a) && TextUtils.isEmpty(C1343f.f2186w)) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (true) {
                    if (!networkInterfaces.hasMoreElements()) {
                        break;
                    }
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if ("wlan0".equalsIgnoreCase(nextElement.getName())) {
                        byte[] hardwareAddress = nextElement.getHardwareAddress();
                        if (hardwareAddress == null) {
                            continue;
                        } else if (hardwareAddress.length != 0) {
                            StringBuilder sb = new StringBuilder();
                            int length = hardwareAddress.length;
                            for (int i = 0; i < length; i++) {
                                sb.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                            }
                            if (sb.length() > 0) {
                                sb.deleteCharAt(sb.length() - 1);
                            }
                            C1312h.m1937a().mo14674b(sb.toString());
                        }
                    }
                }
                C1179b.m1354a("CoreLogic mac:" + C1343f.f2186w);
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: n */
    private boolean m2034n() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE);
        intentFilter.addAction("com.igexin.sdk.action.execute");
        intentFilter.addAction("com.igexin.sdk.action.doaction");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.f2093a.registerReceiver(C1352o.m2096a(), intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addDataScheme("package");
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        this.f2093a.registerReceiver(C1351n.m2095a(), intentFilter2);
        return true;
    }

    /* renamed from: a */
    public boolean mo14701a(Context context) {
        this.f2093a = context.getApplicationContext();
        C1345h hVar = this.f2094b;
        if ((hVar == null || !hVar.isAlive()) && !this.f2103l.getAndSet(true)) {
            C1179b.m1354a("CoreLogic|start coreThread +++++");
            this.f2094b.start();
            this.f2095c = new C1331d(this.f2094b.getLooper());
            this.f2096d = new C1159d(this.f2094b.getLooper());
        }
        return true;
    }

    /* renamed from: a */
    public boolean mo14702a(Message message) {
        if (C1343f.f2170g.get()) {
            this.f2095c.sendMessage(message);
            return true;
        }
        this.f2097e.add(message);
        return true;
    }

    /* renamed from: a */
    public boolean mo14273a(C1185e eVar, C1191f fVar) {
        C1257f fVar2 = this.f2098f;
        return fVar2 != null && fVar2.mo14465a((Object) eVar);
    }

    /* renamed from: a */
    public boolean mo14274a(C1190e eVar, C1191f fVar) {
        C1257f fVar2 = this.f2098f;
        return fVar2 != null && fVar2.mo14464a(eVar);
    }

    /* renamed from: a */
    public boolean mo14703a(C1575h hVar) {
        return hVar != null && C1174c.m1310b().mo14317a(hVar, false, true);
    }

    /* renamed from: a */
    public boolean mo14704a(boolean z) {
        C1179b.m1354a("CoreLogic|start sdkSwitch isSlave = " + z);
        if (C1343f.f2169f != null) {
            new C1601d(C1343f.f2169f).mo15296a();
            C1343f.f2172i = true;
            if (!new C1599b(C1343f.f2169f).mo15292b()) {
                new C1600c(C1343f.f2169f).mo15293a();
                C1343f.f2173j = true;
                new C1599b(C1343f.f2169f).mo15291a();
            }
            if (z) {
                new C1600c(C1343f.f2169f).mo15293a();
                C1343f.f2173j = true;
            }
            m2032a().mo14710g().mo15192b();
        }
        return true;
    }

    /* renamed from: b */
    public Handler mo14705b() {
        return this.f2096d;
    }

    /* renamed from: c */
    public void mo14706c() {
        try {
            this.f2102j = new C1203b(this.f2093a);
            C1343f.m2077a(this.f2093a);
            C1232i.m1623a().mo14452b();
            m2034n();
            C1202a aVar = new C1202a();
            aVar.mo14353a(C1312h.m1937a());
            aVar.mo14353a(C1308d.m1924a());
            aVar.mo14353a(C1306b.m1906a());
            aVar.mo14353a(C1224a.m1601a());
            aVar.mo14353a(C1304ai.m1896a());
            this.f2099g.mo14317a(aVar, true, false);
            this.f2099g.mo14313a(this.f2093a);
            C1174c.m1310b().mo14265a(C1196a.m1437a(C1343f.f2109A.getBytes()));
            C1343f.f2139aa = this.f2099g.mo14317a(C1570c.m3170i(), false, true);
            C1343f.f2140ab = this.f2099g.mo14317a(C1574g.m3187i(), true, true);
            C1214i.m1500a().mo14383c();
            mo14707d();
            this.f2098f = C1257f.m1711a();
            this.f2101i.mo15192b();
            m2033m();
            C1199c.m1446c().mo14351d();
            C1343f.f2170g.set(true);
            C1238a.m1630a().mo14454a(Process.myPid());
            C1362c.m2166a().mo14812c();
            C1395a.m2261a().mo14845a(this.f2093a);
            Iterator<Message> it = this.f2097e.iterator();
            while (it.hasNext()) {
                Message next = it.next();
                if (this.f2095c != null) {
                    this.f2095c.sendMessage(next);
                }
            }
            C1355r.m2114a().mo14776e();
            try {
                AssistPushManager.getInstance().initialize(C1343f.f2169f);
                AssistPushManager.getInstance().register(C1343f.f2169f);
            } catch (Throwable unused) {
            }
        } catch (Throwable th) {
            C1179b.m1354a("CoreLogic|init|failed|" + th.toString());
        }
    }

    /* renamed from: d */
    public C1568a mo14707d() {
        C1568a i = C1568a.m3162i();
        C1198b bVar = new C1198b();
        i.mo15207a(bVar);
        i.mo15207a(new C1197a());
        i.mo15207a(new C1200d());
        i.mo15207a(C1199c.m1446c());
        try {
            bVar.mo14348a();
            bVar.mo14349a(System.currentTimeMillis());
        } catch (Throwable unused) {
        }
        C1343f.f2141ac = this.f2099g.mo14317a(i, false, true);
        return i;
    }

    /* renamed from: e */
    public boolean mo14708e() {
        if (C1343f.f2169f == null) {
            return true;
        }
        new C1600c(C1343f.f2169f).mo15294b();
        C1343f.f2173j = false;
        C1343f.f2176m = false;
        this.f2101i.mo15193c();
        return true;
    }

    /* renamed from: f */
    public C1173b mo14709f() {
        if (this.f2100h == null) {
            this.f2100h = C1367b.m2182a();
        }
        return this.f2100h;
    }

    /* renamed from: g */
    public C1560a mo14710g() {
        return this.f2101i;
    }

    /* renamed from: h */
    public C1257f mo14711h() {
        return this.f2098f;
    }

    /* renamed from: i */
    public C1203b mo14712i() {
        return this.f2102j;
    }

    /* renamed from: j */
    public String mo14713j() {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) C1343f.f2169f.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return UtilityImpl.NET_TYPE_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                return "mobile";
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: k */
    public boolean mo14275k() {
        return true;
    }

    /* renamed from: l */
    public long mo14276l() {
        return 94808;
    }
}
