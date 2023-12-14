package com.aliyun.ams.emas.push;

import android.content.Intent;
import android.text.TextUtils;
import com.aliyun.ams.emas.push.notification.C0915e;
import com.taobao.accs.data.MsgDistributeService;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class MsgService extends MsgDistributeService {
    public static final String TAG = "MPS:MsgService";
    C0915e listener = new C0915e();

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            ALog.m3725d(TAG, "intent null", new Object[0]);
            return super.onStartCommand(intent, i, i2);
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return super.onStartCommand(intent, i, i2);
        }
        ALog.m3725d(TAG, "MsgService onStartCommand begin...action=" + action, new Object[0]);
        if (!TextUtils.equals(action, C0910h.f1433a)) {
            return super.onStartCommand(intent, i, i2);
        }
        this.listener.mo10234a(intent, getApplicationContext());
        return 2;
    }
}
