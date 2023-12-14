package anetwork.channel.cookie;

import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.CookieMonitorStat;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import java.net.HttpCookie;

/* renamed from: anetwork.channel.cookie.c */
/* compiled from: Taobao */
final class C0629c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f680a;

    /* renamed from: b */
    final /* synthetic */ String f681b;

    C0629c(String str, String str2) {
        this.f680a = str;
        this.f681b = str2;
    }

    public void run() {
        if (CookieManager.f672d != null) {
            try {
                if (!TextUtils.isEmpty(CookieManager.f672d.f674a) && HttpCookie.domainMatches(CookieManager.f672d.f677d, HttpUrl.parse(this.f680a).host()) && !TextUtils.isEmpty(this.f681b)) {
                    String str = this.f681b;
                    if (!str.contains(CookieManager.f672d.f674a + "=")) {
                        CookieMonitorStat cookieMonitorStat = new CookieMonitorStat(this.f680a);
                        cookieMonitorStat.cookieName = CookieManager.f672d.f674a;
                        cookieMonitorStat.cookieText = CookieManager.f672d.f675b;
                        cookieMonitorStat.setCookie = CookieManager.f672d.f676c;
                        cookieMonitorStat.missType = 1;
                        AppMonitor.getInstance().commitStat(cookieMonitorStat);
                    }
                }
            } catch (Exception e) {
                ALog.m326e(CookieManager.TAG, "cookieMonitorReport error.", (String) null, e, new Object[0]);
            }
        }
    }
}
