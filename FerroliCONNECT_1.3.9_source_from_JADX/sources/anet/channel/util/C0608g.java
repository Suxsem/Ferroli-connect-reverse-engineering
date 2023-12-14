package anet.channel.util;

import android.util.Base64;
import java.net.InetSocketAddress;
import java.net.Proxy;

/* renamed from: anet.channel.util.g */
/* compiled from: Taobao */
public class C0608g {

    /* renamed from: a */
    public static C0608g f589a;

    /* renamed from: b */
    private final Proxy f590b;

    /* renamed from: c */
    private final String f591c;

    /* renamed from: d */
    private final String f592d;

    /* renamed from: a */
    public static C0608g m360a() {
        return f589a;
    }

    /* renamed from: b */
    public Proxy mo9108b() {
        return this.f590b;
    }

    public C0608g(String str, int i, String str2, String str3) {
        this.f590b = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        this.f591c = str2;
        this.f592d = str3;
    }

    /* renamed from: c */
    public String mo9109c() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.f591c);
        sb.append(":");
        sb.append(this.f592d);
        String encodeToString = Base64.encodeToString(sb.toString().getBytes(), 0);
        StringBuilder sb2 = new StringBuilder(64);
        sb2.append("Basic ");
        sb2.append(encodeToString);
        return sb2.toString();
    }
}
