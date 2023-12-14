package com.alibaba.sdk.android.push.channel;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;

@TargetApi(21)
public class KeepChannelService extends JobService {

    /* renamed from: a */
    private static AmsLogger f1067a = AmsLogger.getLogger("MPS:KeepChannelService");

    public boolean onStartJob(JobParameters jobParameters) {
        f1067a.mo9538d("keepScheduleService start");
        return false;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        f1067a.mo9538d("keepScheduleService stoped");
        return false;
    }
}
