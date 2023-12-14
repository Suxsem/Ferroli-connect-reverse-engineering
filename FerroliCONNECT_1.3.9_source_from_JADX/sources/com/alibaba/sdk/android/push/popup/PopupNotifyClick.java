package com.alibaba.sdk.android.push.popup;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.agoo.BaseNotifyClick;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class PopupNotifyClick extends BaseNotifyClick {
    static final String TAG = "PopupNotifyClick";
    private PopupNotifyClickListener listener;

    public PopupNotifyClick(PopupNotifyClickListener popupNotifyClickListener) {
        this.listener = popupNotifyClickListener;
    }

    public void onParseFailed(Intent intent) {
        PopupNotifyClickListener popupNotifyClickListener = this.listener;
        if (popupNotifyClickListener instanceof OnPushParseFailedListener) {
            ((OnPushParseFailedListener) popupNotifyClickListener).onParseFailed(intent);
        }
    }

    public void onNotPushData(Intent intent) {
        PopupNotifyClickListener popupNotifyClickListener = this.listener;
        if (popupNotifyClickListener instanceof OnPushParseFailedListener) {
            ((OnPushParseFailedListener) popupNotifyClickListener).onNotPushData(intent);
        }
    }

    public void onMessage(Intent intent) {
        if (intent == null) {
            ALog.m3727e(TAG, "intent null, return", new Object[0]);
            return;
        }
        String stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if (stringExtra != null) {
            ALog.m3728i(TAG, "Receive notification, body: " + stringExtra, new Object[0]);
            try {
                Map<String, String> map = JsonUtility.toMap(new JSONObject(stringExtra));
                String str = map.get("title");
                String str2 = map.get("content");
                String str3 = map.get("msg_id");
                int intValue = new Integer(map.get("type")).intValue();
                if (1 == intValue) {
                    Map<String, String> map2 = JsonUtility.toMap(new JSONObject(map.get("ext")));
                    map2.put(AgooConstants.MESSAGE_BODY_MSG_ID_ALIYUN_FLAG, str3);
                    if (this.listener != null) {
                        this.listener.onSysNoticeOpened(str, str2, map2);
                    } else {
                        ALog.m3727e(TAG, "PopupNotifyClickListener is null", new Object[0]);
                    }
                } else if (2 == intValue) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(AgooConstants.MESSAGE_BODY_MSG_ID_ALIYUN_FLAG, str3);
                    if (this.listener != null) {
                        this.listener.onSysNoticeOpened(str, str2, hashMap);
                    } else {
                        ALog.m3727e(TAG, "PopupNotifyClickListener is null", new Object[0]);
                    }
                }
            } catch (JSONException e) {
                ALog.m3727e(TAG, "Parse json error, " + e, new Object[0]);
            }
        }
    }
}
