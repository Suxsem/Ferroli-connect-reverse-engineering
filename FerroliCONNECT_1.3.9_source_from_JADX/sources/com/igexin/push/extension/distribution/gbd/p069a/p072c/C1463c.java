package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.NeighboringCellInfo;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p076b.C1483c;
import com.igexin.push.extension.distribution.gbd.p076b.C1485e;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p078d.C1494d;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.c */
public class C1463c {

    /* renamed from: a */
    protected static int f2533a = 1;

    /* renamed from: b */
    protected static int f2534b = 0;

    /* renamed from: c */
    protected static int f2535c = -1;

    /* renamed from: d */
    protected static int f2536d = -2;

    /* renamed from: e */
    protected static int f2537e = -3;

    /* renamed from: f */
    protected static int f2538f = -4;

    /* renamed from: g */
    protected static int f2539g = -5;

    /* renamed from: h */
    protected static int f2540h = -6;

    /* renamed from: i */
    protected static int f2541i = -7;

    /* renamed from: j */
    protected static int f2542j = -8;

    /* renamed from: k */
    protected static int f2543k = -9;

    /* renamed from: l */
    private static C1463c f2544l;

    /* renamed from: m */
    private Context f2545m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public final Object f2546n = new Object();

    /* renamed from: o */
    private C1461a f2547o;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public C1466f f2548p;

    /* renamed from: q */
    private long f2549q;

    /* renamed from: r */
    private float f2550r;

    private C1463c(Context context) {
        try {
            this.f2545m = context;
            this.f2547o = new C1461a(context);
            this.f2547o.mo15012a(this);
            this.f2548p = new C1466f(context);
            this.f2548p.mo15021a(this);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public static C1463c m2637a() {
        if (f2544l == null) {
            f2544l = new C1463c(C1490c.f2747a);
        }
        return f2544l;
    }

    /* renamed from: a */
    private String m2639a(ScanResult scanResult) {
        if (scanResult == null) {
            return "";
        }
        String replace = scanResult.SSID.replace("|", "").replace(MqttTopic.MULTI_LEVEL_WILDCARD, "").replace(",", "");
        long j = 0;
        if (Build.VERSION.SDK_INT >= 17) {
            j = C1541i.m3034i() - (((SystemClock.elapsedRealtimeNanos() / 1000) / 1000) - (scanResult.timestamp / 1000));
        }
        return replace + MqttTopic.MULTI_LEVEL_WILDCARD + scanResult.BSSID + MqttTopic.MULTI_LEVEL_WILDCARD + scanResult.level + MqttTopic.MULTI_LEVEL_WILDCARD + scanResult.capabilities + MqttTopic.MULTI_LEVEL_WILDCARD + j;
    }

    /* renamed from: a */
    private String m2640a(C1483c cVar) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (cVar == null) {
            sb.append(0);
            sb.append("|");
            sb.append(0);
            sb.append("|");
            sb.append(0);
            sb.append("|");
            sb.append(0);
            sb.append("|");
        } else {
            sb.append(cVar.mo15044a());
            sb.append("|");
            sb.append(cVar.mo15047b());
            sb.append("|");
            sb.append(cVar.mo15049c());
            sb.append("|");
            sb.append(cVar.mo15051d());
            sb.append("|");
            List<NeighboringCellInfo> e = cVar.mo15053e();
            while (e != null && i < e.size()) {
                sb.append(e.get(i).getCid());
                if (i < e.size() - 1) {
                    sb.append(",");
                }
                i++;
            }
        }
        sb.append("|");
        return sb.toString();
    }

    /* renamed from: a */
    private String m2641a(List<C1485e> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                C1485e eVar = list.get(i);
                sb.append(eVar.mo15060a());
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(eVar.mo15061b());
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(eVar.mo15062c());
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(eVar.mo15064e());
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(eVar.mo15063d());
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("|");
        return sb.toString();
    }

    /* renamed from: a */
    private String m2642a(List<ScanResult> list, Location location, C1483c cVar, List<C1485e> list2, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i())));
        sb.append("|");
        sb.append(C1343f.f2182s);
        sb.append("|");
        sb.append(C1343f.f2135a);
        sb.append("|");
        String b = m2645b(location);
        String a = m2640a(cVar);
        String b2 = m2646b(list);
        String a2 = m2641a(list2);
        sb.append(b);
        sb.append(a);
        sb.append(b2);
        sb.append(a2);
        sb.append(i);
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append(this.f2549q);
        sb.append("|");
        sb.append(this.f2550r);
        sb.append("|");
        sb.append("|");
        sb.append("ANDROID");
        return sb.toString();
    }

    /* renamed from: a */
    private void m2643a(String str) {
        C1503b.m2819a().mo15111b(str, mo15017b());
        C1540h.m2997b("GBD_RLA", "saveRALData: type = " + mo15017b());
    }

    /* renamed from: b */
    private String m2645b(Location location) {
        long j;
        long j2;
        float accuracy;
        StringBuilder sb = new StringBuilder();
        if (location == null) {
            sb.append(SchedulerSupport.NONE);
            sb.append("|");
            sb.append("0");
            sb.append("|");
            sb.append("0");
            sb.append("|");
            sb.append("0");
            sb.append("|");
            this.f2549q = 0;
            accuracy = 0.0f;
        } else {
            sb.append(location.getProvider());
            sb.append("|");
            sb.append(location.getLongitude());
            sb.append("|");
            sb.append(location.getLatitude());
            sb.append("|");
            sb.append(location.getAltitude());
            sb.append("|");
            if (Build.VERSION.SDK_INT >= 17) {
                j2 = ((location.getElapsedRealtimeNanos() - SystemClock.elapsedRealtimeNanos()) / 1000) / 1000;
                j = C1541i.m3034i();
            } else {
                j2 = location.getTime();
                j = C1490c.f2732I;
            }
            this.f2549q = j2 + j;
            accuracy = location.getAccuracy();
        }
        this.f2550r = accuracy;
        return sb.toString();
    }

    /* renamed from: b */
    private String m2646b(List<ScanResult> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(m2639a(list.get(i)));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("|");
        return sb.toString();
    }

    /* renamed from: d */
    private boolean m2647d() {
        return C1541i.m3006a(this.f2545m, "android.permission.ACCESS_FINE_LOCATION") && C1541i.m3006a(this.f2545m, "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* renamed from: a */
    public void mo15014a(int i) {
        try {
            if (!C1488a.f2701g) {
                C1540h.m2997b("GBD_RLA", "doSample 11 not enable.");
                return;
            }
            boolean e = C1494d.m2778a().mo15091e();
            C1540h.m2997b("GBD_RLA", "doSample checkSafeStatus = " + e);
            if (e) {
                new C1464d(this, i).start();
                return;
            }
            this.f2548p.f2557a = C1465e.SCAN_END;
        } catch (Exception e2) {
            C1540h.m2996a(e2);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo15015a(Location location) {
        List<ScanResult> scanResults = (!C1490c.f2752f || !m2647d()) ? null : C1490c.f2750d.getScanResults();
        C1483c a = this.f2547o.mo15011a();
        C1540h.m2997b("GBD_RLA", "gps location data.");
        String a2 = m2642a(scanResults, location, a, (List<C1485e>) null, f2533a);
        if (a2 != null) {
            m2643a(a2);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo15016a(List<ScanResult> list, int i) {
        String a;
        if (C1343f.f2182s != null) {
            Location a2 = this.f2547o.mo15010a(false);
            C1483c a3 = this.f2547o.mo15011a();
            if ((a2 != null || (!(a3 == null || a3.mo15051d() == 0) || (list != null && !list.isEmpty()))) && (a = m2642a(list, a2, a3, (List<C1485e>) null, i)) != null) {
                m2643a(a);
            }
        }
    }

    /* renamed from: b */
    public int mo15017b() {
        return 11;
    }

    /* renamed from: c */
    public String mo15018c() {
        return "* * * * *";
    }
}
