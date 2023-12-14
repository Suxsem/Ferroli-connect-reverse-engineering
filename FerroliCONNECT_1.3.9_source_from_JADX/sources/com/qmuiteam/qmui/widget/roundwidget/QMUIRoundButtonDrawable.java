package com.qmuiteam.qmui.widget.roundwidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.qmuiteam.qmui.C1614R;

public class QMUIRoundButtonDrawable extends GradientDrawable {
    private ColorStateList mFillColors;
    private boolean mRadiusAdjustBounds = true;
    private ColorStateList mStrokeColors;
    private int mStrokeWidth = 0;

    public void setBgData(@Nullable ColorStateList colorStateList) {
        if (hasNativeStateListAPI()) {
            super.setColor(colorStateList);
            return;
        }
        this.mFillColors = colorStateList;
        int i = 0;
        if (colorStateList != null) {
            i = colorStateList.getColorForState(getState(), 0);
        }
        setColor(i);
    }

    public void setStrokeData(int i, @Nullable ColorStateList colorStateList) {
        if (hasNativeStateListAPI()) {
            super.setStroke(i, colorStateList);
            return;
        }
        this.mStrokeWidth = i;
        this.mStrokeColors = colorStateList;
        int i2 = 0;
        if (colorStateList != null) {
            i2 = colorStateList.getColorForState(getState(), 0);
        }
        setStroke(i, i2);
    }

    private boolean hasNativeStateListAPI() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public void setIsRadiusAdjustBounds(boolean z) {
        this.mRadiusAdjustBounds = z;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList = this.mFillColors;
        if (colorStateList != null) {
            setColor(colorStateList.getColorForState(iArr, 0));
            onStateChange = true;
        }
        ColorStateList colorStateList2 = this.mStrokeColors;
        if (colorStateList2 == null) {
            return onStateChange;
        }
        setStroke(this.mStrokeWidth, colorStateList2.getColorForState(iArr, 0));
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.mStrokeColors;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            android.content.res.ColorStateList r0 = r1.mFillColors
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x001a
        L_0x000a:
            android.content.res.ColorStateList r0 = r1.mStrokeColors
            if (r0 == 0) goto L_0x0014
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x001a
        L_0x0014:
            boolean r0 = super.isStateful()
            if (r0 == 0) goto L_0x001c
        L_0x001a:
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable.isStateful():boolean");
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mRadiusAdjustBounds) {
            setCornerRadius((float) (Math.min(rect.width(), rect.height()) / 2));
        }
    }

    public static QMUIRoundButtonDrawable fromAttributeSet(Context context, AttributeSet attributeSet, int i) {
        boolean z = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIRoundButton, i, 0);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(C1614R.styleable.QMUIRoundButton_qmui_backgroundColor);
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(C1614R.styleable.QMUIRoundButton_qmui_borderColor);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_borderWidth, 0);
        boolean z2 = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIRoundButton_qmui_isRadiusAdjustBounds, false);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_radius, 0);
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_radiusTopLeft, 0);
        int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_radiusTopRight, 0);
        int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_radiusBottomLeft, 0);
        int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRoundButton_qmui_radiusBottomRight, 0);
        obtainStyledAttributes.recycle();
        QMUIRoundButtonDrawable qMUIRoundButtonDrawable = new QMUIRoundButtonDrawable();
        qMUIRoundButtonDrawable.setBgData(colorStateList);
        qMUIRoundButtonDrawable.setStrokeData(dimensionPixelSize, colorStateList2);
        if (dimensionPixelSize3 > 0 || dimensionPixelSize4 > 0 || dimensionPixelSize5 > 0 || dimensionPixelSize6 > 0) {
            float f = (float) dimensionPixelSize3;
            float f2 = (float) dimensionPixelSize4;
            float f3 = (float) dimensionPixelSize6;
            float f4 = (float) dimensionPixelSize5;
            qMUIRoundButtonDrawable.setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
        } else {
            qMUIRoundButtonDrawable.setCornerRadius((float) dimensionPixelSize2);
            if (dimensionPixelSize2 <= 0) {
                z = z2;
            }
        }
        qMUIRoundButtonDrawable.setIsRadiusAdjustBounds(z);
        return qMUIRoundButtonDrawable;
    }
}
