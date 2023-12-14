package com.qmuiteam.qmui.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.lang.Character;

public class QMUIVerticalTextView extends TextView {
    private boolean mIsVerticalMode = true;
    private int[] mLineBreakIndex;
    private int mLineCount;
    private float[] mLineWidths;

    private void init() {
    }

    public QMUIVerticalTextView(Context context) {
        super(context);
        init();
    }

    public QMUIVerticalTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public QMUIVerticalTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DrawAllocation"})
    @TargetApi(16)
    public void onMeasure(int i, int i2) {
        int i3;
        TextPaint textPaint;
        float f;
        float f2;
        super.onMeasure(i, i2);
        if (this.mIsVerticalMode) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            float paddingLeft = (float) (getPaddingLeft() + getPaddingRight());
            float paddingTop = (float) (getPaddingTop() + getPaddingBottom());
            char[] charArray = getText().toString().toCharArray();
            TextPaint paint = getPaint();
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int paddingBottom = (mode2 == 0 ? Integer.MAX_VALUE : size2) - getPaddingBottom();
            this.mLineCount = 0;
            boolean z = true;
            this.mLineWidths = new float[(charArray.length + 1)];
            this.mLineBreakIndex = new int[(charArray.length + 1)];
            float f3 = paddingLeft;
            float paddingTop2 = (float) getPaddingTop();
            float f4 = paddingTop2;
            int i4 = 0;
            float f5 = paddingTop;
            int i5 = 0;
            while (i4 < charArray.length) {
                if (isCJKCharacter(charArray[i4]) ^ z) {
                    f = (float) (fontMetricsInt.descent - fontMetricsInt.ascent);
                    textPaint = paint;
                    f2 = paint.measureText(charArray, i4, 1);
                } else {
                    f = paint.measureText(charArray, i4, z ? 1 : 0);
                    textPaint = paint;
                    f2 = (float) (fontMetricsInt.descent - fontMetricsInt.ascent);
                }
                float f6 = paddingTop2 + f2;
                float f7 = f2;
                if (f6 > ((float) paddingBottom) && i4 > 0) {
                    if (f4 >= paddingTop2) {
                        paddingTop2 = f4;
                    }
                    this.mLineBreakIndex[i5] = i4 - 1;
                    f3 += this.mLineWidths[i5];
                    i5++;
                    f4 = paddingTop2;
                    paddingTop2 = f7;
                } else if (f4 < f6) {
                    paddingTop2 = f6;
                    f4 = paddingTop2;
                } else {
                    paddingTop2 = f6;
                }
                float[] fArr = this.mLineWidths;
                if (fArr[i5] < f) {
                    fArr[i5] = f;
                }
                if (i4 == charArray.length - 1) {
                    f3 += this.mLineWidths[i5];
                    f5 = f4 + ((float) getPaddingBottom());
                }
                i4++;
                paint = textPaint;
                z = true;
            }
            if (charArray.length > 0) {
                this.mLineCount = i5 + 1;
                i3 = 1;
                this.mLineBreakIndex[i5] = charArray.length - 1;
            } else {
                i3 = 1;
            }
            int i6 = this.mLineCount;
            if (i6 > i3) {
                int i7 = i6 - i3;
                for (int i8 = 0; i8 < i7; i8++) {
                    f3 += (this.mLineWidths[i8] * (getLineSpacingMultiplier() - 1.0f)) + getLineSpacingExtra();
                }
            }
            if (mode2 == 1073741824) {
                f5 = (float) size2;
            } else if (mode2 == Integer.MIN_VALUE) {
                f5 = Math.min(f5, (float) size2);
            }
            if (mode == 1073741824) {
                f3 = (float) size;
            } else if (mode == Integer.MIN_VALUE) {
                f3 = Math.min(f3, (float) size);
            }
            setMeasuredDimension((int) f3, (int) f5);
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(16)
    public void onDraw(Canvas canvas) {
        float f;
        Canvas canvas2 = canvas;
        if (!this.mIsVerticalMode) {
            super.onDraw(canvas);
        } else if (this.mLineCount != 0) {
            TextPaint paint = getPaint();
            paint.setColor(getCurrentTextColor());
            paint.drawableState = getDrawableState();
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            char[] charArray = getText().toString().toCharArray();
            canvas.save();
            float width = ((float) (getWidth() - getPaddingRight())) - this.mLineWidths[0];
            float f2 = width;
            float paddingTop = (float) getPaddingTop();
            int i = 0;
            int i2 = 0;
            while (i < charArray.length) {
                boolean z = !isCJKCharacter(charArray[i]);
                int save = canvas.save();
                if (z) {
                    canvas2.rotate(90.0f, width, paddingTop);
                }
                float f3 = width;
                canvas.drawText(charArray, i, 1, width, z ? (paddingTop - ((this.mLineWidths[i2] - ((float) (fontMetricsInt.bottom - fontMetricsInt.top))) / 2.0f)) - ((float) fontMetricsInt.descent) : paddingTop - ((float) fontMetricsInt.ascent), paint);
                canvas2.restoreToCount(save);
                int i3 = i + 1;
                if (i3 < charArray.length) {
                    if (i3 > this.mLineBreakIndex[i2]) {
                        int i4 = i2 + 1;
                        float[] fArr = this.mLineWidths;
                        if (i4 < fArr.length) {
                            width = f2 - ((fArr[i4] * getLineSpacingMultiplier()) + getLineSpacingExtra());
                            i2 = i4;
                            paddingTop = (float) getPaddingTop();
                            f2 = width;
                            i = i3;
                        }
                    }
                    if (z) {
                        f = paint.measureText(charArray, i, 1);
                    } else {
                        f = (float) (fontMetricsInt.descent - fontMetricsInt.ascent);
                    }
                    paddingTop += f;
                }
                width = f3;
                i = i3;
            }
            canvas.restore();
        }
    }

    private static boolean isCJKCharacter(char c) {
        Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        return of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || of == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || of == Character.UnicodeBlock.HANGUL_SYLLABLES || of == Character.UnicodeBlock.HANGUL_JAMO || of == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || of == Character.UnicodeBlock.HIRAGANA || of == Character.UnicodeBlock.KATAKANA || of == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS;
    }

    public void setVerticalMode(boolean z) {
        this.mIsVerticalMode = z;
        requestLayout();
    }

    public boolean isVerticalMode() {
        return this.mIsVerticalMode;
    }
}
