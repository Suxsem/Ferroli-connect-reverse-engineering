package com.qmuiteam.qmui.widget.roundwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class QMUIRoundLinearLayout extends LinearLayout {
    public QMUIRoundLinearLayout(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0);
    }

    public QMUIRoundLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, C1614R.attr.QMUIButtonStyle);
    }

    public QMUIRoundLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        QMUIViewHelper.setBackgroundKeepingPadding((View) this, (Drawable) QMUIRoundButtonDrawable.fromAttributeSet(context, attributeSet, 0));
    }
}
