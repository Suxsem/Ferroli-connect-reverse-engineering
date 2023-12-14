package com.gyf.barlibrary;

import android.database.ContentObserver;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.p000v4.view.ViewCompat;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class BarParams implements Cloneable {
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public boolean darkFont = false;
    public boolean fits = false;
    @Deprecated
    public boolean fixMarginAtBottom = false;
    @ColorInt
    public int flymeOSStatusBarFontColor;
    public boolean fullScreen = false;
    public boolean fullScreenTemp = this.fullScreen;
    public boolean isSupportActionBar = false;
    public boolean keyboardEnable = false;
    public int keyboardMode = 18;
    public KeyboardPatch keyboardPatch;
    @FloatRange(from = 0.0d, mo101to = 1.0d)
    float navigationBarAlpha = 0.0f;
    @ColorInt
    public int navigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    public int navigationBarColorTemp = this.navigationBarColor;
    @ColorInt
    public int navigationBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    public boolean navigationBarEnable = true;
    public View navigationBarView;
    public boolean navigationBarWithKitkatEnable = true;
    public ContentObserver navigationStatusObserver;
    public OnKeyboardListener onKeyboardListener;
    @FloatRange(from = 0.0d, mo101to = 1.0d)
    public float statusBarAlpha = 0.0f;
    @ColorInt
    public int statusBarColor = 0;
    @ColorInt
    public int statusBarColorContentView = 0;
    @ColorInt
    public int statusBarColorContentViewTransform = ViewCompat.MEASURED_STATE_MASK;
    @ColorInt
    public int statusBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    @FloatRange(from = 0.0d, mo101to = 1.0d)
    public float statusBarContentViewAlpha = 0.0f;
    public boolean statusBarFlag = true;
    public View statusBarView;
    public View statusBarViewByHeight;
    public boolean systemWindows = false;
    public int titleBarHeight;
    public int titleBarPaddingTopHeight;
    public View titleBarView;
    public View titleBarViewMarginTop;
    public boolean titleBarViewMarginTopFlag = false;
    @FloatRange(from = 0.0d, mo101to = 1.0d)
    public float viewAlpha = 0.0f;
    public Map<View, Map<Integer, Integer>> viewMap = new HashMap();

    /* access modifiers changed from: protected */
    public BarParams clone() {
        try {
            return (BarParams) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
