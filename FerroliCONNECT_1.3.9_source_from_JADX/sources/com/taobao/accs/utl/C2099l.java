package com.taobao.accs.utl;

import anet.channel.util.ALog;
import com.alibaba.sdk.android.logger.ILog;

/* renamed from: com.taobao.accs.utl.l */
/* compiled from: Taobao */
public class C2099l implements ALog.ILog {

    /* renamed from: a */
    private ILog f3567a = AccsLogger.getLogger("NetworkSdk");

    public boolean isPrintLog(int i) {
        return true;
    }

    public boolean isValid() {
        return true;
    }

    public void setLogLevel(int i) {
    }

    /* renamed from: d */
    public void mo8758d(String str, String str2) {
        this.f3567a.mo9706d(m3803a(str, str2));
    }

    /* renamed from: i */
    public void mo8761i(String str, String str2) {
        this.f3567a.mo9711i(m3803a(str, str2));
    }

    /* renamed from: w */
    public void mo8765w(String str, String str2) {
        this.f3567a.mo9713w(m3803a(str, str2));
    }

    /* renamed from: w */
    public void mo8766w(String str, String str2, Throwable th) {
        this.f3567a.mo9714w(m3803a(str, str2), th);
    }

    /* renamed from: e */
    public void mo8759e(String str, String str2) {
        this.f3567a.mo9708e(m3803a(str, str2));
    }

    /* renamed from: e */
    public void mo8760e(String str, String str2, Throwable th) {
        this.f3567a.mo9709e(m3803a(str, str2), th);
    }

    /* renamed from: a */
    private String m3803a(String str, String str2) {
        return "[" + str + "]" + str2;
    }
}
