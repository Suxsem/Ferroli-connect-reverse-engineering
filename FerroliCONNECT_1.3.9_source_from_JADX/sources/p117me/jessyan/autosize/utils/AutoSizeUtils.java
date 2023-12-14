package p117me.jessyan.autosize.utils;

import android.content.Context;
import android.util.TypedValue;

/* renamed from: me.jessyan.autosize.utils.AutoSizeUtils */
public class AutoSizeUtils {
    private AutoSizeUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static int dp2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int pt2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(3, f, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int in2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(4, f, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int mm2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(5, f, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
