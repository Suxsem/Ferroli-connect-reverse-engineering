package com.triggertrap.seekarc;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SeekArc extends View {
    private static int INVALID_PROGRESS_VALUE = -1;
    private static final String TAG = "SeekArc";
    private final int mAngleOffset = -90;
    private Paint mArcPaint;
    private int mArcRadius = 0;
    private RectF mArcRect = new RectF();
    private int mArcWidth = 2;
    private boolean mClockwise = true;
    private boolean mEnabled = true;
    private int mMax = 100;
    private OnSeekArcChangeListener mOnSeekArcChangeListener;
    private int mProgress = 0;
    private Paint mProgressPaint;
    private float mProgressSweep = 0.0f;
    private int mProgressWidth = 4;
    private int mRotation = 0;
    private boolean mRoundedEdges = false;
    private int mStartAngle = 0;
    private int mSweepAngle = 360;
    private Drawable mThumb;
    private int mThumbXPos;
    private int mThumbYPos;
    private double mTouchAngle;
    private float mTouchIgnoreRadius;
    private boolean mTouchInside = true;
    private int mTranslateX;
    private int mTranslateY;

    public interface OnSeekArcChangeListener {
        void onProgressChanged(SeekArc seekArc, int i, boolean z);

        void onStartTrackingTouch(SeekArc seekArc);

        void onStopTrackingTouch(SeekArc seekArc);
    }

    public SeekArc(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0);
    }

    public SeekArc(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, C2229R.attr.seekArcStyle);
    }

    public SeekArc(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        Log.d(TAG, "Initialising SeekArc");
        Resources resources = getResources();
        float f = context.getResources().getDisplayMetrics().density;
        int color = resources.getColor(C2229R.color.progress_gray);
        int color2 = resources.getColor(C2229R.color.default_blue_light);
        this.mThumb = resources.getDrawable(C2229R.C2230drawable.seek_arc_control_selector);
        this.mProgressWidth = (int) (((float) this.mProgressWidth) * f);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2229R.styleable.SeekArc, i, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(C2229R.styleable.SeekArc_thumb);
            if (drawable != null) {
                this.mThumb = drawable;
            }
            int intrinsicHeight = this.mThumb.getIntrinsicHeight() / 2;
            int intrinsicWidth = this.mThumb.getIntrinsicWidth() / 2;
            this.mThumb.setBounds(-intrinsicWidth, -intrinsicHeight, intrinsicWidth, intrinsicHeight);
            this.mMax = obtainStyledAttributes.getInteger(C2229R.styleable.SeekArc_max, this.mMax);
            this.mProgress = obtainStyledAttributes.getInteger(C2229R.styleable.SeekArc_progress, this.mProgress);
            this.mProgressWidth = (int) obtainStyledAttributes.getDimension(C2229R.styleable.SeekArc_progressWidth, (float) this.mProgressWidth);
            this.mArcWidth = (int) obtainStyledAttributes.getDimension(C2229R.styleable.SeekArc_arcWidth, (float) this.mArcWidth);
            this.mStartAngle = obtainStyledAttributes.getInt(C2229R.styleable.SeekArc_startAngle, this.mStartAngle);
            this.mSweepAngle = obtainStyledAttributes.getInt(C2229R.styleable.SeekArc_sweepAngle, this.mSweepAngle);
            this.mRotation = obtainStyledAttributes.getInt(C2229R.styleable.SeekArc_rotation, this.mRotation);
            this.mRoundedEdges = obtainStyledAttributes.getBoolean(C2229R.styleable.SeekArc_roundEdges, this.mRoundedEdges);
            this.mTouchInside = obtainStyledAttributes.getBoolean(C2229R.styleable.SeekArc_touchInside, this.mTouchInside);
            this.mClockwise = obtainStyledAttributes.getBoolean(C2229R.styleable.SeekArc_clockwise, this.mClockwise);
            this.mEnabled = obtainStyledAttributes.getBoolean(C2229R.styleable.SeekArc_enabled, this.mEnabled);
            color = obtainStyledAttributes.getColor(C2229R.styleable.SeekArc_arcColor, color);
            color2 = obtainStyledAttributes.getColor(C2229R.styleable.SeekArc_progressColor, color2);
            obtainStyledAttributes.recycle();
        }
        int i2 = this.mProgress;
        int i3 = this.mMax;
        if (i2 > i3) {
            i2 = i3;
        }
        this.mProgress = i2;
        int i4 = this.mProgress;
        if (i4 < 0) {
            i4 = 0;
        }
        this.mProgress = i4;
        int i5 = this.mSweepAngle;
        if (i5 > 360) {
            i5 = 360;
        }
        this.mSweepAngle = i5;
        int i6 = this.mSweepAngle;
        if (i6 < 0) {
            i6 = 0;
        }
        this.mSweepAngle = i6;
        this.mProgressSweep = (((float) this.mProgress) / ((float) this.mMax)) * ((float) this.mSweepAngle);
        int i7 = this.mStartAngle;
        if (i7 > 360) {
            i7 = 0;
        }
        this.mStartAngle = i7;
        int i8 = this.mStartAngle;
        if (i8 < 0) {
            i8 = 0;
        }
        this.mStartAngle = i8;
        this.mArcPaint = new Paint();
        this.mArcPaint.setColor(color);
        this.mArcPaint.setAntiAlias(true);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth((float) this.mArcWidth);
        this.mProgressPaint = new Paint();
        this.mProgressPaint.setColor(color2);
        this.mProgressPaint.setAntiAlias(true);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth((float) this.mProgressWidth);
        if (this.mRoundedEdges) {
            this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.mClockwise) {
            canvas.scale(-1.0f, 1.0f, this.mArcRect.centerX(), this.mArcRect.centerY());
        }
        int i = (this.mStartAngle - 90) + this.mRotation;
        float f = (float) i;
        Canvas canvas2 = canvas;
        float f2 = f;
        canvas2.drawArc(this.mArcRect, f2, (float) this.mSweepAngle, false, this.mArcPaint);
        canvas.drawArc(this.mArcRect, f, this.mProgressSweep, false, this.mProgressPaint);
        if (this.mEnabled) {
            canvas.translate((float) (this.mTranslateX - this.mThumbXPos), (float) (this.mTranslateY - this.mThumbYPos));
            this.mThumb.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(getSuggestedMinimumHeight(), i2);
        int defaultSize2 = getDefaultSize(getSuggestedMinimumWidth(), i);
        int min = Math.min(defaultSize2, defaultSize);
        this.mTranslateX = (int) (((float) defaultSize2) * 0.5f);
        this.mTranslateY = (int) (((float) defaultSize) * 0.5f);
        int paddingLeft = min - getPaddingLeft();
        int i3 = paddingLeft / 2;
        this.mArcRadius = i3;
        float f = (float) ((defaultSize / 2) - i3);
        float f2 = (float) ((defaultSize2 / 2) - i3);
        float f3 = (float) paddingLeft;
        this.mArcRect.set(f2, f, f2 + f3, f3 + f);
        double d = (double) this.mArcRadius;
        double d2 = (double) (((int) this.mProgressSweep) + this.mStartAngle + this.mRotation + 90);
        double cos = Math.cos(Math.toRadians(d2));
        Double.isNaN(d);
        this.mThumbXPos = (int) (d * cos);
        double d3 = (double) this.mArcRadius;
        double sin = Math.sin(Math.toRadians(d2));
        Double.isNaN(d3);
        this.mThumbYPos = (int) (d3 * sin);
        setTouchInSide(this.mTouchInside);
        super.onMeasure(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        int action = motionEvent.getAction();
        if (action == 0) {
            onStartTrackingTouch();
            updateOnTouch(motionEvent);
        } else if (action == 1) {
            onStopTrackingTouch();
            setPressed(false);
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (action == 2) {
            updateOnTouch(motionEvent);
        } else if (action == 3) {
            onStopTrackingTouch();
            setPressed(false);
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mThumb;
        if (drawable != null && drawable.isStateful()) {
            this.mThumb.setState(getDrawableState());
        }
        invalidate();
    }

    private void onStartTrackingTouch() {
        OnSeekArcChangeListener onSeekArcChangeListener = this.mOnSeekArcChangeListener;
        if (onSeekArcChangeListener != null) {
            onSeekArcChangeListener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        OnSeekArcChangeListener onSeekArcChangeListener = this.mOnSeekArcChangeListener;
        if (onSeekArcChangeListener != null) {
            onSeekArcChangeListener.onStopTrackingTouch(this);
        }
    }

    private void updateOnTouch(MotionEvent motionEvent) {
        if (!ignoreTouch(motionEvent.getX(), motionEvent.getY())) {
            setPressed(true);
            this.mTouchAngle = getTouchDegrees(motionEvent.getX(), motionEvent.getY());
            onProgressRefresh(getProgressForAngle(this.mTouchAngle), true);
        }
    }

    private boolean ignoreTouch(float f, float f2) {
        float f3 = f - ((float) this.mTranslateX);
        float f4 = f2 - ((float) this.mTranslateY);
        return ((float) Math.sqrt((double) ((f3 * f3) + (f4 * f4)))) < this.mTouchIgnoreRadius;
    }

    private double getTouchDegrees(float f, float f2) {
        float f3 = f - ((float) this.mTranslateX);
        float f4 = f2 - ((float) this.mTranslateY);
        if (!this.mClockwise) {
            f3 = -f3;
        }
        double degrees = Math.toDegrees((Math.atan2((double) f4, (double) f3) + 1.5707963267948966d) - Math.toRadians((double) this.mRotation));
        if (degrees < 0.0d) {
            degrees += 360.0d;
        }
        double d = (double) this.mStartAngle;
        Double.isNaN(d);
        return degrees - d;
    }

    private int getProgressForAngle(double d) {
        double valuePerDegree = (double) valuePerDegree();
        Double.isNaN(valuePerDegree);
        int round = (int) Math.round(valuePerDegree * d);
        if (round < 0) {
            round = INVALID_PROGRESS_VALUE;
        }
        return round > this.mMax ? INVALID_PROGRESS_VALUE : round;
    }

    private float valuePerDegree() {
        return ((float) this.mMax) / ((float) this.mSweepAngle);
    }

    private void onProgressRefresh(int i, boolean z) {
        updateProgress(i, z);
    }

    private void updateThumbPosition() {
        double d = (double) this.mArcRadius;
        double d2 = (double) ((int) (((float) this.mStartAngle) + this.mProgressSweep + ((float) this.mRotation) + 90.0f));
        double cos = Math.cos(Math.toRadians(d2));
        Double.isNaN(d);
        this.mThumbXPos = (int) (d * cos);
        double d3 = (double) this.mArcRadius;
        double sin = Math.sin(Math.toRadians(d2));
        Double.isNaN(d3);
        this.mThumbYPos = (int) (d3 * sin);
    }

    private void updateProgress(int i, boolean z) {
        if (i != INVALID_PROGRESS_VALUE) {
            OnSeekArcChangeListener onSeekArcChangeListener = this.mOnSeekArcChangeListener;
            if (onSeekArcChangeListener != null) {
                onSeekArcChangeListener.onProgressChanged(this, i, z);
            }
            int i2 = this.mMax;
            if (i > i2) {
                i = i2;
            }
            if (i < 0) {
                i = 0;
            }
            this.mProgress = i;
            this.mProgressSweep = (((float) i) / ((float) this.mMax)) * ((float) this.mSweepAngle);
            updateThumbPosition();
            invalidate();
        }
    }

    public void setOnSeekArcChangeListener(OnSeekArcChangeListener onSeekArcChangeListener) {
        this.mOnSeekArcChangeListener = onSeekArcChangeListener;
    }

    public void setProgress(int i) {
        updateProgress(i, false);
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressWidth() {
        return this.mProgressWidth;
    }

    public void setProgressWidth(int i) {
        this.mProgressWidth = i;
        this.mProgressPaint.setStrokeWidth((float) i);
    }

    public int getArcWidth() {
        return this.mArcWidth;
    }

    public void setArcWidth(int i) {
        this.mArcWidth = i;
        this.mArcPaint.setStrokeWidth((float) i);
    }

    public int getArcRotation() {
        return this.mRotation;
    }

    public void setArcRotation(int i) {
        this.mRotation = i;
        updateThumbPosition();
    }

    public int getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(int i) {
        this.mStartAngle = i;
        updateThumbPosition();
    }

    public int getSweepAngle() {
        return this.mSweepAngle;
    }

    public void setSweepAngle(int i) {
        this.mSweepAngle = i;
        updateThumbPosition();
    }

    public void setRoundedEdges(boolean z) {
        this.mRoundedEdges = z;
        if (this.mRoundedEdges) {
            this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
            return;
        }
        this.mArcPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.mProgressPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    public void setTouchInSide(boolean z) {
        int intrinsicHeight = this.mThumb.getIntrinsicHeight() / 2;
        int intrinsicWidth = this.mThumb.getIntrinsicWidth() / 2;
        this.mTouchInside = z;
        if (this.mTouchInside) {
            this.mTouchIgnoreRadius = ((float) this.mArcRadius) / 4.0f;
        } else {
            this.mTouchIgnoreRadius = (float) (this.mArcRadius - Math.min(intrinsicWidth, intrinsicHeight));
        }
    }

    public void setClockwise(boolean z) {
        this.mClockwise = z;
    }

    public boolean isClockwise() {
        return this.mClockwise;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public int getProgressColor() {
        return this.mProgressPaint.getColor();
    }

    public void setProgressColor(int i) {
        this.mProgressPaint.setColor(i);
        invalidate();
    }

    public int getArcColor() {
        return this.mArcPaint.getColor();
    }

    public void setArcColor(int i) {
        this.mArcPaint.setColor(i);
        invalidate();
    }
}
