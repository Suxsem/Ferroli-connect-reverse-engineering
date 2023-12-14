package org.android.agoo.control;

import com.taobao.accs.base.TaoBaseService;

/* renamed from: org.android.agoo.control.b */
/* compiled from: Taobao */
class C2355b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ byte[] f4069a;

    /* renamed from: b */
    final /* synthetic */ String f4070b;

    /* renamed from: c */
    final /* synthetic */ TaoBaseService.ExtraInfo f4071c;

    /* renamed from: d */
    final /* synthetic */ AgooFactory f4072d;

    C2355b(AgooFactory agooFactory, byte[] bArr, String str, TaoBaseService.ExtraInfo extraInfo) {
        this.f4072d = agooFactory;
        this.f4069a = bArr;
        this.f4070b = str;
        this.f4071c = extraInfo;
    }

    public void run() {
        this.f4072d.msgReceiverPreHandler(this.f4069a, this.f4070b, this.f4071c, true);
    }
}
