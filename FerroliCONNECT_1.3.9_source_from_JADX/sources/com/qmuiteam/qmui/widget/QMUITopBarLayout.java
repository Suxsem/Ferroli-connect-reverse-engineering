package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.p000v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class QMUITopBarLayout extends FrameLayout {
    private QMUITopBar mTopBar;
    private int mTopBarBgColor;
    private Drawable mTopBarBgWithSeparatorDrawableCache;
    private int mTopBarSeparatorColor;
    private int mTopBarSeparatorHeight;

    public QMUITopBarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUITopBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUITopBarStyle);
    }

    public QMUITopBarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1614R.styleable.QMUITopBar, C1614R.attr.QMUITopBarStyle, 0);
        this.mTopBarSeparatorColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_separator_color, ContextCompat.getColor(context, C1614R.color.qmui_config_color_separator));
        this.mTopBarSeparatorHeight = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_separator_height, 1);
        this.mTopBarBgColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_bg_color, -1);
        boolean z = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUITopBar_qmui_topbar_need_separator, true);
        this.mTopBar = new QMUITopBar(context, true);
        this.mTopBar.getCommonFieldFormTypedArray(context, obtainStyledAttributes);
        addView(this.mTopBar, new FrameLayout.LayoutParams(-1, QMUIResHelper.getAttrDimen(context, C1614R.attr.qmui_topbar_height)));
        obtainStyledAttributes.recycle();
        setBackgroundDividerEnabled(z);
    }

    public void setCenterView(View view) {
        this.mTopBar.setCenterView(view);
    }

    public TextView setTitle(int i) {
        return this.mTopBar.setTitle(i);
    }

    public TextView setTitle(String str) {
        return this.mTopBar.setTitle(str);
    }

    public TextView setEmojiTitle(String str) {
        return this.mTopBar.setEmojiTitle(str);
    }

    public void showTitlteView(boolean z) {
        this.mTopBar.showTitleView(z);
    }

    public void setSubTitle(int i) {
        this.mTopBar.setSubTitle(i);
    }

    public void setSubTitle(String str) {
        this.mTopBar.setSubTitle(str);
    }

    public void setTitleGravity(int i) {
        this.mTopBar.setTitleGravity(i);
    }

    public void addLeftView(View view, int i) {
        this.mTopBar.addLeftView(view, i);
    }

    public void addLeftView(View view, int i, RelativeLayout.LayoutParams layoutParams) {
        this.mTopBar.addLeftView(view, i, layoutParams);
    }

    public void addRightView(View view, int i) {
        this.mTopBar.addRightView(view, i);
    }

    public void addRightView(View view, int i, RelativeLayout.LayoutParams layoutParams) {
        this.mTopBar.addRightView(view, i, layoutParams);
    }

    public QMUIAlphaImageButton addRightImageButton(int i, int i2) {
        return this.mTopBar.addRightImageButton(i, i2);
    }

    public QMUIAlphaImageButton addLeftImageButton(int i, int i2) {
        return this.mTopBar.addLeftImageButton(i, i2);
    }

    public Button addLeftTextButton(int i, int i2) {
        return this.mTopBar.addLeftTextButton(i, i2);
    }

    public Button addLeftTextButton(String str, int i) {
        return this.mTopBar.addLeftTextButton(str, i);
    }

    public Button addRightTextButton(int i, int i2) {
        return this.mTopBar.addRightTextButton(i, i2);
    }

    public Button addRightTextButton(String str, int i) {
        return this.mTopBar.addRightTextButton(str, i);
    }

    public QMUIAlphaImageButton addLeftBackImageButton() {
        return this.mTopBar.addLeftBackImageButton();
    }

    public void removeAllLeftViews() {
        this.mTopBar.removeAllLeftViews();
    }

    public void removeAllRightViews() {
        this.mTopBar.removeAllRightViews();
    }

    public void removeCenterViewAndTitleView() {
        this.mTopBar.removeCenterViewAndTitleView();
    }

    public void setBackgroundAlpha(int i) {
        getBackground().setAlpha(i);
    }

    public int computeAndSetBackgroundAlpha(int i, int i2, int i3) {
        int max = (int) (Math.max(0.0d, Math.min((double) (((float) (i - i2)) / ((float) (i3 - i2))), 1.0d)) * 255.0d);
        setBackgroundAlpha(max);
        return max;
    }

    public void setBackgroundDividerEnabled(boolean z) {
        if (z) {
            if (this.mTopBarBgWithSeparatorDrawableCache == null) {
                this.mTopBarBgWithSeparatorDrawableCache = QMUIDrawableHelper.createItemSeparatorBg(this.mTopBarSeparatorColor, this.mTopBarBgColor, this.mTopBarSeparatorHeight, false);
            }
            QMUIViewHelper.setBackgroundKeepingPadding((View) this, this.mTopBarBgWithSeparatorDrawableCache);
            return;
        }
        QMUIViewHelper.setBackgroundColorKeepPadding(this, this.mTopBarBgColor);
    }
}
