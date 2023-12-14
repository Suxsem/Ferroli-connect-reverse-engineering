package com.aliyun.ams.emas.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.aliyun.ams.emas.push.notification.C0915e;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class NotificationActivity extends Activity {
    public static final String TAG = "MPS:NotificationActivity";

    /* renamed from: a */
    C0915e f1412a = new C0915e();

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            ALog.m3725d(TAG, "MsgService onStartCommand begin...action=" + action, new Object[0]);
            if (TextUtils.equals(action, C0910h.f1433a)) {
                this.f1412a.mo10235a(intent, getApplicationContext(), 1);
            }
        }
        finish();
    }
}
