package com.baoyz.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

class MaterialDrawable extends RefreshDrawable implements Animatable {
    private static final int ANIMATION_DURATION = 1333;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final float ARROW_OFFSET_ANGLE = 5.0f;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 8.75f;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final int CIRCLE_BG_LIGHT = -328966;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    static final int DEFAULT = 1;
    private static final Interpolator EASE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    /* access modifiers changed from: private */
    public static final Interpolator END_CURVE_INTERPOLATOR = new EndCurveInterpolator();
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final int KEY_SHADOW_COLOR = 503316480;
    static final int LARGE = 0;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float NUM_POINTS = 5.0f;
    private static final int SHADOW_ELEVATION = 4;
    private static final float SHADOW_RADIUS = 3.5f;
    /* access modifiers changed from: private */
    public static final Interpolator START_CURVE_INTERPOLATOR = new StartCurveInterpolator();
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private final int[] COLORS = {-16777216};
    /* access modifiers changed from: private */
    public Animation mAnimation;
    private final ArrayList<Animation> mAnimators = new ArrayList<>();
    private final Drawable.Callback mCallback = new Drawable.Callback() {
        public void invalidateDrawable(Drawable drawable) {
            MaterialDrawable.this.invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            MaterialDrawable.this.scheduleSelf(runnable, j);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            MaterialDrawable.this.unscheduleSelf(runnable);
        }
    };
    private ShapeDrawable mCircle;
    private int mDiameter;
    private Animation mFinishAnimation;
    private double mHeight;
    private int mPadding;
    /* access modifiers changed from: private */
    public View mParent;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    /* access modifiers changed from: private */
    public float mRotationCount;
    private int mShadowRadius;
    private int mTop;
    private double mWidth;

    @IntDef({0, 1})
    @Retention(RetentionPolicy.CLASS)
    public @interface ProgressDrawableSize {
    }

    public int getOpacity() {
        return -3;
    }

    public MaterialDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
        this.mParent = pullRefreshLayout;
        this.mResources = context.getResources();
        this.mRing = new Ring(this.mCallback);
        this.mRing.setColors(this.COLORS);
        updateSizes(1);
        setupAnimators();
        createCircleDrawable();
        setBackgroundColor(CIRCLE_BG_LIGHT);
        this.mDiameter = dp2px(40);
        this.mTop = (-this.mDiameter) - ((getRefreshLayout().getFinalOffset() - this.mDiameter) / 2);
    }

    private void createCircleDrawable() {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.mShadowRadius = (int) (f * SHADOW_RADIUS);
        this.mCircle = new ShapeDrawable(new OvalShadow(this.mShadowRadius, (int) (20.0f * f * 2.0f)));
        this.mCircle.getPaint().setShadowLayer((float) this.mShadowRadius, (float) ((int) (0.0f * f)), (float) ((int) (1.75f * f)), KEY_SHADOW_COLOR);
        this.mPadding = this.mShadowRadius;
        this.mCircle.getPaint().setColor(-1);
    }

    private class OvalShadow extends OvalShape {
        private int mCircleDiameter;
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();
        private int mShadowRadius;

        public OvalShadow(int i, int i2) {
            this.mShadowRadius = i;
            this.mCircleDiameter = i2;
            int i3 = this.mCircleDiameter;
            this.mRadialGradient = new RadialGradient((float) (i3 / 2), (float) (i3 / 2), (float) this.mShadowRadius, new int[]{MaterialDrawable.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP);
            this.mShadowPaint.setShader(this.mRadialGradient);
        }

        public void draw(Canvas canvas, Paint paint) {
            float centerX = (float) MaterialDrawable.this.getBounds().centerX();
            float centerY = (float) MaterialDrawable.this.getBounds().centerY();
            canvas.drawCircle(centerX, centerY, (float) ((this.mCircleDiameter / 2) + this.mShadowRadius), this.mShadowPaint);
            canvas.drawCircle(centerX, centerY, (float) (this.mCircleDiameter / 2), paint);
        }
    }

    private void setSizeParameters(double d, double d2, double d3, double d4, float f, float f2) {
        Ring ring = this.mRing;
        float f3 = this.mResources.getDisplayMetrics().density;
        double d5 = (double) f3;
        Double.isNaN(d5);
        this.mWidth = d * d5;
        Double.isNaN(d5);
        this.mHeight = d2 * d5;
        ring.setStrokeWidth(((float) d4) * f3);
        Double.isNaN(d5);
        ring.setCenterRadius(d3 * d5);
        ring.setColorIndex(0);
        ring.setArrowDimensions(f * f3, f2 * f3);
        ring.setInsets((int) this.mWidth, (int) this.mHeight);
    }

    public void updateSizes(@ProgressDrawableSize int i) {
        if (i == 0) {
            setSizeParameters(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        } else {
            setSizeParameters(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        }
    }

    public void showArrow(boolean z) {
        this.mRing.setShowArrow(z);
    }

    public void setArrowScale(float f) {
        this.mRing.setArrowScale(f);
    }

    public void setStartEndTrim(float f, float f2) {
        this.mRing.setStartTrim(f);
        this.mRing.setEndTrim(f2);
    }

    public void setProgressRotation(float f) {
        this.mRing.setRotation(f);
    }

    public void setBackgroundColor(int i) {
        this.mRing.setBackgroundColor(i);
    }

    public void setPercent(float f) {
        if (f >= 0.4f) {
            float f2 = (f - 0.4f) / 0.6f;
            setAlpha((int) (255.0f * f2));
            showArrow(true);
            float f3 = 0.0f;
            setStartEndTrim(0.0f, Math.min(0.8f, f2 * 0.8f));
            setArrowScale(Math.min(1.0f, f2));
            if (f2 >= 0.8f) {
                f3 = ((f2 - 0.8f) / 0.2f) * 0.25f;
            }
            setProgressRotation(f3);
        }
    }

    public void setColorSchemeColors(int... iArr) {
        this.mRing.setColors(iArr);
        this.mRing.setColorIndex(0);
    }

    public void offsetTopAndBottom(int i) {
        this.mTop += i;
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.translate(0.0f, (float) this.mTop);
        this.mCircle.draw(canvas);
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        this.mRing.draw(canvas, bounds);
        canvas.restoreToCount(save);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        int i5 = (i3 - i) / 2;
        int i6 = this.mDiameter;
        super.setBounds(i5 - (i6 / 2), i2, i5 + (i6 / 2), i6 + i2);
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }

    public void setAlpha(int i) {
        this.mRing.setAlpha(i);
    }

    public int getAlpha() {
        return this.mRing.getAlpha();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mRing.setColorFilter(colorFilter);
    }

    /* access modifiers changed from: package-private */
    public void setRotation(float f) {
        this.mRotation = f;
        invalidateSelf();
    }

    private float getRotation() {
        return this.mRotation;
    }

    public boolean isRunning() {
        ArrayList<Animation> arrayList = this.mAnimators;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Animation animation = arrayList.get(i);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        this.mAnimation.reset();
        this.mRing.storeOriginals();
        if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
            this.mParent.startAnimation(this.mFinishAnimation);
            return;
        }
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
        this.mParent.startAnimation(this.mAnimation);
    }

    public void stop() {
        this.mParent.clearAnimation();
        setRotation(0.0f);
        this.mRing.setShowArrow(false);
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
    }

    private void setupAnimators() {
        final Ring ring = this.mRing;
        C09231 r1 = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                float floor = (float) (Math.floor((double) (ring.getStartingRotation() / 0.8f)) + 1.0d);
                ring.setStartTrim(ring.getStartingStartTrim() + ((ring.getStartingEndTrim() - ring.getStartingStartTrim()) * f));
                ring.setRotation(ring.getStartingRotation() + ((floor - ring.getStartingRotation()) * f));
                ring.setArrowScale(1.0f - f);
            }
        };
        r1.setInterpolator(EASE_INTERPOLATOR);
        r1.setDuration(666);
        r1.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ring.goToNextColor();
                ring.storeOriginals();
                ring.setShowArrow(false);
                MaterialDrawable.this.mParent.startAnimation(MaterialDrawable.this.mAnimation);
            }
        });
        C09253 r2 = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                double strokeWidth = (double) ring.getStrokeWidth();
                Double.isNaN(strokeWidth);
                float radians = (float) Math.toRadians(strokeWidth / (ring.getCenterRadius() * 6.283185307179586d));
                float startingEndTrim = ring.getStartingEndTrim();
                float startingStartTrim = ring.getStartingStartTrim();
                float startingRotation = ring.getStartingRotation();
                ring.setEndTrim(startingEndTrim + ((0.8f - radians) * MaterialDrawable.START_CURVE_INTERPOLATOR.getInterpolation(f)));
                ring.setStartTrim(startingStartTrim + (MaterialDrawable.END_CURVE_INTERPOLATOR.getInterpolation(f) * 0.8f));
                ring.setRotation(startingRotation + (0.25f * f));
                MaterialDrawable.this.setRotation((f * 144.0f) + ((MaterialDrawable.this.mRotationCount / 5.0f) * 720.0f));
            }
        };
        r2.setRepeatCount(-1);
        r2.setRepeatMode(1);
        r2.setInterpolator(LINEAR_INTERPOLATOR);
        r2.setDuration(1333);
        r2.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                float unused = MaterialDrawable.this.mRotationCount = 0.0f;
            }

            public void onAnimationRepeat(Animation animation) {
                ring.storeOriginals();
                ring.goToNextColor();
                Ring ring = ring;
                ring.setStartTrim(ring.getEndTrim());
                MaterialDrawable materialDrawable = MaterialDrawable.this;
                float unused = materialDrawable.mRotationCount = (materialDrawable.mRotationCount + 1.0f) % 5.0f;
            }
        });
        this.mFinishAnimation = r1;
        this.mAnimation = r2;
    }

    private static class Ring {
        private int mAlpha;
        private Path mArrow;
        private int mArrowHeight;
        private final Paint mArrowPaint = new Paint();
        private float mArrowScale;
        private int mArrowWidth;
        private int mBackgroundColor;
        private final Drawable.Callback mCallback;
        private final Paint mCirclePaint = new Paint();
        private int mColorIndex;
        private int[] mColors;
        private float mEndTrim = 0.0f;
        private final Paint mPaint = new Paint();
        private double mRingCenterRadius;
        private float mRotation = 0.0f;
        private boolean mShowArrow;
        private float mStartTrim = 0.0f;
        private float mStartingEndTrim;
        private float mStartingRotation;
        private float mStartingStartTrim;
        private float mStrokeInset = MaterialDrawable.STROKE_WIDTH;
        private float mStrokeWidth = 5.0f;
        private final RectF mTempBounds = new RectF();

        public Ring(Drawable.Callback callback) {
            this.mCallback = callback;
            this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mArrowPaint.setStyle(Paint.Style.FILL);
            this.mArrowPaint.setAntiAlias(true);
        }

        public void setBackgroundColor(int i) {
            this.mBackgroundColor = i;
        }

        public void setArrowDimensions(float f, float f2) {
            this.mArrowWidth = (int) f;
            this.mArrowHeight = (int) f2;
        }

        public void draw(Canvas canvas, Rect rect) {
            RectF rectF = this.mTempBounds;
            rectF.set(rect);
            float f = this.mStrokeInset;
            rectF.inset(f, f);
            float f2 = this.mStartTrim;
            float f3 = this.mRotation;
            float f4 = (f2 + f3) * 360.0f;
            float f5 = ((this.mEndTrim + f3) * 360.0f) - f4;
            this.mPaint.setColor(this.mColors[this.mColorIndex]);
            canvas.drawArc(rectF, f4, f5, false, this.mPaint);
            drawTriangle(canvas, f4, f5, rect);
            if (this.mAlpha < 255) {
                this.mCirclePaint.setColor(this.mBackgroundColor);
                this.mCirclePaint.setAlpha(255 - this.mAlpha);
                canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), (float) (rect.width() / 2), this.mCirclePaint);
            }
        }

        private void drawTriangle(Canvas canvas, float f, float f2, Rect rect) {
            if (this.mShowArrow) {
                Path path = this.mArrow;
                if (path == null) {
                    this.mArrow = new Path();
                    this.mArrow.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                float f3 = ((float) (((int) this.mStrokeInset) / 2)) * this.mArrowScale;
                double cos = this.mRingCenterRadius * Math.cos(0.0d);
                double exactCenterX = (double) rect.exactCenterX();
                Double.isNaN(exactCenterX);
                double sin = this.mRingCenterRadius * Math.sin(0.0d);
                double exactCenterY = (double) rect.exactCenterY();
                Double.isNaN(exactCenterY);
                float f4 = (float) (sin + exactCenterY);
                this.mArrow.moveTo(0.0f, 0.0f);
                this.mArrow.lineTo(((float) this.mArrowWidth) * this.mArrowScale, 0.0f);
                Path path2 = this.mArrow;
                float f5 = this.mArrowScale;
                path2.lineTo((((float) this.mArrowWidth) * f5) / 2.0f, ((float) this.mArrowHeight) * f5);
                this.mArrow.offset(((float) (cos + exactCenterX)) - f3, f4);
                this.mArrow.close();
                this.mArrowPaint.setColor(this.mColors[this.mColorIndex]);
                canvas.rotate((f + f2) - 5.0f, rect.exactCenterX(), rect.exactCenterY());
                canvas.drawPath(this.mArrow, this.mArrowPaint);
            }
        }

        public void setColors(@NonNull int[] iArr) {
            this.mColors = iArr;
            setColorIndex(0);
        }

        public void setColorIndex(int i) {
            this.mColorIndex = i;
        }

        public void goToNextColor() {
            this.mColorIndex = (this.mColorIndex + 1) % this.mColors.length;
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        public void setAlpha(int i) {
            this.mAlpha = i;
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public void setStrokeWidth(float f) {
            this.mStrokeWidth = f;
            this.mPaint.setStrokeWidth(f);
            invalidateSelf();
        }

        public float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        public void setStartTrim(float f) {
            this.mStartTrim = f;
            invalidateSelf();
        }

        public float getStartTrim() {
            return this.mStartTrim;
        }

        public float getStartingStartTrim() {
            return this.mStartingStartTrim;
        }

        public float getStartingEndTrim() {
            return this.mStartingEndTrim;
        }

        public void setEndTrim(float f) {
            this.mEndTrim = f;
            invalidateSelf();
        }

        public float getEndTrim() {
            return this.mEndTrim;
        }

        public void setRotation(float f) {
            this.mRotation = f;
            invalidateSelf();
        }

        public float getRotation() {
            return this.mRotation;
        }

        public void setInsets(int i, int i2) {
            double d;
            float min = (float) Math.min(i, i2);
            double d2 = this.mRingCenterRadius;
            if (d2 <= 0.0d || min < 0.0f) {
                d = Math.ceil((double) (this.mStrokeWidth / 2.0f));
            } else {
                double d3 = (double) (min / 2.0f);
                Double.isNaN(d3);
                d = d3 - d2;
            }
            this.mStrokeInset = (float) d;
        }

        public float getInsets() {
            return this.mStrokeInset;
        }

        public void setCenterRadius(double d) {
            this.mRingCenterRadius = d;
        }

        public double getCenterRadius() {
            return this.mRingCenterRadius;
        }

        public void setShowArrow(boolean z) {
            if (this.mShowArrow != z) {
                this.mShowArrow = z;
                invalidateSelf();
            }
        }

        public void setArrowScale(float f) {
            if (f != this.mArrowScale) {
                this.mArrowScale = f;
                invalidateSelf();
            }
        }

        public float getStartingRotation() {
            return this.mStartingRotation;
        }

        public void storeOriginals() {
            this.mStartingStartTrim = this.mStartTrim;
            this.mStartingEndTrim = this.mEndTrim;
            this.mStartingRotation = this.mRotation;
        }

        public void resetOriginals() {
            this.mStartingStartTrim = 0.0f;
            this.mStartingEndTrim = 0.0f;
            this.mStartingRotation = 0.0f;
            setStartTrim(0.0f);
            setEndTrim(0.0f);
            setRotation(0.0f);
        }

        private void invalidateSelf() {
            this.mCallback.invalidateDrawable((Drawable) null);
        }
    }

    private static class EndCurveInterpolator extends AccelerateDecelerateInterpolator {
        private EndCurveInterpolator() {
        }

        public float getInterpolation(float f) {
            return super.getInterpolation(Math.max(0.0f, (f - 0.5f) * 2.0f));
        }
    }

    private static class StartCurveInterpolator extends AccelerateDecelerateInterpolator {
        private StartCurveInterpolator() {
        }

        public float getInterpolation(float f) {
            return super.getInterpolation(Math.min(1.0f, f * 2.0f));
        }
    }
}
