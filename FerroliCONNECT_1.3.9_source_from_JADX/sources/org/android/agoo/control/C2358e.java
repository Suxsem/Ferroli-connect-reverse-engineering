package org.android.agoo.control;

/* renamed from: org.android.agoo.control.e */
/* compiled from: Taobao */
class C2358e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f4077a;

    /* renamed from: b */
    final /* synthetic */ String f4078b;

    /* renamed from: c */
    final /* synthetic */ AgooFactory f4079c;

    C2358e(AgooFactory agooFactory, String str, String str2) {
        this.f4079c = agooFactory;
        this.f4077a = str;
        this.f4078b = str2;
    }

    public void run() {
        this.f4079c.updateMsgStatus(this.f4077a, this.f4078b);
    }
}
