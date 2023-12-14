package com.alibaba.sdk.android.push.notification;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;

/* renamed from: com.alibaba.sdk.android.push.notification.a */
public class C0860a extends C0862c {

    /* renamed from: l */
    private static final AmsLogger f1265l = AmsLogger.getLogger("MPS:BasicNotificationBuilder");

    /* renamed from: a */
    private Bitmap m914a(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v37, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: android.support.v4.app.NotificationCompat$BigTextStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v76, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v77, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v79, resolved type: android.app.Notification$BigTextStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v97, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v98, resolved type: android.support.v4.app.NotificationCompat$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v99, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v100, resolved type: android.app.Notification$BigPictureStyle} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x02ac  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x02f4  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01a7  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01f2  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.Notification mo10025a(android.content.Context r17, com.alibaba.sdk.android.push.notification.PushData r18, com.alibaba.sdk.android.push.notification.NotificationConfigure r19) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            java.lang.String r0 = r1.f1298f
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r5 = 0
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = r1.f1298f
            android.graphics.Bitmap r0 = com.alibaba.sdk.android.push.p026f.C0821a.m818a((android.content.Context) r2, (java.lang.String) r0)
            goto L_0x0019
        L_0x0018:
            r0 = r5
        L_0x0019:
            java.lang.String r6 = r1.f1302j
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x0027
            java.lang.String r5 = r1.f1302j
            android.graphics.Bitmap r5 = com.alibaba.sdk.android.push.p026f.C0821a.m818a((android.content.Context) r2, (java.lang.String) r5)
        L_0x0027:
            java.lang.String r6 = "drawable"
            if (r0 != 0) goto L_0x0052
            android.graphics.Bitmap r7 = com.alibaba.sdk.android.push.common.p020a.C0802b.m766b()
            if (r7 == 0) goto L_0x0036
            android.graphics.Bitmap r0 = com.alibaba.sdk.android.push.common.p020a.C0802b.m766b()
            goto L_0x0052
        L_0x0036:
            android.content.res.Resources r7 = r17.getResources()
            java.lang.String r8 = r17.getPackageName()
            java.lang.String r9 = "alicloud_notification_largeicon"
            int r7 = r7.getIdentifier(r9, r6, r8)
            if (r7 == 0) goto L_0x0052
            android.content.res.Resources r0 = r17.getResources()
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r7)
            android.graphics.Bitmap r0 = r1.m914a(r0)
        L_0x0052:
            r7 = r0
            int r0 = com.alibaba.sdk.android.push.common.p020a.C0802b.m767c()
            if (r0 == 0) goto L_0x005e
            int r0 = com.alibaba.sdk.android.push.common.p020a.C0802b.m767c()
            goto L_0x006c
        L_0x005e:
            android.content.res.Resources r0 = r17.getResources()
            java.lang.String r8 = r17.getPackageName()
            java.lang.String r9 = "alicloud_notification_smallicon"
            int r0 = r0.getIdentifier(r9, r6, r8)
        L_0x006c:
            r6 = r0
            r8 = 17301623(0x1080077, float:2.4979588E-38)
            r9 = 0
            android.content.pm.PackageManager r0 = r17.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0082 }
            java.lang.String r10 = r17.getPackageName()     // Catch:{ NameNotFoundException -> 0x0082 }
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r10, r9)     // Catch:{ NameNotFoundException -> 0x0082 }
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo     // Catch:{ NameNotFoundException -> 0x0082 }
            int r0 = r0.icon     // Catch:{ NameNotFoundException -> 0x0082 }
            goto L_0x008d
        L_0x0082:
            r0 = move-exception
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r10 = f1265l
            java.lang.String r11 = "Get system icon error, package name not found, "
            r10.mo9542e(r11, r0)
            r0 = 17301623(0x1080077, float:2.4979588E-38)
        L_0x008d:
            if (r7 != 0) goto L_0x009b
            android.content.res.Resources r7 = r17.getResources()
            android.graphics.drawable.Drawable r7 = r7.getDrawable(r0)
            android.graphics.Bitmap r7 = r1.m914a(r7)
        L_0x009b:
            if (r6 != 0) goto L_0x009e
            goto L_0x009f
        L_0x009e:
            r0 = r6
        L_0x009f:
            int r6 = android.os.Build.VERSION.SDK_INT
            r8 = 16
            r10 = 3
            r11 = 2
            java.lang.String r12 = ""
            java.lang.String r13 = "/raw/"
            java.lang.String r14 = "android.resource://"
            r15 = 1
            if (r6 < r8) goto L_0x01fa
            android.app.Notification$Builder r6 = new android.app.Notification$Builder
            r6.<init>(r2)
            java.lang.String r8 = r16.mo10073a()
            android.app.Notification$Builder r8 = r6.setContentTitle(r8)
            java.lang.String r9 = r16.mo10076b()
            android.app.Notification$Builder r8 = r8.setContentText(r9)
            android.app.Notification$Builder r0 = r8.setSmallIcon(r0)
            int r8 = r16.mo10079c()
            android.app.Notification$Builder r0 = r0.setPriority(r8)
            android.app.Notification$Builder r0 = r0.setLargeIcon(r7)
            long r8 = java.lang.System.currentTimeMillis()
            android.app.Notification$Builder r0 = r0.setWhen(r8)
            r0.setTicker(r12)
            int r0 = r1.f1299g
            if (r0 != r15) goto L_0x00fe
            android.app.Notification$BigTextStyle r0 = new android.app.Notification$BigTextStyle
            r0.<init>()
            java.lang.String r5 = r1.f1301i
            android.app.Notification$BigTextStyle r0 = r0.bigText(r5)
            java.lang.String r5 = r1.f1300h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x00fa
            java.lang.String r5 = r1.f1300h
            r0.setBigContentTitle(r5)
        L_0x00fa:
            r6.setStyle(r0)
            goto L_0x0153
        L_0x00fe:
            int r0 = r1.f1299g
            if (r0 != r11) goto L_0x011e
            android.app.Notification$BigPictureStyle r0 = new android.app.Notification$BigPictureStyle
            r0.<init>()
            android.app.Notification$BigPictureStyle r0 = r0.bigPicture(r5)
            java.lang.String r5 = r1.f1300h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0118
            java.lang.String r5 = r1.f1300h
            r0.setBigContentTitle(r5)
        L_0x0118:
            if (r7 == 0) goto L_0x00fa
            r0.bigLargeIcon(r7)
            goto L_0x00fa
        L_0x011e:
            int r0 = r1.f1299g
            if (r0 != r10) goto L_0x0153
            android.app.Notification$InboxStyle r5 = new android.app.Notification$InboxStyle
            r5.<init>()
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x013f }
            java.lang.String r7 = r1.f1303k     // Catch:{ JSONException -> 0x013f }
            r0.<init>(r7)     // Catch:{ JSONException -> 0x013f }
            r7 = 0
        L_0x012f:
            int r8 = r0.length()     // Catch:{ JSONException -> 0x013f }
            if (r7 >= r8) goto L_0x0143
            java.lang.String r8 = r0.getString(r7)     // Catch:{ JSONException -> 0x013f }
            r5.addLine(r8)     // Catch:{ JSONException -> 0x013f }
            int r7 = r7 + 1
            goto L_0x012f
        L_0x013f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0143:
            java.lang.String r0 = r1.f1300h
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0150
            java.lang.String r0 = r1.f1300h
            r5.setBigContentTitle(r0)
        L_0x0150:
            r6.setStyle(r5)
        L_0x0153:
            java.lang.String r0 = r1.f1297e
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01a1
            java.lang.String r0 = r1.f1297e
            boolean r0 = r0.startsWith(r14)
            if (r0 == 0) goto L_0x016d
            java.lang.String r0 = r1.f1297e
        L_0x0165:
            android.net.Uri r0 = android.net.Uri.parse(r0)
            r6.setSound(r0)
            goto L_0x01a1
        L_0x016d:
            java.lang.String r0 = r1.f1297e
            boolean r0 = r0.startsWith(r13)
            if (r0 == 0) goto L_0x0185
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            java.lang.String r2 = r17.getPackageName()
            r0.append(r2)
            goto L_0x0197
        L_0x0185:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            java.lang.String r2 = r17.getPackageName()
            r0.append(r2)
            r0.append(r13)
        L_0x0197:
            java.lang.String r2 = r1.f1297e
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            goto L_0x0165
        L_0x01a1:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 17
            if (r0 < r2) goto L_0x01aa
            r6.setShowWhen(r15)
        L_0x01aa:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 26
            if (r0 < r2) goto L_0x01c1
            java.lang.String r0 = r16.mo10081d()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01c1
            java.lang.String r0 = r16.mo10081d()
            r6.setChannelId(r0)
        L_0x01c1:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 20
            if (r0 < r2) goto L_0x01f0
            boolean r0 = com.alibaba.sdk.android.push.notification.C0864e.m988b()
            java.lang.String r2 = "group"
            if (r0 == 0) goto L_0x01e9
            r6.setGroupSummary(r15)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            int r2 = com.alibaba.sdk.android.push.notification.C0864e.m985a()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r6.setGroup(r0)
            goto L_0x01f0
        L_0x01e9:
            r8 = 0
            r6.setGroupSummary(r8)
            r6.setGroup(r2)
        L_0x01f0:
            if (r4 == 0) goto L_0x01f5
            r4.configBuilder((android.app.Notification.Builder) r6, (com.alibaba.sdk.android.push.notification.PushData) r3)
        L_0x01f5:
            android.app.Notification r0 = r6.build()
            return r0
        L_0x01fa:
            r8 = 0
            android.support.v4.app.NotificationCompat$Builder r6 = new android.support.v4.app.NotificationCompat$Builder
            r6.<init>(r2)
            java.lang.String r9 = r16.mo10073a()
            android.support.v4.app.NotificationCompat$Builder r9 = r6.setContentTitle(r9)
            java.lang.String r8 = r16.mo10076b()
            android.support.v4.app.NotificationCompat$Builder r8 = r9.setContentText(r8)
            android.support.v4.app.NotificationCompat$Builder r0 = r8.setSmallIcon(r0)
            int r8 = r16.mo10079c()
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setPriority(r8)
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setLargeIcon(r7)
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setTicker(r12)
            long r8 = java.lang.System.currentTimeMillis()
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setWhen(r8)
            r0.setShowWhen(r15)
            int r0 = r1.f1299g
            if (r0 != r15) goto L_0x024f
            android.support.v4.app.NotificationCompat$BigTextStyle r0 = new android.support.v4.app.NotificationCompat$BigTextStyle
            r0.<init>()
            java.lang.String r5 = r1.f1301i
            android.support.v4.app.NotificationCompat$BigTextStyle r0 = r0.bigText(r5)
            java.lang.String r5 = r1.f1300h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x024b
            java.lang.String r5 = r1.f1300h
            r0.setBigContentTitle(r5)
        L_0x024b:
            r6.setStyle(r0)
            goto L_0x02a4
        L_0x024f:
            int r0 = r1.f1299g
            if (r0 != r11) goto L_0x026f
            android.support.v4.app.NotificationCompat$BigPictureStyle r0 = new android.support.v4.app.NotificationCompat$BigPictureStyle
            r0.<init>()
            android.support.v4.app.NotificationCompat$BigPictureStyle r0 = r0.bigPicture(r5)
            java.lang.String r5 = r1.f1300h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0269
            java.lang.String r5 = r1.f1300h
            r0.setBigContentTitle(r5)
        L_0x0269:
            if (r7 == 0) goto L_0x024b
            r0.bigLargeIcon(r7)
            goto L_0x024b
        L_0x026f:
            int r0 = r1.f1299g
            if (r0 != r10) goto L_0x02a4
            android.support.v4.app.NotificationCompat$InboxStyle r5 = new android.support.v4.app.NotificationCompat$InboxStyle
            r5.<init>()
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0290 }
            java.lang.String r7 = r1.f1303k     // Catch:{ JSONException -> 0x0290 }
            r0.<init>(r7)     // Catch:{ JSONException -> 0x0290 }
            r7 = 0
        L_0x0280:
            int r8 = r0.length()     // Catch:{ JSONException -> 0x0290 }
            if (r7 >= r8) goto L_0x0294
            java.lang.String r8 = r0.getString(r7)     // Catch:{ JSONException -> 0x0290 }
            r5.addLine(r8)     // Catch:{ JSONException -> 0x0290 }
            int r7 = r7 + 1
            goto L_0x0280
        L_0x0290:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0294:
            java.lang.String r0 = r1.f1300h
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02a1
            java.lang.String r0 = r1.f1300h
            r5.setBigContentTitle(r0)
        L_0x02a1:
            r6.setStyle(r5)
        L_0x02a4:
            java.lang.String r0 = r1.f1297e
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02f2
            java.lang.String r0 = r1.f1297e
            boolean r0 = r0.startsWith(r14)
            if (r0 == 0) goto L_0x02be
            java.lang.String r0 = r1.f1297e
        L_0x02b6:
            android.net.Uri r0 = android.net.Uri.parse(r0)
            r6.setSound(r0)
            goto L_0x02f2
        L_0x02be:
            java.lang.String r0 = r1.f1297e
            boolean r0 = r0.startsWith(r13)
            if (r0 == 0) goto L_0x02d6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            java.lang.String r2 = r17.getPackageName()
            r0.append(r2)
            goto L_0x02e8
        L_0x02d6:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            java.lang.String r2 = r17.getPackageName()
            r0.append(r2)
            r0.append(r13)
        L_0x02e8:
            java.lang.String r2 = r1.f1297e
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            goto L_0x02b6
        L_0x02f2:
            if (r4 == 0) goto L_0x02f7
            r4.configBuilder((android.support.p000v4.app.NotificationCompat.Builder) r6, (com.alibaba.sdk.android.push.notification.PushData) r3)
        L_0x02f7:
            android.app.Notification r0 = r6.build()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.notification.C0860a.mo10025a(android.content.Context, com.alibaba.sdk.android.push.notification.PushData, com.alibaba.sdk.android.push.notification.NotificationConfigure):android.app.Notification");
    }
}
