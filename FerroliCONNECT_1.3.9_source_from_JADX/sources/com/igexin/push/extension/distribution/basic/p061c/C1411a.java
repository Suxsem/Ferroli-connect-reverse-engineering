package com.igexin.push.extension.distribution.basic.p061c;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.igexin.p032b.p033a.p039c.C1179b;

/* renamed from: com.igexin.push.extension.distribution.basic.c.a */
public class C1411a {

    /* renamed from: a */
    private static C1411a f2412a;

    private C1411a() {
    }

    /* renamed from: a */
    public static synchronized C1411a m2404a() {
        C1411a aVar;
        synchronized (C1411a.class) {
            if (f2412a == null) {
                f2412a = new C1411a();
            }
            aVar = f2412a;
        }
        return aVar;
    }

    /* renamed from: a */
    public boolean mo14939a(Context context, Intent intent, int i) {
        C1179b.m1354a("JobSender| JobSender.runJob()");
        if (!(Build.VERSION.SDK_INT < 26 || context == null || intent == null || intent.getComponent() == null)) {
            try {
                JobInfo build = new JobInfo.Builder(i, intent.getComponent()).setOverrideDeadline(0).build();
                Class<?> cls = Class.forName("android.app.job.JobWorkItem");
                Object newInstance = cls.getConstructor(new Class[]{Intent.class}).newInstance(new Object[]{intent});
                JobScheduler.class.getDeclaredMethod("enqueue", new Class[]{JobInfo.class, cls}).invoke((JobScheduler) context.getSystemService("jobscheduler"), new Object[]{build, newInstance});
                C1179b.m1354a("JobSender| started by JobSender");
                return true;
            } catch (Throwable th) {
                C1179b.m1354a("JobSender|" + th.toString());
            }
        }
        return false;
    }
}
