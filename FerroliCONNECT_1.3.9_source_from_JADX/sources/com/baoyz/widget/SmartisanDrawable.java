package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;

public class SmartisanDrawable extends RefreshDrawable {
    final float mArrowAngle;
    final float mArrowLength;
    final float mArrowXSpace;
    final float mArrowYSpace;
    RectF mBounds;
    float mCenterX;
    float mCenterY;
    float mDegrees;
    float mHeight;
    final float mLineLength;
    final float mLineWidth;
    final float mMaxAngle = 153.0f;
    int mOffset;
    final Paint mPaint;
    float mPercent;
    final float mRadius = ((float) dp2px(12));
    boolean mRunning;
    float mWidth;

    public SmartisanDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
        double d = (double) this.mRadius;
        Double.isNaN(d);
        this.mLineLength = (float) (d * 2.670353755551324d);
        this.mLineWidth = (float) dp2px(3);
        double d2 = (double) this.mLineLength;
        Double.isNaN(d2);
        this.mArrowLength = (float) ((int) (d2 * 0.15d));
        this.mArrowAngle = 0.43633232f;
        double d3 = (double) this.mArrowLength;
        double sin = Math.sin(0.4363323152065277d);
        Double.isNaN(d3);
        this.mArrowXSpace = (float) ((int) (d3 * sin));
        double d4 = (double) this.mArrowLength;
        double cos = Math.cos(0.4363323152065277d);
        Double.isNaN(d4);
        this.mArrowYSpace = (float) ((int) (d4 * cos));
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeWidth(this.mLineWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(-7829368);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mHeight = (float) getRefreshLayout().getFinalOffset();
        this.mWidth = this.mHeight;
        this.mBounds = new RectF(((float) (rect.width() / 2)) - (this.mWidth / 2.0f), ((float) rect.top) - (this.mHeight / 2.0f), ((float) (rect.width() / 2)) + (this.mWidth / 2.0f), ((float) rect.top) + (this.mHeight / 2.0f));
        this.mCenterX = this.mBounds.centerX();
        this.mCenterY = this.mBounds.centerY();
    }

    public void setPercent(float f) {
        this.mPercent = f;
        invalidateSelf();
    }

    public void setColorSchemeColors(int[] iArr) {
        if (iArr != null && iArr.length > 0) {
            this.mPaint.setColor(iArr[0]);
        }
    }

    public void offsetTopAndBottom(int i) {
        this.mOffset += i;
        invalidateSelf();
    }

    public void start() {
        this.mRunning = true;
        this.mDegrees = 0.0f;
        invalidateSelf();
    }

    public void stop() {
        this.mRunning = false;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas.save();
        float f = 0.0f;
        canvas2.translate(0.0f, (float) (this.mOffset / 2));
        canvas2.clipRect(this.mBounds);
        if (((float) this.mOffset) > this.mHeight && !isRunning()) {
            float f2 = this.mHeight;
            canvas2.rotate(((((float) this.mOffset) - f2) / f2) * 360.0f, this.mCenterX, this.mCenterY);
        }
        if (isRunning()) {
            canvas2.rotate(this.mDegrees, this.mCenterX, this.mCenterY);
            float f3 = this.mDegrees;
            if (f3 < 360.0f) {
                f = 10.0f + f3;
            }
            this.mDegrees = f;
            invalidateSelf();
        }
        float f4 = this.mPercent;
        if (f4 <= 0.5f) {
            float f5 = f4 / 0.5f;
            float f6 = this.mCenterX - this.mRadius;
            float f7 = this.mCenterY;
            float f8 = this.mLineLength;
            float f9 = (f7 + f8) - (f8 * f5);
            float f10 = f6;
            float f11 = f9;
            canvas.drawLine(f10, f11, f6, f9 + f8, this.mPaint);
            canvas.drawLine(f10, f11, f6 - this.mArrowXSpace, f9 + this.mArrowYSpace, this.mPaint);
            float f12 = this.mCenterX + this.mRadius;
            float f13 = this.mCenterY;
            float f14 = this.mLineLength;
            float f15 = (f5 * f14) + (f13 - f14);
            float f16 = f12;
            float f17 = f15;
            canvas.drawLine(f16, f17, f12, f15 - f14, this.mPaint);
            canvas.drawLine(f16, f17, f12 + this.mArrowXSpace, f15 - this.mArrowYSpace, this.mPaint);
        } else {
            float f18 = (f4 - 0.5f) / 0.5f;
            float f19 = this.mCenterX - this.mRadius;
            float f20 = this.mCenterY;
            float f21 = this.mLineLength;
            canvas.drawLine(f19, f20, f19, (f20 + f21) - (f21 * f18), this.mPaint);
            float f22 = this.mCenterX;
            float f23 = this.mRadius;
            float f24 = this.mCenterY;
            RectF rectF = new RectF(f22 - f23, f24 - f23, f22 + f23, f24 + f23);
            float f25 = f18 * 153.0f;
            canvas.drawArc(rectF, 180.0f, f25, false, this.mPaint);
            float f26 = this.mCenterX + this.mRadius;
            float f27 = this.mCenterY;
            float f28 = this.mLineLength;
            Canvas canvas3 = canvas;
            canvas3.drawLine(f26, f27, f26, (f27 - f28) + (f28 * f18), this.mPaint);
            canvas3.drawArc(rectF, 0.0f, f25, false, this.mPaint);
            canvas.save();
            canvas2.rotate(f25, this.mCenterX, this.mCenterY);
            canvas.drawLine(f19, f20, f19 - this.mArrowXSpace, f20 + this.mArrowYSpace, this.mPaint);
            canvas.drawLine(f26, f27, f26 + this.mArrowXSpace, f27 - this.mArrowYSpace, this.mPaint);
            canvas.restore();
        }
        canvas.restore();
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }
}
