package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;
import com.aigestudio.wheelpicker.core.WheelScroller;

interface ICrossOrientation {
    void clearCache();

    void computeCurItemRect(Rect rect, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    int computeDegreeSingleDelta(int i, int i2, int i3);

    int computeRadius(int i, int i2, int i3, int i4);

    void computeRectPadding(Rect rect, Rect rect2, Rect rect3, int i, int i2, int i3, int i4);

    int computeStraightUnit(int i, int i2, int i3, int i4, int i5, int i6);

    void draw(Canvas canvas, TextPaint textPaint, String str, int i, int i2, int i3);

    void fling(WheelScroller wheelScroller, VelocityTracker velocityTracker, int i, int i2, int i3, int i4);

    int getCurvedHeight(int i, int i2, int i3);

    int getCurvedWidth(int i, int i2, int i3);

    int getDisplay(int i, int i2, int i3, int i4);

    int getStraightHeight(int i, int i2, int i3, int i4);

    int getStraightUnit(int i, int i2, int i3);

    int getStraightWidth(int i, int i2, int i3, int i4);

    int getUnitDeltaTotal(int i, int i2);

    int getUnitDeltaTotal(WheelScroller wheelScroller);

    void matrixToCenter(Matrix matrix, int i, int i2, int i3);

    int obtainCurrentDis(int i, int i2);

    void removePadding(Rect rect, int i, int i2, int i3, int i4);

    void rotateCamera(Camera camera, int i);

    void startScroll(WheelScroller wheelScroller, int i, int i2);
}
