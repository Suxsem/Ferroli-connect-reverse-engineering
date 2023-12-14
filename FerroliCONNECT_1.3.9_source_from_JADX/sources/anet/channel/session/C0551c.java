package anet.channel.session;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* renamed from: anet.channel.session.c */
/* compiled from: Taobao */
final class C0551c implements HostnameVerifier {

    /* renamed from: a */
    final /* synthetic */ String f392a;

    C0551c(String str) {
        this.f392a = str;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f392a, sSLSession);
    }
}
