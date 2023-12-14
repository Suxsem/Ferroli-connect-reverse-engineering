package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.HashMap;

public class WheelCurvedPicker extends WheelCrossPicker {
    private final HashMap<Integer, Integer> DEPTH = new HashMap<>();
    private final HashMap<Integer, Integer> SPACE = new HashMap<>();
    private final Camera camera = new Camera();
    private int degreeIndex;
    private int degreeSingleDelta;
    private int degreeUnitDelta;
    private final Matrix matrixDepth = new Matrix();
    private final Matrix matrixRotate = new Matrix();
    private int radius;

    public WheelCurvedPicker(Context context) {
        super(context);
    }

    public WheelCurvedPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void computeWheelSizes() {
        super.computeWheelSizes();
        this.radius = this.mOrientation.computeRadius(this.itemCount, this.itemSpace, this.maxTextWidth, this.maxTextHeight);
        this.unit = (int) (180.0f / ((float) (this.itemCount + 1)));
        this.wheelContentWidth = this.mOrientation.getCurvedWidth(this.radius, this.maxTextWidth, this.maxTextHeight);
        this.wheelContentHeight = this.mOrientation.getCurvedHeight(this.radius, this.maxTextWidth, this.maxTextHeight);
        this.unitDisplayMin = -90;
        this.unitDisplayMax = 90;
        this.unitDeltaMin = (-this.unit) * ((this.data.size() - this.itemIndex) - 1);
        this.unitDeltaMax = this.unit * this.itemIndex;
    }

    /* access modifiers changed from: protected */
    public void drawItems(Canvas canvas) {
        for (int i = -this.itemIndex; i < this.data.size() - this.itemIndex; i++) {
            int i2 = (this.unit * i) + this.unitDeltaTotal + this.degreeSingleDelta;
            if (i2 <= this.unitDisplayMax && i2 >= this.unitDisplayMin) {
                int computeSpace = computeSpace(i2);
                if (computeSpace == 0) {
                    i2 = 1;
                }
                int computeDepth = computeDepth(i2);
                this.camera.save();
                this.mOrientation.rotateCamera(this.camera, i2);
                this.camera.getMatrix(this.matrixRotate);
                this.camera.restore();
                this.mOrientation.matrixToCenter(this.matrixRotate, computeSpace, this.wheelCenterX, this.wheelCenterY);
                this.camera.save();
                this.camera.translate(0.0f, 0.0f, (float) computeDepth);
                this.camera.getMatrix(this.matrixDepth);
                this.camera.restore();
                this.mOrientation.matrixToCenter(this.matrixDepth, computeSpace, this.wheelCenterX, this.wheelCenterY);
                this.matrixRotate.postConcat(this.matrixDepth);
                canvas.save();
                canvas.concat(this.matrixRotate);
                canvas.clipRect(this.rectCurItem, Region.Op.DIFFERENCE);
                this.mTextPaint.setColor(this.textColor);
                this.mTextPaint.setAlpha(255 - ((Math.abs(i2) * 255) / this.unitDisplayMax));
                int i3 = computeSpace;
                this.mOrientation.draw(canvas, this.mTextPaint, (String) this.data.get(this.itemIndex + i), i3, this.wheelCenterX, this.wheelCenterTextY);
                canvas.restore();
                canvas.save();
                canvas.clipRect(this.rectCurItem);
                this.mTextPaint.setColor(this.curTextColor);
                this.mOrientation.draw(canvas, this.mTextPaint, (String) this.data.get(this.itemIndex + i), i3, this.wheelCenterX, this.wheelCenterTextY);
                canvas.restore();
            }
        }
    }

    private int computeSpace(int i) {
        if (this.SPACE.containsKey(Integer.valueOf(i))) {
            return this.SPACE.get(Integer.valueOf(i)).intValue();
        }
        double sin = Math.sin(Math.toRadians((double) i));
        double d = (double) this.radius;
        Double.isNaN(d);
        int i2 = (int) (sin * d);
        this.SPACE.put(Integer.valueOf(i), Integer.valueOf(i2));
        return i2;
    }

    private int computeDepth(int i) {
        if (this.DEPTH.containsKey(Integer.valueOf(i))) {
            return this.DEPTH.get(Integer.valueOf(i)).intValue();
        }
        double d = (double) this.radius;
        double cos = Math.cos(Math.toRadians((double) i));
        double d2 = (double) this.radius;
        Double.isNaN(d2);
        Double.isNaN(d);
        int i2 = (int) (d - (cos * d2));
        this.DEPTH.put(Integer.valueOf(i), Integer.valueOf(i2));
        return i2;
    }

    /* access modifiers changed from: protected */
    public void onTouchMove(MotionEvent motionEvent) {
        this.degreeUnitDelta = this.mOrientation.computeDegreeSingleDelta(this.diSingleMoveX, this.diSingleMoveY, this.radius);
        int obtainCurrentDis = this.mOrientation.obtainCurrentDis(this.diSingleMoveX, this.diSingleMoveY);
        if (Math.abs(obtainCurrentDis) >= this.radius) {
            if (obtainCurrentDis >= 0) {
                this.degreeIndex++;
            } else {
                this.degreeIndex--;
            }
            this.diSingleMoveX = 0;
            this.diSingleMoveY = 0;
            this.degreeUnitDelta = 0;
        }
        this.degreeSingleDelta = (this.degreeIndex * 80) + this.degreeUnitDelta;
        super.onTouchMove(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onTouchUp(MotionEvent motionEvent) {
        this.unitDeltaTotal += this.degreeSingleDelta;
        this.degreeSingleDelta = 0;
        this.degreeUnitDelta = 0;
        this.degreeIndex = 0;
        super.onTouchUp(motionEvent);
    }

    public void clearCache() {
        this.SPACE.clear();
        this.DEPTH.clear();
        this.mOrientation.clearCache();
    }
}
