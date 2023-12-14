package com.igexin.push.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.util.C1581f;
import com.igexin.sdk.PushConsts;

/* renamed from: com.igexin.push.core.i */
public class C1346i implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a */
    private long f2190a;

    /* renamed from: b */
    private int f2191b;

    /* renamed from: a */
    private void m2084a(Activity activity) {
        try {
            C1179b.m1354a("GALC|" + activity.getComponentName().getClassName() + " onAStart " + this.f2191b);
            if (this.f2191b == 0) {
                C1179b.m1354a("GALC|>>>>>> FG");
                Context applicationContext = activity.getApplicationContext();
                if (!C1581f.m3233a(applicationContext) && System.currentTimeMillis() - this.f2190a > 60000) {
                    Intent intent = new Intent(applicationContext, C1257f.m1711a().mo14471a(applicationContext));
                    intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_SERVICE_ONRESUME);
                    C1356s.m2138a().mo14782a(applicationContext, intent);
                    C1179b.m1354a("GALC|on fg, start>>>>>>");
                    this.f2190a = System.currentTimeMillis();
                }
            }
        } catch (Throwable unused) {
        }
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

    public void onActivityStarted(Activity activity) {
        if (activity != null) {
            m2084a(activity);
            this.f2191b++;
        }
    }

    public void onActivityStopped(Activity activity) {
        if (activity != null) {
            this.f2191b--;
            int i = this.f2191b;
            if (i < 0) {
                i = 0;
            }
            this.f2191b = i;
            C1179b.m1354a("GALC|" + activity.getComponentName().getClassName() + " onAStopp " + this.f2191b);
            int i2 = this.f2191b;
        }
    }
}
