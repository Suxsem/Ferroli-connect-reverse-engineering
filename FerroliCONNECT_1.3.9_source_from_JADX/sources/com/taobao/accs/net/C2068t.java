package com.taobao.accs.net;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.taobao.accs.internal.AccsJobService;

/* renamed from: com.taobao.accs.net.t */
/* compiled from: Taobao */
public class C2068t extends C2053f {
    public static final int DEAMON_JOB_ID = 2051;
    public static final int HB_JOB_ID = 2050;

    protected C2068t(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18467a(int i) {
        long j = (long) (i * 1000);
        ((JobScheduler) this.f3398a.getSystemService("jobscheduler")).schedule(new JobInfo.Builder(HB_JOB_ID, new ComponentName(this.f3398a.getPackageName(), AccsJobService.class.getName())).setMinimumLatency(j).setOverrideDeadline(j).setRequiredNetworkType(1).build());
    }
}
