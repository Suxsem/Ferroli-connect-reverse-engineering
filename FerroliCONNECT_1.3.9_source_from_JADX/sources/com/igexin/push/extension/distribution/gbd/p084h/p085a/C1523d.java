package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p069a.p072c.C1463c;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.szacs.ferroliconnect.util.ACache;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.eclipse.jetty.util.security.Constraint;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.d */
public class C1523d extends C1532b {

    /* renamed from: c */
    private static C1523d f2925c;

    private C1523d() {
        this.f2934a = C1490c.f2771y;
        this.f2935b = m2939e();
    }

    /* renamed from: a */
    private void m2936a(int i, int i2) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        if (C1490c.f2749c != null) {
            C1490c.f2749c.sendMessage(obtain);
        }
    }

    /* renamed from: a */
    private boolean m2937a(String str) {
        String[] split = str.split(" ");
        String[] split2 = new SimpleDateFormat("mm HH dd MM yy", Locale.getDefault()).format(new Date()).split(" ");
        for (int i = 0; i < split2.length; i++) {
            if (!split[i].equals(Constraint.ANY_ROLE)) {
                if (split[i].startsWith("*/")) {
                    int intValue = Integer.valueOf(split[i].substring(2)).intValue();
                    if (intValue > 0 && Integer.valueOf(split2[i]).intValue() % intValue != 0) {
                        return false;
                    }
                } else if (!split2[i].equals(split[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /* renamed from: d */
    public static C1523d m2938d() {
        if (f2925c == null) {
            f2925c = new C1523d();
        }
        return f2925c;
    }

    /* renamed from: e */
    private long m2939e() {
        Date date = new Date(new Date().getTime() - C1490c.f2754h);
        int intValue = Integer.valueOf(new SimpleDateFormat("mm", Locale.getDefault()).format(date)).intValue();
        int intValue2 = Integer.valueOf(new SimpleDateFormat("ss", Locale.getDefault()).format(date)).intValue();
        int i = (3600 - (((intValue * 60) + intValue2) % ACache.TIME_HOUR)) * 1000;
        C1540h.m2997b("GBD_CT", "calcDelay nowMinute:" + intValue + "|nowSecond:" + intValue2 + "|delaySeconds:" + i);
        return (long) i;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_CT", "doTask...");
        if (m2937a(C1463c.m2637a().mo15018c())) {
            m2936a(1, 12);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        this.f2935b = m2939e();
        C1507f.m2840a().mo15122c(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }
}
