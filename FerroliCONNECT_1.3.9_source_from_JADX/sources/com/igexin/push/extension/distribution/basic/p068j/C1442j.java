package com.igexin.push.extension.distribution.basic.p068j;

import android.app.Notification;
import android.app.NotificationManager;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.extension.distribution.basic.j.j */
public class C1442j {
    /* renamed from: a */
    public static void m2528a(NotificationManager notificationManager, int i, Notification notification, int i2) {
        try {
            if (notification.icon == 0 || C1343f.f2169f.getResources().getDrawable(notification.icon) != null) {
                notificationManager.notify(i, notification);
                return;
            }
            C1179b.m1354a("NotificationShow|showNotification smallIconId: " + notification.icon + " couldn't find resource");
        } catch (Throwable unused) {
        }
    }
}
