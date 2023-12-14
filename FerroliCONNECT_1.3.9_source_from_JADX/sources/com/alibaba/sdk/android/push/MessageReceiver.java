package com.alibaba.sdk.android.push;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.push.notification.C0863d;
import com.alibaba.sdk.android.push.notification.C0864e;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.alibaba.sdk.android.push.notification.NotificationConfigure;
import com.alibaba.sdk.android.push.notification.PushData;
import com.aliyun.ams.emas.push.AgooMessageReceiver;
import java.util.Map;

public abstract class MessageReceiver extends AgooMessageReceiver {
    private C0863d mMessageNotification = new C0863d();

    public Notification customNotificationUI(Context context, PushData pushData) {
        return null;
    }

    public Notification customNotificationUI(Context context, Map<String, String> map) {
        PushData parse = PushData.parse(context, map);
        NotificationConfigure hookNotificationBuild = hookNotificationBuild();
        Notification customNotificationUI = customNotificationUI(context, parse);
        if (customNotificationUI != null) {
            return customNotificationUI;
        }
        return this.mMessageNotification.mo10088a(context, this.mMessageNotification.mo10089a(context, map), parse, hookNotificationBuild);
    }

    public NotificationConfigure hookNotificationBuild() {
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract void onMessage(Context context, CPushMessage cPushMessage);

    public void onMessageArrived(Context context, com.aliyun.ams.emas.push.notification.CPushMessage cPushMessage) {
        onMessage(context, CPushMessage.from(cPushMessage));
    }

    /* access modifiers changed from: protected */
    public abstract void onNotification(Context context, String str, String str2, Map<String, String> map);

    /* access modifiers changed from: protected */
    public abstract void onNotificationClickedWithNoAction(Context context, String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public abstract void onNotificationOpened(Context context, String str, String str2, String str3);

    public void onNotificationOpened(Context context, String str, String str2, String str3, int i) {
        if (i == 4) {
            onNotificationClickedWithNoAction(context, str, str2, str3);
        } else {
            onNotificationOpened(context, str, str2, str3);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onNotificationReceivedInApp(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4);

    public void onNotificationReceivedWithoutShow(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4) {
        onNotificationReceivedInApp(context, str, str2, map, i, str3, str4);
    }

    /* access modifiers changed from: protected */
    public abstract void onNotificationRemoved(Context context, String str);

    public void onNotificationRemoved(Context context, String str, String str2, String str3, int i, String str4) {
        onNotificationRemoved(context, str4);
    }

    public void onNotificationShow(Context context, String str, String str2, Map<String, String> map) {
        onNotification(context, str, str2, map);
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public boolean showNotificationNow(Context context, Map<String, String> map) {
        return C0863d.m981a(map) || !C0864e.m987a(context);
    }
}
