package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.qmuiteam.qmui.util.QMUIWindowInsetHelper;

public class QMUIWindowInsetLayout extends FrameLayout implements IWindowInsetLayout {
    private QMUIWindowInsetHelper mQMUIWindowInsetHelper;

    public QMUIWindowInsetLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIWindowInsetLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMUIWindowInsetLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mQMUIWindowInsetHelper = new QMUIWindowInsetHelper(this, this);
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        if (Build.VERSION.SDK_INT < 19 || Build.VERSION.SDK_INT >= 21) {
            return super.fitSystemWindows(rect);
        }
        return applySystemWindowInsets19(rect);
    }

    public boolean applySystemWindowInsets19(Rect rect) {
        return this.mQMUIWindowInsetHelper.defaultApplySystemWindowInsets19(this, rect);
    }

    public boolean applySystemWindowInsets21(WindowInsetsCompat windowInsetsCompat) {
        return this.mQMUIWindowInsetHelper.defaultApplySystemWindowInsets21(this, windowInsetsCompat);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this);
        }
    }
}
