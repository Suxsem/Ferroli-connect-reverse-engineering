package com.baoyz.widget;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public abstract class RefreshDrawable extends Drawable implements Drawable.Callback, Animatable {
    private PullRefreshLayout mRefreshLayout;

    public int getOpacity() {
        return -3;
    }

    public abstract void offsetTopAndBottom(int i);

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public abstract void setColorSchemeColors(int[] iArr);

    public abstract void setPercent(float f);

    public RefreshDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        this.mRefreshLayout = pullRefreshLayout;
    }

    public Context getContext() {
        PullRefreshLayout pullRefreshLayout = this.mRefreshLayout;
        if (pullRefreshLayout != null) {
            return pullRefreshLayout.getContext();
        }
        return null;
    }

    public PullRefreshLayout getRefreshLayout() {
        return this.mRefreshLayout;
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
