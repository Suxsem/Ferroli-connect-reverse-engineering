package com.qmuiteam.qmui.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class QMUIStatusBarHelper {
    private static final int STATUSBAR_TYPE_ANDROID6 = 3;
    private static final int STATUSBAR_TYPE_DEFAULT = 0;
    private static final int STATUSBAR_TYPE_FLYME = 2;
    private static final int STATUSBAR_TYPE_MIUI = 1;
    private static final int STATUS_BAR_DEFAULT_HEIGHT_DP = 25;
    private static int mStatuBarType = 0;
    private static int sStatusbarHeight = -1;
    private static Integer sTransparentValue = null;
    public static float sVirtualDensity = -1.0f;
    public static float sVirtualDensityDpi = -1.0f;

    public static void translucent(Activity activity) {
        translucent(activity, 1073741824);
    }

    private static boolean supportTranslucent() {
        return Build.VERSION.SDK_INT >= 19 && !Build.BRAND.toLowerCase().contains("essential");
    }

    @TargetApi(19)
    public static void translucent(Activity activity, @ColorInt int i) {
        if (supportTranslucent()) {
            if (QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) {
                activity.getWindow().setFlags(67108864, 67108864);
            } else if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.getDecorView().setSystemUiVisibility(1280);
                if (Build.VERSION.SDK_INT < 23 || !supportTransclentStatusBar6()) {
                    window.clearFlags(67108864);
                    window.addFlags(Integer.MIN_VALUE);
                    window.setStatusBarColor(i);
                    return;
                }
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
                window.setStatusBarColor(0);
            }
        }
    }

    public static boolean setStatusBarLightMode(Activity activity) {
        if (activity == null || QMUIDeviceHelper.isZTKC2016()) {
            return false;
        }
        int i = mStatuBarType;
        if (i != 0) {
            return setStatusBarLightMode(activity, i);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (isMIUICustomStatusBarLightModeImpl() && MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                mStatuBarType = 1;
                return true;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                mStatuBarType = 2;
                return true;
            } else if (Build.VERSION.SDK_INT >= 23) {
                Android6SetStatusBarLightMode(activity.getWindow(), true);
                mStatuBarType = 3;
                return true;
            }
        }
        return false;
    }

    private static boolean setStatusBarLightMode(Activity activity, int i) {
        if (i == 1) {
            return MIUISetStatusBarLightMode(activity.getWindow(), true);
        }
        if (i == 2) {
            return FlymeSetStatusBarLightMode(activity.getWindow(), true);
        }
        if (i == 3) {
            return Android6SetStatusBarLightMode(activity.getWindow(), true);
        }
        return false;
    }

    public static boolean setStatusBarDarkMode(Activity activity) {
        if (activity == null) {
            return false;
        }
        int i = mStatuBarType;
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return MIUISetStatusBarLightMode(activity.getWindow(), false);
        }
        if (i == 2) {
            return FlymeSetStatusBarLightMode(activity.getWindow(), false);
        }
        if (i == 3) {
            return Android6SetStatusBarLightMode(activity.getWindow(), false);
        }
        return true;
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window, int i) {
        return retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, retainSystemUiFlag(window, i, 1024), 4), 2), 4096), 1024), 512);
    }

    public static int retainSystemUiFlag(Window window, int i, int i2) {
        return (window.getDecorView().getSystemUiVisibility() & i2) == i2 ? i | i2 : i;
    }

    @TargetApi(23)
    private static boolean Android6SetStatusBarLightMode(Window window, boolean z) {
        window.getDecorView().setSystemUiVisibility(changeStatusBarModeRetainFlag(window, z ? 8192 : 256));
        if (!QMUIDeviceHelper.isMIUIV9()) {
            return true;
        }
        MIUISetStatusBarLightMode(window, z);
        return true;
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
                if (z) {
                    method.invoke(window, new Object[]{Integer.valueOf(i), Integer.valueOf(i)});
                    return true;
                }
                method.invoke(window, new Object[]{0, Integer.valueOf(i)});
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private static boolean isMIUICustomStatusBarLightModeImpl() {
        if ((!QMUIDeviceHelper.isMIUIV9() || Build.VERSION.SDK_INT >= 23) && !QMUIDeviceHelper.isMIUIV5() && !QMUIDeviceHelper.isMIUIV6() && !QMUIDeviceHelper.isMIUIV7() && !QMUIDeviceHelper.isMIUIV8()) {
            return false;
        }
        return true;
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean z) {
        Android6SetStatusBarLightMode(window, z);
        if (window != null) {
            try {
                WindowManager.LayoutParams attributes = window.getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt((Object) null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i2 | i : (i ^ -1) & i2);
                window.setAttributes(attributes);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static boolean isFullScreen(Activity activity) {
        try {
            if ((activity.getWindow().getAttributes().flags & 1024) != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Integer getStatusBarAPITransparentValue(Context context) {
        Integer num = sTransparentValue;
        if (num != null) {
            return num;
        }
        String str = null;
        for (String str2 : context.getPackageManager().getSystemSharedLibraryNames()) {
            if ("touchwiz".equals(str2)) {
                str = "SYSTEM_UI_FLAG_TRANSPARENT_BACKGROUND";
            } else if (str2.startsWith("com.sonyericsson.navigationbar")) {
                str = "SYSTEM_UI_FLAG_TRANSPARENT";
            }
        }
        if (str != null) {
            try {
                Field field = View.class.getField(str);
                if (field != null && field.getType() == Integer.TYPE) {
                    sTransparentValue = Integer.valueOf(field.getInt((Object) null));
                }
            } catch (Exception unused) {
            }
        }
        return sTransparentValue;
    }

    public static boolean supportTransclentStatusBar6() {
        return !QMUIDeviceHelper.isZUKZ1() && !QMUIDeviceHelper.isZTKC2016();
    }

    public static int getStatusbarHeight(Context context) {
        if (sStatusbarHeight == -1) {
            initStatusBarHeight(context);
        }
        return sStatusbarHeight;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void initStatusBarHeight(android.content.Context r4) {
        /*
            r0 = 0
            java.lang.String r1 = "com.android.internal.R$dimen"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Throwable -> 0x0027 }
            java.lang.Object r2 = r1.newInstance()     // Catch:{ Throwable -> 0x0027 }
            boolean r3 = com.qmuiteam.qmui.util.QMUIDeviceHelper.isMeizu()     // Catch:{ Throwable -> 0x0025 }
            if (r3 == 0) goto L_0x001c
            java.lang.String r3 = "status_bar_height_large"
            java.lang.reflect.Field r0 = r1.getField(r3)     // Catch:{ Throwable -> 0x0018 }
            goto L_0x001c
        L_0x0018:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ Throwable -> 0x0025 }
        L_0x001c:
            if (r0 != 0) goto L_0x002c
            java.lang.String r3 = "status_bar_height"
            java.lang.reflect.Field r0 = r1.getField(r3)     // Catch:{ Throwable -> 0x0025 }
            goto L_0x002c
        L_0x0025:
            r1 = move-exception
            goto L_0x0029
        L_0x0027:
            r1 = move-exception
            r2 = r0
        L_0x0029:
            r1.printStackTrace()
        L_0x002c:
            if (r0 == 0) goto L_0x004b
            if (r2 == 0) goto L_0x004b
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x0047 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0047 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0047 }
            android.content.res.Resources r1 = r4.getResources()     // Catch:{ Throwable -> 0x0047 }
            int r0 = r1.getDimensionPixelSize(r0)     // Catch:{ Throwable -> 0x0047 }
            sStatusbarHeight = r0     // Catch:{ Throwable -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004b:
            boolean r0 = com.qmuiteam.qmui.util.QMUIDeviceHelper.isTablet(r4)
            r1 = 25
            if (r0 == 0) goto L_0x005f
            int r0 = sStatusbarHeight
            int r2 = com.qmuiteam.qmui.util.QMUIDisplayHelper.dp2px(r4, r1)
            if (r0 <= r2) goto L_0x005f
            r4 = 0
            sStatusbarHeight = r4
            goto L_0x007c
        L_0x005f:
            int r0 = sStatusbarHeight
            if (r0 > 0) goto L_0x007c
            float r0 = sVirtualDensity
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0072
            int r4 = com.qmuiteam.qmui.util.QMUIDisplayHelper.dp2px(r4, r1)
            sStatusbarHeight = r4
            goto L_0x007c
        L_0x0072:
            r4 = 1103626240(0x41c80000, float:25.0)
            float r0 = r0 * r4
            r4 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r4
            int r4 = (int) r0
            sStatusbarHeight = r4
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.util.QMUIStatusBarHelper.initStatusBarHeight(android.content.Context):void");
    }

    public static void setVirtualDensity(float f) {
        sVirtualDensity = f;
    }

    public static void setVirtualDensityDpi(float f) {
        sVirtualDensityDpi = f;
    }
}
