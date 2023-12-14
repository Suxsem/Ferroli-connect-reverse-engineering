package com.igexin.push.extension.distribution.gbd.p069a.p070a;

import android.content.pm.PackageInfo;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.a.a */
public class C1448a {

    /* renamed from: a */
    private static C1448a f2495a;

    /* renamed from: b */
    private List<PackageInfo> f2496b = new ArrayList();

    private C1448a() {
    }

    /* renamed from: a */
    public static C1448a m2541a() {
        if (f2495a == null) {
            f2495a = new C1448a();
        }
        return f2495a;
    }

    /* renamed from: a */
    private String m2542a(List<PackageInfo> list, int i) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i()));
        StringBuilder sb = new StringBuilder();
        sb.append(format);
        sb.append("|");
        sb.append(C1343f.f2182s);
        sb.append("|");
        sb.append(C1343f.f2135a);
        sb.append("|");
        for (PackageInfo next : list) {
            try {
                sb.append(next.packageName);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(C1541i.m3021d(next.packageName, C1490c.f2747a));
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(next.versionName);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(next.versionCode);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(next.firstInstallTime);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(C1541i.m3001a(next));
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(next.lastUpdateTime);
                sb.append(",");
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        if (sb.toString().endsWith(",")) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("|");
        sb.append(i);
        sb.append("|");
        sb.append("ANDROID");
        return sb.toString();
    }

    /* renamed from: a */
    private void m2543a(String str) {
        List<PackageInfo> list = this.f2496b;
        if (list != null) {
            list.clear();
        }
        C1503b.m2819a().mo15109a(str, mo14996c());
        C1540h.m2995a("GBD_RALA", "applist data: type = " + mo14996c() + " content = " + str);
    }

    /* renamed from: b */
    public void mo14995b() {
        try {
            C1540h.m2997b("GBD_RALA", "doSample");
            if (this.f2496b != null) {
                this.f2496b.clear();
            }
            for (PackageInfo next : C1343f.f2169f.getPackageManager().getInstalledPackages(0)) {
                if ((next.applicationInfo.flags & 1) == 0) {
                    this.f2496b.add(next);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_RALA", th.getMessage());
            return;
        }
        C1540h.m2997b("GBD_RALA", "thirdy applist size = " + this.f2496b.size());
        m2543a(m2542a(this.f2496b, 0));
    }

    /* renamed from: c */
    public int mo14996c() {
        return 28;
    }
}
