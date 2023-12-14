package com.aliyun.ams.emas.push.notification;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import java.util.Random;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.aliyun.ams.emas.push.notification.a */
/* compiled from: Taobao */
public class C0911a extends C0914d {

    /* renamed from: e */
    private static Random f1441e;

    /* renamed from: a */
    public Notification mo10193a(Context context) {
        int i;
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            ALog.m3726e("BasicNotificationBuilder", "Get system icon error, package name not found, ", e, new Object[0]);
            i = 17301623;
        }
        Bitmap a = m1084a(context.getResources().getDrawable(i));
        if (Build.VERSION.SDK_INT >= 16) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(mo10226a()).setContentText(mo10229b()).setSmallIcon(i).setVibrate(new long[]{100, 250, 100, 250, 100, 250}).setSound(RingtoneManager.getDefaultUri(2)).setPriority(mo10231c()).setAutoCancel(true).setLargeIcon(a).setWhen(System.currentTimeMillis()).setTicker("");
            if (Build.VERSION.SDK_INT >= 17) {
                builder.setShowWhen(true);
            }
            if (Build.VERSION.SDK_INT >= 26 && !TextUtils.isEmpty(mo10233d())) {
                builder.setChannelId(mo10233d());
            }
            if (Build.VERSION.SDK_INT >= 20) {
                if (m1086e()) {
                    builder.setGroupSummary(true);
                    if (f1441e == null) {
                        f1441e = new Random(System.currentTimeMillis());
                    }
                    builder.setGroup("group" + f1441e.nextInt());
                } else {
                    builder.setGroupSummary(false);
                    builder.setGroup("group");
                }
            }
            return builder.build();
        }
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context);
        builder2.setContentTitle(mo10226a()).setContentText(mo10229b()).setSmallIcon(i).setVibrate(new long[]{100, 250, 100, 250, 100, 250}).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(mo10231c()).setLargeIcon(a).setTicker("").setWhen(System.currentTimeMillis()).setShowWhen(true);
        return builder2.build();
    }

    /* renamed from: e */
    private static boolean m1086e() {
        try {
            String d = m1085d("ro.vivo.os.build.display.id");
            if (!Build.MANUFACTURER.equalsIgnoreCase(AgooConstants.MESSAGE_SYSTEM_SOURCE_VIVO)) {
                if (!d.startsWith("Funtouch")) {
                    String d2 = m1085d("ro.iqoo.os.build.display.id");
                    if (d2 == null || TextUtils.isEmpty(d2.trim())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: d */
    private static String m1085d(String str) {
        try {
            Class[] clsArr = {String.class};
            Object[] objArr = {str};
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", clsArr).invoke(cls, objArr);
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    private Bitmap m1084a(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }
}
