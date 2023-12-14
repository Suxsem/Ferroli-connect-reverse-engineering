package com.aliyun.ams.emas.push.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.ams.emas.push.C0910h;
import com.aliyun.ams.emas.push.MsgService;
import com.aliyun.ams.emas.push.NotificationActivity;
import com.aliyun.ams.emas.push.p029a.C0903a;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.aliyun.ams.emas.push.notification.f */
/* compiled from: Taobao */
public class C0916f {
    public static final String APP_ID = "appId";
    public static final String EXTRA_MAP = "extraMap";
    public static final String EXT_DATA = "extData";
    public static final String MSG_ID = "msgId";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_OPEN_TYPE = "notificationOpenType";
    public static final String SUMMARY = "summary";
    public static final String TAG = "MPS:MessageNotification";
    public static final String TASK_ID = "task_id";
    public static final String TITLE = "title";

    /* renamed from: a */
    private NotificationManager f1461a;

    /* renamed from: a */
    public CPushMessage mo10236a(Map<String, String> map, String str, String str2) {
        String str3 = map.get("title");
        String str4 = map.get("content");
        String str5 = map.get("extData");
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            ALog.m3727e(TAG, "Message title or content is empty:" + map.toString(), new Object[0]);
            return null;
        }
        CPushMessage cPushMessage = new CPushMessage();
        cPushMessage.setMessageId(str2);
        cPushMessage.setAppId(str);
        cPushMessage.setTitle(str3);
        cPushMessage.setContent(str4);
        cPushMessage.setTraceInfo(str5);
        return cPushMessage;
    }

    /* renamed from: b */
    public C0913c mo10238b(Map<String, String> map, String str, String str2) {
        int i;
        Map<String, String> map2 = map;
        String str3 = map2.get("title");
        String str4 = map2.get("content");
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            ALog.m3727e(TAG, "title or content of notify is empty: " + map2, new Object[0]);
            return null;
        }
        C0913c cVar = new C0913c();
        String str5 = map2.get("open");
        if (TextUtils.isEmpty(str5)) {
            str5 = String.valueOf(1);
        }
        String str6 = map2.get("url");
        String str7 = map2.get(AgooConstants.OPEN_ACTIIVTY_NAME);
        String str8 = map2.get("ext");
        String str9 = map2.get("task_id");
        String str10 = map2.get("extData");
        String str11 = map2.get("notification_channel");
        String str12 = map2.get("notify_id");
        if (!TextUtils.isEmpty(str12)) {
            i = Integer.parseInt(str12);
        } else {
            i = C0910h.m1082c();
        }
        int i2 = i;
        cVar.mo10210e(str);
        cVar.mo10208d(str2);
        cVar.mo10216h(str9);
        cVar.mo10218i(str10);
        cVar.mo10222k(map2.get(AgooConstants.MESSAGE_SOURCE));
        cVar.mo10200a(str3);
        cVar.mo10204b(str4);
        cVar.mo10199a(Integer.parseInt(str5));
        if (TextUtils.isEmpty(str6)) {
            str6 = null;
        }
        cVar.mo10206c(str6);
        if (TextUtils.isEmpty(str7)) {
            str7 = null;
        }
        cVar.mo10212f(str7);
        cVar.mo10203b(i2);
        cVar.mo10220j(str11);
        if (!TextUtils.isEmpty(str8)) {
            try {
                Map<String, String> map3 = JsonUtility.toMap(new JSONObject(str8));
                map3.put("_ALIYUN_NOTIFICATION_ID_", String.valueOf(cVar.mo10217i()));
                if (map3.containsKey("_ALIYUN_NOTIFICATION_PRIORITY_")) {
                    cVar.mo10214g(map3.get("_ALIYUN_NOTIFICATION_PRIORITY_"));
                } else if (Build.VERSION.SDK_INT >= 16) {
                    cVar.mo10214g(String.valueOf(0));
                } else {
                    cVar.mo10214g(String.valueOf(0));
                }
                map3.put(AgooConstants.MESSAGE_BODY_MSG_ID_ALIYUN_FLAG, map2.get(AgooConstants.MESSAGE_BODY_MSG_ID_ALIYUN_FLAG));
                cVar.mo10201a(map3);
            } catch (JSONException e) {
                ALog.m3726e(TAG, "Parse inner json(ext) error:", e, new Object[0]);
            }
        }
        return cVar;
    }

    /* renamed from: a */
    public void mo10237a(Context context, Notification notification, C0913c cVar) {
        try {
            this.f1461a = (NotificationManager) context.getSystemService("notification");
            String str = "";
            if (notification == null) {
                C0911a aVar = new C0911a();
                aVar.mo10228a(cVar.mo10202b());
                aVar.mo10230b(cVar.mo10205c());
                aVar.mo10227a(cVar.mo10219j());
                aVar.mo10232c(cVar.mo10224m());
                notification = aVar.mo10193a(context);
                if (notification == null) {
                    notification = new Notification(17301623, str, System.currentTimeMillis());
                }
            }
            Intent intent = new Intent();
            intent.putExtra(APP_ID, cVar.mo10213g());
            intent.putExtra(MSG_ID, cVar.mo10211f());
            intent.putExtra("task_id", cVar.mo10221k());
            intent.putExtra("extData", cVar.mo10223l());
            intent.putExtra(AgooConstants.MESSAGE_SOURCE, cVar.mo10225n());
            intent.setFlags(270532608);
            try {
                int a = cVar.mo10198a();
                if (a == 1) {
                    str = "app";
                    intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                } else if (a == 2) {
                    str = AgooConstants.OPEN_ACTIIVTY_NAME;
                    intent.setClass(context, Class.forName(cVar.mo10215h()));
                } else if (a == 3) {
                    str = "url";
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(cVar.mo10207d()));
                } else if (a == 4) {
                    str = "no action";
                }
            } catch (Throwable th) {
                ALog.m3726e(TAG, "openType excption", th, new Object[0]);
            }
            ALog.m3728i(TAG, "open type:" + str, new Object[0]);
            notification.contentIntent = m1130a(context, cVar, intent, C0910h.m1083d());
            notification.deleteIntent = m1129a(context, cVar, C0910h.m1083d());
            ALog.m3728i(TAG, "messageId=" + cVar.mo10211f() + ";appId=" + cVar.mo10213g() + ";messageType=notify", null, 1);
        } catch (Throwable th2) {
            C0910h.imortantLogger.mo9709e("onNotification", th2);
            return;
        }
        C0903a.m1057a().mo10171a(cVar.mo10217i());
        this.f1461a.notify(cVar.mo10217i(), notification);
        C0910h.imortantLogger.mo9706d("push notify notification");
    }

    /* renamed from: a */
    private PendingIntent m1129a(Context context, C0913c cVar, int i) {
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), MsgService.class.getName());
        intent.setAction(C0910h.f1433a);
        intent.putExtra(AgooConstants.ACTION_TYPE, AgooConstants.NOTIFICATION_TYPE_DELETE);
        intent.putExtra("task_id", cVar.mo10221k());
        intent.putExtra("extData", cVar.mo10223l());
        intent.putExtra(MSG_ID, cVar.mo10211f());
        intent.putExtra("title", cVar.mo10202b());
        intent.putExtra("summary", cVar.mo10205c());
        intent.putExtra("notificationOpenType", cVar.mo10198a());
        intent.putExtra("notificationId", cVar.mo10217i());
        if (cVar.mo10209e() != null) {
            intent.putExtra("extraMap", new JSONObject(cVar.mo10209e()).toString());
        }
        ALog.m3725d(TAG, "delete content messageId:" + cVar.mo10211f(), new Object[0]);
        intent.putExtra(APP_ID, cVar.mo10213g());
        if (Build.VERSION.SDK_INT >= 23) {
            return PendingIntent.getService(context, i, intent, 201326592);
        }
        return PendingIntent.getService(context, i, intent, 134217728);
    }

    /* renamed from: a */
    private PendingIntent m1130a(Context context, C0913c cVar, Intent intent, int i) {
        Intent intent2 = new Intent();
        if (Build.VERSION.SDK_INT > 30 || context.getApplicationInfo().targetSdkVersion > 30) {
            intent2.setClassName(context.getPackageName(), NotificationActivity.class.getName());
        } else {
            intent2.setClassName(context.getPackageName(), MsgService.class.getName());
        }
        intent2.setAction(C0910h.f1433a);
        intent2.putExtra(AgooConstants.ACTION_TYPE, AgooConstants.NOTIFICATION_TYPE_OPEN);
        intent2.putExtra("task_id", cVar.mo10221k());
        intent2.putExtra("extData", cVar.mo10223l());
        intent.putExtra("title", cVar.mo10202b());
        intent.putExtra("summary", cVar.mo10205c());
        intent.putExtra(MSG_ID, cVar.mo10211f());
        intent.putExtra(APP_ID, cVar.mo10213g());
        intent.putExtra("notificationOpenType", cVar.mo10198a());
        intent.putExtra("notificationId", cVar.mo10217i());
        intent2.putExtra(MSG_ID, cVar.mo10211f());
        if (cVar.mo10209e() != null) {
            intent.putExtra("extraMap", new JSONObject(cVar.mo10209e()).toString());
        }
        ALog.m3725d(TAG, "build content messageId:" + cVar.mo10211f(), new Object[0]);
        intent2.putExtra(AgooConstants.KEY_REAL_INTENT, intent);
        if (Build.VERSION.SDK_INT > 30 || context.getApplicationInfo().targetSdkVersion > 30) {
            return PendingIntent.getActivity(context, i, intent2, 201326592);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return PendingIntent.getService(context, i, intent2, 201326592);
        }
        return PendingIntent.getService(context, i, intent2, 134217728);
    }
}
