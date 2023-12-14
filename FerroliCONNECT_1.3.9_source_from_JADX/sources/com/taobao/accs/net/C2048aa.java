package com.taobao.accs.net;

import com.taobao.accs.utl.UtilityImpl;
import org.android.spdy.AccsSSLCallback;

/* renamed from: com.taobao.accs.net.aa */
/* compiled from: Taobao */
class C2048aa implements AccsSSLCallback {

    /* renamed from: a */
    final /* synthetic */ C2071w f3372a;

    C2048aa(C2071w wVar) {
        this.f3372a = wVar;
    }

    public byte[] getSSLPublicKey(int i, byte[] bArr) {
        return UtilityImpl.m3746a();
    }
}
