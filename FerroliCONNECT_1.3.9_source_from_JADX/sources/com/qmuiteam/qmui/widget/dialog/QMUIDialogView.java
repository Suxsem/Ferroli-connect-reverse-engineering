package com.qmuiteam.qmui.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIResHelper;

public class QMUIDialogView extends QMUILinearLayout {
    private int mMaxWidth;
    private int mMinWidth;
    private OnDecorationListener mOnDecorationListener;

    public interface OnDecorationListener {
        void onDraw(Canvas canvas, QMUIDialogView qMUIDialogView);

        void onDrawOver(Canvas canvas, QMUIDialogView qMUIDialogView);
    }

    public QMUIDialogView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIDialogView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMUIDialogView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinWidth = QMUIResHelper.getAttrDimen(context, C1614R.attr.qmui_dialog_min_width);
        this.mMaxWidth = QMUIResHelper.getAttrDimen(context, C1614R.attr.qmui_dialog_max_width);
    }

    public void setOnDecorationListener(OnDecorationListener onDecorationListener) {
        this.mOnDecorationListener = onDecorationListener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int i4 = this.mMaxWidth;
        if (i4 > 0 && size > i4) {
            i = View.MeasureSpec.makeMeasureSpec(i4, mode);
        }
        super.onMeasure(i, i2);
        if (mode == Integer.MIN_VALUE && getMeasuredWidth() < (i3 = this.mMinWidth) && i3 < size) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        OnDecorationListener onDecorationListener = this.mOnDecorationListener;
        if (onDecorationListener != null) {
            onDecorationListener.onDraw(canvas, this);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        OnDecorationListener onDecorationListener = this.mOnDecorationListener;
        if (onDecorationListener != null) {
            onDecorationListener.onDrawOver(canvas, this);
        }
    }
}
