package com.alibaba.sdk.android.push.notification;

import android.app.Notification;
import android.support.p000v4.app.NotificationCompat;

public interface NotificationConfigure {
    void configBuilder(Notification.Builder builder, PushData pushData);

    void configBuilder(NotificationCompat.Builder builder, PushData pushData);

    void configNotification(Notification notification, PushData pushData);
}
