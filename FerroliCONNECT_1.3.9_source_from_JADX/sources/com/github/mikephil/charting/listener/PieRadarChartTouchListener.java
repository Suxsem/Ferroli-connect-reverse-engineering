package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.SelectionDetail;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class PieRadarChartTouchListener extends ChartTouchListener<PieRadarChartBase<?>> {
    private ArrayList<AngularVelocitySample> _velocitySamples = new ArrayList<>();
    private float mDecelerationAngularVelocity = 0.0f;
    private long mDecelerationLastTime = 0;
    private float mStartAngle = 0.0f;
    private PointF mTouchStartPoint = new PointF();

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    public PieRadarChartTouchListener(PieRadarChartBase<?> pieRadarChartBase) {
        super(pieRadarChartBase);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.mGestureDetector.onTouchEvent(motionEvent) && ((PieRadarChartBase) this.mChart).isRotationEnabled()) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int action = motionEvent.getAction();
            if (action == 0) {
                startAction(motionEvent);
                stopDeceleration();
                resetVelocity();
                if (((PieRadarChartBase) this.mChart).isDragDecelerationEnabled()) {
                    sampleVelocity(x, y);
                }
                setGestureStartAngle(x, y);
                PointF pointF = this.mTouchStartPoint;
                pointF.x = x;
                pointF.y = y;
            } else if (action == 1) {
                if (((PieRadarChartBase) this.mChart).isDragDecelerationEnabled()) {
                    stopDeceleration();
                    sampleVelocity(x, y);
                    this.mDecelerationAngularVelocity = calculateVelocity();
                    if (this.mDecelerationAngularVelocity != 0.0f) {
                        this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                        Utils.postInvalidateOnAnimation(this.mChart);
                    }
                }
                ((PieRadarChartBase) this.mChart).enableScroll();
                this.mTouchMode = 0;
                endAction(motionEvent);
            } else if (action == 2) {
                if (((PieRadarChartBase) this.mChart).isDragDecelerationEnabled()) {
                    sampleVelocity(x, y);
                }
                if (this.mTouchMode == 0 && distance(x, this.mTouchStartPoint.x, y, this.mTouchStartPoint.y) > Utils.convertDpToPixel(8.0f)) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.ROTATE;
                    this.mTouchMode = 6;
                    ((PieRadarChartBase) this.mChart).disableScroll();
                } else if (this.mTouchMode == 6) {
                    updateGestureRotation(x, y);
                    ((PieRadarChartBase) this.mChart).invalidate();
                }
                endAction(motionEvent);
            }
        }
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase) this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartLongPressed(motionEvent);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase) this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        int i = 0;
        if (!((PieRadarChartBase) this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        float distanceToCenter = ((PieRadarChartBase) this.mChart).distanceToCenter(motionEvent.getX(), motionEvent.getY());
        if (distanceToCenter > ((PieRadarChartBase) this.mChart).getRadius()) {
            if (this.mLastHighlighted == null) {
                ((PieRadarChartBase) this.mChart).highlightValues((Highlight[]) null);
            } else {
                ((PieRadarChartBase) this.mChart).highlightTouch((Highlight) null);
            }
            this.mLastHighlighted = null;
            return true;
        }
        float angleForPoint = ((PieRadarChartBase) this.mChart).getAngleForPoint(motionEvent.getX(), motionEvent.getY());
        if (this.mChart instanceof PieChart) {
            angleForPoint /= ((PieRadarChartBase) this.mChart).getAnimator().getPhaseY();
        }
        int indexForAngle = ((PieRadarChartBase) this.mChart).getIndexForAngle(angleForPoint);
        if (indexForAngle < 0) {
            ((PieRadarChartBase) this.mChart).highlightValues((Highlight[]) null);
            this.mLastHighlighted = null;
            return true;
        }
        List<SelectionDetail> selectionDetailsAtIndex = ((PieRadarChartBase) this.mChart).getSelectionDetailsAtIndex(indexForAngle);
        if (this.mChart instanceof RadarChart) {
            i = Utils.getClosestDataSetIndexByValue(selectionDetailsAtIndex, distanceToCenter / ((RadarChart) this.mChart).getFactor(), (YAxis.AxisDependency) null);
        }
        if (i < 0) {
            ((PieRadarChartBase) this.mChart).highlightValues((Highlight[]) null);
            this.mLastHighlighted = null;
            return true;
        }
        performHighlight(new Highlight(indexForAngle, i), motionEvent);
        return true;
    }

    private void resetVelocity() {
        this._velocitySamples.clear();
    }

    private void sampleVelocity(float f, float f2) {
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this._velocitySamples.add(new AngularVelocitySample(currentAnimationTimeMillis, ((PieRadarChartBase) this.mChart).getAngleForPoint(f, f2)));
        for (int size = this._velocitySamples.size(); size - 2 > 0 && currentAnimationTimeMillis - this._velocitySamples.get(0).time > 1000; size--) {
            this._velocitySamples.remove(0);
        }
    }

    private float calculateVelocity() {
        if (this._velocitySamples.isEmpty()) {
            return 0.0f;
        }
        boolean z = false;
        AngularVelocitySample angularVelocitySample = this._velocitySamples.get(0);
        ArrayList<AngularVelocitySample> arrayList = this._velocitySamples;
        AngularVelocitySample angularVelocitySample2 = arrayList.get(arrayList.size() - 1);
        AngularVelocitySample angularVelocitySample3 = angularVelocitySample;
        for (int size = this._velocitySamples.size() - 1; size >= 0; size--) {
            angularVelocitySample3 = this._velocitySamples.get(size);
            if (angularVelocitySample3.angle != angularVelocitySample2.angle) {
                break;
            }
        }
        float f = ((float) (angularVelocitySample2.time - angularVelocitySample.time)) / 1000.0f;
        if (f == 0.0f) {
            f = 0.1f;
        }
        if (angularVelocitySample2.angle >= angularVelocitySample3.angle) {
            z = true;
        }
        if (((double) Math.abs(angularVelocitySample2.angle - angularVelocitySample3.angle)) > 270.0d) {
            z = !z;
        }
        if (((double) (angularVelocitySample2.angle - angularVelocitySample.angle)) > 180.0d) {
            double d = (double) angularVelocitySample.angle;
            Double.isNaN(d);
            angularVelocitySample.angle = (float) (d + 360.0d);
        } else if (((double) (angularVelocitySample.angle - angularVelocitySample2.angle)) > 180.0d) {
            double d2 = (double) angularVelocitySample2.angle;
            Double.isNaN(d2);
            angularVelocitySample2.angle = (float) (d2 + 360.0d);
        }
        float abs = Math.abs((angularVelocitySample2.angle - angularVelocitySample.angle) / f);
        return !z ? -abs : abs;
    }

    public void setGestureStartAngle(float f, float f2) {
        this.mStartAngle = ((PieRadarChartBase) this.mChart).getAngleForPoint(f, f2) - ((PieRadarChartBase) this.mChart).getRawRotationAngle();
    }

    public void updateGestureRotation(float f, float f2) {
        ((PieRadarChartBase) this.mChart).setRotationAngle(((PieRadarChartBase) this.mChart).getAngleForPoint(f, f2) - this.mStartAngle);
    }

    public void stopDeceleration() {
        this.mDecelerationAngularVelocity = 0.0f;
    }

    public void computeScroll() {
        if (this.mDecelerationAngularVelocity != 0.0f) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mDecelerationAngularVelocity *= ((PieRadarChartBase) this.mChart).getDragDecelerationFrictionCoef();
            ((PieRadarChartBase) this.mChart).setRotationAngle(((PieRadarChartBase) this.mChart).getRotationAngle() + (this.mDecelerationAngularVelocity * (((float) (currentAnimationTimeMillis - this.mDecelerationLastTime)) / 1000.0f)));
            this.mDecelerationLastTime = currentAnimationTimeMillis;
            if (((double) Math.abs(this.mDecelerationAngularVelocity)) >= 0.001d) {
                Utils.postInvalidateOnAnimation(this.mChart);
            } else {
                stopDeceleration();
            }
        }
    }

    private class AngularVelocitySample {
        public float angle;
        public long time;

        public AngularVelocitySample(long j, float f) {
            this.time = j;
            this.angle = f;
        }
    }
}
