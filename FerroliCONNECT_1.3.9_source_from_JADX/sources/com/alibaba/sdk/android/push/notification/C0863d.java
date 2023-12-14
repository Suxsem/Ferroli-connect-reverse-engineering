package com.alibaba.sdk.android.push.notification;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.alibaba.sdk.android.push.common.p020a.C0802b;
import com.alibaba.sdk.android.push.common.util.JSONUtils;
import com.aliyun.ams.emas.push.notification.C0916f;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alibaba.sdk.android.push.notification.d */
public class C0863d {

    /* renamed from: a */
    static AmsLogger f1304a = AmsLogger.getLogger(C0916f.TAG);

    /* renamed from: a */
    private Uri m979a(Context context, C0861b bVar) {
        String str;
        Uri parse;
        Uri uri = Uri.EMPTY;
        if (!TextUtils.isEmpty(bVar.mo10026a())) {
            String a = bVar.mo10026a();
            if (a.startsWith("android.resource://")) {
                parse = Uri.parse(a);
            } else {
                if (a.startsWith("/raw/")) {
                    str = "android.resource://" + context.getPackageName() + a;
                } else {
                    str = "android.resource://" + context.getPackageName() + "/raw/" + a;
                }
                parse = Uri.parse(str);
            }
            uri = parse;
        } else if (C0802b.m765a() != null) {
            uri = Uri.parse(C0802b.m765a());
        } else {
            int identifier = context.getResources().getIdentifier("alicloud_notification_sound", "raw", context.getPackageName());
            f1304a.mo9538d("sound resId:" + identifier);
            if (identifier != 0) {
                uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + identifier);
                f1304a.mo9538d("sound resId:" + identifier + "  ;uri:" + uri.toString());
            }
        }
        if (uri == Uri.EMPTY) {
            uri = RingtoneManager.getDefaultUri(2);
        }
        f1304a.mo9538d("soundUri:" + uri.toString());
        return uri;
    }

    /* renamed from: a */
    private void m980a(Context context, C0861b bVar, Notification notification) {
        String str;
        String str2;
        Uri parse;
        long[] jArr = {100, 250, 100, 250, 100, 250};
        int d = bVar.mo10037d();
        if (d == 0) {
            return;
        }
        if (d != 1) {
            if (d != 2) {
                if (d == 3) {
                    notification.vibrate = jArr;
                } else {
                    return;
                }
            }
            Uri uri = Uri.EMPTY;
            if (!TextUtils.isEmpty(bVar.mo10026a())) {
                String a = bVar.mo10026a();
                if (a.startsWith("android.resource://")) {
                    parse = Uri.parse(a);
                } else {
                    if (a.startsWith("/raw/")) {
                        str2 = "android.resource://" + context.getPackageName() + a;
                    } else {
                        str2 = "android.resource://" + context.getPackageName() + "/raw/" + a;
                    }
                    parse = Uri.parse(str2);
                }
                uri = parse;
            } else {
                if (C0802b.m765a() != null) {
                    str = C0802b.m765a();
                } else {
                    int identifier = context.getResources().getIdentifier("alicloud_notification_sound", "raw", context.getPackageName());
                    if (identifier != 0) {
                        str = "android.resource://" + context.getPackageName() + "/" + identifier;
                    }
                }
                uri = Uri.parse(str);
            }
            if (uri == Uri.EMPTY) {
                uri = RingtoneManager.getDefaultUri(2);
            }
            notification.sound = uri;
            return;
        }
        notification.vibrate = jArr;
    }

    /* renamed from: a */
    public static boolean m981a(Map<String, String> map) {
        int parseInt;
        BasicCustomPushNotification a;
        if (!map.containsKey(PushData.KEY_CUSTOM_NOTIFICAITON_ID) || (parseInt = Integer.parseInt(map.get(PushData.KEY_CUSTOM_NOTIFICAITON_ID))) == 0 || (a = CustomNotificationBuilder.getInstance().mo10002a(parseInt)) == null) {
            return true;
        }
        return a.isBuildWhenAppInForeground();
    }

    /* renamed from: b */
    private void m982b(Context context, C0861b bVar, Notification notification) {
        f1304a.mo9538d("custom notification feedback");
        long[] jArr = {100, 250, 100, 250, 100, 250};
        if (bVar.mo10052i() != 0) {
            if (2 == bVar.mo10052i()) {
                notification.sound = m979a(context, bVar);
                return;
            }
            if (1 != bVar.mo10052i()) {
                if (3 == bVar.mo10052i()) {
                    notification.sound = m979a(context, bVar);
                } else {
                    notification.defaults = -1;
                    return;
                }
            }
            notification.vibrate = jArr;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0091 A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00a2 A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00ba A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0077 A[Catch:{ Throwable -> 0x00be }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.Notification mo10088a(android.content.Context r7, com.alibaba.sdk.android.push.notification.C0861b r8, com.alibaba.sdk.android.push.notification.PushData r9, com.alibaba.sdk.android.push.notification.NotificationConfigure r10) {
        /*
            r6 = this;
            com.alibaba.sdk.android.push.notification.a r0 = new com.alibaba.sdk.android.push.notification.a     // Catch:{ Throwable -> 0x00be }
            r0.<init>()     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10031b()     // Catch:{ Throwable -> 0x00be }
            r0.mo10075a((java.lang.String) r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10034c()     // Catch:{ Throwable -> 0x00be }
            r0.mo10078b((java.lang.String) r1)     // Catch:{ Throwable -> 0x00be }
            int r1 = r8.mo10065p()     // Catch:{ Throwable -> 0x00be }
            r0.mo10074a((int) r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10066q()     // Catch:{ Throwable -> 0x00be }
            r0.mo10080c(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10067r()     // Catch:{ Throwable -> 0x00be }
            r0.mo10082d(r1)     // Catch:{ Throwable -> 0x00be }
            int r1 = r8.mo10068s()     // Catch:{ Throwable -> 0x00be }
            r0.mo10077b((int) r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10069t()     // Catch:{ Throwable -> 0x00be }
            r0.mo10083e(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10070u()     // Catch:{ Throwable -> 0x00be }
            r0.mo10084f(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10071v()     // Catch:{ Throwable -> 0x00be }
            r0.mo10085g(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10072w()     // Catch:{ Throwable -> 0x00be }
            r0.mo10086h(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = r8.mo10026a()     // Catch:{ Throwable -> 0x00be }
            r0.mo10087i(r1)     // Catch:{ Throwable -> 0x00be }
            int r1 = r8.mo10046g()     // Catch:{ Throwable -> 0x00be }
            r2 = 1
            if (r2 == r1) goto L_0x0071
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r1 = f1304a     // Catch:{ Throwable -> 0x00be }
            java.lang.String r3 = "building customNotification"
            r1.mo9538d(r3)     // Catch:{ Throwable -> 0x00be }
            com.alibaba.sdk.android.push.notification.CustomNotificationBuilder r1 = com.alibaba.sdk.android.push.notification.CustomNotificationBuilder.getInstance()     // Catch:{ Throwable -> 0x00be }
            android.app.Notification r1 = r1.mo10001a(r7, r8, r9, r10)     // Catch:{ Throwable -> 0x00be }
            if (r1 != 0) goto L_0x0075
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r1 = f1304a     // Catch:{ Throwable -> 0x00be }
            java.lang.String r3 = "build custom notification failed, build default notification"
            r1.mo9541e(r3)     // Catch:{ Throwable -> 0x00be }
        L_0x0071:
            android.app.Notification r1 = r0.mo10025a(r7, r9, r10)     // Catch:{ Throwable -> 0x00be }
        L_0x0075:
            if (r1 != 0) goto L_0x0085
            android.app.Notification r1 = new android.app.Notification     // Catch:{ Throwable -> 0x00be }
            r0 = 17301623(0x1080077, float:2.4979588E-38)
            java.lang.String r3 = ""
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00be }
            r1.<init>(r0, r3, r4)     // Catch:{ Throwable -> 0x00be }
        L_0x0085:
            int r0 = r8.mo10046g()     // Catch:{ Throwable -> 0x00be }
            if (r2 == r0) goto L_0x00a2
            boolean r0 = r8.mo10064o()     // Catch:{ Throwable -> 0x00be }
            if (r0 != 0) goto L_0x00a2
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1304a     // Catch:{ Throwable -> 0x00be }
            java.lang.String r2 = "custom notification option first"
            r0.mo9538d(r2)     // Catch:{ Throwable -> 0x00be }
            r6.m982b(r7, r8, r1)     // Catch:{ Throwable -> 0x00be }
            int r7 = r8.mo10049h()     // Catch:{ Throwable -> 0x00be }
            r1.flags = r7     // Catch:{ Throwable -> 0x00be }
            goto L_0x00b8
        L_0x00a2:
            r6.m980a(r7, r8, r1)     // Catch:{ Throwable -> 0x00be }
            boolean r7 = r8.mo10042e()     // Catch:{ Throwable -> 0x00be }
            if (r7 != 0) goto L_0x00b2
            int r7 = r1.flags     // Catch:{ Throwable -> 0x00be }
            r7 = r7 | 16
            r1.flags = r7     // Catch:{ Throwable -> 0x00be }
            goto L_0x00b8
        L_0x00b2:
            int r7 = r1.flags     // Catch:{ Throwable -> 0x00be }
            r7 = r7 | 32
            r1.flags = r7     // Catch:{ Throwable -> 0x00be }
        L_0x00b8:
            if (r10 == 0) goto L_0x00bd
            r10.configNotification(r1, r9)     // Catch:{ Throwable -> 0x00be }
        L_0x00bd:
            return r1
        L_0x00be:
            r7 = move-exception
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r8 = f1304a
            java.lang.String r9 = "onNotification"
            r8.mo9542e(r9, r7)
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.notification.C0863d.mo10088a(android.content.Context, com.alibaba.sdk.android.push.notification.b, com.alibaba.sdk.android.push.notification.PushData, com.alibaba.sdk.android.push.notification.NotificationConfigure):android.app.Notification");
    }

    /* renamed from: a */
    public C0861b mo10089a(Context context, Map<String, String> map) {
        Map<String, String> map2 = map;
        String str = map2.get("title");
        String str2 = map2.get("content");
        String str3 = null;
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(str2)) {
            AmsLogger amsLogger = f1304a;
            amsLogger.mo9541e("title or content of notify is empty: " + map2);
            return null;
        }
        C0861b bVar = new C0861b();
        String str4 = map2.get("remind");
        if (StringUtil.isEmpty(str4)) {
            str4 = String.valueOf(3);
        }
        String str5 = map2.get("music");
        String str6 = map2.get("ext");
        String str7 = map2.get("style");
        String str8 = map2.get("title");
        String str9 = map2.get("big_body");
        String str10 = map2.get("big_picture");
        String str11 = map2.get("inbox_content");
        bVar.mo10033b(str);
        bVar.mo10039d(str2);
        bVar.mo10036c(str2);
        bVar.mo10027a(Integer.parseInt(str4));
        bVar.mo10045f(map2.get("notification_channel"));
        bVar.mo10048g(map2.get("image"));
        if (!TextUtils.isEmpty(str7)) {
            try {
                bVar.mo10059k(Integer.parseInt(str7));
            } catch (Throwable unused) {
            }
        }
        bVar.mo10051h(str8);
        bVar.mo10054i(str9);
        bVar.mo10057j(str10);
        bVar.mo10060k(str11);
        if (!StringUtil.isEmpty(str5)) {
            str3 = str5;
        }
        bVar.mo10028a(str3);
        if (!StringUtil.isEmpty(str6)) {
            try {
                Map<String, String> map3 = JSONUtils.toMap(new JSONObject(str6));
                bVar.mo10041e(map3.containsKey("_ALIYUN_NOTIFICATION_PRIORITY_") ? map3.get("_ALIYUN_NOTIFICATION_PRIORITY_") : Build.VERSION.SDK_INT >= 16 ? String.valueOf(0) : String.valueOf(0));
                bVar.mo10029a(map3);
            } catch (JSONException e) {
                f1304a.mo9542e("Parse inner json(ext) error:", e);
            }
        }
        if (map2.containsKey(PushData.KEY_CUSTOM_NOTIFICAITON_ID)) {
            int parseInt = Integer.parseInt(map2.get(PushData.KEY_CUSTOM_NOTIFICAITON_ID));
            if (parseInt != 0) {
                BasicCustomPushNotification a = CustomNotificationBuilder.getInstance().mo10002a(parseInt);
                if (a == null) {
                    f1304a.mo9547w("custom notification is null");
                } else {
                    bVar.mo10035c(a.getNotificationType());
                    bVar.mo10032b(a.getStatusBarDrawable());
                    bVar.mo10040e(a.getRemindType());
                    bVar.mo10038d(a.getNotificationFlags());
                    bVar.mo10030a(a.isServerOptionFirst());
                    if (3 == a.getNotificationType()) {
                        AdvancedCustomPushNotification advancedCustomPushNotification = (AdvancedCustomPushNotification) a;
                        bVar.mo10053i(advancedCustomPushNotification.getContentView());
                        bVar.mo10044f(advancedCustomPushNotification.getNotificationView());
                        bVar.mo10050h(advancedCustomPushNotification.getTitleView());
                        bVar.mo10047g(advancedCustomPushNotification.getIconView());
                        bVar.mo10056j(advancedCustomPushNotification.getIcon());
                    }
                }
            } else {
                f1304a.mo9538d("default notification");
            }
        }
        return bVar;
    }
}
