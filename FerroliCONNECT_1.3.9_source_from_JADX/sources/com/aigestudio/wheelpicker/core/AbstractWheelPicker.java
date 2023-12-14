package com.aigestudio.wheelpicker.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.p000v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import com.aigestudio.wheelpicker.C0662R;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractWheelPicker extends View implements IWheelPicker {
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SCROLLING = 2;
    private static final int TOUCH_DISTANCE_MINIMUM = 8;
    private static final int VELOCITY_TRACKER_UNITS = 150;
    protected String curData;
    protected int curTextColor;
    protected List<String> data;
    protected int diSingleMoveX;
    protected int diSingleMoveY;
    protected int disTotalMoveX;
    protected int disTotalMoveY;
    protected boolean hasSameSize;
    protected boolean ignorePadding;
    protected int itemCount;
    protected int itemIndex;
    protected int itemSpace;
    protected int lastX;
    protected int lastY;
    protected Rect mDrawBound;
    protected Handler mHandler;
    protected OnWheelChangeListener mListener;
    protected Paint mPaint;
    protected WheelScroller mScroller;
    protected Rect mTextBound;
    protected TextPaint mTextPaint;
    protected VelocityTracker mTracker;
    protected AbstractWheelDecor mWheelDecor;
    protected int maxTextHeight;
    protected int maxTextWidth;
    protected int state = 0;
    protected int textColor;
    protected int textSize;
    protected int wheelCenterTextY;
    protected int wheelCenterX;
    protected int wheelCenterY;
    protected int wheelContentHeight;
    protected int wheelContentWidth;

    public interface OnWheelChangeListener {
        void onWheelScrollStateChanged(int i);

        void onWheelScrolling(float f, float f2);

        void onWheelSelected(int i, String str);
    }

    public static class SimpleWheelChangeListener implements OnWheelChangeListener {
        public void onWheelScrollStateChanged(int i) {
        }

        public void onWheelScrolling(float f, float f2) {
        }

        public void onWheelSelected(int i, String str) {
        }
    }

    /* access modifiers changed from: protected */
    public abstract void drawBackground(Canvas canvas);

    /* access modifiers changed from: protected */
    public abstract void drawForeground(Canvas canvas);

    /* access modifiers changed from: protected */
    public abstract void drawItems(Canvas canvas);

    /* access modifiers changed from: protected */
    public abstract void onTouchDown(MotionEvent motionEvent);

    /* access modifiers changed from: protected */
    public abstract void onTouchMove(MotionEvent motionEvent);

    /* access modifiers changed from: protected */
    public abstract void onTouchUp(MotionEvent motionEvent);

    public AbstractWheelPicker(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public AbstractWheelPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        obtainAttrs(attributeSet);
        instantiation();
        assignment();
        computeWheelSizes();
    }

    /* access modifiers changed from: protected */
    public void obtainAttrs(AttributeSet attributeSet) {
        int i = C0662R.array.WheelArrayDefault;
        int dimensionPixelSize = getResources().getDimensionPixelSize(C0662R.dimen.WheelItemSpace);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C0662R.dimen.WheelTextSize);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0662R.styleable.AbstractWheelPicker);
            int resourceId = obtainStyledAttributes.getResourceId(C0662R.styleable.AbstractWheelPicker_wheel_data, 0);
            if (resourceId != 0) {
                i = resourceId;
            }
            this.data = Arrays.asList(getContext().getResources().getStringArray(i));
            this.itemIndex = obtainStyledAttributes.getInt(C0662R.styleable.AbstractWheelPicker_wheel_item_index, 0);
            this.itemCount = obtainStyledAttributes.getInt(C0662R.styleable.AbstractWheelPicker_wheel_item_count, 7);
            this.itemSpace = obtainStyledAttributes.getDimensionPixelSize(C0662R.styleable.AbstractWheelPicker_wheel_item_space, dimensionPixelSize);
            this.textSize = obtainStyledAttributes.getDimensionPixelSize(C0662R.styleable.AbstractWheelPicker_wheel_text_size, dimensionPixelSize2);
            this.textColor = obtainStyledAttributes.getColor(C0662R.styleable.AbstractWheelPicker_wheel_text_color, ViewCompat.MEASURED_STATE_MASK);
            this.curTextColor = obtainStyledAttributes.getColor(C0662R.styleable.AbstractWheelPicker_wheel_text_color_current, ViewCompat.MEASURED_STATE_MASK);
            this.hasSameSize = obtainStyledAttributes.getBoolean(C0662R.styleable.AbstractWheelPicker_wheel_item_same_size, false);
            obtainStyledAttributes.recycle();
            return;
        }
        this.data = Arrays.asList(getContext().getResources().getStringArray(i));
        this.itemIndex = 0;
        this.itemCount = 7;
        this.itemSpace = dimensionPixelSize;
        this.textSize = dimensionPixelSize2;
        this.curTextColor = ViewCompat.MEASURED_STATE_MASK;
    }

    /* access modifiers changed from: protected */
    public void instantiation() {
        this.mTextPaint = new TextPaint(69);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setTextSize((float) this.textSize);
        this.mPaint = new Paint(5);
        this.mTextBound = new Rect();
        this.mDrawBound = new Rect();
        this.mHandler = new Handler();
        if (Build.VERSION.SDK_INT >= 9) {
            this.mScroller = new OverScrollerCompat(getContext(), new DecelerateInterpolator());
        } else {
            this.mScroller = new ScrollerCompat(getContext(), new DecelerateInterpolator());
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.mScroller.setFriction(ViewConfiguration.getScrollFriction() / 25.0f);
        }
    }

    /* access modifiers changed from: protected */
    public void assignment() {
        this.curData = "";
    }

    /* access modifiers changed from: protected */
    public void computeWheelSizes() {
        this.disTotalMoveX = 0;
        this.disTotalMoveY = 0;
        this.maxTextWidth = 0;
        this.maxTextHeight = 0;
        if (this.hasSameSize) {
            String str = this.data.get(0);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
            this.maxTextWidth = Math.max(this.maxTextWidth, this.mTextBound.width());
            this.maxTextHeight = Math.max(this.maxTextHeight, this.mTextBound.height());
            return;
        }
        for (String next : this.data) {
            this.mTextPaint.getTextBounds(next, 0, next.length(), this.mTextBound);
            this.maxTextWidth = Math.max(this.maxTextWidth, this.mTextBound.width());
            this.maxTextHeight = Math.max(this.maxTextHeight, this.mTextBound.height());
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i3 = this.wheelContentWidth;
        int i4 = this.wheelContentHeight;
        setMeasuredDimension(measureSize(mode, size, i3 + getPaddingLeft() + getPaddingRight()), measureSize(mode2, size2, i4 + getPaddingTop() + getPaddingBottom()));
    }

    private int measureSize(int i, int i2, int i3) {
        if (i == 1073741824) {
            return i2;
        }
        return i == Integer.MIN_VALUE ? Math.min(i3, i2) : i3;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5 = this.itemIndex;
        onWheelSelected(i5, this.data.get(i5));
        this.mDrawBound.set(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), i2 - getPaddingBottom());
        this.wheelCenterX = this.mDrawBound.centerX();
        this.wheelCenterY = this.mDrawBound.centerY();
        this.wheelCenterTextY = (int) (((float) this.wheelCenterY) - ((this.mTextPaint.ascent() + this.mTextPaint.descent()) / 2.0f));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        canvas.save();
        canvas.clipRect(this.mDrawBound);
        drawItems(canvas);
        canvas.restore();
        drawForeground(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTracker == null) {
            this.mTracker = VelocityTracker.obtain();
        }
        this.mTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.lastX = (int) motionEvent.getX();
            this.lastY = (int) motionEvent.getY();
            onTouchDown(motionEvent);
        } else if (action == 1) {
            this.disTotalMoveX += this.diSingleMoveX;
            this.disTotalMoveY += this.diSingleMoveY;
            this.diSingleMoveX = 0;
            this.diSingleMoveY = 0;
            this.mTracker.computeCurrentVelocity(VELOCITY_TRACKER_UNITS);
            onTouchUp(motionEvent);
            getParent().requestDisallowInterceptTouchEvent(false);
            this.mTracker.recycle();
            this.mTracker = null;
        } else if (action == 2) {
            this.diSingleMoveX = (int) (((float) this.diSingleMoveX) + (motionEvent.getX() - ((float) this.lastX)));
            this.diSingleMoveY = (int) (((float) this.diSingleMoveY) + (motionEvent.getY() - ((float) this.lastY)));
            this.lastX = (int) motionEvent.getX();
            this.lastY = (int) motionEvent.getY();
            onTouchMove(motionEvent);
        } else if (action == 3) {
            getParent().requestDisallowInterceptTouchEvent(false);
            this.mScroller.abortAnimation();
            this.mTracker.recycle();
            this.mTracker = null;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isEventValid() {
        return isEventValidVer() || isEventValidHor();
    }

    /* access modifiers changed from: protected */
    public boolean isEventValidHor() {
        return Math.abs(this.diSingleMoveX) > 8;
    }

    /* access modifiers changed from: protected */
    public boolean isEventValidVer() {
        return Math.abs(this.diSingleMoveY) > 8;
    }

    /* access modifiers changed from: protected */
    public void onWheelScrolling(float f, float f2) {
        OnWheelChangeListener onWheelChangeListener = this.mListener;
        if (onWheelChangeListener != null) {
            onWheelChangeListener.onWheelScrolling(f, f2);
        }
    }

    /* access modifiers changed from: protected */
    public void onWheelSelected(int i, String str) {
        OnWheelChangeListener onWheelChangeListener = this.mListener;
        if (onWheelChangeListener != null) {
            onWheelChangeListener.onWheelSelected(i, str);
        }
    }

    /* access modifiers changed from: protected */
    public void onWheelScrollStateChanged(int i) {
        if (this.state != i) {
            this.state = i;
            OnWheelChangeListener onWheelChangeListener = this.mListener;
            if (onWheelChangeListener != null) {
                onWheelChangeListener.onWheelScrollStateChanged(i);
            }
        }
    }

    public void setData(List<String> list) {
        this.data = list;
        computeWheelSizes();
        requestLayout();
    }

    public void setOnWheelChangeListener(OnWheelChangeListener onWheelChangeListener) {
        this.mListener = onWheelChangeListener;
    }

    public void setItemIndex(int i) {
        this.itemIndex = i;
        computeWheelSizes();
        requestLayout();
    }

    public void setItemSpace(int i) {
        this.itemSpace = i;
        computeWheelSizes();
        requestLayout();
    }

    public void setItemCount(int i) {
        this.itemCount = i;
        computeWheelSizes();
        requestLayout();
    }

    public void setTextColor(int i) {
        this.textColor = i;
        invalidate();
    }

    public void setTextSize(int i) {
        this.textSize = i;
        this.mTextPaint.setTextSize((float) i);
        computeWheelSizes();
        requestLayout();
    }

    public void setCurrentTextColor(int i) {
        this.curTextColor = i;
    }

    public void setWheelDecor(boolean z, AbstractWheelDecor abstractWheelDecor) {
        this.ignorePadding = z;
        this.mWheelDecor = abstractWheelDecor;
    }
}
