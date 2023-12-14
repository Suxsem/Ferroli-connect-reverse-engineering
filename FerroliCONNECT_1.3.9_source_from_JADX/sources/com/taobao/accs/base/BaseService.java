package com.taobao.accs.base;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.support.p000v4.app.NotificationCompat;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.internal.ServiceImpl;
import com.taobao.accs.messenger.MessengerService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;

/* compiled from: Taobao */
public class BaseService extends Service {
    private static final String TAG = "BaseService";
    IBaseService mBaseService = null;
    private Messenger messenger = new Messenger(new Handler() {
        public void handleMessage(Message message) {
            if (message != null) {
                ALog.m3728i(BaseService.TAG, "handleMessage on receive msg", NotificationCompat.CATEGORY_MESSAGE, message.toString());
                Intent intent = (Intent) message.getData().getParcelable(MessengerService.INTENT);
                if (intent != null) {
                    ALog.m3728i(BaseService.TAG, "handleMessage get intent success", MessengerService.INTENT, intent.toString());
                    BaseService.this.onStartCommand(intent, 0, 0);
                }
            }
        }
    });

    public void onCreate() {
        super.onCreate();
        ACCSClient.setCurrentProcessName(getApplicationContext());
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                try {
                    BaseService.this.mBaseService = new ServiceImpl(BaseService.this);
                    BaseService.this.mBaseService.onCreate();
                } catch (Exception e) {
                    ALog.m3727e(BaseService.TAG, "create ServiceImpl error", e.getMessage());
                }
            }
        });
    }

    public int onStartCommand(final Intent intent, final int i, final int i2) {
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                if (BaseService.this.mBaseService != null) {
                    BaseService.this.mBaseService.onStartCommand(intent, i, i2);
                    return;
                }
                BaseService.this.onCreate();
                BaseService.this.onStartCommand(intent, i, i2);
            }
        });
        if (UtilityImpl.m3770l(this)) {
            return 1;
        }
        ALog.m3725d(TAG, "channel process is disabled, stop", new Object[0]);
        Process.killProcess(Process.myPid());
        return 1;
    }

    public IBinder onBind(Intent intent) {
        ALog.m3725d(TAG, "onBind", MessengerService.INTENT, intent);
        try {
            if (Utils.isTarget26(this)) {
                ALog.m3728i(TAG, "onBind bind service", new Object[0]);
                getApplicationContext().bindService(new Intent(this, getClass()), new ServiceConnection() {
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    }

                    public void onServiceDisconnected(ComponentName componentName) {
                    }
                }, 1);
            }
        } catch (Throwable th) {
            ALog.m3728i(TAG, "onBind bind service with exception", th.toString());
        }
        return this.messenger.getBinder();
    }

    public void onDestroy() {
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                if (BaseService.this.mBaseService != null) {
                    BaseService.this.mBaseService.onDestroy();
                    BaseService.this.mBaseService = null;
                }
            }
        });
        super.onDestroy();
    }
}
