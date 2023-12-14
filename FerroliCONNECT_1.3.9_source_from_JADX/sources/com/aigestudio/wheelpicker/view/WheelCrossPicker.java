package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.core.WheelScroller;
import java.util.List;

public abstract class WheelCrossPicker extends AbstractWheelPicker implements IWheelCrossPicker, Runnable {
    public static final int HORIZONTAL = 0;
    private static final int TIME_REFRESH = 16;
    public static final int VERTICAL = 1;
    protected ICrossOrientation mOrientation;
    protected Rect rectCurDecor;
    protected Rect rectCurItem;
    protected Rect rectLast;
    protected Rect rectNext;
    protected int unit;
    protected int unitDeltaMax;
    protected int unitDeltaMin;
    protected int unitDeltaTotal;
    protected int unitDisplayMax;
    protected int unitDisplayMin;

    public void clearCache() {
    }

    /* access modifiers changed from: protected */
    public void drawBackground(Canvas canvas) {
    }

    /* access modifiers changed from: protected */
    public void onTouchDown(MotionEvent motionEvent) {
    }

    public WheelCrossPicker(Context context) {
        super(context);
    }

    public WheelCrossPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void instantiation() {
        super.instantiation();
        this.mOrientation = new CrossVerImpl();
        this.rectCurDecor = new Rect();
        this.rectCurItem = new Rect();
        this.rectLast = new Rect();
        this.rectNext = new Rect();
    }

    public void setOrientation(int i) {
        this.mOrientation = i == 0 ? new CrossHorImpl() : new CrossVerImpl();
        computeWheelSizes();
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mOrientation.computeCurItemRect(this.rectCurItem, this.itemSpace, i, i2, this.maxTextWidth, this.maxTextHeight, this.wheelCenterX, this.wheelCenterY, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        ICrossOrientation iCrossOrientation = this.mOrientation;
        Rect rect = this.rectLast;
        Rect rect2 = this.rectNext;
        iCrossOrientation.computeRectPadding(rect, rect2, this.rectCurItem, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        this.rectCurDecor.set(this.rectCurItem);
        if (!this.ignorePadding) {
            this.mOrientation.removePadding(this.rectCurDecor, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    /* access modifiers changed from: protected */
    public void drawForeground(Canvas canvas) {
        if (this.mWheelDecor != null) {
            canvas.save();
            canvas.clipRect(this.rectCurDecor);
            this.mWheelDecor.drawDecor(canvas, this.rectLast, this.rectNext, this.mPaint);
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public void onTouchMove(MotionEvent motionEvent) {
        onWheelScrollStateChanged(1);
        onWheelScrolling((float) (this.disTotalMoveX + this.diSingleMoveX), (float) (this.disTotalMoveY + this.diSingleMoveY));
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onTouchUp(MotionEvent motionEvent) {
        this.mOrientation.fling(this.mScroller, this.mTracker, this.unitDeltaTotal, this.unitDeltaMin, this.unitDeltaMax, this.unitDisplayMax);
        onWheelScrollStateChanged(2);
        this.mHandler.post(this);
    }

    public void run() {
        if (this.mScroller.isFinished()) {
            onWheelScrollStateChanged(0);
            correctLocation();
            confirmData();
        }
        if (this.mScroller.computeScrollOffset()) {
            this.disTotalMoveX = this.mScroller.getCurrX();
            this.disTotalMoveY = this.mScroller.getCurrY();
            this.unitDeltaTotal = this.mOrientation.getUnitDeltaTotal(this.mScroller);
            onWheelScrolling((float) this.disTotalMoveX, (float) this.disTotalMoveY);
            postInvalidate();
            this.mHandler.postDelayed(this, 16);
        }
    }

    private void confirmData() {
        if (this.state == 0) {
            int min = Math.min(this.data.size() - 1, Math.max(0, this.itemIndex - (this.unitDeltaTotal / this.unit)));
            String str = (String) this.data.get(min);
            if (!this.curData.equals(str)) {
                this.curData = str;
                onWheelSelected(min, str);
            }
        }
    }

    private void correctLocation() {
        int abs = Math.abs(this.unitDeltaTotal % this.unit);
        if (abs != 0) {
            int i = this.unit;
            if (((float) abs) >= ((float) i) / 2.0f) {
                correctScroll(abs - i, i - abs);
            } else {
                correctScroll(abs, -abs);
            }
            postInvalidate();
            this.mHandler.postDelayed(this, 16);
        }
    }

    private void correctScroll(int i, int i2) {
        if (this.unitDeltaTotal < 0) {
            this.mOrientation.startScroll(this.mScroller, this.unitDeltaTotal, i);
        } else {
            this.mOrientation.startScroll(this.mScroller, this.unitDeltaTotal, i2);
        }
        onWheelScrollStateChanged(2);
    }

    public void checkScrollState() {
        if (this.unitDeltaTotal > this.unitDeltaMax) {
            ICrossOrientation iCrossOrientation = this.mOrientation;
            WheelScroller wheelScroller = this.mScroller;
            int i = this.unitDeltaTotal;
            iCrossOrientation.startScroll(wheelScroller, i, this.unitDeltaMax - i);
        }
        if (this.unitDeltaTotal < this.unitDeltaMin) {
            ICrossOrientation iCrossOrientation2 = this.mOrientation;
            WheelScroller wheelScroller2 = this.mScroller;
            int i2 = this.unitDeltaTotal;
            iCrossOrientation2.startScroll(wheelScroller2, i2, this.unitDeltaMin - i2);
        }
        this.mHandler.post(this);
    }

    public void setCurrentTextColor(int i) {
        super.setCurrentTextColor(i);
        invalidate(this.rectCurItem);
    }

    public void setWheelDecor(boolean z, AbstractWheelDecor abstractWheelDecor) {
        super.setWheelDecor(z, abstractWheelDecor);
        invalidate(this.rectCurItem);
    }

    public void setTextSize(int i) {
        super.setTextSize(i);
        clearCache();
    }

    public void setItemSpace(int i) {
        super.setItemSpace(i);
        clearCache();
    }

    public void setItemCount(int i) {
        super.setItemCount(i);
        clearCache();
    }

    public void setData(List<String> list) {
        super.setData(list);
        clearCache();
    }
}
