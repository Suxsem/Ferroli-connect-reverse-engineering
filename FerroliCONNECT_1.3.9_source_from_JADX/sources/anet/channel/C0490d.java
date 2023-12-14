package anet.channel;

import anet.channel.security.ISecurity;
import anet.channel.strategy.dispatch.IAmdcSign;

/* renamed from: anet.channel.d */
/* compiled from: Taobao */
class C0490d implements IAmdcSign {

    /* renamed from: a */
    final /* synthetic */ String f176a;

    /* renamed from: b */
    final /* synthetic */ ISecurity f177b;

    /* renamed from: c */
    final /* synthetic */ SessionCenter f178c;

    C0490d(SessionCenter sessionCenter, String str, ISecurity iSecurity) {
        this.f178c = sessionCenter;
        this.f176a = str;
        this.f177b = iSecurity;
    }

    public String getAppkey() {
        return this.f176a;
    }

    public String sign(String str) {
        return this.f177b.sign(this.f178c.f117b, ISecurity.SIGN_ALGORITHM_HMAC_SHA1, getAppkey(), str);
    }

    public boolean useSecurityGuard() {
        return !this.f177b.isSecOff();
    }
}
