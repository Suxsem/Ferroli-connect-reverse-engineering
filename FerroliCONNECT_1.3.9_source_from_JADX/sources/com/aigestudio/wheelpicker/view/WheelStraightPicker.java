package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class WheelStraightPicker extends WheelCrossPicker {
    public WheelStraightPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WheelStraightPicker(Context context) {
        super(context);
    }

    public void computeWheelSizes() {
        super.computeWheelSizes();
        this.wheelContentWidth = this.mOrientation.getStraightWidth(this.itemCount, this.itemSpace, this.maxTextWidth, this.maxTextHeight);
        this.wheelContentHeight = this.mOrientation.getStraightHeight(this.itemCount, this.itemSpace, this.maxTextWidth, this.maxTextHeight);
        this.unit = this.mOrientation.getStraightUnit(this.itemSpace, this.maxTextWidth, this.maxTextHeight);
        int display = this.mOrientation.getDisplay(this.itemCount, this.itemSpace, this.maxTextWidth, this.maxTextHeight);
        this.unitDisplayMin = -display;
        this.unitDisplayMax = display;
        this.unitDeltaMin = (-this.unit) * ((this.data.size() - this.itemIndex) - 1);
        this.unitDeltaMax = this.unit * this.itemIndex;
    }

    /* access modifiers changed from: protected */
    public void drawItems(Canvas canvas) {
        int i;
        Canvas canvas2 = canvas;
        int i2 = -this.itemIndex;
        while (i2 < this.data.size() - this.itemIndex) {
            int computeStraightUnit = this.mOrientation.computeStraightUnit(this.unit, i2, this.disTotalMoveX, this.disTotalMoveY, this.diSingleMoveX, this.diSingleMoveY);
            if (computeStraightUnit > this.unitDisplayMax || computeStraightUnit < this.unitDisplayMin) {
                i = i2;
            } else {
                canvas.save();
                canvas2.clipRect(this.rectCurItem, Region.Op.DIFFERENCE);
                this.mTextPaint.setColor(this.textColor);
                this.mTextPaint.setAlpha(255 - ((Math.abs(computeStraightUnit) * 255) / this.unitDisplayMax));
                int i3 = computeStraightUnit;
                i = i2;
                this.mOrientation.draw(canvas, this.mTextPaint, (String) this.data.get(this.itemIndex + i2), i3, this.wheelCenterX, this.wheelCenterTextY);
                canvas.restore();
                canvas.save();
                canvas2.clipRect(this.rectCurItem);
                this.mTextPaint.setColor(this.curTextColor);
                this.mOrientation.draw(canvas, this.mTextPaint, (String) this.data.get(i + this.itemIndex), i3, this.wheelCenterX, this.wheelCenterTextY);
                canvas.restore();
            }
            i2 = i + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void onTouchMove(MotionEvent motionEvent) {
        super.onTouchMove(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onTouchUp(MotionEvent motionEvent) {
        this.unitDeltaTotal = this.mOrientation.getUnitDeltaTotal(this.disTotalMoveX, this.disTotalMoveY);
        super.onTouchUp(motionEvent);
    }
}
