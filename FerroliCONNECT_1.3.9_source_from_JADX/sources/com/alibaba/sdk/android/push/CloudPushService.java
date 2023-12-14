package com.alibaba.sdk.android.push;

import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.sdk.android.push.notification.CPushMessage;

public interface CloudPushService {
    public static final int ACCOUNT_TARGET = 2;
    public static final int ALIAS_TARGET = 3;
    public static final int DEVICE_TARGET = 1;
    public static final int LOG_DEBUG = 2;
    public static final int LOG_ERROR = 0;
    public static final int LOG_INFO = 1;
    public static final int LOG_OFF = -1;
    public static final String NOTIFICATION_ID = "_ALIYUN_NOTIFICATION_ID_";
    public static final String NOTIFICATION_PRIORITY = "_ALIYUN_NOTIFICATION_PRIORITY_";

    void addAlias(String str, CommonCallback commonCallback);

    void bindAccount(String str, CommonCallback commonCallback);

    void bindPhoneNumber(String str, CommonCallback commonCallback);

    void bindTag(int i, String[] strArr, String str, CommonCallback commonCallback);

    void checkPushChannelStatus(CommonCallback commonCallback);

    void clearNotifications();

    void clickMessage(CPushMessage cPushMessage);

    void closeDoNotDisturbMode();

    void dismissMessage(CPushMessage cPushMessage);

    String getDeviceId();

    String getUTDeviceId();

    void listAliases(CommonCallback commonCallback);

    void listTags(int i, CommonCallback commonCallback);

    @Deprecated
    void onAppStart();

    void register(Context context, CommonCallback commonCallback);

    @Deprecated
    void register(Context context, String str, String str2, CommonCallback commonCallback);

    void removeAlias(String str, CommonCallback commonCallback);

    @Deprecated
    void setAppSecret(String str);

    @Deprecated
    void setAppkey(String str);

    void setDebug(boolean z);

    void setDoNotDisturb(int i, int i2, int i3, int i4, CommonCallback commonCallback);

    void setLogLevel(int i);

    void setNotificationLargeIcon(Bitmap bitmap);

    void setNotificationSmallIcon(int i);

    void setNotificationSoundFilePath(String str);

    void setPushIntentService(Class cls);

    void turnOffPushChannel(CommonCallback commonCallback);

    void turnOnPushChannel(CommonCallback commonCallback);

    void unbindAccount(CommonCallback commonCallback);

    void unbindPhoneNumber(CommonCallback commonCallback);

    void unbindTag(int i, String[] strArr, String str, CommonCallback commonCallback);
}
