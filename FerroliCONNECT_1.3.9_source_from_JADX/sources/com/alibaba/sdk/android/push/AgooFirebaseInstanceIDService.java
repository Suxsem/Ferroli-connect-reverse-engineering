package com.alibaba.sdk.android.push;

import android.content.Context;
import com.alibaba.sdk.android.push.fcm.BuildConfig;
import com.alibaba.sdk.android.push.register.ThirdPushManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.taobao.accs.utl.ALog;

public class AgooFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TAG = "MPS:GcmRegister";

    public void onCreate() {
        super.onCreate();
        ALog.m3725d("MPS:GcmRegister", "Token注册服务已经开启", new Object[0]);
    }

    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        ALog.m3728i("MPS:GcmRegister", "onTokenRefresh token:" + token, new Object[0]);
        reportGcmToken(this, token);
    }

    public static void reportGcmToken(Context context, String str) {
        ThirdPushManager.reportToken(context, ThirdPushManager.ThirdPushReportKeyword.FCM.thirdTokenKeyword, str, BuildConfig.GCM_VERSION);
    }
}
