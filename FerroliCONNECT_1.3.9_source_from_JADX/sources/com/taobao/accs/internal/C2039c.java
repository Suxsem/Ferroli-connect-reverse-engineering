package com.taobao.accs.internal;

import android.app.job.JobParameters;

/* renamed from: com.taobao.accs.internal.c */
/* compiled from: Taobao */
class C2039c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ JobParameters f3345a;

    /* renamed from: b */
    final /* synthetic */ AccsJobService f3346b;

    C2039c(AccsJobService accsJobService, JobParameters jobParameters) {
        this.f3346b = accsJobService;
        this.f3345a = jobParameters;
    }

    public void run() {
        this.f3346b.jobFinished(this.f3345a, false);
    }
}
