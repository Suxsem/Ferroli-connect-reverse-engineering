package com.alibaba.sdk.android.push.notification;

import java.io.Serializable;

public class BasicCustomPushNotification implements Serializable {
    public static final String CUSTOM_NOTIFICATION_TAG = "custom_notification_";
    public static final int NOTIFICATION_TYPE_CUSTOM_ADVANCED = 3;
    public static final int NOTIFICATION_TYPE_CUSTOM_BASIC = 2;
    public static final int NOTIFICATION_TYPE_DEFAULT = 1;
    public static final int REMIND_TYPE_SILENT = 0;
    public static final int REMIND_TYPE_SOUND = 2;
    public static final int REMIND_TYPE_VIBRATE = 1;
    public static final int REMIND_TYPE_VIBRATE_AND_SOUND = 3;
    protected boolean buildWhenAppInForeground = true;
    protected int notificationFlags = 16;
    protected int notificationType = 2;
    protected int remindType = 3;
    protected boolean serverOptionFirst = false;
    protected int statusBarDrawable = 17301623;

    public BasicCustomPushNotification() {
    }

    public BasicCustomPushNotification(int i, int i2, int i3) {
        this.statusBarDrawable = i;
        this.remindType = i3;
        this.notificationFlags = i2;
    }

    public int getNotificationFlags() {
        return this.notificationFlags;
    }

    public int getNotificationType() {
        return this.notificationType;
    }

    public int getRemindType() {
        return this.remindType;
    }

    public int getStatusBarDrawable() {
        return this.statusBarDrawable;
    }

    public boolean isBuildWhenAppInForeground() {
        return this.buildWhenAppInForeground;
    }

    public boolean isServerOptionFirst() {
        return this.serverOptionFirst;
    }

    public void setBuildWhenAppInForeground(boolean z) {
        this.buildWhenAppInForeground = z;
    }

    public void setNotificationFlags(int i) {
        this.notificationFlags = i;
    }

    public void setRemindType(int i) {
        if (i == 0 || 1 == i || 2 == i || 3 == i) {
            this.remindType = i;
        }
    }

    public void setServerOptionFirst(boolean z) {
        this.serverOptionFirst = z;
    }

    public void setStatusBarDrawable(int i) {
        this.statusBarDrawable = i;
    }

    public String toString() {
        return "type:" + this.notificationType + ", statusBarDrawable:" + this.statusBarDrawable + ", remindType:" + this.remindType + ", flags:" + this.notificationFlags + ", serverOptionFirst:" + this.serverOptionFirst + ", buildWhenAppInForeground:" + isBuildWhenAppInForeground();
    }
}
