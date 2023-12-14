package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class WaterWaveView extends View {
    private static final int FPS = 25;
    private Interpolator interpolator = new CycleInterpolator(0.5f);
    private boolean mFillAllView;
    private float mFillWaveSourceShapeRadius;
    private Wave mLastRmoveWave;
    private float mMaxWaveAreaRadius;
    private float mStirStep;
    private float mViewCenterX;
    private float mViewCenterY;
    private final Paint mWaveCenterShapePaint;
    /* access modifiers changed from: private */
    public int mWaveColor;
    private float mWaveEndWidth;
    private float mWaveIntervalSize;
    private final Paint mWavePaint = new Paint();
    /* access modifiers changed from: private */
    public float mWaveStartWidth;
    private final List<Wave> mWaves;

    public WaterWaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWavePaint.setAntiAlias(true);
        this.mWavePaint.setStyle(Paint.Style.STROKE);
        this.mWaveCenterShapePaint = new Paint();
        this.mWaveCenterShapePaint.setAntiAlias(true);
        this.mWaveCenterShapePaint.setStyle(Paint.Style.FILL);
        this.mFillAllView = false;
        this.mWaves = new ArrayList();
        init();
    }

    public WaterWaveView(Context context) {
        super(context);
        this.mWavePaint.setAntiAlias(true);
        this.mWavePaint.setStyle(Paint.Style.STROKE);
        this.mWaveCenterShapePaint = new Paint();
        this.mWaveCenterShapePaint.setAntiAlias(true);
        this.mWaveCenterShapePaint.setStyle(Paint.Style.FILL);
        this.mFillAllView = false;
        this.mWaves = new ArrayList();
        init();
    }

    private void init() {
        setWaveInfo(60.0f, 2.0f, 2.0f, 15.0f, -16776961);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        super.onLayout(z, i, i2, i3, i4);
        this.mViewCenterX = (float) (getWidth() / 2);
        this.mViewCenterY = (float) (getHeight() / 2);
        float f2 = this.mMaxWaveAreaRadius;
        if (this.mFillAllView) {
            float f3 = this.mViewCenterX;
            float f4 = this.mViewCenterY;
            f = (float) Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
        } else {
            f = Math.min(this.mViewCenterX, this.mViewCenterY);
        }
        if (this.mMaxWaveAreaRadius != f) {
            this.mMaxWaveAreaRadius = f;
            resetWave();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        stir();
        for (Wave next : this.mWaves) {
            this.mWavePaint.setColor(next.color);
            this.mWavePaint.setStrokeWidth(next.width);
            canvas.drawCircle(this.mViewCenterX, this.mViewCenterY, next.radius, this.mWavePaint);
        }
        float f = this.mFillWaveSourceShapeRadius;
        if (f > 0.0f) {
            canvas.drawCircle(this.mViewCenterX, this.mViewCenterY, f, this.mWaveCenterShapePaint);
        }
        postInvalidateDelayed(25);
    }

    class Wave {
        public int color;
        public float radius;
        public float width;

        public Wave() {
            reset();
        }

        public void reset() {
            this.radius = 0.0f;
            this.width = WaterWaveView.this.mWaveStartWidth;
            this.color = WaterWaveView.this.mWaveColor;
        }

        public String toString() {
            return "Wave [radius=" + this.radius + ", width=" + this.width + ", color=" + this.color + "]";
        }
    }

    private void stir() {
        Wave wave = this.mWaves.isEmpty() ? null : this.mWaves.get(0);
        if (wave == null || wave.radius >= this.mWaveIntervalSize) {
            Wave wave2 = this.mLastRmoveWave;
            if (wave2 != null) {
                this.mLastRmoveWave = null;
                wave2.reset();
            } else {
                wave2 = new Wave();
            }
            this.mWaves.add(0, wave2);
        }
        float f = this.mWaveEndWidth - this.mWaveStartWidth;
        int size = this.mWaves.size();
        for (int i = 0; i < size; i++) {
            Wave wave3 = this.mWaves.get(i);
            float f2 = wave3.radius / this.mMaxWaveAreaRadius;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            wave3.width = this.mWaveStartWidth + (f2 * f);
            wave3.radius += this.mStirStep;
            wave3.color = (((int) (this.interpolator.getInterpolation(f2) * 255.0f)) << 24) | (this.mWaveColor & ViewCompat.MEASURED_SIZE_MASK);
        }
        int i2 = size - 1;
        Wave wave4 = this.mWaves.get(i2);
        if (wave4.radius > this.mMaxWaveAreaRadius + (wave4.width / 2.0f)) {
            this.mWaves.remove(i2);
        }
    }

    public void setFillAllView(boolean z) {
        this.mFillAllView = z;
        resetWave();
    }

    public void resetWave() {
        this.mWaves.clear();
        postInvalidate();
    }

    public void setFillWaveSourceShapeRadius(float f) {
        this.mFillWaveSourceShapeRadius = f;
    }

    public void setWaveInfo(float f, float f2, float f3, float f4, int i) {
        this.mWaveIntervalSize = f;
        this.mStirStep = f2;
        this.mWaveStartWidth = f3;
        this.mWaveEndWidth = f4;
        setWaveColor(i);
        resetWave();
    }

    public void setWaveColor(int i) {
        this.mWaveColor = i;
        this.mWaveCenterShapePaint.setColor(this.mWaveColor);
    }
}
