package com.alibaba.sdk.android.emas;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.alibaba.sdk.android.emas.i */
/* compiled from: ForBackgroundCallback */
class C0714i implements Application.ActivityLifecycleCallbacks {

    /* renamed from: c */
    private List<C0715a> f912c;

    /* renamed from: c */
    private boolean f913c = false;

    /* renamed from: e */
    private int f914e = 0;

    /* renamed from: com.alibaba.sdk.android.emas.i$a */
    /* compiled from: ForBackgroundCallback */
    public interface C0715a {
        /* renamed from: c */
        void mo9606c();

        /* renamed from: d */
        void mo9607d();
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    C0714i() {
    }

    public void onActivityStarted(Activity activity) {
        this.f914e++;
        if (!this.f913c) {
            this.f913c = true;
            List<C0715a> list = this.f912c;
            if (list != null) {
                for (C0715a c : list) {
                    c.mo9606c();
                }
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        this.f914e--;
        if (this.f914e == 0) {
            this.f913c = false;
            List<C0715a> list = this.f912c;
            if (list != null) {
                for (C0715a d : list) {
                    d.mo9607d();
                }
            }
        }
    }

    /* renamed from: a */
    public void mo9647a(C0715a aVar) {
        if (this.f912c == null) {
            this.f912c = new ArrayList();
        }
        this.f912c.add(aVar);
    }
}
