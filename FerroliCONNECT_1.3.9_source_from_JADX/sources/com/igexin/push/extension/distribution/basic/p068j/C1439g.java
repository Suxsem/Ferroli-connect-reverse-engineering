package com.igexin.push.extension.distribution.basic.p068j;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.p107tb.appyunsdk.Constant;
import java.lang.reflect.Method;

/* renamed from: com.igexin.push.extension.distribution.basic.j.g */
public class C1439g {
    /* renamed from: a */
    private static Object m2519a(int i, String str, Context context) {
        if (!C1435c.m2504a(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Constant.PHONE);
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            Method method = telephonyManager.getClass().getMethod(str, m2521a(str));
            if (i < 0) {
                return null;
            }
            return method.invoke(telephonyManager, new Object[]{Integer.valueOf(i)});
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static String m2520a(int i, Context context) {
        try {
            Object a = m2519a(i, "getDeviceId", context);
            return a != null ? (String) a : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    private static Class[] m2521a(String str) {
        Class[] clsArr = null;
        try {
            Method[] declaredMethods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                if (str.equals(declaredMethods[i].getName())) {
                    clsArr = declaredMethods[i].getParameterTypes();
                    if (clsArr.length >= 1) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return clsArr;
    }
}
