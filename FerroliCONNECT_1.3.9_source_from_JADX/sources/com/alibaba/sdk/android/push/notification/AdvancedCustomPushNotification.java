package com.alibaba.sdk.android.push.notification;

public class AdvancedCustomPushNotification extends BasicCustomPushNotification {
    private int contentView = 0;
    private int icon = 0;
    private int iconView = 0;
    private int notificationView = 0;
    private int titleView = 0;

    public AdvancedCustomPushNotification(int i, int i2, int i3, int i4) {
        this.notificationType = 3;
        this.notificationView = i;
        this.titleView = i3;
        this.iconView = i2;
        this.contentView = i4;
    }

    public int getContentView() {
        return this.contentView;
    }

    public int getIcon() {
        return this.icon;
    }

    public int getIconView() {
        return this.iconView;
    }

    public int getNotificationView() {
        return this.notificationView;
    }

    public int getTitleView() {
        return this.titleView;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public String toString() {
        return super.toString() + ", notificationView:" + this.notificationView + ", titleView:" + this.titleView + ", iconView:" + this.titleView + ", contentView:" + this.contentView;
    }
}
