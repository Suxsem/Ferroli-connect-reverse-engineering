package com.taobao.accs.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class MsgDistributeService extends Service {

    /* renamed from: a */
    private Messenger f3294a = new Messenger(new C2035i(this));

    public IBinder onBind(Intent intent) {
        return this.f3294a.getBinder();
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            ALog.m3728i("MsgDistributeService", "onStartCommand", PushConsts.CMD_ACTION, intent.getAction());
            if (TextUtils.isEmpty(intent.getAction()) || !TextUtils.equals(intent.getAction(), Constants.ACTION_SEND)) {
                ALog.m3728i("MsgDistributeService", "onStartCommand distribute message", new Object[0]);
                C2033g.m3534a(getApplicationContext(), intent);
                return 2;
            }
            if (getPackageName().equals(intent.getStringExtra(Constants.KEY_PACKAGE_NAME))) {
                ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2036j(this, intent));
            }
            return 2;
        } catch (Throwable th) {
            ALog.m3726e("MsgDistributeService", "onStartCommand", th, new Object[0]);
        }
    }
}
