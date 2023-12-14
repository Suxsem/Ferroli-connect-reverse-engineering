package com.topband.tsmart.interfaces;

import android.app.Application;
import com.alibaba.sdk.android.push.CloudPushService;

public interface ITSmartPush extends ICloudPushManager {
    void addAlias(String str);

    String getChannelId();

    PushDataCallback getPushDataCallback();

    CloudPushService getPushService();

    void initPush(Application application, boolean z);

    void removeAlias(String str);

    void setChannelDescription(String str);

    void setChannelId(String str);

    void setChannelName(CharSequence charSequence);

    void setDispose(boolean z);

    void setPopWindow(String str);

    void setPushDataCallback(PushDataCallback pushDataCallback);
}
