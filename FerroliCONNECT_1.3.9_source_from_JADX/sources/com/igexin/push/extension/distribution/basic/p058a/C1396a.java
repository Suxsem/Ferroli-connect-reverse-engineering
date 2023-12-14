package com.igexin.push.extension.distribution.basic.p058a;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.RemoteViews;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p060b.C1406a;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p062d.C1417a;
import com.igexin.push.extension.distribution.basic.p064f.C1422a;
import com.igexin.push.extension.distribution.basic.p064f.C1425d;
import com.igexin.push.extension.distribution.basic.p067i.C1432a;
import com.igexin.push.extension.distribution.basic.p068j.C1435c;
import com.igexin.push.extension.distribution.basic.p068j.C1437e;
import com.igexin.push.extension.distribution.basic.p068j.C1438f;
import com.igexin.push.extension.distribution.basic.p068j.C1440h;
import com.igexin.push.extension.distribution.basic.p068j.C1441i;
import com.igexin.push.extension.distribution.basic.p068j.C1442j;
import com.igexin.sdk.PushBuildConfig;
import com.igexin.sdk.PushConsts;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.extension.distribution.basic.a.a */
public class C1396a implements C1240a {

    /* renamed from: a */
    private static final String f2345a = ("EXT-" + C1396a.class.getName());

    /* renamed from: a */
    private int m2265a(C1406a aVar, boolean z) {
        if (z) {
            int identifier = C1343f.f2169f.getResources().getIdentifier("push_small", CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
            if (identifier == 0) {
                identifier = C1343f.f2169f.getResources().getIdentifier("push_small", "mipmap", C1343f.f2168e);
            }
            if (identifier != 0) {
                C1179b.m1354a(f2345a + "|push_small.png is set, use default push_small");
                return identifier;
            }
            C1179b.m1354a(f2345a + "|push_small.png is missing");
        }
        int identifier2 = C1343f.f2169f.getResources().getIdentifier("push", CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
        if (identifier2 == 0) {
            identifier2 = C1343f.f2169f.getResources().getIdentifier("push", "mipmap", C1343f.f2168e);
        }
        if (TextUtils.isEmpty(aVar.mo14908t())) {
            if (identifier2 != 0) {
                return identifier2;
            }
            return 17301651;
        } else if ("null".equals(aVar.mo14908t())) {
            return 17301651;
        } else {
            if (aVar.mo14908t().startsWith("@")) {
                String t = aVar.mo14908t();
                return t.substring(1, t.length()).endsWith("email") ? 17301647 : 17301651;
            }
            int identifier3 = C1343f.f2169f.getResources().getIdentifier(aVar.mo14908t(), CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, C1343f.f2168e);
            if (identifier3 == 0) {
                identifier3 = C1343f.f2169f.getResources().getIdentifier(aVar.mo14908t(), "mipmap", C1343f.f2168e);
            }
            if (identifier3 != 0) {
                return identifier3;
            }
            if (identifier2 != 0) {
                return identifier2;
            }
            return 17301651;
        }
    }

    /* renamed from: a */
    private int m2266a(String str) {
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
    private Notification.Builder m2267a(C1406a aVar) {
        Notification.Builder builder = new Notification.Builder(C1343f.f2169f);
        NotificationManager notificationManager = (NotificationManager) C1343f.f2169f.getSystemService("notification");
        try {
            Class<?> cls = Class.forName("android.app.NotificationChannel");
            Constructor<?> constructor = cls.getConstructor(new Class[]{String.class, CharSequence.class, Integer.TYPE});
            if (constructor != null) {
                Class<?> cls2 = notificationManager.getClass();
                if (((Parcelable) cls2.getMethod("getNotificationChannel", new Class[]{String.class}).invoke(notificationManager, new Object[]{aVar.mo14867c()})) == null) {
                    Parcelable parcelable = (Parcelable) constructor.newInstance(new Object[]{aVar.mo14867c(), aVar.mo14871d(), Integer.valueOf(aVar.mo14875e())});
                    Method method = cls2.getMethod("createNotificationChannel", new Class[]{Class.forName("android.app.NotificationChannel")});
                    Method method2 = cls.getMethod("enableVibration", new Class[]{Boolean.TYPE});
                    Method method3 = cls.getMethod("setSound", new Class[]{Uri.class, AudioAttributes.class});
                    method2.invoke(parcelable, new Object[]{Boolean.valueOf(aVar.mo14906r())});
                    if (!aVar.mo14907s()) {
                        method3.invoke(parcelable, new Object[]{null, null});
                    }
                    method.invoke(notificationManager, new Object[]{parcelable});
                }
                builder.getClass().getMethod("setChannelId", new Class[]{String.class}).invoke(builder, new Object[]{aVar.mo14867c()});
            }
        } catch (Exception unused) {
        }
        return builder;
    }

    /* renamed from: a */
    private Notification m2268a(Notification notification, C1406a aVar) {
        notification.defaults = 4;
        notification.ledARGB = -16711936;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 3000;
        notification.flags = 1;
        if (aVar.mo14907s()) {
            notification.defaults |= 1;
        }
        notification.flags = aVar.mo14905q() ? notification.flags | 16 : notification.flags | 32;
        if (aVar.mo14906r()) {
            notification.defaults |= 2;
        }
        notification.icon = m2265a(aVar, true);
        return notification;
    }

    /* renamed from: a */
    private PendingIntent m2269a(String str, String str2, int i, C1406a aVar, boolean z) {
        Intent intent = new Intent("com.igexin.sdk.action.notification.burying.point");
        intent.putExtra("taskid", str);
        intent.putExtra("messageid", str2);
        intent.putExtra("appid", C1343f.f2135a);
        intent.putExtra("actionid", aVar.getDoActionId());
        intent.putExtra("accesstoken", C1416f.f2426d);
        intent.putExtra("notifID", i);
        intent.putExtra("notifyStyle", aVar.mo14857C() + "");
        intent.putExtra(AgooConstants.MESSAGE_ID, aVar.mo14883g() + "");
        intent.putExtra("bigStyle", aVar.mo14893j() + "");
        intent.putExtra("isFloat", z);
        intent.putExtra("checkpackage", C1343f.f2169f.getPackageName());
        intent.putExtra("feedbackid", aVar.getActionId().substring(aVar.getActionId().length() - 1));
        String o = aVar.mo14903o();
        if (o == null) {
            o = "";
        }
        intent.putExtra("title", o);
        String p = aVar.mo14904p();
        if (p == null) {
            p = "";
        }
        intent.putExtra("content", p);
        if (C1438f.m2518a(PushBuildConfig.sdk_conf_version, "2.11.0.0") < 0) {
            return PendingIntent.getBroadcast(C1343f.f2169f, new Random().nextInt(1000), intent, 134217728);
        }
        try {
            Intent intent2 = new Intent(C1343f.f2169f, Class.forName(C1440h.m2522a(C1416f.f2423a)));
            intent2.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_NOTIFICATION_CLICK);
            intent2.putExtra("broadcast_intent", intent);
            if (Build.VERSION.SDK_INT >= 26 && C1437e.m2516a()) {
                Class<PendingIntent> cls = PendingIntent.class;
                try {
                    return (PendingIntent) cls.getDeclaredMethod("getForegroundService", new Class[]{Context.class, Integer.TYPE, Intent.class, Integer.TYPE}).invoke((Object) null, new Object[]{C1343f.f2169f, Integer.valueOf(new Random().nextInt(1000)), intent2, 134217728});
                } catch (Throwable unused) {
                }
            }
            return PendingIntent.getService(C1343f.f2169f, new Random().nextInt(1000), intent2, 134217728);
        } catch (Throwable unused2) {
            return PendingIntent.getBroadcast(C1343f.f2169f, new Random().nextInt(1000), intent, 134217728);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2270a(java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17) {
        /*
            r13 = this;
            r1 = r13
            java.lang.String r2 = "4.3.8.0"
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            if (r0 != 0) goto L_0x0008
            return
        L_0x0008:
            r3 = 0
            r4 = 1902131808(0x71603a60, float:1.1103234E30)
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00a6 }
            java.lang.Class r0 = r13.m2274b(r0)     // Catch:{ Throwable -> 0x00a6 }
            if (r0 == 0) goto L_0x00da
            java.lang.String r5 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Throwable -> 0x00a6 }
            if (r5 == 0) goto L_0x00da
            android.content.Intent r5 = new android.content.Intent     // Catch:{ Throwable -> 0x00a6 }
            android.content.Context r6 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00a6 }
            r5.<init>(r6, r0)     // Catch:{ Throwable -> 0x00a6 }
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ Throwable -> 0x00a4 }
            r0.<init>()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r3 = "action"
            r6 = 10011(0x271b, float:1.4028E-41)
            r0.putInt(r3, r6)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r3 = "com.igexin.sdk.message.GTNotificationMessage"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r6 = "2.12.0.0"
            int r6 = com.igexin.push.extension.distribution.basic.p068j.C1438f.m2518a(r2, r6)     // Catch:{ Throwable -> 0x00a4 }
            r7 = 1
            r8 = 0
            r9 = 2
            if (r6 != 0) goto L_0x005c
            java.lang.Class[] r6 = new java.lang.Class[r9]     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r6[r8] = r10     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r6[r7] = r10     // Catch:{ Throwable -> 0x00a4 }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r6)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r6 = "notification_arrive"
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x00a4 }
            r9[r8] = r14     // Catch:{ Throwable -> 0x00a4 }
            r9[r7] = r15     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Object r3 = r3.newInstance(r9)     // Catch:{ Throwable -> 0x00a4 }
            java.io.Serializable r3 = (java.io.Serializable) r3     // Catch:{ Throwable -> 0x00a4 }
            r0.putSerializable(r6, r3)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0089
        L_0x005c:
            r6 = 4
            java.lang.Class[] r10 = new java.lang.Class[r6]     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            r10[r8] = r11     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            r10[r7] = r11     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            r10[r9] = r11     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            r12 = 3
            r10[r12] = r11     // Catch:{ Throwable -> 0x00a4 }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r10)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r10 = "notification_arrived"
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00a4 }
            r6[r8] = r14     // Catch:{ Throwable -> 0x00a4 }
            r6[r7] = r15     // Catch:{ Throwable -> 0x00a4 }
            r6[r9] = r16     // Catch:{ Throwable -> 0x00a4 }
            r6[r12] = r17     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Object r3 = r3.newInstance(r6)     // Catch:{ Throwable -> 0x00a4 }
            java.io.Serializable r3 = (java.io.Serializable) r3     // Catch:{ Throwable -> 0x00a4 }
            r0.putSerializable(r10, r3)     // Catch:{ Throwable -> 0x00a4 }
        L_0x0089:
            r5.putExtras(r0)     // Catch:{ Throwable -> 0x00a4 }
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00a4 }
            boolean r0 = r13.m2272a((android.content.Context) r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r0 == 0) goto L_0x009e
            com.igexin.push.extension.distribution.basic.c.a r0 = com.igexin.push.extension.distribution.basic.p061c.C1411a.m2404a()     // Catch:{ Throwable -> 0x00a4 }
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00a4 }
            r0.mo14939a(r3, r5, r4)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x00da
        L_0x009e:
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00a4 }
            r0.startService(r5)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x00da
        L_0x00a4:
            r0 = move-exception
            goto L_0x00a8
        L_0x00a6:
            r0 = move-exception
            r5 = r3
        L_0x00a8:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = f2345a
            r3.append(r6)
            java.lang.String r6 = "|"
            r3.append(r6)
            java.lang.String r6 = r0.toString()
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r3)
            boolean r0 = r0 instanceof java.lang.IllegalStateException
            if (r0 == 0) goto L_0x00da
            java.lang.String r0 = "2.13.1.0"
            int r0 = com.igexin.push.extension.distribution.basic.p068j.C1438f.m2518a(r2, r0)
            if (r0 < 0) goto L_0x00da
            com.igexin.push.extension.distribution.basic.c.a r0 = com.igexin.push.extension.distribution.basic.p061c.C1411a.m2404a()
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f
            r0.mo14939a(r2, r5, r4)
        L_0x00da:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1396a.m2270a(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    private boolean m2271a() {
        C1432a a = C1432a.m2496a(C1416f.f2423a);
        if (a.mo14986a("getui_notification", "layout") != 0) {
            return (a.mo14986a("getui_notification_style1", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_style2", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_style3", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_icon", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_bg", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_date", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_icon2", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_style1_title", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_style1_content", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification__style2_title", AgooConstants.MESSAGE_ID) == 0 || a.mo14986a("getui_notification_style3_content", AgooConstants.MESSAGE_ID) == 0) ? false : true;
        }
    }

    /* renamed from: a */
    private boolean m2272a(Context context) {
        return C1438f.m2518a(PushBuildConfig.sdk_conf_version, "2.13.1.0") >= 0 && C1435c.m2512d() && !C1435c.m2509b(context.getPackageName());
    }

    /* renamed from: b */
    private int m2273b() {
        try {
            Field field = Class.forName("com.android.internal.R$id").getField("icon");
            field.setAccessible(true);
            return field.getInt((Object) null);
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: b */
    private Class m2274b(Context context) {
        try {
            C1417a.m2423a();
            String d = C1417a.m2425d();
            if (!TextUtils.isEmpty(d)) {
                return Class.forName(d);
            }
            return null;
        } catch (Throwable th) {
            C1179b.m1354a(f2345a + "|" + th.toString());
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0103, code lost:
        if (r1 >= 1) goto L_0x0106;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        r9.mo14868c(0);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:150:0x029d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:86:0x018d */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x01fd A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0213 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0229 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x023d A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x024e A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x025f A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0270 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x028e A[SYNTHETIC, Splitter:B:148:0x028e] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0053 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x009f A[SYNTHETIC, Splitter:B:29:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00e5 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0128 A[SYNTHETIC, Splitter:B:60:0x0128] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x017a A[Catch:{ Exception -> 0x018d }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01a2 A[Catch:{ Exception -> 0x02cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01aa A[Catch:{ Exception -> 0x02cc }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.core.bean.BaseAction mo14466a(org.json.JSONObject r17) {
        /*
            r16 = this;
            r0 = r17
            java.lang.String r1 = "notifyStyle"
            java.lang.String r2 = "notifyid"
            java.lang.String r3 = "big_text"
            java.lang.String r4 = "big_image_url"
            java.lang.String r5 = "logo"
            java.lang.String r6 = "logo_url"
            java.lang.String r7 = "banner_url"
            r8 = 0
            com.igexin.push.extension.distribution.basic.b.a r9 = new com.igexin.push.extension.distribution.basic.b.a     // Catch:{ Exception -> 0x02cc }
            r9.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r10 = "notification"
            r9.setType(r10)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r10 = "actionid"
            java.lang.String r10 = r0.getString(r10)     // Catch:{ Exception -> 0x02cc }
            r9.setActionId(r10)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r10 = "do"
            java.lang.String r10 = r0.getString(r10)     // Catch:{ Exception -> 0x02cc }
            r9.setDoActionId(r10)     // Catch:{ Exception -> 0x02cc }
            boolean r10 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            r11 = 0
            if (r10 == 0) goto L_0x0039
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x0039 }
            goto L_0x003a
        L_0x0039:
            r1 = 0
        L_0x003a:
            java.lang.String r10 = "id"
            boolean r10 = r0.has(r10)     // Catch:{ Exception -> 0x02cc }
            if (r10 == 0) goto L_0x004b
            java.lang.String r10 = "id"
            java.lang.String r10 = r0.getString(r10)     // Catch:{ Exception -> 0x02cc }
            r9.mo14869c((java.lang.String) r10)     // Catch:{ Exception -> 0x02cc }
        L_0x004b:
            java.lang.String r10 = "title"
            boolean r10 = r0.has(r10)     // Catch:{ Exception -> 0x02cc }
            if (r10 == 0) goto L_0x005c
            java.lang.String r10 = "title"
            java.lang.String r10 = r0.getString(r10)     // Catch:{ Exception -> 0x02cc }
            r9.mo14885g((java.lang.String) r10)     // Catch:{ Exception -> 0x02cc }
        L_0x005c:
            java.lang.String r10 = "text"
            boolean r10 = r0.has(r10)     // Catch:{ Exception -> 0x02cc }
            if (r10 == 0) goto L_0x006d
            java.lang.String r10 = "text"
            java.lang.String r10 = r0.getString(r10)     // Catch:{ Exception -> 0x02cc }
            r9.mo14888h((java.lang.String) r10)     // Catch:{ Exception -> 0x02cc }
        L_0x006d:
            java.lang.String r10 = r9.mo14903o()     // Catch:{ Exception -> 0x02cc }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02cc }
            r12 = 4
            if (r10 == 0) goto L_0x009b
            java.lang.String r10 = r9.mo14904p()     // Catch:{ Exception -> 0x02cc }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02cc }
            if (r10 == 0) goto L_0x009b
            if (r1 == r12) goto L_0x009b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            r0.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = f2345a     // Catch:{ Exception -> 0x02cc }
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = "|title and content is empty, not support"
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x02cc }
            return r8
        L_0x009b:
            java.lang.String r10 = "http"
            if (r1 != r12) goto L_0x00e5
            java.lang.String r1 = f2345a     // Catch:{ Exception -> 0x02cc }
            java.lang.String r13 = "parse notify style 4"
            com.igexin.p032b.p033a.p039c.C1179b.m1355a(r1, r13)     // Catch:{ Exception -> 0x02cc }
            r9.mo14887h((int) r12)     // Catch:{ Exception -> 0x02cc }
            boolean r1 = r16.m2271a()     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x00c7
            boolean r1 = r0.has(r7)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x00ef
            java.lang.String r1 = r0.getString(r7)     // Catch:{ Exception -> 0x02cc }
            boolean r1 = r1.startsWith(r10)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x00ef
            java.lang.String r1 = r0.getString(r7)     // Catch:{ Exception -> 0x02cc }
            r9.mo14896k(r1)     // Catch:{ Exception -> 0x02cc }
            goto L_0x00ef
        L_0x00c7:
            java.lang.String r0 = f2345a     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = "getui_notification.xml doesn't exist"
            com.igexin.p032b.p033a.p039c.C1179b.m1355a(r0, r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            r0.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = f2345a     // Catch:{ Exception -> 0x02cc }
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = "getui_notification.xml doesn't exist"
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x02cc }
            return r8
        L_0x00e5:
            java.lang.String r1 = f2345a     // Catch:{ Exception -> 0x02cc }
            java.lang.String r7 = "parse notify style 0"
            com.igexin.p032b.p033a.p039c.C1179b.m1355a(r1, r7)     // Catch:{ Exception -> 0x02cc }
            r9.mo14887h((int) r11)     // Catch:{ Exception -> 0x02cc }
        L_0x00ef:
            java.lang.String r1 = "bigStyle"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            r7 = 3
            r13 = 1
            if (r1 == 0) goto L_0x0105
            java.lang.String r1 = "bigStyle"
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x0100 }
            goto L_0x0101
        L_0x0100:
            r1 = 0
        L_0x0101:
            if (r1 > r7) goto L_0x0105
            if (r1 >= r13) goto L_0x0106
        L_0x0105:
            r1 = 0
        L_0x0106:
            r9.mo14872d((int) r1)     // Catch:{ Exception -> 0x02cc }
            boolean r14 = r0.has(r6)     // Catch:{ Exception -> 0x02cc }
            if (r14 == 0) goto L_0x0120
            java.lang.String r14 = r0.getString(r6)     // Catch:{ Exception -> 0x02cc }
            boolean r14 = r14.startsWith(r10)     // Catch:{ Exception -> 0x02cc }
            if (r14 == 0) goto L_0x0120
            java.lang.String r6 = r0.getString(r6)     // Catch:{ Exception -> 0x02cc }
            r9.mo14894j(r6)     // Catch:{ Exception -> 0x02cc }
        L_0x0120:
            boolean r6 = r0.has(r5)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r14 = ""
            if (r6 == 0) goto L_0x0172
            java.lang.String r6 = r0.getString(r5)     // Catch:{ Exception -> 0x02cc }
            boolean r6 = r14.equals(r6)     // Catch:{ Exception -> 0x02cc }
            if (r6 != 0) goto L_0x0172
            java.lang.String r5 = r0.getString(r5)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r6 = ".png"
            int r6 = r5.lastIndexOf(r6)     // Catch:{ Exception -> 0x02cc }
            r15 = -1
            if (r6 != r15) goto L_0x014a
            java.lang.String r6 = ".jpeg"
            int r6 = r5.lastIndexOf(r6)     // Catch:{ Exception -> 0x02cc }
            if (r6 == r15) goto L_0x0148
            goto L_0x014a
        L_0x0148:
            r5 = r14
            goto L_0x016f
        L_0x014a:
            java.lang.String r6 = ".png"
            int r6 = r5.indexOf(r6)     // Catch:{ Exception -> 0x02cc }
            if (r6 != r15) goto L_0x0158
            java.lang.String r6 = ".jpeg"
            int r6 = r5.indexOf(r6)     // Catch:{ Exception -> 0x02cc }
        L_0x0158:
            if (r6 == r15) goto L_0x016f
            java.lang.String r5 = r5.substring(r11, r6)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r6 = "^\\d+$"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ Exception -> 0x02cc }
            java.util.regex.Matcher r6 = r6.matcher(r5)     // Catch:{ Exception -> 0x02cc }
            boolean r6 = r6.matches()     // Catch:{ Exception -> 0x02cc }
            if (r6 == 0) goto L_0x016f
            goto L_0x0148
        L_0x016f:
            r9.mo14892i(r5)     // Catch:{ Exception -> 0x02cc }
        L_0x0172:
            java.lang.String r5 = "priority"
            boolean r5 = r0.has(r5)     // Catch:{ Exception -> 0x018d }
            if (r5 == 0) goto L_0x0190
            java.lang.String r5 = "priority"
            int r5 = r0.getInt(r5)     // Catch:{ Exception -> 0x018d }
            r6 = -3
            if (r5 <= r6) goto L_0x0189
            if (r5 >= r7) goto L_0x0189
            r9.mo14868c((int) r5)     // Catch:{ Exception -> 0x018d }
            goto L_0x0190
        L_0x0189:
            r9.mo14868c((int) r11)     // Catch:{ Exception -> 0x018d }
            goto L_0x0190
        L_0x018d:
            r9.mo14868c((int) r11)     // Catch:{ Exception -> 0x02cc }
        L_0x0190:
            if (r1 != r13) goto L_0x01aa
            boolean r5 = r0.has(r4)     // Catch:{ Exception -> 0x02cc }
            if (r5 == 0) goto L_0x01aa
            java.lang.String r5 = r0.getString(r4)     // Catch:{ Exception -> 0x02cc }
            boolean r5 = r5.startsWith(r10)     // Catch:{ Exception -> 0x02cc }
            if (r5 == 0) goto L_0x01aa
            java.lang.String r1 = r0.getString(r4)     // Catch:{ Exception -> 0x02cc }
            r9.mo14881f((java.lang.String) r1)     // Catch:{ Exception -> 0x02cc }
            goto L_0x01de
        L_0x01aa:
            r4 = 2
            if (r1 != r4) goto L_0x01c5
            boolean r4 = r0.has(r3)     // Catch:{ Exception -> 0x02cc }
            if (r4 == 0) goto L_0x01c5
            java.lang.String r4 = r0.getString(r3)     // Catch:{ Exception -> 0x02cc }
            boolean r4 = r4.equals(r14)     // Catch:{ Exception -> 0x02cc }
            if (r4 != 0) goto L_0x01c5
            java.lang.String r1 = r0.getString(r3)     // Catch:{ Exception -> 0x02cc }
            r9.mo14873d((java.lang.String) r1)     // Catch:{ Exception -> 0x02cc }
            goto L_0x01de
        L_0x01c5:
            if (r1 != r7) goto L_0x01de
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            r0.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = f2345a     // Catch:{ Exception -> 0x02cc }
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r1 = "big style = 3 doesn't support"
            r0.append(r1)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x02cc }
            return r8
        L_0x01de:
            java.lang.String r1 = "isFloat"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x01f5
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x02cc }
            r3 = 11
            if (r1 < r3) goto L_0x01f5
            java.lang.String r1 = "isFloat"
            boolean r1 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x02cc }
            r9.mo14866b((boolean) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x01f5:
            java.lang.String r1 = "is_noclear"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x020b
            java.lang.String r1 = "is_noclear"
            boolean r1 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 != 0) goto L_0x0207
            r1 = 1
            goto L_0x0208
        L_0x0207:
            r1 = 0
        L_0x0208:
            r9.mo14874d((boolean) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x020b:
            java.lang.String r1 = "is_novibrate"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0221
            java.lang.String r1 = "is_novibrate"
            boolean r1 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 != 0) goto L_0x021d
            r1 = 1
            goto L_0x021e
        L_0x021d:
            r1 = 0
        L_0x021e:
            r9.mo14878e((boolean) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x0221:
            java.lang.String r1 = "is_noring"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0235
            java.lang.String r1 = "is_noring"
            boolean r1 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 != 0) goto L_0x0232
            r11 = 1
        L_0x0232:
            r9.mo14882f((boolean) r11)     // Catch:{ Exception -> 0x02cc }
        L_0x0235:
            java.lang.String r1 = "color"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0246
            java.lang.String r1 = "color"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x02cc }
            r9.mo14902n(r1)     // Catch:{ Exception -> 0x02cc }
        L_0x0246:
            java.lang.String r1 = "channel"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0257
            java.lang.String r1 = "channel"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x02cc }
            r9.mo14860a((java.lang.String) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x0257:
            java.lang.String r1 = "channelName"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0268
            java.lang.String r1 = "channelName"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x02cc }
            r9.mo14865b((java.lang.String) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x0268:
            java.lang.String r1 = "channelLevel"
            boolean r1 = r0.has(r1)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x0279
            java.lang.String r1 = "channelLevel"
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x02cc }
            r9.mo14864b((int) r1)     // Catch:{ Exception -> 0x02cc }
        L_0x0279:
            int r1 = r9.mo14875e()     // Catch:{ Exception -> 0x02cc }
            if (r1 > r12) goto L_0x0285
            int r1 = r9.mo14875e()     // Catch:{ Exception -> 0x02cc }
            if (r1 >= 0) goto L_0x0288
        L_0x0285:
            r9.mo14864b((int) r7)     // Catch:{ Exception -> 0x02cc }
        L_0x0288:
            boolean r1 = r0.has(r2)     // Catch:{ Exception -> 0x02cc }
            if (r1 == 0) goto L_0x02cb
            java.lang.String r1 = r0.optString(r2)     // Catch:{ NumberFormatException -> 0x029d }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x029d }
            r9.mo14859a((int) r1)     // Catch:{ NumberFormatException -> 0x029d }
            r9.mo14861a((boolean) r13)     // Catch:{ NumberFormatException -> 0x029d }
            goto L_0x02cb
        L_0x029d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            r1.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r3 = " NotificationAction.parseAction() : "
            r1.append(r3)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r0.optString(r2)     // Catch:{ Exception -> 0x02cc }
            r1.append(r0)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = "_"
            r1.append(r0)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x02cc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            r1.<init>()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r2 = f2345a     // Catch:{ Exception -> 0x02cc }
            r1.append(r2)     // Catch:{ Exception -> 0x02cc }
            r1.append(r0)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x02cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x02cc }
        L_0x02cb:
            return r9
        L_0x02cc:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1396a.mo14466a(org.json.JSONObject):com.igexin.push.core.bean.BaseAction");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008b  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.core.C1294c mo14467a(com.igexin.push.core.bean.PushTaskBean r13, com.igexin.push.core.bean.BaseAction r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof com.igexin.push.extension.distribution.basic.p060b.C1406a
            if (r0 == 0) goto L_0x0098
            r0 = r14
            com.igexin.push.extension.distribution.basic.b.a r0 = (com.igexin.push.extension.distribution.basic.p060b.C1406a) r0
            java.lang.String r1 = r0.mo14909u()
            java.lang.String r6 = r0.mo14910v()
            java.lang.String r7 = r0.mo14901n()
            java.lang.String r8 = r13.getTaskId()
            java.lang.String r9 = r13.getMessageId()
            r2 = 1
            java.lang.String r3 = ""
            r4 = 0
            if (r7 == 0) goto L_0x0037
            com.igexin.push.extension.distribution.basic.c.c r5 = com.igexin.push.extension.distribution.basic.p061c.C1413c.m2411a()
            java.lang.String r5 = r5.mo14941a((java.lang.String) r7)
            boolean r10 = r5.equals(r3)
            if (r10 == 0) goto L_0x0034
            r0.mo14870c((boolean) r4)
            r10 = 1
            goto L_0x0038
        L_0x0034:
            r0.mo14877e((java.lang.String) r5)
        L_0x0037:
            r10 = 0
        L_0x0038:
            if (r1 == 0) goto L_0x0050
            com.igexin.push.extension.distribution.basic.c.c r5 = com.igexin.push.extension.distribution.basic.p061c.C1413c.m2411a()
            java.lang.String r5 = r5.mo14941a((java.lang.String) r1)
            boolean r11 = r5.equals(r3)
            if (r11 == 0) goto L_0x004d
            r0.mo14886g((boolean) r4)
            r5 = 1
            goto L_0x0051
        L_0x004d:
            r0.mo14897l(r5)
        L_0x0050:
            r5 = 0
        L_0x0051:
            if (r6 == 0) goto L_0x0069
            com.igexin.push.extension.distribution.basic.c.c r11 = com.igexin.push.extension.distribution.basic.p061c.C1413c.m2411a()
            java.lang.String r11 = r11.mo14941a((java.lang.String) r6)
            boolean r3 = r11.equals(r3)
            if (r3 == 0) goto L_0x0066
            r0.mo14889h((boolean) r4)
            r11 = 1
            goto L_0x006a
        L_0x0066:
            r0.mo14900m(r11)
        L_0x0069:
            r11 = 0
        L_0x006a:
            if (r5 != 0) goto L_0x0074
            if (r11 != 0) goto L_0x0074
            if (r10 == 0) goto L_0x0071
            goto L_0x0074
        L_0x0071:
            com.igexin.push.core.c r0 = com.igexin.push.core.C1294c.success
            return r0
        L_0x0074:
            if (r5 == 0) goto L_0x007e
            r5 = 2
            r0 = r12
            r2 = r8
            r3 = r9
            r4 = r14
            r0.mo14848a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String) r3, (com.igexin.push.core.bean.BaseAction) r4, (int) r5)
        L_0x007e:
            if (r11 == 0) goto L_0x0089
            r5 = 3
            r0 = r12
            r1 = r6
            r2 = r8
            r3 = r9
            r4 = r14
            r0.mo14848a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String) r3, (com.igexin.push.core.bean.BaseAction) r4, (int) r5)
        L_0x0089:
            if (r10 == 0) goto L_0x0095
            r5 = 8
            r0 = r12
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r14
            r0.mo14848a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String) r3, (com.igexin.push.core.bean.BaseAction) r4, (int) r5)
        L_0x0095:
            com.igexin.push.core.c r0 = com.igexin.push.core.C1294c.wait
            return r0
        L_0x0098:
            com.igexin.push.core.c r0 = com.igexin.push.core.C1294c.stop
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1396a.mo14467a(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):com.igexin.push.core.c");
    }

    /* renamed from: a */
    public void mo14847a(String str, String str2, C1406a aVar, int i) {
        Bitmap a;
        String str3 = str;
        C1406a aVar2 = aVar;
        C1343f.f2143ae.put(str, Integer.valueOf(i));
        NotificationManager notificationManager = (NotificationManager) C1343f.f2169f.getSystemService("notification");
        Notification notification = new Notification();
        if (Build.VERSION.SDK_INT >= 26) {
            notification = m2267a(aVar).build();
        }
        notification.tickerText = aVar.mo14904p();
        Notification a2 = m2268a(notification, aVar);
        String x = aVar.mo14912x();
        String o = aVar.mo14903o();
        String p = aVar.mo14904p();
        C1432a a3 = C1432a.m2496a(C1343f.f2169f);
        int a4 = a3.mo14986a("getui_notification", "layout");
        int a5 = a3.mo14986a("getui_notification_bg", AgooConstants.MESSAGE_ID);
        a2.contentView = new RemoteViews(C1343f.f2168e, a4);
        if (x != null && (a = C1441i.m2527a(x)) != null) {
            a2.contentView.setImageViewBitmap(a5, a);
            a2.contentIntent = m2269a(str, str2, i, aVar, false);
            C1442j.m2528a(notificationManager, i, a2, 4);
            if (C1438f.m2518a(PushBuildConfig.sdk_conf_version, "2.12.0.0") >= 0) {
                String str4 = str2;
                m2270a(str, str2, o, p);
            }
        }
    }

    /* renamed from: a */
    public void mo14848a(String str, String str2, String str3, BaseAction baseAction, int i) {
        String str4;
        String str5;
        String str6 = str;
        int i2 = i;
        String str7 = "width=" + C1416f.f2425c + "&height=" + C1416f.f2424b;
        if (!str.contains(str7)) {
            if (str.indexOf("?") > 0) {
                str5 = str + DispatchConstants.SIGN_SPLIT_SYMBOL + str7;
            } else {
                str5 = str + "?" + str7;
            }
            str4 = str5;
        } else {
            str4 = str6;
        }
        String str8 = str2;
        C1425d dVar = new C1425d(str4, str, str8, baseAction, i, new C1400b(this, baseAction, str8, str3, str, i));
        if (i2 == 2) {
            C1406a aVar = (C1406a) baseAction;
            aVar.mo14880f(aVar.mo14855A() + 1);
        } else if (i2 == 3) {
            C1406a aVar2 = (C1406a) baseAction;
            aVar2.mo14884g(aVar2.mo14856B() + 1);
        } else if (i2 == 8) {
            C1406a aVar3 = (C1406a) baseAction;
            aVar3.mo14876e(aVar3.mo14899m() + 1);
        }
        C1174c.m1310b().mo14317a(new C1422a(dVar), false, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:82:0x0222  */
    /* JADX WARNING: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14849b(java.lang.String r17, java.lang.String r18, com.igexin.push.extension.distribution.basic.p060b.C1406a r19, int r20) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            r8 = r19
            java.util.Map<java.lang.String, java.lang.Integer> r0 = com.igexin.push.core.C1343f.f2143ae
            java.lang.Integer r1 = java.lang.Integer.valueOf(r20)
            r0.put(r7, r1)
            r5 = 0
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r20
            r4 = r19
            android.app.PendingIntent r0 = r0.m2269a((java.lang.String) r1, (java.lang.String) r2, (int) r3, (com.igexin.push.extension.distribution.basic.p060b.C1406a) r4, (boolean) r5)
            android.content.Context r1 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r2 = "notification"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.app.NotificationManager r1 = (android.app.NotificationManager) r1
            android.app.Notification r2 = new android.app.Notification
            r2.<init>()
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 26
            if (r3 < r4) goto L_0x003b
            android.app.Notification$Builder r2 = r6.m2267a((com.igexin.push.extension.distribution.basic.p060b.C1406a) r8)
            android.app.Notification r2 = r2.build()
        L_0x003b:
            java.lang.String r3 = r19.mo14904p()
            r2.tickerText = r3
            android.app.Notification r2 = r6.m2268a((android.app.Notification) r2, (com.igexin.push.extension.distribution.basic.p060b.C1406a) r8)
            java.lang.String r3 = r19.mo14911w()
            java.lang.String r5 = r19.mo14903o()
            java.lang.String r9 = r19.mo14904p()
            r12 = 0
            if (r3 == 0) goto L_0x007f
            java.lang.String r13 = ""
            boolean r13 = r13.equals(r3)
            if (r13 != 0) goto L_0x007f
            android.graphics.Bitmap r3 = com.igexin.push.extension.distribution.basic.p068j.C1441i.m2527a(r3)
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = f2345a
            r13.append(r14)
            java.lang.String r14 = "|use net logo bitmap is null = "
            r13.append(r14)
            if (r3 != 0) goto L_0x0073
            r14 = 1
            goto L_0x0074
        L_0x0073:
            r14 = 0
        L_0x0074:
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r13)
            goto L_0x0080
        L_0x007f:
            r3 = 0
        L_0x0080:
            if (r3 != 0) goto L_0x0090
            int r3 = r6.m2265a((com.igexin.push.extension.distribution.basic.p060b.C1406a) r8, (boolean) r12)
            android.content.Context r13 = com.igexin.push.extension.distribution.basic.p061c.C1416f.f2423a
            android.content.res.Resources r13 = r13.getResources()
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeResource(r13, r3)
        L_0x0090:
            int r13 = android.os.Build.VERSION.SDK_INT
            r14 = 11
            r15 = 2
            if (r13 < r14) goto L_0x01cf
            android.app.Notification$Builder r13 = new android.app.Notification$Builder
            android.content.Context r14 = com.igexin.push.core.C1343f.f2169f
            r13.<init>(r14)
            int r14 = android.os.Build.VERSION.SDK_INT
            if (r14 < r4) goto L_0x00a6
            android.app.Notification$Builder r13 = r6.m2267a((com.igexin.push.extension.distribution.basic.p060b.C1406a) r8)
        L_0x00a6:
            int r4 = r2.icon
            if (r4 == 0) goto L_0x00d9
            android.content.Context r4 = com.igexin.push.core.C1343f.f2169f
            android.content.res.Resources r4 = r4.getResources()
            int r14 = r2.icon
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r14)
            if (r4 != 0) goto L_0x00d9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = f2345a
            r0.append(r1)
            java.lang.String r1 = "|showNotification smallIconId: "
            r0.append(r1)
            int r1 = r2.icon
            r0.append(r1)
            java.lang.String r1 = " couldn't find resource"
        L_0x00ce:
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            return
        L_0x00d9:
            int r4 = r2.icon
            android.app.Notification$Builder r4 = r13.setSmallIcon(r4)
            java.lang.CharSequence r2 = r2.tickerText
            android.app.Notification$Builder r2 = r4.setTicker(r2)
            long r10 = java.lang.System.currentTimeMillis()
            android.app.Notification$Builder r2 = r2.setWhen(r10)
            android.app.Notification$Builder r2 = r2.setContentTitle(r5)
            android.app.Notification$Builder r2 = r2.setLargeIcon(r3)
            android.app.Notification$Builder r0 = r2.setContentIntent(r0)
            r0.setContentText(r9)
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 24
            if (r0 < r2) goto L_0x0119
            java.lang.String r0 = r19.mo14858D()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0119
            java.lang.String r0 = r19.mo14858D()     // Catch:{ Throwable -> 0x0118 }
            int r0 = android.graphics.Color.parseColor(r0)     // Catch:{ Throwable -> 0x0118 }
            r13.setColor(r0)     // Catch:{ Throwable -> 0x0118 }
            goto L_0x0119
        L_0x0118:
        L_0x0119:
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 16
            if (r0 < r3) goto L_0x0175
            int r0 = r19.mo14893j()
            com.igexin.push.extension.distribution.basic.g.a r3 = com.igexin.push.extension.distribution.basic.p065g.C1429a.BIG_IMAGE
            int r3 = r3.mo14982a()
            if (r0 != r3) goto L_0x014c
            java.lang.String r0 = r19.mo14895k()
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0175
            android.graphics.Bitmap r0 = com.igexin.push.extension.distribution.basic.p068j.C1441i.m2527a(r0)
            if (r0 == 0) goto L_0x0175
            int r3 = r19.mo14879f()
            r13.setPriority(r3)
            android.app.Notification$BigPictureStyle r3 = new android.app.Notification$BigPictureStyle
            r3.<init>()
            android.app.Notification$BigPictureStyle r0 = r3.bigPicture(r0)
            goto L_0x0172
        L_0x014c:
            int r0 = r19.mo14893j()
            com.igexin.push.extension.distribution.basic.g.a r3 = com.igexin.push.extension.distribution.basic.p065g.C1429a.LONG_TEXT
            int r3 = r3.mo14982a()
            if (r0 != r3) goto L_0x0175
            java.lang.String r0 = r19.mo14891i()
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0175
            int r3 = r19.mo14879f()
            r13.setPriority(r3)
            android.app.Notification$BigTextStyle r3 = new android.app.Notification$BigTextStyle
            r3.<init>()
            android.app.Notification$BigTextStyle r0 = r3.bigText(r0)
        L_0x0172:
            r13.setStyle(r0)
        L_0x0175:
            boolean r0 = r19.mo14890h()
            r3 = 21
            if (r0 == 0) goto L_0x0190
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r3) goto L_0x0190
            boolean r0 = r19.mo14906r()
            if (r0 != 0) goto L_0x018d
            boolean r0 = r19.mo14907s()
            if (r0 == 0) goto L_0x0190
        L_0x018d:
            r13.setPriority(r15)
        L_0x0190:
            android.app.Notification r0 = r13.getNotification()
            android.app.Notification r0 = r6.m2268a((android.app.Notification) r0, (com.igexin.push.extension.distribution.basic.p060b.C1406a) r8)
            boolean r8 = com.igexin.push.extension.distribution.basic.p068j.C1435c.m2508b()
            if (r8 != 0) goto L_0x01cb
            int r8 = android.os.Build.VERSION.SDK_INT
            if (r8 < r3) goto L_0x01cb
            int r3 = android.os.Build.VERSION.SDK_INT
            if (r3 >= r2) goto L_0x01cb
            java.lang.String r2 = "com.android.internal.R$id"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x01cb }
            java.lang.String r3 = "right_icon"
            java.lang.reflect.Field r2 = r2.getField(r3)     // Catch:{ Exception -> 0x01cb }
            r3 = 1
            r2.setAccessible(r3)     // Catch:{ Exception -> 0x01cb }
            r3 = 0
            int r2 = r2.getInt(r3)     // Catch:{ Exception -> 0x01cb }
            android.widget.RemoteViews r3 = r0.contentView     // Catch:{ Exception -> 0x01cb }
            if (r3 == 0) goto L_0x01cb
            android.widget.RemoteViews r3 = r0.contentView     // Catch:{ Exception -> 0x01cb }
            r4 = 8
            r3.setViewVisibility(r2, r4)     // Catch:{ Exception -> 0x01cb }
            android.widget.RemoteViews r3 = r0.bigContentView     // Catch:{ Exception -> 0x01cb }
            r3.setViewVisibility(r2, r4)     // Catch:{ Exception -> 0x01cb }
        L_0x01cb:
            r2 = r0
        L_0x01cc:
            r0 = r20
            goto L_0x0215
        L_0x01cf:
            android.widget.RemoteViews r4 = r2.contentView     // Catch:{ Exception -> 0x0228 }
            if (r4 == 0) goto L_0x01e0
            if (r3 == 0) goto L_0x01e0
            int r4 = r16.m2273b()     // Catch:{ Exception -> 0x0228 }
            if (r4 <= 0) goto L_0x01e0
            android.widget.RemoteViews r8 = r2.contentView     // Catch:{ Exception -> 0x0228 }
            r8.setImageViewBitmap(r4, r3)     // Catch:{ Exception -> 0x0228 }
        L_0x01e0:
            java.lang.String r3 = "android.app.Notification"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r4 = "setLatestEventInfo"
            r8 = 4
            java.lang.Class[] r10 = new java.lang.Class[r8]     // Catch:{ Exception -> 0x0228 }
            java.lang.Class<android.content.Context> r11 = android.content.Context.class
            r10[r12] = r11     // Catch:{ Exception -> 0x0228 }
            java.lang.Class<java.lang.CharSequence> r11 = java.lang.CharSequence.class
            r13 = 1
            r10[r13] = r11     // Catch:{ Exception -> 0x0228 }
            java.lang.Class<java.lang.CharSequence> r11 = java.lang.CharSequence.class
            r10[r15] = r11     // Catch:{ Exception -> 0x0228 }
            java.lang.Class<android.app.PendingIntent> r11 = android.app.PendingIntent.class
            r13 = 3
            r10[r13] = r11     // Catch:{ Exception -> 0x0228 }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r10)     // Catch:{ Exception -> 0x0228 }
            r4 = 1
            r3.setAccessible(r4)     // Catch:{ Exception -> 0x0228 }
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0228 }
            android.content.Context r10 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Exception -> 0x0228 }
            r8[r12] = r10     // Catch:{ Exception -> 0x0228 }
            r8[r4] = r5     // Catch:{ Exception -> 0x0228 }
            r8[r15] = r9     // Catch:{ Exception -> 0x0228 }
            r8[r13] = r0     // Catch:{ Exception -> 0x0228 }
            r3.invoke(r2, r8)     // Catch:{ Exception -> 0x0228 }
            goto L_0x01cc
        L_0x0215:
            com.igexin.push.extension.distribution.basic.p068j.C1442j.m2528a(r1, r0, r2, r12)
            java.lang.String r0 = "4.3.8.0"
            java.lang.String r1 = "2.12.0.0"
            int r0 = com.igexin.push.extension.distribution.basic.p068j.C1438f.m2518a(r0, r1)
            if (r0 < 0) goto L_0x0227
            r0 = r18
            r6.m2270a((java.lang.String) r7, (java.lang.String) r0, (java.lang.String) r5, (java.lang.String) r9)
        L_0x0227:
            return
        L_0x0228:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = f2345a
            r0.append(r1)
            java.lang.String r1 = "reflect invoke setLatestEventInfo failed!"
            goto L_0x00ce
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1396a.mo14849b(java.lang.String, java.lang.String, com.igexin.push.extension.distribution.basic.b.a, int):void");
    }

    /* renamed from: b */
    public boolean mo14468b(PushTaskBean pushTaskBean, BaseAction baseAction) {
        StringBuilder sb;
        String str;
        C1257f fVar;
        if (pushTaskBean != null && (baseAction instanceof C1406a)) {
            C1406a aVar = (C1406a) baseAction;
            int a = !aVar.mo14862a() ? m2266a(pushTaskBean.getTaskId()) : aVar.mo14863b();
            int i = 0;
            try {
                i = Integer.parseInt(aVar.getActionId().substring(aVar.getActionId().length() - 1)) + RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT;
            } catch (Exception unused) {
            }
            if (aVar.mo14857C() == 4) {
                mo14847a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), aVar, a);
                if (i != 0) {
                    fVar = C1257f.m1711a();
                    str = i + "";
                    sb = new StringBuilder();
                }
                pushTaskBean.setPerActionid(Integer.parseInt(aVar.getActionId()));
                pushTaskBean.setCurrentActionid(Integer.parseInt(aVar.getDoActionId()));
            } else {
                mo14849b(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), aVar, a);
                if (i != 0) {
                    fVar = C1257f.m1711a();
                    str = i + "";
                    sb = new StringBuilder();
                }
                pushTaskBean.setPerActionid(Integer.parseInt(aVar.getActionId()));
                pushTaskBean.setCurrentActionid(Integer.parseInt(aVar.getDoActionId()));
            }
            sb.append("notifyStyle:");
            sb.append(aVar.mo14857C());
            fVar.mo14477a(pushTaskBean, str, sb.toString());
            pushTaskBean.setPerActionid(Integer.parseInt(aVar.getActionId()));
            pushTaskBean.setCurrentActionid(Integer.parseInt(aVar.getDoActionId()));
        }
        return true;
    }
}
