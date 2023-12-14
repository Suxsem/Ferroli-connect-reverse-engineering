package com.github.mikephil.charting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.List;

public abstract class Utils {
    public static final double DEG2RAD = 0.017453292519943295d;
    public static final float FDEG2RAD = 0.017453292f;
    private static final int[] POW_10 = {1, 10, 100, 1000, RestConstants.G_MAX_CONNECTION_TIME_OUT, 100000, 1000000, 10000000, 100000000, 1000000000};
    private static Rect mDrawTextRectBuffer = new Rect();
    private static Paint.FontMetrics mFontMetricsBuffer = new Paint.FontMetrics();
    private static int mMaximumFlingVelocity = 8000;
    private static DisplayMetrics mMetrics = null;
    private static int mMinimumFlingVelocity = 50;

    public static float getNormalizedAngle(float f) {
        while (f < 0.0f) {
            f += 360.0f;
        }
        return f % 360.0f;
    }

    public static void init(Context context) {
        if (context == null) {
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMetrics = context.getResources().getDisplayMetrics();
    }

    @Deprecated
    public static void init(Resources resources) {
        mMetrics = resources.getDisplayMetrics();
        mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
        mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
    }

    public static float convertDpToPixel(float f) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) {
            return f * (((float) displayMetrics.densityDpi) / 160.0f);
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
        return f;
    }

    public static float convertPixelsToDp(float f) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) {
            return f / (((float) displayMetrics.densityDpi) / 160.0f);
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertPixelsToDp(...). Otherwise conversion does not take place.");
        return f;
    }

    public static int calcTextWidth(Paint paint, String str) {
        return (int) paint.measureText(str);
    }

    public static int calcTextHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public static float getLineHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getLineSpacing(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (fontMetrics.ascent - fontMetrics.top) + fontMetrics.bottom;
    }

    public static FSize calcTextSize(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return new FSize((float) rect.width(), (float) rect.height());
    }

    public static String formatNumber(float f, int i, boolean z) {
        return formatNumber(f, i, z, '.');
    }

    public static String formatNumber(float f, int i, boolean z, char c) {
        boolean z2;
        float f2 = f;
        char[] cArr = new char[35];
        if (f2 == 0.0f) {
            return "0";
        }
        int i2 = 0;
        boolean z3 = f2 < 1.0f && f2 > -1.0f;
        if (f2 < 0.0f) {
            f2 = -f2;
            z2 = true;
        } else {
            z2 = false;
        }
        int[] iArr = POW_10;
        int i3 = i;
        int length = i3 > iArr.length ? iArr.length - 1 : i3;
        long round = (long) Math.round(f2 * ((float) POW_10[length]));
        int length2 = cArr.length - 1;
        boolean z4 = false;
        while (true) {
            if (round == 0 && i2 >= length + 1) {
                break;
            }
            int i4 = (int) (round % 10);
            round /= 10;
            int i5 = length2 - 1;
            cArr[length2] = (char) (i4 + 48);
            i2++;
            if (i2 == length) {
                length2 = i5 - 1;
                cArr[i5] = ',';
                i2++;
                z4 = true;
            } else {
                if (z && round != 0 && i2 > length) {
                    if (z4) {
                        if ((i2 - length) % 4 == 0) {
                            length2 = i5 - 1;
                            cArr[i5] = c;
                        }
                    } else if ((i2 - length) % 4 == 3) {
                        length2 = i5 - 1;
                        cArr[i5] = c;
                    }
                    i2++;
                }
                length2 = i5;
            }
        }
        if (z3) {
            cArr[length2] = '0';
            i2++;
            length2--;
        }
        if (z2) {
            cArr[length2] = '-';
            i2++;
        }
        int length3 = cArr.length - i2;
        return String.valueOf(cArr, length3, cArr.length - length3);
    }

    public static float roundToNextSignificant(double d) {
        float pow = (float) Math.pow(10.0d, (double) (1 - ((int) ((float) Math.ceil((double) ((float) Math.log10(d < 0.0d ? -d : d)))))));
        double d2 = (double) pow;
        Double.isNaN(d2);
        return ((float) Math.round(d * d2)) / pow;
    }

    public static int getDecimals(float f) {
        return ((int) Math.ceil(-Math.log10((double) roundToNextSignificant((double) f)))) + 2;
    }

    public static int[] convertIntegers(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public static String[] convertStrings(List<String> list) {
        String[] strArr = new String[list.size()];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = list.get(i);
        }
        return strArr;
    }

    public static double nextUp(double d) {
        if (d == Double.POSITIVE_INFINITY) {
            return d;
        }
        double d2 = d + 0.0d;
        return Double.longBitsToDouble(Double.doubleToRawLongBits(d2) + (d2 >= 0.0d ? 1 : -1));
    }

    public static int getClosestDataSetIndexByValue(List<SelectionDetail> list, float f, YAxis.AxisDependency axisDependency) {
        SelectionDetail closestSelectionDetailByValue = getClosestSelectionDetailByValue(list, f, axisDependency);
        if (closestSelectionDetailByValue == null) {
            return -2147483647;
        }
        return closestSelectionDetailByValue.dataSetIndex;
    }

    public static int getClosestDataSetIndexByPixelY(List<SelectionDetail> list, float f, YAxis.AxisDependency axisDependency) {
        SelectionDetail closestSelectionDetailByPixelY = getClosestSelectionDetailByPixelY(list, f, axisDependency);
        if (closestSelectionDetailByPixelY == null) {
            return -2147483647;
        }
        return closestSelectionDetailByPixelY.dataSetIndex;
    }

    public static SelectionDetail getClosestSelectionDetailByValue(List<SelectionDetail> list, float f, YAxis.AxisDependency axisDependency) {
        SelectionDetail selectionDetail = null;
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            SelectionDetail selectionDetail2 = list.get(i);
            if (axisDependency == null || selectionDetail2.dataSet.getAxisDependency() == axisDependency) {
                float abs = Math.abs(selectionDetail2.value - f);
                if (abs < f2) {
                    selectionDetail = selectionDetail2;
                    f2 = abs;
                }
            }
        }
        return selectionDetail;
    }

    public static SelectionDetail getClosestSelectionDetailByPixelY(List<SelectionDetail> list, float f, YAxis.AxisDependency axisDependency) {
        SelectionDetail selectionDetail = null;
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            SelectionDetail selectionDetail2 = list.get(i);
            if (axisDependency == null || selectionDetail2.dataSet.getAxisDependency() == axisDependency) {
                float abs = Math.abs(selectionDetail2.f1492y - f);
                if (abs < f2) {
                    selectionDetail = selectionDetail2;
                    f2 = abs;
                }
            }
        }
        return selectionDetail;
    }

    public static float getMinimumDistance(List<SelectionDetail> list, float f, YAxis.AxisDependency axisDependency) {
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            SelectionDetail selectionDetail = list.get(i);
            if (selectionDetail.dataSet.getAxisDependency() == axisDependency) {
                float abs = Math.abs(selectionDetail.f1492y - f);
                if (abs < f2) {
                    f2 = abs;
                }
            }
        }
        return f2;
    }

    public static boolean needsDefaultFormatter(ValueFormatter valueFormatter) {
        return valueFormatter == null || (valueFormatter instanceof DefaultValueFormatter);
    }

    public static PointF getPosition(PointF pointF, float f, float f2) {
        double d = (double) pointF.x;
        double d2 = (double) f;
        double d3 = (double) f2;
        double cos = Math.cos(Math.toRadians(d3));
        Double.isNaN(d2);
        Double.isNaN(d);
        double d4 = (double) pointF.y;
        double sin = Math.sin(Math.toRadians(d3));
        Double.isNaN(d2);
        Double.isNaN(d4);
        return new PointF((float) (d + (cos * d2)), (float) (d4 + (d2 * sin)));
    }

    public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent motionEvent, VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(1000, (float) mMaximumFlingVelocity);
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        float xVelocity = velocityTracker.getXVelocity(pointerId);
        float yVelocity = velocityTracker.getYVelocity(pointerId);
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            if (i != actionIndex) {
                int pointerId2 = motionEvent.getPointerId(i);
                if ((velocityTracker.getXVelocity(pointerId2) * xVelocity) + (velocityTracker.getYVelocity(pointerId2) * yVelocity) < 0.0f) {
                    velocityTracker.clear();
                    return;
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void postInvalidateOnAnimation(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
        } else {
            view.postInvalidateDelayed(10);
        }
    }

    public static int getMinimumFlingVelocity() {
        return mMinimumFlingVelocity;
    }

    public static int getMaximumFlingVelocity() {
        return mMaximumFlingVelocity;
    }

    public static void drawXAxisValue(Canvas canvas, String str, float f, float f2, Paint paint, PointF pointF, float f3) {
        float fontMetrics = paint.getFontMetrics(mFontMetricsBuffer);
        paint.getTextBounds(str, 0, str.length(), mDrawTextRectBuffer);
        float f4 = 0.0f - ((float) mDrawTextRectBuffer.left);
        float f5 = (-mFontMetricsBuffer.ascent) + 0.0f;
        Paint.Align textAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.LEFT);
        if (f3 != 0.0f) {
            float width = f4 - (((float) mDrawTextRectBuffer.width()) * 0.5f);
            float f6 = f5 - (fontMetrics * 0.5f);
            if (!(pointF.x == 0.5f && pointF.y == 0.5f)) {
                FSize sizeOfRotatedRectangleByDegrees = getSizeOfRotatedRectangleByDegrees((float) mDrawTextRectBuffer.width(), fontMetrics, f3);
                f -= sizeOfRotatedRectangleByDegrees.width * (pointF.x - 0.5f);
                f2 -= sizeOfRotatedRectangleByDegrees.height * (pointF.y - 0.5f);
            }
            canvas.save();
            canvas.translate(f, f2);
            canvas.rotate(f3);
            canvas.drawText(str, width, f6, paint);
            canvas.restore();
        } else {
            if (!(pointF.x == 0.0f && pointF.y == 0.0f)) {
                f4 -= ((float) mDrawTextRectBuffer.width()) * pointF.x;
                f5 -= fontMetrics * pointF.y;
            }
            canvas.drawText(str, f4 + f, f5 + f2, paint);
        }
        paint.setTextAlign(textAlign);
    }

    public static void drawMultilineText(Canvas canvas, StaticLayout staticLayout, float f, float f2, TextPaint textPaint, PointF pointF, float f3) {
        float fontMetrics = textPaint.getFontMetrics(mFontMetricsBuffer);
        float width = (float) staticLayout.getWidth();
        float lineCount = ((float) staticLayout.getLineCount()) * fontMetrics;
        float f4 = 0.0f - ((float) mDrawTextRectBuffer.left);
        float f5 = lineCount + 0.0f;
        Paint.Align textAlign = textPaint.getTextAlign();
        textPaint.setTextAlign(Paint.Align.LEFT);
        if (f3 != 0.0f) {
            float f6 = f4 - (width * 0.5f);
            float f7 = f5 - (lineCount * 0.5f);
            if (!(pointF.x == 0.5f && pointF.y == 0.5f)) {
                FSize sizeOfRotatedRectangleByDegrees = getSizeOfRotatedRectangleByDegrees(width, lineCount, f3);
                f -= sizeOfRotatedRectangleByDegrees.width * (pointF.x - 0.5f);
                f2 -= sizeOfRotatedRectangleByDegrees.height * (pointF.y - 0.5f);
            }
            canvas.save();
            canvas.translate(f, f2);
            canvas.rotate(f3);
            canvas.translate(f6, f7);
            staticLayout.draw(canvas);
            canvas.restore();
        } else {
            if (!(pointF.x == 0.0f && pointF.y == 0.0f)) {
                f4 -= width * pointF.x;
                f5 -= lineCount * pointF.y;
            }
            canvas.save();
            canvas.translate(f4 + f, f5 + f2);
            staticLayout.draw(canvas);
            canvas.restore();
        }
        textPaint.setTextAlign(textAlign);
    }

    public static void drawMultilineText(Canvas canvas, String str, float f, float f2, TextPaint textPaint, FSize fSize, PointF pointF, float f3) {
        TextPaint textPaint2 = textPaint;
        drawMultilineText(canvas, new StaticLayout(str, 0, str.length(), textPaint2, (int) Math.max(Math.ceil((double) fSize.width), 1.0d), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false), f, f2, textPaint2, pointF, f3);
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(FSize fSize, float f) {
        return getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(FSize fSize, float f) {
        return getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f);
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(float f, float f2, float f3) {
        return getSizeOfRotatedRectangleByRadians(f, f2, f3 * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(float f, float f2, float f3) {
        double d = (double) f3;
        return new FSize(Math.abs(((float) Math.cos(d)) * f) + Math.abs(((float) Math.sin(d)) * f2), Math.abs(f * ((float) Math.sin(d))) + Math.abs(f2 * ((float) Math.cos(d))));
    }

    public static int getSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static double granularity(float f, int i) {
        double roundToNextSignificant = (double) roundToNextSignificant((double) (f / ((float) i)));
        double roundToNextSignificant2 = (double) roundToNextSignificant(Math.pow(10.0d, (double) ((int) Math.log10(roundToNextSignificant))));
        Double.isNaN(roundToNextSignificant);
        Double.isNaN(roundToNextSignificant2);
        if (((int) (roundToNextSignificant / roundToNextSignificant2)) > 5) {
            Double.isNaN(roundToNextSignificant2);
            roundToNextSignificant = Math.floor(roundToNextSignificant2 * 10.0d);
        }
        return roundToNextSignificant * 0.1d;
    }
}
