package com.taobao.accs.utl;

import anet.channel.util.ALog;

/* renamed from: com.taobao.accs.utl.k */
/* compiled from: Taobao */
public class C2097k implements ALog.ILog {

    /* renamed from: a */
    private ALog.ILog f3565a;

    /* renamed from: b */
    private C2098a f3566b;

    /* renamed from: com.taobao.accs.utl.k$a */
    /* compiled from: Taobao */
    interface C2098a {
        /* renamed from: a */
        void mo18603a(String str);
    }

    public boolean isPrintLog(int i) {
        return true;
    }

    public boolean isValid() {
        return true;
    }

    public void setLogLevel(int i) {
    }

    public C2097k(ALog.ILog iLog, C2098a aVar) {
        this.f3565a = iLog;
        this.f3566b = aVar;
    }

    /* renamed from: d */
    public void mo8758d(String str, String str2) {
        this.f3565a.mo8758d(str, str2);
        this.f3566b.mo18603a(str2);
    }

    /* renamed from: i */
    public void mo8761i(String str, String str2) {
        this.f3565a.mo8761i(str, str2);
        this.f3566b.mo18603a(str2);
    }

    /* renamed from: w */
    public void mo8765w(String str, String str2) {
        this.f3565a.mo8765w(str, str2);
        this.f3566b.mo18603a(str2);
    }

    /* renamed from: w */
    public void mo8766w(String str, String str2, Throwable th) {
        this.f3565a.mo8766w(str, str2, th);
        C2098a aVar = this.f3566b;
        aVar.mo18603a(str2 + " " + th.getMessage());
    }

    /* renamed from: e */
    public void mo8759e(String str, String str2) {
        this.f3565a.mo8759e(str, str2);
        this.f3566b.mo18603a(str2);
    }

    /* renamed from: e */
    public void mo8760e(String str, String str2, Throwable th) {
        this.f3565a.mo8760e(str, str2, th);
        C2098a aVar = this.f3566b;
        aVar.mo18603a(str2 + " " + th.getMessage());
    }
}
