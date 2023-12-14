package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class QMUIFontFitTextView extends TextView {
    private Paint mTestPaint;
    private float maxSize;
    private float minSize;

    public QMUIFontFitTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIFontFitTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTestPaint = new Paint();
        this.mTestPaint.set(getPaint());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIFontFitTextView);
        this.minSize = (float) obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIFontFitTextView_qmui_minTextSize, Math.round(QMUIDisplayHelper.DENSITY * 14.0f));
        this.maxSize = (float) obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIFontFitTextView_qmui_maxTextSize, Math.round(QMUIDisplayHelper.DENSITY * 18.0f));
        obtainStyledAttributes.recycle();
    }

    private void refitText(String str, int i) {
        if (i > 0) {
            int paddingLeft = (i - getPaddingLeft()) - getPaddingRight();
            float f = this.maxSize;
            float f2 = this.minSize;
            this.mTestPaint.set(getPaint());
            this.mTestPaint.setTextSize(this.maxSize);
            float f3 = (float) paddingLeft;
            if (this.mTestPaint.measureText(str) <= f3) {
                f2 = this.maxSize;
            } else {
                this.mTestPaint.setTextSize(this.minSize);
                if (this.mTestPaint.measureText(str) < f3) {
                    while (f - f2 > 0.5f) {
                        float f4 = (f + f2) / 2.0f;
                        this.mTestPaint.setTextSize(f4);
                        if (this.mTestPaint.measureText(str) >= f3) {
                            f = f4;
                        } else {
                            f2 = f4;
                        }
                    }
                }
            }
            setTextSize(0, f2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int measuredHeight = getMeasuredHeight();
        refitText(getText().toString(), size);
        setMeasuredDimension(size, measuredHeight);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        refitText(charSequence.toString(), getWidth());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i != i3) {
            refitText(getText().toString(), i);
        }
    }
}
