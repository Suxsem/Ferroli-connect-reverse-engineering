package com.qmuiteam.qmui.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;

public class QMUIBlockSpaceSpan extends ReplacementSpan {
    private int mHeight;

    public void draw(@NonNull Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, @NonNull Paint paint) {
    }

    public QMUIBlockSpaceSpan(int i) {
        this.mHeight = i;
    }

    public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        if (fontMetricsInt != null && !QMUIDeviceHelper.isMeizu()) {
            int fontMetricsInt2 = (-this.mHeight) - paint.getFontMetricsInt(fontMetricsInt);
            fontMetricsInt.top = fontMetricsInt2;
            fontMetricsInt.ascent = fontMetricsInt2;
            fontMetricsInt.bottom = 0;
            fontMetricsInt.descent = 0;
        }
        return 0;
    }
}
