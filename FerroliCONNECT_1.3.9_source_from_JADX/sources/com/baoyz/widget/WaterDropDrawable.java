package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import java.security.InvalidParameterException;

class WaterDropDrawable extends RefreshDrawable implements Runnable {
    private static final float CIRCLE_COUNT = ((float) ProgressStates.values().length);
    private static final float MAX_LEVEL = 10000.0f;
    private static final float MAX_LEVEL_PER_CIRCLE = (MAX_LEVEL / CIRCLE_COUNT);
    private boolean isRunning;
    private int[] mColorSchemeColors;
    private ProgressStates mCurrentState;
    private Handler mHandler = new Handler();
    private int mHeight;
    private int mLevel;
    private Paint mPaint = new Paint();
    private Path mPath;
    private int mTop;
    private int mWidth;

    /* renamed from: p1 */
    private Point f1463p1;

    /* renamed from: p2 */
    private Point f1464p2;

    /* renamed from: p3 */
    private Point f1465p3;

    /* renamed from: p4 */
    private Point f1466p4;

    private enum ProgressStates {
        ONE,
        TWO,
        TREE,
        FOUR
    }

    private int evaluate(float f, int i, int i2) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((i3 + ((int) (((float) (((i2 >> 24) & 255) - i3)) * f))) << 24) | ((i4 + ((int) (((float) (((i2 >> 16) & 255) - i4)) * f))) << 16) | ((i5 + ((int) (((float) (((i2 >> 8) & 255) - i5)) * f))) << 8) | (i6 + ((int) (f * ((float) ((i2 & 255) - i6)))));
    }

    public WaterDropDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
        this.mPaint.setColor(-16776961);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mPath = new Path();
        this.f1463p1 = new Point();
        this.f1464p2 = new Point();
        this.f1465p3 = new Point();
        this.f1466p4 = new Point();
    }

    public void draw(Canvas canvas) {
        canvas.save();
        int i = this.mTop;
        canvas.translate(0.0f, i > 0 ? (float) i : 0.0f);
        this.mPath.reset();
        this.mPath.moveTo((float) this.f1463p1.x, (float) this.f1463p1.y);
        this.mPath.cubicTo((float) this.f1465p3.x, (float) this.f1465p3.y, (float) this.f1466p4.x, (float) this.f1466p4.y, (float) this.f1464p2.x, (float) this.f1464p2.y);
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.mWidth = rect.width();
        updateBounds();
        super.onBoundsChange(rect);
    }

    private void updateBounds() {
        int i = this.mHeight;
        int i2 = this.mWidth;
        if (i > getRefreshLayout().getFinalOffset()) {
            i = getRefreshLayout().getFinalOffset();
        }
        int i3 = i2 / 2;
        int finalOffset = (int) (((float) i3) * (((float) i) / ((float) getRefreshLayout().getFinalOffset())));
        this.f1463p1.set(finalOffset, 0);
        this.f1464p2.set(i2 - finalOffset, 0);
        this.f1465p3.set(i3 - i, i);
        this.f1466p4.set(i3 + i, i);
    }

    public void setColorSchemeColors(int[] iArr) {
        if (iArr == null || iArr.length < 4) {
            throw new InvalidParameterException("The color scheme length must be 4");
        }
        this.mPaint.setColor(iArr[0]);
        this.mColorSchemeColors = iArr;
    }

    public void setPercent(float f) {
        Paint paint = this.mPaint;
        int[] iArr = this.mColorSchemeColors;
        paint.setColor(evaluate(f, iArr[0], iArr[1]));
    }

    private void updateLevel(int i) {
        int i2 = (int) (((float) (((float) i) == MAX_LEVEL ? 0 : i)) / MAX_LEVEL_PER_CIRCLE);
        this.mCurrentState = ProgressStates.values()[i2];
        int[] iArr = this.mColorSchemeColors;
        this.mPaint.setColor(evaluate(((float) (i % 2500)) / 2500.0f, iArr[i2], iArr[(i2 + 1) % ProgressStates.values().length]));
    }

    public void offsetTopAndBottom(int i) {
        this.mHeight += i;
        this.mTop = this.mHeight - getRefreshLayout().getFinalOffset();
        updateBounds();
        invalidateSelf();
    }

    public void start() {
        this.mLevel = 2500;
        this.isRunning = true;
        this.mHandler.postDelayed(this, 20);
    }

    public void stop() {
        this.mHandler.removeCallbacks(this);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void run() {
        this.mLevel += 60;
        if (((float) this.mLevel) > MAX_LEVEL) {
            this.mLevel = 0;
        }
        if (this.isRunning) {
            this.mHandler.postDelayed(this, 20);
            updateLevel(this.mLevel);
            invalidateSelf();
        }
    }
}
