package com.topband.tsmart.interfaces;

import android.content.Context;

public interface ICloudPushManager {
    void GcmRegister(Context context, String str, String str2);

    void bindTag();

    boolean isHasRegister();

    void setLoginStatus(boolean z);

    void setUserId(String str);

    void turnOffPushChannel();

    void turnOnPushChannel();

    void unbindTag();
}
