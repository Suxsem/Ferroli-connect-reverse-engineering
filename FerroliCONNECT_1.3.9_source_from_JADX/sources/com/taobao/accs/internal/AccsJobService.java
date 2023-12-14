package com.taobao.accs.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import com.igexin.sdk.GTIntentService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UtilityImpl;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
/* compiled from: Taobao */
public class AccsJobService extends JobService {
    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public boolean onStartJob(JobParameters jobParameters) {
        ALog.m3725d("AccsJobService", "onStartJob", new Object[0]);
        if (UtilityImpl.m3752b((Context) this, true)) {
            return false;
        }
        try {
            String packageName = getPackageName();
            Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra("command", 201);
            intent.setClassName(packageName, AdapterUtilityImpl.channelService);
            IntentDispatch.dispatchIntent(getApplicationContext(), intent, AdapterUtilityImpl.channelService);
        } catch (Throwable th) {
            ALog.m3726e("AccsJobService", "onStartJob", th, new Object[0]);
        }
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2039c(this, jobParameters), GTIntentService.WAIT_TIME, TimeUnit.MILLISECONDS);
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        if (UtilityImpl.m3752b((Context) this, true)) {
            return false;
        }
        try {
            Intent intent = new Intent();
            intent.setPackage(getPackageName());
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra("command", 201);
            intent.setClassName(getPackageName(), AdapterUtilityImpl.channelService);
            IntentDispatch.dispatchIntent(getApplicationContext(), intent, AdapterUtilityImpl.channelService);
        } catch (Throwable th) {
            ALog.m3726e("AccsJobService", "onStopJob", th, new Object[0]);
        }
        return false;
    }
}
