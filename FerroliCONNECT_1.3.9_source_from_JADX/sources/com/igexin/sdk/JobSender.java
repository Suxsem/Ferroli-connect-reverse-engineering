package com.igexin.sdk;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.igexin.p032b.p033a.p039c.C1179b;

public class JobSender {

    /* renamed from: a */
    private static JobSender f3043a;

    private JobSender() {
    }

    public static synchronized JobSender getInstance() {
        JobSender jobSender;
        synchronized (JobSender.class) {
            if (f3043a == null) {
                f3043a = new JobSender();
            }
            jobSender = f3043a;
        }
        return jobSender;
    }

    public boolean runJob(Context context, Intent intent, int i) {
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

    public int size(Context context) {
        try {
            return ((JobScheduler) context.getSystemService("jobscheduler")).getAllPendingJobs().size();
        } catch (Throwable th) {
            C1179b.m1354a("JobSender|" + th.toString());
            return -1;
        }
    }
}
