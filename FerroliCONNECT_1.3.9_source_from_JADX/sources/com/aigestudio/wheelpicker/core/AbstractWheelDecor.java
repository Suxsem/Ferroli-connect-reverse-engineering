package com.aigestudio.wheelpicker.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class AbstractWheelDecor {
    public abstract void drawDecor(Canvas canvas, Rect rect, Rect rect2, Paint paint);
}
