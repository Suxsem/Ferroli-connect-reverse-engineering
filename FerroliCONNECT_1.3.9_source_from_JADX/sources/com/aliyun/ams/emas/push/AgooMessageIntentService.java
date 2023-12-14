package com.aliyun.ams.emas.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public abstract class AgooMessageIntentService extends Service implements IAgooPushCallback, IAgooPushConfig {
    private static final String TAG = "MPS:AliyunMessageIntentService";
    private Messenger messenger = new Messenger(new C0902a(this));

    public IBinder onBind(Intent intent) {
        return this.messenger.getBinder();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        onHandleIntent(intent);
        return 2;
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            ALog.m3727e(TAG, "Action is null, return.", new Object[0]);
            return;
        }
        ALog.m3725d(TAG, "check_notif action: " + action, new Object[0]);
        if (AgooInnerService.AGOO_RECEIVE_ACTION.equals(action)) {
            C0904b.m1061a(getApplicationContext(), intent, (IAgooPushConfig) this, (IAgooPushCallback) this);
        } else if (AgooMessageReceiver.NOTIFICATION_OPENED_ACTION.equals(action)) {
            C0904b.m1060a(getApplicationContext(), intent, this);
        } else if (AgooMessageReceiver.NOTIFICATION_REMOVED_ACTION.equals(action)) {
            C0904b.m1064b(getApplicationContext(), intent, this);
        } else {
            ALog.m3727e(TAG, "Invalid action", new Object[0]);
        }
    }
}
