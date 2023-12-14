package com.aliyun.ams.emas.push;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.sdk.android.logger.ILog;
import com.aliyun.ams.emas.push.notification.C0913c;
import com.aliyun.ams.emas.push.notification.C0916f;
import com.aliyun.ams.emas.push.notification.CPushMessage;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.aliyun.ams.emas.push.b */
/* compiled from: Taobao */
class C0904b {
    C0904b() {
    }

    /* renamed from: a */
    public static void m1061a(Context context, Intent intent, IAgooPushConfig iAgooPushConfig, IAgooPushCallback iAgooPushCallback) {
        Intent intent2 = intent;
        try {
            String stringExtra = intent2.getStringExtra(AgooConstants.MESSAGE_ID);
            if (TextUtils.isEmpty(stringExtra)) {
                ALog.m3727e("AgooPushHandler", "handle message Null messageId!", new Object[0]);
                return;
            }
            String stringExtra2 = intent2.getStringExtra(AgooConstants.MESSAGE_BODY);
            String stringExtra3 = intent2.getStringExtra("task_id");
            String stringExtra4 = intent2.getStringExtra("extData");
            String stringExtra5 = intent2.getStringExtra(AgooConstants.MESSAGE_SOURCE);
            if (TextUtils.isEmpty(stringExtra2)) {
                ALog.m3727e("AgooPushHandler", "handle message json body is Empty!", new Object[0]);
                return;
            }
            Map<String, String> map = null;
            map = JsonUtility.toMap(new JSONObject(stringExtra2));
            try {
                int parseInt = Integer.parseInt(map.get("type"));
                ILog iLog = C0910h.imortantLogger;
                iLog.mo9706d("handle message, messageId:" + stringExtra + ", type:" + parseInt + ", msg receive:" + stringExtra2);
                for (Map.Entry next : map.entrySet()) {
                    ILog iLog2 = C0910h.imortantLogger;
                    iLog2.mo9706d(((String) next.getKey()) + " --> " + ((String) next.getValue()));
                }
                map.put("task_id", stringExtra3);
                map.put("extData", stringExtra4);
                map.put(AgooConstants.MESSAGE_SOURCE, stringExtra5);
                map.put(AgooConstants.MESSAGE_BODY_MSG_ID_ALIYUN_FLAG, map.get("msg_id"));
                if (C0910h.m1081b()) {
                    C0910h.imortantLogger.mo9706d("Push received in DoNotDisturb time window, ignored.");
                } else {
                    m1062a(context, iAgooPushConfig, iAgooPushCallback, stringExtra, map, parseInt);
                }
            } catch (Throwable th) {
                ALog.m3726e("AgooPushHandler", "Wrong message Type Define!", th, new Object[0]);
            }
        } catch (JSONException e) {
            ALog.m3726e("AgooPushHandler", "Parse json error:", e, new Object[0]);
        } catch (Throwable th2) {
            ALog.m3726e("AgooPushHandler", "onHandleCallException", th2, new Object[0]);
        }
    }

    /* renamed from: a */
    private static void m1063a(Context context, IAgooPushConfig iAgooPushConfig, Map<String, String> map, C0909g gVar) {
        String str = map.get("big_picture");
        if (!TextUtils.isEmpty(map.get("image")) || !TextUtils.isEmpty(str)) {
            Handler handler = null;
            if (Looper.myLooper() != null) {
                handler = new Handler(Looper.myLooper());
            }
            ThreadPoolExecutorFactory.execute(new C0905c(iAgooPushConfig, context, map, handler, gVar));
            return;
        }
        gVar.mo10175a(iAgooPushConfig.customNotificationUI(context, map));
    }

    /* renamed from: a */
    private static void m1062a(Context context, IAgooPushConfig iAgooPushConfig, IAgooPushCallback iAgooPushCallback, String str, Map<String, String> map, int i) {
        Context context2 = context;
        IAgooPushConfig iAgooPushConfig2 = iAgooPushConfig;
        IAgooPushCallback iAgooPushCallback2 = iAgooPushCallback;
        String str2 = str;
        Map<String, String> map2 = map;
        int i2 = i;
        C0916f fVar = new C0916f();
        if (i2 == 1) {
            try {
                String b = Config.m3894b(context);
                C0913c b2 = fVar.mo10238b(map, b, str);
                if (b2 != null) {
                    C0910h.m1075a(context, b2.mo10211f(), i2);
                    if (iAgooPushConfig.showNotificationNow(context, map)) {
                        m1063a(context, iAgooPushConfig, map, (C0909g) new C0907e(b2, fVar, context, iAgooPushCallback));
                        return;
                    }
                    C0910h.imortantLogger.mo9711i("do not build notification as user request");
                    iAgooPushCallback.onNotificationReceivedWithoutShow(context, b2.mo10202b(), b2.mo10205c(), b2.mo10209e(), b2.mo10198a(), b2.mo10215h(), b2.mo10207d());
                    return;
                }
                ALog.m3727e("AgooPushHandler", "Notify title is null or server push data Error appId =  " + b, new Object[0]);
            } catch (Throwable th) {
                ALog.m3726e("AgooPushHandler", "Notify message error:", th, new Object[0]);
            }
        } else if (i2 != 2) {
            ALog.m3727e("AgooPushHandler", "Wrong message Type Define!", new Object[0]);
        } else {
            try {
                CPushMessage a = fVar.mo10236a(map, Config.m3894b(context), str);
                if (a != null) {
                    C0910h.m1075a(context, a.getMessageId(), i2);
                    ALog.m3728i("AgooPushHandler", "messageId=" + a.getMessageId() + ";appId=" + a.getAppId() + ";messageType=msg", null, 1);
                    iAgooPushCallback.onMessageArrived(context, a);
                }
            } catch (Throwable th2) {
                ALog.m3726e("AgooPushHandler", "Custom message parse error:", th2, new Object[0]);
            }
        }
    }

    /* renamed from: a */
    public static void m1060a(Context context, Intent intent, IAgooPushCallback iAgooPushCallback) {
        try {
            String stringExtra = intent.getStringExtra("messageId");
            String stringExtra2 = intent.getStringExtra("title");
            String stringExtra3 = intent.getStringExtra("summary");
            String stringExtra4 = intent.getStringExtra("extraMap");
            int intExtra = intent.getIntExtra("notificationOpenType", 1);
            ILog iLog = C0910h.imortantLogger;
            iLog.mo9706d("notification opened " + stringExtra);
            iAgooPushCallback.onNotificationOpened(context, stringExtra2, stringExtra3, stringExtra4, intExtra);
        } catch (Throwable th) {
            ALog.m3726e("AgooPushHandler", "Handle notification open action failed.", th, new Object[0]);
        }
    }

    /* renamed from: b */
    public static void m1064b(Context context, Intent intent, IAgooPushCallback iAgooPushCallback) {
        try {
            String stringExtra = intent.getStringExtra("messageId");
            String stringExtra2 = intent.getStringExtra("title");
            String stringExtra3 = intent.getStringExtra("summary");
            String stringExtra4 = intent.getStringExtra("extraMap");
            int intExtra = intent.getIntExtra("notificationOpenType", 1);
            ILog iLog = C0910h.imortantLogger;
            iLog.mo9706d("notification deleted " + stringExtra);
            iAgooPushCallback.onNotificationRemoved(context, stringExtra2, stringExtra3, stringExtra4, intExtra, stringExtra);
        } catch (Throwable th) {
            ALog.m3726e("AgooPushHandler", "Handle notification delete action failed.", th, new Object[0]);
        }
    }
}
