package com.p092ta.p093a.p095b;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* renamed from: com.ta.a.b.d */
class C1970d implements HostnameVerifier {

    /* renamed from: b */
    public String f3155b;

    public C1970d(String str) {
        this.f3155b = str;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f3155b, sSLSession);
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f3155b) || !(obj instanceof C1970d)) {
            return false;
        }
        String str = ((C1970d) obj).f3155b;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f3155b.equals(str);
    }
}
