package com.alibaba.sdk.android.push.noonesdk;

import android.app.Application;
import android.content.Context;
import com.alibaba.sdk.android.ams.common.p009a.C0670b;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.PushControlService;
import com.alibaba.sdk.android.push.p018b.C0763b;
import com.alibaba.sdk.android.push.p018b.C0796e;
import com.alibaba.sdk.android.push.p027g.C0827a;
import com.taobao.accs.ACCSClient;

public class PushServiceFactory {
    public static CloudPushService getCloudPushService() {
        return C0763b.m746a();
    }

    public static PushControlService getPushControlService() {
        return C0796e.m756a();
    }

    public static void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext instanceof Application) {
            C0670b.m473a((Application) applicationContext);
        }
        C0670b.m474a(applicationContext);
        C0763b a = C0763b.m746a();
        a.mo9850a(applicationContext);
        ACCSClient.enableChannelProcess(applicationContext, true);
        a.setPushIntentService((Class) null);
        C0827a.m825a().mo9907b();
    }

    @Deprecated
    public static void init(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext instanceof Application) {
            C0670b.m473a((Application) applicationContext);
        }
        C0670b.m474a(applicationContext);
        C0763b a = C0763b.m746a();
        a.mo9850a(applicationContext);
        a.setAppkey(str);
        a.setAppSecret(str2);
        a.setPushIntentService((Class) null);
        C0827a.m825a().mo9907b();
    }

    @Deprecated
    public static void init(Context context, String str, String str2, boolean z) {
        ACCSClient.enableChannelProcess(context, z);
        init(context, str, str2);
    }

    @Deprecated
    public static void init(Context context, boolean z) {
        ACCSClient.enableChannelProcess(context, z);
        init(context);
    }

    public static void init(PushInitConfig pushInitConfig) {
        C0670b.m473a(pushInitConfig.getApplication());
        C0670b.m474a(pushInitConfig.getApplication().getApplicationContext());
        C0670b.m475a(pushInitConfig.isDisableForegroundCheck());
        C0763b a = C0763b.m746a();
        a.mo9850a(pushInitConfig.getApplication().getApplicationContext());
        a.setPushIntentService((Class) null);
        if (pushInitConfig.getAppKey() != null) {
            a.setAppkey(pushInitConfig.getAppKey());
        }
        if (pushInitConfig.getAppSecret() != null) {
            a.setAppSecret(pushInitConfig.getAppSecret());
        }
        ACCSClient.enableChannelProcess(pushInitConfig.getApplication(), !pushInitConfig.isDisableChannelProcess());
        ACCSClient.enableChannelProcessHeartbeat(pushInitConfig.getApplication(), !pushInitConfig.isDisableChannelProcessHeartbeat());
        C0827a.m825a().mo9906a(pushInitConfig.isLoopStartChannel(), pushInitConfig.getLoopInterval());
    }
}
