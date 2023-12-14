package anet.channel.strategy.dispatch;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* renamed from: anet.channel.strategy.dispatch.c */
/* compiled from: Taobao */
final class C0573c implements HostnameVerifier {
    C0573c() {
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(DispatchConstants.getAmdcServerDomain(), sSLSession);
    }
}
