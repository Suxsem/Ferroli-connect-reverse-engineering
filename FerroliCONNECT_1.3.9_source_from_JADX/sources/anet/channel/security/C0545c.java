package anet.channel.security;

/* renamed from: anet.channel.security.c */
/* compiled from: Taobao */
public class C0545c {

    /* renamed from: a */
    private static volatile ISecurityFactory f368a;

    /* renamed from: a */
    public static ISecurityFactory m193a() {
        if (f368a == null) {
            f368a = new C0546d();
        }
        return f368a;
    }
}
