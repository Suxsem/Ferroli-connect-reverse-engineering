package com.qmuiteam.qmui.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

public class QMUITextSizeSpan extends ReplacementSpan {
    private Paint mPaint;
    private int mTextSize;
    private int mVerticalOffset;

    public QMUITextSizeSpan(int i, int i2) {
        this.mTextSize = i;
        this.mVerticalOffset = i2;
    }

    public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        this.mPaint = new Paint(paint);
        this.mPaint.setTextSize((float) this.mTextSize);
        if (((float) this.mTextSize) > paint.getTextSize() && fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt2 = this.mPaint.getFontMetricsInt();
            fontMetricsInt.descent = fontMetricsInt2.descent;
            fontMetricsInt.ascent = fontMetricsInt2.ascent;
            fontMetricsInt.top = fontMetricsInt2.top;
            fontMetricsInt.bottom = fontMetricsInt2.bottom;
        }
        return (int) this.mPaint.measureText(charSequence, i, i2);
    }

    public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
        canvas.drawText(charSequence, i, i2, f, (float) (i4 + this.mVerticalOffset), this.mPaint);
    }
}
