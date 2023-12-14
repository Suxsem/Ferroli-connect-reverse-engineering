package com.qmuiteam.qmui.layout;

import android.support.annotation.ColorInt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IQMUILayout {
    public static final int HIDE_RADIUS_SIDE_BOTTOM = 3;
    public static final int HIDE_RADIUS_SIDE_LEFT = 4;
    public static final int HIDE_RADIUS_SIDE_NONE = 0;
    public static final int HIDE_RADIUS_SIDE_RIGHT = 2;
    public static final int HIDE_RADIUS_SIDE_TOP = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HideRadiusSide {
    }

    int getHideRadiusSide();

    int getRadius();

    float getShadowAlpha();

    int getShadowElevation();

    void onlyShowBottomDivider(int i, int i2, int i3, int i4);

    void onlyShowLeftDivider(int i, int i2, int i3, int i4);

    void onlyShowRightDivider(int i, int i2, int i3, int i4);

    void onlyShowTopDivider(int i, int i2, int i3, int i4);

    void setBorderColor(@ColorInt int i);

    void setBorderWidth(int i);

    void setBottomDividerAlpha(int i);

    boolean setHeightLimit(int i);

    void setHideRadiusSide(int i);

    void setLeftDividerAlpha(int i);

    void setOutlineExcludePadding(boolean z);

    void setOutlineInset(int i, int i2, int i3, int i4);

    void setRadius(int i);

    void setRadius(int i, int i2);

    void setRadiusAndShadow(int i, int i2, float f);

    void setRadiusAndShadow(int i, int i2, int i3, float f);

    void setRightDividerAlpha(int i);

    void setShadowAlpha(float f);

    void setShadowElevation(int i);

    void setShowBorderOnlyBeforeL(boolean z);

    void setTopDividerAlpha(int i);

    void setUseThemeGeneralShadowElevation();

    boolean setWidthLimit(int i);

    void updateBottomDivider(int i, int i2, int i3, int i4);

    void updateLeftDivider(int i, int i2, int i3, int i4);

    void updateRightDivider(int i, int i2, int i3, int i4);

    void updateTopDivider(int i, int i2, int i3, int i4);
}
