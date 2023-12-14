package com.alibaba.sdk.android.push.notification;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p009a.C0669a;
import com.alibaba.sdk.android.push.common.p020a.C0802b;
import com.alibaba.sdk.android.push.p026f.C0821a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;

public class CustomNotificationBuilder {
    public static final String NOTIFICATION_ICON_RES_TYPE = "drawable";
    public static final String NOTIFICATION_LARGE_ICON_FILE = "alicloud_notification_largeicon";
    public static final String NOTIFICATION_SMALL_ICON_FILE = "alicloud_notification_smallicon";

    /* renamed from: a */
    static AmsLogger f1262a = AmsLogger.getLogger("MPS:CustomNotificationBuilder");

    /* renamed from: c */
    private static CustomNotificationBuilder f1263c = null;

    /* renamed from: b */
    private Map<String, Object> f1264b = null;

    private CustomNotificationBuilder() {
        if (this.f1264b == null) {
            this.f1264b = new HashMap();
        }
    }

    /* renamed from: a */
    private int m907a(Context context, C0861b bVar) {
        int i;
        int f = bVar.mo10043f();
        if (f != 0) {
            return f;
        }
        int c = C0802b.m767c() != 0 ? C0802b.m767c() : context.getResources().getIdentifier(NOTIFICATION_SMALL_ICON_FILE, NOTIFICATION_ICON_RES_TYPE, context.getPackageName());
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            f1262a.mo9542e("Get system icon error, package name not found, ", e);
            i = 17301623;
        }
        return c == 0 ? i : c;
    }

    /* renamed from: a */
    private Bitmap m908a(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: android.support.v4.app.NotificationCompat$BigTextStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: android.app.Notification$BigTextStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v50, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v51, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0220  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.app.Notification m909b(android.content.Context r17, com.alibaba.sdk.android.push.notification.C0861b r18, com.alibaba.sdk.android.push.notification.PushData r19, com.alibaba.sdk.android.push.notification.NotificationConfigure r20) {
        /*
            r16 = this;
            r1 = r17
            r2 = r19
            r3 = r20
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r4 = "building basic custom notification"
            r0.mo9538d(r4)
            int r0 = r18.mo10068s()
            java.lang.String r4 = r18.mo10069t()
            java.lang.String r5 = r18.mo10070u()
            java.lang.String r6 = r18.mo10072w()
            java.lang.String r7 = r18.mo10026a()
            java.lang.String r8 = r18.mo10071v()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x0034
            java.lang.String r8 = r18.mo10071v()
            android.graphics.Bitmap r8 = com.alibaba.sdk.android.push.p026f.C0821a.m818a((android.content.Context) r1, (java.lang.String) r8)
            goto L_0x0035
        L_0x0034:
            r8 = 0
        L_0x0035:
            android.graphics.Bitmap r9 = r16.m910b(r17, r18)
            int r10 = android.os.Build.VERSION.SDK_INT
            r11 = 16
            java.lang.String r14 = ""
            java.lang.String r15 = "/raw/"
            java.lang.String r12 = "android.resource://"
            if (r10 < r11) goto L_0x017f
            android.app.Notification$Builder r10 = new android.app.Notification$Builder
            r10.<init>(r1)
            java.lang.String r11 = r18.mo10031b()
            android.app.Notification$Builder r11 = r10.setContentTitle(r11)
            java.lang.String r13 = r18.mo10034c()
            android.app.Notification$Builder r11 = r11.setContentText(r13)
            int r13 = r16.m907a(r17, r18)
            android.app.Notification$Builder r11 = r11.setSmallIcon(r13)
            int r13 = r18.mo10065p()
            android.app.Notification$Builder r11 = r11.setPriority(r13)
            android.app.Notification$Builder r11 = r11.setLargeIcon(r9)
            android.app.Notification$Builder r11 = r11.setTicker(r14)
            long r13 = java.lang.System.currentTimeMillis()
            r11.setWhen(r13)
            r11 = 1
            if (r0 != r11) goto L_0x0092
            android.app.Notification$BigTextStyle r0 = new android.app.Notification$BigTextStyle
            r0.<init>()
            android.app.Notification$BigTextStyle r0 = r0.bigText(r5)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x008e
            r0.setBigContentTitle(r4)
        L_0x008e:
            r10.setStyle(r0)
            goto L_0x00db
        L_0x0092:
            r5 = 2
            if (r0 != r5) goto L_0x00ad
            android.app.Notification$BigPictureStyle r0 = new android.app.Notification$BigPictureStyle
            r0.<init>()
            android.app.Notification$BigPictureStyle r0 = r0.bigPicture(r8)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x00a7
            r0.setBigContentTitle(r4)
        L_0x00a7:
            if (r9 == 0) goto L_0x008e
            r0.bigLargeIcon(r9)
            goto L_0x008e
        L_0x00ad:
            r5 = 3
            if (r0 != r5) goto L_0x00db
            android.app.Notification$InboxStyle r5 = new android.app.Notification$InboxStyle
            r5.<init>()
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x00cb }
            r0.<init>(r6)     // Catch:{ JSONException -> 0x00cb }
            r6 = 0
        L_0x00bb:
            int r8 = r0.length()     // Catch:{ JSONException -> 0x00cb }
            if (r6 >= r8) goto L_0x00cf
            java.lang.String r8 = r0.getString(r6)     // Catch:{ JSONException -> 0x00cb }
            r5.addLine(r8)     // Catch:{ JSONException -> 0x00cb }
            int r6 = r6 + 1
            goto L_0x00bb
        L_0x00cb:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00cf:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x00d8
            r5.setBigContentTitle(r4)
        L_0x00d8:
            r10.setStyle(r5)
        L_0x00db:
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 17
            if (r0 < r4) goto L_0x00e5
            r4 = 1
            r10.setShowWhen(r4)
        L_0x00e5:
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 26
            if (r0 < r4) goto L_0x00fc
            java.lang.String r0 = r18.mo10066q()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00fc
            java.lang.String r0 = r18.mo10066q()
            r10.setChannelId(r0)
        L_0x00fc:
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 20
            if (r0 < r4) goto L_0x012c
            boolean r0 = com.alibaba.sdk.android.push.notification.C0864e.m988b()
            java.lang.String r4 = "group"
            if (r0 == 0) goto L_0x0125
            r5 = 1
            r10.setGroupSummary(r5)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r4)
            int r4 = com.alibaba.sdk.android.push.notification.C0864e.m985a()
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            r10.setGroup(r0)
            goto L_0x012c
        L_0x0125:
            r11 = 0
            r10.setGroupSummary(r11)
            r10.setGroup(r4)
        L_0x012c:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L_0x0174
            boolean r0 = r7.startsWith(r12)
            if (r0 == 0) goto L_0x0140
            android.net.Uri r0 = android.net.Uri.parse(r7)
        L_0x013c:
            r10.setSound(r0)
            goto L_0x0174
        L_0x0140:
            boolean r0 = r7.startsWith(r15)
            if (r0 == 0) goto L_0x0156
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            java.lang.String r1 = r17.getPackageName()
            r0.append(r1)
            goto L_0x0168
        L_0x0156:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            java.lang.String r1 = r17.getPackageName()
            r0.append(r1)
            r0.append(r15)
        L_0x0168:
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            android.net.Uri r0 = android.net.Uri.parse(r0)
            goto L_0x013c
        L_0x0174:
            if (r3 == 0) goto L_0x0179
            r3.configBuilder((android.app.Notification.Builder) r10, (com.alibaba.sdk.android.push.notification.PushData) r2)
        L_0x0179:
            android.app.Notification r0 = r10.build()
            goto L_0x026d
        L_0x017f:
            r11 = 0
            android.support.v4.app.NotificationCompat$Builder r10 = new android.support.v4.app.NotificationCompat$Builder
            r10.<init>(r1)
            java.lang.String r13 = r18.mo10031b()
            android.support.v4.app.NotificationCompat$Builder r13 = r10.setContentTitle(r13)
            java.lang.String r11 = r18.mo10034c()
            android.support.v4.app.NotificationCompat$Builder r11 = r13.setContentText(r11)
            int r13 = r16.m907a(r17, r18)
            android.support.v4.app.NotificationCompat$Builder r11 = r11.setSmallIcon(r13)
            int r13 = r18.mo10065p()
            android.support.v4.app.NotificationCompat$Builder r11 = r11.setPriority(r13)
            android.support.v4.app.NotificationCompat$Builder r11 = r11.setLargeIcon(r9)
            long r1 = java.lang.System.currentTimeMillis()
            android.support.v4.app.NotificationCompat$Builder r1 = r11.setWhen(r1)
            r2 = 1
            android.support.v4.app.NotificationCompat$Builder r1 = r1.setShowWhen(r2)
            r1.setTicker(r14)
            if (r0 != r2) goto L_0x01d1
            android.support.v4.app.NotificationCompat$BigTextStyle r0 = new android.support.v4.app.NotificationCompat$BigTextStyle
            r0.<init>()
            android.support.v4.app.NotificationCompat$BigTextStyle r0 = r0.bigText(r5)
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 != 0) goto L_0x01cd
            r0.setBigContentTitle(r4)
        L_0x01cd:
            r10.setStyle(r0)
            goto L_0x021a
        L_0x01d1:
            r1 = 2
            if (r0 != r1) goto L_0x01ec
            android.support.v4.app.NotificationCompat$BigPictureStyle r0 = new android.support.v4.app.NotificationCompat$BigPictureStyle
            r0.<init>()
            android.support.v4.app.NotificationCompat$BigPictureStyle r0 = r0.bigPicture(r8)
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 != 0) goto L_0x01e6
            r0.setBigContentTitle(r4)
        L_0x01e6:
            if (r9 == 0) goto L_0x01cd
            r0.bigLargeIcon(r9)
            goto L_0x01cd
        L_0x01ec:
            r1 = 3
            if (r0 != r1) goto L_0x021a
            android.support.v4.app.NotificationCompat$InboxStyle r1 = new android.support.v4.app.NotificationCompat$InboxStyle
            r1.<init>()
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x020a }
            r0.<init>(r6)     // Catch:{ JSONException -> 0x020a }
            r2 = 0
        L_0x01fa:
            int r5 = r0.length()     // Catch:{ JSONException -> 0x020a }
            if (r2 >= r5) goto L_0x020e
            java.lang.String r5 = r0.getString(r2)     // Catch:{ JSONException -> 0x020a }
            r1.addLine(r5)     // Catch:{ JSONException -> 0x020a }
            int r2 = r2 + 1
            goto L_0x01fa
        L_0x020a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x020e:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x0217
            r1.setBigContentTitle(r4)
        L_0x0217:
            r10.setStyle(r1)
        L_0x021a:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L_0x0262
            boolean r0 = r7.startsWith(r12)
            if (r0 == 0) goto L_0x022e
            android.net.Uri r0 = android.net.Uri.parse(r7)
        L_0x022a:
            r10.setSound(r0)
            goto L_0x0262
        L_0x022e:
            boolean r0 = r7.startsWith(r15)
            if (r0 == 0) goto L_0x0244
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            java.lang.String r1 = r17.getPackageName()
            r0.append(r1)
            goto L_0x0256
        L_0x0244:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            java.lang.String r1 = r17.getPackageName()
            r0.append(r1)
            r0.append(r15)
        L_0x0256:
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            android.net.Uri r0 = android.net.Uri.parse(r0)
            goto L_0x022a
        L_0x0262:
            if (r3 == 0) goto L_0x0269
            r1 = r19
            r3.configBuilder((android.support.p000v4.app.NotificationCompat.Builder) r10, (com.alibaba.sdk.android.push.notification.PushData) r1)
        L_0x0269:
            android.app.Notification r0 = r10.build()
        L_0x026d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.notification.CustomNotificationBuilder.m909b(android.content.Context, com.alibaba.sdk.android.push.notification.b, com.alibaba.sdk.android.push.notification.PushData, com.alibaba.sdk.android.push.notification.NotificationConfigure):android.app.Notification");
    }

    /* renamed from: b */
    private Bitmap m910b(Context context, C0861b bVar) {
        Bitmap a = !TextUtils.isEmpty(bVar.mo10067r()) ? C0821a.m818a(context, bVar.mo10067r()) : null;
        if (a == null) {
            if (C0802b.m766b() != null) {
                a = C0802b.m766b();
            } else {
                int identifier = context.getResources().getIdentifier(NOTIFICATION_LARGE_ICON_FILE, NOTIFICATION_ICON_RES_TYPE, context.getPackageName());
                if (identifier != 0) {
                    a = m908a(context.getResources().getDrawable(identifier));
                }
            }
        }
        if (a != null) {
            return a;
        }
        int i = 17301623;
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            f1262a.mo9542e("Get system icon error, package name not found, ", e);
        }
        return m908a(context.getResources().getDrawable(i));
    }

    /* renamed from: c */
    private Notification m911c(Context context, C0861b bVar, PushData pushData, NotificationConfigure notificationConfigure) {
        int i;
        int i2;
        StringBuilder sb;
        Uri parse;
        StringBuilder sb2;
        Uri parse2;
        String a = bVar.mo10026a();
        f1262a.mo9538d("building advanced custom notification");
        if (bVar.mo10055j() == 0) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), bVar.mo10055j());
        remoteViews.setTextViewText(bVar.mo10061l(), bVar.mo10031b());
        remoteViews.setTextViewText(bVar.mo10062m(), bVar.mo10034c());
        if (bVar.mo10063n() != 0) {
            i2 = bVar.mo10058k();
            i = bVar.mo10063n();
        } else {
            i2 = bVar.mo10058k();
            i = 17301623;
        }
        remoteViews.setImageViewResource(i2, i);
        if (Build.VERSION.SDK_INT >= 16) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContent(remoteViews).setPriority(bVar.mo10065p()).setSmallIcon(m907a(context, bVar)).setTicker("").setWhen(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= 17) {
                builder.setShowWhen(true);
            }
            if (Build.VERSION.SDK_INT >= 26 && !TextUtils.isEmpty(bVar.mo10066q())) {
                builder.setChannelId(bVar.mo10066q());
            }
            if (Build.VERSION.SDK_INT >= 20) {
                if (C0864e.m988b()) {
                    builder.setGroupSummary(true);
                    builder.setGroup("group" + C0864e.m985a());
                } else {
                    builder.setGroupSummary(false);
                    builder.setGroup("group");
                }
            }
            if (!TextUtils.isEmpty(a)) {
                if (a.startsWith("android.resource://")) {
                    parse2 = Uri.parse(a);
                } else {
                    if (a.startsWith("/raw/")) {
                        sb2 = new StringBuilder();
                        sb2.append("android.resource://");
                        sb2.append(context.getPackageName());
                    } else {
                        sb2 = new StringBuilder();
                        sb2.append("android.resource://");
                        sb2.append(context.getPackageName());
                        sb2.append("/raw/");
                    }
                    sb2.append(a);
                    parse2 = Uri.parse(sb2.toString());
                }
                builder.setSound(parse2);
            }
            if (notificationConfigure != null) {
                notificationConfigure.configBuilder(builder, pushData);
            }
            return builder.build();
        }
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context);
        builder2.setContent(remoteViews).setPriority(bVar.mo10065p()).setSmallIcon(m907a(context, bVar)).setTicker("").setShowWhen(true).setWhen(System.currentTimeMillis());
        if (!TextUtils.isEmpty(a)) {
            if (a.startsWith("android.resource://")) {
                parse = Uri.parse(a);
            } else {
                if (a.startsWith("/raw/")) {
                    sb = new StringBuilder();
                    sb.append("android.resource://");
                    sb.append(context.getPackageName());
                } else {
                    sb = new StringBuilder();
                    sb.append("android.resource://");
                    sb.append(context.getPackageName());
                    sb.append("/raw/");
                }
                sb.append(a);
                parse = Uri.parse(sb.toString());
            }
            builder2.setSound(parse);
        }
        if (notificationConfigure != null) {
            notificationConfigure.configBuilder(builder2, pushData);
        }
        return builder2.build();
    }

    public static CustomNotificationBuilder getInstance() {
        if (f1263c == null) {
            f1263c = new CustomNotificationBuilder();
        }
        return f1263c;
    }

    /* renamed from: a */
    public Notification mo10001a(Context context, C0861b bVar, PushData pushData, NotificationConfigure notificationConfigure) {
        if (2 == bVar.mo10046g()) {
            return m909b(context, bVar, pushData, notificationConfigure);
        }
        if (3 == bVar.mo10046g()) {
            return m911c(context, bVar, pushData, notificationConfigure);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x013f  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0185  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:38:0x00e3=Splitter:B:38:0x00e3, B:59:0x0155=Splitter:B:59:0x0155, B:45:0x0109=Splitter:B:45:0x0109, B:31:0x00bd=Splitter:B:31:0x00bd, B:52:0x012f=Splitter:B:52:0x012f} */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.sdk.android.push.notification.BasicCustomPushNotification mo10002a(int r7) {
        /*
            r6 = this;
            java.lang.String r0 = "get custom notification failed"
            java.util.Map<java.lang.String, java.lang.Object> r1 = r6.f1264b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "custom_notification_"
            r2.append(r3)
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x003a
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = "find custom notification from cache"
            r0.mo9538d(r1)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            java.lang.Object r7 = r0.get(r7)
            com.alibaba.sdk.android.push.notification.BasicCustomPushNotification r7 = (com.alibaba.sdk.android.push.notification.BasicCustomPushNotification) r7
            return r7
        L_0x003a:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r1 = f1262a
            java.lang.String r2 = "do not find custom notification from cache, find it from SharedPreferences"
            r1.mo9538d(r2)
            android.content.SharedPreferences r1 = com.alibaba.sdk.android.ams.common.p009a.C0669a.m471f()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r4 = 0
            java.lang.String r1 = r1.getString(r2, r4)
            if (r1 != 0) goto L_0x0063
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r7 = f1262a
            java.lang.String r0 = "no corresponding custom notificaiton"
            r7.mo9541e(r0)
            return r4
        L_0x0063:
            java.lang.String r2 = "UTF-8"
            java.lang.String r1 = java.net.URLDecoder.decode(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            java.lang.String r2 = "ISO-8859-1"
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            byte[] r1 = r1.getBytes(r2)     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            r5.<init>(r1)     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            r1.<init>(r5)     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            java.lang.Object r2 = r1.readObject()     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            com.alibaba.sdk.android.push.notification.BasicCustomPushNotification r2 = (com.alibaba.sdk.android.push.notification.BasicCustomPushNotification) r2     // Catch:{ UnsupportedEncodingException -> 0x0154, OptionalDataException -> 0x012e, StreamCorruptedException -> 0x0108, IOException -> 0x00e2, ClassNotFoundException -> 0x00bc }
            r1.close()     // Catch:{ UnsupportedEncodingException -> 0x00b5, OptionalDataException -> 0x00b1, StreamCorruptedException -> 0x00ae, IOException -> 0x00ab, ClassNotFoundException -> 0x00a8, all -> 0x00a5 }
            r5.close()     // Catch:{ UnsupportedEncodingException -> 0x00b5, OptionalDataException -> 0x00b1, StreamCorruptedException -> 0x00ae, IOException -> 0x00ab, ClassNotFoundException -> 0x00a8, all -> 0x00a5 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r2.toString()
            r0.mo9538d(r1)
            if (r2 == 0) goto L_0x00a4
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r2)
        L_0x00a4:
            return r2
        L_0x00a5:
            r4 = r2
            goto L_0x017a
        L_0x00a8:
            r1 = move-exception
            r4 = r2
            goto L_0x00bd
        L_0x00ab:
            r1 = move-exception
            r4 = r2
            goto L_0x00e3
        L_0x00ae:
            r1 = move-exception
            r4 = r2
            goto L_0x0109
        L_0x00b1:
            r1 = move-exception
            r4 = r2
            goto L_0x012f
        L_0x00b5:
            r1 = move-exception
            r4 = r2
            goto L_0x0155
        L_0x00b9:
            goto L_0x017a
        L_0x00bc:
            r1 = move-exception
        L_0x00bd:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = f1262a     // Catch:{ all -> 0x00b9 }
            r2.mo9542e(r0, r1)     // Catch:{ all -> 0x00b9 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x00e1
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x00e1:
            return r4
        L_0x00e2:
            r1 = move-exception
        L_0x00e3:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = f1262a     // Catch:{ all -> 0x00b9 }
            r2.mo9542e(r0, r1)     // Catch:{ all -> 0x00b9 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x0107
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x0107:
            return r4
        L_0x0108:
            r1 = move-exception
        L_0x0109:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = f1262a     // Catch:{ all -> 0x00b9 }
            r2.mo9542e(r0, r1)     // Catch:{ all -> 0x00b9 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x012d
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x012d:
            return r4
        L_0x012e:
            r1 = move-exception
        L_0x012f:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = f1262a     // Catch:{ all -> 0x00b9 }
            r2.mo9542e(r0, r1)     // Catch:{ all -> 0x00b9 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x0153
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x0153:
            return r4
        L_0x0154:
            r1 = move-exception
        L_0x0155:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = f1262a     // Catch:{ all -> 0x00b9 }
            r2.mo9542e(r0, r1)     // Catch:{ all -> 0x00b9 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x0179
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x0179:
            return r4
        L_0x017a:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = f1262a
            java.lang.String r1 = r4.toString()
            r0.mo9538d(r1)
            if (r4 == 0) goto L_0x0199
            java.util.Map<java.lang.String, java.lang.Object> r0 = r6.f1264b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.put(r7, r4)
        L_0x0199:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.notification.CustomNotificationBuilder.mo10002a(int):com.alibaba.sdk.android.push.notification.BasicCustomPushNotification");
    }

    public boolean setCustomNotification(int i, BasicCustomPushNotification basicCustomPushNotification) {
        AmsLogger amsLogger;
        String str;
        boolean z = false;
        if (i <= 0) {
            amsLogger = f1262a;
            str = "custom notification id must be an integer greater than 0";
        } else if (basicCustomPushNotification == null) {
            amsLogger = f1262a;
            str = "notification cannot be null";
        } else {
            SharedPreferences f = C0669a.m471f();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(basicCustomPushNotification);
                String encode = URLEncoder.encode(byteArrayOutputStream.toString(StringUtil.__ISO_8859_1), "UTF-8");
                objectOutputStream.close();
                byteArrayOutputStream.close();
                SharedPreferences.Editor edit = f.edit();
                edit.putString(BasicCustomPushNotification.CUSTOM_NOTIFICATION_TAG + i, encode);
                edit.commit();
                z = true;
            } catch (IOException e) {
                f1262a.mo9542e("get custom notification failed", e);
            }
            if (z) {
                Map<String, Object> map = this.f1264b;
                if (map.containsKey(BasicCustomPushNotification.CUSTOM_NOTIFICATION_TAG + i)) {
                    Map<String, Object> map2 = this.f1264b;
                    map2.remove(BasicCustomPushNotification.CUSTOM_NOTIFICATION_TAG + i);
                }
                f1262a.mo9538d("save the notification to cache");
                Map<String, Object> map3 = this.f1264b;
                map3.put(BasicCustomPushNotification.CUSTOM_NOTIFICATION_TAG + i, basicCustomPushNotification);
            }
            return z;
        }
        amsLogger.mo9541e(str);
        return false;
    }
}
