package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class PieView extends View implements Determinate {
    private RectF mBound;
    private Paint mGreyPaint;
    private int mMax = 100;
    private int mProgress = 0;
    private Paint mWhitePaint;

    public PieView(Context context) {
        super(context);
        init();
    }

    public PieView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PieView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mWhitePaint = new Paint(1);
        this.mWhitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mWhitePaint.setStrokeWidth((float) Helper.dpToPixel(0.1f, getContext()));
        this.mWhitePaint.setColor(-1);
        this.mGreyPaint = new Paint(1);
        this.mGreyPaint.setStyle(Paint.Style.STROKE);
        this.mGreyPaint.setStrokeWidth((float) Helper.dpToPixel(2.0f, getContext()));
        this.mGreyPaint.setColor(-1);
        this.mBound = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int dpToPixel = Helper.dpToPixel(4.0f, getContext());
        float f = (float) dpToPixel;
        this.mBound.set(f, f, (float) (i - dpToPixel), (float) (i2 - dpToPixel));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.mBound, 270.0f, (((float) this.mProgress) * 360.0f) / ((float) this.mMax), true, this.mWhitePaint);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) ((getWidth() / 2) - Helper.dpToPixel(4.0f, getContext())), this.mGreyPaint);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int dpToPixel = Helper.dpToPixel(40.0f, getContext());
        setMeasuredDimension(dpToPixel, dpToPixel);
    }

    public void setMax(int i) {
        this.mMax = i;
    }

    public void setProgress(int i) {
        this.mProgress = i;
        invalidate();
    }
}
