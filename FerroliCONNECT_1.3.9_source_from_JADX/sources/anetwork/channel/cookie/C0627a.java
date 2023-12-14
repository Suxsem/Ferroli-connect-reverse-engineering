package anetwork.channel.cookie;

import android.text.TextUtils;
import anet.channel.util.ALog;
import anetwork.channel.cookie.CookieManager;

/* renamed from: anetwork.channel.cookie.a */
/* compiled from: Taobao */
final class C0627a implements Runnable {
    C0627a() {
    }

    public void run() {
        try {
            if (!TextUtils.isEmpty(CookieManager.m397f())) {
                CookieManager.C0626a unused = CookieManager.f672d = new CookieManager.C0626a(CookieManager.m397f());
            }
        } catch (Exception e) {
            ALog.m326e(CookieManager.TAG, "", (String) null, e, new Object[0]);
        }
    }
}
