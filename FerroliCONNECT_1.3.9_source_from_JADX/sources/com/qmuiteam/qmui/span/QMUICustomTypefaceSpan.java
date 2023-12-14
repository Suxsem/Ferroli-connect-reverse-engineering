package com.qmuiteam.qmui.span;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class QMUICustomTypefaceSpan extends TypefaceSpan {
    public static final Parcelable.Creator<QMUICustomTypefaceSpan> CREATOR = new Parcelable.Creator<QMUICustomTypefaceSpan>() {
        public QMUICustomTypefaceSpan createFromParcel(Parcel parcel) {
            return null;
        }

        public QMUICustomTypefaceSpan[] newArray(int i) {
            return new QMUICustomTypefaceSpan[i];
        }
    };
    @Nullable
    private final Typeface newType;

    public QMUICustomTypefaceSpan(String str, @Nullable Typeface typeface) {
        super(str);
        this.newType = typeface;
    }

    private static void applyCustomTypeFace(Paint paint, @Nullable Typeface typeface) {
        int i;
        if (typeface != null) {
            Typeface typeface2 = paint.getTypeface();
            if (typeface2 == null) {
                i = 0;
            } else {
                i = typeface2.getStyle();
            }
            int style = i & (typeface.getStyle() ^ -1);
            if ((style & 1) != 0) {
                paint.setFakeBoldText(true);
            }
            if ((style & 2) != 0) {
                paint.setTextSkewX(-0.25f);
            }
            paint.setTypeface(typeface);
        }
    }

    public void updateDrawState(TextPaint textPaint) {
        applyCustomTypeFace(textPaint, this.newType);
    }

    public void updateMeasureState(TextPaint textPaint) {
        applyCustomTypeFace(textPaint, this.newType);
    }
}
