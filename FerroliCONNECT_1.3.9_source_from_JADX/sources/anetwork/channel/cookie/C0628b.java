package anetwork.channel.cookie;

import anet.channel.util.ALog;
import java.net.HttpCookie;

/* renamed from: anetwork.channel.cookie.b */
/* compiled from: Taobao */
final class C0628b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f679a;

    C0628b(String str) {
        this.f679a = str;
    }

    public void run() {
        if (CookieManager.f672d != null) {
            try {
                for (HttpCookie next : HttpCookie.parse(this.f679a)) {
                    if (next.getName().equals(CookieManager.f672d.f674a)) {
                        CookieManager.f672d.f675b = next.toString();
                        CookieManager.f672d.f677d = next.getDomain();
                        CookieManager.f672d.f676c = this.f679a;
                        CookieManager.f672d.mo9302a();
                        return;
                    }
                }
            } catch (Exception e) {
                ALog.m326e(CookieManager.TAG, "cookieMonitorSave error.", (String) null, e, new Object[0]);
            }
        }
    }
}
