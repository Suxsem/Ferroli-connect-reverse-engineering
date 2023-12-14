package com.igexin.sdk;

import android.app.job.JobParameters;
import android.content.Intent;
import android.util.Log;
import com.igexin.push.util.C1588m;
import com.igexin.sdk.GTIntentService;
import java.lang.reflect.Method;

/* renamed from: com.igexin.sdk.b */
class C1602b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ JobParameters f3066a;

    /* renamed from: b */
    final /* synthetic */ GTIntentService.JobIntentService f3067b;

    C1602b(GTIntentService.JobIntentService jobIntentService, JobParameters jobParameters) {
        this.f3067b = jobIntentService;
        this.f3066a = jobParameters;
    }

    public void run() {
        try {
            Class<?> cls = Class.forName("android.app.job.JobWorkItem");
            Method a = C1588m.m3254a((Class<?>) JobParameters.class, "dequeueWork", (Class<?>[]) new Class[0]);
            Method a2 = C1588m.m3254a(cls, "getIntent", (Class<?>[]) new Class[0]);
            Method a3 = C1588m.m3254a((Class<?>) JobParameters.class, "completeWork", (Class<?>[]) new Class[]{cls});
            while (true) {
                Object invoke = a.invoke(this.f3066a, new Object[0]);
                if (invoke != null) {
                    a3.invoke(this.f3066a, new Object[]{invoke});
                    GTIntentService.this.onHandleIntent((Intent) a2.invoke(invoke, new Object[0]));
                } else {
                    return;
                }
            }
        } catch (Throwable th) {
            Log.e(GTIntentService.TAG, "onStartJobIntentService err: " + th.toString());
        }
    }
}
