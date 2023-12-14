package com.taobao.accs.net;

/* renamed from: com.taobao.accs.net.z */
/* compiled from: Taobao */
class C2075z implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f3483a;

    /* renamed from: b */
    final /* synthetic */ C2071w f3484b;

    C2075z(C2071w wVar, String str) {
        this.f3484b = wVar;
        this.f3483a = str;
    }

    public void run() {
        String str = this.f3483a;
        if (str != null && str.equals(this.f3484b.f3461N) && this.f3484b.f3467s == 2) {
            boolean unused = this.f3484b.f3457J = false;
            boolean unused2 = this.f3484b.f3459L = true;
            this.f3484b.mo18526q();
            this.f3484b.f3455H.setCloseReason("conn timeout");
        }
    }
}
