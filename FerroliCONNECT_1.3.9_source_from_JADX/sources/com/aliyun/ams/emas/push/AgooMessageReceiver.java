package com.aliyun.ams.emas.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public abstract class AgooMessageReceiver extends BroadcastReceiver implements IAgooPushCallback, IAgooPushConfig {
    public static final String EXTRA_MAP = "extraMap";
    public static final String MESSAGE_ID = "messageId";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_OPENED_ACTION = "com.alibaba.push2.action.NOTIFICATION_OPENED";
    public static final String NOTIFICATION_OPEN_TYPE = "notificationOpenType";
    public static final String NOTIFICATION_REMOVED_ACTION = "com.alibaba.push2.action.NOTIFICATION_REMOVED";
    public static final String SUMMARY = "summary";
    public static final String TAG = "MPS:AgooMessageReceiver";
    public static final String TITLE = "title";

    public void onReceive(Context context, Intent intent) {
        ALog.m3725d(TAG, "AgooMessageReceiver onReceive begin...intent=" + intent, new Object[0]);
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            try {
                if (TextUtils.equals(AgooInnerService.AGOO_RECEIVE_ACTION, action)) {
                    C0904b.m1061a(context, intent, (IAgooPushConfig) this, (IAgooPushCallback) this);
                } else if (TextUtils.equals(NOTIFICATION_OPENED_ACTION, action)) {
                    C0904b.m1060a(context, intent, this);
                } else if (TextUtils.equals(NOTIFICATION_REMOVED_ACTION, action)) {
                    C0904b.m1064b(context, intent, this);
                } else if (TextUtils.equals(action, PushConsts.ACTION_BROADCAST_NETWORK_CHANGE) || TextUtils.equals(action, PushConsts.ACTION_BROADCAST_TO_BOOT) || TextUtils.equals(action, "android.intent.action.PACKAGE_ADDED") || TextUtils.equals(action, "android.intent.action.PACKAGE_REPLACED") || TextUtils.equals(action, PushConsts.ACTION_BROADCAST_USER_PRESENT) || TextUtils.equals(action, "android.intent.action.ACTION_POWER_CONNECTED") || TextUtils.equals(action, "android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    ALog.m3725d(TAG, "USER ACTION: " + action, new Object[0]);
                }
            } catch (Throwable th) {
                ALog.m3726e(TAG, "handle action error:", th, new Object[0]);
            }
        }
    }
}
