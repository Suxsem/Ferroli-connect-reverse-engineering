package com.qmuiteam.qmui;

import android.support.p000v4.view.animation.FastOutLinearInInterpolator;
import android.support.p000v4.view.animation.FastOutSlowInInterpolator;
import android.support.p000v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class QMUIInterpolatorStaticHolder {
    public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    public static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();
}
