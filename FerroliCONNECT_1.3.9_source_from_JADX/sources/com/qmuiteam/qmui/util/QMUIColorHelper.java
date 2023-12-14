package com.qmuiteam.qmui.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.p000v4.view.ViewCompat;

public class QMUIColorHelper {
    public static int setColorAlpha(@ColorInt int i, float f) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | (((int) (f * 255.0f)) << 24);
    }

    public static int computeColor(@ColorInt int i, @ColorInt int i2, float f) {
        float max = Math.max(Math.min(f, 1.0f), 0.0f);
        int alpha = Color.alpha(i);
        int alpha2 = ((int) (((float) (Color.alpha(i2) - alpha)) * max)) + alpha;
        int red = Color.red(i);
        int red2 = ((int) (((float) (Color.red(i2) - red)) * max)) + red;
        int green = Color.green(i);
        int blue = Color.blue(i);
        return Color.argb(alpha2, red2, ((int) (((float) (Color.green(i2) - green)) * max)) + green, ((int) (((float) (Color.blue(i2) - blue)) * max)) + blue);
    }

    public static String colorToString(@ColorInt int i) {
        return String.format("#%08X", new Object[]{Integer.valueOf(i)});
    }
}
