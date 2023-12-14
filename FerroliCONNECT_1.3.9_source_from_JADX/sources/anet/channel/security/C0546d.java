package anet.channel.security;

/* renamed from: anet.channel.security.d */
/* compiled from: Taobao */
final class C0546d implements ISecurityFactory {
    C0546d() {
    }

    public ISecurity createSecurity(String str) {
        return new C0544b(str);
    }

    public ISecurity createNonSecurity(String str) {
        return new C0543a(str);
    }
}
