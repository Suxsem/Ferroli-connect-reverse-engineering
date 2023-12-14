package com.igexin.sdk;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1356s;
import com.igexin.push.util.C1588m;
import java.lang.reflect.Method;

public class PushService extends Service {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static String f3055a = "PushService";

    /* renamed from: b */
    private Service f3056b;

    public class GTJobService extends JobService {

        /* renamed from: b */
        private Service f3058b;

        public GTJobService(Service service) {
            this.f3058b = service;
            try {
                C1588m.m3254a(getClass(), "attachBaseContext", (Class<?>[]) new Class[]{Context.class}).invoke(this, new Object[]{service});
            } catch (Throwable th) {
                String a = PushService.f3055a;
                Log.e(a, "GTJobService init err: " + th.toString());
            }
        }

        public boolean onStartJob(JobParameters jobParameters) {
            try {
                Class<?> cls = Class.forName("android.app.job.JobWorkItem");
                Method a = C1588m.m3254a((Class<?>) JobParameters.class, "dequeueWork", (Class<?>[]) new Class[0]);
                Method a2 = C1588m.m3254a(cls, "getIntent", (Class<?>[]) new Class[0]);
                Method a3 = C1588m.m3254a((Class<?>) JobParameters.class, "completeWork", (Class<?>[]) new Class[]{cls});
                while (true) {
                    Object invoke = a.invoke(jobParameters, new Object[0]);
                    if (invoke == null) {
                        break;
                    }
                    C1356s.m2138a().mo14778a(this.f3058b, (Intent) a2.invoke(invoke, new Object[0]), 0, 0);
                    a3.invoke(jobParameters, new Object[]{invoke});
                }
            } catch (Throwable th) {
                String a4 = PushService.f3055a;
                Log.e(a4, "onStartJobService err: " + th.toString());
            }
            return false;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    /* renamed from: a */
    private void m3290a(Intent intent) {
        Class c = C1356s.m2138a().mo14786c(this);
        if (intent != null) {
            intent.setClass(getApplicationContext(), c);
        } else {
            intent = new Intent(getApplicationContext(), c);
        }
        getApplicationContext().startService(intent);
    }

    /* renamed from: b */
    private boolean m3291b() {
        return getClass() == PushService.class && C1356s.m2138a().mo14784b((Context) this);
    }

    public IBinder onBind(Intent intent) {
        String type;
        if (intent == null || (type = intent.getType()) == null || !type.startsWith("GB-")) {
            Service service = this.f3056b;
            return service == null ? C1356s.m2138a().mo14779a(intent) : service.onBind(intent);
        }
        onStartCommand(intent, 0, 0);
        return null;
    }

    public void onCreate() {
        super.onCreate();
        C1356s.m2138a().mo14781a((Context) this);
        if (Build.VERSION.SDK_INT >= 26) {
            this.f3056b = new GTJobService(this);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (!m3291b()) {
            C1356s.m2138a().mo14787c();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        C1356s.m2138a().mo14783b();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        try {
            if (m3291b()) {
                C1179b.m1355a(f3055a, "isUserPushServiceSet = true, call -> startUserPushService");
                m3290a(intent);
                stopSelf();
                return 1;
            }
        } catch (Throwable unused) {
        }
        return C1356s.m2138a().mo14778a(this, intent, i, i2);
    }
}
