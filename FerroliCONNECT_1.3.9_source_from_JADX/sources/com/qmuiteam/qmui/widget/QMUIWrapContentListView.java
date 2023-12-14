package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class QMUIWrapContentListView extends ListView {
    private int mMaxHeight = 536870911;

    public QMUIWrapContentListView(Context context) {
        super(context);
    }

    public QMUIWrapContentListView(Context context, int i) {
        super(context);
        this.mMaxHeight = i;
    }

    public QMUIWrapContentListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QMUIWrapContentListView(Context context, AttributeSet attributeSet, int i) {
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
