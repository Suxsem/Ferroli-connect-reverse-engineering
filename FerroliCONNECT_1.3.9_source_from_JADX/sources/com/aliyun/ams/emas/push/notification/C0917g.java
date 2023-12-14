package com.aliyun.ams.emas.push.notification;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import java.util.List;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.aliyun.ams.emas.push.notification.g */
/* compiled from: Taobao */
public class C0917g {
    /* renamed from: a */
    public static boolean m1134a(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            if (Build.VERSION.SDK_INT > 16) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                ActivityManager.getMyMemoryState(runningAppProcessInfo);
                if (runningAppProcessInfo.importance == 100) {
                    return true;
                }
                return false;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks.isEmpty() || runningTasks.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
