package com.topband.tsmart.interfaces;

import android.content.Context;
import com.topband.tsmart.TBPushMessage;
import java.util.Map;

public interface PushDataCallback {
    void onMessage(Context context, TBPushMessage tBPushMessage);

    void onNotification(Context context, String str, String str2, Map<String, String> map);

    void onNotificationOpened(Context context, String str, String str2, Map<String, String> map);

    void onNotificationReceivedInApp(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4);
}
