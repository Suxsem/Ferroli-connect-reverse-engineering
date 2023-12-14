package com.qmuiteam.qmui.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class QMUIMarginImageSpan extends QMUIAlignMiddleImageSpan {
    private int mOffsetY;
    private int mSpanMarginLeft;
    private int mSpanMarginRight;

    public QMUIMarginImageSpan(Drawable drawable, int i, int i2, int i3) {
        super(drawable, i);
        this.mSpanMarginLeft = 0;
        this.mSpanMarginRight = 0;
        this.mOffsetY = 0;
        this.mSpanMarginLeft = i2;
        this.mSpanMarginRight = i3;
    }

    public QMUIMarginImageSpan(Drawable drawable, int i, int i2, int i3, int i4) {
        this(drawable, i, i2, i3);
        this.mOffsetY = i4;
    }

    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        if (this.mSpanMarginLeft == 0 && this.mSpanMarginRight == 0) {
            return super.getSize(paint, charSequence, i, i2, fontMetricsInt);
        }
        super.getSize(paint, charSequence, i, i2, fontMetricsInt);
        return getDrawable().getIntrinsicWidth() + this.mSpanMarginLeft + this.mSpanMarginRight;
    }

    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        canvas.save();
        Canvas canvas2 = canvas;
        canvas.translate(0.0f, (float) this.mOffsetY);
        super.draw(canvas, charSequence, i, i2, f + ((float) this.mSpanMarginLeft), i3, i4, i5, paint);
        canvas.restore();
    }
}
