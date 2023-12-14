package com.szacs.ferroliconnect.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompat;
import com.szacs.ferroliconnect.C1683R;

public class NotificationUtil extends ContextWrapper {
    private static final String TAG = "NotificationUtil";

    /* renamed from: id */
    public static final String f3146id = "channel_1";
    public static final String name = "channel_name_1";
    private static NotificationUtil notificationUtil;
    private NotificationManager manager;

    public static NotificationUtil getInstance(Context context) {
        if (notificationUtil == null) {
            notificationUtil = new NotificationUtil(context);
        }
        return notificationUtil;
    }

    private NotificationUtil(Context context) {
        super(context);
    }

    @RequiresApi(api = 26)
    public void createNotificationChannel() {
        getManager().createNotificationChannel(new NotificationChannel(f3146id, name, 4));
    }

    private NotificationManager getManager() {
        if (this.manager == null) {
            this.manager = (NotificationManager) getSystemService("notification");
        }
        return this.manager;
    }

    @RequiresApi(api = 26)
    public Notification.Builder getChannelNotification(String str, String str2, PendingIntent pendingIntent) {
        return new Notification.Builder(getApplicationContext(), f3146id).setContentTitle(str).setContentText(str2).setSmallIcon(C1683R.mipmap.logo).setAutoCancel(true).setChannelId(f3146id).setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getNotification_25(String str, String str2, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(getApplicationContext()).setContentTitle(str).setContentText(str2).setSmallIcon(C1683R.mipmap.logo).setAutoCancel(true).setContentIntent(pendingIntent);
    }

    public void sendNotification(String str, String str2, PendingIntent pendingIntent) {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            getManager().notify(currentTimeMillis, getChannelNotification(str, str2, pendingIntent).build());
            return;
        }
        getManager().notify(currentTimeMillis, getNotification_25(str, str2, pendingIntent).build());
    }

    public void clearAllNotify() {
        getManager().cancelAll();
    }
}
