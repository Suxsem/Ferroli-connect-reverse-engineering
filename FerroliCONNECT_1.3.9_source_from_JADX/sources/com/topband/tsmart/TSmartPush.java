package com.topband.tsmart;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.taobao.agoo.p105a.p106a.C2124a;
import com.topband.tsmart.interfaces.ITSmartPush;
import com.topband.tsmart.interfaces.PushDataCallback;

public class TSmartPush implements ITSmartPush {
    public static final String TAG = "TSmartPush";
    private static TSmartPush tSmartPush;
    private int appID;
    private String channelDescription = "notification description";
    private String channelId = "16845";
    private CharSequence channelName = "notification channel";
    /* access modifiers changed from: private */
    public boolean hasRegister;
    /* access modifiers changed from: private */
    public boolean isLogin = false;
    private String popWindow = "";
    private PushDataCallback pushDataCallback;
    /* access modifiers changed from: private */
    public CloudPushService pushService;
    /* access modifiers changed from: private */
    public String userId = "";

    private void pushSetting() {
    }

    public static ITSmartPush instance() {
        if (tSmartPush == null) {
            synchronized (TSmartPush.class) {
                if (tSmartPush == null) {
                    tSmartPush = new TSmartPush();
                }
            }
        }
        return tSmartPush;
    }

    private TSmartPush() {
    }

    public void setPopWindow(String str) {
        this.popWindow = str;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setChannelName(CharSequence charSequence) {
        this.channelName = charSequence;
    }

    public void setChannelDescription(String str) {
        this.channelDescription = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public boolean isHasRegister() {
        return this.hasRegister;
    }

    public void setDispose(boolean z) {
        TBMessageDispose.getInstance().setDispose(z);
    }

    public PushDataCallback getPushDataCallback() {
        return this.pushDataCallback;
    }

    public void setPushDataCallback(PushDataCallback pushDataCallback2) {
        this.pushDataCallback = pushDataCallback2;
    }

    public void initPush(final Application application, boolean z) {
        loadCompanyId(application);
        PushServiceFactory.init((Context) application);
        this.pushService = PushServiceFactory.getCloudPushService();
        this.pushService.setDebug(z);
        this.pushService.register(application, new CommonCallback() {
            public void onSuccess(String str) {
                if (!TextUtils.isEmpty(TSmartPush.this.userId) && TSmartPush.this.isLogin) {
                    TSmartPush.this.bindTag();
                }
                boolean unused = TSmartPush.this.hasRegister = true;
                TSmartPush tSmartPush = TSmartPush.this;
                tSmartPush.printLog("initPush success;deviceId:" + TSmartPush.this.pushService.getDeviceId());
                TSmartPush.this.createNotificationChannel(application);
            }

            public void onFailed(String str, String str2) {
                boolean unused = TSmartPush.this.hasRegister = false;
                TSmartPush tSmartPush = TSmartPush.this;
                tSmartPush.printLog("initPush failed -- errorcode:" + str + " -- errorMessage:" + str2);
            }
        });
    }

    /* access modifiers changed from: private */
    public void createNotificationChannel(Application application) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(this.channelId, this.channelName, 4);
            notificationChannel.setDescription(this.channelDescription);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16711936);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            ((NotificationManager) application.getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
    }

    public CloudPushService getPushService() {
        return this.pushService;
    }

    public void bindTag() {
        this.pushService.bindTag(1, new String[]{this.userId}, "", getCommonCallback("bindTag"));
        pushSetting();
    }

    public void unbindTag() {
        this.pushService.unbindTag(1, new String[]{"1"}, "", getCommonCallback("unbindTag"));
    }

    public void turnOnPushChannel() {
        this.pushService.turnOnPushChannel(getCommonCallback("turnOnPushChannel"));
    }

    public void turnOffPushChannel() {
        this.pushService.turnOffPushChannel(getCommonCallback("turnOffPushChannel"));
    }

    private CommonCallback getCommonCallback(final String str) {
        return new CommonCallback() {
            public void onSuccess(String str) {
                TSmartPush tSmartPush = TSmartPush.this;
                tSmartPush.printLog(str + " success:" + str);
            }

            public void onFailed(String str, String str2) {
                TSmartPush tSmartPush = TSmartPush.this;
                tSmartPush.printLog(str + " failed;errorcode:" + str + " ;   errorMessage:" + str2);
            }
        };
    }

    private void loadCompanyId(Application application) {
        try {
            ApplicationInfo applicationInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                this.appID = applicationInfo.metaData.getInt("com.alibaba.app.appkey", 0);
            } else {
                printLog("AndroidManifest.xml need to add: <application > <meta-data  android:name=\"APP_ID\"  android:value=\"xxxx\"/>  </application>");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void printLog(String str) {
        String str2 = TAG;
        Log.e(str2, "TSmartPush->" + str);
    }

    public void GcmRegister(Context context, String str, String str2) {
        GcmRegister.register(context, str, str2);
    }

    public void addAlias(String str) {
        this.pushService.addAlias(str, getCommonCallback("addAlias"));
    }

    public void removeAlias(String str) {
        this.pushService.removeAlias(str, getCommonCallback(C2124a.JSON_CMD_REMOVEALIAS));
    }

    public void setUserId(String str) {
        this.userId = str;
        if (!TextUtils.isEmpty(str)) {
            bindTag();
            addAlias(str);
        }
    }

    public void setLoginStatus(boolean z) {
        this.isLogin = z;
        if (!z) {
            unbindTag();
            removeAlias("");
        }
    }
}
