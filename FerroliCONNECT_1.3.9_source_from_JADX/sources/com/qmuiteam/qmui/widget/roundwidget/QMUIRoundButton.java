package com.qmuiteam.qmui.widget.roundwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class QMUIRoundButton extends QMUIAlphaButton {
    public QMUIRoundButton(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0);
    }

    public QMUIRoundButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1614R.attr.QMUIButtonStyle);
        init(context, attributeSet, C1614R.attr.QMUIButtonStyle);
    }

    public QMUIRoundButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        QMUIViewHelper.setBackgroundKeepingPadding((View) this, (Drawable) QMUIRoundButtonDrawable.fromAttributeSet(context, attributeSet, i));
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }
}
