package com.igexin.sdk;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.util.C1588m;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class GTIntentService extends Service {
    public static final String TAG = "GTIntentService";
    public static final long WAIT_TIME = 30000;
    private Service mService;
    /* access modifiers changed from: private */
    public volatile C1603c mServiceHandler;
    private volatile Looper mServiceLooper;
    private BlockingQueue<Intent> messageQueue;

    public class JobIntentService extends JobService {
        public JobIntentService(Service service) {
            try {
                C1588m.m3254a(getClass(), "attachBaseContext", (Class<?>[]) new Class[]{Context.class}).invoke(this, new Object[]{service});
            } catch (Throwable th) {
                Log.e(GTIntentService.TAG, "GTJobService init err: " + th.toString());
            }
        }

        public boolean onStartJob(JobParameters jobParameters) {
            GTIntentService.this.mServiceHandler.post(new C1602b(this, jobParameters));
            return true;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    private void processOnHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.get(PushConsts.CMD_ACTION) != null && (extras.get(PushConsts.CMD_ACTION) instanceof Integer)) {
            int i = extras.getInt(PushConsts.CMD_ACTION);
            if (i == 10001) {
                onReceiveMessageData(this, (GTTransmitMessage) intent.getSerializableExtra(PushConsts.KEY_MESSAGE_DATA));
            } else if (i == 10002) {
                onReceiveClientId(this, extras.getString(PushConsts.KEY_CLIENT_ID));
            } else if (i == 10007) {
                onReceiveOnlineState(this, extras.getBoolean(PushConsts.KEY_ONLINE_STATE));
            } else if (i != 10008) {
                switch (i) {
                    case 10010:
                        onReceiveCommandResult(this, (GTCmdMessage) intent.getSerializableExtra(PushConsts.KEY_CMD_MSG));
                        return;
                    case 10011:
                        onNotificationMessageArrived(this, (GTNotificationMessage) intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_ARRIVED));
                        return;
                    case PushConsts.ACTION_NOTIFICATION_CLICKED:
                        onNotificationMessageClicked(this, (GTNotificationMessage) intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_CLICKED));
                        return;
                    default:
                        return;
                }
            } else {
                onReceiveServicePid(this, extras.getInt(PushConsts.KEY_SERVICE_PIT));
            }
        }
    }

    public IBinder onBind(Intent intent) {
        Service service = this.mService;
        if (service == null) {
            return null;
        }
        return service.onBind(intent);
    }

    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("IntentService[GTJob]");
        handlerThread.start();
        this.mServiceLooper = handlerThread.getLooper();
        this.mServiceHandler = new C1603c(this, this.mServiceLooper);
        if (Build.VERSION.SDK_INT >= 26) {
            this.mService = new JobIntentService(this);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mServiceLooper.quit();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                processOnHandleIntent(intent);
                if (this.messageQueue != null) {
                    this.messageQueue.remove(intent);
                    this.messageQueue.poll(WAIT_TIME, TimeUnit.MILLISECONDS);
                }
            } catch (Throwable th) {
                C1179b.m1354a("GTIntentService|" + th.toString());
            }
        }
    }

    public abstract void onNotificationMessageArrived(Context context, GTNotificationMessage gTNotificationMessage);

    public abstract void onNotificationMessageClicked(Context context, GTNotificationMessage gTNotificationMessage);

    public abstract void onReceiveClientId(Context context, String str);

    public abstract void onReceiveCommandResult(Context context, GTCmdMessage gTCmdMessage);

    public abstract void onReceiveMessageData(Context context, GTTransmitMessage gTTransmitMessage);

    public abstract void onReceiveOnlineState(Context context, boolean z);

    public abstract void onReceiveServicePid(Context context, int i);

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            if (this.messageQueue == null) {
                this.messageQueue = new LinkedBlockingQueue();
            }
            this.messageQueue.offer(intent);
            Message obtainMessage = this.mServiceHandler.obtainMessage();
            obtainMessage.arg1 = i2;
            obtainMessage.obj = intent;
            this.mServiceHandler.sendMessage(obtainMessage);
            return 2;
        } catch (Throwable th) {
            C1179b.m1354a("GTIntentService|" + th.toString());
            return 2;
        }
    }
}
