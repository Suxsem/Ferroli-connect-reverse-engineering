package com.qmuiteam.qmui.span;

import android.support.annotation.ColorInt;
import android.support.p000v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.qmuiteam.qmui.link.ITouchableSpan;

public abstract class QMUITouchableSpan extends ClickableSpan implements ITouchableSpan {
    private boolean mIsNeedUnderline = false;
    private boolean mIsPressed;
    @ColorInt
    private int mNormalBackgroundColor;
    @ColorInt
    private int mNormalTextColor;
    @ColorInt
    private int mPressedBackgroundColor;
    @ColorInt
    private int mPressedTextColor;

    public abstract void onSpanClick(View view);

    public final void onClick(View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            onSpanClick(view);
        }
    }

    public QMUITouchableSpan(@ColorInt int i, @ColorInt int i2, @ColorInt int i3, @ColorInt int i4) {
        this.mNormalTextColor = i;
        this.mPressedTextColor = i2;
        this.mNormalBackgroundColor = i3;
        this.mPressedBackgroundColor = i4;
    }

    public int getNormalBackgroundColor() {
        return this.mNormalBackgroundColor;
    }

    public int getNormalTextColor() {
        return this.mNormalTextColor;
    }

    public int getPressedBackgroundColor() {
        return this.mPressedBackgroundColor;
    }

    public int getPressedTextColor() {
        return this.mPressedTextColor;
    }

    public void setPressed(boolean z) {
        this.mIsPressed = z;
    }

    public boolean isPressed() {
        return this.mIsPressed;
    }

    public void setIsNeedUnderline(boolean z) {
        this.mIsNeedUnderline = z;
    }

    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(this.mIsPressed ? this.mPressedTextColor : this.mNormalTextColor);
        textPaint.bgColor = this.mIsPressed ? this.mPressedBackgroundColor : this.mNormalBackgroundColor;
        textPaint.setUnderlineText(this.mIsNeedUnderline);
    }
}
