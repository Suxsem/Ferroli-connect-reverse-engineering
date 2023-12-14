package com.igexin.push.core.p047a.p048a;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1238a;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1356s;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1285h;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.util.C1576a;
import com.igexin.sdk.PushConsts;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

/* renamed from: com.igexin.push.core.a.a.g */
public class C1246g implements C1240a {

    /* renamed from: a */
    private static final String f1869a = C1233j.f1818a;

    /* renamed from: a */
    private int m1665a(C1285h hVar, boolean z) {
        if (z) {
            int i = 0;
            try {
                i = C1343f.f2169f.getResources().getIdentifier("push_small", CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
                if (i == 0) {
                    i = C1343f.f2169f.getResources().getIdentifier("push_small", "mipmap", C1343f.f2168e);
                }
            } catch (Throwable th) {
                C1179b.m1354a(f1869a + "|" + th.toString());
            }
            if (i != 0) {
                return i;
            }
            C1179b.m1354a(f1869a + "|push_small.png is missing");
        }
        int identifier = C1343f.f2169f.getResources().getIdentifier("push", CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
        if (identifier == 0) {
            identifier = C1343f.f2169f.getResources().getIdentifier("push", "mipmap", C1343f.f2168e);
        }
        if (TextUtils.isEmpty(hVar.mo14585g())) {
            if (identifier != 0) {
                return identifier;
            }
            return 17301651;
        } else if ("null".equals(hVar.mo14585g())) {
            return 17301651;
        } else {
            if (hVar.mo14585g().startsWith("@")) {
                String g = hVar.mo14585g();
                return g.substring(1, g.length()).endsWith("email") ? 17301647 : 17301651;
            }
            int identifier2 = C1343f.f2169f.getResources().getIdentifier(hVar.mo14585g(), CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
            if (identifier2 == 0) {
                identifier2 = C1343f.f2169f.getResources().getIdentifier(hVar.mo14585g(), "mipmap", C1343f.f2168e);
            }
            if (identifier2 != 0) {
                return identifier2;
            }
            if (identifier != 0) {
                return identifier;
            }
            return 17301651;
        }
    }

    /* renamed from: a */
    private int m1666a(String str) {
        int i = 0;
        for (int i2 = 0; i2 != str.length(); i2++) {
            i = (i * 131) + str.charAt(i2);
        }
        if (i == Integer.MIN_VALUE) {
            i = 1;
        }
        return Math.abs(i);
    }

    @TargetApi(26)
    /* renamed from: a */
    private Notification.Builder m1667a(C1285h hVar) {
        Notification.Builder builder = new Notification.Builder(C1343f.f2169f);
        NotificationManager notificationManager = (NotificationManager) C1343f.f2169f.getSystemService("notification");
        try {
            Class<?> cls = Class.forName("android.app.NotificationChannel");
            Constructor<?> constructor = cls.getConstructor(new Class[]{String.class, CharSequence.class, Integer.TYPE});
            if (constructor != null) {
                Class<?> cls2 = notificationManager.getClass();
                if (((Parcelable) cls2.getMethod("getNotificationChannel", new Class[]{String.class}).invoke(notificationManager, new Object[]{hVar.mo14590k()})) == null) {
                    Parcelable parcelable = (Parcelable) constructor.newInstance(new Object[]{hVar.mo14590k(), hVar.mo14591l(), Integer.valueOf(hVar.mo14592m())});
                    Method method = cls2.getMethod("createNotificationChannel", new Class[]{Class.forName("android.app.NotificationChannel")});
                    Method method2 = cls.getMethod("enableVibration", new Class[]{Boolean.TYPE});
                    Method method3 = cls.getMethod("setSound", new Class[]{Uri.class, AudioAttributes.class});
                    method2.invoke(parcelable, new Object[]{Boolean.valueOf(hVar.mo14582e())});
                    if (!hVar.mo14584f()) {
                        method3.invoke(parcelable, new Object[]{null, null});
                    }
                    method.invoke(notificationManager, new Object[]{parcelable});
                }
                builder.getClass().getMethod("setChannelId", new Class[]{String.class}).invoke(builder, new Object[]{hVar.mo14590k()});
            }
        } catch (Throwable unused) {
        }
        return builder;
    }

    /* renamed from: a */
    private PendingIntent m1668a(String str, String str2, String str3, int i, String str4, String str5) {
        Intent intent = new Intent("com.igexin.sdk.action.doaction");
        intent.putExtra("taskid", str);
        intent.putExtra("messageid", str2);
        String str6 = "";
        if (str4 == null) {
            str4 = str6;
        }
        intent.putExtra("title", str4);
        if (str5 != null) {
            str6 = str5;
        }
        intent.putExtra("content", str6);
        intent.putExtra("appid", C1343f.f2135a);
        intent.putExtra("actionid", str3);
        intent.putExtra("accesstoken", C1343f.f2153ao);
        intent.putExtra("notifID", i);
        Intent intent2 = new Intent(C1343f.f2169f, C1356s.m2138a().mo14786c(C1343f.f2169f));
        intent2.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_NOTIFICATION_CLICK);
        intent2.putExtra("broadcast_intent", intent);
        return PendingIntent.getService(C1343f.f2169f, new Random().nextInt(1000), intent2, 134217728);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:65|66|67|68) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x0137 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.core.bean.BaseAction mo14466a(org.json.JSONObject r17) {
        /*
            r16 = this;
            r0 = r17
            java.lang.String r1 = "channel"
            java.lang.String r2 = "banner_url"
            java.lang.String r3 = "logo_url"
            java.lang.String r4 = "is_chklayout"
            java.lang.String r5 = ".png"
            java.lang.String r6 = "is_noring"
            java.lang.String r7 = "is_novibrate"
            java.lang.String r8 = "is_noclear"
            java.lang.String r9 = "notifyid"
            java.lang.String r10 = ""
            java.lang.String r11 = "logo"
            com.igexin.push.core.bean.h r12 = new com.igexin.push.core.bean.h     // Catch:{ JSONException -> 0x0166 }
            r12.<init>()     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r13 = "notification"
            r12.setType(r13)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r13 = "actionid"
            java.lang.String r13 = r0.getString(r13)     // Catch:{ JSONException -> 0x0166 }
            r12.setActionId(r13)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r13 = "do"
            java.lang.String r13 = r0.getString(r13)     // Catch:{ JSONException -> 0x0166 }
            r12.setDoActionId(r13)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r13 = "title"
            java.lang.String r13 = r0.getString(r13)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r14 = "text"
            java.lang.String r14 = r0.getString(r14)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14567a((java.lang.String) r13)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14572b((java.lang.String) r14)     // Catch:{ JSONException -> 0x0166 }
            boolean r13 = r0.has(r11)     // Catch:{ JSONException -> 0x0166 }
            if (r13 == 0) goto L_0x0096
            java.lang.String r13 = r0.getString(r11)     // Catch:{ JSONException -> 0x0166 }
            boolean r13 = r10.equals(r13)     // Catch:{ JSONException -> 0x0166 }
            if (r13 != 0) goto L_0x0096
            java.lang.String r11 = r0.getString(r11)     // Catch:{ JSONException -> 0x0166 }
            int r13 = r11.lastIndexOf(r5)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r15 = ".jpeg"
            r14 = -1
            if (r13 != r14) goto L_0x006c
            int r13 = r11.lastIndexOf(r15)     // Catch:{ JSONException -> 0x0166 }
            if (r13 == r14) goto L_0x006a
            goto L_0x006c
        L_0x006a:
            r13 = 0
            goto L_0x0092
        L_0x006c:
            int r5 = r11.indexOf(r5)     // Catch:{ JSONException -> 0x0166 }
            if (r5 != r14) goto L_0x0076
            int r5 = r11.indexOf(r15)     // Catch:{ JSONException -> 0x0166 }
        L_0x0076:
            if (r5 == r14) goto L_0x0090
            r13 = 0
            java.lang.String r5 = r11.substring(r13, r5)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r11 = "^\\d+$"
            java.util.regex.Pattern r11 = java.util.regex.Pattern.compile(r11)     // Catch:{ JSONException -> 0x0166 }
            java.util.regex.Matcher r11 = r11.matcher(r5)     // Catch:{ JSONException -> 0x0166 }
            boolean r11 = r11.matches()     // Catch:{ JSONException -> 0x0166 }
            if (r11 == 0) goto L_0x008e
            goto L_0x0092
        L_0x008e:
            r10 = r5
            goto L_0x0092
        L_0x0090:
            r13 = 0
            r10 = r11
        L_0x0092:
            r12.mo14575c((java.lang.String) r10)     // Catch:{ JSONException -> 0x0166 }
            goto L_0x0097
        L_0x0096:
            r13 = 0
        L_0x0097:
            boolean r5 = r0.has(r8)     // Catch:{ JSONException -> 0x0166 }
            r14 = 1
            if (r5 == 0) goto L_0x00aa
            boolean r5 = r0.getBoolean(r8)     // Catch:{ JSONException -> 0x0166 }
            if (r5 != 0) goto L_0x00a6
            r5 = 1
            goto L_0x00a7
        L_0x00a6:
            r5 = 0
        L_0x00a7:
            r12.mo14573b((boolean) r5)     // Catch:{ JSONException -> 0x0166 }
        L_0x00aa:
            boolean r5 = r0.has(r7)     // Catch:{ JSONException -> 0x0166 }
            if (r5 == 0) goto L_0x00bc
            boolean r5 = r0.getBoolean(r7)     // Catch:{ JSONException -> 0x0166 }
            if (r5 != 0) goto L_0x00b8
            r5 = 1
            goto L_0x00b9
        L_0x00b8:
            r5 = 0
        L_0x00b9:
            r12.mo14576c((boolean) r5)     // Catch:{ JSONException -> 0x0166 }
        L_0x00bc:
            boolean r5 = r0.has(r6)     // Catch:{ JSONException -> 0x0166 }
            if (r5 == 0) goto L_0x00cc
            boolean r5 = r0.getBoolean(r6)     // Catch:{ JSONException -> 0x0166 }
            if (r5 != 0) goto L_0x00c9
            r13 = 1
        L_0x00c9:
            r12.mo14578d((boolean) r13)     // Catch:{ JSONException -> 0x0166 }
        L_0x00cc:
            boolean r5 = r0.has(r4)     // Catch:{ JSONException -> 0x0166 }
            if (r5 == 0) goto L_0x00d9
            boolean r4 = r0.getBoolean(r4)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14581e((boolean) r4)     // Catch:{ JSONException -> 0x0166 }
        L_0x00d9:
            boolean r4 = r0.has(r3)     // Catch:{ JSONException -> 0x0166 }
            if (r4 == 0) goto L_0x00e6
            java.lang.String r3 = r0.getString(r3)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14577d((java.lang.String) r3)     // Catch:{ JSONException -> 0x0166 }
        L_0x00e6:
            boolean r3 = r0.has(r2)     // Catch:{ JSONException -> 0x0166 }
            if (r3 == 0) goto L_0x00f3
            java.lang.String r2 = r0.getString(r2)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14580e((java.lang.String) r2)     // Catch:{ JSONException -> 0x0166 }
        L_0x00f3:
            boolean r2 = r0.has(r1)     // Catch:{ JSONException -> 0x0166 }
            if (r2 == 0) goto L_0x0100
            java.lang.String r1 = r0.getString(r1)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14583f(r1)     // Catch:{ JSONException -> 0x0166 }
        L_0x0100:
            java.lang.String r1 = "channelName"
            boolean r1 = r0.has(r1)     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x0111
            java.lang.String r1 = "channelName"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14586g(r1)     // Catch:{ JSONException -> 0x0166 }
        L_0x0111:
            java.lang.String r1 = "channelLevel"
            boolean r1 = r0.has(r1)     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x0122
            java.lang.String r1 = "channelLevel"
            int r1 = r0.getInt(r1)     // Catch:{ JSONException -> 0x0166 }
            r12.mo14566a((int) r1)     // Catch:{ JSONException -> 0x0166 }
        L_0x0122:
            boolean r1 = r0.has(r9)     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x0165
            java.lang.String r1 = r0.optString(r9)     // Catch:{ NumberFormatException -> 0x0137 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0137 }
            r12.mo14571b((int) r1)     // Catch:{ NumberFormatException -> 0x0137 }
            r12.mo14568a((boolean) r14)     // Catch:{ NumberFormatException -> 0x0137 }
            goto L_0x0165
        L_0x0137:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0166 }
            r1.<init>()     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r2 = " NotificationAction.parseAction() : "
            r1.append(r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r0 = r0.optString(r9)     // Catch:{ JSONException -> 0x0166 }
            r1.append(r0)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r0 = "_"
            r1.append(r0)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r0 = r1.toString()     // Catch:{ JSONException -> 0x0166 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0166 }
            r1.<init>()     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r2 = f1869a     // Catch:{ JSONException -> 0x0166 }
            r1.append(r2)     // Catch:{ JSONException -> 0x0166 }
            r1.append(r0)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r0 = r1.toString()     // Catch:{ JSONException -> 0x0166 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ JSONException -> 0x0166 }
        L_0x0165:
            return r12
        L_0x0166:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1246g.mo14466a(org.json.JSONObject):com.igexin.push.core.bean.BaseAction");
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* renamed from: a */
    public void mo14469a(String str, String str2, C1285h hVar) {
        Notification notification;
        String str3 = str;
        C1285h hVar2 = hVar;
        int a = !hVar.mo14569a() ? m1666a(str) : hVar.mo14593n();
        C1343f.f2143ae.put(str3, Integer.valueOf(a));
        C1343f.f2144af.add(str3);
        PendingIntent a2 = m1668a(str, str2, hVar.getDoActionId(), a, hVar.mo14570b(), hVar.mo14574c());
        NotificationManager notificationManager = (NotificationManager) C1343f.f2169f.getSystemService("notification");
        int a3 = m1665a(hVar2, false);
        if (Build.VERSION.SDK_INT < 11) {
            notification = new Notification();
            notification.icon = a3;
            try {
                Method method = Class.forName("android.app.Notification").getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class});
                method.setAccessible(true);
                method.invoke(notification, new Object[]{C1343f.f2169f, hVar.mo14570b(), hVar.mo14574c(), a2});
            } catch (Exception unused) {
                C1179b.m1354a(f1869a + "reflect invoke setLatestEventInfo failed!");
                return;
            }
        } else {
            notification = (Build.VERSION.SDK_INT >= 26 ? m1667a(hVar2) : new Notification.Builder(C1343f.f2169f)).setContentTitle(hVar.mo14570b()).setContentText(hVar.mo14574c()).setSmallIcon(m1665a(hVar2, true)).setLargeIcon(BitmapFactory.decodeResource(C1343f.f2169f.getResources(), a3)).setContentIntent(a2).getNotification();
            if (C1576a.m3220f() && Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 24) {
                try {
                    Field field = Class.forName("com.android.internal.R$id").getField("right_icon");
                    field.setAccessible(true);
                    int i = field.getInt((Object) null);
                    if (!(notification.contentView == null || i == 0)) {
                        notification.contentView.setViewVisibility(i, 8);
                    }
                } catch (Exception unused2) {
                }
            }
        }
        notification.tickerText = hVar.mo14574c();
        notification.defaults = 4;
        notification.ledARGB = -16711936;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 3000;
        notification.flags = 1;
        notification.flags = hVar.mo14579d() ? notification.flags | 16 : notification.flags | 32;
        if (hVar.mo14584f()) {
            notification.defaults |= 1;
        }
        if (hVar.mo14582e()) {
            notification.defaults |= 2;
        }
        if ((hVar.mo14588i() == null && hVar.mo14587h() == null) || !hVar.mo14589j()) {
            notificationManager.notify(a, notification);
            C1238a a4 = C1238a.m1630a();
            String str4 = "";
            String b = hVar.mo14570b() == null ? str4 : hVar.mo14570b();
            if (hVar.mo14574c() != null) {
                str4 = hVar.mo14574c();
            }
            a4.mo14460b(str3, str2, b, str4);
        }
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        if (pushTaskBean == null || !(baseAction instanceof C1285h)) {
            return true;
        }
        C1285h hVar = (C1285h) baseAction;
        if (TextUtils.isEmpty(hVar.mo14570b()) && TextUtils.isEmpty(hVar.mo14574c())) {
            return true;
        }
        mo14469a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), hVar);
        return true;
    }
}
