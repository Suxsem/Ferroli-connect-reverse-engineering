package com.aigestudio.wheelpicker.core;

public interface WheelScroller {
    void abortAnimation();

    boolean computeScrollOffset();

    void extendDuration(int i);

    void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    void forceFinished(boolean z);

    float getCurrVelocity();

    int getCurrX();

    int getCurrY();

    int getDuration();

    int getFinalX();

    int getFinalY();

    int getStartX();

    int getStartY();

    boolean isFinished();

    boolean isOverScrolled();

    void notifyHorizontalEdgeReached(int i, int i2, int i3);

    void notifyVerticalEdgeReached(int i, int i2, int i3);

    void setFinalX(int i);

    void setFinalY(int i);

    void setFriction(float f);

    boolean springBack(int i, int i2, int i3, int i4, int i5, int i6);

    void startScroll(int i, int i2, int i3, int i4);

    void startScroll(int i, int i2, int i3, int i4, int i5);

    int timePassed();
}
