package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.p000v4.internal.view.SupportMenu;
import android.util.TypedValue;

class ArcDrawable extends RefreshDrawable {
    private static final int MAX_LEVEL = 200;
    private boolean isRunning;
    private float mAngle;
    private Runnable mAnimationTask = new Runnable() {
        public void run() {
            if (ArcDrawable.this.isRunning()) {
                ArcDrawable.access$008(ArcDrawable.this);
                if (ArcDrawable.this.mLevel > 200) {
                    int unused = ArcDrawable.this.mLevel = 0;
                }
                ArcDrawable arcDrawable = ArcDrawable.this;
                arcDrawable.updateLevel(arcDrawable.mLevel);
                ArcDrawable.this.invalidateSelf();
                ArcDrawable.this.mHandler.postDelayed(this, 20);
            }
        }
    };
    private RectF mBounds;
    private int[] mColorSchemeColors;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private int mHeight;
    /* access modifiers changed from: private */
    public int mLevel;
    private int mOffsetTop;
    private Paint mPaint = new Paint(1);
    private int mTop;
    private int mWidth;

    private int evaluate(float f, int i, int i2) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((i3 + ((int) (((float) (((i2 >> 24) & 255) - i3)) * f))) << 24) | ((i4 + ((int) (((float) (((i2 >> 16) & 255) - i4)) * f))) << 16) | ((i5 + ((int) (((float) (((i2 >> 8) & 255) - i5)) * f))) << 8) | (i6 + ((int) (f * ((float) ((i2 & 255) - i6)))));
    }

    static /* synthetic */ int access$008(ArcDrawable arcDrawable) {
        int i = arcDrawable.mLevel;
        arcDrawable.mLevel = i + 1;
        return i;
    }

    ArcDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
        this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
    }

    public void setPercent(float f) {
        Paint paint = this.mPaint;
        int[] iArr = this.mColorSchemeColors;
        paint.setColor(evaluate(f, iArr[3], iArr[0]));
    }

    public void setColorSchemeColors(int[] iArr) {
        this.mColorSchemeColors = iArr;
    }

    public void offsetTopAndBottom(int i) {
        this.mTop += i;
        this.mOffsetTop += i;
        int i2 = this.mOffsetTop;
        float f = (float) i2;
        if (i2 > getRefreshLayout().getFinalOffset()) {
            f = (float) getRefreshLayout().getFinalOffset();
        }
        this.mAngle = (f / ((float) getRefreshLayout().getFinalOffset())) * 360.0f;
        invalidateSelf();
    }

    public void start() {
        this.isRunning = true;
        this.mHandler.post(this.mAnimationTask);
    }

    /* access modifiers changed from: private */
    public void updateLevel(int i) {
        int i2 = (i == 200 ? 0 : i) / 50;
        int[] iArr = this.mColorSchemeColors;
        this.mPaint.setColor(evaluate(((float) (i % 50)) / 50.0f, iArr[i2], iArr[(i2 + 1) % iArr.length]));
    }

    public void stop() {
        this.isRunning = false;
        this.mHandler.removeCallbacks(this.mAnimationTask);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mWidth = dp2px(40);
        this.mHeight = this.mWidth;
        this.mBounds = new RectF((float) ((rect.width() / 2) - (this.mWidth / 2)), (float) rect.top, (float) ((rect.width() / 2) + (this.mWidth / 2)), (float) (rect.top + this.mHeight));
    }

    public void draw(Canvas canvas) {
        canvas.save();
        drawRing(canvas);
        canvas.restore();
    }

    private void drawRing(Canvas canvas) {
        canvas.drawArc(this.mBounds, 270.0f, this.mAngle, true, this.mPaint);
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }
}
