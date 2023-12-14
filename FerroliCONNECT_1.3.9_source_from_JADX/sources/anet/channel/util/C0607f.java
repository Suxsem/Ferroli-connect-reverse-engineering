package anet.channel.util;

import java.net.Inet6Address;

/* renamed from: anet.channel.util.f */
/* compiled from: Taobao */
public class C0607f {

    /* renamed from: a */
    public int f587a;

    /* renamed from: b */
    public Inet6Address f588b;

    public C0607f(Inet6Address inet6Address, int i) {
        this.f587a = i;
        this.f588b = inet6Address;
    }

    public String toString() {
        return this.f588b.getHostAddress() + "/" + this.f587a;
    }
}
