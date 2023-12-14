package com.kaopiz.kprogresshud;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

class BackgroundLayout extends LinearLayout {
    private int mBackgroundColor;
    private float mCornerRadius;

    public BackgroundLayout(Context context) {
        super(context);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    @TargetApi(11)
    public BackgroundLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        initBackground(getContext().getResources().getColor(C1609R.color.kprogresshud_default_color), this.mCornerRadius);
    }

    private void initBackground(int i, float f) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(f);
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(gradientDrawable);
        } else {
            setBackgroundDrawable(gradientDrawable);
        }
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = (float) Helper.dpToPixel(f, getContext());
        initBackground(this.mBackgroundColor, this.mCornerRadius);
    }

    public void setBaseColor(int i) {
        this.mBackgroundColor = i;
        initBackground(this.mBackgroundColor, this.mCornerRadius);
    }
}
