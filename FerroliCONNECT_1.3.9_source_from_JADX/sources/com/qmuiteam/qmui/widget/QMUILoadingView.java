package com.qmuiteam.qmui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class QMUILoadingView extends View {
    private static final int DEGREE_PER_LINE = 30;
    private static final int LINE_COUNT = 12;
    /* access modifiers changed from: private */
    public int mAnimateValue;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private int mPaintColor;
    private int mSize;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;

    public QMUILoadingView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUILoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUILoadingStyle);
    }

    public QMUILoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int unused = QMUILoadingView.this.mAnimateValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                QMUILoadingView.this.invalidate();
            }
        };
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1614R.styleable.QMUILoadingView, i, 0);
        this.mSize = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUILoadingView_qmui_loading_view_size, QMUIDisplayHelper.dp2px(context, 32));
        this.mPaintColor = obtainStyledAttributes.getInt(C1614R.styleable.QMUILoadingView_android_color, -1);
        obtainStyledAttributes.recycle();
        initPaint();
    }

    public QMUILoadingView(Context context, int i, int i2) {
        super(context);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int unused = QMUILoadingView.this.mAnimateValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                QMUILoadingView.this.invalidate();
            }
        };
        this.mSize = i;
        this.mPaintColor = i2;
        initPaint();
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setColor(this.mPaintColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int i) {
        this.mPaintColor = i;
        this.mPaint.setColor(i);
        invalidate();
    }

    public void setSize(int i) {
        this.mSize = i;
        requestLayout();
    }

    public void start() {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator == null) {
            this.mAnimator = ValueAnimator.ofInt(new int[]{0, 11});
            this.mAnimator.addUpdateListener(this.mUpdateListener);
            this.mAnimator.setDuration(600);
            this.mAnimator.setRepeatMode(1);
            this.mAnimator.setRepeatCount(-1);
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.start();
        } else if (!valueAnimator.isStarted()) {
            this.mAnimator.start();
        }
    }

    public void stop() {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeUpdateListener(this.mUpdateListener);
            this.mAnimator.removeAllUpdateListeners();
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
    }

    private void drawLoading(Canvas canvas, int i) {
        int i2 = this.mSize;
        int i3 = i2 / 12;
        int i4 = i2 / 6;
        this.mPaint.setStrokeWidth((float) i3);
        int i5 = this.mSize;
        canvas.rotate((float) i, (float) (i5 / 2), (float) (i5 / 2));
        int i6 = this.mSize;
        canvas.translate((float) (i6 / 2), (float) (i6 / 2));
        int i7 = 0;
        while (i7 < 12) {
            canvas.rotate(30.0f);
            i7++;
            this.mPaint.setAlpha((int) ((((float) i7) * 255.0f) / 12.0f));
            int i8 = i3 / 2;
            canvas.translate(0.0f, (float) (((-this.mSize) / 2) + i8));
            canvas.drawLine(0.0f, 0.0f, 0.0f, (float) i4, this.mPaint);
            canvas.translate(0.0f, (float) ((this.mSize / 2) - i8));
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = this.mSize;
        setMeasuredDimension(i3, i3);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), (Paint) null, 31);
        drawLoading(canvas, this.mAnimateValue * 30);
        canvas.restoreToCount(saveLayer);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(@NonNull View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            start();
        } else {
            stop();
        }
    }
}
