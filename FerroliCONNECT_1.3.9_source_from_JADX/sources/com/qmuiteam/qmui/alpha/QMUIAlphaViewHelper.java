package com.qmuiteam.qmui.alpha;

import android.support.annotation.NonNull;
import android.view.View;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIResHelper;
import java.lang.ref.WeakReference;

public class QMUIAlphaViewHelper {
    private boolean mChangeAlphaWhenDisable = true;
    private boolean mChangeAlphaWhenPress = true;
    private float mDisabledAlpha = 0.5f;
    private float mNormalAlpha = 1.0f;
    private float mPressedAlpha = 0.5f;
    private WeakReference<View> mTarget;

    public QMUIAlphaViewHelper(@NonNull View view) {
        this.mTarget = new WeakReference<>(view);
        this.mPressedAlpha = QMUIResHelper.getAttrFloatValue(view.getContext(), C1614R.attr.qmui_alpha_pressed);
        this.mDisabledAlpha = QMUIResHelper.getAttrFloatValue(view.getContext(), C1614R.attr.qmui_alpha_disabled);
    }

    public QMUIAlphaViewHelper(@NonNull View view, float f, float f2) {
        this.mTarget = new WeakReference<>(view);
        this.mPressedAlpha = f;
        this.mDisabledAlpha = f2;
    }

    public void onPressedChanged(View view, boolean z) {
        View view2 = (View) this.mTarget.get();
        if (view2 != null) {
            if (view.isEnabled()) {
                view2.setAlpha((!this.mChangeAlphaWhenPress || !z || !view.isClickable()) ? this.mNormalAlpha : this.mPressedAlpha);
            } else if (this.mChangeAlphaWhenDisable) {
                view2.setAlpha(this.mDisabledAlpha);
            }
        }
    }

    public void onEnabledChanged(View view, boolean z) {
        float f;
        View view2 = (View) this.mTarget.get();
        if (view2 != null) {
            if (this.mChangeAlphaWhenDisable) {
                f = z ? this.mNormalAlpha : this.mDisabledAlpha;
            } else {
                f = this.mNormalAlpha;
            }
            if (!(view == view2 || view2.isEnabled() == z)) {
                view2.setEnabled(z);
            }
            view2.setAlpha(f);
        }
    }

    public void setChangeAlphaWhenPress(boolean z) {
        this.mChangeAlphaWhenPress = z;
    }

    public void setChangeAlphaWhenDisable(boolean z) {
        this.mChangeAlphaWhenDisable = z;
        View view = (View) this.mTarget.get();
        if (view != null) {
            onEnabledChanged(view, view.isEnabled());
        }
    }
}
