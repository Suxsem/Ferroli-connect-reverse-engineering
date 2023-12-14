package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;
import com.aigestudio.wheelpicker.core.WheelScroller;
import java.util.HashMap;

class CrossVerImpl implements ICrossOrientation {
    private final HashMap<Integer, Integer> DEGREE = new HashMap<>();

    public int computeStraightUnit(int i, int i2, int i3, int i4, int i5, int i6) {
        return (i * i2) + i4 + i6;
    }

    public int getCurvedHeight(int i, int i2, int i3) {
        return i * 2;
    }

    public int getCurvedWidth(int i, int i2, int i3) {
        return i2;
    }

    public int getStraightHeight(int i, int i2, int i3, int i4) {
        return (i4 * i) + ((i - 1) * i2);
    }

    public int getStraightUnit(int i, int i2, int i3) {
        return i3 + i;
    }

    public int getStraightWidth(int i, int i2, int i3, int i4) {
        return i3;
    }

    public int getUnitDeltaTotal(int i, int i2) {
        return i2;
    }

    public int obtainCurrentDis(int i, int i2) {
        return i2;
    }

    CrossVerImpl() {
    }

    public int getUnitDeltaTotal(WheelScroller wheelScroller) {
        return wheelScroller.getCurrY();
    }

    public void startScroll(WheelScroller wheelScroller, int i, int i2) {
        wheelScroller.startScroll(0, i, 0, i2, 300);
    }

    public int computeRadius(int i, int i2, int i3, int i4) {
        double d = (double) (((i + 1) * i4) + ((i - 1) * i2));
        Double.isNaN(d);
        return (int) (d / 3.141592653589793d);
    }

    public int getDisplay(int i, int i2, int i3, int i4) {
        return ((i / 2) + 1) * (i4 + i2);
    }

    public void rotateCamera(Camera camera, int i) {
        camera.rotateX((float) (-i));
    }

    public void matrixToCenter(Matrix matrix, int i, int i2, int i3) {
        int i4 = i3 + i;
        matrix.preTranslate((float) (-i2), (float) (-i4));
        matrix.postTranslate((float) i2, (float) i4);
    }

    public void draw(Canvas canvas, TextPaint textPaint, String str, int i, int i2, int i3) {
        canvas.drawText(str, (float) i2, (float) (i3 + i), textPaint);
    }

    public int computeDegreeSingleDelta(int i, int i2, int i3) {
        if (this.DEGREE.containsKey(Integer.valueOf(i2))) {
            return this.DEGREE.get(Integer.valueOf(i2)).intValue();
        }
        double d = (double) i2;
        Double.isNaN(d);
        double d2 = (double) i3;
        Double.isNaN(d2);
        int degrees = (int) Math.toDegrees(Math.asin((d * 1.0d) / d2));
        this.DEGREE.put(Integer.valueOf(i2), Integer.valueOf(degrees));
        return degrees;
    }

    public void fling(WheelScroller wheelScroller, VelocityTracker velocityTracker, int i, int i2, int i3, int i4) {
        wheelScroller.fling(0, i, 0, (int) velocityTracker.getYVelocity(), 0, 0, i2, i3, 0, i4);
    }

    public void computeCurItemRect(Rect rect, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        int i12 = (i5 + i) / 2;
        rect.set(0, i7 - i12, i2, i7 + i12);
    }

    public void clearCache() {
        this.DEGREE.clear();
    }

    public void removePadding(Rect rect, int i, int i2, int i3, int i4) {
        rect.set(rect.left + i, rect.top, rect.right - i3, rect.bottom);
    }

    public void computeRectPadding(Rect rect, Rect rect2, Rect rect3, int i, int i2, int i3, int i4) {
        rect.set(0, rect3.top, i, rect3.bottom);
        rect2.set(rect3.right - i3, rect3.top, rect3.right, rect3.bottom);
    }
}
