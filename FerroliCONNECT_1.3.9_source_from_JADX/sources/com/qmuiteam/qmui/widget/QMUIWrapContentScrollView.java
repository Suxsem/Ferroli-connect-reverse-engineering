package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class QMUIWrapContentScrollView extends QMUIObservableScrollView {
    private int mMaxHeight = 536870911;

    public QMUIWrapContentScrollView(Context context) {
        super(context);
    }

    public QMUIWrapContentScrollView(Context context, int i) {
        super(context);
        this.mMaxHeight = i;
    }

    public QMUIWrapContentScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QMUIWrapContentScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setMaxHeight(int i) {
        if (this.mMaxHeight != i) {
            this.mMaxHeight = i;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, Integer.MIN_VALUE));
    }
}
