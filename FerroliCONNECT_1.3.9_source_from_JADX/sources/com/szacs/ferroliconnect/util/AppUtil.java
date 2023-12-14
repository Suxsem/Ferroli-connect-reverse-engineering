package com.szacs.ferroliconnect.util;

import android.content.Context;

public class AppUtil {
    public static synchronized String getAppName(Context context) {
        String string;
        synchronized (AppUtil.class) {
            try {
                string = context.getResources().getString(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return string;
    }

    public static synchronized String getVersionName(Context context) {
        String str;
        synchronized (AppUtil.class) {
            try {
                str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return str;
    }

    public static synchronized int getVersionCode(Context context) {
        int i;
        synchronized (AppUtil.class) {
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return i;
    }

    public static synchronized String getPackageName(Context context) {
        String str;
        synchronized (AppUtil.class) {
            try {
                str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return str;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0019 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized android.graphics.Bitmap getBitmap(android.content.Context r4) {
        /*
            java.lang.Class<com.szacs.ferroliconnect.util.AppUtil> r0 = com.szacs.ferroliconnect.util.AppUtil.class
            monitor-enter(r0)
            r1 = 0
            android.content.Context r2 = r4.getApplicationContext()     // Catch:{ NameNotFoundException -> 0x0018 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0018 }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ NameNotFoundException -> 0x0019 }
            r3 = 0
            android.content.pm.ApplicationInfo r1 = r2.getApplicationInfo(r4, r3)     // Catch:{ NameNotFoundException -> 0x0019 }
            goto L_0x0019
        L_0x0016:
            r4 = move-exception
            goto L_0x0025
        L_0x0018:
            r2 = r1
        L_0x0019:
            android.graphics.drawable.Drawable r4 = r2.getApplicationIcon(r1)     // Catch:{ all -> 0x0016 }
            android.graphics.drawable.BitmapDrawable r4 = (android.graphics.drawable.BitmapDrawable) r4     // Catch:{ all -> 0x0016 }
            android.graphics.Bitmap r4 = r4.getBitmap()     // Catch:{ all -> 0x0016 }
            monitor-exit(r0)
            return r4
        L_0x0025:
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.AppUtil.getBitmap(android.content.Context):android.graphics.Bitmap");
    }
}
