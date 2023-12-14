package com.qmuiteam.qmui.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.qmuiteam.qmui.QMUILog;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.android.agoo.common.AgooConstants;

@SuppressLint({"PrivateApi"})
public class QMUIDeviceHelper {
    private static final String FLYME = "flyme";
    private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String[] MEIZUBOARD = {"m9", "M9", "mx", "MX"};
    private static final String TAG = "QMUIDeviceHelper";
    private static final String ZTEC2016 = "zte c2016";
    private static final String ZUKZ1 = "zuk z1";
    private static String sFlymeVersionName;
    private static boolean sIsTabletChecked = false;
    private static boolean sIsTabletValue = false;
    private static String sMiuiVersionName;

    static {
        Properties properties = new Properties();
        if (Build.VERSION.SDK_INT < 26) {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                try {
                    properties.load(fileInputStream2);
                    QMUILangHelper.close(fileInputStream2);
                } catch (Exception e) {
                    FileInputStream fileInputStream3 = fileInputStream2;
                    e = e;
                    fileInputStream = fileInputStream3;
                    try {
                        QMUILog.printErrStackTrace(TAG, e, "read file error", new Object[0]);
                        QMUILangHelper.close(fileInputStream);
                        Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
                        sMiuiVersionName = getLowerCaseName(properties, declaredMethod, KEY_MIUI_VERSION_NAME);
                        sFlymeVersionName = getLowerCaseName(properties, declaredMethod, KEY_FLYME_VERSION_NAME);
                    } catch (Throwable th) {
                        th = th;
                        QMUILangHelper.close(fileInputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    QMUILangHelper.close(fileInputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                QMUILog.printErrStackTrace(TAG, e, "read file error", new Object[0]);
                QMUILangHelper.close(fileInputStream);
                Method declaredMethod2 = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
                sMiuiVersionName = getLowerCaseName(properties, declaredMethod2, KEY_MIUI_VERSION_NAME);
                sFlymeVersionName = getLowerCaseName(properties, declaredMethod2, KEY_FLYME_VERSION_NAME);
            }
        }
        try {
            Method declaredMethod22 = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
            sMiuiVersionName = getLowerCaseName(properties, declaredMethod22, KEY_MIUI_VERSION_NAME);
            sFlymeVersionName = getLowerCaseName(properties, declaredMethod22, KEY_FLYME_VERSION_NAME);
        } catch (Exception e3) {
            QMUILog.printErrStackTrace(TAG, e3, "read SystemProperties error", new Object[0]);
        }
    }

    private static boolean _isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isTablet(Context context) {
        if (sIsTabletChecked) {
            return sIsTabletValue;
        }
        sIsTabletValue = _isTablet(context);
        sIsTabletChecked = true;
        return sIsTabletValue;
    }

    public static boolean isFlyme() {
        return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(FLYME);
    }

    public static boolean isMIUI() {
        return !TextUtils.isEmpty(sMiuiVersionName);
    }

    public static boolean isMIUIV5() {
        return "v5".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV6() {
        return "v6".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV7() {
        return "v7".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV8() {
        return "v8".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV9() {
        return "v9".equals(sMiuiVersionName);
    }

    public static boolean isFlymeVersionHigher5_2_4() {
        boolean z;
        String group;
        String str = sFlymeVersionName;
        if (str != null && !str.equals("")) {
            Matcher matcher = Pattern.compile("(\\d+\\.){2}\\d").matcher(sFlymeVersionName);
            if (matcher.find() && (group = matcher.group()) != null && !group.equals("")) {
                String[] split = group.split("\\.");
                if (split.length == 3) {
                    if (Integer.valueOf(split[0]).intValue() >= 5) {
                        if (Integer.valueOf(split[0]).intValue() <= 5) {
                            if (Integer.valueOf(split[1]).intValue() >= 2) {
                                if (Integer.valueOf(split[1]).intValue() <= 2) {
                                    if (Integer.valueOf(split[2]).intValue() >= 4) {
                                        int intValue = Integer.valueOf(split[2]).intValue();
                                    }
                                }
                            }
                        }
                    }
                    z = false;
                    if (!isMeizu() && z) {
                        return true;
                    }
                }
            }
        }
        z = true;
        return !isMeizu() ? false : false;
    }

    public static boolean isMeizu() {
        return isPhone(MEIZUBOARD) || isFlyme();
    }

    public static boolean isXiaomi() {
        return Build.MANUFACTURER.toLowerCase().equals(AgooConstants.MESSAGE_SYSTEM_SOURCE_XIAOMI);
    }

    public static boolean isZUKZ1() {
        String str = Build.MODEL;
        return str != null && str.toLowerCase().contains(ZUKZ1);
    }

    public static boolean isZTKC2016() {
        String str = Build.MODEL;
        return str != null && str.toLowerCase().contains(ZTEC2016);
    }

    private static boolean isPhone(String[] strArr) {
        String str = Build.BOARD;
        if (str == null) {
            return false;
        }
        for (String equals : strArr) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFloatWindowOpAllowed(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        try {
            if ((context.getApplicationInfo().flags & 134217728) == 134217728) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            try {
                if (((Integer) appOpsManager.getClass().getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(i), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    private static String getLowerCaseName(Properties properties, Method method, String str) {
        String property = properties.getProperty(str);
        if (property == null) {
            try {
                property = (String) method.invoke((Object) null, new Object[]{str});
            } catch (Exception unused) {
            }
        }
        return property != null ? property.toLowerCase() : property;
    }
}
