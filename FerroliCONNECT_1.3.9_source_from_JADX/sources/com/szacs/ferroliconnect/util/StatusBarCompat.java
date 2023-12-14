package com.szacs.ferroliconnect.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.szacs.ferroliconnect.C1683R;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarCompat {
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");
    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";
    private static final int INVALID_VAL = -1;
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static DisplayMetrics mMetrics;
    public static int navigationHeight = 0;
    public static int screenHeight;
    public static int screenWidth;

    public static void compat(Activity activity, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (i != -1) {
                activity.getWindow().setStatusBarColor(i);
            }
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            int i2 = COLOR_DEFAULT;
            ViewGroup viewGroup = (ViewGroup) activity.findViewById(16908290);
            if (i == -1) {
                i = i2;
            }
            View view = new View(activity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, getStatusBarHeight(activity));
            view.setBackgroundColor(i);
            viewGroup.addView(view, layoutParams);
        }
    }

    public static void compat(Activity activity) {
        compat(activity, -1);
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void setStatusTextColor(boolean z, Activity activity) {
        if (isFlyme()) {
            processFlyMe(z, activity);
        } else if (isMIUI()) {
            processMIUI(z, activity);
        } else {
            if (!z) {
                activity.getWindow().getDecorView().setSystemUiVisibility(1280);
            } else if (Build.VERSION.SDK_INT >= 23) {
                activity.getWindow().getDecorView().setSystemUiVisibility(9216);
            }
            activity.getWindow().getDecorView().findViewById(16908290).setPadding(0, 0, 0, navigationHeight);
        }
    }

    public static boolean isFlyme() {
        try {
            return Build.class.getMethod("hasSmartBar", new Class[0]) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isMIUI() {
        try {
            BuildProperties newInstance = BuildProperties.newInstance();
            if (newInstance.getProperty(KEY_MIUI_VERSION_CODE, (String) null) == null && newInstance.getProperty(KEY_MIUI_VERSION_NAME, (String) null) == null && newInstance.getProperty(KEY_MIUI_INTERNAL_STORAGE, (String) null) == null) {
                return false;
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private static void processMIUI(boolean z, Activity activity) {
        Class<?> cls = activity.getWindow().getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Method method = cls.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
            Window window = activity.getWindow();
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(z ? i : 0);
            objArr[1] = Integer.valueOf(i);
            method.invoke(window, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFlyMe(boolean z, Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        try {
            Class<?> cls = Class.forName("android.view.WindowManager$LayoutParams");
            int i = cls.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(attributes);
            Field declaredField = cls.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            int i2 = declaredField.getInt(attributes);
            if (z) {
                declaredField.set(attributes, Integer.valueOf(i2 | i));
            } else {
                declaredField.set(attributes, Integer.valueOf((i ^ -1) & i2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reMeasure(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        mMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(mMetrics);
        } else {
            defaultDisplay.getMetrics(mMetrics);
        }
        screenWidth = mMetrics.widthPixels;
        screenHeight = mMetrics.heightPixels;
    }

    public static void setStatusBar(Activity activity, boolean z, boolean z2) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().getDecorView().setSystemUiVisibility(1280);
            if (z) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(C1683R.color.ferroli_green));
            } else {
                activity.getWindow().setStatusBarColor(0);
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.flags = 67108864 | attributes.flags;
        }
        if (Build.VERSION.SDK_INT >= 23 && !z2) {
            activity.getWindow().getDecorView().setSystemUiVisibility(9216);
        }
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", DispatchConstants.ANDROID);
        boolean z = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"qemu.hw.mainkeys"});
            if ("1".equals(str)) {
                return false;
            }
            if ("0".equals(str)) {
                return true;
            }
            return z;
        } catch (Exception unused) {
            return z;
        }
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        navigationHeight = resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", DispatchConstants.ANDROID));
        return navigationHeight;
    }
}
