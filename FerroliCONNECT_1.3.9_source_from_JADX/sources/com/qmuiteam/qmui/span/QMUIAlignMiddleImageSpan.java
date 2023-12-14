package com.qmuiteam.qmui.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class QMUIAlignMiddleImageSpan extends ImageSpan {
    public static final int ALIGN_MIDDLE = -100;
    private boolean mAvoidSuperChangeFontMetrics;
    private float mFontWidthMultiple;
    private int mWidth;

    public QMUIAlignMiddleImageSpan(Drawable drawable, int i) {
        super(drawable, i);
        this.mFontWidthMultiple = -1.0f;
        this.mAvoidSuperChangeFontMetrics = false;
    }

    public QMUIAlignMiddleImageSpan(Drawable drawable, int i, float f) {
        this(drawable, i);
        if (f >= 0.0f) {
            this.mFontWidthMultiple = f;
        }
    }

    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        if (this.mAvoidSuperChangeFontMetrics) {
            this.mWidth = getDrawable().getBounds().right;
        } else {
            this.mWidth = super.getSize(paint, charSequence, i, i2, fontMetricsInt);
        }
        if (this.mFontWidthMultiple > 0.0f) {
            this.mWidth = (int) (paint.measureText("子") * this.mFontWidthMultiple);
        }
        return this.mWidth;
    }

    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        if (this.mVerticalAlignment == -100) {
            Drawable drawable = getDrawable();
            canvas.save();
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            canvas.translate(f, (float) (i4 + fontMetricsInt.top + (((fontMetricsInt.bottom - fontMetricsInt.top) - (drawable.getBounds().bottom - drawable.getBounds().top)) / 2)));
            drawable.draw(canvas);
            canvas.restore();
            return;
        }
        super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
    }

    public void setAvoidSuperChangeFontMetrics(boolean z) {
        this.mAvoidSuperChangeFontMetrics = z;
    }
}
