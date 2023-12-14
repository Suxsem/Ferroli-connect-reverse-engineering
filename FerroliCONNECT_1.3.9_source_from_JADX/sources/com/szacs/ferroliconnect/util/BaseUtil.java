package com.szacs.ferroliconnect.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseUtil {
    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNumeric(String str) {
        return !TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str);
    }

    public static boolean isHex(String str) {
        if (isNull(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'f') && (charAt < 'A' || charAt > 'F'))) {
                return false;
            }
        }
        return true;
    }

    public static String DtaeFromat(long j, String str) {
        return new SimpleDateFormat(str, Locale.getDefault()).format(new Date(j));
    }

    public static String timeFormat(long j) {
        long j2 = j / 3600;
        long j3 = j / 60;
        long j4 = j % 60;
        StringBuilder sb = new StringBuilder();
        if (j2 > 0) {
            sb.append(j2);
            sb.append(":");
        }
        if (j3 < 10) {
            sb.append("0");
            sb.append(j3);
            sb.append(":");
        } else {
            sb.append(j3);
            sb.append(":");
        }
        if (j4 < 10) {
            sb.append("0");
            sb.append(j4);
        } else {
            sb.append(j4);
        }
        return sb.toString();
    }

    public static Drawable getDrawableResByName(Context context, String str) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier(str, CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE, context.getPackageName());
        if (identifier > 0) {
            return resources.getDrawable(identifier);
        }
        return null;
    }

    public static void btnClickDelay(final View view, int i) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.setClickable(true);
            }
        }, (long) i);
    }

    public static int changeAlpha(int i, float f) {
        return Color.argb((int) (((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static void changeAlpha(Activity activity, float f) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = f;
        activity.getWindow().setAttributes(attributes);
        activity.getWindow().addFlags(2);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            try {
                if (activity.getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkDeviceName(String str) {
        return str.matches("^[a-zA-Z0-9\\u4e00-\\u9fa5]+$") && str.length() <= 16;
    }

    public static int getStatusHeight(Activity activity) {
        Resources resources = activity.getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isDebug(Context context) {
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
