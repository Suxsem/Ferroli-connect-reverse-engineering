package anet.channel.session;

import anet.channel.security.ISecurity;
import anet.channel.util.ALog;
import org.android.spdy.AccsSSLCallback;
import org.android.spdy.SpdyProtocol;

/* renamed from: anet.channel.session.j */
/* compiled from: Taobao */
class C0558j implements AccsSSLCallback {

    /* renamed from: a */
    final /* synthetic */ TnetSpdySession f403a;

    C0558j(TnetSpdySession tnetSpdySession) {
        this.f403a = tnetSpdySession;
    }

    public byte[] getSSLPublicKey(int i, byte[] bArr) {
        byte[] bArr2;
        try {
            bArr2 = this.f403a.f375G.decrypt(this.f403a.f89a, ISecurity.CIPHER_ALGORITHM_AES128, SpdyProtocol.TNET_PUBKEY_SG_KEY, bArr);
            if (bArr2 != null) {
                try {
                    if (ALog.isPrintLog(2)) {
                        ALog.m328i("getSSLPublicKey", (String) null, "decrypt", new String(bArr2));
                    }
                } catch (Throwable th) {
                    th = th;
                    ALog.m326e("awcn.TnetSpdySession", "getSSLPublicKey", (String) null, th, new Object[0]);
                    return bArr2;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            bArr2 = null;
            ALog.m326e("awcn.TnetSpdySession", "getSSLPublicKey", (String) null, th, new Object[0]);
            return bArr2;
        }
        return bArr2;
    }
}
