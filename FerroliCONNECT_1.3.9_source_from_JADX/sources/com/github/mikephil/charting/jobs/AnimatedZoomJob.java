package com.github.mikephil.charting.jobs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

@SuppressLint({"NewApi"})
public class AnimatedZoomJob extends AnimatedViewPortJob implements Animator.AnimatorListener {
    protected float xValCount;
    protected YAxis yAxis;
    protected float zoomCenterX;
    protected float zoomCenterY;
    protected float zoomOriginX;
    protected float zoomOriginY;

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @SuppressLint({"NewApi"})
    public AnimatedZoomJob(ViewPortHandler viewPortHandler, View view, Transformer transformer, YAxis yAxis2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, long j) {
        super(viewPortHandler, f2, f3, transformer, view, f4, f5, j);
        this.zoomCenterX = f6;
        this.zoomCenterY = f7;
        this.zoomOriginX = f8;
        this.zoomOriginY = f9;
        this.animator.addListener(this);
        this.yAxis = yAxis2;
        this.xValCount = f;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mViewPortHandler.refresh(this.mViewPortHandler.setZoom(this.xOrigin + ((this.xValue - this.xOrigin) * this.phase), this.yOrigin + ((this.yValue - this.yOrigin) * this.phase)), this.view, false);
        float scaleY = this.yAxis.mAxisRange / this.mViewPortHandler.getScaleY();
        float scaleX = this.xValCount / this.mViewPortHandler.getScaleX();
        float[] fArr = this.pts;
        float f = this.zoomOriginX;
        fArr[0] = f + (((this.zoomCenterX - (scaleX / 2.0f)) - f) * this.phase);
        float[] fArr2 = this.pts;
        float f2 = this.zoomOriginY;
        fArr2[1] = f2 + (((this.zoomCenterY + (scaleY / 2.0f)) - f2) * this.phase);
        this.mTrans.pointValuesToPixel(this.pts);
        this.mViewPortHandler.refresh(this.mViewPortHandler.translate(this.pts), this.view, true);
    }

    public void onAnimationEnd(Animator animator) {
        ((BarLineChartBase) this.view).calculateOffsets();
        this.view.postInvalidate();
    }
}
