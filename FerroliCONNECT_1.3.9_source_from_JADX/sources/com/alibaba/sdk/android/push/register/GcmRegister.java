package com.alibaba.sdk.android.push.register;

import android.content.Context;
import com.alibaba.sdk.android.push.AgooFirebaseInstanceIDService;
import com.alibaba.sdk.android.push.utils.SysUtils;
import com.alibaba.sdk.android.push.utils.ThreadUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.p105a.p106a.C2126c;

public class GcmRegister {
    public static final String TAG = "MPS:GcmRegister";
    /* access modifiers changed from: private */
    public static volatile boolean isRegistered = false;

    public static boolean register(final Context context, final String str, final String str2) {
        if (!SysUtils.isMainProcess(context)) {
            ALog.m3728i("MPS:GcmRegister", "not in main process, return", new Object[0]);
            return false;
        }
        ThreadUtil.getExecutor().execute(new Runnable() {
            public void run() {
                try {
                    if (GcmRegister.isRegistered) {
                        ALog.m3731w("MPS:GcmRegister", "registered already", new Object[0]);
                        return;
                    }
                    boolean unused = GcmRegister.isRegistered = true;
                    FirebaseOptions.Builder builder = new FirebaseOptions.Builder();
                    builder.setGcmSenderId(str);
                    builder.setApplicationId(str2);
                    FirebaseApp.initializeApp(context.getApplicationContext(), builder.build());
                    String token = FirebaseInstanceId.getInstance().getToken();
                    ALog.m3728i("MPS:GcmRegister", "register token：" + token, new Object[0]);
                    AgooFirebaseInstanceIDService.reportGcmToken(context, token);
                } catch (Throwable th) {
                    ALog.m3726e("MPS:GcmRegister", C2126c.JSON_CMD_REGISTER, th, new Object[0]);
                }
            }
        });
        return true;
    }
}
