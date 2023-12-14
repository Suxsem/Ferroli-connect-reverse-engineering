package com.taobao.accs.net;

import android.text.TextUtils;

/* renamed from: com.taobao.accs.net.q */
/* compiled from: Taobao */
class C2065q implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2057j f3442a;

    C2065q(C2057j jVar) {
        this.f3442a = jVar;
    }

    public void run() {
        try {
            if (this.f3442a.f3376d != null && !TextUtils.isEmpty(this.f3442a.mo18490i())) {
                this.f3442a.f3414t.mo9711i("mTryStartServiceRunable bindapp");
                this.f3442a.mo18481b(this.f3442a.f3376d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
