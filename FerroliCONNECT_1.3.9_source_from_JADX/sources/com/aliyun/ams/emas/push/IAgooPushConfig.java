package com.aliyun.ams.emas.push;

import android.app.Notification;
import android.content.Context;
import java.util.Map;

/* compiled from: Taobao */
public interface IAgooPushConfig {
    Notification customNotificationUI(Context context, Map<String, String> map);

    boolean showNotificationNow(Context context, Map<String, String> map);
}
