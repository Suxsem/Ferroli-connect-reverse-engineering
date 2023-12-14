package com.alibaba.sdk.android.push.p019c;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.push.channel.KeepChannelService;

/* renamed from: com.alibaba.sdk.android.push.c.a */
public class C0800a {

    /* renamed from: a */
    private static AmsLogger f1064a = AmsLogger.getLogger("MPS:KeepLiveManager");

    /* renamed from: b */
    private static Context f1065b = null;

    /* renamed from: c */
    private static C0800a f1066c = null;

    private C0800a() {
    }

    /* renamed from: a */
    public static C0800a m759a() {
        if (f1066c == null) {
            f1066c = new C0800a();
        }
        return f1066c;
    }

    /* renamed from: a */
    public static void m760a(Context context) {
        f1065b = context;
        if (f1066c == null) {
            f1066c = m759a();
        }
    }

    /* renamed from: b */
    public void mo9879b() {
        if (f1065b != null) {
            f1064a.mo9538d("Check KeepChannelService");
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    JobScheduler jobScheduler = (JobScheduler) f1065b.getSystemService("jobscheduler");
                    for (JobInfo next : jobScheduler.getAllPendingJobs()) {
                        if (next.getId() == 900715 && next.getService().equals(new ComponentName(f1065b.getPackageName(), KeepChannelService.class.getName()))) {
                            f1064a.mo9538d("cancel Keep Channel Service");
                            jobScheduler.cancel(next.getId());
                            return;
                        }
                    }
                } catch (Throwable th) {
                    f1064a.mo9542e("start KeepChannelService failed.", th);
                }
            }
        }
    }
}
