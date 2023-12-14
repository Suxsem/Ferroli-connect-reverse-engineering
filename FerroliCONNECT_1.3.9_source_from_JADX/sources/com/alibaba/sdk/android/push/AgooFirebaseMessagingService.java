package com.alibaba.sdk.android.push;

import android.text.TextUtils;
import com.alibaba.sdk.android.push.register.ThirdPushManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.taobao.accs.utl.ALog;

public class AgooFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MPS:GcmRegister";

    public void onCreate() {
        super.onCreate();
        ALog.m3725d("MPS:GcmRegister", "AgooFirebaseMessagingService oncreate", new Object[0]);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            ALog.m3725d("MPS:GcmRegister", "onMessageReceived", new Object[0]);
            if (remoteMessage.getData() != null) {
                String str = remoteMessage.getData().get("payload");
                ALog.m3725d("MPS:GcmRegister", "onMessageReceived payload msg:" + str, new Object[0]);
                if (!TextUtils.isEmpty(str)) {
                    ThirdPushManager.onPushMsg(getApplicationContext(), ThirdPushManager.ThirdPushReportKeyword.FCM.thirdMsgKeyword, str);
                }
            }
        } catch (Throwable th) {
            ALog.m3726e("MPS:GcmRegister", "onMessageReceived", th, new Object[0]);
        }
    }
}
