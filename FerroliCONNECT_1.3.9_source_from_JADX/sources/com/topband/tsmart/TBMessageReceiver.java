package com.topband.tsmart;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public class TBMessageReceiver extends MessageReceiver {
    /* access modifiers changed from: protected */
    public void onNotificationClickedWithNoAction(Context context, String str, String str2, String str3) {
    }

    /* access modifiers changed from: protected */
    public void onNotificationRemoved(Context context, String str) {
    }

    /* access modifiers changed from: protected */
    public void onNotification(Context context, String str, String str2, Map<String, String> map) {
        Log.e("TBMessageReceiver", "onNotification->title:" + str + "\nsummary:" + str2 + ", extraMap:" + map);
        if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onNotification(context, str, str2, map);
        }
    }

    /* access modifiers changed from: protected */
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("TBMessageReceiver", "onMessage->title:" + cPushMessage.getTitle() + "\nsummary:" + cPushMessage.getContent());
        TBPushMessage tBPushMessage = new TBPushMessage();
        tBPushMessage.appId = cPushMessage.getAppId();
        tBPushMessage.title = cPushMessage.getTitle();
        tBPushMessage.content = cPushMessage.getContent();
        tBPushMessage.messageId = cPushMessage.getMessageId();
        tBPushMessage.traceInfo = cPushMessage.getTraceInfo();
        TBMessageDispose.getInstance().onMessage(context, tBPushMessage);
        if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onMessage(context, tBPushMessage);
        }
    }

    /* access modifiers changed from: protected */
    public void onNotificationReceivedInApp(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append("onNotificationReceivedInApp->title:");
        String str5 = str;
        sb.append(str);
        sb.append("\nsummary:");
        sb.append(str2);
        sb.append(", extraMap:");
        sb.append(map);
        Log.e("TBMessageReceiver", sb.toString());
        TBMessageDispose.getInstance().onNotificationReceivedInApp(context, str, str2, map, i, str3, str4);
    }

    public void onNotificationOpened(Context context, String str, String str2, String str3) {
        Log.e("TBMessageReceiver", "onNotificationOpened->title:" + str + "\nsummary:" + str2 + ", extraMap:" + str3);
        Map map = (Map) new Gson().fromJson(str3, new TypeToken<Map<String, String>>() {
        }.getType());
        if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onNotificationOpened(context, str, str2, map);
        }
    }
}
