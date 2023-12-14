package com.qmuiteam.qmui.widget.roundwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class QMUIRoundFrameLayout extends FrameLayout {
    public QMUIRoundFrameLayout(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0);
    }

    public QMUIRoundFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public QMUIRoundFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        QMUIViewHelper.setBackgroundKeepingPadding((View) this, (Drawable) QMUIRoundButtonDrawable.fromAttributeSet(context, attributeSet, i));
    }
}
