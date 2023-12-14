package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.TypedValue;
import java.security.InvalidParameterException;

class CirclesDrawable extends RefreshDrawable implements Runnable {
    private static final int ALPHA_OPAQUE = 255;
    private static final float CIRCLE_COUNT = ((float) ProgressStates.values().length);
    private static final float MAX_LEVEL = 10000.0f;
    private static final float MAX_LEVEL_PER_CIRCLE = (MAX_LEVEL / CIRCLE_COUNT);
    private static int mColor1;
    private static int mColor2;
    private static int mColor3;
    private static int mColor4;
    private int fstColor;
    private boolean goesBackward;
    private boolean isRunning;
    private Paint mAbovePaint;
    private int mAxisValue;
    private Rect mBounds;
    private ColorFilter mColorFilter;
    private int mControlPointMaximum;
    private int mControlPointMinimum;
    private ProgressStates mCurrentState;
    private int mDiameter;
    private int mDrawHeight;
    private int mDrawWidth;
    private Paint mFstHalfPaint;
    private int mHalf;
    private Handler mHandler = new Handler();
    private int mLevel;
    private RectF mOval = new RectF();
    private Path mPath;
    private Paint mScndHalfPaint;
    private int mTop;
    private int scndColor;

    private enum ProgressStates {
        FOLDING_DOWN,
        FOLDING_LEFT,
        FOLDING_UP,
        FOLDING_RIGHT
    }

    public CirclesDrawable(Context context, PullRefreshLayout pullRefreshLayout) {
        super(context, pullRefreshLayout);
    }

    public void start() {
        this.mLevel = 2500;
        this.isRunning = true;
        this.mHandler.postDelayed(this, 10);
    }

    public void stop() {
        this.isRunning = false;
        this.mHandler.removeCallbacks(this);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setColorSchemeColors(int[] iArr) {
        initCirclesProgress(iArr);
    }

    public void setPercent(float f) {
        updateLevel((int) (f * 2500.0f));
    }

    private void updateLevel(int i) {
        boolean z = false;
        if (((float) i) == MAX_LEVEL) {
            i = 0;
        }
        float f = (float) i;
        this.mCurrentState = ProgressStates.values()[(int) (f / MAX_LEVEL_PER_CIRCLE)];
        resetColor(this.mCurrentState);
        float f2 = MAX_LEVEL_PER_CIRCLE;
        int i2 = (int) (f % f2);
        if (this.goesBackward) {
            if (i2 == ((int) (f % (f2 / 2.0f)))) {
                z = true;
            }
            i2 = (int) (MAX_LEVEL_PER_CIRCLE - ((float) i2));
        } else if (i2 != ((int) (f % (f2 / 2.0f)))) {
            z = true;
        }
        this.mFstHalfPaint.setColor(this.fstColor);
        this.mScndHalfPaint.setColor(this.scndColor);
        if (!z) {
            this.mAbovePaint.setColor(this.mScndHalfPaint.getColor());
        } else {
            this.mAbovePaint.setColor(this.mFstHalfPaint.getColor());
        }
        float f3 = (float) i2;
        this.mAbovePaint.setAlpha(((int) ((f3 / MAX_LEVEL_PER_CIRCLE) * 55.0f)) + 200);
        int i3 = this.mControlPointMinimum;
        this.mAxisValue = (int) (((float) i3) + (((float) (this.mControlPointMaximum - i3)) * (f3 / MAX_LEVEL_PER_CIRCLE)));
    }

    public void offsetTopAndBottom(int i) {
        this.mTop += i;
        invalidateSelf();
    }

    public void run() {
        this.mLevel += 80;
        if (((float) this.mLevel) > MAX_LEVEL) {
            this.mLevel = 0;
        }
        if (this.isRunning) {
            this.mHandler.postDelayed(this, 20);
            updateLevel(this.mLevel);
            invalidateSelf();
        }
    }

    private void initCirclesProgress(int[] iArr) {
        initColors(iArr);
        this.mPath = new Path();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        this.mFstHalfPaint = new Paint(paint);
        this.mScndHalfPaint = new Paint(paint);
        this.mAbovePaint = new Paint(paint);
        setColorFilter(this.mColorFilter);
    }

    private void initColors(int[] iArr) {
        if (iArr == null || iArr.length < 4) {
            throw new InvalidParameterException("The color scheme length must be 4");
        }
        mColor1 = iArr[0];
        mColor2 = iArr[1];
        mColor3 = iArr[2];
        mColor4 = iArr[3];
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawWidth = dp2px(40);
        this.mDrawHeight = this.mDrawWidth;
        int finalOffset = getRefreshLayout().getFinalOffset();
        int i = this.mDrawHeight;
        this.mTop = (-this.mDrawHeight) - ((finalOffset - i) / 2);
        this.mBounds = rect;
        measureCircleProgress(this.mDrawWidth, i);
    }

    /* renamed from: com.baoyz.widget.CirclesDrawable$1 */
    static /* synthetic */ class C09221 {
        static final /* synthetic */ int[] $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates = new int[ProgressStates.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.baoyz.widget.CirclesDrawable$ProgressStates[] r0 = com.baoyz.widget.CirclesDrawable.ProgressStates.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates = r0
                int[] r0 = $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.baoyz.widget.CirclesDrawable$ProgressStates r1 = com.baoyz.widget.CirclesDrawable.ProgressStates.FOLDING_DOWN     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates     // Catch:{ NoSuchFieldError -> 0x001f }
                com.baoyz.widget.CirclesDrawable$ProgressStates r1 = com.baoyz.widget.CirclesDrawable.ProgressStates.FOLDING_LEFT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates     // Catch:{ NoSuchFieldError -> 0x002a }
                com.baoyz.widget.CirclesDrawable$ProgressStates r1 = com.baoyz.widget.CirclesDrawable.ProgressStates.FOLDING_UP     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.baoyz.widget.CirclesDrawable$ProgressStates r1 = com.baoyz.widget.CirclesDrawable.ProgressStates.FOLDING_RIGHT     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baoyz.widget.CirclesDrawable.C09221.<clinit>():void");
        }
    }

    private void resetColor(ProgressStates progressStates) {
        int i = C09221.$SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates[progressStates.ordinal()];
        if (i == 1) {
            this.fstColor = mColor1;
            this.scndColor = mColor2;
            this.goesBackward = false;
        } else if (i == 2) {
            this.fstColor = mColor1;
            this.scndColor = mColor3;
            this.goesBackward = true;
        } else if (i == 3) {
            this.fstColor = mColor3;
            this.scndColor = mColor4;
            this.goesBackward = true;
        } else if (i == 4) {
            this.fstColor = mColor2;
            this.scndColor = mColor4;
            this.goesBackward = false;
        }
    }

    public void draw(Canvas canvas) {
        if (this.mCurrentState != null) {
            canvas.save();
            canvas.translate((float) ((this.mBounds.width() / 2) - (this.mDrawWidth / 2)), (float) this.mTop);
            makeCirclesProgress(canvas);
            canvas.restore();
        }
    }

    private void measureCircleProgress(int i, int i2) {
        this.mDiameter = Math.min(i, i2);
        int i3 = this.mDiameter;
        this.mHalf = i3 / 2;
        this.mOval.set(0.0f, 0.0f, (float) i3, (float) i3);
        int i4 = this.mDiameter;
        this.mControlPointMinimum = (-i4) / 6;
        this.mControlPointMaximum = i4 + (i4 / 6);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0014, code lost:
        if (r0 != 4) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void makeCirclesProgress(android.graphics.Canvas r3) {
        /*
            r2 = this;
            int[] r0 = com.baoyz.widget.CirclesDrawable.C09221.$SwitchMap$com$baoyz$widget$CirclesDrawable$ProgressStates
            com.baoyz.widget.CirclesDrawable$ProgressStates r1 = r2.mCurrentState
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 1
            if (r0 == r1) goto L_0x001b
            r1 = 2
            if (r0 == r1) goto L_0x0017
            r1 = 3
            if (r0 == r1) goto L_0x001b
            r1 = 4
            if (r0 == r1) goto L_0x0017
            goto L_0x001e
        L_0x0017:
            r2.drawXMotion(r3)
            goto L_0x001e
        L_0x001b:
            r2.drawYMotion(r3)
        L_0x001e:
            android.graphics.Path r0 = r2.mPath
            android.graphics.Paint r1 = r2.mAbovePaint
            r3.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baoyz.widget.CirclesDrawable.makeCirclesProgress(android.graphics.Canvas):void");
    }

    private void drawXMotion(Canvas canvas) {
        canvas.drawArc(this.mOval, 90.0f, 180.0f, true, this.mFstHalfPaint);
        canvas.drawArc(this.mOval, -270.0f, -180.0f, true, this.mScndHalfPaint);
        this.mPath.reset();
        this.mPath.moveTo((float) this.mHalf, 0.0f);
        Path path = this.mPath;
        int i = this.mAxisValue;
        float f = (float) i;
        float f2 = (float) i;
        int i2 = this.mDiameter;
        path.cubicTo(f, 0.0f, f2, (float) i2, (float) this.mHalf, (float) i2);
    }

    private void drawYMotion(Canvas canvas) {
        canvas.drawArc(this.mOval, 0.0f, -180.0f, true, this.mFstHalfPaint);
        canvas.drawArc(this.mOval, -180.0f, -180.0f, true, this.mScndHalfPaint);
        this.mPath.reset();
        this.mPath.moveTo(0.0f, (float) this.mHalf);
        Path path = this.mPath;
        int i = this.mAxisValue;
        int i2 = this.mDiameter;
        path.cubicTo(0.0f, (float) i, (float) i2, (float) i, (float) i2, (float) this.mHalf);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mFstHalfPaint.setColorFilter(colorFilter);
        this.mScndHalfPaint.setColorFilter(colorFilter);
        this.mAbovePaint.setColorFilter(colorFilter);
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }
}
