package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;
import com.aigestudio.wheelpicker.core.WheelScroller;
import java.util.HashMap;

class CrossHorImpl implements ICrossOrientation {
    private final HashMap<Integer, Integer> DEGREE = new HashMap<>();

    public int computeStraightUnit(int i, int i2, int i3, int i4, int i5, int i6) {
        return (i * i2) + i3 + i5;
    }

    public int getCurvedHeight(int i, int i2, int i3) {
        return i3;
    }

    public int getCurvedWidth(int i, int i2, int i3) {
        return i * 2;
    }

    public int getStraightHeight(int i, int i2, int i3, int i4) {
        return i4;
    }

    public int getStraightUnit(int i, int i2, int i3) {
        return i2 + i;
    }

    public int getStraightWidth(int i, int i2, int i3, int i4) {
        return (i3 * i) + ((i - 1) * i2);
    }

    public int getUnitDeltaTotal(int i, int i2) {
        return i;
    }

    public int obtainCurrentDis(int i, int i2) {
        return i;
    }

    CrossHorImpl() {
    }

    public int getUnitDeltaTotal(WheelScroller wheelScroller) {
        return wheelScroller.getCurrX();
    }

    public void startScroll(WheelScroller wheelScroller, int i, int i2) {
        wheelScroller.startScroll(i, 0, i2, 0, 300);
    }

    public int computeRadius(int i, int i2, int i3, int i4) {
        double d = (double) (((i + 1) * i3) + ((i - 1) * i2));
        Double.isNaN(d);
        return (int) (d / 3.141592653589793d);
    }

    public int getDisplay(int i, int i2, int i3, int i4) {
        return ((i / 2) + 1) * (i3 + i2);
    }

    public void rotateCamera(Camera camera, int i) {
        camera.rotateY((float) i);
    }

    public void matrixToCenter(Matrix matrix, int i, int i2, int i3) {
        int i4 = i2 + i;
        matrix.preTranslate((float) (-i4), (float) (-i3));
        matrix.postTranslate((float) i4, (float) i3);
    }

    public void draw(Canvas canvas, TextPaint textPaint, String str, int i, int i2, int i3) {
        canvas.drawText(str, (float) (i2 + i), (float) i3, textPaint);
    }

    public int computeDegreeSingleDelta(int i, int i2, int i3) {
        if (this.DEGREE.containsKey(Integer.valueOf(i))) {
            return this.DEGREE.get(Integer.valueOf(i)).intValue();
        }
        double d = (double) i;
        Double.isNaN(d);
        double d2 = (double) i3;
        Double.isNaN(d2);
        int degrees = (int) Math.toDegrees(Math.asin((d * 1.0d) / d2));
        this.DEGREE.put(Integer.valueOf(i), Integer.valueOf(degrees));
        return degrees;
    }

    public void fling(WheelScroller wheelScroller, VelocityTracker velocityTracker, int i, int i2, int i3, int i4) {
        wheelScroller.fling(i, 0, (int) velocityTracker.getXVelocity(), 0, i2, i3, 0, 0, i4, 0);
    }

    public void computeCurItemRect(Rect rect, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        int i12 = (i4 + i) / 2;
        rect.set(i6 - i12, 0, i6 + i12, i3);
    }

    public void clearCache() {
        this.DEGREE.clear();
    }

    public void removePadding(Rect rect, int i, int i2, int i3, int i4) {
        rect.set(rect.left, rect.top + i2, rect.right, rect.bottom - i4);
    }

    public void computeRectPadding(Rect rect, Rect rect2, Rect rect3, int i, int i2, int i3, int i4) {
        rect.set(rect3.left, 0, rect3.right, i2);
        rect2.set(rect3.left, rect3.bottom - i4, rect3.right, rect3.bottom);
    }
}
