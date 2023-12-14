package com.aigestudio.wheelpicker.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

@TargetApi(9)
class OverScrollerCompat implements WheelScroller {
    private OverScroller mOverScroller;

    OverScrollerCompat(Context context) {
        this.mOverScroller = new OverScroller(context);
    }

    OverScrollerCompat(Context context, Interpolator interpolator) {
        this.mOverScroller = new OverScroller(context, interpolator);
    }

    OverScrollerCompat(Context context, Interpolator interpolator, float f, float f2) {
        this.mOverScroller = new OverScroller(context, interpolator, f, f2);
    }

    @TargetApi(11)
    OverScrollerCompat(Context context, Interpolator interpolator, float f, float f2, boolean z) {
        this.mOverScroller = new OverScroller(context, interpolator, f, f2, z);
    }

    public void abortAnimation() {
        this.mOverScroller.abortAnimation();
    }

    public boolean computeScrollOffset() {
        return this.mOverScroller.computeScrollOffset();
    }

    public void extendDuration(int i) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mOverScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mOverScroller.fling(i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }

    public void forceFinished(boolean z) {
        this.mOverScroller.forceFinished(z);
    }

    @TargetApi(14)
    public float getCurrVelocity() {
        return this.mOverScroller.getCurrVelocity();
    }

    public int getCurrX() {
        return this.mOverScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mOverScroller.getCurrY();
    }

    public int getDuration() {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    public int getFinalX() {
        return this.mOverScroller.getFinalX();
    }

    public int getFinalY() {
        return this.mOverScroller.getFinalY();
    }

    public int getStartX() {
        return this.mOverScroller.getStartX();
    }

    public int getStartY() {
        return this.mOverScroller.getStartY();
    }

    public boolean isFinished() {
        return this.mOverScroller.isFinished();
    }

    public void setFinalX(int i) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    public void setFinalY(int i) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    public boolean isOverScrolled() {
        return this.mOverScroller.isOverScrolled();
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mOverScroller.notifyHorizontalEdgeReached(i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mOverScroller.notifyVerticalEdgeReached(i, i2, i3);
    }

    @TargetApi(11)
    public void setFriction(float f) {
        this.mOverScroller.setFriction(f);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.mOverScroller.springBack(i, i2, i3, i4, i5, i6);
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        this.mOverScroller.startScroll(i, i2, i3, i4);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mOverScroller.startScroll(i, i2, i3, i4, i5);
    }

    public int timePassed() {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }
}
