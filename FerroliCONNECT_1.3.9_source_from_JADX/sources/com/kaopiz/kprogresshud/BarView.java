package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class BarView extends View implements Determinate {
    private RectF mBound;
    private float mBoundGap;
    private RectF mInBound;
    private Paint mInnerPaint;
    private int mMax = 100;
    private Paint mOuterPaint;
    private int mProgress = 0;
    private float mRadius;

    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mOuterPaint = new Paint(1);
        this.mOuterPaint.setStyle(Paint.Style.STROKE);
        this.mOuterPaint.setStrokeWidth((float) Helper.dpToPixel(2.0f, getContext()));
        this.mOuterPaint.setColor(-1);
        this.mInnerPaint = new Paint(1);
        this.mInnerPaint.setStyle(Paint.Style.FILL);
        this.mInnerPaint.setColor(-1);
        this.mBoundGap = (float) Helper.dpToPixel(5.0f, getContext());
        float f = this.mBoundGap;
        this.mInBound = new RectF(f, f, ((((float) getWidth()) - this.mBoundGap) * ((float) this.mProgress)) / ((float) this.mMax), ((float) getHeight()) - this.mBoundGap);
        this.mRadius = (float) Helper.dpToPixel(10.0f, getContext());
        this.mBound = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int dpToPixel = Helper.dpToPixel(2.0f, getContext());
        float f = (float) dpToPixel;
        this.mBound.set(f, f, (float) (i - dpToPixel), (float) (i2 - dpToPixel));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.mBound;
        float f = this.mRadius;
        canvas.drawRoundRect(rectF, f, f, this.mOuterPaint);
        RectF rectF2 = this.mInBound;
        float f2 = this.mRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.mInnerPaint);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(Helper.dpToPixel(100.0f, getContext()), Helper.dpToPixel(20.0f, getContext()));
    }

    public void setMax(int i) {
        this.mMax = i;
    }

    public void setProgress(int i) {
        this.mProgress = i;
        RectF rectF = this.mInBound;
        float f = this.mBoundGap;
        rectF.set(f, f, ((((float) getWidth()) - this.mBoundGap) * ((float) this.mProgress)) / ((float) this.mMax), ((float) getHeight()) - this.mBoundGap);
        invalidate();
    }
}
