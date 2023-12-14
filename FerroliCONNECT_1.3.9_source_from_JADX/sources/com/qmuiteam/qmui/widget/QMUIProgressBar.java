package com.qmuiteam.qmui.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class QMUIProgressBar extends View {
    public static int DEFAULT_BACKGROUND_COLOR = -7829368;
    public static int DEFAULT_PROGRESS_COLOR = -16776961;
    public static int DEFAULT_STROKE_WIDTH = QMUIDisplayHelper.dpToPx(40);
    public static int DEFAULT_TEXT_COLOR = -16777216;
    public static int DEFAULT_TEXT_SIZE = 20;
    public static int TOTAL_DURATION = 1000;
    public static int TYPE_CIRCLE = 1;
    public static int TYPE_RECT;
    /* access modifiers changed from: private */
    public boolean isAnimating = false;
    private ValueAnimator mAnimator;
    private RectF mArcOval = new RectF();
    private int mBackgroundColor;
    private Paint mBackgroundPaint = new Paint();
    RectF mBgRect;
    private Point mCenterPoint;
    private int mCircleRadius;
    private int mHeight;
    private int mMaxValue;
    private Paint mPaint = new Paint();
    private int mProgressColor;
    RectF mProgressRect;
    QMUIProgressBarTextGenerator mQMUIProgressBarTextGenerator;
    private int mStrokeWidth;
    private String mText = "";
    private Paint mTextPaint = new Paint(1);
    private int mType;
    /* access modifiers changed from: private */
    public int mValue;
    private int mWidth;

    public interface QMUIProgressBarTextGenerator {
        String generateText(QMUIProgressBar qMUIProgressBar, int i, int i2);
    }

    public QMUIProgressBar(Context context) {
        super(context);
        setup(context, (AttributeSet) null);
    }

    public QMUIProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup(context, attributeSet);
    }

    public QMUIProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setup(context, attributeSet);
    }

    public void setup(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIProgressBar);
        this.mType = obtainStyledAttributes.getInt(C1614R.styleable.QMUIProgressBar_qmui_type, TYPE_RECT);
        this.mProgressColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIProgressBar_qmui_progress_color, DEFAULT_PROGRESS_COLOR);
        this.mBackgroundColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIProgressBar_qmui_background_color, DEFAULT_BACKGROUND_COLOR);
        this.mMaxValue = obtainStyledAttributes.getInt(C1614R.styleable.QMUIProgressBar_qmui_max_value, 100);
        this.mValue = obtainStyledAttributes.getInt(C1614R.styleable.QMUIProgressBar_qmui_value, 0);
        boolean z = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIProgressBar_qmui_stroke_round_cap, false);
        int i = DEFAULT_TEXT_SIZE;
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUIProgressBar_android_textSize)) {
            i = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIProgressBar_android_textSize, DEFAULT_TEXT_SIZE);
        }
        int i2 = DEFAULT_TEXT_COLOR;
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUIProgressBar_android_textColor)) {
            i2 = obtainStyledAttributes.getColor(C1614R.styleable.QMUIProgressBar_android_textColor, DEFAULT_TEXT_COLOR);
        }
        if (this.mType == TYPE_CIRCLE) {
            this.mStrokeWidth = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIProgressBar_qmui_stroke_width, DEFAULT_STROKE_WIDTH);
        }
        obtainStyledAttributes.recycle();
        configPaint(i2, i, z);
        setProgress(this.mValue);
    }

    private void configShape() {
        if (this.mType == TYPE_RECT) {
            this.mBgRect = new RectF((float) getPaddingLeft(), (float) getPaddingTop(), (float) (this.mWidth + getPaddingLeft()), (float) (this.mHeight + getPaddingTop()));
            this.mProgressRect = new RectF();
            return;
        }
        this.mCircleRadius = (Math.min(this.mWidth, this.mHeight) - this.mStrokeWidth) / 2;
        this.mCenterPoint = new Point(this.mWidth / 2, this.mHeight / 2);
    }

    private void configPaint(int i, int i2, boolean z) {
        this.mPaint.setColor(this.mProgressColor);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        if (this.mType == TYPE_RECT) {
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        } else {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mPaint.setAntiAlias(true);
            if (z) {
                this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            }
            this.mBackgroundPaint.setStyle(Paint.Style.STROKE);
            this.mBackgroundPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mBackgroundPaint.setAntiAlias(true);
        }
        this.mTextPaint.setColor(i);
        this.mTextPaint.setTextSize((float) i2);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextSize(int i) {
        this.mTextPaint.setTextSize((float) i);
        invalidate();
    }

    public void setTextColor(int i) {
        this.mTextPaint.setColor(i);
        invalidate();
    }

    public void setStrokeRoundCap(boolean z) {
        this.mPaint.setStrokeCap(z ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        invalidate();
    }

    public void setQMUIProgressBarTextGenerator(QMUIProgressBarTextGenerator qMUIProgressBarTextGenerator) {
        this.mQMUIProgressBarTextGenerator = qMUIProgressBarTextGenerator;
    }

    public QMUIProgressBarTextGenerator getQMUIProgressBarTextGenerator() {
        return this.mQMUIProgressBarTextGenerator;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        QMUIProgressBarTextGenerator qMUIProgressBarTextGenerator = this.mQMUIProgressBarTextGenerator;
        if (qMUIProgressBarTextGenerator != null) {
            this.mText = qMUIProgressBarTextGenerator.generateText(this, this.mValue, this.mMaxValue);
        }
        if (this.mType == TYPE_RECT) {
            drawRect(canvas);
        } else {
            drawCircle(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        this.mHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        configShape();
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    private void drawRect(Canvas canvas) {
        canvas.drawRect(this.mBgRect, this.mBackgroundPaint);
        this.mProgressRect.set((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getPaddingLeft() + parseValueToWidth()), (float) (getPaddingTop() + this.mHeight));
        canvas.drawRect(this.mProgressRect, this.mPaint);
        String str = this.mText;
        if (str != null && str.length() > 0) {
            Paint.FontMetricsInt fontMetricsInt = this.mTextPaint.getFontMetricsInt();
            canvas.drawText(this.mText, this.mBgRect.centerX(), (this.mBgRect.top + (((this.mBgRect.height() - ((float) fontMetricsInt.bottom)) + ((float) fontMetricsInt.top)) / 2.0f)) - ((float) fontMetricsInt.top), this.mTextPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle((float) this.mCenterPoint.x, (float) this.mCenterPoint.y, (float) this.mCircleRadius, this.mBackgroundPaint);
        this.mArcOval.left = (float) (this.mCenterPoint.x - this.mCircleRadius);
        this.mArcOval.right = (float) (this.mCenterPoint.x + this.mCircleRadius);
        this.mArcOval.top = (float) (this.mCenterPoint.y - this.mCircleRadius);
        this.mArcOval.bottom = (float) (this.mCenterPoint.y + this.mCircleRadius);
        canvas.drawArc(this.mArcOval, 270.0f, (float) ((this.mValue * 360) / this.mMaxValue), false, this.mPaint);
        String str = this.mText;
        if (str != null && str.length() > 0) {
            Paint.FontMetricsInt fontMetricsInt = this.mTextPaint.getFontMetricsInt();
            canvas.drawText(this.mText, (float) this.mCenterPoint.x, (this.mArcOval.top + (((this.mArcOval.height() - ((float) fontMetricsInt.bottom)) + ((float) fontMetricsInt.top)) / 2.0f)) - ((float) fontMetricsInt.top), this.mTextPaint);
        }
    }

    private int parseValueToWidth() {
        return (this.mWidth * this.mValue) / this.mMaxValue;
    }

    public int getProgress() {
        return this.mValue;
    }

    public void setProgress(int i) {
        if (i <= this.mValue || i >= 0) {
            if (this.isAnimating) {
                this.isAnimating = false;
                this.mAnimator.cancel();
            }
            int i2 = this.mValue;
            this.mValue = i;
            startAnimation(i2, i);
        }
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int i) {
        this.mMaxValue = i;
    }

    private void startAnimation(int i, int i2) {
        this.mAnimator = ValueAnimator.ofInt(new int[]{i, i2});
        this.mAnimator.setDuration((long) Math.abs((TOTAL_DURATION * (i2 - i)) / this.mMaxValue));
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int unused = QMUIProgressBar.this.mValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                QMUIProgressBar.this.invalidate();
            }
        });
        this.mAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                boolean unused = QMUIProgressBar.this.isAnimating = true;
            }

            public void onAnimationEnd(Animator animator) {
                boolean unused = QMUIProgressBar.this.isAnimating = false;
            }
        });
        this.mAnimator.start();
    }
}
