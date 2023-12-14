package com.aigestudio.wheelpicker.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

class ScrollerCompat implements WheelScroller {
    private Scroller mScroller;

    ScrollerCompat(Context context) {
        this.mScroller = new Scroller(context);
    }

    ScrollerCompat(Context context, Interpolator interpolator) {
        this.mScroller = new Scroller(context, interpolator);
    }

    @TargetApi(11)
    ScrollerCompat(Context context, Interpolator interpolator, boolean z) {
        this.mScroller = new Scroller(context, interpolator, z);
    }

    public void abortAnimation() {
        this.mScroller.abortAnimation();
    }

    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    public void extendDuration(int i) {
        this.mScroller.extendDuration(i);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public void forceFinished(boolean z) {
        this.mScroller.forceFinished(z);
    }

    @TargetApi(14)
    public float getCurrVelocity() {
        return this.mScroller.getCurrVelocity();
    }

    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    public int getDuration() {
        return this.mScroller.getDuration();
    }

    public int getFinalX() {
        return this.mScroller.getFinalX();
    }

    public int getFinalY() {
        return this.mScroller.getFinalY();
    }

    @TargetApi(3)
    public int getStartX() {
        return this.mScroller.getStartX();
    }

    @TargetApi(3)
    public int getStartY() {
        return this.mScroller.getStartY();
    }

    public boolean isFinished() {
        return this.mScroller.isFinished();
    }

    public void setFinalX(int i) {
        this.mScroller.setFinalX(i);
    }

    public void setFinalY(int i) {
        this.mScroller.setFinalY(i);
    }

    public boolean isOverScrolled() {
        throw new RuntimeException("ScrollerCompat not support this method.");
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        throw new RuntimeException("ScrollerCompat not support this method.");
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        throw new RuntimeException("ScrollerCompat not support this method.");
    }

    @TargetApi(11)
    public void setFriction(float f) {
        this.mScroller.setFriction(f);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        throw new RuntimeException("ScrollerCompat not support this method.");
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        this.mScroller.startScroll(i, i2, i3, i4);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mScroller.startScroll(i, i2, i3, i4, i5);
    }

    public int timePassed() {
        return this.mScroller.timePassed();
    }
}
