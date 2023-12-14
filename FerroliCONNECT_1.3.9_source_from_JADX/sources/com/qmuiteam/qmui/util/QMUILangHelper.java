package com.qmuiteam.qmui.util;

import android.support.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;

public class QMUILangHelper {
    public static float constrain(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    public static int constrain(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static int getNumberDigits(int i) {
        if (i <= 0) {
            return 0;
        }
        return (int) (Math.log10((double) i) + 1.0d);
    }

    public static int getNumberDigits(long j) {
        if (j <= 0) {
            return 0;
        }
        return (int) (Math.log10((double) j) + 1.0d);
    }

    public static String regularizePrice(float f) {
        return String.format(Locale.CHINESE, "%.2f", new Object[]{Float.valueOf(f)});
    }

    public static String regularizePrice(double d) {
        return String.format(Locale.CHINESE, "%.2f", new Object[]{Double.valueOf(d)});
    }

    public static boolean isNullOrEmpty(@Nullable CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean objectEquals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }
}
