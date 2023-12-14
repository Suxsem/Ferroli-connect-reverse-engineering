package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.TypedValue;

class RingDrawable extends RefreshDrawable {
    private static final int MAX_LEVEL = 200;
    private boolean isRunning;
    private float mAngle;
    private RectF mBounds;
    private int[] mColorSchemeColors;
    private float mDegress;
    private Handler mHandler = new Handler();
    private int mHeight;
    private int mLevel;
    private Paint mPaint = new Paint(1);
    private Path mPath;
    private int mWidth;

    private int evaluate(float f, int i, int i2) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((i3 + ((int) (((float) (((i2 >> 24) & 255) - i3)) * f))) << 24) | ((i4 + ((int) (((float) (((i2 >> 16) & 255) - i4)) * f))) << 16) | ((i5 + ((int) (((float) (((i2 >> 8) & 255) - i5)) * f))) << 8) | (i6 + ((int) (f * ((float) ((i2 & 255) - i6)))));
    }

    RingDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) dp2px(3));
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPath = new Path();
    }

    public void setPercent(float f) {
        Paint paint = this.mPaint;
        int[] iArr = this.mColorSchemeColors;
        paint.setColor(evaluate(f, iArr[0], iArr[1]));
        this.mAngle = f * 340.0f;
    }

    public void setColorSchemeColors(int[] iArr) {
        this.mColorSchemeColors = iArr;
    }

    public void offsetTopAndBottom(int i) {
        invalidateSelf();
    }

    public void start() {
        this.mLevel = 50;
        this.isRunning = true;
        invalidateSelf();
    }

    private void updateLevel(int i) {
        int i2 = (i == 200 ? 0 : i) / 50;
        float f = ((float) (i % 50)) / 50.0f;
        int[] iArr = this.mColorSchemeColors;
        this.mPaint.setColor(evaluate(f, iArr[i2], iArr[(i2 + 1) % iArr.length]));
        this.mDegress = f * 360.0f;
    }

    public void stop() {
        this.isRunning = false;
        this.mDegress = 0.0f;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mWidth = getRefreshLayout().getFinalOffset();
        this.mHeight = this.mWidth;
        this.mBounds = new RectF((float) ((rect.width() / 2) - (this.mWidth / 2)), (float) rect.top, (float) ((rect.width() / 2) + (this.mWidth / 2)), (float) (rect.top + this.mHeight));
        this.mBounds.inset((float) dp2px(15), (float) dp2px(15));
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(this.mDegress, this.mBounds.centerX(), this.mBounds.centerY());
        drawRing(canvas);
        canvas.restore();
        if (this.isRunning) {
            int i = this.mLevel;
            this.mLevel = i >= 200 ? 0 : i + 1;
            updateLevel(this.mLevel);
            invalidateSelf();
        }
    }

    private void drawRing(Canvas canvas) {
        this.mPath.reset();
        this.mPath.arcTo(this.mBounds, 270.0f, this.mAngle, true);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }
}
